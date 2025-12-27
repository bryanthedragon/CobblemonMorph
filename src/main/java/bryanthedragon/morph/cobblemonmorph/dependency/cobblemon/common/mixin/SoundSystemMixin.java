package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.mixin;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.sound.battle.BattleMusicController;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.duck.SoundSystemDuck;
import com.google.common.collect.Multimap;
import com.mojang.blaze3d.audio.Channel;
import java.util.Map;
import net.minecraft.client.resources.sounds.SoundInstance;
import net.minecraft.client.sounds.SoundEngine;
import net.minecraft.client.sounds.ChannelAccess.ChannelHandle;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundSource;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(SoundEngine.class)
public abstract class SoundSystemMixin implements SoundSystemDuck {
   @Shadow
   private boolean f_120219_;
   @Shadow
   private Multimap<SoundSource, SoundInstance> f_120227_;
   @Shadow
   @Final
   private Map<SoundInstance, ChannelHandle> f_120226_;

   @Shadow
   protected abstract void m_120274_(SoundInstance sound);

   @Shadow
   public abstract boolean m_120305_(SoundInstance sound);

   private void resume(SoundInstance sound) {
      ChannelHandle sourceManager = this.f_120226_.get(sound);
      if (this.f_120219_ && sourceManager != null) {
         sourceManager.m_120154_(Channel::m_83678_);
      }
   }

   private void pause(SoundInstance sound) {
      ChannelHandle sourceManager = this.f_120226_.get(sound);
      if (this.f_120219_ && sourceManager != null) {
         sourceManager.m_120154_(Channel::m_83677_);
      }
   }

   @Override
   public void resumeSounds(@Nullable ResourceLocation id, @Nullable SoundSource category) {
      if (category != null) {
         this.f_120227_.get(category).forEach(sound -> {
            if (id == null || sound.m_7904_().equals(id)) {
               this.resume(sound);
            }
         });
      } else if (id == null) {
         this.f_120226_.keySet().forEach(this::resume);
      } else {
         this.f_120226_.keySet().forEach(sound -> {
            if (sound.m_7904_().equals(id)) {
               this.resume(sound);
            }
         });
      }
   }

   @Override
   public void pauseSounds(@Nullable ResourceLocation id, @Nullable SoundSource category) {
      if (category != null) {
         this.f_120227_.get(category).forEach(sound -> {
            if (id == null || sound.m_7904_().equals(id)) {
               this.pause(sound);
            }
         });
      } else if (id == null) {
         this.f_120226_.keySet().forEach(this::pause);
      } else {
         this.f_120226_.keySet().forEach(sound -> {
            if (sound.m_7904_().equals(id)) {
               this.pause(sound);
            }
         });
      }
   }

   @Inject(method = "stopSounds(Lnet/minecraft/util/Identifier;Lnet/minecraft/sound/SoundCategory;)V", at = @At("HEAD"), cancellable = true)
   public void stopSounds(@Nullable ResourceLocation id, @Nullable SoundSource category, CallbackInfo cb) {
      if (id == null && category != null) {
         this.f_120227_.get(category).forEach(this::m_120274_);
         cb.cancel();
      }
   }

   @Inject(method = "resumeAll()V", at = @At("HEAD"), cancellable = true)
   public void resumeAll(CallbackInfo cb) {
      if (this.m_120305_(BattleMusicController.INSTANCE.getMusic())) {
         this.f_120227_.values().forEach(sound -> {
            if (sound == BattleMusicController.INSTANCE.getMusic() || !BattleMusicController.INSTANCE.getFilteredCategories().contains(sound.m_8070_())) {
               this.resume(sound);
            }
         });
         cb.cancel();
      }
   }
}
