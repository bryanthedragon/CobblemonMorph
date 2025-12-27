package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.abilities.CommonAbility
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.item.ability.AbilityChanger
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.mulch.MulchVariant
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokeball.PokeBalls
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.Natures
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.stats.Stats
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.status.Statuses
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.block.BerryBlock
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.block.EnergyRootBlock
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.block.MedicinalLeekBlock
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.block.MintBlock
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.entity.boat.CobblemonBoatType
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.item.ApricornItem
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.item.ApricornSeedItem
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.item.BerryItem
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.item.CobblemonBoatItem
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.item.CobblemonItem
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.item.MedicinalLeekItem
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.item.MintLeafItem
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.item.MulchItem
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.item.PokeBallItem
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.item.PokemonItem
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.item.RevivalHerbItem
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.item.TumblestoneItem
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.item.VivichokeItem
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.item.armor.CobblemonArmorTrims
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.item.battle.DireHitItem
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.item.battle.GuardSpecItem
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.item.battle.XStatItem
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.item.berry.FriendshipRaisingBerryItem
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.item.berry.HealingBerryItem
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.item.berry.PPRestoringBerryItem
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.item.berry.PortionHealingBerryItem
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.item.berry.StatusCuringBerryItem
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.item.berry.VolatileCuringBerryItem
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.item.interactive.BerryJuiceItem
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.item.interactive.CandyItem
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.item.interactive.ElixirItem
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.item.interactive.EnergyRootItem
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.item.interactive.EtherItem
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.item.interactive.FeatherItem
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.item.interactive.HealPowderItem
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.item.interactive.LinkCableItem
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.item.interactive.MintItem
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.item.interactive.PPUpItem
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.item.interactive.PotionItem
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.item.interactive.PotionType
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.item.interactive.RemedyItem
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.item.interactive.ReviveItem
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.item.interactive.StatusCureItem
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.item.interactive.VitaminItem
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.item.interactive.CandyItem.Calculator
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.item.interactive.ability.AbilityChangeItem
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.platform.PlatformRegistry
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokeball.PokeBall
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.Pokemon;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.abilities.HiddenAbility
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.helditem.CobblemonHeldItemManager
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.MiscUtilsKt

import java.util.ArrayList;
import java.util.LinkedHashMap

import net.minecraft.core.DefaultedRegistry
import net.minecraft.core.Registry
import net.minecraft.core.registries.BuiltInRegistries
import net.minecraft.core.registries.Registries
import net.minecraft.resources.ResourceKey
import net.minecraft.resources.ResourceLocation
import net.minecraft.server.level.ServerPlayer
import net.minecraft.world.effect.MobEffectInstance
import net.minecraft.world.effect.MobEffects
import net.minecraft.world.entity.LivingEntity
import net.minecraft.world.food.FoodProperties.Builder
import net.minecraft.world.item.BlockItem
import net.minecraft.world.item.BowlFoodItem
import net.minecraft.world.item.HangingSignItem
import net.minecraft.world.item.Item
import net.minecraft.world.item.ItemStack
import net.minecraft.world.item.Items
import net.minecraft.world.item.SignItem
import net.minecraft.world.item.SmithingTemplateItem
import net.minecraft.world.item.Item.Properties
import net.minecraft.world.level.ItemLike
import net.minecraft.world.level.Level
import net.minecraft.world.level.block.Block
import net.minecraft.world.level.block.ButtonBlock
import net.minecraft.world.level.block.DoorBlock
import net.minecraft.world.level.block.PressurePlateBlock
import net.minecraft.world.level.block.StairBlock
import net.minecraft.world.level.block.TrapDoorBlock

import org.jetbrains.annotations.NotNull
import org.jetbrains.annotations.Nullable

