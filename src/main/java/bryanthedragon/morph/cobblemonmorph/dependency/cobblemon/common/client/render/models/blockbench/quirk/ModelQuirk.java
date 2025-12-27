package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.quirk

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.PoseableEntityModel
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.PoseableEntityState
import kotlin.jvm.internal.SourceDebugExtension
import net.minecraft.world.entity.Entity

@SourceDebugExtension(["SMAP\nModelQuirk.kt\nKotlin\n*S Kotlin\n*F\n+ 1 ModelQuirk.kt\ncom/cobblemon/mod/common/client/render/models/blockbench/quirk/ModelQuirk\n+ 2 Maps.kt\nkotlin/collections/MapsKt__MapsKt\n*L\n1#1,26:1\n361#2,7:27\n*S KotlinDebug\n*F\n+ 1 ModelQuirk.kt\ncom/cobblemon/mod/common/client/render/models/blockbench/quirk/ModelQuirk\n*L\n24#1:27,7\n*E\n"])
public abstract class ModelQuirk<T extends Entity, D extends QuirkData<T>> {
   public abstract fun createData(): Any {
   }

   protected abstract fun tick(state: PoseableEntityState<Any>, data: Any) {
   }

   public fun tick(
      entity: Any?,
      model: PoseableEntityModel<Any>,
      state: PoseableEntityState<Any>,
      limbSwing: Float,
      limbSwingAmount: Float,
      ageInTicks: Float,
      headYaw: Float,
      headPitch: Float,
      intensity: Float
   ) {
      val data: QuirkData = this.getOrCreateData(state);
      this.tick(state, (D)data);
      data.run(entity, model, state, limbSwing, limbSwingAmount, ageInTicks, headYaw, headPitch, intensity);
   }

   public fun getOrCreateData(state: PoseableEntityState<Any>): Any {
      val `$this$getOrPut$iv`: java.util.Map = state.getQuirks();
      val `value$iv`: Any = `$this$getOrPut$iv`.get(this);
      val var10000: Any;
      if (`value$iv` == null) {
         val var6: Any = this.createData();
         `$this$getOrPut$iv`.put(this, var6);
         var10000 = var6;
      } else {
         var10000 = `value$iv`;
      }

      return (D)var10000;
   }
}
