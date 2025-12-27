package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.bedrock.animation

import com.bedrockk.molang.Expression
import com.bedrockk.molang.runtime.MoLangEnvironment
import com.bedrockk.molang.runtime.MoLangRuntime
import com.bedrockk.molang.runtime.value.DoubleValue
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.MoLangExtensionsKt
import net.minecraft.client.Minecraft
import net.minecraft.world.phys.Vec3

public class MolangBoneValue(x: Expression, y: Expression, z: Expression, transformation: Transformation) : BedrockBoneValue {
   public final val x: Expression
   public final val y: Expression
   public final val yMul: Int
   public final val z: Expression

   init {
      this.x = x;
      this.y = y;
      this.z = z;
      this.yMul = if (transformation === Transformation.POSITION) -1 else 1;
   }

   public override fun isEmpty(): Boolean {
      return false;
   }

   public override fun resolve(time: Double, runtime: MoLangRuntime): Vec3 {
      val environment: MoLangEnvironment = runtime.getEnvironment();
      environment.setSimpleVariable("anim_time", new DoubleValue(time));
      environment.setSimpleVariable("camera_rotation_x", new DoubleValue((double)Minecraft.m_91087_().f_91063_.m_109153_().m_253121_().x));
      environment.setSimpleVariable("camera_rotation_y", new DoubleValue((double)Minecraft.m_91087_().f_91063_.m_109153_().m_253121_().y));
      return new Vec3(
         MoLangExtensionsKt.resolveDouble(runtime, this.x),
         MoLangExtensionsKt.resolveDouble(runtime, this.y) * this.yMul,
         MoLangExtensionsKt.resolveDouble(runtime, this.z)
      );
   }
}
