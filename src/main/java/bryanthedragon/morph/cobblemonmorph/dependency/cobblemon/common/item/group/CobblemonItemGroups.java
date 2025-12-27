package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.item.group

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.CobblemonItems
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.MiscUtilsKt
import java.util.ArrayList;
import java.util.HashMap
import kotlin.jvm.functions.Function1
import kotlin.jvm.internal.SourceDebugExtension
import net.minecraft.core.registries.BuiltInRegistries
import net.minecraft.item.ItemGroup.DisplayContext
import net.minecraft.item.ItemGroup.Entries
import net.minecraft.item.ItemGroup.EntryCollector
import net.minecraft.network.chat.Component
import net.minecraft.resources.ResourceKey
import net.minecraft.resources.ResourceLocation
import net.minecraft.world.item.CreativeModeTab
import net.minecraft.world.item.Item
import net.minecraft.world.item.ItemStack
import net.minecraft.world.item.Items
import net.minecraft.world.level.ItemLike
import org.jetbrains.annotations.NotNull

@SourceDebugExtension(["SMAP\nCobblemonItemGroups.kt\nKotlin\n*S Kotlin\n*F\n+ 1 CobblemonItemGroups.kt\ncom/cobblemon/mod/common/item/group/CobblemonItemGroups\n+ 2 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n*L\n1#1,604:1\n1855#2,2:605\n1855#2,2:607\n1855#2,2:609\n*S KotlinDebug\n*F\n+ 1 CobblemonItemGroups.kt\ncom/cobblemon/mod/common/item/group/CobblemonItemGroups\n*L\n52#1:605,2\n130#1:607,2\n536#1:609,2\n*E\n"])
public object CobblemonItemGroups {
   @JvmStatic
   public final val AGRICULTURE: CreativeModeTab?
      public final get() {
         return BuiltInRegistries.f_279662_.m_6246_(AGRICULTURE_KEY) as CreativeModeTab;
      }


   @JvmStatic
   public final val AGRICULTURE_KEY: ResourceKey<CreativeModeTab> = INSTANCE.create("agriculture", INSTANCE::agricultureEntries, <unrepresentable>.INSTANCE)

   private final val ALL: ArrayList<bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.item.group.CobblemonItemGroups.ItemGroupHolder> = new ArrayList()

   @JvmStatic
   public final val ARCHAEOLOGY: CreativeModeTab?
      public final get() {
         return BuiltInRegistries.f_279662_.m_6246_(ARCHAEOLOGY_KEY) as CreativeModeTab;
      }


   @JvmStatic
   public final val ARCHAEOLOGY_KEY: ResourceKey<CreativeModeTab> = INSTANCE.create("archaeology", INSTANCE::archaeologyEntries, <unrepresentable>.INSTANCE)

   @JvmStatic
   public final val BLOCKS: CreativeModeTab?
      public final get() {
         return BuiltInRegistries.f_279662_.m_6246_(BLOCKS_KEY) as CreativeModeTab;
      }


   @JvmStatic
   public final val BLOCKS_KEY: ResourceKey<CreativeModeTab> = INSTANCE.create("blocks", INSTANCE::blockEntries, <unrepresentable>.INSTANCE)

   @JvmStatic
   public final val CONSUMABLES: CreativeModeTab?
      public final get() {
         return BuiltInRegistries.f_279662_.m_6246_(CONSUMABLES_KEY) as CreativeModeTab;
      }


   @JvmStatic
   public final val CONSUMABLES_KEY: ResourceKey<CreativeModeTab> = INSTANCE.create("consumables", INSTANCE::consumableEntries, <unrepresentable>.INSTANCE)

   @JvmStatic
   public final val EVOLUTION_ITEMS: CreativeModeTab?
      public final get() {
         return BuiltInRegistries.f_279662_.m_6246_(EVOLUTION_ITEMS_KEY) as CreativeModeTab;
      }


   @JvmStatic
   public final val EVOLUTION_ITEMS_KEY: ResourceKey<CreativeModeTab> =
      INSTANCE.create("evolution_item", INSTANCE::evolutionItemEntries, <unrepresentable>.INSTANCE)

   @JvmStatic
   public final val FOOD_INJECTIONS: (bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.item.group.CobblemonItemGroups.Injector) -> Unit

   @JvmStatic
   public final val HELD_ITEMS: CreativeModeTab?
      public final get() {
         return BuiltInRegistries.f_279662_.m_6246_(HELD_ITEMS_KEY) as CreativeModeTab;
      }


   @JvmStatic
   public final val HELD_ITEMS_KEY: ResourceKey<CreativeModeTab> = INSTANCE.create("held_item", INSTANCE::heldItemEntries, <unrepresentable>.INSTANCE)

   @JvmStatic
   public final val INGREDIENTS_INJECTIONS: (bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.item.group.CobblemonItemGroups.Injector) -> Unit

   private final val INJECTORS: HashMap<ResourceKey<CreativeModeTab>, (bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.item.group.CobblemonItemGroups.Injector) -> Unit> =
      new HashMap()

   @JvmStatic
   public final val POKEBALLS: CreativeModeTab?
      public final get() {
         return BuiltInRegistries.f_279662_.m_6246_(POKEBALLS_KEY) as CreativeModeTab;
      }


   @JvmStatic
   public final val POKEBALLS_KEY: ResourceKey<CreativeModeTab> = INSTANCE.create("pokeball", INSTANCE::pokeballentries, <unrepresentable>.INSTANCE)

   @JvmStatic
   public final val TOOLS_AND_UTILITIES_INJECTIONS: (bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.item.group.CobblemonItemGroups.Injector) -> Unit

   public fun register(consumer: (bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.item.group.CobblemonItemGroups.ItemGroupHolder) -> CreativeModeTab) {
      val `$this$forEach$iv`: java.lang.Iterable;
      for (Object element$iv : $this$forEach$iv) {
         consumer.invoke(`element$iv` as CobblemonItemGroups.ItemGroupHolder);
      }
   }

   public fun inject(tabKey: ResourceKey<CreativeModeTab>, injector: bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.item.group.CobblemonItemGroups.Injector) {
      val var10000: Function1 = INJECTORS.get(tabKey);
      if (var10000 != null) {
         var10000.invoke(injector);
      }
   }

   public fun injectorKeys(): Collection<ResourceKey<CreativeModeTab>> {
      val var10000: java.util.Set = INJECTORS.keySet();
      return var10000;
   }

   private fun create(name: String, entryCollector: EntryCollector, displayIconProvider: () -> ItemStack): ResourceKey<CreativeModeTab> {
      val key: ResourceKey = ResourceKey.m_135785_(BuiltInRegistries.f_279662_.m_123023_(), MiscUtilsKt.cobblemonResource(name));
      val var10000: java.util.Collection = ALL;
      var10000.add(new CobblemonItemGroups.ItemGroupHolder(key, displayIconProvider, entryCollector, null, 8, null));
      return key;
   }

