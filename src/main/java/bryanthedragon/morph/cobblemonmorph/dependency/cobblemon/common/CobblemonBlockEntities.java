package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.block.BerryBlock
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.block.entity.BerryBlockEntity
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.block.entity.CobblemonHangingSignBlockEntity
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.block.entity.CobblemonSignBlockEntity
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.block.entity.DisplayCaseBlockEntity
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.block.entity.FossilAnalyzerBlockEntity
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.block.entity.FossilMultiblockEntity
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.block.entity.GildedChestBlockEntity
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.block.entity.HealingMachineBlockEntity
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.block.entity.PCBlockEntity
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.block.entity.PokemonPastureBlockEntity
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.block.entity.RestorationTankBlockEntity
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.block.multiblock.FossilMultiblockBuilder
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.platform.PlatformRegistry

import java.util.Arrays

import net.minecraft.core.BlockPos
import net.minecraft.core.Registry
import net.minecraft.core.registries.BuiltInRegistries
import net.minecraft.core.registries.Registries
import net.minecraft.resources.ResourceKey
import net.minecraft.world.level.block.Block
import net.minecraft.world.level.block.entity.BlockEntityType
import net.minecraft.world.level.block.entity.BlockEntityType.BlockEntitySupplier
import net.minecraft.world.level.block.entity.BlockEntityType.Builder
import net.minecraft.world.level.block.state.BlockState

public object CobblemonBlockEntities : PlatformRegistry<Registry<BlockEntityType<?>>, ResourceKey<Registry<BlockEntityType<?>>>, BlockEntityType<?>> {
   public final val BERRY: BlockEntityType<BerryBlockEntity>
   public final val DISPLAY_CASE: BlockEntityType<DisplayCaseBlockEntity>
   public final val FOSSIL_ANALYZER: BlockEntityType<FossilAnalyzerBlockEntity>
   public final val FOSSIL_MULTIBLOCK: BlockEntityType<FossilMultiblockEntity>
   public final val GILDED_CHEST: BlockEntityType<GildedChestBlockEntity>
   public final val HANGING_SIGN: BlockEntityType<CobblemonHangingSignBlockEntity>
   public final val HEALING_MACHINE: BlockEntityType<HealingMachineBlockEntity>
   public final val PASTURE: BlockEntityType<PokemonPastureBlockEntity>
   public final val PC: BlockEntityType<PCBlockEntity>
   public final val RESTORATION_TANK: BlockEntityType<RestorationTankBlockEntity>
   public final val SIGN: BlockEntityType<CobblemonSignBlockEntity>
   public open val registry: Registry<BlockEntityType<*>>
   public open val registryKey: ResourceKey<Registry<BlockEntityType<*>>>

   @JvmStatic
   fun `FOSSIL_MULTIBLOCK$lambda$1`(pos: BlockPos, state: BlockState): FossilMultiblockEntity {
      return new FossilMultiblockEntity(pos, state, new FossilMultiblockBuilder(pos), null, 8, null);
   }

   @JvmStatic
   fun `RESTORATION_TANK$lambda$2`(pos: BlockPos, state: BlockState): RestorationTankBlockEntity {
      return new RestorationTankBlockEntity(pos, state, new FossilMultiblockBuilder(pos));
   }

   @JvmStatic
   fun `FOSSIL_ANALYZER$lambda$3`(pos: BlockPos, state: BlockState): FossilAnalyzerBlockEntity {
      return new FossilAnalyzerBlockEntity(pos, state, new FossilMultiblockBuilder(pos));
   }

