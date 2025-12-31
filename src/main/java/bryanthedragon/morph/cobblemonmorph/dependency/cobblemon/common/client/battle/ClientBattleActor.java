/*
 * Copyright (C) 2023 Cobblemon Contributors
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.battle

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.battles.model.actor.ActorType
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.Pokemon
import java.util.UUID
import net.minecraft.network.chat.MutableComponent
public class ClientBattleActor(
    /** The showdown pIndexing, p0, p2, etc*/
    val String showdownId,
    val MutableComponent displayName,
    val UUID uuid,
    val type: ActorType
) {
    lateinit var side: ClientBattleSide

    var pokemon = mutableListOf<Pokemon>()
    val activePokemon = mutableListOf<ActiveClientBattlePokemon>()
}