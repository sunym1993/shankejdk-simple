/*
 * Copyright (c) 2011, 2014, Oracle and/or its affiliates. All rights reserved.
 */

#import <Cocoa/Cocoa.h>
#include <objc/objc-runtime.h>

#import "sun_lwawt_macosx_CInputMethod.h"
#import "sun_lwawt_macosx_CInputMethodDescriptor.h"
#import "ThreadUtilities.h"
#import "AWTView.h"

#import <JavaNativeFoundation/JavaNativeFoundation.h>
#import <JavaRuntimeSupport/JavaRuntimeSupport.h>

#define JAVA_LIST @"JAVA_LIST"
#define CURRENT_KB_DESCRIPTION @"CURRENT_KB_DESCRIPTION"

static JNF_CLASS_CACHE(jc_localeClass, "java/util/Locale");
static JNF_CTOR_CACHE(jm_localeCons, jc_localeClass, "(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)V");
static JNF_CLASS_CACHE(jc_arrayListClass, "java/util/ArrayList");
static JNF_CTOR_CACHE(jm_arrayListCons, jc_arrayListClass, "()V");
static JNF_MEMBER_CACHE(jm_listAdd, jc_arrayListClass, "add", "(Ljava/lang/Object;)Z");
static JNF_MEMBER_CACHE(jm_listContains, jc_arrayListClass, "contains", "(Ljava/lang/Object;)Z");



//
// NOTE: This returns a JNI Local Ref. Any code that calls must call DeleteLocalRef with the return value.
//
static jobject CreateLocaleObjectFromNSString(JNIEnv *env, NSString *name)
{
    // Break apart the string into its components.
    // First, duplicate the NSString into a C string, since we're going to modify it.
    char * language = strdup([name UTF8String]);
    char * country;
    char * variant;

    // Convert _ to NULL -- this gives us three null terminated strings in place.
    for (country = language; *country != '_' && *country != '\0'; country++);
    if (*country == '_') {
        *country++ = '\0';
        for (variant = country; *variant != '_' && *variant != '\0'; variant++);
        if (*variant == '_') {
            *variant++ = '\0';
        }
    } else {
        variant = country;
    }

    // Create the java.util.Locale object
    jobject localeObj = NULL;
    jobject langObj = (*env)->NewStringUTF(env, language);
    if (langObj != NULL) {
        jobject ctryObj = (*env)->NewStringUTF(env, country);
        if(ctryObj != NULL) {
            jobject vrntObj = (*env)->NewStringUTF(env, variant);
            if (vrntObj != NULL) {
                localeObj = JNFNewObject(env, jm_localeCons,langObj, ctryObj,
                                         vrntObj);
                (*env)->DeleteLocalRef(env, vrntObj);
            }
            (*env)->DeleteLocalRef(env, ctryObj);
        }
        (*env)->DeleteLocalRef(env, langObj);
    }
    // Clean up and return.
    free(language);
    return localeObj;
}

static id inputMethodController = nil;

static void initializeInputMethodController() {
    static BOOL checkedJRSInputMethodController = NO;
    if (!checkedJRSInputMethodController && (inputMethodController == nil)) {
        id jrsInputMethodController = objc_lookUpClass("JRSInputMethodController");
        if (jrsInputMethodController != nil) {
            inputMethodController = [jrsInputMethodController performSelector:@selector(controller)];
        }
        checkedJRSInputMethodController = YES;
    }
}


@interface CInputMethod : NSObject {}
@end

@implementation CInputMethod

+ (void) setKeyboardLayout:(NSString *)theLocale
{
    AWT_ASSERT_APPKIT_THREAD;
    if (!inputMethodController) return;

    [inputMethodController performSelector:@selector(setCurrentInputMethodForLocale) withObject:theLocale];
}

+ (void) _nativeNotifyPeerWithView:(AWTView *)view inputMethod:(JNFJObjectWrapper *) inputMethod {
    AWT_ASSERT_APPKIT_THREAD;

    if (!view) return;
    if (!inputMethod) return;

    [view setInputMethod:[inputMethod jObject]];
}

+ (void) _nativeEndComposition:(AWTView *)view {
    if (view == nil) return;

    [view abandonInput];
}


@end

/*
 * Class:     sun_lwawt_macosx_CInputMethod
 * Method:    nativeInit
 * Signature: ();
 */
JNIEXPORT void JNICALL Java_sun_lwawt_macosx_CInputMethod_nativeInit
(JNIEnv *env, jclass klass)
{
    initializeInputMethodController();
}

/*
 * Class:     sun_lwawt_macosx_CInputMethod
 * Method:    nativeGetCurrentInputMethodInfo
 * Signature: ()Ljava/lang/String;
 */
JNIEXPORT jobject JNICALL Java_sun_lwawt_macosx_CInputMethod_nativeGetCurrentInputMethodInfo
(JNIEnv *env, jclass klass)
{
    if (!inputMethodController) return NULL;
    jobject returnValue = 0;
    __block NSString *keyboardInfo = NULL;
JNF_COCOA_ENTER(env);

    [ThreadUtilities performOnMainThreadWaiting:YES block:^(){
        keyboardInfo = [inputMethodController performSelector:@selector(currentInputMethodName)];
        [keyboardInfo retain];
    }];

    if (keyboardInfo == nil) return NULL;
    returnValue = JNFNSToJavaString(env, keyboardInfo);
    [keyboardInfo release];

JNF_COCOA_EXIT(env);
    return returnValue;
}

/*
 * Class:     sun_lwawt_macosx_CInputMethod
 * Method:    nativeActivate
 * Signature: (JLsun/lwawt/macosx/CInputMethod;)V
 */
