package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.helditem

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.battles.model.PokemonBattle
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.helditem.HeldItemManager
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.pokemon.BattlePokemon
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.runner.ShowdownService
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.Pokemon;
import com.google.common.collect.HashBiMap
import com.google.gson.JsonArray
import java.util.HashSet
import kotlin.jvm.internal.SourceDebugExtension
import net.minecraft.core.registries.BuiltInRegistries
import net.minecraft.network.chat.Component
import net.minecraft.resources.ResourceLocation
import net.minecraft.world.item.Item
import net.minecraft.world.item.ItemStack
import net.minecraft.world.level.ItemLike

@SourceDebugExtension(["SMAP\nBaseCobblemonHeldItemManager.kt\nKotlin\n*S Kotlin\n*F\n+ 1 BaseCobblemonHeldItemManager.kt\ncom/cobblemon/mod/common/pokemon/helditem/BaseCobblemonHeldItemManager\n+ 2 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n+ 3 fake.kt\nkotlin/jvm/internal/FakeKt\n*L\n1#1,88:1\n1855#2,2:89\n1#3:91\n*S KotlinDebug\n*F\n+ 1 BaseCobblemonHeldItemManager.kt\ncom/cobblemon/mod/common/pokemon/helditem/BaseCobblemonHeldItemManager\n*L\n39#1:89,2\n*E\n"])
public abstract class BaseCobblemonHeldItemManager : HeldItemManager {
   private final val itemIds: HashBiMap<String, Item> = HashBiMap.create()

   internal open fun load() {
      this.itemIds.clear();
      val itemsJson: JsonArray = ShowdownService.Companion.getService().getItemIds();
      val showdownIds: HashSet = new HashSet();
      var `$this$forEach$iv`: Int = 0;

      for (int $i$f$forEach = itemsJson.size(); i < $i$f$forEach; i++) {
         showdownIds.add(itemsJson.get(`$this$forEach$iv`).getAsString());
      }
      for (Object element$iv : var11) {
         val item: Item = `element$iv` as Item;
         val var13: ResourceLocation = BuiltInRegistries.f_257033_.m_7981_(`element$iv` as Item);
         if (var13.m_135827_() == "cobblemon") {
            val var14: java.lang.String = var13.m_135815_();
            val formattedPath: java.lang.String = StringsKt.replace$default(var14, "_", "", false, 4, null);
            if (showdownIds.contains(formattedPath)) {
               val var15: HashBiMap = this.itemIds;
               (var15 as java.util.Map).put(formattedPath, item);
            }
         }
      }
   }

   public override fun showdownId(pokemon: BattlePokemon): String? {
      val var10001: Item = pokemon.getEffectedPokemon().heldItemNoCopy$common().m_41720_();
      return this.showdownIdOf(var10001);
   }

   public override fun nameOf(showdownId: String): Component {
      val var10000: Item = this.itemIds.get(showdownId) as Item;
      var var2: Component = if (var10000 != null) var10000.m_41466_() else null;
      if (var2 == null) {
         var2 = Component.m_130674_(showdownId);
      }

      return var2;
   }

   public override fun give(pokemon: BattlePokemon, showdownId: String) {
      val var10000: Item = this.itemIds.get(showdownId) as Item;
      val var6: ItemStack = if (var10000 != null) new ItemStack(var10000 as ItemLike) else ItemStack.f_41583_;
      val var7: Pokemon = pokemon.getEffectedPokemon();
      var7.swapHeldItem(var6, false);
   }

   public override fun take(pokemon: BattlePokemon, showdownId: String) {
      pokemon.getEffectedPokemon().removeHeldItem();
   }

   protected fun loadedItemCount(): Int {
      return this.itemIds.size();
   }

   private fun showdownIdOf(item: Item): String? {
      val var10000: ResourceLocation = BuiltInRegistries.f_257033_.m_7981_(item);
      val var4: java.lang.String = var10000.m_135815_();
      val formattedPath: java.lang.String = StringsKt.replace$default(var4, "_", "", false, 4, null);
      return if (this.itemIds.containsKey(formattedPath)) formattedPath else null;
   }

   override fun shouldConsumeItem(pokemon: BattlePokemon, battle: PokemonBattle, showdownId: java.lang.String): Boolean {
      return HeldItemManager.DefaultImpls.shouldConsumeItem(this, pokemon, battle, showdownId);
   }
}
