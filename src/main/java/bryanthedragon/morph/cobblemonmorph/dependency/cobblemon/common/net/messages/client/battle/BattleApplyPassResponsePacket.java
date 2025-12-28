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
 * Tells a specific player that they should choose a battle capture response for the next Pokémon request in their queue.
 *
 * Handled by [bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.net.battle.BattleApplyPassResponseHandler].
 *
 * @author Hiroku
 * @since July 3rd, 2022
 */
class BattleApplyPassResponsePacket : NetworkPacket<BattleApplyPassResponsePacket> {
    override val id = ID
    override fun encode(buffer: RegistryFriendlyByteBuf) {}
    companion object {
        val ID = cobblemonResource("battle_apply_pass_response")
        fun decode(buffer: RegistryFriendlyByteBuf) = BattleApplyPassResponsePacket()
    }
}