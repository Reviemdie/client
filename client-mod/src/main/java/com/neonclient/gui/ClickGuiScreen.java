package com.neonclient.gui;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.neonclient.core.NeonClientMod;
import com.neonclient.hud.HudElement;
import com.neonclient.module.Module;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.util.text.StringTextComponent;

public class ClickGuiScreen extends Screen {
    private HudElement dragged;

    public ClickGuiScreen() {
        super(new StringTextComponent("Neon Client"));
    }

    @Override
    protected void init() {
        int panelX = this.width / 2 - 110;
        int panelY = this.height / 2 - 80;
        int index = 0;

        for (Module module : NeonClientMod.MODULE_MANAGER.getModules()) {
            int y = panelY + 20 + (index++ * 24);
            addButton(new Button(panelX, y, 220, 20, label(module), button -> {
                module.toggle();
                button.setMessage(label(module));
            }));
        }
    }

    @Override
    public void render(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks) {
        fill(matrixStack, 0, 0, this.width, this.height, 0xC0000000);

        int panelX = this.width / 2 - 120;
        int panelY = this.height / 2 - 90;
        fill(matrixStack, panelX, panelY, panelX + 240, panelY + 180, 0xDD101010);
        fill(matrixStack, panelX, panelY, panelX + 240, panelY + 24, 0xDD00AA66);
        drawCenteredString(matrixStack, this.font, "NEON CLIENT", this.width / 2, panelY + 8, 0x000000);
        drawString(matrixStack, this.font, "Drag HUD elements directly", panelX + 10, panelY + 155, 0x00FF88);

        for (HudElement element : NeonClientMod.HUD_MANAGER.getElements()) {
            int x = element.getX();
            int y = element.getY();
            int w = element.getWidth();
            int h = element.getHeight();
            fill(matrixStack, x - 1, y - 1, x + w + 1, y + h + 1, 0x9900FF88);
            drawString(matrixStack, this.font, element.previewLabel(), x + 2, y + 2, 0x00111111);
        }

        super.render(matrixStack, mouseX, mouseY, partialTicks);
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        if (button == 0) {
            for (HudElement element : NeonClientMod.HUD_MANAGER.getElements()) {
                if (element.contains(mouseX, mouseY)) {
                    dragged = element;
                    dragged.startDrag(mouseX, mouseY);
                    return true;
                }
            }
        }

        return super.mouseClicked(mouseX, mouseY, button);
    }

    @Override
    public boolean mouseDragged(double mouseX, double mouseY, int button, double dragX, double dragY) {
        if (dragged != null) {
            dragged.dragTo(mouseX, mouseY);
            return true;
        }
        return super.mouseDragged(mouseX, mouseY, button, dragX, dragY);
    }

    @Override
    public boolean mouseReleased(double mouseX, double mouseY, int button) {
        if (dragged != null) {
            dragged.stopDrag();
            dragged = null;
        }
        return super.mouseReleased(mouseX, mouseY, button);
    }

    @Override
    public boolean isPauseScreen() {
        return false;
    }

    private StringTextComponent label(Module module) {
        return new StringTextComponent((module.isEnabled() ? "[ON] " : "[OFF] ") + module.getName());
    }
}
