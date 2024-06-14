/*
 * Copyright © Wynntils 2022-2023.
 * This file is released under LGPLv3. See LICENSE for full license details.
 */
package com.wynntils.mc.mixin.accessors;

import net.minecraft.network.protocol.game.ClientboundBossEventPacket;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import java.util.UUID;

@Mixin(ClientboundBossEventPacket.class)
public interface ClientboundBossEventPacketAccessor {
    @Accessor("id")
    UUID getId();

    @Accessor("operation")
    ClientboundBossEventPacket.Operation getOperation();
}
