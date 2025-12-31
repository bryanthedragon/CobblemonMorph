/*
 * Copyright (C) 2023 Cobblemon Contributors
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.ai.config.task

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.CobblemonMemories
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.ai.BehaviourConfigurationContext
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.ai.WrapperLivingEntityTask
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.ai.asVariables
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.entity.ai.StayAfloatTask
import net.minecraft.world.entity.LivingEntity
import net.minecraft.world.entity.PathfinderMob
import net.minecraft.world.entity.ai.behavior.BehaviorControl

public class StayAfloatTaskConfig : SingleTaskConfig {
    val condition = booleanVariable(SharedEntityVariables.MOVEMENT_CATEGORY, "can_float", true).asExpressible()
    val chance = numberVariable(SharedEntityVariables.MOVEMENT_CATEGORY, "float_chance", 0.8F).asExpressible()

    override fun getVariables(LivingEntity entity, behaviourConfigurationContext: BehaviourConfigurationContext) = listOf(condition, chance).asVariables()
    override fun createTask(LivingEntity entity, behaviourConfigurationContext: BehaviourConfigurationContext): BehaviorControl<LivingEntity>? {
        if (!condition.resolveBoolean(behaviourConfigurationContext.runtime)) return null
        behaviourConfigurationContext.addMemories(CobblemonMemories.POKEMON_BATTLE)
        return WrapperLivingEntityTask(
            StayAfloatTask(chance.resolveFloat(behaviourConfigurationContext.runtime)),
            PathfinderMob.class
        )
    }
}