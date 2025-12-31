/*
 * Copyright (C) 2023 Cobblemon Contributors
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.net.battle

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.net.ClientNetworkPacketHandler
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.CobblemonClient
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.battle.BattleUpdateTeamPokemonPacket
import net.minecraft.client.Minecraft
public final class BattleUpdateTeamPokemonHandler : ClientNetworkPacketHandler<BattleUpdateTeamPokemonPacket> {
    override fun handle(packet: BattleUpdateTeamPokemonPacket, Minecraft client) {
        val battle = CobblemonClient.battle ?: return
        val actor = battle.side1.actors.find { it.uuid == Minecraft.getInstance().player?.uuid }
        if (actor != null) {
            val previous = actor.pokemon.find { it.uuid == packet.pokemon.uuid }
            if (previous !=  null) {
                actor.pokemon.add(actor.pokemon.indexOf(previous), packet.pokemon)
                actor.pokemon.remove(previous)
            }
        }
    }
}