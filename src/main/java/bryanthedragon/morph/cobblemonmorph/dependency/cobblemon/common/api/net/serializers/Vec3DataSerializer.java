/*
 * Copyright (C) 2023 Cobblemon Contributors
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.net.serializers

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.cobblemonResource
import net.minecraft.network.RegistryFriendlyByteBuf
import net.minecraft.network.codec.StreamCodec
import net.minecraft.network.syncher.EntityDataSerializer
import net.minecraft.world.phys.Vec3
public final class Vec3DataSerializer : EntityDataSerializer<Vec3> {
    val ID = cobblemonResource("vec3d")
    fun write(RegistryFriendlyByteBuf buffer, Vec3 vec) {
        buffer.writeDouble(vec.x)
        buffer.writeDouble(vec.y)
        buffer.writeDouble(vec.z)
    }

    fun read(RegistryFriendlyByteBuf buffer) = Vec3(
        buffer.readDouble(),
        buffer.readDouble(),
        buffer.readDouble()
    )

    override fun copy(Vec3 vec) = Vec3(vec.x, vec.y, vec.z)
    override fun codec() = StreamCodec.of(::write, ::read)

}