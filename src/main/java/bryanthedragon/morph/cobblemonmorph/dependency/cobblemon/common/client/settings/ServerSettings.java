/*
 * Copyright (C) 2023 Cobblemon Contributors
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.settings

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.Cobblemon
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.net.settings.ServerSettingsPacketHandler
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.settings.ServerSettingsPacket

/**
 * A holder for config options the server wants to sync with the client.
 * See [ServerSettingsPacket] & [ServerSettingsPacketHandler] for more information.
 *
 * @author Licious
 * @since September 27th, 2022
 */
public final class ServerSettings {

    var preventCompletePartyDeposit = Cobblemon.config.preventCompletePartyDeposit
    var displayEntityLevelLabel = Cobblemon.config.displayEntityLevelLabel
    var displayEntityNameLabel = Cobblemon.config.displayEntityNameLabel
    var maxPokemonLevel = Cobblemon.config.maxPokemonLevel
    var maxPokemonFriendship = Cobblemon.config.maxPokemonFriendship
    var maxDynamaxLevel = Cobblemon.config.maxDynamaxLevel

}