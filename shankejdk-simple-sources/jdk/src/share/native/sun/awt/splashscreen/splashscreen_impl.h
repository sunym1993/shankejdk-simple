/*
 * Copyright (c) 2005, 2012, Oracle and/or its affiliates. All rights reserved.
 */

#ifndef SPLASHSCREEN_IMPL_H
#define SPLASHSCREEN_IMPL_H

#include "splashscreen_config.h"
#include "splashscreen_gfx.h"

SPLASHEXPORT int SplashLoadMemory(void *pdata, int size); /* requires preloading the file */
SPLASHEXPORT int SplashLoadFile(const char *filename);  // FIXME: range checking for SplashLoadMemory

SPLASHEXPORT void SplashInit(void);
SPLASHEXPORT void SplashClose(void);

SPLASHEXPORT void SplashSetScaleFactor(float);
SPLASHEXPORT char* SplashGetScaledImageName(const char*, const char*, float*);

SPLASHEXPORT void
SplashSetFileJarName(const char* fileName, const char* jarName);

typedef struct SplashImage
{
    rgbquad_t *bitmapBits;
    int delay;                  /* before next image display, in msec                                                       */
#if defined(WITH_WIN32)
    HRGN hRgn;
#elif defined(WITH_X11)
    XRectangle *rects;
    int numRects;
#endif
} SplashImage;

#define SPLASH_COLOR_MAP_SIZE 0x100

typedef struct Splash
{
    ImageFormat screenFormat;   /* must be preset before image decoding */
    DitherSettings dithers[3];
    ImageFormat imageFormat;
    rgbquad_t colorMap[SPLASH_COLOR_MAP_SIZE];
    int byteAlignment;          /* must be preset before image decoding */
    int maskRequired;           /* must be preset before image decoding */
    int width;                  /* in pixels */
    int height;                 /* in pixels */
    int frameCount;
    SplashImage *frames;        /* dynamically allocated array of frame descriptors */
    unsigned time;              /* in msec, origin is not important */
    rgbquad_t *overlayData;     /* overlay image data, always rgbquads */
    ImageRect overlayRect;
    ImageFormat overlayFormat;
    void *screenData;
    int screenStride;           /* stored scanline length in bytes */
    int currentFrame;           // currentFrame==-1 means image is not loaded
    int loopCount;
    int x, y;
    rgbquad_t colorIndex[SPLASH_COLOR_MAP_SIZE];
    int isVisible;
    char*       fileName;       /* stored in 16-bit unicode (jchars) */
    int         fileNameLen;
    char*       jarName;        /* stored in 16-bit unicode (jchars) */
    int         jarNameLen;
    float       scaleFactor;
#if defined(WITH_WIN32)
    BOOL isLayered;
    HWND hWnd;
    HPALETTE hPalette;
    CRITICAL_SECTION lock;
#elif defined(WITH_X11)
    int controlpipe[2];
    Display *display;
    Window window;
    Screen *screen;
    Visual *visual;
    Colormap cmap;
    pthread_mutex_t lock;
    Cursor cursor;
    XWMHints* wmHints;
#elif defined(WITH_MACOSX)
    pthread_mutex_t lock;
    int controlpipe[2];
    NSWindow * window;
#endif
} Splash;

/* various shared and/or platform dependent splash screen functions */

/*************** Platform-specific ******************/

/* To be implemented in the platform-specific native code. */


void SplashInitPlatform(Splash * splash);
void SplashCreateThread(Splash * splash);
void SplashCleanupPlatform(Splash * splash);
void SplashDonePlatform(Splash * splash);

unsigned SplashTime();
char* SplashConvertStringAlloc(const char* in, int *size);
char* SplashGetScaledImageName(const char* jarName,
                               const char* fileName, float *scaleFactor);

void SplashLock(Splash * splash);
void SplashUnlock(Splash * splash);

void SplashInitFrameShape(Splash * splash, int imageIndex);

void SplashUpdate(Splash * splash);
void SplashReconfigure(Splash * splash);
void SplashClosePlatform(Splash * splash);



/********************* Shared **********************/
Splash *SplashGetInstance();

int SplashIsStillLooping(Splash * splash);
void SplashNextFrame(Splash * splash);
void SplashStart(Splash * splash);
void SplashDone(Splash * splash);

void SplashUpdateScreenData(Splash * splash);

void SplashCleanup(Splash * splash);
void SplashSetScaleFactor(float scaleFactor);


typedef struct SplashStream {
    int (*read)(void* pStream, void* pData, int nBytes);
    int (*peek)(void* pStream);
    void (*close)(void* pStream);
    union {
        struct {
            FILE* f;
        } stdio;
        struct {
            unsigned char* pData;
            unsigned char* pDataEnd;
        } mem;
    } arg;
} SplashStream;

int SplashStreamInitFile(SplashStream * stream, const char* filename);
int SplashStreamInitMemory(SplashStream * stream, void * pData, int size);

/* image decoding */
int SplashDecodeGifStream(Splash * splash, SplashStream * stream);
int SplashDecodeJpegStream(Splash * splash, SplashStream * stream);
int SplashDecodePngStream(Splash * splash, SplashStream * stream);

/* utility functions */

int BitmapToYXBandedRectangles(ImageRect * pSrcRect, RECT_T * out);

#define SAFE_TO_ALLOC(c, sz)                                               \
    (((c) > 0) && ((sz) > 0) &&                                            \
     ((0xffffffffu / ((unsigned int)(c))) > (unsigned int)(sz)))

#define dbgprintf printf

#endif
