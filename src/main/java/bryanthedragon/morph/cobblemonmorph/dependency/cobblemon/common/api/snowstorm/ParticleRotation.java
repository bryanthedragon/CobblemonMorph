package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.snowstorm

import com.bedrockk.molang.runtime.MoLangRuntime
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.codec.CodecMapped
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.data.ArbitrarilyMappedSerializableCompanion

public interface ParticleRotation : CodecMapped {
   public val type: ParticleRotationType

   public abstract fun getInitialRotation(runtime: MoLangRuntime): Double {
   }

   public abstract fun getInitialAngularVelocity(runtime: MoLangRuntime): Double {
   }

   public abstract fun getAngularVelocity(runtime: MoLangRuntime, angle: Double, angularVelocity: Double): Double {
   }

   public companion object : ArbitrarilyMappedSerializableCompanion(<unrepresentable>.INSTANCE, <unrepresentable>.INSTANCE, <unrepresentable>.INSTANCE)
}
