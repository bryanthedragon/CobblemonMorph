/*
 * Copyright (C) 2023 Cobblemon Contributors
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.net.serializers

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.entity.PlatformType
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.cobblemonResource
import net.minecraft.network.RegistryFriendlyByteBuf
import net.minecraft.network.codec.StreamCodec
import net.minecraft.network.syncher.EntityDataSerializer
public final class PlatformTypeDataSerializer : EntityDataSerializer<PlatformType> {
    // TODO: This ID only looks it's used by NeoForge and that hasn't been tested yet
    val ID = cobblemonResource("platform_type")
    fun read(RegistryFriendlyByteBuf buf) = PlatformType.entries[buf.readInt()]
    override fun copy(value: PlatformType) = value
    fun write(RegistryFriendlyByteBuf buf, value: PlatformType) {
        buf.writeInt(value.ordinal)
    }

    override fun codec() = StreamCodec.of(::write, ::read)
}