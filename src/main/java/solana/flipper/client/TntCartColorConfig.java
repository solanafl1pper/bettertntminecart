package solana.flipper.client;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Path;

public final class TntCartColorConfig {

    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    private static final Path CONFIG_PATH = Path.of("config", "bettertntminecart.json");

    private static TntCartColorConfig instance = new TntCartColorConfig();
    public static TntCartColorConfig get() { return instance; }

    public boolean showHitbox      = true;
    public int     hitboxColor     = 0xFF00FF00;
    public boolean hitboxRgbMode   = false;
    public float   hitboxRgbSpeed  = 1.0f;
    public int     hitboxAlpha     = 200;
    public float   hitboxLineWidth = 2.0f;

    public boolean showLabel  = true;
    public String  labelText  = "Cart";

    public transient int currentHitboxArgb = 0xFF00FF00;
    private float hitboxHue = 0f;

    public static int getHitboxColor() {
        int c = get().currentHitboxArgb;
        int r = (c >> 16) & 0xFF;
        int g = (c >>  8) & 0xFF;
        int b = (c      ) & 0xFF;
        int a = get().hitboxAlpha;
        return (a << 24) | (r << 16) | (g << 8) | b;
    }

    public void tick() {
        if (hitboxRgbMode) {
            hitboxHue = (hitboxHue + hitboxRgbSpeed * 0.005f) % 1.0f;
            int rgb = java.awt.Color.HSBtoRGB(hitboxHue, 1f, 1f);
            currentHitboxArgb = 0xFF000000 | (rgb & 0x00FFFFFF);
        } else {
            currentHitboxArgb = hitboxColor;
        }
    }

    public static void load() {
        if (Files.exists(CONFIG_PATH)) {
            try (Reader r = Files.newBufferedReader(CONFIG_PATH)) {
                instance = GSON.fromJson(r, TntCartColorConfig.class);
                if (instance == null) instance = new TntCartColorConfig();
                instance.currentHitboxArgb = instance.hitboxColor;
                if (instance.labelText == null || instance.labelText.isEmpty())
                    instance.labelText = "Cart";
            } catch (IOException e) {
                instance = new TntCartColorConfig();
            }
        }
    }

    public static void save() {
        try {
            if (CONFIG_PATH.getParent() != null) {
                Files.createDirectories(CONFIG_PATH.getParent());
            }
            try (Writer w = Files.newBufferedWriter(CONFIG_PATH)) {
                GSON.toJson(instance, w);
            }
        } catch (IOException e) {}
    }
}
