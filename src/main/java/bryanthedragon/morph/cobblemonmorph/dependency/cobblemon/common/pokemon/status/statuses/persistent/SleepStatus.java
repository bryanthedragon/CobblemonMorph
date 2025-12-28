/*
 * Copyright (C) 2023 Cobblemon Contributors
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.status.statuses.persistent

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.status.PersistentStatus
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.cobblemonResource
class SleepStatus : PersistentStatus(
    name = cobblemonResource("sleep"),
    showdownName = "slp",
    applyMessage = "cobblemon.status.sleep.apply",
    removeMessage = "cobblemon.status.sleep.cure",
    defaultDuration = IntRange(180, 300)
)