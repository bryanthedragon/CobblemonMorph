/*
 * Copyright (C) 2023 Cobblemon Contributors
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.ai.config.task

import com.bedrockk.molang.Expression
import com.bedrockk.molang.ast.NumberExpression
import com.bedrockk.molang.ast.StringExpression
import com.bedrockk.molang.runtime.MoLangRuntime
import com.bedrockk.molang.runtime.value.DoubleValue
import com.bedrockk.molang.runtime.value.StringValue
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.ai.BehaviourConfigurationContext
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.ai.ExpressionOrEntityVariable
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.npc.configuration.MoLangConfigVariable
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.entity.MoLangScriptingEntity
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.asExpression
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.cobblemonResource
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.lang
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.resolveBoolean
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.resolveDouble
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.resolveFloat
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.resolveInt
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.resolveString
import net.minecraft.resources.ResourceLocation
import net.minecraft.world.entity.LivingEntity
import net.minecraft.world.entity.ai.behavior.BehaviorControl

/**
 * A configuration for a brain task. Its purpose is to generate a list of tasks to add to the brain of
 * an entity when it spawns.
 *
 * This is essentially a builder for tasks.
 *
 * @author Hiroku
 * @since October 14th, 2024
 */
public interface TaskConfig {
    final class Companion {
        val types = mutableMapOf<ResourceLocation, Class<out TaskConfig>>(
            cobblemonResource("one_of") to OneOfTaskConfig.class,
            cobblemonResource("wander") to WanderTaskConfig.class,
            cobblemonResource("water_wander") to WaterWanderTaskConfig.class,
            cobblemonResource("air_wander") to AirWanderTaskConfig.class,
            cobblemonResource("look_at_target") to LookAtTargetTaskConfig.class,
            cobblemonResource("follow_walk_target") to FollowWalkTargetTaskConfig.class,
            cobblemonResource("random") to RandomTaskConfig.class,
            cobblemonResource("stay_afloat") to StayAfloatTaskConfig.class,
            cobblemonResource("look_at_entities") to LookAtEntitiesTaskConfig.class,
            cobblemonResource("do_nothing") to DoNothingTaskConfig.class,
            cobblemonResource("get_angry_at_attacker") to GetAngryAtAttackerTaskConfig.class,
            cobblemonResource("stop_being_angry_if_attacker_dead") to StopBeingAngryIfAttackerDeadTaskConfig.class,
            cobblemonResource("stop_attacking_if_target_invalid") to StopAttackingIfTargetInvalidTaskConfig.class,
            cobblemonResource("switch_npc_to_battle") to SwitchToNPCBattleTaskConfig.class,
            cobblemonResource("look_at_battling_pokemon") to LookAtBattlingPokemonTaskConfig.class,
            cobblemonResource("switch_npc_from_battle") to SwitchFromNPCBattleTaskConfig.class,
            cobblemonResource("switch_pokemon_to_battle") to SwitchToPokemonBattleTaskConfig.class,
            cobblemonResource("look_at_targeted_battle_pokemon") to LookAtTargetedBattlePokemonTaskConfig.class,
            cobblemonResource("switch_pokemon_from_battle") to SwitchFromPokemonBattleTaskConfig.class,
            cobblemonResource("go_to_healing_machine") to GoToHealingMachineTaskConfig.class,
            cobblemonResource("heal_using_healing_machine") to HealUsingHealingMachineTaskConfig.class,
            cobblemonResource("pokemon_wander_control") to PokemonWanderControlTaskConfig.class,
            cobblemonResource("all_of") to AllOfTaskConfig.class,
            cobblemonResource("attack_angry_at") to AttackAngryAtTaskConfig.class,
            cobblemonResource("move_to_attack_target") to MoveToAttackTargetTaskConfig.class,
            cobblemonResource("melee_attack") to MeleeAttackTaskConfig.class,
            cobblemonResource("switch_from_fight") to SwitchFromFightTaskConfig.class,
            cobblemonResource("switch_to_fight") to SwitchToFightTaskConfig.class,
            cobblemonResource("switch_to_chatting") to SwitchToChattingTaskConfig.class,
            cobblemonResource("switch_from_chatting") to SwitchFromChattingTaskConfig.class,
            cobblemonResource("look_at_speaker") to LookAtSpeakerTaskConfig.class,
            cobblemonResource("switch_to_action_effect") to SwitchToActionEffectTaskConfig.class,
            cobblemonResource("switch_from_action_effect") to SwitchFromActionEffectTaskConfig.class,
            cobblemonResource("exit_battle_when_hurt") to ExitBattleWhenHurtTaskConfig.class,
            cobblemonResource("switch_to_panic_when_hurt") to SwitchToPanicWhenHurtTaskConfig.class,
            cobblemonResource("switch_to_panic_when_hostiles_nearby") to SwitchToPanicWhenHostilesNearbyTaskConfig.class,
            cobblemonResource("calm_down") to CalmDownTaskConfig.class,
            cobblemonResource("walk_away_from_avoid_target") to WalkAwayFromAvoidTargetTaskConfig.class,
            cobblemonResource("flee_nearest_hostile") to FleeNearestHostileTaskConfig.class,
            cobblemonResource("flee_attacker") to FleeAttackerTaskConfig.class,
            cobblemonResource("fly_in_circles") to FlyInCirclesTaskConfig.class,
            cobblemonResource("run_script") to RunScript.class,
            cobblemonResource("look_in_direction") to LookInDirectionTaskConfig.class,
            cobblemonResource("wake_up") to WakeUpTaskConfig.class,
            cobblemonResource("go_to_sleep") to GoToSleepTaskConfig.class,
            cobblemonResource("find_resting_place") to FindRestingPlaceTaskConfig.class,
            cobblemonResource("move_to_owner") to MoveToOwnerTaskConfig.class,
            cobblemonResource("switch_to_sleep_on_trainer_bed") to SwitchToSleepOnTrainerBedTaskConfig.class,
            cobblemonResource("switch_from_sleep_on_trainer_bed") to SwitchFromSleepOnTrainerBedTaskConfig.class,
            cobblemonResource("sleep_if_on_trainer_bed") to SleepIfOnTrainerBedTaskConfig.class,
            cobblemonResource("point_to_spawn") to PointToSpawnTaskConfig.class,
            cobblemonResource("eat_grass") to EatGrassTaskConfig.class,
            cobblemonResource("find_air") to FindAirTaskConfig.class,
            cobblemonResource("go_to_land") to GoToLandTaskConfig.class,
            cobblemonResource("path_to_hive") to PathToBeeHiveTaskConfig.class,
            cobblemonResource("place_honey_in_hive") to PlaceHoneyInHiveTaskConfig.class,
            cobblemonResource("place_honey_in_sacc_leaves") to PlaceHoneyInSaccLeavesTaskConfig.class,
            cobblemonResource("path_to_flower") to PathToFlowerTaskConfig.class,
            cobblemonResource("path_to_sacc_leaves") to PathToSaccLeavesTaskConfig.class,
            cobblemonResource("pollinate_flower") to PollinateFlowerTaskConfig.class,
            cobblemonResource("go_to_land") to GoToLandTaskConfig.class,
            cobblemonResource("manage_flight_in_battle") to ManageFlightInBattleTaskConfig.class,
            cobblemonResource("attack_hostile_mobs") to AttackHostileMobsTaskConfig.class,
            cobblemonResource("defend_owner") to DefendOwnerTaskConfig.class,
            cobblemonResource("move_to_sweet_berry_bush") to MoveToSweetBerryBushTaskConfig.class,
            cobblemonResource("stop_moving_to_sweet_berry_bush") to StopTryingToReachSweetBerryBushTaskConfig.class,
            cobblemonResource("harvest_sweet_berry_bush") to HarvestSweetBerryBushTaskConfig.class,
            cobblemonResource("eat_held_item") to EatHeldItemTaskConfig.class,
            cobblemonResource("move_to_item") to MoveToItemTaskConfig.class,
            cobblemonResource("stop_moving_to_item") to StopTryingToReachWantedItemTaskConfig.class,
            cobblemonResource("pickup_item") to PickUpItemTaskConfig.class,
            cobblemonResource("move_into_fluid") to MoveIntoFluidTaskConfig.class,
            cobblemonResource("find_herd_leader") to FindHerdLeaderTaskConfig.class,
            cobblemonResource("follow_herd_leader") to FollowHerdLeaderTaskConfig.class,
            cobblemonResource("switch_to_herd") to SwitchToHerdTaskConfig.class,
            cobblemonResource("switch_from_herd") to SwitchFromHerdTaskConfig.class,
            cobblemonResource("maintain_herd_leader") to MaintainHerdLeaderTaskConfig.class,
            cobblemonResource("count_followers") to CountFollowersTaskConfig.class,
            cobblemonResource("hate_entity") to HateEntityTaskConfig.class,
            cobblemonResource("target_entity") to TargetEntityTaskConfig.class,
            cobblemonResource("memory_aspect") to MemoryAspectTaskConfig.class,
            cobblemonResource("activity_change") to ActivityChangeTaskConfig.class,
        )
    }

