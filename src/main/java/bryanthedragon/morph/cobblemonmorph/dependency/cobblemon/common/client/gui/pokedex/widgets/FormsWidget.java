/*
 * Copyright (C) 2023 Cobblemon Contributors
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.gui.pokedex.widgets

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.gui.blitk
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.text.text
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.gui.ScrollingWidget
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.gui.pokedex.PokedexGUIConstants
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.drawScaledText
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.FormData
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.cobblemonResource
import net.minecraft.client.gui.GuiGraphics
import net.minecraft.network.chat.Component
import kotlin.math.max

class FormsWidget (val pX: Int, val pY: Int, val setFormData : (String) -> (Unit)): ScrollingWidget<FormsWidget.FormSlot>(
        left = pX,
        top = pY,
        width = PokedexGUIConstants.POKEMON_FORMS_WIDTH,
        height = PokedexGUIConstants.POKEMON_FORMS_HEIGHT,
        slotHeight = 15
) {

    override fun addEntry(entry: FormSlot): Int {
        return super.addEntry(entry)
    }

    fun setForms(forms: Collection<String>){
        clearEntries()
        forms.forEach {
            addEntry(
                FormSlot(it, setFormData)
            )
        }
    }

    override fun getScrollbarPosition(): Int {
        return left + width - scrollBarWidth
    }

    override fun getMaxScroll() = max(this.height.toDouble(), (this.maxPosition - (this.bottom - this.y - 4)).toDouble()).toInt()

    class FormSlot(val form : String, val setFormData: (String) -> Unit) : Slot<FormSlot>() {

        companion object {
            private val scrollSlotResource = cobblemonResource("textures/gui/pokedex/scroll_slot_base.png")// Render Scroll Slot Background
        }

        override fun render(
            context: GuiGraphics,
            index: Int,
            y: Int,
            x: Int,
            entryWidth: Int,
            entryHeight: Int,
            mouseX: Int,
            mouseY: Int,
            hovered: Boolean,
            tickDelta: Float
        ) {

            val textScale = 1F
            blitk(
                matrixStack = context.pose(),
                texture = scrollSlotResource,
                x = x,
                y = y,
                width = entryWidth,
                height = entryHeight
            )

            drawScaledText(
                context = context,
                text = form.text(),
                x = x + entryWidth/2,
                y = y + entryHeight/2 - 5,
                scale = textScale,
                centered = true
            )
        }

        override fun getNarration(): Component {
            return Component.literal(form)
        }

        override fun mouseClicked(mouseX: Double, mouseY: Double, button: Int): Boolean {
            setFormData.invoke(form)
            return true
        }
    }
}