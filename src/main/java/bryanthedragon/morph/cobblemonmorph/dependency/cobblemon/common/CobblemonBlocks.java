package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.apricorn.Apricorn
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.block.ApricornBlock
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.block.ApricornSaplingBlock
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.block.BerryBlock
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.block.BigRootBlock
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.block.CoinPouchBlock
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.block.DisplayCaseBlock
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.block.EnergyRootBlock
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.block.FossilAnalyzerBlock
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.block.HealingMachineBlock
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.block.MedicinalLeekBlock
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.block.MintBlock
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.block.MonitorBlock
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.block.PCBlock
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.block.PastureBlock
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.block.RestorationTankBlock
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.block.RevivalHerbBlock
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.block.TumblestoneBlock
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.block.VivichokeBlock
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.block.chest.GildedChestBlock
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.block.sign.CobblemonHangingSignBlock
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.block.sign.CobblemonSignBlock
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.block.sign.CobblemonWallHangingSignBlock
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.block.sign.CobblemonWallSignBlock
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.mixin.invoker.BlocksInvoker
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.mixin.invoker.DoorBlockInvoker
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.mixin.invoker.FireBlockInvoker
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.mixin.invoker.PressurePlateBlockInvoker
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.mixin.invoker.StairsBlockInvoker
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.mixin.invoker.TrapdoorBlockInvoker
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.platform.PlatformRegistry
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.MiscUtilsKt

import java.util.LinkedHashMap

import net.minecraft.block.AbstractBlock.Settings
import net.minecraft.core.BlockPos
import net.minecraft.core.DefaultedRegistry
import net.minecraft.core.Registry
import net.minecraft.core.registries.BuiltInRegistries
import net.minecraft.core.registries.Registries
import net.minecraft.resources.ResourceKey
import net.minecraft.resources.ResourceLocation
import net.minecraft.util.valueproviders.IntProvider
import net.minecraft.util.valueproviders.UniformInt
import net.minecraft.world.effect.MobEffects
import net.minecraft.world.entity.EntityType
import net.minecraft.world.level.BlockGetter
import net.minecraft.world.level.block.Block
import net.minecraft.world.level.block.Blocks
import net.minecraft.world.level.block.ButtonBlock
import net.minecraft.world.level.block.DoorBlock
import net.minecraft.world.level.block.DropExperienceBlock
import net.minecraft.world.level.block.FenceBlock
import net.minecraft.world.level.block.FenceGateBlock
import net.minecraft.world.level.block.FireBlock
import net.minecraft.world.level.block.FlowerBlock
import net.minecraft.world.level.block.FlowerPotBlock
import net.minecraft.world.level.block.LeavesBlock
import net.minecraft.world.level.block.PressurePlateBlock
import net.minecraft.world.level.block.RotatedPillarBlock
import net.minecraft.world.level.block.SlabBlock
import net.minecraft.world.level.block.SoundType
import net.minecraft.world.level.block.StairBlock
import net.minecraft.world.level.block.TrapDoorBlock
import net.minecraft.world.level.block.PressurePlateBlock.Sensitivity
import net.minecraft.world.level.block.state.BlockBehaviour
import net.minecraft.world.level.block.state.BlockState
import net.minecraft.world.level.block.state.BlockBehaviour.OffsetType
import net.minecraft.world.level.block.state.BlockBehaviour.Properties
import net.minecraft.world.level.block.state.properties.BlockSetType
import net.minecraft.world.level.block.state.properties.NoteBlockInstrument
import net.minecraft.world.level.block.state.properties.Property
import net.minecraft.world.level.block.state.properties.WoodType
import net.minecraft.world.level.material.MapColor
import net.minecraft.world.level.material.PushReaction

