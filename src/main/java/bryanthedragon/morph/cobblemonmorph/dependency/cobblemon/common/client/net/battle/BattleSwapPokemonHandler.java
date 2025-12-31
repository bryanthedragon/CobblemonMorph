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
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.battle.ActiveClientBattlePokemon
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.battle.ClientBattlePokemon
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.battle.animations.MoveTileOffscreenAnimation
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.battle.animations.MoveTileOnscreenAnimation
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.battle.BattleSwapPokemonPacket
import net.minecraft.client.Minecraft
public final class BattleSwapPokemonHandler : ClientNetworkPacketHandler<BattleSwapPokemonPacket> {
    override fun handle(packet: BattleSwapPokemonPacket, Minecraft client) {
        val battle = CobblemonClient.battle ?: return
        val (actor, activeBattlePokemon) = battle.getPokemonFromPNX(packet.pnx)

        val swapPokemon = activeBattlePokemon.getAdjacentAllies().first() as ActiveClientBattlePokemon
        val swapBattlePokemon = swapPokemon.battlePokemon

        if (swapBattlePokemon != null && swapBattlePokemon.hpValue > 0) {
            activeBattlePokemon.animations.add(
                    MoveTileOnscreenAnimation(
                    swapPokemon.battlePokemon as ClientBattlePokemon
                )
            )
        } else {
            activeBattlePokemon.animations.add(MoveTileOffscreenAnimation(swappedPokemon = swapPokemon.battlePokemon))
        }

        swapPokemon.animations.add(
                MoveTileOnscreenAnimation(
                    activeBattlePokemon.battlePokemon as ClientBattlePokemon
            )
        )
    }
}