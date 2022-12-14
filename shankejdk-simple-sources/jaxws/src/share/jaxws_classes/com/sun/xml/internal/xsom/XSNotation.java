/*
 * Copyright (c) 1997, 2010, Oracle and/or its affiliates. All rights reserved.
 */

package com.sun.xml.internal.xsom;

/**
 * Notation declaration.
 *
 * @author
 *     Kohsuke Kawaguchi (kohsuke.kawaguchi@sun.com)
 */
public interface XSNotation extends XSDeclaration {
    String getPublicId();
    String getSystemId();
}
