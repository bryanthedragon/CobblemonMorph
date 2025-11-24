/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.resources.sounds.SoundInstance
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.sounds.SoundSource
 *  org.jetbrains.annotations.Nullable
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.duck;

import java.util.Collection;
import net.minecraft.client.resources.sounds.SoundInstance;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundSource;
import org.jetbrains.annotations.Nullable;

public interface SoundSystemDuck {
    public void pauseSounds(@Nullable ResourceLocation var1, @Nullable SoundSource var2);

    public void resumeSounds(@Nullable ResourceLocation var1, @Nullable SoundSource var2);

    public Collection<SoundInstance> getSounds(@Nullable SoundSource var1);
}

