package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.gui.dialogue

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.entity.EntitySideDelegate
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.gui.GuiUtilsKt
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.entity.PokemonClientDelegate
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.PoseableEntityState
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.entity.Poseable
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.Species
import com.mojang.blaze3d.vertex.PoseStack
import net.minecraft.client.gui.GuiGraphics

public class ReferenceRenderableFace(entity: Poseable) : RenderableFace {
   public final val state: PoseableEntityState<*>

   init {
      val var10001: EntitySideDelegate = entity.getDelegate();
      this.state = var10001 as PoseableEntityState<?>;
   }

   public override fun render(drawContext: GuiGraphics, partialTicks: Float) {
      val state: PoseableEntityState = this.state;
      if (this.state is PokemonClientDelegate) {
         val var10000: Species = (this.state as PokemonClientDelegate).getCurrentEntity().getPokemon().getSpecies();
         val var10001: java.util.Set = (state as PokemonClientDelegate).getCurrentEntity().getPokemon().getAspects();
         val var10002: PoseStack = drawContext.m_280168_();
         GuiUtilsKt.drawPortraitPokemon$default(var10000, var10001, var10002, 0.0F, false, state, 0.0F, 24, null);
      }
   }
}
