package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.rules.selector

import com.bedrockk.molang.Expression
import com.bedrockk.molang.runtime.MoLangRuntime
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.molang.MoLangFunctions
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.context.SpawningContext
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.MoLangExtensionsKt

public class ExpressionSpawningContextSelector : SpawningContextSelector {
   public final var expression: Expression = MoLangExtensionsKt.asExpression("true")
   public final val runtime: MoLangRuntime = MoLangFunctions.INSTANCE.setup(new MoLangRuntime())

   public override fun selects(ctx: SpawningContext): Boolean {
      this.runtime.getEnvironment().setSimpleVariable("context", ctx.getOrSetupStruct());
      val var10000: MoLangRuntime = this.runtime;
      val var10001: Expression = this.expression;
      return MoLangExtensionsKt.resolveBoolean(var10000, var10001);
   }
}
