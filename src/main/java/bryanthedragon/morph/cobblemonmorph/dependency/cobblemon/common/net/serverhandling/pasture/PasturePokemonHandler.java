/*
 * Copyright (C) 2023 Cobblemon Contributors
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.serverhandling.pasture

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.Cobblemon
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.CobblemonNetwork.sendPacket
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.net.ServerNetworkPacketHandler
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pasture.PastureLinkManager
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.block.entity.PokemonPastureBlockEntity
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.pasture.ClosePasturePacket
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.server.pasture.PasturePokemonPacket
import net.minecraft.world.level.block.HorizontalDirectionalBlock
import net.minecraft.server.MinecraftServer
import net.minecraft.server.level.ServerPlayer
public final class PasturePokemonHandler : ServerNetworkPacketHandler<PasturePokemonPacket> {
    override fun handle(packet: PasturePokemonPacket, server: MinecraftServer, ServerPlayer player) {
        val pastureLink = PastureLinkManager.getLinkByPlayer(player) ?: return
        if (pastureLink.linkId != packet.pastureId) {
            return player.sendPacket(ClosePasturePacket())
        }
        val pc = Cobblemon.storage.getPC(pastureLink.pcId, player.registryAccess())
        val pokemon = pc[packet.pokemonId] ?: return

        val pastureBlockEntity = player.level().getBlockEntity(pastureLink.pos) as? PokemonPastureBlockEntity ?: return
        val state = player.level().getBlockState(pastureLink.pos)
        val direction = state.getValue(HorizontalDirectionalBlock.FACING)

        if (pokemon.tetheringId != null) {
            return
        }

        val maxPerPlayer = pastureLink.permissions.maxPokemon
        if (pastureBlockEntity.canAddPokemon(player, pokemon, maxPerPlayer)) {
            pastureBlockEntity.tether(player, pokemon, direction)
        }
    }
}