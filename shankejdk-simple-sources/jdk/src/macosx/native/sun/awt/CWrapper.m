/*
 * Copyright (c) 2011, 2014, Oracle and/or its affiliates. All rights reserved.
 */

#import <JavaNativeFoundation/JavaNativeFoundation.h>
#import "ThreadUtilities.h"
#import "sun_lwawt_macosx_CWrapper_NSWindow.h"

/*
 * Class:     sun_lwawt_macosx_CWrapper$NSWindow
 * Method:    makeKeyAndOrderFront
 * Signature: (J)V
 */
JNIEXPORT void JNICALL
Java_sun_lwawt_macosx_CWrapper_00024NSWindow_makeKeyAndOrderFront
(JNIEnv *env, jclass cls, jlong windowPtr)
{
JNF_COCOA_ENTER(env);

    NSWindow *window = (NSWindow *)jlong_to_ptr(windowPtr);
    [ThreadUtilities performOnMainThread:@selector(makeKeyAndOrderFront:)
                                      on:window
                              withObject:nil
                           waitUntilDone:NO];

JNF_COCOA_EXIT(env);
}

/*
 * Class:     sun_lwawt_macosx_CWrapper$NSWindow
 * Method:    makeKeyWindow
 * Signature: (J)V
 */
JNIEXPORT void JNICALL
Java_sun_lwawt_macosx_CWrapper_00024NSWindow_makeKeyWindow
(JNIEnv *env, jclass cls, jlong windowPtr)
{
JNF_COCOA_ENTER(env);

    NSWindow *window = (NSWindow *)jlong_to_ptr(windowPtr);
    [ThreadUtilities performOnMainThread:@selector(makeKeyWindow)
                                      on:window
                              withObject:nil
                           waitUntilDone:NO];

JNF_COCOA_EXIT(env);
}

/*
 * Class:     sun_lwawt_macosx_CWrapper$NSWindow
 * Method:    makeMainWindow
 * Signature: (J)V
 */
JNIEXPORT void JNICALL
Java_sun_lwawt_macosx_CWrapper_00024NSWindow_makeMainWindow
(JNIEnv *env, jclass cls, jlong windowPtr)
{
JNF_COCOA_ENTER(env);

    NSWindow *window = (NSWindow *)jlong_to_ptr(windowPtr);
    [ThreadUtilities performOnMainThread:@selector(makeMainWindow)
                                      on:window
                              withObject:nil
                           waitUntilDone:NO];

JNF_COCOA_EXIT(env);
}

/*
 * Class:     sun_lwawt_macosx_CWrapper$NSWindow
 * Method:    canBecomeMainWindow
 * Signature: (J)V
 */
JNIEXPORT jboolean JNICALL
Java_sun_lwawt_macosx_CWrapper_00024NSWindow_canBecomeMainWindow
(JNIEnv *env, jclass cls, jlong windowPtr)
{
    __block jboolean canBecomeMainWindow = JNI_FALSE;

JNF_COCOA_ENTER(env);

    NSWindow *window = (NSWindow *)jlong_to_ptr(windowPtr);
    [ThreadUtilities performOnMainThreadWaiting:YES block:^(){
        canBecomeMainWindow = [window canBecomeMainWindow];
    }];

JNF_COCOA_EXIT(env);

    return canBecomeMainWindow;
}

/*
 * Class:     sun_lwawt_macosx_CWrapper$NSWindow
 * Method:    isKeyWindow
 * Signature: (J)Z
 */
JNIEXPORT jboolean JNICALL
Java_sun_lwawt_macosx_CWrapper_00024NSWindow_isKeyWindow
(JNIEnv *env, jclass cls, jlong windowPtr)
{
    __block jboolean isKeyWindow = JNI_FALSE;

JNF_COCOA_ENTER(env);

    NSWindow *window = (NSWindow *)jlong_to_ptr(windowPtr);
    [ThreadUtilities performOnMainThreadWaiting:YES block:^(){
        isKeyWindow = [window isKeyWindow];
    }];

JNF_COCOA_EXIT(env);

    return isKeyWindow;
}

/*
 * Class:     sun_lwawt_macosx_CWrapper$NSWindow
 * Method:    orderFront
 * Signature: (J)V
 */
