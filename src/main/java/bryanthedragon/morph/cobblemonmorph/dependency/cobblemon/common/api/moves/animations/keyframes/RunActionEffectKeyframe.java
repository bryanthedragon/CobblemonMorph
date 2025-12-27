package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.moves.animations.keyframes

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.moves.animations.ActionEffectContext
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.moves.animations.ActionEffectTimeline
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.moves.animations.ActionEffects
import java.util.concurrent.CompletableFuture
import kotlin.jvm.internal.SourceDebugExtension
import net.minecraft.resources.ResourceLocation

@SourceDebugExtension(["SMAP\nRunActionEffectKeyframe.kt\nKotlin\n*S Kotlin\n*F\n+ 1 RunActionEffectKeyframe.kt\ncom/cobblemon/mod/common/api/moves/animations/keyframes/RunActionEffectKeyframe\n+ 2 fake.kt\nkotlin/jvm/internal/FakeKt\n*L\n1#1,34:1\n1#2:35\n*E\n"])
public class RunActionEffectKeyframe : ConditionalActionEffectKeyframe {
   public final val actionEffect: ResourceLocation?
   public final val waitForActionEffect: Boolean = true

   public override fun playWhenTrue(context: ActionEffectContext): CompletableFuture<Unit> {
      var var7: CompletableFuture;
      if (this.actionEffect != null) {
         val var10000: ActionEffectTimeline = ActionEffects.INSTANCE.getActionEffects().get(this.actionEffect);
         if (var10000 != null) {
            var7 = var10000.run(context);
            if (var7 != null) {
               var7 = if (this.waitForActionEffect) var7 else null;
               if ((if (this.waitForActionEffect) var7 else null) != null) {
                  return var7;
               }
            }
         }

         var7 = this.skip();
      } else {
         var7 = this.skip();
      }

      return var7;
   }
}
