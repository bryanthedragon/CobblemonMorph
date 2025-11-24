/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  kotlin.Metadata
 *  kotlin.jvm.internal.Intrinsics
 *  net.minecraft.data.worldgen.placement.PlacementUtils
 *  net.minecraft.resources.ResourceKey
 *  net.minecraft.tags.BiomeTags
 *  net.minecraft.tags.TagKey
 *  net.minecraft.world.level.biome.Biome
 *  net.minecraft.world.level.levelgen.GenerationStep$Decoration
 *  net.minecraft.world.level.levelgen.placement.PlacedFeature
 *  org.jetbrains.annotations.NotNull
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.world.feature;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.Cobblemon;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.tags.CobblemonBiomeTags;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.BiomeTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000\"\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b \b\u00c6\u0002\u0018\u00002\u00020\u0001B\t\b\u0002\u00a2\u0006\u0004\b'\u0010\nJ\u001d\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\u00050\u00042\u0006\u0010\u0003\u001a\u00020\u0002H\u0002\u00a2\u0006\u0004\b\u0006\u0010\u0007J\r\u0010\t\u001a\u00020\b\u00a2\u0006\u0004\b\t\u0010\nR\u001d\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\u00050\u00048\u0006\u00a2\u0006\f\n\u0004\b\u000b\u0010\f\u001a\u0004\b\r\u0010\u000eR\u001d\u0010\u000f\u001a\b\u0012\u0004\u0012\u00020\u00050\u00048\u0006\u00a2\u0006\f\n\u0004\b\u000f\u0010\f\u001a\u0004\b\u0010\u0010\u000eR\u001d\u0010\u0011\u001a\b\u0012\u0004\u0012\u00020\u00050\u00048\u0006\u00a2\u0006\f\n\u0004\b\u0011\u0010\f\u001a\u0004\b\u0012\u0010\u000eR\u001d\u0010\u0013\u001a\b\u0012\u0004\u0012\u00020\u00050\u00048\u0006\u00a2\u0006\f\n\u0004\b\u0013\u0010\f\u001a\u0004\b\u0014\u0010\u000eR\u001d\u0010\u0015\u001a\b\u0012\u0004\u0012\u00020\u00050\u00048\u0006\u00a2\u0006\f\n\u0004\b\u0015\u0010\f\u001a\u0004\b\u0016\u0010\u000eR\u001d\u0010\u0017\u001a\b\u0012\u0004\u0012\u00020\u00050\u00048\u0006\u00a2\u0006\f\n\u0004\b\u0017\u0010\f\u001a\u0004\b\u0018\u0010\u000eR\u001d\u0010\u0019\u001a\b\u0012\u0004\u0012\u00020\u00050\u00048\u0006\u00a2\u0006\f\n\u0004\b\u0019\u0010\f\u001a\u0004\b\u001a\u0010\u000eR\u001d\u0010\u001b\u001a\b\u0012\u0004\u0012\u00020\u00050\u00048\u0006\u00a2\u0006\f\n\u0004\b\u001b\u0010\f\u001a\u0004\b\u001c\u0010\u000eR\u001d\u0010\u001d\u001a\b\u0012\u0004\u0012\u00020\u00050\u00048\u0006\u00a2\u0006\f\n\u0004\b\u001d\u0010\f\u001a\u0004\b\u001e\u0010\u000eR\u001d\u0010\u001f\u001a\b\u0012\u0004\u0012\u00020\u00050\u00048\u0006\u00a2\u0006\f\n\u0004\b\u001f\u0010\f\u001a\u0004\b \u0010\u000eR\u001d\u0010!\u001a\b\u0012\u0004\u0012\u00020\u00050\u00048\u0006\u00a2\u0006\f\n\u0004\b!\u0010\f\u001a\u0004\b\"\u0010\u000eR\u001d\u0010#\u001a\b\u0012\u0004\u0012\u00020\u00050\u00048\u0006\u00a2\u0006\f\n\u0004\b#\u0010\f\u001a\u0004\b$\u0010\u000eR\u001d\u0010%\u001a\b\u0012\u0004\u0012\u00020\u00050\u00048\u0006\u00a2\u0006\f\n\u0004\b%\u0010\f\u001a\u0004\b&\u0010\u000e\u00a8\u0006("}, d2={"Lcom/cobblemon/mod/common/world/feature/CobblemonPlacedFeatures;", "", "", "id", "Lnet/minecraft/resources/ResourceKey;", "Lnet/minecraft/world/level/levelgen/placement/PlacedFeature;", "of", "(Ljava/lang/String;)Lnet/minecraft/resources/ResourceKey;", "", "register", "()V", "APRICORN_TREES", "Lnet/minecraft/resources/ResourceKey;", "getAPRICORN_TREES", "()Lnet/minecraft/resources/ResourceKey;", "BERRY_GROVE", "getBERRY_GROVE", "BIG_ROOT", "getBIG_ROOT", "BLACK_APRICORN_TREE_PLACED_FEATURE", "getBLACK_APRICORN_TREE_PLACED_FEATURE", "BLUE_APRICORN_TREE_PLACED_FEATURE", "getBLUE_APRICORN_TREE_PLACED_FEATURE", "GREEN_APRICORN_TREE_PLACED_FEATURE", "getGREEN_APRICORN_TREE_PLACED_FEATURE", "MEDICINAL_LEEK", "getMEDICINAL_LEEK", "MINTS", "getMINTS", "PINK_APRICORN_TREE_PLACED_FEATURE", "getPINK_APRICORN_TREE_PLACED_FEATURE", "RED_APRICORN_TREE_PLACED_FEATURE", "getRED_APRICORN_TREE_PLACED_FEATURE", "REVIVAL_HERB", "getREVIVAL_HERB", "WHITE_APRICORN_TREE_PLACED_FEATURE", "getWHITE_APRICORN_TREE_PLACED_FEATURE", "YELLOW_APRICORN_TREE_PLACED_FEATURE", "getYELLOW_APRICORN_TREE_PLACED_FEATURE", "<init>", "common"})
public final class CobblemonPlacedFeatures {
    @NotNull
    public static final CobblemonPlacedFeatures INSTANCE = new CobblemonPlacedFeatures();
    @NotNull
    private static final ResourceKey<PlacedFeature> BLACK_APRICORN_TREE_PLACED_FEATURE = INSTANCE.of("black_apricorn_tree");
    @NotNull
    private static final ResourceKey<PlacedFeature> BLUE_APRICORN_TREE_PLACED_FEATURE = INSTANCE.of("blue_apricorn_tree");
    @NotNull
    private static final ResourceKey<PlacedFeature> GREEN_APRICORN_TREE_PLACED_FEATURE = INSTANCE.of("green_apricorn_tree");
    @NotNull
    private static final ResourceKey<PlacedFeature> PINK_APRICORN_TREE_PLACED_FEATURE = INSTANCE.of("pink_apricorn_tree");
    @NotNull
    private static final ResourceKey<PlacedFeature> RED_APRICORN_TREE_PLACED_FEATURE = INSTANCE.of("red_apricorn_tree");
    @NotNull
    private static final ResourceKey<PlacedFeature> WHITE_APRICORN_TREE_PLACED_FEATURE = INSTANCE.of("white_apricorn_tree");
    @NotNull
    private static final ResourceKey<PlacedFeature> YELLOW_APRICORN_TREE_PLACED_FEATURE = INSTANCE.of("yellow_apricorn_tree");
    @NotNull
    private static final ResourceKey<PlacedFeature> APRICORN_TREES = INSTANCE.of("apricorn_trees");
    @NotNull
    private static final ResourceKey<PlacedFeature> MINTS = INSTANCE.of("mints");
    @NotNull
    private static final ResourceKey<PlacedFeature> MEDICINAL_LEEK = INSTANCE.of("medicinal_leek");
    @NotNull
    private static final ResourceKey<PlacedFeature> BIG_ROOT = INSTANCE.of("big_root");
    @NotNull
    private static final ResourceKey<PlacedFeature> REVIVAL_HERB = INSTANCE.of("revival_herb");
    @NotNull
    private static final ResourceKey<PlacedFeature> BERRY_GROVE = INSTANCE.of("berry_groves");

