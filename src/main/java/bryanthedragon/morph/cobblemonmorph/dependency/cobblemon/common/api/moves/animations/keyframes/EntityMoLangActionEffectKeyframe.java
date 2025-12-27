package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.moves.animations.keyframes

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.CobblemonNetwork
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.molang.ExpressionLike
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.moves.animations.ActionEffectContext
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.moves.animations.EntityProvider
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.moves.animations.UsersProvider
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.scheduling.SchedulingFunctionsKt
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.effect.RunPosableMoLangPacket
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.MoLangExtensionsKt
import java.util.ArrayList;
import java.util.LinkedHashSet
import java.util.concurrent.CompletableFuture
import kotlin.jvm.functions.Function1
import kotlin.jvm.internal.SourceDebugExtension
import net.minecraft.server.level.ServerLevel
import net.minecraft.server.level.ServerPlayer
import net.minecraft.world.entity.Entity
import net.minecraft.world.level.Level

@SourceDebugExtension(["SMAP\nEntityMoLangActionEffectKeyframe.kt\nKotlin\n*S Kotlin\n*F\n+ 1 EntityMoLangActionEffectKeyframe.kt\ncom/cobblemon/mod/common/api/moves/animations/keyframes/EntityMoLangActionEffectKeyframe\n+ 2 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n*L\n1#1,52:1\n800#2,11:53\n1360#2:64\n1446#2,2:65\n766#2:67\n857#2,2:68\n1448#2,3:70\n1855#2,2:73\n*S KotlinDebug\n*F\n+ 1 EntityMoLangActionEffectKeyframe.kt\ncom/cobblemon/mod/common/api/moves/animations/keyframes/EntityMoLangActionEffectKeyframe\n*L\n39#1:53,11\n40#1:64\n40#1:65,2\n40#1:67\n40#1:68,2\n40#1:70,3\n46#1:73,2\n*E\n"])
public class EntityMoLangActionEffectKeyframe : ConditionalActionEffectKeyframe, EntityConditionalActionEffectKeyframe {
   public final var delay: ExpressionLike = MoLangExtensionsKt.asExpressionLike("0")
   public open val entityCondition: ExpressionLike = MoLangExtensionsKt.asExpressionLike("q.entity.is_user")
   public final val expressions: MutableSet<String> = (new LinkedHashSet()) as java.util.Set
   public final val visibilityRange: Int = 200

   public override fun playWhenTrue(context: ActionEffectContext): CompletableFuture<Unit> {
      var `$this$flatMap$iv`: java.lang.Iterable = context.getProviders();
      var players: java.util.Collection = new ArrayList();

      for (Object element$iv$iv : $this$flatMap$iv) {
         if (`$i$f$forEach` is EntityProvider) {
            players.add(`$i$f$forEach`);
         }
      }

      `$this$flatMap$iv` = players as java.util.List;
      players = new ArrayList();

      for (Object element$iv$ivx : $this$flatMap$iv) {
         val `list$iv$iv`: EntityProvider = `element$iv$ivx` as EntityProvider;
         val it: java.lang.Iterable = (`element$iv$ivx` as EntityProvider).getEntities();
         val `destination$iv$ivx`: java.util.Collection = new ArrayList();

         for (Object element$iv$ivxx : $this$filter$iv) {
            if (this.test(context, `element$iv$ivxx` as Entity, `list$iv$iv` is UsersProvider)) {
               `destination$iv$ivx`.add(`element$iv$ivxx`);
            }
         }

         CollectionsKt.addAll(players, `destination$iv$ivx` as java.util.List);
      }

      for (Entity entity : (java.util.List)destination$iv$iv) {
         val var10000: Level = var24.m_9236_();
         val var26: java.util.List = (var10000 as ServerLevel).m_8795_(EntityMoLangActionEffectKeyframe::playWhenTrue$lambda$2);
         val var28: RunPosableMoLangPacket = new RunPosableMoLangPacket(var24.m_19879_(), this.expressions);

         val var30: java.lang.Iterable;
         for (Object element$iv : var30) {
            val var36: ServerPlayer = var35 as ServerPlayer;
            val var38: CobblemonNetwork = CobblemonNetwork.INSTANCE;
            var38.sendPacket(var36, var28);
         }
      }

      return SchedulingFunctionsKt.delayedFuture$default(0, this.delay.resolveFloat(context.getRuntime()), true, 1, null);
   }

   override fun test(context: ActionEffectContext, entity: Entity, isUser: Boolean): Boolean {
      return EntityConditionalActionEffectKeyframe.DefaultImpls.test(this, context, entity, isUser);
   }

   @JvmStatic
   fun `playWhenTrue$lambda$2`(`$tmp0`: Function1, p0: Any): Boolean {
      return `$tmp0`.invoke(p0) as java.lang.Boolean;
   }
}
