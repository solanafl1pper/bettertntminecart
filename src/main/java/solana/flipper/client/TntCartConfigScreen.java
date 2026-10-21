package solana.flipper.client;

import me.shedaniel.clothconfig2.api.ConfigBuilder;
import me.shedaniel.clothconfig2.api.ConfigCategory;
import me.shedaniel.clothconfig2.api.ConfigEntryBuilder;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.TranslatableComponent;

public final class TntCartConfigScreen {

    private TntCartConfigScreen() {}

    public static Screen create(Screen parent) {
        TntCartColorConfig cfg = TntCartColorConfig.get();

        ConfigBuilder builder = ConfigBuilder.create()
                .setParentScreen(parent)
                .setTitle(new TranslatableComponent("config.bettertntminecart.title"))
                .setSavingRunnable(TntCartColorConfig::save);

        ConfigEntryBuilder eb = builder.entryBuilder();

        ConfigCategory hitbox = builder.getOrCreateCategory(new TranslatableComponent("config.bettertntminecart.cat.hitbox"));

        hitbox.addEntry(eb.startBooleanToggle(new TranslatableComponent("config.bettertntminecart.show_hitbox"), cfg.showHitbox)
                .setDefaultValue(true).setSaveConsumer(v -> cfg.showHitbox = v).build());

        hitbox.addEntry(eb.startBooleanToggle(new TranslatableComponent("config.bettertntminecart.hitbox_rgb_mode"), cfg.hitboxRgbMode)
                .setDefaultValue(false).setSaveConsumer(v -> cfg.hitboxRgbMode = v).build());

        hitbox.addEntry(eb.startFloatField(new TranslatableComponent("config.bettertntminecart.hitbox_rgb_speed"), cfg.hitboxRgbSpeed)
                .setDefaultValue(1.0f).setMin(0.1f).setMax(10.0f).setSaveConsumer(v -> cfg.hitboxRgbSpeed = v).build());

        hitbox.addEntry(eb.startColorField(new TranslatableComponent("config.bettertntminecart.hitbox_color"), cfg.hitboxColor & 0x00FFFFFF)
                .setDefaultValue(0x00FF00).setSaveConsumer(rgb -> cfg.hitboxColor = 0xFF000000 | (rgb & 0x00FFFFFF)).build());

        hitbox.addEntry(eb.startIntSlider(new TranslatableComponent("config.bettertntminecart.hitbox_alpha"), cfg.hitboxAlpha, 0, 255)
                .setDefaultValue(200).setSaveConsumer(a -> cfg.hitboxAlpha = a).build());

        hitbox.addEntry(eb.startFloatField(new TranslatableComponent("config.bettertntminecart.hitbox_line_width"), cfg.hitboxLineWidth)
                .setDefaultValue(2.0f).setMin(0.5f).setMax(8.0f).setSaveConsumer(v -> cfg.hitboxLineWidth = v).build());

        ConfigCategory label = builder.getOrCreateCategory(new TranslatableComponent("config.bettertntminecart.cat.label"));

        label.addEntry(eb.startBooleanToggle(new TranslatableComponent("config.bettertntminecart.show_label"), cfg.showLabel)
                .setDefaultValue(true).setSaveConsumer(v -> cfg.showLabel = v).build());

        label.addEntry(eb.startStrField(new TranslatableComponent("config.bettertntminecart.label_text"), cfg.labelText)
                .setDefaultValue("Cart")
                .setSaveConsumer(v -> cfg.labelText = v.isEmpty() ? "Cart" : v).build());

        return builder.build();
    }
}
