/*
 * Copyright (c) 2009, 2012, Oracle and/or its affiliates. All rights reserved.
 */

package com.sun.tools.javac.nio;

import java.io.IOException;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import javax.tools.FileObject;
import javax.tools.JavaFileManager;
import javax.tools.JavaFileObject;

/**
 *  File manager based on {@link java.nio.file.Path}.
 *
 *  Eventually, this should be moved to javax.tools.
 *  Also, JavaCompiler might reasonably provide a method getPathFileManager,
 *  similar to {@link javax.tools.JavaCompiler#getStandardFileManager
 *  getStandardFileManager}. However, would need to be handled carefully
 *  as another forward reference from langtools to jdk.
 *
 *  <p><b>This is NOT part of any supported API.
 *  If you write code that depends on this, you do so at your own risk.
 *  This code and its internal interfaces are subject to change or
 *  deletion without notice.</b>
 */
public interface PathFileManager extends JavaFileManager {
    /**
     * Get the default file system used to create paths. If no value has been
     * set, the default file system is {@link FileSystems#getDefault}.
     */
    FileSystem getDefaultFileSystem();

    /**
     * Set the default file system used to create paths.
     * @param fs the default file system used to create any new paths.
     */
    void setDefaultFileSystem(FileSystem fs);

    /**
     * Get file objects representing the given files.
     *
     * @param paths a list of paths
     * @return a list of file objects
     * @throws IllegalArgumentException if the list of paths includes
     * a directory
     */
    Iterable<? extends JavaFileObject> getJavaFileObjectsFromPaths(
        Iterable<? extends Path> paths);

    /**
     * Get file objects representing the given paths.
     * Convenience method equivalent to:
     *
     * <pre>
     *     getJavaFileObjectsFromPaths({@linkplain java.util.Arrays#asList Arrays.asList}(paths))
     * </pre>
     *
     * @param paths an array of paths
     * @return a list of file objects
     * @throws IllegalArgumentException if the array of files includes
     * a directory
     * @throws NullPointerException if the given array contains null
     * elements
     */
    Iterable<? extends JavaFileObject> getJavaFileObjects(Path... paths);

    /**
     * Return the Path for a file object that has been obtained from this
     * file manager.
     *
     * @param fo A file object that has been obtained from this file manager.
     * @return The underlying Path object.
     * @throws IllegalArgumentException is the file object was not obtained from
     * from this file manager.
     */
    Path getPath(FileObject fo);

    /**
     * Get the search path associated with the given location.
     *
     * @param location a location
     * @return a list of paths or {@code null} if this location has no
     * associated search path
     * @see #setLocation
     */
    Iterable<? extends Path> getLocation(Location location);

    /**
     * Associate the given search path with the given location.  Any
     * previous value will be discarded.
     *
     * @param location a location
     * @param searchPath a list of files, if {@code null} use the default
     * search path for this location
     * @see #getLocation
     * @throws IllegalArgumentException if location is an output
     * location and searchpath does not contain exactly one element
     * @throws IOException if location is an output location and searchpath
     * does not represent an existing directory
     */
    void setLocation(Location location, Iterable<? extends Path> searchPath) throws IOException;
}
