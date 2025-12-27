package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.pokemon

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.entity.PokemonSideDelegate
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.entity.PokemonClientDelegate
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.PoseableEntityModel
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.PoseableEntityState
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.animation.StatefulAnimation
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.animation.StatelessAnimation
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.frame.ModelFrame
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.pose.ModelPartTransformation
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.repository.PokemonModelRepository
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.repository.RenderContext
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.entity.PoseType
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.entity.pokemon.PokemonEntity
import kotlin.jvm.internal.SourceDebugExtension
import net.minecraft.client.renderer.texture.OverlayTexture
import net.minecraft.world.entity.Entity
import net.minecraft.world.phys.Vec3

@SourceDebugExtension(["SMAP\nPokemonPoseableModel.kt\nKotlin\n*S Kotlin\n*F\n+ 1 PokemonPoseableModel.kt\ncom/cobblemon/mod/common/client/render/models/blockbench/pokemon/PokemonPoseableModel\n+ 2 ArrayIntrinsics.kt\nkotlin/ArrayIntrinsicsKt\n+ 3 fake.kt\nkotlin/jvm/internal/FakeKt\n*L\n1#1,106:1\n26#2:107\n1#3:108\n*S KotlinDebug\n*F\n+ 1 PokemonPoseableModel.kt\ncom/cobblemon/mod/common/client/render/models/blockbench/pokemon/PokemonPoseableModel\n*L\n41#1:107\n*E\n"])
public abstract class PokemonPoseableModel : PoseableEntityModel<PokemonEntity> {
   public open val cryAnimation: CryProvider
   public open val isForLivingEntityRenderer: Boolean = true
   public open var portraitScale: Float = 1.0F
   public open var portraitTranslation: Vec3
   public open var profileScale: Float
   public open var profileTranslation: Vec3

   open fun PokemonPoseableModel() {
      super(null, 1, null);
      var var10001: Vec3 = Vec3.f_82478_;
      this.portraitTranslation = var10001;
      this.profileScale = 1.0F;
      var10001 = Vec3.f_82478_;
      this.profileTranslation = var10001;
      this.cryAnimation = PokemonPoseableModel::cryAnimation$lambda$0;
   }

   public open fun getState(entity: PokemonEntity): PokemonClientDelegate {
      val var10000: PokemonSideDelegate = entity.getDelegate();
      return var10000 as PokemonClientDelegate;
   }

   public fun <F : ModelFrame> registerShoulderPoses(
      transformTicks: Int = 30,
      idleAnimations: Array<StatelessAnimation<PokemonEntity, out Any>>,
      transformedParts: Array<ModelPartTransformation> = new ModelPartTransformation[0]
   ) {
      PoseableEntityModel.registerPose$default(
         this, PoseType.SHOULDER_LEFT, null, transformTicks, null, null, idleAnimations, transformedParts, null, 154, null
      );
      PoseableEntityModel.registerPose$default(
         this, PoseType.SHOULDER_RIGHT, null, transformTicks, null, null, idleAnimations, transformedParts, null, 154, null
      );
   }

   public open fun getFaintAnimation(pokemonEntity: PokemonEntity, state: PoseableEntityState<PokemonEntity>): StatefulAnimation<PokemonEntity, ModelFrame>? {
      return null;
   }

   public open fun getEatAnimation(pokemonEntity: PokemonEntity, state: PoseableEntityState<PokemonEntity>): StatefulAnimation<PokemonEntity, ModelFrame>? {
      return null;
   }

   public override fun getOverlayTexture(entity: Entity?): Int {
      return if (entity is PokemonEntity)
         OverlayTexture.m_118093_(OverlayTexture.m_118088_(0.0F), OverlayTexture.m_118096_((entity as PokemonEntity).f_20916_ > 0))
         else
         OverlayTexture.f_118083_;
   }

   public open fun setupEntityTypeContext(entity: PokemonEntity?) {
      if (entity != null) {
         this.getContext().put(RenderContext.Companion.getSCALE(), entity.getPokemon().getForm().getBaseScale());
         this.getContext().put(RenderContext.Companion.getSPECIES(), entity.getPokemon().getSpecies().getResourceIdentifier());
         this.getContext().put(RenderContext.Companion.getASPECTS(), entity.getPokemon().getAspects());
         this.getContext()
            .put(
               RenderContext.Companion.getTEXTURE(),
               PokemonModelRepository.INSTANCE.getTexture(entity.getPokemon().getSpecies().getResourceIdentifier(), entity.getPokemon().getAspects(), 0.0F)
            );
      }
   }

   @JvmStatic
   fun `cryAnimation$lambda$0`(var0: PokemonEntity, var1: PoseableEntityState): StatefulAnimation {
      return null;
   }
}
