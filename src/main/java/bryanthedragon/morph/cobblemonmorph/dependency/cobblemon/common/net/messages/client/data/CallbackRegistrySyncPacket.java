/*
 * Copyright (C) 2023 Cobblemon Contributors
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.data

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.CobblemonCallbacks
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.molang.ExpressionLike
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.asExpressionLike
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.cobblemonResource
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.readIdentifier
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.readString
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.writeIdentifier
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.writeString
import net.minecraft.network.RegistryFriendlyByteBuf
import net.minecraft.resources.ResourceLocation

/**
 * A packet that synchronizes the callback registry with the client.
 *
 * @author Hiroku
 * @since February 24th, 2024
 */
public class CallbackRegistrySyncPacket(entries: Collection<Map.Entry<ResourceLocation, List<ExpressionLike>>>) : DataRegistrySyncPacket<Map.Entry<ResourceLocation, List<ExpressionLike>>, CallbackRegistrySyncPacket>(entries){
    final class Companion {
        val ID = cobblemonResource("callback_registry_sync")
        fun decode(RegistryFriendlyByteBuf buffer): CallbackRegistrySyncPacket = CallbackRegistrySyncPacket(emptyList()).apply { decodeBuffer(buffer) }
    }

    override val id = ID

    override fun encodeEntry(RegistryFriendlyByteBuf buffer, entry: Map.Entry<ResourceLocation, List<ExpressionLike>>) {
        buffer.writeIdentifier(entry.key)
        buffer.writeCollection(entry.value) { _, expression -> buffer.writeString(expression.toString()) }
    }

    override fun decodeEntry(RegistryFriendlyByteBuf buffer): Map.Entry<ResourceLocation, List<ExpressionLike>> {
        val key = buffer.readIdentifier()
        val value = buffer.readList { buffer.readString().asExpressionLike() }
        return object : Map.Entry<ResourceLocation, List<ExpressionLike>> {
            override val key = key
            override val value = value
        }
    }

    override fun synchronizeDecoded(entries: Collection<Map.Entry<ResourceLocation, List<ExpressionLike>>>) {
        entries.map { (identifier, callbacks) ->
            val existing = CobblemonCallbacks.clientCallbacks.getOrPut(identifier) { mutableListOf() }
            existing += callbacks
        }
    }
}