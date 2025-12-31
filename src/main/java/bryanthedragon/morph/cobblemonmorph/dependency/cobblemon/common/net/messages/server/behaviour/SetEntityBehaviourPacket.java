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
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.readIdentifier
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.writeIdentifier
import net.minecraft.network.RegistryFriendlyByteBuf
import net.minecraft.resources.ResourceLocation

public class SetEntityBehaviourPacket(
    val entityId: Int,
    val behaviours: Set<ResourceLocation>
): NetworkPacket<SetEntityBehaviourPacket> {
    final class Companion {
        val ID = cobblemonResource("set_entity_behaviour")
        fun decode(RegistryFriendlyByteBuf buffer): SetEntityBehaviourPacket = SetEntityBehaviourPacket(
            entityId = buffer.readInt(),
            behaviours = buffer.readList { buffer.readIdentifier() }.toSet()
        )
    }

    override val id = ID
    override fun encode(RegistryFriendlyByteBuf buffer) {
        buffer.writeInt(entityId)
        buffer.writeCollection(behaviours) { _, it -> buffer.writeIdentifier(it) }
    }
}