public object CobblemonItems : PlatformRegistry<Registry<Item>, ResourceKey<Registry<Item>>, Item> {
   public final val ABILITY_CAPSULE: AbilityChangeItem<CommonAbility> =
      INSTANCE.create("ability_capsule", new AbilityChangeItem<>(AbilityChanger.Companion.getCOMMON_ABILITY())) as AbilityChangeItem
      public final val ABILITY_PATCH: AbilityChangeItem<HiddenAbility> =
      INSTANCE.create("ability_patch", new AbilityChangeItem<>(AbilityChanger.Companion.getHIDDEN_ABILITY())) as AbilityChangeItem
      public final val ABILITY_SHIELD: CobblemonItem = heldItem$default(INSTANCE, "ability_shield", null, 2, null)
   public final val ABSORB_BULB: CobblemonItem = heldItem$default(INSTANCE, "absorb_bulb", null, 2, null)
   public final val ADAMANT_MINT: MintItem = INSTANCE.mintItem("adamant_mint", new MintItem(Natures.INSTANCE.getADAMANT()))
   public final val AGUAV_BERRY: BerryItem =
      INSTANCE.berryItem("aguav", new PortionHealingBerryItem(CobblemonBlocks.INSTANCE.getAGUAV_BERRY(), true, <unrepresentable>.INSTANCE))
      public final val AIR_BALLOON: CobblemonItem = heldItem$default(INSTANCE, "air_balloon", null, 2, null)
   public final val ANCIENT_AZURE_BALL: PokeBallItem = INSTANCE.pokeBallItem(PokeBalls.INSTANCE.getANCIENT_AZURE_BALL())
   public final val ANCIENT_CITRINE_BALL: PokeBallItem = INSTANCE.pokeBallItem(PokeBalls.INSTANCE.getANCIENT_CITRINE_BALL())
   public final val ANCIENT_FEATHER_BALL: PokeBallItem = INSTANCE.pokeBallItem(PokeBalls.INSTANCE.getANCIENT_FEATHER_BALL())
   public final val ANCIENT_GIGATON_BALL: PokeBallItem = INSTANCE.pokeBallItem(PokeBalls.INSTANCE.getANCIENT_GIGATON_BALL())
   public final val ANCIENT_GREAT_BALL: PokeBallItem = INSTANCE.pokeBallItem(PokeBalls.INSTANCE.getANCIENT_GREAT_BALL())
   public final val ANCIENT_HEAVY_BALL: PokeBallItem = INSTANCE.pokeBallItem(PokeBalls.INSTANCE.getANCIENT_HEAVY_BALL())
   public final val ANCIENT_IVORY_BALL: PokeBallItem = INSTANCE.pokeBallItem(PokeBalls.INSTANCE.getANCIENT_IVORY_BALL())
   public final val ANCIENT_JET_BALL: PokeBallItem = INSTANCE.pokeBallItem(PokeBalls.INSTANCE.getANCIENT_JET_BALL())
   public final val ANCIENT_LEADEN_BALL: PokeBallItem = INSTANCE.pokeBallItem(PokeBalls.INSTANCE.getANCIENT_LEADEN_BALL())
   public final val ANCIENT_ORIGIN_BALL: PokeBallItem = INSTANCE.pokeBallItem(PokeBalls.INSTANCE.getANCIENT_ORIGIN_BALL())
   public final val ANCIENT_POKE_BALL: PokeBallItem = INSTANCE.pokeBallItem(PokeBalls.INSTANCE.getANCIENT_POKE_BALL())
   public final val ANCIENT_ROSEATE_BALL: PokeBallItem = INSTANCE.pokeBallItem(PokeBalls.INSTANCE.getANCIENT_ROSEATE_BALL())
   public final val ANCIENT_SLATE_BALL: PokeBallItem = INSTANCE.pokeBallItem(PokeBalls.INSTANCE.getANCIENT_SLATE_BALL())
   public final val ANCIENT_ULTRA_BALL: PokeBallItem = INSTANCE.pokeBallItem(PokeBalls.INSTANCE.getANCIENT_ULTRA_BALL())
   public final val ANCIENT_VERDANT_BALL: PokeBallItem = INSTANCE.pokeBallItem(PokeBalls.INSTANCE.getANCIENT_VERDANT_BALL())
   public final val ANCIENT_WING_BALL: PokeBallItem = INSTANCE.pokeBallItem(PokeBalls.INSTANCE.getANCIENT_WING_BALL())
   public final val ANTIDOTE: StatusCureItem =
      INSTANCE.create("antidote", new StatusCureItem("item.cobblemon.antidote", Statuses.INSTANCE.getPOISON(), Statuses.INSTANCE.getPOISON_BADLY())) as StatusCureItem
      public final val APICOT_BERRY: BerryItem = INSTANCE.berryItem("apicot", CobblemonBlocks.INSTANCE.getAPICOT_BERRY())
   public final val APRICORN_BOAT: CobblemonBoatItem
   public final val APRICORN_BUTTON: BlockItem
   public final val APRICORN_CHEST_BOAT: CobblemonBoatItem
   public final val APRICORN_DOOR: BlockItem
   public final val APRICORN_FENCE: BlockItem = INSTANCE.blockItem("apricorn_fence", CobblemonBlocks.APRICORN_FENCE as Block)
   public final val APRICORN_FENCE_GATE: BlockItem = INSTANCE.blockItem("apricorn_fence_gate", CobblemonBlocks.APRICORN_FENCE_GATE as Block)
   public final val APRICORN_HANGING_SIGN: HangingSignItem =
      INSTANCE.create(
         "apricorn_hanging_sign",
         new HangingSignItem(CobblemonBlocks.APRICORN_HANGING_SIGN as Block, CobblemonBlocks.APRICORN_WALL_HANGING_SIGN as Block, new Properties().m_41487_(16))
      ) as HangingSignItem
      public final val APRICORN_LEAVES: Item =
      compostableBlockItem$default(INSTANCE, "apricorn_leaves", CobblemonBlocks.APRICORN_LEAVES as Block, 0.0F, 4, null)
      public final val APRICORN_LOG: BlockItem = INSTANCE.blockItem("apricorn_log", CobblemonBlocks.APRICORN_LOG as Block)
   public final val APRICORN_PLANKS: BlockItem = INSTANCE.blockItem("apricorn_planks", CobblemonBlocks.APRICORN_PLANKS)
   public final val APRICORN_PRESSURE_PLATE: BlockItem
   public final val APRICORN_SIGN: SignItem =
      INSTANCE.create(
         "apricorn_sign", new SignItem(new Properties().m_41487_(16), CobblemonBlocks.APRICORN_SIGN as Block, CobblemonBlocks.APRICORN_WALL_SIGN as Block)
      ) as SignItem
      public final val APRICORN_SLAB: BlockItem = INSTANCE.blockItem("apricorn_slab", CobblemonBlocks.APRICORN_SLAB as Block)
   public final val APRICORN_STAIRS: BlockItem
   public final val APRICORN_TRAPDOOR: BlockItem
   public final val APRICORN_WOOD: BlockItem = INSTANCE.blockItem("apricorn_wood", CobblemonBlocks.APRICORN_WOOD as Block)
   public final val ARMOR_FOSSIL: CobblemonItem = INSTANCE.noSettingsItem("armor_fossil")
   public final val ASPEAR_BERRY: BerryItem =
      INSTANCE.berryItem("aspear", new StatusCuringBerryItem(CobblemonBlocks.INSTANCE.getASPEAR_BERRY(), Statuses.INSTANCE.getFROZEN()))
      public final val ASSAULT_VEST: CobblemonItem = heldItem$default(INSTANCE, "assault_vest", null, 2, null)
   public final val AUSPICIOUS_ARMOR: CobblemonItem = heldItem$default(INSTANCE, "auspicious_armor", null, 2, null)
   public final val AUTOMATON_ARMOR_TRIM_SMITHING_TEMPLATE: SmithingTemplateItem
   public final val AWAKENING: StatusCureItem =
      INSTANCE.create("awakening", new StatusCureItem("item.cobblemon.awakening", Statuses.INSTANCE.getSLEEP())) as StatusCureItem
      public final val AZURE_BALL: PokeBallItem = INSTANCE.pokeBallItem(PokeBalls.INSTANCE.getAZURE_BALL())
   public final val BABIRI_BERRY: BerryItem = INSTANCE.berryItem("babiri", CobblemonBlocks.INSTANCE.getBABIRI_BERRY())
   public final val BEAST_BALL: PokeBallItem = INSTANCE.pokeBallItem(PokeBalls.INSTANCE.getBEAST_BALL())
   public final val BELUE_BERRY: BerryItem = INSTANCE.berryItem("belue", CobblemonBlocks.INSTANCE.getBELUE_BERRY())
   public final val BERRY_JUICE: BerryJuiceItem = INSTANCE.create("berry_juice", new BerryJuiceItem()) as BerryJuiceItem
   public final val BERRY_SWEET: CobblemonItem = INSTANCE.noSettingsItem("berry_sweet")
   public final val BIG_ROOT: BlockItem = INSTANCE.blockItem("big_root", CobblemonBlocks.BIG_ROOT)
   public final val BINDING_BAND: CobblemonItem = heldItem$default(INSTANCE, "binding_band", null, 2, null)
   public final val BLACK_APRICORN: ApricornItem = INSTANCE.apricornItem("black", new ApricornItem(CobblemonBlocks.BLACK_APRICORN))
   public final val BLACK_APRICORN_SEED: ApricornSeedItem =
      INSTANCE.apricornSeedItem("black", new ApricornSeedItem(CobblemonBlocks.BLACK_APRICORN_SAPLING, CobblemonBlocks.BLACK_APRICORN))
      public final val BLACK_AUGURITE: CobblemonItem = INSTANCE.noSettingsItem("black_augurite")
   public final val BLACK_BELT: CobblemonItem = heldItem$default(INSTANCE, "black_belt", null, 2, null)
   public final val BLACK_GILDED_CHEST: BlockItem =
      INSTANCE.create("black_gilded_chest", new BlockItem(CobblemonBlocks.BLACK_GILDED_CHEST as Block, new Properties())) as BlockItem
      public final val BLACK_GLASSES: CobblemonItem = heldItem$default(INSTANCE, "black_glasses", null, 2, null)
   public final val BLACK_SLUDGE: CobblemonItem = heldItem$default(INSTANCE, "black_sludge", null, 2, null)
   public final val BLACK_TUMBLESTONE: TumblestoneItem =
      INSTANCE.create("black_tumblestone", new TumblestoneItem(new Properties(), CobblemonBlocks.SMALL_BUDDING_BLACK_TUMBLESTONE)) as TumblestoneItem
      public final val BLACK_TUMBLESTONE_BLOCK: BlockItem = INSTANCE.blockItem("black_tumblestone_block", CobblemonBlocks.BLACK_TUMBLESTONE_BLOCK)
   public final val BLACK_TUMBLESTONE_CLUSTER: BlockItem = INSTANCE.blockItem("black_tumblestone_cluster", CobblemonBlocks.BLACK_TUMBLESTONE_CLUSTER)
   public final val BLUE_APRICORN: ApricornItem = INSTANCE.apricornItem("blue", new ApricornItem(CobblemonBlocks.BLUE_APRICORN))
   public final val BLUE_APRICORN_SEED: ApricornSeedItem =
      INSTANCE.apricornSeedItem("blue", new ApricornSeedItem(CobblemonBlocks.BLUE_APRICORN_SAPLING, CobblemonBlocks.BLUE_APRICORN))
      public final val BLUE_GILDED_CHEST: BlockItem =
      INSTANCE.create("blue_gilded_chest", new BlockItem(CobblemonBlocks.BLUE_GILDED_CHEST as Block, new Properties())) as BlockItem
      public final val BLUE_MINT_LEAF: MintLeafItem = INSTANCE.mintLeaf("blue", new MintLeafItem(MintBlock.MintType.BLUE))
   public final val BLUE_MINT_SEEDS: Item = INSTANCE.mintSeed("blue", MintBlock.MintType.BLUE.getCropBlock())
   public final val BLUK_BERRY: BerryItem = INSTANCE.berryItem("bluk", CobblemonBlocks.INSTANCE.getBLUK_BERRY())
   public final val BLUNDER_POLICY: CobblemonItem = heldItem$default(INSTANCE, "blunder_policy", null, 2, null)
   public final val BOLD_MINT: MintItem = INSTANCE.mintItem("bold_mint", new MintItem(Natures.INSTANCE.getBOLD()))
   public final val BRAISED_VIVICHOKE: Item =
      INSTANCE.create("braised_vivichoke", new Item(new Properties().m_41489_(new Builder().m_38760_(6).m_38758_(0.6F).m_38767_()))) as Item
      public final val BRAVE_MINT: MintItem = INSTANCE.mintItem("brave_mint", new MintItem(Natures.INSTANCE.getBRAVE()))
   public final val BRIGHT_POWDER: CobblemonItem = heldItem$default(INSTANCE, "bright_powder", null, 2, null)
   public final val BUG_GEM: CobblemonItem = INSTANCE.noSettingsItem("bug_gem")
   public final val BURN_HEAL: StatusCureItem =
      INSTANCE.create("burn_heal", new StatusCureItem("item.cobblemon.burn_heal", Statuses.INSTANCE.getBURN())) as StatusCureItem
      public final val BYGONE_SHERD: CobblemonItem = INSTANCE.noSettingsItem("bygone_sherd")
   public final val CALCIUM: VitaminItem = INSTANCE.create("calcium", new VitaminItem(Stats.SPECIAL_ATTACK)) as VitaminItem
   public final val CALM_MINT: MintItem = INSTANCE.mintItem("calm_mint", new MintItem(Natures.INSTANCE.getCALM()))
   public final val CAPTURE_SHERD: CobblemonItem = INSTANCE.noSettingsItem("capture_sherd")
   public final val CARBOS: VitaminItem = INSTANCE.create("carbos", new VitaminItem(Stats.SPEED)) as VitaminItem
   public final val CAREFUL_MINT: MintItem = INSTANCE.mintItem("careful_mint", new MintItem(Natures.INSTANCE.getCAREFUL()))
   public final val CELL_BATTERY: CobblemonItem = heldItem$default(INSTANCE, "cell_battery", null, 2, null)
   public final val CHARCOAL: CobblemonItem = INSTANCE.heldItem("charcoal_stick", "charcoal")
   public final val CHARTI_BERRY: BerryItem = INSTANCE.berryItem("charti", CobblemonBlocks.INSTANCE.getCHARTI_BERRY())
   public final val CHERISH_BALL: PokeBallItem = INSTANCE.pokeBallItem(PokeBalls.INSTANCE.getCHERISH_BALL())
   public final val CHERI_BERRY: BerryItem =
      INSTANCE.berryItem("cheri", new StatusCuringBerryItem(CobblemonBlocks.INSTANCE.getCHERI_BERRY(), Statuses.INSTANCE.getPARALYSIS()))
      public final val CHESTO_BERRY: BerryItem =
      INSTANCE.berryItem("chesto", new StatusCuringBerryItem(CobblemonBlocks.INSTANCE.getCHESTO_BERRY(), Statuses.INSTANCE.getSLEEP()))
      public final val CHILAN_BERRY: BerryItem = INSTANCE.berryItem("chilan", CobblemonBlocks.INSTANCE.getCHILAN_BERRY())
   public final val CHIPPED_POT: CobblemonItem = INSTANCE.noSettingsItem("chipped_pot")
   public final val CHOICE_BAND: CobblemonItem = heldItem$default(INSTANCE, "choice_band", null, 2, null)
   public final val CHOICE_SCARF: CobblemonItem = heldItem$default(INSTANCE, "choice_scarf", null, 2, null)
   public final val CHOICE_SPECS: CobblemonItem = heldItem$default(INSTANCE, "choice_specs", null, 2, null)
   public final val CHOPLE_BERRY: BerryItem = INSTANCE.berryItem("chople", CobblemonBlocks.INSTANCE.getCHOPLE_BERRY())
   public final val CITRINE_BALL: PokeBallItem = INSTANCE.pokeBallItem(PokeBalls.INSTANCE.getCITRINE_BALL())
   public final val CLAW_FOSSIL: CobblemonItem = INSTANCE.noSettingsItem("claw_fossil")
   public final val CLEANSE_TAG: CobblemonItem = heldItem$default(INSTANCE, "cleanse_tag", null, 2, null)
   public final val CLEVER_FEATHER: FeatherItem = INSTANCE.create("clever_feather", new FeatherItem(Stats.SPECIAL_DEFENCE)) as FeatherItem
   public final val CLOVER_SWEET: CobblemonItem = INSTANCE.noSettingsItem("clover_sweet")
   public final val COARSE_MULCH: MulchItem = INSTANCE.mulchItem("coarse_mulch", MulchVariant.COARSE)
   public final val COBA_BERRY: BerryItem = INSTANCE.berryItem("coba", CobblemonBlocks.INSTANCE.getCOBA_BERRY())
   public final val COLBUR_BERRY: BerryItem = INSTANCE.berryItem("colbur", CobblemonBlocks.INSTANCE.getCOLBUR_BERRY())
   public final val CORNN_BERRY: BerryItem = INSTANCE.berryItem("cornn", CobblemonBlocks.INSTANCE.getCORNN_BERRY())
   public final val COVERT_CLOAK: CobblemonItem = heldItem$default(INSTANCE, "covert_cloak", null, 2, null)
   public final val COVER_FOSSIL: CobblemonItem = INSTANCE.noSettingsItem("cover_fossil")
   public final val CRACKED_POT: CobblemonItem = INSTANCE.noSettingsItem("cracked_pot")
   public final val CUSTAP_BERRY: BerryItem = INSTANCE.berryItem("custap", CobblemonBlocks.INSTANCE.getCUSTAP_BERRY())
   public final val CYAN_MINT_LEAF: MintLeafItem = INSTANCE.mintLeaf("cyan", new MintLeafItem(MintBlock.MintType.CYAN))
   public final val CYAN_MINT_SEEDS: Item = INSTANCE.mintSeed("cyan", MintBlock.MintType.CYAN.getCropBlock())
   public final val DAMP_ROCK: CobblemonItem = heldItem$default(INSTANCE, "damp_rock", null, 2, null)
   public final val DARK_GEM: CobblemonItem = INSTANCE.noSettingsItem("dark_gem")
   public final val DAWN_STONE: CobblemonItem = INSTANCE.noSettingsItem("dawn_stone")
   public final val DAWN_STONE_ORE: BlockItem = INSTANCE.blockItem("dawn_stone_ore", CobblemonBlocks.DAWN_STONE_ORE as Block)
   public final val DEEPSLATE_DAWN_STONE_ORE: BlockItem = INSTANCE.blockItem("deepslate_dawn_stone_ore", CobblemonBlocks.DEEPSLATE_DAWN_STONE_ORE as Block)
   public final val DEEPSLATE_DUSK_STONE_ORE: BlockItem = INSTANCE.blockItem("deepslate_dusk_stone_ore", CobblemonBlocks.DEEPSLATE_DUSK_STONE_ORE as Block)
   public final val DEEPSLATE_FIRE_STONE_ORE: BlockItem = INSTANCE.blockItem("deepslate_fire_stone_ore", CobblemonBlocks.DEEPSLATE_FIRE_STONE_ORE as Block)
   public final val DEEPSLATE_ICE_STONE_ORE: BlockItem = INSTANCE.blockItem("deepslate_ice_stone_ore", CobblemonBlocks.DEEPSLATE_ICE_STONE_ORE as Block)
   public final val DEEPSLATE_LEAF_STONE_ORE: BlockItem = INSTANCE.blockItem("deepslate_leaf_stone_ore", CobblemonBlocks.DEEPSLATE_LEAF_STONE_ORE as Block)
   public final val DEEPSLATE_MOON_STONE_ORE: BlockItem = INSTANCE.blockItem("deepslate_moon_stone_ore", CobblemonBlocks.DEEPSLATE_MOON_STONE_ORE as Block)
   public final val DEEPSLATE_SHINY_STONE_ORE: BlockItem = INSTANCE.blockItem("deepslate_shiny_stone_ore", CobblemonBlocks.DEEPSLATE_SHINY_STONE_ORE as Block)
   public final val DEEPSLATE_SUN_STONE_ORE: BlockItem = INSTANCE.blockItem("deepslate_sun_stone_ore", CobblemonBlocks.DEEPSLATE_SUN_STONE_ORE as Block)
   public final val DEEPSLATE_THUNDER_STONE_ORE: BlockItem =
      INSTANCE.blockItem("deepslate_thunder_stone_ore", CobblemonBlocks.DEEPSLATE_THUNDER_STONE_ORE as Block)
      public final val DEEPSLATE_WATER_STONE_ORE: BlockItem =
      INSTANCE.blockItem("deepslate_water_stone_ore", CobblemonBlocks.DEEPSLATE_WATER_STONE_ORE as Block)
      public final val DEEP_SEA_SCALE: CobblemonItem = INSTANCE.noSettingsItem("deep_sea_scale")
   public final val DEEP_SEA_TOOTH: CobblemonItem = INSTANCE.noSettingsItem("deep_sea_tooth")
   public final val DESTINY_KNOT: CobblemonItem = heldItem$default(INSTANCE, "destiny_knot", null, 2, null)
   public final val DIRE_HIT: DireHitItem = INSTANCE.create("dire_hit", new DireHitItem()) as DireHitItem
   public final val DISPLAY_CASE: BlockItem = INSTANCE.blockItem("display_case", CobblemonBlocks.DISPLAY_CASE as Block)
   public final val DIVE_BALL: PokeBallItem = INSTANCE.pokeBallItem(PokeBalls.INSTANCE.getDIVE_BALL())
   public final val DOME_FOSSIL: CobblemonItem = INSTANCE.noSettingsItem("dome_fossil")
   public final val DOME_SHERD: CobblemonItem = INSTANCE.noSettingsItem("dome_sherd")
   public final val DRAGON_FANG: CobblemonItem = heldItem$default(INSTANCE, "dragon_fang", null, 2, null)
   public final val DRAGON_GEM: CobblemonItem = INSTANCE.noSettingsItem("dragon_gem")
   public final val DRAGON_SCALE: CobblemonItem = INSTANCE.noSettingsItem("dragon_scale")
   public final val DREAM_BALL: PokeBallItem = INSTANCE.pokeBallItem(PokeBalls.INSTANCE.getDREAM_BALL())
   public final val DRIPSTONE_MOON_STONE_ORE: BlockItem = INSTANCE.blockItem("dripstone_moon_stone_ore", CobblemonBlocks.DRIPSTONE_MOON_STONE_ORE as Block)
   public final val DUBIOUS_DISC: CobblemonItem = INSTANCE.noSettingsItem("dubious_disc")
   public final val DURIN_BERRY: BerryItem = INSTANCE.berryItem("durin", CobblemonBlocks.INSTANCE.getDURIN_BERRY())
   public final val DUSK_BALL: PokeBallItem = INSTANCE.pokeBallItem(PokeBalls.INSTANCE.getDUSK_BALL())
   public final val DUSK_STONE: CobblemonItem = INSTANCE.noSettingsItem("dusk_stone")
   public final val DUSK_STONE_ORE: BlockItem = INSTANCE.blockItem("dusk_stone_ore", CobblemonBlocks.DUSK_STONE_ORE as Block)
   public final val EJECT_BUTTON: CobblemonItem = heldItem$default(INSTANCE, "eject_button", null, 2, null)
   public final val EJECT_PACK: CobblemonItem = heldItem$default(INSTANCE, "eject_pack", null, 2, null)
   public final val ELECTIRIZER: CobblemonItem = INSTANCE.noSettingsItem("electirizer")
   public final val ELECTRIC_GEM: CobblemonItem = INSTANCE.noSettingsItem("electric_gem")
   public final val ELIXIR: ElixirItem = INSTANCE.create("elixir", new ElixirItem(false)) as ElixirItem
   public final val ENERGY_ROOT: Item
   public final val ENIGMA_BERRY: BerryItem = INSTANCE.berryItem("enigma", CobblemonBlocks.INSTANCE.getENIGMA_BERRY())
   public final val ETHER: EtherItem = INSTANCE.create("ether", new EtherItem(false)) as EtherItem
   public final val EVERSTONE: CobblemonItem = heldItem$default(INSTANCE, "everstone", null, 2, null)
   public final val EVIOLITE: CobblemonItem = heldItem$default(INSTANCE, "eviolite", null, 2, null)
   public final val EXPERIENCE_CANDY_L: CandyItem = INSTANCE.candyItem("exp_candy_l", CobblemonItems::EXPERIENCE_CANDY_L$lambda$4)
   public final val EXPERIENCE_CANDY_M: CandyItem = INSTANCE.candyItem("exp_candy_m", CobblemonItems::EXPERIENCE_CANDY_M$lambda$3)
   public final val EXPERIENCE_CANDY_S: CandyItem = INSTANCE.candyItem("exp_candy_s", CobblemonItems::EXPERIENCE_CANDY_S$lambda$2)
   public final val EXPERIENCE_CANDY_XL: CandyItem = INSTANCE.candyItem("exp_candy_xl", CobblemonItems::EXPERIENCE_CANDY_XL$lambda$5)
   public final val EXPERIENCE_CANDY_XS: CandyItem = INSTANCE.candyItem("exp_candy_xs", CobblemonItems::EXPERIENCE_CANDY_XS$lambda$1)
   public final val EXPERT_BELT: CobblemonItem = heldItem$default(INSTANCE, "expert_belt", null, 2, null)
   public final val EXP_SHARE: CobblemonItem = heldItem$default(INSTANCE, "exp_share", null, 2, null)
   public final val FAIRY_FEATHER: CobblemonItem = heldItem$default(INSTANCE, "fairy_feather", null, 2, null)
   public final val FAIRY_GEM: CobblemonItem = INSTANCE.noSettingsItem("fairy_gem")
   public final val FAST_BALL: PokeBallItem = INSTANCE.pokeBallItem(PokeBalls.INSTANCE.getFAST_BALL())
   public final val FIGHTING_GEM: CobblemonItem = INSTANCE.noSettingsItem("fighting_gem")
   public final val FIGY_BERRY: BerryItem =
      INSTANCE.berryItem("figy", new PortionHealingBerryItem(CobblemonBlocks.INSTANCE.getFIGY_BERRY(), true, <unrepresentable>.INSTANCE))
      public final val FINE_REMEDY: RemedyItem = INSTANCE.create("fine_remedy", new RemedyItem("fine")) as RemedyItem
   public final val FIRE_GEM: CobblemonItem = INSTANCE.noSettingsItem("fire_gem")
   public final val FIRE_STONE: CobblemonItem = INSTANCE.noSettingsItem("fire_stone")
   public final val FIRE_STONE_ORE: BlockItem = INSTANCE.blockItem("fire_stone_ore", CobblemonBlocks.FIRE_STONE_ORE as Block)
   public final val FLAME_ORB: CobblemonItem = heldItem$default(INSTANCE, "flame_orb", null, 2, null)
   public final val FLOAT_STONE: CobblemonItem = heldItem$default(INSTANCE, "float_stone", null, 2, null)
   public final val FLOWER_SWEET: CobblemonItem = INSTANCE.noSettingsItem("flower_sweet")
   public final val FLYING_GEM: CobblemonItem = INSTANCE.noSettingsItem("flying_gem")
   public final val FOCUS_BAND: CobblemonItem = heldItem$default(INSTANCE, "focus_band", null, 2, null)
   public final val FOCUS_SASH: CobblemonItem = heldItem$default(INSTANCE, "focus_sash", null, 2, null)
   public final val FOSSILIZED_BIRD: CobblemonItem = INSTANCE.noSettingsItem("fossilized_bird")
   public final val FOSSILIZED_DINO: CobblemonItem = INSTANCE.noSettingsItem("fossilized_dino")
   public final val FOSSILIZED_DRAKE: CobblemonItem = INSTANCE.noSettingsItem("fossilized_drake")
   public final val FOSSILIZED_FISH: CobblemonItem = INSTANCE.noSettingsItem("fossilized_fish")
   public final val FOSSIL_ANALYZER: BlockItem = INSTANCE.blockItem("fossil_analyzer", CobblemonBlocks.FOSSIL_ANALYZER as Block)
   public final val FRIEND_BALL: PokeBallItem = INSTANCE.pokeBallItem(PokeBalls.INSTANCE.getFRIEND_BALL())
   public final val FULL_HEAL: StatusCureItem = INSTANCE.create("full_heal", new StatusCureItem("item.cobblemon.full_heal")) as StatusCureItem
   public final val FULL_RESTORE: PotionItem = INSTANCE.create("full_restore", new PotionItem(PotionType.FULL_RESTORE)) as PotionItem
   public final val GALARICA_CUFF: CobblemonItem = INSTANCE.noSettingsItem("galarica_cuff")
   public final val GALARICA_WREATH: CobblemonItem = INSTANCE.noSettingsItem("galarica_wreath")
   public final val GANLON_BERRY: BerryItem = INSTANCE.berryItem("ganlon", CobblemonBlocks.INSTANCE.getGANLON_BERRY())
   public final val GENIUS_FEATHER: FeatherItem = INSTANCE.create("genius_feather", new FeatherItem(Stats.SPECIAL_ATTACK)) as FeatherItem
   public final val GENTLE_MINT: MintItem = INSTANCE.mintItem("gentle_mint", new MintItem(Natures.INSTANCE.getGENTLE()))
   public final val GHOST_GEM: CobblemonItem = INSTANCE.noSettingsItem("ghost_gem")
   public final val GILDED_CHEST: BlockItem =
      INSTANCE.create("gilded_chest", new BlockItem(CobblemonBlocks.GILDED_CHEST as Block, new Properties())) as BlockItem
      public final val GIMMIGHOUL_CHEST: BlockItem =
      INSTANCE.create("gimmighoul_chest", new BlockItem(CobblemonBlocks.GIMMIGHOUL_CHEST as Block, new Properties())) as BlockItem
      public final val GRASS_GEM: CobblemonItem = INSTANCE.noSettingsItem("grass_gem")
   public final val GREAT_BALL: PokeBallItem = INSTANCE.pokeBallItem(PokeBalls.INSTANCE.getGREAT_BALL())
   public final val GREEN_APRICORN: ApricornItem = INSTANCE.apricornItem("green", new ApricornItem(CobblemonBlocks.GREEN_APRICORN))
   public final val GREEN_APRICORN_SEED: ApricornSeedItem =
      INSTANCE.apricornSeedItem("green", new ApricornSeedItem(CobblemonBlocks.GREEN_APRICORN_SAPLING, CobblemonBlocks.GREEN_APRICORN))
      public final val GREEN_GILDED_CHEST: BlockItem =
      INSTANCE.create("green_gilded_chest", new BlockItem(CobblemonBlocks.GREEN_GILDED_CHEST as Block, new Properties())) as BlockItem
      public final val GREEN_MINT_LEAF: MintLeafItem = INSTANCE.mintLeaf("green", new MintLeafItem(MintBlock.MintType.GREEN))
   public final val GREEN_MINT_SEEDS: Item = INSTANCE.mintSeed("green", MintBlock.MintType.GREEN.getCropBlock())
   public final val GREPA_BERRY: BerryItem =
      INSTANCE.berryItem("grepa", new FriendshipRaisingBerryItem(CobblemonBlocks.INSTANCE.getGREPA_BERRY(), Stats.SPECIAL_DEFENCE))
      public final val GROUND_GEM: CobblemonItem = INSTANCE.noSettingsItem("ground_gem")
   public final val GROWTH_MULCH: MulchItem = INSTANCE.mulchItem("growth_mulch", MulchVariant.GROWTH)
   public final val GUARD_SPEC: GuardSpecItem = INSTANCE.create("guard_spec", new GuardSpecItem()) as GuardSpecItem
   public final val HABAN_BERRY: BerryItem = INSTANCE.berryItem("haban", CobblemonBlocks.INSTANCE.getHABAN_BERRY())
   public final val HARD_STONE: CobblemonItem = heldItem$default(INSTANCE, "hard_stone", null, 2, null)
   public final val HASTY_MINT: MintItem = INSTANCE.mintItem("hasty_mint", new MintItem(Natures.INSTANCE.getHASTY()))
   public final val HEALING_MACHINE: BlockItem = INSTANCE.blockItem("healing_machine", CobblemonBlocks.HEALING_MACHINE as Block)
   public final val HEALTH_FEATHER: FeatherItem = INSTANCE.create("health_feather", new FeatherItem(Stats.HP)) as FeatherItem
   public final val HEAL_BALL: PokeBallItem = INSTANCE.pokeBallItem(PokeBalls.INSTANCE.getHEAL_BALL())
   public final val HEAL_POWDER: HealPowderItem = INSTANCE.create("heal_powder", new HealPowderItem()) as HealPowderItem
   public final val HEAT_ROCK: CobblemonItem = heldItem$default(INSTANCE, "heat_rock", null, 2, null)
   public final val HEAVY_BALL: PokeBallItem = INSTANCE.pokeBallItem(PokeBalls.INSTANCE.getHEAVY_BALL())
   public final val HEAVY_DUTY_BOOTS: CobblemonItem = heldItem$default(INSTANCE, "heavy_duty_boots", null, 2, null)
   public final val HELIX_FOSSIL: CobblemonItem = INSTANCE.noSettingsItem("helix_fossil")
   public final val HELIX_SHERD: CobblemonItem = INSTANCE.noSettingsItem("helix_sherd")
   public final val HONDEW_BERRY: BerryItem =
      INSTANCE.berryItem("hondew", new FriendshipRaisingBerryItem(CobblemonBlocks.INSTANCE.getHONDEW_BERRY(), Stats.SPECIAL_ATTACK))
      public final val HOPO_BERRY: BerryItem =
      INSTANCE.berryItem("hopo", new PPRestoringBerryItem(CobblemonBlocks.INSTANCE.getHOPO_BERRY(), <unrepresentable>.INSTANCE))
      public final val HP_UP: VitaminItem = INSTANCE.create("hp_up", new VitaminItem(Stats.HP)) as VitaminItem
   public final val HUMID_MULCH: MulchItem = INSTANCE.mulchItem("humid_mulch", MulchVariant.HUMID)
   public final val HYPER_POTION: PotionItem = INSTANCE.create("hyper_potion", new PotionItem(PotionType.HYPER_POTION)) as PotionItem
   public final val IAPAPA_BERRY: BerryItem =
      INSTANCE.berryItem("iapapa", new PortionHealingBerryItem(CobblemonBlocks.INSTANCE.getIAPAPA_BERRY(), true, <unrepresentable>.INSTANCE))
      public final val ICE_GEM: CobblemonItem = INSTANCE.noSettingsItem("ice_gem")
   public final val ICE_HEAL: StatusCureItem =
      INSTANCE.create("ice_heal", new StatusCureItem("item.cobblemon.ice_heal", Statuses.INSTANCE.getFROZEN())) as StatusCureItem
      public final val ICE_STONE: CobblemonItem = INSTANCE.noSettingsItem("ice_stone")
   public final val ICE_STONE_ORE: BlockItem = INSTANCE.blockItem("ice_stone_ore", CobblemonBlocks.ICE_STONE_ORE as Block)
   public final val ICY_ROCK: CobblemonItem = heldItem$default(INSTANCE, "icy_rock", null, 2, null)
   public final val IMPISH_MINT: MintItem = INSTANCE.mintItem("impish_mint", new MintItem(Natures.INSTANCE.getIMPISH()))
   public final val IRON: VitaminItem = INSTANCE.create("iron", new VitaminItem(Stats.DEFENCE)) as VitaminItem
   public final val IRON_BALL: CobblemonItem = heldItem$default(INSTANCE, "iron_ball", null, 2, null)
   public final val JABOCA_BERRY: BerryItem = INSTANCE.berryItem("jaboca", CobblemonBlocks.INSTANCE.getJABOCA_BERRY())
   public final val JAW_FOSSIL: CobblemonItem = INSTANCE.noSettingsItem("jaw_fossil")
   public final val JOLLY_MINT: MintItem = INSTANCE.mintItem("jolly_mint", new MintItem(Natures.INSTANCE.getJOLLY()))
   public final val KASIB_BERRY: BerryItem = INSTANCE.berryItem("kasib", CobblemonBlocks.INSTANCE.getKASIB_BERRY())
   public final val KEBIA_BERRY: BerryItem = INSTANCE.berryItem("kebia", CobblemonBlocks.INSTANCE.getKEBIA_BERRY())
   public final val KEE_BERRY: BerryItem = INSTANCE.berryItem("kee", CobblemonBlocks.INSTANCE.getKEE_BERRY())
   public final val KELPSY_BERRY: BerryItem =
      INSTANCE.berryItem("kelpsy", new FriendshipRaisingBerryItem(CobblemonBlocks.INSTANCE.getKELPSY_BERRY(), Stats.ATTACK))
      public final val KINGS_ROCK: CobblemonItem = INSTANCE.noSettingsItem("kings_rock")
   public final val LANSAT_BERRY: BerryItem = INSTANCE.berryItem("lansat", CobblemonBlocks.INSTANCE.getLANSAT_BERRY())
   public final val LARGE_BUDDING_BLACK_TUMBLESTONE: BlockItem =
      INSTANCE.blockItem("large_budding_black_tumblestone", CobblemonBlocks.LARGE_BUDDING_BLACK_TUMBLESTONE)
      public final val LARGE_BUDDING_SKY_TUMBLESTONE: BlockItem =
      INSTANCE.blockItem("large_budding_sky_tumblestone", CobblemonBlocks.LARGE_BUDDING_SKY_TUMBLESTONE)
      public final val LARGE_BUDDING_TUMBLESTONE: BlockItem = INSTANCE.blockItem("large_budding_tumblestone", CobblemonBlocks.LARGE_BUDDING_TUMBLESTONE)
   public final val LAX_MINT: MintItem = INSTANCE.mintItem("lax_mint", new MintItem(Natures.INSTANCE.getLAX()))
   public final val LEAF_STONE: CobblemonItem = INSTANCE.noSettingsItem("leaf_stone")
   public final val LEAF_STONE_ORE: BlockItem = INSTANCE.blockItem("leaf_stone_ore", CobblemonBlocks.LEAF_STONE_ORE as Block)
   public final val LEEK_AND_POTATO_STEW: BowlFoodItem =
      INSTANCE.create("leek_and_potato_stew", new BowlFoodItem(new Properties().m_41489_(new Builder().m_38760_(8).m_38758_(0.6F).m_38767_()).m_41487_(1))) as BowlFoodItem
      public final val LEFTOVERS: CobblemonItem = heldItem$default(INSTANCE, "leftovers", null, 2, null)
   public final val LEPPA_BERRY: BerryItem =
      INSTANCE.berryItem("leppa", new PPRestoringBerryItem(CobblemonBlocks.INSTANCE.getLEPPA_BERRY(), <unrepresentable>.INSTANCE))
      public final val LEVEL_BALL: PokeBallItem = INSTANCE.pokeBallItem(PokeBalls.INSTANCE.getLEVEL_BALL())
   public final val LIECHI_BERRY: BerryItem = INSTANCE.berryItem("liechi", CobblemonBlocks.INSTANCE.getLIECHI_BERRY())
   public final val LIFE_ORB: CobblemonItem = heldItem$default(INSTANCE, "life_orb", null, 2, null)
   public final val LIGHT_BALL: CobblemonItem = heldItem$default(INSTANCE, "light_ball", null, 2, null)
   public final val LIGHT_CLAY: CobblemonItem = heldItem$default(INSTANCE, "light_clay", null, 2, null)
   public final val LINK_CABLE: LinkCableItem = INSTANCE.create("link_cable", new LinkCableItem()) as LinkCableItem
   public final val LOADED_DICE: CobblemonItem = heldItem$default(INSTANCE, "loaded_dice", null, 2, null)
   public final val LOAMY_MULCH: MulchItem = INSTANCE.mulchItem("loamy_mulch", MulchVariant.LOAMY)
   public final val LONELY_MINT: MintItem = INSTANCE.mintItem("lonely_mint", new MintItem(Natures.INSTANCE.getLONELY()))
   public final val LOVE_BALL: PokeBallItem = INSTANCE.pokeBallItem(PokeBalls.INSTANCE.getLOVE_BALL())
   public final val LOVE_SWEET: CobblemonItem = INSTANCE.noSettingsItem("love_sweet")
   public final val LUCKY_EGG: CobblemonItem = heldItem$default(INSTANCE, "lucky_egg", null, 2, null)
   public final val LUM_BERRY: BerryItem = INSTANCE.berryItem("lum", new StatusCuringBerryItem(CobblemonBlocks.INSTANCE.getLUM_BERRY()))
   public final val LURE_BALL: PokeBallItem = INSTANCE.pokeBallItem(PokeBalls.INSTANCE.getLURE_BALL())
   public final val LUXURY_BALL: PokeBallItem = INSTANCE.pokeBallItem(PokeBalls.INSTANCE.getLUXURY_BALL())
   public final val MAGMARIZER: CobblemonItem = INSTANCE.noSettingsItem("magmarizer")
   public final val MAGNET: CobblemonItem = heldItem$default(INSTANCE, "magnet", null, 2, null)
   public final val MAGOST_BERRY: BerryItem = INSTANCE.berryItem("magost", CobblemonBlocks.INSTANCE.getMAGOST_BERRY())
   public final val MAGO_BERRY: BerryItem =
      INSTANCE.berryItem("mago", new PortionHealingBerryItem(CobblemonBlocks.INSTANCE.getMAGO_BERRY(), true, <unrepresentable>.INSTANCE))
      public final val MALICIOUS_ARMOR: CobblemonItem = heldItem$default(INSTANCE, "malicious_armor", null, 2, null)
   public final val MARANGA_BERRY: BerryItem = INSTANCE.berryItem("maranga", CobblemonBlocks.INSTANCE.getMARANGA_BERRY())
   public final val MASTERPIECE_TEACUP: CobblemonItem = INSTANCE.noSettingsItem("masterpiece_teacup")
   public final val MASTER_BALL: PokeBallItem = INSTANCE.pokeBallItem(PokeBalls.INSTANCE.getMASTER_BALL())
   public final val MAX_ELIXIR: ElixirItem = INSTANCE.create("max_elixir", new ElixirItem(true)) as ElixirItem
   public final val MAX_ETHER: EtherItem = INSTANCE.create("max_ether", new EtherItem(true)) as EtherItem
   public final val MAX_POTION: PotionItem = INSTANCE.create("max_potion", new PotionItem(PotionType.MAX_POTION)) as PotionItem
   public final val MAX_REVIVE: ReviveItem = INSTANCE.create("max_revive", new ReviveItem(true)) as ReviveItem
   public final val MEDICINAL_BREW: Item = INSTANCE.create("medicinal_brew", new Item(new Properties())) as Item
   public final val MEDICINAL_LEEK: Item
   public final val MEDIUM_BUDDING_BLACK_TUMBLESTONE: BlockItem =
      INSTANCE.blockItem("medium_budding_black_tumblestone", CobblemonBlocks.MEDIUM_BUDDING_BLACK_TUMBLESTONE)
      public final val MEDIUM_BUDDING_SKY_TUMBLESTONE: BlockItem =
      INSTANCE.blockItem("medium_budding_sky_tumblestone", CobblemonBlocks.MEDIUM_BUDDING_SKY_TUMBLESTONE)
      public final val MEDIUM_BUDDING_TUMBLESTONE: BlockItem = INSTANCE.blockItem("medium_budding_tumblestone", CobblemonBlocks.MEDIUM_BUDDING_TUMBLESTONE)
   public final val MENTAL_HERB: CobblemonItem = INSTANCE.compostableHeldItem("mental_herb", null, 1.0F)
   public final val METAL_COAT: CobblemonItem = INSTANCE.noSettingsItem("metal_coat")
   public final val METAL_POWDER: CobblemonItem = heldItem$default(INSTANCE, "metal_powder", null, 2, null)
   public final val MICLE_BERRY: BerryItem = INSTANCE.berryItem("micle", CobblemonBlocks.INSTANCE.getMICLE_BERRY())
   public final val MILD_MINT: MintItem = INSTANCE.mintItem("mild_mint", new MintItem(Natures.INSTANCE.getMILD()))
   public final val MIRACLE_SEED: CobblemonItem = heldItem$default(INSTANCE, "miracle_seed", null, 2, null)
   public final val MIRROR_HERB: CobblemonItem = INSTANCE.compostableHeldItem("mirror_herb", null, 1.0F)
   public final val MODEST_MINT: MintItem = INSTANCE.mintItem("modest_mint", new MintItem(Natures.INSTANCE.getMODEST()))
   public final val MONITOR: BlockItem = INSTANCE.blockItem("monitor", CobblemonBlocks.MONITOR as Block)
   public final val MOON_BALL: PokeBallItem = INSTANCE.pokeBallItem(PokeBalls.INSTANCE.getMOON_BALL())
   public final val MOON_STONE: CobblemonItem = INSTANCE.noSettingsItem("moon_stone")
   public final val MOON_STONE_ORE: BlockItem = INSTANCE.blockItem("moon_stone_ore", CobblemonBlocks.MOON_STONE_ORE as Block)
   public final val MULCH_BASE: CobblemonItem = INSTANCE.noSettingsItem("mulch_base")
   public final val MUSCLE_BAND: CobblemonItem = heldItem$default(INSTANCE, "muscle_band", null, 2, null)
   public final val MUSCLE_FEATHER: FeatherItem = INSTANCE.create("muscle_feather", new FeatherItem(Stats.ATTACK)) as FeatherItem
   public final val MYSTIC_WATER: CobblemonItem = heldItem$default(INSTANCE, "mystic_water", null, 2, null)
   public final val NAIVE_MINT: MintItem = INSTANCE.mintItem("naive_mint", new MintItem(Natures.INSTANCE.getNAIVE()))
   public final val NANAB_BERRY: BerryItem = INSTANCE.berryItem("nanab", CobblemonBlocks.INSTANCE.getNANAB_BERRY())
   public final val NAUGHTY_MINT: MintItem = INSTANCE.mintItem("naughty_mint", new MintItem(Natures.INSTANCE.getNAUGHTY()))
   public final val NEST_BALL: PokeBallItem = INSTANCE.pokeBallItem(PokeBalls.INSTANCE.getNEST_BALL())
   public final val NETHER_FIRE_STONE_ORE: BlockItem = INSTANCE.blockItem("nether_fire_stone_ore", CobblemonBlocks.NETHER_FIRE_STONE_ORE as Block)
   public final val NET_BALL: PokeBallItem = INSTANCE.pokeBallItem(PokeBalls.INSTANCE.getNET_BALL())
   public final val NEVER_MELT_ICE: CobblemonItem = heldItem$default(INSTANCE, "never_melt_ice", null, 2, null)
   public final val NOMEL_BERRY: BerryItem = INSTANCE.berryItem("nomel", CobblemonBlocks.INSTANCE.getNOMEL_BERRY())
   public final val NORMAL_GEM: CobblemonItem = INSTANCE.noSettingsItem("normal_gem")
   public final val NOSTALGIC_SHERD: CobblemonItem = INSTANCE.noSettingsItem("nostalgic_sherd")
   public final val OCCA_BERRY: BerryItem = INSTANCE.berryItem("occa", CobblemonBlocks.INSTANCE.getOCCA_BERRY())
   public final val OLD_AMBER_FOSSIL: CobblemonItem = INSTANCE.noSettingsItem("old_amber_fossil")
   public final val ORAN_BERRY: BerryItem =
      INSTANCE.berryItem("oran", new HealingBerryItem(CobblemonBlocks.INSTANCE.getORAN_BERRY(), <unrepresentable>.INSTANCE))
      public final val OVAL_STONE: CobblemonItem = INSTANCE.noSettingsItem("oval_stone")
   public final val PAMTRE_BERRY: BerryItem = INSTANCE.berryItem("pamtre", CobblemonBlocks.INSTANCE.getPAMTRE_BERRY())
   public final val PARALYZE_HEAL: StatusCureItem =
      INSTANCE.create("paralyze_heal", new StatusCureItem("item.cobblemon.paralyze_heal", Statuses.INSTANCE.getPARALYSIS())) as StatusCureItem
      public final val PARK_BALL: PokeBallItem = INSTANCE.pokeBallItem(PokeBalls.INSTANCE.getPARK_BALL())
   public final val PASSHO_BERRY: BerryItem = INSTANCE.berryItem("passho", CobblemonBlocks.INSTANCE.getPASSHO_BERRY())
   public final val PASTURE: BlockItem = INSTANCE.blockItem("pasture", CobblemonBlocks.PASTURE as Block)
   public final val PAYAPA_BERRY: BerryItem = INSTANCE.berryItem("payapa", CobblemonBlocks.INSTANCE.getPAYAPA_BERRY())
   public final val PC: BlockItem = INSTANCE.blockItem("pc", CobblemonBlocks.PC as Block)
   public final val PEAT_BLOCK: CobblemonItem = INSTANCE.noSettingsItem("peat_block")
   public final val PEAT_MULCH: MulchItem = INSTANCE.mulchItem("peat_mulch", MulchVariant.PEAT)
   public final val PECHA_BERRY: BerryItem =
      INSTANCE.berryItem(
         "pecha", new StatusCuringBerryItem(CobblemonBlocks.INSTANCE.getPECHA_BERRY(), Statuses.INSTANCE.getPOISON(), Statuses.INSTANCE.getPOISON_BADLY())
      )
      public final val PEP_UP_FLOWER: Item = compostableBlockItem$default(INSTANCE, "pep_up_flower", CobblemonBlocks.PEP_UP_FLOWER as Block, 0.0F, 4, null)
   public final val PERSIM_BERRY: BerryItem =
      INSTANCE.berryItem("persim", new VolatileCuringBerryItem(CobblemonBlocks.INSTANCE.getPERSIM_BERRY(), "confusion"))
      public final val PETAYA_BERRY: BerryItem = INSTANCE.berryItem("petaya", CobblemonBlocks.INSTANCE.getPETAYA_BERRY())
   public final val PINAP_BERRY: BerryItem = INSTANCE.berryItem("pinap", CobblemonBlocks.INSTANCE.getPINAP_BERRY())
   public final val PINK_APRICORN: ApricornItem = INSTANCE.apricornItem("pink", new ApricornItem(CobblemonBlocks.PINK_APRICORN))
   public final val PINK_APRICORN_SEED: ApricornSeedItem =
      INSTANCE.apricornSeedItem("pink", new ApricornSeedItem(CobblemonBlocks.PINK_APRICORN_SAPLING, CobblemonBlocks.PINK_APRICORN))
      public final val PINK_GILDED_CHEST: BlockItem =
      INSTANCE.create("pink_gilded_chest", new BlockItem(CobblemonBlocks.PINK_GILDED_CHEST as Block, new Properties())) as BlockItem
      public final val PINK_MINT_LEAF: MintLeafItem = INSTANCE.mintLeaf("pink", new MintLeafItem(MintBlock.MintType.PINK))
   public final val PINK_MINT_SEEDS: Item = INSTANCE.mintSeed("pink", MintBlock.MintType.PINK.getCropBlock())
   public final val PLUME_FOSSIL: CobblemonItem = INSTANCE.noSettingsItem("plume_fossil")
   public final val POISON_BARB: CobblemonItem = heldItem$default(INSTANCE, "poison_barb", null, 2, null)
   public final val POISON_GEM: CobblemonItem = INSTANCE.noSettingsItem("poison_gem")
   public final val POKEMON_MODEL: PokemonItem = INSTANCE.create("pokemon_model", new PokemonItem()) as PokemonItem
   public final val POKE_BALL: PokeBallItem = INSTANCE.pokeBallItem(PokeBalls.INSTANCE.getPOKE_BALL())
   public final val POMEG_BERRY: BerryItem = INSTANCE.berryItem("pomeg", new FriendshipRaisingBerryItem(CobblemonBlocks.INSTANCE.getPOMEG_BERRY(), Stats.HP))
   public final val POTION: PotionItem = INSTANCE.create("potion", new PotionItem(PotionType.POTION)) as PotionItem
   public final val POWER_ANKLET: CobblemonItem = heldItem$default(INSTANCE, "power_anklet", null, 2, null)
   public final val POWER_BAND: CobblemonItem = heldItem$default(INSTANCE, "power_band", null, 2, null)
   public final val POWER_BELT: CobblemonItem = heldItem$default(INSTANCE, "power_belt", null, 2, null)
   public final val POWER_BRACER: CobblemonItem = heldItem$default(INSTANCE, "power_bracer", null, 2, null)
   public final val POWER_HERB: CobblemonItem = INSTANCE.compostableHeldItem("power_herb", null, 1.0F)
   public final val POWER_LENS: CobblemonItem = heldItem$default(INSTANCE, "power_lens", null, 2, null)
   public final val POWER_WEIGHT: CobblemonItem = heldItem$default(INSTANCE, "power_weight", null, 2, null)
   public final val PP_MAX: PPUpItem = INSTANCE.create("pp_max", new PPUpItem(3)) as PPUpItem
   public final val PP_UP: PPUpItem = INSTANCE.create("pp_up", new PPUpItem(1)) as PPUpItem
   public final val PREMIER_BALL: PokeBallItem = INSTANCE.pokeBallItem(PokeBalls.INSTANCE.getPREMIER_BALL())
   public final val PRISM_SCALE: CobblemonItem = INSTANCE.noSettingsItem("prism_scale")
   public final val PROTECTOR: CobblemonItem = INSTANCE.noSettingsItem("protector")
   public final val PROTEIN: VitaminItem = INSTANCE.create("protein", new VitaminItem(Stats.ATTACK)) as VitaminItem
   public final val PSYCHIC_GEM: CobblemonItem = INSTANCE.noSettingsItem("psychic_gem")
   public final val QUALOT_BERRY: BerryItem =
      INSTANCE.berryItem("qualot", new FriendshipRaisingBerryItem(CobblemonBlocks.INSTANCE.getQUALOT_BERRY(), Stats.DEFENCE))
      public final val QUICK_BALL: PokeBallItem = INSTANCE.pokeBallItem(PokeBalls.INSTANCE.getQUICK_BALL())
   public final val QUICK_CLAW: CobblemonItem = heldItem$default(INSTANCE, "quick_claw", null, 2, null)
   public final val QUICK_POWDER: CobblemonItem = heldItem$default(INSTANCE, "quick_powder", null, 2, null)
   public final val QUIET_MINT: MintItem = INSTANCE.mintItem("quiet_mint", new MintItem(Natures.INSTANCE.getQUIET()))
   public final val RABUTA_BERRY: BerryItem = INSTANCE.berryItem("rabuta", CobblemonBlocks.INSTANCE.getRABUTA_BERRY())
   public final val RARE_CANDY: CandyItem = INSTANCE.candyItem("rare_candy", CobblemonItems::RARE_CANDY$lambda$0)
   public final val RASH_MINT: MintItem = INSTANCE.mintItem("rash_mint", new MintItem(Natures.INSTANCE.getRASH()))
   public final val RAWST_BERRY: BerryItem =
      INSTANCE.berryItem("rawst", new StatusCuringBerryItem(CobblemonBlocks.INSTANCE.getRAWST_BERRY(), Statuses.INSTANCE.getBURN()))
      public final val RAZOR_CLAW: CobblemonItem = INSTANCE.noSettingsItem("razor_claw")
   public final val RAZOR_FANG: CobblemonItem = INSTANCE.noSettingsItem("razor_fang")
   public final val RAZZ_BERRY: BerryItem = INSTANCE.berryItem("razz", CobblemonBlocks.INSTANCE.getRAZZ_BERRY())
   public final val REAPER_CLOTH: CobblemonItem = INSTANCE.noSettingsItem("reaper_cloth")
   public final val RED_APRICORN: ApricornItem = INSTANCE.apricornItem("red", new ApricornItem(CobblemonBlocks.RED_APRICORN))
   public final val RED_APRICORN_SEED: ApricornSeedItem =
      INSTANCE.apricornSeedItem("red", new ApricornSeedItem(CobblemonBlocks.RED_APRICORN_SAPLING, CobblemonBlocks.RED_APRICORN))
      public final val RED_CARD: CobblemonItem = heldItem$default(INSTANCE, "red_card", null, 2, null)
   public final val RED_MINT_LEAF: MintLeafItem = INSTANCE.mintLeaf("red", new MintLeafItem(MintBlock.MintType.RED))
   public final val RED_MINT_SEEDS: Item = INSTANCE.mintSeed("red", MintBlock.MintType.RED.getCropBlock())
   public final val RELAXED_MINT: MintItem = INSTANCE.mintItem("relaxed_mint", new MintItem(Natures.INSTANCE.getRELAXED()))
   public final val RELIC_COIN: CobblemonItem = INSTANCE.noSettingsItem("relic_coin")
   public final val RELIC_COIN_POUCH: BlockItem = INSTANCE.blockItem("relic_coin_pouch", CobblemonBlocks.RELIC_COIN_POUCH as Block)
   public final val RELIC_COIN_SACK: BlockItem = INSTANCE.blockItem("relic_coin_sack", CobblemonBlocks.RELIC_COIN_SACK as Block)
   public final val REMEDY: RemedyItem = INSTANCE.create("remedy", new RemedyItem("normal")) as RemedyItem
   public final val REPEAT_BALL: PokeBallItem = INSTANCE.pokeBallItem(PokeBalls.INSTANCE.getREPEAT_BALL())
   public final val RESIST_FEATHER: FeatherItem = INSTANCE.create("resist_feather", new FeatherItem(Stats.DEFENCE)) as FeatherItem
   public final val RESTORATION_TANK: BlockItem = INSTANCE.blockItem("restoration_tank", CobblemonBlocks.RESTORATION_TANK as Block)
   public final val REVIVAL_HERB: Item =
      compostableItem$default(INSTANCE, "revival_herb", (new RevivalHerbItem(CobblemonBlocks.REVIVAL_HERB)) as Item, 0.0F, 4, null)
      public final val REVIVE: ReviveItem = INSTANCE.create("revive", new ReviveItem(false)) as ReviveItem
   public final val RIBBON_SWEET: CobblemonItem = INSTANCE.noSettingsItem("ribbon_sweet")
   public final val RICH_MULCH: MulchItem = INSTANCE.mulchItem("rich_mulch", MulchVariant.RICH)
   public final val RINDO_BERRY: BerryItem = INSTANCE.berryItem("rindo", CobblemonBlocks.INSTANCE.getRINDO_BERRY())
   public final val RING_TARGET: CobblemonItem = heldItem$default(INSTANCE, "ring_target", null, 2, null)
   public final val ROASTED_LEEK: Item =
      INSTANCE.create("roasted_leek", new Item(new Properties().m_41489_(new Builder().m_38766_().m_38760_(3).m_38758_(0.3F).m_38767_()))) as Item
      public final val ROCKY_HELMET: CobblemonItem = heldItem$default(INSTANCE, "rocky_helmet", null, 2, null)
   public final val ROCK_GEM: CobblemonItem = INSTANCE.noSettingsItem("rock_gem")
   public final val ROOT_FOSSIL: CobblemonItem = INSTANCE.noSettingsItem("root_fossil")
   public final val ROSEATE_BALL: PokeBallItem = INSTANCE.pokeBallItem(PokeBalls.INSTANCE.getROSEATE_BALL())
   public final val ROSELI_BERRY: BerryItem = INSTANCE.berryItem("roseli", CobblemonBlocks.INSTANCE.getROSELI_BERRY())
   public final val ROWAP_BERRY: BerryItem = INSTANCE.berryItem("rowap", CobblemonBlocks.INSTANCE.getROWAP_BERRY())
   public final val SACHET: CobblemonItem = INSTANCE.noSettingsItem("sachet")
   public final val SAFARI_BALL: PokeBallItem = INSTANCE.pokeBallItem(PokeBalls.INSTANCE.getSAFARI_BALL())
   public final val SAFETY_GOGGLES: CobblemonItem = heldItem$default(INSTANCE, "safety_goggles", null, 2, null)
   public final val SAIL_FOSSIL: CobblemonItem = INSTANCE.noSettingsItem("sail_fossil")
   public final val SALAC_BERRY: BerryItem = INSTANCE.berryItem("salac", CobblemonBlocks.INSTANCE.getSALAC_BERRY())
   public final val SANDY_MULCH: MulchItem = INSTANCE.mulchItem("sandy_mulch", MulchVariant.SANDY)
   public final val SASSY_MINT: MintItem = INSTANCE.mintItem("sassy_mint", new MintItem(Natures.INSTANCE.getSASSY()))
   public final val SERIOUS_MINT: MintItem = INSTANCE.mintItem("serious_mint", new MintItem(Natures.INSTANCE.getSERIOUS()))
   public final val SHARP_BEAK: CobblemonItem = heldItem$default(INSTANCE, "sharp_beak", null, 2, null)
   public final val SHELL_BELL: CobblemonItem = heldItem$default(INSTANCE, "shell_bell", null, 2, null)
   public final val SHINY_STONE: CobblemonItem = INSTANCE.noSettingsItem("shiny_stone")
   public final val SHINY_STONE_ORE: BlockItem = INSTANCE.blockItem("shiny_stone_ore", CobblemonBlocks.SHINY_STONE_ORE as Block)
   public final val SHUCA_BERRY: BerryItem = INSTANCE.berryItem("shuca", CobblemonBlocks.INSTANCE.getSHUCA_BERRY())
   public final val SILK_SCARF: CobblemonItem = heldItem$default(INSTANCE, "silk_scarf", null, 2, null)
   public final val SILVER_POWDER: CobblemonItem = heldItem$default(INSTANCE, "silver_powder", null, 2, null)
   public final val SITRUS_BERRY: BerryItem =
      INSTANCE.berryItem("sitrus", new HealingBerryItem(CobblemonBlocks.INSTANCE.getSITRUS_BERRY(), <unrepresentable>.INSTANCE))
      public final val SKULL_FOSSIL: CobblemonItem = INSTANCE.noSettingsItem("skull_fossil")
   public final val SKY_TUMBLESTONE: TumblestoneItem =
      INSTANCE.create("sky_tumblestone", new TumblestoneItem(new Properties(), CobblemonBlocks.SMALL_BUDDING_SKY_TUMBLESTONE)) as TumblestoneItem
      public final val SKY_TUMBLESTONE_BLOCK: BlockItem = INSTANCE.blockItem("sky_tumblestone_block", CobblemonBlocks.SKY_TUMBLESTONE_BLOCK)
   public final val SKY_TUMBLESTONE_CLUSTER: BlockItem = INSTANCE.blockItem("sky_tumblestone_cluster", CobblemonBlocks.SKY_TUMBLESTONE_CLUSTER)
   public final val SLATE_BALL: PokeBallItem = INSTANCE.pokeBallItem(PokeBalls.INSTANCE.getSLATE_BALL())
   public final val SMALL_BUDDING_BLACK_TUMBLESTONE: BlockItem =
      INSTANCE.blockItem("small_budding_black_tumblestone", CobblemonBlocks.SMALL_BUDDING_BLACK_TUMBLESTONE)
      public final val SMALL_BUDDING_SKY_TUMBLESTONE: BlockItem =
      INSTANCE.blockItem("small_budding_sky_tumblestone", CobblemonBlocks.SMALL_BUDDING_SKY_TUMBLESTONE)
      public final val SMALL_BUDDING_TUMBLESTONE: BlockItem = INSTANCE.blockItem("small_budding_tumblestone", CobblemonBlocks.SMALL_BUDDING_TUMBLESTONE)
   public final val SMOKE_BALL: CobblemonItem = heldItem$default(INSTANCE, "smoke_ball", null, 2, null)
   public final val SMOOTH_ROCK: CobblemonItem = heldItem$default(INSTANCE, "smooth_rock", null, 2, null)
   public final val SOFT_SAND: CobblemonItem = heldItem$default(INSTANCE, "soft_sand", null, 2, null)
   public final val SOOTHE_BELL: CobblemonItem = heldItem$default(INSTANCE, "soothe_bell", null, 2, null)
   public final val SPELL_TAG: CobblemonItem = heldItem$default(INSTANCE, "spell_tag", null, 2, null)
   public final val SPELON_BERRY: BerryItem = INSTANCE.berryItem("spelon", CobblemonBlocks.INSTANCE.getSPELON_BERRY())
   public final val SPORT_BALL: PokeBallItem = INSTANCE.pokeBallItem(PokeBalls.INSTANCE.getSPORT_BALL())
   public final val STARF_BERRY: BerryItem = INSTANCE.berryItem("starf", CobblemonBlocks.INSTANCE.getSTARF_BERRY())
   public final val STAR_SWEET: CobblemonItem = INSTANCE.noSettingsItem("star_sweet")
   public final val STEEL_GEM: CobblemonItem = INSTANCE.noSettingsItem("steel_gem")
   public final val STICKY_BARB: CobblemonItem = heldItem$default(INSTANCE, "sticky_barb", null, 2, null)
   public final val STRAWBERRY_SWEET: CobblemonItem = INSTANCE.noSettingsItem("strawberry_sweet")
   public final val STRIPPED_APRICORN_LOG: BlockItem = INSTANCE.blockItem("stripped_apricorn_log", CobblemonBlocks.STRIPPED_APRICORN_LOG as Block)
   public final val STRIPPED_APRICORN_WOOD: BlockItem = INSTANCE.blockItem("stripped_apricorn_wood", CobblemonBlocks.STRIPPED_APRICORN_WOOD as Block)
   public final val SUN_STONE: CobblemonItem = INSTANCE.noSettingsItem("sun_stone")
   public final val SUN_STONE_ORE: BlockItem = INSTANCE.blockItem("sun_stone_ore", CobblemonBlocks.SUN_STONE_ORE as Block)
   public final val SUPERB_REMEDY: RemedyItem = INSTANCE.create("superb_remedy", new RemedyItem("superb")) as RemedyItem
   public final val SUPER_POTION: PotionItem = INSTANCE.create("super_potion", new PotionItem(PotionType.SUPER_POTION)) as PotionItem
   public final val SURPRISE_MULCH: MulchItem = INSTANCE.mulchItem("surprise_mulch", MulchVariant.SURPRISE)
   public final val SUSPICIOUS_SHERD: CobblemonItem = INSTANCE.noSettingsItem("suspicious_sherd")
   public final val SWEET_APPLE: CobblemonItem = INSTANCE.noSettingsItem("sweet_apple")
   public final val SWIFT_FEATHER: FeatherItem = INSTANCE.create("swift_feather", new FeatherItem(Stats.SPEED)) as FeatherItem
   public final val TAMATO_BERRY: BerryItem =
      INSTANCE.berryItem("tamato", new FriendshipRaisingBerryItem(CobblemonBlocks.INSTANCE.getTAMATO_BERRY(), Stats.SPEED))
      public final val TANGA_BERRY: BerryItem = INSTANCE.berryItem("tanga", CobblemonBlocks.INSTANCE.getTANGA_BERRY())
   public final val TART_APPLE: CobblemonItem = INSTANCE.noSettingsItem("tart_apple")
   public final val TERRACOTTA_SUN_STONE_ORE: BlockItem = INSTANCE.blockItem("terracotta_sun_stone_ore", CobblemonBlocks.TERRACOTTA_SUN_STONE_ORE as Block)
   public final val THUNDER_STONE: CobblemonItem = INSTANCE.noSettingsItem("thunder_stone")
   public final val THUNDER_STONE_ORE: BlockItem = INSTANCE.blockItem("thunder_stone_ore", CobblemonBlocks.THUNDER_STONE_ORE as Block)
   public final val TIMER_BALL: PokeBallItem = INSTANCE.pokeBallItem(PokeBalls.INSTANCE.getTIMER_BALL())
   public final val TIMID_MINT: MintItem = INSTANCE.mintItem("timid_mint", new MintItem(Natures.INSTANCE.getTIMID()))
   public final val TOUGA_BERRY: BerryItem = INSTANCE.berryItem("touga", CobblemonBlocks.INSTANCE.getTOUGA_BERRY())
   public final val TOXIC_ORB: CobblemonItem = heldItem$default(INSTANCE, "toxic_orb", null, 2, null)
   public final val TUMBLESTONE: TumblestoneItem =
      INSTANCE.create("tumblestone", new TumblestoneItem(new Properties(), CobblemonBlocks.SMALL_BUDDING_TUMBLESTONE)) as TumblestoneItem
      public final val TUMBLESTONE_BLOCK: BlockItem = INSTANCE.blockItem("tumblestone_block", CobblemonBlocks.TUMBLESTONE_BLOCK)
   public final val TUMBLESTONE_CLUSTER: BlockItem = INSTANCE.blockItem("tumblestone_cluster", CobblemonBlocks.TUMBLESTONE_CLUSTER)
   public final val TWISTED_SPOON: CobblemonItem = heldItem$default(INSTANCE, "twisted_spoon", null, 2, null)
   public final val ULTRA_BALL: PokeBallItem = INSTANCE.pokeBallItem(PokeBalls.INSTANCE.getULTRA_BALL())
   public final val UNREMARKABLE_TEACUP: CobblemonItem = INSTANCE.noSettingsItem("unremarkable_teacup")
   public final val UPGRADE: CobblemonItem = INSTANCE.noSettingsItem("upgrade")
   public final val VERDANT_BALL: PokeBallItem = INSTANCE.pokeBallItem(PokeBalls.INSTANCE.getVERDANT_BALL())
   public final val VIVICHOKE: Item = compostableItem$default(INSTANCE, "vivichoke", null, 0.0F, 6, null)

