/*
 * Copyright (c) 2005, 2013, Oracle and/or its affiliates. All rights reserved.
 */

#include <math.h>
#include <float.h>
#include "jni_util.h"

#include "GraphicsPrimitiveMgr.h"
#include "LineUtils.h"
#include "ProcessPath.h"
#include "DrawPath.h"

#include "sun_java2d_loops_DrawPath.h"

static void processLine(DrawHandler* hnd,
                        jint x0, jint y0, jint x1, jint y1)
{
    LineUtils_ProcessLine(DHND(hnd)->pRasInfo,
                          DHND(hnd)->pixel,
                          DHND(hnd)->pPrim->funcs.drawline,
                          DHND(hnd)->pPrim,
                          DHND(hnd)->pCompInfo,
                          x0, y0, x1, y1, 0);
}

static void processPoint(DrawHandler* hnd, jint x0, jint y0)
{
    DHND(hnd)->pPrim->funcs.drawline(
        DHND(hnd)->pRasInfo, x0, y0, DHND(hnd)->pixel, 1, 0,
        BUMP_POS_PIXEL, 0, BUMP_NOOP, 0,
        DHND(hnd)->pPrim, DHND(hnd)->pCompInfo);
}

 /*
  * Class:     sun_java2d_loops_DrawPath
  * Method:    DrawPath
  * Signature: (Lsun/java2d/SunGraphics2D;Lsun/java2d/SurfaceData;IILjava/awt/geom/Path2D.Float;)V
  */
JNIEXPORT void JNICALL Java_sun_java2d_loops_DrawPath_DrawPath
   (JNIEnv *env, jobject self,
    jobject sg2d, jobject sData,
    jint transX, jint transY, jobject p2df)
{
    jarray typesArray;
    jarray coordsArray;
    jint numTypes;
    jboolean ok = JNI_TRUE;
    jint pixel = GrPrim_Sg2dGetPixel(env, sg2d);
    jint maxCoords;
    jfloat *coords;
    SurfaceDataOps *sdOps;
    SurfaceDataRasInfo rasInfo;
    CompositeInfo compInfo;
    jint ret;
    NativePrimitive *pPrim = GetNativePrim(env, self);
    jint stroke;
    jboolean throwExc = JNI_FALSE;

    if (pPrim == NULL) {
        return;
    }
    if (pPrim->pCompType->getCompInfo != NULL) {
        GrPrim_Sg2dGetCompInfo(env, sg2d, pPrim, &compInfo);
    }

    stroke = (*env)->GetIntField(env, sg2d, sg2dStrokeHintID);

    sdOps = SurfaceData_GetOps(env, sData);
    if (sdOps == 0) {
        return;
    }

    typesArray = (jarray)(*env)->GetObjectField(env, p2df, path2DTypesID);
    coordsArray = (jarray)(*env)->GetObjectField(env, p2df,
                                                 path2DFloatCoordsID);
    if (coordsArray == NULL) {
        JNU_ThrowNullPointerException(env, "coordinates array");
        return;
    }
    numTypes = (*env)->GetIntField(env, p2df, path2DNumTypesID);
    if ((*env)->GetArrayLength(env, typesArray) < numTypes) {
        JNU_ThrowArrayIndexOutOfBoundsException(env, "types array");
        return;
    }

    GrPrim_Sg2dGetClip(env, sg2d, &rasInfo.bounds);

    ret = sdOps->Lock(env, sdOps, &rasInfo, SD_LOCK_FASTEST | pPrim->dstflags);
    if (ret == SD_FAILURE) {
        return;
    }

    maxCoords = (*env)->GetArrayLength(env, coordsArray);
    coords = (jfloat*)(*env)->GetPrimitiveArrayCritical(
            env, coordsArray, NULL);
    if (coords == NULL) {
        SurfaceData_InvokeUnlock(env, sdOps, &rasInfo);
        return;
    }

    if (ret == SD_SLOWLOCK) {
        GrPrim_RefineBounds(&rasInfo.bounds, transX, transY,
                     coords, maxCoords);
        ok = (rasInfo.bounds.x2 > rasInfo.bounds.x1 &&
              rasInfo.bounds.y2 > rasInfo.bounds.y1);
    }

    if (ok) {
        sdOps->GetRasInfo(env, sdOps, &rasInfo);
        if (rasInfo.rasBase) {
            if (rasInfo.bounds.x2 > rasInfo.bounds.x1 &&
                rasInfo.bounds.y2 > rasInfo.bounds.y1)
            {
                DrawHandlerData dHData;
                DrawHandler drawHandler =
                {
                    &processLine,
                    &processPoint,
                    NULL,
                    0, 0, 0, 0,
                    0, 0, 0, 0,
                    NULL
                };

                jbyte *types = (jbyte*)(*env)->GetPrimitiveArrayCritical(
                    env, typesArray, NULL);

                /* Initialization of the following fields in the declaration of
                 * the dHData and drawHandler above causes warnings on sun
                 * studio compiler with
                 * -xc99=%none option applied (this option means compliance
                 *  with C90 standard instead of C99)
                 */
                dHData.pRasInfo = &rasInfo;
                dHData.pixel = pixel;
                dHData.pPrim = pPrim;
                dHData.pCompInfo = &compInfo;

                drawHandler.xMin = rasInfo.bounds.x1;
                drawHandler.yMin = rasInfo.bounds.y1;
                drawHandler.xMax = rasInfo.bounds.x2;
                drawHandler.yMax = rasInfo.bounds.y2;
                drawHandler.pData = &dHData;

                if (types != NULL) {
                    if (!doDrawPath(&drawHandler, NULL, transX, transY,
                                    coords, maxCoords, types, numTypes,
                                    (stroke == sunHints_INTVAL_STROKE_PURE)?
                                            PH_STROKE_PURE : PH_STROKE_DEFAULT))
                    {
                        throwExc = JNI_TRUE;
                    }

                    (*env)->ReleasePrimitiveArrayCritical(env, typesArray, types,
                                                          JNI_ABORT);
                }
            }
        }
        SurfaceData_InvokeRelease(env, sdOps, &rasInfo);
    }
    (*env)->ReleasePrimitiveArrayCritical(env, coordsArray, coords,
                                          JNI_ABORT);

    if (throwExc) {
        JNU_ThrowArrayIndexOutOfBoundsException(env,
                                                "coords array");
    }

    SurfaceData_InvokeUnlock(env, sdOps, &rasInfo);
}