public object CobblemonBlocks : PlatformRegistry<Registry<Block>, ResourceKey<Registry<Block>>, Block> {
   public final val AGUAV_BERRY: BerryBlock = INSTANCE.berryBlock("aguav")
   public final val APICOT_BERRY: BerryBlock = INSTANCE.berryBlock("apicot")
   public final val APRICORN_BLOCK_SET_TYPE: BlockSetType = new BlockSetType("apricorn")
   public final val APRICORN_BUTTON: ButtonBlock =
      INSTANCE.create("apricorn_button", BlocksInvoker.createWoodenButtonBlock(BlockSetType.f_271198_)) as ButtonBlock
      public final val APRICORN_DOOR: DoorBlock =
      INSTANCE.create(
         "apricorn_door",
         DoorBlockInvoker.cobblemon$create(
            Properties.m_284310_()
               .m_284180_(APRICORN_PLANKS.m_284356_())
               .m_280658_(NoteBlockInstrument.BASS)
               .m_60978_(3.0F)
               .m_60918_(SoundType.f_56736_)
               .m_60955_(),
            APRICORN_BLOCK_SET_TYPE
         )
      ) as DoorBlock
      public final val APRICORN_FENCE: FenceBlock =
      INSTANCE.create(
         "apricorn_fence",
         new FenceBlock(
            Properties.m_284310_().m_284180_(APRICORN_PLANKS.m_284356_()).m_280658_(NoteBlockInstrument.BASS).m_60913_(2.0F, 3.0F).m_60918_(SoundType.f_56736_)
         )
      ) as FenceBlock
      public final val APRICORN_FENCE_GATE: FenceGateBlock =
      INSTANCE.create(
         "apricorn_fence_gate",
         new FenceGateBlock(
            Properties.m_284310_().m_284180_(APRICORN_PLANKS.m_284356_()).m_280658_(NoteBlockInstrument.BASS).m_60913_(2.0F, 3.0F).m_60918_(SoundType.f_56736_),
            APRICORN_WOOD_TYPE
         )
      ) as FenceGateBlock
      public final val APRICORN_HANGING_SIGN: CobblemonHangingSignBlock
   public final val APRICORN_LEAVES: LeavesBlock = INSTANCE.leaves("apricorn_leaves")
   public final val APRICORN_LOG: RotatedPillarBlock
   public final val APRICORN_PLANKS: Block =
      INSTANCE.create(
         "apricorn_planks",
         new Block(Properties.m_284310_().m_284180_(MapColor.f_283762_).m_280658_(NoteBlockInstrument.BASS).m_60913_(2.0F, 3.0F).m_60918_(SoundType.f_56736_))
      ) as Block
      public final val APRICORN_PRESSURE_PLATE: PressurePlateBlock =
      INSTANCE.create(
         "apricorn_pressure_plate",
         PressurePlateBlockInvoker.cobblemon$create(
            Sensitivity.EVERYTHING,
            Properties.m_284310_()
               .m_284180_(APRICORN_PLANKS.m_284356_())
               .m_280658_(NoteBlockInstrument.BASS)
               .m_60910_()
               .m_60978_(0.5F)
               .m_60918_(SoundType.f_56736_),
            APRICORN_BLOCK_SET_TYPE
         )
      ) as PressurePlateBlock
      public final val APRICORN_SIGN: CobblemonSignBlock
   public final val APRICORN_SLAB: SlabBlock =
      INSTANCE.create(
         "apricorn_slab",
         new SlabBlock(
            Properties.m_284310_().m_284180_(MapColor.f_283825_).m_280658_(NoteBlockInstrument.BASS).m_60913_(2.0F, 3.0F).m_60918_(SoundType.f_56736_)
         )
      ) as SlabBlock
      public final val APRICORN_STAIRS: StairBlock =
      INSTANCE.create(
         "apricorn_stairs", StairsBlockInvoker.cobblemon$create(APRICORN_PLANKS.m_49966_(), Properties.m_60926_(APRICORN_PLANKS as BlockBehaviour))
      ) as StairBlock
      public final val APRICORN_TRAPDOOR: TrapDoorBlock =
      INSTANCE.create(
         "apricorn_trapdoor",
         TrapdoorBlockInvoker.cobblemon$create(
            Properties.m_284310_()
               .m_284180_(MapColor.f_283825_)
               .m_280658_(NoteBlockInstrument.BASS)
               .m_60978_(3.0F)
               .m_60918_(SoundType.f_56736_)
               .m_60955_()
               .m_60922_(CobblemonBlocks::APRICORN_TRAPDOOR$lambda$0),
            APRICORN_BLOCK_SET_TYPE
         )
      ) as TrapDoorBlock
      public final val APRICORN_WALL_HANGING_SIGN: CobblemonWallHangingSignBlock
   public final val APRICORN_WALL_SIGN: CobblemonWallSignBlock
   public final val APRICORN_WOOD: RotatedPillarBlock = log$default(INSTANCE, "apricorn_wood", null, null, 6, null)
   public final val APRICORN_WOOD_TYPE: WoodType = WoodType.m_61844_(new WoodType("apricorn", APRICORN_BLOCK_SET_TYPE))
   public final val ASPEAR_BERRY: BerryBlock = INSTANCE.berryBlock("aspear")
   public final val BABIRI_BERRY: BerryBlock = INSTANCE.berryBlock("babiri")
   public final val BELUE_BERRY: BerryBlock = INSTANCE.berryBlock("belue")
   public final val BIG_ROOT: BigRootBlock
   public final val BLACK_APRICORN: ApricornBlock = INSTANCE.apricornBlock("black_apricorn", Apricorn.BLACK)
   public final val BLACK_APRICORN_SAPLING: ApricornSaplingBlock
   public final val BLACK_GILDED_CHEST: GildedChestBlock
   public final val BLACK_TUMBLESTONE_BLOCK: Block =
      INSTANCE.create(
         "black_tumblestone_block",
         new Block(
            Properties.m_284310_()
               .m_284180_(MapColor.f_283771_)
               .m_60978_(1.0F)
               .m_60918_(CobblemonSounds.TUMBLESTONE_BLOCK_SOUNDS)
               .m_60999_()
               .m_280658_(NoteBlockInstrument.BASEDRUM)
         )
      ) as Block
      public final val BLACK_TUMBLESTONE_CLUSTER: Block = INSTANCE.tumblestoneBlock("black_tumblestone_cluster", 3, 7, 3, null)
   public final val BLUE_APRICORN: ApricornBlock = INSTANCE.apricornBlock("blue_apricorn", Apricorn.BLUE)
   public final val BLUE_APRICORN_SAPLING: ApricornSaplingBlock
   public final val BLUE_GILDED_CHEST: GildedChestBlock
   public final val BLUE_MINT: MintBlock
   public final val BLUK_BERRY: BerryBlock = INSTANCE.berryBlock("bluk")
   public final val CHARTI_BERRY: BerryBlock = INSTANCE.berryBlock("charti")
   public final val CHERI_BERRY: BerryBlock = INSTANCE.berryBlock("cheri")
   public final val CHESTO_BERRY: BerryBlock = INSTANCE.berryBlock("chesto")
   public final val CHILAN_BERRY: BerryBlock = INSTANCE.berryBlock("chilan")
   public final val CHOPLE_BERRY: BerryBlock = INSTANCE.berryBlock("chople")
   public final val COBA_BERRY: BerryBlock = INSTANCE.berryBlock("coba")
   public final val COLBUR_BERRY: BerryBlock = INSTANCE.berryBlock("colbur")
   public final val CORNN_BERRY: BerryBlock = INSTANCE.berryBlock("cornn")
   public final val CUSTAP_BERRY: BerryBlock = INSTANCE.berryBlock("custap")
   public final val CYAN_MINT: MintBlock
   public final val DAWN_STONE_ORE: DropExperienceBlock = INSTANCE.evolutionStoneOre("dawn_stone_ore")
   public final val DEEPSLATE_DAWN_STONE_ORE: DropExperienceBlock = INSTANCE.deepslateEvolutionStoneOre("deepslate_dawn_stone_ore")
   public final val DEEPSLATE_DUSK_STONE_ORE: DropExperienceBlock = INSTANCE.deepslateEvolutionStoneOre("deepslate_dusk_stone_ore")
   public final val DEEPSLATE_FIRE_STONE_ORE: DropExperienceBlock = INSTANCE.deepslateEvolutionStoneOre("deepslate_fire_stone_ore")
   public final val DEEPSLATE_ICE_STONE_ORE: DropExperienceBlock = INSTANCE.deepslateEvolutionStoneOre("deepslate_ice_stone_ore")
   public final val DEEPSLATE_LEAF_STONE_ORE: DropExperienceBlock = INSTANCE.deepslateEvolutionStoneOre("deepslate_leaf_stone_ore")
   public final val DEEPSLATE_MOON_STONE_ORE: DropExperienceBlock = INSTANCE.deepslateEvolutionStoneOre("deepslate_moon_stone_ore")
   public final val DEEPSLATE_SHINY_STONE_ORE: DropExperienceBlock = INSTANCE.deepslateEvolutionStoneOre("deepslate_shiny_stone_ore")
   public final val DEEPSLATE_SUN_STONE_ORE: DropExperienceBlock = INSTANCE.deepslateEvolutionStoneOre("deepslate_sun_stone_ore")
   public final val DEEPSLATE_THUNDER_STONE_ORE: DropExperienceBlock = INSTANCE.deepslateEvolutionStoneOre("deepslate_thunder_stone_ore")
   public final val DEEPSLATE_WATER_STONE_ORE: DropExperienceBlock = INSTANCE.deepslateEvolutionStoneOre("deepslate_water_stone_ore")
   public final val DISPLAY_CASE: DisplayCaseBlock
   public final val DRIPSTONE_MOON_STONE_ORE: DropExperienceBlock = INSTANCE.evolutionStoneOre("dripstone_moon_stone_ore")
   public final val DURIN_BERRY: BerryBlock = INSTANCE.berryBlock("durin")
   public final val DUSK_STONE_ORE: DropExperienceBlock = INSTANCE.evolutionStoneOre("dusk_stone_ore")
   public final val ENERGY_ROOT: EnergyRootBlock
   public final val ENIGMA_BERRY: BerryBlock = INSTANCE.berryBlock("enigma")
   public final val FIGY_BERRY: BerryBlock = INSTANCE.berryBlock("figy")
   public final val FIRE_STONE_ORE: DropExperienceBlock = INSTANCE.evolutionStoneOre("fire_stone_ore")
   public final val FOSSIL_ANALYZER: FossilAnalyzerBlock
   public final val GANLON_BERRY: BerryBlock = INSTANCE.berryBlock("ganlon")
   public final val GILDED_CHEST: GildedChestBlock
   public final val GIMMIGHOUL_CHEST: GildedChestBlock
   public final val GREEN_APRICORN: ApricornBlock = INSTANCE.apricornBlock("green_apricorn", Apricorn.GREEN)
   public final val GREEN_APRICORN_SAPLING: ApricornSaplingBlock
   public final val GREEN_GILDED_CHEST: GildedChestBlock
   public final val GREEN_MINT: MintBlock
   public final val GREPA_BERRY: BerryBlock = INSTANCE.berryBlock("grepa")
   public final val HABAN_BERRY: BerryBlock = INSTANCE.berryBlock("haban")
   public final val HEALING_MACHINE: HealingMachineBlock
   public final val HONDEW_BERRY: BerryBlock = INSTANCE.berryBlock("hondew")
   public final val HOPO_BERRY: BerryBlock = INSTANCE.berryBlock("hopo")
   public final val IAPAPA_BERRY: BerryBlock = INSTANCE.berryBlock("iapapa")
   public final val ICE_STONE_ORE: DropExperienceBlock = INSTANCE.evolutionStoneOre("ice_stone_ore")
   public final val JABOCA_BERRY: BerryBlock = INSTANCE.berryBlock("jaboca")
   public final val KASIB_BERRY: BerryBlock = INSTANCE.berryBlock("kasib")
   public final val KEBIA_BERRY: BerryBlock = INSTANCE.berryBlock("kebia")
   public final val KEE_BERRY: BerryBlock = INSTANCE.berryBlock("kee")
   public final val KELPSY_BERRY: BerryBlock = INSTANCE.berryBlock("kelpsy")
   public final val LANSAT_BERRY: BerryBlock = INSTANCE.berryBlock("lansat")
   public final val LARGE_BUDDING_BLACK_TUMBLESTONE: Block = INSTANCE.tumblestoneBlock("large_budding_black_tumblestone", 2, 5, 3, BLACK_TUMBLESTONE_CLUSTER)
   public final val LARGE_BUDDING_SKY_TUMBLESTONE: Block = INSTANCE.tumblestoneBlock("large_budding_sky_tumblestone", 2, 5, 3, SKY_TUMBLESTONE_CLUSTER)
   public final val LARGE_BUDDING_TUMBLESTONE: Block = INSTANCE.tumblestoneBlock("large_budding_tumblestone", 2, 5, 3, TUMBLESTONE_CLUSTER)
   public final val LEAF_STONE_ORE: DropExperienceBlock = INSTANCE.evolutionStoneOre("leaf_stone_ore")
   public final val LEPPA_BERRY: BerryBlock = INSTANCE.berryBlock("leppa")
   public final val LIECHI_BERRY: BerryBlock = INSTANCE.berryBlock("liechi")
   public final val LUM_BERRY: BerryBlock = INSTANCE.berryBlock("lum")
   public final val MAGOST_BERRY: BerryBlock = INSTANCE.berryBlock("magost")
   public final val MAGO_BERRY: BerryBlock = INSTANCE.berryBlock("mago")
   public final val MARANGA_BERRY: BerryBlock = INSTANCE.berryBlock("maranga")
   public final val MEDICINAL_LEEK: MedicinalLeekBlock
   public final val MEDIUM_BUDDING_BLACK_TUMBLESTONE: Block =
      INSTANCE.tumblestoneBlock("medium_budding_black_tumblestone", 1, 4, 3, LARGE_BUDDING_BLACK_TUMBLESTONE)
      public final val MEDIUM_BUDDING_SKY_TUMBLESTONE: Block =
      INSTANCE.tumblestoneBlock("medium_budding_sky_tumblestone", 1, 4, 3, LARGE_BUDDING_SKY_TUMBLESTONE)
      public final val MEDIUM_BUDDING_TUMBLESTONE: Block = INSTANCE.tumblestoneBlock("medium_budding_tumblestone", 1, 4, 3, LARGE_BUDDING_TUMBLESTONE)
   public final val MICLE_BERRY: BerryBlock = INSTANCE.berryBlock("micle")
   public final val MONITOR: MonitorBlock
   public final val MOON_STONE_ORE: DropExperienceBlock = INSTANCE.evolutionStoneOre("moon_stone_ore")
   public final val NANAB_BERRY: BerryBlock = INSTANCE.berryBlock("nanab")
   public final val NETHER_FIRE_STONE_ORE: DropExperienceBlock = INSTANCE.evolutionStoneOre("nether_fire_stone_ore")
   public final val NOMEL_BERRY: BerryBlock = INSTANCE.berryBlock("nomel")
   public final val OCCA_BERRY: BerryBlock = INSTANCE.berryBlock("occa")
   public final val ORAN_BERRY: BerryBlock = INSTANCE.berryBlock("oran")
   public final val PAMTRE_BERRY: BerryBlock = INSTANCE.berryBlock("pamtre")
   public final val PASSHO_BERRY: BerryBlock = INSTANCE.berryBlock("passho")
   public final val PASTURE: PastureBlock
   public final val PAYAPA_BERRY: BerryBlock = INSTANCE.berryBlock("payapa")
   public final val PC: PCBlock
   public final val PECHA_BERRY: BerryBlock = INSTANCE.berryBlock("pecha")
   public final val PEP_UP_FLOWER: FlowerBlock =
      INSTANCE.create(
         "pep_up_flower",
         new FlowerBlock(
            MobEffects.f_19620_,
            10,
            Properties.m_284310_()
               .m_284180_(MapColor.f_283915_)
               .m_60910_()
               .m_60966_()
               .m_60918_(SoundType.f_56740_)
               .m_222979_(OffsetType.XZ)
               .m_278166_(PushReaction.DESTROY)
         )
      ) as FlowerBlock
      public final val PERSIM_BERRY: BerryBlock = INSTANCE.berryBlock("persim")
   public final val PETAYA_BERRY: BerryBlock = INSTANCE.berryBlock("petaya")
   public final val PINAP_BERRY: BerryBlock = INSTANCE.berryBlock("pinap")
   public final val PINK_APRICORN: ApricornBlock = INSTANCE.apricornBlock("pink_apricorn", Apricorn.PINK)
   public final val PINK_APRICORN_SAPLING: ApricornSaplingBlock
   public final val PINK_GILDED_CHEST: GildedChestBlock
   public final val PINK_MINT: MintBlock
   private final val PLANT_PROPERTIES: Settings
   public final val POMEG_BERRY: BerryBlock = INSTANCE.berryBlock("pomeg")
   public final val POTTED_PEP_UP_FLOWER: FlowerPotBlock =
      INSTANCE.create("potted_pep_up_flower", BlocksInvoker.createFlowerPotBlock(PEP_UP_FLOWER as Block)) as FlowerPotBlock
      public final val QUALOT_BERRY: BerryBlock = INSTANCE.berryBlock("qualot")
   public final val RABUTA_BERRY: BerryBlock = INSTANCE.berryBlock("rabuta")
   public final val RAWST_BERRY: BerryBlock = INSTANCE.berryBlock("rawst")
   public final val RAZZ_BERRY: BerryBlock = INSTANCE.berryBlock("razz")
   public final val RED_APRICORN: ApricornBlock = INSTANCE.apricornBlock("red_apricorn", Apricorn.RED)
   public final val RED_APRICORN_SAPLING: ApricornSaplingBlock
   public final val RED_MINT: MintBlock
   public final val RELIC_COIN_POUCH: CoinPouchBlock
   public final val RELIC_COIN_SACK: CoinPouchBlock
   public final val RESTORATION_TANK: RestorationTankBlock
   public final val REVIVAL_HERB: RevivalHerbBlock
   public final val RINDO_BERRY: BerryBlock = INSTANCE.berryBlock("rindo")
   public final val ROSELI_BERRY: BerryBlock = INSTANCE.berryBlock("roseli")
   public final val ROWAP_BERRY: BerryBlock = INSTANCE.berryBlock("rowap")
   public final val SALAC_BERRY: BerryBlock = INSTANCE.berryBlock("salac")
   public final val SHINY_STONE_ORE: DropExperienceBlock = INSTANCE.evolutionStoneOre("shiny_stone_ore")
   public final val SHUCA_BERRY: BerryBlock = INSTANCE.berryBlock("shuca")
   public final val SITRUS_BERRY: BerryBlock = INSTANCE.berryBlock("sitrus")
   public final val SKY_TUMBLESTONE_BLOCK: Block =
      INSTANCE.create(
         "sky_tumblestone_block",
         new Block(
            Properties.m_284310_()
               .m_284180_(MapColor.f_283869_)
               .m_60978_(1.0F)
               .m_60918_(CobblemonSounds.TUMBLESTONE_BLOCK_SOUNDS)
               .m_60999_()
               .m_280658_(NoteBlockInstrument.BASEDRUM)
         )
      ) as Block
      public final val SKY_TUMBLESTONE_CLUSTER: Block = INSTANCE.tumblestoneBlock("sky_tumblestone_cluster", 3, 7, 3, null)
   public final val SMALL_BUDDING_BLACK_TUMBLESTONE: Block =
      INSTANCE.tumblestoneBlock("small_budding_black_tumblestone", 0, 3, 4, MEDIUM_BUDDING_BLACK_TUMBLESTONE)
      public final val SMALL_BUDDING_SKY_TUMBLESTONE: Block =
      INSTANCE.tumblestoneBlock("small_budding_sky_tumblestone", 0, 3, 4, MEDIUM_BUDDING_SKY_TUMBLESTONE)
      public final val SMALL_BUDDING_TUMBLESTONE: Block = INSTANCE.tumblestoneBlock("small_budding_tumblestone", 0, 3, 4, MEDIUM_BUDDING_TUMBLESTONE)
   public final val SPELON_BERRY: BerryBlock = INSTANCE.berryBlock("spelon")
   public final val STARF_BERRY: BerryBlock = INSTANCE.berryBlock("starf")
   public final val STRIPPED_APRICORN_LOG: RotatedPillarBlock = log$default(INSTANCE, "stripped_apricorn_log", null, null, 6, null)
   public final val STRIPPED_APRICORN_WOOD: RotatedPillarBlock = log$default(INSTANCE, "stripped_apricorn_wood", null, null, 6, null)
   public final val SUN_STONE_ORE: DropExperienceBlock = INSTANCE.evolutionStoneOre("sun_stone_ore")
   public final val TAMATO_BERRY: BerryBlock = INSTANCE.berryBlock("tamato")
   public final val TANGA_BERRY: BerryBlock = INSTANCE.berryBlock("tanga")
   public final val TERRACOTTA_SUN_STONE_ORE: DropExperienceBlock = INSTANCE.evolutionStoneOre("terracotta_sun_stone_ore")
   public final val THUNDER_STONE_ORE: DropExperienceBlock = INSTANCE.evolutionStoneOre("thunder_stone_ore")
   public final val TOUGA_BERRY: BerryBlock = INSTANCE.berryBlock("touga")
   public final val TUMBLESTONE_BLOCK: Block =
      INSTANCE.create(
         "tumblestone_block",
         new Block(
            Properties.m_284310_()
               .m_284180_(MapColor.f_283895_)
               .m_60978_(1.0F)
               .m_60918_(CobblemonSounds.TUMBLESTONE_BLOCK_SOUNDS)
               .m_60999_()
               .m_280658_(NoteBlockInstrument.BASEDRUM)
         )
      ) as Block
      public final val TUMBLESTONE_CLUSTER: Block = INSTANCE.tumblestoneBlock("tumblestone_cluster", 3, 7, 3, null)
   public final val VIVICHOKE_SEEDS: VivichokeBlock
   public final val WACAN_BERRY: BerryBlock = INSTANCE.berryBlock("wacan")
   public final val WATER_STONE_ORE: DropExperienceBlock = INSTANCE.evolutionStoneOre("water_stone_ore")
   public final val WATMEL_BERRY: BerryBlock = INSTANCE.berryBlock("watmel")
   public final val WEPEAR_BERRY: BerryBlock = INSTANCE.berryBlock("wepear")
   public final val WHITE_APRICORN: ApricornBlock = INSTANCE.apricornBlock("white_apricorn", Apricorn.WHITE)
   public final val WHITE_APRICORN_SAPLING: ApricornSaplingBlock
   public final val WHITE_GILDED_CHEST: GildedChestBlock
   public final val WHITE_MINT: MintBlock
   public final val WIKI_BERRY: BerryBlock = INSTANCE.berryBlock("wiki")
   public final val YACHE_BERRY: BerryBlock = INSTANCE.berryBlock("yache")
   public final val YELLOW_APRICORN: ApricornBlock = INSTANCE.apricornBlock("yellow_apricorn", Apricorn.YELLOW)
   public final val YELLOW_APRICORN_SAPLING: ApricornSaplingBlock
   public final val YELLOW_GILDED_CHEST: GildedChestBlock
   private final val berries: MutableMap<ResourceLocation, BerryBlock> = (new LinkedHashMap()) as java.util.Map
   public open val registry: Registry<Block>
   public open val registryKey: ResourceKey<Registry<Block>>

