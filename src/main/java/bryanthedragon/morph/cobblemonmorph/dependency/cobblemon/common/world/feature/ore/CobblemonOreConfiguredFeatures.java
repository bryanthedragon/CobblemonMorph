/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  kotlin.Metadata
 *  kotlin.jvm.internal.Intrinsics
 *  net.minecraft.data.worldgen.features.FeatureUtils
 *  net.minecraft.resources.ResourceKey
 *  net.minecraft.world.level.levelgen.feature.ConfiguredFeature
 *  org.jetbrains.annotations.NotNull
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.world.feature.ore;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.minecraft.data.worldgen.features.FeatureUtils;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000\u001a\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u001f\b\u00c6\u0002\u0018\u00002\u00020\u0001B\t\b\u0002\u00a2\u0006\u0004\b\"\u0010#J%\u0010\u0006\u001a\u0010\u0012\f\u0012\n\u0012\u0002\b\u0003\u0012\u0002\b\u00030\u00050\u00042\u0006\u0010\u0003\u001a\u00020\u0002H\u0002\u00a2\u0006\u0004\b\u0006\u0010\u0007R%\u0010\b\u001a\u0010\u0012\f\u0012\n\u0012\u0002\b\u0003\u0012\u0002\b\u00030\u00050\u00048\u0006\u00a2\u0006\f\n\u0004\b\b\u0010\t\u001a\u0004\b\n\u0010\u000bR%\u0010\f\u001a\u0010\u0012\f\u0012\n\u0012\u0002\b\u0003\u0012\u0002\b\u00030\u00050\u00048\u0006\u00a2\u0006\f\n\u0004\b\f\u0010\t\u001a\u0004\b\r\u0010\u000bR%\u0010\u000e\u001a\u0010\u0012\f\u0012\n\u0012\u0002\b\u0003\u0012\u0002\b\u00030\u00050\u00048\u0006\u00a2\u0006\f\n\u0004\b\u000e\u0010\t\u001a\u0004\b\u000f\u0010\u000bR%\u0010\u0010\u001a\u0010\u0012\f\u0012\n\u0012\u0002\b\u0003\u0012\u0002\b\u00030\u00050\u00048\u0006\u00a2\u0006\f\n\u0004\b\u0010\u0010\t\u001a\u0004\b\u0011\u0010\u000bR%\u0010\u0012\u001a\u0010\u0012\f\u0012\n\u0012\u0002\b\u0003\u0012\u0002\b\u00030\u00050\u00048\u0006\u00a2\u0006\f\n\u0004\b\u0012\u0010\t\u001a\u0004\b\u0013\u0010\u000bR%\u0010\u0014\u001a\u0010\u0012\f\u0012\n\u0012\u0002\b\u0003\u0012\u0002\b\u00030\u00050\u00048\u0006\u00a2\u0006\f\n\u0004\b\u0014\u0010\t\u001a\u0004\b\u0015\u0010\u000bR%\u0010\u0016\u001a\u0010\u0012\f\u0012\n\u0012\u0002\b\u0003\u0012\u0002\b\u00030\u00050\u00048\u0006\u00a2\u0006\f\n\u0004\b\u0016\u0010\t\u001a\u0004\b\u0017\u0010\u000bR%\u0010\u0018\u001a\u0010\u0012\f\u0012\n\u0012\u0002\b\u0003\u0012\u0002\b\u00030\u00050\u00048\u0006\u00a2\u0006\f\n\u0004\b\u0018\u0010\t\u001a\u0004\b\u0019\u0010\u000bR%\u0010\u001a\u001a\u0010\u0012\f\u0012\n\u0012\u0002\b\u0003\u0012\u0002\b\u00030\u00050\u00048\u0006\u00a2\u0006\f\n\u0004\b\u001a\u0010\t\u001a\u0004\b\u001b\u0010\u000bR%\u0010\u001c\u001a\u0010\u0012\f\u0012\n\u0012\u0002\b\u0003\u0012\u0002\b\u00030\u00050\u00048\u0006\u00a2\u0006\f\n\u0004\b\u001c\u0010\t\u001a\u0004\b\u001d\u0010\u000bR%\u0010\u001e\u001a\u0010\u0012\f\u0012\n\u0012\u0002\b\u0003\u0012\u0002\b\u00030\u00050\u00048\u0006\u00a2\u0006\f\n\u0004\b\u001e\u0010\t\u001a\u0004\b\u001f\u0010\u000bR%\u0010 \u001a\u0010\u0012\f\u0012\n\u0012\u0002\b\u0003\u0012\u0002\b\u00030\u00050\u00048\u0006\u00a2\u0006\f\n\u0004\b \u0010\t\u001a\u0004\b!\u0010\u000b\u00a8\u0006$"}, d2={"Lcom/cobblemon/mod/common/world/feature/ore/CobblemonOreConfiguredFeatures;", "", "", "id", "Lnet/minecraft/resources/ResourceKey;", "Lnet/minecraft/world/level/levelgen/feature/ConfiguredFeature;", "of", "(Ljava/lang/String;)Lnet/minecraft/resources/ResourceKey;", "ORE_DAWN_STONE", "Lnet/minecraft/resources/ResourceKey;", "getORE_DAWN_STONE", "()Lnet/minecraft/resources/ResourceKey;", "ORE_DUSK_STONE", "getORE_DUSK_STONE", "ORE_FIRE_STONE", "getORE_FIRE_STONE", "ORE_FIRE_STONE_NETHER", "getORE_FIRE_STONE_NETHER", "ORE_ICE_STONE", "getORE_ICE_STONE", "ORE_LEAF_STONE", "getORE_LEAF_STONE", "ORE_MOON_STONE", "getORE_MOON_STONE", "ORE_MOON_STONE_DRIPSTONE", "getORE_MOON_STONE_DRIPSTONE", "ORE_SHINY_STONE", "getORE_SHINY_STONE", "ORE_SUN_STONE", "getORE_SUN_STONE", "ORE_THUNDER_STONE", "getORE_THUNDER_STONE", "ORE_WATER_STONE", "getORE_WATER_STONE", "<init>", "()V", "common"})
public final class CobblemonOreConfiguredFeatures {
    @NotNull
    public static final CobblemonOreConfiguredFeatures INSTANCE = new CobblemonOreConfiguredFeatures();
    @NotNull
    private static final ResourceKey<ConfiguredFeature<?, ?>> ORE_DAWN_STONE = INSTANCE.of("dawn_stone");
    @NotNull
    private static final ResourceKey<ConfiguredFeature<?, ?>> ORE_DUSK_STONE = INSTANCE.of("dusk_stone");
    @NotNull
    private static final ResourceKey<ConfiguredFeature<?, ?>> ORE_FIRE_STONE = INSTANCE.of("fire_stone");
    @NotNull
    private static final ResourceKey<ConfiguredFeature<?, ?>> ORE_FIRE_STONE_NETHER = INSTANCE.of("nether_fire_stone");
    @NotNull
    private static final ResourceKey<ConfiguredFeature<?, ?>> ORE_ICE_STONE = INSTANCE.of("ice_stone");
    @NotNull
    private static final ResourceKey<ConfiguredFeature<?, ?>> ORE_LEAF_STONE = INSTANCE.of("leaf_stone");
    @NotNull
    private static final ResourceKey<ConfiguredFeature<?, ?>> ORE_MOON_STONE = INSTANCE.of("moon_stone");
    @NotNull
    private static final ResourceKey<ConfiguredFeature<?, ?>> ORE_MOON_STONE_DRIPSTONE = INSTANCE.of("dripstone_moon_stone");
    @NotNull
    private static final ResourceKey<ConfiguredFeature<?, ?>> ORE_SHINY_STONE = INSTANCE.of("shiny_stone");
    @NotNull
    private static final ResourceKey<ConfiguredFeature<?, ?>> ORE_SUN_STONE = INSTANCE.of("sun_stone");
    @NotNull
    private static final ResourceKey<ConfiguredFeature<?, ?>> ORE_THUNDER_STONE = INSTANCE.of("thunder_stone");
    @NotNull
    private static final ResourceKey<ConfiguredFeature<?, ?>> ORE_WATER_STONE = INSTANCE.of("water_stone");

