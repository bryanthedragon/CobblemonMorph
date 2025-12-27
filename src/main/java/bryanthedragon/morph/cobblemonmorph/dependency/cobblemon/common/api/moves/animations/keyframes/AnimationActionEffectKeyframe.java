package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.moves.animations.keyframes

import com.bedrockk.molang.Expression
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.CobblemonNetwork
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.molang.ExpressionLike
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.moves.animations.ActionEffectContext
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.moves.animations.EntityProvider
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.moves.animations.UsersProvider
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.scheduling.SchedulingFunctionsKt
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.animation.PlayPoseableAnimationPacket
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.MoLangExtensionsKt
import java.util.ArrayList;
import java.util.concurrent.CompletableFuture
import kotlin.jvm.functions.Function1
import kotlin.jvm.internal.SourceDebugExtension
import net.minecraft.server.level.ServerLevel
import net.minecraft.server.level.ServerPlayer
import net.minecraft.world.entity.Entity
import net.minecraft.world.level.Level

@SourceDebugExtension(["SMAP\nAnimationActionEffectKeyframe.kt\nKotlin\n*S Kotlin\n*F\n+ 1 AnimationActionEffectKeyframe.kt\ncom/cobblemon/mod/common/api/moves/animations/keyframes/AnimationActionEffectKeyframe\n+ 2 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n+ 3 fake.kt\nkotlin/jvm/internal/FakeKt\n*L\n1#1,54:1\n800#2,11:55\n1360#2:66\n1446#2,2:67\n766#2:69\n857#2,2:70\n1448#2,3:72\n1549#2:75\n1620#2,3:76\n1549#2:79\n1620#2,2:80\n1622#2:83\n1855#2,2:84\n1#3:82\n*S KotlinDebug\n*F\n+ 1 AnimationActionEffectKeyframe.kt\ncom/cobblemon/mod/common/api/moves/animations/keyframes/AnimationActionEffectKeyframe\n*L\n31#1:55,11\n32#1:66\n32#1:67,2\n32#1:69\n32#1:70,2\n32#1:72,3\n34#1:75\n34#1:76,3\n37#1:79\n37#1:80,2\n37#1:83\n49#1:84,2\n*E\n"])
public class AnimationActionEffectKeyframe : ConditionalActionEffectKeyframe, EntityConditionalActionEffectKeyframe {
   public final var animation: Set<String> = SetsKt.setOf("physical")
   public final var delay: ExpressionLike = MoLangExtensionsKt.asExpressionLike("0")
   public open val entityCondition: ExpressionLike = MoLangExtensionsKt.asExpressionLike("q.entity.is_user")
   public final var variables: List<Expression> = CollectionsKt.emptyList()
   public final var visibilityRange: Int = 200

   public override fun playWhenTrue(context: ActionEffectContext): CompletableFuture<Unit> {
      var expressions: java.lang.Iterable = context.getProviders();
      var entity: java.util.Collection = new ArrayList();

      for (Object element$iv$iv : expressions) {
         if (pkt is EntityProvider) {
            entity.add(pkt);
         }
      }

      expressions = entity as java.util.List;
      entity = new ArrayList();

      for (Object element$iv$ivx : expressions) {
         val `$this$forEach$iv`: EntityProvider = `element$iv$ivx` as EntityProvider;
         val it: java.lang.Iterable = (`element$iv$ivx` as EntityProvider).getEntities();
         val `destination$iv$ivx`: java.util.Collection = new ArrayList();

         for (Object element$iv$ivxx : $this$filter$iv) {
            if (this.test(context, `element$iv$ivxx` as Entity, `$this$forEach$iv` is UsersProvider)) {
               `destination$iv$ivx`.add(`element$iv$ivxx`);
            }
         }

         CollectionsKt.addAll(entity, `destination$iv$ivx` as java.util.List);
      }

      val entities: java.util.List = entity as java.util.List;
      val var26: java.lang.Iterable = this.variables;
      val `destination$iv$ivx`: java.util.Collection = new ArrayList(CollectionsKt.collectionSizeOrDefault(this.variables, 10));

      for (Object item$iv$iv : $this$map$iv) {
         `destination$iv$ivx`.add((var45 as Expression).getOriginalString());
      }

      val var24: java.util.Set = CollectionsKt.toSet(`destination$iv$ivx` as java.util.List);
      val `$this$map$ivx`: java.lang.Iterable = this.animation;
      val `destination$iv$ivxx`: java.util.Collection = new ArrayList(CollectionsKt.collectionSizeOrDefault(this.animation, 10));

      for (Object item$iv$iv : $this$map$ivx) {
         val var52: java.lang.String = var49 as java.lang.String;

         var var59: java.lang.String;
         try {
            val it: java.lang.String = MoLangExtensionsKt.asExpressionLike(var52).resolveString(context.getRuntime());
            var var10000: java.lang.String = if (!(it == "0.0")) it else null;
            if (var10000 == null) {
               var10000 = var52;
            }

            var59 = var10000;
         } catch (var22: Exception) {
            var59 = var49 as java.lang.String;
         }

         `destination$iv$ivxx`.add(var59);
      }

      val var27: java.util.Set = CollectionsKt.toSet(`destination$iv$ivxx` as java.util.List);

      for (Entity entityx : entities) {
         val var60: Level = entityx.m_9236_();
         val var39: java.util.List = (var60 as ServerLevel).m_8795_(AnimationActionEffectKeyframe::playWhenTrue$lambda$5);
         val var43: PlayPoseableAnimationPacket = new PlayPoseableAnimationPacket(entityx.m_19879_(), var27, var24);

         val var47: java.lang.Iterable;
         for (Object element$iv : var47) {
            val var56: ServerPlayer = var55 as ServerPlayer;
            val var61: CobblemonNetwork = CobblemonNetwork.INSTANCE;
            var61.sendPacket(var56, var43);
         }
      }

      return SchedulingFunctionsKt.delayedFuture$default(0, this.delay.resolveFloat(context.getRuntime()), true, 1, null);
   }

   override fun test(context: ActionEffectContext, entity: Entity, isUser: Boolean): Boolean {
      return EntityConditionalActionEffectKeyframe.DefaultImpls.test(this, context, entity, isUser);
   }

   @JvmStatic
   fun `playWhenTrue$lambda$5`(`$tmp0`: Function1, p0: Any): Boolean {
      return `$tmp0`.invoke(p0) as java.lang.Boolean;
   }
}
