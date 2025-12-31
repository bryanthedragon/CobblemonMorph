/*
 * Copyright (C) 2023 Cobblemon Contributors
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.feature.species.provider.sync;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.feature.SynchronizedSpeciesFeature;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.feature.species.provider.SpeciesFeatureProvider;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.serialization.BufferSerializer;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.gui.summary.featurerenderers.SummarySpeciesFeatureRenderer;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.Pokemon;

import net.minecraft.network.RegistryFriendlyByteBuf;

/**
 * A species feature provider that will be synchronized to the client. For it to be renderable in the summary
 * screen of a Pokémon it must also provide a [SummarySpeciesFeatureRenderer] in [getRenderer]. Note that you
 * really don't want to initialize a renderer outside of that function call because if the server ever initializes
 * it then you're going to get crashes on dedicated servers (this is a bad thing).
 *
 * @author Hiroku
 * @since November 13th, 2023
 */
public interface SynchronizedSpeciesFeatureProvider<T extends SynchronizedSpeciesFeature> extends SpeciesFeatureProvider<T>, BufferSerializer {
    Boolean visible;
    default T invoke(RegistryFriendlyByteBuf buffer, String name){
        return buffer + " " + name;
    }

    /** Gets the feature from this Pokémon, if it has been created yet. */
    default T getPokemon(Pokemon pokemon)
    {
        return pokemon;
    }

    /** Only run this from the client. */
    default SummarySpeciesFeatureRenderer<T> getRenderer(Pokemon pokemonrenderer) {
        return pokemonrenderer;
    }
}