package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.duck;

import java.util.Collection;
import net.minecraft.client.resources.sounds.SoundInstance;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundSource;
import org.jetbrains.annotations.Nullable;

public interface SoundSystemDuck {
   void pauseSounds(@Nullable ResourceLocation id, @Nullable SoundSource category);

   void resumeSounds(@Nullable ResourceLocation id, @Nullable SoundSource category);

   Collection<SoundInstance> getSounds(@Nullable SoundSource category);
}
