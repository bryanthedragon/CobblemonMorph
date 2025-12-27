package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.battles.model.actor

import net.minecraft.world.entity.LivingEntity

public interface EntityBackedBattleActor<T extends LivingEntity> {
   public val entity: Any?
}
