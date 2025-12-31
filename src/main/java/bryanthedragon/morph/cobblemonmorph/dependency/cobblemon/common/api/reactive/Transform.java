/*
 * Copyright (C) 2023 Cobblemon Contributors
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.reactive;

/**
 * A transformation function that can be used in [Observable.pipe] to translate the [Observable] in some way.
 * If an input value should not have an output, using [Transform.noTransform] will throw a controlled exception
 * which prevents the [Observable] from propagating anything for that input. That function has a parameter for
 * whether the transformed [Observable] should clear all subscriptions.
 *
 * @author Hiroku
 * @since November 26th, 2021
 */
public interface Transform<I, O> {

    // Kotlin companion object → Java static members
    final class Companion {
        private static final NoTransformThrowable NO_TRANSFORM_NO_TERMINATE = new NoTransformThrowable(false);
        private static final NoTransformThrowable NO_TRANSFORM_TERMINATE = new NoTransformThrowable(true);
    }

    O invoke(I input) throws NoTransformThrowable;

    default void noTransform(boolean terminate) throws NoTransformThrowable {
        throw terminate ? Companion.NO_TRANSFORM_TERMINATE : Companion.NO_TRANSFORM_NO_TERMINATE;
    }
}
