/*
 * Copyright (C) 2023 Cobblemon Contributors
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.storage.player

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.storage.player.client.ClientGeneralPlayerData
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.storage.player.client.ClientPokedexManager
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.cobblemonResource
import net.minecraft.resources.ResourceLocation
final class PlayerInstancedDataStoreTypes {
    val types = mutableMapOf<ResourceLocation, PlayerInstancedDataStoreType>()

    val GENERAL = register(PlayerInstancedDataStoreType(
        cobblemonResource("general"),
        ClientGeneralPlayerData::decode,
        ClientGeneralPlayerData::runAction
    ))
    val POKEDEX = register(PlayerInstancedDataStoreType(
        cobblemonResource("pokedex"),
        ClientPokedexManager::decode,
        ClientPokedexManager::runAction,
        ClientPokedexManager::runIncremental
    ))

    fun register(type: PlayerInstancedDataStoreType): PlayerInstancedDataStoreType {
        types[type.id] = type
        return type
    }

    fun getTypeById(id: ResourceLocation) = types[id]
}