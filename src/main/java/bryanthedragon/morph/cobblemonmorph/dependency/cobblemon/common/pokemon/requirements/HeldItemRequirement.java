/*
 * Copyright (C) 2023 Cobblemon Contributors
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.requirements

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.requirement.Requirement
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.Pokemon
import net.minecraft.advancements.critereon.ItemPredicate
import net.minecraft.world.item.Items

/**
 * An [Requirement] for a [Pokemon.heldItem].
 *
 * @property itemCondition The [ItemPredicate] expected to match the [Pokemon.heldItem].
 * @author Licious
 * @since March 21st, 2022
 */
class HeldItemRequirement(val itemCondition: ItemPredicate) : Requirement {

    constructor() : this(ItemPredicate.Builder.item().of(Items.EGG).build())

    override fun check(pokemon: Pokemon): Boolean = this.itemCondition.test(pokemon.heldItemNoCopy())

    companion object {
        const val ADAPTER_VARIANT = "held_item"
    }
}