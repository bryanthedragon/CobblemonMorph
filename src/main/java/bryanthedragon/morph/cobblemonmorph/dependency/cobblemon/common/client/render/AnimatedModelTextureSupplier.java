package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render

import net.minecraft.resources.ResourceLocation

public class AnimatedModelTextureSupplier(loop: Boolean, fps: Float, frames: List<ResourceLocation>) : ModelTextureSupplier {
   public final val fps: Float
   public final val frames: List<ResourceLocation>
   public final val loop: Boolean

   init {
      this.loop = loop;
      this.fps = fps;
      this.frames = frames;
   }

   public override operator fun invoke(animationSeconds: Float): ResourceLocation {
      val frameIndex: Int = (int)((float)Math.floor((double)(animationSeconds * this.fps)));
      return if (frameIndex >= this.frames.size() && !this.loop)
         CollectionsKt.last(this.frames) as ResourceLocation
         else
         this.frames.get(frameIndex % this.frames.size());
   }
}
