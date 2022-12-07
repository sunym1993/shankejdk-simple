/*
 * Copyright (c) 1997, 2007, Oracle and/or its affiliates. All rights reserved.
 */


/* Generated By:JJTree: Do not edit this line. JDMTrapItem.java */

package com.sun.jmx.snmp.IPAcl;

class JDMTrapItem extends SimpleNode {
  protected JDMTrapCommunity comm = null;

  JDMTrapItem(int id) {
    super(id);
  }

  JDMTrapItem(Parser p, int id) {
    super(p, id);
  }

  public static Node jjtCreate(int id) {
      return new JDMTrapItem(id);
  }

  public static Node jjtCreate(Parser p, int id) {
      return new JDMTrapItem(p, id);
  }

  public JDMTrapCommunity getCommunity(){
        return comm;
  }
}