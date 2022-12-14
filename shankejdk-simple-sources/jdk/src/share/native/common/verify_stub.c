/*
 * Copyright (c) 1999, 2003, Oracle and/or its affiliates. All rights reserved.
 */


/*
 * The real verifier now lives in libverifier.so/verifier.dll.
 *
 * This dummy exists so that HotSpot will run with the new
 * libjava.so/java.dll which is where is it accustomed to finding the
 * verifier.
 */

#include "jni.h"

struct struct_class_size_info;
typedef struct struct_class_size_info class_size_info;


JNIIMPORT jboolean
VerifyClass(JNIEnv *env, jclass cb, char *buffer, jint len);

JNIIMPORT jboolean
VerifyClassForMajorVersion(JNIEnv *env, jclass cb, char *buffer, jint len,
                           jint major_version);

JNIEXPORT jboolean
VerifyClassCodes(JNIEnv *env, jclass cb, char *buffer, jint len)
{
    return VerifyClass(env, cb, buffer, len);
}

JNIEXPORT jboolean
VerifyClassCodesForMajorVersion(JNIEnv *env, jclass cb, char *buffer,
                                jint len, jint major_version)
{
    return VerifyClassForMajorVersion(env, cb, buffer, len, major_version);
}
