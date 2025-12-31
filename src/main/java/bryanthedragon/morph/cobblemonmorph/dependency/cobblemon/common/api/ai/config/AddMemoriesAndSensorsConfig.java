/*
 * Copyright (C) 2023 Cobblemon Contributors
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.ai.config

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.ai.BehaviourConfigurationContext
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.ai.ExpressionOrEntityVariable
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.ai.asVariables
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.asExpression
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.resolveBoolean
import com.mojang.datafixers.util.Either
import net.minecraft.world.entity.LivingEntity
import net.minecraft.world.entity.ai.memory.MemoryModuleType
import net.minecraft.world.entity.ai.sensing.SensorType

/**
 * Does nothing except conditionally add some memories and sensors to the brain of the entity.
 *
 * @author Hiroku
 * @since June 27th, 2025
 */
public class AddMemoriesAndSensorsConfig : BehaviourConfig {
    val condition: ExpressionOrEntityVariable = Either.left("true".asExpression())
    val memories: Set<MemoryModuleType<*>> = emptySet()
    val sensors: Set<SensorType<*>> = mutableSetOf()

    override fun getVariables(LivingEntity entity, behaviourConfigurationContext: BehaviourConfigurationContext) = listOf(condition).asVariables()
    override fun configure(LivingEntity entity, behaviourConfigurationContext: BehaviourConfigurationContext) {
        if (!checkCondition(behaviourConfigurationContext, condition)) return
        behaviourConfigurationContext.addMemories(memories)
        behaviourConfigurationContext.addSensors(sensors)
    }
}