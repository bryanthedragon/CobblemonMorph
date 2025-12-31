/*
 * Copyright (C) 2023 Cobblemon Contributors
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.advancement.criterion;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.advancement.criterion.context.countable.CountableContext;

import net.minecraft.advancements.critereon.ContextAwarePredicate;
import net.minecraft.advancements.critereon.EntityPredicate;
import net.minecraft.server.level.ServerPlayer;

import java.util.Optional;

public class CountableCriterion<T extends CountableContext>(Optional<ContextAwarePredicate> playerCtx, int count): SimpleCriterionCondition<T>(playerCtx) {

    final class Companion {
        public final Codec<CountableCriterion<CountableContext>> CODEC Codec<CountableCriterion<CountableContext>> = RecordCodecBuilder.create { it.group(EntityPredicate.ADVANCEMENT_CODEC.optionalFieldOf("player").forGetter(CountableCriterion<CountableContext>::playerCtx), Codec.INT.optionalFieldOf("count", 0).forGetter(CountableCriterion<CountableContext>::count)).apply(it, ::CountableCriterion) }
    }
    fun matches(ServerPlayer player, T context) = context.times >= count

}