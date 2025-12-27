package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util

import net.minecraft.resources.ResourceLocation

public fun ResourceLocation.endsWith(suffix: String): Boolean {
   val var10000: java.lang.String = `$this$endsWith`.toString();
   return StringsKt.endsWith$default(var10000, suffix, false, 2, null);
}
