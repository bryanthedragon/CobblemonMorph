/*
 * Copyright (C) 2023 Cobblemon Contributors
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.net.data

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.CobblemonMechanics
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.net.ClientNetworkPacketHandler
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.data.CobblemonMechanicsSyncPacket
import net.minecraft.client.Minecraft
final class CobblemonMechanicsSyncHandler : ClientNetworkPacketHandler<CobblemonMechanicsSyncPacket> {

    override fun handle(packet: CobblemonMechanicsSyncPacket, client: Minecraft) {
        CobblemonMechanics.remedies = packet.remedies
        CobblemonMechanics.berries = packet.berries
        CobblemonMechanics.potions = packet.potions
        CobblemonMechanics.aprijuices = packet.aprijuices
        CobblemonMechanics.slowpokeTails = packet.slowpokeTails
    }
}