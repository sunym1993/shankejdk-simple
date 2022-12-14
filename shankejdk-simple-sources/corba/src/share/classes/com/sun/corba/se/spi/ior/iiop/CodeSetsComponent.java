/*
 * Copyright (c) 2004, Oracle and/or its affiliates. All rights reserved.
 */

package com.sun.corba.se.spi.ior.iiop;

import com.sun.corba.se.spi.ior.TaggedComponent ;

import com.sun.corba.se.impl.encoding.CodeSetComponentInfo ;

/**
 * @author Ken Cavanaugh
 */
public interface CodeSetsComponent extends TaggedComponent
{
    public CodeSetComponentInfo getCodeSetComponentInfo() ;
}
