/*
 * Copyright (C) 2023 Cobblemon Contributors
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.serverhandling.pokemon.update

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.CobblemonNetwork.sendPacket
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.events.CobblemonEvents
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.events.pokemon.PokemonNicknamedEvent
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.net.ServerNetworkPacketHandler
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.storage.PokemonStore
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.storage.pc.link.PCLinkManager
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.pokemon.update.NicknameUpdatePacket
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.storage.pc.ClosePCPacket
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.server.pokemon.update.SetNicknamePacket
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.party
import net.minecraft.server.MinecraftServer
import net.minecraft.server.level.ServerPlayer
import net.minecraft.network.chat.Component
final class SetNicknameHandler : ServerNetworkPacketHandler<SetNicknamePacket> {

    const val MAX_NAME_LENGTH = 12

    override fun handle(packet: SetNicknamePacket, server: MinecraftServer, player: ServerPlayer) {
        val pokemonStore: PokemonStore<*> = if (packet.isParty) {
            player.party()
        } else {
            PCLinkManager.getPC(player) ?: return ClosePCPacket(null).sendToPlayer(player)
        }

        val pokemon = pokemonStore[packet.pokemonUUID] ?: return

        val nickname = packet.nickname
        if (nickname != null && nickname.length > MAX_NAME_LENGTH) {
            return player.sendPacket(NicknameUpdatePacket({ pokemon }, pokemon.nickname))
        }

        CobblemonEvents.POKEMON_NICKNAMED.postThen(
            event = PokemonNicknamedEvent(
                player = player,
                pokemon = pokemon,
                nickname = packet.nickname?.let { Component.literal(it) }
            ),
            ifSucceeded = {
                pokemon.nickname = it.nickname
            },
            ifCanceled = {
                player.sendPacket(NicknameUpdatePacket({ pokemon }, pokemon.nickname))
            }
        )
    }
}