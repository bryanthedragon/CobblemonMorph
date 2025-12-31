/*
 * Copyright (C) 2023 Cobblemon Contributors
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.pokemon.update

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.net.NetworkPacket
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.riding.behaviour.RidingBehaviourState
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.cobblemonResource
import net.minecraft.network.FriendlyByteBuf
import net.minecraft.network.RegistryFriendlyByteBuf
import net.minecraft.resources.ResourceLocation

public class ClientboundUpdateRidingStatePacket(
    val entity: Int,
    val behaviour: ResourceLocation,
    val state: RidingBehaviourState? = null,
    val data: FriendlyByteBuf? = null
) : NetworkPacket<ClientboundUpdateRidingStatePacket> {
    override val id = ID

    override fun encode(RegistryFriendlyByteBuf buffer) {
        if (state == null) error("Expected state to be populated for encoding")
        buffer.writeInt(entity)
        buffer.writeResourceLocation(behaviour)
        state.encode(buffer)
    }

    final class Companion {
        val ID = cobblemonResource("s2c_update_ride_controller")
        fun decode(RegistryFriendlyByteBuf buffer): ClientboundUpdateRidingStatePacket {
            val entity = buffer.readInt()
            val behaviour = buffer.readResourceLocation()
            val state = FriendlyByteBuf(buffer.readBytes(buffer.readableBytes()))
            return ClientboundUpdateRidingStatePacket(
                entity = entity,
                behaviour = behaviour,
                data = state
            )
        }
    }
}
