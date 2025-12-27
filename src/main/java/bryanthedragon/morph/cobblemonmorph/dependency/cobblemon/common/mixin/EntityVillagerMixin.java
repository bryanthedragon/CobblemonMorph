package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.mixin;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.villager.VillagerGatherableItems;
import java.util.Objects;
import net.minecraft.world.entity.npc.Villager;
import net.minecraft.world.entity.npc.VillagerProfession;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Villager.class)
public abstract class EntityVillagerMixin {
   @Inject(method = "canGather", at = @At("RETURN"), cancellable = true)
   private void cobblemon$canGather(ItemStack stack, CallbackInfoReturnable<Boolean> ci) {
      Villager villager = (Villager)this;
      if (!(Boolean)ci.getReturnValue()
         && Objects.equals(villager.m_7141_().m_35571_(), VillagerProfession.f_35590_)
         && villager.m_35311_().m_19183_(stack)
         && VillagerGatherableItems.INSTANCE.getVillagerGatherableItems().contains(stack.m_41720_())) {
         ci.setReturnValue(true);
      }
   }
}
