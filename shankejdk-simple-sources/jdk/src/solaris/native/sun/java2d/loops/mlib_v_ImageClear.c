/*
 * Copyright (c) 2003, Oracle and/or its affiliates. All rights reserved.
 */


/*
 * FUNCTIONS
 *      mlib_ImageClear         - Clear an image to a specific color.
 *
 * SYNOPSIS
 *      mlib_status mlib_ImageClear(mlib_image     *img,
 *                                  const mlib_s32 *color);
 *
 * ARGUMENT
 *      img     Pointer to an image.
 *      color   Pointer to the color that the image is set to.
 *
 * RESTRICTION
 *      img can have 1, 2, 3 or 4 channels of MLIB_BIT, MLIB_BYTE,
 *      MLIB_SHORT, MLIB_USHORT or MLIB_INT data type.
 *
 * DESCRIPTION
 *      Clear an image to a specific color.
 */

#include <mlib_image.h>
#include <mlib_ImageCheck.h>
#include <mlib_v_ImageClear_f.h>

/***************************************************************/

#if ! defined ( __MEDIALIB_OLD_NAMES )
#if defined ( __SUNPRO_C )

#pragma weak mlib_ImageClear = __mlib_ImageClear

#elif defined ( __GNUC__ ) /* defined ( __SUNPRO_C ) */
  __typeof__ (__mlib_ImageClear) mlib_ImageClear
    __attribute__ ((weak,alias("__mlib_ImageClear")));

#else /* defined ( __SUNPRO_C ) */

#error  "unknown platform"

#endif /* defined ( __SUNPRO_C ) */
#endif /* ! defined ( __MEDIALIB_OLD_NAMES ) */

/***************************************************************/

mlib_status __mlib_ImageClear(mlib_image     *img,
                              const mlib_s32 *color)
{
  MLIB_IMAGE_CHECK(img);

  switch (mlib_ImageGetType(img)) {

    case MLIB_BIT:
      switch (mlib_ImageGetChannels(img)) {

        case 1:
          mlib_v_ImageClear_BIT_1(img, color);
          break;

        case 2:
          mlib_v_ImageClear_BIT_2(img, color);
          break;

        case 3:
          mlib_v_ImageClear_BIT_3(img, color);
          break;

        case 4:
          mlib_v_ImageClear_BIT_4(img, color);
          break;

        default:
          return MLIB_FAILURE;
      }

      break;

    case MLIB_BYTE:
      switch (mlib_ImageGetChannels(img)) {

        case 1:
          mlib_v_ImageClear_U8_1(img, color);
          break;

        case 2:
          mlib_v_ImageClear_U8_2(img, color);
          break;

        case 3:
          mlib_v_ImageClear_U8_3(img, color);
          break;

        case 4:
          mlib_v_ImageClear_U8_4(img, color);
          break;

        default:
          return MLIB_FAILURE;
      }

      break;

    case MLIB_SHORT:
      switch (mlib_ImageGetChannels(img)) {

        case 1:
          mlib_v_ImageClear_S16_1(img, color);
          break;

        case 2:
          mlib_v_ImageClear_S16_2(img, color);
          break;

        case 3:
          mlib_v_ImageClear_S16_3(img, color);
          break;

        case 4:
          mlib_v_ImageClear_S16_4(img, color);
          break;

        default:
          return MLIB_FAILURE;
      }

      break;

    case MLIB_INT:
      switch (mlib_ImageGetChannels(img)) {

        case 1:
          mlib_v_ImageClear_S32_1(img, color);
          break;

        case 2:
          mlib_v_ImageClear_S32_2(img, color);
          break;

        case 3:
          mlib_v_ImageClear_S32_3(img, color);
          break;

        case 4:
          mlib_v_ImageClear_S32_4(img, color);
          break;

        default:
          return MLIB_FAILURE;
      }

      break;

    default:
      return MLIB_FAILURE;                  /* MLIB_BIT is not supported here */
  }

  return MLIB_SUCCESS;
}

/***************************************************************/
