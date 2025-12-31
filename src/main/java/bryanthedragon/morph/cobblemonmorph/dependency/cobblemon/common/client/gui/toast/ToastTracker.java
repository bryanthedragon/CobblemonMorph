/*
 * Copyright (C) 2023 Cobblemon Contributors
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.gui.toast

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.toast.ToastPacket
import net.minecraft.client.Minecraft
import java.util.UUID
public final class ToastTracker {

    private val toasts = hashMapOf<UUID, CobblemonToast>()

    fun handle(packet: ToastPacket, Minecraft client) {
        var needsQueue = false
        var toast = this.toasts[packet.uuid]
        if (toast == null) {
            toast = CobblemonToast(packet)
            this.toasts[packet.uuid] = toast
            needsQueue = true
        }
        toast.updateFrom(packet)
        if (needsQueue) {
            client.toasts.addToast(toast)
        }
    }

}