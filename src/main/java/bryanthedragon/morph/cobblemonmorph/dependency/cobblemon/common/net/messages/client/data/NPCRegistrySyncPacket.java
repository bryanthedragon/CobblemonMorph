/*
 * Copyright (C) 2023 Cobblemon Contributors
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.data

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.Cobblemon
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.npc.NPCClass
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.npc.NPCClasses
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.cobblemonResource
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.readIdentifier
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.writeIdentifier
import net.minecraft.network.RegistryFriendlyByteBuf

// We do not need to know every single attribute as a client, as such, we only sync the aspects that matter
public class NPCRegistrySyncPacket(npcs: Collection<NPCClass>) : DataRegistrySyncPacket<NPCClass, NPCRegistrySyncPacket>(npcs) {

    override val id = ID

    override fun encodeEntry(RegistryFriendlyByteBuf buffer, entry: NPCClass) {
        try {
            buffer.writeIdentifier(entry.id)
            entry.encode(buffer)
        } catch (Exception e) {
            Cobblemon.LOGGER.error("Caught exception encoding the NPC class {}", entry.id, e)
        }
    }

    override fun decodeEntry(RegistryFriendlyByteBuf buffer): NPCClass? {
        val identifier = buffer.readIdentifier()
        val npc = NPCClass()
        npc.id = identifier
        return try {
            npc.decode(buffer)
            npc
        } catch (Exception e) {
            Cobblemon.LOGGER.error("Caught exception decoding the NPC class {}", identifier, e)
            null
        }
    }

    override fun synchronizeDecoded(entries: Collection<NPCClass>) {
        NPCClasses.reload(entries.associateBy { it.id })
    }

    final class Companion {
        val ID = cobblemonResource("npcs_sync")
        fun decode(RegistryFriendlyByteBuf buffer): NPCRegistrySyncPacket = NPCRegistrySyncPacket(emptyList()).apply { decodeBuffer(buffer) }
    }
}