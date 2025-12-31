/*
 * Copyright (C) 2023 Cobblemon Contributors
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.data

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.fishing.SpawnBait
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.fishing.SpawnBaitEffects
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.cobblemonResource
import net.minecraft.network.RegistryFriendlyByteBuf
import net.minecraft.resources.ResourceLocation

public class SpawnBaitRegistrySyncPacket(spawnBaits: Map<ResourceLocation, SpawnBait>) : DataRegistrySyncPacket<Map.Entry<ResourceLocation, SpawnBait>, SpawnBaitRegistrySyncPacket>(spawnBaits.entries) {
    final class Companion {
        val ID = cobblemonResource("spawn_baits")
        fun decode(RegistryFriendlyByteBuf buffer) = SpawnBaitRegistrySyncPacket(emptyMap()).apply { decodeBuffer(buffer) }
    }

    override val id = ID
    override fun encodeEntry(RegistryFriendlyByteBuf buffer, entry: Map.Entry<ResourceLocation, SpawnBait>) {
        buffer.writeResourceLocation(entry.key)
        SpawnBait.STREAM_CODEC.encode(buffer, entry.value)
    }

    override fun decodeEntry(RegistryFriendlyByteBuf buffer): Map.Entry<ResourceLocation, SpawnBait> {
        val resourceLocation = buffer.readResourceLocation()
        val bait = SpawnBait.STREAM_CODEC.decode(buffer)
        return object : Map.Entry<ResourceLocation, SpawnBait> {
            override val key: ResourceLocation = resourceLocation
            override val value: SpawnBait = bait
        }
    }

    override fun synchronizeDecoded(entries: Collection<Map.Entry<ResourceLocation, SpawnBait>>) {
        SpawnBaitEffects.reload(entries.associateByTo(mutableMapOf(), { it.key }, { it.value }))
    }
}