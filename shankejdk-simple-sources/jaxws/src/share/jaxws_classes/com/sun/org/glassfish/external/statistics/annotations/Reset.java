/*
 * Copyright (c) 2009, 2010, Oracle and/or its affiliates. All rights reserved.
 */



package com.sun.org.glassfish.external.statistics.annotations;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.annotation.ElementType;

/**
 *
 * @author Jennifer Chou
 *         Date: Sep 16, 2009
 */

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
public @interface Reset {
}