JNIEXPORT void JNICALL
Java_sun_lwawt_macosx_CWrapper_00024NSWindow_orderFront
(JNIEnv *env, jclass cls, jlong windowPtr)
{
JNF_COCOA_ENTER(env);

    NSWindow *window = (NSWindow *)jlong_to_ptr(windowPtr);
    [ThreadUtilities performOnMainThread:@selector(orderFront:)
                                      on:window
                              withObject:window
                           waitUntilDone:NO];

JNF_COCOA_EXIT(env);
}

/*
 * Class:     sun_lwawt_macosx_CWrapper$NSWindow
 * Method:    orderOut
 * Signature: (J)V
 */
JNIEXPORT void JNICALL
Java_sun_lwawt_macosx_CWrapper_00024NSWindow_orderOut
(JNIEnv *env, jclass cls, jlong windowPtr)
{
JNF_COCOA_ENTER(env);

    NSWindow *window = (NSWindow *)jlong_to_ptr(windowPtr);
    [ThreadUtilities performOnMainThread:@selector(orderOut:)
                                      on:window
                              withObject:window
                           waitUntilDone:NO];

JNF_COCOA_EXIT(env);
}

/*
 * Class:     sun_lwawt_macosx_CWrapper$NSWindow
 * Method:    close
 * Signature: (J)V
 */
JNIEXPORT void JNICALL
Java_sun_lwawt_macosx_CWrapper_00024NSWindow_close
        (JNIEnv *env, jclass cls, jlong windowPtr)
{
JNF_COCOA_ENTER(env);
    NSWindow *window = (NSWindow *)jlong_to_ptr(windowPtr);
    [ThreadUtilities performOnMainThreadWaiting:NO block:^(){
        [window close];
    }];
JNF_COCOA_EXIT(env);
}

/*
 * Class:     sun_lwawt_macosx_CWrapper$NSWindow
 * Method:    orderFrontRegardless
 * Signature: (J)V
 */
JNIEXPORT void JNICALL
Java_sun_lwawt_macosx_CWrapper_00024NSWindow_orderFrontRegardless
(JNIEnv *env, jclass cls, jlong windowPtr)
{
JNF_COCOA_ENTER(env);

    NSWindow *window = (NSWindow *)jlong_to_ptr(windowPtr);
    [ThreadUtilities performOnMainThread:@selector(orderFrontRegardless)
                                      on:window
                              withObject:nil
                           waitUntilDone:NO];

JNF_COCOA_EXIT(env);
}

/*
 * Class:     sun_lwawt_macosx_CWrapper$NSWindow
 * Method:    orderWindow
 * Signature: (JIJ)V
 */
JNIEXPORT void JNICALL
Java_sun_lwawt_macosx_CWrapper_00024NSWindow_orderWindow
(JNIEnv *env, jclass cls, jlong windowPtr, jint order, jlong relativeToPtr)
{
JNF_COCOA_ENTER(env);

    NSWindow *window = (NSWindow *)jlong_to_ptr(windowPtr);
    NSWindow *relativeTo = (NSWindow *)jlong_to_ptr(relativeToPtr);
    [ThreadUtilities performOnMainThreadWaiting:NO block:^(){
        [window orderWindow:(NSWindowOrderingMode)order relativeTo:[relativeTo windowNumber]];
    }];

JNF_COCOA_EXIT(env);
}

// Used for CWrapper.NSWindow.setLevel() (and level() which isn't implemented yet)
static NSInteger LEVELS[sun_lwawt_macosx_CWrapper_NSWindow_MAX_WINDOW_LEVELS];
static void initLevels()
{
    static dispatch_once_t pred;

    dispatch_once(&pred, ^{
        LEVELS[sun_lwawt_macosx_CWrapper_NSWindow_NSNormalWindowLevel] = NSNormalWindowLevel;
        LEVELS[sun_lwawt_macosx_CWrapper_NSWindow_NSFloatingWindowLevel] = NSFloatingWindowLevel;
        LEVELS[sun_lwawt_macosx_CWrapper_NSWindow_NSPopUpMenuWindowLevel] = NSPopUpMenuWindowLevel;
    });
}

/*
 * Class:     sun_lwawt_macosx_CWrapper$NSWindow
 * Method:    setLevel
 * Signature: (JI)V
 */