    private CobblemonPlacedFeatures() {
    }

    @NotNull
    public final ResourceKey<PlacedFeature> getBLACK_APRICORN_TREE_PLACED_FEATURE() {
        return BLACK_APRICORN_TREE_PLACED_FEATURE;
    }

    @NotNull
    public final ResourceKey<PlacedFeature> getBLUE_APRICORN_TREE_PLACED_FEATURE() {
        return BLUE_APRICORN_TREE_PLACED_FEATURE;
    }

    @NotNull
    public final ResourceKey<PlacedFeature> getGREEN_APRICORN_TREE_PLACED_FEATURE() {
        return GREEN_APRICORN_TREE_PLACED_FEATURE;
    }

    @NotNull
    public final ResourceKey<PlacedFeature> getPINK_APRICORN_TREE_PLACED_FEATURE() {
        return PINK_APRICORN_TREE_PLACED_FEATURE;
    }

    @NotNull
    public final ResourceKey<PlacedFeature> getRED_APRICORN_TREE_PLACED_FEATURE() {
        return RED_APRICORN_TREE_PLACED_FEATURE;
    }

    @NotNull
    public final ResourceKey<PlacedFeature> getWHITE_APRICORN_TREE_PLACED_FEATURE() {
        return WHITE_APRICORN_TREE_PLACED_FEATURE;
    }

