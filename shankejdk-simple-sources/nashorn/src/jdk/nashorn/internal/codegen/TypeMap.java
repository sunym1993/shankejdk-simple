/*
 * Copyright (c) 2010-2014, Oracle and/or its affiliates. All rights reserved.
 */

package jdk.nashorn.internal.codegen;

import java.lang.invoke.MethodType;
import java.util.Arrays;
import java.util.NoSuchElementException;
import jdk.nashorn.internal.codegen.types.Type;
import jdk.nashorn.internal.ir.FunctionNode;
import jdk.nashorn.internal.runtime.ScriptFunction;

/**
 * A tuple containing function id, parameter types, return type and needsCallee flag.
 */
public final class TypeMap {
    private final int functionNodeId;
    private final Type[] paramTypes;
    private final Type returnType;
    private final boolean needsCallee;

    /**
     * Constructor
     * @param functionNodeId function node id
     * @param type           method type found at runtime corresponding to parameter guess
     * @param needsCallee    does the function using this type map need a callee
     */
    public TypeMap(final int functionNodeId, final MethodType type, final boolean needsCallee) {
        final Type[] types = new Type[type.parameterCount()];
        int pos = 0;
        for (final Class<?> p : type.parameterArray()) {
            types[pos++] = Type.typeFor(p);
        }

        this.functionNodeId = functionNodeId;
        this.paramTypes = types;
        this.returnType = Type.typeFor(type.returnType());
        this.needsCallee = needsCallee;
    }

    /**
     * Returns the array of parameter types for a particular function node
     * @param functionNodeId the ID of the function node
     * @return an array of parameter types
     * @throws NoSuchElementException if the type map has no mapping for the requested function
     */
    public Type[] getParameterTypes(final int functionNodeId) {
        assert this.functionNodeId == functionNodeId;
        return paramTypes.clone();
    }

    MethodType getCallSiteType(final FunctionNode functionNode) {
        assert this.functionNodeId == functionNode.getId();
        final Type[] types = paramTypes;
        MethodType mt = MethodType.methodType(returnType.getTypeClass());
        if (needsCallee) {
            mt = mt.appendParameterTypes(ScriptFunction.class);
        }

        mt = mt.appendParameterTypes(Object.class); //this

        for (final Type type : types) {
            if (type == null) {
                return null; // not all parameter information is supplied
            }
            mt = mt.appendParameterTypes(type.getTypeClass());
        }

        return mt;
    }

    /**
     * Does the function using this TypeMap need a callee argument. This is used
     * to compute correct param index offsets in {@link jdk.nashorn.internal.codegen.ApplySpecialization}
     * @return true if a callee is needed, false otherwise
     */
    public boolean needsCallee() {
        return needsCallee;
    }

    /**
     * Get the parameter type for this parameter position, or
     * null if now known
     * @param functionNode functionNode
     * @param pos position
     * @return parameter type for this callsite if known
     */
    Type get(final FunctionNode functionNode, final int pos) {
        assert this.functionNodeId == functionNode.getId();
        final Type[] types = paramTypes;
        assert types == null || pos < types.length : "fn = " + functionNode.getId() + " " + "types=" + Arrays.toString(types) + " || pos=" + pos + " >= length=" + types.length + " in " + this;
        if (types != null && pos < types.length) {
            return types[pos];
        }
        return null;
    }

    @Override
    public String toString() {
        return toString("");
    }

    String toString(final String prefix) {
        final StringBuilder sb = new StringBuilder();

        final int id = functionNodeId;
        sb.append(prefix).append('\t');
        sb.append("function ").append(id).append('\n');
        sb.append(prefix).append("\t\tparamTypes=");
        sb.append(Arrays.toString(paramTypes));
        sb.append('\n');
        sb.append(prefix).append("\t\treturnType=");
        final Type ret = returnType;
        sb.append(ret == null ? "N/A" : ret);
        sb.append('\n');

        return sb.toString();
    }
}
