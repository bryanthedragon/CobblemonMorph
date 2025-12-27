package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util

import net.minecraft.core.BlockPos
import net.minecraft.world.phys.Vec3
import org.joml.Vector3f

public fun Vec3.toBlockPos(): BlockPos {
   val var10000: BlockPos = BlockPos.m_274561_(`$this$toBlockPos`.f_82479_, `$this$toBlockPos`.f_82480_, `$this$toBlockPos`.f_82481_);
   return var10000;
}

public fun Vec3.toVec3f(): Vector3f {
   return new Vector3f((float)`$this$toVec3f`.f_82479_, (float)`$this$toVec3f`.f_82480_, (float)`$this$toVec3f`.f_82481_);
}

public fun Vector3f.toVec3d(): Vec3 {
   return new Vec3(`$this$toVec3d`.x, `$this$toVec3d`.y, `$this$toVec3d`.z);
}

public fun Vector3f.set(vec3d: Vec3): Vector3f {
   `$this$set`.x = (float)vec3d.f_82479_;
   `$this$set`.y = (float)vec3d.f_82480_;
   `$this$set`.z = (float)vec3d.f_82481_;
   return `$this$set`;
}
