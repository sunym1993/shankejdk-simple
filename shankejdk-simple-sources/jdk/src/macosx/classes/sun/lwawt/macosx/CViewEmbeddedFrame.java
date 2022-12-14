/*
 * Copyright (c) 2012, 2014, Oracle and/or its affiliates. All rights reserved.
 */


package sun.lwawt.macosx;

import java.awt.AWTKeyStroke;
import java.awt.Toolkit;
import java.lang.reflect.InvocationTargetException;

import sun.awt.EmbeddedFrame;
import sun.lwawt.LWWindowPeer;

/*
 * The CViewEmbeddedFrame class is used in the SWT_AWT bridge.
 * This is a part of public API and should not be renamed or moved
 */
public class CViewEmbeddedFrame extends EmbeddedFrame {

    private final long nsViewPtr;

    private boolean isActive = false;

    public CViewEmbeddedFrame(long nsViewPtr) {
        this.nsViewPtr = nsViewPtr;
    }

    @SuppressWarnings("deprecation")
    @Override
    public void addNotify() {
        if (getPeer() == null) {
            LWCToolkit toolkit = (LWCToolkit) Toolkit.getDefaultToolkit();
            setPeer(toolkit.createEmbeddedFrame(this));
        }
        super.addNotify();
    }

    public long getEmbedderHandle() {
        return nsViewPtr;
    }

    @Override
    public void registerAccelerator(AWTKeyStroke awtks) {
    }

    @Override
    public void unregisterAccelerator(AWTKeyStroke awtks) {
    }

    public boolean isParentWindowActive() {
        return isActive;
    }

    /*
     * Synthetic event delivery for focus management
     */
    @Override
    public void synthesizeWindowActivation(boolean activated) {
        if (isActive != activated) {
            isActive = activated;
            ((LWWindowPeer)getPeer()).notifyActivation(activated, null);
        }
    }

    /*
     * Initializes the embedded frame bounds and validates a component.
     * Designed to be called from the main thread
     * This method should be called once from the initialization of the SWT_AWT Bridge
     */
    @SuppressWarnings("deprecation")
    public void validateWithBounds(final int x, final int y, final int width, final int height) {
        try {
            LWCToolkit.invokeAndWait(new Runnable() {
                @Override
                public void run() {
                    ((LWWindowPeer) getPeer()).setBoundsPrivate(0, 0, width, height);
                    validate();
                    setVisible(true);
                }
            }, this);
        } catch (InvocationTargetException ex) {}
    }
}
