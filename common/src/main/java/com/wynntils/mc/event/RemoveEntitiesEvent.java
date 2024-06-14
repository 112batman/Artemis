/*
 * Copyright © Wynntils 2023.
 * This file is released under LGPLv3. See LICENSE for full license details.
 */
package com.wynntils.mc.event;

import net.minecraft.network.protocol.game.ClientboundRemoveEntitiesPacket;
import net.minecraftforge.eventbus.api.Event;

import java.util.List;

public class RemoveEntitiesEvent extends Event {
    private final List<Integer> entityIds;

    public RemoveEntitiesEvent(ClientboundRemoveEntitiesPacket packet) {
        this.entityIds = packet.getEntityIds();
    }

    public List<Integer> getEntityIds() {
        return entityIds;
    }
}
