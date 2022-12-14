/*
 * Copyright (c) 1996, 1997, Oracle and/or its affiliates. All rights reserved.
 */

/*
 * This file contains macro definitions for the Alpha category of the
 * macros used by the generic scaleloop function.
 *
 * This implementation of the Alpha macros will perform an ordered
 * dither of the 8-bit alpha values collected from the input pixel
 * data to construct a 1-bit deep image mask used to control the
 * pixel coverage of the color pixels in the output.  This is a
 * minimal quality implementation of Alpha that has the advantage
 * that it is easy to support on a wide variety of platforms and
 * graphics systems.
 *
 * This file can be used to provide the default implementation of the
 * Alpha macros, handling all transparency cases.
 */

/*
 * The macro IfAlpha is used by the varous pixel conversion macros
 * to conditionally compile code that is only needed if alpha values
 * are going to be used.
 */
#define IfAlpha(statements)     statements

#ifdef DEBUG
#define DeclareAlphaDebugVars                           \
    MaskBits *endMask;
#define SetupEndMask(mask, dstH, cvdata)                \
    do {endMask = mask + dstH * MaskScan(cvdata);} while (0)
#else /* DEBUG */
#define DeclareAlphaDebugVars
#define SetupEndMask(mask, dstH, cvdata)                \
    do {} while (0)
#endif /* DEBUG */

#define DeclareAlphaVars                                \
    DeclareAlphaDebugVars                               \
    MaskBits *mask;                                     \
    MaskBits maskbits, maskcurbit, maskadjust;          \
    int laststore;                                      \
    extern uns_ordered_dither_array img_oda_alpha;

#define InitAlpha(cvdata, dstY, dstX1, dstX2)                   \
    do {                                                        \
        laststore = 1;                                          \
        mask = (MaskBits *) cvdata->maskbuf;                    \
        maskadjust = - (MaskOffset(dstX2) - MaskOffset(dstX1)); \
        if (mask) {                                             \
            SetupEndMask(mask, dstTotalHeight, cvdata);         \
            mask += ((dstY * MaskScan(cvdata))                  \
                     + MaskOffset(dstX1));                      \
            maskadjust += MaskScan(cvdata);                     \
            maskcurbit = 1;                                     \
        } else {                                                \
            maskcurbit = 0;                                     \
        }                                                       \
    } while (0)

#define StartAlphaRow(cvdata, dstX, dstY)                       \
    do {                                                        \
        if (maskcurbit) {                                       \
            maskbits = *mask;                                   \
            maskcurbit = MaskInit(dstX);                        \
        }                                                       \
    } while (0)

#define IncrementMaskBit(dstX)                                  \
    do {                                                        \
        if (((maskcurbit) >>= 1) == 0) {                        \
            *mask++ = maskbits;                                 \
            if (dstX < DSTX2 - 1) {                             \
                img_check(mask < endMask);                      \
                maskbits = *mask;                               \
            } else {                                            \
                laststore = 0;                                  \
            }                                                   \
            maskcurbit = MaskInit(0);                           \
        }                                                       \
    } while (0)

#define SetTransparentPixel(cvdata, dstX, dstY)                 \
    do {                                                        \
        if (!maskcurbit) {                                      \
            mask = (MaskBits *) ImgInitMask(cvdata,             \
                                            DSTX1, DSTY1,       \
                                            DSTX2, DSTY2);      \
            if (!mask) {                                        \
                SignalError(0, JAVAPKG "OutOfMemoryError", 0);  \
                return SCALEFAILURE;                            \
            }                                                   \
            SetupEndMask(mask, dstTotalHeight, cvdata);         \
            mask += ((dstY * MaskScan(cvdata))                  \
                     + MaskOffset(dstX));                       \
            maskadjust += MaskScan(cvdata);                     \
            maskbits = *mask;                                   \
            maskcurbit = MaskInit(dstX);                        \
        }                                                       \
        SetTransparentBit(maskbits, maskcurbit);                \
        IncrementMaskBit(dstX);                                 \
    } while (0)

#define SetOpaquePixel(cvdata, dstX, dstY)                      \
    do {                                                        \
        if (maskcurbit) {                                       \
            SetOpaqueBit(maskbits, maskcurbit);                 \
            IncrementMaskBit(dstX);                             \
        }                                                       \
    } while (0)

#define ApplyAlpha(cvdata, dstX, dstY, alpha)                   \
    do {                                                        \
        if (alpha + img_oda_alpha[dstX & 7][dstY & 7] < 255) {  \
            SetTransparentPixel(cvdata, dstX, dstY);            \
        } else {                                                \
            SetOpaquePixel(cvdata, dstX, dstY);                 \
        }                                                       \
    } while (0)

#define EndMaskLine()                                           \
    do {                                                        \
        if (maskcurbit) {                                       \
            if (laststore) {                                    \
                img_check(mask < endMask);                      \
                *mask = maskbits;                               \
            }                                                   \
            mask += maskadjust;                                 \
        }                                                       \
    } while (0)
