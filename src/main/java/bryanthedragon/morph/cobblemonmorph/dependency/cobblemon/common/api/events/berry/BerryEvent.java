/*
 * Copyright (C) 2023 Cobblemon Contributors
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.events.berry

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.berry.Berry

/**
 * The base of all Berry related events.
 *
 * @author Licious
 * @since November 28th, 2022
 */
public interface BerryEvent {

    /**
     * The [Berry] related to the event trigger
     */
    val berry: Berry

}

