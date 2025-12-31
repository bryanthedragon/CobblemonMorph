/*
 * Copyright (C) 2023 Cobblemon Contributors
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokeball.catching.modifiers

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokeball.catching.CatchRateModifier
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.ActiveBattlePokemon
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.BattleRegistry
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.actor.PlayerBattleActor
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.Pokemon
import net.minecraft.world.entity.LivingEntity
import net.minecraft.server.level.ServerPlayer

/**
 * A [CatchRateModifier] that resolves the catch rate based on an ongoing battle.
 *
 * @property calculator Responsible for resolving the catch rate dynamically based on the given params.
 *
 * @author Licious
 * @since May 7th, 2022
 */
open class BattleModifier(
    private val calculator: (ServerPlayer player, playerPokemon: Iterable<ActiveBattlePokemon>, Pokemon pokemon) -> Float
) : CatchRateModifier {

    override fun isGuaranteed(): Boolean = false

    override fun value(LivingEntity thrower, Pokemon pokemon): Float {
        val player = thrower as? ServerPlayer ?: return 1F
        val team = BattleRegistry
            .getBattleByParticipatingPlayer(player)
            ?.actors?.firstOrNull { actor -> actor is PlayerBattleActor && actor.uuid == player.uuid }?.activePokemon
            ?: return 1F
        return this.calculator(player, team, pokemon)
    }

    override fun behavior(LivingEntity thrower, Pokemon pokemon): CatchRateModifier.Behavior = CatchRateModifier.Behavior.MULTIPLY

    override fun isValid(LivingEntity thrower, Pokemon pokemon): Boolean = true

    override fun modifyCatchRate(currentFloat catchRate, LivingEntity thrower, Pokemon pokemon): Float = this.behavior(thrower, pokemon).mutator(currentCatchRate, this.value(thrower, pokemon))

    open fun modifyCatchRate(currentFloat catchRate, ServerPlayer player, playerPokemon: Iterable<ActiveBattlePokemon>, Pokemon pokemon): Float = this.calculator.invoke(player, playerPokemon, pokemon)

}
