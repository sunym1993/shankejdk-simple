/*
 * Copyright (c) 1997, 2011, Oracle and/or its affiliates. All rights reserved.
 */

package com.sun.xml.internal.bind.v2.model.annotation;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Type;

/**
 * @author Kohsuke Kawaguchi
 */
public interface RuntimeAnnotationReader extends AnnotationReader<Type,Class,Field,Method> {
}
