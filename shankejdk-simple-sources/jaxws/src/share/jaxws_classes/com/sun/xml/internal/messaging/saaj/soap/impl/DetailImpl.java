/*
 * Copyright (c) 1997, 2017, Oracle and/or its affiliates. All rights reserved.
 */

package com.sun.xml.internal.messaging.saaj.soap.impl;

import java.util.Iterator;
import java.util.NoSuchElementException;

import javax.xml.namespace.QName;
import javax.xml.soap.*;

import org.w3c.dom.Element;

import com.sun.xml.internal.messaging.saaj.soap.SOAPDocumentImpl;
import com.sun.xml.internal.messaging.saaj.soap.name.NameImpl;

public abstract class DetailImpl extends FaultElementImpl implements Detail {
    public DetailImpl(SOAPDocumentImpl ownerDoc, NameImpl detailName) {
        super(ownerDoc, detailName);
    }

    protected abstract DetailEntry createDetailEntry(Name name);
    protected abstract DetailEntry createDetailEntry(QName name);

    public DetailEntry addDetailEntry(Name name) throws SOAPException {
        DetailEntry entry = createDetailEntry(name);
        addNode(entry);
        return entry;
    }

    public DetailEntry addDetailEntry(QName qname) throws SOAPException {
        DetailEntry entry = createDetailEntry(qname);
        addNode(entry);
        return entry;
    }

    protected SOAPElement addElement(Name name) throws SOAPException {
        return addDetailEntry(name);
    }

    protected SOAPElement addElement(QName name) throws SOAPException {
        return addDetailEntry(name);
    }

    protected SOAPElement convertToSoapElement(Element element) {
        if (element instanceof DetailEntry) {
            return (SOAPElement) element;
        } else {
            DetailEntry detailEntry =
                createDetailEntry(NameImpl.copyElementName(element));
            return replaceElementWithSOAPElement(
                element,
                (ElementImpl) detailEntry);
        }
    }

    public Iterator getDetailEntries() {
        return new Iterator() {
            Iterator eachNode = getChildElementNodes();
            SOAPElement next = null;
            SOAPElement last = null;

            public boolean hasNext() {
                if (next == null) {
                    while (eachNode.hasNext()) {
                        next = (SOAPElement) eachNode.next();
                        if (next instanceof DetailEntry) {
                            break;
                        }
                        next = null;
                    }
                }
                return next != null;
            }

            public Object next() {
                if (!hasNext()) {
                    throw new NoSuchElementException();
                }
                last = next;
                next = null;
                return last;
            }

            public void remove() {
                if (last == null) {
                    throw new IllegalStateException();
                }
                SOAPElement target = last;
                removeChild(target);
                last = null;
            }
        };
    }

   protected  boolean isStandardFaultElement() {
       return true;
   }

}
