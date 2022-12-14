/*
 * Copyright (c) 1997, Oracle and/or its affiliates. All rights reserved.
 */

#ifndef _VDrawingAreaP_h_
#define _VDrawingAreaP_h_

#include <Xm/DrawingAP.h>
#include "VDrawingArea.h"


/***************************************************************
 * VDrawingArea Widget Data Structures
 *
 *
 **************************************************************/

/* Define part class structure */
typedef struct _VDrawingAreaClass {
        XtPointer                       extension;
} VDrawingAreaClassPart;

/* Define the full class record */
typedef struct _VDrawingAreaClassRec {
        CoreClassPart           core_class;
        CompositeClassPart      composite_class;
        ConstraintClassPart     constraint_class;
        XmManagerClassPart      manager_class;
        XmDrawingAreaClassPart  drawing_area_class;
        VDrawingAreaClassPart   vdrawingarea_class;
} VDrawingAreaClassRec;

/* External definition for class record */
extern VDrawingAreaClassRec vDrawingAreaClassRec;

typedef struct {
        Visual *visual;
} VDrawingAreaPart;

/****************************************************************
 *
 * Full instance record declaration
 *
 ****************************************************************/

typedef struct _VDrawingAreaRec
{
        CorePart                core;
        CompositePart           composite;
        ConstraintPart          constraint;
        XmManagerPart           manager;
        XmDrawingAreaPart       drawing_area;
        VDrawingAreaPart        vdrawing_area;
} VDrawingAreaRec;



#endif /* !_VDrawingAreaP_h_ */