JNIEXPORT void JNICALL Java_sun_lwawt_macosx_CInputMethod_nativeNotifyPeer
(JNIEnv *env, jobject this, jlong nativePeer, jobject inputMethod)
{
JNF_COCOA_ENTER(env);
    AWTView *view = (AWTView *)jlong_to_ptr(nativePeer);
    JNFJObjectWrapper *inputMethodWrapper = [[JNFJObjectWrapper alloc] initWithJObject:inputMethod withEnv:env];
    [ThreadUtilities performOnMainThreadWaiting:NO block:^(){
        [CInputMethod _nativeNotifyPeerWithView:view inputMethod:inputMethodWrapper];
    }];

JNF_COCOA_EXIT(env);

}

/*
 * Class:     sun_lwawt_macosx_CInputMethod
 * Method:    nativeEndComposition
 * Signature: (J)V
 */
JNIEXPORT void JNICALL Java_sun_lwawt_macosx_CInputMethod_nativeEndComposition
(JNIEnv *env, jobject this, jlong nativePeer)
{
JNF_COCOA_ENTER(env);
   AWTView *view = (AWTView *)jlong_to_ptr(nativePeer);

   [ThreadUtilities performOnMainThreadWaiting:NO block:^(){
        [CInputMethod _nativeEndComposition:view];
    }];

JNF_COCOA_EXIT(env);
}

/*
 * Class:     sun_lwawt_macosx_CInputMethod
 * Method:    getNativeLocale
 * Signature: ()Ljava/util/Locale;
 */
JNIEXPORT jobject JNICALL Java_sun_lwawt_macosx_CInputMethod_getNativeLocale
(JNIEnv *env, jobject this)
{
    if (!inputMethodController) return NULL;
    jobject returnValue = 0;
    __block NSString *isoAbbreviation;
JNF_COCOA_ENTER(env);

    [ThreadUtilities performOnMainThreadWaiting:YES block:^(){
        isoAbbreviation = (NSString *) [inputMethodController performSelector:@selector(currentInputMethodLocale)];
        [isoAbbreviation retain];
    }];

    if (isoAbbreviation == nil) return NULL;

    static NSString *sLastKeyboardStr = nil;
    static jobject sLastKeyboardLocaleObj = NULL;

    if (![isoAbbreviation isEqualTo:sLastKeyboardStr]) {
        [sLastKeyboardStr release];
        sLastKeyboardStr = [isoAbbreviation retain];
        jobject localObj = CreateLocaleObjectFromNSString(env, isoAbbreviation);
        [isoAbbreviation release];

        if (sLastKeyboardLocaleObj) {
            JNFDeleteGlobalRef(env, sLastKeyboardLocaleObj);
            sLastKeyboardLocaleObj = NULL;
        }
        if (localObj != NULL) {
            sLastKeyboardLocaleObj = JNFNewGlobalRef(env, localObj);
            (*env)->DeleteLocalRef(env, localObj);
        }
    }

    returnValue = sLastKeyboardLocaleObj;

JNF_COCOA_EXIT(env);
    return returnValue;
}


/*
 * Class:     sun_lwawt_macosx_CInputMethod
 * Method:    setNativeLocale
 * Signature: (Ljava/lang/String;Z)Z
 */
JNIEXPORT jboolean JNICALL Java_sun_lwawt_macosx_CInputMethod_setNativeLocale
(JNIEnv *env, jobject this, jstring locale, jboolean isActivating)
{
JNF_COCOA_ENTER(env);
    NSString *localeStr = JNFJavaToNSString(env, locale);
    [localeStr retain];

    [ThreadUtilities performOnMainThreadWaiting:YES block:^(){
        [CInputMethod setKeyboardLayout:localeStr];
    }];

    [localeStr release];
JNF_COCOA_EXIT(env);
    return JNI_TRUE;
}

/*
 * Class:     sun_lwawt_macosx_CInputMethodDescriptor
 * Method:    nativeInit
 * Signature: ();
 */
JNIEXPORT void JNICALL Java_sun_lwawt_macosx_CInputMethodDescriptor_nativeInit
(JNIEnv *env, jclass klass)
{
    initializeInputMethodController();
}

/*
 * Class:     sun_lwawt_macosx_CInputMethodDescriptor
 * Method:    nativeGetAvailableLocales
 * Signature: ()[Ljava/util/Locale;
     */
JNIEXPORT jobject JNICALL Java_sun_lwawt_macosx_CInputMethodDescriptor_nativeGetAvailableLocales
(JNIEnv *env, jclass klass)
{
    if (!inputMethodController) return NULL;
    jobject returnValue = 0;

    __block NSArray *selectableArray = nil;
JNF_COCOA_ENTER(env);

    [ThreadUtilities performOnMainThreadWaiting:YES block:^(){
        selectableArray = (NSArray *)[inputMethodController performSelector:@selector(availableInputMethodLocales)];
        [selectableArray retain];
    }];

    if (selectableArray == nil) return NULL;

     // Create an ArrayList to return back to the caller.
    returnValue = JNFNewObject(env, jm_arrayListCons);

    for(NSString *locale in selectableArray) {
        jobject localeObj = CreateLocaleObjectFromNSString(env, locale);
        if (localeObj == NULL) {
            break;
        }

        if (JNFCallBooleanMethod(env, returnValue, jm_listContains, localeObj) == JNI_FALSE) {
            JNFCallBooleanMethod(env, returnValue, jm_listAdd, localeObj);
        }

        (*env)->DeleteLocalRef(env, localeObj);
    }
    [selectableArray release];
JNF_COCOA_EXIT(env);
    return returnValue;
}