JNIEXPORT void JNICALL
Java_sun_lwawt_macosx_CWrapper_00024NSWindow_setLevel
(JNIEnv *env, jclass cls, jlong windowPtr, jint level)
{
JNF_COCOA_ENTER(env);

    if (level >= 0 && level < sun_lwawt_macosx_CWrapper_NSWindow_MAX_WINDOW_LEVELS) {
        initLevels();

        NSWindow *window = (NSWindow *)jlong_to_ptr(windowPtr);
        [ThreadUtilities performOnMainThreadWaiting:NO block:^(){
            [window setLevel: LEVELS[level]];
        }];
    } else {
        [JNFException raise:env as:kIllegalArgumentException reason:"unknown level"];
    }

JNF_COCOA_EXIT(env);
}

/*
 * Class:     sun_lwawt_macosx_CWrapper$NSWindow
 * Method:    addChildWindow
 * Signature: (JJI)V
 */
JNIEXPORT void JNICALL
Java_sun_lwawt_macosx_CWrapper_00024NSWindow_addChildWindow
(JNIEnv *env, jclass cls, jlong parentPtr, jlong childPtr, jint order)
{
JNF_COCOA_ENTER(env);

    NSWindow *parent = (NSWindow *)jlong_to_ptr(parentPtr);
    NSWindow *child = (NSWindow *)jlong_to_ptr(childPtr);
    [ThreadUtilities performOnMainThreadWaiting:NO block:^(){
        [parent addChildWindow:child ordered:order];
    }];

JNF_COCOA_EXIT(env);
}

/*
 * Class:     sun_lwawt_macosx_CWrapper$NSWindow
 * Method:    removeChildWindow
 * Signature: (JJ)V
 */
JNIEXPORT void JNICALL
Java_sun_lwawt_macosx_CWrapper_00024NSWindow_removeChildWindow
(JNIEnv *env, jclass cls, jlong parentPtr, jlong childPtr)
{
JNF_COCOA_ENTER(env);

    NSWindow *parent = (NSWindow *)jlong_to_ptr(parentPtr);
    NSWindow *child = (NSWindow *)jlong_to_ptr(childPtr);
    [ThreadUtilities performOnMainThread:@selector(removeChildWindow:)
                                      on:parent
                              withObject:child
                           waitUntilDone:NO];

JNF_COCOA_EXIT(env);
}

/*
 * Class:     sun_lwawt_macosx_CWrapper$NSWindow
 * Method:    setAlphaValue
 * Signature: (JF)V
 */
JNIEXPORT void JNICALL
Java_sun_lwawt_macosx_CWrapper_00024NSWindow_setAlphaValue
(JNIEnv *env, jclass cls, jlong windowPtr, jfloat alpha)
{
JNF_COCOA_ENTER(env);

    NSWindow *window = (NSWindow *)jlong_to_ptr(windowPtr);
    [ThreadUtilities performOnMainThreadWaiting:NO block:^(){
        [window setAlphaValue:(CGFloat)alpha];
    }];

JNF_COCOA_EXIT(env);
}

/*
 * Class:     sun_lwawt_macosx_CWrapper$NSWindow
 * Method:    setOpaque
 * Signature: (Z)V
 */
JNIEXPORT void JNICALL
Java_sun_lwawt_macosx_CWrapper_00024NSWindow_setOpaque
(JNIEnv *env, jclass cls, jlong windowPtr, jboolean opaque)
{
JNF_COCOA_ENTER(env);

    NSWindow *window = (NSWindow *)jlong_to_ptr(windowPtr);
    [ThreadUtilities performOnMainThreadWaiting:NO block:^(){
        [window setOpaque:(BOOL)opaque];
    }];

JNF_COCOA_EXIT(env);
}

/*
 * Class:     sun_lwawt_macosx_CWrapper$NSWindow
 * Method:    setBackgroundColor
 * Signature: (J)V
 */
