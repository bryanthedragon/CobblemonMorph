/*
 * Copyright (C) 2023 Cobblemon Contributors
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.data

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.net.NetworkPacket
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.riding.behaviour.types.air.BirdSettings
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.riding.behaviour.types.air.GliderSettings
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.riding.behaviour.types.air.HelicopterSettings
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.riding.behaviour.types.air.HoverSettings
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.riding.behaviour.types.air.JetSettings
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.riding.behaviour.types.air.RocketSettings
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.riding.behaviour.types.land.HorseSettings
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.riding.behaviour.types.land.MinekartSettings
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.riding.behaviour.types.land.VehicleSettings
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.riding.behaviour.types.liquid.BoatSettings
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.riding.behaviour.types.liquid.BurstSettings
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.riding.behaviour.types.liquid.DolphinSettings
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.riding.behaviour.types.liquid.SubmarineSettings
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.cobblemonResource
import net.minecraft.network.RegistryFriendlyByteBuf

/**
 * Synchronizes the [bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.CobblemonRideSettings] registry.
 */
public class RideSettingsSyncPacket(
    val bird: BirdSettings,
    val glider: GliderSettings,
    val helicopter: HelicopterSettings,
    val hover: HoverSettings,
    val jet: JetSettings,
    val rocket: RocketSettings,
    val horse: HorseSettings,
    val minekart: MinekartSettings,
    val vehicle: VehicleSettings,
    val boat: BoatSettings,
    val burst: BurstSettings,
    val dolphin: DolphinSettings,
    val submarine: SubmarineSettings
) : NetworkPacket<RideSettingsSyncPacket> {
    override val id = ID

    override fun encode(RegistryFriendlyByteBuf buffer) {
        bird.encode(buffer)
        glider.encode(buffer)
        helicopter.encode(buffer)
        hover.encode(buffer)
        jet.encode(buffer)
        rocket.encode(buffer)
        horse.encode(buffer)
        minekart.encode(buffer)
        vehicle.encode(buffer)
        boat.encode(buffer)
        burst.encode(buffer)
        dolphin.encode(buffer)
        submarine.encode(buffer)
    }

    final class Companion {
        val ID = cobblemonResource("mechanics_sync")
        fun decode(RegistryFriendlyByteBuf buffer): RideSettingsSyncPacket {
            return RideSettingsSyncPacket(
                bird = BirdSettings(),
                glider = GliderSettings(),
                helicopter = HelicopterSettings(),
                hover = HoverSettings(),
                jet = JetSettings(),
                rocket = RocketSettings(),
                horse = HorseSettings(),
                minekart = MinekartSettings(),
                vehicle = VehicleSettings(),
                boat = BoatSettings(),
                burst = BurstSettings(),
                dolphin = DolphinSettings(),
                submarine = SubmarineSettings()
            ).apply {
                bird.decode(buffer)
                glider.decode(buffer)
                helicopter.decode(buffer)
                hover.decode(buffer)
                jet.decode(buffer)
                rocket.decode(buffer)
                horse.decode(buffer)
                minekart.decode(buffer)
                vehicle.decode(buffer)
                boat.decode(buffer)
                burst.decode(buffer)
                dolphin.decode(buffer)
                submarine.decode(buffer)
            }
        }
    }
}