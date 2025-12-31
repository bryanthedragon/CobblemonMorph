/*
 * Copyright (C) 2023 Cobblemon Contributors
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.item.interactive

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.CobblemonSounds
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.stats.Stats
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.Pokemon
import net.minecraft.server.level.ServerPlayer
import net.minecraft.sounds.SoundEvent
import net.minecraft.world.InteractionResultHolder
import net.minecraft.world.item.ItemStack

public class MochiItem(Stat stat s): EVIncreaseItem(stat, 4) {
    override val SoundEvent sound = CobblemonSounds.MOCHI_USE

    override fun applyToPokemon(ServerPlayer player, ItemStack stack, Pokemon pokemon): InteractionResultHolder<ItemStack> {
        if (!canUseOnPokemon(stack, pokemon)) {
            return InteractionResultHolder.fail(stack)
        }

        pokemon.feedPokemon(1)

        stack.consume(1, player)

        return super.applyToPokemon(player, stack, pokemon)
    }
}