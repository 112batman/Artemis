/*
 * Copyright © Wynntils 2022-2024.
 * This file is released under LGPLv3. See LICENSE for full license details.
 */
package com.wynntils.models.containers;

import com.wynntils.core.components.Model;
import com.wynntils.mc.event.ScreenClosedEvent;
import com.wynntils.mc.event.ScreenInitEvent;
import com.wynntils.models.containers.containers.*;
import com.wynntils.models.containers.containers.personal.*;
import com.wynntils.models.containers.containers.reward.*;
import com.wynntils.models.profession.type.ProfessionType;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public final class ContainerModel extends Model {
    // Test in ContainerModel_ABILITY_TREE_PATTERN
    public static final Pattern ABILITY_TREE_PATTERN =
            Pattern.compile("(?:Warrior|Shaman|Mage|Assassin|Archer) Abilities");

    public static final String CHARACTER_INFO_NAME = "Character Info";
    public static final String COSMETICS_MENU_NAME = "Crates, Bombs & Cosmetics";
    public static final String GUILD_MENU_NAME = "[a-zA-Z\\s]+: Manage";
    public static final String GUILD_DIPLOMACY_MENU_NAME = "[a-zA-Z\\s]+: Diplomacy";
    public static final String MASTERY_TOMES_NAME = "Mastery Tomes";

    private static final List<Container> containerTypes = new ArrayList<>();
    private Container currentContainer = null;

    public ContainerModel() {
        super(List.of());

        registerWynncraftContainers();
    }

    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public void onScreenInit(ScreenInitEvent e) {
        currentContainer = null;

        if (!(e.getScreen() instanceof AbstractContainerScreen<?> screen)) return;

        for (Container container : containerTypes) {
            if (container.isScreen(screen)) {
                currentContainer = container;
                currentContainer.setContainerId(screen.getMenu().containerId);
                break;
            }
        }
    }

    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public void onScreenClose(ScreenClosedEvent e) {
        currentContainer = null;
    }

    public Container getCurrentContainer() {
        return currentContainer;
    }

    private void registerWynncraftContainers() {
        // Order does not matter here so just keep it alphabetical
        registerWynncraftContainer(new AbilityTreeContainer());
        registerWynncraftContainer(new AccountBankContainer());
        registerWynncraftContainer(new BlockBankContainer());
        registerWynncraftContainer(new BookshelfContainer());
        registerWynncraftContainer(new ChallengeRewardContainer());
        registerWynncraftContainer(new CharacterBankContainer());
        registerWynncraftContainer(new CharacterInfoContainer());
        registerWynncraftContainer(new ContentBookContainer());
        registerWynncraftContainer(new DailyRewardContainer());
        registerWynncraftContainer(new FlyingChestContainer());
        registerWynncraftContainer(new GuildBankContainer());
        registerWynncraftContainer(new GuildManagementContainer());
        registerWynncraftContainer(new GuildMemberListContainer());
        registerWynncraftContainer(new GuildTerritoriesContainer());
        registerWynncraftContainer(new HousingJukeboxContainer());
        registerWynncraftContainer(new HousingListContainer());
        registerWynncraftContainer(new IngredientPouchContainer());
        registerWynncraftContainer(new InventoryContainer());
        registerWynncraftContainer(new JukeboxContainer());
        registerWynncraftContainer(new LobbyContainer());
        registerWynncraftContainer(new LootChestContainer());
        registerWynncraftContainer(new MiscBucketContainer());
        registerWynncraftContainer(new ObjectiveRewardContainer());
        registerWynncraftContainer(new PetMenuContainer());
        registerWynncraftContainer(new ScrapMenuContainer());
        registerWynncraftContainer(new SeaskipperContainer());
        registerWynncraftContainer(new TradeMarketFiltersContainer());
        registerWynncraftContainer(new TradeMarketPrimaryContainer());
        registerWynncraftContainer(new TradeMarketSecondaryContainer());

        for (ProfessionType type : ProfessionType.craftingProfessionTypes()) {
            registerWynncraftContainer(new CraftingStationContainer(Pattern.compile(type.getDisplayName()), type));
        }
    }

    private void registerWynncraftContainer(Container container) {
        containerTypes.add(container);
    }
}
