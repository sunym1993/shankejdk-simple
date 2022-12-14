/*
 * Copyright (c) 2004, 2011, Oracle and/or its affiliates. All rights reserved.
 *
 * THIS FILE WAS MODIFIED BY SUN MICROSYSTEMS, INC.
 */

package com.sun.xml.internal.fastinfoset.stax.events ;

import javax.xml.stream.events.EndDocument;


public class EndDocumentEvent extends EventBase implements EndDocument {

    public EndDocumentEvent() {
        super(END_DOCUMENT);
    }

    public String toString() {
        return "<? EndDocument ?>";
    }

}