   public final val VIVICHOKE_DIP: BowlFoodItem =
      INSTANCE.create(
         "vivichoke_dip",
         new BowlFoodItem(
            new Properties()
               .m_41487_(1)
               .m_41489_(new Builder().m_38760_(10).m_38758_(1.2F).m_38762_(new MobEffectInstance(MobEffects.f_19617_, 900, 0), 1.0F).m_38765_().m_38767_())
         ) {
            {
               super(`$super_call_param$1`);
            }

            @NotNull
            public ItemStack m_5922_(@Nullable ItemStack stack, @Nullable Level world, @Nullable LivingEntity user) {
               if (user != null) {
                  user.m_21219_();
               }

               val var10000: ItemStack = super.m_5922_(stack, world, user);
               return var10000;
            }
         }
      ) as BowlFoodItem

   public final val VIVICHOKE_SEEDS: Item =
      compostableItem$default(INSTANCE, "vivichoke_seeds", (new VivichokeItem(CobblemonBlocks.VIVICHOKE_SEEDS)) as Item, 0.0F, 4, null)
      public final val WACAN_BERRY: BerryItem = INSTANCE.berryItem("wacan", CobblemonBlocks.INSTANCE.getWACAN_BERRY())
   public final val WATER_GEM: CobblemonItem = INSTANCE.noSettingsItem("water_gem")
   public final val WATER_STONE: CobblemonItem = INSTANCE.noSettingsItem("water_stone")
   public final val WATER_STONE_ORE: BlockItem = INSTANCE.blockItem("water_stone_ore", CobblemonBlocks.WATER_STONE_ORE as Block)
   public final val WATMEL_BERRY: BerryItem = INSTANCE.berryItem("watmel", CobblemonBlocks.INSTANCE.getWATMEL_BERRY())
   public final val WEAKNESS_POLICY: CobblemonItem = heldItem$default(INSTANCE, "weakness_policy", null, 2, null)
   public final val WEPEAR_BERRY: BerryItem = INSTANCE.berryItem("wepear", CobblemonBlocks.INSTANCE.getWEPEAR_BERRY())
   public final val WHIPPED_DREAM: CobblemonItem = INSTANCE.noSettingsItem("whipped_dream")
   public final val WHITE_APRICORN: ApricornItem = INSTANCE.apricornItem("white", new ApricornItem(CobblemonBlocks.WHITE_APRICORN))
   public final val WHITE_APRICORN_SEED: ApricornSeedItem =
      INSTANCE.apricornSeedItem("white", new ApricornSeedItem(CobblemonBlocks.WHITE_APRICORN_SAPLING, CobblemonBlocks.WHITE_APRICORN))
      public final val WHITE_GILDED_CHEST: BlockItem =
      INSTANCE.create("white_gilded_chest", new BlockItem(CobblemonBlocks.WHITE_GILDED_CHEST as Block, new Properties())) as BlockItem
      public final val WHITE_HERB: CobblemonItem = INSTANCE.compostableHeldItem("white_herb", null, 1.0F)
   public final val WHITE_MINT_LEAF: MintLeafItem = INSTANCE.mintLeaf("white", new MintLeafItem(MintBlock.MintType.WHITE))
   public final val WHITE_MINT_SEEDS: Item = INSTANCE.mintSeed("white", MintBlock.MintType.WHITE.getCropBlock())
   public final val WIKI_BERRY: BerryItem =
      INSTANCE.berryItem("wiki", new PortionHealingBerryItem(CobblemonBlocks.INSTANCE.getWIKI_BERRY(), true, <unrepresentable>.INSTANCE))
      public final val WISE_GLASSES: CobblemonItem = heldItem$default(INSTANCE, "wise_glasses", null, 2, null)
   public final val X_ACCURACY: XStatItem =
      INSTANCE.create("x_${Stats.ACCURACY.getIdentifier().m_135815_()}", new XStatItem(Stats.ACCURACY, 0, 2, null)) as XStatItem
      public final val X_ATTACK: XStatItem =
      INSTANCE.create("x_${Stats.ATTACK.getIdentifier().m_135815_()}", new XStatItem(Stats.ATTACK, 0, 2, null)) as XStatItem
      public final val X_DEFENSE: XStatItem =
      INSTANCE.create("x_${Stats.DEFENCE.getIdentifier().m_135815_()}", new XStatItem(Stats.DEFENCE, 0, 2, null)) as XStatItem
      public final val X_SPEED: XStatItem =
      INSTANCE.create("x_${Stats.SPEED.getIdentifier().m_135815_()}", new XStatItem(Stats.SPEED, 0, 2, null)) as XStatItem
      public final val X_SP_ATK: XStatItem =
      INSTANCE.create("x_${Stats.SPECIAL_ATTACK.getIdentifier().m_135815_()}", new XStatItem(Stats.SPECIAL_ATTACK, 0, 2, null)) as XStatItem
      public final val X_SP_DEF: XStatItem =
      INSTANCE.create("x_${Stats.SPECIAL_DEFENCE.getIdentifier().m_135815_()}", new XStatItem(Stats.SPECIAL_DEFENCE, 0, 2, null)) as XStatItem
      public final val YACHE_BERRY: BerryItem = INSTANCE.berryItem("yache", CobblemonBlocks.INSTANCE.getYACHE_BERRY())
   public final val YELLOW_APRICORN: ApricornItem = INSTANCE.apricornItem("yellow", new ApricornItem(CobblemonBlocks.YELLOW_APRICORN))
   public final val YELLOW_APRICORN_SEED: ApricornSeedItem =
      INSTANCE.apricornSeedItem("yellow", new ApricornSeedItem(CobblemonBlocks.YELLOW_APRICORN_SAPLING, CobblemonBlocks.YELLOW_APRICORN))
      public final val YELLOW_GILDED_CHEST: BlockItem =
      INSTANCE.create("yellow_gilded_chest", new BlockItem(CobblemonBlocks.YELLOW_GILDED_CHEST as Block, new Properties())) as BlockItem
      public final val ZINC: VitaminItem = INSTANCE.create("zinc", new VitaminItem(Stats.SPECIAL_DEFENCE)) as VitaminItem
   private final val berries: MutableMap<ResourceLocation, BerryItem> = (new LinkedHashMap()) as java.util.Map
   public final val mints: MutableMap<String, MintItem> = (new LinkedHashMap()) as java.util.Map
   public final val pokeBalls: MutableList<PokeBallItem> = (new ArrayList()) as java.util.List
   public open val registry: Registry<Item>
   public open val registryKey: ResourceKey<Registry<Item>>

