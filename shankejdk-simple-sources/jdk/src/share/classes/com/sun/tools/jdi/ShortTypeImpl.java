/*
 * Copyright (c) 1998, 1999, Oracle and/or its affiliates. All rights reserved.
 */

package com.sun.tools.jdi;

import com.sun.jdi.*;

public class ShortTypeImpl extends PrimitiveTypeImpl implements ShortType {
    ShortTypeImpl(VirtualMachine vm) {
        super(vm);
    }


    public String signature() {
        return String.valueOf((char)JDWP.Tag.SHORT);
    }

    PrimitiveValue convert(PrimitiveValue value) throws InvalidTypeException {
        return vm.mirrorOf(((PrimitiveValueImpl)value).checkedShortValue());
    }

}
