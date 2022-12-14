/*
 * Copyright (c) 2012, 2013, Oracle and/or its affiliates. All rights reserved.
 */

package com.sun.tools.sjavac;

/**
 * Used to signal serious problems when running sjavac.
 *
 * <p><b>This is NOT part of any supported API.
 * If you write code that depends on this, you do so at your own
 * risk.  This code and its internal interfaces are subject to change
 * or deletion without notice.</b></p>
 */
public class ProblemException extends Exception {
    static final long serialVersionUID = -3387516993124229949L;
    public ProblemException(String s) {
        super(s);
    }
}
