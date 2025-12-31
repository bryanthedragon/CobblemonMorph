/*
 * Copyright (C) 2023 Cobblemon Contributors
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.ai.config.task

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.ai.BehaviourConfigurationContext
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.ai.WrapperLivingEntityTask
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.ai.asVariables
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.entity.pokemon.PokemonEntity
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.entity.pokemon.ai.tasks.PlaceHoneyInHiveTask
import net.minecraft.world.entity.LivingEntity
import net.minecraft.world.entity.ai.behavior.BehaviorControl

public class PlaceHoneyInHiveTaskConfig : SingleTaskConfig {
    final class Companion {
        const val HONEY = "honey"
    }

    val condition = booleanVariable(HONEY, "can_add_honey", true).asExpressible()

    override fun getVariables(LivingEntity entity, behaviourConfigurationContext: BehaviourConfigurationContext) = listOf(
        condition
    ).asVariables()

    override fun createTask(
        LivingEntity entity,
        behaviourConfigurationContext: BehaviourConfigurationContext
    ): BehaviorControl<in LivingEntity>? {
        if (entity !is PokemonEntity) {
            return null
        }
        if (!checkCondition(behaviourConfigurationContext.runtime, condition)) {
            return null
        }
        return WrapperLivingEntityTask(
            PlaceHoneyInHiveTask.create(),
            PokemonEntity.class
        )
    }
}