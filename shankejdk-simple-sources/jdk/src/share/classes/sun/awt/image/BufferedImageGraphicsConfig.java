/*
 * Copyright (c) 1997, 2005, Oracle and/or its affiliates. All rights reserved.
 */

package sun.awt.image;

import java.awt.AWTException;
import java.awt.Component;
import java.awt.Graphics2D;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsDevice;
import java.awt.ImageCapabilities;
import java.awt.Rectangle;
import java.awt.Transparency;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.DirectColorModel;
import java.awt.image.Raster;
import java.awt.image.VolatileImage;
import java.awt.image.WritableRaster;

public class BufferedImageGraphicsConfig
    extends GraphicsConfiguration
{
    private static final int numconfigs = BufferedImage.TYPE_BYTE_BINARY;
    private static BufferedImageGraphicsConfig configs[] =
        new BufferedImageGraphicsConfig[numconfigs];

    public static BufferedImageGraphicsConfig getConfig(BufferedImage bImg) {
        BufferedImageGraphicsConfig ret;
        int type = bImg.getType();
        if (type > 0 && type < numconfigs) {
            ret = configs[type];
            if (ret != null) {
                return ret;
            }
        }
        ret = new BufferedImageGraphicsConfig(bImg, null);
        if (type > 0 && type < numconfigs) {
            configs[type] = ret;
        }
        return ret;
    }

    GraphicsDevice gd;
    ColorModel model;
    Raster raster;
    int width, height;

    public BufferedImageGraphicsConfig(BufferedImage bufImg, Component comp) {
        if (comp == null) {
            this.gd = new BufferedImageDevice(this);
        } else {
            Graphics2D g2d = (Graphics2D)comp.getGraphics();
            this.gd = g2d.getDeviceConfiguration().getDevice();
        }
        this.model = bufImg.getColorModel();
        this.raster = bufImg.getRaster().createCompatibleWritableRaster(1, 1);
        this.width = bufImg.getWidth();
        this.height = bufImg.getHeight();
    }

    /**
     * Return the graphics device associated with this configuration.
     */
    public GraphicsDevice getDevice() {
        return gd;
    }

    /**
     * Returns a BufferedImage with channel layout and color model
     * compatible with this graphics configuration.  This method
     * has nothing to do with memory-mapping
     * a device.  This BufferedImage has
     * a layout and color model
     * that is closest to this native device configuration and thus
     * can be optimally blitted to this device.
     */
    public BufferedImage createCompatibleImage(int width, int height) {
        WritableRaster wr = raster.createCompatibleWritableRaster(width, height);
        return new BufferedImage(model, wr, model.isAlphaPremultiplied(), null);
    }

    /**
     * Returns the color model associated with this configuration.
     */
    public ColorModel getColorModel() {
        return model;
    }

    /**
     * Returns the color model associated with this configuration that
     * supports the specified transparency.
     */
    public ColorModel getColorModel(int transparency) {

        if (model.getTransparency() == transparency) {
            return model;
        }
        switch (transparency) {
        case Transparency.OPAQUE:
            return new DirectColorModel(24, 0xff0000, 0xff00, 0xff);
        case Transparency.BITMASK:
            return new DirectColorModel(25, 0xff0000, 0xff00, 0xff, 0x1000000);
        case Transparency.TRANSLUCENT:
            return ColorModel.getRGBdefault();
        default:
            return null;
        }
    }

    /**
     * Returns the default Transform for this configuration.  This
     * Transform is typically the Identity transform for most normal
     * screens.  Device coordinates for screen and printer devices will
     * have the origin in the upper left-hand corner of the target region of
     * the device, with X coordinates
     * increasing to the right and Y coordinates increasing downwards.
     * For image buffers, this Transform will be the Identity transform.
     */
    public AffineTransform getDefaultTransform() {
        return new AffineTransform();
    }

    /**
     *
     * Returns a Transform that can be composed with the default Transform
     * of a Graphics2D so that 72 units in user space will equal 1 inch
     * in device space.
     * Given a Graphics2D, g, one can reset the transformation to create
     * such a mapping by using the following pseudocode:
     * <pre>
     *      GraphicsConfiguration gc = g.getGraphicsConfiguration();
     *
     *      g.setTransform(gc.getDefaultTransform());
     *      g.transform(gc.getNormalizingTransform());
     * </pre>
     * Note that sometimes this Transform will be identity (e.g. for
     * printers or metafile output) and that this Transform is only
     * as accurate as the information supplied by the underlying system.
     * For image buffers, this Transform will be the Identity transform,
     * since there is no valid distance measurement.
     */
    public AffineTransform getNormalizingTransform() {
        return new AffineTransform();
    }

    public Rectangle getBounds() {
        return new Rectangle(0, 0, width, height);
    }
}
