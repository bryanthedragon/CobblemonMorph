/*
 * Copyright (C) 2023 Cobblemon Contributors
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.advancement.criterion;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.cobblemonResource;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

import net.minecraft.advancements.critereon.ContextAwarePredicate;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;

import java.util.List;
import java.util.Optional;

public class AspectCriterion(Optional<ContextAwarePredicate> playerCtx, ResourceLocation species, List<String> aspects): SimpleCriterionCondition<MutableMap<ResourceLocation, MutableSet<String>>>(playerCtx) {

    final class Companion {
        //All three of these codecs used to use Codecs.createStrictOptionalFieldCodec, that no longer exists
        public final Codec<AspectCriterion> CODEC = RecordCodecBuilder.create { it.group(ContextAwarePredicate.CODEC.optionalFieldOf("player").forGetter(AspectCriterion::playerCtx), ResourceLocation.CODEC.optionalFieldOf("species", cobblemonResource("pikachu")).forGetter(AspectCriterion::species), Codec.STRING.listOf().optionalFieldOf("aspects", listOf()).forGetter(AspectCriterion::aspects)) .apply(it, ::AspectCriterion) }
}

    public boolean matches(ServerPlayer player, MutableMap<ResourceLocation, MutableSet<String>> context) {
        val caughtAspects = context.getOrDefault(species, mutableSetOf());
        return this.aspects.all { it in caughtAspects };
    }
}
