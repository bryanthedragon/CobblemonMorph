/*
 * Copyright (C) 2023 Cobblemon Contributors
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.ai.config.task

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.ai.BehaviourConfigurationContext
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.ai.ExpressionOrEntityVariable
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.ai.WrapperLivingEntityTask
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.ai.asVariables
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.molang.MoLangFunctions.asMostSpecificMoLangValue
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.entity.ai.FollowWalkTargetTask
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.asExpression
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.withQueryValue
import com.mojang.datafixers.util.Either
import net.minecraft.world.entity.LivingEntity
import net.minecraft.world.entity.PathfinderMob
import net.minecraft.world.entity.ai.behavior.BehaviorControl
import net.minecraft.world.entity.ai.memory.MemoryModuleType

public class FollowWalkTargetTaskConfig : SingleTaskConfig {
    val condition: ExpressionOrEntityVariable = Either.left("true".asExpression())
    val minRunTicks: ExpressionOrEntityVariable = Either.left("150".asExpression())
    val maxRunTicks: ExpressionOrEntityVariable = Either.left("250".asExpression())

    override fun getVariables(LivingEntity entity, behaviourConfigurationContext: BehaviourConfigurationContext) = listOf(minRunTicks, maxRunTicks).asVariables()

    override fun createTask(LivingEntity entity, behaviourConfigurationContext: BehaviourConfigurationContext): BehaviorControl<LivingEntity>? {
        behaviourConfigurationContext.addMemories(MemoryModuleType.WALK_TARGET, MemoryModuleType.PATH, MemoryModuleType.CANT_REACH_WALK_TARGET_SINCE)
        return WrapperLivingEntityTask(
            FollowWalkTargetTask(minRunTicks.resolveInt(behaviourConfigurationContext.runtime), maxRunTicks.resolveInt(behaviourConfigurationContext.runtime)),
            PathfinderMob.class
        )
    }
}