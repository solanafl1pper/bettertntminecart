package solana.flipper;

import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.loading.FMLEnvironment;
import solana.flipper.client.BettertntminecartClient;

@Mod(Bettertntminecart.MODID)
public class Bettertntminecart {
    public static final String MODID = "bettertntminecart";

    public Bettertntminecart(IEventBus modEventBus) {
        if (FMLEnvironment.dist == Dist.CLIENT) {
            BettertntminecartClient.init(modEventBus);
        }
    }
}
