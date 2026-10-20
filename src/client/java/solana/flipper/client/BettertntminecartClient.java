package solana.flipper.client;

import com.mojang.blaze3d.platform.InputConstants;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keymapping.v1.KeyMappingHelper;
import net.minecraft.client.KeyMapping;

public class BettertntminecartClient implements ClientModInitializer {

    public static KeyMapping openConfigKey;

    @Override
    public void onInitializeClient() {
        TntCartColorConfig.load();

        openConfigKey = KeyMappingHelper.registerKeyMapping(new KeyMapping(
                "key.bettertntminecart.open_config",
                InputConstants.UNKNOWN.getValue(),
                KeyMapping.Category.MISC
        ));

        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            TntCartColorConfig.get().tick();
            while (openConfigKey.consumeClick()) {
                if (client.gui.screen() == null) {
                    client.setScreenAndShow(TntCartConfigScreen.create(null));
                }
            }
        });
    }
}
