/*
 * Copyright (C) 2023 Cobblemon Contributors
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.requirements

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.requirement.OwnerQueryRequirement
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.entity.npc.NPCEntity
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.Pokemon
import net.minecraft.advancements.critereon.ItemPredicate
import net.minecraft.server.level.ServerPlayer
import net.minecraft.resources.ResourceLocation
import net.minecraft.world.InteractionHand
import net.minecraft.world.entity.LivingEntity
import kotlin.collections.iterator

/**
 * An [OwnerQueryRequirement] that checks if the player/npc is holding a certain [ItemPredicate]
 *
 * @param itemCondition The [ItemPredicate] the owner is supposed to hold
 *
 * @author Polymeta
 */
public class OwnerHoldsItemRequirement(val itemCondition: ItemPredicate)
    : OwnerQueryRequirement {

    override fun checkPlayer(
        Pokemon pokemon,
        owner: ServerPlayer
    ): Boolean {
        return checkItem(owner)
    }

    override fun checkNPC(
        Pokemon pokemon,
        owner: NPCEntity
    ): Boolean {
        return checkItem(owner)
    }

    fun checkItem(LivingEntity entity): Boolean {
        return itemCondition.test(entity.getItemInHand(InteractionHand.MAIN_HAND))
    }

    final class Companion {
        val ADAPTER_VARIANT = "owner_held_item"
    }
}