JNIEXPORT void JNICALL
Java_sun_lwawt_macosx_CWrapper_00024NSWindow_setBackgroundColor
(JNIEnv *env, jclass cls, jlong windowPtr, jint rgb)
{
JNF_COCOA_ENTER(env);

    NSWindow *window = (NSWindow *)jlong_to_ptr(windowPtr);
    CGFloat alpha = (((rgb >> 24) & 0xff) / 255.0);
    CGFloat red   = (((rgb >> 16) & 0xff) / 255.0);
    CGFloat green = (((rgb >>  8) & 0xff) / 255.0);
    CGFloat blue  = (((rgb >>  0) & 0xff) / 255.0);
    NSColor *color = [NSColor colorWithCalibratedRed:red green:green blue:blue
                                               alpha:alpha];
    [ThreadUtilities performOnMainThreadWaiting:NO block:^(){
        [window setBackgroundColor:color];
    }];

JNF_COCOA_EXIT(env);
}

/*
 * Class:     sun_lwawt_macosx_CWrapper$NSWindow
 * Method:    miniaturize
 * Signature: (J)V
 */
JNIEXPORT void JNICALL
Java_sun_lwawt_macosx_CWrapper_00024NSWindow_miniaturize
(JNIEnv *env, jclass cls, jlong windowPtr)
{
JNF_COCOA_ENTER(env);

    NSWindow *window = (NSWindow *)jlong_to_ptr(windowPtr);
    [ThreadUtilities performOnMainThread:@selector(miniaturize:)
                                      on:window
                              withObject:nil
                           waitUntilDone:NO];

JNF_COCOA_EXIT(env);
}

/*
 * Class:     sun_lwawt_macosx_CWrapper$NSWindow
 * Method:    deminiaturize
 * Signature: (J)V
 */
JNIEXPORT void JNICALL
Java_sun_lwawt_macosx_CWrapper_00024NSWindow_deminiaturize
(JNIEnv *env, jclass cls, jlong windowPtr)
{
JNF_COCOA_ENTER(env);

    NSWindow *window = (NSWindow *)jlong_to_ptr(windowPtr);
    [ThreadUtilities performOnMainThread:@selector(deminiaturize:)
                                      on:window
                              withObject:nil
                           waitUntilDone:NO];

JNF_COCOA_EXIT(env);
}

/*
 * Class:     sun_lwawt_macosx_CWrapper$NSWindow
 * Method:    isZoomed
 * Signature: (J)Z
 */
JNIEXPORT jboolean JNICALL
Java_sun_lwawt_macosx_CWrapper_00024NSWindow_isZoomed
(JNIEnv *env, jclass cls, jlong windowPtr)
{
    __block jboolean isZoomed = JNI_FALSE;
    
JNF_COCOA_ENTER(env);
    
    NSWindow *window = (NSWindow *)jlong_to_ptr(windowPtr);
    [ThreadUtilities performOnMainThreadWaiting:YES block:^(){
        isZoomed = [window isZoomed];
    }];
    
JNF_COCOA_EXIT(env);
    
    return isZoomed;
}

/*
 * Class:     sun_lwawt_macosx_CWrapper$NSWindow
 * Method:    zoom
 * Signature: (J)V
 */
JNIEXPORT void JNICALL
Java_sun_lwawt_macosx_CWrapper_00024NSWindow_zoom
(JNIEnv *env, jclass cls, jlong windowPtr)
{
JNF_COCOA_ENTER(env);

    NSWindow *window = (NSWindow *)jlong_to_ptr(windowPtr);
    [ThreadUtilities performOnMainThread:@selector(zoom:)
                                      on:window
                              withObject:nil
                           waitUntilDone:NO];

JNF_COCOA_EXIT(env);
}

/*
 * Class:     sun_lwawt_macosx_CWrapper$NSWindow
 * Method:    makeFirstResponder
 * Signature: (JJ)V
 */
JNIEXPORT void JNICALL
Java_sun_lwawt_macosx_CWrapper_00024NSWindow_makeFirstResponder
(JNIEnv *env, jclass cls, jlong windowPtr, jlong responderPtr)
{
JNF_COCOA_ENTER(env);

    NSWindow *window = (NSWindow *)jlong_to_ptr(windowPtr);
    NSResponder *responder = (NSResponder *)jlong_to_ptr(responderPtr);
    [ThreadUtilities performOnMainThread:@selector(makeFirstResponder:)
                                      on:window
                              withObject:responder
                           waitUntilDone:NO];

JNF_COCOA_EXIT(env);
}

