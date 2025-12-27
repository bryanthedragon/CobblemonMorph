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
import org.joml.AxisAngle4d
import org.joml.Quaternionf
import org.joml.Quaternionfc

public class EmitterXYPlane : ParticleCameraMode {
   public open val type: ParticleCameraModeType = ParticleCameraModeType.EMITTER_XY_PLANE

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
      val quat: AxisAngle4d = new AxisAngle4d(rotation as Quaternionfc);
      matrixWrapper.getMatrix().getRotation(quat);
      rotation.set(quat);
      val var10001: Quaternionf = Axis.f_252436_.m_252977_(180.0F);
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
   fun `CODEC$lambda$2$lambda$0`(it: EmitterXYPlane): java.lang.String {
      return it.getType().name();
   }

   @JvmStatic
   fun `CODEC$lambda$2$lambda$1`(it: java.lang.String): EmitterXYPlane {
      return new EmitterXYPlane();
   }

   @JvmStatic
   fun `CODEC$lambda$2`(instance: Instance): App {
      return instance.group(PrimitiveCodec.STRING.fieldOf("type").forGetter(EmitterXYPlane::CODEC$lambda$2$lambda$0) as App)
         .apply(instance as Applicative, EmitterXYPlane::CODEC$lambda$2$lambda$1);
   }

   @JvmStatic
   fun {
      val var10000: Codec = RecordCodecBuilder.create(EmitterXYPlane::CODEC$lambda$2);
      CODEC = var10000;
   }

   public companion object {
      public final val CODEC: Codec<EmitterXYPlane>
   }
}
