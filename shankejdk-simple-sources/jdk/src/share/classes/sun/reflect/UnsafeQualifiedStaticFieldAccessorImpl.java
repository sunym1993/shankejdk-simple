/*
 * Copyright (c) 2004, Oracle and/or its affiliates. All rights reserved.
 */

package sun.reflect;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.security.AccessController;
import sun.misc.Unsafe;

/** Base class for sun.misc.Unsafe-based FieldAccessors for final or
    volatile static fields.  */

abstract class UnsafeQualifiedStaticFieldAccessorImpl
    extends UnsafeStaticFieldAccessorImpl
{
    protected final boolean isReadOnly;

    UnsafeQualifiedStaticFieldAccessorImpl(Field field, boolean isReadOnly) {
        super(field);
        this.isReadOnly = isReadOnly;
    }
}
