/*
 * Copyright (c) 1999, 2018, Oracle and/or its affiliates. All rights reserved.
 */

#include "jni.h"
#include "jni_util.h"
#include "jvm.h"

#include "java_lang_Shutdown.h"

JNIEXPORT void JNICALL
Java_java_lang_Shutdown_beforeHalt(JNIEnv *env, jclass ignored)
{
    JVM_BeforeHalt();
}

JNIEXPORT void JNICALL
Java_java_lang_Shutdown_halt0(JNIEnv *env, jclass ignored, jint code)
{
    JVM_Halt(code);
}


JNIEXPORT void JNICALL
Java_java_lang_Shutdown_runAllFinalizers(JNIEnv *env, jclass ignored)
{
    jclass cl;
    jmethodID mid;

    if ((cl = (*env)->FindClass(env, "java/lang/ref/Finalizer"))
        && (mid = (*env)->GetStaticMethodID(env, cl,
                                            "runAllFinalizers", "()V"))) {
        (*env)->CallStaticVoidMethod(env, cl, mid);
    }
}
