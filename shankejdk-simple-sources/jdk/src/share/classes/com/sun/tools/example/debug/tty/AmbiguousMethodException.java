/*
 * Copyright (c) 1998, 2011, Oracle and/or its affiliates. All rights reserved.
 */

/*
 * This source code is provided to illustrate the usage of a given feature
 * or technique and has been deliberately simplified. Additional steps
 * required for a production-quality application, such as security checks,
 * input validation and proper error handling, might not be present in
 * this sample code.
 */


package com.sun.tools.example.debug.tty;

public class AmbiguousMethodException extends Exception
{
    private static final long serialVersionUID = -5372629264936918654L;

    public AmbiguousMethodException()
    {
        super();
    }

    public AmbiguousMethodException(String s)
    {
        super(s);
    }
}