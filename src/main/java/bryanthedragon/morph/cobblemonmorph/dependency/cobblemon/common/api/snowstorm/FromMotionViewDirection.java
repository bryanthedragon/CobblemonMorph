package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.snowstorm

import com.bedrockk.molang.runtime.MoLangRuntime
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

public class FromMotionViewDirection(minSpeed: Double = 0.01) : ParticleViewDirection {
   public final var minSpeed: Double
   public open val type: ParticleViewDirectionType

   init {
      this.minSpeed = minSpeed;
      this.type = ParticleViewDirectionType.FROM_MOTION;
   }

   public override fun <T> encode(ops: DynamicOps<Any>): DataResult<Any> {
      return CODEC.encodeStart(ops, this);
   }

   public override fun writeToBuffer(buffer: FriendlyByteBuf) {
      buffer.writeDouble(this.minSpeed);
   }

   public override fun readFromBuffer(buffer: FriendlyByteBuf) {
      this.minSpeed = buffer.readDouble();
   }

   public override fun getDirection(runtime: MoLangRuntime, lastDirection: Vec3, currentVelocity: Vec3): Vec3 {
      val var10000: Vec3;
      if (currentVelocity.m_82553_() * 20 >= this.minSpeed) {
         var10000 = currentVelocity.m_82541_();
      } else {
         var10000 = lastDirection;
      }

      return var10000;
   }

   @JvmStatic
   fun `CODEC$lambda$3$lambda$0`(it: FromMotionViewDirection): java.lang.String {
      return it.getType().name();
   }

   @JvmStatic
   fun `CODEC$lambda$3$lambda$1`(it: FromMotionViewDirection): java.lang.Double {
      return it.minSpeed;
   }

   @JvmStatic
   fun `CODEC$lambda$3$lambda$2`(var0: java.lang.String, minSpeed: java.lang.Double): FromMotionViewDirection {
      return new FromMotionViewDirection(minSpeed);
   }

   @JvmStatic
   fun `CODEC$lambda$3`(instance: Instance): App {
      return instance.group(
            PrimitiveCodec.STRING.fieldOf("type").forGetter(FromMotionViewDirection::CODEC$lambda$3$lambda$0) as App,
            PrimitiveCodec.DOUBLE.fieldOf("minSpeed").forGetter(FromMotionViewDirection::CODEC$lambda$3$lambda$1) as App
         )
         .apply(instance as Applicative, FromMotionViewDirection::CODEC$lambda$3$lambda$2);
   }

   fun FromMotionViewDirection() {
      this(0.0, 1, null);
   }

   @JvmStatic
   fun {
      val var10000: Codec = RecordCodecBuilder.create(FromMotionViewDirection::CODEC$lambda$3);
      CODEC = var10000;
   }

   public companion object {
      public final val CODEC: Codec<FromMotionViewDirection>
   }
}
