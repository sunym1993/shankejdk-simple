/*
 * Copyright (c) 1997, 2010, Oracle and/or its affiliates. All rights reserved.
 */

package com.sun.xml.internal.xsom.visitor;

import com.sun.xml.internal.xsom.XSContentType;
import com.sun.xml.internal.xsom.XSParticle;
import com.sun.xml.internal.xsom.XSSimpleType;

/**
 * Visitor that works on {@link XSContentType}.
 *
 * @author
 *     Kohsuke Kawaguchi (kohsuke.kawaguchi@sun.com)
 */
public interface XSContentTypeVisitor {
    void simpleType( XSSimpleType simpleType );
    void particle( XSParticle particle );
    void empty( XSContentType empty );
}
