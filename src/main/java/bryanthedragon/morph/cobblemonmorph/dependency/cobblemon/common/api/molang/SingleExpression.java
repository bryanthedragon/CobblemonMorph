package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.molang

import com.bedrockk.molang.Expression
import com.bedrockk.molang.runtime.MoLangRuntime
import com.bedrockk.molang.runtime.value.MoValue
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.MoLangExtensionsKt

public class SingleExpression(expr: Expression) : ExpressionLike {
   public final val expr: Expression

   init {
      this.expr = expr;
   }

   public override fun resolve(runtime: MoLangRuntime): MoValue {
      return MoLangExtensionsKt.resolve(runtime, this.expr);
   }

   override fun resolveDouble(runtime: MoLangRuntime): Double {
      return ExpressionLike.DefaultImpls.resolveDouble(this, runtime);
   }

   override fun resolveFloat(runtime: MoLangRuntime): Float {
      return ExpressionLike.DefaultImpls.resolveFloat(this, runtime);
   }

   override fun resolveString(runtime: MoLangRuntime): java.lang.String {
      return ExpressionLike.DefaultImpls.resolveString(this, runtime);
   }

   override fun resolveInt(runtime: MoLangRuntime): Int {
      return ExpressionLike.DefaultImpls.resolveInt(this, runtime);
   }

   override fun resolveBoolean(runtime: MoLangRuntime): Boolean {
      return ExpressionLike.DefaultImpls.resolveBoolean(this, runtime);
   }

   override fun resolveObject(runtime: MoLangRuntime): ObjectValue<?> {
      return ExpressionLike.DefaultImpls.resolveObject(this, runtime);
   }
}
