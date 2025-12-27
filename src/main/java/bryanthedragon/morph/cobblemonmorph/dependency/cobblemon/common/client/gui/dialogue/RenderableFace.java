package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.gui.dialogue

import net.minecraft.client.gui.GuiGraphics

public sealed interface RenderableFace {
   public abstract fun render(drawContext: GuiGraphics, partialTicks: Float) {
   }
}
