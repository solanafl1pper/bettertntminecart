package solana.flipper.client.mixin;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.entity.AbstractMinecartRenderer;
import net.minecraft.client.renderer.entity.state.MinecartRenderState;
import net.minecraft.client.renderer.entity.state.MinecartTntRenderState;
import net.minecraft.client.renderer.rendertype.RenderTypes;
import net.minecraft.client.renderer.state.level.CameraRenderState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import solana.flipper.client.TntCartColorConfig;

@Mixin(AbstractMinecartRenderer.class)
public abstract class AbstractMinecartRendererMixin {

    @Inject(
        method = "submit(Lnet/minecraft/client/renderer/entity/state/MinecartRenderState;Lcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/SubmitNodeCollector;Lnet/minecraft/client/renderer/state/level/CameraRenderState;)V",
        at = @At("TAIL")
    )
    private void injectHitboxAndLabel(
            MinecartRenderState renderState,
            PoseStack poseStack,
            SubmitNodeCollector collector,
            CameraRenderState cameraState,
            CallbackInfo ci) {

        if (!(renderState instanceof MinecartTntRenderState)) return;

        TntCartColorConfig cfg = TntCartColorConfig.get();

        // ── Colored outline hitbox (like F3+B, always visible) ─────────────
        // Uses RenderTypes.LINES — same as vanilla hitbox debug rendering.
        // Always drawn on top (no depth test on LINES render type).
        if (cfg.showHitbox) {
            int argb = TntCartColorConfig.getHitboxColor();
            float r = ((argb >> 16) & 0xFF) / 255f;
            float g = ((argb >>  8) & 0xFF) / 255f;
            float b = ((argb      ) & 0xFF) / 255f;
            float a = ((argb >> 24) & 0xFF) / 255f;

            float hw = renderState.boundingBoxWidth  * 0.5f;
            float hh = renderState.boundingBoxHeight;

            final float fr = r, fg = g, fb = b, fa = a;
            final float lw = cfg.hitboxLineWidth;

            collector.submitCustomGeometry(poseStack, RenderTypes.LINES,
                (pose, vc) -> renderOutlineBox(vc, pose,
                        -hw, 0f, -hw,
                         hw, hh,  hw,
                        fr, fg, fb, fa, lw));
        }

        // ── "Cart" label is rendered via HudRenderCallback (CartLabelRenderer)
        // — always visible, no walls, no distance limit.
    }

    private static void renderOutlineBox(
            VertexConsumer vc, PoseStack.Pose pose,
            float x1, float y1, float z1,
            float x2, float y2, float z2,
            float r, float g, float b, float a, float lw) {

        // Bottom face edges
        line(vc, pose, x1,y1,z1, x2,y1,z1, r,g,b,a,lw);
        line(vc, pose, x2,y1,z1, x2,y1,z2, r,g,b,a,lw);
        line(vc, pose, x2,y1,z2, x1,y1,z2, r,g,b,a,lw);
        line(vc, pose, x1,y1,z2, x1,y1,z1, r,g,b,a,lw);
        // Top face edges
        line(vc, pose, x1,y2,z1, x2,y2,z1, r,g,b,a,lw);
        line(vc, pose, x2,y2,z1, x2,y2,z2, r,g,b,a,lw);
        line(vc, pose, x2,y2,z2, x1,y2,z2, r,g,b,a,lw);
        line(vc, pose, x1,y2,z2, x1,y2,z1, r,g,b,a,lw);
        // Vertical edges
        line(vc, pose, x1,y1,z1, x1,y2,z1, r,g,b,a,lw);
        line(vc, pose, x2,y1,z1, x2,y2,z1, r,g,b,a,lw);
        line(vc, pose, x2,y1,z2, x2,y2,z2, r,g,b,a,lw);
        line(vc, pose, x1,y1,z2, x1,y2,z2, r,g,b,a,lw);
    }

    private static void line(VertexConsumer vc, PoseStack.Pose pose,
            float x1, float y1, float z1,
            float x2, float y2, float z2,
            float r, float g, float b, float a, float lw) {
        int ri = (int)(r*255), gi = (int)(g*255), bi = (int)(b*255), ai = (int)(a*255);
        float dx = x2-x1, dy = y2-y1, dz = z2-z1;
        float len = (float) Math.sqrt(dx*dx + dy*dy + dz*dz);
        if (len == 0) len = 1;
        float nx = dx/len, ny = dy/len, nz = dz/len;
        vc.addVertex(pose, x1, y1, z1).setColor(ri, gi, bi, ai).setNormal(pose, nx, ny, nz).setLineWidth(lw);
        vc.addVertex(pose, x2, y2, z2).setColor(ri, gi, bi, ai).setNormal(pose, nx, ny, nz).setLineWidth(lw);
    }
}
