package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.duck.SoundManagerDuck
import net.minecraft.client.sounds.SoundManager
import net.minecraft.resources.ResourceLocation
import net.minecraft.sounds.SoundSource

public fun SoundManager.pauseSounds(id: ResourceLocation?, category: SoundSource?) {
   (`$this$pauseSounds` as SoundManagerDuck).pauseSounds(id, category);
}

public fun SoundManager.resumeSounds(id: ResourceLocation?, category: SoundSource?) {
   (`$this$resumeSounds` as SoundManagerDuck).resumeSounds(id, category);
}
