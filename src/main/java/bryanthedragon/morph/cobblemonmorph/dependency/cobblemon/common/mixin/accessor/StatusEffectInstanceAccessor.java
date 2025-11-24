/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.world.effect.MobEffectInstance
 *  org.spongepowered.asm.mixin.Mixin
 *  org.spongepowered.asm.mixin.gen.Accessor
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.mixin.accessor;

import net.minecraft.world.effect.MobEffectInstance;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(value={MobEffectInstance.class})
public interface StatusEffectInstanceAccessor {
    @Accessor
    public void setAmplifier(int var1);

    @Accessor
    public void setShowParticles(boolean var1);

    @Accessor
    public void setShowIcon(boolean var1);
}

