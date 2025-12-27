package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.battle

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.reactive.SettableObservable
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.reactive.SimpleObservable
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.scheduling.ClientTaskTracker
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.pokeball.PokeBallPoseableState
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.entity.pokeball.EmptyPokeBallEntity
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.entity.pokeball.EmptyPokeBallEntity.CaptureState
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokeball.PokeBall
import kotlin.jvm.functions.Function0
import kotlin.jvm.functions.Function1

public class ClientBallDisplay(pokeBall: PokeBall, aspects: Set<String>) : PokeBallPoseableState {
   public final val aspects: Set<String>
   public final val pokeBall: PokeBall
   public final var scale: Float
   public open val schedulingTracker: ClientTaskTracker
   public open val shakeEmitter: SimpleObservable<Unit>
   public open val stateEmitter: SettableObservable<CaptureState>

   init {
      this.pokeBall = pokeBall;
      this.aspects = aspects;
      this.stateEmitter = new SettableObservable<>(EmptyPokeBallEntity.CaptureState.FALL);
      this.shakeEmitter = new SimpleObservable<>();
      this.schedulingTracker = ClientTaskTracker.INSTANCE;
      this.scale = 1.0F;
   }

   public open fun getEntity(): Nothing? {
      return null;
   }

   public override fun updatePartialTicks(partialTicks: Float) {
      this.setCurrentPartialTicks(this.getCurrentPartialTicks() + partialTicks);
   }

   public fun start() {
      this.initSubscriptions();
      this.after(1.0F, (new Function0<Unit>(this) {
         {
            super(0);
            this.this$0 = `$receiver`;
         }

         public final void invoke() {
            this.this$0.lerp(0.3F, (new Function1<java.lang.Float, Unit>(this.this$0) {
               {
                  super(1);
                  this.this$0 = `$receiver`;
               }

               public final void invoke(float it) {
                  this.this$0.setScale((float)1 - it);
               }
            }) as (java.lang.Float?) -> Unit);
            this.this$0.after(0.3F, (new Function0<Unit>(this.this$0) {
               {
                  super(0);
                  this.this$0 = `$receiver`;
               }

               public final void invoke() {
                  this.this$0.getStateEmitter().set(EmptyPokeBallEntity.CaptureState.SHAKE);
                  this.this$0.lerp(0.3F, (new Function1<java.lang.Float, Unit>(this.this$0) {
                     {
                        super(1);
                        this.this$0 = `$receiver`;
                     }

                     public final void invoke(float it) {
                        this.this$0.setScale(it);
                     }
                  }) as (java.lang.Float?) -> Unit);
               }
            }) as () -> Unit);
         }
      }) as () -> Unit);
   }
}
