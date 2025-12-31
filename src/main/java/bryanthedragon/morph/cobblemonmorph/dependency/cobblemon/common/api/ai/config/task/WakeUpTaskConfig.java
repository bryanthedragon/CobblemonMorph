/*
 * Copyright (C) 2023 Cobblemon Contributors
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.ai.config.task

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.CobblemonMemories
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.CobblemonSensors
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.ai.BehaviourConfigurationContext
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.ai.WrapperLivingEntityTask
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.npc.configuration.MoLangConfigVariable
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.entity.pokemon.PokemonEntity
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.entity.pokemon.ai.tasks.WakeUpTask
import net.minecraft.world.entity.LivingEntity
import net.minecraft.world.entity.ai.behavior.BehaviorControl

public class WakeUpTaskConfig : SingleTaskConfig {
    override fun getVariables(LivingEntity entity, behaviourConfigurationContext: BehaviourConfigurationContext) = emptyList<MoLangConfigVariable>()
    override fun createTask(
        LivingEntity entity,
        behaviourConfigurationContext: BehaviourConfigurationContext
    ): BehaviorControl<in LivingEntity>? {
        if (entity !is PokemonEntity) {
            return null
        }
        behaviourConfigurationContext.addMemories(CobblemonMemories.POKEMON_DROWSY, CobblemonMemories.POKEMON_SLEEPING)
        behaviourConfigurationContext.addSensors(CobblemonSensors.POKEMON_DROWSY)
        return WrapperLivingEntityTask(WakeUpTask.create(), PokemonEntity.class)
    }
}