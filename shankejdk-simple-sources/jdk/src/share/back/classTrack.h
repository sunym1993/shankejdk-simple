/*
 * Copyright (c) 2001, 2004, Oracle and/or its affiliates. All rights reserved.
 */

#ifndef JDWP_CLASSTRACK_H
#define JDWP_CLASSTRACK_H

/*
 * Called after class unloads have occurred.
 * The signatures of classes which were unloaded are returned.
 */
struct bag *
classTrack_processUnloads(JNIEnv *env);

/*
 * Add a class to the prepared class hash table.
 */
void
classTrack_addPreparedClass(JNIEnv *env, jclass klass);

/*
 * Initialize class tracking.
 */
void
classTrack_initialize(JNIEnv *env);

/*
 * Activates class tracking.
 */
void
classTrack_activate(JNIEnv *env);

/*
 * Reset class tracking.
 */
void
classTrack_reset(void);

#endif
