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
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.ai.BehaviourConfigurationContext
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.ai.WrapperLivingEntityTask.Companion.wrapped
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.molang.MoLangFunctions.asMostSpecificMoLangValue
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.molang.MoLangFunctions.setup
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.npc.configuration.MoLangConfigVariable
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.mainThreadRuntime
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.resolveBoolean
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.withQueryValue
import java.util.function.Predicate
import net.minecraft.world.entity.LivingEntity
import net.minecraft.world.entity.Mob
import net.minecraft.world.entity.ai.behavior.BehaviorControl
import net.minecraft.world.entity.ai.behavior.StopAttackingIfTargetInvalid
import net.minecraft.world.entity.ai.memory.MemoryModuleType

/**
 * A task config wrapping around vanilla's [StopAttackingIfTargetInvalid] that supports the optional ATTACK_TARGET_DATA
 * memory to determine if the target is still valid for attacking.
 *
 * @author Hiroku
 * @since June 23rd, 2025
 */
class StopAttackingIfTargetInvalidTaskConfig : SingleTaskConfig {
    override fun getVariables(entity: LivingEntity, behaviourConfigurationContext: BehaviourConfigurationContext) = emptyList<MoLangConfigVariable>()
    override fun createTask(
        entity: LivingEntity,
        behaviourConfigurationContext: BehaviourConfigurationContext
    ): BehaviorControl<in LivingEntity> {
        behaviourConfigurationContext.addMemories(CobblemonMemories.ATTACK_TARGET_DATA, MemoryModuleType.CANT_REACH_WALK_TARGET_SINCE)
        return StopAttackingIfTargetInvalid.create<Mob>(
            Predicate { target ->
                val targetData = entity.brain.getMemory(CobblemonMemories.ATTACK_TARGET_DATA).orElse(null) ?: return@Predicate false
                mainThreadRuntime.withQueryValue("entity", target.asMostSpecificMoLangValue())
                return@Predicate !mainThreadRuntime.resolveBoolean(targetData.shouldContinue)
            }
        ).wrapped()
    }
}