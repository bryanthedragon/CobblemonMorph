package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.entity

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.entity.EntitySideDelegate

public interface Poseable {
   public val delegate: EntitySideDelegate<*>

   public abstract fun getCurrentPoseType(): PoseType {
   }
}
