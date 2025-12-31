/*
 * Copyright (C) 2023 Cobblemon Contributors
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.advancement.criterion;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.Pokemon;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.advancement.criterion.LevelUpContext;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

import net.minecraft.advancements.critereon.ContextAwarePredicate;
import net.minecraft.advancements.critereon.EntityPredicate;
import net.minecraft.server.level.ServerPlayer;

import java.util.Optional;

public class LevelUpCriterion(Optional<ContextAwarePredicate> playerCtx, Int level, boolean evolved): SimpleCriterionCondition<LevelUpContext>(playerCtx) {

    final class Companion {
        public final Codec<LevelUpCriterion> CODEC = RecordCodecBuilder.create { it.group(EntityPredicate.ADVANCEMENT_CODEC.optionalFieldOf("player").forGetter(LevelUpCriterion::playerCtx), Codec.INT.optionalFieldOf("level", 0).forGetter(LevelUpCriterion::level), Codec.BOOL.optionalFieldOf("has_evolved", true).forGetter(LevelUpCriterion::evolved)).apply(it, ::LevelUpCriterion) }
    }

    public boolean matches(ServerPlayer player, LevelUpContext context) {
        val preEvo = context.pokemon.preEvolution != null;
        val hasEvolution = !context.pokemon.evolutions.none();
        var evolutionCheck = true;
        if (preEvo || hasEvolution) {
            evolutionCheck = preEvo != hasEvolution;
        }
        return level == context.level && evolutionCheck == evolved;
    }

}
