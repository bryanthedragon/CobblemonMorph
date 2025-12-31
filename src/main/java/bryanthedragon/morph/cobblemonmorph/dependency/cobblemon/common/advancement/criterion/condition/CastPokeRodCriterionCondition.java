/*
 * Copyright (C) 2023 Cobblemon Contributors
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.advancement.criterion;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.advancement.criterion.context.fishing.CastPokeRodContext;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.asIdentifierDefaultingNamespace;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

import java.util.Optional;

import net.minecraft.advancements.critereon.ContextAwarePredicate;
import net.minecraft.advancements.critereon.EntityPredicate;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.resources.ResourceLocation;

public class CastPokeRodCriterionCondition(Optional<ContextAwarePredicate> playerCtx, String baitId): SimpleCriterionCondition<CastPokeRodContext>(playerCtx) {
    final class Companion {
    public final Codec<CastPokeRodCriterionCondition> CODEC = RecordCodecBuilder.create { it.group(EntityPredicate.ADVANCEMENT_CODEC.optionalFieldOf("player").forGetter(CastPokeRodCriterionCondition::playerCtx), Codec.STRING.optionalFieldOf("baitId", "empty_bait").forGetter(CastPokeRodCriterionCondition::baitId)).apply(it, { playerCtx, baitId -> CastPokeRodCriterionCondition(playerCtx, baitId.ifEmpty { "empty_bait" }) }) }}

    Boolean matches(ServerPlayer player, CastPokeRodContext context) {
        return (context.baitId == this.baitId.asIdentifierDefaultingNamespace() || this.baitId == "empty_bait");
    }
}
