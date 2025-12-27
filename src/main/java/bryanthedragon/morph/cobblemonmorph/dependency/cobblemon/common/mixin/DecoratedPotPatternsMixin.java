package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.mixin;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.sherds.CobblemonSherds;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.entity.DecoratedPotPatterns;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(DecoratedPotPatterns.class)
public abstract class DecoratedPotPatternsMixin {
   @Inject(method = "fromSherd", at = @At("HEAD"), cancellable = true)
   private static void cobblemon$getCobblemonSherdTexture(Item sherd, CallbackInfoReturnable<ResourceKey<String>> cir) {
      if (CobblemonSherds.INSTANCE.getSherdToPattern().containsKey(sherd)) {
         cir.setReturnValue(CobblemonSherds.INSTANCE.getSherdToPattern().get(sherd));
      }
   }
}
