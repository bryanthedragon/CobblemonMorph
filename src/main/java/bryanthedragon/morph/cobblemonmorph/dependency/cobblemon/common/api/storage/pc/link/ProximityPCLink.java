/*
 * Copyright (C) 2023 Cobblemon Contributors
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.storage.pc.link

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.storage.pc.PCStore
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.block.entity.PCBlockEntity
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.toVec3d
import java.util.UUID
import net.minecraft.server.level.ServerPlayer

/**
 * A [PCLink] that is based on proximity to a specific PC. If the PC is broken or the player is moved
 * beyond [maxDistance] from it, they will not be able to make PC changes to this linked PC anymore and
 * the next attempt will actively remove the link.
 *
 * @author Hiroku
 * @since June 20th, 2022
 */
public class ProximityPCLink(
    pc: PCStore,
    playerID: UUID,
    pcBlockEntity: PCBlockEntity,
    val maxDistance: Double = 10.0
) : PCLink(pc, playerID) {
    val world = pcBlockEntity.level
    val pos = pcBlockEntity.blockPos

    override fun isPermitted(ServerPlayer player): Boolean {
        val isWithinRange = player.level() == world && player.position().closerThan(pos.toVec3d(), maxDistance)
        val pcStillStanding = player.level().getBlockEntity(pos) is PCBlockEntity
        if (!isWithinRange || !pcStillStanding) {
            PCLinkManager.removeLink(playerID)
        }
        return isWithinRange && pcStillStanding
    }
}