/*
 * Class:     sun_lwawt_macosx_CWrapper$NSView
 * Method:    addSubview
 * Signature: (JJ)V
 */
JNIEXPORT void JNICALL
Java_sun_lwawt_macosx_CWrapper_00024NSView_addSubview
(JNIEnv *env, jclass cls, jlong viewPtr, jlong subviewPtr)
{
JNF_COCOA_ENTER(env);

    NSView *view = (NSView *)jlong_to_ptr(viewPtr);
    NSView *subview = (NSView *)jlong_to_ptr(subviewPtr);
    [ThreadUtilities performOnMainThreadWaiting:YES block:^(){
        [view addSubview:subview];
    }];

JNF_COCOA_EXIT(env);
}

/*
 * Class:     sun_lwawt_macosx_CWrapper$NSView
 * Method:    removeFromSuperview
 * Signature: (J)V
 */
JNIEXPORT void JNICALL
Java_sun_lwawt_macosx_CWrapper_00024NSView_removeFromSuperview
(JNIEnv *env, jclass cls, jlong viewPtr)
{
JNF_COCOA_ENTER(env);

    NSView *view = (NSView *)jlong_to_ptr(viewPtr);
    [ThreadUtilities performOnMainThread:@selector(removeFromSuperview)
                                      on:view
                              withObject:nil
                           waitUntilDone:NO];

JNF_COCOA_EXIT(env);
}

/*
 * Class:     sun_lwawt_macosx_CWrapper$NSView
 * Method:    setFrame
 * Signature: (JIIII)V
 */
JNIEXPORT void JNICALL
Java_sun_lwawt_macosx_CWrapper_00024NSView_setFrame
(JNIEnv *env, jclass cls, jlong viewPtr, jint x, jint y, jint w, jint h)
{
JNF_COCOA_ENTER(env);

    NSView *view = (NSView *)jlong_to_ptr(viewPtr);
    [ThreadUtilities performOnMainThreadWaiting:NO block:^(){
        [view setFrame:NSMakeRect(x, y, w, h)];
    }];

JNF_COCOA_EXIT(env);
}

/*
 * Class:     sun_lwawt_macosx_CWrapper$NSView
 * Method:    window
 * Signature: (J)J
 */
JNIEXPORT jlong JNICALL
Java_sun_lwawt_macosx_CWrapper_00024NSView_window
(JNIEnv *env, jclass cls, jlong viewPtr)
{
    __block jlong windowPtr = 0L;

JNF_COCOA_ENTER(env);

    NSView *view = (NSView *)jlong_to_ptr(viewPtr);
    [ThreadUtilities performOnMainThreadWaiting:YES block:^(){
        windowPtr = ptr_to_jlong([view window]);
    }];

JNF_COCOA_EXIT(env);

    return windowPtr;
}

/*
 * Class:     sun_lwawt_macosx_CWrapper$NSView
 * Method:    setHidden
 * Signature: (JZ)V
 */
JNIEXPORT void JNICALL
Java_sun_lwawt_macosx_CWrapper_00024NSView_setHidden
(JNIEnv *env, jclass cls, jlong viewPtr, jboolean toHide)
{    
    JNF_COCOA_ENTER(env);
    
    NSView *view = (NSView *)jlong_to_ptr(viewPtr);
    [ThreadUtilities performOnMainThreadWaiting:NO block:^(){
        [view setHidden:(BOOL)toHide];
    }];
    
    JNF_COCOA_EXIT(env);
}

/*
 * Class:     sun_lwawt_macosx_CWrapper$NSView
 * Method:    setToolTip
 * Signature: (JLjava/lang/String;)V
 */
JNIEXPORT void JNICALL
Java_sun_lwawt_macosx_CWrapper_00024NSView_setToolTip
(JNIEnv *env, jclass cls, jlong viewPtr, jstring msg)
{

JNF_COCOA_ENTER(env);

    NSView *view = (NSView *)jlong_to_ptr(viewPtr);
    NSString* s = JNFJavaToNSString(env, msg); 
    [ThreadUtilities performOnMainThreadWaiting:NO block:^(){
        [view setToolTip: s];
    }];

JNF_COCOA_EXIT(env);
}
