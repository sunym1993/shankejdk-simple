/*
 * Copyright (c) 1997, 2010, Oracle and/or its affiliates. All rights reserved.
 */

package com.sun.codemodel.internal;


/**
 * JThrow statement
 */

class JThrow implements JStatement {

    /**
     * JExpression to throw
     */
    private JExpression expr;

    /**
     * JThrow constructor
     *
     * @param expr
     *        JExpression which evaluates to JThrow value
     */
    JThrow(JExpression expr) {
       this.expr = expr;
    }

    public void state(JFormatter f) {
        f.p("throw");
        f.g(expr);
        f.p(';').nl();
    }

}
