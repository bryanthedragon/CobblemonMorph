package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.moves.animations.keyframes

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.molang.ExpressionLike
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.moves.animations.ActionEffectContext
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.moves.animations.ActionEffectTimeline
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.moves.animations.ActionEffects
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.moves.animations.TargetsProvider
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

@SourceDebugExtension(["SMAP\nMoveToTargetActionEffectKeyframe.kt\nKotlin\n*S Kotlin\n*F\n+ 1 MoveToTargetActionEffectKeyframe.kt\ncom/cobblemon/mod/common/api/moves/animations/keyframes/MoveToTargetActionEffectKeyframe\n+ 2 ActionEffectTimeline.kt\ncom/cobblemon/mod/common/api/moves/animations/ActionEffectContext\n+ 3 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n+ 4 fake.kt\nkotlin/jvm/internal/FakeKt\n*L\n1#1,75:1\n73#2:76\n73#2:88\n800#3,11:77\n800#3,11:89\n1#4:100\n*S KotlinDebug\n*F\n+ 1 MoveToTargetActionEffectKeyframe.kt\ncom/cobblemon/mod/common/api/moves/animations/keyframes/MoveToTargetActionEffectKeyframe\n*L\n32#1:76\n33#1:88\n32#1:77,11\n33#1:89,11\n*E\n"])
public class MoveToTargetActionEffectKeyframe : ActionEffectKeyframe {
   public final var proximity: Float = -1.0F
   public final val speed: Float = 1.0F
   public final val timeout: ExpressionLike = MoLangExtensionsKt.asExpressionLike("4")
   public final val timeoutActionEffect: ResourceLocation?

   public override fun play(context: ActionEffectContext): CompletableFuture<Unit> {
      val nav: java.lang.Iterable = context.getProviders();
      val it: java.util.Collection = new ArrayList();

      for (Object element$iv$iv$iv : $this$filterIsInstance$iv$iv) {
         if (`element$iv$iv$iv` is UsersProvider) {
            it.add(`element$iv$iv$iv`);
         }
      }

      var var32: Entity;
      label85: {
         val var10000: UsersProvider = CollectionsKt.firstOrNull(it as java.util.List) as UsersProvider;
         if (var10000 != null) {
            val var31: java.util.List = var10000.getEntities();
            if (var31 != null) {
               var32 = CollectionsKt.firstOrNull(var31) as Entity;
               break label85;
            }
         }

         var32 = null;
      }

      val var33: PokemonEntity = var32 as? PokemonEntity;
      if ((var32 as? PokemonEntity) == null) {
         val var39: CompletableFuture = CompletableFuture.completedFuture(Unit.INSTANCE);
         return var39;
      } else {
         val `$this$filterIsInstance$iv$ivx`: java.lang.Iterable = context.getProviders();
         val `destination$iv$iv$ivx`: java.util.Collection = new ArrayList();

         for (Object element$iv$iv$ivx : $this$filterIsInstance$iv$ivx) {
            if (`element$iv$iv$ivx` is TargetsProvider) {
               `destination$iv$iv$ivx`.add(`element$iv$iv$ivx`);
            }
         }

         val var34: TargetsProvider = CollectionsKt.firstOrNull(`destination$iv$iv$ivx` as java.util.List) as TargetsProvider;
         if (var34 != null) {
            val var35: java.util.List = var34.getEntities();
            if (var35 != null) {
               var32 = CollectionsKt.firstOrNull(var35) as Entity;
               if (var32 != null) {
                  val var15: CompletableFuture = new CompletableFuture();
                  val timedOut: BooleanRef = new BooleanRef();
                  val var21: java.lang.Float = this.proximity;
                  val var23: Float = var21.floatValue();
                  val var16: Float = if ((if (var23 != -1.0F) var21 else null) != null)
                     if (var23 != -1.0F) var21 else null
                     else
                     (float)(
                        Math.sqrt((double)2 * Math.pow(var33.m_20191_().m_82362_(), (double)2))
                           + 1.5F
                           + Math.sqrt((double)2 * Math.pow(var32.m_20191_().m_82362_(), (double)2))
                     );
                  val var18: Float = var32.m_20270_(var33 as Entity);
                  if (!(var16 <= var18) || !(var18 <= 20.0F)) {
                     var15.complete(Unit.INSTANCE);
                     return var15;
                  }

                  label69: {
                     if (this.timeoutActionEffect != null) {
                        val itx: ResourceLocation = this.timeoutActionEffect;
                        var38 = ActionEffects.INSTANCE.getActionEffects().get(itx);
                        if (var38 != null) {
                           break label69;
                        }
                     }

                     var38 = ActionEffectTimeline.Companion.getNONE();
                  }

                  val var19: ActionEffectTimeline = var38;
                  val var22: PokemonNavigation = var33.getNavigation();
                  val var24: PokemonNavigation.NavigationContext = new PokemonNavigation.NavigationContext(null, null, (new Function0<Unit>(var15, timedOut) {
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
                  }) as Function0, (new Function0<Unit>(var15, timedOut, var19, context) {
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
                  }) as Function0, false, var16, 19, null);
                  SchedulingFunctionsKt.afterOnServer$default(
                     0, this.timeout.resolveFloat(context.getRuntime()), (new Function0<Unit>(var15, timedOut, var19, context, var22) {
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
                  var22.startMovingTo(var32.m_20185_(), var32.m_20186_(), var32.m_20189_(), (double)this.speed, var24);
                  return var15;
               }
            }
         }

         val var37: CompletableFuture = CompletableFuture.completedFuture(Unit.INSTANCE);
         return var37;
      }
   }

   override fun interrupt(context: ActionEffectContext) {
      ActionEffectKeyframe.DefaultImpls.interrupt(this, context);
   }

   override fun skip(): CompletableFuture<Unit> {
      return ActionEffectKeyframe.DefaultImpls.skip(this);
   }
}
