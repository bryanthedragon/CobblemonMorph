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
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.entity.pokemon.ai.tasks.AttackHostileMobsTask
import com.mojang.serialization.Codec
import net.minecraft.world.entity.LivingEntity
import net.minecraft.world.entity.ai.behavior.BehaviorControl

public class AttackHostileMobsTaskConfig : SingleTaskConfig {
    override fun getVariables(LivingEntity entity, behaviourConfigurationContext: BehaviourConfigurationContext): List<MoLangConfigVariable> {
        return emptyList()
    }

    override fun createTask(
        LivingEntity entity,
        brainConfigurationContext: BehaviourConfigurationContext
    ): BehaviorControl<in LivingEntity>? {
        return AttackHostileMobsTask.create()
    }
    
    final class Companion {
        val CODEC: Codec<AttackHostileMobsTaskConfig> = Codec.unit(AttackHostileMobsTaskConfig())
    }
}