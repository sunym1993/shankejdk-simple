/*
 * Copyright (c) 1997, 2011, Oracle and/or its affiliates. All rights reserved.
 */

package com.sun.tools.internal.xjc.model.nav;

import com.sun.codemodel.internal.JClass;
import com.sun.tools.internal.xjc.outline.Aspect;
import com.sun.tools.internal.xjc.outline.Outline;

/**
 * @author Kohsuke Kawaguchi
 */
public interface NClass extends NType {
    JClass toType(Outline o, Aspect aspect);

    boolean isAbstract();
}