   @JvmStatic
   fun {
      var var10000: Registry = BuiltInRegistries.f_257049_;
      registry = var10000;
      val var14: ResourceKey = Registries.f_256922_;
      registryKey = var14;
      var10000 = INSTANCE.create(
         "healing_machine", Builder.m_155273_(HealingMachineBlockEntity::new, new Block[]{CobblemonBlocks.HEALING_MACHINE}).m_58966_(null)
      );
      HEALING_MACHINE = var10000 as BlockEntityType<HealingMachineBlockEntity>;
      var10000 = INSTANCE.create("pc", Builder.m_155273_(PCBlockEntity::new, new Block[]{CobblemonBlocks.PC}).m_58966_(null));
      PC = var10000 as BlockEntityType<PCBlockEntity>;
      val var19: CobblemonBlockEntities = INSTANCE;
      val var37: BlockEntitySupplier = BerryBlockEntity::new;
      val var0: Array<BerryBlock> = CobblemonBlocks.INSTANCE.berries().values().toArray(new BerryBlock[0]);
      BERRY = var19.create("berry", Builder.m_155273_(var37, Arrays.copyOf(var0, var0.length)).m_58966_(null));
      var10000 = INSTANCE.create("pasture", Builder.m_155273_(PokemonPastureBlockEntity::new, new Block[]{CobblemonBlocks.PASTURE}).m_58966_(null));
      PASTURE = var10000 as BlockEntityType<PokemonPastureBlockEntity>;
      var10000 = INSTANCE.create(
         "sign",
         Builder.m_155273_(CobblemonSignBlockEntity::new, new Block[]{CobblemonBlocks.APRICORN_SIGN, CobblemonBlocks.APRICORN_WALL_SIGN}).m_58966_(null)
      );
      SIGN = var10000 as BlockEntityType<CobblemonSignBlockEntity>;
      var10000 = INSTANCE.create(
         "hanging_sign",
         Builder.m_155273_(CobblemonHangingSignBlockEntity::new, new Block[]{CobblemonBlocks.APRICORN_HANGING_SIGN, CobblemonBlocks.APRICORN_WALL_HANGING_SIGN})
            .m_58966_(null)
      );
      HANGING_SIGN = var10000 as BlockEntityType<CobblemonHangingSignBlockEntity>;
      var10000 = INSTANCE.create(
         "chest",
         Builder.m_155273_(
               (p0, p1) -> new GildedChestBlockEntity(p0, p1, null, 4, null),
               new Block[]{
                  CobblemonBlocks.GILDED_CHEST,
                  CobblemonBlocks.BLUE_GILDED_CHEST,
                  CobblemonBlocks.YELLOW_GILDED_CHEST,
                  CobblemonBlocks.PINK_GILDED_CHEST,
                  CobblemonBlocks.BLACK_GILDED_CHEST,
                  CobblemonBlocks.WHITE_GILDED_CHEST,
                  CobblemonBlocks.GREEN_GILDED_CHEST,
                  CobblemonBlocks.GIMMIGHOUL_CHEST
               }
            )
            .m_58966_(null)
      );
      GILDED_CHEST = var10000 as BlockEntityType<GildedChestBlockEntity>;
      var10000 = INSTANCE.create(
         "fossil_multiblock", Builder.m_155273_(CobblemonBlockEntities::FOSSIL_MULTIBLOCK$lambda$1, new Block[]{CobblemonBlocks.MONITOR}).m_58966_(null)
      );
      FOSSIL_MULTIBLOCK = var10000 as BlockEntityType<FossilMultiblockEntity>;
      var10000 = INSTANCE.create(
         "restoration_tank", Builder.m_155273_(CobblemonBlockEntities::RESTORATION_TANK$lambda$2, new Block[]{CobblemonBlocks.RESTORATION_TANK}).m_58966_(null)
      );
      RESTORATION_TANK = var10000 as BlockEntityType<RestorationTankBlockEntity>;
      var10000 = INSTANCE.create(
         "fossil_analyzer", Builder.m_155273_(CobblemonBlockEntities::FOSSIL_ANALYZER$lambda$3, new Block[]{CobblemonBlocks.FOSSIL_ANALYZER}).m_58966_(null)
      );
      FOSSIL_ANALYZER = var10000 as BlockEntityType<FossilAnalyzerBlockEntity>;
      var10000 = INSTANCE.create("display_case", Builder.m_155273_(DisplayCaseBlockEntity::new, new Block[]{CobblemonBlocks.DISPLAY_CASE}).m_58966_(null));
      DISPLAY_CASE = var10000 as BlockEntityType<DisplayCaseBlockEntity>;
   }
}
