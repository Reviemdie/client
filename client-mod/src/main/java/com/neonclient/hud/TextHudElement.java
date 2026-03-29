package com.neonclient.hud;

import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.AbstractGui;
import net.minecraft.client.gui.FontRenderer;

import java.util.function.Supplier;

public class TextHudElement extends HudElement {
    private final Supplier<String> textSupplier;

    public TextHudElement(String id, int x, int y, Supplier<String> textSupplier) {
        super(id, x, y, 100, 12);
        this.textSupplier = textSupplier;
    }

    @Override
    public void render(MatrixStack matrixStack) {
        FontRenderer renderer = Minecraft.getInstance().font;
        String text = textSupplier.get();
        this.width = Math.max(50, renderer.width(text) + 8);
        this.height = 14;

        AbstractGui.fill(matrixStack, x - 2, y - 2, x + width, y + height, 0x88000000);
        renderer.draw(matrixStack, text, x + 2, y + 1, 0x00FF88);
    }

    @Override
    public String previewLabel() {
        return textSupplier.get();
    }
}
