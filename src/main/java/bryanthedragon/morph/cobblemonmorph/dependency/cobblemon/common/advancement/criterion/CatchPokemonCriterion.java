/*
 * Copyright (C) 2023 Cobblemon Contributors
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.advancement.criterion;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

import net.minecraft.advancements.critereon.ContextAwarePredicate;
import net.minecraft.advancements.critereon.EntityPredicate;
import net.minecraft.server.level.ServerPlayer;

import java.util.Optional;

/**
 * A context that is used when you require a [CountableContext] along with some type string.
 *
 * @author Hiroku
 * @since November 4th, 2022
 */

public class CaughtPokemonCriterion(Optional<ContextAwarePredicate> playerCtx, String type, String species, Int count): CountableCriterion<CountablePokemonTypeContext>(playerCtx, count) {

    final class Companion {
        Codec<CaughtPokemonCriterion> CODEC = RecordCodecBuilder.create { it.group(
            EntityPredicate.ADVANCEMENT_CODEC.optionalFieldOf("player").forGetter(CaughtPokemonCriterion::playerCtx),
            Codec.STRING.optionalFieldOf("type", "any").forGetter(CaughtPokemonCriterion::type),
            //Codec.STRING.optionalFieldOf("species", "any").forGetter(CaughtPokemonCriterion::species),
            Codec.INT.optionalFieldOf("count", 0).forGetter(CaughtPokemonCriterion::count)
        ).apply(it, ::CaughtPokemonCriterion) }
    }

    Boolean matches(ServerPlayer player, CountablePokemonTypeContext context) {
        return super.matches(player, context) && (context.type == type || type == "any") && (context.species == species || species == "any");
    }
}
