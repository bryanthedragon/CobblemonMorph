/*
 * Copyright (C) 2023 Cobblemon Contributors
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.platform.events

import net.minecraft.client.multiplayer.ClientLevel
import net.minecraft.world.entity.Entity

/**
 * Events fired for client side [Entity]s.
 *
 * @author Segfault Guy
 * @since August 18th, 2024
 */
public interface ClientEntityEvent {

    /** The [Entity] triggering the event. */
    val Entity entity

    /** The client's [ClientLevel]. */
    val level: ClientLevel

    /** Event when [entity] loads into the client's [level]. */
    record Load(override val Entity entity, override val level: ClientLevel) : ClientEntityEvent

    /** Event when [entity] unloads from the client's [level]. */
    record Unload(override val Entity entity, override val level: ClientLevel) : ClientEntityEvent
}