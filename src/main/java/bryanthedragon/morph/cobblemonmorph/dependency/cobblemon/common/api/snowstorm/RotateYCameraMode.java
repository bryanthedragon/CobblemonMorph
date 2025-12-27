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
import net.minecraft.util.Mth
import net.minecraft.world.phys.Vec3
import org.joml.Quaternionf

public class RotateYCameraMode : ParticleCameraMode {
   public open val type: ParticleCameraModeType = ParticleCameraModeType.ROTATE_Y

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
      val i: Float = if (angle == 0.0F) 0.0F else Mth.m_14179_(deltaTicks, prevAngle, angle);
      val q2: Quaternionf = Axis.f_252436_.m_252977_(-cameraYaw);
      val var10001: Quaternionf = Axis.f_252403_.m_252977_(i);
      QuaternionUtilsKt.hamiltonProduct(q2, var10001);
      return q2;
   }

   public override fun <T> encode(ops: DynamicOps<Any>): DataResult<Any> {
      return CODEC.encodeStart(ops, this);
   }

   public override fun readFromBuffer(buffer: FriendlyByteBuf) {
   }

   public override fun writeToBuffer(buffer: FriendlyByteBuf) {
   }

   @JvmStatic
   fun `CODEC$lambda$2$lambda$0`(it: RotateYCameraMode): java.lang.String {
      return it.getType().name();
   }

   @JvmStatic
   fun `CODEC$lambda$2$lambda$1`(it: java.lang.String): RotateYCameraMode {
      return new RotateYCameraMode();
   }

   @JvmStatic
   fun `CODEC$lambda$2`(instance: Instance): App {
      return instance.group(PrimitiveCodec.STRING.fieldOf("type").forGetter(RotateYCameraMode::CODEC$lambda$2$lambda$0) as App)
         .apply(instance as Applicative, RotateYCameraMode::CODEC$lambda$2$lambda$1);
   }

   @JvmStatic
   fun {
      val var10000: Codec = RecordCodecBuilder.create(RotateYCameraMode::CODEC$lambda$2);
      CODEC = var10000;
   }

   public companion object {
      public final val CODEC: Codec<RotateYCameraMode>
   }
}
