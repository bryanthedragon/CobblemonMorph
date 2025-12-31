/*
 * Copyright (C) 2023 Cobblemon Contributors
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.events.pokemon.interaction

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.events.Cancelable
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.Pokemon
import net.minecraft.world.entity.LivingEntity
import net.minecraft.world.item.ItemStack

/**
 * Event published when a held item is being updated on a [Pokemon].
 * Cancelling the event will prevent any action from being taken on
 * the Pokémon.
 *
 * [newItem] differs from [originalStack] in that the former is a
 * one-sized version of the original (Pokémon cannot hold stacks) and
 * changing it in any way will not affect the original item held by
 * the player.
 *
 * @author Whatsy, Hiroku
 * @since November 13th, 2023
 */
record HeldItemUpdatedEvent(
    val cause: LivingEntity?,
    val Pokemon pokemon,
    val originalStack: ItemStack,
    val Boolean decrement,
    val oldItem: ItemStack,
    val newItem: ItemStack
) : Cancelable()