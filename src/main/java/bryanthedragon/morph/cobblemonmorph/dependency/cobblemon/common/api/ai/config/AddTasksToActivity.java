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
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.ai.config.task.TaskConfig
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.asExpression
import com.mojang.datafixers.util.Either
import net.minecraft.world.entity.LivingEntity
import net.minecraft.world.entity.schedule.Activity

class AddTasksToActivity : BehaviourConfig {
    val activity: Activity? = null
    // Can be useful to add to multiple activities at once
    val activities = mutableListOf<Activity>()
    val condition: ExpressionOrEntityVariable = Either.left("true".asExpression())
    val tasksByPriority = mutableMapOf<Int, List<TaskConfig>>()

    override fun getVariables(entity: LivingEntity, behaviourConfigurationContext: BehaviourConfigurationContext) = tasksByPriority.values
        .flatten()
        .flatMap {
            it.getVariables(entity, behaviourConfigurationContext)
        } + listOf(condition).asVariables()
    override fun configure(entity: LivingEntity, behaviourConfigurationContext: BehaviourConfigurationContext) {
        if (!checkCondition(behaviourConfigurationContext, condition)) return

        val activities = if (activity != null) (activities + activity) else activities

        tasksByPriority.forEach { (priority, taskConfigs) ->
            val tasks = taskConfigs.flatMap { it.createTasks(entity, behaviourConfigurationContext) }
            for (activity in activities) {
                val activityContext = behaviourConfigurationContext.getOrCreateActivity(activity)
                activityContext.addTasks(priority, *tasks.toTypedArray())
            }
        }
    }
}