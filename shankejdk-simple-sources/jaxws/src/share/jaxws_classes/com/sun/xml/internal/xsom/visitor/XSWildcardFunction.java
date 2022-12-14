/*
 * Copyright (c) 1997, 2010, Oracle and/or its affiliates. All rights reserved.
 */

package com.sun.xml.internal.xsom.visitor;

import com.sun.xml.internal.xsom.XSWildcard;

/**
 * Visits three kinds of {@link XSWildcard}.
 *
 * @author
 *     Kohsuke Kawaguchi (kohsuke.kawaguchi@sun.com)
 */
public interface XSWildcardFunction<T> {
    T any( XSWildcard.Any wc );
    T other( XSWildcard.Other wc );
    T union( XSWildcard.Union wc );
}
