/*
 * Copyright (c) 1997, 2012, Oracle and/or its affiliates. All rights reserved.
 */

package com.sun.tools.internal.ws;

import com.sun.xml.internal.ws.util.Version;

/**
 * Obtains the version number of the JAX-WS tools.
 * @author Kohsuke Kawaguchi
 */
public abstract class ToolVersion {
    private ToolVersion() {}    // no instanciation please

    public static final Version VERSION = Version.create(ToolVersion.class.getResourceAsStream("version.properties"));
}
