/*
 * Copyright (c) 2010, 2013, Oracle and/or its affiliates. All rights reserved.
 */

package jdk.nashorn.internal.ir;

import jdk.nashorn.internal.codegen.types.Type;
import jdk.nashorn.internal.ir.annotations.Immutable;
import jdk.nashorn.internal.ir.visitor.NodeVisitor;
/**
 * IR representation of an indexed access (brackets operator.)
 */
@Immutable
public final class IndexNode extends BaseNode {
    private static final long serialVersionUID = 1L;

    /** Property index. */
    private final Expression index;

    /**
     * Constructors
     *
     * @param token   token
     * @param finish  finish
     * @param base    base node for access
     * @param index   index for access
     */
    public IndexNode(final long token, final int finish, final Expression base, final Expression index) {
        super(token, finish, base, false);
        this.index = index;
    }

    private IndexNode(final IndexNode indexNode, final Expression base, final Expression index, final boolean isFunction, final Type type, final int programPoint) {
        super(indexNode, base, isFunction, type, programPoint);
        this.index = index;
    }

    @Override
    public Node accept(final NodeVisitor<? extends LexicalContext> visitor) {
        if (visitor.enterIndexNode(this)) {
            return visitor.leaveIndexNode(
                setBase((Expression)base.accept(visitor)).
                setIndex((Expression)index.accept(visitor)));
        }
        return this;
    }

    @Override
    public void toString(final StringBuilder sb, final boolean printType) {
        final boolean needsParen = tokenType().needsParens(base.tokenType(), true);

        if (needsParen) {
            sb.append('(');
        }

        if (printType) {
            optimisticTypeToString(sb);
        }

        base.toString(sb, printType);

        if (needsParen) {
            sb.append(')');
        }

        sb.append('[');
        index.toString(sb, printType);
        sb.append(']');
    }

    /**
     * Get the index expression for this IndexNode
     * @return the index
     */
    public Expression getIndex() {
        return index;
    }

    private IndexNode setBase(final Expression base) {
        if (this.base == base) {
            return this;
        }
        return new IndexNode(this, base, index, isFunction(), type, programPoint);
    }

    /**
     * Set the index expression for this node
     * @param index new index expression
     * @return a node equivalent to this one except for the requested change.
     */
    public IndexNode setIndex(final Expression index) {
        if(this.index == index) {
            return this;
        }
        return new IndexNode(this, base, index, isFunction(), type, programPoint);
    }

    @Override
    public IndexNode setType(final Type type) {
        if (this.type == type) {
            return this;
        }
        return new IndexNode(this, base, index, isFunction(), type, programPoint);
    }

    @Override
    public IndexNode setIsFunction() {
        if (isFunction()) {
            return this;
        }
        return new IndexNode(this, base, index, true, type, programPoint);
    }

    @Override
    public IndexNode setProgramPoint(final int programPoint) {
        if (this.programPoint == programPoint) {
            return this;
        }
        return new IndexNode(this, base, index, isFunction(), type, programPoint);
    }
}
