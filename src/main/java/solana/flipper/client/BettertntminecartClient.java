package solana.flipper.client;

import com.mojang.blaze3d.platform.InputConstants;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.ConfigScreenHandler;
import net.minecraftforge.client.event.RegisterKeyMappingsEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import solana.flipper.Bettertntminecart;

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
                    ConfigScreenHandler.ConfigScreenFactory.class,
                    () -> new ConfigScreenHandler.ConfigScreenFactory((client, parent) -> TntCartConfigScreen.create(parent))
            );
        } catch (Throwable ignored) {
        }

        FMLJavaModLoadingContext.get().getModEventBus().addListener(BettertntminecartClient::onRegisterKeyMappings);
        MinecraftForge.EVENT_BUS.addListener(BettertntminecartClient::onClientTick);
    }

    public static void onRegisterKeyMappings(RegisterKeyMappingsEvent event) {
        event.register(openConfigKey);
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
