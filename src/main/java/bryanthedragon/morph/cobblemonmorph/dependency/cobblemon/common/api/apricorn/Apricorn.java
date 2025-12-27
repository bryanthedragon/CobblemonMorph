package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.apricorn

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.CobblemonBlocks
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.CobblemonItems
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.block.ApricornBlock
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.block.ApricornSaplingBlock
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.item.ApricornItem

import net.minecraft.world.item.BlockItem
import net.minecraft.world.level.material.MapColor

public enum Apricorn {
   BLACK,
   BLUE,
   GREEN,
   PINK,
   RED,
   WHITE,
   YELLOW
   public fun item(): ApricornItem {
      var var10000: ApricornItem;
      switch (Apricorn.WhenMappings.$EnumSwitchMapping$0[this.ordinal()]) {
         case 1:
            var10000 = CobblemonItems.BLACK_APRICORN;
            break;
         case 2:
            var10000 = CobblemonItems.BLUE_APRICORN;
            break;
         case 3:
            var10000 = CobblemonItems.GREEN_APRICORN;
            break;
         case 4:
            var10000 = CobblemonItems.PINK_APRICORN;
            break;
         case 5:
            var10000 = CobblemonItems.RED_APRICORN;
            break;
         case 6:
            var10000 = CobblemonItems.WHITE_APRICORN;
            break;
         case 7:
            var10000 = CobblemonItems.YELLOW_APRICORN;
            break;
         default:
            throw new NoWhenBranchMatchedException();
      }

      return var10000;
   }

   public fun seed(): BlockItem {
      var var10000: BlockItem;
      switch (Apricorn.WhenMappings.$EnumSwitchMapping$0[this.ordinal()]) {
         case 1:
            var10000 = CobblemonItems.BLACK_APRICORN_SEED as BlockItem;
            break;
         case 2:
            var10000 = CobblemonItems.BLUE_APRICORN_SEED as BlockItem;
            break;
         case 3:
            var10000 = CobblemonItems.GREEN_APRICORN_SEED as BlockItem;
            break;
         case 4:
            var10000 = CobblemonItems.PINK_APRICORN_SEED as BlockItem;
            break;
         case 5:
            var10000 = CobblemonItems.RED_APRICORN_SEED as BlockItem;
            break;
         case 6:
            var10000 = CobblemonItems.WHITE_APRICORN_SEED as BlockItem;
            break;
         case 7:
            var10000 = CobblemonItems.YELLOW_APRICORN_SEED as BlockItem;
            break;
         default:
            throw new NoWhenBranchMatchedException();
      }

      return var10000;
   }

   public fun block(): ApricornBlock {
      var var10000: ApricornBlock;
      switch (Apricorn.WhenMappings.$EnumSwitchMapping$0[this.ordinal()]) {
         case 1:
            var10000 = CobblemonBlocks.BLACK_APRICORN;
            break;
         case 2:
            var10000 = CobblemonBlocks.BLUE_APRICORN;
            break;
         case 3:
            var10000 = CobblemonBlocks.GREEN_APRICORN;
            break;
         case 4:
            var10000 = CobblemonBlocks.PINK_APRICORN;
            break;
         case 5:
            var10000 = CobblemonBlocks.RED_APRICORN;
            break;
         case 6:
            var10000 = CobblemonBlocks.WHITE_APRICORN;
            break;
         case 7:
            var10000 = CobblemonBlocks.YELLOW_APRICORN;
            break;
         default:
            throw new NoWhenBranchMatchedException();
      }

      return var10000;
   }

   public fun sapling(): ApricornSaplingBlock {
      var var10000: ApricornSaplingBlock;
      switch (Apricorn.WhenMappings.$EnumSwitchMapping$0[this.ordinal()]) {
         case 1:
            var10000 = CobblemonBlocks.BLACK_APRICORN_SAPLING;
            break;
         case 2:
            var10000 = CobblemonBlocks.BLUE_APRICORN_SAPLING;
            break;
         case 3:
            var10000 = CobblemonBlocks.GREEN_APRICORN_SAPLING;
            break;
         case 4:
            var10000 = CobblemonBlocks.PINK_APRICORN_SAPLING;
            break;
         case 5:
            var10000 = CobblemonBlocks.RED_APRICORN_SAPLING;
            break;
         case 6:
            var10000 = CobblemonBlocks.WHITE_APRICORN_SAPLING;
            break;
         case 7:
            var10000 = CobblemonBlocks.YELLOW_APRICORN_SAPLING;
            break;
         default:
            throw new NoWhenBranchMatchedException();
      }

      return var10000;
   }

   public fun mapColor(): MapColor {
      var var10000: MapColor;
      switch (Apricorn.WhenMappings.$EnumSwitchMapping$0[this.ordinal()]) {
         case 1:
            var10000 = MapColor.f_283927_;
            break;
         case 2:
            var10000 = MapColor.f_283743_;
            break;
         case 3:
            var10000 = MapColor.f_283784_;
            break;
         case 4:
            var10000 = MapColor.f_283765_;
            break;
         case 5:
            var10000 = MapColor.f_283913_;
            break;
         case 6:
            var10000 = MapColor.f_283811_;
            break;
         case 7:
            var10000 = MapColor.f_283832_;
            break;
         default:
            throw new NoWhenBranchMatchedException();
      }

      return var10000;
   }
}
