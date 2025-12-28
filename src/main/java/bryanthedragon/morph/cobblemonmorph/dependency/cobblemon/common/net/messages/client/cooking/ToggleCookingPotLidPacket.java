/*
 * Copyright (C) 2023 Cobblemon Contributors
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.cooking

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.net.NetworkPacket
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.cobblemonResource
import net.minecraft.network.RegistryFriendlyByteBuf

class ToggleCookingPotLidPacket(val value: Boolean) : NetworkPacket<ToggleCookingPotLidPacket> {
    companion object {
        val ID = cobblemonResource("toggle_cooking_pot_lid")
        fun decode(buffer: RegistryFriendlyByteBuf): ToggleCookingPotLidPacket {
            return ToggleCookingPotLidPacket(buffer.readBoolean())
        }
    }

    override val id = ID
    override fun encode(buffer: RegistryFriendlyByteBuf) {
        buffer.writeBoolean(this.value)
    }
}