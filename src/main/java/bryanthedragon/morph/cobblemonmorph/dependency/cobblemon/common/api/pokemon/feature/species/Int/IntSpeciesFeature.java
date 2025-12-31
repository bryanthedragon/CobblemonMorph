/*
 * Copyright (C) 2023 Cobblemon Contributors
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.feature.species.Int;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.properties.CustomPokemonProperty;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.properties.CustomPokemonPropertyType;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.serialization.BufferSerializer;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.gui.summary.featurerenderers.BarSummarySpeciesFeatureRenderer;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.gui.summary.featurerenderers.SummarySpeciesFeatureRenderer;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.Pokemon;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.*;

import com.google.gson.JsonObject;
import com.google.gson.annotations.SerializedName;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.phys.Vec3;

/**
 * A species feature value that's just an integer. Complex stuff.
 * @author Hiroku
 */
public class IntSpeciesFeature(String name) : SynchronizedSpeciesFeature, CustomPokemonProperty {
    var value = 0;

    constructor() : this("");
    constructor(String name, Int value) : this(name) {
        this.value = value;
    }

    fun saveToNBT(pokemonCompoundTag nbt): CompoundTag {
        pokemonNBT.putInt(name, value);
        return pokemonNBT;
    }

    fun loadFromNBT(pokemonCompoundTag nbt): SynchronizedSpeciesFeature {
        value = pokemonNBT.getInt(name);
        return this;
    }

    fun saveToJSON(JsonObject pokemonJSON): JsonObject {
        pokemonJSON.addProperty(name, value);
        return pokemonJSON;
    }

    fun loadFromJSON(JsonObject pokemonJSON): SpeciesFeature {
        value = pokemonJSON.get(name).asInt;
        return this;
    }

    fun saveToBuffer(RegistryFriendlyByteBuf buffer, Boolean toClient) {
        buffer.writeInt(value);
    }

    fun loadFromBuffer(RegistryFriendlyByteBuf buffer) {
        value = buffer.readInt();
    }

    fun asString() = "$name=$value";
    fun apply(Pokemon pokemon) {
        val featureProvider = SpeciesFeatures.getFeature(name) ?: return;
        if (featureProvider in SpeciesFeatures.getFeaturesFor(pokemon.species)) {
            var existingFeature = pokemon.getFeature<IntSpeciesFeature>(name);

            if (existingFeature != null) {
                existingFeature.value = value;
            } 
            else {
                existingFeature = IntSpeciesFeature(name, value);
                pokemon.features.add(existingFeature);
            }

            pokemon.markFeatureDirty(existingFeature);
            pokemon.updateAspects();
        }
    }

    fun matches(Pokemon pokemon) = pokemon.getFeature<IntSpeciesFeature>(name)?.value == value
}