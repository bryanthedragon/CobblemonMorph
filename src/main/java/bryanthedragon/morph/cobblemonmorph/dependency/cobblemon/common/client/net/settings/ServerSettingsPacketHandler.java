/*
 * Copyright (C) 2023 Cobblemon Contributors
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.net.settings

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.net.ClientNetworkPacketHandler
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.settings.ServerSettings
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.settings.ServerSettingsPacket
import net.minecraft.client.Minecraft
public final class ServerSettingsPacketHandler : ClientNetworkPacketHandler<ServerSettingsPacket> {
    override fun handle(packet: ServerSettingsPacket, Minecraft client) {
        ServerSettings.preventCompletePartyDeposit = packet.preventCompletePartyDeposit
        ServerSettings.displayEntityLevelLabel = packet.displayEntityLevelLabel
        ServerSettings.displayEntityNameLabel = packet.displayEntityNameLabel
        ServerSettings.maxPokemonLevel = packet.maxPokemonLevel
        ServerSettings.maxPokemonFriendship = packet.maxPokemonFriendship
        ServerSettings.maxDynamaxLevel = packet.maxDynamaxLevel
    }

}