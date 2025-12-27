package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.bedrock.animation

import com.bedrockk.molang.Expression
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.PoseableEntityState
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.MoLangExtensionsKt
import kotlin.jvm.internal.SourceDebugExtension
import net.minecraft.world.entity.Entity

@SourceDebugExtension(["SMAP\nBedrockAnimation.kt\nKotlin\n*S Kotlin\n*F\n+ 1 BedrockAnimation.kt\ncom/cobblemon/mod/common/client/render/models/blockbench/bedrock/animation/BedrockInstructionKeyframe\n+ 2 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n*L\n1#1,346:1\n1855#2,2:347\n*S KotlinDebug\n*F\n+ 1 BedrockAnimation.kt\ncom/cobblemon/mod/common/client/render/models/blockbench/bedrock/animation/BedrockInstructionKeyframe\n*L\n134#1:347,2\n*E\n"])
public class BedrockInstructionKeyframe(seconds: Float, expressions: List<Expression>) : BedrockEffectKeyframe(seconds) {
   public final val expressions: List<Expression>

   init {
      this.expressions = expressions;
   }

   public override fun <T : Entity> run(entity: Any, state: PoseableEntityState<Any>) {
      val `$this$forEach$iv`: java.lang.Iterable;
      for (Object element$iv : $this$forEach$iv) {
         MoLangExtensionsKt.resolve(state.getRuntime(), `element$iv` as Expression);
      }
   }
}
