/*
 * Copyright (c) 2000, 2011, Oracle and/or its affiliates. All rights reserved.
 */

package sun.nio.ch;

import sun.misc.Cleaner;


public interface DirectBuffer {

    public long address();

    public Object attachment();

    public Cleaner cleaner();

}
