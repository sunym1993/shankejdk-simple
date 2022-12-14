/*
 * Copyright (c) 1997, 2012, Oracle and/or its affiliates. All rights reserved.
 */

package com.sun.xml.internal.messaging.saaj.client.p2p;

import javax.xml.soap.*;

/**
 * Implementation of the SOAPConnectionFactory
 *
 * @author Anil Vijendran (anil@sun.com)
 */
public class HttpSOAPConnectionFactory extends SOAPConnectionFactory {

    public SOAPConnection createConnection()
        throws SOAPException
    {
        return new HttpSOAPConnection();
    }
}
