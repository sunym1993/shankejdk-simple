/*
 * Copyright (c) 1999, Oracle and/or its affiliates. All rights reserved.
 */
#include <jni.h>
#include "shmemBase.h"

/*
 * Common declarations for native implementations of SharedMemoryTransport
 * and SharedMemoryConnection.
 */
#define CONNECTION_TO_ID(connection) ((jlong)connection)
#define ID_TO_CONNECTION(id)         ((SharedMemoryConnection *)id)
#define TRANSPORT_TO_ID(transport)   ((jlong)transport)
#define ID_TO_TRANSPORT(id)          ((SharedMemoryTransport *)id)

/*
 * Defined in SharedMemoryTransport.c
 */
void throwException(JNIEnv *env, char *exceptionClassName, char *message);
void throwShmemException(JNIEnv *env, char *message, jint errorCode);
