package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.block;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntity;

public record BlockEntityPos<T extends BlockEntity>(BlockPos pos, T entity) {
    
}
