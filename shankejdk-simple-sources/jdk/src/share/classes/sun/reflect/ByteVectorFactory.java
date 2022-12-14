/*
 * Copyright (c) 2001, Oracle and/or its affiliates. All rights reserved.
 */

package sun.reflect;

class ByteVectorFactory {
    static ByteVector create() {
        return new ByteVectorImpl();
    }

    static ByteVector create(int sz) {
        return new ByteVectorImpl(sz);
    }
}
