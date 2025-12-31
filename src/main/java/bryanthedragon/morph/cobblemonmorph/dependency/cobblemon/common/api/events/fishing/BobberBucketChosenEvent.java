/*
 * Copyright (C) 2023 Cobblemon Contributors
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.events.fishing

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.SpawnBucket

/**
 * Event that is fired when a bucket is chosen by the bobber.
 * @param chosenBucket The bucket that is chosen.
 * @param buckets The list of buckets that the bobber can choose from.
 * @param luckOfTheSeaLevel The level of the Luck of the Sea enchantment on the rod.
 */
public class BobberBucketChosenEvent(var chosenBucket: SpawnBucket, val buckets: MutableList<SpawnBucket>, val luckOfTheSeaLevel: Int)