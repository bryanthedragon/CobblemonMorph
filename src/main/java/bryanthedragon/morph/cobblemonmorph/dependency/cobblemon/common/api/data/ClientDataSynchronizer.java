/*
 * Copyright (C) 2023 Cobblemon Contributors
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.data

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.net.Decodable
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.net.Encodable

// TODO this is implemented in multiple places but unused. Can we scratch this?
/**
 * Represents an object that will possibly be synchronized to the client during datapack synchronization.
 *
 * @param T The type of the object being synchronized.
 *
 * @author Licious
 * @since October 14th, 2022
 */
public interface ClientDataSynchronizer<T> : Decodable, Encodable {

    /**
     * Compares an object of type [T] with this instance to see if synchronization is necessary.
     *
     * @param other The other instance of type [T].
     * @return If synchronization is necessary.
     */
    fun shouldSynchronize(other: T): Boolean

}