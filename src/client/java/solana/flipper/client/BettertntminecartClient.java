package solana.flipper.client;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.KeyMapping;
import org.lwjgl.glfw.GLFW;

public class BettertntminecartClient implements ClientModInitializer {

    public static KeyMapping openConfigKey;

    @Override
    public void onInitializeClient() {
        TntCartColorConfig.load();

        openConfigKey = KeyBindingHelper.registerKeyBinding(new KeyMapping(
                "key.bettertntminecart.open_config",
                GLFW.GLFW_KEY_UNKNOWN,
                KeyMapping.Category.MISC
        ));

        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            TntCartColorConfig.get().tick();
            while (openConfigKey.consumeClick()) {
                if (client.screen == null) {
                    client.setScreen(TntCartConfigScreen.create(null));
                }
            }
        });
    }
}
