/*
 * Copyright (c) 2010, 2013, Oracle and/or its affiliates. All rights reserved.
 */

package jdk.nashorn.internal.ir;

/**
 * Any node that can be a property key inherits this
 */
public interface PropertyKey {

    /**
     * Get the property name
     *
     * @return the property name
     */
    public abstract String getPropertyName();
}
