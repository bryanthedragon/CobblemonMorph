/*
 * Copyright (C) 2023 Cobblemon Contributors
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.properties;

import java.util.Collection;

/**
 * A provider of a particular sort of [CustomPokemonProperty]. This interface
 * provides the means to parse a new property of the implementing generic type.
 *
 * @author Hiroku
 * @since February 12th, 2022
 */
public interface CustomPokemonPropertyType<T extends CustomPokemonProperty> {
    public static final Iterable<String> keys = null;
    public static final Boolean needsKey = true;
    /** Tries parsing a new instance of this generic type based off a nullable string. */
    T fromString(String value);

    /**
     * Returns a list of literal examples of the values this property will accept.
     * This may not contain every possible value, the intent is for tab completion when using a PokemonProperty argument in a command.
     *
     * @return A list of literal examples.
     */
    Collection<String> examples();

}