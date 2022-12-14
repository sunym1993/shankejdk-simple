/*
 * Copyright (c) 2004, 2014, Oracle and/or its affiliates. All rights reserved.
 */

package sun.awt.windows;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Insets;
import java.awt.Point;


/**
 * This is a stubbed out placeholder class, intended to allow building
 * WindowsLookAndFeel on Unix. This class is never actually called on
 * Unix, and will be deleted when WindowsLookAndFeel is no longer built
 * on Unix.
 *
 * @author Leif Samuelsson
 */
public final class ThemeReader {

    public static boolean isThemed() {
        return false;
    }

    public static boolean isXPStyleEnabled() {
        return false;
    }

    public static void paintBackground(int[] buffer, String widget,
           int part, int state, int x, int y, int w, int h, int stride) {
    }

    public static Insets getThemeMargins(String widget, int part, int state, int marginType) {
        return null;
    }

    public static boolean isThemePartDefined(String widget, int part, int state) {
        return false;
    }

    public static Color getColor(String widget, int part, int state, int property) {
        return null;
    }

    public static int getInt(String widget, int part, int state, int property) {
        return 0;
    }

    public static int getEnum(String widget, int part, int state, int property) {
        return 0;
    }

    public static boolean getBoolean(String widget, int part, int state, int property) {
        return false;
    }

    public static boolean getSysBoolean(String widget, int property) {
        return false;
    }

    public static Point getPoint(String widget, int part, int state, int property) {
        return null;
    }

    public static Dimension getPosition(String widget, int part, int state, int property) {
        return null;
    }

    public static Dimension getPartSize(String widget, int part, int state) {
        return null;
    }

    public static long getThemeTransitionDuration(String widget, int part,
                                       int stateFrom, int stateTo, int propId) {
        return 0;
    }

    public static boolean isGetThemeTransitionDurationDefined() {
        return false;
    }

    public static Insets getThemeBackgroundContentMargins(String widget,
                    int part, int state, int boundingWidth, int boundingHeight) {
        return null;
    }
}
