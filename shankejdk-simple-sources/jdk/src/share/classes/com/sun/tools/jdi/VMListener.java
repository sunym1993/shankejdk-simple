/*
 * Copyright (c) 1999, 2000, Oracle and/or its affiliates. All rights reserved.
 */

package com.sun.tools.jdi;

import com.sun.jdi.*;
import java.util.EventListener;

interface VMListener extends EventListener {
    boolean vmSuspended(VMAction action);
    boolean vmNotSuspended(VMAction action);
}
