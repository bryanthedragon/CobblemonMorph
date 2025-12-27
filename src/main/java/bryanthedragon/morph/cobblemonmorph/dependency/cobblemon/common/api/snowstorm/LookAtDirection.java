package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.snowstorm

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.MatrixWrapper
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.Vec3ExtensionsKt
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
import org.joml.AxisAngle4d
import org.joml.Quaternionf
import org.joml.Quaternionfc
import org.joml.Vector3f
import org.joml.Vector3fc

public class LookAtDirection : ParticleCameraMode {
   public final val axisAngle: AxisAngle4d = new AxisAngle4d()
   public final val cameraPositionF: Vector3f = new Vector3f(0.0F, 0.0F, 0.0F)
   public final val particlePositionF: Vector3f = new Vector3f(0.0F, 0.0F, 0.0F)
   public open val type: ParticleCameraModeType = ParticleCameraModeType.LOOK_AT_DIRECTION
   public final val viewDirectionF: Vector3f = new Vector3f(0.0F, 0.0F, 0.0F)

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
      Vec3ExtensionsKt.set(this.viewDirectionF, viewDirection);
      Vec3ExtensionsKt.set(this.particlePositionF, particlePosition);
      Vec3ExtensionsKt.set(this.cameraPositionF, cameraPosition);
      new Quaternionf()
         .rotateTo(
            this.particlePositionF.sub(this.cameraPositionF as Vector3fc, new Vector3f()) as Vector3fc,
            this.particlePositionF.add(this.viewDirectionF as Vector3fc, new Vector3f()).sub(this.cameraPositionF as Vector3fc) as Vector3fc
         )
         .get(this.axisAngle);
      val correctY: Vector3f = new Vector3f((float)this.axisAngle.x, (float)this.axisAngle.y, (float)this.axisAngle.z);
      val rotation: Quaternionf = new Quaternionf().rotateTo((new Vector3f(1.0F, 0.0F, 0.0F)) as Vector3fc, this.viewDirectionF as Vector3fc);
      rotation.premul(
         new Quaternionf().rotateTo(new Vector3f(0.0F, 1.0F, 0.0F).rotate(rotation as Quaternionfc) as Vector3fc, correctY as Vector3fc) as Quaternionfc
      );
      val particleAngle: Float = if (angle == 0.0F) 0.0F else Mth.m_14179_(deltaTicks, prevAngle, angle);
      val var10001: Quaternionf = Axis.f_252403_.m_252977_(particleAngle);
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
   fun `CODEC$lambda$2$lambda$0`(it: LookAtDirection): java.lang.String {
      return it.getType().name();
   }

   @JvmStatic
   fun `CODEC$lambda$2$lambda$1`(it: java.lang.String): LookAtDirection {
      return new LookAtDirection();
   }

   @JvmStatic
   fun `CODEC$lambda$2`(instance: Instance): App {
      return instance.group(PrimitiveCodec.STRING.fieldOf("type").forGetter(LookAtDirection::CODEC$lambda$2$lambda$0) as App)
         .apply(instance as Applicative, LookAtDirection::CODEC$lambda$2$lambda$1);
   }

   @JvmStatic
   fun {
      val var10000: Codec = RecordCodecBuilder.create(LookAtDirection::CODEC$lambda$2);
      CODEC = var10000;
   }

   public companion object {
      public final val CODEC: Codec<LookAtDirection>
   }
}