   private fun blockItem(name: String, block: Block): BlockItem {
      return this.create(name, new BlockItem(block, new Properties()));
   }

   private fun noSettingsItem(name: String): CobblemonItem {
      return this.create(name, new CobblemonItem(new Properties()));
   }

   public fun berries(): Map<ResourceLocation, BerryItem> {
      return MapsKt.toMap(berries);
   }

   private fun mulchItem(name: String, mulchVariant: MulchVariant): MulchItem {
      return this.create(name, new MulchItem(mulchVariant));
   }

   private fun pokeBallItem(pokeBall: PokeBall): PokeBallItem {
      val var10001: java.lang.String = pokeBall.getName().m_135815_();
      val item: PokeBallItem = this.create(var10001, new PokeBallItem(pokeBall));
      pokeBall.setItem$common(item);
      pokeBalls.add(item);
      return item;
   }

   private fun candyItem(name: String, calculator: Calculator): CandyItem {
      return this.create(name, new CandyItem(calculator));
   }

   private fun heldItem(name: String, remappedName: String? = null): CobblemonItem {
      val var3: CobblemonItem = new CobblemonItem(new Properties());
      if (remappedName != null) {
         CobblemonHeldItemManager.INSTANCE.registerRemap(var3, remappedName);
      }

      return this.create(name, var3);
   }