    fun checkCondition(MoLangRuntime runtime, expressionOrEntityVariable: ExpressionOrEntityVariable): Boolean {
        return expressionOrEntityVariable.resolveBoolean(runtime)
    }

    fun ExpressionOrEntityVariable.asSimplifiedExpression(LivingEntity entity): Expression {
        return map(
            { it },
            {
                if (entity is MoLangScriptingEntity) {
                    val variable = entity.config.map[it.variableName]
                    if (variable is DoubleValue) {
                        return@map NumberExpression(variable.value)
                    } else if (variable is StringValue) {
                        return@map StringExpression(variable)
                    }
                }
                return@map "q.entity.config.${it.variableName}".asExpression()
            }
        )
    }

    fun ExpressionOrEntityVariable.asExpression() = map({ it }, { "q.entity.config.${it.variableName}".asExpression() })
    fun ExpressionOrEntityVariable.resolveString(MoLangRuntime runtime) = runtime.resolveString(asExpression())
    fun ExpressionOrEntityVariable.resolveBoolean(MoLangRuntime runtime) = runtime.resolveBoolean(asExpression())
    fun ExpressionOrEntityVariable.resolveInt(MoLangRuntime runtime) = runtime.resolveInt(asExpression())
    fun ExpressionOrEntityVariable.resolveDouble(MoLangRuntime runtime) = runtime.resolveDouble(asExpression())
    fun ExpressionOrEntityVariable.resolveFloat(MoLangRuntime runtime) = runtime.resolveFloat(asExpression())

