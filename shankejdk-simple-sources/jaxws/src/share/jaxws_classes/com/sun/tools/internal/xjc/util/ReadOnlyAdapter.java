/*
 * Copyright (c) 1997, 2011, Oracle and/or its affiliates. All rights reserved.
 */

package com.sun.tools.internal.xjc.util;

import javax.xml.bind.annotation.adapters.XmlAdapter;

/**
 * {@link XmlAdapter} used inside XJC is almost always unmarshal-only.
 *
 * @author Kohsuke Kawaguchi
 */
public abstract class ReadOnlyAdapter<OnTheWire,InMemory> extends XmlAdapter<OnTheWire,InMemory> {
    public final OnTheWire marshal(InMemory onTheWire) {
        // the unmarshaller uses this method
        // to get the current collection object from the property.
        // so we can't just throw UnsupportedOperationException here
        return null;
    }
}
