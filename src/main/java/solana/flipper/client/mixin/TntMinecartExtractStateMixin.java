package solana.flipper.client.mixin;

import net.minecraft.client.renderer.entity.TntMinecartRenderer;
import net.minecraft.client.renderer.entity.state.MinecartTntRenderState;
import net.minecraft.world.entity.vehicle.minecart.MinecartTNT;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import solana.flipper.client.MiniMessageParser;
import solana.flipper.client.TntCartColorConfig;

@Mixin(TntMinecartRenderer.class)
public abstract class TntMinecartExtractStateMixin {

    @Inject(
        method = "extractRenderState(Lnet/minecraft/world/entity/vehicle/minecart/MinecartTNT;Lnet/minecraft/client/renderer/entity/state/MinecartTntRenderState;F)V",
        at = @At("TAIL")
    )
    private void injectNameTag(MinecartTNT entity, MinecartTntRenderState renderState, float partialTick, CallbackInfo ci) {
        TntCartColorConfig cfg = TntCartColorConfig.get();
        if (!cfg.showLabel) {
            renderState.nameTag = null;
            return;
        }
        String text = (cfg.labelText != null && !cfg.labelText.isEmpty()) ? cfg.labelText : "Cart";
        renderState.nameTag = MiniMessageParser.parse(text);
        renderState.nameTagAttachment = new Vec3(0, renderState.boundingBoxHeight + 0.5, 0);
    }
}
