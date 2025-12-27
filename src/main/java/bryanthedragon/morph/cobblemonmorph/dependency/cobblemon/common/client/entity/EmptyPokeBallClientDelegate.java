package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.entity

import com.bedrockk.molang.runtime.MoLangEnvironment
import com.bedrockk.molang.runtime.MoParams
import com.bedrockk.molang.runtime.value.DoubleValue
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.entity.EntitySideDelegate
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.molang.MoLangFunctions
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.reactive.SettableObservable
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.reactive.SimpleObservable
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.scheduling.SchedulingTracker
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.pokeball.PokeBallPoseableState
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.entity.pokeball.EmptyPokeBallEntity
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.entity.pokeball.EmptyPokeBallEntity.CaptureState
import net.minecraft.network.syncher.EntityDataAccessor
import net.minecraft.world.entity.Entity
import net.minecraft.world.phys.Vec3

public class EmptyPokeBallClientDelegate : PokeBallPoseableState, EntitySideDelegate<EmptyPokeBallEntity> {
   public final lateinit var currentEntity: EmptyPokeBallEntity

   public open val schedulingTracker: SchedulingTracker
      public open get() {
         return this.getEntity().getSchedulingTracker();
      }


   public open val shakeEmitter: SimpleObservable<Unit> = new SimpleObservable()
   public open val stateEmitter: SettableObservable<CaptureState> = new SettableObservable(EmptyPokeBallEntity.CaptureState.NOT)

   public open fun getEntity(): EmptyPokeBallEntity {
      return this.getCurrentEntity();
   }

   public override fun updatePartialTicks(partialTicks: Float) {
      this.setCurrentPartialTicks(partialTicks);
   }

   public open fun initialize(entity: EmptyPokeBallEntity) {
      this.setCurrentEntity(entity);
      this.setAge(entity.f_19797_);
      this.initSubscriptions();
      val var10000: MoLangFunctions = MoLangFunctions.INSTANCE;
      val var10001: MoLangFunctions = MoLangFunctions.INSTANCE;
      val var10002: MoLangEnvironment = this.getRuntime().getEnvironment();
      var10000.addFunctions(
         MoLangFunctions.getQueryStruct$default(var10001, var10002, null, 1, null),
         MapsKt.mapOf(TuplesKt.to("pokeball_type", EmptyPokeBallClientDelegate::initialize$lambda$0))
      );
   }

   public open fun tick(entity: EmptyPokeBallEntity) {
      EntitySideDelegate.DefaultImpls.tick(this, entity as Entity);
      val var10001: Vec3 = entity.m_20182_();
      this.updateLocatorPosition(var10001);
      this.incrementAge(entity as Entity);
   }

   public override fun onTrackedDataSet(data: EntityDataAccessor<*>) {
      EntitySideDelegate.DefaultImpls.onTrackedDataSet(this, data);
      if (data == EmptyPokeBallEntity.Companion.getCAPTURE_STATE()) {
         this.getStateEmitter().set(this.getCurrentEntity().getCaptureState());
      } else if (data == EmptyPokeBallEntity.Companion.getSHAKE()) {
         this.getShakeEmitter().emit(Unit.INSTANCE);
      }
   }

   @JvmStatic
   fun `initialize$lambda$0`(`this$0`: EmptyPokeBallClientDelegate, it: MoParams): Any {
      return new DoubleValue(`this$0`.getCurrentEntity().getPokeBall().getName().toString());
   }
}