   private fun heldItem(name: String, item: Item, remappedName: String? = null): Item {
      if (remappedName != null) {
         CobblemonHeldItemManager.INSTANCE.registerRemap(item, remappedName);
         var var10000: CobblemonHeldItemManager = CobblemonHeldItemManager.INSTANCE;
         var var10001: Item = Items.f_42500_;
         var10000.registerRemap(var10001, "thickclub");
         var10000 = CobblemonHeldItemManager.INSTANCE;
         var10001 = Items.f_42452_;
         var10000.registerRemap(var10001, "snowball");
      }

      return this.create(name, item);
   }

   private fun compostable(item: Item, increaseLevelChance: Float) {
      Cobblemon.INSTANCE.getImplementation().registerCompostable(item as ItemLike, increaseLevelChance);
   }

   private fun berryItem(name: String, berryBlock: BerryBlock): BerryItem {
      val finalName: java.lang.String = "$name_berry";
      val item: BerryItem = this.create(finalName, new BerryItem(berryBlock));
      this.compostable(item as Item, 0.65F);
      berries.put(MiscUtilsKt.cobblemonResource(finalName), item);
      return item;
   }

   private fun berryItem(name: String, berryItem: BerryItem): BerryItem {
      val finalName: java.lang.String = "$name_berry";
      val item: BerryItem = this.create(finalName, berryItem);
      this.compostable(item as Item, 0.65F);
      berries.put(MiscUtilsKt.cobblemonResource(finalName), item);
      return item;
   }

