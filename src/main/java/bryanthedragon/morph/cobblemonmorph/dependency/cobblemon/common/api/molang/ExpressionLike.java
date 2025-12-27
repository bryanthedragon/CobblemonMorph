package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.molang

import com.bedrockk.molang.runtime.MoLangRuntime
import com.bedrockk.molang.runtime.value.MoValue

public interface ExpressionLike {
   public abstract fun resolve(runtime: MoLangRuntime): MoValue {
   }

   public open fun resolveDouble(runtime: MoLangRuntime): Double {
   }

   public open fun resolveFloat(runtime: MoLangRuntime): Float {
   }

   public open fun resolveString(runtime: MoLangRuntime): String {
   }

   public open fun resolveInt(runtime: MoLangRuntime): Int {
   }

   public open fun resolveBoolean(runtime: MoLangRuntime): Boolean {
   }

   public open fun resolveObject(runtime: MoLangRuntime): ObjectValue<*> {
   }

   // $VF: Class flags could not be determined
   internal class DefaultImpls {
      @JvmStatic
      fun resolveDouble(`$this`: ExpressionLike, runtime: MoLangRuntime): Double {
         return `$this`.resolve(runtime).asDouble();
      }

      @JvmStatic
      fun resolveFloat(`$this`: ExpressionLike, runtime: MoLangRuntime): Float {
         return (float)`$this`.resolveDouble(runtime);
      }

      @JvmStatic
      fun resolveString(`$this`: ExpressionLike, runtime: MoLangRuntime): java.lang.String {
         return `$this`.resolve(runtime).asString();
      }

      @JvmStatic
      fun resolveInt(`$this`: ExpressionLike, runtime: MoLangRuntime): Int {
         return (int)`$this`.resolveDouble(runtime);
      }

      @JvmStatic
      fun resolveBoolean(`$this`: ExpressionLike, runtime: MoLangRuntime): Boolean {
         return `$this`.resolveDouble(runtime) == 1.0;
      }

      @JvmStatic
      fun resolveObject(`$this`: ExpressionLike, runtime: MoLangRuntime): ObjectValue<?> {
         val var10000: MoValue = `$this`.resolve(runtime);
         return var10000 as ObjectValue<?>;
      }
   }
}
