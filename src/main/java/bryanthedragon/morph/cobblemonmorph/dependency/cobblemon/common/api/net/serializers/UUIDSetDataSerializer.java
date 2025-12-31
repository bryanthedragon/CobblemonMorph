/*
 * Copyright (C) 2023 Cobblemon Contributors
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.net.serializers

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.IntSize
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.*
import net.minecraft.network.RegistryFriendlyByteBuf
import net.minecraft.network.codec.StreamCodec
import net.minecraft.network.syncher.EntityDataSerializer
import java.util.*
public final class UUIDSetDataSerializer : EntityDataSerializer<Set<UUID>> {
    val ID = cobblemonResource("uuidset")
    fun write(RegistryFriendlyByteBuf buffer, set: Set<UUID>) {
        buffer.writeSizedInt(IntSize.U_BYTE, set.size)
        set.forEach(buffer::writeUUID)
    }

    fun read(RegistryFriendlyByteBuf buffer): Set<UUID> {
        val set = mutableSetOf<UUID>()
        repeat(times = buffer.readSizedInt(IntSize.U_BYTE)) {
            set.add(buffer.readUUID())
        }
        return set
    }

    override fun copy(set: Set<UUID>) = set.toSet()
    override fun codec(): StreamCodec<RegistryFriendlyByteBuf, Set<UUID>> = StreamCodec.of(::write, ::read)
}