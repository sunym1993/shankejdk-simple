/*
 * Copyright (c) 1997, 2011, Oracle and/or its affiliates. All rights reserved.
 */

package com.sun.xml.internal.bind.v2.model.runtime;

import java.lang.reflect.Type;

import com.sun.xml.internal.bind.v2.model.core.AttributePropertyInfo;

/**
 * @author Kohsuke Kawaguchi
 */
public interface RuntimeAttributePropertyInfo extends AttributePropertyInfo<Type,Class>, RuntimePropertyInfo, RuntimeNonElementRef {
    // refinement
    RuntimeNonElement getTarget();
}
