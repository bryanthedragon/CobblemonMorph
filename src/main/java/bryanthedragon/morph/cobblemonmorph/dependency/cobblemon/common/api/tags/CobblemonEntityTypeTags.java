package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.tags

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.MiscUtilsKt
import net.minecraft.core.registries.Registries
import net.minecraft.tags.TagKey
import net.minecraft.world.entity.EntityType

public object CobblemonEntityTypeTags {
   public final val BOATS: TagKey<EntityType<*>> = INSTANCE.create("boats")
   public final val CANNOT_HAVE_NAME_TAG: TagKey<EntityType<*>> = INSTANCE.create("cannot_have_name_tag")

   private fun create(path: String): TagKey<EntityType<*>> {
      return TagKey.m_203882_(Registries.f_256939_, MiscUtilsKt.cobblemonResource(path));
   }
}
