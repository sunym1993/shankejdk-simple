/*
 * Copyright (c) 2010, 2013, Oracle and/or its affiliates. All rights reserved.
 */
package jdk.nashorn.internal.ir;

import jdk.nashorn.internal.codegen.types.Type;

/**
 * Is this a node that can be optimistically typed? This means that it
 * has a probable type but it's not available through static analysis
 *
 * The follow nodes are optimistic, with reasons therefore given within
 * parenthesis
 *
 * @see IndexNode  (dynamicGetIndex)
 * @see BinaryNode (local calculations to strongly typed bytecode)
 * @see UnaryNode  (local calculations to strongly typed bytecode)
 * @see CallNode   (dynamicCall)
 * @see AccessNode (dynamicGet)
 * @see IdentNode  (dynamicGet)
 */
public interface Optimistic {
    /**
     * Unique node ID that is associated with an invokedynamic call that mail
     * fail and its callsite. This is so that nodes can be regenerated less
     * pessimistically the next generation if an assumption failed
     *
     * @return unique node id
     */
    public int getProgramPoint();

    /**
     * Set the node number for this node, associating with a unique per-function
     * program point
     * @param programPoint the node number
     * @return new node, or same if unchanged
     */
    public Optimistic setProgramPoint(final int programPoint);

    /**
     * Is it possible for this particular implementor to actually have any optimism?
     * SHIFT operators for instance are binary nodes, but never optimistic. Multiply
     * operators are. We might want to refurbish the type hierarchy to fix this.
     * @return true if theoretically optimistic
     */
    public boolean canBeOptimistic();

    /**
     * Get the most optimistic type for this node. Typically we start out as
     * an int, and then at runtime we bump this up to number and then Object
     *
     * @return optimistic type to be used in code generation
     */
    public Type getMostOptimisticType();

    /**
     * Most pessimistic type that is guaranteed to be safe.  Typically this is
     * number for arithmetic operations that can overflow, or Object for an add
     *
     * @return pessimistic type guaranteed to never overflow
     */
    public Type getMostPessimisticType();

    /**
     * Set the override type
     *
     * @param type the type
     * @return a node equivalent to this one except for the requested change.
     */
    public Optimistic setType(final Type type);
}
