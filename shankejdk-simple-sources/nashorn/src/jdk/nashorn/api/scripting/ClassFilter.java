/*
 * Copyright (c) 2014, Oracle and/or its affiliates. All rights reserved.
 */
package jdk.nashorn.api.scripting;

/**
 * Class filter (optional) to be used by nashorn script engine.
 * jsr-223 program embedding nashorn script can set ClassFilter instance
 * to be used when an engine instance is created.
 *
 * @since 1.8u40
 */
@jdk.Exported
public interface ClassFilter {
     /**
      * Should the Java class of the specified name be exposed to scripts?
      * @param className is the fully qualified name of the java class being
      * checked. This will not be null. Only non-array class names will be
      * passed.
      * @return true if the java class can be exposed to scripts false otherwise
      */
     public boolean exposeToScripts(String className);
}