   private fun mintItem(name: String, mintItem: MintItem): MintItem {
      val item: MintItem = this.create(name, mintItem);
      mints.put(item.getNature().getDisplayName(), item);
      this.compostable(item, 0.65F);
      return item;
   }

   private fun apricornItem(name: String, apricornItem: ApricornItem): ApricornItem {
      val item: ApricornItem = this.create("$name_apricorn", apricornItem);
      this.compostable(item as Item, 0.65F);
      return item;
   }

   private fun apricornSeedItem(name: String, apricornSeedItem: ApricornSeedItem): ApricornSeedItem {
      val item: ApricornSeedItem = this.create("$name_apricorn_seed", apricornSeedItem);
      this.compostable(item as Item, 0.65F);
      return item;
   }

   private fun mintSeed(name: String, mintBlock: MintBlock): Item {
      val item: BlockItem = this.blockItem("$name_mint_seeds", mintBlock as Block);
      this.compostable(item as Item, 0.65F);
      return item as Item;
   }

   private fun mintLeaf(name: String, mintLeafItem: MintLeafItem): MintLeafItem {
      val item: MintLeafItem = this.create("$name_mint_leaf", mintLeafItem);
      this.compostable(item, 0.65F);
      return item;
   }

   private fun compostableItem(name: String, item: Item? = null, increaseLevelChance: Float = 0.65F): Item {
      var var10002: Item = item;
      if (item == null) {
         var10002 = new Item(new Properties());
      }

      val createdItem: Item = this.create(name, var10002);
      this.compostable(createdItem, increaseLevelChance);
      return createdItem;
   }

