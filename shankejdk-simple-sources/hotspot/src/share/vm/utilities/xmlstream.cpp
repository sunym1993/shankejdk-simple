/*
 * Copyright (c) 2002, 2014, Oracle and/or its affiliates. All rights reserved.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * This code is free software; you can redistribute it and/or modify it
 * under the terms of the GNU General Public License version 2 only, as
 * published by the Free Software Foundation.
 *
 * This code is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE.  See the GNU General Public License
 * version 2 for more details (a copy is included in the LICENSE file that
 * accompanied this code).
 *
 * You should have received a copy of the GNU General Public License version
 * 2 along with this work; if not, write to the Free Software Foundation,
 * Inc., 51 Franklin St, Fifth Floor, Boston, MA 02110-1301 USA.
 *
 * Please contact Oracle, 500 Oracle Parkway, Redwood Shores, CA 94065 USA
 * or visit www.oracle.com if you need additional information or have any
 * questions.
 *
 */

#include "precompiled.hpp"
#include "code/nmethod.hpp"
#include "memory/allocation.hpp"
#include "memory/allocation.inline.hpp"
#include "oops/methodData.hpp"
#include "oops/method.hpp"
#include "oops/oop.inline.hpp"
#include "runtime/deoptimization.hpp"
#include "runtime/vmThread.hpp"
#include "utilities/xmlstream.hpp"

void xmlStream::initialize(outputStream* out) {
  _out = out;
  _last_flush = 0;
  _markup_state = BODY;
  _text_init._outer_xmlStream = this;
  _text = &_text_init;


  // Make sure each log uses the same base for time stamps.
  if (is_open()) {
    _out->time_stamp().update_to(1);
  }
}


// Pass the given chars directly to _out.
void xmlStream::write(const char* s, size_t len) {
  if (!is_open())  return;

  out()->write(s, len);
  update_position(s, len);
}


// Pass the given chars directly to _out, except that
// we watch for special "<&>" chars.
// This is suitable for either attribute text or for body text.
// We don't fool with "<![CDATA[" quotes, just single-character entities.
// This makes it easier for dumb tools to parse the output.
void xmlStream::write_text(const char* s, size_t len) {
  if (!is_open())  return;

  size_t written = 0;
  // All normally printed material goes inside XML quotes.
  // This leaves the output free to include markup also.
  // Scan the string looking for inadvertant "<&>" chars
  for (size_t i = 0; i < len; i++) {
    char ch = s[i];
    // Escape special chars.
    const char* esc = NULL;
    switch (ch) {
      // These are important only in attrs, but we do them always:
    case '\'': esc = "&apos;"; break;
    case '"':  esc = "&quot;"; break;
    case '<':  esc = "&lt;";   break;
    case '&':  esc = "&amp;";  break;
      // This is a freebie.
    case '>':  esc = "&gt;";   break;
    }
    if (esc != NULL) {
      if (written < i) {
        out()->write(&s[written], i - written);
        written = i;
      }
      out()->print_raw(esc);
      written++;
    }
  }

  // Print the clean remainder.  Usually, it is all of s.
  if (written < len) {
    out()->write(&s[written], len - written);
  }
}

// ------------------------------------------------------------------
// Outputs XML text, with special characters quoted.
void xmlStream::text(const char* format, ...) {
  va_list ap;
  va_start(ap, format);
  va_text(format, ap);
  va_end(ap);
}

#define BUFLEN 2*K   /* max size of output of individual print methods */

// ------------------------------------------------------------------
void xmlStream::va_tag(bool push, const char* format, va_list ap) {
  assert_if_no_error(!inside_attrs(), "cannot print tag inside attrs");
  char buffer[BUFLEN];
  size_t len;
  const char* kind = do_vsnprintf(buffer, BUFLEN, format, ap, false, len);
  see_tag(kind, push);
  print_raw("<");
  write(kind, len);
  _markup_state = (push ? HEAD : ELEM);
}



// ------------------------------------------------------------------
// First word in formatted string is element kind, and any subsequent
// words must be XML attributes.  Outputs "<kind .../>".
void xmlStream::elem(const char* format, ...) {
  va_list ap;
  va_start(ap, format);
  va_elem(format, ap);
  va_end(ap);
}

// ------------------------------------------------------------------
void xmlStream::va_elem(const char* format, va_list ap) {
  va_begin_elem(format, ap);
  end_elem();
}


