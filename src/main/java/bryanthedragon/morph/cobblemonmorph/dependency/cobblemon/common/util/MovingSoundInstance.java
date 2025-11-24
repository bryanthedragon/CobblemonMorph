/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  kotlin.Metadata
 *  kotlin.jvm.functions.Function0
 *  kotlin.jvm.internal.DefaultConstructorMarker
 *  kotlin.jvm.internal.Intrinsics
 *  net.minecraft.client.resources.sounds.AbstractTickableSoundInstance
 *  net.minecraft.client.resources.sounds.SoundInstance
 *  net.minecraft.sounds.SoundEvent
 *  net.minecraft.sounds.SoundSource
 *  net.minecraft.world.phys.Vec3
 *  org.jetbrains.annotations.NotNull
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util;

import kotlin.Metadata;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import net.minecraft.client.resources.sounds.AbstractTickableSoundInstance;
import net.minecraft.client.resources.sounds.SoundInstance;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000D\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0006\n\u0002\u0010\u000b\n\u0002\b\u0006\n\u0002\u0010\u0007\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u000b\u0018\u00002\u00020\u0001BU\u0012\u0006\u0010!\u001a\u00020 \u0012\u0006\u0010\u0006\u001a\u00020\u0005\u0012\u000e\u0010\u001b\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u001a0\u0019\u0012\u0006\u0010%\u001a\u00020\u0016\u0012\u0006\u0010\u0017\u001a\u00020\u0016\u0012\b\b\u0002\u0010\u0010\u001a\u00020\u000f\u0012\b\b\u0002\u0010\t\u001a\u00020\b\u0012\b\b\u0002\u0010\u001f\u001a\u00020\b\u00a2\u0006\u0004\b)\u0010*J\u000f\u0010\u0003\u001a\u00020\u0002H\u0016\u00a2\u0006\u0004\b\u0003\u0010\u0004R\u0014\u0010\u0006\u001a\u00020\u00058\u0002X\u0082\u0004\u00a2\u0006\u0006\n\u0004\b\u0006\u0010\u0007R\"\u0010\t\u001a\u00020\b8\u0006@\u0006X\u0086\u000e\u00a2\u0006\u0012\n\u0004\b\t\u0010\n\u001a\u0004\b\u000b\u0010\f\"\u0004\b\r\u0010\u000eR\"\u0010\u0010\u001a\u00020\u000f8\u0006@\u0006X\u0086\u000e\u00a2\u0006\u0012\n\u0004\b\u0010\u0010\u0011\u001a\u0004\b\u0012\u0010\u0013\"\u0004\b\u0014\u0010\u0015R\u0014\u0010\u0017\u001a\u00020\u00168\u0002X\u0082\u0004\u00a2\u0006\u0006\n\u0004\b\u0017\u0010\u0018R\u001f\u0010\u001b\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u001a0\u00198\u0006\u00a2\u0006\f\n\u0004\b\u001b\u0010\u001c\u001a\u0004\b\u001d\u0010\u001eR\u0014\u0010\u001f\u001a\u00020\b8\u0002X\u0082\u0004\u00a2\u0006\u0006\n\u0004\b\u001f\u0010\nR\u0017\u0010!\u001a\u00020 8\u0006\u00a2\u0006\f\n\u0004\b!\u0010\"\u001a\u0004\b#\u0010$R\u0014\u0010%\u001a\u00020\u00168\u0002X\u0082\u0004\u00a2\u0006\u0006\n\u0004\b%\u0010\u0018R\"\u0010&\u001a\u00020\b8\u0006@\u0006X\u0086\u000e\u00a2\u0006\u0012\n\u0004\b&\u0010\n\u001a\u0004\b'\u0010\f\"\u0004\b(\u0010\u000e\u00a8\u0006+"}, d2={"Lcom/cobblemon/mod/common/util/MovingSoundInstance;", "Lnet/minecraft/client/resources/sounds/AbstractTickableSoundInstance;", "", "tick", "()V", "Lnet/minecraft/sounds/SoundSource;", "category", "Lnet/minecraft/sounds/SoundSource;", "", "duration", "I", "getDuration", "()I", "setDuration", "(I)V", "", "looping", "Z", "getLooping", "()Z", "setLooping", "(Z)V", "", "pitch", "F", "Lkotlin/Function0;", "Lnet/minecraft/world/phys/Vec3;", "pos", "Lkotlin/jvm/functions/Function0;", "getPos", "()Lkotlin/jvm/functions/Function0;", "repeatDelay", "Lnet/minecraft/sounds/SoundEvent;", "sound", "Lnet/minecraft/sounds/SoundEvent;", "getSound", "()Lnet/minecraft/sounds/SoundEvent;", "startingVol", "time", "getTime", "setTime", "<init>", "(Lnet/minecraft/sounds/SoundEvent;Lnet/minecraft/sounds/SoundSource;Lkotlin/jvm/functions/Function0;FFZII)V", "common"})
public final class MovingSoundInstance
extends AbstractTickableSoundInstance {
    @NotNull
    private final SoundEvent sound;
    @NotNull
    private final SoundSource category;
    @NotNull
    private final Function0<Vec3> pos;
    private final float startingVol;
    private final float pitch;
    private boolean looping;
    private int duration;
    private final int repeatDelay;
    private int time;

    public MovingSoundInstance(@NotNull SoundEvent sound2, @NotNull SoundSource category, @NotNull Function0<? extends Vec3> pos, float startingVol, float pitch, boolean looping, int duration, int repeatDelay) {
        Intrinsics.checkNotNullParameter((Object)sound2, (String)"sound");
        Intrinsics.checkNotNullParameter((Object)category, (String)"category");
        Intrinsics.checkNotNullParameter(pos, (String)"pos");
        super(sound2, category, SoundInstance.m_235150_());
        this.sound = sound2;
        this.category = category;
        this.pos = pos;
        this.startingVol = startingVol;
        this.pitch = pitch;
        this.looping = looping;
        this.duration = duration;
        this.repeatDelay = repeatDelay;
        this.f_119578_ = this.looping;
        Vec3 vec3 = (Vec3)this.pos.invoke();
        this.f_119575_ = vec3 != null ? vec3.f_82479_ : 0.0;
        Vec3 vec32 = (Vec3)this.pos.invoke();
        this.f_119576_ = vec32 != null ? vec32.f_82480_ : 0.0;
        Vec3 vec33 = (Vec3)this.pos.invoke();
        this.f_119577_ = vec33 != null ? vec33.f_82481_ : 0.0;
        this.f_119573_ = this.startingVol;
    }

    public /* synthetic */ MovingSoundInstance(SoundEvent soundEvent, SoundSource soundSource, Function0 function0, float f, float f2, boolean bl, int n, int n2, int n3, DefaultConstructorMarker defaultConstructorMarker) {
        if ((n3 & 0x20) != 0) {
            bl = true;
        }
        if ((n3 & 0x40) != 0) {
            n = 20;
        }
        if ((n3 & 0x80) != 0) {
            n2 = 0;
        }
        this(soundEvent, soundSource, (Function0<? extends Vec3>)function0, f, f2, bl, n, n2);
    }

    @NotNull
    public final SoundEvent getSound() {
        return this.sound;
    }

    @NotNull
    public final Function0<Vec3> getPos() {
        return this.pos;
    }

    public final boolean getLooping() {
        return this.looping;
    }

    public final void setLooping(boolean bl) {
        this.looping = bl;
    }

    public final int getDuration() {
        return this.duration;
    }

    public final void setDuration(int n) {
        this.duration = n;
    }

    public final int getTime() {
        return this.time;
    }

    public final void setTime(int n) {
        this.time = n;
    }

    public void m_7788_() {
        if (!this.looping && this.time > this.duration) {
            this.m_119609_();
        } else {
            Vec3 vec3 = (Vec3)this.pos.invoke();
            this.f_119575_ = vec3 != null ? vec3.f_82479_ : 0.0;
            Vec3 vec32 = (Vec3)this.pos.invoke();
            this.f_119576_ = vec32 != null ? vec32.f_82480_ : 0.0;
            Vec3 vec33 = (Vec3)this.pos.invoke();
            double d = this.f_119577_ = vec33 != null ? vec33.f_82481_ : 0.0;
        }
        if (this.repeatDelay > 0 && this.time > this.duration + this.repeatDelay) {
            this.time = 0;
        } else if (this.repeatDelay == 0 && this.time > this.duration) {
            this.time = 0;
        } else if (this.repeatDelay < 0) {
            this.time = 0;
        } else {
            int n = this.time;
            this.time = n + 1;
        }
    }
}

