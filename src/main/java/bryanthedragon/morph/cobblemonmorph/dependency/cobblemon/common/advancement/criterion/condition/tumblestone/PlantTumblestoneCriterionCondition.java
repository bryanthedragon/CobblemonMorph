/*
 * Copyright (C) 2023 Cobblemon Contributors
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.advancement.criterion.condition.tumblestone;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.advancement.criterion.context.tumblestone.PlantTumblestoneContext;

import net.minecraft.advancements.critereon.ContextAwarePredicate;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;

import java.util.Optional;

public class PlantTumblestoneCriterionCondition(Optional<ContextAwarePredicate> playerCtx) extends SimpleCriterionCondition<PlantTumblestoneContext>(playerCtx) {

    final class Companion {
        public final Codec<PlantTumblestoneCriterionCondition> CODEC = RecordCodecBuilder.create { it.group(ContextAwarePredicate.CODEC.optionalFieldOf("player").forGetter(PlantTumblestoneCriterionCondition::playerCtx)).apply(it, ::PlantTumblestoneCriterionCondition) };
    }

    public boolean matches(ServerPlayer player, PlantTumblestoneContext context) {
        return context.block.canGrow(context.pos, player.level());
    }
}

///**
// * A criterion condition for when a tumblestone block is planted
// * @param id The identifier of the criterion
// * @param predicate The predicate for the criterion
// *
// * @author Aethen
// * @since 02/28/2024
// */
//class PlantTumblestoneCriterionCondition(id: Identifier, predicate: LootContextPredicate) :
//    SimpleCriterionCondition<PlantTumblestoneContext>(id, predicate) {
//    override fun toJson(JsonObject json) {
//        // Add properties to json if needed. None needed for this criterion
//    }
//
//    override fun fromJson(JsonObject json) {
//        // Parse properties from json if needed. None needed for this criterion
//    }
//
//    /**
//     * Checks if the tumblestone block can grow at the given position
//     * @param player The player that planted the tumblestone block
//     * @param context The context of the criterion
//     * @return True if the tumblestone block can grow at the given position, false otherwise
//     */
//    override fun matches(ServerPlayer player, context: PlantTumblestoneContext): Boolean {
//        return context.tumbleStoneBlock.canGrow(context.pos, player.level())
//    }
//}
//
///**
// * The context of the [PlantTumblestoneCriterionCondition]
// * @param pos The position of the tumblestone block
// * @param tumbleStoneBlock The tumblestone block
// */
//open class PlantTumblestoneContext(var (BlockPos pos, var tumbleStoneBlock: TumblestoneBlock)