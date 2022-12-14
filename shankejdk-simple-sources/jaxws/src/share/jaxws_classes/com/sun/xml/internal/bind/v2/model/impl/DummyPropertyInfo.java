/*
 * Copyright (c) 1997, 2011, Oracle and/or its affiliates. All rights reserved.
 */

package com.sun.xml.internal.bind.v2.model.impl;

/**
 * {@link PropertyInfo} that allows to add additional elements to the collection.
 *
 * @author Martin Grebac
 */
public interface DummyPropertyInfo<T, C, F, M> {
    void addType(PropertyInfoImpl<T, C, F, M> info);
}
