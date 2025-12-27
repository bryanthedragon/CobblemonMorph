package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.moves.animations.keyframes

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.molang.ExpressionLike
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.moves.animations.ActionEffectContext
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.moves.animations.EntityProvider
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.moves.animations.UsersProvider
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.scheduling.SchedulingFunctionsKt
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.MoLangExtensionsKt
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.ResourceLocationExtensionsKt
import java.util.ArrayList;
import java.util.concurrent.CompletableFuture
import kotlin.jvm.internal.SourceDebugExtension
import net.minecraft.core.registries.Registries
import net.minecraft.resources.ResourceLocation
import net.minecraft.sounds.SoundEvent
import net.minecraft.world.entity.Entity

@SourceDebugExtension(["SMAP\nEntitySoundActionEffectKeyframe.kt\nKotlin\n*S Kotlin\n*F\n+ 1 EntitySoundActionEffectKeyframe.kt\ncom/cobblemon/mod/common/api/moves/animations/keyframes/EntitySoundActionEffectKeyframe\n+ 2 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n+ 3 fake.kt\nkotlin/jvm/internal/FakeKt\n*L\n1#1,50:1\n800#2,11:51\n1360#2:62\n1446#2,2:63\n766#2:65\n857#2,2:66\n1448#2,3:68\n1855#2,2:72\n1#3:71\n*S KotlinDebug\n*F\n+ 1 EntitySoundActionEffectKeyframe.kt\ncom/cobblemon/mod/common/api/moves/animations/keyframes/EntitySoundActionEffectKeyframe\n*L\n34#1:51,11\n35#1:62\n35#1:63,2\n35#1:65\n35#1:66,2\n35#1:68,3\n43#1:72,2\n*E\n"])
public class EntitySoundActionEffectKeyframe : ConditionalActionEffectKeyframe, EntityConditionalActionEffectKeyframe {
   public final val delay: ExpressionLike = MoLangExtensionsKt.asExpressionLike("0")
   public open val entityCondition: ExpressionLike = MoLangExtensionsKt.asExpressionLike("q.entity.is_user")
   public final var sound: String?

   public override fun playWhenTrue(context: ActionEffectContext): CompletableFuture<Unit> {
      var soundIdentifier: java.lang.Iterable = context.getProviders();
      var e: java.util.Collection = new ArrayList();

      for (Object element$iv$iv : soundIdentifier) {
         if (var9 is EntityProvider) {
            e.add(var9);
         }
      }

      soundIdentifier = e as java.util.List;
      e = new ArrayList();

      for (Object element$iv$ivx : soundIdentifier) {
         val soundEvent: EntityProvider = `element$iv$ivx` as EntityProvider;
         val `$this$filter$iv`: java.lang.Iterable = (`element$iv$ivx` as EntityProvider).getEntities();
         val `destination$iv$ivx`: java.util.Collection = new ArrayList();

         for (Object element$iv$ivxx : $this$filter$iv) {
            if (this.test(context, `element$iv$ivxx` as Entity, soundEvent is UsersProvider)) {
               `destination$iv$ivx`.add(`element$iv$ivxx`);
            }
         }

         CollectionsKt.addAll(e, `destination$iv$ivx` as java.util.List);
      }

      var `$i$f$forEach`: java.lang.String;
      try {
         var var41: java.lang.String;
         label62: {
            if (this.sound != null) {
               val var10000: ExpressionLike = MoLangExtensionsKt.asExpressionLike(this.sound);
               if (var10000 != null) {
                  var41 = var10000.resolveString(context.getRuntime());
                  if (var41 != null) {
                     var41 = if (!(var41 == "0.0")) var41 else null;
                     if (var41 != null) {
                        break label62;
                     }
                  }
               }
            }

            var41 = this.sound;
         }

         `$i$f$forEach` = var41;
      } catch (var21: Exception) {
         `$i$f$forEach` = this.sound;
      }

      if (`$i$f$forEach` != null) {
         val var26: ResourceLocation = ResourceLocationExtensionsKt.asIdentifierDefaultingNamespace$default(`$i$f$forEach`, null, 1, null);
         if (var26 != null) {
            val var23: ResourceLocation = var26;

            val var25: java.lang.Iterable;
            for (Object element$iv : var25) {
               val var33: Entity = var31 as Entity;
               val var42: SoundEvent = (var31 as Entity).m_9236_().m_9598_().m_175515_(Registries.f_256840_).m_7745_(var23) as SoundEvent;
               if (var42 == null) {
                  return this.skip();
               }

               var33.m_5496_(var42, 1.0F, 1.0F);
            }

            return SchedulingFunctionsKt.delayedFuture$default(0, this.delay.resolveFloat(context.getRuntime()), true, 1, null);
         }
      }

      return this.skip();
   }

   override fun test(context: ActionEffectContext, entity: Entity, isUser: Boolean): Boolean {
      return EntityConditionalActionEffectKeyframe.DefaultImpls.test(this, context, entity, isUser);
   }
}
