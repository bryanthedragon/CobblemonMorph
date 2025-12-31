/*
 * Copyright (C) 2023 Cobblemon Contributors
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.battle

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.net.NetworkPacket
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.cobblemonResource
import net.minecraft.network.RegistryFriendlyByteBuf

/**
 * Packet sent to tell the player they can't do a thing.
 *
 * @author Yaseen
 * April 22nd, 2023
 */
public class BattleMadeInvalidChoicePacket : NetworkPacket<BattleMadeInvalidChoicePacket> {
    final class Companion {
        val ID = cobblemonResource("battle_made_invalid_choice")
        fun decode(RegistryFriendlyByteBuf buffer) = BattleMadeInvalidChoicePacket()
    }

    override val id = ID
    override fun encode(RegistryFriendlyByteBuf buffer) {}
}