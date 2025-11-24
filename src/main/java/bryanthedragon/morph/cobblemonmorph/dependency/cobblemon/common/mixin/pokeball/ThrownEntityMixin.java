/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.world.entity.Entity
 *  net.minecraft.world.entity.EntityType
 *  net.minecraft.world.entity.projectile.ThrowableProjectile
 *  net.minecraft.world.level.Level
 *  org.spongepowered.asm.mixin.Mixin
 *  org.spongepowered.asm.mixin.injection.At
 *  org.spongepowered.asm.mixin.injection.ModifyVariable
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.mixin.pokeball;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.entity.pokeball.WaterDragModifier;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.projectile.ThrowableProjectile;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(value={ThrowableProjectile.class})
public abstract class ThrownEntityMixin
extends Entity {
    public ThrownEntityMixin(EntityType<?> type, Level world) {
        super(type, world);
    }

    @ModifyVariable(method={"tick"}, at=@At(value="STORE"), ordinal=0)
    private float cobblemon$waterDragModifier(float value2) {
        if (this.m_20069_() && this instanceof WaterDragModifier) {
            return ((WaterDragModifier)((Object)this)).waterDrag();
        }
        return value2;
    }
}

