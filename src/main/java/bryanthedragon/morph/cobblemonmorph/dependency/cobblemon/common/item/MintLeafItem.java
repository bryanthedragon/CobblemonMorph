package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.item

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.block.MintBlock.MintType
import net.minecraft.world.item.Item.Properties

public class MintLeafItem(mintType: MintType) : CobblemonItem(new Properties()) {
   public final val mintType: MintType

   init {
      this.mintType = mintType;
   }
}
