/*
 * Copyright © Wynntils 2022-2023.
 * This file is released under LGPLv3. See LICENSE for full license details.
 */
package com.wynntils.screens.base;

import net.minecraft.network.chat.Component;

import java.util.List;

public interface TooltipProvider {
    List<Component> getTooltipLines();
}
