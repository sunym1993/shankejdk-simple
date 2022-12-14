/*
 * Copyright (c) 2003, Oracle and/or its affiliates. All rights reserved.
 */
package sun.management.snmp.jvminstr;

import java.net.InetAddress;

/**
 * Target notification.
 */
public interface NotificationTarget {
    public InetAddress getAddress();
    public int getPort();
    public String getCommunity();
}
