package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util

import net.minecraft.core.BlockPos
import net.minecraft.world.phys.Vec3

public fun BlockPos.toVec3d(): Vec3 {
   return new Vec3(`$this$toVec3d`.m_123341_(), `$this$toVec3d`.m_123342_(), `$this$toVec3d`.m_123343_());
}
