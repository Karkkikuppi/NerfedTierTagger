package com.kevin.tiertagger.config;

import com.kevin.tiertagger.TierTagger;
import com.mojang.serialization.Codec;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.option.GameOptions;
import net.minecraft.client.option.SimpleOption;
import net.uku3lig.ukulib.config.option.WidgetCreator;
import net.uku3lig.ukulib.config.option.WideWidgetCreator;
import net.uku3lig.ukulib.config.screen.AbstractConfigScreen;

import java.util.Arrays;

public class TTConfigScreen extends AbstractConfigScreen<TierTaggerConfig> {
    public TTConfigScreen(Screen parent) {
        super("TierTagger Config", parent, TierTagger.getManager());
    }

    @Override
    protected WidgetCreator[] getWidgets(TierTaggerConfig config) {
        GameOptions options = MinecraftClient.getInstance().options;

        return new WidgetCreator[] {
            // Boolean Option (Wrapped in WideWidgetCreator)
            new WideWidgetCreator((x, y, width, height) -> SimpleOption.ofBoolean(
                "tiertagger.config.enabled", 
                config.isEnabled(), 
                value -> config.setEnabled(value)
            ).createWidget(options, x, y, width)),

            // Enum Option for GameMode (Wrapped in WideWidgetCreator)
            new WideWidgetCreator((x, y, width, height) -> new SimpleOption<>(
                "tiertagger.config.gamemode",
                SimpleOption.emptyTooltip(),
                SimpleOption.enumValueText(),
                new SimpleOption.PotentialValuesBasedCallbacks<>(
                    Arrays.asList(TierTaggerConfig.GameMode.values()),
                    Codec.INT.xmap(TierTaggerConfig.GameMode::byId, TierTaggerConfig.GameMode::getId)
                ),
                config.getGameMode(),
                value -> config.setGameMode(value)
            ).createWidget(options, x, y, width)),

            // Boolean Option for Unranked (Wrapped in WideWidgetCreator)
            new WideWidgetCreator((x, y, width, height) -> SimpleOption.ofBoolean(
                "tiertagger.config.unranked", 
                config.isShowUnranked(), 
                value -> config.setShowUnranked(value)
            ).createWidget(options, x, y, width)),

            // Boolean Option for Retired (Wrapped in WideWidgetCreator)
            new WideWidgetCreator((x, y, width, height) -> SimpleOption.ofBoolean(
                "tiertagger.config.retired", 
                config.isShowRetired(), 
                value -> config.setShowRetired(value)
            ).createWidget(options, x, y, width)),

            // Enum Option for Statistic (Wrapped in WideWidgetCreator)
            new WideWidgetCreator((x, y, width, height) -> new SimpleOption<>(
                "tiertagger.config.statistic",
                SimpleOption.emptyTooltip(),
                SimpleOption.enumValueText(),
                new SimpleOption.PotentialValuesBasedCallbacks<>(
                    Arrays.asList(TierTaggerConfig.Statistic.values()),
                    Codec.INT.xmap(TierTaggerConfig.Statistic::byId, TierTaggerConfig.Statistic::getId)
                ),
                config.getShownStatistic(),
                value -> config.setShownStatistic(value)
            ).createWidget(options, x, y, width))
        };
    }
}
