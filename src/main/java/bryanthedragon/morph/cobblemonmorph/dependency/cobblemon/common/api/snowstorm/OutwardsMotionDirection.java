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

public class OutwardsMotionDirection : ParticleMotionDirection {
   public open val type: ParticleMotionDirectionType = ParticleMotionDirectionType.OUTWARDS

   public override fun getDirectionVector(runtime: MoLangRuntime, storm: ParticleStorm, emitterPos: Vec3, particlePos: Vec3): Vec3 {
      val var10000: Vec3 = (if (particlePos == emitterPos)
            new Vec3(storm.getWorld().f_46441_.m_188500_() - 0.5, storm.getWorld().f_46441_.m_188500_() - 0.5, storm.getWorld().f_46441_.m_188500_() - 0.5)
            else
            particlePos.m_82546_(emitterPos))
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
   fun `CODEC$lambda$2$lambda$0`(it: OutwardsMotionDirection): java.lang.String {
      return it.getType().name();
   }

   @JvmStatic
   fun `CODEC$lambda$2$lambda$1`(it: java.lang.String): OutwardsMotionDirection {
      return new OutwardsMotionDirection();
   }

   @JvmStatic
   fun `CODEC$lambda$2`(instance: Instance): App {
      return instance.group(PrimitiveCodec.STRING.fieldOf("type").forGetter(OutwardsMotionDirection::CODEC$lambda$2$lambda$0) as App)
         .apply(instance as Applicative, OutwardsMotionDirection::CODEC$lambda$2$lambda$1);
   }

   @JvmStatic
   fun {
      val var10000: Codec = RecordCodecBuilder.create(OutwardsMotionDirection::CODEC$lambda$2);
      CODEC = var10000;
   }

   public companion object {
      public final val CODEC: Codec<OutwardsMotionDirection>
   }
}
