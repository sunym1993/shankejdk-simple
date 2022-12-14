/*
 * Copyright (c) 1997, 2012, Oracle and/or its affiliates. All rights reserved.
 */

package com.sun.xml.internal.ws.wsdl.writer.document.soap12;

import com.sun.xml.internal.txw2.TypedXmlWriter;
import com.sun.xml.internal.txw2.annotation.XmlAttribute;
import com.sun.xml.internal.txw2.annotation.XmlElement;

/**
 *
 * @author WS Development Team
 */
@XmlElement("operation")
public interface SOAPOperation
    extends TypedXmlWriter
{


    @XmlAttribute
    public com.sun.xml.internal.ws.wsdl.writer.document.soap12.SOAPOperation soapAction(String value);

    @XmlAttribute
    public com.sun.xml.internal.ws.wsdl.writer.document.soap12.SOAPOperation style(String value);

}
