package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.snowstorm

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.MatrixWrapper
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.math.QuaternionUtilsKt
import com.mojang.datafixers.kinds.App
import com.mojang.datafixers.kinds.Applicative
import com.mojang.math.Axis
import com.mojang.serialization.Codec
import com.mojang.serialization.DataResult
import com.mojang.serialization.DynamicOps
import com.mojang.serialization.codecs.PrimitiveCodec
import com.mojang.serialization.codecs.RecordCodecBuilder
import com.mojang.serialization.codecs.RecordCodecBuilder.Instance
import net.minecraft.network.FriendlyByteBuf
import net.minecraft.world.phys.Vec3
import org.joml.Quaternionf

public class DirectionY : ParticleCameraMode {
   public open val type: ParticleCameraModeType = ParticleCameraModeType.DIRECTION_Y

   public override fun getRotation(
      matrixWrapper: MatrixWrapper,
      prevAngle: Float,
      angle: Float,
      deltaTicks: Float,
      particlePosition: Vec3,
      cameraPosition: Vec3,
      cameraAngle: Quaternionf,
      cameraYaw: Float,
      cameraPitch: Float,
      viewDirection: Vec3
   ): Quaternionf {
      val rotation: Quaternionf = new Quaternionf(0.0F, 0.0F, 0.0F, 1.0F);
      val y: Double = Math.atan2(viewDirection.f_82479_, viewDirection.f_82481_);
      var var10001: Quaternionf = Axis.f_252529_
         .m_252977_(
            (float)Math.atan2(viewDirection.f_82480_, Math.sqrt(Math.pow(viewDirection.f_82479_, 2.0) + Math.pow(viewDirection.f_82481_, 2.0)))
               - (float) (Math.PI / 2)
         );
      QuaternionUtilsKt.hamiltonProduct(rotation, var10001);
      var10001 = Axis.f_252436_.m_252977_((float)y - (float) Math.PI);
      QuaternionUtilsKt.hamiltonProduct(rotation, var10001);
      return rotation;
   }

   public override fun <T> encode(ops: DynamicOps<Any>): DataResult<Any> {
      return CODEC.encodeStart(ops, this);
   }

   public override fun readFromBuffer(buffer: FriendlyByteBuf) {
   }

   public override fun writeToBuffer(buffer: FriendlyByteBuf) {
   }

   @JvmStatic
   fun `CODEC$lambda$2$lambda$0`(it: DirectionY): java.lang.String {
      return it.getType().name();
   }

   @JvmStatic
   fun `CODEC$lambda$2$lambda$1`(it: java.lang.String): DirectionY {
      return new DirectionY();
   }

   @JvmStatic
   fun `CODEC$lambda$2`(instance: Instance): App {
      return instance.group(PrimitiveCodec.STRING.fieldOf("type").forGetter(DirectionY::CODEC$lambda$2$lambda$0) as App)
         .apply(instance as Applicative, DirectionY::CODEC$lambda$2$lambda$1);
   }

   @JvmStatic
   fun {
      val var10000: Codec = RecordCodecBuilder.create(DirectionY::CODEC$lambda$2);
      CODEC = var10000;
   }

   public companion object {
      public final val CODEC: Codec<DirectionY>
   }
}
