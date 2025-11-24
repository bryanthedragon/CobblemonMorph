/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  kotlin.Metadata
 *  kotlin.jvm.internal.DefaultConstructorMarker
 *  kotlin.jvm.internal.Intrinsics
 *  kotlin.jvm.internal.SourceDebugExtension
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.resources.sounds.SimpleSoundInstance
 *  net.minecraft.client.resources.sounds.SoundInstance
 *  net.minecraft.client.resources.sounds.SoundInstance$Attenuation
 *  net.minecraft.client.resources.sounds.TickableSoundInstance
 *  net.minecraft.client.sounds.SoundManager
 *  net.minecraft.core.BlockPos
 *  net.minecraft.sounds.SoundEvent
 *  net.minecraft.sounds.SoundSource
 *  net.minecraft.util.Mth
 *  org.jetbrains.annotations.NotNull
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.sound.battle;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.sound.battle.BattleMusicController;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.SoundExtensionsKt;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.sounds.SimpleSoundInstance;
import net.minecraft.client.resources.sounds.SoundInstance;
import net.minecraft.client.resources.sounds.TickableSoundInstance;
import net.minecraft.client.sounds.SoundManager;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000B\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0006\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\u0006\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0007\n\u0002\b\u0005\u0018\u00002\u00020\u00012\u00020\u0002B#\u0012\u0006\u0010\u001a\u001a\u00020\u0019\u0012\b\b\u0002\u0010\u001c\u001a\u00020\u001b\u0012\b\b\u0002\u0010\u001d\u001a\u00020\u001b\u00a2\u0006\u0004\b\u001e\u0010\u001fJ\u000f\u0010\u0004\u001a\u00020\u0003H\u0016\u00a2\u0006\u0004\b\u0004\u0010\u0005J\r\u0010\u0007\u001a\u00020\u0006\u00a2\u0006\u0004\b\u0007\u0010\bJ\u000f\u0010\t\u001a\u00020\u0006H\u0016\u00a2\u0006\u0004\b\t\u0010\bR\u0016\u0010\n\u001a\u00020\u00038\u0002@\u0002X\u0082\u000e\u00a2\u0006\u0006\n\u0004\b\n\u0010\u000bR\u0016\u0010\f\u001a\u00020\u00038\u0002@\u0002X\u0082\u000e\u00a2\u0006\u0006\n\u0004\b\f\u0010\u000bR\u0016\u0010\u000e\u001a\u00020\r8\u0002@\u0002X\u0082\u000e\u00a2\u0006\u0006\n\u0004\b\u000e\u0010\u000fR\u0014\u0010\u0011\u001a\u00020\u00108\u0002X\u0082D\u00a2\u0006\u0006\n\u0004\b\u0011\u0010\u0012R\u0016\u0010\u0013\u001a\u00020\u00108\u0002@\u0002X\u0082\u000e\u00a2\u0006\u0006\n\u0004\b\u0013\u0010\u0012R\u001c\u0010\u0016\u001a\n \u0015*\u0004\u0018\u00010\u00140\u00148\u0002X\u0082\u0004\u00a2\u0006\u0006\n\u0004\b\u0016\u0010\u0017R\u0016\u0010\u0018\u001a\u00020\r8\u0002@\u0002X\u0082\u000e\u00a2\u0006\u0006\n\u0004\b\u0018\u0010\u000f\u00a8\u0006 "}, d2={"Lcom/cobblemon/mod/common/client/sound/battle/BattleMusicInstance;", "Lnet/minecraft/client/resources/sounds/SimpleSoundInstance;", "Lnet/minecraft/client/resources/sounds/TickableSoundInstance;", "", "isDone", "()Z", "", "setFade", "()V", "tick", "done", "Z", "fade", "", "fadeCount", "I", "", "fadeTime", "D", "initVolume", "Lnet/minecraft/client/sounds/SoundManager;", "kotlin.jvm.PlatformType", "soundManager", "Lnet/minecraft/client/sounds/SoundManager;", "tickCount", "Lnet/minecraft/sounds/SoundEvent;", "sound", "", "volume", "pitch", "<init>", "(Lnet/minecraft/sounds/SoundEvent;FF)V", "common"})
@SourceDebugExtension(value={"SMAP\nBattleMusicInstance.kt\nKotlin\n*S Kotlin\n*F\n+ 1 BattleMusicInstance.kt\ncom/cobblemon/mod/common/client/sound/battle/BattleMusicInstance\n+ 2 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n*L\n1#1,67:1\n1855#2,2:68\n*S KotlinDebug\n*F\n+ 1 BattleMusicInstance.kt\ncom/cobblemon/mod/common/client/sound/battle/BattleMusicInstance\n*L\n48#1:68,2\n*E\n"})
public final class BattleMusicInstance
extends SimpleSoundInstance
implements TickableSoundInstance {
    private final SoundManager soundManager;
    private boolean fade;
    private boolean done;
    private int tickCount;
    private int fadeCount;
    private final double fadeTime;
    private double initVolume;

    public BattleMusicInstance(@NotNull SoundEvent sound2, float volume, float pitch) {
        Intrinsics.checkNotNullParameter((Object)sound2, (String)"sound");
        super(sound2, SoundSource.MUSIC, volume, pitch, SoundInstance.m_235150_(), BlockPos.f_121853_);
        this.soundManager = Minecraft.m_91087_().m_91106_();
        this.fadeTime = 60.0;
        this.initVolume = 1.0;
        this.f_119582_ = true;
        this.f_119578_ = true;
        this.f_119580_ = SoundInstance.Attenuation.NONE;
        this.initVolume = volume;
    }

    public /* synthetic */ BattleMusicInstance(SoundEvent soundEvent, float f, float f2, int n, DefaultConstructorMarker defaultConstructorMarker) {
        if ((n & 2) != 0) {
            f = 1.0f;
        }
        if ((n & 4) != 0) {
            f2 = 1.0f;
        }
        this(soundEvent, f, f2);
    }

    public boolean m_7801_() {
        if (this.done) {
            Iterable $this$forEach$iv = BattleMusicController.INSTANCE.getFilteredCategories();
            boolean $i$f$forEach = false;
            for (Object element$iv : $this$forEach$iv) {
                SoundSource it = (SoundSource)element$iv;
                boolean bl = false;
                SoundManager soundManager = this.soundManager;
                Intrinsics.checkNotNullExpressionValue((Object)soundManager, (String)"soundManager");
                SoundExtensionsKt.resumeSounds(soundManager, null, it);
            }
        }
        return this.done;
    }

    public final void setFade() {
        this.fade = true;
        this.f_119578_ = false;
    }

    public void m_7788_() {
        ++this.tickCount;
        if (this.fade) {
            ++this.fadeCount;
            this.f_119573_ = (float)Mth.m_14139_((double)((double)this.fadeCount / this.fadeTime), (double)this.initVolume, (double)0.0);
            if (this.f_119573_ <= 0.0f) {
                this.done = true;
            }
        }
    }
}

