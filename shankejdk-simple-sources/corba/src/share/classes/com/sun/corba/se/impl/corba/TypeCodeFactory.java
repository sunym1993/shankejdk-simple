/*
 * Copyright (c) 1999, 2003, Oracle and/or its affiliates. All rights reserved.
 */

package com.sun.corba.se.impl.corba;

public interface TypeCodeFactory {
    void setTypeCode(String id, TypeCodeImpl code);

    TypeCodeImpl getTypeCode(String id);

    void setTypeCodeForClass( Class c, TypeCodeImpl tcimpl ) ;

    TypeCodeImpl getTypeCodeForClass( Class c ) ;
}
