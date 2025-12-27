package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.keybind

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.mixin.accessor.KeyBindingAccessor
import net.minecraft.client.KeyMapping
import net.minecraft.client.util.InputUtil.Key

public fun KeyMapping.boundKey(): Key {
   val var10000: com.mojang.blaze3d.platform.InputConstants.Key = (`$this$boundKey` as KeyBindingAccessor).boundKey();
   return var10000;
}
