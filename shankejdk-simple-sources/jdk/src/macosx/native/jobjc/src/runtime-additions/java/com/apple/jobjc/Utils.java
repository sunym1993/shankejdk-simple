/*
 * Copyright (c) 2011, 2012, Oracle and/or its affiliates. All rights reserved.
 */
package com.apple.jobjc;

import java.util.concurrent.Callable;

import com.apple.jobjc.foundation.NSNumber;
import com.apple.jobjc.foundation.NSString;

public class Utils {
    JObjCRuntime runtime;
    Utils(JObjCRuntime runtime){ this.runtime = runtime; }

    private static Utils utils;
    public static Utils get() {
        JObjCRuntime runtime = JObjCRuntime.getInstance(); // enforce security check
        return utils != null ? utils : (utils = new Utils(runtime));
    }

    private Strings strings_;
    public Strings strings() {
        return strings_ != null ? strings_ : (strings_ = new Strings(runtime));
    }

    private Numbers numbers_;
    public Numbers numbers() {
        return numbers_ != null ? numbers_ : (numbers_ = new Numbers(runtime));
    }

    private Threads threads_;
    public Threads threads() {
        return threads_ != null ? threads_ : (threads_ = new Threads(runtime));
    }

    public static class Strings {
        private static native long getNativeNSStringForJavaString(final String javaString);
        private static native String getNativeJavaStringForNSString(final long nsString);

        JObjCRuntime runtime;
        Strings(JObjCRuntime runtime) { this.runtime = runtime; }

        public NSString nsString(final String str) {
            if (str == null) return null;
            final long nsString = getNativeNSStringForJavaString(str);
            return ID.createNewObjCObjectForClass(NSString.class, nsString, runtime);
        }

        public String javaString(final NSString str) {
            if (str == null) return null;
            return getNativeJavaStringForNSString(((ID)str).ptr);
        }

//        static public CString cStringForJavaString(final String str) {
//            return null;
//        }
    }

    public static class Numbers {
        private static native long getNativeNSNumberForJavaNumber(final Number num);
        private static native Number getNativeJavaNumberForNSNumber(final long num);

        JObjCRuntime runtime;
        Numbers(JObjCRuntime runtime) { this.runtime = runtime; }

        public NSNumber nsNumber(final Number num) {
            if (num == null) return null;
            final long nsNumber = getNativeNSNumberForJavaNumber(num);
            return ID.createNewObjCObjectForClass(NSNumber.class, nsNumber, runtime);
        }

        public Number javaNumber(final NSNumber num) {
            if (num == null) return null;
            return getNativeJavaNumberForNSNumber(((ID)num).ptr);
        }
    }

    public static class Threads {
        private static native void performRunnableOnMainThread(final Runnable runnable, final boolean wait);
        private static native <V> V performCallableOnMainThread(final Callable<V> callable) throws Exception;

        JObjCRuntime runtime;
        Threads(JObjCRuntime runtime) { this.runtime = runtime; }

        /**
         * Perform callable on main thread. Exceptions that are thrown on the main thread are ignored.
         */
        public void performOnMainThread(final Runnable runnable, final boolean wait) {
            performRunnableOnMainThread(runnable, wait);
        }

        /**
         * Perform callable on main thread, block until done, and return the result.
         * This also catches any exceptions on the main thread, brings them back and throws them to the caller.
         */
        public <V> V performOnMainThread(final Callable<V> callable) throws Exception{
            return performCallableOnMainThread(callable);
        }
    }
}
