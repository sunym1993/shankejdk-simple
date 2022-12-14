/*
 * Copyright (c) 2001, Oracle and/or its affiliates. All rights reserved.
 */

package com.sun.tools.jdi;

import com.sun.jdi.*;

interface LineInfo {

    String liStratum();

    int liLineNumber();

    String liSourceName() throws AbsentInformationException;

    String liSourcePath() throws AbsentInformationException;
}
