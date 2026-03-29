package com.neonclient.module.impl;

import com.neonclient.hud.HudManager;
import com.neonclient.hud.KeystrokesHudElement;
import com.neonclient.module.Module;

public class KeystrokesModule extends Module {
    public KeystrokesModule(HudManager hudManager) {
        super("Keystrokes", true);
        hudManager.add(new KeystrokesHudElement("keystrokes", 8, 46));
    }
}
