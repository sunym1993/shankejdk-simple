/*
 * Copyright (c) 2004, 2011, Oracle and/or its affiliates. All rights reserved.
 *
 * THIS FILE WAS MODIFIED BY SUN MICROSYSTEMS, INC.
 */

package com.sun.xml.internal.org.jvnet.fastinfoset;

public class EncodingAlgorithmException extends FastInfosetException {

    public EncodingAlgorithmException(String message) {
        super(message);
    }

    public EncodingAlgorithmException(String message, Exception e) {
        super(message, e);
    }

    public EncodingAlgorithmException(Exception e) {
        super(e);
    }
}
