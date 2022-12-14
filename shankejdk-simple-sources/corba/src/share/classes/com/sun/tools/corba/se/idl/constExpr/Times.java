/*
 * Copyright (c) 1999, Oracle and/or its affiliates. All rights reserved.
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

import com.sun.tools.corba.se.idl.Util;
import java.math.BigInteger;

public class Times extends BinaryExpr
{
  protected Times (Expression leftOperand, Expression rightOperand)
  {
    super ("*", leftOperand, rightOperand);
  } // ctor

  public Object evaluate () throws EvaluationException
  {
    try
    {
      Number l = (Number)left ().evaluate ();
      Number r = (Number)right ().evaluate ();

      boolean lIsNonInteger = l instanceof Float || l instanceof Double;
      boolean rIsNonInteger = r instanceof Float || r instanceof Double;

      if (lIsNonInteger && rIsNonInteger)
        value (new Double (l.doubleValue () * r.doubleValue ()));
      else if (lIsNonInteger || rIsNonInteger)
      {
        String[] parameters = {Util.getMessage ("EvaluationException.times"), left ().value ().getClass ().getName (), right ().value ().getClass ().getName ()};
        throw new EvaluationException (Util.getMessage ("EvaluationException.1", parameters));
      }
      else
      {
        // Multiplication (*)
        BigInteger tmpL = (BigInteger)l,  tmpR = (BigInteger)r;
        value (tmpL.multiply (tmpR));
        //daz        value (new Long (l.longValue () * r.longValue ()));
      }
    }
    catch (ClassCastException e)
    {
      String[] parameters = {Util.getMessage ("EvaluationException.times"), left ().value ().getClass ().getName (), right ().value ().getClass ().getName ()};
      throw new EvaluationException (Util.getMessage ("EvaluationException.1", parameters));
    }
    return value ();
  } // evaluate
} // class Times
