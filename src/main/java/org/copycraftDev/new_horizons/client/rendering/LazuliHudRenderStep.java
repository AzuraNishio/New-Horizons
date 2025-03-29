package org.copycraftDev.new_horizons.client.rendering;

import com.mojang.blaze3d.systems.RenderSystem;
import net.fabricmc.fabric.api.client.rendering.v1.WorldRenderEvents;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gl.ShaderProgram;
import net.minecraft.client.render.*;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Vec3d;
import org.joml.Matrix4f;

import java.util.function.Supplier;

public class LazuliHudRenderStep {

    private static Camera camera;
    private static Matrix4f matrix4f;
    private static ShaderProgram TEST_SHADER;


    private static final Identifier ALBEDO = Identifier.of("new_horizons", "textures/test_textures/planet_albedo.png");
    private static final Identifier HEIGHT = Identifier.of("new_horizons", "textures/test_textures/planet_albedo.png");

    public static void register() {
        WorldRenderEvents.LAST.register((context) -> {
            // Validate camera & matrix before rendering
            if (camera == null || matrix4f == null) {
                return; // Skip rendering if data isn't set yet
            }

            Vec3d cameraPos = camera.getPos();

            MinecraftClient client = MinecraftClient.getInstance();
            Tessellator tessellator = Tessellator.getInstance();
            BufferBuilder bufferBuilder = tessellator.begin(VertexFormat.DrawMode.QUADS, VertexFormats.POSITION_TEXTURE_COLOR_NORMAL);

            // Load shader
            TEST_SHADER = LazuliShaderRegistry.getShader(ModShaders.PLANET1_SHADER);
            if (TEST_SHADER == null) {
                return; // Shader not loaded yet, skip rendering
            }

            // Set the shader
            Supplier<ShaderProgram> shaderSupplier = () -> TEST_SHADER;
            RenderSystem.setShader(shaderSupplier);
            RenderSystem.setShaderTexture(0, ALBEDO);
            RenderSystem.setShaderTexture(1, HEIGHT);
            RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
            RenderSystem.enableBlend();
            RenderSystem.disableCull();
            RenderSystem.enableDepthTest();
            RenderSystem.depthMask(true);
            RenderSystem.assertOnRenderThread();

            //==================================[Matrix black magic]=========================================================
            MatrixStack matrixStack = new MatrixStack();
            matrixStack.multiplyPositionMatrix(matrix4f);
            matrixStack.push();
            matrixStack.multiply(camera.getRotation());

            Matrix4f matrix4f2 = matrixStack.peek().getPositionMatrix();

            // Camera position
            float x = (float) -cameraPos.x;
            float y = (float) -cameraPos.y;
            float z = (float) -cameraPos.z;

            LazuliGeometryBuilder.buildTexturedSphere(20, 2, new Vec3d(0,0,0), camera, matrix4f2, bufferBuilder);



            // Start buffer and draw geometry
//            bufferBuilder.vertex(matrix4f2, x, y, z).color(0,0,1,1);
//            bufferBuilder.vertex(matrix4f2, x, y, z).color(0,1,1,1);
//            bufferBuilder.vertex(matrix4f2, x, y, z).color(1,1,1,1);
//            bufferBuilder.vertex(matrix4f2, x, y, z).color(1,0,1,1);
//
//            bufferBuilder.vertex(matrix4f2, 1 + x, 0 + y, 1 + z).texture(1,1);
//            bufferBuilder.vertex(matrix4f2, -1 + x, 0 + y, 1 + z).texture(0,1);
//            bufferBuilder.vertex(matrix4f2, -1 + x, 0 + y, -1 + z).texture(0,0);
//            bufferBuilder.vertex(matrix4f2, 1 + x, 0 + y, -1 + z).texture(1,0);

            // Draw the buffer
            BufferRenderer.drawWithGlobalProgram(bufferBuilder.end());

            // Cleanup
            RenderSystem.disableBlend();
        });
    }

    public static void setThings(Camera cam, Matrix4f matrix, MatrixStack stack) {
        camera = cam;
        matrix4f = matrix;
    }
}
