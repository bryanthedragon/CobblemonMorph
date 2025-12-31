/*
 * Copyright (C) 2023 Cobblemon Contributors
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.data

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.CobblemonBehaviours
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.ai.CobblemonBehaviour
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.cobblemonResource
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.readIdentifier
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.readText
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.writeIdentifier
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.writeText
import net.minecraft.network.RegistryFriendlyByteBuf
import net.minecraft.resources.ResourceLocation

public class BehaviourSyncPacket(entries: Map<ResourceLocation, CobblemonBehaviour>) : DataRegistrySyncPacket<Map.Entry<ResourceLocation, CobblemonBehaviour>, BehaviourSyncPacket>(entries.entries.toList()) {
    final class Companion {
        val ID = cobblemonResource("behaviour_sync")
        fun decode(RegistryFriendlyByteBuf buffer): BehaviourSyncPacket = BehaviourSyncPacket(emptyMap()).apply { decodeBuffer(buffer) }
    }

    override val id = ID
    override fun decodeEntry(RegistryFriendlyByteBuf buffer): Map.Entry<ResourceLocation, CobblemonBehaviour>? {
        val identifier = buffer.readIdentifier()
        val name = buffer.readText()
        val description = buffer.readText()
        val entityType = buffer.readNullable { buffer.readIdentifier() }
        val behaviour = CobblemonBehaviour(
            name = name,
            description = description,
            configurations = emptyList(),
            entityType = entityType
        )
        return object : Map.Entry<ResourceLocation, CobblemonBehaviour> {
            override val key = identifier
            override val value = behaviour
        }
    }

    override fun encodeEntry(RegistryFriendlyByteBuf buffer, entry: Map.Entry<ResourceLocation, CobblemonBehaviour>) {
        buffer.writeIdentifier(entry.key)
        buffer.writeText(entry.value.name)
        buffer.writeText(entry.value.description)
        buffer.writeNullable(entry.value.entityType) { _, it -> buffer.writeIdentifier(it) }
    }

    override fun synchronizeDecoded(entries: Collection<Map.Entry<ResourceLocation, CobblemonBehaviour>>) {
        CobblemonBehaviours.behaviours.clear()
        CobblemonBehaviours.behaviours.putAll(entries.associate { it.key to it.value })
    }
}