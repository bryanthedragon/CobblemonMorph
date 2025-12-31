/*
 * Copyright (C) 2023 Cobblemon Contributors
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.reactive.pipes;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.reactive.Transform;

/**
 * A transform that transforms the emitted values from one value to another using the given mapping function.
 *
 * @author Hiroku
 * @since November 26th, 2021
 */
public class MapTransform<I, O>(private val mapping: (I) -> O) implements Transform<I, O> {
    O invoke(I input) {
        return mapping(input);
    }
}