    private CobblemonOreConfiguredFeatures() {
    }

    @NotNull
    public final ResourceKey<ConfiguredFeature<?, ?>> getORE_DAWN_STONE() {
        return ORE_DAWN_STONE;
    }

    @NotNull
    public final ResourceKey<ConfiguredFeature<?, ?>> getORE_DUSK_STONE() {
        return ORE_DUSK_STONE;
    }

    @NotNull
    public final ResourceKey<ConfiguredFeature<?, ?>> getORE_FIRE_STONE() {
        return ORE_FIRE_STONE;
    }

    @NotNull
    public final ResourceKey<ConfiguredFeature<?, ?>> getORE_FIRE_STONE_NETHER() {
        return ORE_FIRE_STONE_NETHER;
    }

    @NotNull
    public final ResourceKey<ConfiguredFeature<?, ?>> getORE_ICE_STONE() {
        return ORE_ICE_STONE;
    }

    @NotNull
    public final ResourceKey<ConfiguredFeature<?, ?>> getORE_LEAF_STONE() {
        return ORE_LEAF_STONE;
    }

    @NotNull
    public final ResourceKey<ConfiguredFeature<?, ?>> getORE_MOON_STONE() {
        return ORE_MOON_STONE;
    }

    @NotNull
    public final ResourceKey<ConfiguredFeature<?, ?>> getORE_MOON_STONE_DRIPSTONE() {
        return ORE_MOON_STONE_DRIPSTONE;
    }

    @NotNull
    public final ResourceKey<ConfiguredFeature<?, ?>> getORE_SHINY_STONE() {
        return ORE_SHINY_STONE;
    }

    @NotNull
    public final ResourceKey<ConfiguredFeature<?, ?>> getORE_SUN_STONE() {
        return ORE_SUN_STONE;
    }

    @NotNull
    public final ResourceKey<ConfiguredFeature<?, ?>> getORE_THUNDER_STONE() {
        return ORE_THUNDER_STONE;
    }

    @NotNull
    public final ResourceKey<ConfiguredFeature<?, ?>> getORE_WATER_STONE() {
        return ORE_WATER_STONE;
    }

    private final ResourceKey<ConfiguredFeature<?, ?>> of(String id) {
        ResourceKey resourceKey = FeatureUtils.m_255087_((String)("cobblemon:ore/" + id));
        Intrinsics.checkNotNullExpressionValue((Object)resourceKey, (String)"of(\"${Cobblemon.MODID}:ore/$id\")");
        return resourceKey;
    }
}

