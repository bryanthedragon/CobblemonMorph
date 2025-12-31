/*
 * Copyright (C) 2023 Cobblemon Contributors
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.advancement.criterion;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.asIdentifierDefaultingNamespace;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

import net.minecraft.advancements.critereon.ContextAwarePredicate;
import net.minecraft.advancements.critereon.EntityPredicate;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.resources.ResourceLocation;

import java.util.Optional;

public class PokemonInteractContext(ResourceLocation species, ResourceLocation item)

public class PokemonInteractCriterion(
    playerCtx: Optional<ContextAwarePredicate>,
    val species: String,
    val item: String
): SimpleCriterionCondition<PokemonInteractContext>(playerCtx) {
    final class Companion {
        val CODEC: Codec<PokemonInteractCriterion> = RecordCodecBuilder.create { it.group(
            EntityPredicate.ADVANCEMENT_CODEC.optionalFieldOf("player").forGetter(PokemonInteractCriterion::playerCtx),
            Codec.STRING.optionalFieldOf("species", "any").forGetter(PokemonInteractCriterion::species),
            Codec.STRING.optionalFieldOf("item", "any").forGetter(PokemonInteractCriterion::item)
        ).apply(it, ::PokemonInteractCriterion) }
    }

    override fun matches(ServerPlayer player, context: PokemonInteractContext): Boolean {
        return (context.species == this.species.asIdentifierDefaultingNamespace() || this.species == "any") && (context.item == this.item.asIdentifierDefaultingNamespace() || this.item == "any")
    }
}
