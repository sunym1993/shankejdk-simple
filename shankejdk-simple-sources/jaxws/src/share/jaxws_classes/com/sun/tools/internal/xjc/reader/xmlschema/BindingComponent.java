/*
 * Copyright (c) 1997, 2011, Oracle and/or its affiliates. All rights reserved.
 */

package com.sun.tools.internal.xjc.reader.xmlschema;

import com.sun.tools.internal.xjc.reader.Ring;

/**
 * Component accessible from {@link Ring}.
 *
 * @author Kohsuke Kawaguchi
 */
public abstract class BindingComponent {
    protected BindingComponent() {
        Ring.add(this);
    }

//
//
// Accessor to common components.
//
//

    protected final ErrorReporter getErrorReporter() {
        return Ring.get(ErrorReporter.class);
    }
    protected final ClassSelector getClassSelector() {
        return Ring.get(ClassSelector.class);
    }
}
