/*
 * Copyright (c) 1997, 2011, Oracle and/or its affiliates. All rights reserved.
 */

package com.sun.tools.internal.xjc.reader.xmlschema.bindinfo;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;

/**
 * Enum member name handling mode.
 *
 * @author Kohsuke Kawaguchi
 */
@XmlEnum
public enum EnumMemberMode {
    @XmlEnumValue("skipGeneration")
    SKIP,
    @XmlEnumValue("generateError")
    ERROR,
    @XmlEnumValue("generateName")
    GENERATE

    ;

    /**
     * The mode will change to this when there's &lt;jaxb:enum> customization.
     */
    public EnumMemberMode getModeWithEnum() {
        if(this==SKIP)  return ERROR;
        return this;
    }
}
