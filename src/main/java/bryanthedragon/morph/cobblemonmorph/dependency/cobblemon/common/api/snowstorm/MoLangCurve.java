package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.snowstorm

import com.bedrockk.molang.Expression
import com.bedrockk.molang.runtime.MoLangRuntime
import com.bedrockk.molang.runtime.value.DoubleValue
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.codec.CodecMapped
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.data.ArbitrarilyMappedSerializableCompanion
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.MoLangExtensionsKt

public interface MoLangCurve : CodecMapped {
   public var input: Expression
   public var name: String
   public val type: CurveType

   public abstract fun resolve(runtime: MoLangRuntime, inputValue: Double): Double {
   }

   public open fun apply(runtime: MoLangRuntime) {
   }

   public companion object : ArbitrarilyMappedSerializableCompanion(<unrepresentable>.INSTANCE, <unrepresentable>.INSTANCE, <unrepresentable>.INSTANCE)

   // $VF: Class flags could not be determined
   internal class DefaultImpls {
      @JvmStatic
      fun apply(`$this`: MoLangCurve, runtime: MoLangRuntime) {
         runtime.getEnvironment()
            .setSimpleVariable(`$this`.getName(), new DoubleValue(`$this`.resolve(runtime, MoLangExtensionsKt.resolveDouble(runtime, `$this`.getInput()))));
      }
   }
}
