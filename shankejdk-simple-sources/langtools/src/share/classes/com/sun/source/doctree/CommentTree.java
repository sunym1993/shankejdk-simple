/*
 * Copyright (c) 2011, 2013, Oracle and/or its affiliates. All rights reserved.
 */

package com.sun.source.doctree;

/**
 * An embedded HTML comment.
 *
 * <p>
 * {@literal <!-- text --> }
 *
 * @since 1.8
 */
@jdk.Exported
public interface CommentTree extends DocTree {
    String getBody();
}

