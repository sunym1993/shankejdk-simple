/*
 * Copyright (c) 1994, 2011, Oracle and/or its affiliates. All rights reserved.
 */

/*
 *      Implementation of class Throwable
 *
 *      former classruntime.c, Wed Jun 26 18:43:20 1991
 */

#include <stdio.h>
#include <signal.h>

#include "jni.h"
#include "jvm.h"

#include "java_lang_Throwable.h"

/*
 * Fill in the current stack trace in this exception.  This is
 * usually called automatically when the exception is created but it
 * may also be called explicitly by the user.  This routine returns
 * `this' so you can write 'throw e.fillInStackTrace();'
 */
JNIEXPORT jobject JNICALL
Java_java_lang_Throwable_fillInStackTrace(JNIEnv *env, jobject throwable, int dummy)
{
    JVM_FillInStackTrace(env, throwable);
    return throwable;
}

JNIEXPORT jint JNICALL
Java_java_lang_Throwable_getStackTraceDepth(JNIEnv *env, jobject throwable)
{
    return JVM_GetStackTraceDepth(env, throwable);
}

JNIEXPORT jobject JNICALL
Java_java_lang_Throwable_getStackTraceElement(JNIEnv *env,
                                              jobject throwable, jint index)
{
    return JVM_GetStackTraceElement(env, throwable, index);
}
