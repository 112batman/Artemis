package com.wynntils.mc.mixin.accessors;

import net.minecraft.client.gui.screens.ReceivingLevelScreen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import java.util.function.BooleanSupplier;

@Mixin(ReceivingLevelScreen.class)
public interface ReceivingLevelScreenAccessor {
    @Accessor
    void setLevelReceived(BooleanSupplier supplier);
}
