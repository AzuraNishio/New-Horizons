package org.copycraftDev.new_horizons.client;

import foundry.veil.Veil;
import nazario.liby.LibyClientEntry;
import nazario.liby.api.registry.runtime.recipe.types.LibyShapelessCraftingRecipe;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.fabricmc.fabric.api.resource.ResourceManagerHelper;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.resource.ResourceFactory;
import net.minecraft.resource.ResourceType;
import net.minecraft.scoreboard.ScoreboardCriterion;
import org.joml.Matrix4f;
import org.lwjgl.glfw.GLFW;

public class NewHorizonsClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        Veil.init();
        ModShaders.registerShaders();



        KeyBinding flashlightKeyBinding = new KeyBinding(
                "key.new_horizons.override_my_ass",   // Translation key
                GLFW.GLFW_KEY_F5,                // Default key
                "category.new_horizons"         // Keybinding category
        );
        KeyBindingHelper.registerKeyBinding(flashlightKeyBinding);


    }

}
