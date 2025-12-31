package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.block;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;

public interface BlockAction {
    void accept(BlockState state, BlockPos pos);
}
