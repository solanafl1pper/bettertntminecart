package solana.flipper.client;

import com.mojang.blaze3d.platform.InputConstants;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.client.event.ClientTickEvent;
import net.neoforged.neoforge.client.event.RegisterKeyMappingsEvent;
import net.neoforged.neoforge.client.gui.IConfigScreenFactory;
import net.neoforged.neoforge.common.NeoForge;

public class BettertntminecartClient {

    public static final KeyMapping openConfigKey = new KeyMapping(
            "key.bettertntminecart.open_config",
            InputConstants.UNKNOWN.getValue(),
            KeyMapping.Category.MISC
    );

    public static void init(IEventBus modEventBus) {
        TntCartColorConfig.load();
        try {
            net.neoforged.fml.ModLoadingContext.get().registerExtensionPoint(IConfigScreenFactory.class,
                    () -> (client, parent) -> TntCartConfigScreen.create(parent));
        } catch (Throwable ignored) {
        }

        modEventBus.addListener(BettertntminecartClient::onRegisterKeyMappings);
        NeoForge.EVENT_BUS.addListener(BettertntminecartClient::onClientTick);
    }

    private static void onRegisterKeyMappings(RegisterKeyMappingsEvent event) {
        event.register(openConfigKey);
    }

    private static void onClientTick(ClientTickEvent.Post event) {
        TntCartColorConfig.get().tick();
        while (openConfigKey.consumeClick()) {
            Minecraft client = Minecraft.getInstance();
            if (client.gui.screen() == null) {
                client.setScreenAndShow(TntCartConfigScreen.create(null));
            }
        }
    }
}
