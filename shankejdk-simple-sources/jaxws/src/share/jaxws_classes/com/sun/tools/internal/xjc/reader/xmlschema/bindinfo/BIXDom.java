/*
 * Copyright (c) 1997, 2011, Oracle and/or its affiliates. All rights reserved.
 */

package com.sun.tools.internal.xjc.reader.xmlschema.bindinfo;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

import com.sun.tools.internal.xjc.reader.Const;

/**
 * Compatibility with 1.0.
 *
 * Read &lt;xjc:dom> as &lt;jaxb:dom>.
 *
 * @author Kohsuke Kawaguchi
 */
@XmlRootElement(name="dom",namespace=Const.XJC_EXTENSION_URI)
public class BIXDom extends BIDom {

    // unsupported yet
    @XmlAttribute
    String type = "w3c";
}
