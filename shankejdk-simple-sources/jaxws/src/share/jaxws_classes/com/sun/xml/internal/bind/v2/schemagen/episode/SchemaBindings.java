/*
 * Copyright (c) 1997, 2011, Oracle and/or its affiliates. All rights reserved.
 */

package com.sun.xml.internal.bind.v2.schemagen.episode;

import com.sun.xml.internal.txw2.TypedXmlWriter;
import com.sun.xml.internal.txw2.annotation.XmlAttribute;
import com.sun.xml.internal.txw2.annotation.XmlElement;

/**
 * @author Kohsuke Kawaguchi
 */
public interface SchemaBindings extends TypedXmlWriter {
        @XmlAttribute
        void map(boolean value);

        @XmlElement("package")
        Package _package();
}
