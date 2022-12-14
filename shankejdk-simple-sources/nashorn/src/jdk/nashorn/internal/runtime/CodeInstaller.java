/*
 * Copyright (c) 2010, 2013, Oracle and/or its affiliates. All rights reserved.
 */

package jdk.nashorn.internal.runtime;

import java.util.Collection;
import java.util.Map;
import jdk.nashorn.internal.codegen.ClassEmitter;

/**
 * Interface for installing classes passed to the compiler.
 * As only the code generating package (i.e. Context) knows about
 * the ScriptLoader and it would be a security hazard otherwise
 * the Compiler is given an installation interface for its code.
 * <p>
 * The compiler still retains most of the state around code emission
 * and management internally, so this is to avoid passing around any
 * logic that isn't directly related to installing a class
 *
 */
public interface CodeInstaller {
    /**
     * Return the {@link Context} associated with this code installer.
     * @return the context.
     */
    public Context getContext();

    /**
     * Install a class.
     * @param className name of the class with / separation
     * @param bytecode  bytecode
     * @return the installed class
     */
    public Class<?> install(final String className, final byte[] bytecode);

    /**
     * Initialize already installed classes.
     * @param classes the class to initialize
     * @param source the source object for the classes
     * @param constants the runtime constants for the classes
     */
    public void initialize(final Collection<Class<?>> classes, final Source source, final Object[] constants);

    /**
     * Verify generated bytecode before emission. This is called back from the
     * {@link ClassEmitter} or the {@link Compiler}. If the "--verify-code" parameter
     * hasn't been given, this is a nop
     *
     * @param code bytecode to verify
     */
    public void verify(final byte[] code);

    /**
     * Get next unique script id
     * @return unique script id
     */
    public long getUniqueScriptId();

    /**
     * Store a compiled script for later reuse
     *
     * @param cacheKey key to use in cache
     * @param source the script source
     * @param mainClassName the main class name
     * @param classBytes map of class names to class bytes
     * @param initializers compilation id -&gt; FunctionInitializer map
     * @param constants constants array
     * @param compilationId compilation id
     */
    public void storeScript(final String cacheKey, final Source source, final String mainClassName, final Map<String, byte[]> classBytes,
            final Map<Integer, FunctionInitializer> initializers, final Object[] constants, final int compilationId);

    /**
     * Load a previously compiled script
     * @param source the script source
     * @param functionKey the function id and signature
     * @return compiled script data
     */
    public StoredScript loadScript(Source source, String functionKey);

    /**
     * Returns a new code installer that shares most of the functionality of this code installer, but uses a
     * new, independent class loader.
     * @return a new code installer with a new independent class loader.
     */
    public CodeInstaller withNewLoader();

    /**
     * Returns true if this code installer is compatible with the other code installer. Compatibility is expected to be
     * an equivalence relation, and installers are supposed to be compatible with those they create using
     * {@link #withNewLoader()}.
     * @param other the other code installer tested for compatibility with this code installer.
     * @return true if this code installer is compatible with the other code installer.
     */
    public boolean isCompatibleWith(CodeInstaller other);

}
