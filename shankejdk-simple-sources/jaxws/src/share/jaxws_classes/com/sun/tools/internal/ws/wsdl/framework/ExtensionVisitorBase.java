/*
 * Copyright (c) 1997, 2012, Oracle and/or its affiliates. All rights reserved.
 */

package com.sun.tools.internal.ws.wsdl.framework;

import com.sun.tools.internal.ws.api.wsdl.TWSDLExtension;

/**
 * A base class for extension visitors.
 *
 * @author WS Development Team
 */
public class ExtensionVisitorBase implements ExtensionVisitor {
    public ExtensionVisitorBase() {
    }

    public void preVisit(TWSDLExtension extension) throws Exception {
    }
    public void postVisit(TWSDLExtension extension) throws Exception {
    }
}
