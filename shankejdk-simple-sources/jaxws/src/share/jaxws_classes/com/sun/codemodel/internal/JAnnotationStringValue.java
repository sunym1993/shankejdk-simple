/*
 * Copyright (c) 1997, 2010, Oracle and/or its affiliates. All rights reserved.
 */

package com.sun.codemodel.internal;



/**
 * Captures the value of the annotation.
 *
 * @author
 *     Bhakti Mehta (bhakti.mehta@sun.com)
 */
final class JAnnotationStringValue extends JAnnotationValue {

    /**
     * The value of the Annotation member
     */
    private final JExpression value;

    JAnnotationStringValue(JExpression value) {
        this.value = value;
    }

    public void generate(JFormatter f) {
        f.g(value);
    }
}
