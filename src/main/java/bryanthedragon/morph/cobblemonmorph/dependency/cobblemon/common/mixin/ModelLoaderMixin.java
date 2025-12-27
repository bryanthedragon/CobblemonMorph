package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.mixin;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.CobblemonBakingOverrides;
import java.io.IOException;
import java.util.Map;
import net.minecraft.client.color.block.BlockColors;
import net.minecraft.client.renderer.block.model.BlockModel;
import net.minecraft.client.resources.model.ModelBakery;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.client.resources.model.UnbakedModel;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.profiling.ProfilerFiller;
import org.slf4j.Logger;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ModelBakery.class)
public abstract class ModelLoaderMixin {
   @Final
   @Shadow
   private Map<ResourceLocation, UnbakedModel> f_119212_;
   @Final
   @Shadow
   private static Logger f_119235_;

   @Shadow
   protected abstract void m_119306_(ModelResourceLocation modelId);

   @Inject(method = "<init>", at = @At("TAIL"))
   public void init(BlockColors blockColors, ProfilerFiller profiler, Map jsonUnbakedModels, Map blockStates, CallbackInfo ci) {
      CobblemonBakingOverrides.INSTANCE.getModels().forEach(bakingOverride -> {
         try {
            this.f_119212_.put(bakingOverride.getModelIdentifier(), this.m_119364_(bakingOverride.getModelLocation()));
            this.m_119306_(bakingOverride.getModelIdentifier());
         } catch (IOException var3) {
            f_119235_.error("Error loading a Cobblemon BakedModel:", var3);
            throw new RuntimeException(var3);
         }
      });
   }

   @Shadow
   private BlockModel m_119364_(ResourceLocation id) throws IOException {
      return null;
   }
}
