package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.item

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.PoseableEntityModel
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.pokemon.PokemonPoseableModel
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.repository.PokemonModelRepository
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.entity.PoseType
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.item.PokemonItem
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.Species
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.math.QuaternionUtilsKt
import com.mojang.blaze3d.platform.Lighting
import com.mojang.blaze3d.vertex.PoseStack
import com.mojang.blaze3d.vertex.VertexConsumer
import kotlin.jvm.functions.Function0
import net.minecraft.client.renderer.LightTexture
import net.minecraft.client.renderer.MultiBufferSource
import net.minecraft.client.renderer.RenderType
import net.minecraft.client.renderer.texture.OverlayTexture
import net.minecraft.world.item.Item
import net.minecraft.world.item.ItemDisplayContext
import net.minecraft.world.item.ItemStack
import org.joml.Quaternionf
import org.joml.Vector3f
import org.joml.Vector4f

public class PokemonItemRenderer : CobblemonBuiltinItemRenderer {
   public override fun render(stack: ItemStack, mode: ItemDisplayContext, matrices: PoseStack, vertexConsumers: MultiBufferSource, light: Int, overlay: Int) {
      val species: Item = stack.m_41720_();
      val var10000: PokemonItem = species as? PokemonItem;
      if ((species as? PokemonItem) != null) {
         val var19: Pair = var10000.getSpeciesAndAspects(stack);
         if (var19 != null) {
            val var18: Species = var19.component1() as Species;
            val aspects: java.util.Set = var19.component2() as java.util.Set;
            matrices.m_85836_();
            val model: PokemonPoseableModel = PokemonModelRepository.INSTANCE.getPoser(var18.getResourceIdentifier(), aspects);
            val renderLayer: RenderType = model.m_103119_(PokemonModelRepository.INSTANCE.getTexture(var18.getResourceIdentifier(), aspects, 0.0F));
            val var20: Any = positions.get(mode);
            val transformations: PokemonItemRenderer.Transformations = var20 as PokemonItemRenderer.Transformations;
            Lighting.m_84931_();
            matrices.m_85841_(
               transformations.getScale().getX().floatValue(), transformations.getScale().getY().floatValue(), transformations.getScale().getZ().floatValue()
            );
            matrices.m_85837_(
               transformations.getTranslation().getX().doubleValue(),
               transformations.getTranslation().getY().doubleValue(),
               transformations.getTranslation().getZ().doubleValue()
            );
            PoseableEntityModel.setupAnimStateless$default(model, PoseType.PROFILE, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 62, null);
            matrices.m_85837_(model.getProfileTranslation().f_82479_, model.getProfileTranslation().f_82480_, model.getProfileTranslation().f_82481_ - 4.0);
            matrices.m_85841_(model.getProfileScale(), model.getProfileScale(), 0.15F);
            val rotation: Quaternionf = QuaternionUtilsKt.fromEulerXYZDegrees(
               new Quaternionf(),
               new Vector3f(
                  transformations.getRotation().getX().floatValue(),
                  transformations.getRotation().getY().floatValue(),
                  transformations.getRotation().getZ().floatValue()
               )
            );
            matrices.m_252781_(rotation);
            rotation.conjugate();
            val var21: VertexConsumer = vertexConsumers.m_6299_(renderLayer);
            val vertexConsumer: VertexConsumer = var21;
            matrices.m_85836_();
            val packedLight: Int = if (mode === ItemDisplayContext.GUI) LightTexture.m_109885_(13, 13) else light;
            val tint: Vector4f = var10000.tint(stack);
            model.withLayerContext(
               vertexConsumers,
               null,
               PokemonModelRepository.INSTANCE.getLayers(var18.getResourceIdentifier(), aspects),
               (
                  new Function0<Unit>(model, matrices, vertexConsumer, packedLight, tint) {
                     {
                        super(0);
                        this.$model = `$model`;
                        this.$matrices = `$matrices`;
                        this.$vertexConsumer = `$vertexConsumer`;
                        this.$packedLight = `$packedLight`;
                        this.$tint = `$tint`;
                     }

                     public final void invoke() {
                        this.$model
                           .m_7695_(
                              this.$matrices,
                              this.$vertexConsumer,
                              this.$packedLight,
                              OverlayTexture.f_118083_,
                              this.$tint.x,
                              this.$tint.y,
                              this.$tint.z,
                              this.$tint.w
                           );
                     }
                  }
               ) as () -> Unit
            );
            model.setDefault();
            matrices.m_85849_();
            matrices.m_85849_();
            Lighting.m_84930_();
         }
      }
   }

