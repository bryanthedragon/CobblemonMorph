/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  kotlin.Metadata
 *  kotlin.jvm.internal.Intrinsics
 *  net.minecraft.core.Registry
 *  net.minecraft.core.particles.ParticleType
 *  net.minecraft.core.registries.BuiltInRegistries
 *  net.minecraft.core.registries.Registries
 *  net.minecraft.resources.ResourceKey
 *  org.jetbrains.annotations.NotNull
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.particle;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.particle.SnowstormParticleType;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.platform.PlatformRegistry;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.minecraft.core.Registry;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000\u001c\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u000f\b\u00c6\u0002\u0018\u000022\u0012\u000e\u0012\f\u0012\b\u0012\u0006\u0012\u0002\b\u00030\u00030\u0002\u0012\u0014\u0012\u0012\u0012\u000e\u0012\f\u0012\b\u0012\u0006\u0012\u0002\b\u00030\u00030\u00020\u0004\u0012\b\u0012\u0006\u0012\u0002\b\u00030\u00030\u0001B\t\b\u0002\u00a2\u0006\u0004\b\u0012\u0010\u0013R\u0017\u0010\u0006\u001a\u00020\u00058\u0006\u00a2\u0006\f\n\u0004\b\u0006\u0010\u0007\u001a\u0004\b\b\u0010\tR$\u0010\n\u001a\f\u0012\b\u0012\u0006\u0012\u0002\b\u00030\u00030\u00028\u0016X\u0096\u0004\u00a2\u0006\f\n\u0004\b\n\u0010\u000b\u001a\u0004\b\f\u0010\rR*\u0010\u000e\u001a\u0012\u0012\u000e\u0012\f\u0012\b\u0012\u0006\u0012\u0002\b\u00030\u00030\u00020\u00048\u0016X\u0096\u0004\u00a2\u0006\f\n\u0004\b\u000e\u0010\u000f\u001a\u0004\b\u0010\u0010\u0011\u00a8\u0006\u0014"}, d2={"Lcom/cobblemon/mod/common/particle/CobblemonParticles;", "Lcom/cobblemon/mod/common/platform/PlatformRegistry;", "Lnet/minecraft/core/Registry;", "Lnet/minecraft/core/particles/ParticleType;", "Lnet/minecraft/resources/ResourceKey;", "Lcom/cobblemon/mod/common/particle/SnowstormParticleType;", "SNOWSTORM_PARTICLE_TYPE", "Lcom/cobblemon/mod/common/particle/SnowstormParticleType;", "getSNOWSTORM_PARTICLE_TYPE", "()Lcom/cobblemon/mod/common/particle/SnowstormParticleType;", "registry", "Lnet/minecraft/core/Registry;", "getRegistry", "()Lnet/minecraft/core/Registry;", "registryKey", "Lnet/minecraft/resources/ResourceKey;", "getRegistryKey", "()Lnet/minecraft/resources/ResourceKey;", "<init>", "()V", "common"})
public final class CobblemonParticles
extends PlatformRegistry<Registry<ParticleType<?>>, ResourceKey<Registry<ParticleType<?>>>, ParticleType<?>> {
    @NotNull
    public static final CobblemonParticles INSTANCE = new CobblemonParticles();
    @NotNull
    private static final Registry<ParticleType<?>> registry;
    @NotNull
    private static final ResourceKey<Registry<ParticleType<?>>> registryKey;
    @NotNull
    private static final SnowstormParticleType SNOWSTORM_PARTICLE_TYPE;

    private CobblemonParticles() {
    }

    @Override
    @NotNull
    public Registry<ParticleType<?>> getRegistry() {
        return registry;
    }

    @Override
    @NotNull
    public ResourceKey<Registry<ParticleType<?>>> getRegistryKey() {
        return registryKey;
    }

    @NotNull
    public final SnowstormParticleType getSNOWSTORM_PARTICLE_TYPE() {
        return SNOWSTORM_PARTICLE_TYPE;
    }

    static {
        Registry registry = BuiltInRegistries.f_257034_;
        Intrinsics.checkNotNullExpressionValue((Object)registry, (String)"PARTICLE_TYPE");
        CobblemonParticles.registry = registry;
        ResourceKey resourceKey = Registries.f_256890_;
        Intrinsics.checkNotNullExpressionValue((Object)resourceKey, (String)"PARTICLE_TYPE");
        registryKey = resourceKey;
        SNOWSTORM_PARTICLE_TYPE = INSTANCE.create("snowstorm", new SnowstormParticleType());
    }
}

