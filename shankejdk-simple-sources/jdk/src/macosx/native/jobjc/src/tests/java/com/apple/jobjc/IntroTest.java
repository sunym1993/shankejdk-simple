/*
 * Copyright (c) 2011, 2012, Oracle and/or its affiliates. All rights reserved.
 */
package com.apple.jobjc;

import junit.framework.TestCase;

import com.apple.jobjc.Invoke.FunCall;
import com.apple.jobjc.PrimitiveCoder.DoubleCoder;
import com.apple.jobjc.appkit.AppKitFramework;
import com.apple.jobjc.appkit.NSApplication;
import com.apple.jobjc.appkit.NSApplicationClass;
import com.apple.jobjc.appkit.NSColorPanel;
import com.apple.jobjc.appkit.NSWorkspace;
import com.apple.jobjc.foundation.FoundationFramework;
import com.apple.jobjc.foundation.NSAutoreleasePool;
import com.apple.jobjc.foundation.NSPoint;
import com.apple.jobjc.foundation.NSString;
import com.apple.jobjc.foundation.NSStringClass;

public class IntroTest extends TestCase{
    // The low-level core makes function calls, sends messages, marshals data, etc.
    public void testCore(){
        // pass security check and get ahold of a runtime (should cache this)
        final JObjCRuntime RUNTIME = JObjCRuntime.getInstance();
        final NativeArgumentBuffer ARGS = JObjCRuntime.getInstance().getThreadLocalState();

        // create a funcall (should cache this)
        final FunCall fc = new FunCall(RUNTIME, "sin", DoubleCoder.INST, DoubleCoder.INST);

        // start function call
        fc.init(ARGS);
        // push an arg
        DoubleCoder.INST.push(ARGS, 3.14159265 / 2.0);
        // make the call
        fc.invoke(ARGS);
        // read the return value
        double ret = DoubleCoder.INST.pop(ARGS);

        assertEquals(1.0, ret);
    }

    // Frameworks bridge the Mac OS X frameworks
    public void testFrameworks(){
        // First, get an instance of JObjC:
        final JObjC JOBJC = com.apple.jobjc.JObjC.getInstance();

        // It's your gateway to the frameworks.
        final FoundationFramework FND = JOBJC.Foundation();
        final AppKitFramework APP = JOBJC.AppKit();

        // From which you can then access...

        // enums, defines, constants
        int nsmye = FND.NSMaxYEdge();
        boolean debug = FND.NSDebugEnabled();

        // structs
        NSPoint p = FND.makeNSPoint();
        p.setX(3);
        assertEquals(3.0, p.x());

        // C functions
        NSPoint p2 = FND.NSMakePoint(12, 34);
        assertEquals(12.0, p2.x());

        // ... Let's create an AutoreleasePool before we go on
        NSAutoreleasePool pool = ((NSAutoreleasePool) FND.NSAutoreleasePool().alloc()).init();

        // Objective-C classes
        NSStringClass nsc = FND.NSString();

        // class-methods
        NSString nsStringClassDescr = nsc.description();

        // instances
        NSString nsi = ((NSString) FND.NSString().alloc()).init();

        // instance methods
        NSString d = nsi.description();

        // The bridge marshals some types for you, but it doesn't
        // convert between NSString and Java String automatically.
        // For that we use Utils.get().strings().nsString(String)
        // and Utils.get().strings().javaString(NSString);

        assertEquals("NSString", Utils.get().strings().javaString(nsStringClassDescr));

        NSString format = Utils.get().strings().nsString("Foo bar %d baz");

        NSString formatted = ((NSString) FND.NSString().alloc()).initWithFormat(format, 34);
        String jformatted = Utils.get().strings().javaString(formatted);

        assertEquals("Foo bar 34 baz", jformatted);

        // Reveal in Finder
//        NSString file = Utils.get().strings().nsString(
//                "/Applications/Calculator.app/Contents/Resources/Calculator.icns");
//        APP.NSWorkspace().sharedWorkspace()
//           .selectFile_inFileViewerRootedAtPath(file, null);

        pool.drain();
    }
}
