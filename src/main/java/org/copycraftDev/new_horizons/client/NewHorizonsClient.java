package org.copycraftDev.new_horizons.client;

import foundry.veil.Veil;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.option.KeyBinding;
import org.copycraftDev.new_horizons.client.rendering.LazuliHudRenderStep;
import org.copycraftDev.new_horizons.client.rendering.ModShaders;
import org.lwjgl.glfw.GLFW;

public class NewHorizonsClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        Veil.init();
        LazuliHudRenderStep.register();



        KeyBinding flashlightKeyBinding = new KeyBinding(
                "key.new_horizons.override_my_ass",   // Translation key
                GLFW.GLFW_KEY_F5,                // Default key
                "category.new_horizons"         // Keybinding category
        );
        KeyBindingHelper.registerKeyBinding(flashlightKeyBinding);


    }

}
