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
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.ai.ExpressionOrEntityVariable
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.ai.asVariables
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.ai.config.task.GoToHealingMachineTaskConfig.Companion.SELF_HEALING
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.ai.config.task.GoToHealingMachineTaskConfig.Companion.USE_HEALING_MACHINES
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.molang.MoLangFunctions.asMostSpecificMoLangValue
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.entity.npc.ai.HealUsingHealingMachineTask
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.asExpression
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.withQueryValue
import com.mojang.datafixers.util.Either
import net.minecraft.world.entity.LivingEntity
import net.minecraft.world.entity.ai.behavior.BehaviorControl
import net.minecraft.world.entity.ai.memory.MemoryModuleType

class HealUsingHealingMachineTaskConfig : SingleTaskConfig {
    val condition = booleanVariable(SELF_HEALING, USE_HEALING_MACHINES, true).asExpressible()
    val horizontalUseRange: ExpressionOrEntityVariable = Either.left("2".asExpression())
    val verticalUseRange: ExpressionOrEntityVariable = Either.left("1".asExpression())

    override fun getVariables(entity: LivingEntity, behaviourConfigurationContext: BehaviourConfigurationContext) = listOf(condition, horizontalUseRange, verticalUseRange).asVariables()

    override fun createTask(
        entity: LivingEntity,
        behaviourConfigurationContext: BehaviourConfigurationContext
    ): BehaviorControl<in LivingEntity>? {
        if (!condition.resolveBoolean(behaviourConfigurationContext.runtime)) return null
        behaviourConfigurationContext.addMemories(MemoryModuleType.WALK_TARGET, CobblemonMemories.NPC_BATTLING,)
        behaviourConfigurationContext.addSensors(CobblemonSensors.NPC_BATTLING)
        return HealUsingHealingMachineTask(horizontalUseRange.asExpression(), verticalUseRange.asExpression())
    }
}