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
 * A dummy implementation of the [PlayerDataExtension] interface.
 * Used for when a previous [PlayerDataExtension] is no longer registered.
 *
 * @author Deltric
 * @since 2023-10-01
 */
public class DummyPlayerDataExtension(
    val JsonObject json
): PlayerDataExtension {

    override fun name(): String {
        return this.json.get(PlayerDataExtension.NAME_KEY).asString
    }

    override fun serialize(): JsonObject {
        return this.json
    }

    override fun deserialize(JsonObject json): PlayerDataExtension {
        return DummyPlayerDataExtension(json)
    }
}