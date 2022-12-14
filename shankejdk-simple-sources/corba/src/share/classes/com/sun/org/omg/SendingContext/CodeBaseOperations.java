/*
 * Copyright (c) 1999, Oracle and/or its affiliates. All rights reserved.
 */
package com.sun.org.omg.SendingContext;


/**
* com/sun/org/omg/SendingContext/CodeBaseOperations.java
* Generated by the IDL-to-Java compiler (portable), version "3.0"
* from rt.idl
* Thursday, May 6, 1999 1:52:08 AM PDT
*/

// Edited to leave RunTime in org.omg.CORBA

public interface CodeBaseOperations  extends org.omg.SendingContext.RunTimeOperations
{

    // Operation to obtain the IR from the sending context
    com.sun.org.omg.CORBA.Repository get_ir ();

    // Operations to obtain a URL to the implementation code
    String implementation (String x);
    String[] implementations (String[] x);

    // the same information
    com.sun.org.omg.CORBA.ValueDefPackage.FullValueDescription meta (String x);
    com.sun.org.omg.CORBA.ValueDefPackage.FullValueDescription[] metas (String[] x);

    // information
    String[] bases (String x);
} // interface CodeBaseOperations
