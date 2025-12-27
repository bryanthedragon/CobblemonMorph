package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.gui.dialogue

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.Cobblemon
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.gui.GuiUtilsKt
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.PokemonSpecies
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.PoseableEntityState
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.pokemon.PokemonFloatingState
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.Species
import com.mojang.blaze3d.vertex.PoseStack
import net.minecraft.client.gui.GuiGraphics
import net.minecraft.resources.ResourceLocation

public class ArtificialRenderableFace(modelType: String, identifier: ResourceLocation, aspects: Set<String>) : RenderableFace {
   public final val aspects: Set<String>
   public final val identifier: ResourceLocation
   public final val species: Species
   public final val state: PoseableEntityState<*>

   init {
      this.identifier = identifier;
      this.aspects = aspects;
      var var10000: ArtificialRenderableFace = this;
      var var10001: Species = PokemonSpecies.INSTANCE.getByIdentifier(this.identifier);
      if (var10001 == null) {
         Cobblemon.INSTANCE.getLOGGER().error("Unable to find species for ${this.identifier} for a dialogue face. Defaulting to first species.");
         var10001 = CollectionsKt.first(PokemonSpecies.INSTANCE.getSpecies()) as Species;
         var10000 = this;
      }

      var10000.species = var10001;
      if (modelType == "pokemon") {
         this.state = new PokemonFloatingState();
      } else {
         throw new IllegalArgumentException("Unknown model type: $modelType");
      }
   }

   public override fun render(drawContext: GuiGraphics, partialTicks: Float) {
      val state: PoseableEntityState = this.state;
      if (this.state is PokemonFloatingState) {
         val var10000: Species = this.species;
         val var10001: java.util.Set = this.aspects;
         val var10002: PoseStack = drawContext.m_280168_();
         GuiUtilsKt.drawPortraitPokemon$default(var10000, var10001, var10002, 0.0F, false, state, partialTicks, 24, null);
      }
   }
}
