/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.resources.sounds.BiomeAmbientSoundsHandler$LoopSoundInstance
 *  net.minecraft.client.resources.sounds.SoundInstance
 *  net.minecraft.client.resources.sounds.UnderwaterAmbientSoundInstances$UnderwaterAmbientSoundInstance
 *  net.minecraft.client.sounds.SoundEngine
 *  net.minecraft.client.sounds.SoundManager
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
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.duck.SoundManagerDuck;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.duck.SoundSystemDuck;
import net.minecraft.client.resources.sounds.BiomeAmbientSoundsHandler;
import net.minecraft.client.resources.sounds.SoundInstance;
import net.minecraft.client.resources.sounds.UnderwaterAmbientSoundInstances;
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

@Mixin(value={SoundManager.class})
public abstract class SoundManagerMixin
implements SoundManagerDuck {
    @Shadow
    @Final
    private SoundEngine f_120349_;

    @Shadow
    public abstract boolean m_120403_(SoundInstance var1);

    private boolean isAmbientLoop(SoundInstance sound2) {
        return sound2 instanceof BiomeAmbientSoundsHandler.LoopSoundInstance || sound2 instanceof UnderwaterAmbientSoundInstances.UnderwaterAmbientSoundInstance;
    }

    private boolean filterCondition(SoundInstance sound2) {
        return !this.isAmbientLoop(sound2) && this.m_120403_((SoundInstance)BattleMusicController.INSTANCE.getMusic()) && BattleMusicController.INSTANCE.getFilteredCategories().contains(sound2.m_8070_());
    }

    private boolean ambientLoopCondition(SoundInstance sound2) {
        return this.isAmbientLoop(sound2) && this.m_120403_((SoundInstance)BattleMusicController.INSTANCE.getMusic()) && BattleMusicController.INSTANCE.getFilteredCategories().contains(sound2.m_8070_());
    }

    @Override
    public void pauseSounds(@Nullable ResourceLocation id, @Nullable SoundSource category) {
        ((SoundSystemDuck)this.f_120349_).pauseSounds(id, category);
    }

    @Override
    public void resumeSounds(@Nullable ResourceLocation id, @Nullable SoundSource category) {
        ((SoundSystemDuck)this.f_120349_).resumeSounds(id, category);
    }

    @Inject(method={"play(Lnet/minecraft/client/sound/SoundInstance;)V"}, at={@At(value="HEAD")}, cancellable=true)
    public void playStart(SoundInstance sound2, CallbackInfo cb) {
        if (this.filterCondition(sound2)) {
            cb.cancel();
        }
    }

    @Inject(method={"play(Lnet/minecraft/client/sound/SoundInstance;I)V"}, at={@At(value="HEAD")}, cancellable=true)
    public void playStart(SoundInstance sound2, int delay, CallbackInfo cb) {
        if (this.filterCondition(sound2)) {
            cb.cancel();
        }
    }

    @Inject(method={"play(Lnet/minecraft/client/sound/SoundInstance;)V"}, at={@At(value="TAIL")})
    public void playEnd(SoundInstance sound2, CallbackInfo cb) {
        if (this.ambientLoopCondition(sound2)) {
            this.pauseSounds(sound2.m_7904_(), SoundSource.AMBIENT);
        }
    }

    @Inject(method={"play(Lnet/minecraft/client/sound/SoundInstance;I)V"}, at={@At(value="TAIL")})
    public void playEnd(SoundInstance sound2, int delay, CallbackInfo cb) {
        if (this.ambientLoopCondition(sound2)) {
            this.pauseSounds(sound2.m_7904_(), SoundSource.AMBIENT);
        }
    }
}

