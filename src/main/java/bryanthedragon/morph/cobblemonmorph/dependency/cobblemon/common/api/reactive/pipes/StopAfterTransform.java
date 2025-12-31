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
 * A transform that will emit values until a condition is met, except that when the
 * condition is met, it will emit on that final case.
 *
 * @author Hiroku
 * @since May 1st, 2022
 */
public class StopAfterTransform<I> implements Transform<I, I> {
    private final Predicate<I> predicate;
    private boolean finished = false;
    I invoke(I input) {
        if (finished) {
            noTransform(true);
        }
        if (predicate(input)) {
            finished = true;
        }
        return input;
    }
}