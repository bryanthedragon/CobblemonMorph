/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.color.block.BlockColors
 *  net.minecraft.client.renderer.block.model.BlockModel
 *  net.minecraft.client.resources.model.ModelBakery
 *  net.minecraft.client.resources.model.ModelResourceLocation
 *  net.minecraft.client.resources.model.UnbakedModel
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.util.profiling.ProfilerFiller
 *  org.slf4j.Logger
 *  org.spongepowered.asm.mixin.Final
 *  org.spongepowered.asm.mixin.Mixin
 *  org.spongepowered.asm.mixin.Shadow
 *  org.spongepowered.asm.mixin.injection.At
 *  org.spongepowered.asm.mixin.injection.Inject
 *  org.spongepowered.asm.mixin.injection.callback.CallbackInfo
 */
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

@Mixin(value={ModelBakery.class})
public abstract class ModelLoaderMixin {
    @Final
    @Shadow
    private Map<ResourceLocation, UnbakedModel> f_119212_;
    @Final
    @Shadow
    private static Logger f_119235_;

    @Shadow
    protected abstract void m_119306_(ModelResourceLocation var1);


    @Inject(method={"<init>"}, at={@At(value="TAIL")})
    @SuppressWarnings("rawtypes")
    public void init(BlockColors blockColors, ProfilerFiller profiler, Map jsonUnbakedModels, Map blockStates, CallbackInfo ci) {
        CobblemonBakingOverrides.INSTANCE.getModels().forEach(bakingOverride -> {
            try {
                this.f_119212_.put((ResourceLocation)bakingOverride.getModelIdentifier(), (UnbakedModel)this.m_119364_(bakingOverride.getModelLocation()));
                this.m_119306_(bakingOverride.getModelIdentifier());
            }
            catch (IOException e) {
                f_119235_.error("Error loading a Cobblemon BakedModel:", (Throwable)e);
                throw new RuntimeException(e);
            }
        });
    }

    @Shadow
    private BlockModel m_119364_(ResourceLocation id) throws IOException {
        return null;
    }
}

