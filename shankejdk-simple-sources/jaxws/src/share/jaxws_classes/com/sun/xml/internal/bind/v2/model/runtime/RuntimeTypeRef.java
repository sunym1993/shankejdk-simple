/*
 * Copyright (c) 1997, 2011, Oracle and/or its affiliates. All rights reserved.
 */

package com.sun.xml.internal.bind.v2.model.runtime;

import java.lang.reflect.Type;

import com.sun.xml.internal.bind.v2.model.core.TypeRef;

/**
 * @author Kohsuke Kawaguchi
 */
public interface RuntimeTypeRef extends TypeRef<Type,Class>, RuntimeNonElementRef {
    RuntimeNonElement getTarget();
    RuntimePropertyInfo getSource();
}
