package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.bedrock.animation

import com.bedrockk.molang.runtime.MoLangRuntime
import net.minecraft.world.phys.Vec3

public interface BedrockBoneValue {
   public abstract fun resolve(time: Double, runtime: MoLangRuntime): Vec3 {
   }

   public abstract fun isEmpty(): Boolean {
   }
}
