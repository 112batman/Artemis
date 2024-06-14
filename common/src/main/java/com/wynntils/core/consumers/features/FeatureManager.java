/*
 * Copyright © Wynntils 2021-2024.
 * This file is released under LGPLv3. See LICENSE for full license details.
 */
package com.wynntils.core.consumers.features;

import com.wynntils.core.WynntilsMod;
import com.wynntils.core.components.Manager;
import com.wynntils.core.components.Managers;
import com.wynntils.core.consumers.features.properties.StartDisabled;
import com.wynntils.core.mod.type.CrashType;
import com.wynntils.core.persisted.config.Category;
import com.wynntils.core.persisted.config.ConfigCategory;
import com.wynntils.features.*;
import com.wynntils.features.chat.*;
import com.wynntils.features.combat.*;
import com.wynntils.features.commands.AddCommandExpansionFeature;
import com.wynntils.features.commands.CommandAliasesFeature;
import com.wynntils.features.commands.CustomCommandKeybindsFeature;
import com.wynntils.features.commands.FilterAdminCommandsFeature;
import com.wynntils.features.debug.*;
import com.wynntils.features.embellishments.WarHornFeature;
import com.wynntils.features.embellishments.WybelSoundFeature;
import com.wynntils.features.embellishments.WynntilsCosmeticsFeature;
import com.wynntils.features.inventory.*;
import com.wynntils.features.map.*;
import com.wynntils.features.overlays.*;
import com.wynntils.features.players.*;
import com.wynntils.features.redirects.*;
import com.wynntils.features.tooltips.*;
import com.wynntils.features.trademarket.TradeMarketAutoOpenChatFeature;
import com.wynntils.features.trademarket.TradeMarketBulkSellFeature;
import com.wynntils.features.trademarket.TradeMarketPriceConversionFeature;
import com.wynntils.features.trademarket.TradeMarketPriceMatchFeature;
import com.wynntils.features.ui.*;
import com.wynntils.features.utilities.*;
import com.wynntils.features.wynntils.*;
import com.wynntils.mc.event.ClientsideMessageEvent;
import com.wynntils.mc.event.CommandsAddedEvent;
import com.wynntils.utils.mc.McUtils;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.ClickEvent;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * Loads {@link Feature}s
 */
public final class FeatureManager extends Manager {
    private static final Map<Feature, FeatureState> FEATURES = new LinkedHashMap<>();
    private static final Map<Class<? extends Feature>, Feature> FEATURE_INSTANCES = new LinkedHashMap<>();

    private final FeatureCommands commands = new FeatureCommands();

    public FeatureManager() {
        super(List.of());
    }

    @SubscribeEvent
    public void onCommandsAdded(CommandsAddedEvent event) {
        // Register feature commands
        Managers.Command.addNode(event.getRoot(), commands.getCommandNode());
    }

