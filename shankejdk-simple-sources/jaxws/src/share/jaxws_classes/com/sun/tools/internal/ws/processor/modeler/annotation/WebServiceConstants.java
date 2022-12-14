/*
 * Copyright (c) 1997, 2012, Oracle and/or its affiliates. All rights reserved.
 */

package com.sun.tools.internal.ws.processor.modeler.annotation;

/**
 * @author dkohlert
 */
public enum WebServiceConstants {

    SERVICE("Service"),

    JAXWS_PACKAGE_PD("jaxws."),

    PD_JAXWS_PACKAGE_PD(".jaxws."),

    BEAN("Bean"),

    FAULT_INFO("faultInfo"),

    RESPONSE("Response");

    private String value;

    private WebServiceConstants(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
