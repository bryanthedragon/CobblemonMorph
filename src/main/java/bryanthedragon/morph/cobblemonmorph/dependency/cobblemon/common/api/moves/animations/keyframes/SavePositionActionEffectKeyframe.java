package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.moves.animations.keyframes

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.molang.ObjectValue
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.moves.animations.ActionEffectContext
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.moves.animations.UsersProvider
import java.util.ArrayList;
import java.util.concurrent.CompletableFuture
import kotlin.jvm.internal.SourceDebugExtension
import net.minecraft.world.entity.Entity

@SourceDebugExtension(["SMAP\nSavePositionActionEffectKeyframe.kt\nKotlin\n*S Kotlin\n*F\n+ 1 SavePositionActionEffectKeyframe.kt\ncom/cobblemon/mod/common/api/moves/animations/keyframes/SavePositionActionEffectKeyframe\n+ 2 ActionEffectTimeline.kt\ncom/cobblemon/mod/common/api/moves/animations/ActionEffectContext\n+ 3 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n*L\n1#1,22:1\n73#2:23\n800#3,11:24\n*S KotlinDebug\n*F\n+ 1 SavePositionActionEffectKeyframe.kt\ncom/cobblemon/mod/common/api/moves/animations/keyframes/SavePositionActionEffectKeyframe\n*L\n18#1:23\n18#1:24,11\n*E\n"])
public class SavePositionActionEffectKeyframe : ConditionalActionEffectKeyframe {
   public override fun playWhenTrue(context: ActionEffectContext): CompletableFuture<Unit> {
      val `$this$filterIsInstance$iv$iv`: java.lang.Iterable = context.getProviders();
      val `destination$iv$iv$iv`: java.util.Collection = new ArrayList();

      for (Object element$iv$iv$iv : $this$filterIsInstance$iv$iv) {
         if (`element$iv$iv$iv` is UsersProvider) {
            `destination$iv$iv$iv`.add(`element$iv$iv$iv`);
         }
      }

      val var10000: UsersProvider = CollectionsKt.firstOrNull(`destination$iv$iv$iv` as java.util.List) as UsersProvider;
      if (var10000 != null) {
         val var12: java.util.List = var10000.getEntities();
         if (var12 != null) {
            val var13: Entity = CollectionsKt.firstOrNull(var12) as Entity;
            if (var13 != null) {
               context.getRuntime().getEnvironment().setSimpleVariable("${var13.m_20149_()}-pos", new ObjectValue(var13.m_20182_(), null, null, 6, null));
               return this.skip();
            }
         }
      }

      return this.skip();
   }
}
