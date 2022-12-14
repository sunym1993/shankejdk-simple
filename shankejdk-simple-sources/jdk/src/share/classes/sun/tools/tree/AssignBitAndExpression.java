/*
 * Copyright (c) 1994, 2003, Oracle and/or its affiliates. All rights reserved.
 */

package sun.tools.tree;

import sun.tools.java.*;
import sun.tools.asm.Assembler;

/**
 * WARNING: The contents of this source file are not part of any
 * supported API.  Code that depends on them does so at its own risk:
 * they are subject to change or removal without notice.
 */
public
class AssignBitAndExpression extends AssignOpExpression {
    /**
     * Constructor
     */
    public AssignBitAndExpression(long where, Expression left, Expression right) {
        super(ASGBITAND, where, left, right);
    }


    /**
     * Code
     */
    void codeOperation(Environment env, Context ctx, Assembler asm) {
        asm.add(where, opc_iand + itype.getTypeCodeOffset());
    }
}
