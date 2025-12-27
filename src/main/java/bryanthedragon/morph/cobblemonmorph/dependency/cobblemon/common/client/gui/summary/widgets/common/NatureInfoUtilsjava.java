package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.gui.summary.widgets.common

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.CobblemonItems
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.text.TextKt
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.item.interactive.MintItem
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.Nature
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.Pokemon;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.MiscUtilsKt
import net.minecraft.network.chat.Component
import net.minecraft.network.chat.MutableComponent

public fun reformatNatureTextIfMinted(pokemon: Pokemon): MutableComponent {
   var var4: MutableComponent = MiscUtilsKt.asTranslated(pokemon.getNature().getDisplayName());
   if (pokemon.getMintedNature() != null) {
      val var10000: java.util.Map = CobblemonItems.INSTANCE.getMints();
      val var10001: Nature = pokemon.getMintedNature();
      val var5: MintItem = var10000.get(var10001.getDisplayName()) as MintItem;
      if (var5 != null) {
         val var6: MutableComponent = TextKt.italicise(var4);
         val var7: Component = var5.m_41466_();
         var4 = TextKt.onHover(var6, var7);
      }
   }

   return var4;
}
