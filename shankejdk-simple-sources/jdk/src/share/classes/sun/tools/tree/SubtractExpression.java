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
class SubtractExpression extends BinaryArithmeticExpression {
    /**
     * constructor
     */
    public SubtractExpression(long where, Expression left, Expression right) {
        super(SUB, where, left, right);
    }

    /**
     * Evaluate
     */
    Expression eval(int a, int b) {
        return new IntExpression(where, a - b);
    }
    Expression eval(long a, long b) {
        return new LongExpression(where, a - b);
    }
    Expression eval(float a, float b) {
        return new FloatExpression(where, a - b);
    }
    Expression eval(double a, double b) {
        return new DoubleExpression(where, a - b);
    }

    /**
     * Simplify
     */
    Expression simplify() {
        // Can't simplify floating point subtract because of -0.0 strangeness
        if (type.inMask(TM_INTEGER)) {
            if (left.equals(0)) {
                return new NegativeExpression(where, right);
            }
            if (right.equals(0)) {
                return left;
            }
        }
        return this;
    }

    /**
     * Code
     */
    void codeOperation(Environment env, Context ctx, Assembler asm) {
        asm.add(where, opc_isub + type.getTypeCodeOffset());
    }
}
