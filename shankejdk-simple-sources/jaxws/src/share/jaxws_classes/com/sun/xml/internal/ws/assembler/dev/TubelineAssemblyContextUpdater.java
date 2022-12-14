/*
 * Copyright (c) 1997, 2012, Oracle and/or its affiliates. All rights reserved.
 */

package com.sun.xml.internal.ws.assembler.dev;

import javax.xml.ws.WebServiceException;

/**
 *
 * @author Marek Potociar (marek.potociar at sun.com)
 */
public interface TubelineAssemblyContextUpdater {
    /**
     * TODO javadoc
     *
     * @param context
     * @throws javax.xml.ws.WebServiceException
     */
    void prepareContext(ClientTubelineAssemblyContext context) throws WebServiceException;

    /**
     * TODO javadoc
     *
     * @param context
     * @throws javax.xml.ws.WebServiceException
     */
    void prepareContext(ServerTubelineAssemblyContext context) throws WebServiceException;
}
