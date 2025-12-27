package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.quirk

import net.minecraft.world.entity.Entity

public class SimpleQuirkData<T extends Entity> : QuirkData<T> {
   public final var nextOccurrenceSeconds: Float = -1.0F
   public final var remainingLoops: Int
}
