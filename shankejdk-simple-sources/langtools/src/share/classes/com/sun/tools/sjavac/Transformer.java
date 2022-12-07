/*
 * Copyright (c) 2012, 2013, Oracle and/or its affiliates. All rights reserved.
 */

package com.sun.tools.sjavac;

import java.io.PrintStream;
import java.net.URI;
import java.util.Set;
import java.util.Map;

/**
 * The transform interface is used to transform content inside a package, from one form to another.
 * Usually the output form is an unpredictable number of output files. (eg class files)
 * but can also be an unpredictable number of generated source files (eg idl2java)
 * or a single predictable output file (eg when copying,cleaning or compiling a properties file).
 *
 * <p><b>This is NOT part of any supported API.
 * If you write code that depends on this, you do so at your own
 * risk.  This code and its internal interfaces are subject to change
 * or deletion without notice.</b></p>
 */
public interface Transformer
{
    /**
     * The transform method takes a set of package names, mapped to their source files and to the
     * pubapis of the packages.
     *
     * The transform implementation must:
     *    store the names of the generated artifacts for each package into package_artifacts
     *    store found dependencies to other packages into the supplied set package_dependencies
     *    store the public api for a package into the supplied set package_pubapis
     *
     * Any benign messages as a result of running the transform
     * are written into stdout, and errors are written to stderr.
     *
     * The debug_level can be 0=silent (only warnings and errors) 1=normal 2=verbose 3 or greater=debug
     * setExtra is used to set the extra information information that can be passed on
     * the command line to the smart javac wrapper.
     *
     * If sjavac is building incrementally from an existing javac_state, the var incremental is true.
     *
     * The transformer will only be called if some source in the package (or dependency) has
     * a modified timestamp. Thus the transformer might get called with many sources, of which
     * only one has changed. The transformer is allowed to regenerate all artifacts but
     * a better transformer will only write those artifacts that need updating.
     *
     * However the transformer must verify that the existing artifacts really are there!
     * and it must always update package_artifacts, package_dependencies, and package_pubapis correctly.
     * This means that at least for Java source, it will always have to recompile the sources.
     *
     * The transformer is allowed to put files anywhere in the dest_root.
     * An example of this is, can be the META-INF transformer that copy files
     * below META-INF directories to the single META-INF directory below dest_root.
     *
     * False is returned if there was an error that prevented the transform.
     * I.e. something was printed on stderr.
     *
     * If num_cores is set to a non-zero value. The transform should attempt to use no more than these
     * number of threads for heavy work.
     */
    boolean transform(Map<String,Set<URI>> pkgSrcs,
                      Set<URI>             visibleSources,
                      Map<URI,Set<String>> visibleClasses,
                      Map<String,Set<String>> oldPackageDependencies,
                      URI destRoot,
                      Map<String,Set<URI>>    packageArtifacts,
                      Map<String,Set<String>> packageDependencies,
                      Map<String,String>      packagePublicApis,
                      int debugLevel,
                      boolean incremental,
                      int numCores,
                      PrintStream out,
                      PrintStream err);

    void setExtra(String e);
    void setExtra(String[] args);
}