/*
 * Copyright (c) 1994, 2003, Oracle and/or its affiliates. All rights reserved.
 */

package sun.tools.java;

/**
 * This exception is thrown when a class definition is needed
 * and the class can't be found.
 *
 * WARNING: The contents of this source file are not part of any
 * supported API.  Code that depends on them does so at its own risk:
 * they are subject to change or removal without notice.
 */
public
class ClassNotFound extends Exception {
    /**
     * The class that was not found
     */
    public Identifier name;

    /**
     * Create a ClassNotFound exception
     */
    public ClassNotFound(Identifier nm) {
        super(nm.toString());
        name = nm;
    }
}
