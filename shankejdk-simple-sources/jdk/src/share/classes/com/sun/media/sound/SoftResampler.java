/*
 * Copyright (c) 2007, Oracle and/or its affiliates. All rights reserved.
 */
package com.sun.media.sound;

/**
 * Basic resampler interface.
 *
 * @author Karl Helgason
 */
public interface SoftResampler {

    public SoftResamplerStreamer openStreamer();
}
