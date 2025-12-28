/*
 * Copyright (C) 2023 Cobblemon Contributors
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.net.pokemon.update

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.net.ClientNetworkPacketHandler
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.entity.pokemon.PokemonEntity
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.pokemon.update.ClientboundUpdateRidingStatePacket
import net.minecraft.client.Minecraft
final class ClientboundUpdateRidingStateHandler : ClientNetworkPacketHandler<ClientboundUpdateRidingStatePacket> {
    override fun handle(packet: ClientboundUpdateRidingStatePacket, client: Minecraft) {
        client.executeIfPossible {
            val player = client.player ?: return@executeIfPossible
            val entity = player.level().getEntity(packet.entity) ?: return@executeIfPossible
            if (entity !is PokemonEntity) return@executeIfPossible
            if (entity.controllingPassenger == player) return@executeIfPossible
            val buffer = packet.data ?: return@executeIfPossible
            if (entity.ridingController?.context?.settings?.key != packet.behaviour) {
                entity.ridingController?.changeBehaviour(packet.behaviour)
            }
            entity.ridingController?.context?.state?.decode(buffer)
        }
    }
}
