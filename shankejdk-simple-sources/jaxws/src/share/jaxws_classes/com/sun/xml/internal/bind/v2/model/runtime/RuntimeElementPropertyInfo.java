/*
 * Copyright (c) 1997, 2011, Oracle and/or its affiliates. All rights reserved.
 */

package com.sun.xml.internal.bind.v2.model.runtime;

import java.lang.reflect.Type;
import java.util.Collection;
import java.util.List;

import com.sun.xml.internal.bind.v2.model.core.ElementPropertyInfo;

/**
 * @author Kohsuke Kawaguchi
 */
public interface RuntimeElementPropertyInfo extends ElementPropertyInfo<Type,Class>, RuntimePropertyInfo {
    /** {@inheritDoc} */
    Collection<? extends RuntimeTypeInfo> ref();

    List<? extends RuntimeTypeRef> getTypes();
}