// ------------------------------------------------------------------
// First word in formatted string is element kind, and any subsequent
// words must be XML attributes.  Outputs "<kind ...", not including "/>".
void xmlStream::begin_elem(const char* format, ...) {
  va_list ap;
  va_start(ap, format);
  va_tag(false, format, ap);
  va_end(ap);
}

// ------------------------------------------------------------------
void xmlStream::va_begin_elem(const char* format, va_list ap) {
  va_tag(false, format, ap);
}

// ------------------------------------------------------------------
// Outputs "/>".
void xmlStream::end_elem() {
  assert(_markup_state == ELEM, "misplaced end_elem");
  print_raw("/>\n");
  _markup_state = BODY;
}

// ------------------------------------------------------------------
// Outputs formatted text, followed by "/>".
void xmlStream::end_elem(const char* format, ...) {
  va_list ap;
  va_start(ap, format);
  out()->vprint(format, ap);
  va_end(ap);
  end_elem();
}


// ------------------------------------------------------------------
// First word in formatted string is element kind, and any subsequent
// words must be XML attributes.  Outputs "<kind ...>".
void xmlStream::head(const char* format, ...) {
  va_list ap;
  va_start(ap, format);
  va_head(format, ap);
  va_end(ap);
}

// ------------------------------------------------------------------
void xmlStream::va_head(const char* format, va_list ap) {
  va_begin_head(format, ap);
  end_head();
}

// ------------------------------------------------------------------
// First word in formatted string is element kind, and any subsequent
// words must be XML attributes.  Outputs "<kind ...", not including ">".
void xmlStream::begin_head(const char* format, ...) {
  va_list ap;
  va_start(ap, format);
  va_tag(true, format, ap);
  va_end(ap);
}

// ------------------------------------------------------------------
void xmlStream::va_begin_head(const char* format, va_list ap) {
  va_tag(true, format, ap);
}

// ------------------------------------------------------------------
// Outputs ">".
void xmlStream::end_head() {
  assert(_markup_state == HEAD, "misplaced end_head");
  print_raw(">\n");
  _markup_state = BODY;
}


// ------------------------------------------------------------------
// Outputs formatted text, followed by ">".
void xmlStream::end_head(const char* format, ...) {
  va_list ap;
  va_start(ap, format);
  out()->vprint(format, ap);
  va_end(ap);
  end_head();
}


// ------------------------------------------------------------------
// Outputs "</kind>".
void xmlStream::tail(const char* kind) {
  pop_tag(kind);
  print_raw("</");
  print_raw(kind);
  print_raw(">\n");
}

// ------------------------------------------------------------------
// Outputs "<kind_done ... stamp='D.DD'/> </kind>".
void xmlStream::done(const char* format, ...) {
  va_list ap;
  va_start(ap, format);
  va_done(format, ap);
  va_end(ap);
}

// ------------------------------------------------------------------
// Outputs "<kind_done stamp='D.DD'/> </kind>".
// Because done_raw() doesn't need to format strings, it's simpler than
// done(), and can be called safely by fatal error handler.
void xmlStream::done_raw(const char* kind) {
  print_raw("<");
  print_raw(kind);
  print_raw("_done stamp='");
  out()->stamp();
  print_raw_cr("'/>");
  print_raw("</");
  print_raw(kind);
  print_raw_cr(">");
}

PRAGMA_DIAG_PUSH
PRAGMA_FORMAT_NONLITERAL_IGNORED
// ------------------------------------------------------------------
void xmlStream::va_done(const char* format, va_list ap) {
  char buffer[200];
  guarantee(strlen(format) + 10 < sizeof(buffer), "bigger format buffer");
  const char* kind = format;
  const char* kind_end = strchr(kind, ' ');
  size_t kind_len = (kind_end != NULL) ? (kind_end - kind) : strlen(kind);
  strncpy(buffer, kind, kind_len);
  strcpy(buffer + kind_len, "_done");
  strcat(buffer, format + kind_len);
  // Output the trailing event with the timestamp.
  va_begin_elem(buffer, ap);
  stamp();
  end_elem();
  // Output the tail-tag of the enclosing element.
  buffer[kind_len] = 0;
  tail(buffer);
}
PRAGMA_DIAG_POP

// Output a timestamp attribute.
void xmlStream::stamp() {
  assert_if_no_error(inside_attrs(), "stamp must be an attribute");
  print_raw(" stamp='");
  out()->stamp();
  print_raw("'");
}


