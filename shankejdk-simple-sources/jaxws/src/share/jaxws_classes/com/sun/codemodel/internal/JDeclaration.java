/*
 * Copyright (c) 1997, 2010, Oracle and/or its affiliates. All rights reserved.
 */

package com.sun.codemodel.internal;


/**
 * Common interface for code components that can generate declarations
 * of themselves.
 */

public interface JDeclaration {

    public void declare(JFormatter f);

}
