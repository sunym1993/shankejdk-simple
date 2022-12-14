/*
 * Copyright (c) 1997, 2008, Oracle and/or its affiliates. All rights reserved.
 */


/*
 * The Original Code is HAT. The Initial Developer of the
 * Original Code is Bill Foote, with contributions from others
 * at JavaSoft/Sun.
 */

package com.sun.tools.hat.internal.model;

/**
 * Represents a short (i.e. a short field in an instance).
 *
 * @author      Bill Foote
 */


public class JavaShort extends JavaValue {

    short value;

    public JavaShort(short value) {
        this.value = value;
    }

    public String toString() {
        return "" + value;
    }

}