// ------------------------------------------------------------------
// Output a method attribute, in the form " method='pkg/cls name sig'".
// This is used only when there is no ciMethod available.
void xmlStream::method(methodHandle method) {
  assert_if_no_error(inside_attrs(), "printing attributes");
  if (method.is_null())  return;
  print_raw(" method='");
  method_text(method);
  print("' bytes='%d'", method->code_size());
  print(" count='%d'", method->invocation_count());
  int bec = method->backedge_count();
  if (bec != 0)  print(" backedge_count='%d'", bec);
  print(" iicount='%d'", method->interpreter_invocation_count());
  int throwouts = method->interpreter_throwout_count();
  if (throwouts != 0)  print(" throwouts='%d'", throwouts);
  MethodData* mdo = method->method_data();
  if (mdo != NULL) {
    uint cnt;
    cnt = mdo->decompile_count();
    if (cnt != 0)  print(" decompiles='%d'", cnt);
    for (uint reason = 0; reason < mdo->trap_reason_limit(); reason++) {
      cnt = mdo->trap_count(reason);
      if (cnt != 0)  print(" %s_traps='%d'", Deoptimization::trap_reason_name(reason), cnt);
    }
    cnt = mdo->overflow_trap_count();
    if (cnt != 0)  print(" overflow_traps='%d'", cnt);
    cnt = mdo->overflow_recompile_count();
    if (cnt != 0)  print(" overflow_recompiles='%d'", cnt);
  }
}

void xmlStream::method_text(methodHandle method) {
  assert_if_no_error(inside_attrs(), "printing attributes");
  if (method.is_null())  return;
  //method->print_short_name(text());
  method->method_holder()->name()->print_symbol_on(text());
  print_raw(" ");  // " " is easier for tools to parse than "::"
  method->name()->print_symbol_on(text());
  print_raw(" ");  // separator
  method->signature()->print_symbol_on(text());
}


// ------------------------------------------------------------------
// Output a klass attribute, in the form " klass='pkg/cls'".
// This is used only when there is no ciKlass available.
void xmlStream::klass(KlassHandle klass) {
  assert_if_no_error(inside_attrs(), "printing attributes");
  if (klass.is_null())  return;
  print_raw(" klass='");
  klass_text(klass);
  print_raw("'");
}

void xmlStream::klass_text(KlassHandle klass) {
  assert_if_no_error(inside_attrs(), "printing attributes");
  if (klass.is_null())  return;
  //klass->print_short_name(log->out());
  klass->name()->print_symbol_on(out());
}

void xmlStream::name(const Symbol* name) {
  assert_if_no_error(inside_attrs(), "printing attributes");
  if (name == NULL)  return;
  print_raw(" name='");
  name_text(name);
  print_raw("'");
}

void xmlStream::name_text(const Symbol* name) {
  assert_if_no_error(inside_attrs(), "printing attributes");
  if (name == NULL)  return;
  //name->print_short_name(text());
  name->print_symbol_on(text());
}

void xmlStream::object(const char* attr, Handle x) {
  assert_if_no_error(inside_attrs(), "printing attributes");
  if (x == NULL)  return;
  print_raw(" ");
  print_raw(attr);
  print_raw("='");
  object_text(x);
  print_raw("'");
}

void xmlStream::object_text(Handle x) {
  assert_if_no_error(inside_attrs(), "printing attributes");
  if (x == NULL)  return;
  x->print_value_on(text());
}


void xmlStream::object(const char* attr, Metadata* x) {
  assert_if_no_error(inside_attrs(), "printing attributes");
  if (x == NULL)  return;
  print_raw(" ");
  print_raw(attr);
  print_raw("='");
  object_text(x);
  print_raw("'");
}

void xmlStream::object_text(Metadata* x) {
  assert_if_no_error(inside_attrs(), "printing attributes");
  if (x == NULL)  return;
  //x->print_value_on(text());
  if (x->is_method())
    method_text((Method*)x);
  else if (x->is_klass())
    klass_text((Klass*)x);
  else
    ShouldNotReachHere(); // Add impl if this is reached.
}


void xmlStream::flush() {
  out()->flush();
  _last_flush = count();
}

void xmlTextStream::flush() {
  if (_outer_xmlStream == NULL)  return;
  _outer_xmlStream->flush();
}

void xmlTextStream::write(const char* str, size_t len) {
  if (_outer_xmlStream == NULL)  return;
  _outer_xmlStream->write_text(str, len);
  update_position(str, len);
}
