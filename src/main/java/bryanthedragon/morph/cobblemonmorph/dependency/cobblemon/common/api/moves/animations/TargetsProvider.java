package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.moves.animations

import net.minecraft.world.entity.Entity

public class TargetsProvider(targets: List<Entity>) : EntityProvider {
   public open val entities: List<Entity>

   init {
      this.entities = targets;
   }

   public constructor(vararg targets: Entity) : this(ArraysKt.toList(targets))}
