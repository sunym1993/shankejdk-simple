/*
 * Copyright (c) 1997, 2012, Oracle and/or its affiliates. All rights reserved.
 */

package com.sun.xml.internal.ws.wsdl.writer.document.soap;

import javax.xml.namespace.QName;
import com.sun.xml.internal.txw2.TypedXmlWriter;
import com.sun.xml.internal.txw2.annotation.XmlAttribute;
import com.sun.xml.internal.txw2.annotation.XmlElement;
import com.sun.xml.internal.ws.wsdl.writer.document.soap.BodyType;

/**
 *
 * @author WS Development Team
 */
@XmlElement("headerFault")
public interface HeaderFault
    extends TypedXmlWriter, BodyType
{


    @XmlAttribute
    public com.sun.xml.internal.ws.wsdl.writer.document.soap.HeaderFault message(QName value);

}