   private fun compostableHeldItem(name: String, remappedName: String? = null, increaseLevelChance: Float = 0.65F): CobblemonItem {
      val createdItem: CobblemonItem = this.heldItem(name, remappedName);
      this.compostable(createdItem, increaseLevelChance);
      return createdItem;
   }

   private fun compostableBlockItem(name: String, block: Block, increaseLevelChance: Float = 0.65F): Item {
      val createdItem: BlockItem = this.blockItem(name, block);
      this.compostable(createdItem as Item, increaseLevelChance);
      return createdItem as Item;
   }

   @JvmStatic
   fun `RARE_CANDY$lambda$0`(var0: ServerPlayer, pokemon: Pokemon): Int {
      return pokemon.getExperienceToNextLevel();
   }

   @JvmStatic
   fun `EXPERIENCE_CANDY_XS$lambda$1`(var0: ServerPlayer, var1: Pokemon): Int {
      return 100;
   }

   @JvmStatic
   fun `EXPERIENCE_CANDY_S$lambda$2`(var0: ServerPlayer, var1: Pokemon): Int {
      return 800;
   }

   @JvmStatic
   fun `EXPERIENCE_CANDY_M$lambda$3`(var0: ServerPlayer, var1: Pokemon): Int {
      return 3000;
   }

