/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  kotlin.Metadata
 *  kotlin.jvm.internal.DefaultConstructorMarker
 *  kotlin.jvm.internal.Intrinsics
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.player.LocalPlayer
 *  net.minecraft.client.resources.sounds.SimpleSoundInstance
 *  net.minecraft.client.resources.sounds.SoundInstance
 *  net.minecraft.client.resources.sounds.SoundInstance$Attenuation
 *  net.minecraft.client.resources.sounds.TickableSoundInstance
 *  net.minecraft.client.sounds.SoundManager
 *  net.minecraft.core.BlockPos
 *  net.minecraft.sounds.SoundEvent
 *  net.minecraft.sounds.SoundSource
 *  net.minecraft.world.phys.Vec3
 *  org.jetbrains.annotations.NotNull
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.sound;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.sound.CancellableSoundController;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.resources.sounds.SimpleSoundInstance;
import net.minecraft.client.resources.sounds.SoundInstance;
import net.minecraft.client.resources.sounds.TickableSoundInstance;
import net.minecraft.client.sounds.SoundManager;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000L\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0004\n\u0002\u0010\u0006\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0007\n\u0002\b\u0006\u0018\u0000 $2\u00020\u00012\u00020\u0002:\u0001$B7\u0012\u0006\u0010\u001d\u001a\u00020\u001c\u0012\b\b\u0002\u0010\u000f\u001a\u00020\u000e\u0012\b\b\u0002\u0010\u001e\u001a\u00020\u0003\u0012\b\b\u0002\u0010 \u001a\u00020\u001f\u0012\b\b\u0002\u0010!\u001a\u00020\u001f\u00a2\u0006\u0004\b\"\u0010#J\u000f\u0010\u0004\u001a\u00020\u0003H\u0016\u00a2\u0006\u0004\b\u0004\u0010\u0005J\u000f\u0010\u0007\u001a\u00020\u0006H\u0016\u00a2\u0006\u0004\b\u0007\u0010\bR\u0016\u0010\t\u001a\u00020\u00038\u0002@\u0002X\u0082\u000e\u00a2\u0006\u0006\n\u0004\b\t\u0010\nR\u0016\u0010\f\u001a\u00020\u000b8\u0002@\u0002X\u0082\u000e\u00a2\u0006\u0006\n\u0004\b\f\u0010\rR\"\u0010\u000f\u001a\u00020\u000e8\u0006@\u0006X\u0086\u000e\u00a2\u0006\u0012\n\u0004\b\u000f\u0010\u0010\u001a\u0004\b\u0011\u0010\u0012\"\u0004\b\u0013\u0010\u0014R\u001c\u0010\u0017\u001a\n \u0016*\u0004\u0018\u00010\u00150\u00158\u0002X\u0082\u0004\u00a2\u0006\u0006\n\u0004\b\u0017\u0010\u0018R\u0016\u0010\u001a\u001a\u00020\u00198\u0002@\u0002X\u0082\u000e\u00a2\u0006\u0006\n\u0004\b\u001a\u0010\u001b\u00a8\u0006%"}, d2={"Lcom/cobblemon/mod/common/client/sound/CancellableSoundInstance;", "Lnet/minecraft/client/resources/sounds/SimpleSoundInstance;", "Lnet/minecraft/client/resources/sounds/TickableSoundInstance;", "", "isDone", "()Z", "", "tick", "()V", "done", "Z", "", "initVolume", "D", "Lnet/minecraft/core/BlockPos;", "pos", "Lnet/minecraft/core/BlockPos;", "getPos", "()Lnet/minecraft/core/BlockPos;", "setPos", "(Lnet/minecraft/core/BlockPos;)V", "Lnet/minecraft/client/sounds/SoundManager;", "kotlin.jvm.PlatformType", "soundManager", "Lnet/minecraft/client/sounds/SoundManager;", "", "unheardTicks", "I", "Lnet/minecraft/sounds/SoundEvent;", "sound", "repeat", "", "volume", "pitch", "<init>", "(Lnet/minecraft/sounds/SoundEvent;Lnet/minecraft/core/BlockPos;ZFF)V", "Companion", "common"})
public final class CancellableSoundInstance
extends SimpleSoundInstance
implements TickableSoundInstance {
    @NotNull
    public static final Companion Companion = new Companion(null);
    private final SoundManager soundManager;
    private boolean done;
    private int unheardTicks;
    private double initVolume;
    @NotNull
    private BlockPos pos;
    public static final int UNHEARD_TICKS_MAX = 200;
    public static final int ATTENUATION_DISTANCE_MAX_SQUARED = 324;

    public CancellableSoundInstance(@NotNull SoundEvent sound2, @NotNull BlockPos pos, boolean repeat, float volume, float pitch) {
        Intrinsics.checkNotNullParameter((Object)sound2, (String)"sound");
        Intrinsics.checkNotNullParameter((Object)pos, (String)"pos");
        super(sound2, SoundSource.BLOCKS, volume, pitch, SoundInstance.m_235150_(), pos);
        this.soundManager = Minecraft.m_91087_().m_91106_();
        this.initVolume = 1.0;
        this.f_119582_ = false;
        this.f_119578_ = repeat;
        this.f_119580_ = SoundInstance.Attenuation.NONE;
        this.initVolume = volume;
        this.pos = pos;
        this.f_119580_ = SoundInstance.Attenuation.LINEAR;
    }

    public /* synthetic */ CancellableSoundInstance(SoundEvent soundEvent, BlockPos blockPos2, boolean bl, float f, float f2, int n, DefaultConstructorMarker defaultConstructorMarker) {
        if ((n & 2) != 0) {
            BlockPos blockPos3 = BlockPos.f_121853_;
            Intrinsics.checkNotNullExpressionValue((Object)blockPos3, (String)"ORIGIN");
            blockPos2 = blockPos3;
        }
        if ((n & 4) != 0) {
            bl = false;
        }
        if ((n & 8) != 0) {
            f = 1.0f;
        }
        if ((n & 0x10) != 0) {
            f2 = 1.0f;
        }
        this(soundEvent, blockPos2, bl, f, f2);
    }

    @NotNull
    public final BlockPos getPos() {
        return this.pos;
    }

    public final void setPos(@NotNull BlockPos blockPos2) {
        Intrinsics.checkNotNullParameter((Object)blockPos2, (String)"<set-?>");
        this.pos = blockPos2;
    }

    public boolean m_7801_() {
        return this.done;
    }

    public void m_7788_() {
        if (this.soundManager.m_120403_((SoundInstance)this) && !this.f_119578_) {
            this.done = true;
            CancellableSoundController.INSTANCE.stopSound(this);
        } else {
            Double listenerPos;
            LocalPlayer localPlayer = Minecraft.m_91087_().f_91074_;
            Double d = localPlayer != null && (localPlayer = localPlayer.m_20182_()) != null ? Double.valueOf(localPlayer.m_82557_(new Vec3(this.f_119575_, this.f_119576_, this.f_119577_))) : (listenerPos = null);
            if (listenerPos != null) {
                if (listenerPos > 648.0) {
                    this.done = true;
                    CancellableSoundController.INSTANCE.stopSound(this);
                } else if (listenerPos > 324.0) {
                    ++this.unheardTicks;
                    if (this.unheardTicks > 200) {
                        this.done = true;
                        this.f_119578_ = false;
                        CancellableSoundController.INSTANCE.stopSound(this);
                    }
                }
            } else {
                this.unheardTicks = 0;
            }
        }
    }

    @Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000\u0010\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0010\b\n\u0002\b\u0006\b\u0086\u0003\u0018\u00002\u00020\u0001B\t\b\u0002\u00a2\u0006\u0004\b\u0006\u0010\u0007R\u0014\u0010\u0003\u001a\u00020\u00028\u0006X\u0086T\u00a2\u0006\u0006\n\u0004\b\u0003\u0010\u0004R\u0014\u0010\u0005\u001a\u00020\u00028\u0006X\u0086T\u00a2\u0006\u0006\n\u0004\b\u0005\u0010\u0004\u00a8\u0006\b"}, d2={"Lcom/cobblemon/mod/common/client/sound/CancellableSoundInstance$Companion;", "", "", "ATTENUATION_DISTANCE_MAX_SQUARED", "I", "UNHEARD_TICKS_MAX", "<init>", "()V", "common"})
    public static final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker $constructor_marker) {
            this();
        }
    }
}

