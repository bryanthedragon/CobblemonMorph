/*
 * Copyright (C) 2023 Cobblemon Contributors
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.evolution.progress;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.evolution.progress.EvolutionProgress;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.evolution.progress.EvolutionProgressType;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.evolution.progress.EvolutionProgressTypes;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.Pokemon;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.requirements.BattleCriticalHitsRequirement;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.cobblemonResource;

import com.google.gson.JsonObject;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

public class LastBattleCriticalHitsEvolutionProgress extends EvolutionProgress<LastBattleCriticalHitsEvolutionProgress.Progress> {

    private var progress = Progress(0)

    override fun id(): ResourceLocation = ID

    override fun currentProgress(): Progress = this.progress

    override fun updateProgress(progress: Progress) {
        this.progress = progress
    }

    override fun reset() {
        this.updateProgress(Progress(0))
    }

    override fun shouldKeep(Pokemon pokemon): Boolean = supports(pokemon)

    override fun type(): EvolutionProgressType<*> = EvolutionProgressTypes.LAST_BATTLE_CRITICAL_HITS

    record Progress(val amount: Int)

    final class Companion {

        val ID = cobblemonResource(BattleCriticalHitsRequirement.ADAPTER_VARIANT)
        private const val AMOUNT = "amount"

        @JvmStatic
        val CODEC: MapCodec<LastBattleCriticalHitsEvolutionProgress> = RecordCodecBuilder.mapCodec { instance ->
            instance.group(
                Codec.intRange(0, Int.MAX_VALUE).fieldOf(AMOUNT).forGetter { it.progress.amount }
            ).apply(instance) { amount -> LastBattleCriticalHitsEvolutionProgress().apply { updateProgress(Progress(amount)) } }
        }

        fun supports(Pokemon pokemon): Boolean {
            return pokemon.form.evolutions.any { evolution ->
                evolution.requirements.any { requirement ->
                    requirement is BattleCriticalHitsRequirement
                }
            }
        }

    }

}