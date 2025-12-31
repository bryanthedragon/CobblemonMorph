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
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.ai.CobblemonWanderControl
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.npc.configuration.MoLangConfigVariable
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.entity.pokemon.PokemonEntity
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.getMemorySafely
import net.minecraft.server.level.ServerLevel
import net.minecraft.world.entity.LivingEntity
import net.minecraft.world.entity.ai.behavior.BehaviorControl
import net.minecraft.world.entity.ai.behavior.OneShot

/**
 * Runs at priority zero to do the basics of setting up the wander control for any later wander
 * tasks to reference.
 *
 * @author Hiroku
 * @since November 19th, 2025
 */
public class PokemonWanderControlTaskConfig : SingleTaskConfig {
    override fun getVariables(LivingEntity entity, behaviourConfigurationContext: BehaviourConfigurationContext) = emptyList<MoLangConfigVariable>()
    override fun createTask(
        LivingEntity entity,
        behaviourConfigurationContext: BehaviourConfigurationContext
    ): BehaviorControl<in LivingEntity> {
        behaviourConfigurationContext.addMemories(CobblemonMemories.WANDER_CONTROL)

        return object : OneShot<LivingEntity>() {
            override fun trigger(ServerLevel serverLevel, livingEntity: LivingEntity, l: Long): Boolean {
                if (entity !is PokemonEntity) {
                    return false
                }

                val wanderControl = entity.brain.getMemorySafely(CobblemonMemories.WANDER_CONTROL).orElse(null)
                    ?: CobblemonWanderControl()

                wanderControl.reset()

                wanderControl.allowLand = (entity.behaviour.moving.walk.canWalk && !entity.behaviour.moving.walk.avoidsLand) || entity.behaviour.moving.fly.canFly
                wanderControl.allowWater = entity.behaviour.moving.swim.canSwimInWater && !entity.behaviour.moving.swim.avoidsWater
                wanderControl.allowAir = entity.behaviour.moving.fly.canFly && !entity.behaviour.moving.walk.canWalk

                wanderControl.wanderSpeed = 0.35F

                entity.brain.setMemory(CobblemonMemories.WANDER_CONTROL, wanderControl)

                return true
            }
        }
    }
}