   private fun inject(key: ResourceKey<CreativeModeTab>, consumer: (bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.item.group.CobblemonItemGroups.Injector) -> Unit): (
         bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.item.group.CobblemonItemGroups.Injector
      ) -> Unit {
      INJECTORS.put(key, consumer);
      return consumer;
   }

   private fun agricultureEntries(displayContext: DisplayContext, entries: Entries) {
      entries.m_246326_(CobblemonItems.MEDICINAL_LEEK as ItemLike);
      entries.m_246326_(CobblemonItems.BIG_ROOT as ItemLike);
      entries.m_246326_(CobblemonItems.ENERGY_ROOT as ItemLike);
      entries.m_246326_(CobblemonItems.REVIVAL_HERB as ItemLike);
      entries.m_246326_(CobblemonItems.PEP_UP_FLOWER as ItemLike);
      entries.m_246326_(CobblemonItems.MENTAL_HERB as ItemLike);
      entries.m_246326_(CobblemonItems.POWER_HERB as ItemLike);
      entries.m_246326_(CobblemonItems.WHITE_HERB as ItemLike);
      entries.m_246326_(CobblemonItems.MIRROR_HERB as ItemLike);
      entries.m_246326_(CobblemonItems.VIVICHOKE as ItemLike);
      entries.m_246326_(CobblemonItems.VIVICHOKE_SEEDS as ItemLike);
      entries.m_246326_(CobblemonItems.RED_APRICORN as ItemLike);
      entries.m_246326_(CobblemonItems.YELLOW_APRICORN as ItemLike);
      entries.m_246326_(CobblemonItems.GREEN_APRICORN as ItemLike);
      entries.m_246326_(CobblemonItems.BLUE_APRICORN as ItemLike);
      entries.m_246326_(CobblemonItems.PINK_APRICORN as ItemLike);
      entries.m_246326_(CobblemonItems.BLACK_APRICORN as ItemLike);
      entries.m_246326_(CobblemonItems.WHITE_APRICORN as ItemLike);
      entries.m_246326_(CobblemonItems.RED_APRICORN_SEED as ItemLike);
      entries.m_246326_(CobblemonItems.YELLOW_APRICORN_SEED as ItemLike);
      entries.m_246326_(CobblemonItems.GREEN_APRICORN_SEED as ItemLike);
      entries.m_246326_(CobblemonItems.BLUE_APRICORN_SEED as ItemLike);
      entries.m_246326_(CobblemonItems.PINK_APRICORN_SEED as ItemLike);
      entries.m_246326_(CobblemonItems.BLACK_APRICORN_SEED as ItemLike);
      entries.m_246326_(CobblemonItems.WHITE_APRICORN_SEED as ItemLike);
      entries.m_246326_(CobblemonItems.RED_MINT_SEEDS as ItemLike);
      entries.m_246326_(CobblemonItems.RED_MINT_LEAF as ItemLike);
      entries.m_246326_(CobblemonItems.BLUE_MINT_SEEDS as ItemLike);
      entries.m_246326_(CobblemonItems.BLUE_MINT_LEAF as ItemLike);
      entries.m_246326_(CobblemonItems.CYAN_MINT_SEEDS as ItemLike);
      entries.m_246326_(CobblemonItems.CYAN_MINT_LEAF as ItemLike);
      entries.m_246326_(CobblemonItems.PINK_MINT_SEEDS as ItemLike);
      entries.m_246326_(CobblemonItems.PINK_MINT_LEAF as ItemLike);
      entries.m_246326_(CobblemonItems.GREEN_MINT_SEEDS as ItemLike);
      entries.m_246326_(CobblemonItems.GREEN_MINT_LEAF as ItemLike);
      entries.m_246326_(CobblemonItems.WHITE_MINT_SEEDS as ItemLike);
      entries.m_246326_(CobblemonItems.WHITE_MINT_LEAF as ItemLike);
      entries.m_246326_(CobblemonItems.GROWTH_MULCH as ItemLike);
      entries.m_246326_(CobblemonItems.RICH_MULCH as ItemLike);
      entries.m_246326_(CobblemonItems.SURPRISE_MULCH as ItemLike);
      entries.m_246326_(CobblemonItems.LOAMY_MULCH as ItemLike);
      entries.m_246326_(CobblemonItems.COARSE_MULCH as ItemLike);
      entries.m_246326_(CobblemonItems.PEAT_MULCH as ItemLike);
      entries.m_246326_(CobblemonItems.HUMID_MULCH as ItemLike);
      entries.m_246326_(CobblemonItems.SANDY_MULCH as ItemLike);
      entries.m_246326_(CobblemonItems.MULCH_BASE as ItemLike);

      val `$this$forEach$iv`: java.lang.Iterable;
      for (Object element$iv : $this$forEach$iv) {
         entries.m_246326_(`element$iv` as ItemLike);
      }
   }

