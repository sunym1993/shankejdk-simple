/*
 * Copyright (c) 2010, 2013, Oracle and/or its affiliates. All rights reserved.
 */

package jdk.nashorn.internal.runtime;

import static jdk.nashorn.internal.codegen.Compiler.SCRIPTS_PACKAGE;
import static jdk.nashorn.internal.codegen.Compiler.binaryName;
import static jdk.nashorn.internal.codegen.CompilerConstants.JS_OBJECT_DUAL_FIELD_PREFIX;
import static jdk.nashorn.internal.codegen.CompilerConstants.JS_OBJECT_SINGLE_FIELD_PREFIX;

import java.security.ProtectionDomain;
import jdk.nashorn.internal.codegen.ObjectClassGenerator;

/**
 * Responsible for on the fly construction of structure classes.
 */
final class StructureLoader extends NashornLoader {
    private static final String SINGLE_FIELD_PREFIX = binaryName(SCRIPTS_PACKAGE) + '.' + JS_OBJECT_SINGLE_FIELD_PREFIX.symbolName();
    private static final String DUAL_FIELD_PREFIX   = binaryName(SCRIPTS_PACKAGE) + '.' + JS_OBJECT_DUAL_FIELD_PREFIX.symbolName();

    /**
     * Constructor.
     */
    StructureLoader(final ClassLoader parent) {
        super(parent);
    }

    /**
     * Returns true if the class name represents a structure object with dual primitive/object fields.
     * @param name a class name
     * @return true if a dual field structure class
     */
    private static boolean isDualFieldStructure(final String name) {
        return name.startsWith(DUAL_FIELD_PREFIX);
    }

    /**
     * Returns true if the class name represents a structure object with single object-only fields.
     * @param name a class name
     * @return true if a single field structure class
     */
    static boolean isSingleFieldStructure(final String name) {
        return name.startsWith(SINGLE_FIELD_PREFIX);
    }

    /**
     * Returns true if the class name represents a Nashorn structure object.
     * @param name a class name
     * @return true if a structure class
     */
    static boolean isStructureClass(final String name) {
        return isDualFieldStructure(name) || isSingleFieldStructure(name);
    }

    @Override
    protected Class<?> findClass(final String name) throws ClassNotFoundException {
        if (isDualFieldStructure(name)) {
            return generateClass(name, name.substring(DUAL_FIELD_PREFIX.length()), true);
        } else if (isSingleFieldStructure(name)) {
            return generateClass(name, name.substring(SINGLE_FIELD_PREFIX.length()), false);
        }
        return super.findClass(name);
    }

    /**
     * Generate a layout class.
     * @param name       Name of class.
     * @param descriptor Layout descriptor.
     * @return Generated class.
     */
    private Class<?> generateClass(final String name, final String descriptor, final boolean dualFields) {
        final Context context = Context.getContextTrusted();

        final byte[] code = new ObjectClassGenerator(context, dualFields).generate(descriptor);
        return defineClass(name, code, 0, code.length, new ProtectionDomain(null, getPermissions(null)));
    }
}
