package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.mixin;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.CobblemonItems;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.monster.piglin.Piglin;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Piglin.class)
public abstract class PiglinEntityMixin {
   @Inject(method = "equipToOffHand", at = @At("HEAD"), cancellable = true)
   public void cobblemon$isValidBarteringItem(ItemStack stack, CallbackInfo ci) {
      Piglin entity = (Piglin)this;
      if (stack.m_150930_(CobblemonItems.RELIC_COIN_POUCH)) {
         entity.m_8061_(EquipmentSlot.OFFHAND, stack);
         entity.m_21508_(EquipmentSlot.OFFHAND);
         ci.cancel();
      }
   }
}
