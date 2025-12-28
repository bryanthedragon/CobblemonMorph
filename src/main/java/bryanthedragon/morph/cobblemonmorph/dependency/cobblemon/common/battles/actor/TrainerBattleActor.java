/*
 * Copyright (C) 2023 Cobblemon Contributors
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.actor

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.battles.model.actor.AIBattleActor
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.battles.model.actor.ActorType
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.battles.model.ai.BattleAI
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.pokemon.BattlePokemon
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.asTranslated
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.battleLang
import java.util.UUID
import net.minecraft.network.chat.MutableComponent

class TrainerBattleActor(
    val trainerName: String,
    uuid: UUID,
    pokemonList: List<BattlePokemon>,
    artificialDecider: BattleAI
) : AIBattleActor(uuid, pokemonList, artificialDecider) {
    override fun getName() = trainerName.asTranslated()
    override fun nameOwned(name: String): MutableComponent = battleLang("owned_pokemon", this.getName(), name)
    override val type = ActorType.NPC
}