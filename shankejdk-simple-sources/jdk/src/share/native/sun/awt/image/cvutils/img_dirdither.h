/*
 * Copyright (c) 1996, Oracle and/or its affiliates. All rights reserved.
 */

/*
 * This file contains macro definitions for the Encoding category of
 * the macros used by the generic scaleloop function.
 *
 * This implementation can encode the color information into the
 * output pixels directly by using shift and scale amounts to
 * specify which bits of the output pixel should contain the red,
 * green, and blue components.  The scale factors are only needed
 * if some of the color components in the output pixels hold less
 * than 8-bits of information.
 *
 * This file can be used to provide the default implementation of the
 * Encoding macros for direct pixel type displays with any size up to
 * 8-bits of color information per component.
 */

#define DeclareDitherVars                                               \
    int red_dither_shift, green_dither_shift, blue_dither_shift;        \
    int red_dither_scale, green_dither_scale, blue_dither_scale;

#define InitDither(cvdata, clrdata, dstTW)                      \
    do {                                                        \
        red_dither_shift = clrdata->rOff;                       \
        green_dither_shift = clrdata->gOff;                     \
        blue_dither_shift = clrdata->bOff;                      \
        red_dither_scale = clrdata->rScale;                     \
        green_dither_scale = clrdata->gScale;                   \
        blue_dither_scale = clrdata->bScale;                    \
    } while (0)

#define StartDitherLine(cvdata, dstX1, dstY)                    \
    do {} while (0)

#define DitherPixel(dstX, dstY, pixel, red, green, blue)        \
    do {                                                        \
        pixel = (((red >> red_dither_scale)                     \
                  << red_dither_shift) |                        \
                 ((green >> green_dither_scale)                 \
                  << green_dither_shift) |                      \
                 ((blue >> blue_dither_scale)                   \
                  << blue_dither_shift));                       \
    } while (0)

#define DitherBufComplete(cvdata, dstX1)                        \
    do {} while (0)
