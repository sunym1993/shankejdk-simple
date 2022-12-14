/*
 * Copyright (c) 1997, 2010, Oracle and/or its affiliates. All rights reserved.
 */

package com.sun.xml.internal.xsom;

/**
 * Attribute declaration.
 *
 * @author
 *  Kohsuke Kawaguchi (kohsuke.kawaguchi@sun.com)
 */
public interface XSAttributeDecl extends XSDeclaration
{
    XSSimpleType getType();

    XmlString getDefaultValue();
    XmlString getFixedValue();
}
