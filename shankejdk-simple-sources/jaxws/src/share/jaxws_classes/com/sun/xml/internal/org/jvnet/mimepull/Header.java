/*
 * Copyright (c) 1997, 2012, Oracle and/or its affiliates. All rights reserved.
 */

package com.sun.xml.internal.org.jvnet.mimepull;

/**
 * The Header class stores a name/value pair to represent headers.
 *
 * @author John Mani
 */

public interface Header {

    /**
     * Returns the name of this header.
     *
     * @return          name of the header
     */
    String getName();

    /**
     * Returns the value of this header.
     *
     * @return          value of the header
     */
    String getValue();
}