   private fun archaeologyEntries(displayContext: DisplayContext, entries: Entries) {
      entries.m_246326_(CobblemonItems.HELIX_FOSSIL as ItemLike);
      entries.m_246326_(CobblemonItems.DOME_FOSSIL as ItemLike);
      entries.m_246326_(CobblemonItems.OLD_AMBER_FOSSIL as ItemLike);
      entries.m_246326_(CobblemonItems.ROOT_FOSSIL as ItemLike);
      entries.m_246326_(CobblemonItems.CLAW_FOSSIL as ItemLike);
      entries.m_246326_(CobblemonItems.SKULL_FOSSIL as ItemLike);
      entries.m_246326_(CobblemonItems.ARMOR_FOSSIL as ItemLike);
      entries.m_246326_(CobblemonItems.COVER_FOSSIL as ItemLike);
      entries.m_246326_(CobblemonItems.PLUME_FOSSIL as ItemLike);
      entries.m_246326_(CobblemonItems.JAW_FOSSIL as ItemLike);
      entries.m_246326_(CobblemonItems.SAIL_FOSSIL as ItemLike);
      entries.m_246326_(CobblemonItems.FOSSILIZED_BIRD as ItemLike);
      entries.m_246326_(CobblemonItems.FOSSILIZED_FISH as ItemLike);
      entries.m_246326_(CobblemonItems.FOSSILIZED_DRAKE as ItemLike);
      entries.m_246326_(CobblemonItems.FOSSILIZED_DINO as ItemLike);
      entries.m_246326_(CobblemonItems.TUMBLESTONE as ItemLike);
      entries.m_246326_(CobblemonItems.BLACK_TUMBLESTONE as ItemLike);
      entries.m_246326_(CobblemonItems.SKY_TUMBLESTONE as ItemLike);
      entries.m_246326_(CobblemonItems.SMALL_BUDDING_TUMBLESTONE as ItemLike);
      entries.m_246326_(CobblemonItems.SMALL_BUDDING_BLACK_TUMBLESTONE as ItemLike);
      entries.m_246326_(CobblemonItems.SMALL_BUDDING_SKY_TUMBLESTONE as ItemLike);
      entries.m_246326_(CobblemonItems.MEDIUM_BUDDING_TUMBLESTONE as ItemLike);
      entries.m_246326_(CobblemonItems.MEDIUM_BUDDING_BLACK_TUMBLESTONE as ItemLike);
      entries.m_246326_(CobblemonItems.MEDIUM_BUDDING_SKY_TUMBLESTONE as ItemLike);
      entries.m_246326_(CobblemonItems.LARGE_BUDDING_TUMBLESTONE as ItemLike);
      entries.m_246326_(CobblemonItems.LARGE_BUDDING_BLACK_TUMBLESTONE as ItemLike);
      entries.m_246326_(CobblemonItems.LARGE_BUDDING_SKY_TUMBLESTONE as ItemLike);
      entries.m_246326_(CobblemonItems.TUMBLESTONE_CLUSTER as ItemLike);
      entries.m_246326_(CobblemonItems.BLACK_TUMBLESTONE_CLUSTER as ItemLike);
      entries.m_246326_(CobblemonItems.SKY_TUMBLESTONE_CLUSTER as ItemLike);
      entries.m_246326_(CobblemonItems.TUMBLESTONE_BLOCK as ItemLike);
      entries.m_246326_(CobblemonItems.BLACK_TUMBLESTONE_BLOCK as ItemLike);
      entries.m_246326_(CobblemonItems.SKY_TUMBLESTONE_BLOCK as ItemLike);
      entries.m_246326_(CobblemonItems.BYGONE_SHERD as ItemLike);
      entries.m_246326_(CobblemonItems.CAPTURE_SHERD as ItemLike);
      entries.m_246326_(CobblemonItems.DOME_SHERD as ItemLike);
      entries.m_246326_(CobblemonItems.HELIX_SHERD as ItemLike);
      entries.m_246326_(CobblemonItems.NOSTALGIC_SHERD as ItemLike);
      entries.m_246326_(CobblemonItems.SUSPICIOUS_SHERD as ItemLike);
      entries.m_246326_(CobblemonItems.AUTOMATON_ARMOR_TRIM_SMITHING_TEMPLATE as ItemLike);
      entries.m_246326_(CobblemonItems.RELIC_COIN as ItemLike);
      entries.m_246326_(CobblemonItems.RELIC_COIN_POUCH as ItemLike);
      entries.m_246326_(CobblemonItems.RELIC_COIN_SACK as ItemLike);
      entries.m_246326_(CobblemonItems.GILDED_CHEST as ItemLike);
      entries.m_246326_(CobblemonItems.YELLOW_GILDED_CHEST as ItemLike);
      entries.m_246326_(CobblemonItems.GREEN_GILDED_CHEST as ItemLike);
      entries.m_246326_(CobblemonItems.BLUE_GILDED_CHEST as ItemLike);
      entries.m_246326_(CobblemonItems.PINK_GILDED_CHEST as ItemLike);
      entries.m_246326_(CobblemonItems.BLACK_GILDED_CHEST as ItemLike);
      entries.m_246326_(CobblemonItems.WHITE_GILDED_CHEST as ItemLike);
      entries.m_246326_(CobblemonItems.GIMMIGHOUL_CHEST as ItemLike);
      entries.m_246326_(CobblemonItems.NORMAL_GEM as ItemLike);
      entries.m_246326_(CobblemonItems.FIRE_GEM as ItemLike);
      entries.m_246326_(CobblemonItems.WATER_GEM as ItemLike);
      entries.m_246326_(CobblemonItems.GRASS_GEM as ItemLike);
      entries.m_246326_(CobblemonItems.ELECTRIC_GEM as ItemLike);
      entries.m_246326_(CobblemonItems.ICE_GEM as ItemLike);
      entries.m_246326_(CobblemonItems.FIGHTING_GEM as ItemLike);
      entries.m_246326_(CobblemonItems.POISON_GEM as ItemLike);
      entries.m_246326_(CobblemonItems.GROUND_GEM as ItemLike);
      entries.m_246326_(CobblemonItems.FLYING_GEM as ItemLike);
      entries.m_246326_(CobblemonItems.PSYCHIC_GEM as ItemLike);
      entries.m_246326_(CobblemonItems.BUG_GEM as ItemLike);
      entries.m_246326_(CobblemonItems.ROCK_GEM as ItemLike);
      entries.m_246326_(CobblemonItems.GHOST_GEM as ItemLike);
      entries.m_246326_(CobblemonItems.DRAGON_GEM as ItemLike);
      entries.m_246326_(CobblemonItems.DARK_GEM as ItemLike);
      entries.m_246326_(CobblemonItems.STEEL_GEM as ItemLike);
      entries.m_246326_(CobblemonItems.FAIRY_GEM as ItemLike);
   }

