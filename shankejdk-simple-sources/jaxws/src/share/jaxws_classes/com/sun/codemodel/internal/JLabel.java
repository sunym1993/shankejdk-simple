/*
 * Copyright (c) 1997, 2010, Oracle and/or its affiliates. All rights reserved.
 */

package com.sun.codemodel.internal;

/**
 * Label that can be used for continue and break.
 *
 * @author
 *     Kohsuke Kawaguchi (kohsuke.kawaguchi@sun.com)
 */
public class JLabel implements JStatement {

    final String label;

    /**
     * JBreak constructor
     *
     * @param   _label
     *      break label or null.
     */
    JLabel( String _label ) {
        this.label = _label;
    }

    public void state(JFormatter f) {
        f.p(label+':').nl();
    }

}
