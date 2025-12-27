package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.mixin.accessor;

import net.minecraft.world.entity.Entity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(Entity.class)
public interface AccessorEntity {
   @Accessor("standingEyeHeight")
   void standingEyeHeight(float standingEyeHeight);
}
