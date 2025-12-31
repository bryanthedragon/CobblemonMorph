/*
 * Copyright (C) 2023 Cobblemon Contributors
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.npc

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.npc.partyproviders.PoolPartyProvider
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.npc.partyproviders.ScriptPartyProvider
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.npc.partyproviders.SimplePartyProvider
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.storage.party.NPCPartyStore
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.entity.npc.NPCEntity
import com.google.gson.JsonElement
import net.minecraft.server.level.ServerPlayer

/**
 * A provider of a party for battling the NPC. Completely custom party providers will only display
 * as text labels in any GUIs.
 *
 * @author Hiroku
 * @since August 16th, 2023
 */
public interface NPCPartyProvider {
    final class Companion {
        val types = mutableMapOf<String, (String) -> NPCPartyProvider>(
            SimplePartyProvider.TYPE to { SimplePartyProvider() },
            PoolPartyProvider.TYPE to { PoolPartyProvider() },
            ScriptPartyProvider.TYPE to { ScriptPartyProvider() }
        )
    }

    val type: String
    val isStatic: Boolean
    fun provide(npc: NPCEntity, level: Int, players: List<ServerPlayer> = emptyList()): NPCPartyStore
    // Why did I opt for manual JSON loading??? I must have had a reason but I can't remember. Maybe for S2C? Use a codec doofus
    fun loadFromJSON(JsonElement json)
}