@file:SourceDebugExtension(["SMAP\nPokemonGuiUtils.kt\nKotlin\n*S Kotlin\n*F\n+ 1 PokemonGuiUtils.kt\ncom/cobblemon/mod/common/client/gui/PokemonGuiUtilsKt\n+ 2 fake.kt\nkotlin/jvm/internal/FakeKt\n*L\n1#1,102:1\n1#2:103\n*E\n"])

package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.gui

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.PokemonSpecies
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.PoseableEntityModel
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.PoseableEntityState
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.pokemon.PokemonPoseableModel
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.pose.Pose
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.repository.PokemonModelRepository
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.repository.RenderContext
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.entity.PoseType
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.entity.pokemon.PokemonEntity
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.RenderablePokemon
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.Species
import com.mojang.blaze3d.platform.Lighting
import com.mojang.blaze3d.systems.RenderSystem
import com.mojang.blaze3d.vertex.PoseStack
import com.mojang.blaze3d.vertex.VertexConsumer
import kotlin.jvm.functions.Function0
import kotlin.jvm.internal.SourceDebugExtension
import net.minecraft.client.Minecraft
import net.minecraft.client.renderer.LightTexture
import net.minecraft.client.renderer.MultiBufferSource
import net.minecraft.client.renderer.RenderType
import net.minecraft.client.renderer.MultiBufferSource.BufferSource
import net.minecraft.client.renderer.entity.EntityRenderDispatcher
import net.minecraft.client.renderer.texture.OverlayTexture
import net.minecraft.resources.ResourceLocation
import org.joml.Quaternionf
import org.joml.Vector3f

public fun drawProfilePokemon(
   renderablePokemon: RenderablePokemon,
   matrixStack: PoseStack,
   rotation: Quaternionf,
   state: PoseableEntityState<PokemonEntity>?,
   partialTicks: Float,
   scale: Float = 20.0F
) {
   drawProfilePokemon(renderablePokemon.getSpecies().getResourceIdentifier(), renderablePokemon.getAspects(), matrixStack, rotation, state, partialTicks, scale);
}

@JvmSynthetic
fun `drawProfilePokemon$default`(
   var0: RenderablePokemon, var1: PoseStack, var2: Quaternionf, var3: PoseableEntityState, var4: Float, var5: Float, var6: Int, var7: Any
) {
   if ((var6 and 32) != 0) {
      var5 = 20.0F;
   }

   drawProfilePokemon(var0, var1, var2, var3, var4, var5);
}

public fun drawProfilePokemon(
   species: ResourceLocation,
   aspects: Set<String>,
   matrixStack: PoseStack,
   rotation: Quaternionf,
   state: PoseableEntityState<PokemonEntity>?,
   partialTicks: Float,
   scale: Float = 20.0F
) {
   val model: PokemonPoseableModel = PokemonModelRepository.INSTANCE.getPoser(species, aspects);
   val texture: ResourceLocation = PokemonModelRepository.INSTANCE.getTexture(species, aspects, if (state != null) state.getAnimationSeconds() else 0.0F);
   val context: RenderContext = new RenderContext();
   context.put(RenderContext.Companion.getTEXTURE(), PokemonModelRepository.INSTANCE.getTextureNoSubstitute(species, aspects, 0.0F));
   val var10001: RenderContext.Key = RenderContext.Companion.getSCALE();
   val var10002: Species = PokemonSpecies.INSTANCE.getByIdentifier(species);
   context.put(var10001, var10002.getForm(aspects).getBaseScale());
   context.put(RenderContext.Companion.getSPECIES(), species);
   context.put(RenderContext.Companion.getASPECTS(), aspects);
   val renderType: RenderType = model.m_103119_(texture);
   RenderSystem.applyModelViewMatrix();
   matrixStack.m_85841_(scale, scale, -scale);
   if (state != null) {
      val var10000: Pose = model.getPose(PoseType.PROFILE);
      if (var10000 != null) {
         state.setPose(var10000.getPoseName());
      }

      state.setTimeEnteredPose(0.0F);
      state.updatePartialTicks(partialTicks);
      model.setupAnimStateful(null, state, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F);
   } else {
      PoseableEntityModel.setupAnimStateless$default(model, PoseType.PROFILE, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 62, null);
   }

   matrixStack.m_85837_(model.getProfileTranslation().f_82479_, model.getProfileTranslation().f_82480_, model.getProfileTranslation().f_82481_ - 4.0);
   matrixStack.m_85841_(model.getProfileScale(), model.getProfileScale(), (float)1 / model.getProfileScale());
   matrixStack.m_252781_(rotation);
   Lighting.m_166384_();
   val var17: EntityRenderDispatcher = Minecraft.m_91087_().m_91290_();
   rotation.conjugate();
   var17.m_252923_(rotation);
   var17.m_114468_(true);
   val var18: BufferSource = Minecraft.m_91087_().m_91269_().m_110104_();
   val var19: VertexConsumer = var18.m_6299_(renderType);
   RenderSystem.setShaderLights(new Vector3f(-1.0F, 1.0F, 1.0F), new Vector3f(1.3F, -1.0F, 1.0F));
   val packedLight: Int = LightTexture.m_109885_(11, 7);
   model.withLayerContext(
      var18 as MultiBufferSource,
      state,
      PokemonModelRepository.INSTANCE.getLayers(species, aspects),
      (new Function0<Unit>(model, context, matrixStack, var19, packedLight, var18) {
         {
            super(0);
            this.$model = `$model`;
            this.$context = `$context`;
            this.$matrixStack = `$matrixStack`;
            this.$buffer = `$buffer`;
            this.$packedLight = `$packedLight`;
            this.$bufferSource = `$bufferSource`;
         }

         public final void invoke() {
            val var10000: PokemonPoseableModel = this.$model;
            val var10001: RenderContext = this.$context;
            val var10002: PoseStack = this.$matrixStack;
            val var10003: VertexConsumer = this.$buffer;
            var10000.render(var10001, var10002, var10003, this.$packedLight, OverlayTexture.f_118083_, 1.0F, 1.0F, 1.0F, 1.0F);
            this.$bufferSource.m_109911_();
         }
      }) as () -> Unit
   );
   model.setDefault();
   var17.m_114468_(true);
   Lighting.m_84931_();
}

@JvmSynthetic
fun `drawProfilePokemon$default`(
   var0: ResourceLocation, var1: java.util.Set, var2: PoseStack, var3: Quaternionf, var4: PoseableEntityState, var5: Float, var6: Float, var7: Int, var8: Any
) {
   if ((var7 and 64) != 0) {
      var6 = 20.0F;
   }

   drawProfilePokemon(var0, var1, var2, var3, var4, var5, var6);
}
