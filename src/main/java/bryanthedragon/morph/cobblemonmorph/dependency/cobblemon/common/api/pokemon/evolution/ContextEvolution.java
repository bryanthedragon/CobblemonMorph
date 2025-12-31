/*
 * Copyright (C) 2023 Cobblemon Contributors
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.evolution;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.Pokemon;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.evolution.variants.ItemInteractionEvolution;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.evolution.variants.TradeEvolution;

/**
 * Represents an evolution of a [Pokemon] that can only occur during specific actions and with added context.
 * For the default implementations see [ItemInteractionEvolution] & [TradeEvolution].
 *
 * @param RC The context given at runtime when querying the [Evolution].
 * @param TC The context that is serialized from JSON during species loading, this is what the [RC] is expected to match against.
 * @author Licious
 * @since March 19th, 2022
 */
public interface ContextEvolution<RC, TC> : Evolution {

    /**
     * The target context for this [Evolution] to even be tested.
     */
    public final TC requiredContext;

    /**
     * Attempts to evolve the given [Pokemon] under the given context of type [RC].
     *
     * @param pokemon The [Pokemon] attempting to evolve.
     * @param context The context of this query.
     * @return If the evolution was successful.
     */
    boolean attemptEvolution(Pokemon pokemon, RC context) {
        if (this.testContext(pokemon, context) && super.test(pokemon)) {
            return super.evolve(pokemon);
        }
        return false;
    }

    /**
     * Checks if the given context is valid for the [requiredContext].
     *
     * @param pokemon The [Pokemon] attempting to evolve.
     * @param context The context of this query.
     * @return If the context matched the [requiredContext].
     */
    boolean testContext(Pokemon pokemon, RC context);

}