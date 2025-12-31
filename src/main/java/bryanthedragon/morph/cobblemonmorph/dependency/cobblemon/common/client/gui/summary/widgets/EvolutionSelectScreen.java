/*
 * Copyright (C) 2023 Cobblemon Contributors
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.gui.summary.widgets

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.CobblemonSounds
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.gui.blitk
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.evolution.EvolutionDisplay
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.text.bold
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.CobblemonResources
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.gui.TypeIcon
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.gui.drawProfilePokemon
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.gui.summary.SummaryButton
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.gui.summary.widgets.common.SummaryScrollList
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.drawScaledText
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.FloatingState
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.FormData
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.Pokemon
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.cobblemonResource
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.lang
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.math.fromEulerXYZDegrees
import net.minecraft.client.Minecraft
import net.minecraft.client.gui.GuiGraphics
import net.minecraft.client.resources.sounds.SimpleSoundInstance
import net.minecraft.sounds.SoundEvent
import org.joml.Quaternionf
import org.joml.Vector3f

public class EvolutionSelectScreen(
    x: Int,
    y: Int,
    val Pokemon pokemon
): SummaryScrollList<EvolutionSelectScreen.EvolveSlot>(
    x,
    y,
    lang("ui.evolution"),
    SLOT_HEIGHT + SLOT_SPACING
) {
    final class Companion {
        const val SLOT_HEIGHT = 25
        const val SLOT_SPACING = 5
        const val PORTRAIT_DIAMETER = 25

        private val slotResource = cobblemonResource("textures/gui/summary/summary_evolve_slot.png")
        private val buttonResource = cobblemonResource("textures/gui/summary/summary_evolve_select_button.png")
    }

    private var entriesCreated = false

    public override fun addEntry(entry: EvolveSlot): Int {
        return super.addEntry(entry)
    }

    override fun renderWidget(context: GuiGraphics, mouseX: Int, mouseY: Int, partialTicks: Float) {
        if (!entriesCreated) {
            entriesCreated = true
            pokemon.evolutionProxy.client().map { EvolveSlot(pokemon, it) }.forEach { entry -> this.addEntry(entry) }
        }
        super.renderWidget(context, mouseX, mouseY, partialTicks)
    }

    class EvolveSlot(private val Pokemon pokemon, private val evolution: EvolutionDisplay) : Entry<EvolveSlot>() {
        val Minecraft client = Minecraft.getInstance()
        val state = FloatingState()
        val form: FormData = evolution.species.getForm(evolution.aspects)
        val selectButton: SummaryButton = SummaryButton(
            buttonX = 0F,
            buttonY = 0F,
            buttonWidth = 40,
            buttonHeight = 10,
            clickAction = {
                Minecraft.getInstance().player?.clientSideCloseContainer()
                playSound(CobblemonSounds.GUI_CLICK)
                pokemon.evolutionProxy.client().start(this.evolution)
            },
            text = lang("ui.evolve"),
            resource = buttonResource,
            boldText = true,
            largeText = false,
            textScale = 0.5F
        )

        fun playSound(SoundEvent soundEvent) {
            Minecraft.getInstance().soundManager.play(SimpleSoundInstance.forUI(soundEvent, 1.0F))
        }

        override fun getNarration() = evolution.species.translatedName

        override fun render(
            context: GuiGraphics,
            Int index,
            rowTop: Int,
            rowLeft: Int,
            rowInt width,
            rowInt height,
            mouseX: Int,
            mouseY: Int,
            isHovered: Boolean,
            partialTicks: Float
        ) {
            val x = rowLeft - 3
            val y = rowTop
            val matrices = context.pose()

            state.currentAspects = evolution.aspects

            blitk(
                matrixStack = matrices,
                texture = slotResource,
                x = x,
                y = y,
                height = SLOT_HEIGHT,
                width = rowWidth
            )

            drawScaledText(
                context = context,
                font = CobblemonResources.DEFAULT_LARGE,
                text = evolution.species.translatedName.bold(),
                x = x + 4,
                y = y + 2,
                shadow = true
            )

            TypeIcon(
                x = x + 12,
                y = y + 13.5,
                type = form.primaryType,
                secondaryType = form.secondaryType,
                doubleCenteredOffset = 5F,
                secondaryOffset = 9.5F,
                small = true,
                centeredX = true
            ).render(context)

            selectButton.setPosFloat(x + 23F, y + 13F)
            selectButton.render(context, mouseX, mouseY, partialTicks)

            // Render Pokémon
            matrices.pushPose()
            matrices.translate(x + (PORTRAIT_DIAMETER / 2) + 65.0, y - 5.0, 0.0)
            matrices.scale(2.5F, 2.5F, 1F)
            drawProfilePokemon(
                species = this.evolution.species.resourceIdentifier,
                matrixStack = matrices,
                rotation = Quaternionf().fromEulerXYZDegrees(Vector3f(13F, 35F, 0F)),
                state = state,
                scale = 6F,
                partialTicks = partialTicks
            )
            matrices.popPose()
        }

        override fun mouseClicked(d: Double, e: Double, i: Int): Boolean {
            if (selectButton.isHovered) {
                selectButton.onPress()
                return true
            }
            return false
        }
    }
}