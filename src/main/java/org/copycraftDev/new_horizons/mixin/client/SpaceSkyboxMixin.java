package org.copycraftDev.new_horizons.mixin.client;

import com.mojang.blaze3d.systems.RenderSystem;
import foundry.veil.api.client.render.shader.ShaderManager;
import foundry.veil.api.client.render.shader.ShaderSourceSet;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.*;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RotationAxis;
import org.joml.Matrix4f;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.HashSet;

@Mixin(WorldRenderer.class)
public class SpaceSkyboxMixin {
    @Unique
    private static final Identifier SKYBOX_TEXTURE = Identifier.of( "textures/skyboxes/space_skybox");
    @Inject(method = "renderSky",
            at = @At(value = "INVOKE",
                    target = "Lcom/mojang/blaze3d/systems/RenderSystem;setShaderColor(FFFF)V",
                    ordinal = 0,
                    shift = At.Shift.BY),
            cancellable = true)
    public void injectCustomSky0(Matrix4f matrix4f, Matrix4f projectionMatrix, float tickDelta, Camera camera, boolean thickFog, Runnable fogCallback, CallbackInfo ci) {
        MinecraftClient client = MinecraftClient.getInstance();
        ClientWorld world = client.world;

        MatrixStack matrixStack = new MatrixStack();
        matrixStack.multiplyPositionMatrix(matrix4f);
        Tessellator tessellator = Tessellator.getInstance();

        Float[] fs = new Float[]{1F, 0F, 0F, 0.5F}; // Example default values


            // Example: Add your own rendering logic here
            // RenderSystem.setShader(GameRenderer::getPositionColorShader);
            // Render your skybox, overlay, or custom effect

        //=================== meow ==================================================================================

        RenderSystem.setShader(GameRenderer::getPositionColorProgram);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        matrixStack.push();
        matrixStack.multiply(RotationAxis.POSITIVE_X.rotationDegrees(90.0F));
        float i = MathHelper.sin(world.getSkyAngleRadians(tickDelta)) < 0.0F ? 180.0F : 0.0F;
        matrixStack.multiply(RotationAxis.POSITIVE_Z.rotationDegrees(i));
        matrixStack.multiply(RotationAxis.POSITIVE_Z.rotationDegrees(90.0F));
        float j = fs[0];
        float k = fs[1];
        float l = fs[2];
        Matrix4f matrix4f2 = matrixStack.peek().getPositionMatrix();
        BufferBuilder bufferBuilder = tessellator.begin(VertexFormat.DrawMode.TRIANGLE_FAN, VertexFormats.POSITION_COLOR);
        bufferBuilder.vertex(matrix4f2, 0.0F, 100.0F, 0.0F).color(j, k, l, fs[3]);
        int m = 16;

//        for(int n = 0; n <= 4; ++n) {
//            float o = (float)n * ((float)Math.PI * 2F) / 16.0F;
//            float p = MathHelper.sin(o);
//            float q = MathHelper.cos(o);
//            bufferBuilder.vertex(matrix4f2, p * 120.0F, q * 120.0F, -q * 40.0F).color(fs[0], fs[1], fs[2], 0.0F);
//        }

        float x = (float) camera.getPos().z;
        float y = (float) -camera.getPos().x;
        float z = (float) camera.getPos().y;

        RenderSystem.enableBlend();
        RenderSystem.depthMask(true);
        bufferBuilder.vertex(matrix4f2, 1+x, 1+y, 1+z).color(fs[0], fs[1], fs[2], fs[3]);
        bufferBuilder.vertex(matrix4f2, -1+x, 1+y, 1+z).color(fs[0], fs[1], fs[2], fs[3]);
        bufferBuilder.vertex(matrix4f2, -1+x, -1+y, 1+z).color(fs[0], fs[1], fs[2], fs[3]);
        bufferBuilder.vertex(matrix4f2, 1+x, -1+y, 1+z).color(fs[0], fs[1], fs[2], fs[3]);

        BufferRenderer.drawWithGlobalProgram(bufferBuilder.end());
        matrixStack.pop();

        //================== meow ===================================================================================

        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1F);
        RenderSystem.depthMask(true);
        RenderSystem.setShaderFogColor(0,0,0);

        ci.cancel();


       // }
    }
}
