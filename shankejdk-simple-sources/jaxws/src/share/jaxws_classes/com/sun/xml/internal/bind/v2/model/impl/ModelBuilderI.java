/*
 * Copyright (c) 2005, 2010, Oracle and/or its affiliates. All rights reserved.
 */

package com.sun.xml.internal.bind.v2.model.impl;

import com.sun.xml.internal.bind.v2.model.annotation.AnnotationReader;
import com.sun.xml.internal.bind.v2.model.nav.Navigator;

/**
 * User: Iaroslav Savytskyi
 * Date: 24/05/12
 */
public interface ModelBuilderI<T,C,F,M> {

    Navigator<T,C,F,M> getNavigator();

    AnnotationReader<T,C,F,M> getReader();
}
