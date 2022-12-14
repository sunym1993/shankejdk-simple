/*
 * Copyright (c) 2003, 2004, Oracle and/or its affiliates. All rights reserved.
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

import java.lang.management.GarbageCollectorMXBean;

import sun.management.snmp.jvmmib.JvmMemGCEntryMBean;
import sun.management.snmp.util.MibLogger;

/**
 * The class is used for implementing the "JvmMemGCEntry" group.
 */
public class JvmMemGCEntryImpl implements JvmMemGCEntryMBean {

    /**
     * Variable for storing the value of "JvmMemManagerIndex".
     *
     * "An index opaquely computed by the agent and which uniquely
     * identifies a Memory Manager."
     *
     */
    protected final int JvmMemManagerIndex;

    protected final GarbageCollectorMXBean gcm;

    /**
     * Constructor for the "JvmMemGCEntry" group.
     */
    public JvmMemGCEntryImpl(GarbageCollectorMXBean gcm, int index) {
        this.gcm=gcm;
        this.JvmMemManagerIndex = index;
    }

    /**
     * Getter for the "JvmMemGCTimeMs" variable.
     */
    // Don't bother to uses the request contextual cache for this.
    public Long getJvmMemGCTimeMs() throws SnmpStatusException {
        return new Long(gcm.getCollectionTime());
    }

    /**
     * Getter for the "JvmMemGCCount" variable.
     */
    // Don't bother to uses the request contextual cache for this.
    public Long getJvmMemGCCount() throws SnmpStatusException {
        return new Long(gcm.getCollectionCount());
    }

    /**
     * Getter for the "JvmMemManagerIndex" variable.
     */
    public Integer getJvmMemManagerIndex() throws SnmpStatusException {
        return new Integer(JvmMemManagerIndex);
    }

}
