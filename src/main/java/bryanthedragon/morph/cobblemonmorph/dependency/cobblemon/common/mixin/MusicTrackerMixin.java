package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.mixin;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.sound.battle.BattleMusicController;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.sounds.SoundInstance;
import net.minecraft.client.sounds.MusicManager;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(MusicManager.class)
public class MusicTrackerMixin {
   @Shadow
   @Final
   private Minecraft f_120178_;
   @Shadow
   @Nullable
   private SoundInstance f_120179_;

   @Inject(method = "tick()V", at = @At("HEAD"), cancellable = true)
   public void tick(CallbackInfo cb) {
      if (this.f_120178_.m_91106_().m_120403_(BattleMusicController.INSTANCE.getMusic())) {
         cb.cancel();
      }
   }
}
