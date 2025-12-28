/*
 * Copyright (C) 2023 Cobblemon Contributors
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.moves.MoveTemplate

/**
 * The result of adding experience to a [Pokemon]. This contains information
 * about any level changes and any new moves that were learned.
 *
 * @author Hiroku
 * @since April 18th, 2022
 */
record AddExperienceResult(
    val oldLevel: Int,
    val newLevel: Int,
    val newMoves: Set<MoveTemplate>,
    val experienceAdded: Int
)