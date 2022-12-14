/*
 * Copyright (c) 2011, 2013, Oracle and/or its affiliates. All rights reserved.
 */

package sun.lwawt.macosx;

import java.awt.*;
import java.awt.peer.*;

import sun.awt.CGraphicsDevice;

class CRobot implements RobotPeer {
    private static final int MOUSE_LOCATION_UNKNOWN      = -1;

    private final CGraphicsDevice fDevice;
    private int mouseLastX = MOUSE_LOCATION_UNKNOWN;
    private int mouseLastY = MOUSE_LOCATION_UNKNOWN;

    // OS X doesn't generate dragged event as a result of button press and
    // mouse move events. This means that we have to track buttons state
    // in order to generate dragged events ourselves.
    private int mouseButtonsState = 0;

    /**
     * Uses the given GraphicsDevice as the coordinate system for subsequent
     * coordinate calls.
     */
    public CRobot(Robot r, CGraphicsDevice d) {
        fDevice = d;
        initRobot();
    }

    @Override
    public void dispose() {
    }

    /**
     * Moves mouse pointer to given screen coordinates.
     * @param x X position
     * @param y Y position
     */
    @Override
    public void mouseMove(int x, int y) {
        mouseLastX = x;
        mouseLastY = y;

        mouseEvent(fDevice.getCGDisplayID(), mouseLastX, mouseLastY,
                   mouseButtonsState, true, true);
    }

    /**
     * Presses one or more mouse buttons.
     *
     * @param buttons the button mask (combination of
     * <code>InputEvent.BUTTON1/2/3_MASK</code>)
     */
    @Override
    public void mousePress(int buttons) {
        mouseButtonsState |= buttons;
        checkMousePos();
        mouseEvent(fDevice.getCGDisplayID(), mouseLastX, mouseLastY,
                   buttons, true, false);
    }

    /**
     * Releases one or more mouse buttons.
     *
     * @param buttons the button mask (combination of
     * <code>InputEvent.BUTTON1/2/3_MASK</code>)
     */
    @Override
    public void mouseRelease(int buttons) {
        mouseButtonsState &= ~buttons;
        checkMousePos();
        mouseEvent(fDevice.getCGDisplayID(), mouseLastX, mouseLastY,
                   buttons, false, false);
    }

    /**
     * Set unknown mouse location, if needed.
     */
    private void checkMousePos() {
        if (mouseLastX == MOUSE_LOCATION_UNKNOWN ||
                mouseLastY == MOUSE_LOCATION_UNKNOWN) {

            Rectangle deviceBounds = fDevice.getDefaultConfiguration().getBounds();
            Point mousePos = CCursorManager.getInstance().getCursorPosition();

            if (mousePos.x < deviceBounds.x) {
                mousePos.x = deviceBounds.x;
            }
            else if (mousePos.x > deviceBounds.x + deviceBounds.width) {
                mousePos.x = deviceBounds.x + deviceBounds.width;
            }

            if (mousePos.y < deviceBounds.y) {
                mousePos.y = deviceBounds.y;
            }
            else if (mousePos.y > deviceBounds.y + deviceBounds.height) {
                mousePos.y = deviceBounds.y + deviceBounds.height;
            }

            mouseLastX = mousePos.x;
            mouseLastY = mousePos.y;
        }
    }

    @Override
    public native void mouseWheel(int wheelAmt);

    /**
     * Presses a given key.
     * <p>
     * Key codes that have more than one physical key associated with them
     * (e.g. <code>KeyEvent.VK_SHIFT</code> could mean either the
     * left or right shift key) will map to the left key.
     * <p>
     * Assumes that the
     * peer implementations will throw an exception for other bogus
     * values e.g. -1, 999999
     *
     * @param keycode the key to press (e.g. <code>KeyEvent.VK_A</code>)
     */
    @Override
    public void keyPress(final int keycode) {
        keyEvent(keycode, true);
    }

    /**
     * Releases a given key.
     * <p>
     * Key codes that have more than one physical key associated with them
     * (e.g. <code>KeyEvent.VK_SHIFT</code> could mean either the
     * left or right shift key) will map to the left key.
     * <p>
     * Assumes that the
     * peer implementations will throw an exception for other bogus
     * values e.g. -1, 999999
     *
     * @param keycode the key to release (e.g. <code>KeyEvent.VK_A</code>)
     */
    @Override
    public void keyRelease(final int keycode) {
        keyEvent(keycode, false);
    }

    /**
     * Returns the color of a pixel at the given screen coordinates.
     * @param x X position of pixel
     * @param y Y position of pixel
     * @return color of the pixel
     */
    @Override
    public int getRGBPixel(int x, int y) {
        int c[] = new int[1];
        getScreenPixels(new Rectangle(x, y, 1, 1), c);
        return c[0];
    }

    /**
     * Creates an image containing pixels read from the screen.
     * @param bounds the rect to capture in screen coordinates
     * @return the array of pixels
     */
    @Override
    public int [] getRGBPixels(final Rectangle bounds) {
        int c[] = new int[bounds.width * bounds.height];
        getScreenPixels(bounds, c);

        return c;
    }

    private native void initRobot();
    private native void mouseEvent(int displayID, int lastX, int lastY,
                                   int buttonsState,
                                   boolean isButtonsDownState,
                                   boolean isMouseMove);
    private native void keyEvent(int javaKeyCode, boolean keydown);
    private void getScreenPixels(Rectangle r, int[] pixels){
        nativeGetScreenPixels(r.x, r.y, r.width, r.height, pixels);
    }
    private native void nativeGetScreenPixels(int x, int y, int width, int height, int[] pixels);
}
