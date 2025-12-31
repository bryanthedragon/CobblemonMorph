/*
 * Copyright (C) 2023 Cobblemon Contributors
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.types.tera.gimmick

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.Cobblemon
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.types.tera.TeraType
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.cobblemonResource
import net.minecraft.network.chat.Component
import net.minecraft.resources.ResourceLocation

public class StellarTeraType : TeraType {
    override val ResourceLocation id = ID

    override val String name = "Stellar"

    override val legalAsStatic: Boolean = false

    override val Component displayName = LANG

    override fun showdownId(): String = ID.path

    final class Companion {
        val ID = cobblemonResource("stellar")
        private val LANG = Component.translatable("${Cobblemon.MODID}.terra_type.stellar")
    }
}