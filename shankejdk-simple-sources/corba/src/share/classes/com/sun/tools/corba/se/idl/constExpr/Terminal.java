/*
 * Copyright (c) 1999, 2001, Oracle and/or its affiliates. All rights reserved.
 */
/*
 * COMPONENT_NAME: idl.parser
 *
 * ORIGINS: 27
 *
 * Licensed Materials - Property of IBM
 * 5639-D57 (C) COPYRIGHT International Business Machines Corp. 1997, 1999
 * RMI-IIOP v1.0
 *
 */

package com.sun.tools.corba.se.idl.constExpr;

// NOTES:

import com.sun.tools.corba.se.idl.ConstEntry;
import java.math.BigInteger;

/**
 * This class contains values.  Objects of this class are the terminal
 * nodes of an expression tree.
 * <b>
 * Note that there is a constructor for Double values, but not Float.
 * CORBA defines that all floating point expressions are evaluated as
 * double, and that the result is coerced back to float if necessary.
 * <b>
 * Note also that there is a constructor for long values, but not for
 * int or short.  CORBA defines that all integral expressions are evaluated
 * as unsigned long.  A CORBA long is a Java int.  There is no unsigned int
 * in Java, so the next larger type, long, is used.
 **/
public class Terminal extends Expression
{
  protected Terminal (String representation, Character charValue,
    boolean isWide)
  {
    rep (representation);
    value (charValue);
    if (isWide)
        type( "wchar" ) ;
    else
        type( "char" ) ;
  } // ctor

  protected Terminal (String representation, Boolean booleanValue)
  {
    rep (representation);
    value (booleanValue);
  } // ctor

  // Support long long <daz>
  protected Terminal (String representation, BigInteger bigIntegerValue)
  {
    rep (representation);
    value (bigIntegerValue);
  } // ctor

  protected Terminal (String representation, Long longValue)
  {
    long lv = longValue.longValue ();
    rep (representation);
    if (lv > Integer.MAX_VALUE || lv < Integer.MIN_VALUE)
      value (longValue);
    else
      value (new Integer (longValue.intValue ()));
  } // ctor

  protected Terminal (String representation, Double doubleValue)
  {
    rep (representation);
    value (doubleValue);
  } // ctor

  protected Terminal (String stringValue, boolean isWide )
  {
    rep (stringValue);
    value (stringValue);
    if (isWide)
        type( "wstring" ) ;
    else
        type( "string" ) ;
  } // ctor

  protected Terminal (ConstEntry constReference)
  {
    rep (constReference.fullName ());
    value (constReference);
  } // ctor

  ///// INSTANCE METHODS
  public Object evaluate () throws EvaluationException
  {
    if (value () instanceof ConstEntry)
      return ((ConstEntry)value ()).value ().evaluate ();
    else
      return value ();
  } // evaluate
} // class Terminal
