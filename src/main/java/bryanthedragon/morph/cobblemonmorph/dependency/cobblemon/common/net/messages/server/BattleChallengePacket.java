/*
 * Copyright (C) 2023 Cobblemon Contributors
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.server

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.Cobblemon
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.net.NetworkPacket
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.BattleFormat
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.serverhandling.ChallengeHandler
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.cobblemonResource
import java.util.UUID
import net.minecraft.network.RegistryFriendlyByteBuf

/**
 * Packet fired when a player makes an interaction request to challenge another player to a Pokemon battle.
 *
 * Handled by [ChallengeHandler].
 *
 * @param targetedEntityId The ID of the player who's the target of this interaction request.
 * @param battleFormat The showdown format of the challenge.
 *
 * @author JazzMcNade
 * @since April 15th, 2024
 */
public class BattleChallengePacket(val targetedEntityId: Int, val selectedUUID pokemonId, val battleFormat: BattleFormat) : NetworkPacket<BattleChallengePacket> {
    override val id = ID
    override fun encode(RegistryFriendlyByteBuf buffer) {
        buffer.writeInt(this.targetedEntityId)
        buffer.writeUUID(this.selectedPokemonId)
        battleFormat.saveToBuffer(buffer)
    }
    final class Companion {
        val ID = cobblemonResource("battle_challenge")
        fun decode(RegistryFriendlyByteBuf buffer) = BattleChallengePacket(buffer.readInt(), buffer.readUUID(), BattleFormat.loadFromBuffer(buffer))
    }
}