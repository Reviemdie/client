package com.neonclient.hud;

import com.mojang.blaze3d.matrix.MatrixStack;

public abstract class HudElement {
    private final String id;
    protected int x;
    protected int y;
    protected int width;
    protected int height;
    private boolean dragging;
    private int dragOffsetX;
    private int dragOffsetY;

    protected HudElement(String id, int x, int y, int width, int height) {
        this.id = id;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    public String getId() {
        return id;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public boolean contains(double mouseX, double mouseY) {
        return mouseX >= x && mouseX <= x + width && mouseY >= y && mouseY <= y + height;
    }

    public void startDrag(double mouseX, double mouseY) {
        this.dragging = true;
        this.dragOffsetX = (int) mouseX - x;
        this.dragOffsetY = (int) mouseY - y;
    }

    public void dragTo(double mouseX, double mouseY) {
        if (!dragging) {
            return;
        }
        this.x = (int) mouseX - dragOffsetX;
        this.y = (int) mouseY - dragOffsetY;
    }

    public void stopDrag() {
        this.dragging = false;
    }

    public boolean isDragging() {
        return dragging;
    }

    public abstract void render(MatrixStack matrixStack);

    public abstract String previewLabel();
}