   private fun blockEntries(displayContext: DisplayContext, entries: Entries) {
      entries.m_246326_(CobblemonItems.RESTORATION_TANK as ItemLike);
      entries.m_246326_(CobblemonItems.FOSSIL_ANALYZER as ItemLike);
      entries.m_246326_(CobblemonItems.MONITOR as ItemLike);
      entries.m_246326_(CobblemonItems.PC as ItemLike);
      entries.m_246326_(CobblemonItems.HEALING_MACHINE as ItemLike);
      entries.m_246326_(CobblemonItems.PASTURE as ItemLike);
      entries.m_246326_(CobblemonItems.GILDED_CHEST as ItemLike);
      entries.m_246326_(CobblemonItems.YELLOW_GILDED_CHEST as ItemLike);
      entries.m_246326_(CobblemonItems.GREEN_GILDED_CHEST as ItemLike);
      entries.m_246326_(CobblemonItems.BLUE_GILDED_CHEST as ItemLike);
      entries.m_246326_(CobblemonItems.PINK_GILDED_CHEST as ItemLike);
      entries.m_246326_(CobblemonItems.BLACK_GILDED_CHEST as ItemLike);
      entries.m_246326_(CobblemonItems.WHITE_GILDED_CHEST as ItemLike);
      entries.m_246326_(CobblemonItems.GIMMIGHOUL_CHEST as ItemLike);
      entries.m_246326_(CobblemonItems.RELIC_COIN_POUCH as ItemLike);
      entries.m_246326_(CobblemonItems.RELIC_COIN_SACK as ItemLike);
      entries.m_246326_(CobblemonItems.DISPLAY_CASE as ItemLike);
      entries.m_246326_(CobblemonItems.APRICORN_LOG as ItemLike);
      entries.m_246326_(CobblemonItems.APRICORN_WOOD as ItemLike);
      entries.m_246326_(CobblemonItems.STRIPPED_APRICORN_LOG as ItemLike);
      entries.m_246326_(CobblemonItems.STRIPPED_APRICORN_WOOD as ItemLike);
      entries.m_246326_(CobblemonItems.APRICORN_PLANKS as ItemLike);
      entries.m_246326_(CobblemonItems.APRICORN_STAIRS as ItemLike);
      entries.m_246326_(CobblemonItems.APRICORN_SLAB as ItemLike);
      entries.m_246326_(CobblemonItems.APRICORN_FENCE as ItemLike);
      entries.m_246326_(CobblemonItems.APRICORN_FENCE_GATE as ItemLike);
      entries.m_246326_(CobblemonItems.APRICORN_DOOR as ItemLike);
      entries.m_246326_(CobblemonItems.APRICORN_TRAPDOOR as ItemLike);
      entries.m_246326_(CobblemonItems.APRICORN_BUTTON as ItemLike);
      entries.m_246326_(CobblemonItems.APRICORN_PRESSURE_PLATE as ItemLike);
      entries.m_246326_(CobblemonItems.APRICORN_SIGN as ItemLike);
      entries.m_246326_(CobblemonItems.APRICORN_HANGING_SIGN as ItemLike);
      entries.m_246326_(CobblemonItems.APRICORN_LEAVES as ItemLike);
      entries.m_246326_(CobblemonItems.TUMBLESTONE_BLOCK as ItemLike);
      entries.m_246326_(CobblemonItems.BLACK_TUMBLESTONE_BLOCK as ItemLike);
      entries.m_246326_(CobblemonItems.SKY_TUMBLESTONE_BLOCK as ItemLike);
      entries.m_246326_(CobblemonItems.DAWN_STONE_ORE as ItemLike);
      entries.m_246326_(CobblemonItems.DEEPSLATE_DAWN_STONE_ORE as ItemLike);
      entries.m_246326_(CobblemonItems.DUSK_STONE_ORE as ItemLike);
      entries.m_246326_(CobblemonItems.DEEPSLATE_DUSK_STONE_ORE as ItemLike);
      entries.m_246326_(CobblemonItems.FIRE_STONE_ORE as ItemLike);
      entries.m_246326_(CobblemonItems.DEEPSLATE_FIRE_STONE_ORE as ItemLike);
      entries.m_246326_(CobblemonItems.NETHER_FIRE_STONE_ORE as ItemLike);
      entries.m_246326_(CobblemonItems.ICE_STONE_ORE as ItemLike);
      entries.m_246326_(CobblemonItems.DEEPSLATE_ICE_STONE_ORE as ItemLike);
      entries.m_246326_(CobblemonItems.LEAF_STONE_ORE as ItemLike);
      entries.m_246326_(CobblemonItems.DEEPSLATE_LEAF_STONE_ORE as ItemLike);
      entries.m_246326_(CobblemonItems.MOON_STONE_ORE as ItemLike);
      entries.m_246326_(CobblemonItems.DEEPSLATE_MOON_STONE_ORE as ItemLike);
      entries.m_246326_(CobblemonItems.DRIPSTONE_MOON_STONE_ORE as ItemLike);
      entries.m_246326_(CobblemonItems.SHINY_STONE_ORE as ItemLike);
      entries.m_246326_(CobblemonItems.DEEPSLATE_SHINY_STONE_ORE as ItemLike);
      entries.m_246326_(CobblemonItems.SUN_STONE_ORE as ItemLike);
      entries.m_246326_(CobblemonItems.DEEPSLATE_SUN_STONE_ORE as ItemLike);
      entries.m_246326_(CobblemonItems.TERRACOTTA_SUN_STONE_ORE as ItemLike);
      entries.m_246326_(CobblemonItems.THUNDER_STONE_ORE as ItemLike);
      entries.m_246326_(CobblemonItems.DEEPSLATE_THUNDER_STONE_ORE as ItemLike);
      entries.m_246326_(CobblemonItems.WATER_STONE_ORE as ItemLike);
      entries.m_246326_(CobblemonItems.DEEPSLATE_WATER_STONE_ORE as ItemLike);
   }

