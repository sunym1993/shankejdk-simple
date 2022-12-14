/*
 * Copyright (c) 2000, 2004, Oracle and/or its affiliates. All rights reserved.
 */

package com.sun.corba.se.impl.ior.iiop;

import org.omg.CORBA_2_3.portable.InputStream ;
import org.omg.CORBA_2_3.portable.OutputStream ;

import com.sun.corba.se.spi.ior.TaggedComponentBase ;

import com.sun.corba.se.spi.ior.iiop.CodeSetsComponent ;

import org.omg.IOP.TAG_CODE_SETS ;

import com.sun.corba.se.impl.encoding.CodeSetComponentInfo ;
import com.sun.corba.se.impl.encoding.MarshalOutputStream ;
import com.sun.corba.se.impl.encoding.MarshalInputStream ;

/**
 * @author
 */
public class CodeSetsComponentImpl extends TaggedComponentBase
    implements CodeSetsComponent
{
    CodeSetComponentInfo csci ;

    public boolean equals( Object obj )
    {
        if (!(obj instanceof CodeSetsComponentImpl))
            return false ;

        CodeSetsComponentImpl other = (CodeSetsComponentImpl)obj ;

        return csci.equals( other.csci ) ;
    }

    public int hashCode()
    {
        return csci.hashCode() ;
    }

    public String toString()
    {
        return "CodeSetsComponentImpl[csci=" + csci + "]" ;
    }

    public CodeSetsComponentImpl()
    {
        // Uses our default code sets (see CodeSetComponentInfo)
        csci = new CodeSetComponentInfo() ;
    }

    public CodeSetsComponentImpl( InputStream is )
    {
        csci = new CodeSetComponentInfo() ;
        csci.read( (MarshalInputStream)is ) ;
    }

    public CodeSetsComponentImpl(com.sun.corba.se.spi.orb.ORB orb)
    {
        if (orb == null)
            csci = new CodeSetComponentInfo();
        else
            csci = orb.getORBData().getCodeSetComponentInfo();
    }

    public CodeSetComponentInfo getCodeSetComponentInfo()
    {
        return csci ;
    }

    public void writeContents(OutputStream os)
    {
        csci.write( (MarshalOutputStream)os ) ;
    }

    public int getId()
    {
        return TAG_CODE_SETS.value ; // 1 in CORBA 2.3.1 13.6.3
    }
}
