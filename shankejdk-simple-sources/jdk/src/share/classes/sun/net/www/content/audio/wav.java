/*
 * Copyright (c) 1999, Oracle and/or its affiliates. All rights reserved.
 */

/**
 * Basic .wav audio handler.
 * @author  Jeff Nisewanger
 */
package sun.net.www.content.audio;

import java.net.*;
import java.io.IOException;
import sun.applet.AppletAudioClip;

/**
 * Returns an AppletAudioClip object.
 */
public class wav extends ContentHandler {
    public Object getContent(URLConnection uc) throws IOException {
        return new AppletAudioClip(uc);
    }
}
