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
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.battle.ClientBattlePokemon
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.battle.BattleTransformPokemonPacket
import net.minecraft.client.Minecraft

/**
 * The handler for [BattleTransformPokemonPacket]s. Updates the [ClientBattlePokemon] after a transformation.
 *
 * @author Segfault Guy
 * @since April 22nd, 2023
 */
public final class BattleTransformPokemonHandler : ClientNetworkPacketHandler<BattleTransformPokemonPacket> {
    override fun handle(packet: BattleTransformPokemonPacket, Minecraft client) {
        val battle = CobblemonClient.battle ?: return
        val (_, activeBattlePokemon) = battle.getPokemonFromPNX(packet.pnx)
        val update = packet.updatedPokemon

        activeBattlePokemon.battlePokemon?.apply {
            displayName = update.displayName
            properties = update.properties
            updateAspects(update.aspects)
            hpValue = update.hpValue
            maxHp = update.maxHp
            isHpFlat = update.isFlatHp
            status = update.status
            statChanges = update.statChanges
        }
    }
}