/*
 * Copyright (C) 2023 Cobblemon Contributors
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.events.berry

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.berry.Berry
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.block.BerryBlock
import net.minecraft.core.BlockPos
import net.minecraft.world.level.Level
import net.minecraft.world.level.block.state.BlockState

/**
 * The event fired when [BerryBlock.grow] is invoked and the tree passes to [BerryBlock.MATURE_AGE].
 * This allows you to modify the possible mutations for the tree, for the event that decides which of these mutations is used see [BerryMutationResultEvent].
 *
 * @property world The [World] the berry tree is in.
 * @property state The [BlockState] of the berry tree.
 * @property pos The [BlockPos] of the berry tree.
 * @property mutations The possible [Berry] mutations.
 * @property pickedMutation The [Berry] chosen to mutate into, if null the mutation will not occur.
 *
 * @author Licious
 * @since January 19th, 2022
 */
record BerryMutationResultEvent(
    override val berry: Berry,
    val Level world,
    val BlockState state,
    val (BlockPos pos,
    val mutations: Set<Berry>,
    var pickedMutation: Berry?
) : BerryEvent