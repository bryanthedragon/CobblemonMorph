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
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.ai.asVariables
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.npc.configuration.MoLangConfigVariable
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.entity.ai.FleeFromAttackerTask
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.asExpression
import com.mojang.datafixers.util.Either
import net.minecraft.world.entity.LivingEntity
import net.minecraft.world.entity.ai.behavior.BehaviorControl
import net.minecraft.world.entity.ai.memory.MemoryModuleType
import net.minecraft.world.entity.ai.sensing.SensorType

public class FleeAttackerTaskConfig : SingleTaskConfig {
    val avoidDurationTicks: ExpressionOrEntityVariable = Either.left("600".asExpression())

    override fun getVariables(
        LivingEntity entity,
        behaviourConfigurationContext: BehaviourConfigurationContext
    ): List<MoLangConfigVariable> {
        return listOf(avoidDurationTicks).asVariables()
    }

    override fun createTask(
        LivingEntity entity,
        behaviourConfigurationContext: BehaviourConfigurationContext
    ): BehaviorControl<in LivingEntity> {
        behaviourConfigurationContext.addMemories(
            MemoryModuleType.HURT_BY_ENTITY,
            MemoryModuleType.AVOID_TARGET
        )
        behaviourConfigurationContext.addSensors(SensorType.HURT_BY)
        return FleeFromAttackerTask.create(avoidDurationTicks.asExpression())
    }
}