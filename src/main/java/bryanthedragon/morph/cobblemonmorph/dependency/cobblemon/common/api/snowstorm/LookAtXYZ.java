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

public class LookAtXYZ : ParticleCameraMode {
   public open val type: ParticleCameraModeType = ParticleCameraModeType.LOOK_AT_XYZ

   public override fun <T> encode(ops: DynamicOps<Any>): DataResult<Any> {
      return CODEC.encodeStart(ops, this);
   }

   public override fun readFromBuffer(buffer: FriendlyByteBuf) {
   }

   public override fun writeToBuffer(buffer: FriendlyByteBuf) {
   }

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
      val rotation: Quaternionf = new Quaternionf();
      var var10001: Quaternionf = Axis.f_252436_.m_252977_(-cameraYaw);
      QuaternionUtilsKt.hamiltonProduct(rotation, var10001);
      var10001 = Axis.f_252529_.m_252977_(cameraPitch);
      QuaternionUtilsKt.hamiltonProduct(rotation, var10001);
      var10001 = Axis.f_252403_.m_252977_(i);
      QuaternionUtilsKt.hamiltonProduct(rotation, var10001);
      return rotation;
   }

   @JvmStatic
   fun `CODEC$lambda$2$lambda$0`(it: LookAtXYZ): java.lang.String {
      return it.getType().name();
   }

   @JvmStatic
   fun `CODEC$lambda$2$lambda$1`(it: java.lang.String): LookAtXYZ {
      return new LookAtXYZ();
   }

   @JvmStatic
   fun `CODEC$lambda$2`(instance: Instance): App {
      return instance.group(PrimitiveCodec.STRING.fieldOf("type").forGetter(LookAtXYZ::CODEC$lambda$2$lambda$0) as App)
         .apply(instance as Applicative, LookAtXYZ::CODEC$lambda$2$lambda$1);
   }

   @JvmStatic
   fun {
      val var10000: Codec = RecordCodecBuilder.create(LookAtXYZ::CODEC$lambda$2);
      CODEC = var10000;
   }

   public companion object {
      public final val CODEC: Codec<LookAtXYZ>
   }
}
