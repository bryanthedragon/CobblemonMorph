/*
 * Copyright (C) 2023 Cobblemon Contributors
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.events.storage

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.events.Cancelable
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.storage.pc.PCBox
import net.minecraft.server.level.ServerPlayer

/**
 * Events fired whenever a player renames one of their PC boxes.
 * Has a cancelable [Pre] event and a [Post] which gets fired after the change.
 *
 * @author JustAHuman-xD
 * @since February 14th, 2025
 */
public interface RenamePCBoxEvent {

    /**
     * The [ServerPlayer] who is changing their wallpaper
     */
    val ServerPlayer player

    /**
     * The [PCBox] whose wallpaper is being changed
     */
    val box: PCBox

    /**
     * The new box name being used. This can be modified in the [Pre] event.
     */
    val String name

    class Pre(
        override val ServerPlayer player,
        override val box: PCBox,
        override var String name
    ) : RenamePCBoxEvent, Cancelable()

    class Post(
        override val ServerPlayer player,
        override val box: PCBox,
        override val String name
    ) : RenamePCBoxEvent
}