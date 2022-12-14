/*
 * Copyright (c) 1997, 2010, Oracle and/or its affiliates. All rights reserved.
 */


package com.sun.org.glassfish.gmbal;

import javax.management.DynamicMBean;
import javax.management.NotificationEmitter;

/** Type returned from ManagedObjectManager createRoot and register methods.
 * Used because all Gmbal MBeans are dynamic MBeans that support attribute
 * change notification.
 *
 * @author ken
 */
public interface GmbalMBean extends DynamicMBean, NotificationEmitter {
}
