/*
 * Copyright (C) 2023 Cobblemon Contributors
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.evolution.progress

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.moves.MoveTemplate
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.evolution.progress.EvolutionProgress
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.evolution.progress.EvolutionProgressType
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.evolution.progress.EvolutionProgressTypes
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.Pokemon
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.requirements.UseMoveRequirement
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.cobblemonResource
import com.google.gson.JsonObject
import net.minecraft.resources.ResourceLocation
import com.mojang.serialization.Codec
import com.mojang.serialization.MapCodec
import com.mojang.serialization.codecs.RecordCodecBuilder

/**
 * An [EvolutionProgress] meant to keep track of the amount of times a specific move in battle.
 *
 * @author Licious
 * @since January 28th, 2022
 */
class UseMoveEvolutionProgress : EvolutionProgress<UseMoveEvolutionProgress.Progress> {

    private var progress = Progress(MoveTemplate.dummy(""), 0)

    override fun id(): ResourceLocation = ID

    override fun currentProgress(): Progress = this.progress

    override fun updateProgress(progress: Progress) {
        this.progress = progress
    }

    override fun reset() {
        this.progress = Progress(MoveTemplate.dummy(""), 0)
    }

    override fun shouldKeep(pokemon: Pokemon): Boolean = supports(pokemon, this.progress.move)

    override fun type(): EvolutionProgressType<*> = EvolutionProgressTypes.USE_MOVE

    record Progress(
        val move: MoveTemplate,
        val amount: Int
    )

    companion object {

        val ID = cobblemonResource("use_move")
        private const val MOVE = "move"
        private const val AMOUNT = "amount"

        @JvmStatic
        val CODEC: MapCodec<UseMoveEvolutionProgress> = RecordCodecBuilder.mapCodec { instance ->
            instance.group(
                MoveTemplate.BY_STRING_CODEC.fieldOf(MOVE).forGetter { it.progress.move },
                Codec.intRange(0, Int.MAX_VALUE).fieldOf(AMOUNT).forGetter { it.progress.amount }
            ).apply(instance) { move, amount -> UseMoveEvolutionProgress().apply { updateProgress(Progress(move, amount)) } }
        }

        fun supports(pokemon: Pokemon, move: MoveTemplate): Boolean {
            return pokemon.form.evolutions.any { evolution ->
                evolution.requirements.any { requirement ->
                    requirement is UseMoveRequirement && requirement.move == move
                }
            }
        }

    }

}