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
class BitNotExpression extends UnaryExpression {
    /**
     * Constructor
     */
    public BitNotExpression(long where, Expression right) {
        super(BITNOT, where, right.type, right);
    }

    /**
     * Select the type of the expression
     */
    void selectType(Environment env, Context ctx, int tm) {
        if ((tm & TM_LONG) != 0) {
            type = Type.tLong;
        } else {
            type = Type.tInt;
        }
        right = convert(env, ctx, type, right);
    }

    /**
     * Evaluate
     */
    Expression eval(int a) {
        return new IntExpression(where, ~a);
    }
    Expression eval(long a) {
        return new LongExpression(where, ~a);
    }

    /**
     * Simplify
     */
    Expression simplify() {
        if (right.op == BITNOT) {
            return ((BitNotExpression)right).right;
        }
        return this;
    }

    /**
     * Code
     */
    public void codeValue(Environment env, Context ctx, Assembler asm) {
        right.codeValue(env, ctx, asm);
        if (type.isType(TC_INT)) {
            asm.add(where, opc_ldc, new Integer(-1));
            asm.add(where, opc_ixor);
        } else {
            asm.add(where, opc_ldc2_w, new Long(-1));
            asm.add(where, opc_lxor);
        }
    }
}