   @JvmStatic
   fun `EXPERIENCE_CANDY_L$lambda$4`(var0: ServerPlayer, var1: Pokemon): Int {
      return 10000;
   }

   @JvmStatic
   fun `EXPERIENCE_CANDY_XL$lambda$5`(var0: ServerPlayer, var1: Pokemon): Int {
      return 30000;
   }

   @JvmStatic
   fun {
      var var10000: DefaultedRegistry = BuiltInRegistries.f_257033_;
      registry = var10000 as Registry<Item>;
      val var11: ResourceKey = Registries.f_256913_;
      registryKey = var11;
      val var12: CobblemonItems = INSTANCE;
      var var10004: CobblemonBoatType = CobblemonBoatType.APRICORN;
      var var10006: Properties = new Properties().m_41487_(1);
      APRICORN_BOAT = var12.create("apricorn_boat", new CobblemonBoatItem(var10004, false, var10006));
      val var13: CobblemonItems = INSTANCE;
      var10004 = CobblemonBoatType.APRICORN;
      var10006 = new Properties().m_41487_(1);
      APRICORN_CHEST_BOAT = var13.create("apricorn_chest_boat", new CobblemonBoatItem(var10004, true, var10006));
      val var14: CobblemonItems = INSTANCE;
      val var10002: DoorBlock = CobblemonBlocks.APRICORN_DOOR;
      APRICORN_DOOR = var14.blockItem("apricorn_door", var10002 as Block);
      val var15: CobblemonItems = INSTANCE;
      val var33: TrapDoorBlock = CobblemonBlocks.APRICORN_TRAPDOOR;
      APRICORN_TRAPDOOR = var15.blockItem("apricorn_trapdoor", var33 as Block);
      val var16: CobblemonItems = INSTANCE;
      val var34: ButtonBlock = CobblemonBlocks.APRICORN_BUTTON;
      APRICORN_BUTTON = var16.blockItem("apricorn_button", var34 as Block);
      val var17: CobblemonItems = INSTANCE;
      val var35: PressurePlateBlock = CobblemonBlocks.APRICORN_PRESSURE_PLATE;
      APRICORN_PRESSURE_PLATE = var17.blockItem("apricorn_pressure_plate", var35 as Block);
      val var18: CobblemonItems = INSTANCE;
      val var36: StairBlock = CobblemonBlocks.APRICORN_STAIRS;
      APRICORN_STAIRS = var18.blockItem("apricorn_stairs", var36 as Block);
      val var24: CobblemonItems = INSTANCE;
      val var43: MedicinalLeekBlock = CobblemonBlocks.MEDICINAL_LEEK;
      var var10005: Properties = new Properties().m_41489_(new Builder().m_38766_().m_38760_(1).m_38758_(0.2F).m_38767_());
      MEDICINAL_LEEK = var24.heldItem("medicinal_leek", (new MedicinalLeekItem(var43, var10005)) as Item, "leek");
      val var26: CobblemonItems = INSTANCE;
      val var44: EnergyRootBlock = CobblemonBlocks.ENERGY_ROOT;
      var10005 = new Properties().m_41489_(new Builder().m_38760_(1).m_38766_().m_38758_(0.2F).m_38767_());
      ENERGY_ROOT = compostableItem$default(var26, "energy_root", (new EnergyRootItem(var44, var10005)) as Item, 0.0F, 4, null);
      var10000 = INSTANCE.create("automaton_armor_trim_smithing_template", SmithingTemplateItem.m_266172_(CobblemonArmorTrims.INSTANCE.getAUTOMATON()));
      AUTOMATON_ARMOR_TRIM_SMITHING_TEMPLATE = var10000 as SmithingTemplateItem;
   }
}
