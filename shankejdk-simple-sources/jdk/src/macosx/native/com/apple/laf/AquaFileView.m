/*
 * Copyright (c) 2011, 2014, Oracle and/or its affiliates. All rights reserved.
 */


#include <jni_util.h>

#import "com_apple_laf_AquaFileView.h"

#import <sys/param.h> // for MAXPATHLEN
#import <CoreFoundation/CoreFoundation.h>
#import <JavaNativeFoundation/JavaNativeFoundation.h>

/*
 * Class:     com_apple_laf_AquaFileView
 * Method:    getNativePathToRunningJDKBundle
 * Signature: ()Ljava/lang/String;
 */
// TODO: Un-comment this out
/*JNIEXPORT jstring JNICALL Java_com_apple_laf_AquaFileView_getNativePathToRunningJDKBundle
(JNIEnv *env, jclass clazz)
{
    jstring returnValue = NULL;
JNF_COCOA_ENTER(env);

    returnValue = JNFNSToJavaString(env, getRunningJavaBundle());

JNF_COCOA_EXIT(env);
    return returnValue;
}*/

/*
 * Class:     com_apple_laf_AquaFileView
 * Method:    getNativePathToSharedJDKBundle
 * Signature: ()Ljava/lang/String;
 */
JNIEXPORT jstring JNICALL Java_com_apple_laf_AquaFileView_getNativePathToSharedJDKBundle
(JNIEnv *env, jclass clazz)
{
    jstring returnValue = NULL;
JNF_COCOA_ENTER(env);

    returnValue = JNFNSToJavaString(env, [[NSBundle bundleWithIdentifier:@"com.apple.JavaVM"] bundlePath]);

JNF_COCOA_EXIT(env);
    return returnValue;
}

/*
 * Class:     com_apple_laf_AquaFileView
 * Method:    getNativeMachineName
 * Signature: ()Ljava/lang/String;
 */
JNIEXPORT jstring JNICALL Java_com_apple_laf_AquaFileView_getNativeMachineName
(JNIEnv *env, jclass clazz)
{
    jstring returnValue = NULL;
JNF_COCOA_ENTER(env);

    CFStringRef machineName = CSCopyMachineName();
    returnValue = JNFNSToJavaString(env, (NSString*)machineName);

    if (machineName != NULL) {
        CFRelease(machineName);
    }

JNF_COCOA_EXIT(env);
    return returnValue;
}

/*
 * Class:     com_apple_laf_AquaFileView
 * Method:    getNativeLSInfo
 * Signature: ([BZ)I
 */
JNIEXPORT jint JNICALL Java_com_apple_laf_AquaFileView_getNativeLSInfo
(JNIEnv *env, jclass clazz, jbyteArray absolutePath, jboolean isDir)
{
    jint returnValue = com_apple_laf_AquaFileView_UNINITALIZED_LS_INFO;
JNF_COCOA_ENTER(env);

    jbyte *byteArray = (*env)->GetByteArrayElements(env, absolutePath, NULL);
    CHECK_NULL_RETURN(byteArray, returnValue);
    jsize length = (*env)->GetArrayLength(env, absolutePath);

    // Can't assume that byteArray is NULL terminated and FSPathMakeRef doesn't
    // let us specify a length.
    UInt8 arrayCopy[length + 1];
    jsize i;
    for (i = 0; i < length; i++) {
        arrayCopy[i] = (UInt8)byteArray[i];
    }
    arrayCopy[length] = '\0';
    (*env)->ReleaseByteArrayElements(env, absolutePath, byteArray, JNI_ABORT);

    Boolean isDirectory = (isDir == JNI_TRUE ? true : false);
    FSRef ref;
    OSErr err = FSPathMakeRef((const UInt8 *)&arrayCopy, &ref, &isDirectory);
    if (err == noErr) {
        LSItemInfoRecord itemInfo;
        err = LSCopyItemInfoForRef(&ref, kLSRequestBasicFlagsOnly, &itemInfo);

        if (err == noErr) {
            returnValue = itemInfo.flags;
        }
    }

JNF_COCOA_EXIT(env);
    return returnValue;
}

