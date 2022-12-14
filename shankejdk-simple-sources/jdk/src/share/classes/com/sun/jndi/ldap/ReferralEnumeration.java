/*
 * Copyright (c) 1999, 2011, Oracle and/or its affiliates. All rights reserved.
 */

package com.sun.jndi.ldap;

import javax.naming.NamingEnumeration;

interface ReferralEnumeration<T> extends NamingEnumeration<T> {
    void appendUnprocessedReferrals(LdapReferralException ex);
}
