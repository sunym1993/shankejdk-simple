/*
 * Copyright (c) 1997, 2012, Oracle and/or its affiliates. All rights reserved.
 */

package com.sun.xml.internal.org.jvnet.mimepull;

import java.util.concurrent.Executor;

public abstract class CleanUpExecutorFactory {
    private static final String DEFAULT_PROPERTY_NAME = CleanUpExecutorFactory.class
            .getName();

    protected CleanUpExecutorFactory() {
    }

    public static CleanUpExecutorFactory newInstance() {
        try {
            return (CleanUpExecutorFactory) FactoryFinder.find(DEFAULT_PROPERTY_NAME);
        } catch (Exception e) {
            return null;
        }
    }

    public abstract Executor getExecutor();
}
