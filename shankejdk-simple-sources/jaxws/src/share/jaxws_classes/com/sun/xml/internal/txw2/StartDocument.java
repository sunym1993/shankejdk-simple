/*
 * Copyright (c) 2005, 2010, Oracle and/or its affiliates. All rights reserved.
 */

package com.sun.xml.internal.txw2;

/**
 * @author Kohsuke Kawaguchi
 */
final class StartDocument extends Content {
    boolean concludesPendingStartTag() {
        return true;
    }

    void accept(ContentVisitor visitor) {
        visitor.onStartDocument();
    }
}
