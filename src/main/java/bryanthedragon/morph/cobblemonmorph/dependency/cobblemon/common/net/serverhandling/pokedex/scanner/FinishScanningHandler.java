/*
 * Copyright (C) 2023 Cobblemon Contributors
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.serverhandling.pokedex.scanner

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.Cobblemon
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.events.CobblemonEvents.POKEMON_SCANNED
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.events.pokedex.scanning.PokemonScannedEvent
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.net.ServerNetworkPacketHandler
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.entity.pokemon.PokemonEntity
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.pokedex.ServerConfirmedRegisterPacket
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.server.pokedex.scanner.FinishScanningPacket
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokedex.scanner.PlayerScanningDetails
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokedex.scanner.PokedexUsageContext
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokedex.scanner.PokemonScanner
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokedex.scanner.ScannableEntity
import net.minecraft.server.MinecraftServer
import net.minecraft.server.level.ServerPlayer

/**
 * Calls [POKEMON_SCANNED] event when Pokémon is finished scanning
 *
 * @author Apion
 * @since August 10, 2024
 */final class FinishScanningHandler : ServerNetworkPacketHandler<FinishScanningPacket> {
    override fun handle(
        packet: FinishScanningPacket,
        server: MinecraftServer,
        player: ServerPlayer
    ) {
        val targetEntity = player.level().getEntity(packet.targetedId) ?: return
        if (PokemonScanner.isEntityInRange(player, targetEntity, packet.zoomLevel)) {
            val inProgressUUID = PlayerScanningDetails.playerToEntityMap[player.uuid]
            val progressTick = PlayerScanningDetails.playerToTickMap[player.uuid]
            val ticksScan = progressTick?.let { server.tickCount - it } ?: return
            if (targetEntity.uuid == inProgressUUID && ticksScan >= PokedexUsageContext.SUCCESS_SCAN_SERVER_TICKS) {
                val scannableEntity = targetEntity as? ScannableEntity ?: return
                val dex = Cobblemon.playerDataManager.getPokedexData(player)
                val pokedexEntityData = scannableEntity.resolvePokemonScan()
                if (pokedexEntityData != null){
                    val newInformation = dex.getNewInformation(pokedexEntityData)
                    if ((scannableEntity as? PokemonEntity)?.owner === player) {
                        dex.catch(scannableEntity.pokemon)
                    } else {
                        dex.encounter(pokedexEntityData)
                    }

                    POKEMON_SCANNED.post(PokemonScannedEvent(player, pokedexEntityData, scannableEntity))
                    ServerConfirmedRegisterPacket((pokedexEntityData.getApparentSpecies()).resourceIdentifier, newInformation).sendToPlayer(player)
                }
            }
        }
    }
}