   public fun strippedBlocks(): Map<Block, Block> {
      return MapsKt.mapOf(new Pair[]{TuplesKt.to(APRICORN_WOOD, STRIPPED_APRICORN_WOOD), TuplesKt.to(APRICORN_LOG, STRIPPED_APRICORN_LOG)});
   }

   private fun apricornBlock(name: String, apricorn: Apricorn): ApricornBlock {
      val var10004: Properties = Properties.m_284310_()
         .m_284180_(apricorn.mapColor())
         .m_60977_()
         .m_60913_(Blocks.f_49999_.m_155943_(), Blocks.f_49999_.m_7325_())
         .m_60918_(SoundType.f_56736_)
         .m_60955_();
      return this.create(name, new ApricornBlock(var10004, apricorn));
   }

   private fun tumblestoneBlock(name: String, stage: Int, height: Int, xzOffset: Int, nextStage: Block?): Block {
      val var10004: Properties = Properties.m_284310_().m_278166_(PushReaction.DESTROY).m_60955_().m_60978_(1.5F).m_60918_(CobblemonSounds.TUMBLESTONE_SOUNDS);
      return this.create(name, new TumblestoneBlock(var10004, stage, height, xzOffset, nextStage));
   }

   public fun berries(): Map<ResourceLocation, BerryBlock> {
      return MapsKt.toMap(berries);
   }

