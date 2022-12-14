/*
 * Copyright (c) 2011, 2013, Oracle and/or its affiliates. All rights reserved.
 */
package com.sun.source.doctree;

import java.util.List;

/**
 * A tree node for an unrecognized inline tag.
 *
 * <p>
 * {&#064;name content}
 *
 * @since 1.8
 *
 */
@jdk.Exported
public interface UnknownInlineTagTree extends InlineTagTree {
    List<? extends DocTree> getContent();
}
