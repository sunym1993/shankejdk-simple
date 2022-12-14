/*
 * Copyright (c) 2000, 2004, Oracle and/or its affiliates. All rights reserved.
 */



package sun.awt.image;

import java.awt.Point;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.DataBuffer;
import java.awt.image.DirectColorModel;
import java.awt.image.PixelInterleavedSampleModel;
import java.awt.image.SampleModel;
import java.awt.image.SinglePixelPackedSampleModel;
import java.awt.image.WritableRaster;
import sun.java2d.SurfaceData;

/**
 * WritableRasterNative
 * This class exists to wrap a native DataBuffer object.  The
 * standard WritableRaster object assumes that a DataBuffer
 * of a given type (e.g., DataBuffer.TYPE_INT) implies a certain
 * subclass (e.g., DataBufferInt).  But this is not always the
 * case.  DataBufferNative, for example, may allow access to
 * integer-based data, but it is not DataBufferInt (which is a
 * final class and cannot be subclassed).
 * So this class exists simply to allow the WritableRaster
 * functionality for this new kind of DataBuffer object.
 */
public class WritableRasterNative extends WritableRaster {

    public static WritableRasterNative createNativeRaster(SampleModel sm,
                                                          DataBuffer db)
    {
        return new WritableRasterNative(sm, db);
    }

    protected WritableRasterNative(SampleModel sm, DataBuffer db) {
        super(sm, db, new Point(0, 0));
    }

    public static WritableRasterNative createNativeRaster(ColorModel cm,
                                                          SurfaceData sd,
                                                          int width,
                                                          int height)
    {
        SampleModel smHw = null;
        int dataType = 0;
        int scanStride = width;

        switch (cm.getPixelSize()) {
        case 8:
        case 12:
            // 8-bits uses PixelInterleavedSampleModel
            if (cm.getPixelSize() == 8) {
                dataType = DataBuffer.TYPE_BYTE;
            } else {
                dataType = DataBuffer.TYPE_USHORT;
            }
            int[] bandOffsets = new int[1];
            bandOffsets[0] = 0;
            smHw = new PixelInterleavedSampleModel(dataType, width,
                                                   height,
                                                   1, scanStride,
                                                   bandOffsets);
            break;

            // all others use SinglePixelPackedSampleModel
        case 15:
        case 16:
            dataType = DataBuffer.TYPE_USHORT;
            int[] bitMasks = new int[3];
            DirectColorModel dcm = (DirectColorModel)cm;
            bitMasks[0] = dcm.getRedMask();
            bitMasks[1] = dcm.getGreenMask();
            bitMasks[2] = dcm.getBlueMask();
            smHw = new SinglePixelPackedSampleModel(dataType, width,
                                                    height, scanStride,
                                                    bitMasks);
            break;
        case 24:
        case 32:
            dataType = DataBuffer.TYPE_INT;
            bitMasks = new int[3];
            dcm = (DirectColorModel)cm;
            bitMasks[0] = dcm.getRedMask();
            bitMasks[1] = dcm.getGreenMask();
            bitMasks[2] = dcm.getBlueMask();
            smHw = new SinglePixelPackedSampleModel(dataType, width,
                                                    height, scanStride,
                                                    bitMasks);
            break;
        default:
            throw new InternalError("Unsupported depth " +
                                    cm.getPixelSize());
        }

        DataBuffer dbn = new DataBufferNative(sd, dataType,
                                              width, height);
        return new WritableRasterNative(smHw, dbn);
    }
}
