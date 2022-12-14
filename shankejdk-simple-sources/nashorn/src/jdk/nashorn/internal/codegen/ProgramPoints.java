/*
 * Copyright (c) 2010, 2013, Oracle and/or its affiliates. All rights reserved.
 */
package jdk.nashorn.internal.codegen;

import static jdk.nashorn.internal.runtime.UnwarrantedOptimismException.FIRST_PROGRAM_POINT;
import static jdk.nashorn.internal.runtime.linker.NashornCallSiteDescriptor.MAX_PROGRAM_POINT_VALUE;

import java.util.HashSet;
import java.util.Set;
import jdk.nashorn.internal.IntDeque;
import jdk.nashorn.internal.ir.AccessNode;
import jdk.nashorn.internal.ir.BinaryNode;
import jdk.nashorn.internal.ir.CallNode;
import jdk.nashorn.internal.ir.Expression;
import jdk.nashorn.internal.ir.FunctionNode;
import jdk.nashorn.internal.ir.IdentNode;
import jdk.nashorn.internal.ir.IndexNode;
import jdk.nashorn.internal.ir.Node;
import jdk.nashorn.internal.ir.Optimistic;
import jdk.nashorn.internal.ir.UnaryNode;
import jdk.nashorn.internal.ir.VarNode;
import jdk.nashorn.internal.ir.visitor.SimpleNodeVisitor;

/**
 * Find program points in the code that are needed for optimistic assumptions
 */
class ProgramPoints extends SimpleNodeVisitor {

    private final IntDeque nextProgramPoint = new IntDeque();
    private final Set<Node> noProgramPoint = new HashSet<>();

    private int next() {
        final int next = nextProgramPoint.getAndIncrement();
        if(next > MAX_PROGRAM_POINT_VALUE) {
            throw new AssertionError("Function has more than " + MAX_PROGRAM_POINT_VALUE + " program points");
        }
        return next;
    }

    @Override
    public boolean enterFunctionNode(final FunctionNode functionNode) {
        nextProgramPoint.push(FIRST_PROGRAM_POINT);
        return true;
    }

    @Override
    public Node leaveFunctionNode(final FunctionNode functionNode) {
        nextProgramPoint.pop();
        return functionNode;
    }

    private Expression setProgramPoint(final Optimistic optimistic) {
        if (noProgramPoint.contains(optimistic)) {
            return (Expression)optimistic;
        }
        return (Expression)(optimistic.canBeOptimistic() ? optimistic.setProgramPoint(next()) : optimistic);
    }

    @Override
    public boolean enterVarNode(final VarNode varNode) {
        noProgramPoint.add(varNode.getName());
        return true;
    }

    @Override
    public boolean enterIdentNode(final IdentNode identNode) {
        if (identNode.isInternal()) {
            noProgramPoint.add(identNode);
        }
        return true;
    }

    @Override
    public Node leaveIdentNode(final IdentNode identNode) {
        if(identNode.isPropertyName()) {
            return identNode;
        }
        return setProgramPoint(identNode);
    }

    @Override
    public Node leaveCallNode(final CallNode callNode) {
        return setProgramPoint(callNode);
    }

    @Override
    public Node leaveAccessNode(final AccessNode accessNode) {
        return setProgramPoint(accessNode);
    }

    @Override
    public Node leaveIndexNode(final IndexNode indexNode) {
        return setProgramPoint(indexNode);
    }

    @Override
    public Node leaveBinaryNode(final BinaryNode binaryNode) {
        return setProgramPoint(binaryNode);
    }

    @Override
    public Node leaveUnaryNode(final UnaryNode unaryNode) {
        return setProgramPoint(unaryNode);
    }
}
