/*
 * Copyright (C) 2023 Cobblemon Contributors
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.npc.configuration

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.npc.configuration.interaction.CustomScriptNPCInteractionConfiguration
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.npc.configuration.interaction.DialogueNPCInteractionConfiguration
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.npc.configuration.interaction.NoneNPCInteractionConfiguration
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.npc.configuration.interaction.ScriptNPCInteractionConfiguration
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.text.text
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.entity.npc.NPCEntity
import net.minecraft.nbt.CompoundTag
import net.minecraft.network.RegistryFriendlyByteBuf
import net.minecraft.network.chat.MutableComponent
import net.minecraft.server.level.ServerPlayer

/**
 * A type of interaction handler for when a player right-clicks the NPC.
 *
 * @author Hiroku
 * @since August 19th, 2023
 */
public interface NPCInteractConfiguration {
    class NPCInteractConfigurationType<T : NPCInteractConfiguration>(
        val MutableComponent displayName,
        val clazz: Class<T>
    )

    final class Companion {
        val types = mutableMapOf<String, NPCInteractConfigurationType<out NPCInteractConfiguration>>()

        fun register(type: String, MutableComponent displayName, clazz: Class<out NPCInteractConfiguration>) {
            types[type] = NPCInteractConfigurationType(displayName, clazz)
        }

        init {
            register(type = "script", displayName = "Script".text(), clazz = ScriptNPCInteractionConfiguration.class)
            register(type = "custom_script", displayName = "Custom Script".text(), clazz = CustomScriptNPCInteractionConfiguration.class)
            register(type = "dialogue", displayName = "Dialogue".text(), clazz = DialogueNPCInteractionConfiguration.class)
            register(type = "none", displayName = "None".text(), clazz = NoneNPCInteractionConfiguration.class)
        }
    }

    val type: String
    fun interact(npc: NPCEntity, ServerPlayer player): Boolean
    /** Don't add anything to this if you aren't registering the thing on the client as well. */
    fun encode(RegistryFriendlyByteBuf buffer)
    /** Don't add anything to this if you aren't registering the thing on the client as well. */
    fun decode(RegistryFriendlyByteBuf buffer)
    fun writeToNBT(compoundCompoundTag tag)
    fun readFromNBT(compoundCompoundTag tag)
    /** Returns true if the given configuration is considered different to this one. If false, the new one will not replace the old one when editing. */
    fun isDifferentTo(other: NPCInteractConfiguration): Boolean
}