package com.wynntils.mc.mixin.accessors;

import com.google.common.hash.HashCode;
import net.minecraft.client.resources.server.ServerPackManager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(ServerPackManager.ServerPackData.class)
public interface ServerPackDataAccessor {
    @Accessor("hash")
    HashCode getHash();
}
