/*
 * Copyright (c) 2003, 2017, Oracle and/or its affiliates. All rights reserved.
 */

package sun.awt.X11;

import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Point;
import java.awt.Window;
import java.awt.peer.MouseInfoPeer;

public class XMouseInfoPeer implements MouseInfoPeer {

    /**
     * Package-private constructor to prevent instantiation.
     */
    XMouseInfoPeer() {
    }

    public int fillPointWithCoords(Point point) {
        long display = XToolkit.getDisplay();
        GraphicsEnvironment ge = GraphicsEnvironment.
                                     getLocalGraphicsEnvironment();
        GraphicsDevice[] gds = ge.getScreenDevices();
        int gdslen = gds.length;

        XToolkit.awtLock();
        try {
            for (int i = 0; i < gdslen; i++) {
                long screenRoot = XlibWrapper.RootWindow(display, i);
                boolean pointerFound = XlibWrapper.XQueryPointer(
                                           display, screenRoot,
                                           XlibWrapper.larg1,  // root_return
                                           XlibWrapper.larg2,  // child_return
                                           XlibWrapper.larg3,  // xr_return
                                           XlibWrapper.larg4,  // yr_return
                                           XlibWrapper.larg5,  // xw_return
                                           XlibWrapper.larg6,  // yw_return
                                           XlibWrapper.larg7); // mask_return
                if (pointerFound) {
                    point.x = Native.getInt(XlibWrapper.larg3);
                    point.y = Native.getInt(XlibWrapper.larg4);
                    return i;
                }
            }
        } finally {
            XToolkit.awtUnlock();
        }

        // this should never happen
        assert false : "No pointer found in the system.";
        return 0;
    }

    public boolean isWindowUnderMouse(Window w) {
        if (w == null) {
            return false;
        }
        XWindow peer = (XWindow) w.getPeer();
        if (peer == null) {
            return false;
        }
        long display = XToolkit.getDisplay();
        long contentWindow = peer.getContentWindow();
        long parent = XlibUtil.getParentWindow(contentWindow);

        XToolkit.awtLock();
        try
        {
            boolean windowOnTheSameScreen = XlibWrapper.XQueryPointer(display, parent,
                                  XlibWrapper.larg1, // root_return
                                  XlibWrapper.larg8, // child_return
                                  XlibWrapper.larg3, // root_x_return
                                  XlibWrapper.larg4, // root_y_return
                                  XlibWrapper.larg5, // win_x_return
                                  XlibWrapper.larg6, // win_y_return
                                  XlibWrapper.larg7); //  mask_return
            long siblingWindow = Native.getWindow(XlibWrapper.larg8);
            return (siblingWindow == contentWindow && windowOnTheSameScreen);
        }
        finally
        {
            XToolkit.awtUnlock();
        }
    }
}
