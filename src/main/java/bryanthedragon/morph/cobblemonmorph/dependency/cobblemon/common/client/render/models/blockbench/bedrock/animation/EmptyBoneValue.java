package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.bedrock.animation

import com.bedrockk.molang.runtime.MoLangRuntime
import net.minecraft.world.phys.Vec3

public object EmptyBoneValue : BedrockBoneValue {
   public override fun resolve(time: Double, runtime: MoLangRuntime): Vec3 {
      return Vec3.f_82478_;
   }

   public override fun isEmpty(): Boolean {
      return true;
   }
}
