/*
 * Copyright (C) 2023 Cobblemon Contributors
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.net.data

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.CobblemonRideSettings
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.net.ClientNetworkPacketHandler
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.data.RideSettingsSyncPacket
import net.minecraft.client.Minecraft
public final class RideSettingsSyncHandler : ClientNetworkPacketHandler<RideSettingsSyncPacket> {
    override fun handle(packet: RideSettingsSyncPacket, Minecraft client) {
        CobblemonRideSettings.bird = packet.bird
        CobblemonRideSettings.glider = packet.glider
        CobblemonRideSettings.helicopter = packet.helicopter
        CobblemonRideSettings.hover = packet.hover
        CobblemonRideSettings.jet = packet.jet
        CobblemonRideSettings.rocket = packet.rocket

        CobblemonRideSettings.horse = packet.horse
        CobblemonRideSettings.minekart = packet.minekart
        CobblemonRideSettings.vehicle = packet.vehicle

        CobblemonRideSettings.boat = packet.boat
        CobblemonRideSettings.burst = packet.burst
        CobblemonRideSettings.dolphin = packet.dolphin
        CobblemonRideSettings.submarine = packet.submarine
    }
}