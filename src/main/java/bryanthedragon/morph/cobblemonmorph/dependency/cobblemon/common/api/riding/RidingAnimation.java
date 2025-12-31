/*
 * Copyright (C) 2023 Cobblemon Contributors
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.riding

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.readString
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.writeString
import net.minecraft.network.RegistryFriendlyByteBuf

public class RidingAnimation(
    val fileName: String,
    val animationName: String
) {
    fun encode(RegistryFriendlyByteBuf buffer) {
        buffer.writeString(fileName)
        buffer.writeString(animationName)
    }

    final class Companion {
        fun decode(RegistryFriendlyByteBuf buffer) : RidingAnimation {
            return RidingAnimation(
                buffer.readString(),
                buffer.readString()
            )
        }
    }
}
