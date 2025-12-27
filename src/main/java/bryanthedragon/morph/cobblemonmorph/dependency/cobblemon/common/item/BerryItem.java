package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.item

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.berry.Berry
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.block.BerryBlock
import net.minecraft.world.item.ItemNameBlockItem
import net.minecraft.world.item.Item.Properties
import net.minecraft.world.level.block.Block

public open class BerryItem(berryBlock: BerryBlock) : ItemNameBlockItem(berryBlock as Block, new Properties()) {
   private final val berryBlock: BerryBlock

   init {
      this.berryBlock = berryBlock;
   }

   public fun berry(): Berry? {
      return this.berryBlock.berry();
   }
}
