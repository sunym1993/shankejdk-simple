/*
 * Copyright (c) 1997, 2010, Oracle and/or its affiliates. All rights reserved.
 */

package com.sun.codemodel.internal.writer;

import java.io.OutputStream;
import java.io.IOException;
import java.io.Writer;

import com.sun.codemodel.internal.CodeWriter;
import com.sun.codemodel.internal.JPackage;

/**
 * {@link CodeWriter} that delegates to another {@link CodeWriter}.
 *
 * @author Kohsuke Kawaguchi
 */
public class FilterCodeWriter extends CodeWriter {
    protected CodeWriter core;

    public FilterCodeWriter(CodeWriter core) {
        this.core = core;
    }

    public OutputStream openBinary( JPackage pkg, String fileName ) throws IOException {
        return core.openBinary(pkg, fileName);
    }

    public Writer openSource( JPackage pkg, String fileName ) throws IOException {
        return core.openSource(pkg, fileName);
    }

    public void close() throws IOException {
        core.close();
    }
}
