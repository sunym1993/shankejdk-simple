/*
 * Copyright (c) 1997, 2010, Oracle and/or its affiliates. All rights reserved.
 */

package com.sun.xml.internal.ws.policy.privateutil;

/**
 *
 * @author Marek Potociar (marek.potociar at sun.com)
 */
public final class RuntimePolicyUtilsException extends RuntimeException {

    RuntimePolicyUtilsException(final String message) {
        super(message);
    }

    RuntimePolicyUtilsException(final String message, final Throwable cause) {
        super(message, cause);
    }
}