   private fun berryBlock(name: String): BerryBlock {
      val identifier: ResourceLocation = MiscUtilsKt.cobblemonResource("$name_berry");
      val var10001: java.lang.String = identifier.m_135815_();
      val var10005: Properties = Properties.m_60926_(Blocks.f_50092_ as BlockBehaviour).m_60988_().m_60918_(CobblemonSounds.BERRY_BUSH_SOUNDS).m_60978_(0.2F);
      val block: BerryBlock = this.create(var10001, new BerryBlock(identifier, var10005));
      berries.put(identifier, block);
      return block;
   }

   private fun log(name: String, arg: MapColor = MapColor.f_283762_, arg2: MapColor = MapColor.f_283762_): RotatedPillarBlock {
      val var10000: Any = this.create(name, BlocksInvoker.createLogBlock(arg, arg2));
      return var10000 as RotatedPillarBlock;
   }

   private fun <E> setFlammable(block: Any, burnChance: Int, spreadChance: Int): Any {
      if (block !is Block) {
         return (E)block;
      } else {
         val var10000: Block = Blocks.f_50083_;
         ((var10000 as FireBlock) as FireBlockInvoker).registerNewFlammableBlock(block as Block, burnChance, spreadChance);
         return (E)block;
      }
   }

   private fun evolutionStoneOre(name: String): DropExperienceBlock {
      return this.create(name, new DropExperienceBlock(Properties.m_60926_(Blocks.f_49996_ as BlockBehaviour), UniformInt.m_146622_(1, 2) as IntProvider));
   }

