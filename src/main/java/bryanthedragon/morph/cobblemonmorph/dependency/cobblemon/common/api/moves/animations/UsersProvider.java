package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.moves.animations

import net.minecraft.world.entity.Entity

public class UsersProvider(users: List<Entity>) : EntityProvider {
   public open val entities: List<Entity>

   init {
      this.entities = users;
   }

   public constructor(vararg users: Entity) : this(ArraysKt.toList(users))}
