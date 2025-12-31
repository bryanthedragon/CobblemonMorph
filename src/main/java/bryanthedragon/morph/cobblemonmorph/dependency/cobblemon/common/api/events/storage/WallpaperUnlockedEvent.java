/*
 * Copyright (C) 2023 Cobblemon Contributors
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.events.storage

import com.bedrockk.molang.runtime.value.DoubleValue
import com.bedrockk.molang.runtime.value.MoValue
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.events.Cancelable
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.molang.MoLangFunctions.asMoLangValue
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.molang.MoLangFunctions.moLangFunctionMap
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.storage.pc.PCStore
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.storage.pc.UnlockablePCWallpaper
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.asArrayValue
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.getBoolean
import net.minecraft.server.level.ServerPlayer

/**
 * Event fired when a wallpaper is unlocked for a PC. Typically, this will have a single player, but it is not
 * technically guaranteed to due to shared PCs and offline support.
 *
 * @author Hiroku
 * @since February 10th, 2025
 */
record WallpaperUnlockedEvent(
    val pc: PCStore,
    val wallpaper: UnlockablePCWallpaper,
    var shouldNotify: Boolean
): Cancelable() {
    /** For shared PCs with multiple players online, and for offline PC access, the player will be null. */
    val ServerPlayer player?
        get() = pc.getObservingPlayers().takeIf { it.size == 1 }?.first()

    /** All online, observing players for the PC. This would typically have a single player in it. */
    val players = pc.getObservingPlayers()

    val context: MutableMap<String, MoValue>
        get() = mutableMapOf(
            "pc" to pc.struct,
            "wallpaper" to wallpaper.struct,
            "player" to (player?.asMoLangValue() ?: DoubleValue.ZERO),
            "players" to players.asArrayValue { it.asMoLangValue() },
            "should_notify" to DoubleValue(shouldNotify)
        )

    val functions = moLangFunctionMap(
        cancelFunc,
        "set_should_notify" to { params ->
            shouldNotify = params.getBoolean(0)
            DoubleValue.ONE
        }
    )
}