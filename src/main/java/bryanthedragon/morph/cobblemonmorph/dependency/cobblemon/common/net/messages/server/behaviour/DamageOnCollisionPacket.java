/*
 * Copyright (C) 2023 Cobblemon Contributors
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.server.behaviour

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.net.NetworkPacket
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.cobblemonResource
import net.minecraft.network.RegistryFriendlyByteBuf
import net.minecraft.world.phys.Vec3

class DamageOnCollisionPacket(
    val impactVec: Vec3
) : NetworkPacket<DamageOnCollisionPacket> {
    companion object {
        val ID = cobblemonResource("c2s_on_collision_damage")
        fun decode(buffer: RegistryFriendlyByteBuf): DamageOnCollisionPacket = DamageOnCollisionPacket(
            impactVec = buffer.readVec3()
        )
    }

    override val id = ID
    override fun encode(buffer: RegistryFriendlyByteBuf) {
        buffer.writeVec3(impactVec)
    }
}