    public void init() {
        // debug
        registerFeature(new AbilityTreeDataDumpFeature());
        registerFeature(new ConnectionProgressFeature());
        registerFeature(new ContentBookDumpFeature());
        registerFeature(new FunctionDumpFeature());
        registerFeature(new ItemDebugTooltipsFeature());
        registerFeature(new LogItemInfoFeature());
        registerFeature(new PacketDebuggerFeature());

        // always on
        registerFeature(new LootrunFeature());

        // region chat
        registerFeature(new BombBellRelayFeature());
        registerFeature(new ChatCoordinatesFeature());
        registerFeature(new ChatItemFeature());
        registerFeature(new ChatMentionFeature());
        registerFeature(new ChatTabsFeature());
        registerFeature(new ChatTimestampFeature());
        registerFeature(new DeathCoordinatesFeature());
        registerFeature(new DialogueOptionOverrideFeature());
        registerFeature(new GuildRankReplacementFeature());
        registerFeature(new InputTranscriptionFeature());
        registerFeature(new MessageFilterFeature());
        registerFeature(new RevealNicknamesFeature());
        // endregion

        // region combat
        registerFeature(new AbbreviateMobHealthFeature());
        registerFeature(new ContentTrackerFeature());
        registerFeature(new CustomLootrunBeaconsFeature());
        registerFeature(new FixCastingSpellsFromInventoryFeature());
        registerFeature(new HealthPotionBlockerFeature());
        registerFeature(new HorseMountFeature());
        registerFeature(new LowHealthVignetteFeature());
        registerFeature(new MythicBlockerFeature());
        registerFeature(new MythicBoxScalerFeature());
        registerFeature(new PreventTradesDuelsFeature());
        registerFeature(new QuickCastFeature());
        registerFeature(new RangeVisualizerFeature());
        registerFeature(new ShamanTotemTrackingFeature());
        registerFeature(new SpellCastVignetteFeature());
        registerFeature(new TokenTrackerBellFeature());
        // endregion

        // region commands
        registerFeature(new AddCommandExpansionFeature());
        registerFeature(new CommandAliasesFeature());
        registerFeature(new CustomCommandKeybindsFeature());
        registerFeature(new FilterAdminCommandsFeature());
        // endregion

        // region embellishments
        registerFeature(new WarHornFeature());
        registerFeature(new WybelSoundFeature());
        registerFeature(new WynntilsCosmeticsFeature());
        // endregion

        // region inventory
        registerFeature(new ContainerSearchFeature());
        registerFeature(new CustomBankPageNamesFeature());
        registerFeature(new CustomBankQuickJumpsFeature());
        registerFeature(new DurabilityArcFeature());
        registerFeature(new EmeraldPouchFillArcFeature());
        registerFeature(new EmeraldPouchHotkeyFeature());
        registerFeature(new ExtendedItemCountFeature());
        registerFeature(new GuildBankHotkeyFeature());
        registerFeature(new HightlightDuplicateCosmeticsFeature());
        registerFeature(new IngredientPouchHotkeyFeature());
        registerFeature(new InventoryEmeraldCountFeature());
        registerFeature(new ItemFavoriteFeature());
        registerFeature(new ItemHighlightFeature());
        registerFeature(new ItemLockFeature());
        registerFeature(new ItemScreenshotFeature());
        registerFeature(new ItemTextOverlayFeature());
        registerFeature(new LootchestTextFeature());
        registerFeature(new ReplaceRecipeBookFeature());
        registerFeature(new UnidentifiedItemIconFeature());
        // endregion

        // region map
        registerFeature(new BeaconBeamFeature());
        registerFeature(new GuildMapFeature());
        registerFeature(new MainMapFeature());
        registerFeature(new MinimapFeature());
        registerFeature(new WorldWaypointDistanceFeature());
        // endregion

        // region overlays
        registerFeature(new ArrowShieldTrackerOverlayFeature());
        registerFeature(new BombBellOverlayFeature());
        registerFeature(new CombatExperienceOverlayFeature());
        registerFeature(new ContentTrackerOverlayFeature());
        registerFeature(new CustomBarsOverlayFeature());
        registerFeature(new CustomPlayerListOverlayFeature());
        registerFeature(new GameBarsOverlayFeature());
        registerFeature(new GameNotificationOverlayFeature());
        registerFeature(new InfoBoxFeature());
        registerFeature(new LootrunOverlaysFeature());
        registerFeature(new MobTotemTimerOverlayFeature());
        registerFeature(new NpcDialogueFeature());
        registerFeature(new ObjectivesOverlayFeature());
        registerFeature(new PartyMembersOverlayFeature());
        registerFeature(new PowderSpecialBarOverlayFeature());
        registerFeature(new RaidProgressFeature());
        registerFeature(new ServerUptimeInfoOverlayFeature());
        registerFeature(new ShamanMaskOverlayFeature());
        registerFeature(new ShamanTotemTimerOverlayFeature());
        registerFeature(new SpellCastMessageOverlayFeature());
        registerFeature(new StatusEffectsOverlayFeature());
        registerFeature(new StopwatchFeature());
        registerFeature(new TerritoryAttackTimerOverlayFeature());
        registerFeature(new TokenBarsOverlayFeature());
        registerFeature(new TowerEffectOverlayFeature());
        registerFeature(new TowerStatsFeature());
        // endregion

        // region players
        registerFeature(new AutoJoinPartyFeature());
        registerFeature(new CustomNametagRendererFeature());
        registerFeature(new GearViewerFeature());
        registerFeature(new HadesFeature());
        registerFeature(new PartyManagementScreenFeature());
        registerFeature(new PlayerArmorHidingFeature());
        registerFeature(new PlayerGhostTransparencyFeature());
        // endregion

        // region redirects
        registerFeature(new AbilityRefreshRedirectFeature());
        registerFeature(new BlacksmithRedirectFeature());
        registerFeature(new ChatRedirectFeature());
        registerFeature(new InventoryRedirectFeature());
        registerFeature(new TerritoryMessageRedirectFeature());
        // endregion

        // region tooltips
        registerFeature(new ItemCompareFeature());
        registerFeature(new ItemGuessFeature());
        registerFeature(new ItemStatInfoFeature());
        registerFeature(new TooltipFittingFeature());
        registerFeature(new TooltipVanillaHideFeature());
        // endregion

        // region trademarket
        registerFeature(new TradeMarketAutoOpenChatFeature());
        registerFeature(new TradeMarketBulkSellFeature());
        registerFeature(new TradeMarketPriceConversionFeature());
        registerFeature(new TradeMarketPriceMatchFeature());
        // endregion

        // region ui
        registerFeature(new BulkBuyFeature());
        registerFeature(new ContainerScrollFeature());
        registerFeature(new CosmeticsPreviewFeature());
        registerFeature(new CraftingProfessionLevelProgressBarFeature());
        registerFeature(new CustomCharacterSelectionScreenFeature());
        registerFeature(new CustomLoadingScreenFeature());
        registerFeature(new CustomSeaskipperScreenFeature());
        registerFeature(new CustomTerritoryManagementScreenFeature());
        registerFeature(new CustomTradeMarketResultScreenFeature());
        registerFeature(new LobbyUptimeFeature());
        registerFeature(new ProfessionHighlightFeature());
        registerFeature(new SoulPointTimerFeature());
        registerFeature(new WynncraftButtonFeature());
        registerFeature(new WynncraftPauseScreenFeature());
        registerFeature(new WynntilsContentBookFeature());
        // endregion

        // region utilities
        registerFeature(new AutoApplyResourcePackFeature());
        registerFeature(new GammabrightFeature());
        registerFeature(new PerCharacterGuildContributionFeature());
        registerFeature(new SilencerFeature());
        registerFeature(new SkillPointLoadoutsFeature());
        registerFeature(new TranscribeMessagesFeature());
        registerFeature(new TranslationFeature());
        registerFeature(new XpGainMessageFeature());
        // endregion

        // region wynntils
        registerFeature(new BetaWarningFeature());
        registerFeature(new ChangelogFeature());
        registerFeature(new CommandsFeature());
        registerFeature(new DataCrowdSourcingFeature());
        registerFeature(new FixPacketBugsFeature());
        registerFeature(new TelemetryFeature());
        registerFeature(new UpdatesFeature());
        registerFeature(new WeeklyConfigBackupFeature());
        // endregion

        // region uncategorized
        registerFeature(new DiscordRichPresenceFeature());
        registerFeature(new ExtendedSeasonLeaderboardFeature());
        registerFeature(new MythicFoundFeature());
        registerFeature(new TerritoryDefenseMessageFeature());
        // endregion

        // Reload Minecraft's config files so our own keybinds get loaded
        // This is needed because we are late to register the keybinds,
        // but we cannot move it earlier to the init process because of I18n
        synchronized (McUtils.options()) {
            McUtils.options().load();
        }

        commands.init();

        addCrashCallbacks();
    }

