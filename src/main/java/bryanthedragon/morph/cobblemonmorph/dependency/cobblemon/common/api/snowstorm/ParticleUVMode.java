package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.snowstorm

import com.bedrockk.molang.Expression
import com.bedrockk.molang.ast.NumberExpression
import com.bedrockk.molang.runtime.MoLangRuntime
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.codec.CodecMapped
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.data.ArbitrarilyMappedSerializableCompanion

public abstract class ParticleUVMode : CodecMapped {
   public open var startU: Expression = (new NumberExpression(0.0)) as Expression
   public open var startV: Expression = (new NumberExpression(0.0)) as Expression
   public open var textureSizeX: Int = 8
   public open var textureSizeY: Int = 8
   public abstract val type: ParticleUVModeType
   public open var uSize: Expression = (new NumberExpression(8.0)) as Expression
   public open var vSize: Expression = (new NumberExpression(8.0)) as Expression

   public abstract fun get(moLangRuntime: MoLangRuntime, age: Double, maxAge: Double, uvDetails: UVDetails): UVDetails {
   }

   @JvmStatic
   fun {
      Companion.registerSubtype(ParticleUVModeType.ANIMATED, AnimatedParticleUVMode::class.java, AnimatedParticleUVMode.Companion.getCODEC());
      Companion.registerSubtype(ParticleUVModeType.STATIC, StaticParticleUVMode::class.java, StaticParticleUVMode.Companion.getCODEC());
   }

   public companion object : ArbitrarilyMappedSerializableCompanion(<unrepresentable>.INSTANCE, <unrepresentable>.INSTANCE, <unrepresentable>.INSTANCE)
}
