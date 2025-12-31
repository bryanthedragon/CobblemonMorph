/*
 * Copyright (C) 2023 Cobblemon Contributors
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.server.trade

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.net.NetworkPacket
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.cobblemonResource
import net.minecraft.network.RegistryFriendlyByteBuf

public class CancelTradePacket : NetworkPacket<CancelTradePacket> {
    final class Companion {
        val ID = cobblemonResource("cancel_trade")
        fun decode(RegistryFriendlyByteBuf buffer) = CancelTradePacket()
    }

    override val id = ID
    override fun encode(RegistryFriendlyByteBuf buffer) {}
}