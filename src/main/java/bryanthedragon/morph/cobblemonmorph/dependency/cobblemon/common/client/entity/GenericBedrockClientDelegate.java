package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.entity

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.entity.EntitySideDelegate
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.scheduling.SchedulingTracker
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.PoseableEntityModel
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.PoseableEntityState
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.pose.Pose
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.repository.GenericBedrockEntityModelRepository
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.repository.RenderContext
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.entity.PoseType
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.entity.generic.GenericBedrockEntity
import kotlin.jvm.functions.Function0
import kotlin.jvm.internal.SourceDebugExtension
import net.minecraft.network.syncher.EntityDataAccessor
import net.minecraft.world.phys.Vec3

@SourceDebugExtension(["SMAP\nGenericBedrockClientDelegate.kt\nKotlin\n*S Kotlin\n*F\n+ 1 GenericBedrockClientDelegate.kt\ncom/cobblemon/mod/common/client/entity/GenericBedrockClientDelegate\n+ 2 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n*L\n1#1,50:1\n288#2,2:51\n*S KotlinDebug\n*F\n+ 1 GenericBedrockClientDelegate.kt\ncom/cobblemon/mod/common/client/entity/GenericBedrockClientDelegate\n*L\n35#1:51,2\n*E\n"])
public class GenericBedrockClientDelegate : PoseableEntityState<GenericBedrockEntity>, EntitySideDelegate<GenericBedrockEntity> {
   public final lateinit var currentEntity: GenericBedrockEntity

   public open val schedulingTracker: SchedulingTracker
      public open get() {
         return this.getEntity().getSchedulingTracker();
      }


   public open fun getEntity(): GenericBedrockEntity {
      return this.getCurrentEntity();
   }

   public open fun initialize(entity: GenericBedrockEntity) {
      EntitySideDelegate.DefaultImpls.initialize(this, entity);
      this.setCurrentEntity(entity);
      this.setAge(entity.f_19797_);
      this.setCurrentModel(GenericBedrockEntityModelRepository.INSTANCE.getPoser(entity.getCategory(), entity.getAspects()));
      var var10000: PoseableEntityModel = this.getCurrentModel();
      var10000.getContext().put(RenderContext.Companion.getENTITY(), entity);
      var10000 = this.getCurrentModel();
      var10000.updateLocators(this as PoseableEntityState<GenericBedrockEntity>);
      val var10001: Vec3 = entity.m_20182_();
      this.updateLocatorPosition(var10001);
      val currentPoseType: PoseType = entity.getCurrentPoseType();
      var10000 = this.getCurrentModel();
      val var7: java.util.Iterator = var10000.getPoses().values().iterator();

      while (true) {
         if (!var7.hasNext()) {
            var10000 = null;
            break;
         }

         val `element$iv`: Any = var7.next();
         if ((`element$iv` as Pose).getPoseTypes().contains(currentPoseType)
            && ((`element$iv` as Pose).getCondition() == null || (`element$iv` as Pose).getCondition().invoke(entity) as java.lang.Boolean)) {
            var10000 = (PoseableEntityModel)`element$iv`;
            break;
         }
      }

      val pose: Pose = var10000 as Pose;
      if (var10000 as Pose != null) {
         this.doLater((new Function0<Unit>(this, pose) {
            {
               super(0);
               this.this$0 = `$receiver`;
               this.$pose = `$pose`;
            }

            public final void invoke() {
               this.this$0.setPose(this.$pose.getPoseName());
            }
         }) as () -> Unit);
      }
   }

   public open fun tick(entity: GenericBedrockEntity) {
      EntitySideDelegate.DefaultImpls.tick(this, entity);
      val var10001: Vec3 = entity.m_20182_();
      this.updateLocatorPosition(var10001);
      this.incrementAge(entity);
   }

   public override fun updatePartialTicks(partialTicks: Float) {
      this.setCurrentPartialTicks(partialTicks);
   }

   override fun onTrackedDataSet(data: EntityDataAccessor<?>) {
      EntitySideDelegate.DefaultImpls.onTrackedDataSet(this, data);
   }
}
