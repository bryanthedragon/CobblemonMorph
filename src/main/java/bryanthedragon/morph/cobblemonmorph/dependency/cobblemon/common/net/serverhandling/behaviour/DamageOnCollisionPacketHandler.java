/*
 * Copyright (C) 2023 Cobblemon Contributors
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.serverhandling.behaviour

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.net.ServerNetworkPacketHandler
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.entity.pokemon.PokemonEntity
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.server.behaviour.DamageOnCollisionPacket
import net.minecraft.server.MinecraftServer
import net.minecraft.server.level.ServerPlayer
public final class DamageOnCollisionPacketHandler : ServerNetworkPacketHandler<DamageOnCollisionPacket> {
    override fun handle(packet: DamageOnCollisionPacket, server: MinecraftServer, ServerPlayer player) {
        val pokemon = player.vehicle
        if (pokemon != null && pokemon is PokemonEntity) {
            pokemon.ifRidingAvailable { behaviour, settings, state ->
                behaviour.damageOnCollision(settings, state, pokemon, packet.impactVec)
            }
        }
    }
}