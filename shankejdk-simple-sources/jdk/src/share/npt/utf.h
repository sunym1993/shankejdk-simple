/*
 * Copyright (c) 2004, 2012, Oracle and/or its affiliates. All rights reserved.
 */

/* Routines for various UTF conversions */

#ifndef  _UTF_H
#define _UTF_H

#include <stdio.h>

#include "jni.h"
#include "utf_md.h"

/* Use THIS_FILE when it is available. */
#ifndef THIS_FILE
    #define THIS_FILE __FILE__
#endif

/* Error and assert macros */
#define UTF_ERROR(m) utfError(THIS_FILE, __LINE__,  m)
#define UTF_ASSERT(x) ( (x)==0 ? UTF_ERROR("ASSERT ERROR " #x) : (void)0 )

void utfError(char *file, int line, char *message);

struct UtfInst* JNICALL utfInitialize
                            (char *options);
void            JNICALL utfTerminate
                            (struct UtfInst *ui, char *options);
int             JNICALL utf8ToPlatform
                            (struct UtfInst *ui, jbyte *utf8,
                             int len, char *output, int outputMaxLen);
int             JNICALL utf8FromPlatform
                            (struct UtfInst *ui, char *str, int len,
                             jbyte *output, int outputMaxLen);
int             JNICALL utf8ToUtf16
                            (struct UtfInst *ui, jbyte *utf8, int len,
                             jchar *output, int outputMaxLen);
int             JNICALL utf16ToUtf8m
                            (struct UtfInst *ui, jchar *utf16, int len,
                             jbyte *output, int outputMaxLen);
int             JNICALL utf16ToUtf8s
                            (struct UtfInst *ui, jchar *utf16, int len,
                             jbyte *output, int outputMaxLen);
int             JNICALL utf8sToUtf8mLength
                            (struct UtfInst *ui, jbyte *string, int length);
void            JNICALL utf8sToUtf8m
                            (struct UtfInst *ui, jbyte *string, int length,
                             jbyte *new_string, int new_length);
int             JNICALL utf8mToUtf8sLength
                            (struct UtfInst *ui, jbyte *string, int length);
void            JNICALL utf8mToUtf8s
                            (struct UtfInst *ui, jbyte *string, int length,
                             jbyte *new_string, int new_length);

#endif
