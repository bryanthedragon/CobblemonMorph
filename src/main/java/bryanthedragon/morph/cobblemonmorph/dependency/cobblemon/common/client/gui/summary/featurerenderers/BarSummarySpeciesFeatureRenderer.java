package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.gui.summary.featurerenderers

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.gui.GuiUtilsKt
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.feature.IntSpeciesFeature
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.text.TextKt
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.CobblemonResources
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.RenderHelperKt
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.Pokemon;
import com.mojang.blaze3d.vertex.PoseStack
import net.minecraft.client.gui.GuiGraphics
import net.minecraft.network.chat.MutableComponent
import net.minecraft.resources.ResourceLocation
import net.minecraft.util.Mth
import net.minecraft.world.phys.Vec3

public class BarSummarySpeciesFeatureRenderer(name: String,
      displayName: MutableComponent,
      min: Int,
      max: Int,
      colour: Vec3,
      underlay: ResourceLocation,
      overlay: ResourceLocation,
      pokemon: Pokemon
   ) :
   SummarySpeciesFeatureRenderer<IntSpeciesFeature> {
   public final val colour: Vec3
   public final val displayName: MutableComponent
   public final val max: Int
   public final val min: Int
   public open val name: String
   public final val overlay: ResourceLocation
   public final val pokemon: Pokemon
   public final val underlay: ResourceLocation

   init {
      this.name = name;
      this.displayName = displayName;
      this.min = min;
      this.max = max;
      this.colour = colour;
      this.underlay = underlay;
      this.overlay = overlay;
      this.pokemon = pokemon;
   }

   public open fun render(drawContext: GuiGraphics, x: Float, y: Float, pokemon: Pokemon, feature: IntSpeciesFeature) {
      val value: Int = feature.getValue();
      val barRatio: Float = (float)(value - this.min) / (this.max - this.min);
      val barWidth: Int = Mth.m_14167_((float)(value - this.min) / (float)(this.max - this.min) * (float)108);
      var var10000: PoseStack = drawContext.m_280168_();
      GuiUtilsKt.blitk$default(var10000, this.underlay, x, y, 28, 124, null, null, null, null, null, null, null, null, null, false, 0.0F, 131008, null);
      val red: Double = this.colour.f_82479_ / 255.0F;
      val green: Double = this.colour.f_82480_ / 255.0F;
      val blue: Double = this.colour.f_82481_ / 255.0F;
      var10000 = drawContext.m_280168_();
      GuiUtilsKt.blitk$default(
         var10000,
         CobblemonResources.INSTANCE.getWHITE(),
         x + (float)8,
         y + (float)16,
         10,
         barWidth,
         null,
         null,
         null,
         null,
         null,
         red,
         green,
         blue,
         null,
         false,
         0.0F,
         116672,
         null
      );
      var10000 = drawContext.m_280168_();
      GuiUtilsKt.blitk$default(
         var10000, this.overlay, x / 0.5F, (y + (float)16) / 0.5F, 20, 248, null, null, null, null, null, null, null, null, null, false, 0.5F, 65472, null
      );
      RenderHelperKt.drawScaledText$default(
         drawContext,
         CobblemonResources.INSTANCE.getDEFAULT_LARGE(),
         TextKt.bold(this.displayName),
         x + (float)62,
         (double)y + 2.5,
         0.0F,
         null,
         0,
         0,
         true,
         true,
         null,
         null,
         6624,
         null
      );
      RenderHelperKt.drawScaledText$default(
         drawContext, null, TextKt.text(java.lang.String.valueOf(value)), x + (float)11, y + (float)6, 0.5F, null, 0, 0, true, false, null, null, 7618, null
      );
      RenderHelperKt.drawScaledText$default(
         drawContext,
         null,
         TextKt.text("${Mth.m_14143_(barRatio * (float)100)}%"),
         x + (float)113,
         y + (float)6,
         0.5F,
         null,
         0,
         0,
         true,
         false,
         null,
         null,
         7618,
         null
      );
   }

   override fun render(drawContext: GuiGraphics, x: Float, y: Float, pokemon: Pokemon): Boolean {
      return SummarySpeciesFeatureRenderer.DefaultImpls.render(this, drawContext, x, y, pokemon);
   }
}
