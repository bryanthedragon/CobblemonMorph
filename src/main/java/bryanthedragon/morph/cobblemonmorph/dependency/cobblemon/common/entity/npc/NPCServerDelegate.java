/*
 * Copyright (C) 2023 Cobblemon Contributors
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.entity.npc

import com.bedrockk.molang.runtime.struct.QueryStruct
import com.bedrockk.molang.runtime.value.DoubleValue
import com.bedrockk.molang.runtime.value.MoValue
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.Cobblemon
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.CobblemonActivities
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.CobblemonMemories
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.dialogue.ActiveDialogue
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.dialogue.DialogueManager
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.dialogue.Dialogues
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.entity.NPCSideDelegate
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.molang.MoLangFunctions.addFunctions
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.molang.ObjectValue
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.text.text
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.BattleBuilder
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.BattleFormat
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.BattleRegistry
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.asIdentifierDefaultingNamespace
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.asUUID
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.getBooleanOrNull
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.getIntOrNull
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.getMemorySafely
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.getStringOrNull
import net.minecraft.world.entity.LivingEntity
import net.minecraft.server.level.ServerPlayer
import net.minecraft.world.entity.ai.memory.MemoryModuleType
import net.minecraft.world.entity.schedule.Activity

public class NPCServerDelegate : NPCSideDelegate {
    lateinit var entity: NPCEntity

    override fun initialize(entity: NPCEntity) {
        super.initialize(entity)
        this.entity = entity
    }

    override fun addToStruct(struct: QueryStruct) {
        super.addToStruct(struct)
        entity.registerFunctionsForScripting(struct)
        struct
            .addFunction("save_data") { entity.data } // Handled as part of NBT saving
            .addFunction("is_in_dialogue") { entity.brain.hasMemoryValue(CobblemonMemories.DIALOGUES) }
            .addFunction("is_in_battle") { params ->
                val battles = entity.battleIds.mapNotNull(BattleRegistry::getBattle)
                return@addFunction DoubleValue(battles.isNotEmpty())
            }
            .addFunction("is_in_battle_with") { params ->
                val opponentValue = params.get<MoValue>(0)
                val opponent = if (opponentValue is ObjectValue<*>) {
                    opponentValue.obj as ServerPlayer
                } else {
                    val paramString = opponentValue.asString()
                    val playerUUID = paramString.asUUID
                    if (playerUUID != null) {
                        entity.server!!.playerList.getPlayer(playerUUID) ?: return@addFunction DoubleValue.ZERO
                    } else {
                        entity.server!!.playerList.getPlayerByName(paramString) ?: return@addFunction DoubleValue.ZERO
                    }
                }
                val battles = entity.battleIds.mapNotNull(BattleRegistry::getBattle)
                return@addFunction DoubleValue(battles.any { it.getActor(opponent) != null })
            }
            .addFunction("start_battle") { params ->
                val opponentValue = params.get<MoValue>(0)
                val opponent = if (opponentValue is ObjectValue<*>) {
                    opponentValue.obj as ServerPlayer
                } else {
                    val paramString = opponentValue.asString()
                    val playerUUID = paramString.asUUID
                    if (playerUUID != null) {
                        entity.server!!.playerList.getPlayer(playerUUID) ?: return@addFunction DoubleValue.ZERO
                    } else {
                        entity.server!!.playerList.getPlayerByName(paramString) ?: return@addFunction DoubleValue.ZERO
                    }
                }

                val format = (params.getStringOrNull(1)
                    ?.let(BattleFormat::fromFormatIdentifier)
                    ?: BattleFormat.GEN_9_SINGLES)

                val setLevel = params.getIntOrNull(2) ?: -1
                format.adjustLevel = setLevel

                val rules = params.getStringOrNull(5)
                    ?.split(",")
                    ?.toSet()
                    ?: emptySet()

                val modifiedBattleFormat = BattleFormat.setBattleRules(
                    battleFormat = format,
                    rules = rules
                )

                val cloneParties = (setLevel != -1) || (params.getBooleanOrNull(3) ?: false)
                val healFirst = params.getBooleanOrNull(4) ?: false

                val battleStartResult = BattleBuilder.pvn(
                    player = opponent,
                    npcEntity = entity,
                    battleFormat = modifiedBattleFormat,
                    cloneParties = cloneParties,
                    healFirst = healFirst
                )

                var returnValue: MoValue = DoubleValue.ZERO
                battleStartResult.ifSuccessful { returnValue = it.struct }
                return@addFunction returnValue
            }
            .addFunction("run_dialogue") { params ->
                val playerValue = params.get<MoValue>(0)
                val player = if (playerValue is ObjectValue<*>) {
                    playerValue.obj as ServerPlayer
                } else {
                    val paramString = playerValue.asString()
                    val playerUUID = paramString.asUUID
                    if (playerUUID != null) {
                        entity.server!!.playerList.getPlayer(playerUUID) ?: return@addFunction DoubleValue.ZERO
                    } else {
                        entity.server!!.playerList.getPlayerByName(paramString) ?: return@addFunction DoubleValue.ZERO
                    }
                }
                val dialogue = Dialogues.dialogues[params.getString(1).asIdentifierDefaultingNamespace()]!!
                DialogueManager.startDialogue(
                    ActiveDialogue(player, dialogue).also {
                        it.runtime.environment.query.addFunction("npc") { struct }
                    }
                )
            }
            .addFunction("set_chatting") {
                entity.navigation.stop()
                entity.brain.setActiveActivityIfPossible(CobblemonActivities.NPC_CHATTING)
                return@addFunction DoubleValue.ONE
            }
            .addFunction("set_idling") {
                entity.navigation.stop()
                entity.brain.setActiveActivityIfPossible(Activity.IDLE)
                return@addFunction DoubleValue.ONE
            }
            .addFunction("was_hurt_by") { params ->
                val entity = params.get<ObjectValue<LivingEntity>>(0).obj
                val hurtByEntity = this.entity.brain.getMemorySafely(MemoryModuleType.HURT_BY_ENTITY).orElse(null)
                return@addFunction DoubleValue(hurtByEntity == entity)
            }
    }
}