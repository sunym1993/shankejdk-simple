/*
 * Copyright (c) 1997, 2010, Oracle and/or its affiliates. All rights reserved.
 */

package com.sun.codemodel.internal;

/**
 * Declarations that can have type variables.
 *
 * Something that can be made into a generic.
 *
 * @author
 *     Kohsuke Kawaguchi (kohsuke.kawaguchi@sun.com)
 */
public interface JGenerifiable {
    /**
     * Adds a new type variable to this declaration.
     */
    JTypeVar generify( String name );

    /**
     * Adds a new type variable to this declaration with a bound.
     */
    JTypeVar generify( String name, Class<?> bound );

    /**
     * Adds a new type variable to this declaration with a bound.
     */
    JTypeVar generify( String name, JClass bound );

    /**
     * Iterates all the type parameters of this class/interface.
     */
    JTypeVar[] typeParams();
}
