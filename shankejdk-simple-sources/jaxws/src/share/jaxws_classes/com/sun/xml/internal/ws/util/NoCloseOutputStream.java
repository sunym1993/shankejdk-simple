/*
 * Copyright (c) 1997, 2012, Oracle and/or its affiliates. All rights reserved.
 */

package com.sun.xml.internal.ws.util;

import java.io.FilterOutputStream;
import java.io.OutputStream;
import java.io.IOException;

/**
 * {@link OutputStream} that cannot be closed.
 *
 * @author Kohsuke Kawaguchi
 */
public class NoCloseOutputStream extends FilterOutputStream {
    public NoCloseOutputStream(OutputStream out) {
        super(out);
    }

    @Override
    public void close() throws IOException {
        // Intentionally left empty. use closeOutput() to close
    }

    public void doClose() throws IOException {
        super.close();
    }
}
