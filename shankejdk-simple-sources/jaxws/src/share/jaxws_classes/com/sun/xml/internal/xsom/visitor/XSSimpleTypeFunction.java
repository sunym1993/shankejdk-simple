/*
 * Copyright (c) 1997, 2010, Oracle and/or its affiliates. All rights reserved.
 */

package com.sun.xml.internal.xsom.visitor;

import com.sun.xml.internal.xsom.XSListSimpleType;
import com.sun.xml.internal.xsom.XSRestrictionSimpleType;
import com.sun.xml.internal.xsom.XSUnionSimpleType;

/**
 * Function object that works on {@link com.sun.xml.internal.xsom.XSSimpleType}
 * and its derived interfaces.
 *
 * @author
 *     Kohsuke Kawaguchi (kohsuke,kawaguchi@sun.com)
 */
public interface XSSimpleTypeFunction<T> {
    T listSimpleType( XSListSimpleType type );
    T unionSimpleType( XSUnionSimpleType type );
    T restrictionSimpleType( XSRestrictionSimpleType type );
}
