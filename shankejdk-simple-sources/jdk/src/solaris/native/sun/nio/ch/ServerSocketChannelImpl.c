/*
 * Copyright (c) 2000, 2008, Oracle and/or its affiliates. All rights reserved.
 */

#include <stdlib.h>
#include <netdb.h>
#include <sys/types.h>
#include <sys/socket.h>

#if __linux__
#include <netinet/in.h>
#endif

#if defined(__solaris__) && !defined(_SOCKLEN_T)
typedef size_t socklen_t;       /* New in SunOS 5.7, so need this for 5.6 */
#endif

#include "jni.h"
#include "jni_util.h"
#include "net_util.h"
#include "jvm.h"
#include "jlong.h"
#include "sun_nio_ch_ServerSocketChannelImpl.h"
#include "nio.h"
#include "nio_util.h"


static jfieldID fd_fdID;        /* java.io.FileDescriptor.fd */
static jclass isa_class;        /* java.net.InetSocketAddress */
static jmethodID isa_ctorID;    /*   .InetSocketAddress(InetAddress, int) */


JNIEXPORT void JNICALL
Java_sun_nio_ch_ServerSocketChannelImpl_initIDs(JNIEnv *env, jclass c)
{
    jclass cls;

    cls = (*env)->FindClass(env, "java/io/FileDescriptor");
    CHECK_NULL(cls);
    fd_fdID = (*env)->GetFieldID(env, cls, "fd", "I");
    CHECK_NULL(fd_fdID);

    cls = (*env)->FindClass(env, "java/net/InetSocketAddress");
    CHECK_NULL(cls);
    isa_class = (*env)->NewGlobalRef(env, cls);
    if (isa_class == NULL) {
        JNU_ThrowOutOfMemoryError(env, NULL);
        return;
    }
    isa_ctorID = (*env)->GetMethodID(env, cls, "<init>",
                                     "(Ljava/net/InetAddress;I)V");
    CHECK_NULL(isa_ctorID);
}

JNIEXPORT jint JNICALL
Java_sun_nio_ch_ServerSocketChannelImpl_accept0(JNIEnv *env, jobject this,
                                                jobject ssfdo, jobject newfdo,
                                                jobjectArray isaa)
{
    jint ssfd = (*env)->GetIntField(env, ssfdo, fd_fdID);
    jint newfd;
    struct sockaddr *sa;
    int alloc_len;
    jobject remote_ia = 0;
    jobject isa;
    jint remote_port = 0;

    NET_AllocSockaddr(&sa, &alloc_len);
    if (sa == NULL) {
        JNU_ThrowOutOfMemoryError(env, NULL);
        return IOS_THROWN;
    }

    /*
     * accept connection but ignore ECONNABORTED indicating that
     * a connection was eagerly accepted but was reset before
     * accept() was called.
     */
    for (;;) {
        socklen_t sa_len = alloc_len;
        newfd = accept(ssfd, sa, &sa_len);
        if (newfd >= 0) {
            break;
        }
        if (errno != ECONNABORTED) {
            break;
        }
        /* ECONNABORTED => restart accept */
    }

    if (newfd < 0) {
        free((void *)sa);
        if (errno == EAGAIN)
            return IOS_UNAVAILABLE;
        if (errno == EINTR)
            return IOS_INTERRUPTED;
        JNU_ThrowIOExceptionWithLastError(env, "Accept failed");
        return IOS_THROWN;
    }

    (*env)->SetIntField(env, newfdo, fd_fdID, newfd);
    remote_ia = NET_SockaddrToInetAddress(env, sa, (int *)&remote_port);
    free((void *)sa);
    CHECK_NULL_RETURN(remote_ia, IOS_THROWN);
    isa = (*env)->NewObject(env, isa_class, isa_ctorID, remote_ia, remote_port);
    CHECK_NULL_RETURN(isa, IOS_THROWN);
    (*env)->SetObjectArrayElement(env, isaa, 0, isa);
    return 1;
}
