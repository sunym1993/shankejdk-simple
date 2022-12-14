/*
 * Copyright (c) 1997, 2012, Oracle and/or its affiliates. All rights reserved.
 */

package com.sun.xml.internal.ws.wsdl.writer.document.soap;

import javax.xml.namespace.QName;
import com.sun.xml.internal.txw2.TypedXmlWriter;
import com.sun.xml.internal.txw2.annotation.XmlAttribute;
import com.sun.xml.internal.txw2.annotation.XmlElement;
import com.sun.xml.internal.ws.wsdl.writer.document.soap.BodyType;
import com.sun.xml.internal.ws.wsdl.writer.document.soap.HeaderFault;

/**
 *
 * @author WS Development Team
 */
@XmlElement("header")
public interface Header
    extends TypedXmlWriter, BodyType
{


    @XmlAttribute
    public com.sun.xml.internal.ws.wsdl.writer.document.soap.Header message(QName value);

    @XmlElement
    public HeaderFault headerFault();

    @XmlAttribute
    public com.sun.xml.internal.ws.wsdl.writer.document.soap.BodyType part(String value);

}
