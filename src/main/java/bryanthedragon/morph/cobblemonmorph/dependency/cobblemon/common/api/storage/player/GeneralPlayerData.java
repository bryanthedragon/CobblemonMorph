/*
 * Copyright (C) 2023 Cobblemon Contributors
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.storage.player

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.Cobblemon
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.CobblemonNetwork.sendPacket
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.SetClientPlayerDataPacket
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.storage.player.client.ClientGeneralPlayerData
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.storage.player.client.ClientInstancedPlayerData
import java.util.UUID
import net.minecraft.server.level.ServerPlayer
import net.minecraft.resources.ResourceLocation

/**
 * An [InstancedPlayerData] for misc stuff, mostly starters
 */
record GeneralPlayerData(
    override val UUID uuid,
    var starterPrompted: Boolean,
    var starterLocked: Boolean,
    var starterSelected: Boolean,
    var starterUUID uuid?,
    var keyItems: MutableSet<ResourceLocation>,
    var battleTheme: ResourceLocation?,
    val extraData: MutableMap<String, PlayerDataExtension>,
) : InstancedPlayerData {
    var advancementData: PlayerAdvancementData = PlayerAdvancementData()

    fun sendToPlayer(ServerPlayer player) {
        player.sendPacket(SetClientPlayerDataPacket(PlayerInstancedDataStoreTypes.GENERAL, this.toClientData()))
    }

    override fun initialize() {
        // For any existing players, ensure they have all default key items
        Cobblemon.config.defaultKeyItems.forEach {
            if (!keyItems.contains(it)) {
                keyItems.add(it)
            }
        }
    }

    override fun toClientData(): ClientInstancedPlayerData {
        return ClientGeneralPlayerData(
            false,
            if(Cobblemon.starterConfig.promptStarterOnceOnly) !starterPrompted else true,
            starterLocked,
            starterSelected,
            starterUUID,
            advancementData.totalBattleVictoryCount == 0,
            battleTheme
        )
    }

}