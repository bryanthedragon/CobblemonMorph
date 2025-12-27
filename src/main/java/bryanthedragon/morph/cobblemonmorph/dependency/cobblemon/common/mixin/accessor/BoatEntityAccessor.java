package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.mixin.accessor;

import net.minecraft.world.entity.vehicle.Boat;
import net.minecraft.world.entity.vehicle.Boat.Status;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(Boat.class)
public interface BoatEntityAccessor {
   @Accessor
   void setFallVelocity(double fallVelocity);

   @Accessor
   Status getLocation();
}
