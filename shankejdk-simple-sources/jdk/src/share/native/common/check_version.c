/*
 * Copyright (c) 1998, Oracle and/or its affiliates. All rights reserved.
 */

#include "jni.h"
#include "jvm.h"

JNIEXPORT jint JNICALL
JNI_OnLoad(JavaVM *vm, void *reserved)
{
    jint vm_version = JVM_GetInterfaceVersion();
    if (vm_version != JVM_INTERFACE_VERSION) {
        JNIEnv *env;
        char buf[128];
        sprintf(buf, "JVM interface version mismatch: expecting %d, got %d.",
                JVM_INTERFACE_VERSION, (int)vm_version);
        (*vm)->GetEnv(vm, (void **)&env, JNI_VERSION_1_2);
        if (env) {
            (*env)->FatalError(env, buf);
        }
    }
    return JNI_VERSION_1_2;
}
