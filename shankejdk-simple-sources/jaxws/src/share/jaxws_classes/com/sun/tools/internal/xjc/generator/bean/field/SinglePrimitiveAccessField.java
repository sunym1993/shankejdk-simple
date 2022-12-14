/*
 * Copyright (c) 1997, 2011, Oracle and/or its affiliates. All rights reserved.
 */

package com.sun.tools.internal.xjc.generator.bean.field;

import com.sun.tools.internal.xjc.generator.bean.ClassOutlineImpl;
import com.sun.tools.internal.xjc.model.CPropertyInfo;

/**
 * {@link SingleField} that forces the primitive accessor type.
 *
 * @author Kohsuke Kawaguchi
 */
public class SinglePrimitiveAccessField extends SingleField {
    protected SinglePrimitiveAccessField(ClassOutlineImpl context, CPropertyInfo prop) {
        super(context, prop,true);
    }
}
