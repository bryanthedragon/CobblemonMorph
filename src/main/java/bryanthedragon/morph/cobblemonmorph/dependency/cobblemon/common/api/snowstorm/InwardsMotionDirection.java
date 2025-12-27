package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.snowstorm

import com.bedrockk.molang.runtime.MoLangRuntime
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.particle.ParticleStorm
import com.mojang.datafixers.kinds.App
import com.mojang.datafixers.kinds.Applicative
import com.mojang.serialization.Codec
import com.mojang.serialization.DataResult
import com.mojang.serialization.DynamicOps
import com.mojang.serialization.codecs.PrimitiveCodec
import com.mojang.serialization.codecs.RecordCodecBuilder
import com.mojang.serialization.codecs.RecordCodecBuilder.Instance
import net.minecraft.network.FriendlyByteBuf
import net.minecraft.world.phys.Vec3

public class InwardsMotionDirection : ParticleMotionDirection {
   public open val type: ParticleMotionDirectionType = ParticleMotionDirectionType.INWARDS

   public override fun getDirectionVector(runtime: MoLangRuntime, storm: ParticleStorm, emitterPos: Vec3, particlePos: Vec3): Vec3 {
      val var10000: Vec3 = (if (particlePos == emitterPos)
            new Vec3(storm.getWorld().f_46441_.m_188500_() - 0.5, storm.getWorld().f_46441_.m_188500_() - 0.5, storm.getWorld().f_46441_.m_188500_() - 0.5)
            else
            emitterPos.m_82546_(particlePos))
         .m_82541_();
      return var10000;
   }

   public override fun <T> encode(ops: DynamicOps<Any>): DataResult<Any> {
      return CODEC.encodeStart(ops, this);
   }

   public override fun readFromBuffer(buffer: FriendlyByteBuf) {
   }

   public override fun writeToBuffer(buffer: FriendlyByteBuf) {
   }

   @JvmStatic
   fun `CODEC$lambda$2$lambda$0`(it: InwardsMotionDirection): java.lang.String {
      return it.getType().name();
   }

   @JvmStatic
   fun `CODEC$lambda$2$lambda$1`(it: java.lang.String): InwardsMotionDirection {
      return new InwardsMotionDirection();
   }

   @JvmStatic
   fun `CODEC$lambda$2`(instance: Instance): App {
      return instance.group(PrimitiveCodec.STRING.fieldOf("type").forGetter(InwardsMotionDirection::CODEC$lambda$2$lambda$0) as App)
         .apply(instance as Applicative, InwardsMotionDirection::CODEC$lambda$2$lambda$1);
   }

   @JvmStatic
   fun {
      val var10000: Codec = RecordCodecBuilder.create(InwardsMotionDirection::CODEC$lambda$2);
      CODEC = var10000;
   }

   public companion object {
      public final val CODEC: Codec<InwardsMotionDirection>
   }
}
