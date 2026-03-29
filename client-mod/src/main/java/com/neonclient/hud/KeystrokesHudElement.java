package com.neonclient.hud;

import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.AbstractGui;

public class KeystrokesHudElement extends HudElement {
    public KeystrokesHudElement(String id, int x, int y) {
        super(id, x, y, 64, 64);
    }

    @Override
    public void render(MatrixStack matrixStack) {
        Minecraft mc = Minecraft.getInstance();

        drawKey(matrixStack, "W", x + 22, y, mc.options.keyUp.isDown());
        drawKey(matrixStack, "A", x, y + 22, mc.options.keyLeft.isDown());
        drawKey(matrixStack, "S", x + 22, y + 22, mc.options.keyDown.isDown());
        drawKey(matrixStack, "D", x + 44, y + 22, mc.options.keyRight.isDown());
        drawKey(matrixStack, "LMB", x, y + 44, mc.options.keyAttack.isDown(), 30);
        drawKey(matrixStack, "RMB", x + 34, y + 44, mc.options.keyUse.isDown(), 30);
    }

    private void drawKey(MatrixStack stack, String label, int keyX, int keyY, boolean active) {
        drawKey(stack, label, keyX, keyY, active, 20);
    }

    private void drawKey(MatrixStack stack, String label, int keyX, int keyY, boolean active, int keyWidth) {
        int color = active ? 0xAA00FF88 : 0xAA202020;
        AbstractGui.fill(stack, keyX, keyY, keyX + keyWidth, keyY + 20, color);
        Minecraft.getInstance().font.draw(stack, label, keyX + 5, keyY + 6, 0xD6FFE9);
    }

    @Override
    public String previewLabel() {
        return "Keystrokes";
    }
}
