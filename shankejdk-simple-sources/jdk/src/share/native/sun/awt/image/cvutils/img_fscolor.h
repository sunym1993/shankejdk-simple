/*
 * Copyright (c) 1996, Oracle and/or its affiliates. All rights reserved.
 */

/*
 * This file contains macro definitions for the Encoding category of
 * the macros used by the generic scaleloop function.
 *
 * This implementation uses a Floyd-Steinberg error diffusion technique
 * to produce a very high quality version of an image with only an 8-bit
 * (or less) RGB colormap.  The error diffusion technique requires that
 * the input color information be delivered in a special order from the
 * top row to the bottom row and then left to right within each row, thus
 * it is only valid in cases where the ImageProducer has specified the
 * TopDownLeftRight delivery hint.  If the data is not read in that order,
 * no mathematical or memory access errors should occur, but the dithering
 * error will be spread through the pixels of the output image in an
 * unpleasant manner.
 */

#include "img_fsutil.h"

/*
 * These definitions vector the standard macro names to the "Color"
 * versions of those macros only if the "DitherDeclared" keyword has
 * not yet been defined elsewhere.  The "DitherDeclared" keyword is
 * also defined here to claim ownership of the primary implementation
 * even though this file does not rely on the definitions in any other
 * files.
 */
#ifndef DitherDeclared
#define DitherDeclared
#define DeclareDitherVars       DeclareColorDitherVars
#define InitDither              InitColorDither
#define StartDitherLine         StartColorDitherLine
#define DitherPixel             ColorDitherPixel
#define DitherBufComplete       ColorDitherBufComplete
#endif

typedef struct {
    int r, g, b;
} ColorDitherError;

#define DeclareColorDitherVars                                  \
    int er, eg, eb;                                             \
    ColorDitherError *cep;

#define InitColorDither(cvdata, clrdata, dstTW)                         \
    do {                                                                \
        if (cvdata->fserrors == 0) {                                    \
            int size = (dstTW + 2) * sizeof(ColorDitherError);          \
            cep = (ColorDitherError *) sysMalloc(size);                 \
            if (cep == 0) {                                             \
                SignalError(0, JAVAPKG "OutOfMemoryError", 0);          \
                return SCALEFAILURE;                                    \
            }                                                           \
            memset(cep, 0, size);                                       \
            cvdata->fserrors = (void *) cep;                            \
        }                                                               \
    } while (0)

#define StartColorDitherLine(cvdata, dstX1, dstY)                       \
    do {                                                                \
        cep = (ColorDitherError *) cvdata->fserrors;                    \
        if (dstX1) {                                                    \
            er = cep[0].r;                                              \
            eg = cep[0].g;                                              \
            eb = cep[0].b;                                              \
            cep += dstX1;                                               \
        } else {                                                        \
            er = eg = eb = 0;                                           \
        }                                                               \
    } while (0)

#define ColorDitherPixel(dstX, dstY, pixel, red, green, blue)           \
    do {                                                                \
        int e1, e2, e3;                                                 \
                                                                        \
        /* add previous errors */                                       \
        red += cep[1].r;                                                \
        green += cep[1].g;                                              \
        blue += cep[1].b;                                               \
                                                                        \
        /* bounds checking */                                           \
        e1 = ComponentBound(red);                                       \
        e2 = ComponentBound(green);                                     \
        e3 = ComponentBound(blue);                                      \
                                                                        \
        /* Store the closest color in the destination pixel */          \
        pixel = ColorCubeFSMap(e1, e2, e3);                             \
        GetPixelRGB(pixel, red, green, blue);                           \
                                                                        \
        /* Set the error from the previous lap */                       \
        cep[1].r = er; cep[1].g = eg; cep[1].b = eb;                    \
                                                                        \
        /* compute the errors */                                        \
        er = e1 - red; eg = e2 - green; eb = e3 - blue;                 \
                                                                        \
        /* distribute the errors */                                     \
        DitherDist(cep, e1, e2, e3, er, r);                             \
        DitherDist(cep, e1, e2, e3, eg, g);                             \
        DitherDist(cep, e1, e2, e3, eb, b);                             \
        cep++;                                                          \
    } while (0)

#define ColorDitherBufComplete(cvdata, dstX1)                           \
    do {                                                                \
        if (dstX1) {                                                    \
            cep = (ColorDitherError *) cvdata->fserrors;                \
            cep[0].r = er;                                              \
            cep[0].g = eg;                                              \
            cep[0].b = eb;                                              \
        }                                                               \
    } while (0)
