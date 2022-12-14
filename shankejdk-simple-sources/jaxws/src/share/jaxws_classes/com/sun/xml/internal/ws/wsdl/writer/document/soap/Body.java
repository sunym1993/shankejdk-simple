/*
 * Copyright (c) 1997, 2012, Oracle and/or its affiliates. All rights reserved.
 */

package com.sun.xml.internal.ws.wsdl.writer.document.soap;

import com.sun.xml.internal.txw2.TypedXmlWriter;
import com.sun.xml.internal.txw2.annotation.XmlElement;
import com.sun.xml.internal.ws.wsdl.writer.document.soap.BodyType;

/**
 *
 * @author WS Development Team
 */
@XmlElement("body")
public interface Body
    extends TypedXmlWriter, BodyType
{


}
