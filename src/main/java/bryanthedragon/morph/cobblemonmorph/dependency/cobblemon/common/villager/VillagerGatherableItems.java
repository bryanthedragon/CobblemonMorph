package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.villager

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.CobblemonItems
import net.minecraft.world.item.Item

public object VillagerGatherableItems {
   public final val villagerGatherableItems: Set<Item> =
      SetsKt.setOf(
         new Item[]{
            CobblemonItems.BLUE_MINT_SEEDS,
            CobblemonItems.CYAN_MINT_SEEDS,
            CobblemonItems.GREEN_MINT_SEEDS,
            CobblemonItems.PINK_MINT_SEEDS,
            CobblemonItems.RED_MINT_SEEDS,
            CobblemonItems.REVIVAL_HERB,
            CobblemonItems.WHITE_MINT_SEEDS,
            CobblemonItems.VIVICHOKE_SEEDS
         }
      )
   }
