package com.neonclient.hud;

import com.mojang.blaze3d.matrix.MatrixStack;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class HudManager {
    private final List<HudElement> elements = new ArrayList<>();

    public void add(HudElement element) {
        if (!elements.contains(element)) {
            elements.add(element);
        }
    }

    public List<HudElement> getElements() {
        return Collections.unmodifiableList(elements);
    }

    public void render(MatrixStack matrixStack) {
        for (HudElement element : elements) {
            element.render(matrixStack);
        }
    }
}
