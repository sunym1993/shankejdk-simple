/*
 * Copyright (c) 2002, 2003, Oracle and/or its affiliates. All rights reserved.
 */

package com.sun.corba.se.spi.orbutil.fsm ;

import com.sun.corba.se.impl.orbutil.fsm.NameBase ;

public abstract class GuardBase extends NameBase implements Guard {
    public GuardBase( String name ) { super( name ) ; }
}
