/*
 * Copyright (C) 2023 Cobblemon Contributors
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.requirements;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.requirement.EntityQueryRequirement;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.Pokemon;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.Level;

/**
 * A [EntityQueryRequirement] for when a [Pokemon] is expected to be in a [Level].
 *
 * @property identifier The [ResourceLocation] of the [Level] the queried entity is expected to be in.
 * @author Licious
 * @since March 21st, 2022
 */
public class WorldRequirement ex EntityQueryRequirement {
    final class Companion {
        const val ADAPTER_VARIANT = "world";
    }
    public final ResourceLocation identifier = ResourceLocation.parse("minecraft:the_overworld");
    override fun check(Pokemon pokemon, LivingEntity queriedEntity) = queriedEntity.level().dimension().location() == this.identifier;
}