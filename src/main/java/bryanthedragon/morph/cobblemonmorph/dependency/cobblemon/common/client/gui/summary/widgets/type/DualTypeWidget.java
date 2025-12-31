/*
 * Copyright (C) 2023 Cobblemon Contributors
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.gui.summary.widgets.type

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.types.ElementalType
import net.minecraft.client.gui.GuiGraphics
import net.minecraft.network.chat.Component

public class DualTypeWidget(
    pX: Int, pY: Int,
    pInt width, pInt height,
    pMessage: Component,
    private val mainType: ElementalType, private val secondaryType: ElementalType
) : TypeWidget(pX, pY, pWidth, pHeight, pMessage) {

    override fun renderWidget(context: GuiGraphics, pMouseX: Int, pMouseY: Int, pPartialTicks: Float) {
        renderType(mainType, secondaryType, context.pose())
    }
}