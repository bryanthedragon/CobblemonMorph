/*
 * Copyright (C) 2023 Cobblemon Contributors
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.ResourcePackActivationBehaviour
import net.minecraft.network.chat.Component
import net.minecraft.server.packs.PackType

public class CobblemonPack(
    val id: String,
    val String name,
    val packType: PackType,
    val activationBehaviour: ResourcePackActivationBehaviour,
    val neededMods: Set<String> = setOf()
) {
    val displayName = Component.literal(name)
}