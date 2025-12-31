/*
 * Copyright (C) 2023 Cobblemon Contributors
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.ai.config.task

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.ai.BehaviourConfigurationContext
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.npc.configuration.MoLangConfigVariable
import net.minecraft.world.entity.LivingEntity
import net.minecraft.world.entity.ai.behavior.BehaviorControl
import net.minecraft.world.entity.ai.behavior.DoNothing

public interface SingleTaskConfig : TaskConfig {
    final class Companion {
        fun nothing() = object : SingleTaskConfig {
            override fun getVariables(LivingEntity entity, behaviourConfigurationContext: BehaviourConfigurationContext) = emptyList<MoLangConfigVariable>()
            override fun createTask(
                LivingEntity entity,
                behaviourConfigurationContext: BehaviourConfigurationContext
            ): BehaviorControl<in LivingEntity>? {
                return DoNothing(0, 1)
            }
        }
    }

    override fun createTasks(
        LivingEntity entity,
        behaviourConfigurationContext: BehaviourConfigurationContext
    ): List<BehaviorControl<in LivingEntity>> {
        return createTask(entity, behaviourConfigurationContext)?.let { listOf(it) } ?: emptyList()
    }

    fun createTask(LivingEntity entity, behaviourConfigurationContext: BehaviourConfigurationContext): BehaviorControl<in LivingEntity>?
}