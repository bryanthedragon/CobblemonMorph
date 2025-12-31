/*
 * Copyright (C) 2023 Cobblemon Contributors
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pasture

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.PrioritizedList
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.block.entity.PokemonPastureBlockEntity
import net.minecraft.server.level.ServerPlayer

/**
 * Contains a list of [PasturePermissionController]s to control what kind of access a player
 * has with particular pasture blocks. Controllers are checked in order of priority according
 * to the rules of [PrioritizedList]s, and the first non-null [PasturePermissions] returned
 * for a given player and pasture block entity will be used.
 *
 * The fallback of [PasturePermissionControllers.permit] will return permissions that allow
 * the player to pasture their own Pokémon, unpasture others', and will have as many slots
 * to pasture Pokémon as slots exist on the block entity.
 *
 * @author Hiroku
 * @since July 2nd, 2023
 */
public final class PasturePermissionControllers {
    val controllers = PrioritizedList<PasturePermissionController>()

    @JvmStatic
    fun permit(ServerPlayer player, pastureBlockEntity: PokemonPastureBlockEntity): PasturePermissions {
        return controllers.firstNotNullOfOrNull { it.permit(player, pastureBlockEntity) }
            ?: PasturePermissions(
                canUnpastureOthers = true,
                canPasture = true,
                maxPokemon = pastureBlockEntity.getMaxTethered()
            )
    }
}