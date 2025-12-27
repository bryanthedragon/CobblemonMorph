package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.context;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.SpawnCause;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.WorldSlice;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.influence.SpawningInfluence;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.block.state.BlockState;

public open class GroundedSpawningContext(cause: SpawnCause, world: ServerLevel, position: BlockPos, light: Int, skyLight: Int, canSeeSky: Boolean, influences: MutableList<SpawningInfluence>, height: Int, nearbyBlocks: List<BlockState>, slice: WorldSlice ) : FlooredSpawningContext(cause, world, position, light, skyLight, canSeeSky, influences, height, nearbyBlocks, slice)
