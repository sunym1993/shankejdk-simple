/*
 * Copyright (c) 2000, 2008, Oracle and/or its affiliates. All rights reserved.
 */

#ifndef ByteGray_h_Included
#define ByteGray_h_Included

#include "IntDcm.h"

/*
 * This file contains macro and type definitions used by the macros in
 * LoopMacros.h to manipulate a surface of type "ByteGray".
 */

typedef jubyte  ByteGrayPixelType;
typedef jubyte  ByteGrayDataType;

#define ByteGrayIsOpaque 1

#define ByteGrayPixelStride     1
#define ByteGrayBitsPerPixel    8

#define DeclareByteGrayLoadVars(PREFIX)
#define DeclareByteGrayStoreVars(PREFIX)
#define SetByteGrayStoreVarsYPos(PREFIX, pRasInfo, y)
#define SetByteGrayStoreVarsXPos(PREFIX, pRasInfo, x)
#define InitByteGrayLoadVars(PREFIX, pRasInfo)
#define InitByteGrayStoreVarsY(PREFIX, pRasInfo)
#define InitByteGrayStoreVarsX(PREFIX, pRasInfo)
#define NextByteGrayStoreVarsX(PREFIX)
#define NextByteGrayStoreVarsY(PREFIX)
#define DeclareByteGrayPixelData(PREFIX)
#define ExtractByteGrayPixelData(PIXEL, PREFIX)

#define ByteGrayXparLutEntry            -1
#define ByteGrayIsXparLutEntry(pix)     (pix < 0)
#define StoreByteGrayNonXparFromArgb    StoreByteGrayFrom1IntArgb


#define ComposeByteGrayFrom3ByteRgb(r, g, b) \
    (ByteGrayDataType)(((77*(r)) + (150*(g)) + (29*(b)) + 128) / 256)


#define StoreByteGrayPixel(pRas, x, pixel) \
    ((pRas)[x] = (jubyte) (pixel))

#define StoreByteGrayPixelData(pPix, x, pixel, PREFIX) \
    StoreByteGrayPixel(pPix, x, pixel)

#define ByteGrayPixelFromArgb(pixel, rgb, pRasInfo) \
    do { \
        jint r, g, b; \
        ExtractIntDcmComponentsX123(rgb, r, g, b); \
        (pixel) = ComposeByteGrayFrom3ByteRgb(r, g, b); \
    } while (0)


#define LoadByteGrayTo1IntRgb(pRas, PREFIX, x, rgb) \
    do { \
        int gray = (pRas)[x]; \
        (rgb) = (((gray << 8) | gray) << 8) | gray; \
    } while (0)

#define LoadByteGrayTo1IntArgb(pRas, PREFIX, x, argb) \
    do { \
        int gray = (pRas)[x]; \
        (argb) = (((((0xff << 8) | gray) << 8) | gray) << 8) | gray; \
    } while (0)

#define LoadByteGrayTo3ByteRgb(pRas, PREFIX, x, r, g, b) \
    ((r) = (g) = (b) = (pRas)[x])

#define LoadByteGrayTo4ByteArgb(pRas, PREFIX, x, a, r, g, b) \
    do { \
        LoadByteGrayTo3ByteRgb(pRas, PREFIX, x, r, g, b); \
        (a) = 0xff; \
    } while (0)

#define LoadByteGrayTo1ByteGray(pRas, PREFIX, x, gray) \
    (gray) = (pRas)[x]

#define StoreByteGrayFrom1IntRgb(pRas, PREFIX, x, rgb) \
    do { \
        int r, g, b; \
        ExtractIntDcmComponentsX123(rgb, r, g, b); \
        StoreByteGrayFrom3ByteRgb(pRas, PREFIX, x, r, g, b); \
    } while (0)

#define StoreByteGrayFrom1IntArgb(pRas, PREFIX, x, argb) \
    StoreByteGrayFrom1IntRgb(pRas, PREFIX, x, argb)

#define StoreByteGrayFrom3ByteRgb(pRas, PREFIX, x, r, g, b) \
    (pRas)[x] = ComposeByteGrayFrom3ByteRgb(r, g, b)

#define StoreByteGrayFrom4ByteArgb(pRas, PREFIX, x, a, r, g, b) \
    StoreByteGrayFrom3ByteRgb(pRas, PREFIX, x, r, g, b)

#define StoreByteGrayFrom1ByteGray(pRas, PREFIX, x, gray) \
    StoreByteGrayPixel(pRas, x, gray)

#define CopyByteGrayToIntArgbPre(pRGB, i, PREFIX, pRow, x) \
    LoadByteGrayTo1IntArgb(pRow, PREFIX, x, pRGB[i])


#define DeclareByteGrayAlphaLoadData(PREFIX)
#define InitByteGrayAlphaLoadData(PREFIX, pRasInfo)

#define LoadAlphaFromByteGrayFor1ByteGray(pRas, PREFIX, COMP_PREFIX) \
    COMP_PREFIX ## A = 0xff

#define Postload1ByteGrayFromByteGray(pRas, PREFIX, COMP_PREFIX) \
    COMP_PREFIX ## G = pRas[0]


#define ByteGrayIsPremultiplied 0

#define DeclareByteGrayBlendFillVars(PREFIX) \
    jubyte PREFIX;

#define ClearByteGrayBlendFillVars(PREFIX, argb) \
    PREFIX = 0

#define InitByteGrayBlendFillVarsNonPre(PREFIX, argb, COMP_PREFIX) \
    PREFIX = (jubyte) COMP_PREFIX ## G

#define InitByteGrayBlendFillVarsPre(PREFIX, argb, COMP_PREFIX)

#define StoreByteGrayBlendFill(pRas, PREFIX, x, argb, COMP_PREFIX) \
    (pRas)[x] = PREFIX

#define StoreByteGrayFrom1ByteGrayComps(pRas, PREFIX, x, COMP_PREFIX) \
    StoreByteGrayPixel(pRas, x, COMP_PREFIX ## G)

#endif /* ByteGray_h_Included */
