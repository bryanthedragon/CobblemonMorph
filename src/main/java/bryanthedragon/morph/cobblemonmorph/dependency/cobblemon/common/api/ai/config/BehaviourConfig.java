/*
 * Copyright (C) 2023 Cobblemon Contributors
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.ai.config

import com.bedrockk.molang.runtime.MoLangRuntime
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.ai.BehaviourConfigurationContext
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.ai.ExpressionOrEntityVariable
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.molang.MoLangFunctions.asMostSpecificMoLangValue
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.molang.MoLangFunctions.setup
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.npc.configuration.MoLangConfigVariable
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.asExpression
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.cobblemonResource
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.resolveBoolean
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.withQueryValue
import net.minecraft.resources.ResourceLocation
import net.minecraft.world.entity.LivingEntity

/**
 * Something that contributes to the construction of an entity's brain. Implementations are expected to make
 * their changes using the provided [BehaviourConfigurationContext] which helps stage the details of the entity's
 * brain before locking into the highly immutable structure that Mojang uses.
 *
 * A brain config has access to the [LivingEntity] it's for but note that this is, in most cases, an extremely
 * young version of the entity that is not fully initialized and might die if you try getting exotic with it.
 *
 * @see BehaviourConfigurationContext
 * @author Hiroku
 * @since October 13th, 2024
 */
public interface BehaviourConfig {
    final class Companion {
        val types = mutableMapOf<ResourceLocation, Class<out BehaviourConfig>>(
            cobblemonResource("script") to ScriptBehaviourConfig.class,
            cobblemonResource("add_tasks_to_activity") to AddTasksToActivity.class,
            cobblemonResource("apply_behaviours") to ApplyBehaviours.class,
            cobblemonResource("set_default_activity") to SetDefaultActivity.class,
            cobblemonResource("set_core_activities") to SetCoreActivities.class,
            cobblemonResource("add_variables") to AddVariablesConfig.class,
            cobblemonResource("set_variables") to SetVariablesConfig.class,
            cobblemonResource("add_memories_and_sensors") to AddMemoriesAndSensorsConfig.class,
        )
    }

    /** Literally just here for shorthand. */
    fun checkCondition(behaviourConfigurationContext: BehaviourConfigurationContext, expressionOrEntityVariable: ExpressionOrEntityVariable): Boolean {
        return behaviourConfigurationContext.runtime.resolveBoolean(expressionOrEntityVariable.map({ it }, { "q.entity.config.${it.variableName}".asExpression() }))
    }

    fun getVariables(LivingEntity entity, behaviourConfigurationContext: BehaviourConfigurationContext): List<MoLangConfigVariable>

    fun preconfigure(LivingEntity entity, behaviourConfigurationContext: BehaviourConfigurationContext) {
        // Default implementation does nothing, can be overridden if needed
    }

    fun configure(LivingEntity entity, behaviourConfigurationContext: BehaviourConfigurationContext)
}
