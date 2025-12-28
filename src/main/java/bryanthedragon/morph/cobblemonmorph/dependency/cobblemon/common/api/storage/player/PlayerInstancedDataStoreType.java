/*
 * Copyright (C) 2023 Cobblemon Contributors
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.storage.player

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.storage.player.client.ClientPokedexManager
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.storage.player.client.ClientGeneralPlayerData
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.storage.player.client.ClientInstancedPlayerData
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.SetClientPlayerDataPacket
import net.minecraft.network.RegistryFriendlyByteBuf
import net.minecraft.resources.ResourceLocation

/**
 * Basically, each type here has a server representation, and a client representation
 * Each type has its own deserialization logic, and an action to run on the client after the object is deserialized
 * Some types can also have incremental updates, where a separate run action runs when the incremental flag is present in the packet
 */
class PlayerInstancedDataStoreType(
    val id: ResourceLocation,
    val decoder: (RegistryFriendlyByteBuf) -> (SetClientPlayerDataPacket),
    val afterDecodeAction: (ClientInstancedPlayerData) -> (Unit),
    val incrementalAfterDecodeAction: (ClientInstancedPlayerData) -> Unit = {}
) {

}