   private fun consumableEntries(displayContext: DisplayContext, entries: Entries) {
      entries.m_246326_(CobblemonItems.ROASTED_LEEK as ItemLike);
      entries.m_246326_(CobblemonItems.LEEK_AND_POTATO_STEW as ItemLike);
      entries.m_246326_(CobblemonItems.BRAISED_VIVICHOKE as ItemLike);
      entries.m_246326_(CobblemonItems.VIVICHOKE_DIP as ItemLike);
      entries.m_246326_(CobblemonItems.BERRY_JUICE as ItemLike);
      entries.m_246326_(CobblemonItems.REMEDY as ItemLike);
      entries.m_246326_(CobblemonItems.FINE_REMEDY as ItemLike);
      entries.m_246326_(CobblemonItems.SUPERB_REMEDY as ItemLike);
      entries.m_246326_(CobblemonItems.HEAL_POWDER as ItemLike);
      entries.m_246326_(CobblemonItems.MEDICINAL_BREW as ItemLike);
      entries.m_246326_(CobblemonItems.POTION as ItemLike);
      entries.m_246326_(CobblemonItems.SUPER_POTION as ItemLike);
      entries.m_246326_(CobblemonItems.HYPER_POTION as ItemLike);
      entries.m_246326_(CobblemonItems.MAX_POTION as ItemLike);
      entries.m_246326_(CobblemonItems.FULL_RESTORE as ItemLike);
      entries.m_246326_(CobblemonItems.ANTIDOTE as ItemLike);
      entries.m_246326_(CobblemonItems.AWAKENING as ItemLike);
      entries.m_246326_(CobblemonItems.BURN_HEAL as ItemLike);
      entries.m_246326_(CobblemonItems.ICE_HEAL as ItemLike);
      entries.m_246326_(CobblemonItems.PARALYZE_HEAL as ItemLike);
      entries.m_246326_(CobblemonItems.FULL_HEAL as ItemLike);
      entries.m_246326_(CobblemonItems.ETHER as ItemLike);
      entries.m_246326_(CobblemonItems.MAX_ETHER as ItemLike);
      entries.m_246326_(CobblemonItems.ELIXIR as ItemLike);
      entries.m_246326_(CobblemonItems.MAX_ELIXIR as ItemLike);
      entries.m_246326_(CobblemonItems.REVIVE as ItemLike);
      entries.m_246326_(CobblemonItems.MAX_REVIVE as ItemLike);
      entries.m_246326_(CobblemonItems.X_ATTACK as ItemLike);
      entries.m_246326_(CobblemonItems.X_DEFENSE as ItemLike);
      entries.m_246326_(CobblemonItems.X_SP_ATK as ItemLike);
      entries.m_246326_(CobblemonItems.X_SP_DEF as ItemLike);
      entries.m_246326_(CobblemonItems.X_SPEED as ItemLike);
      entries.m_246326_(CobblemonItems.X_ACCURACY as ItemLike);
      entries.m_246326_(CobblemonItems.DIRE_HIT as ItemLike);
      entries.m_246326_(CobblemonItems.GUARD_SPEC as ItemLike);
      entries.m_246326_(CobblemonItems.HEALTH_FEATHER as ItemLike);
      entries.m_246326_(CobblemonItems.MUSCLE_FEATHER as ItemLike);
      entries.m_246326_(CobblemonItems.RESIST_FEATHER as ItemLike);
      entries.m_246326_(CobblemonItems.GENIUS_FEATHER as ItemLike);
      entries.m_246326_(CobblemonItems.CLEVER_FEATHER as ItemLike);
      entries.m_246326_(CobblemonItems.SWIFT_FEATHER as ItemLike);
      entries.m_246326_(CobblemonItems.HP_UP as ItemLike);
      entries.m_246326_(CobblemonItems.PROTEIN as ItemLike);
      entries.m_246326_(CobblemonItems.IRON as ItemLike);
      entries.m_246326_(CobblemonItems.CALCIUM as ItemLike);
      entries.m_246326_(CobblemonItems.ZINC as ItemLike);
      entries.m_246326_(CobblemonItems.CARBOS as ItemLike);
      entries.m_246326_(CobblemonItems.PP_UP as ItemLike);
      entries.m_246326_(CobblemonItems.PP_MAX as ItemLike);
      entries.m_246326_(CobblemonItems.EXPERIENCE_CANDY_XS as ItemLike);
      entries.m_246326_(CobblemonItems.EXPERIENCE_CANDY_S as ItemLike);
      entries.m_246326_(CobblemonItems.EXPERIENCE_CANDY_M as ItemLike);
      entries.m_246326_(CobblemonItems.EXPERIENCE_CANDY_L as ItemLike);
      entries.m_246326_(CobblemonItems.EXPERIENCE_CANDY_XL as ItemLike);
      entries.m_246326_(CobblemonItems.RARE_CANDY as ItemLike);
      entries.m_246326_(CobblemonItems.LONELY_MINT as ItemLike);
      entries.m_246326_(CobblemonItems.ADAMANT_MINT as ItemLike);
      entries.m_246326_(CobblemonItems.NAUGHTY_MINT as ItemLike);
      entries.m_246326_(CobblemonItems.BRAVE_MINT as ItemLike);
      entries.m_246326_(CobblemonItems.BOLD_MINT as ItemLike);
      entries.m_246326_(CobblemonItems.IMPISH_MINT as ItemLike);
      entries.m_246326_(CobblemonItems.LAX_MINT as ItemLike);
      entries.m_246326_(CobblemonItems.RELAXED_MINT as ItemLike);
      entries.m_246326_(CobblemonItems.MODEST_MINT as ItemLike);
      entries.m_246326_(CobblemonItems.MILD_MINT as ItemLike);
      entries.m_246326_(CobblemonItems.RASH_MINT as ItemLike);
      entries.m_246326_(CobblemonItems.QUIET_MINT as ItemLike);
      entries.m_246326_(CobblemonItems.CALM_MINT as ItemLike);
      entries.m_246326_(CobblemonItems.GENTLE_MINT as ItemLike);
      entries.m_246326_(CobblemonItems.CAREFUL_MINT as ItemLike);
      entries.m_246326_(CobblemonItems.SASSY_MINT as ItemLike);
      entries.m_246326_(CobblemonItems.TIMID_MINT as ItemLike);
      entries.m_246326_(CobblemonItems.HASTY_MINT as ItemLike);
      entries.m_246326_(CobblemonItems.JOLLY_MINT as ItemLike);
      entries.m_246326_(CobblemonItems.NAIVE_MINT as ItemLike);
      entries.m_246326_(CobblemonItems.SERIOUS_MINT as ItemLike);
      entries.m_246326_(CobblemonItems.ABILITY_CAPSULE as ItemLike);
      entries.m_246326_(CobblemonItems.ABILITY_PATCH as ItemLike);
   }

