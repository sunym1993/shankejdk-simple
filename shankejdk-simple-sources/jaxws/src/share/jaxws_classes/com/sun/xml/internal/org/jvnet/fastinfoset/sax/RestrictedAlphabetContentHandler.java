/*
 * Copyright (c) 2004, 2011, Oracle and/or its affiliates. All rights reserved.
 *
 * THIS FILE WAS MODIFIED BY SUN MICROSYSTEMS, INC.
 */

package com.sun.xml.internal.org.jvnet.fastinfoset.sax;

import org.xml.sax.SAXException;

public interface RestrictedAlphabetContentHandler {

    public void numericCharacters(char ch[], int start, int length) throws SAXException;

    public void dateTimeCharacters(char ch[], int start, int length) throws SAXException;

    public void alphabetCharacters(String alphabet, char ch[], int start, int length) throws SAXException;
}
