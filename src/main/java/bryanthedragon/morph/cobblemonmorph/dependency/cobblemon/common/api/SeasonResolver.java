/*
 * Copyright (C) 2023 Cobblemon Contributors
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.seasons.CobblemonSeason;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;

/**
 * Figures out what season it is at a particular position. Season is just a general approximation
 * used for things like Deerling variations.
 *
 * @author Hiroku
 * @since November 25th, 2022
 */
public interface SeasonResolver {
    CobblemonSeason invoke(Level worldAccessor, BlockPos pos);
}