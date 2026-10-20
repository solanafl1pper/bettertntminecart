package solana.flipper.client.mixin;

import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.MinecartRenderer;
import net.minecraft.entity.item.minecart.AbstractMinecartEntity;
import net.minecraft.entity.item.minecart.TNTMinecartEntity;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.text.StringTextComponent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import solana.flipper.client.TntCartColorConfig;

@Mixin(MinecartRenderer.class)
public abstract class MinecartRendererMixin<T extends AbstractMinecartEntity> extends EntityRenderer<T> {

    protected MinecartRendererMixin(EntityRendererManager renderManager) {
        super(renderManager);
    }

    @Inject(
        method = "render(Lnet/minecraft/entity/item/minecart/AbstractMinecartEntity;FFLcom/mojang/blaze3d/matrix/MatrixStack;Lnet/minecraft/client/renderer/IRenderTypeBuffer;I)V",
        at = @At("TAIL")
    )
    private void injectHitboxAndLabel(
            T entity,
            float entityYaw,
            float partialTicks,
            MatrixStack matrixStack,
            IRenderTypeBuffer buffer,
            int packedLight,
            CallbackInfo ci) {

        if (!(entity instanceof TNTMinecartEntity)) return;

        TntCartColorConfig cfg = TntCartColorConfig.get();

        if (cfg.showHitbox) {
            int argb = TntCartColorConfig.getHitboxColor();
            float r = ((argb >> 16) & 0xFF) / 255f;
            float g = ((argb >>  8) & 0xFF) / 255f;
            float b = ((argb      ) & 0xFF) / 255f;
            float a = ((argb >> 24) & 0xFF) / 255f;

            float hw = entity.getBbWidth() * 0.5f;
            float hh = entity.getBbHeight();

            AxisAlignedBB box = new AxisAlignedBB(-hw, 0.0, -hw, hw, hh, hw);
            WorldRenderer.renderLineBox(matrixStack, buffer.getBuffer(RenderType.lines()), box, r, g, b, a);
        }

        if (cfg.showLabel) {
            this.renderNameTag(entity, new StringTextComponent(cfg.labelText), matrixStack, buffer, packedLight);
        }
    }
}
