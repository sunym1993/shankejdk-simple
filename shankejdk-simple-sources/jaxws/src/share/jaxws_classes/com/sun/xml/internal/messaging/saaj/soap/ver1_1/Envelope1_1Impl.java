/*
 * Copyright (c) 1997, 2012, Oracle and/or its affiliates. All rights reserved.
 */

/**
*
* @author SAAJ RI Development Team
*/
package com.sun.xml.internal.messaging.saaj.soap.ver1_1;

import javax.xml.soap.SOAPException;

import com.sun.xml.internal.messaging.saaj.soap.SOAPDocumentImpl;
import com.sun.xml.internal.messaging.saaj.soap.impl.EnvelopeImpl;
import com.sun.xml.internal.messaging.saaj.soap.name.NameImpl;

public class Envelope1_1Impl extends EnvelopeImpl {

    public Envelope1_1Impl(SOAPDocumentImpl ownerDoc, String prefix){
        super(ownerDoc, NameImpl.createEnvelope1_1Name(prefix));
    }
    Envelope1_1Impl(
        SOAPDocumentImpl ownerDoc,
        String prefix,
        boolean createHeader,
        boolean createBody)
        throws SOAPException {
        super(
            ownerDoc,
            NameImpl.createEnvelope1_1Name(prefix),
            createHeader,
            createBody);
    }
    protected NameImpl getBodyName(String prefix) {
        return NameImpl.createBody1_1Name(prefix);
    }

    protected NameImpl getHeaderName(String prefix) {
        return NameImpl.createHeader1_1Name(prefix);
    }

}
