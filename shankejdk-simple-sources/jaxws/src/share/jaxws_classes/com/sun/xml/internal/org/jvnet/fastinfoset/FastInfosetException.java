/*
 * Copyright (c) 2004, 2011, Oracle and/or its affiliates. All rights reserved.
 *
 * THIS FILE WAS MODIFIED BY SUN MICROSYSTEMS, INC.
 */

package com.sun.xml.internal.org.jvnet.fastinfoset;

public class FastInfosetException extends Exception {

    public FastInfosetException(String message) {
        super(message);
    }

    public FastInfosetException(String message, Exception e) {
        super(message, e);
    }

    public FastInfosetException(Exception e) {
        super(e);
    }

}
