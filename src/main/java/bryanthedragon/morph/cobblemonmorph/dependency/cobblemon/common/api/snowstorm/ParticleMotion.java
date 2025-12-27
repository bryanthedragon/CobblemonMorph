package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.snowstorm

import com.bedrockk.molang.runtime.MoLangRuntime
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.codec.CodecMapped
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.data.ArbitrarilyMappedSerializableCompanion
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.particle.ParticleStorm
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.SnowstormParticle
import net.minecraft.world.phys.Vec3

public interface ParticleMotion : CodecMapped {
   public val type: ParticleMotionType

   public abstract fun getInitialVelocity(runtime: MoLangRuntime, storm: ParticleStorm, particlePos: Vec3, emitterPos: Vec3): Vec3 {
   }

   public abstract fun getVelocity(runtime: MoLangRuntime, particle: SnowstormParticle, velocity: Vec3): Vec3 {
   }

   public abstract fun getParticleDirection(runtime: MoLangRuntime, storm: ParticleStorm, velocity: Vec3, minSpeed: Float): Vec3 {
   }

   public companion object : ArbitrarilyMappedSerializableCompanion(<unrepresentable>.INSTANCE, <unrepresentable>.INSTANCE, <unrepresentable>.INSTANCE)
}
