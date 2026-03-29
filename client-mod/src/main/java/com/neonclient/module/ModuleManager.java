package com.neonclient.module;

import com.neonclient.hud.HudManager;
import com.neonclient.module.impl.CoordinatesModule;
import com.neonclient.module.impl.FpsCounterModule;
import com.neonclient.module.impl.KeystrokesModule;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ModuleManager {
    private final List<Module> modules = new ArrayList<>();

    public void registerDefaults(HudManager hudManager) {
        modules.clear();
        modules.add(new FpsCounterModule(hudManager));
        modules.add(new CoordinatesModule(hudManager));
        modules.add(new KeystrokesModule(hudManager));
    }

    public List<Module> getModules() {
        return Collections.unmodifiableList(modules);
    }
}
