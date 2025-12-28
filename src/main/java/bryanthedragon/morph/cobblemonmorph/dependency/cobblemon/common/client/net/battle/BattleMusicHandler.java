/*
 * Copyright (C) 2023 Cobblemon Contributors
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.net.battle

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.net.ClientNetworkPacketHandler
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.sound.BattleMusicController
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.sound.instances.BattleMusicInstance
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.battle.BattleMusicPacket
import net.minecraft.client.Minecraft
import net.minecraft.sounds.SoundEvent

/**
 * The handler for [BattleMusicPacket]s. Interfaces with [BattleMusicController] to change battle music.
 *
 * @author Segfault Guy
 * @since April 22nd, 2023
 */final class BattleMusicHandler : ClientNetworkPacketHandler<BattleMusicPacket> {

    override fun handle(packet: BattleMusicPacket, client: Minecraft) {
        val soundManager = client.soundManager
        val currMusic = BattleMusicController.music
        val newMusic = packet.music?.let {
            val event = SoundEvent.createVariableRangeEvent(it)
            BattleMusicInstance(event, packet.volume, packet.pitch)
        }

        if (newMusic == null)
            BattleMusicController.endMusic()
        else if (!soundManager.isActive(currMusic))
            BattleMusicController.initializeMusic(newMusic)
        else if (currMusic.location == newMusic.location && !packet.restartExisting)
            return
        else
            BattleMusicController.switchMusic(newMusic)
    }
}
