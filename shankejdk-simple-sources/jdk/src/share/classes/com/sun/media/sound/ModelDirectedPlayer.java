/*
 * Copyright (c) 2007, Oracle and/or its affiliates. All rights reserved.
 */
package com.sun.media.sound;

/**
 *  ModelDirectedPlayer is the one who is directed by ModelDirector
 *  to play ModelPerformer objects.
 *
 * @author Karl Helgason
 */
public interface ModelDirectedPlayer {

    public void play(int performerIndex, ModelConnectionBlock[] connectionBlocks);
}
