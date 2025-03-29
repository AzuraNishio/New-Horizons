package org.copycraftDev.new_horizons.client.rendering;

import net.minecraft.client.render.VertexFormats;

public class ModShaders {
    public static String PLANET1_SHADER = "rendertype_test_planet";


    public static void registerShaders(){
        LazuliShaderRegistry.registerShader(PLANET1_SHADER, VertexFormats.POSITION_TEXTURE_COLOR_NORMAL);
    }
}
