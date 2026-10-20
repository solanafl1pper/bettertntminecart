package solana.flipper.client.mixin;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MinecartRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.vehicle.AbstractMinecart;
import net.minecraft.world.entity.vehicle.MinecartTNT;
import net.minecraft.world.phys.AABB;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import solana.flipper.client.TntCartColorConfig;

@Mixin(MinecartRenderer.class)
public abstract class MinecartRendererMixin<T extends AbstractMinecart> extends EntityRenderer<T> {

    protected MinecartRendererMixin(EntityRendererProvider.Context context) {
        super(context);
    }

    @Inject(
        method = "render(Lnet/minecraft/world/entity/vehicle/AbstractMinecart;FFLcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;I)V",
        at = @At("TAIL")
    )
    private void injectHitboxAndLabel(
            T entity,
            float entityYaw,
            float partialTicks,
            PoseStack poseStack,
            MultiBufferSource buffer,
            int packedLight,
            CallbackInfo ci) {

        if (!(entity instanceof MinecartTNT)) return;

        TntCartColorConfig cfg = TntCartColorConfig.get();

        if (cfg.showHitbox) {
            int argb = TntCartColorConfig.getHitboxColor();
            float r = ((argb >> 16) & 0xFF) / 255f;
            float g = ((argb >>  8) & 0xFF) / 255f;
            float b = ((argb      ) & 0xFF) / 255f;
            float a = ((argb >> 24) & 0xFF) / 255f;

            float hw = entity.getBbWidth() * 0.5f;
            float hh = entity.getBbHeight();

            AABB box = new AABB(-hw, 0.0, -hw, hw, hh, hw);
            LevelRenderer.renderLineBox(poseStack, buffer.getBuffer(RenderType.lines()), box, r, g, b, a);
        }

        if (cfg.showLabel) {
            this.renderNameTag(entity, Component.literal(cfg.labelText), poseStack, buffer, packedLight, partialTicks);
        }
    }
}
