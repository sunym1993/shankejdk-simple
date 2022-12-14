/*
 * Copyright (c) 2009, 2010, Oracle and/or its affiliates. All rights reserved.
 */



package com.sun.org.glassfish.external.statistics;

/**
 * Specifies standard count measurements.
 */
public interface CountStatistic extends Statistic {
    /**
     * The count since the last reset.
     */
    long getCount();
}
