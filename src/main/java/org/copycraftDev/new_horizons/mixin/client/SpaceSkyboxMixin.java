package org.copycraftDev.new_horizons.mixin.client;

import com.mojang.blaze3d.systems.RenderSystem;
import foundry.veil.Veil;
import foundry.veil.api.client.render.VeilRenderSystem;
import foundry.veil.api.client.render.shader.ShaderManager;
import foundry.veil.api.client.render.shader.ShaderSourceSet;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gl.ShaderProgram;
import net.minecraft.client.render.*;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RotationAxis;
import org.copycraftDev.new_horizons.client.LazuliShaderRegistry;
import org.copycraftDev.new_horizons.client.ModShaders;
import org.joml.Matrix4f;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;


import java.util.HashSet;
import java.util.function.Supplier;

@Mixin(WorldRenderer.class)
public class SpaceSkyboxMixin {

    @Unique
    private static net.minecraft.client.gl.ShaderProgram TEST_SHADER = LazuliShaderRegistry.getShader(ModShaders.PLANET1_SHADER);
    @Unique
    private static final Identifier TEST_TEXTURE = Identifier.of("new_horizons", "textures/test_textures/texture_test.png");
    @Unique
    private static final Identifier TEST_TEXTURE2 = Identifier.of("new_horizons", "textures/test_textures/texture_test2.png");


    @Unique
    private static final Identifier SKYBOX_TEXTURE = Identifier.of( "textures/skyboxes/space_skybox");


    @Inject(method = "renderSky",
            at = @At(value = "INVOKE",
                    target = "Lcom/mojang/blaze3d/systems/RenderSystem;setShaderColor(FFFF)V",
                    ordinal = 0,
                    shift = At.Shift.BY),
            cancellable = true)
    public void injectCustomSky0(Matrix4f matrix4f, Matrix4f projectionMatrix, float tickDelta, Camera camera, boolean thickFog, Runnable fogCallback, CallbackInfo ci) {
        //==================================[Fetching thingies]=========================================================
        MinecraftClient client = MinecraftClient.getInstance();
        ClientWorld world = client.world;
        Tessellator tessellator = Tessellator.getInstance();

        //==================================[Matrix black magic]=========================================================
        MatrixStack matrixStack = new MatrixStack();
        matrixStack.multiplyPositionMatrix(matrix4f);
        matrixStack.push();
        matrixStack.multiply(RotationAxis.POSITIVE_X.rotationDegrees(90.0F));
//        float i = MathHelper.sin(world.getSkyAngleRadians(tickDelta)) < 0.0F ? 180.0F : 0.0F;
//        matrixStack.multiply(RotationAxis.POSITIVE_Z.rotationDegrees(i));
        matrixStack.multiply(RotationAxis.POSITIVE_Z.rotationDegrees(90.0F));
        Matrix4f matrix4f2 = matrixStack.peek().getPositionMatrix();




        //==================================[RenderSystem setup]=========================================================
        TEST_SHADER = LazuliShaderRegistry.getShader(ModShaders.PLANET1_SHADER);
        Supplier<net.minecraft.client.gl.ShaderProgram> meow = () -> TEST_SHADER;
        RenderSystem.setShader(meow);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.enableBlend();
        RenderSystem.depthMask(true);
        RenderSystem.disableCull();


        //==================================[Geometry]=========================================================
        float x = (float) -camera.getPos().z;
        float y = (float) camera.getPos().x;
        float z = (float) camera.getPos().y;

        BufferBuilder bufferBuilder = tessellator.begin(VertexFormat.DrawMode.TRIANGLE_FAN, VertexFormats.POSITION);

        bufferBuilder.vertex(matrix4f2, 1+x, 1+y, 1+z).light(1);
        bufferBuilder.vertex(matrix4f2, -1+x, 1+y, 1+z).light(1);
        bufferBuilder.vertex(matrix4f2, -1+x, -1+y, 1+z).light(1);
        bufferBuilder.vertex(matrix4f2, 1+x, -1+y, 1+z).light(1);



        //==================================[closing]=========================================================
        BufferRenderer.drawWithGlobalProgram(bufferBuilder.end());
        matrixStack.pop();
        //making things ready for next render layer
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1F);
        RenderSystem.depthMask(true);
        RenderSystem.setShaderFogColor(0,0,0);
        RenderSystem.setShader(GameRenderer::getPositionColorProgram);

        ci.cancel();


       // }
    }
}
