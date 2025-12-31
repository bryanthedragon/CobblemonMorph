/*
 * Copyright (C) 2023 Cobblemon Contributors
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.storage.player.adapter

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.storage.player.InstancedPlayerData
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.storage.player.PlayerInstancedDataStoreType
import net.minecraft.server.MinecraftServer
import java.util.UUID

/**
 * Loads and saves some kind of InstancedPlayerData
 */
abstract class PlayerDataStoreBackend<T : InstancedPlayerData>(val dataType: PlayerInstancedDataStoreType) {
    abstract fun load(UUID uuid): T
    abstract fun save(playerData: T)
    open fun initialize(store: T) {}

    abstract fun setup(server: MinecraftServer)
}