package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.world.placementmodifier

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.codec.CodecMapped
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.data.ArbitrarilyMappedSerializableCompanion
import net.minecraft.world.level.block.state.BlockState

public interface BlockStateTransformer : CodecMapped {
   public val type: BlockStateTransformerType

   public abstract fun transform(blockState: BlockState): BlockState {
   }

   public companion object : ArbitrarilyMappedSerializableCompanion(
         <unrepresentable>.INSTANCE,
         <unrepresentable>.INSTANCE as (BlockStateTransformerType?) -> java.lang.String,
         <unrepresentable>.INSTANCE as (BlockStateTransformer?) -> BlockStateTransformerType
      )
}
