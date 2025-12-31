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

public class ToggleCookingPotLidPacket(val value: Boolean) : NetworkPacket<ToggleCookingPotLidPacket> {
    final class Companion {
        val ID = cobblemonResource("toggle_cooking_pot_lid")
        fun decode(RegistryFriendlyByteBuf buffer): ToggleCookingPotLidPacket {
            return ToggleCookingPotLidPacket(buffer.readBoolean())
        }
    }

    override val id = ID
    override fun encode(RegistryFriendlyByteBuf buffer) {
        buffer.writeBoolean(this.value)
    }
}