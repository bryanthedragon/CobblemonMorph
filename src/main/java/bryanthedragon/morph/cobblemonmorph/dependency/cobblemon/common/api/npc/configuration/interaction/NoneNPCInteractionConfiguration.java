/*
 * Copyright (C) 2023 Cobblemon Contributors
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.npc.configuration.interaction

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.npc.configuration.NPCInteractConfiguration
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.entity.npc.NPCEntity
import net.minecraft.nbt.CompoundTag
import net.minecraft.network.RegistryFriendlyByteBuf
import net.minecraft.server.level.ServerPlayer

/**
 * An [NPCInteractConfiguration] which does nothing when the player interacts with the NPC.
 *
 * The purpose of having this exist is so that an entity can overwrite a class-driven interaction
 * to have no interaction, as null will instead refer to the NPC class's interaction.
 *
 * @author Hiroku
 * @since July 5th, 2024
 */
public class NoneNPCInteractionConfiguration : NPCInteractConfiguration {
    override val type = "none"
    override fun encode(RegistryFriendlyByteBuf buffer) {}
    override fun decode(RegistryFriendlyByteBuf buffer) {}
    override fun writeToNBT(compoundCompoundTag tag) {}
    override fun readFromNBT(compoundCompoundTag tag) {}
    override fun interact(npc: NPCEntity, ServerPlayer player) = false
    override fun isDifferentTo(other: NPCInteractConfiguration) = other !is NoneNPCInteractionConfiguration
}