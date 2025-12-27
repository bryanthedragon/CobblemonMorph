package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.mixin;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.CobblemonItems;
import net.minecraft.world.entity.monster.piglin.PiglinAi;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(PiglinAi.class)
public class PiglinBarterMixin {
   @Inject(method = "acceptsForBarter", at = @At("RETURN"), cancellable = true)
   private static void cobblemon$acceptsForBarter(ItemStack stack, CallbackInfoReturnable<Boolean> ci) {
      if (!(Boolean)ci.getReturnValue() && stack.m_150930_(CobblemonItems.RELIC_COIN_POUCH)) {
         ci.setReturnValue(true);
      }
   }
}
