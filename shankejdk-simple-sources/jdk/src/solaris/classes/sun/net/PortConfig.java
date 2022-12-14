/*
 * Copyright (c) 2013, Oracle and/or its affiliates. All rights reserved.
 */

package sun.net;

import java.security.AccessController;

/**
 * Determines the ephemeral port range in use on this system.
 * If this cannot be determined, then the default settings
 * of the OS are returned.
 */

public final class PortConfig {

    private static int defaultUpper, defaultLower;
    private final static int upper, lower;

    private PortConfig() {}

    static {
        AccessController.doPrivileged(
            new java.security.PrivilegedAction<Void>() {
                public Void run() {
                    System.loadLibrary("net");
                    String os = System.getProperty("os.name");
                    if (os.startsWith("Linux")) {
                        defaultLower = 32768;
                        defaultUpper = 61000;
                    } else if (os.startsWith("SunOS")) {
                        defaultLower = 32768;
                        defaultUpper = 65535;
                    } else if (os.contains("OS X")) {
                        defaultLower = 49152;
                        defaultUpper = 65535;
                    } else if (os.startsWith("AIX")) {
                        // The ephemeral port is OS version dependent on AIX:
                        // http://publib.boulder.ibm.com/infocenter/aix/v7r1/topic/com.ibm.aix.rsct315.admin/bl503_ephport.htm
                        // However, on AIX 5.3 / 6.1 / 7.1 we always see the
                        // settings below by using:
                        // /usr/sbin/no -a | fgrep ephemeral
                        defaultLower = 32768;
                        defaultUpper = 65535;
                    } else {
                        throw new InternalError(
                            "sun.net.PortConfig: unknown OS");
                    }
                    return null;
                }
            });

        int v = getLower0();
        if (v == -1) {
            v = defaultLower;
        }
        lower = v;

        v = getUpper0();
        if (v == -1) {
            v = defaultUpper;
        }
        upper = v;
    }

    static native int getLower0();
    static native int getUpper0();

    public static int getLower() {
        return lower;
    }

    public static int getUpper() {
        return upper;
    }
}
