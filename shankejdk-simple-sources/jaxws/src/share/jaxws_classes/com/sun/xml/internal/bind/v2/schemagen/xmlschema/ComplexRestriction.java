/*
 * Copyright (c) 1997, 2012, Oracle and/or its affiliates. All rights reserved.
 */

package com.sun.xml.internal.bind.v2.schemagen.xmlschema;

import javax.xml.namespace.QName;
import com.sun.xml.internal.txw2.TypedXmlWriter;
import com.sun.xml.internal.txw2.annotation.XmlAttribute;
import com.sun.xml.internal.txw2.annotation.XmlElement;

/**
 * <p><b>
 *     Auto-generated, do not edit.
 * </b></p>
 */
@XmlElement("restriction")
public interface ComplexRestriction
    extends Annotated, AttrDecls, TypeDefParticle, TypedXmlWriter
{


    @XmlAttribute
    public ComplexRestriction base(QName value);

}
