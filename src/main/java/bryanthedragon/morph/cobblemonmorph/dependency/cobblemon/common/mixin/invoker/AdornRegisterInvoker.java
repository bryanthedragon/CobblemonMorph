package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.mixin.invoker;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.integration.adorn.AdornCompatibility;
import java.util.List;
import juuxel.adorn.block.variant.BlockVariantSet;
import juuxel.adorn.block.variant.BlockVariantSets;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(BlockVariantSets.class)
public abstract class AdornRegisterInvoker {
   @Final
   @Shadow(remap = false)
   private static List<BlockVariantSet> variantSets;

   @Inject(method = "register()V", at = @At("HEAD"), remap = false)
   void register(CallbackInfo ci) {
      variantSets.add(AdornCompatibility.INSTANCE);
   }
}
