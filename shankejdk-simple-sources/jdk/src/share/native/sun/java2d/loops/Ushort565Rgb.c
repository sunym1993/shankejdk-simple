/*
 * Copyright (c) 2000, 2005, Oracle and/or its affiliates. All rights reserved.
 */

#include "AnyShort.h"
#include "Ushort565Rgb.h"
#include "AlphaMacros.h"

#include "IntArgb.h"
#include "IntArgbBm.h"
#include "IntArgbPre.h"
#include "IntRgb.h"
#include "ThreeByteBgr.h"
#include "ByteGray.h"
#include "ByteIndexed.h"
#include "Ushort4444Argb.h"

/*
 * This file declares, registers, and defines the various graphics
 * primitive loops to manipulate surfaces of type "Ushort565Rgb".
 *
 * See also LoopMacros.h
 */

RegisterFunc RegisterUshort565Rgb;

DECLARE_CONVERT_BLIT(Ushort565Rgb, IntArgb);
DECLARE_CONVERT_BLIT(IntArgb, Ushort565Rgb);
DECLARE_CONVERT_BLIT(ThreeByteBgr, Ushort565Rgb);
DECLARE_CONVERT_BLIT(ByteGray, Ushort565Rgb);
DECLARE_CONVERT_BLIT(ByteIndexed, Ushort565Rgb);
DECLARE_SCALE_BLIT(Ushort565Rgb, IntArgb);
DECLARE_SCALE_BLIT(IntArgb, Ushort565Rgb);
DECLARE_SCALE_BLIT(ThreeByteBgr, Ushort565Rgb);
DECLARE_SCALE_BLIT(ByteGray, Ushort565Rgb);
DECLARE_SCALE_BLIT(ByteIndexed, Ushort565Rgb);
DECLARE_XPAR_CONVERT_BLIT(ByteIndexedBm, Ushort565Rgb);
DECLARE_XPAR_SCALE_BLIT(ByteIndexedBm, Ushort565Rgb);
DECLARE_XPAR_SCALE_BLIT(IntArgbBm, Ushort565Rgb);
DECLARE_XPAR_BLITBG(ByteIndexedBm, Ushort565Rgb);
DECLARE_XPAR_CONVERT_BLIT(IntArgbBm, Ushort565Rgb);
DECLARE_XPAR_BLITBG(IntArgbBm, Ushort565Rgb);

DECLARE_XOR_BLIT(IntArgb, Ushort565Rgb);
DECLARE_SRC_MASKFILL(Ushort565Rgb);
DECLARE_SRCOVER_MASKFILL(Ushort565Rgb);
DECLARE_ALPHA_MASKFILL(Ushort565Rgb);
DECLARE_SRCOVER_MASKBLIT(IntArgb, Ushort565Rgb);
DECLARE_SRCOVER_MASKBLIT(IntArgbPre, Ushort565Rgb);
DECLARE_SRCOVER_MASKBLIT(Ushort4444Argb, Ushort565Rgb);
DECLARE_ALPHA_MASKBLIT(IntArgb, Ushort565Rgb);
DECLARE_ALPHA_MASKBLIT(IntArgbPre, Ushort565Rgb);
DECLARE_ALPHA_MASKBLIT(IntRgb, Ushort565Rgb);
DECLARE_SOLID_DRAWGLYPHLISTAA(Ushort565Rgb);
DECLARE_SOLID_DRAWGLYPHLISTLCD(Ushort565Rgb);

NativePrimitive Ushort565RgbPrimitives[] = {
    REGISTER_ANYSHORT_ISOCOPY_BLIT(Ushort565Rgb),
    REGISTER_ANYSHORT_ISOSCALE_BLIT(Ushort565Rgb),
    REGISTER_ANYSHORT_ISOXOR_BLIT(Ushort565Rgb),
    REGISTER_CONVERT_BLIT(Ushort565Rgb, IntArgb),
    REGISTER_CONVERT_BLIT(IntArgb, Ushort565Rgb),
    REGISTER_CONVERT_BLIT_EQUIV(IntRgb, Ushort565Rgb,
                                NAME_CONVERT_BLIT(IntArgb, Ushort565Rgb)),
    REGISTER_CONVERT_BLIT_EQUIV(IntArgbBm, Ushort565Rgb,
                                NAME_CONVERT_BLIT(IntArgb, Ushort565Rgb)),
    REGISTER_CONVERT_BLIT(ThreeByteBgr, Ushort565Rgb),
    REGISTER_CONVERT_BLIT(ByteGray, Ushort565Rgb),
    REGISTER_CONVERT_BLIT(ByteIndexed, Ushort565Rgb),
    REGISTER_SCALE_BLIT(Ushort565Rgb, IntArgb),
    REGISTER_SCALE_BLIT(IntArgb, Ushort565Rgb),
    REGISTER_SCALE_BLIT_EQUIV(IntArgbBm, Ushort565Rgb,
                              NAME_SCALE_BLIT(IntArgb, Ushort565Rgb)),
    REGISTER_SCALE_BLIT_EQUIV(IntRgb, Ushort565Rgb,
                              NAME_SCALE_BLIT(IntArgb, Ushort565Rgb)),
    REGISTER_SCALE_BLIT(ThreeByteBgr, Ushort565Rgb),
    REGISTER_SCALE_BLIT(ByteGray, Ushort565Rgb),
    REGISTER_SCALE_BLIT(ByteIndexed, Ushort565Rgb),
    REGISTER_XPAR_CONVERT_BLIT(ByteIndexedBm, Ushort565Rgb),
    REGISTER_XPAR_SCALE_BLIT(ByteIndexedBm, Ushort565Rgb),
    REGISTER_XPAR_SCALE_BLIT(IntArgbBm, Ushort565Rgb),
    REGISTER_XPAR_BLITBG(ByteIndexedBm, Ushort565Rgb),
    REGISTER_XPAR_CONVERT_BLIT(IntArgbBm, Ushort565Rgb),
    REGISTER_XPAR_BLITBG(IntArgbBm, Ushort565Rgb),

    REGISTER_XOR_BLIT(IntArgb, Ushort565Rgb),
    REGISTER_SRC_MASKFILL(Ushort565Rgb),
    REGISTER_SRCOVER_MASKFILL(Ushort565Rgb),
    REGISTER_ALPHA_MASKFILL(Ushort565Rgb),
    REGISTER_SRCOVER_MASKBLIT(IntArgb, Ushort565Rgb),
    REGISTER_SRCOVER_MASKBLIT(IntArgbPre, Ushort565Rgb),
    REGISTER_SRCOVER_MASKBLIT(Ushort4444Argb, Ushort565Rgb),
    REGISTER_ALPHA_MASKBLIT(IntArgb, Ushort565Rgb),
    REGISTER_ALPHA_MASKBLIT(IntArgbPre, Ushort565Rgb),
    REGISTER_ALPHA_MASKBLIT(IntRgb, Ushort565Rgb),
    REGISTER_SOLID_DRAWGLYPHLISTAA(Ushort565Rgb),
    REGISTER_SOLID_DRAWGLYPHLISTLCD(Ushort565Rgb),
};

