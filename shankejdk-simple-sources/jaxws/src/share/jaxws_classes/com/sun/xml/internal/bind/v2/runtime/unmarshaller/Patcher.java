/*
 * Copyright (c) 1997, 2011, Oracle and/or its affiliates. All rights reserved.
 */

package com.sun.xml.internal.bind.v2.runtime.unmarshaller;

import org.xml.sax.SAXException;

/**
 * @author Kohsuke Kawaguchi
 */
public interface Patcher {
    void run() throws SAXException;
}