   @JvmStatic
   fun {
      positions.put(
         ItemDisplayContext.GUI,
         new PokemonItemRenderer().new Transformations(
            new PokemonItemRenderer(),
            new PokemonItemRenderer().new Transformation<>(new PokemonItemRenderer(), 1.0, -1.9, -0.5),
            new PokemonItemRenderer().new Transformation<>(new PokemonItemRenderer(), 0.5F, -0.5F, -0.5F),
            new PokemonItemRenderer().new Transformation<>(new PokemonItemRenderer(), 0.0F, 35.0F, 0.0F)
         )
      );
      positions.put(
         ItemDisplayContext.FIXED,
         new PokemonItemRenderer().new Transformations(
            new PokemonItemRenderer(),
            new PokemonItemRenderer().new Transformation<>(new PokemonItemRenderer(), 1.0, -2.0, 3.0),
            new PokemonItemRenderer().new Transformation<>(new PokemonItemRenderer(), 0.5F, -0.5F, -0.5F),
            new PokemonItemRenderer().new Transformation<>(new PokemonItemRenderer(), 0.0F, -145.0F, 0.0F)
         )
      );
      positions.put(
         ItemDisplayContext.FIRST_PERSON_RIGHT_HAND,
         new PokemonItemRenderer().new Transformations(
            new PokemonItemRenderer(),
            new PokemonItemRenderer().new Transformation<>(new PokemonItemRenderer(), 2.75, -1.2, 5.0),
            new PokemonItemRenderer().new Transformation<>(new PokemonItemRenderer(), 0.5F, -0.5F, -0.5F),
            new PokemonItemRenderer().new Transformation<>(new PokemonItemRenderer(), 0.0F, 35.0F, 0.0F)
         )
      );
      positions.put(
         ItemDisplayContext.FIRST_PERSON_LEFT_HAND,
         new PokemonItemRenderer().new Transformations(
            new PokemonItemRenderer(),
            new PokemonItemRenderer().new Transformation<>(new PokemonItemRenderer(), -0.75, -1.2, 5.0),
            new PokemonItemRenderer().new Transformation<>(new PokemonItemRenderer(), 0.5F, -0.5F, -0.5F),
            new PokemonItemRenderer().new Transformation<>(new PokemonItemRenderer(), 0.0F, -35.0F, 0.0F)
         )
      );
      positions.put(
         ItemDisplayContext.THIRD_PERSON_RIGHT_HAND,
         new PokemonItemRenderer().new Transformations(
            new PokemonItemRenderer(),
            new PokemonItemRenderer().new Transformation<>(new PokemonItemRenderer(), 1.0, -2.6, 2.75),
            new PokemonItemRenderer().new Transformation<>(new PokemonItemRenderer(), 0.5F, -0.5F, -0.5F),
            new PokemonItemRenderer().new Transformation<>(new PokemonItemRenderer(), 0.0F, 35.0F, 0.0F)
         )
      );
      positions.put(
         ItemDisplayContext.THIRD_PERSON_LEFT_HAND,
         new PokemonItemRenderer().new Transformations(
            new PokemonItemRenderer(),
            new PokemonItemRenderer().new Transformation<>(new PokemonItemRenderer(), 1.0, -2.6, 2.75),
            new PokemonItemRenderer().new Transformation<>(new PokemonItemRenderer(), 0.5F, -0.5F, -0.5F),
            new PokemonItemRenderer().new Transformation<>(new PokemonItemRenderer(), 0.0F, -35.0F, 0.0F)
         )
      );
      positions.put(
         ItemDisplayContext.GROUND,
         new PokemonItemRenderer().new Transformations(
            new PokemonItemRenderer(),
            new PokemonItemRenderer().new Transformation<>(new PokemonItemRenderer(), 1.0, -2.6, 3.0),
            new PokemonItemRenderer().new Transformation<>(new PokemonItemRenderer(), 0.5F, -0.5F, -0.5F),
            new PokemonItemRenderer().new Transformation<>(new PokemonItemRenderer(), 0.0F, 35.0F, 0.0F)
         )
      );
      positions.put(
         ItemDisplayContext.HEAD,
         new PokemonItemRenderer().new Transformations(
            new PokemonItemRenderer(),
            new PokemonItemRenderer().new Transformation<>(new PokemonItemRenderer(), 1.0, -3.5, 3.0),
            new PokemonItemRenderer().new Transformation<>(new PokemonItemRenderer(), 0.5F, -0.5F, -0.5F),
            new PokemonItemRenderer().new Transformation<>(new PokemonItemRenderer(), 0.0F, 215.0F, 0.0F)
         )
      );
      positions.put(
         ItemDisplayContext.NONE,
         new PokemonItemRenderer().new Transformations(
            new PokemonItemRenderer(),
            new PokemonItemRenderer().new Transformation<>(new PokemonItemRenderer(), 0.0, 0.0, 0.0),
            new PokemonItemRenderer().new Transformation<>(new PokemonItemRenderer(), 0.5F, -0.5F, -0.5F),
            new PokemonItemRenderer().new Transformation<>(new PokemonItemRenderer(), 0.0F, 0.0F, 0.0F)
         )
      );
   }

   public companion object {
      public final val positions: MutableMap<ItemDisplayContext, bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.item.PokemonItemRenderer.Transformations>
   }

   public inner class Transformation<T>(x: Any, y: Any, z: Any) {
      public final val x: Any
      public final val y: Any
      public final val z: Any

      init {
         this.this$0 = `this$0`;
         this.x = (T)x;
         this.y = (T)y;
         this.z = (T)z;
      }
   }

   public inner class Transformations(translation: bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.item.PokemonItemRenderer.Transformation<Double>,
      scale: bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.item.PokemonItemRenderer.Transformation<Float>,
      rotation: bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.item.PokemonItemRenderer.Transformation<Float>
   ) {
      public final val rotation: bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.item.PokemonItemRenderer.Transformation<Float>
      public final val scale: bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.item.PokemonItemRenderer.Transformation<Float>
      public final val translation: bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.item.PokemonItemRenderer.Transformation<Double>

      init {
         this.this$0 = `this$0`;
         this.translation = translation;
         this.scale = scale;
         this.rotation = rotation;
      }
   }
}
