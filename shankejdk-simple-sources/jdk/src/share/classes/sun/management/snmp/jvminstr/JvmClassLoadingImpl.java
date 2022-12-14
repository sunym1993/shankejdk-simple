/*
 * Copyright (c) 2003, 2004, Oracle and/or its affiliates. All rights reserved.
 */
package sun.management.snmp.jvminstr;

// java imports
//
import java.io.Serializable;

import java.lang.management.ClassLoadingMXBean;
import java.lang.management.ManagementFactory;
// jmx imports
//
import javax.management.MBeanServer;
import com.sun.jmx.snmp.SnmpString;
import com.sun.jmx.snmp.SnmpStatusException;

// jdmk imports
//
import com.sun.jmx.snmp.agent.SnmpMib;

import sun.management.snmp.jvmmib.JvmClassLoadingMBean;
import sun.management.snmp.jvmmib.EnumJvmClassesVerboseLevel;
import sun.management.snmp.util.MibLogger;

/**
 * The class is used for implementing the "JvmClassLoading" group.
 */
public class JvmClassLoadingImpl implements JvmClassLoadingMBean {

    /**
     * Variable for storing the value of "JvmClassesVerboseLevel".
     *
     * "verbose: if the -verbose:class flag is set.
     * silent:  otherwise.
     *
     * See java.management.ClassLoadingMXBean.isVerbose(),
     * java.management.ClassLoadingMXBean.setVerbose()
     * "
     *
     */
    static final EnumJvmClassesVerboseLevel JvmClassesVerboseLevelVerbose =
        new EnumJvmClassesVerboseLevel("verbose");
    static final EnumJvmClassesVerboseLevel JvmClassesVerboseLevelSilent =
        new EnumJvmClassesVerboseLevel("silent");

    /**
     * Constructor for the "JvmClassLoading" group.
     * If the group contains a table, the entries created through an
     * SNMP SET will not be registered in Java DMK.
     */
    public JvmClassLoadingImpl(SnmpMib myMib) {
    }

    /**
     * Constructor for the "JvmClassLoading" group.
     * If the group contains a table, the entries created through an SNMP SET
     * will be AUTOMATICALLY REGISTERED in Java DMK.
     */
    public JvmClassLoadingImpl(SnmpMib myMib, MBeanServer server) {
    }

    static ClassLoadingMXBean getClassLoadingMXBean() {
        return ManagementFactory.getClassLoadingMXBean();
    }

    /**
     * Getter for the "JvmClassesVerboseLevel" variable.
     */
    public EnumJvmClassesVerboseLevel getJvmClassesVerboseLevel()
        throws SnmpStatusException {
        if(getClassLoadingMXBean().isVerbose())
            return JvmClassesVerboseLevelVerbose;
        else
            return JvmClassesVerboseLevelSilent;
    }

    /**
     * Setter for the "JvmClassesVerboseLevel" variable.
     */
    public void setJvmClassesVerboseLevel(EnumJvmClassesVerboseLevel x)
        throws SnmpStatusException {
        final boolean verbose;
        if (JvmClassesVerboseLevelVerbose.equals(x)) verbose=true;
        else if (JvmClassesVerboseLevelSilent.equals(x)) verbose=false;
        // Should never happen, this case is handled by
        // checkJvmClassesVerboseLevel();
        else throw new
            SnmpStatusException(SnmpStatusException.snmpRspWrongValue);
        getClassLoadingMXBean().setVerbose(verbose);
    }

    /**
     * Checker for the "JvmClassesVerboseLevel" variable.
     */
    public void checkJvmClassesVerboseLevel(EnumJvmClassesVerboseLevel x)
        throws SnmpStatusException {
        //
        // Add your own checking policy.
        //
        if (JvmClassesVerboseLevelVerbose.equals(x)) return;
        if (JvmClassesVerboseLevelSilent.equals(x))  return;
        throw new SnmpStatusException(SnmpStatusException.snmpRspWrongValue);

    }

    /**
     * Getter for the "JvmClassesUnloadedCount" variable.
     */
    public Long getJvmClassesUnloadedCount() throws SnmpStatusException {
        return new Long(getClassLoadingMXBean().getUnloadedClassCount());
    }

    /**
     * Getter for the "JvmClassesTotalLoadedCount" variable.
     */
    public Long getJvmClassesTotalLoadedCount() throws SnmpStatusException {
        return new Long(getClassLoadingMXBean().getTotalLoadedClassCount());
    }

    /**
     * Getter for the "JvmClassesLoadedCount" variable.
     */
    public Long getJvmClassesLoadedCount() throws SnmpStatusException {
        return new Long(getClassLoadingMXBean().getLoadedClassCount());
    }

}
