/*
 * Copyright (c) 1997, 2003, Oracle and/or its affiliates. All rights reserved.
 */


/*
 * FUNCTIONS
 *      mlib_ImageConvClearEdge  - Set edge of an image to a specific color.
 *
 * SYNOPSIS
 *      mlib_status mlib_ImageConvClearEdge(mlib_image     *dst,
 *                                          mlib_s32       dx_l,
 *                                          mlib_s32       dx_r,
 *                                          mlib_s32       dy_t,
 *                                          mlib_s32       dy_b,
 *                                          const mlib_s32 *color,
 *                                          mlib_s32       cmask)
 *
 * ARGUMENT
 *      dst       Pointer to an image.
 *      dx_l      Number of columns on the left side of the
 *                image to be cleared.
 *      dx_r      Number of columns on the right side of the
 *                image to be cleared.
 *      dy_t      Number of rows on the top edge of the
 *                image to be cleared.
 *      dy_b      Number of rows on the top edge of the
 *                image to be cleared.
 *      color     Pointer to the color that the edges are set to.
 *      cmask     Channel mask to indicate the channels to be convolved.
 *                Each bit of which represents a channel in the image. The
 *                channels corresponded to 1 bits are those to be processed.
 *
 * RESTRICTION
 *      dst can have 1, 2, 3 or 4 channels of MLIB_BYTE or MLIB_SHORT or MLIB_INT
 *      data type.
 *
 * DESCRIPTION
 *      Set edge of an image to a specific color. (VIS version)
 *      The unselected channels are not overwritten.
 *      If src and dst have just one channel,
 *      cmask is ignored.
 */

#include "mlib_image.h"
#include "mlib_ImageConvEdge.h"

/***************************************************************/
#define EDGES(chan, type, mask)                                       \
  {                                                                   \
    type *pdst = (type *) mlib_ImageGetData(dst);                     \
    type color_i;                                                     \
    mlib_s32 dst_stride = mlib_ImageGetStride(dst) / sizeof(type);    \
    mlib_s32 i, j, l;                                                 \
    mlib_s32 testchan;                                                \
                                                                      \
    testchan = 1;                                                     \
    for (l = chan - 1; l >= 0; l--) {                                 \
      if ((mask & testchan) == 0) {                                   \
        testchan <<= 1;                                               \
        continue;                                                     \
      }                                                               \
      testchan <<= 1;                                                 \
      color_i = (type)color[l];                                       \
      for (j = 0; j < dx_l; j++) {                                    \
        for (i = dy_t; i < (dst_height - dy_b); i++) {                \
          pdst[i*dst_stride + l + j*chan] = color_i;                  \
        }                                                             \
      }                                                               \
      for (j = 0; j < dx_r; j++) {                                    \
        for (i = dy_t; i < (dst_height - dy_b); i++) {                \
          pdst[i*dst_stride + l+(dst_width-1 - j)*chan] = color_i;    \
        }                                                             \
      }                                                               \
      for (i = 0; i < dy_t; i++) {                                    \
        for (j = 0; j < dst_width; j++) {                             \
          pdst[i*dst_stride + l + j*chan] = color_i;                  \
        }                                                             \
      }                                                               \
      for (i = 0; i < dy_b; i++) {                                    \
        for (j = 0; j < dst_width; j++) {                             \
          pdst[(dst_height-1 - i)*dst_stride + l + j*chan] = color_i; \
        }                                                             \
      }                                                               \
    }                                                                 \
  }

/***************************************************************/
mlib_status mlib_ImageConvClearEdge(mlib_image     *dst,
                                    mlib_s32       dx_l,
                                    mlib_s32       dx_r,
                                    mlib_s32       dy_t,
                                    mlib_s32       dy_b,
                                    const mlib_s32 *color,
                                    mlib_s32       cmask)
{
  mlib_s32 dst_width = mlib_ImageGetWidth(dst);
  mlib_s32 dst_height = mlib_ImageGetHeight(dst);
  mlib_s32 channel = mlib_ImageGetChannels(dst);

  if (dx_l + dx_r > dst_width) {
    dx_l = dst_width;
    dx_r = 0;
  }

  if (dy_t + dy_b > dst_height) {
    dy_t = dst_height;
    dy_b = 0;
  }

  if (channel == 1)
    cmask = 1;

  switch (mlib_ImageGetType(dst)) {
    case MLIB_BIT:
      return mlib_ImageConvClearEdge_Bit(dst, dx_l, dx_r, dy_t, dy_b, color, cmask);
    case MLIB_BYTE:
      EDGES(channel, mlib_u8, cmask)
        break;
    case MLIB_SHORT:
    case MLIB_USHORT:
      EDGES(channel, mlib_s16, cmask)
        break;
    case MLIB_INT:
      EDGES(channel, mlib_s32, cmask)
        break;
    default:
      return MLIB_FAILURE;
  }

  return MLIB_SUCCESS;
}

/***************************************************************/
mlib_status mlib_ImageConvZeroEdge(mlib_image *dst,
                                   mlib_s32   dx_l,
                                   mlib_s32   dx_r,
                                   mlib_s32   dy_t,
                                   mlib_s32   dy_b,
                                   mlib_s32   cmask)
{
  mlib_d64 zero[4] = { 0, 0, 0, 0 };
  mlib_type type = mlib_ImageGetType(dst);

  if (type == MLIB_FLOAT || type == MLIB_DOUBLE) {
    return mlib_ImageConvClearEdge_Fp(dst, dx_l, dx_r, dy_t, dy_b, zero, cmask);
  }
  else {
    return mlib_ImageConvClearEdge(dst, dx_l, dx_r, dy_t, dy_b, (mlib_s32 *) zero, cmask);
  }
}

/***************************************************************/
