package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.battles.model.actor

import net.minecraft.server.level.ServerLevel
import net.minecraft.world.phys.Vec3

public interface FleeableBattleActor {
   public val fleeDistance: Float

   public abstract fun getWorldAndPosition(): Pair<ServerLevel, Vec3>? {
   }
}
