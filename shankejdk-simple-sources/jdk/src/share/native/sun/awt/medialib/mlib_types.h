/*
 * Copyright (c) 1997, 2013, Oracle and/or its affiliates. All rights reserved.
 */


#ifndef MLIB_TYPES_H
#define MLIB_TYPES_H

#include <limits.h>

#ifndef DBL_MAX
#define DBL_MAX 1.7976931348623157E+308 /* max decimal value of a "double" */
#endif

#ifndef FLT_MAX
#define FLT_MAX 3.402823466E+38F        /* max decimal value of a "float" */
#endif

#ifndef FLT_MIN
#define FLT_MIN 1.175494351e-38F        /* min normalised value of a "float" */
#endif

#ifdef  __cplusplus
extern "C" {
#endif

typedef char               mlib_s8;
typedef unsigned char      mlib_u8;
typedef short              mlib_s16;
typedef unsigned short     mlib_u16;
typedef int                mlib_s32;
typedef unsigned int       mlib_u32;
typedef float              mlib_f32;
typedef double             mlib_d64;

#if defined(__SUNPRO_C) || defined(__SUNPRO_CC) || defined(__GNUC__) || defined(_AIX)

#include <stdint.h>
#include <stddef.h>

#if defined(MLIB_OS64BIT) || (defined(MACOSX) && defined(_LP64))

typedef long               mlib_s64;
typedef unsigned long      mlib_u64;

#define MLIB_S64_MIN       LONG_MIN
#define MLIB_S64_MAX       LONG_MAX

#define MLIB_S64_CONST(x)  x##L
#define MLIB_U64_CONST(x)  x##UL

#elif (__STDC__ - 0 == 0) || defined(__GNUC__)

#if defined(_NO_LONGLONG)

typedef union {
  mlib_d64 d64;
  mlib_s32 s32[2];
} mlib_s64;

typedef union {
  mlib_d64 d64;
  mlib_u32 u32[2];
} mlib_u64;

#else

typedef long long          mlib_s64;
typedef unsigned long long mlib_u64;

#define MLIB_S64_MIN       LLONG_MIN
#define MLIB_S64_MAX       LLONG_MAX

#define MLIB_S64_CONST(x)  x##LL
#define MLIB_U64_CONST(x)  x##ULL

#endif /* !defined(_NO_LONGLONG) */

#endif  /* MLIB_OS64BIT */

#else

#error  "unknown platform"

#endif

typedef uintptr_t          mlib_addr;
typedef void*              mlib_ras;

#define MLIB_S8_MIN        SCHAR_MIN
#define MLIB_S8_MAX        SCHAR_MAX
#define MLIB_U8_MIN        0
#define MLIB_U8_MAX        UCHAR_MAX
#define MLIB_S16_MIN       SHRT_MIN
#define MLIB_S16_MAX       SHRT_MAX
#define MLIB_U16_MIN       0
#define MLIB_U16_MAX       USHRT_MAX
#define MLIB_S32_MIN       INT_MIN
#define MLIB_S32_MAX       INT_MAX
#define MLIB_U32_MIN       0
#define MLIB_U32_MAX       UINT_MAX
#define MLIB_F32_MIN      -FLT_MAX
#define MLIB_F32_MAX       FLT_MAX
#define MLIB_D64_MIN      -DBL_MAX
#define MLIB_D64_MAX       DBL_MAX

#ifdef  __cplusplus
}
#endif

#endif  /* MLIB_TYPES_H */
