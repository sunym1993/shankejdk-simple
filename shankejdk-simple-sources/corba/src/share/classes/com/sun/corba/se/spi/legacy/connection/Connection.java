/*
 * Copyright (c) 2000, 2003, Oracle and/or its affiliates. All rights reserved.
 */

package com.sun.corba.se.spi.legacy.connection;

/**
 * This interface represents the connection on which a request is made.
 */

public interface Connection
{
    public java.net.Socket getSocket();
}
