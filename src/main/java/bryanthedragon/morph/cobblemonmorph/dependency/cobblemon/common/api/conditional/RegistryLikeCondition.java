package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.conditional

import net.minecraft.core.Registry

public interface RegistryLikeCondition<T> {
   public abstract fun fits(t: Any, registry: Registry<Any>): Boolean {
   }
}
