/*
 * Copyright (c) 2004, 2011, Oracle and/or its affiliates. All rights reserved.
 *
 * THIS FILE WAS MODIFIED BY SUN MICROSYSTEMS, INC.
 */

package com.sun.xml.internal.fastinfoset.stax.events;

import javax.xml.namespace.QName;
import javax.xml.stream.events.Namespace;


public class NamespaceBase extends AttributeBase implements Namespace{
    //J2SE1.5.0 javax.xml.XMLConstants
    static final String DEFAULT_NS_PREFIX = "";
    static final String XML_NS_URI = "http://www.w3.org/XML/1998/namespace";
    static final String XML_NS_PREFIX = "xml";
    static final String XMLNS_ATTRIBUTE_NS_URI = "http://www.w3.org/2000/xmlns/";
    static final String XMLNS_ATTRIBUTE = "xmlns";
    static final String W3C_XML_SCHEMA_NS_URI = "http://www.w3.org/2001/XMLSchema";
    static final String W3C_XML_SCHEMA_INSTANCE_NS_URI = "http://www.w3.org/2001/XMLSchema-instance";

    //is this namespace default declaration?
    private boolean defaultDeclaration = false;

    /** a namespace attribute has a form: xmlns:NCName="URI reference" */
    public NamespaceBase(String namespaceURI) {
        super(XMLNS_ATTRIBUTE, "", namespaceURI);
        setEventType(NAMESPACE);
    }

  /**
   * Create a new Namespace
   * @param prefix prefix of a namespace is the local name for an attribute
   * @param namespaceURI the uri reference of a namespace is the value for an attribute
   */
    public NamespaceBase(String prefix, String namespaceURI){
        super(XMLNS_ATTRIBUTE, prefix, namespaceURI);
        setEventType(NAMESPACE);
        if (Util.isEmptyString(prefix)) {
            defaultDeclaration=true;
        }
    }

    void setPrefix(String prefix){
        if(prefix == null)
            setName(new QName(XMLNS_ATTRIBUTE_NS_URI,DEFAULT_NS_PREFIX,XMLNS_ATTRIBUTE));
        else// new QName(uri, localpart, prefix)
            setName(new QName(XMLNS_ATTRIBUTE_NS_URI,prefix,XMLNS_ATTRIBUTE));
    }

    public String getPrefix() {
        if (defaultDeclaration) return "";
        return super.getLocalName();
    }


  /**
   * set Namespace URI reference (xmlns:prefix = "uri")
   * @param uri the uri reference of a namespace is the value for an attribute
   */
    void setNamespaceURI(String uri) {
        setValue(uri);
    }
    public String getNamespaceURI() {
        return getValue();
    }


    public boolean isNamespace(){
        return true;
    }

    public boolean isDefaultNamespaceDeclaration() {
        return defaultDeclaration;
    }


}
