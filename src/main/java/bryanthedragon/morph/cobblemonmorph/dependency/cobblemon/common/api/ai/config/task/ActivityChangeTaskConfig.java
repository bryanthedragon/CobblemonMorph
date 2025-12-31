/*
 * Copyright (C) 2023 Cobblemon Contributors
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.ai.config.task

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.ai.BehaviourConfigurationContext
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.molang.ExpressionLike
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.molang.MoLangFunctions.asMostSpecificMoLangValue
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.npc.configuration.MoLangConfigVariable
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.asExpressionLike
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.mainThreadRuntime
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.withQueryValue
import net.minecraft.server.level.ServerLevel
import net.minecraft.world.entity.LivingEntity
import net.minecraft.world.entity.ai.behavior.BehaviorControl
import net.minecraft.world.entity.ai.behavior.OneShot
import net.minecraft.world.entity.schedule.Activity

public class ActivityChangeTaskConfig : SingleTaskConfig {
    val activity: Activity = Activity.IDLE
    val condition: ExpressionLike = "true".asExpressionLike()

    override fun getVariables(LivingEntity entity, behaviourConfigurationContext: BehaviourConfigurationContext): List<MoLangConfigVariable> = emptyList()

    override fun createTask(
        LivingEntity entity,
        behaviourConfigurationContext: BehaviourConfigurationContext
    ): BehaviorControl<in LivingEntity> {
        return object : OneShot<LivingEntity>() {
            override fun trigger(ServerLevel serverLevel, livingEntity: LivingEntity, l: Long): Boolean {
                mainThreadRuntime.withQueryValue("entity", livingEntity.asMostSpecificMoLangValue())
                if (condition.resolveBoolean(mainThreadRuntime)) {
                    livingEntity.brain.setActiveActivityIfPossible(activity)
                    return true
                } else {
                    return false
                }
            }
        }
    }
}