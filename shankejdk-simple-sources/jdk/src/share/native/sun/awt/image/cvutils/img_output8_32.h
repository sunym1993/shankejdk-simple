/*
 * Copyright (c) 1996, Oracle and/or its affiliates. All rights reserved.
 */

/*
 * This file contains macro definitions for the Storing category of
 * the macros used by the generic scaleloop function.
 *
 * This implementation can store 8-bit or 32-bit pixels into an array
 * of either bytes or longs such that the pixel for (srcX, srcY) is
 * stored at index (srcOff + srcY * srcScan + srcX) in the array.
 */

#define DeclareOutputVars                               \
    pixptr dstP;                                        \
    int dst32;

#define InitOutput(cvdata, clrdata, dstX, dstY)                 \
    do {                                                        \
        switch (clrdata->bitsperpixel) {                        \
        case 8: dst32 = 0; break;                               \
        case 32: dst32 = 2; break;                              \
        default:                                                \
            SignalError(0, JAVAPKG "InternalError",             \
                        "unsupported screen depth");            \
            return SCALEFAILURE;                                \
        }                                                       \
        img_check((ScanBytes(cvdata) & ((1 << dst32)-1)) == 0); \
        dstP.vp = cvdata->outbuf;                               \
        dstP.bp += dstY * ScanBytes(cvdata) + (dstX << dst32);  \
    } while (0)

#define PutPixelInc(pixel, red, green, blue)                    \
    do {                                                        \
        if (dst32) {                                            \
            *dstP.ip++ = pixel;                                 \
        } else {                                                \
            *dstP.bp++ = ((unsigned char) pixel);               \
        }                                                       \
    } while (0)

#define EndOutputRow(cvdata, dstY, dstX1, dstX2)                \
    do {                                                        \
        SendRow(cvdata, dstY, dstX1, dstX2);                    \
        dstP.bp += (ScanBytes(cvdata)                           \
                    - ((dstX2 - dstX1) << dst32));              \
    } while (0)

#define EndOutputRect(cvdata, dstX1, dstY1, dstX2, dstY2)       \
    SendBuffer(cvdata, dstX1, dstY1, dstX2, dstY2)
