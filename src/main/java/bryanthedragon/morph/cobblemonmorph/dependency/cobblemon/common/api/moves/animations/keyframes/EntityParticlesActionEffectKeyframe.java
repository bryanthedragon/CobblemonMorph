package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.moves.animations.keyframes

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.molang.ExpressionLike
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.moves.animations.ActionEffectContext
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.moves.animations.EntityProvider
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.moves.animations.UsersProvider
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.scheduling.SchedulingFunctionsKt
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.entity.Poseable
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.effect.SpawnSnowstormEntityParticlePacket
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.MoLangExtensionsKt
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.ResourceLocationExtensionsKt
import java.util.ArrayList;
import java.util.concurrent.CompletableFuture
import kotlin.jvm.functions.Function1
import kotlin.jvm.internal.SourceDebugExtension
import net.minecraft.resources.ResourceLocation
import net.minecraft.server.level.ServerLevel
import net.minecraft.world.entity.Entity
import net.minecraft.world.level.Level

@SourceDebugExtension(["SMAP\nEntityParticlesActionEffectKeyframe.kt\nKotlin\n*S Kotlin\n*F\n+ 1 EntityParticlesActionEffectKeyframe.kt\ncom/cobblemon/mod/common/api/moves/animations/keyframes/EntityParticlesActionEffectKeyframe\n+ 2 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n+ 3 fake.kt\nkotlin/jvm/internal/FakeKt\n*L\n1#1,55:1\n800#2,11:56\n1360#2:67\n1446#2,2:68\n766#2:70\n857#2,2:71\n1448#2,3:73\n766#2:77\n857#2,2:78\n1855#2,2:80\n1#3:76\n*S KotlinDebug\n*F\n+ 1 EntityParticlesActionEffectKeyframe.kt\ncom/cobblemon/mod/common/api/moves/animations/keyframes/EntityParticlesActionEffectKeyframe\n*L\n38#1:56,11\n39#1:67\n39#1:68,2\n39#1:70\n39#1:71,2\n39#1:73,3\n47#1:77\n47#1:78,2\n47#1:80,2\n*E\n"])
public class EntityParticlesActionEffectKeyframe : ConditionalActionEffectKeyframe, EntityConditionalActionEffectKeyframe {
   public final val delay: ExpressionLike = MoLangExtensionsKt.asExpressionLike("0")
   public final var effect: String?
   public open val entityCondition: ExpressionLike = MoLangExtensionsKt.asExpressionLike("q.entity.is_user")
   public final var locator: String = "root"
   public final val visibilityRange: Int = 200

   public override fun playWhenTrue(context: ActionEffectContext): CompletableFuture<Unit> {
      var effectIdentifier: java.lang.Iterable = context.getProviders();
      var `$this$filterTo$iv$iv`: java.util.Collection = new ArrayList();

      for (Object element$iv$iv : effectIdentifier) {
         if (var9 is EntityProvider) {
            `$this$filterTo$iv$iv`.add(var9);
         }
      }

      effectIdentifier = `$this$filterTo$iv$iv` as java.util.List;
      `$this$filterTo$iv$iv` = new ArrayList();

      for (Object element$iv$ivx : effectIdentifier) {
         val packet: EntityProvider = `element$iv$ivx` as EntityProvider;
         val var12: java.lang.Iterable = (`element$iv$ivx` as EntityProvider).getEntities();
         val `destination$iv$ivx`: java.util.Collection = new ArrayList();

         for (Object element$iv$ivxx : $this$filter$iv) {
            if (this.test(context, `element$iv$ivxx` as Entity, packet is UsersProvider)) {
               `destination$iv$ivx`.add(`element$iv$ivxx`);
            }
         }

         CollectionsKt.addAll(`$this$filterTo$iv$iv`, `destination$iv$ivx` as java.util.List);
      }

      var `$i$f$forEach`: java.lang.String;
      try {
         var var50: java.lang.String;
         label71: {
            if (this.effect != null) {
               val var10000: ExpressionLike = MoLangExtensionsKt.asExpressionLike(this.effect);
               if (var10000 != null) {
                  var50 = var10000.resolveString(context.getRuntime());
                  if (var50 != null) {
                     var50 = if (!(var50 == "0.0")) var50 else null;
                     if (var50 != null) {
                        break label71;
                     }
                  }
               }
            }

            var50 = this.effect;
         }

         `$i$f$forEach` = var50;
      } catch (var21: Exception) {
         `$i$f$forEach` = this.effect;
      }

      if (`$i$f$forEach` != null) {
         val var27: ResourceLocation = ResourceLocationExtensionsKt.asIdentifierDefaultingNamespace$default(`$i$f$forEach`, null, 1, null);
         if (var27 != null) {
            val var23: ResourceLocation = var27;
            val var25: java.lang.Iterable = `$this$filterTo$iv$iv` as java.util.List;
            val var33: java.util.Collection = new ArrayList();

            for (Object element$iv$ivx : var25) {
               if (`element$iv$ivx` as Entity is Poseable) {
                  var33.add(`element$iv$ivx`);
               }
            }

            for (Object element$iv : var25) {
               val var37: Entity = var34 as Entity;
               val var44: SpawnSnowstormEntityParticlePacket = new SpawnSnowstormEntityParticlePacket(var23, (var34 as Entity).m_19879_(), this.locator);
               val var51: Level = (var34 as Entity).m_9236_();
               val var47: java.util.List = (var51 as ServerLevel).m_8795_(EntityParticlesActionEffectKeyframe::playWhenTrue$lambda$5$lambda$4);
               var44.sendToPlayers(var47);
            }

            return SchedulingFunctionsKt.delayedFuture$default(0, this.delay.resolveFloat(context.getRuntime()), true, 1, null);
         }
      }

      return this.skip();
   }

   override fun test(context: ActionEffectContext, entity: Entity, isUser: Boolean): Boolean {
      return EntityConditionalActionEffectKeyframe.DefaultImpls.test(this, context, entity, isUser);
   }

   @JvmStatic
   fun `playWhenTrue$lambda$5$lambda$4`(`$tmp0`: Function1, p0: Any): Boolean {
      return `$tmp0`.invoke(p0) as java.lang.Boolean;
   }
}
