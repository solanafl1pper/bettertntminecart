package solana.flipper;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.common.Mod;
import solana.flipper.client.BettertntminecartClient;

@Mod(Bettertntminecart.MODID)
public class Bettertntminecart {
    public static final String MODID = "bettertntminecart";

    public Bettertntminecart() {
        DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> BettertntminecartClient::init);
    }
}
