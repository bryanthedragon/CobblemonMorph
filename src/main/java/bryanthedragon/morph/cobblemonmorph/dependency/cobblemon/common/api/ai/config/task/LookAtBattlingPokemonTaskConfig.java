/*
 * Copyright (C) 2023 Cobblemon Contributors
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.ai.config.task

import com.bedrockk.molang.runtime.MoLangRuntime
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.CobblemonMemories
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.CobblemonSensors
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.ai.BehaviourConfigurationContext
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.ai.ExpressionOrEntityVariable
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.ai.asVariables
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.entity.npc.ai.LookAtBattlingPokemonTask
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.asExpression
import com.mojang.datafixers.util.Either
import net.minecraft.world.entity.LivingEntity
import net.minecraft.world.entity.ai.behavior.BehaviorControl
import net.minecraft.world.entity.ai.memory.MemoryModuleType

class LookAtBattlingPokemonTaskConfig : SingleTaskConfig {
    companion object {
        const val LOOK_AT_BATTLING_POKEMON = "look_at_battling_pokemon"
    }

    val condition = booleanVariable(SharedEntityVariables.BATTLING_CATEGORY, LOOK_AT_BATTLING_POKEMON, true).asExpressible()
    val minDurationTicks: ExpressionOrEntityVariable = Either.left("40".asExpression())
    val maxDurationTicks: ExpressionOrEntityVariable = Either.left("80".asExpression())

    override fun getVariables(entity: LivingEntity, behaviourConfigurationContext: BehaviourConfigurationContext) = listOf(
        condition,
        minDurationTicks,
        maxDurationTicks
    ).asVariables()

    override fun createTask(
        entity: LivingEntity,
        behaviourConfigurationContext: BehaviourConfigurationContext
    ): BehaviorControl<in LivingEntity>? {
        if (!resolveBooleanVariable(LOOK_AT_BATTLING_POKEMON, behaviourConfigurationContext.runtime)) return null
        behaviourConfigurationContext.addMemories(
            MemoryModuleType.LOOK_TARGET,
            CobblemonMemories.BATTLING_POKEMON
        )
        behaviourConfigurationContext.addSensors(CobblemonSensors.BATTLING_POKEMON)
        return LookAtBattlingPokemonTask.create(
            minDurationTicks = minDurationTicks.resolveInt(behaviourConfigurationContext.runtime),
            maxDurationTicks = maxDurationTicks.resolveInt(behaviourConfigurationContext.runtime)
        )
    }
}