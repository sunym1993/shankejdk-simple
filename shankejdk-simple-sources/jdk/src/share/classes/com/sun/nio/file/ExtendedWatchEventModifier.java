/*
 * Copyright (c) 2007, 2009, Oracle and/or its affiliates. All rights reserved.
 */

package com.sun.nio.file;

import java.nio.file.WatchEvent.Modifier;

/**
 * Defines <em>extended</em> watch event modifiers supported on some platforms
 * by Sun's provider implementation.
 *
 * @since 1.7
 */

public enum ExtendedWatchEventModifier implements Modifier {

    /**
     * Register a file tree instead of a single directory.
     */
    FILE_TREE,
}
