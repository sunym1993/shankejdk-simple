/*
 * Copyright (c) 1997, 2010, Oracle and/or its affiliates. All rights reserved.
 */

package com.sun.codemodel.internal.util;

import java.io.FilterWriter;
import java.io.IOException;
import java.io.Writer;

/**
 * {@link Writer} that escapes non US-ASCII characters into
 * Java Unicode escape \\uXXXX.
 *
 * This process is necessary if the method names or field names
 * contain non US-ASCII characters.
 *
 * @author
 *      Kohsuke Kawaguchi (kohsuke.kawaguchi@sun.com)
 */
public class UnicodeEscapeWriter extends FilterWriter {

    public UnicodeEscapeWriter( Writer next ) {
        super(next);
    }

    public final void write(int ch) throws IOException {
        if(!requireEscaping(ch))  out.write(ch);
        else {
            // need to escape
            out.write("\\u");
            String s = Integer.toHexString(ch);
            for( int i=s.length(); i<4; i++ )
                out.write('0');
            out.write(s);
        }
    }

    /**
     * Can be overrided. Return true if the character
     * needs to be escaped.
     */
    protected boolean requireEscaping(int ch) {
        if(ch>=128)     return true;

        // control characters
        if( ch<0x20 && " \t\r\n".indexOf(ch)==-1 )  return true;

        return false;
    }

    public final void write(char[] buf, int off, int len) throws IOException {
        for( int i=0; i<len; i++ )
            write(buf[off+i]);
    }

    public final void write(char[] buf) throws IOException {
        write(buf,0,buf.length);
    }

    public final void write(String buf, int off, int len) throws IOException {
        write( buf.toCharArray(), off, len );
    }

    public final void write(String buf) throws IOException {
        write( buf.toCharArray(), 0, buf.length() );
    }

}
