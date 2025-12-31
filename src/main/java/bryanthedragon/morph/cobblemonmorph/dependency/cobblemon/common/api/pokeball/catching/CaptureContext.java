/*
 * Copyright (C) 2023 Cobblemon Contributors
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokeball.catching;

public record CaptureContext(int numberOfShakes, Boolean isSuccessfulCapture, Boolean isCriticalCapture) {

    final class Companion {
        /**
         * Creates a successful capture.
         * The amount of [CaptureContext.numberOfShakes] will be 4 if [critical] is false otherwise 1.
         *
         * @param critical If the capture is a critical capture, defaults to false.
         * @return The generated [CaptureContext].
         */
        CaptureContext successful(Boolean critical) {
            if (critical) {
                return CaptureContext(numberOfShakes = 1, isSuccessfulCapture = true, isCriticalCapture = true);
            }
            return CaptureContext(numberOfShakes = 4, isSuccessfulCapture = true, isCriticalCapture = false);
        }

    }

}