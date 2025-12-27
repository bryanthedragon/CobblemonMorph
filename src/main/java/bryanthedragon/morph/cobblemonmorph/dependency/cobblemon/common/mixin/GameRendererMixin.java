package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.mixin;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.scheduling.ClientTaskTracker;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GameRenderer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(GameRenderer.class)
public class GameRendererMixin {
   @Unique
   long lastTime = -1L;

   @Inject(method = "render", at = @At("TAIL"))
   public void render(float tickDelta, long startTime, boolean tick, CallbackInfo ci) {
      Minecraft client = Minecraft.m_91087_();
      long newTime = System.currentTimeMillis();
      if (client.m_91104_()) {
         this.lastTime = newTime;
      } else {
         if (this.lastTime != -1L) {
            ClientTaskTracker.INSTANCE.update((float)(newTime - this.lastTime) / 1000.0F);
         }

         this.lastTime = newTime;
      }
   }
}