    private fun variable(category: String, String name, type: MoLangConfigVariable.MoLangVariableType, default: String) = MoLangConfigVariable(
        variableName = name,
        category = lang("entity.variable.category.$category"),
        displayName = lang("entity.variable.$name.name"),
        description = lang("entity.variable.$name.desc"),
        type = type,
        defaultValue = default
    )

    fun stringVariable(category: String, String name, default: String) = variable(category = category, name = name, type = MoLangConfigVariable.MoLangVariableType.TEXT, default = default)
    fun numberVariable(category: String, String name, default: Number) = variable(category = category, name = name, type = MoLangConfigVariable.MoLangVariableType.NUMBER, default = default.toString())
    fun booleanVariable(category: String, String name, default: Boolean) = variable(category = category, name = name, type = MoLangConfigVariable.MoLangVariableType.BOOLEAN, default = default.toString())

    fun getVariableExpression(String name) = "q.entity.config.$name".asExpression()
    fun resolveBooleanVariable(String name, MoLangRuntime runtime) = runtime.resolveBoolean(getVariableExpression(name))

    /** The variables that this task config uses. These are used to declare variables on the entity cleanly. */
    fun getVariables(LivingEntity entity, behaviourConfigurationContext: BehaviourConfigurationContext): List<MoLangConfigVariable>
    /** Given the entity in construction, returns a list of tasks. */
    fun createTasks(LivingEntity entity, behaviourConfigurationContext: BehaviourConfigurationContext): List<BehaviorControl<in LivingEntity>>
}