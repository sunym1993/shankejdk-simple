/*
 * Copyright (c) 2002, 2003, Oracle and/or its affiliates. All rights reserved.
 */

package com.sun.corba.se.spi.encoding ;

import com.sun.corba.se.impl.encoding.CDRInputStream ;
import com.sun.corba.se.pept.encoding.InputObject ;

public abstract class CorbaInputObject
    extends CDRInputStream
    implements InputObject
{
}
