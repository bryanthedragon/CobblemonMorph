package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util

import java.util.Arrays
import net.minecraft.network.chat.MutableComponent

public fun lang(subKey: String, vararg objects: Any): MutableComponent {
   return MiscUtilsKt.asTranslated("cobblemon.$subKey", Arrays.copyOf(objects, objects.length));
}

public fun commandLang(subKey: String, vararg objects: Any): MutableComponent {
   return lang("command.$subKey", Arrays.copyOf(objects, objects.length));
}

public fun battleLang(key: String, vararg objects: Any): MutableComponent {
   return lang("battle.$key", Arrays.copyOf(objects, objects.length));
}

public fun tooltipLang(modId: String = "cobblemon", key: String, vararg objects: Any): MutableComponent {
   return MiscUtilsKt.asTranslated("item.$modId.$key.tooltip", Arrays.copyOf(objects, objects.length));
}

@JvmSynthetic
fun `tooltipLang$default`(var0: java.lang.String, var1: java.lang.String, var2: Array<Any>, var3: Int, var4: Any): MutableComponent {
   if ((var3 and 1) != 0) {
      var0 = "cobblemon";
   }

   return tooltipLang(var0, var1, var2);
}
