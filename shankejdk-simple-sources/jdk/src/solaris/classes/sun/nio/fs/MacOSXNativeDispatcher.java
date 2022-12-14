/*
 * Copyright (c) 2008, 2012, Oracle and/or its affiliates. All rights reserved.
 */

package sun.nio.fs;

import java.security.AccessController;
import java.security.PrivilegedAction;

/**
 * MacOSX specific system calls.
 */

class MacOSXNativeDispatcher extends BsdNativeDispatcher {
    private MacOSXNativeDispatcher() { }

    static final int kCFStringNormalizationFormC = 2;
    static final int kCFStringNormalizationFormD = 0;
    static native char[] normalizepath(char[] path, int form);
}
