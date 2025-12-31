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
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.ai.WrapperLivingEntityTask.Companion.wrapped
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.ai.asVariables
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.ai.config.task.WanderTaskConfig.Companion.WANDER
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.molang.MoLangFunctions.asMostSpecificMoLangValue
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.npc.configuration.MoLangConfigVariable
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.entity.PoseType
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.entity.ai.CircleAroundTask
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.entity.pokemon.PokemonEntity
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.asExpression
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.withQueryValue
import com.mojang.datafixers.util.Either
import net.minecraft.world.entity.LivingEntity
import net.minecraft.world.entity.ai.behavior.BehaviorControl
import net.minecraft.world.entity.ai.memory.MemoryModuleType

public class FlyInCirclesTaskConfig : SingleTaskConfig {
    val poseTypes: Set<PoseType> = setOf(
        PoseType.FLY,
        PoseType.HOVER
    )

    val minAngularVelocityDegrees = numberVariable(WANDER, "min_fly_circling_angular_velocity", 0.0).asExpressible()
    val maxAngularVelocityDegrees = numberVariable(WANDER, "max_fly_circling_angular_velocity", 1.0).asExpressible()
    val speed = numberVariable(WANDER, "fly_circling_speed", 0.6).asExpressible()
    val verticalSpeed: ExpressionOrEntityVariable = Either.left("0.0".asExpression())
    val minDurationTicks: ExpressionOrEntityVariable = Either.left("60".asExpression())
    val maxDurationTicks: ExpressionOrEntityVariable = Either.left("180".asExpression())

    override fun getVariables(LivingEntity entity, behaviourConfigurationContext: BehaviourConfigurationContext): List<MoLangConfigVariable> {
        return listOf(
            minAngularVelocityDegrees,
            maxAngularVelocityDegrees,
            speed,
            verticalSpeed,
            minDurationTicks,
            maxDurationTicks
        ).asVariables()
    }

    override fun createTask(
        LivingEntity entity,
        behaviourConfigurationContext: BehaviourConfigurationContext
    ): BehaviorControl<in LivingEntity>? {
        if (entity !is PokemonEntity) {
            return null
        }
        behaviourConfigurationContext.addMemories(MemoryModuleType.WALK_TARGET, MemoryModuleType.PATH)
        return CircleAroundTask(
            poseTypes = poseTypes,
            minTurnAngleDegrees = minAngularVelocityDegrees.resolveFloat(behaviourConfigurationContext.runtime),
            maxTurnAngleDegrees = maxAngularVelocityDegrees.resolveFloat(behaviourConfigurationContext.runtime),
            speed = speed.resolveFloat(behaviourConfigurationContext.runtime),
            verticalSpeed = verticalSpeed.resolveFloat(behaviourConfigurationContext.runtime),
            minDurationTicks = minDurationTicks.resolveFloat(behaviourConfigurationContext.runtime).toInt(),
            maxDurationTicks = maxDurationTicks.resolveFloat(behaviourConfigurationContext.runtime).toInt()
        ).wrapped<PokemonEntity>()
    }
}