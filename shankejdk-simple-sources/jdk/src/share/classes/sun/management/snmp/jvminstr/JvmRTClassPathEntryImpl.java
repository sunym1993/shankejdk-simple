/*
 * Copyright (c) 2004, 2012, Oracle and/or its affiliates. All rights reserved.
 */
package sun.management.snmp.jvminstr;

// java imports
//
import java.io.Serializable;

// jmx imports
//
import com.sun.jmx.snmp.SnmpStatusException;

// jdmk imports
//
import com.sun.jmx.snmp.agent.SnmpMib;

import sun.management.snmp.jvmmib.JvmRTClassPathEntryMBean;

/**
 * The class is used for implementing the "JvmRTClassPathEntry" group.
 */
public class JvmRTClassPathEntryImpl implements JvmRTClassPathEntryMBean,
                                                Serializable {

    static final long serialVersionUID = 8524792845083365742L;
    private final String item;
    private final int index;

    /**
     * Constructor for the "JvmRTClassPathEntry" group.
     */
    public JvmRTClassPathEntryImpl(String item, int index) {
        this.item = validPathElementTC(item);
        this.index = index;
    }

    private String validPathElementTC(String str) {
        return JVM_MANAGEMENT_MIB_IMPL.validPathElementTC(str);
    }

    /**
     * Getter for the "JvmRTClassPathItem" variable.
     */
    public String getJvmRTClassPathItem() throws SnmpStatusException {
        return item;
    }

    /**
     * Getter for the "JvmRTClassPathIndex" variable.
     */
    public Integer getJvmRTClassPathIndex() throws SnmpStatusException {
        return new Integer(index);
    }

}
