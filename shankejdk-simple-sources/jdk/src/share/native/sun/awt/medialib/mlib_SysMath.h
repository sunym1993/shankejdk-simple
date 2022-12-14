/*
 * Copyright (c) 1998, 2003, Oracle and/or its affiliates. All rights reserved.
 */


#ifndef MLIB_SYSMATH_H
#define MLIB_SYSMATH_H

#include <math.h>

#define mlib_acos       acos
#define mlib_sin        sin
#define mlib_cos        cos
#define mlib_fabs       fabs
#define mlib_ceil       ceil

#ifdef MLIB_LIBCAFEMATH

#include <stdlib.h>

#define mlib_sqrt       mlib_sqrt_cafe
#define mlib_sinf       sinf
#define mlib_cosf       cosf
void mlib_sincosf (float x, float *s, float *c);
#define mlib_sqrtf      mlib_sqrtf_cafe
#define mlib_fabsf      fabsf

double mlib_sqrt_cafe  (double x);
float  mlib_sqrtf_cafe (float  x);

#else

#define mlib_sqrt       sqrt

#ifdef MLIB_NO_LIBSUNMATH

#define mlib_sinf       (float) sin
#define mlib_cosf       (float) cos
void mlib_sincosf (float x, float *s, float *c);
#define mlib_sqrtf      (float) sqrt
#define mlib_fabsf      (float) fabs

#else

#include <sunmath.h>

#define mlib_sinf       sinf
#define mlib_cosf       cosf
#define mlib_sincosf    sincosf
#define mlib_sqrtf       sqrtf
#define mlib_fabsf       fabsf

#endif  /* MLIB_NO_LIBSUNMATH */

#endif  /* MLIB_LIBCAFEMATH */


  /* internal mathematical functions */

double mlib_sincospi(double x, double *co);
double mlib_atan2i (int y, int x);
int    mlib_ilogb (double x);

#endif /* MLIB_SYSMATH_H */
