package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.gui.summary.featurerenderers

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.feature.SynchronizedSpeciesFeature
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.Pokemon;
import net.minecraft.client.gui.GuiGraphics

public interface SummarySpeciesFeatureRenderer<T extends SynchronizedSpeciesFeature> {
   public val name: String

   public abstract fun render(drawContext: GuiGraphics, x: Float, y: Float, pokemon: Pokemon, feature: Any) {
   }

   public open fun render(drawContext: GuiGraphics, x: Float, y: Float, pokemon: Pokemon): Boolean {
   }

   // $VF: Class flags could not be determined
   internal class DefaultImpls {
      @JvmStatic
      fun <T extends SynchronizedSpeciesFeature> render(
         `$this`: SummarySpeciesFeatureRenderer<T>, drawContext: GuiGraphics, x: Float, y: Float, pokemon: Pokemon
      ): Boolean {
         val var10000: SynchronizedSpeciesFeature = pokemon.getFeature(`$this`.getName());
         if (var10000 == null) {
            return false;
         } else {
            `$this`.render(drawContext, x, y, pokemon, var10000);
            return true;
         }
      }
   }
}
