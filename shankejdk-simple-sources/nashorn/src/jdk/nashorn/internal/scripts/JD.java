/*
 * Copyright (c) 2015, Oracle and/or its affiliates. All rights reserved.
 */

package jdk.nashorn.internal.scripts;

import jdk.nashorn.internal.runtime.PropertyMap;
import jdk.nashorn.internal.runtime.ScriptObject;

/**
 * Empty object class for dual primitive-object fields.
 */
public class JD extends ScriptObject {

    private static final PropertyMap map$ = PropertyMap.newMap(JD.class);

    /**
     * Returns the initial property map to be used.
     * @return the initial property map.
     */
    public static PropertyMap getInitialMap() {
        return map$;
    }

    /**
     * Constructor given an initial property map
     *
     * @param map the property map
     */
    public JD(final PropertyMap map) {
        super(map);
    }

    /**
     * Constructor given an initial prototype and the default initial property map.
     *
     * @param proto the prototype object
     */
    public JD(final ScriptObject proto) {
        super(proto, getInitialMap());
    }

    /**
     * Constructor that takes a pre-initialized spill pool. Used by
     * {@link jdk.nashorn.internal.codegen.SpillObjectCreator} and
     * {@link jdk.nashorn.internal.parser.JSONParser} for initializing object literals
     *
     * @param map            property map
     * @param primitiveSpill primitive spill pool
     * @param objectSpill    reference spill pool
     */
    public JD(final PropertyMap map, final long[] primitiveSpill, final Object[] objectSpill) {
        super(map, primitiveSpill, objectSpill);
    }

    /**
     * A method handle of this method is passed to the ScriptFunction constructor.
     *
     * @param map  the property map to use for allocatorMap
     *
     * @return newly allocated ScriptObject
     */
    public static ScriptObject allocate(final PropertyMap map) {
        return new JD(map);
    }
}

