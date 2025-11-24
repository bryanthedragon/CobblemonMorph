/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  kotlin.Metadata
 *  kotlin.jvm.JvmField
 *  kotlin.jvm.internal.Intrinsics
 *  net.minecraft.data.worldgen.features.FeatureUtils
 *  net.minecraft.resources.ResourceKey
 *  net.minecraft.world.level.levelgen.feature.ConfiguredFeature
 *  org.jetbrains.annotations.NotNull
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.world.feature;

import kotlin.Metadata;
import kotlin.jvm.JvmField;
import kotlin.jvm.internal.Intrinsics;
import net.minecraft.data.worldgen.features.FeatureUtils;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000\u001a\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0012\b\u00c6\u0002\u0018\u00002\u00020\u0001B\t\b\u0002\u00a2\u0006\u0004\b\u0015\u0010\u0016J%\u0010\u0006\u001a\u0010\u0012\f\u0012\n\u0012\u0002\b\u0003\u0012\u0002\b\u00030\u00050\u00042\u0006\u0010\u0003\u001a\u00020\u0002H\u0002\u00a2\u0006\u0004\b\u0006\u0010\u0007R\"\u0010\b\u001a\u0010\u0012\f\u0012\n\u0012\u0002\b\u0003\u0012\u0002\b\u00030\u00050\u00048\u0006X\u0087\u0004\u00a2\u0006\u0006\n\u0004\b\b\u0010\tR\"\u0010\n\u001a\u0010\u0012\f\u0012\n\u0012\u0002\b\u0003\u0012\u0002\b\u00030\u00050\u00048\u0006X\u0087\u0004\u00a2\u0006\u0006\n\u0004\b\n\u0010\tR\"\u0010\u000b\u001a\u0010\u0012\f\u0012\n\u0012\u0002\b\u0003\u0012\u0002\b\u00030\u00050\u00048\u0006X\u0087\u0004\u00a2\u0006\u0006\n\u0004\b\u000b\u0010\tR\"\u0010\f\u001a\u0010\u0012\f\u0012\n\u0012\u0002\b\u0003\u0012\u0002\b\u00030\u00050\u00048\u0006X\u0087\u0004\u00a2\u0006\u0006\n\u0004\b\f\u0010\tR\"\u0010\r\u001a\u0010\u0012\f\u0012\n\u0012\u0002\b\u0003\u0012\u0002\b\u00030\u00050\u00048\u0006X\u0087\u0004\u00a2\u0006\u0006\n\u0004\b\r\u0010\tR\"\u0010\u000e\u001a\u0010\u0012\f\u0012\n\u0012\u0002\b\u0003\u0012\u0002\b\u00030\u00050\u00048\u0006X\u0087\u0004\u00a2\u0006\u0006\n\u0004\b\u000e\u0010\tR\"\u0010\u000f\u001a\u0010\u0012\f\u0012\n\u0012\u0002\b\u0003\u0012\u0002\b\u00030\u00050\u00048\u0006X\u0087\u0004\u00a2\u0006\u0006\n\u0004\b\u000f\u0010\tR\"\u0010\u0010\u001a\u0010\u0012\f\u0012\n\u0012\u0002\b\u0003\u0012\u0002\b\u00030\u00050\u00048\u0006X\u0087\u0004\u00a2\u0006\u0006\n\u0004\b\u0010\u0010\tR\"\u0010\u0011\u001a\u0010\u0012\f\u0012\n\u0012\u0002\b\u0003\u0012\u0002\b\u00030\u00050\u00048\u0006X\u0087\u0004\u00a2\u0006\u0006\n\u0004\b\u0011\u0010\tR\"\u0010\u0012\u001a\u0010\u0012\f\u0012\n\u0012\u0002\b\u0003\u0012\u0002\b\u00030\u00050\u00048\u0006X\u0087\u0004\u00a2\u0006\u0006\n\u0004\b\u0012\u0010\tR\"\u0010\u0013\u001a\u0010\u0012\f\u0012\n\u0012\u0002\b\u0003\u0012\u0002\b\u00030\u00050\u00048\u0006X\u0087\u0004\u00a2\u0006\u0006\n\u0004\b\u0013\u0010\tR\"\u0010\u0014\u001a\u0010\u0012\f\u0012\n\u0012\u0002\b\u0003\u0012\u0002\b\u00030\u00050\u00048\u0006X\u0087\u0004\u00a2\u0006\u0006\n\u0004\b\u0014\u0010\t\u00a8\u0006\u0017"}, d2={"Lcom/cobblemon/mod/common/world/feature/CobblemonConfiguredFeatures;", "", "", "id", "Lnet/minecraft/resources/ResourceKey;", "Lnet/minecraft/world/level/levelgen/feature/ConfiguredFeature;", "of", "(Ljava/lang/String;)Lnet/minecraft/resources/ResourceKey;", "BERRY_GROVE_KEY", "Lnet/minecraft/resources/ResourceKey;", "BIG_ROOTS_KEY", "BLACK_APRICORN_TREE_KEY", "BLUE_APRICORN_TREE_KEY", "GREEN_APRICORN_TREE_KEY", "MEDICINAL_LEEKS_KEY", "MINTS_KEY", "PINK_APRICORN_TREE_KEY", "RED_APRICORN_TREE_KEY", "REVIVAL_HERBS_KEY", "WHITE_APRICORN_TREE_KEY", "YELLOW_APRICORN_TREE_KEY", "<init>", "()V", "common"})
public final class CobblemonConfiguredFeatures {
    @NotNull
    public static final CobblemonConfiguredFeatures INSTANCE = new CobblemonConfiguredFeatures();
    @JvmField
    @NotNull
    public static final ResourceKey<ConfiguredFeature<?, ?>> BLACK_APRICORN_TREE_KEY = INSTANCE.of("black_apricorn_tree");
    @JvmField
    @NotNull
    public static final ResourceKey<ConfiguredFeature<?, ?>> BLUE_APRICORN_TREE_KEY = INSTANCE.of("blue_apricorn_tree");
    @JvmField
    @NotNull
    public static final ResourceKey<ConfiguredFeature<?, ?>> GREEN_APRICORN_TREE_KEY = INSTANCE.of("green_apricorn_tree");
    @JvmField
    @NotNull
    public static final ResourceKey<ConfiguredFeature<?, ?>> PINK_APRICORN_TREE_KEY = INSTANCE.of("pink_apricorn_tree");
    @JvmField
    @NotNull
    public static final ResourceKey<ConfiguredFeature<?, ?>> RED_APRICORN_TREE_KEY = INSTANCE.of("red_apricorn_tree");
    @JvmField
    @NotNull
    public static final ResourceKey<ConfiguredFeature<?, ?>> WHITE_APRICORN_TREE_KEY = INSTANCE.of("white_apricorn_tree");
    @JvmField
    @NotNull
    public static final ResourceKey<ConfiguredFeature<?, ?>> YELLOW_APRICORN_TREE_KEY = INSTANCE.of("yellow_apricorn_tree");
    @JvmField
    @NotNull
    public static final ResourceKey<ConfiguredFeature<?, ?>> MINTS_KEY = INSTANCE.of("mints");
    @JvmField
    @NotNull
    public static final ResourceKey<ConfiguredFeature<?, ?>> MEDICINAL_LEEKS_KEY = INSTANCE.of("medicinal_leek");
    @JvmField
    @NotNull
    public static final ResourceKey<ConfiguredFeature<?, ?>> BIG_ROOTS_KEY = INSTANCE.of("big_root");
    @JvmField
    @NotNull
    public static final ResourceKey<ConfiguredFeature<?, ?>> REVIVAL_HERBS_KEY = INSTANCE.of("revival_herb");
    @JvmField
    @NotNull
    public static final ResourceKey<ConfiguredFeature<?, ?>> BERRY_GROVE_KEY = INSTANCE.of("berry_groves");

    private CobblemonConfiguredFeatures() {
    }

    private final ResourceKey<ConfiguredFeature<?, ?>> of(String id) {
        ResourceKey resourceKey = FeatureUtils.m_255087_((String)("cobblemon:" + id));
        Intrinsics.checkNotNullExpressionValue((Object)resourceKey, (String)"of(\"${Cobblemon.MODID}:$id\")");
        return resourceKey;
    }
}

