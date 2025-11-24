/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.sounds.SoundSource
 *  org.jetbrains.annotations.Nullable
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.duck;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundSource;
import org.jetbrains.annotations.Nullable;

public interface SoundManagerDuck {
    public void pauseSounds(@Nullable ResourceLocation var1, @Nullable SoundSource var2);

    public void resumeSounds(@Nullable ResourceLocation var1, @Nullable SoundSource var2);
}