   private fun deepslateEvolutionStoneOre(name: String): DropExperienceBlock {
      return this.create(name, new DropExperienceBlock(Properties.m_60926_(Blocks.f_152468_ as BlockBehaviour), UniformInt.m_146622_(1, 2) as IntProvider));
   }

   private fun leaves(name: String): LeavesBlock {
      val var10000: Any = this.create(name, BlocksInvoker.createLeavesBlock(SoundType.f_56740_));
      return var10000 as LeavesBlock;
   }

   @JvmStatic
   fun `APRICORN_TRAPDOOR$lambda$0`(var0: BlockState, var1: BlockGetter, var2: BlockPos, var3: EntityType): Boolean {
      return false;
   }

   @JvmStatic
   fun `MONITOR$lambda$1`(it: BlockState): Int {
      return if (it.m_61143_(MonitorBlock.Companion.getSCREEN() as Property) != MonitorBlock.MonitorScreen.OFF) 15 else 0;
   }

   @JvmStatic
   fun `HEALING_MACHINE$lambda$2`(it: BlockState): Int {
      val var10000: java.lang.Comparable = it.m_61143_(HealingMachineBlock.Companion.getCHARGE_LEVEL() as Property);
      return if ((var10000 as java.lang.Number).intValue() >= 5) 7 else 2;
   }