    private void registerFeature(Feature feature) {
        FEATURES.put(feature, FeatureState.DISABLED);
        FEATURE_INSTANCES.put(feature.getClass(), feature);

        try {
            initializeFeature(feature);
        } catch (AssertionError ae) {
            WynntilsMod.error("Fix i18n for " + feature.getClass().getSimpleName(), ae);
            if (WynntilsMod.isDevelopmentEnvironment()) {
                System.exit(1);
            }
        } catch (Throwable exception) {
            // Log and handle gracefully, just disable this feature
            crashFeature(feature);
            WynntilsMod.reportCrash(
                    CrashType.FEATURE,
                    feature.getClass().getSimpleName(),
                    feature.getClass().getName(),
                    "init",
                    false,
                    true,
                    exception);
        }
    }

    private void initializeFeature(Feature feature) {
        Class<? extends Feature> featureClass = feature.getClass();

        // Set feature category
        ConfigCategory configCategory = feature.getClass().getAnnotation(ConfigCategory.class);
        Category category = configCategory != null ? configCategory.value() : Category.UNCATEGORIZED;
        feature.setCategory(category);

        // Register commands and key binds
        commands.discoverCommands(feature);
        Managers.KeyBind.discoverKeyBinds(feature);

        // Determine if feature should be enabled & set default enabled value for user features
        boolean startDisabled = featureClass.isAnnotationPresent(StartDisabled.class);
        feature.userEnabled.store(!startDisabled);

        Managers.Overlay.discoverOverlays(feature);
        Managers.Overlay.discoverOverlayGroups(feature);

        // Assert that the feature name is properly translated
        assert !feature.getTranslatedName().startsWith("feature.wynntils.")
                : "Fix i18n for " + feature.getTranslatedName();

        // Assert that the feature description is properly translated
        assert !feature.getTranslatedDescription().startsWith("feature.wynntils.")
                : "Fix i18n for " + feature.getTranslatedDescription();

        if (!feature.userEnabled.get()) return; // not enabled by user

        enableFeature(feature);
    }

