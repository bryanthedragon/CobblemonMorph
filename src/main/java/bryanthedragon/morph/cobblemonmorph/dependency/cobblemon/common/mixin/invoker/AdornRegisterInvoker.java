/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  juuxel.adorn.block.variant.BlockVariantSet
 *  juuxel.adorn.block.variant.BlockVariantSets
 *  org.spongepowered.asm.mixin.Final
 *  org.spongepowered.asm.mixin.Mixin
 *  org.spongepowered.asm.mixin.Shadow
 *  org.spongepowered.asm.mixin.injection.At
 *  org.spongepowered.asm.mixin.injection.Inject
 *  org.spongepowered.asm.mixin.injection.callback.CallbackInfo
 */
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

@Mixin(value={BlockVariantSets.class})
public abstract class AdornRegisterInvoker {
    @Final
    @Shadow(remap=false)
    private static List<BlockVariantSet> variantSets;

    @Inject(method={"register()V"}, at={@At(value="HEAD")}, remap=false)
    void register(CallbackInfo ci) {
        variantSets.add(AdornCompatibility.INSTANCE);
    }
}