   private fun evolutionItemEntries(displayContext: DisplayContext, entries: Entries) {
      entries.m_246326_(CobblemonItems.FIRE_STONE as ItemLike);
      entries.m_246326_(CobblemonItems.WATER_STONE as ItemLike);
      entries.m_246326_(CobblemonItems.THUNDER_STONE as ItemLike);
      entries.m_246326_(CobblemonItems.LEAF_STONE as ItemLike);
      entries.m_246326_(CobblemonItems.MOON_STONE as ItemLike);
      entries.m_246326_(CobblemonItems.SUN_STONE as ItemLike);
      entries.m_246326_(CobblemonItems.SHINY_STONE as ItemLike);
      entries.m_246326_(CobblemonItems.DUSK_STONE as ItemLike);
      entries.m_246326_(CobblemonItems.DAWN_STONE as ItemLike);
      entries.m_246326_(CobblemonItems.ICE_STONE as ItemLike);
      entries.m_246326_(CobblemonItems.LINK_CABLE as ItemLike);
      entries.m_246326_(CobblemonItems.KINGS_ROCK as ItemLike);
      entries.m_246326_(CobblemonItems.GALARICA_CUFF as ItemLike);
      entries.m_246326_(CobblemonItems.GALARICA_WREATH as ItemLike);
      entries.m_246326_(CobblemonItems.METAL_COAT as ItemLike);
      entries.m_246326_(CobblemonItems.BLACK_AUGURITE as ItemLike);
      entries.m_246326_(CobblemonItems.PROTECTOR as ItemLike);
      entries.m_246326_(CobblemonItems.OVAL_STONE as ItemLike);
      entries.m_246326_(CobblemonItems.DRAGON_SCALE as ItemLike);
      entries.m_246326_(CobblemonItems.ELECTIRIZER as ItemLike);
      entries.m_246326_(CobblemonItems.MAGMARIZER as ItemLike);
      entries.m_246326_(CobblemonItems.UPGRADE as ItemLike);
      entries.m_246326_(CobblemonItems.DUBIOUS_DISC as ItemLike);
      entries.m_246326_(CobblemonItems.RAZOR_FANG as ItemLike);
      entries.m_246326_(CobblemonItems.RAZOR_CLAW as ItemLike);
      entries.m_246326_(CobblemonItems.PEAT_BLOCK as ItemLike);
      entries.m_246326_(CobblemonItems.PRISM_SCALE as ItemLike);
      entries.m_246326_(CobblemonItems.REAPER_CLOTH as ItemLike);
      entries.m_246326_(CobblemonItems.DEEP_SEA_TOOTH as ItemLike);
      entries.m_246326_(CobblemonItems.DEEP_SEA_SCALE as ItemLike);
      entries.m_246326_(CobblemonItems.SACHET as ItemLike);
      entries.m_246326_(CobblemonItems.WHIPPED_DREAM as ItemLike);
      entries.m_246326_(CobblemonItems.TART_APPLE as ItemLike);
      entries.m_246326_(CobblemonItems.SWEET_APPLE as ItemLike);
      entries.m_246326_(CobblemonItems.CRACKED_POT as ItemLike);
      entries.m_246326_(CobblemonItems.CHIPPED_POT as ItemLike);
      entries.m_246326_(CobblemonItems.MASTERPIECE_TEACUP as ItemLike);
      entries.m_246326_(CobblemonItems.UNREMARKABLE_TEACUP as ItemLike);
      entries.m_246326_(CobblemonItems.STRAWBERRY_SWEET as ItemLike);
      entries.m_246326_(CobblemonItems.LOVE_SWEET as ItemLike);
      entries.m_246326_(CobblemonItems.BERRY_SWEET as ItemLike);
      entries.m_246326_(CobblemonItems.CLOVER_SWEET as ItemLike);
      entries.m_246326_(CobblemonItems.FLOWER_SWEET as ItemLike);
      entries.m_246326_(CobblemonItems.STAR_SWEET as ItemLike);
      entries.m_246326_(CobblemonItems.RIBBON_SWEET as ItemLike);
      entries.m_246326_(CobblemonItems.AUSPICIOUS_ARMOR as ItemLike);
      entries.m_246326_(CobblemonItems.MALICIOUS_ARMOR as ItemLike);
   }

