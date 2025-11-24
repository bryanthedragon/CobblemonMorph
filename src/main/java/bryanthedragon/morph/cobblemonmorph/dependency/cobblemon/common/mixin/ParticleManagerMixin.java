/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.ImmutableList
 *  net.minecraft.client.particle.ParticleEngine
 *  net.minecraft.client.particle.ParticleRenderType
 *  org.spongepowered.asm.mixin.Final
 *  org.spongepowered.asm.mixin.Mixin
 *  org.spongepowered.asm.mixin.Mutable
 *  org.spongepowered.asm.mixin.Shadow
 *  org.spongepowered.asm.mixin.injection.At
 *  org.spongepowered.asm.mixin.injection.Inject
 *  org.spongepowered.asm.mixin.injection.callback.CallbackInfo
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.mixin;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.snowstorm.ParticleMaterials;
import com.google.common.collect.ImmutableList;
import java.util.List;
import net.minecraft.client.particle.ParticleEngine;
import net.minecraft.client.particle.ParticleRenderType;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value={ParticleEngine.class})
final class ParticleManagerMixin {
    @Mutable
    @Final
    @Shadow
    private static List<ParticleRenderType> f_107288_;

    ParticleManagerMixin() {
    }

    @Inject(at={@At(value="RETURN")}, method={"<clinit>"})
    private static void lodestone$addTypes(CallbackInfo ci) {
        f_107288_ = ImmutableList.builder().addAll(f_107288_).add((Object[])new ParticleRenderType[]{ParticleMaterials.INSTANCE.getADD(), ParticleMaterials.INSTANCE.getALPHA(), ParticleMaterials.INSTANCE.getBLEND(), ParticleMaterials.INSTANCE.getOPAQUE()}).build();
    }
}

