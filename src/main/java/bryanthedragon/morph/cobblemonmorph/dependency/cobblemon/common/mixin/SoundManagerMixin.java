package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.mixin;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.sound.battle.BattleMusicController;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.duck.SoundManagerDuck;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.duck.SoundSystemDuck;
import net.minecraft.client.resources.sounds.SoundInstance;
import net.minecraft.client.resources.sounds.BiomeAmbientSoundsHandler.LoopSoundInstance;
import net.minecraft.client.resources.sounds.UnderwaterAmbientSoundInstances.UnderwaterAmbientSoundInstance;
import net.minecraft.client.sounds.SoundEngine;
import net.minecraft.client.sounds.SoundManager;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundSource;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(SoundManager.class)
public abstract class SoundManagerMixin implements SoundManagerDuck {
   @Shadow
   @Final
   private SoundEngine f_120349_;

   @Shadow
   public abstract boolean m_120403_(SoundInstance sound);

   private boolean isAmbientLoop(SoundInstance sound) {
      return sound instanceof LoopSoundInstance || sound instanceof UnderwaterAmbientSoundInstance;
   }

   private boolean filterCondition(SoundInstance sound) {
      return !this.isAmbientLoop(sound)
         && this.m_120403_(BattleMusicController.INSTANCE.getMusic())
         && BattleMusicController.INSTANCE.getFilteredCategories().contains(sound.m_8070_());
   }

   private boolean ambientLoopCondition(SoundInstance sound) {
      return this.isAmbientLoop(sound)
         && this.m_120403_(BattleMusicController.INSTANCE.getMusic())
         && BattleMusicController.INSTANCE.getFilteredCategories().contains(sound.m_8070_());
   }

   @Override
   public void pauseSounds(@Nullable ResourceLocation id, @Nullable SoundSource category) {
      ((SoundSystemDuck)this.f_120349_).pauseSounds(id, category);
   }

   @Override
   public void resumeSounds(@Nullable ResourceLocation id, @Nullable SoundSource category) {
      ((SoundSystemDuck)this.f_120349_).resumeSounds(id, category);
   }

   @Inject(method = "play(Lnet/minecraft/client/sound/SoundInstance;)V", at = @At("HEAD"), cancellable = true)
   public void playStart(SoundInstance sound, CallbackInfo cb) {
      if (this.filterCondition(sound)) {
         cb.cancel();
      }
   }

   @Inject(method = "play(Lnet/minecraft/client/sound/SoundInstance;I)V", at = @At("HEAD"), cancellable = true)
   public void playStart(SoundInstance sound, int delay, CallbackInfo cb) {
      if (this.filterCondition(sound)) {
         cb.cancel();
      }
   }

   @Inject(method = "play(Lnet/minecraft/client/sound/SoundInstance;)V", at = @At("TAIL"))
   public void playEnd(SoundInstance sound, CallbackInfo cb) {
      if (this.ambientLoopCondition(sound)) {
         this.pauseSounds(sound.m_7904_(), SoundSource.AMBIENT);
      }
   }

   @Inject(method = "play(Lnet/minecraft/client/sound/SoundInstance;I)V", at = @At("TAIL"))
   public void playEnd(SoundInstance sound, int delay, CallbackInfo cb) {
      if (this.ambientLoopCondition(sound)) {
         this.pauseSounds(sound.m_7904_(), SoundSource.AMBIENT);
      }
   }
}
