/*
 * Copyright (C) 2023 Cobblemon Contributors
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.net.orientation

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.OrientationControllable
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.net.ClientNetworkPacketHandler
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.duck.RidePassenger
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.orientation.ClientboundUpdateOrientationPacket
import net.minecraft.client.Minecraft
import net.minecraft.util.Mth
public final class ClientboundUpdateOrientationHandler : ClientNetworkPacketHandler<ClientboundUpdateOrientationPacket> {
    override fun handle(packet: ClientboundUpdateOrientationPacket, Minecraft client) {
        client.executeIfPossible {
            val level = client.level ?: return@executeIfPossible
            val player = client.player ?: return@executeIfPossible
            val vehicle = level.getEntity(packet.entityId)
            val vehicleController = if (vehicle is OrientationControllable) vehicle.orientationController else return@executeIfPossible

            // If the client is also the driver of this entity then don't update it as the driver is the source
            // of truth on vehicle orientation updates.
            if (vehicle.controllingPassenger != player) {
                packet.active?.let { shouldUseCustomOrientation ->

                    val playerIsPassenger = vehicle.passengers.contains(player)

                    // If the player has just switched to riding a custom orientation ride then set their
                    // ride x and y rots local to the vehicle rots(if the controller wasn't active and will now be active)
                    // This ensures that on transition your camera stays in the same spot
                    if (!vehicleController.isActive() && shouldUseCustomOrientation && playerIsPassenger) {
                        // Set local to the vehicle x and yrot
                        val playerRotater = player as RidePassenger?
                        if (playerRotater != null) {
                            playerRotater.`cobblemon$setRideXRot`(Mth.wrapDegrees(player.getXRot() - vehicle.getXRot()))
                            playerRotater.`cobblemon$setRideYRot`(Mth.wrapDegrees(player.getYRot() - vehicle.getYRot()))
                        }
                    }

                    vehicle.orientationController.active = shouldUseCustomOrientation
                }
                vehicle.orientationController.updateOrientation { _ -> packet.orientation }
            }
        }
    }
}
