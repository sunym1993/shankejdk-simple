/*
 * Copyright (c) 1997, 2010, Oracle and/or its affiliates. All rights reserved.
 */

package com.sun.xml.internal.xsom;

/**
 * Union simple type.
 *
 * @author
 *  Kohsuke Kawaguchi (kohsuke.kawaguchi@sun.com)
 */
public interface XSUnionSimpleType extends XSSimpleType, Iterable<XSSimpleType>
{
    XSSimpleType getMember(int idx);
    int getMemberSize();
}
