/*
 * Copyright (C) 2023 Cobblemon Contributors
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.net.cooking

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.Cobblemon
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.net.ServerNetworkPacketHandler
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.block.campfirepot.CookingPotMenu
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.block.entity.CampfireBlockEntity
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.cooking.ToggleCookingPotLidPacket
import net.minecraft.server.MinecraftServer
import net.minecraft.server.level.ServerPlayer
public final class ToggleCookingPotLidHandler : ServerNetworkPacketHandler<ToggleCookingPotLidPacket> {
    override fun handle(
        packet: ToggleCookingPotLidPacket,
        server: MinecraftServer,
        ServerPlayer player
    ) {
        if (player.containerMenu !is CookingPotMenu) {
            Cobblemon.LOGGER.debug("Player {} interacted with invalid menu {}", player, player.containerMenu);
            return
        }

        val menu = player.containerMenu as? CookingPotMenu ?: return
        val isLidOpen = packet.value
        if (menu.container is CampfireBlockEntity) {
            menu.container.toggleLid(isLidOpen)
        }
    }
}