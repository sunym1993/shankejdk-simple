/*
 * Copyright (c) 1997, 2011, Oracle and/or its affiliates. All rights reserved.
 */

package com.sun.tools.internal.xjc.generator.util;

import com.sun.codemodel.internal.JBlock;

/**
 *
 * @author
 *     Kohsuke Kawaguchi (kohsuke.kawaguchi@sun.com)
 */
public class ExistingBlockReference implements BlockReference {
    private final JBlock block;

    public ExistingBlockReference( JBlock _block ) {
        this.block = _block;
    }

    public JBlock get(boolean create) {
        return block;
    }

}
