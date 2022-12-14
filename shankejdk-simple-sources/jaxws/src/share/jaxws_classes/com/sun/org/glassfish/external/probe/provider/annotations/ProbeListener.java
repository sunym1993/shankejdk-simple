/*
 * Copyright (c) 2009, 2010, Oracle and/or its affiliates. All rights reserved.
 */



package com.sun.org.glassfish.external.probe.provider.annotations;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.annotation.ElementType;

/**
 *
 * @author Prashanth Abbagani
 *         Date: May 26, 2009
 */

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
public @interface ProbeListener {

    public abstract String value();

}
