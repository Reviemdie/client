package com.neonclient.module.impl;

import com.neonclient.hud.HudManager;
import com.neonclient.hud.TextHudElement;
import com.neonclient.module.Module;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.player.ClientPlayerEntity;

public class CoordinatesModule extends Module {
    public CoordinatesModule(HudManager hudManager) {
        super("Coordinates", true);
        TextHudElement element = new TextHudElement("coords", 8, 26, () -> {
            ClientPlayerEntity player = Minecraft.getInstance().player;
            if (player == null) {
                return "XYZ: 0 / 0 / 0";
            }
            return String.format("XYZ: %.1f / %.1f / %.1f", player.getX(), player.getY(), player.getZ());
        });
        hudManager.add(element);
    }
}