   @JvmStatic
   fun `PC$lambda$3`(it: BlockState): Int {
      val var10000: java.lang.Comparable = it.m_61143_(PCBlock.Companion.getON() as Property);
      return if (var10000 as java.lang.Boolean && it.m_61143_(PCBlock.Companion.getPART() as Property) === PCBlock.PCPart.TOP) 10 else 0;
   }

   @JvmStatic
   fun `PASTURE$lambda$4`(it: BlockState): Int {
      val var10000: java.lang.Comparable = it.m_61143_(PastureBlock.Companion.getON() as Property);
      return if (var10000 as java.lang.Boolean && it.m_61143_(PastureBlock.Companion.getPART() as Property) === PastureBlock.PasturePart.TOP) 10 else 0;
   }

   @JvmStatic
   fun {
      val var10000: DefaultedRegistry = BuiltInRegistries.f_256975_;
      registry = var10000 as Registry<Block>;
      val var7: ResourceKey = Registries.f_256747_;
      registryKey = var7;
      val var8: CobblemonBlocks = INSTANCE;
      val var10003: MapColor = MapColor.f_283748_;
      APRICORN_LOG = log$default(var8, "apricorn_log", null, var10003, 2, null);
      val var9: CobblemonBlocks = INSTANCE;
      var var10004: Properties = Properties.m_60926_(Blocks.f_50095_ as BlockBehaviour);
      var var10005: WoodType = APRICORN_WOOD_TYPE;
      APRICORN_SIGN = var9.create("apricorn_sign", new CobblemonSignBlock(var10004, var10005));
      val var10: CobblemonBlocks = INSTANCE;
      var10004 = Properties.m_60926_(Blocks.f_50158_ as BlockBehaviour);
      var10005 = APRICORN_WOOD_TYPE;
      APRICORN_WALL_SIGN = var10.create("apricorn_wall_sign", new CobblemonWallSignBlock(var10004, var10005));
      val var11: CobblemonBlocks = INSTANCE;
      var10004 = Properties.m_60926_(Blocks.f_244093_ as BlockBehaviour);
      var10005 = APRICORN_WOOD_TYPE;
      APRICORN_HANGING_SIGN = var11.create("apricorn_hanging_sign", new CobblemonHangingSignBlock(var10004, var10005));
      val var12: CobblemonBlocks = INSTANCE;
      var10004 = Properties.m_60926_(Blocks.f_244319_ as BlockBehaviour);
      var10005 = APRICORN_WOOD_TYPE;
      APRICORN_WALL_HANGING_SIGN = var12.create("apricorn_wall_hanging_sign", new CobblemonWallHangingSignBlock(var10004, var10005));
      val var13: CobblemonBlocks = INSTANCE;
      var10004 = PLANT_PROPERTIES;
      BLACK_APRICORN_SAPLING = var13.create("black_apricorn_sapling", new ApricornSaplingBlock(var10004, Apricorn.BLACK));
      val var14: CobblemonBlocks = INSTANCE;
      var10004 = PLANT_PROPERTIES;
      BLUE_APRICORN_SAPLING = var14.create("blue_apricorn_sapling", new ApricornSaplingBlock(var10004, Apricorn.BLUE));
      val var15: CobblemonBlocks = INSTANCE;
      var10004 = PLANT_PROPERTIES;
      GREEN_APRICORN_SAPLING = var15.create("green_apricorn_sapling", new ApricornSaplingBlock(var10004, Apricorn.GREEN));
      val var16: CobblemonBlocks = INSTANCE;
      var10004 = PLANT_PROPERTIES;
      PINK_APRICORN_SAPLING = var16.create("pink_apricorn_sapling", new ApricornSaplingBlock(var10004, Apricorn.PINK));
      val var17: CobblemonBlocks = INSTANCE;
      var10004 = PLANT_PROPERTIES;
      RED_APRICORN_SAPLING = var17.create("red_apricorn_sapling", new ApricornSaplingBlock(var10004, Apricorn.RED));
      val var18: CobblemonBlocks = INSTANCE;
      var10004 = PLANT_PROPERTIES;
      WHITE_APRICORN_SAPLING = var18.create("white_apricorn_sapling", new ApricornSaplingBlock(var10004, Apricorn.WHITE));
      val var19: CobblemonBlocks = INSTANCE;
      var10004 = PLANT_PROPERTIES;
      YELLOW_APRICORN_SAPLING = var19.create("yellow_apricorn_sapling", new ApricornSaplingBlock(var10004, Apricorn.YELLOW));
      val var20: CobblemonBlocks = INSTANCE;
      var10004 = Properties.m_284310_()
         .m_278166_(PushReaction.DESTROY)
         .m_278183_()
         .m_284180_(MapColor.f_283909_)
         .m_60910_()
         .m_60977_()
         .m_60966_()
         .m_60918_(CobblemonSounds.MEDICINAL_LEEK_SOUNDS);
      MEDICINAL_LEEK = var20.create("medicinal_leek", new MedicinalLeekBlock(var10004));
      val var21: CobblemonBlocks = INSTANCE;
      var10004 = Properties.m_284310_()
         .m_278166_(PushReaction.DESTROY)
         .m_278183_()
         .m_284180_(MapColor.f_283762_)
         .m_60910_()
         .m_60977_()
         .m_60966_()
         .m_60918_(CobblemonSounds.ENERGY_ROOT_SOUNDS);
      ENERGY_ROOT = var21.create("energy_root", new EnergyRootBlock(var10004));
      val var22: CobblemonBlocks = INSTANCE;
      var10004 = Properties.m_284310_()
         .m_278166_(PushReaction.DESTROY)
         .m_278183_()
         .m_284180_(MapColor.f_283915_)
         .m_60910_()
         .m_60977_()
         .m_60966_()
         .m_60918_(CobblemonSounds.BIG_ROOT_SOUNDS);
      BIG_ROOT = var22.create("big_root", new BigRootBlock(var10004));
      val var23: CobblemonBlocks = INSTANCE;
      var10004 = Properties.m_284310_()
         .m_278166_(PushReaction.DESTROY)
         .m_284180_(MapColor.f_283915_)
         .m_278183_()
         .m_60910_()
         .m_60966_()
         .m_60918_(CobblemonSounds.REVIVAL_HERB_SOUNDS);
      REVIVAL_HERB = var23.create("revival_herb", new RevivalHerbBlock(var10004));
      val var24: CobblemonBlocks = INSTANCE;
      var10004 = Properties.m_284310_().m_60918_(CobblemonSounds.RELIC_COIN_POUCH_SOUNDS).m_278166_(PushReaction.DESTROY).m_60978_(0.4F).m_60955_();
      RELIC_COIN_POUCH = var24.create("relic_coin_pouch", new CoinPouchBlock(var10004, true));
      val var25: CobblemonBlocks = INSTANCE;
      var10004 = Properties.m_284310_().m_60918_(CobblemonSounds.RELIC_COIN_SACK_SOUNDS).m_278166_(PushReaction.DESTROY).m_60978_(0.4F);
      RELIC_COIN_SACK = var25.create("relic_coin_sack", new CoinPouchBlock(var10004, false));
      val var26: CobblemonBlocks = INSTANCE;
      var10004 = Properties.m_60926_(Blocks.f_50087_ as BlockBehaviour).m_60955_().m_60918_(CobblemonSounds.GILDED_CHEST_SOUNDS);
      GILDED_CHEST = var26.create("gilded_chest", new GildedChestBlock(var10004, GildedChestBlock.Type.RED));
      val var27: CobblemonBlocks = INSTANCE;
      var10004 = Properties.m_60926_(Blocks.f_50087_ as BlockBehaviour).m_60955_().m_60918_(CobblemonSounds.GILDED_CHEST_SOUNDS);
      BLUE_GILDED_CHEST = var27.create("blue_gilded_chest", new GildedChestBlock(var10004, GildedChestBlock.Type.BLUE));
      val var28: CobblemonBlocks = INSTANCE;
      var10004 = Properties.m_60926_(Blocks.f_50087_ as BlockBehaviour).m_60955_().m_60918_(CobblemonSounds.GILDED_CHEST_SOUNDS);
      BLACK_GILDED_CHEST = var28.create("black_gilded_chest", new GildedChestBlock(var10004, GildedChestBlock.Type.BLACK));
      val var29: CobblemonBlocks = INSTANCE;
      var10004 = Properties.m_60926_(Blocks.f_50087_ as BlockBehaviour).m_60955_().m_60918_(CobblemonSounds.GILDED_CHEST_SOUNDS);
      YELLOW_GILDED_CHEST = var29.create("yellow_gilded_chest", new GildedChestBlock(var10004, GildedChestBlock.Type.YELLOW));
      val var30: CobblemonBlocks = INSTANCE;
      var10004 = Properties.m_60926_(Blocks.f_50087_ as BlockBehaviour).m_60955_().m_60918_(CobblemonSounds.GILDED_CHEST_SOUNDS);
      WHITE_GILDED_CHEST = var30.create("white_gilded_chest", new GildedChestBlock(var10004, GildedChestBlock.Type.WHITE));
      val var31: CobblemonBlocks = INSTANCE;
      var10004 = Properties.m_60926_(Blocks.f_50087_ as BlockBehaviour).m_60955_().m_60918_(CobblemonSounds.GILDED_CHEST_SOUNDS);
      GREEN_GILDED_CHEST = var31.create("green_gilded_chest", new GildedChestBlock(var10004, GildedChestBlock.Type.GREEN));
      val var32: CobblemonBlocks = INSTANCE;
      var10004 = Properties.m_60926_(Blocks.f_50087_ as BlockBehaviour).m_60955_().m_60918_(CobblemonSounds.GILDED_CHEST_SOUNDS);
      PINK_GILDED_CHEST = var32.create("pink_gilded_chest", new GildedChestBlock(var10004, GildedChestBlock.Type.PINK));
      val var33: CobblemonBlocks = INSTANCE;
      var10004 = Properties.m_60926_(Blocks.f_50087_ as BlockBehaviour).m_60955_().m_60918_(CobblemonSounds.GILDED_CHEST_SOUNDS);
      GIMMIGHOUL_CHEST = var33.create("gimmighoul_chest", new GildedChestBlock(var10004, GildedChestBlock.Type.FAKE));
      val var34: CobblemonBlocks = INSTANCE;
      var10004 = Properties.m_284310_()
         .m_284180_(MapColor.f_283906_)
         .m_60918_(SoundType.f_56743_)
         .m_278166_(PushReaction.BLOCK)
         .m_60999_()
         .m_60913_(5.0F, 6.0F)
         .m_60953_(CobblemonBlocks::MONITOR$lambda$1);
      MONITOR = var34.create("monitor", new MonitorBlock(var10004));
      val var35: CobblemonBlocks = INSTANCE;
      var10004 = Properties.m_284310_()
         .m_284180_(MapColor.f_283906_)
         .m_60918_(SoundType.f_56743_)
         .m_278166_(PushReaction.BLOCK)
         .m_60999_()
         .m_60913_(5.0F, 6.0F)
         .m_60955_();
      FOSSIL_ANALYZER = var35.create("fossil_analyzer", new FossilAnalyzerBlock(var10004));
      val var36: CobblemonBlocks = INSTANCE;
      var10004 = Properties.m_284310_()
         .m_284180_(MapColor.f_283906_)
         .m_60918_(SoundType.f_56744_)
         .m_278166_(PushReaction.BLOCK)
         .m_60999_()
         .m_60913_(5.0F, 6.0F)
         .m_60955_();
      RESTORATION_TANK = var36.create("restoration_tank", new RestorationTankBlock(var10004));
      val var37: CobblemonBlocks = INSTANCE;
      var10004 = Properties.m_284310_()
         .m_284180_(MapColor.f_283906_)
         .m_60918_(SoundType.f_56743_)
         .m_278166_(PushReaction.BLOCK)
         .m_60978_(2.0F)
         .m_60955_()
         .m_60953_(CobblemonBlocks::HEALING_MACHINE$lambda$2);
      HEALING_MACHINE = var37.create("healing_machine", new HealingMachineBlock(var10004));
      val var38: CobblemonBlocks = INSTANCE;
      var10004 = Properties.m_284310_()
         .m_284180_(MapColor.f_283906_)
         .m_60918_(SoundType.f_56743_)
         .m_278166_(PushReaction.BLOCK)
         .m_60978_(2.0F)
         .m_60955_()
         .m_60953_(CobblemonBlocks::PC$lambda$3);
      PC = var38.create("pc", new PCBlock(var10004));
      val var39: CobblemonBlocks = INSTANCE;
      var10004 = Properties.m_284310_()
         .m_60918_(CobblemonSounds.DISPLAY_CASE_SOUNDS)
         .m_60955_()
         .m_278166_(PushReaction.BLOCK)
         .m_284180_(MapColor.f_283947_)
         .m_60978_(0.3F);
      DISPLAY_CASE = var39.create("display_case", new DisplayCaseBlock(var10004));
      val var40: CobblemonBlocks = INSTANCE;
      val var78: MintBlock.MintType = MintBlock.MintType.RED;
      val var89: Properties = Properties.m_284310_().m_284180_(MapColor.f_283913_).m_60910_().m_60977_().m_60966_().m_60918_(CobblemonSounds.MINT_SOUNDS);
      RED_MINT = var40.create("red_mint", new MintBlock(var78, var89));
      val var41: CobblemonBlocks = INSTANCE;
      val var79: MintBlock.MintType = MintBlock.MintType.BLUE;
      val var90: Properties = Properties.m_284310_().m_284180_(MapColor.f_283743_).m_60910_().m_60977_().m_60966_().m_60918_(CobblemonSounds.MINT_SOUNDS);
      BLUE_MINT = var41.create("blue_mint", new MintBlock(var79, var90));
      val var42: CobblemonBlocks = INSTANCE;
      val var80: MintBlock.MintType = MintBlock.MintType.CYAN;
      val var91: Properties = Properties.m_284310_().m_284180_(MapColor.f_283772_).m_60910_().m_60977_().m_60966_().m_60918_(CobblemonSounds.MINT_SOUNDS);
      CYAN_MINT = var42.create("cyan_mint", new MintBlock(var80, var91));
      val var43: CobblemonBlocks = INSTANCE;
      val var81: MintBlock.MintType = MintBlock.MintType.PINK;
      val var92: Properties = Properties.m_284310_().m_284180_(MapColor.f_283765_).m_60910_().m_60977_().m_60966_().m_60918_(CobblemonSounds.MINT_SOUNDS);
      PINK_MINT = var43.create("pink_mint", new MintBlock(var81, var92));
      val var44: CobblemonBlocks = INSTANCE;
      val var82: MintBlock.MintType = MintBlock.MintType.GREEN;
      val var93: Properties = Properties.m_284310_().m_284180_(MapColor.f_283784_).m_60910_().m_60977_().m_60966_().m_60918_(CobblemonSounds.MINT_SOUNDS);
      GREEN_MINT = var44.create("green_mint", new MintBlock(var82, var93));
      val var45: CobblemonBlocks = INSTANCE;
      val var83: MintBlock.MintType = MintBlock.MintType.WHITE;
      val var94: Properties = Properties.m_284310_().m_284180_(MapColor.f_283811_).m_60910_().m_60977_().m_60966_().m_60918_(CobblemonSounds.MINT_SOUNDS);
      WHITE_MINT = var45.create("white_mint", new MintBlock(var83, var94));
      val var46: CobblemonBlocks = INSTANCE;
      var10004 = Properties.m_284310_()
         .m_284180_(MapColor.f_283748_)
         .m_60918_(SoundType.f_56736_)
         .m_60978_(2.0F)
         .m_60955_()
         .m_278166_(PushReaction.BLOCK)
         .m_60953_(CobblemonBlocks::PASTURE$lambda$4);
      PASTURE = var46.create("pasture", new PastureBlock(var10004));
      val var47: CobblemonBlocks = INSTANCE;
      var10004 = Properties.m_284310_()
         .m_278166_(PushReaction.DESTROY)
         .m_278183_()
         .m_284180_(MapColor.f_283915_)
         .m_60910_()
         .m_60977_()
         .m_60966_()
         .m_60918_(CobblemonSounds.VIVICHOKE_SOUNDS);
      VIVICHOKE_SEEDS = var47.create("vivichoke_seeds", new VivichokeBlock(var10004));

      val var0: Array<Triple>;
      for (Triple data : var0) {
         INSTANCE.setFlammable(data.getFirst(), (data.getSecond() as java.lang.Number).intValue(), (data.getThird() as java.lang.Number).intValue());
      }
   }
}
