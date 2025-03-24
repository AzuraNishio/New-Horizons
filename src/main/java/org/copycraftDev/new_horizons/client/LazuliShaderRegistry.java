package org.copycraftDev.new_horizons.client;

import com.mojang.blaze3d.systems.RenderSystem;
import net.fabricmc.fabric.api.client.rendering.v1.CoreShaderRegistrationCallback;
import net.minecraft.client.gl.ShaderProgram;
import net.minecraft.client.render.VertexFormats;
import net.minecraft.util.Identifier;
import org.copycraftDev.new_horizons.NewHorizonsMain;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LazuliShaderRegistry {
    // Store registered shaders in a map for easy access
    private static final Map<String, ShaderProgram> SHADER_MAP = new HashMap<>();

    /**
     * Registers a shader and stores it in SHADER_MAP.
     *
     * @param name The shader name (e.g., "rendertype_pure_void")
     */
    public static void registerShader(String name) {
        Identifier shaderId = NewHorizonsMain.id(name);

        CoreShaderRegistrationCallback.EVENT.register(ctx -> {
            ctx.register(shaderId, VertexFormats.POSITION_COLOR_TEXTURE_LIGHT, shaderProgram -> {
                SHADER_MAP.put(name, shaderProgram);
                System.out.println("[NewHorizons] Shader '" + name + "' registered!");
            });
        });
    }

    /**
     * Retrieves a registered shader by name.
     *
     * @param name The shader name (e.g., "rendertype_pure_void")
     * @return The corresponding ShaderProgram or null if not registered
     */
    public static ShaderProgram getShader(String name) {
        return SHADER_MAP.get(name);
    }
}
