package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.snowstorm

import com.bedrockk.molang.runtime.MoLangRuntime
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.codec.CodecMapped
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.data.ArbitrarilyMappedSerializableCompanion
import net.minecraft.world.entity.Entity
import net.minecraft.world.phys.Vec3

public interface ParticleEmitterShape : CodecMapped {
   public val type: ParticleEmitterShapeType

   public abstract fun getNewParticlePosition(runtime: MoLangRuntime, entity: Entity?): Vec3 {
   }

   public abstract fun getCenter(runtime: MoLangRuntime, entity: Entity?): Vec3 {
   }

   public companion object : ArbitrarilyMappedSerializableCompanion(<unrepresentable>.INSTANCE, <unrepresentable>.INSTANCE, <unrepresentable>.INSTANCE)
}
