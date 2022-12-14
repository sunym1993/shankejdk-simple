/*
 * Copyright (c) 2003, Oracle and/or its affiliates. All rights reserved.
 */
package com.sun.corba.se.spi.copyobject ;

public class ReflectiveCopyException extends Exception {
    public ReflectiveCopyException()
    {
        super() ;
    }

    public ReflectiveCopyException( String msg )
    {
        super( msg ) ;
    }

    public ReflectiveCopyException( String msg, Throwable t )
    {
        super( msg, t ) ;
    }
}
