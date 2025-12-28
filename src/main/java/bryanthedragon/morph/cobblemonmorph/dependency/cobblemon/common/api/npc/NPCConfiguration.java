/*
 * Copyright (C) 2023 Cobblemon Contributors
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.npc

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.npc.configuration.NPCBattleConfiguration
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.entity.npc.NPCEntity

/**
 * An NPC configuration is like a snapshot of an NPC setup. It can be taken from an existing NPC and loaded onto
 * others. Useful as a backup mechanism or as shorthand for copying an NPC's configuration without building an
 * entire class for them.
 *
 * @author Hiroku
 * @since August 16th, 2023
 */
class NPCConfiguration {
    var battle: NPCBattleConfiguration? = null

    fun applyTo(npc: NPCEntity) {
    }

    fun loadFrom(npc: NPCEntity) {
    }
}