/*
 * Class:     com_apple_laf_AquaFileView
 * Method:    getNativeDisplayName
 * Signature: ([BZ)Ljava/lang/String;
 */
JNIEXPORT jstring JNICALL Java_com_apple_laf_AquaFileView_getNativeDisplayName
(JNIEnv *env, jclass clazz, jbyteArray absolutePath, jboolean isDir)
{
    jstring returnValue = NULL;
JNF_COCOA_ENTER(env);

    jbyte *byteArray = (*env)->GetByteArrayElements(env, absolutePath, NULL);
    CHECK_NULL_RETURN(byteArray, returnValue);
    jsize length = (*env)->GetArrayLength(env, absolutePath);

    // Can't assume that byteArray is NULL terminated and FSPathMakeRef doesn't
    // let us specify a length.
    UInt8 arrayCopy[length + 1];
    jsize i;
    for (i = 0; i < length; i++) {
        arrayCopy[i] = (UInt8)byteArray[i];
    }
    arrayCopy[length] = '\0';
    (*env)->ReleaseByteArrayElements(env, absolutePath, byteArray, JNI_ABORT);

    Boolean isDirectory = (isDir == JNI_TRUE ? true : false);
    FSRef ref;

    OSErr theErr = FSPathMakeRefWithOptions((const UInt8 *)&arrayCopy,
                                            kFSPathMakeRefDoNotFollowLeafSymlink,
                                            &ref, &isDirectory);
    if (theErr == noErr) {
        CFStringRef displayName = NULL;

        theErr = LSCopyDisplayNameForRef(&ref, &displayName);

        if (theErr == noErr) {
            CFMutableStringRef mutableDisplayName = CFStringCreateMutableCopy(NULL, 0, displayName);
            CFStringNormalize(mutableDisplayName, kCFStringNormalizationFormC);
            returnValue = JNFNSToJavaString(env, (NSString *)mutableDisplayName);
            CFRelease(mutableDisplayName);
        }

        if (displayName != NULL) {
            CFRelease(displayName);
        }
    }

JNF_COCOA_EXIT(env);
    return returnValue;
}

/*
 * Class:     com_apple_laf_AquaFileView
 * Method:    getNativePathForResolvedAlias
 * Signature: ([BZ)Ljava/lang/String;
 */
JNIEXPORT jstring JNICALL Java_com_apple_laf_AquaFileView_getNativePathForResolvedAlias
(JNIEnv *env, jclass clazz, jbyteArray pathToAlias, jboolean isDir)
{
    jstring returnValue = NULL;
JNF_COCOA_ENTER(env);

    UInt8 pathCString[MAXPATHLEN + 1];
    size_t maxPathLen = sizeof(pathCString) - 1;

    jbyte *byteArray = (*env)->GetByteArrayElements(env, pathToAlias, NULL);
    CHECK_NULL_RETURN(byteArray, returnValue);
    jsize length = (*env)->GetArrayLength(env, pathToAlias);

    if (length > maxPathLen) {
        length = maxPathLen;
    }
    strncpy((char *)pathCString, (char *)byteArray, length);
    // make sure it's null terminated
    pathCString[length] = '\0';
    (*env)->ReleaseByteArrayElements(env, pathToAlias, byteArray, JNI_ABORT);

    Boolean isDirectory = (isDir == JNI_TRUE ? true : false);
    FSRef fileRef;
    OSErr theErr = FSPathMakeRef(pathCString, &fileRef, &isDirectory);

    Boolean ignored;
    theErr = FSResolveAliasFileWithMountFlags(&fileRef, false, &ignored,
                                              &ignored, kResolveAliasFileNoUI);
    if (theErr == noErr) {
        UInt8 resolvedPath[MAXPATHLEN];
        theErr = FSRefMakePath(&fileRef, resolvedPath, MAXPATHLEN);

        if (theErr == noErr) {
            returnValue = (*env)->NewStringUTF(env, (char *)resolvedPath);
        }
    }

JNF_COCOA_EXIT(env);
    return returnValue;
}
