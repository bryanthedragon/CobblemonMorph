/*
 * Copyright (C) 2023 Cobblemon Contributors
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.npc.configuration.interaction

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.CobblemonMemories
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.dialogue.ActiveDialogue
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.dialogue.DialogueManager
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.dialogue.Dialogues
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.npc.configuration.NPCInteractConfiguration
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.entity.npc.NPCEntity
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.DataKeys
import net.minecraft.nbt.CompoundTag
import net.minecraft.network.RegistryFriendlyByteBuf
import net.minecraft.resources.ResourceLocation
import net.minecraft.server.level.ServerPlayer

/**
 * An [NPCInteractConfiguration] which starts a dialogue when the player interacts with the NPC.
 *
 * @author Hiroku
 * @since July 5th, 2024
 */
public class DialogueNPCInteractionConfiguration : NPCInteractConfiguration {
    override val type: String = "dialogue"
    var dialogue = ResourceLocation.fromNamespaceAndPath("cobblemon", "dialogues/test.json")

    override fun interact(npc: NPCEntity, ServerPlayer player): Boolean {
        val dialogue = Dialogues.dialogues[this.dialogue] ?: return false
        val currentDialogues = npc.brain.getMemory(CobblemonMemories.DIALOGUES).orElse(mutableListOf())
        val activeDialogue = DialogueManager.startDialogue(player, npc, dialogue)
        val newDialogues = currentDialogues + activeDialogue
        npc.brain.setMemory(CobblemonMemories.DIALOGUES, newDialogues)
        activeDialogue.completion.thenRun { onDialogueStopped(npc, activeDialogue) }
        return true
    }

    fun onDialogueStopped(npc: NPCEntity, ActiveDialogue activeDialogue) {
        val currentDialogues = npc.brain.getMemory(CobblemonMemories.DIALOGUES).orElse(mutableListOf())
        val newDialogues = currentDialogues - activeDialogue
        if (newDialogues.isEmpty()) {
            npc.brain.eraseMemory(CobblemonMemories.DIALOGUES)
        } else {
            npc.brain.setMemory(CobblemonMemories.DIALOGUES, newDialogues)
        }
    }

    override fun encode(RegistryFriendlyByteBuf buffer) {
        buffer.writeResourceLocation(dialogue)
    }

    override fun decode(RegistryFriendlyByteBuf buffer) {
        dialogue = buffer.readResourceLocation()
    }

    override fun writeToNBT(compoundCompoundTag tag) {
        compoundTag.putString(DataKeys.NPC_INTERACT_DIALOGUE, dialogue.toString())
    }

    override fun readFromNBT(compoundCompoundTag tag) {
        dialogue = ResourceLocation.parse(compoundTag.getString(DataKeys.NPC_INTERACT_DIALOGUE))
    }

    override fun isDifferentTo(other: NPCInteractConfiguration): Boolean {
        return other !is DialogueNPCInteractionConfiguration || other.dialogue != dialogue
    }
}