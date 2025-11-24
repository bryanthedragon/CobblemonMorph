/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  kotlin.Metadata
 *  kotlin.jvm.internal.Intrinsics
 *  net.minecraft.client.sounds.SoundManager
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.sounds.SoundSource
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.duck.SoundManagerDuck;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.minecraft.client.sounds.SoundManager;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundSource;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(mv={1, 8, 0}, k=2, xi=48, d1={"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0004\u001a%\u0010\u0006\u001a\u00020\u0005*\u00020\u00002\b\u0010\u0002\u001a\u0004\u0018\u00010\u00012\b\u0010\u0004\u001a\u0004\u0018\u00010\u0003\u00a2\u0006\u0004\b\u0006\u0010\u0007\u001a%\u0010\b\u001a\u00020\u0005*\u00020\u00002\b\u0010\u0002\u001a\u0004\u0018\u00010\u00012\b\u0010\u0004\u001a\u0004\u0018\u00010\u0003\u00a2\u0006\u0004\b\b\u0010\u0007\u00a8\u0006\t"}, d2={"Lnet/minecraft/client/sounds/SoundManager;", "Lnet/minecraft/resources/ResourceLocation;", "id", "Lnet/minecraft/sounds/SoundSource;", "category", "", "pauseSounds", "(Lnet/minecraft/client/sounds/SoundManager;Lnet/minecraft/resources/ResourceLocation;Lnet/minecraft/sounds/SoundSource;)V", "resumeSounds", "common"})
public final class SoundExtensionsKt {
    public static final void pauseSounds(@NotNull SoundManager $this$pauseSounds, @Nullable ResourceLocation id, @Nullable SoundSource category) {
        Intrinsics.checkNotNullParameter((Object)$this$pauseSounds, (String)"<this>");
        ((SoundManagerDuck)$this$pauseSounds).pauseSounds(id, category);
    }

    public static final void resumeSounds(@NotNull SoundManager $this$resumeSounds, @Nullable ResourceLocation id, @Nullable SoundSource category) {
        Intrinsics.checkNotNullParameter((Object)$this$resumeSounds, (String)"<this>");
        ((SoundManagerDuck)$this$resumeSounds).resumeSounds(id, category);
    }
}

