/*
 * Copyright (c) 1997, 2010, Oracle and/or its affiliates. All rights reserved.
 */

package com.sun.codemodel.internal;

/**
 * String literal.
 *
 * @author
 *     Kohsuke Kawaguchi (kohsuke.kawaguchi@sun.com)
 */
public class JStringLiteral extends JExpressionImpl {

    public final String str;


    JStringLiteral(String what) {
        this.str = what;

    }


    public void generate(JFormatter f) {
        f.p(JExpr.quotify('"', str));
    }
}