jboolean RegisterUshort565Rgb(JNIEnv *env)
{
    return RegisterPrimitives(env, Ushort565RgbPrimitives,
                              ArraySize(Ushort565RgbPrimitives));
}

jint PixelForUshort565Rgb(SurfaceDataRasInfo *pRasInfo, jint rgb)
{
    return IntArgbToUshort565Rgb(rgb);
}

DEFINE_CONVERT_BLIT(Ushort565Rgb, IntArgb, 3ByteRgb)

DEFINE_CONVERT_BLIT(IntArgb, Ushort565Rgb, 1IntRgb)

DEFINE_CONVERT_BLIT(ThreeByteBgr, Ushort565Rgb, 3ByteRgb)

DEFINE_CONVERT_BLIT(ByteGray, Ushort565Rgb, 3ByteRgb)

DEFINE_CONVERT_BLIT_LUT8(ByteIndexed, Ushort565Rgb, PreProcessLut)

DEFINE_SCALE_BLIT(Ushort565Rgb, IntArgb, 3ByteRgb)

DEFINE_SCALE_BLIT(IntArgb, Ushort565Rgb, 1IntRgb)

DEFINE_SCALE_BLIT(ThreeByteBgr, Ushort565Rgb, 3ByteRgb)

DEFINE_SCALE_BLIT(ByteGray, Ushort565Rgb, 3ByteRgb)

DEFINE_SCALE_BLIT_LUT8(ByteIndexed, Ushort565Rgb, PreProcessLut)

DEFINE_XPAR_CONVERT_BLIT_LUT8(ByteIndexedBm, Ushort565Rgb, PreProcessLut)

DEFINE_XPAR_SCALE_BLIT_LUT8(ByteIndexedBm, Ushort565Rgb, PreProcessLut)

DEFINE_XPAR_SCALE_BLIT(IntArgbBm, Ushort565Rgb, 1IntRgb)

DEFINE_XPAR_BLITBG_LUT8(ByteIndexedBm, Ushort565Rgb, PreProcessLut)

DEFINE_XPAR_CONVERT_BLIT(IntArgbBm, Ushort565Rgb, 1IntRgb)

DEFINE_XPAR_BLITBG(IntArgbBm, Ushort565Rgb, 1IntRgb)

DEFINE_XOR_BLIT(IntArgb, Ushort565Rgb, AnyShort)

DEFINE_SRC_MASKFILL(Ushort565Rgb, 4ByteArgb)

DEFINE_SRCOVER_MASKFILL(Ushort565Rgb, 4ByteArgb)

DEFINE_ALPHA_MASKFILL(Ushort565Rgb, 4ByteArgb)

DEFINE_SRCOVER_MASKBLIT(IntArgb, Ushort565Rgb, 4ByteArgb)

DEFINE_SRCOVER_MASKBLIT(IntArgbPre, Ushort565Rgb, 4ByteArgb)

DEFINE_SRCOVER_MASKBLIT(Ushort4444Argb, Ushort565Rgb, 4ByteArgb)

DEFINE_ALPHA_MASKBLIT(IntArgb, Ushort565Rgb, 4ByteArgb)

DEFINE_ALPHA_MASKBLIT(IntArgbPre, Ushort565Rgb, 4ByteArgb)

DEFINE_ALPHA_MASKBLIT(IntRgb, Ushort565Rgb, 4ByteArgb)

DEFINE_SOLID_DRAWGLYPHLISTAA(Ushort565Rgb, 3ByteRgb)

DEFINE_SOLID_DRAWGLYPHLISTLCD(Ushort565Rgb, 3ByteRgb)
