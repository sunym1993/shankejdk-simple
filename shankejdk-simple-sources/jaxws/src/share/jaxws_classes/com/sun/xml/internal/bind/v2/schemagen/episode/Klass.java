/*
 * Copyright (c) 1997, 2011, Oracle and/or its affiliates. All rights reserved.
 */

package com.sun.xml.internal.bind.v2.schemagen.episode;

import com.sun.xml.internal.txw2.TypedXmlWriter;
import com.sun.xml.internal.txw2.annotation.XmlAttribute;

/**
 * @author Kohsuke Kawaguchi
 */
public interface Klass extends TypedXmlWriter {
    /**
     * FQCN.
     */
    @XmlAttribute
    void ref(String className);
}
