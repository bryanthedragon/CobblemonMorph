/*
 * Copyright (C) 2023 Cobblemon Contributors
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokedex.scanner

import java.util.UUID
final class PlayerScanningDetails {
    //Maybe make some of this stuff fixed size caches
    //Maps a player UUID to the UUID of the entity being scanned
    val playerToEntityMap: MutableMap<UUID, UUID> = mutableMapOf()
    //Maps a player UUID to the time (the number of the server tick) they started scanning their current pokemon
    val playerToTickMap: MutableMap<UUID, Int> = mutableMapOf()
}