/*
 * Copyright (c) 2011, 2012, Oracle and/or its affiliates. All rights reserved.
 */

package com.apple.laf;

import javax.swing.JComponent;
import javax.swing.plaf.ComponentUI;

import com.apple.laf.AquaUtils.*;

public class AquaButtonToggleUI extends AquaButtonUI {
    // Create PLAF
    static final RecyclableSingleton<AquaButtonToggleUI> aquaToggleButtonUI = new RecyclableSingletonFromDefaultConstructor<AquaButtonToggleUI>(AquaButtonToggleUI.class);
    public static ComponentUI createUI(final JComponent b) {
        return aquaToggleButtonUI.get();
    }

    protected String getPropertyPrefix() {
        return "ToggleButton" + ".";
    }
}
