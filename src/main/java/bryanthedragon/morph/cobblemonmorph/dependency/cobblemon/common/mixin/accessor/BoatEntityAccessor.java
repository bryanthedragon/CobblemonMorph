/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.world.entity.vehicle.Boat
 *  net.minecraft.world.entity.vehicle.Boat$Status
 *  org.spongepowered.asm.mixin.Mixin
 *  org.spongepowered.asm.mixin.gen.Accessor
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.mixin.accessor;

import net.minecraft.world.entity.vehicle.Boat;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(value={Boat.class})
public interface BoatEntityAccessor {
    @Accessor
    public void setFallVelocity(double var1);

    @Accessor
    public Boat.Status getLocation();
}

