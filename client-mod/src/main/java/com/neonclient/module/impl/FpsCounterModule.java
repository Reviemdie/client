package com.neonclient.module.impl;

import com.neonclient.hud.HudManager;
import com.neonclient.hud.TextHudElement;
import com.neonclient.module.Module;
import net.minecraft.client.Minecraft;

public class FpsCounterModule extends Module {
    public FpsCounterModule(HudManager hudManager) {
        super("FPS Counter", true);
        TextHudElement element = new TextHudElement("fps", 8, 8,
                () -> "FPS: " + Minecraft.getDebugFPS());
        hudManager.add(element);
    }
}
