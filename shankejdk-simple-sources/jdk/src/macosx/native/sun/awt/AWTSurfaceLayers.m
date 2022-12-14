/*
 * Copyright (c) 2011, 2016, Oracle and/or its affiliates. All rights reserved.
 */

#import "AWTSurfaceLayers.h"
#import "ThreadUtilities.h"
#import "LWCToolkit.h"

#import <JavaNativeFoundation/JavaNativeFoundation.h>
#import <QuartzCore/CATransaction.h>

@implementation AWTSurfaceLayers

@synthesize windowLayer;

- (id) initWithWindowLayer:(CALayer *)aWindowLayer {
    self = [super init];
    if (self == nil) return self;

    self.windowLayer = aWindowLayer;

    return self;
}

- (void) dealloc {
    self.windowLayer = nil;
    [super dealloc];
}

- (CALayer *) layer {
    return layer;
}

- (void) setLayer:(CALayer *)newLayer {
    if (layer != newLayer) {
        if (layer != nil || newLayer == nil) {
            [layer removeFromSuperlayer];
            [layer release];
        }

        if (newLayer != nil) {
            layer = [newLayer retain];
            // REMIND: window layer -> container layer
            [windowLayer addSublayer: layer];
        }
    }
}

// Updates back buffer size of the layer if it's an OpenGL layer
// including all OpenGL sublayers
+ (void) repaintLayersRecursively:(CALayer*)aLayer {
    if ([aLayer isKindOfClass:[CAOpenGLLayer class]]) {
        [aLayer setNeedsDisplay];
    }
    for(CALayer *child in aLayer.sublayers) {
        [AWTSurfaceLayers repaintLayersRecursively: child];
    }
}

- (void) setBounds:(CGRect)rect {
    // translates values to the coordinate system of the "root" layer
    rect.origin.y = windowLayer.bounds.size.height - rect.origin.y - rect.size.height;
    [CATransaction begin];
    [CATransaction setDisableActions:YES];
    layer.frame = rect;
    [CATransaction commit];
    [AWTSurfaceLayers repaintLayersRecursively:layer];
}

@end

/*
 * Class:     sun_lwawt_macosx_CPlatformComponent
 * Method:    nativeCreateLayer
 * Signature: ()J
 */
JNIEXPORT jlong JNICALL
Java_sun_lwawt_macosx_CPlatformComponent_nativeCreateComponent
(JNIEnv *env, jobject obj, jlong windowLayerPtr)
{
  __block AWTSurfaceLayers *surfaceLayers = nil;

JNF_COCOA_ENTER(env);

    [ThreadUtilities performOnMainThreadWaiting:YES block:^(){

        CALayer *windowLayer = jlong_to_ptr(windowLayerPtr);
        surfaceLayers = [[AWTSurfaceLayers alloc] initWithWindowLayer: windowLayer];
    }];
    
JNF_COCOA_EXIT(env);

  return ptr_to_jlong(surfaceLayers);
}

/*
 * Class:     sun_lwawt_macosx_CPlatformComponent
 * Method:    nativeSetBounds
 * Signature: (JIIII)V
 */
JNIEXPORT void JNICALL Java_sun_lwawt_macosx_CPlatformComponent_nativeSetBounds
(JNIEnv *env, jclass clazz, jlong surfaceLayersPtr, jint x, jint y, jint width, jint height)
{
JNF_COCOA_ENTER(env);

  AWTSurfaceLayers *surfaceLayers = OBJC(surfaceLayersPtr);
    
  [ThreadUtilities performOnMainThreadWaiting:NO block:^(){

      CGRect rect = CGRectMake(x, y, width, height);
      [surfaceLayers setBounds: rect];
  }];

JNF_COCOA_EXIT(env);
}
