package solana.flipper.client;

import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.KeyBinding;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.fml.ExtensionPoint;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.client.registry.ClientRegistry;

public class BettertntminecartClient {

    public static final KeyBinding openConfigKey = new KeyBinding(
            "key.bettertntminecart.open_config",
            -1,
            "key.categories.misc"
    );

    public static void init() {
        TntCartColorConfig.load();
        try {
            ModLoadingContext.get().registerExtensionPoint(
                    ExtensionPoint.CONFIGGUIFACTORY,
                    () -> (client, parent) -> TntCartConfigScreen.create(parent)
            );
        } catch (Throwable ignored) {
        }

        ClientRegistry.registerKeyBinding(openConfigKey);
        MinecraftForge.EVENT_BUS.addListener(BettertntminecartClient::onClientTick);
    }

    public static void onClientTick(TickEvent.ClientTickEvent event) {
        if (event.phase == TickEvent.Phase.END) {
            TntCartColorConfig.get().tick();
            while (openConfigKey.consumeClick()) {
                Minecraft client = Minecraft.getInstance();
                if (client.screen == null) {
                    client.setScreen(TntCartConfigScreen.create(null));
                }
            }
        }
    }
}
