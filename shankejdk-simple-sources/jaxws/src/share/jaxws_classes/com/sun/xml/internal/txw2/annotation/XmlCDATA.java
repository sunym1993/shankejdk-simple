/*
 * Copyright (c) 2005, 2010, Oracle and/or its affiliates. All rights reserved.
 */

package com.sun.xml.internal.txw2.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.RetentionPolicy.RUNTIME;
import static java.lang.annotation.ElementType.METHOD;

/**
 * Used along with {@link XmlElement} to write a CDATA section,
 * instead of the normal PCDATA.
 *
 * @author Kohsuke Kawaguchi
 */
@Retention(RUNTIME)
@Target({METHOD})
public @interface XmlCDATA {
}
