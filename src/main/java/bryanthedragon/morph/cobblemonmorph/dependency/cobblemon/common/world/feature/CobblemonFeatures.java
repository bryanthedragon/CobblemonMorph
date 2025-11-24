/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  kotlin.Metadata
 *  kotlin.jvm.JvmField
 *  kotlin.jvm.internal.Intrinsics
 *  net.minecraft.core.Registry
 *  net.minecraft.core.registries.BuiltInRegistries
 *  net.minecraft.core.registries.Registries
 *  net.minecraft.resources.ResourceKey
 *  net.minecraft.world.level.levelgen.feature.Feature
 *  org.jetbrains.annotations.NotNull
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.world.feature;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.platform.PlatformRegistry;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.world.feature.ApricornTreeFeature;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.world.feature.BerryGroveFeature;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.world.feature.MintBlockFeature;
import kotlin.Metadata;
import kotlin.jvm.JvmField;
import kotlin.jvm.internal.Intrinsics;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.levelgen.feature.Feature;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000,\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\r\b\u00c6\u0002\u0018\u000022\u0012\u000e\u0012\f\u0012\b\u0012\u0006\u0012\u0002\b\u00030\u00030\u0002\u0012\u0014\u0012\u0012\u0012\u000e\u0012\f\u0012\b\u0012\u0006\u0012\u0002\b\u00030\u00030\u00020\u0004\u0012\b\u0012\u0006\u0012\u0002\b\u00030\u00030\u0001B\t\b\u0002\u00a2\u0006\u0004\b\u0016\u0010\u0017R\u0014\u0010\u0006\u001a\u00020\u00058\u0006X\u0087\u0004\u00a2\u0006\u0006\n\u0004\b\u0006\u0010\u0007R\u0014\u0010\t\u001a\u00020\b8\u0006X\u0087\u0004\u00a2\u0006\u0006\n\u0004\b\t\u0010\nR\u0014\u0010\f\u001a\u00020\u000b8\u0006X\u0087\u0004\u00a2\u0006\u0006\n\u0004\b\f\u0010\rR$\u0010\u000e\u001a\f\u0012\b\u0012\u0006\u0012\u0002\b\u00030\u00030\u00028\u0016X\u0096\u0004\u00a2\u0006\f\n\u0004\b\u000e\u0010\u000f\u001a\u0004\b\u0010\u0010\u0011R*\u0010\u0012\u001a\u0012\u0012\u000e\u0012\f\u0012\b\u0012\u0006\u0012\u0002\b\u00030\u00030\u00020\u00048\u0016X\u0096\u0004\u00a2\u0006\f\n\u0004\b\u0012\u0010\u0013\u001a\u0004\b\u0014\u0010\u0015\u00a8\u0006\u0018"}, d2={"Lcom/cobblemon/mod/common/world/feature/CobblemonFeatures;", "Lcom/cobblemon/mod/common/platform/PlatformRegistry;", "Lnet/minecraft/core/Registry;", "Lnet/minecraft/world/level/levelgen/feature/Feature;", "Lnet/minecraft/resources/ResourceKey;", "Lcom/cobblemon/mod/common/world/feature/ApricornTreeFeature;", "APRICORN_TREE_FEATURE", "Lcom/cobblemon/mod/common/world/feature/ApricornTreeFeature;", "Lcom/cobblemon/mod/common/world/feature/BerryGroveFeature;", "BERRY_GROVE_FEATURE", "Lcom/cobblemon/mod/common/world/feature/BerryGroveFeature;", "Lcom/cobblemon/mod/common/world/feature/MintBlockFeature;", "MINT_FEATURE", "Lcom/cobblemon/mod/common/world/feature/MintBlockFeature;", "registry", "Lnet/minecraft/core/Registry;", "getRegistry", "()Lnet/minecraft/core/Registry;", "registryKey", "Lnet/minecraft/resources/ResourceKey;", "getRegistryKey", "()Lnet/minecraft/resources/ResourceKey;", "<init>", "()V", "common"})
public final class CobblemonFeatures
extends PlatformRegistry<Registry<Feature<?>>, ResourceKey<Registry<Feature<?>>>, Feature<?>> {
    @NotNull
    public static final CobblemonFeatures INSTANCE = new CobblemonFeatures();
    @NotNull
    private static final Registry<Feature<?>> registry;
    @NotNull
    private static final ResourceKey<Registry<Feature<?>>> registryKey;
    @JvmField
    @NotNull
    public static final ApricornTreeFeature APRICORN_TREE_FEATURE;
    @JvmField
    @NotNull
    public static final MintBlockFeature MINT_FEATURE;
    @JvmField
    @NotNull
    public static final BerryGroveFeature BERRY_GROVE_FEATURE;

    private CobblemonFeatures() {
    }

    @Override
    @NotNull
    public Registry<Feature<?>> getRegistry() {
        return registry;
    }

    @Override
    @NotNull
    public ResourceKey<Registry<Feature<?>>> getRegistryKey() {
        return registryKey;
    }

    static {
        Registry registry = BuiltInRegistries.f_256810_;
        Intrinsics.checkNotNullExpressionValue((Object)registry, (String)"FEATURE");
        CobblemonFeatures.registry = registry;
        ResourceKey resourceKey = Registries.f_256833_;
        Intrinsics.checkNotNullExpressionValue((Object)resourceKey, (String)"FEATURE");
        registryKey = resourceKey;
        APRICORN_TREE_FEATURE = INSTANCE.create("apricorn_tree", new ApricornTreeFeature());
        MINT_FEATURE = INSTANCE.create("mint", new MintBlockFeature());
        BERRY_GROVE_FEATURE = INSTANCE.create("berry_grove", new BerryGroveFeature());
    }
}

