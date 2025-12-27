package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.snowstorm

import com.bedrockk.molang.runtime.MoLangRuntime
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.codec.CodecMapped
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.data.ArbitrarilyMappedSerializableCompanion
import net.minecraft.world.phys.Vec3

public interface ParticleViewDirection : CodecMapped {
   public val type: ParticleViewDirectionType

   public abstract fun getDirection(runtime: MoLangRuntime, lastDirection: Vec3, currentVelocity: Vec3): Vec3 {
   }

   public companion object : ArbitrarilyMappedSerializableCompanion(<unrepresentable>.INSTANCE, <unrepresentable>.INSTANCE, <unrepresentable>.INSTANCE)
}
