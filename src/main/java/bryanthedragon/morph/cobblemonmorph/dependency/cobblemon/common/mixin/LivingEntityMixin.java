package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.mixin;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.effect.ShoulderEffectRegistry;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin {
   @Inject(method = "onStatusEffectRemoved", at = @At("TAIL"))
   private void cobblemon$onStatusEffectRemoved(MobEffectInstance effect, CallbackInfo ci) {
      LivingEntity entity = (LivingEntity)this;
      if (entity instanceof ServerPlayer) {
         ShoulderEffectRegistry.INSTANCE.onEffectEnd((ServerPlayer)entity);
      }
   }
}
