/*
 * Copyright (c) 1997, 2012, Oracle and/or its affiliates. All rights reserved.
 */

package com.sun.tools.internal.ws.wsdl.document.http;

import com.sun.tools.internal.ws.wsdl.framework.ExtensionImpl;
import org.xml.sax.Locator;

import javax.xml.namespace.QName;

/**
 * A HTTP urlReplacement extension.
 *
 * @author WS Development Team
 */
public class HTTPUrlReplacement extends ExtensionImpl {

    public HTTPUrlReplacement(Locator locator) {
        super(locator);
    }

    public QName getElementName() {
        return HTTPConstants.QNAME_URL_REPLACEMENT;
    }

    public void validateThis() {
    }
}
