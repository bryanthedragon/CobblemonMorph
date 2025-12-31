/*
 * Copyright (C) 2023 Cobblemon Contributors
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.ai.config

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.Cobblemon
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.CobblemonBehaviours
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.ai.BehaviourConfigurationContext
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.ai.ExpressionOrEntityVariable
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.ai.asVariables
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.npc.configuration.MoLangConfigVariable
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.asExpression
import com.google.gson.annotations.SerializedName
import com.mojang.datafixers.util.Either
import net.minecraft.resources.ResourceLocation
import net.minecraft.world.entity.LivingEntity

public class ApplyBehaviours : BehaviourConfig {
    var condition: ExpressionOrEntityVariable = Either.left("true".asExpression())
    @SerializedName("behaviours", alternate = ["behaviors"])
    val behaviours = mutableListOf<ResourceLocation>()

    override fun getVariables(LivingEntity entity, behaviourConfigurationContext: BehaviourConfigurationContext): List<MoLangConfigVariable> {
        return if (checkCondition(behaviourConfigurationContext, condition)) {
            behaviours.flatMap {
                CobblemonBehaviours.behaviours[it]?.configurations?.flatMap { it.getVariables(entity, behaviourConfigurationContext) } ?: emptyList()
            } + listOf(condition).asVariables()
        } else {
            listOf(condition).asVariables()
        }
    }

    override fun preconfigure(LivingEntity entity, behaviourConfigurationContext: BehaviourConfigurationContext) {
        if (!checkCondition(behaviourConfigurationContext, condition)) return

        val configurations = behaviours.map { CobblemonBehaviours.behaviours[it]?.takeIf { it.canBeApplied(entity) } ?: return }
        configurations.forEach {
            it.configurations.forEach { it.preconfigure(entity, behaviourConfigurationContext) }
        }
    }

    override fun configure(LivingEntity entity, behaviourConfigurationContext: BehaviourConfigurationContext) {
        if (!checkCondition(behaviourConfigurationContext, condition)) return

        val applicableBehaviours = behaviours.mapNotNull {
            val resolvedBehaviour = CobblemonBehaviours.behaviours[it]
            if (resolvedBehaviour == null) {
                Cobblemon.LOGGER.warn("Behaviour $it not found while configuring entity of type ${entity.type}")
            } else if (!resolvedBehaviour.canBeApplied(entity)) {
                Cobblemon.LOGGER.warn("Behaviour $it cannot be applied to entity ${entity.id}")
            } else {
                return@mapNotNull resolvedBehaviour
            }
            null
        }

        // Why not just add the presets to the context directly?
        // Nested preset application is a thing, and I only want to track the top level presets.
        // i.e. if a preset applies another preset, I don't want to track the inner preset, since it's redundant if we track the top one.
        val originalContextBehaviours = behaviourConfigurationContext.appliedBehaviours.toMutableSet()
        applicableBehaviours.forEach {
            it.configure(entity, behaviourConfigurationContext)
        }
        originalContextBehaviours.addAll(behaviours)
        behaviourConfigurationContext.appliedBehaviours = originalContextBehaviours
    }
}