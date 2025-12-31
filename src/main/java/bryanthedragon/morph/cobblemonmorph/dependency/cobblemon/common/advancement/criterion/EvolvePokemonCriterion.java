/*
 * Copyright (C) 2023 Cobblemon Contributors
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.advancement.criterion;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.asIdentifierDefaultingNamespace;

import com.google.gson.JsonObject;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

import net.minecraft.advancements.critereon.ContextAwarePredicate;
import net.minecraft.server.level.ServerPlayer;
import org.checkerframework.checker.signature.qual.Identifier;

import java.util.Optional;

public class EvolvePokemonCriterion(Optional<ContextAwarePredicate> playerCtx, String species, String evolution, int count): CountableCriterion<EvolvePokemonContext>(playerCtx, count) {

    final class Companion {
        public final Codec<EvolvePokemonCriterion> CODEC  = RecordCodecBuilder.create { it.group(ContextAwarePredicate.CODEC.optionalFieldOf("player").forGetter(EvolvePokemonCriterion::playerCtx), Codec.STRING.optionalFieldOf("species", "any").forGetter(EvolvePokemonCriterion::species), Codec.STRING.optionalFieldOf("evolution", "any").forGetter(EvolvePokemonCriterion::evolution), Codec.INT.optionalFieldOf("count", 0).forGetter(EvolvePokemonCriterion::count)).apply(it, ::EvolvePokemonCriterion) }
    }

    public boolean matches(ServerPlayer player, EvolvePokemonContext context) {
        return context.times >= count && (context.species == species.asIdentifierDefaultingNamespace() || species == "any") && (context.evolution == evolution.asIdentifierDefaultingNamespace() || evolution == "any");
    }
}

public class EvolvePokemonCriterionIDer(Identifier id,LootContextPredicate entity) : CountableCriterion<EvolvePokemonContext>(id, entity) {
   var species = "any";
   var evolution = "any";
   fun toJson(JsonObject json) {
       super.toJson(json);
       json.addProperty("species", species);
       json.addProperty("evolution", evolution);
   }

   fun fromJson(JsonObject json) {
       super.fromJson(json);
       species = json.get("species")?.asString ?: "any";
       evolution = json.get("evolution")?.asString ?: "any";
   }

    boolean matches(ServerPlayer player, EvolvePokemonContext context) {
       return context.times >= count && (context.species == species.asIdentifierDefaultingNamespace() || species == "any") && (context.evolution == evolution.asIdentifierDefaultingNamespace() || evolution == "any");
   }
}