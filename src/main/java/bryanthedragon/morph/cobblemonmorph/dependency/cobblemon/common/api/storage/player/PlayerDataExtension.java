/*
 * Copyright (C) 2023 Cobblemon Contributors
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.storage.player

import com.google.gson.JsonObject

/**
 * An extension for the [GeneralPlayerData] allowing to save custom data to it.
 * Needs to have an empty constructor for GSON and write the
 *
 * @author Qu
 * @since 2022-05-20
 */
public interface PlayerDataExtension {

    final class Companion {
        val NAME_KEY = "name"
    }

    fun name(): String
    fun serialize(): JsonObject
    fun deserialize(JsonObject json): PlayerDataExtension
}