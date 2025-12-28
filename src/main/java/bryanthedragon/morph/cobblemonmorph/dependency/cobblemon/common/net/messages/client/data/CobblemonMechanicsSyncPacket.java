/*
 * Copyright (C) 2023 Cobblemon Contributors
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.data

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.net.NetworkPacket
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.mechanics.AprijuicesMechanic
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.mechanics.BerriesMechanic
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.mechanics.PotionsMechanic
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.mechanics.RemediesMechanic
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.mechanics.SlowpokeTailsMechanic
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.cobblemonResource
import net.minecraft.network.RegistryFriendlyByteBuf

class CobblemonMechanicsSyncPacket(
    val remedies: RemediesMechanic,
    val berries: BerriesMechanic,
    val potions: PotionsMechanic,
    val aprijuices: AprijuicesMechanic,
    val slowpokeTails: SlowpokeTailsMechanic
) : NetworkPacket<CobblemonMechanicsSyncPacket> {
    override val id = ID

    override fun encode(buffer: RegistryFriendlyByteBuf) {
        remedies.encode(buffer)
        berries.encode(buffer)
        potions.encode(buffer)
        aprijuices.encode(buffer)
        slowpokeTails.encode(buffer)
    }

    companion object {
        val ID = cobblemonResource("cobblemon_mechanics_sync")

        fun decode(buffer: RegistryFriendlyByteBuf): CobblemonMechanicsSyncPacket {
            return CobblemonMechanicsSyncPacket(
                RemediesMechanic.decode(buffer),
                BerriesMechanic.decode(buffer),
                PotionsMechanic.decode(buffer),
                AprijuicesMechanic.decode(buffer),
                SlowpokeTailsMechanic.decode(buffer)
            )
        }
    }
}