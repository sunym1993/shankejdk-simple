/*
 * Copyright (c) 2002, 2003, Oracle and/or its affiliates. All rights reserved.
 */

package com.sun.corba.se.spi.protocol;

import com.sun.corba.se.spi.ior.IOR ;

public interface LocalClientRequestDispatcherFactory {
    public LocalClientRequestDispatcher create( int id, IOR ior )  ;
}
