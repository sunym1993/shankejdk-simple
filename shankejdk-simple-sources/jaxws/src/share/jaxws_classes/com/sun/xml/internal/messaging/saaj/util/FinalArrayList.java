/*
 * Copyright (c) 1997, 2012, Oracle and/or its affiliates. All rights reserved.
 */

package com.sun.xml.internal.messaging.saaj.util;

import java.util.ArrayList;
import java.util.Collection;

/**
 * {@link ArrayList} with a final marker to help JIT.
 * @author Kohsuke Kawaguchi
 */
public final class FinalArrayList extends ArrayList {
    public FinalArrayList(int initialCapacity) {
        super(initialCapacity);
    }

    public FinalArrayList() {
    }

    public FinalArrayList(Collection collection) {
        super(collection);
    }

}
