/*
 * Copyright (C) 2023 Cobblemon Contributors
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.CobblemonEntities;

import net.minecraft.nbt.CompoundTag;

public class CompoundTagExtensions {
    Boolean CompoundTag.isPokemonEntity() {
        return this.getString("id").equals(CobblemonEntities.POKEMON_KEY.toString());
    }
}