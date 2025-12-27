package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.moves.animations.keyframes

import com.bedrockk.molang.runtime.value.MoValue
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.molang.ExpressionLike
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.molang.ObjectValue
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.moves.animations.ActionEffectContext
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.moves.animations.ActionEffectTimeline
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.moves.animations.ActionEffects
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.moves.animations.UsersProvider
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.scheduling.SchedulingFunctionsKt
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.entity.pokemon.PokemonEntity
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.entity.pokemon.ai.PokemonNavigation
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.MoLangExtensionsKt
import java.util.ArrayList;
import java.util.concurrent.CompletableFuture
import kotlin.jvm.functions.Function0
import kotlin.jvm.functions.Function1
import kotlin.jvm.internal.SourceDebugExtension
import kotlin.jvm.internal.Ref.BooleanRef
import net.minecraft.resources.ResourceLocation
import net.minecraft.world.entity.Entity
import net.minecraft.world.phys.Vec3

@SourceDebugExtension(["SMAP\nReturnToPositionActionEffectKeyframe.kt\nKotlin\n*S Kotlin\n*F\n+ 1 ReturnToPositionActionEffectKeyframe.kt\ncom/cobblemon/mod/common/api/moves/animations/keyframes/ReturnToPositionActionEffectKeyframe\n+ 2 ActionEffectTimeline.kt\ncom/cobblemon/mod/common/api/moves/animations/ActionEffectContext\n+ 3 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n+ 4 fake.kt\nkotlin/jvm/internal/FakeKt\n*L\n1#1,70:1\n73#2:71\n800#3,11:72\n1#4:83\n*S KotlinDebug\n*F\n+ 1 ReturnToPositionActionEffectKeyframe.kt\ncom/cobblemon/mod/common/api/moves/animations/keyframes/ReturnToPositionActionEffectKeyframe\n*L\n30#1:71\n30#1:72,11\n*E\n"])
public class ReturnToPositionActionEffectKeyframe : ActionEffectKeyframe {
   public final val speed: Float = 1.0F
   public final val timeout: ExpressionLike = MoLangExtensionsKt.asExpressionLike("4")
   public final val timeoutActionEffect: ResourceLocation?

   public override fun play(context: ActionEffectContext): CompletableFuture<Unit> {
      var navContext: java.lang.Iterable = context.getProviders();
      val var11: java.util.Collection = new ArrayList();

      for (Object element$iv$iv$iv : $this$filterIsInstance$iv$iv) {
         if (`element$iv$iv$iv` is UsersProvider) {
            var11.add(`element$iv$iv$iv`);
         }
      }

      var var22: Entity;
      label62: {
         val var10000: UsersProvider = CollectionsKt.firstOrNull(var11 as java.util.List) as UsersProvider;
         if (var10000 != null) {
            val var21: java.util.List = var10000.getEntities();
            if (var21 != null) {
               var22 = CollectionsKt.firstOrNull(var21) as Entity;
               break label62;
            }
         }

         var22 = null;
      }

      val var23: PokemonEntity = var22 as? PokemonEntity;
      if ((var22 as? PokemonEntity) == null) {
         return this.skip();
      } else {
         val future: CompletableFuture = new CompletableFuture();
         val var24: MoValue = context.getRuntime().getEnvironment().getValue(SetsKt.setOf("${var23.m_20149_()}-pos").iterator());
         navContext = (java.lang.Iterable)(if (var24 != null) var24.value() else null);
         var timeoutEffect: ActionEffectTimeline = (ActionEffectTimeline)(if ((navContext as? ObjectValue) != null)
            (navContext as? ObjectValue).getObj()
            else
            null);
         val var25: Vec3 = timeoutEffect as? Vec3;
         if ((timeoutEffect as? Vec3) == null) {
            return this.skip();
         } else {
            val timedOut: BooleanRef = new BooleanRef();
            if (var25.m_82554_(var23.m_20182_()) > 20.0) {
               future.complete(Unit.INSTANCE);
               return future;
            } else {
               label50: {
                  if (this.timeoutActionEffect != null) {
                     val it: ResourceLocation = this.timeoutActionEffect;
                     var26 = ActionEffects.INSTANCE.getActionEffects().get(it);
                     if (var26 != null) {
                        break label50;
                     }
                  }

                  var26 = ActionEffectTimeline.Companion.getNONE();
               }

               timeoutEffect = var26;
               val var17: PokemonNavigation = var23.getNavigation();
               val var19: PokemonNavigation.NavigationContext = new PokemonNavigation.NavigationContext(null, null, (new Function0<Unit>(future, timedOut) {
                  {
                     super(0);
                     this.$future = `$future`;
                     this.$timedOut = `$timedOut`;
                  }

                  public final void invoke() {
                     if (!this.$future.isDone() && !this.$timedOut.element) {
                        this.$future.complete(Unit.INSTANCE);
                     }
                  }
               }) as Function0, (new Function0<Unit>(future, timedOut, timeoutEffect, context) {
                  {
                     super(0);
                     this.$future = `$future`;
                     this.$timedOut = `$timedOut`;
                     this.$timeoutEffect = `$timeoutEffect`;
                     this.$context = `$context`;
                  }

                  public final void invoke() {
                     if (!this.$future.isDone() && !this.$timedOut.element) {
                        this.$timedOut.element = true;
                        this.$timeoutEffect.run(this.$context).thenApply(<unrepresentable>::invoke$lambda$0);
                     }
                  }

                  private static final java.lang.Boolean invoke$lambda$0(Function1 $tmp0, Object p0) {
                     return `$tmp0`.invoke(p0) as java.lang.Boolean;
                  }
               }) as Function0, false, 0.0F, 51, null);
               SchedulingFunctionsKt.after$default(
                  0, this.timeout.resolveFloat(context.getRuntime()), true, (new Function0<Unit>(future, timedOut, timeoutEffect, context, var17) {
                     {
                        super(0);
                        this.$future = `$future`;
                        this.$timedOut = `$timedOut`;
                        this.$timeoutEffect = `$timeoutEffect`;
                        this.$context = `$context`;
                        this.$nav = `$nav`;
                     }

                     public final void invoke() {
                        if (!this.$future.isDone() && !this.$timedOut.element) {
                           this.$timedOut.element = true;
                           this.$timeoutEffect.run(this.$context).thenApply(<unrepresentable>::invoke$lambda$0);
                           this.$nav.m_26573_();
                        }
                     }

                     private static final java.lang.Boolean invoke$lambda$0(Function1 $tmp0, Object p0) {
                        return `$tmp0`.invoke(p0) as java.lang.Boolean;
                     }
                  }) as Function0, 1, null
               );
               var17.startMovingTo(var25.f_82479_, var25.f_82480_, var25.f_82481_, (double)this.speed, var19);
               return future;
            }
         }
      }
   }

   override fun interrupt(context: ActionEffectContext) {
      ActionEffectKeyframe.DefaultImpls.interrupt(this, context);
   }

   override fun skip(): CompletableFuture<Unit> {
      return ActionEffectKeyframe.DefaultImpls.skip(this);
   }
}
