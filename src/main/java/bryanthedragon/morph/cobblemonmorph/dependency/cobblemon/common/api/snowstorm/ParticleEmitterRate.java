package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.snowstorm

import com.bedrockk.molang.runtime.MoLangRuntime
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.codec.CodecMapped
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.data.ArbitrarilyMappedSerializableCompanion

public interface ParticleEmitterRate : CodecMapped {
   public val type: ParticleEmitterRateType

   public abstract fun getEmitCount(runtime: MoLangRuntime, started: Boolean, currentlyActive: Int): Int {
   }

   public companion object : ArbitrarilyMappedSerializableCompanion(<unrepresentable>.INSTANCE, <unrepresentable>.INSTANCE, <unrepresentable>.INSTANCE) {
      public const val OVERFLOW_VARIABLE: String = "emitter_overflow"

      @JvmStatic
      fun {
         $$INSTANCE.registerSubtype(ParticleEmitterRateType.INSTANT, InstantParticleEmitterRate::class.java, InstantParticleEmitterRate.Companion.getCODEC());
         $$INSTANCE.registerSubtype(ParticleEmitterRateType.STEADY, SteadyParticleEmitterRate::class.java, SteadyParticleEmitterRate.Companion.getCODEC());
      }
   }
}
