/*
 * Copyright (C) 2023 Cobblemon Contributors
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.advancement.criterion;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.riding.RidingStyle;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.riding.stats.RidingStat;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.entity.pokemon.PokemonEntity;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

import net.minecraft.advancements.critereon.ContextAwarePredicate;
import net.minecraft.advancements.critereon.EntityPredicate;
import net.minecraft.server.level.ServerPlayer;

import java.util.Optional;

public class RidingStatBoostCriterion(Optional<ContextAwarePredicate> playerCtx, boolean requiresOwner = true,  String rideStat, Double statValue, boolean isMax) : SimpleCriterionCondition<RidingStatBoostContext>(playerCtx) {

    final class Companion {
        public final CODEC = Codec<RidingStatBoostCriterion> by lazy {RecordCodecBuilder.create { it.group(EntityPredicate.ADVANCEMENT_CODEC.optionalFieldOf("player").forGetter { it.playerCtx }, Codec.BOOL.optionalFieldOf("requires_owner", true).forGetter { it.requiresOwner }, Codec.STRING.optionalFieldOf("ride_stat", "any").forGetter { it.rideStat }, Codec.DOUBLE.optionalFieldOf("stat_value", 0.0).forGetter { it.statValue }, Codec.BOOL.optionalFieldOf("is_max", false).forGetter { it.isMax }).apply(it, ::RidingStatBoostCriterion) }}
    }

    @Override boolean matches(ServerPlayer player, context: RidingStatBoostContext) {
        if (requiresOwner && context.pokemon.ownerUUID != player.uuid) {
            return false;
        }
        RidingStyle.entries.forEach { ridingStyle ->
            RidingStat.entries.forEach { rideStat ->
                if (rideStat.name.equals(this.rideStat, true) || this.rideStat == "any" || this.rideStat == "all"); {
                    val max = context.pokemon.pokemon.getMaxRideBoost(rideStat).toDouble();
                    val min = context.pokemon.pokemon.getBaseRideStat(rideStat).toDouble();
                    val value = context.pokemon.getRideStat(rideStat, ridingStyle, min, max);
                    val isSufficient = (value >= this.statValue && this.statValue > 0.0) || !context.pokemon.pokemon.canAddRideBoost(rideStat);
                    if (isSufficient && this.rideStat == "any") {
                        return true;
                    } else if (!isSufficient && this.rideStat == "all") {
                        return false;
                    }
                }
            }
        }
        return this.rideStat == "all";
    }
}
