/*
 * Copyright (C) 2023 Cobblemon Contributors
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.platform.events

import com.bedrockk.molang.runtime.value.MoValue
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.molang.MoLangFunctions.asMoLangValue
import net.minecraft.server.MinecraftServer

/**
 * Event fired each time the server ticks.
 *
 * @author Licious
 * @since February 15th, 2023
 */
public interface ServerTickEvent {

    /**
     * The [MinecraftServer] instance.
     */
    val server: MinecraftServer

    val context: MutableMap<String, MoValue>
        get() = mutableMapOf("server" to server.asMoLangValue())

    /**
     * Fired during the Pre tick phase.
     */
    record Pre(override val server: MinecraftServer) : ServerTickEvent

    /**
     * Fired during the Post tick phase.
     */
    record Post(override val server: MinecraftServer) : ServerTickEvent

}