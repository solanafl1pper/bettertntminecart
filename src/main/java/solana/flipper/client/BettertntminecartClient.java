package solana.flipper.client;

import com.mojang.blaze3d.platform.InputConstants;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.minecraftforge.client.ClientRegistry;
import net.minecraftforge.client.ConfigGuiHandler;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.fml.ModLoadingContext;

public class BettertntminecartClient {

    public static final KeyMapping openConfigKey = new KeyMapping(
            "key.bettertntminecart.open_config",
            InputConstants.UNKNOWN.getValue(),
            "key.categories.misc"
    );

    public static void init() {
        TntCartColorConfig.load();
        try {
            ModLoadingContext.get().registerExtensionPoint(
                    ConfigGuiHandler.ConfigGuiFactory.class,
                    () -> new ConfigGuiHandler.ConfigGuiFactory((client, parent) -> TntCartConfigScreen.create(parent))
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
