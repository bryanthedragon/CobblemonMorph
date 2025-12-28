/*
 * Copyright (C) 2023 Cobblemon Contributors
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.net.serializers

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.riding.stats.RidingStat
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.cobblemonResource
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.readString
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.writeString
import net.minecraft.network.RegistryFriendlyByteBuf
import net.minecraft.network.codec.StreamCodec
import net.minecraft.network.syncher.EntityDataSerializer
final class RideBoostsDataSerializer : EntityDataSerializer<Map<RidingStat, Float>> {
    val ID = cobblemonResource("ride_boosts")
    fun read(buf: RegistryFriendlyByteBuf): Map<RidingStat, Float> {
        return buf.readMap(
            { RidingStat.valueOf(it.readString()) },
            { it.readFloat() }
        )
    }
    override fun copy(value: Map<RidingStat, Float>) = value.toMap()
    fun write(buf: RegistryFriendlyByteBuf, value: Map<RidingStat, Float>) {
        buf.writeMap(
            value,
            { _, it -> buf.writeString(it.name) },
            { _, it -> buf.writeFloat(it) }
        )
    }

    override fun codec() = StreamCodec.of(::write, ::read)
}