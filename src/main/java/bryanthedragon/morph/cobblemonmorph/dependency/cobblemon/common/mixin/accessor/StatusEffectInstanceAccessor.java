package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.mixin.accessor;

import net.minecraft.world.effect.MobEffectInstance;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(MobEffectInstance.class)
public interface StatusEffectInstanceAccessor {
   @Accessor
   void setAmplifier(int amplifier);

   @Accessor
   void setShowParticles(boolean showParticles);

   @Accessor
   void setShowIcon(boolean showIcon);
}
