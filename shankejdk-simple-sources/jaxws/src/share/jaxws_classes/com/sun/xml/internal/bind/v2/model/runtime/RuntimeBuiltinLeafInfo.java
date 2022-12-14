/*
 * Copyright (c) 1997, 2011, Oracle and/or its affiliates. All rights reserved.
 */

package com.sun.xml.internal.bind.v2.model.runtime;

import java.lang.reflect.Type;

import com.sun.xml.internal.bind.v2.model.core.BuiltinLeafInfo;

/**
 * @author Kohsuke Kawaguchi
 */
public interface RuntimeBuiltinLeafInfo extends BuiltinLeafInfo<Type,Class>, RuntimeLeafInfo {
}