   private fun heldItemEntries(displayContext: DisplayContext, entries: Entries) {
      entries.m_246326_(CobblemonItems.ABILITY_SHIELD as ItemLike);
      entries.m_246326_(CobblemonItems.ABSORB_BULB as ItemLike);
      entries.m_246326_(CobblemonItems.AIR_BALLOON as ItemLike);
      entries.m_246326_(CobblemonItems.ASSAULT_VEST as ItemLike);
      entries.m_246326_(CobblemonItems.BIG_ROOT as ItemLike);
      entries.m_246326_(CobblemonItems.BINDING_BAND as ItemLike);
      entries.m_246326_(CobblemonItems.BLACK_BELT as ItemLike);
      entries.m_246326_(CobblemonItems.BLACK_GLASSES as ItemLike);
      entries.m_246326_(CobblemonItems.BLACK_SLUDGE as ItemLike);
      entries.m_246326_(CobblemonItems.BLUNDER_POLICY as ItemLike);
      entries.m_246326_(CobblemonItems.BRIGHT_POWDER as ItemLike);
      entries.m_246326_(CobblemonItems.CELL_BATTERY as ItemLike);
      entries.m_246326_(CobblemonItems.CHARCOAL as ItemLike);
      entries.m_246326_(CobblemonItems.CHOICE_BAND as ItemLike);
      entries.m_246326_(CobblemonItems.CHOICE_SCARF as ItemLike);
      entries.m_246326_(CobblemonItems.CHOICE_SPECS as ItemLike);
      entries.m_246326_(CobblemonItems.CLEANSE_TAG as ItemLike);
      entries.m_246326_(CobblemonItems.COVERT_CLOAK as ItemLike);
      entries.m_246326_(CobblemonItems.DAMP_ROCK as ItemLike);
      entries.m_246326_(CobblemonItems.DEEP_SEA_SCALE as ItemLike);
      entries.m_246326_(CobblemonItems.DEEP_SEA_TOOTH as ItemLike);
      entries.m_246326_(CobblemonItems.DESTINY_KNOT as ItemLike);
      entries.m_246326_(CobblemonItems.DRAGON_FANG as ItemLike);
      entries.m_246326_(CobblemonItems.EJECT_BUTTON as ItemLike);
      entries.m_246326_(CobblemonItems.EVERSTONE as ItemLike);
      entries.m_246326_(CobblemonItems.EVIOLITE as ItemLike);
      entries.m_246326_(CobblemonItems.EXPERT_BELT as ItemLike);
      entries.m_246326_(CobblemonItems.EXP_SHARE as ItemLike);
      entries.m_246326_(CobblemonItems.FAIRY_FEATHER as ItemLike);
      entries.m_246326_(CobblemonItems.FLAME_ORB as ItemLike);
      entries.m_246326_(CobblemonItems.FLOAT_STONE as ItemLike);
      entries.m_246326_(CobblemonItems.FOCUS_BAND as ItemLike);
      entries.m_246326_(CobblemonItems.FOCUS_SASH as ItemLike);
      entries.m_246326_(CobblemonItems.HARD_STONE as ItemLike);
      entries.m_246326_(CobblemonItems.HEAT_ROCK as ItemLike);
      entries.m_246326_(CobblemonItems.HEAVY_DUTY_BOOTS as ItemLike);
      entries.m_246326_(CobblemonItems.ICY_ROCK as ItemLike);
      entries.m_246326_(CobblemonItems.IRON_BALL as ItemLike);
      entries.m_246326_(CobblemonItems.KINGS_ROCK as ItemLike);
      entries.m_246326_(CobblemonItems.LEFTOVERS as ItemLike);
      entries.m_246326_(CobblemonItems.LIFE_ORB as ItemLike);
      entries.m_246326_(CobblemonItems.LIGHT_BALL as ItemLike);
      entries.m_246326_(CobblemonItems.LIGHT_CLAY as ItemLike);
      entries.m_246326_(CobblemonItems.LOADED_DICE as ItemLike);
      entries.m_246326_(CobblemonItems.LUCKY_EGG as ItemLike);
      entries.m_246326_(CobblemonItems.MAGNET as ItemLike);
      entries.m_246326_(CobblemonItems.MENTAL_HERB as ItemLike);
      entries.m_246326_(CobblemonItems.METAL_COAT as ItemLike);
      entries.m_246326_(CobblemonItems.METAL_POWDER as ItemLike);
      entries.m_246326_(CobblemonItems.MIRACLE_SEED as ItemLike);
      entries.m_246326_(CobblemonItems.MIRROR_HERB as ItemLike);
      entries.m_246326_(CobblemonItems.MUSCLE_BAND as ItemLike);
      entries.m_246326_(CobblemonItems.MYSTIC_WATER as ItemLike);
      entries.m_246326_(CobblemonItems.NEVER_MELT_ICE as ItemLike);
      entries.m_246326_(CobblemonItems.POISON_BARB as ItemLike);
      entries.m_246326_(CobblemonItems.POWER_ANKLET as ItemLike);
      entries.m_246326_(CobblemonItems.POWER_BAND as ItemLike);
      entries.m_246326_(CobblemonItems.POWER_BELT as ItemLike);
      entries.m_246326_(CobblemonItems.POWER_BRACER as ItemLike);
      entries.m_246326_(CobblemonItems.POWER_LENS as ItemLike);
      entries.m_246326_(CobblemonItems.POWER_WEIGHT as ItemLike);
      entries.m_246326_(CobblemonItems.POWER_HERB as ItemLike);
      entries.m_246326_(CobblemonItems.QUICK_CLAW as ItemLike);
      entries.m_246326_(CobblemonItems.QUICK_POWDER as ItemLike);
      entries.m_246326_(CobblemonItems.RAZOR_CLAW as ItemLike);
      entries.m_246326_(CobblemonItems.RAZOR_FANG as ItemLike);
      entries.m_246326_(CobblemonItems.RED_CARD as ItemLike);
      entries.m_246326_(CobblemonItems.RING_TARGET as ItemLike);
      entries.m_246326_(CobblemonItems.ROCKY_HELMET as ItemLike);
      entries.m_246326_(CobblemonItems.SAFETY_GOGGLES as ItemLike);
      entries.m_246326_(CobblemonItems.SHARP_BEAK as ItemLike);
      entries.m_246326_(CobblemonItems.SHELL_BELL as ItemLike);
      entries.m_246326_(CobblemonItems.SILK_SCARF as ItemLike);
      entries.m_246326_(CobblemonItems.SILVER_POWDER as ItemLike);
      entries.m_246326_(CobblemonItems.SMOKE_BALL as ItemLike);
      entries.m_246326_(CobblemonItems.SMOOTH_ROCK as ItemLike);
      entries.m_246326_(CobblemonItems.SOFT_SAND as ItemLike);
      entries.m_246326_(CobblemonItems.SOOTHE_BELL as ItemLike);
      entries.m_246326_(CobblemonItems.SPELL_TAG as ItemLike);
      entries.m_246326_(CobblemonItems.STICKY_BARB as ItemLike);
      entries.m_246326_(CobblemonItems.TOXIC_ORB as ItemLike);
      entries.m_246326_(CobblemonItems.TWISTED_SPOON as ItemLike);
      entries.m_246326_(CobblemonItems.WEAKNESS_POLICY as ItemLike);
      entries.m_246326_(CobblemonItems.WHITE_HERB as ItemLike);
      entries.m_246326_(CobblemonItems.WISE_GLASSES as ItemLike);
      entries.m_246326_(CobblemonItems.MEDICINAL_LEEK as ItemLike);
      entries.m_246326_(Items.f_42500_ as ItemLike);
      entries.m_246326_(Items.f_42452_ as ItemLike);
      entries.m_246326_(CobblemonItems.NORMAL_GEM as ItemLike);
      entries.m_246326_(CobblemonItems.FIRE_GEM as ItemLike);
      entries.m_246326_(CobblemonItems.WATER_GEM as ItemLike);
      entries.m_246326_(CobblemonItems.GRASS_GEM as ItemLike);
      entries.m_246326_(CobblemonItems.ELECTRIC_GEM as ItemLike);
      entries.m_246326_(CobblemonItems.ICE_GEM as ItemLike);
      entries.m_246326_(CobblemonItems.FIGHTING_GEM as ItemLike);
      entries.m_246326_(CobblemonItems.POISON_GEM as ItemLike);
      entries.m_246326_(CobblemonItems.GROUND_GEM as ItemLike);
      entries.m_246326_(CobblemonItems.FLYING_GEM as ItemLike);
      entries.m_246326_(CobblemonItems.PSYCHIC_GEM as ItemLike);
      entries.m_246326_(CobblemonItems.BUG_GEM as ItemLike);
      entries.m_246326_(CobblemonItems.ROCK_GEM as ItemLike);
      entries.m_246326_(CobblemonItems.GHOST_GEM as ItemLike);
      entries.m_246326_(CobblemonItems.DRAGON_GEM as ItemLike);
      entries.m_246326_(CobblemonItems.DARK_GEM as ItemLike);
      entries.m_246326_(CobblemonItems.STEEL_GEM as ItemLike);
      entries.m_246326_(CobblemonItems.FAIRY_GEM as ItemLike);
   }

   private fun pokeballentries(displayContext: DisplayContext, entries: Entries) {
      val `$this$forEach$iv`: java.lang.Iterable;
      for (Object element$iv : $this$forEach$iv) {
         entries.m_246326_(`element$iv` as ItemLike);
      }
   }

   private fun foodInjections(injector: bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.item.group.CobblemonItemGroups.Injector) {
      var var10001: ItemLike = CobblemonItems.MEDICINAL_LEEK as ItemLike;
      var var10002: Item = Items.f_42675_;
      injector.putAfter(var10001, var10002 as ItemLike);
      injector.putAfter(CobblemonItems.ROASTED_LEEK as ItemLike, CobblemonItems.MEDICINAL_LEEK as ItemLike);
      injector.putAfter(CobblemonItems.BRAISED_VIVICHOKE as ItemLike, CobblemonItems.ROASTED_LEEK as ItemLike);
      var10001 = CobblemonItems.LEEK_AND_POTATO_STEW as ItemLike;
      var10002 = Items.f_42699_;
      injector.putAfter(var10001, var10002 as ItemLike);
      injector.putAfter(CobblemonItems.VIVICHOKE_DIP as ItemLike, CobblemonItems.LEEK_AND_POTATO_STEW as ItemLike);
   }

   private fun toolsAndUtilitiesInjections(injector: bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.item.group.CobblemonItemGroups.Injector) {
      val var10001: ItemLike = CobblemonItems.APRICORN_BOAT as ItemLike;
      val var10002: Item = Items.f_244260_;
      injector.putAfter(var10001, var10002 as ItemLike);
      injector.putAfter(CobblemonItems.APRICORN_CHEST_BOAT as ItemLike, CobblemonItems.APRICORN_BOAT as ItemLike);
   }