    @NotNull
    public final ResourceKey<PlacedFeature> getYELLOW_APRICORN_TREE_PLACED_FEATURE() {
        return YELLOW_APRICORN_TREE_PLACED_FEATURE;
    }

    @NotNull
    public final ResourceKey<PlacedFeature> getAPRICORN_TREES() {
        return APRICORN_TREES;
    }

    @NotNull
    public final ResourceKey<PlacedFeature> getMINTS() {
        return MINTS;
    }

    @NotNull
    public final ResourceKey<PlacedFeature> getMEDICINAL_LEEK() {
        return MEDICINAL_LEEK;
    }

    @NotNull
    public final ResourceKey<PlacedFeature> getBIG_ROOT() {
        return BIG_ROOT;
    }

    @NotNull
    public final ResourceKey<PlacedFeature> getREVIVAL_HERB() {
        return REVIVAL_HERB;
    }

    @NotNull
    public final ResourceKey<PlacedFeature> getBERRY_GROVE() {
        return BERRY_GROVE;
    }

    public final void register() {
        Cobblemon.INSTANCE.getImplementation().addFeatureToWorldGen(APRICORN_TREES, GenerationStep.Decoration.VEGETAL_DECORATION, null);
        Cobblemon.INSTANCE.getImplementation().addFeatureToWorldGen(MINTS, GenerationStep.Decoration.VEGETAL_DECORATION, null);
        Cobblemon.INSTANCE.getImplementation().addFeatureToWorldGen(MEDICINAL_LEEK, GenerationStep.Decoration.VEGETAL_DECORATION, null);
        Cobblemon.INSTANCE.getImplementation().addFeatureToWorldGen(BIG_ROOT, GenerationStep.Decoration.VEGETAL_DECORATION, (TagKey<Biome>)BiomeTags.f_215817_);
        Cobblemon.INSTANCE.getImplementation().addFeatureToWorldGen(REVIVAL_HERB, GenerationStep.Decoration.VEGETAL_DECORATION, CobblemonBiomeTags.HAS_REVIVAL_HERBS);
        Cobblemon.INSTANCE.getImplementation().addFeatureToWorldGen(BERRY_GROVE, GenerationStep.Decoration.VEGETAL_DECORATION, (TagKey<Biome>)BiomeTags.f_215817_);
    }

    private final ResourceKey<PlacedFeature> of(String id) {
        ResourceKey resourceKey = PlacementUtils.m_255070_((String)("cobblemon:" + id));
        Intrinsics.checkNotNullExpressionValue((Object)resourceKey, (String)"of(\"${Cobblemon.MODID}:$id\")");
        return resourceKey;
    }
}

