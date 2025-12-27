package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.pokeball

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.reactive.Observable
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.reactive.SettableObservable
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.reactive.Transform
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.scheduling.Schedulable
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.PoseableEntityModel
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.PoseableEntityState
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.animation.StatefulAnimation
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.pokeball.AncientPokeBallModel
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.pokeball.PokeBallModel
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.repository.RenderContext
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.entity.pokeball.EmptyPokeBallEntity
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.entity.pokeball.EmptyPokeBallEntity.CaptureState
import kotlin.jvm.functions.Function0
import kotlin.jvm.functions.Function1
import kotlin.random.Random
import net.minecraft.world.entity.Entity
import org.jetbrains.annotations.NotNull

public abstract class PokeBallPoseableState : PoseableEntityState<EmptyPokeBallEntity>, Schedulable {
   private final val group: String
      private final get() {
         return if (this.getCurrentModel() is AncientPokeBallModel) "ancient_poke_ball" else "poke_ball";
      }


   public abstract val shakeEmitter: Observable<Unit>
   public abstract val stateEmitter: SettableObservable<CaptureState>

   public open fun initSubscriptions() {
      Observable.DefaultImpls.subscribe$default(
         this.getStateEmitter(),
         null,
         (
            new Function1<EmptyPokeBallEntity.CaptureState, Unit>(this) {
               {
                  super(1);
                  this.this$0 = `$receiver`;
               }

               public final void invoke(@NotNull EmptyPokeBallEntity.CaptureState state) {
                  switch (state) {
                     case HIT:
                        this.this$0
                           .doLater(
                              (
                                 new Function0<Unit>(this.this$0) {
                                    {
                                       super(0);
                                       this.this$0 = `$receiver`;
                                    }

                                    public final void invoke() {
                                       val var10000: PoseableEntityModel = this.this$0.getCurrentModel();
                                       val model: PoseableEntityModel = var10000;
                                       this.this$0
                                          .after(
                                             0.2F,
                                             (
                                                new Function0<Unit>(model, this.this$0) {
                                                   {
                                                      super(0);
                                                      this.$model = `$model`;
                                                      this.this$0 = `$receiver`;
                                                   }

                                                   public final void invoke() {
                                                      if (this.$model is PokeBallModel
                                                         && this.this$0.getStateEmitter().get() === EmptyPokeBallEntity.CaptureState.HIT) {
                                                         this.this$0
                                                            .doLater(
                                                               (
                                                                  new Function0<Unit>(this.$model, this.this$0) {
                                                                     {
                                                                        super(0);
                                                                        this.$model = `$model`;
                                                                        this.this$0 = `$receiver`;
                                                                     }

                                                                     public final void invoke() {
                                                                        val var10000: EmptyPokeBallEntity = this.$model
                                                                           .getContext()
                                                                           .request(RenderContext.Companion.getENTITY()) as EmptyPokeBallEntity;
                                                                        if (var10000 != null) {
                                                                           val entity: EmptyPokeBallEntity = var10000;
                                                                           this.$model
                                                                              .moveToPose(
                                                                                 entity as Entity, this.this$0, (this.$model as PokeBallModel).getOpen()
                                                                              );
                                                                           this.this$0
                                                                              .after(
                                                                                 1.75F,
                                                                                 (
                                                                                    new Function0<Unit>(this.$model, entity, this.this$0) {
                                                                                       {
                                                                                          super(0);
                                                                                          this.$model = `$model`;
                                                                                          this.$entity = `$entity`;
                                                                                          this.this$0 = `$receiver`;
                                                                                       }

                                                                                       public final void invoke() {
                                                                                          this.$model
                                                                                             .moveToPose(
                                                                                                this.$entity as Entity,
                                                                                                this.this$0,
                                                                                                (this.$model as PokeBallModel).getShut()
                                                                                             );
                                                                                       }
                                                                                    }
                                                                                 ) as () -> Unit
                                                                              );
                                                                        }
                                                                     }
                                                                  }
                                                               ) as () -> Unit
                                                            );
                                                      }
                                                   }
                                                }
                                             ) as () -> Unit
                                          );
                                    }
                                 }
                              ) as () -> Unit
                           );
                     case FALL:
                     default:
                        break;
                     case SHAKE:
                        this.this$0
                           .doLater(
                              (
                                 new Function0<Unit>(this.this$0) {
                                    {
                                       super(0);
                                       this.this$0 = `$receiver`;
                                    }

                                    public final void invoke() {
                                       val var10000: PokeBallPoseableState = this.this$0;
                                       val var1: Array<StatefulAnimation> = new StatefulAnimation[1];
                                       val var10003: PoseableEntityModel = this.this$0.getCurrentModel();
                                       var1[0] = PoseableEntityModel.bedrockStateful$default(
                                          var10003, PokeBallPoseableState.access$getGroup(this.this$0), "bounce", null, 4, null
                                       );
                                       var10000.setStatefulAnimations(var1);
                                    }
                                 }
                              ) as () -> Unit
                           );
                        Observable.DefaultImpls.subscribe$default(
                           this.this$0.getShakeEmitter().pipe(Observable.Companion.emitWhile((new Function1<Unit, java.lang.Boolean>(this.this$0) {
                              {
                                 super(1);
                                 this.this$0 = `$receiver`;
                              }

                              @NotNull
                              public final java.lang.Boolean invoke(@NotNull Unit it) {
                                 return this.this$0.getStateEmitter().get() === EmptyPokeBallEntity.CaptureState.SHAKE;
                              }
                           }) as (Unit?) -> java.lang.Boolean) as Transform<Unit, Unit>),
                           null,
                           (
                              new Function1<Unit, Unit>(this.this$0) {
                                 {
                                    super(1);
                                    this.this$0 = `$receiver`;
                                 }

                                 public final void invoke(@NotNull Unit it) {
                                    val bob: java.lang.String = "bob${Random.Default.nextInt(6) + 1}";
                                    this.this$0
                                       .doLater(
                                          (
                                             new Function0<Unit>(this.this$0, bob) {
                                                {
                                                   super(0);
                                                   this.this$0 = `$receiver`;
                                                   this.$bob = `$bob`;
                                                }

                                                public final void invoke() {
                                                   val var10000: PokeBallPoseableState = this.this$0;
                                                   val var1: Array<StatefulAnimation> = new StatefulAnimation[1];
                                                   val var10003: PoseableEntityModel = this.this$0.getCurrentModel();
                                                   var1[0] = PoseableEntityModel.bedrockStateful$default(
                                                      var10003, PokeBallPoseableState.access$getGroup(this.this$0), this.$bob, null, 4, null
                                                   );
                                                   var10000.setStatefulAnimations(var1);
                                                }
                                             }
                                          ) as () -> Unit
                                       );
                                 }
                              }
                           ) as Function1,
                           1,
                           null
                        );
                        break;
                     case CAPTURED:
                        this.this$0
                           .doLater(
                              (
                                 new Function0<Unit>(this.this$0) {
                                    {
                                       super(0);
                                       this.this$0 = `$receiver`;
                                    }

                                    public final void invoke() {
                                       val var10000: PokeBallPoseableState = this.this$0;
                                       val var1: Array<StatefulAnimation> = new StatefulAnimation[1];
                                       val var10003: PoseableEntityModel = this.this$0.getCurrentModel();
                                       var1[0] = PoseableEntityModel.bedrockStateful$default(
                                          var10003, PokeBallPoseableState.access$getGroup(this.this$0), "capture", null, 4, null
                                       );
                                       var10000.setStatefulAnimations(var1);
                                    }
                                 }
                              ) as () -> Unit
                           );
                        break;
                     case CAPTURED_CRITICAL:
                        this.this$0
                           .doLater(
                              (
                                 new Function0<Unit>(this.this$0) {
                                    {
                                       super(0);
                                       this.this$0 = `$receiver`;
                                    }

                                    public final void invoke() {
                                       val var10000: PokeBallPoseableState = this.this$0;
                                       val var1: Array<StatefulAnimation> = new StatefulAnimation[1];
                                       val var10003: PoseableEntityModel = this.this$0.getCurrentModel();
                                       var1[0] = PoseableEntityModel.bedrockStateful$default(
                                          var10003, PokeBallPoseableState.access$getGroup(this.this$0), "critical", null, 4, null
                                       );
                                       var10000.setStatefulAnimations(var1);
                                    }
                                 }
                              ) as () -> Unit
                           );
                        break;
                     case BROKEN_FREE:
                        this.this$0
                           .doLater(
                              (
                                 new Function0<Unit>(this.this$0) {
                                    {
                                       super(0);
                                       this.this$0 = `$receiver`;
                                    }

                                    public final void invoke() {
                                       val var10000: PokeBallPoseableState = this.this$0;
                                       val var1: Array<StatefulAnimation> = new StatefulAnimation[1];
                                       val var10003: PoseableEntityModel = this.this$0.getCurrentModel();
                                       var1[0] = PoseableEntityModel.bedrockStateful$default(
                                          var10003, PokeBallPoseableState.access$getGroup(this.this$0), "break", null, 4, null
                                       );
                                       var10000.setStatefulAnimations(var1);
                                    }
                                 }
                              ) as () -> Unit
                           );
                  }
               }
            }
         ) as Function1,
         1,
         null
      );
   }
}