   private fun ingredientsInjections(injector: bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.item.group.CobblemonItemGroups.Injector) {
      var var10001: ItemLike = CobblemonItems.BYGONE_SHERD as ItemLike;
      var var10002: Item = Items.f_279636_;
      injector.putAfter(var10001, var10002 as ItemLike);
      injector.putAfter(CobblemonItems.CAPTURE_SHERD as ItemLike, CobblemonItems.BYGONE_SHERD as ItemLike);
      injector.putAfter(CobblemonItems.DOME_SHERD as ItemLike, CobblemonItems.CAPTURE_SHERD as ItemLike);
      injector.putAfter(CobblemonItems.HELIX_SHERD as ItemLike, CobblemonItems.DOME_SHERD as ItemLike);
      injector.putAfter(CobblemonItems.NOSTALGIC_SHERD as ItemLike, CobblemonItems.HELIX_SHERD as ItemLike);
      injector.putAfter(CobblemonItems.SUSPICIOUS_SHERD as ItemLike, CobblemonItems.NOSTALGIC_SHERD as ItemLike);
      var10001 = CobblemonItems.AUTOMATON_ARMOR_TRIM_SMITHING_TEMPLATE as ItemLike;
      var10002 = Items.f_266114_;
      injector.putAfter(var10001, var10002 as ItemLike);
   }

   @JvmStatic
   fun {
      var var10000: CobblemonItemGroups = INSTANCE;
      var var10001: ResourceKey = ResourceKey.m_135785_(BuiltInRegistries.f_279662_.m_123023_(), new ResourceLocation("food_and_drinks"));
      FOOD_INJECTIONS = var10000.inject(
         var10001,
         (
            new Function1<CobblemonItemGroups.Injector, Unit>(INSTANCE) {
               {
                  super(
                     1,
                     receiver,
                     CobblemonItemGroups::class.java,
                     "foodInjections",
                     "foodInjections(Lcom/cobblemon/mod/common/item/group/CobblemonItemGroups$Injector;)V",
                     0
                  );
               }

               public final void invoke(@NotNull CobblemonItemGroups.Injector p0) {
                  CobblemonItemGroups.access$foodInjections(this.receiver as CobblemonItemGroups, p0);
               }
            }
         ) as (CobblemonItemGroups.Injector?) -> Unit
      );
      var10000 = INSTANCE;
      var10001 = ResourceKey.m_135785_(BuiltInRegistries.f_279662_.m_123023_(), new ResourceLocation("tools_and_utilities"));
      TOOLS_AND_UTILITIES_INJECTIONS = var10000.inject(
         var10001,
         (
            new Function1<CobblemonItemGroups.Injector, Unit>(INSTANCE) {
               {
                  super(
                     1,
                     receiver,
                     CobblemonItemGroups::class.java,
                     "toolsAndUtilitiesInjections",
                     "toolsAndUtilitiesInjections(Lcom/cobblemon/mod/common/item/group/CobblemonItemGroups$Injector;)V",
                     0
                  );
               }

               public final void invoke(@NotNull CobblemonItemGroups.Injector p0) {
                  CobblemonItemGroups.access$toolsAndUtilitiesInjections(this.receiver as CobblemonItemGroups, p0);
               }
            }
         ) as (CobblemonItemGroups.Injector?) -> Unit
      );
      var10000 = INSTANCE;
      var10001 = ResourceKey.m_135785_(BuiltInRegistries.f_279662_.m_123023_(), new ResourceLocation("ingredients"));
      INGREDIENTS_INJECTIONS = var10000.inject(
         var10001,
         (
            new Function1<CobblemonItemGroups.Injector, Unit>(INSTANCE) {
               {
                  super(
                     1,
                     receiver,
                     CobblemonItemGroups::class.java,
                     "ingredientsInjections",
                     "ingredientsInjections(Lcom/cobblemon/mod/common/item/group/CobblemonItemGroups$Injector;)V",
                     0
                  );
               }

               public final void invoke(@NotNull CobblemonItemGroups.Injector p0) {
                  CobblemonItemGroups.access$ingredientsInjections(this.receiver as CobblemonItemGroups, p0);
               }
            }
         ) as (CobblemonItemGroups.Injector?) -> Unit
      );
   }

   public interface Injector {
      public abstract fun putFirst(item: ItemLike) {
      }

      public abstract fun putBefore(item: ItemLike, target: ItemLike) {
      }

      public abstract fun putAfter(item: ItemLike, target: ItemLike) {
      }

      public abstract fun putLast(item: ItemLike) {
      }
   }

   public data ItemGroupHolder(key: ResourceKey<CreativeModeTab>,
      displayIconProvider: () -> ItemStack,
      entryCollector: EntryCollector,
      displayName: Component = ...
   ) {
      public final val displayIconProvider: () -> ItemStack
      public final val displayName: Component
      public final val entryCollector: EntryCollector
      public final val key: ResourceKey<CreativeModeTab>

      init {
         this.key = key;
         this.displayIconProvider = displayIconProvider;
         this.entryCollector = entryCollector;
         this.displayName = displayName;
      }

      public operator fun component1(): ResourceKey<CreativeModeTab> {
         return this.key;
      }

      public operator fun component2(): () -> ItemStack {
         return this.displayIconProvider;
      }

      public operator fun component3(): EntryCollector {
         return this.entryCollector;
      }

      public operator fun component4(): Component {
         return this.displayName;
      }

      public fun copy(
         key: ResourceKey<CreativeModeTab> = ...,
         displayIconProvider: () -> ItemStack = ...,
         entryCollector: EntryCollector = ...,
         displayName: Component = ...
      ): bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.item.group.CobblemonItemGroups.ItemGroupHolder {
         return new CobblemonItemGroups.ItemGroupHolder(key, displayIconProvider, entryCollector, displayName);
      }

      public override fun toString(): String {
         return "ItemGroupHolder(key=${this.key}, displayIconProvider=${this.displayIconProvider}, entryCollector=${this.entryCollector}, displayName=${this.displayName})";
      }

      public override fun hashCode(): Int {
         return ((this.key.hashCode() * 31 + this.displayIconProvider.hashCode()) * 31 + this.entryCollector.hashCode()) * 31 + this.displayName.hashCode();
      }

      public override operator fun equals(other: Any?): Boolean {
         if (this === other) {
            return true;
         } else if (other !is CobblemonItemGroups.ItemGroupHolder) {
            return false;
         } else {
            val var2: CobblemonItemGroups.ItemGroupHolder = other as CobblemonItemGroups.ItemGroupHolder;
            if (!(this.key == (other as CobblemonItemGroups.ItemGroupHolder).key)) {
               return false;
            } else if (!(this.displayIconProvider == var2.displayIconProvider)) {
               return false;
            } else if (!(this.entryCollector == var2.entryCollector)) {
               return false;
            } else {
               return this.displayName == var2.displayName;
            }
         }
      }
   }
}
