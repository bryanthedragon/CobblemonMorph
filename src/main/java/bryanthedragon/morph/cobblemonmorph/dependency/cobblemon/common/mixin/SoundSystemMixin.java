/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.Multimap
 *  com.mojang.blaze3d.audio.Channel
 *  net.minecraft.client.resources.sounds.SoundInstance
 *  net.minecraft.client.sounds.ChannelAccess$ChannelHandle
 *  net.minecraft.client.sounds.SoundEngine
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.sounds.SoundSource
 *  org.jetbrains.annotations.Nullable
 *  org.spongepowered.asm.mixin.Final
 *  org.spongepowered.asm.mixin.Mixin
 *  org.spongepowered.asm.mixin.Shadow
 *  org.spongepowered.asm.mixin.injection.At
 *  org.spongepowered.asm.mixin.injection.Inject
 *  org.spongepowered.asm.mixin.injection.callback.CallbackInfo
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.mixin;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.sound.battle.BattleMusicController;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.duck.SoundSystemDuck;
import com.google.common.collect.Multimap;
import com.mojang.blaze3d.audio.Channel;
import java.util.Map;
import net.minecraft.client.resources.sounds.SoundInstance;
import net.minecraft.client.sounds.ChannelAccess;
import net.minecraft.client.sounds.SoundEngine;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundSource;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value={SoundEngine.class})
public abstract class SoundSystemMixin
implements SoundSystemDuck {
    @Shadow
    private boolean f_120219_;
    @Shadow
    private Multimap<SoundSource, SoundInstance> f_120227_;
    @Shadow
    @Final
    private Map<SoundInstance, ChannelAccess.ChannelHandle> f_120226_;

    @Shadow
    protected abstract void m_120274_(SoundInstance var1);

    @Shadow
    public abstract boolean m_120305_(SoundInstance var1);

    private void resume(SoundInstance sound2) {
        ChannelAccess.ChannelHandle sourceManager = this.f_120226_.get(sound2);
        if (this.f_120219_ && sourceManager != null) {
            sourceManager.m_120154_(Channel::m_83678_);
        }
    }

    private void pause(SoundInstance sound2) {
        ChannelAccess.ChannelHandle sourceManager = this.f_120226_.get(sound2);
        if (this.f_120219_ && sourceManager != null) {
            sourceManager.m_120154_(Channel::m_83677_);
        }
    }

    @Override
    public void resumeSounds(@Nullable ResourceLocation id, @Nullable SoundSource category) {
        if (category != null) {
            this.f_120227_.get((Object)category).forEach(sound2 -> {
                if (id == null || sound2.m_7904_().equals((Object)id)) {
                    this.resume((SoundInstance)sound2);
                }
            });
        } else if (id == null) {
            this.f_120226_.keySet().forEach(this::resume);
        } else {
            this.f_120226_.keySet().forEach(sound2 -> {
                if (sound2.m_7904_().equals((Object)id)) {
                    this.resume((SoundInstance)sound2);
                }
            });
        }
    }

    @Override
    public void pauseSounds(@Nullable ResourceLocation id, @Nullable SoundSource category) {
        if (category != null) {
            this.f_120227_.get((Object)category).forEach(sound2 -> {
                if (id == null || sound2.m_7904_().equals((Object)id)) {
                    this.pause((SoundInstance)sound2);
                }
            });
        } else if (id == null) {
            this.f_120226_.keySet().forEach(this::pause);
        } else {
            this.f_120226_.keySet().forEach(sound2 -> {
                if (sound2.m_7904_().equals((Object)id)) {
                    this.pause((SoundInstance)sound2);
                }
            });
        }
    }

    @Inject(method={"stopSounds(Lnet/minecraft/util/Identifier;Lnet/minecraft/sound/SoundCategory;)V"}, at={@At(value="HEAD")}, cancellable=true)
    public void stopSounds(@Nullable ResourceLocation id, @Nullable SoundSource category, CallbackInfo cb) {
        if (id == null && category != null) {
            this.f_120227_.get((Object)category).forEach(this::m_120274_);
            cb.cancel();
        }
    }

    @Inject(method={"resumeAll()V"}, at={@At(value="HEAD")}, cancellable=true)
    public void resumeAll(CallbackInfo cb) {
        if (this.m_120305_((SoundInstance)BattleMusicController.INSTANCE.getMusic())) {
            this.f_120227_.values().forEach(sound2 -> {
                if (sound2 == BattleMusicController.INSTANCE.getMusic() || !BattleMusicController.INSTANCE.getFilteredCategories().contains(sound2.m_8070_())) {
                    this.resume((SoundInstance)sound2);
                }
            });
            cb.cancel();
        }
    }
}