    public void enableFeature(Feature feature) {
        if (!FEATURES.containsKey(feature)) {
            throw new IllegalArgumentException("Tried to enable an unregistered feature: " + feature);
        }

        FeatureState state = FEATURES.get(feature);

        if (state != FeatureState.DISABLED && state != FeatureState.CRASHED) return;

        feature.onEnable();

        FEATURES.put(feature, FeatureState.ENABLED);

        WynntilsMod.registerEventListener(feature);

        Managers.Overlay.enableOverlays(feature);

        Managers.KeyBind.enableFeatureKeyBinds(feature);
    }

    public void disableFeature(Feature feature) {
        if (!FEATURES.containsKey(feature)) {
            throw new IllegalArgumentException("Tried to disable an unregistered feature: " + feature);
        }

        FeatureState state = FEATURES.get(feature);

        if (state != FeatureState.ENABLED) return;

        feature.onDisable();

        FEATURES.put(feature, FeatureState.DISABLED);

        WynntilsMod.unregisterEventListener(feature);

        Managers.Overlay.disableOverlays(feature);

        Managers.KeyBind.disableFeatureKeyBinds(feature);
    }

    public void crashFeature(Feature feature) {
        if (!FEATURES.containsKey(feature)) {
            throw new IllegalArgumentException("Tried to crash an unregistered feature: " + feature);
        }

        disableFeature(feature);

        FEATURES.put(feature, FeatureState.CRASHED);
    }

    private FeatureState getFeatureState(Feature feature) {
        if (!FEATURES.containsKey(feature)) {
            throw new IllegalArgumentException(
                    "Feature " + feature + " is not registered, but was was queried for its state");
        }

        return FEATURES.get(feature);
    }

    public boolean isEnabled(Feature feature) {
        return getFeatureState(feature) == FeatureState.ENABLED;
    }

    public List<Feature> getFeatures() {
        return FEATURES.keySet().stream().toList();
    }

    @SuppressWarnings("unchecked")
    public <T extends Feature> T getFeatureInstance(Class<T> featureClass) {
        return (T) FEATURE_INSTANCES.get(featureClass);
    }

    public Optional<Feature> getFeatureFromString(String featureName) {
        return getFeatures().stream()
                .filter(feature -> feature.getShortName().equals(featureName))
                .findFirst();
    }

    public void handleExceptionInEventListener(Event event, String featureClassName, Throwable t) {
        String featureName = featureClassName.substring(featureClassName.lastIndexOf('.') + 1);

        Optional<Feature> featureOptional = getFeatureFromString(featureName);
        if (featureOptional.isEmpty()) {
            WynntilsMod.error("Exception in event listener in feature that cannot be located: " + featureClassName, t);
            return;
        }

        Feature feature = featureOptional.get();

        crashFeature(feature);

        // If a crash happens in a client-side message event, and we send a new message about disabling X feature,
        // we will cause a new exception and an endless recursion.
        boolean shouldSendChat = !(event instanceof ClientsideMessageEvent);

        WynntilsMod.reportCrash(
                CrashType.FEATURE,
                feature.getTranslatedName(),
                feature.getClass().getName(),
                "event listener",
                shouldSendChat,
                true,
                t);

        if (shouldSendChat) {
            MutableComponent enableMessage = Component.literal("Click here to enable it again.")
                    .withStyle(ChatFormatting.UNDERLINE)
                    .withStyle(ChatFormatting.RED)
                    .withStyle(style -> style.withClickEvent(new ClickEvent(
                            ClickEvent.Action.RUN_COMMAND, "/feature enable " + feature.getShortName())));

            McUtils.sendMessageToClient(enableMessage);
        }
    }

    private void addCrashCallbacks() {
        Managers.CrashReport.registerCrashContext("Loaded Features", () -> {
            StringBuilder result = new StringBuilder();

            for (Feature feature : FEATURES.keySet()) {
                if (feature.isEnabled()) {
                    result.append("\n\t\t").append(feature.getTranslatedName());
                }
            }

            return result.toString();
        });
    }
}
