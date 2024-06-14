package com.wynntils.mc.mixin.accessors;

import net.minecraft.client.resources.server.DownloadedPackSource;
import net.minecraft.client.resources.server.ServerPackManager;
import net.minecraft.server.packs.repository.RepositorySource;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(DownloadedPackSource.class)
public interface DownloadedPackSourceAccessor {
    @Accessor("packSource")
    void setPackSource(RepositorySource source);

    @Accessor("manager")
    ServerPackManager getManager();
}
