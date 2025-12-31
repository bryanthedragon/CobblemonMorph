/*
 * Copyright (C) 2023 Cobblemon Contributors
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util;
import net.minecraft.nbt.CompoundTag
import java.util.*
public final class CompoundTagUtilities {
    @JvmStatic
    fun getPokemonID(CompoundTag nbt): UUID {
        return nbt.getCompound(DataKeys.POKEMON)
            .getUUID(DataKeys.POKEMON_UUID)
    }

    @JvmStatic
    fun isShoulderPokemon(CompoundTag nbt): Boolean {
        return nbt.isPokemonEntity()
    }
}