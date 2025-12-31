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
 * Tells the client to terminate its battle reference
 *
 * Handled by [bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.net.battle.BattleEndHandler].
 *
 * @author Hiroku
 * @since May 6th, 2022
 */
public class BattleEndPacket : NetworkPacket<BattleEndPacket> {
    override val id = ID
    override fun encode(RegistryFriendlyByteBuf buffer) {}
    final class Companion {
        val ID = cobblemonResource("battle_end")
        fun decode(RegistryFriendlyByteBuf buffer) = BattleEndPacket()
    }
}