/*
 * Copyright (c) 2005, 2011, Oracle and/or its affiliates. All rights reserved.
 *
 * THIS FILE WAS MODIFIED BY SUN MICROSYSTEMS, INC.
 */

package com.sun.xml.internal.fastinfoset.stax.events;

import javax.xml.stream.events.XMLEvent;
import javax.xml.stream.EventFilter;
import javax.xml.stream.events.Characters;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLEventReader;
import com.sun.xml.internal.fastinfoset.CommonResourceBundle;

public class StAXFilteredEvent implements XMLEventReader {
    private XMLEventReader eventReader;
    private EventFilter _filter;

    /** Creates a new instance of StAXFilteredEvent */
    public StAXFilteredEvent() {
    }

    public StAXFilteredEvent(XMLEventReader reader, EventFilter filter) throws XMLStreamException
    {
        eventReader = reader;
        _filter = filter;
    }

    public void setEventReader(XMLEventReader reader) {
        eventReader = reader;
    }

    public void setFilter(EventFilter filter) {
        _filter = filter;
    }

    public Object next() {
        try {
            return nextEvent();
        } catch (XMLStreamException e) {
            return null;
        }
    }

    public XMLEvent nextEvent() throws XMLStreamException
    {
        if (hasNext())
            return eventReader.nextEvent();
        return null;
    }

    public String getElementText() throws XMLStreamException
    {
        StringBuffer buffer = new StringBuffer();
        XMLEvent e = nextEvent();
        if (!e.isStartElement())
            throw new XMLStreamException(
            CommonResourceBundle.getInstance().getString("message.mustBeOnSTART_ELEMENT"));

        while(hasNext()) {
            e = nextEvent();
            if(e.isStartElement())
                throw new XMLStreamException(
                CommonResourceBundle.getInstance().getString("message.getElementTextExpectTextOnly"));
            if(e.isCharacters())
                buffer.append(((Characters) e).getData());
            if(e.isEndElement())
                return buffer.toString();
        }
        throw new XMLStreamException(CommonResourceBundle.getInstance().getString("message.END_ELEMENTnotFound"));
    }

    public XMLEvent nextTag() throws XMLStreamException {
        while(hasNext()) {
            XMLEvent e = nextEvent();
            if (e.isStartElement() || e.isEndElement())
                return e;
        }
        throw new XMLStreamException(CommonResourceBundle.getInstance().getString("message.startOrEndNotFound"));
    }


    public boolean hasNext()
    {
        try {
            while(eventReader.hasNext()) {
                if (_filter.accept(eventReader.peek())) return true;
                eventReader.nextEvent();
            }
            return false;
        } catch (XMLStreamException e) {
            return false;
        }
    }

    public void remove() {
        throw new UnsupportedOperationException();
    }

    public XMLEvent peek() throws XMLStreamException
    {
        if (hasNext())
            return eventReader.peek();
        return null;
    }

    public void close() throws XMLStreamException
    {
        eventReader.close();
    }

    public Object getProperty(String name) {
        return eventReader.getProperty(name);
    }

}
