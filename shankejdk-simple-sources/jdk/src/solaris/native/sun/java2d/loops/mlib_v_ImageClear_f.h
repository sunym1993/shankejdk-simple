/*
 * Copyright (c) 2003, Oracle and/or its affiliates. All rights reserved.
 */

#ifndef __MLIB_V_IMAGECLEAR_F_H
#define __MLIB_V_IMAGECLEAR_F_H


#include <mlib_types.h>
#include <mlib_image_types.h>

#ifdef __cplusplus
extern "C" {
#endif /* __cplusplus */

void mlib_v_ImageClear_BIT_1(mlib_image     *img,
                             const mlib_s32 *color);

void mlib_v_ImageClear_BIT_2(mlib_image     *img,
                             const mlib_s32 *color);

void mlib_v_ImageClear_BIT_3(mlib_image     *img,
                             const mlib_s32 *color);

void mlib_v_ImageClear_BIT_4(mlib_image     *img,
                             const mlib_s32 *color);

void mlib_v_ImageClear_U8_1(mlib_image     *img,
                            const mlib_s32 *color);

void mlib_v_ImageClear_U8_2(mlib_image     *img,
                            const mlib_s32 *color);

void mlib_v_ImageClear_U8_3(mlib_image     *img,
                            const mlib_s32 *color);

void mlib_v_ImageClear_U8_4(mlib_image     *img,
                            const mlib_s32 *color);

void mlib_v_ImageClear_S16_1(mlib_image     *img,
                             const mlib_s32 *color);

void mlib_v_ImageClear_S16_2(mlib_image     *img,
                             const mlib_s32 *color);

void mlib_v_ImageClear_S16_3(mlib_image     *img,
                             const mlib_s32 *color);

void mlib_v_ImageClear_S16_4(mlib_image     *img,
                             const mlib_s32 *color);

void mlib_v_ImageClear_S32_1(mlib_image     *img,
                             const mlib_s32 *color);

void mlib_v_ImageClear_S32_2(mlib_image     *img,
                             const mlib_s32 *color);

void mlib_v_ImageClear_S32_3(mlib_image     *img,
                             const mlib_s32 *color);

void mlib_v_ImageClear_S32_4(mlib_image     *img,
                             const mlib_s32 *color);

#ifdef __cplusplus
}
#endif /* __cplusplus */
#endif /* __MLIB_V_IMAGECLEAR_F_H */
