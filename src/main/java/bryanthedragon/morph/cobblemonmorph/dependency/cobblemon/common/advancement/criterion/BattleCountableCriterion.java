/*
 * Copyright (C) 2023 Cobblemon Contributors
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.advancement.criterion;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.Cobblemon;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

import net.minecraft.advancements.critereon.ContextAwarePredicate;
import net.minecraft.server.level.ServerPlayer;

import java.util.List;
import java.util.Optional;

public class BattleCountableCriterion(Optional<ContextAwarePredicate> playerCtx, List<String> battleTypes, int count): CountableCriterion<BattleCountableContext>(playerCtx, count) {

    private var battleTypes = mutableListOf("any");

    final class Companion {
        public final Codec<BattleCountableCriterion> CODEC = RecordCodecBuilder.create { it.group(ContextAwarePredicate.CODEC.optionalFieldOf("player").forGetter(BattleCountableCriterion::playerCtx), Codec.STRING.listOf().optionalFieldOf("battle_types", listOf("any")).forGetter(BattleCountableCriterion::battleTypes), Codec.INT.optionalFieldOf("count", 0).forGetter(BattleCountableCriterion::count)).apply(it, ::BattleCountableCriterion) }
    }

    public boolean matches(ServerPlayer player, BattleCountableContext context) {
        var typeCheck = false;
        val advancementData = Cobblemon.playerDataManager.getGenericData(player).advancementData;

        if (battleTypes.isEmpty() || battleTypes.contains("any")) {
            typeCheck = true;
        }

        if (battleTypes.contains("pvp")) {
            typeCheck = context.battle.isPvP;
            context.times = advancementData.totalPvPBattleVictoryCount;
        }

        if (battleTypes.contains("pvw")) {
            typeCheck = context.battle.isPvW;
            context.times = advancementData.totalPvWBattleVictoryCount;
        }

        if (battleTypes.contains("pvn")) {
            typeCheck = context.battle.isPvN;
            context.times = advancementData.totalPvWBattleVictoryCount;
        }

        if (battleTypes.size > 1) {
            context.times = advancementData.totalBattleVictoryCount;
        }
        return typeCheck && super.matches(player, context);
    }

    fun fromJson(JsonObject json) {
       super.fromJson(json);
       if(!json.get("battle_types").isJsonNull) {
           battleTypes.clear();
           json.get("battle_types").asJsonArray.asList().forEach() {
               battleTypes.add(it.asString);
           }
       }
   }

   fun toJson(JsonObject json) {
       super.toJson(json);
       json.add("battle_types", battleTypes.toJsonArray());
   }

}