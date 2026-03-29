package com.neonclient.core;

import com.neonclient.gui.ClickGuiScreen;
import com.neonclient.hud.HudManager;
import com.neonclient.module.ModuleManager;
import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.KeyBinding;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.settings.KeyConflictContext;
import net.minecraftforge.client.settings.KeyModifier;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.Mod;
import org.lwjgl.glfw.GLFW;

@Mod(NeonClientMod.MOD_ID)
public class NeonClientMod {
    public static final String MOD_ID = "neonclient";
    public static final ModuleManager MODULE_MANAGER = new ModuleManager();
    public static final HudManager HUD_MANAGER = new HudManager();
    public static KeyBinding OPEN_GUI_KEY;

    public NeonClientMod() {
        MinecraftForge.EVENT_BUS.register(this);
        MODULE_MANAGER.registerDefaults(HUD_MANAGER);
    }

    @Mod.EventBusSubscriber(modid = MOD_ID, value = Dist.CLIENT)
    public static class Client {
        private static boolean keyRegistered = false;

        @SubscribeEvent
        public static void onClientTick(TickEvent.ClientTickEvent event) {
            if (!keyRegistered && Minecraft.getInstance().options != null) {
                OPEN_GUI_KEY = new KeyBinding(
                        "key.neonclient.opengui",
                        KeyConflictContext.IN_GAME,
                        KeyModifier.NONE,
                        GLFW.GLFW_KEY_RIGHT_SHIFT,
                        "key.categories.neonclient"
                );
                ClientRegistry.registerKeyBinding(OPEN_GUI_KEY);
                keyRegistered = true;
            }

            if (event.phase == TickEvent.Phase.END && OPEN_GUI_KEY != null && OPEN_GUI_KEY.consumeClick()) {
                Minecraft.getInstance().setScreen(new ClickGuiScreen());
            }
        }

        @SubscribeEvent
        public static void onKeyInput(InputEvent.KeyInputEvent event) {
            // reserved for per-module keybinds
        }

        @SubscribeEvent
        public static void onRenderHud(RenderGameOverlayEvent.Post event) {
            if (event.getType() == RenderGameOverlayEvent.ElementType.ALL) {
                HUD_MANAGER.render(event.getMatrixStack());
            }
        }
    }
}
