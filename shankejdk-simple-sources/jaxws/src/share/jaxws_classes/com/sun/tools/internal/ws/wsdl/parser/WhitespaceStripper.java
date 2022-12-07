/*
 * Copyright (c) 1997, 2012, Oracle and/or its affiliates. All rights reserved.
 */

package com.sun.tools.internal.ws.wsdl.parser;

import com.sun.xml.internal.bind.WhiteSpaceProcessor;
import org.xml.sax.*;
import org.xml.sax.helpers.XMLFilterImpl;

/**
 * Strips ignorable whitespace from SAX event stream.
 *
 * <p>
 * This filter works only when the event stream doesn't
 * contain any mixed content.
 *
 * @author
 *     Kohsuke Kawaguchi (kohsuke.kawaguchi@sun.com)
 *     Vivek Pandey
 */
class WhitespaceStripper extends XMLFilterImpl {

    private int state = 0;

    private char[] buf = new char[1024];
    private int bufLen = 0;

    private static final int AFTER_START_ELEMENT = 1;
    private static final int AFTER_END_ELEMENT = 2;

    public WhitespaceStripper(XMLReader reader) {
        setParent(reader);
    }

    public WhitespaceStripper(ContentHandler handler, ErrorHandler eh, EntityResolver er) {
        setContentHandler(handler);
        if(eh!=null)    setErrorHandler(eh);
        if(er!=null)    setEntityResolver(er);
    }

    public void characters(char[] ch, int start, int length) throws SAXException {
        switch(state) {
        case AFTER_START_ELEMENT:
            // we have to store the characters here, even if it consists entirely
            // of whitespaces. This is because successive characters event might
            // include non-whitespace char, in which case all the whitespaces in
            // this event may suddenly become significant.
            if( bufLen+length>buf.length ) {
                // reallocate buffer
                char[] newBuf = new char[Math.max(bufLen+length,buf.length*2)];
                System.arraycopy(buf,0,newBuf,0,bufLen);
                buf = newBuf;
            }
            System.arraycopy(ch,start,buf,bufLen,length);
            bufLen += length;
            break;
        case AFTER_END_ELEMENT:
            // check if this is ignorable.
            int len = start+length;
            for( int i=start; i<len; i++ )
                if( !WhiteSpaceProcessor.isWhiteSpace(ch[i]) ) {
                    super.characters(ch, start, length);
                    return;
                }
            // if it's entirely whitespace, ignore it.
            break;
        }
    }

    public void startElement(String uri, String localName, String qName, Attributes atts) throws SAXException {
        processPendingText();
        super.startElement(uri, localName, qName, atts);
        state = AFTER_START_ELEMENT;
        bufLen = 0;
    }

    public void endElement(String uri, String localName, String qName) throws SAXException {
        processPendingText();
        super.endElement(uri, localName, qName);
        state = AFTER_END_ELEMENT;
    }

    /**
     * Forwars the buffered characters if it contains any non-whitespace
     * character.
     */
    private void processPendingText() throws SAXException {
        if(state==AFTER_START_ELEMENT) {
            for( int i=bufLen-1; i>=0; i-- )
                if( !WhiteSpaceProcessor.isWhiteSpace(buf[i]) ) {
                    super.characters(buf, 0, bufLen);
                    return;
               }
        }
    }

    public void ignorableWhitespace(char[] ch, int start, int length) throws SAXException {
        // ignore completely.
    }
}