/*
 * Copyright (c) 1997, 2011, Oracle and/or its affiliates. All rights reserved.
 */

package com.sun.tools.internal.xjc.reader.xmlschema.bindinfo;

import javax.xml.bind.annotation.XmlEnumValue;

/**
 * @author Kohsuke Kawaguchi
 */
public enum LocalScoping {
    @XmlEnumValue("nested")
    NESTED,
    @XmlEnumValue("toplevel")
    TOPLEVEL
}
