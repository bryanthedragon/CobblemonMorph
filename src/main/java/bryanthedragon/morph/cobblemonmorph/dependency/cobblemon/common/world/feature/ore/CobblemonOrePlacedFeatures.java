/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  kotlin.Metadata
 *  kotlin.jvm.internal.Intrinsics
 *  kotlin.jvm.internal.SourceDebugExtension
 *  net.minecraft.data.worldgen.placement.PlacementUtils
 *  net.minecraft.resources.ResourceKey
 *  net.minecraft.tags.TagKey
 *  net.minecraft.world.level.biome.Biome
 *  net.minecraft.world.level.levelgen.GenerationStep$Decoration
 *  net.minecraft.world.level.levelgen.placement.PlacedFeature
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.world.feature.ore;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.Cobblemon;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.tags.CobblemonBiomeTags;
import java.util.ArrayList;
import java.util.Collection;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000<\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\bX\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\b\u00c6\u0002\u0018\u00002\u00020\u0001:\u0001jB\t\b\u0002\u00a2\u0006\u0004\bi\u0010\rJ+\u0010\t\u001a\b\u0012\u0004\u0012\u00020\b0\u00072\u0006\u0010\u0003\u001a\u00020\u00022\f\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004H\u0002\u00a2\u0006\u0004\b\t\u0010\nJ\r\u0010\f\u001a\u00020\u000b\u00a2\u0006\u0004\b\f\u0010\rR\u001d\u0010\u000e\u001a\b\u0012\u0004\u0012\u00020\b0\u00078\u0006\u00a2\u0006\f\n\u0004\b\u000e\u0010\u000f\u001a\u0004\b\u0010\u0010\u0011R\u001d\u0010\u0012\u001a\b\u0012\u0004\u0012\u00020\b0\u00078\u0006\u00a2\u0006\f\n\u0004\b\u0012\u0010\u000f\u001a\u0004\b\u0013\u0010\u0011R\u001d\u0010\u0014\u001a\b\u0012\u0004\u0012\u00020\b0\u00078\u0006\u00a2\u0006\f\n\u0004\b\u0014\u0010\u000f\u001a\u0004\b\u0015\u0010\u0011R\u001d\u0010\u0016\u001a\b\u0012\u0004\u0012\u00020\b0\u00078\u0006\u00a2\u0006\f\n\u0004\b\u0016\u0010\u000f\u001a\u0004\b\u0017\u0010\u0011R\u001d\u0010\u0018\u001a\b\u0012\u0004\u0012\u00020\b0\u00078\u0006\u00a2\u0006\f\n\u0004\b\u0018\u0010\u000f\u001a\u0004\b\u0019\u0010\u0011R\u001d\u0010\u001a\u001a\b\u0012\u0004\u0012\u00020\b0\u00078\u0006\u00a2\u0006\f\n\u0004\b\u001a\u0010\u000f\u001a\u0004\b\u001b\u0010\u0011R\u001d\u0010\u001c\u001a\b\u0012\u0004\u0012\u00020\b0\u00078\u0006\u00a2\u0006\f\n\u0004\b\u001c\u0010\u000f\u001a\u0004\b\u001d\u0010\u0011R\u001d\u0010\u001e\u001a\b\u0012\u0004\u0012\u00020\b0\u00078\u0006\u00a2\u0006\f\n\u0004\b\u001e\u0010\u000f\u001a\u0004\b\u001f\u0010\u0011R\u001d\u0010 \u001a\b\u0012\u0004\u0012\u00020\b0\u00078\u0006\u00a2\u0006\f\n\u0004\b \u0010\u000f\u001a\u0004\b!\u0010\u0011R\u001d\u0010\"\u001a\b\u0012\u0004\u0012\u00020\b0\u00078\u0006\u00a2\u0006\f\n\u0004\b\"\u0010\u000f\u001a\u0004\b#\u0010\u0011R\u001d\u0010$\u001a\b\u0012\u0004\u0012\u00020\b0\u00078\u0006\u00a2\u0006\f\n\u0004\b$\u0010\u000f\u001a\u0004\b%\u0010\u0011R\u001d\u0010&\u001a\b\u0012\u0004\u0012\u00020\b0\u00078\u0006\u00a2\u0006\f\n\u0004\b&\u0010\u000f\u001a\u0004\b'\u0010\u0011R\u001d\u0010(\u001a\b\u0012\u0004\u0012\u00020\b0\u00078\u0006\u00a2\u0006\f\n\u0004\b(\u0010\u000f\u001a\u0004\b)\u0010\u0011R\u001d\u0010*\u001a\b\u0012\u0004\u0012\u00020\b0\u00078\u0006\u00a2\u0006\f\n\u0004\b*\u0010\u000f\u001a\u0004\b+\u0010\u0011R\u001d\u0010,\u001a\b\u0012\u0004\u0012\u00020\b0\u00078\u0006\u00a2\u0006\f\n\u0004\b,\u0010\u000f\u001a\u0004\b-\u0010\u0011R\u001d\u0010.\u001a\b\u0012\u0004\u0012\u00020\b0\u00078\u0006\u00a2\u0006\f\n\u0004\b.\u0010\u000f\u001a\u0004\b/\u0010\u0011R\u001d\u00100\u001a\b\u0012\u0004\u0012\u00020\b0\u00078\u0006\u00a2\u0006\f\n\u0004\b0\u0010\u000f\u001a\u0004\b1\u0010\u0011R\u001d\u00102\u001a\b\u0012\u0004\u0012\u00020\b0\u00078\u0006\u00a2\u0006\f\n\u0004\b2\u0010\u000f\u001a\u0004\b3\u0010\u0011R\u001d\u00104\u001a\b\u0012\u0004\u0012\u00020\b0\u00078\u0006\u00a2\u0006\f\n\u0004\b4\u0010\u000f\u001a\u0004\b5\u0010\u0011R\u001d\u00106\u001a\b\u0012\u0004\u0012\u00020\b0\u00078\u0006\u00a2\u0006\f\n\u0004\b6\u0010\u000f\u001a\u0004\b7\u0010\u0011R\u001d\u00108\u001a\b\u0012\u0004\u0012\u00020\b0\u00078\u0006\u00a2\u0006\f\n\u0004\b8\u0010\u000f\u001a\u0004\b9\u0010\u0011R\u001d\u0010:\u001a\b\u0012\u0004\u0012\u00020\b0\u00078\u0006\u00a2\u0006\f\n\u0004\b:\u0010\u000f\u001a\u0004\b;\u0010\u0011R\u001d\u0010<\u001a\b\u0012\u0004\u0012\u00020\b0\u00078\u0006\u00a2\u0006\f\n\u0004\b<\u0010\u000f\u001a\u0004\b=\u0010\u0011R\u001d\u0010>\u001a\b\u0012\u0004\u0012\u00020\b0\u00078\u0006\u00a2\u0006\f\n\u0004\b>\u0010\u000f\u001a\u0004\b?\u0010\u0011R\u001d\u0010@\u001a\b\u0012\u0004\u0012\u00020\b0\u00078\u0006\u00a2\u0006\f\n\u0004\b@\u0010\u000f\u001a\u0004\bA\u0010\u0011R\u001d\u0010B\u001a\b\u0012\u0004\u0012\u00020\b0\u00078\u0006\u00a2\u0006\f\n\u0004\bB\u0010\u000f\u001a\u0004\bC\u0010\u0011R\u001d\u0010D\u001a\b\u0012\u0004\u0012\u00020\b0\u00078\u0006\u00a2\u0006\f\n\u0004\bD\u0010\u000f\u001a\u0004\bE\u0010\u0011R\u001d\u0010F\u001a\b\u0012\u0004\u0012\u00020\b0\u00078\u0006\u00a2\u0006\f\n\u0004\bF\u0010\u000f\u001a\u0004\bG\u0010\u0011R\u001d\u0010H\u001a\b\u0012\u0004\u0012\u00020\b0\u00078\u0006\u00a2\u0006\f\n\u0004\bH\u0010\u000f\u001a\u0004\bI\u0010\u0011R\u001d\u0010J\u001a\b\u0012\u0004\u0012\u00020\b0\u00078\u0006\u00a2\u0006\f\n\u0004\bJ\u0010\u000f\u001a\u0004\bK\u0010\u0011R\u001d\u0010L\u001a\b\u0012\u0004\u0012\u00020\b0\u00078\u0006\u00a2\u0006\f\n\u0004\bL\u0010\u000f\u001a\u0004\bM\u0010\u0011R\u001d\u0010N\u001a\b\u0012\u0004\u0012\u00020\b0\u00078\u0006\u00a2\u0006\f\n\u0004\bN\u0010\u000f\u001a\u0004\bO\u0010\u0011R\u001d\u0010P\u001a\b\u0012\u0004\u0012\u00020\b0\u00078\u0006\u00a2\u0006\f\n\u0004\bP\u0010\u000f\u001a\u0004\bQ\u0010\u0011R\u001d\u0010R\u001a\b\u0012\u0004\u0012\u00020\b0\u00078\u0006\u00a2\u0006\f\n\u0004\bR\u0010\u000f\u001a\u0004\bS\u0010\u0011R\u001d\u0010T\u001a\b\u0012\u0004\u0012\u00020\b0\u00078\u0006\u00a2\u0006\f\n\u0004\bT\u0010\u000f\u001a\u0004\bU\u0010\u0011R\u001d\u0010V\u001a\b\u0012\u0004\u0012\u00020\b0\u00078\u0006\u00a2\u0006\f\n\u0004\bV\u0010\u000f\u001a\u0004\bW\u0010\u0011R\u001d\u0010X\u001a\b\u0012\u0004\u0012\u00020\b0\u00078\u0006\u00a2\u0006\f\n\u0004\bX\u0010\u000f\u001a\u0004\bY\u0010\u0011R\u001d\u0010Z\u001a\b\u0012\u0004\u0012\u00020\b0\u00078\u0006\u00a2\u0006\f\n\u0004\bZ\u0010\u000f\u001a\u0004\b[\u0010\u0011R\u001d\u0010\\\u001a\b\u0012\u0004\u0012\u00020\b0\u00078\u0006\u00a2\u0006\f\n\u0004\b\\\u0010\u000f\u001a\u0004\b]\u0010\u0011R\u001d\u0010^\u001a\b\u0012\u0004\u0012\u00020\b0\u00078\u0006\u00a2\u0006\f\n\u0004\b^\u0010\u000f\u001a\u0004\b_\u0010\u0011R\u001d\u0010`\u001a\b\u0012\u0004\u0012\u00020\b0\u00078\u0006\u00a2\u0006\f\n\u0004\b`\u0010\u000f\u001a\u0004\ba\u0010\u0011R\u001d\u0010b\u001a\b\u0012\u0004\u0012\u00020\b0\u00078\u0006\u00a2\u0006\f\n\u0004\bb\u0010\u000f\u001a\u0004\bc\u0010\u0011R$\u0010g\u001a\u0012\u0012\u0004\u0012\u00020e0dj\b\u0012\u0004\u0012\u00020e`f8\u0002X\u0082\u0004\u00a2\u0006\u0006\n\u0004\bg\u0010h\u00a8\u0006k"}, d2={"Lcom/cobblemon/mod/common/world/feature/ore/CobblemonOrePlacedFeatures;", "", "", "id", "Lnet/minecraft/tags/TagKey;", "Lnet/minecraft/world/level/biome/Biome;", "validBiomes", "Lnet/minecraft/resources/ResourceKey;", "Lnet/minecraft/world/level/levelgen/placement/PlacedFeature;", "of", "(Ljava/lang/String;Lnet/minecraft/tags/TagKey;)Lnet/minecraft/resources/ResourceKey;", "", "register", "()V", "DAWN_STONE_LOWER", "Lnet/minecraft/resources/ResourceKey;", "getDAWN_STONE_LOWER", "()Lnet/minecraft/resources/ResourceKey;", "DAWN_STONE_LOWER_RARE", "getDAWN_STONE_LOWER_RARE", "DAWN_STONE_UPPER", "getDAWN_STONE_UPPER", "DAWN_STONE_UPPER_RARE", "getDAWN_STONE_UPPER_RARE", "DUSK_STONE_LOWER", "getDUSK_STONE_LOWER", "DUSK_STONE_LOWER_RARE", "getDUSK_STONE_LOWER_RARE", "DUSK_STONE_UPPER", "getDUSK_STONE_UPPER", "DUSK_STONE_UPPER_RARE", "getDUSK_STONE_UPPER_RARE", "FIRE_STONE_LOWER", "getFIRE_STONE_LOWER", "FIRE_STONE_LOWER_RARE", "getFIRE_STONE_LOWER_RARE", "FIRE_STONE_NETHER", "getFIRE_STONE_NETHER", "FIRE_STONE_UPPER", "getFIRE_STONE_UPPER", "FIRE_STONE_UPPER_RARE", "getFIRE_STONE_UPPER_RARE", "ICE_STONE_LOWER", "getICE_STONE_LOWER", "ICE_STONE_LOWER_RARE", "getICE_STONE_LOWER_RARE", "ICE_STONE_UPPER", "getICE_STONE_UPPER", "ICE_STONE_UPPER_RARE", "getICE_STONE_UPPER_RARE", "LEAF_STONE_LOWER", "getLEAF_STONE_LOWER", "LEAF_STONE_LOWER_RARE", "getLEAF_STONE_LOWER_RARE", "LEAF_STONE_UPPER", "getLEAF_STONE_UPPER", "LEAF_STONE_UPPER_RARE", "getLEAF_STONE_UPPER_RARE", "MOON_STONE_DRIPSTONE", "getMOON_STONE_DRIPSTONE", "MOON_STONE_LOWER", "getMOON_STONE_LOWER", "MOON_STONE_LOWER_RARE", "getMOON_STONE_LOWER_RARE", "MOON_STONE_UPPER", "getMOON_STONE_UPPER", "MOON_STONE_UPPER_RARE", "getMOON_STONE_UPPER_RARE", "SHINY_STONE_LOWER", "getSHINY_STONE_LOWER", "SHINY_STONE_LOWER_RARE", "getSHINY_STONE_LOWER_RARE", "SHINY_STONE_UPPER", "getSHINY_STONE_UPPER", "SHINY_STONE_UPPER_RARE", "getSHINY_STONE_UPPER_RARE", "SUN_STONE_LOWER", "getSUN_STONE_LOWER", "SUN_STONE_LOWER_RARE", "getSUN_STONE_LOWER_RARE", "SUN_STONE_UPPER", "getSUN_STONE_UPPER", "SUN_STONE_UPPER_RARE", "getSUN_STONE_UPPER_RARE", "THUNDER_STONE_LOWER", "getTHUNDER_STONE_LOWER", "THUNDER_STONE_LOWER_RARE", "getTHUNDER_STONE_LOWER_RARE", "THUNDER_STONE_UPPER", "getTHUNDER_STONE_UPPER", "THUNDER_STONE_UPPER_RARE", "getTHUNDER_STONE_UPPER_RARE", "WATER_STONE_LOWER", "getWATER_STONE_LOWER", "WATER_STONE_LOWER_RARE", "getWATER_STONE_LOWER_RARE", "WATER_STONE_UPPER", "getWATER_STONE_UPPER", "WATER_STONE_UPPER_RARE", "getWATER_STONE_UPPER_RARE", "Ljava/util/ArrayList;", "Lcom/cobblemon/mod/common/world/feature/ore/CobblemonOrePlacedFeatures$FeatureHolder;", "Lkotlin/collections/ArrayList;", "features", "Ljava/util/ArrayList;", "<init>", "FeatureHolder", "common"})
@SourceDebugExtension(value={"SMAP\nCobblemonOrePlacedFeatures.kt\nKotlin\n*S Kotlin\n*F\n+ 1 CobblemonOrePlacedFeatures.kt\ncom/cobblemon/mod/common/world/feature/ore/CobblemonOrePlacedFeatures\n+ 2 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n*L\n1#1,103:1\n1855#2,2:104\n*S KotlinDebug\n*F\n+ 1 CobblemonOrePlacedFeatures.kt\ncom/cobblemon/mod/common/world/feature/ore/CobblemonOrePlacedFeatures\n*L\n87#1:104,2\n*E\n"})
public final class CobblemonOrePlacedFeatures {
    @NotNull
    public static final CobblemonOrePlacedFeatures INSTANCE = new CobblemonOrePlacedFeatures();
    @NotNull
    private static final ArrayList<FeatureHolder> features = new ArrayList();
    @NotNull
    private static final ResourceKey<PlacedFeature> DAWN_STONE_UPPER;
    @NotNull
    private static final ResourceKey<PlacedFeature> DAWN_STONE_LOWER;
    @NotNull
    private static final ResourceKey<PlacedFeature> DAWN_STONE_UPPER_RARE;
    @NotNull
    private static final ResourceKey<PlacedFeature> DAWN_STONE_LOWER_RARE;
    @NotNull
    private static final ResourceKey<PlacedFeature> DUSK_STONE_UPPER;
    @NotNull
    private static final ResourceKey<PlacedFeature> DUSK_STONE_LOWER;
    @NotNull
    private static final ResourceKey<PlacedFeature> DUSK_STONE_UPPER_RARE;
    @NotNull
    private static final ResourceKey<PlacedFeature> DUSK_STONE_LOWER_RARE;
    @NotNull
    private static final ResourceKey<PlacedFeature> FIRE_STONE_UPPER;
    @NotNull
    private static final ResourceKey<PlacedFeature> FIRE_STONE_LOWER;
    @NotNull
    private static final ResourceKey<PlacedFeature> FIRE_STONE_UPPER_RARE;
    @NotNull
    private static final ResourceKey<PlacedFeature> FIRE_STONE_LOWER_RARE;
    @NotNull
    private static final ResourceKey<PlacedFeature> FIRE_STONE_NETHER;
    @NotNull
    private static final ResourceKey<PlacedFeature> ICE_STONE_UPPER;
    @NotNull
    private static final ResourceKey<PlacedFeature> ICE_STONE_LOWER;
    @NotNull
    private static final ResourceKey<PlacedFeature> ICE_STONE_UPPER_RARE;
    @NotNull
    private static final ResourceKey<PlacedFeature> ICE_STONE_LOWER_RARE;
    @NotNull
    private static final ResourceKey<PlacedFeature> LEAF_STONE_UPPER;
    @NotNull
    private static final ResourceKey<PlacedFeature> LEAF_STONE_LOWER;
    @NotNull
    private static final ResourceKey<PlacedFeature> LEAF_STONE_UPPER_RARE;
    @NotNull
    private static final ResourceKey<PlacedFeature> LEAF_STONE_LOWER_RARE;
    @NotNull
    private static final ResourceKey<PlacedFeature> MOON_STONE_UPPER;
    @NotNull
    private static final ResourceKey<PlacedFeature> MOON_STONE_LOWER;
    @NotNull
    private static final ResourceKey<PlacedFeature> MOON_STONE_UPPER_RARE;
    @NotNull
    private static final ResourceKey<PlacedFeature> MOON_STONE_LOWER_RARE;
    @NotNull
    private static final ResourceKey<PlacedFeature> MOON_STONE_DRIPSTONE;
    @NotNull
    private static final ResourceKey<PlacedFeature> SHINY_STONE_UPPER;
    @NotNull
    private static final ResourceKey<PlacedFeature> SHINY_STONE_LOWER;
    @NotNull
    private static final ResourceKey<PlacedFeature> SHINY_STONE_UPPER_RARE;
    @NotNull
    private static final ResourceKey<PlacedFeature> SHINY_STONE_LOWER_RARE;
    @NotNull
    private static final ResourceKey<PlacedFeature> SUN_STONE_UPPER;
    @NotNull
    private static final ResourceKey<PlacedFeature> SUN_STONE_LOWER;
    @NotNull
    private static final ResourceKey<PlacedFeature> SUN_STONE_UPPER_RARE;
    @NotNull
    private static final ResourceKey<PlacedFeature> SUN_STONE_LOWER_RARE;
    @NotNull
    private static final ResourceKey<PlacedFeature> THUNDER_STONE_UPPER;
    @NotNull
    private static final ResourceKey<PlacedFeature> THUNDER_STONE_LOWER;
    @NotNull
    private static final ResourceKey<PlacedFeature> THUNDER_STONE_UPPER_RARE;
    @NotNull
    private static final ResourceKey<PlacedFeature> THUNDER_STONE_LOWER_RARE;
    @NotNull
    private static final ResourceKey<PlacedFeature> WATER_STONE_UPPER;
    @NotNull
    private static final ResourceKey<PlacedFeature> WATER_STONE_LOWER;
    @NotNull
    private static final ResourceKey<PlacedFeature> WATER_STONE_UPPER_RARE;
    @NotNull
    private static final ResourceKey<PlacedFeature> WATER_STONE_LOWER_RARE;

    private CobblemonOrePlacedFeatures() {
    }

    @NotNull
    public final ResourceKey<PlacedFeature> getDAWN_STONE_UPPER() {
        return DAWN_STONE_UPPER;
    }

    @NotNull
    public final ResourceKey<PlacedFeature> getDAWN_STONE_LOWER() {
        return DAWN_STONE_LOWER;
    }

    @NotNull
    public final ResourceKey<PlacedFeature> getDAWN_STONE_UPPER_RARE() {
        return DAWN_STONE_UPPER_RARE;
    }

    @NotNull
    public final ResourceKey<PlacedFeature> getDAWN_STONE_LOWER_RARE() {
        return DAWN_STONE_LOWER_RARE;
    }

    @NotNull
    public final ResourceKey<PlacedFeature> getDUSK_STONE_UPPER() {
        return DUSK_STONE_UPPER;
    }

    @NotNull
    public final ResourceKey<PlacedFeature> getDUSK_STONE_LOWER() {
        return DUSK_STONE_LOWER;
    }

    @NotNull
    public final ResourceKey<PlacedFeature> getDUSK_STONE_UPPER_RARE() {
        return DUSK_STONE_UPPER_RARE;
    }

    @NotNull
    public final ResourceKey<PlacedFeature> getDUSK_STONE_LOWER_RARE() {
        return DUSK_STONE_LOWER_RARE;
    }

    @NotNull
    public final ResourceKey<PlacedFeature> getFIRE_STONE_UPPER() {
        return FIRE_STONE_UPPER;
    }

    @NotNull
    public final ResourceKey<PlacedFeature> getFIRE_STONE_LOWER() {
        return FIRE_STONE_LOWER;
    }

    @NotNull
    public final ResourceKey<PlacedFeature> getFIRE_STONE_UPPER_RARE() {
        return FIRE_STONE_UPPER_RARE;
    }

    @NotNull
    public final ResourceKey<PlacedFeature> getFIRE_STONE_LOWER_RARE() {
        return FIRE_STONE_LOWER_RARE;
    }

    @NotNull
    public final ResourceKey<PlacedFeature> getFIRE_STONE_NETHER() {
        return FIRE_STONE_NETHER;
    }

    @NotNull
    public final ResourceKey<PlacedFeature> getICE_STONE_UPPER() {
        return ICE_STONE_UPPER;
    }

    @NotNull
    public final ResourceKey<PlacedFeature> getICE_STONE_LOWER() {
        return ICE_STONE_LOWER;
    }

    @NotNull
    public final ResourceKey<PlacedFeature> getICE_STONE_UPPER_RARE() {
        return ICE_STONE_UPPER_RARE;
    }

    @NotNull
    public final ResourceKey<PlacedFeature> getICE_STONE_LOWER_RARE() {
        return ICE_STONE_LOWER_RARE;
    }

    @NotNull
    public final ResourceKey<PlacedFeature> getLEAF_STONE_UPPER() {
        return LEAF_STONE_UPPER;
    }

    @NotNull
    public final ResourceKey<PlacedFeature> getLEAF_STONE_LOWER() {
        return LEAF_STONE_LOWER;
    }

    @NotNull
    public final ResourceKey<PlacedFeature> getLEAF_STONE_UPPER_RARE() {
        return LEAF_STONE_UPPER_RARE;
    }

    @NotNull
    public final ResourceKey<PlacedFeature> getLEAF_STONE_LOWER_RARE() {
        return LEAF_STONE_LOWER_RARE;
    }

    @NotNull
    public final ResourceKey<PlacedFeature> getMOON_STONE_UPPER() {
        return MOON_STONE_UPPER;
    }

    @NotNull
    public final ResourceKey<PlacedFeature> getMOON_STONE_LOWER() {
        return MOON_STONE_LOWER;
    }

    @NotNull
    public final ResourceKey<PlacedFeature> getMOON_STONE_UPPER_RARE() {
        return MOON_STONE_UPPER_RARE;
    }

    @NotNull
    public final ResourceKey<PlacedFeature> getMOON_STONE_LOWER_RARE() {
        return MOON_STONE_LOWER_RARE;
    }

    @NotNull
    public final ResourceKey<PlacedFeature> getMOON_STONE_DRIPSTONE() {
        return MOON_STONE_DRIPSTONE;
    }

    @NotNull
    public final ResourceKey<PlacedFeature> getSHINY_STONE_UPPER() {
        return SHINY_STONE_UPPER;
    }

    @NotNull
    public final ResourceKey<PlacedFeature> getSHINY_STONE_LOWER() {
        return SHINY_STONE_LOWER;
    }

    @NotNull
    public final ResourceKey<PlacedFeature> getSHINY_STONE_UPPER_RARE() {
        return SHINY_STONE_UPPER_RARE;
    }

    @NotNull
    public final ResourceKey<PlacedFeature> getSHINY_STONE_LOWER_RARE() {
        return SHINY_STONE_LOWER_RARE;
    }

    @NotNull
    public final ResourceKey<PlacedFeature> getSUN_STONE_UPPER() {
        return SUN_STONE_UPPER;
    }

    @NotNull
    public final ResourceKey<PlacedFeature> getSUN_STONE_LOWER() {
        return SUN_STONE_LOWER;
    }

    @NotNull
    public final ResourceKey<PlacedFeature> getSUN_STONE_UPPER_RARE() {
        return SUN_STONE_UPPER_RARE;
    }

    @NotNull
    public final ResourceKey<PlacedFeature> getSUN_STONE_LOWER_RARE() {
        return SUN_STONE_LOWER_RARE;
    }

    @NotNull
    public final ResourceKey<PlacedFeature> getTHUNDER_STONE_UPPER() {
        return THUNDER_STONE_UPPER;
    }

    @NotNull
    public final ResourceKey<PlacedFeature> getTHUNDER_STONE_LOWER() {
        return THUNDER_STONE_LOWER;
    }

    @NotNull
    public final ResourceKey<PlacedFeature> getTHUNDER_STONE_UPPER_RARE() {
        return THUNDER_STONE_UPPER_RARE;
    }

    @NotNull
    public final ResourceKey<PlacedFeature> getTHUNDER_STONE_LOWER_RARE() {
        return THUNDER_STONE_LOWER_RARE;
    }

    @NotNull
    public final ResourceKey<PlacedFeature> getWATER_STONE_UPPER() {
        return WATER_STONE_UPPER;
    }

    @NotNull
    public final ResourceKey<PlacedFeature> getWATER_STONE_LOWER() {
        return WATER_STONE_LOWER;
    }

    @NotNull
    public final ResourceKey<PlacedFeature> getWATER_STONE_UPPER_RARE() {
        return WATER_STONE_UPPER_RARE;
    }

    @NotNull
    public final ResourceKey<PlacedFeature> getWATER_STONE_LOWER_RARE() {
        return WATER_STONE_LOWER_RARE;
    }

    public final void register() {
        Iterable $this$forEach$iv = features;
        boolean $i$f$forEach = false;
        for (Object element$iv : $this$forEach$iv) {
            FeatureHolder holder = (FeatureHolder)element$iv;
            boolean bl = false;
            Cobblemon.INSTANCE.getImplementation().addFeatureToWorldGen(holder.getFeature(), GenerationStep.Decoration.UNDERGROUND_ORES, holder.getValidBiomes());
        }
    }

    private final ResourceKey<PlacedFeature> of(String id, TagKey<Biome> validBiomes) {
        ResourceKey feature = PlacementUtils.m_255070_((String)("cobblemon:ore/" + id));
        Collection collection = features;
        Intrinsics.checkNotNullExpressionValue((Object)feature, (String)"feature");
        collection.add(new FeatureHolder((ResourceKey<PlacedFeature>)feature, validBiomes));
        return feature;
    }

    static {
        TagKey<Biome> tagKey = CobblemonBiomeTags.HAS_DAWN_STONE_ORE;
        Intrinsics.checkNotNullExpressionValue(tagKey, (String)"HAS_DAWN_STONE_ORE");
        DAWN_STONE_UPPER = INSTANCE.of("dawn_stone_upper", tagKey);
        TagKey<Biome> tagKey2 = CobblemonBiomeTags.HAS_DAWN_STONE_ORE;
        Intrinsics.checkNotNullExpressionValue(tagKey2, (String)"HAS_DAWN_STONE_ORE");
        DAWN_STONE_LOWER = INSTANCE.of("dawn_stone_lower", tagKey2);
        TagKey<Biome> tagKey3 = CobblemonBiomeTags.HAS_DAWN_STONE_ORE_RARE;
        Intrinsics.checkNotNullExpressionValue(tagKey3, (String)"HAS_DAWN_STONE_ORE_RARE");
        DAWN_STONE_UPPER_RARE = INSTANCE.of("dawn_stone_upper_rare", tagKey3);
        TagKey<Biome> tagKey4 = CobblemonBiomeTags.HAS_DAWN_STONE_ORE_RARE;
        Intrinsics.checkNotNullExpressionValue(tagKey4, (String)"HAS_DAWN_STONE_ORE_RARE");
        DAWN_STONE_LOWER_RARE = INSTANCE.of("dawn_stone_lower_rare", tagKey4);
        TagKey<Biome> tagKey5 = CobblemonBiomeTags.HAS_DUSK_STONE_ORE;
        Intrinsics.checkNotNullExpressionValue(tagKey5, (String)"HAS_DUSK_STONE_ORE");
        DUSK_STONE_UPPER = INSTANCE.of("dusk_stone_upper", tagKey5);
        TagKey<Biome> tagKey6 = CobblemonBiomeTags.HAS_DUSK_STONE_ORE;
        Intrinsics.checkNotNullExpressionValue(tagKey6, (String)"HAS_DUSK_STONE_ORE");
        DUSK_STONE_LOWER = INSTANCE.of("dusk_stone_lower", tagKey6);
        TagKey<Biome> tagKey7 = CobblemonBiomeTags.HAS_DUSK_STONE_ORE_RARE;
        Intrinsics.checkNotNullExpressionValue(tagKey7, (String)"HAS_DUSK_STONE_ORE_RARE");
        DUSK_STONE_UPPER_RARE = INSTANCE.of("dusk_stone_upper_rare", tagKey7);
        TagKey<Biome> tagKey8 = CobblemonBiomeTags.HAS_DUSK_STONE_ORE_RARE;
        Intrinsics.checkNotNullExpressionValue(tagKey8, (String)"HAS_DUSK_STONE_ORE_RARE");
        DUSK_STONE_LOWER_RARE = INSTANCE.of("dusk_stone_lower_rare", tagKey8);
        TagKey<Biome> tagKey9 = CobblemonBiomeTags.HAS_FIRE_STONE_ORE;
        Intrinsics.checkNotNullExpressionValue(tagKey9, (String)"HAS_FIRE_STONE_ORE");
        FIRE_STONE_UPPER = INSTANCE.of("fire_stone_upper", tagKey9);
        TagKey<Biome> tagKey10 = CobblemonBiomeTags.HAS_FIRE_STONE_ORE;
        Intrinsics.checkNotNullExpressionValue(tagKey10, (String)"HAS_FIRE_STONE_ORE");
        FIRE_STONE_LOWER = INSTANCE.of("fire_stone_lower", tagKey10);
        TagKey<Biome> tagKey11 = CobblemonBiomeTags.HAS_FIRE_STONE_ORE_RARE;
        Intrinsics.checkNotNullExpressionValue(tagKey11, (String)"HAS_FIRE_STONE_ORE_RARE");
        FIRE_STONE_UPPER_RARE = INSTANCE.of("fire_stone_upper_rare", tagKey11);
        TagKey<Biome> tagKey12 = CobblemonBiomeTags.HAS_FIRE_STONE_ORE_RARE;
        Intrinsics.checkNotNullExpressionValue(tagKey12, (String)"HAS_FIRE_STONE_ORE_RARE");
        FIRE_STONE_LOWER_RARE = INSTANCE.of("fire_stone_lower_rare", tagKey12);
        TagKey<Biome> tagKey13 = CobblemonBiomeTags.HAS_FIRE_STONE_ORE_NETHER;
        Intrinsics.checkNotNullExpressionValue(tagKey13, (String)"HAS_FIRE_STONE_ORE_NETHER");
        FIRE_STONE_NETHER = INSTANCE.of("fire_stone_nether", tagKey13);
        TagKey<Biome> tagKey14 = CobblemonBiomeTags.HAS_ICE_STONE_ORE;
        Intrinsics.checkNotNullExpressionValue(tagKey14, (String)"HAS_ICE_STONE_ORE");
        ICE_STONE_UPPER = INSTANCE.of("ice_stone_upper", tagKey14);
        TagKey<Biome> tagKey15 = CobblemonBiomeTags.HAS_ICE_STONE_ORE;
        Intrinsics.checkNotNullExpressionValue(tagKey15, (String)"HAS_ICE_STONE_ORE");
        ICE_STONE_LOWER = INSTANCE.of("ice_stone_lower", tagKey15);
        TagKey<Biome> tagKey16 = CobblemonBiomeTags.HAS_ICE_STONE_ORE_RARE;
        Intrinsics.checkNotNullExpressionValue(tagKey16, (String)"HAS_ICE_STONE_ORE_RARE");
        ICE_STONE_UPPER_RARE = INSTANCE.of("ice_stone_upper_rare", tagKey16);
        TagKey<Biome> tagKey17 = CobblemonBiomeTags.HAS_ICE_STONE_ORE_RARE;
        Intrinsics.checkNotNullExpressionValue(tagKey17, (String)"HAS_ICE_STONE_ORE_RARE");
        ICE_STONE_LOWER_RARE = INSTANCE.of("ice_stone_lower_rare", tagKey17);
        TagKey<Biome> tagKey18 = CobblemonBiomeTags.HAS_LEAF_STONE_ORE;
        Intrinsics.checkNotNullExpressionValue(tagKey18, (String)"HAS_LEAF_STONE_ORE");
        LEAF_STONE_UPPER = INSTANCE.of("leaf_stone_upper", tagKey18);
        TagKey<Biome> tagKey19 = CobblemonBiomeTags.HAS_LEAF_STONE_ORE;
        Intrinsics.checkNotNullExpressionValue(tagKey19, (String)"HAS_LEAF_STONE_ORE");
        LEAF_STONE_LOWER = INSTANCE.of("leaf_stone_lower", tagKey19);
        TagKey<Biome> tagKey20 = CobblemonBiomeTags.HAS_LEAF_STONE_ORE_RARE;
        Intrinsics.checkNotNullExpressionValue(tagKey20, (String)"HAS_LEAF_STONE_ORE_RARE");
        LEAF_STONE_UPPER_RARE = INSTANCE.of("leaf_stone_upper_rare", tagKey20);
        TagKey<Biome> tagKey21 = CobblemonBiomeTags.HAS_LEAF_STONE_ORE_RARE;
        Intrinsics.checkNotNullExpressionValue(tagKey21, (String)"HAS_LEAF_STONE_ORE_RARE");
        LEAF_STONE_LOWER_RARE = INSTANCE.of("leaf_stone_lower_rare", tagKey21);
        TagKey<Biome> tagKey22 = CobblemonBiomeTags.HAS_MOON_STONE_ORE;
        Intrinsics.checkNotNullExpressionValue(tagKey22, (String)"HAS_MOON_STONE_ORE");
        MOON_STONE_UPPER = INSTANCE.of("moon_stone_upper", tagKey22);
        TagKey<Biome> tagKey23 = CobblemonBiomeTags.HAS_MOON_STONE_ORE;
        Intrinsics.checkNotNullExpressionValue(tagKey23, (String)"HAS_MOON_STONE_ORE");
        MOON_STONE_LOWER = INSTANCE.of("moon_stone_lower", tagKey23);
        TagKey<Biome> tagKey24 = CobblemonBiomeTags.HAS_MOON_STONE_ORE_RARE;
        Intrinsics.checkNotNullExpressionValue(tagKey24, (String)"HAS_MOON_STONE_ORE_RARE");
        MOON_STONE_UPPER_RARE = INSTANCE.of("moon_stone_upper_rare", tagKey24);
        TagKey<Biome> tagKey25 = CobblemonBiomeTags.HAS_MOON_STONE_ORE_RARE;
        Intrinsics.checkNotNullExpressionValue(tagKey25, (String)"HAS_MOON_STONE_ORE_RARE");
        MOON_STONE_LOWER_RARE = INSTANCE.of("moon_stone_lower_rare", tagKey25);
        TagKey<Biome> tagKey26 = CobblemonBiomeTags.HAS_MOON_STONE_ORE_DRIPSTONE;
        Intrinsics.checkNotNullExpressionValue(tagKey26, (String)"HAS_MOON_STONE_ORE_DRIPSTONE");
        MOON_STONE_DRIPSTONE = INSTANCE.of("moon_stone_dripstone", tagKey26);
        TagKey<Biome> tagKey27 = CobblemonBiomeTags.HAS_SHINY_STONE_ORE;
        Intrinsics.checkNotNullExpressionValue(tagKey27, (String)"HAS_SHINY_STONE_ORE");
        SHINY_STONE_UPPER = INSTANCE.of("shiny_stone_upper", tagKey27);
        TagKey<Biome> tagKey28 = CobblemonBiomeTags.HAS_SHINY_STONE_ORE;
        Intrinsics.checkNotNullExpressionValue(tagKey28, (String)"HAS_SHINY_STONE_ORE");
        SHINY_STONE_LOWER = INSTANCE.of("shiny_stone_lower", tagKey28);
        TagKey<Biome> tagKey29 = CobblemonBiomeTags.HAS_SHINY_STONE_ORE_RARE;
        Intrinsics.checkNotNullExpressionValue(tagKey29, (String)"HAS_SHINY_STONE_ORE_RARE");
        SHINY_STONE_UPPER_RARE = INSTANCE.of("shiny_stone_upper_rare", tagKey29);
        TagKey<Biome> tagKey30 = CobblemonBiomeTags.HAS_SHINY_STONE_ORE_RARE;
        Intrinsics.checkNotNullExpressionValue(tagKey30, (String)"HAS_SHINY_STONE_ORE_RARE");
        SHINY_STONE_LOWER_RARE = INSTANCE.of("shiny_stone_lower_rare", tagKey30);
        TagKey<Biome> tagKey31 = CobblemonBiomeTags.HAS_SUN_STONE_ORE;
        Intrinsics.checkNotNullExpressionValue(tagKey31, (String)"HAS_SUN_STONE_ORE");
        SUN_STONE_UPPER = INSTANCE.of("sun_stone_upper", tagKey31);
        TagKey<Biome> tagKey32 = CobblemonBiomeTags.HAS_SUN_STONE_ORE;
        Intrinsics.checkNotNullExpressionValue(tagKey32, (String)"HAS_SUN_STONE_ORE");
        SUN_STONE_LOWER = INSTANCE.of("sun_stone_lower", tagKey32);
        TagKey<Biome> tagKey33 = CobblemonBiomeTags.HAS_SUN_STONE_ORE_RARE;
        Intrinsics.checkNotNullExpressionValue(tagKey33, (String)"HAS_SUN_STONE_ORE_RARE");
        SUN_STONE_UPPER_RARE = INSTANCE.of("sun_stone_upper_rare", tagKey33);
        TagKey<Biome> tagKey34 = CobblemonBiomeTags.HAS_SUN_STONE_ORE_RARE;
        Intrinsics.checkNotNullExpressionValue(tagKey34, (String)"HAS_SUN_STONE_ORE_RARE");
        SUN_STONE_LOWER_RARE = INSTANCE.of("sun_stone_lower_rare", tagKey34);
        TagKey<Biome> tagKey35 = CobblemonBiomeTags.HAS_THUNDER_STONE_ORE;
        Intrinsics.checkNotNullExpressionValue(tagKey35, (String)"HAS_THUNDER_STONE_ORE");
        THUNDER_STONE_UPPER = INSTANCE.of("thunder_stone_upper", tagKey35);
        TagKey<Biome> tagKey36 = CobblemonBiomeTags.HAS_THUNDER_STONE_ORE;
        Intrinsics.checkNotNullExpressionValue(tagKey36, (String)"HAS_THUNDER_STONE_ORE");
        THUNDER_STONE_LOWER = INSTANCE.of("thunder_stone_lower", tagKey36);
        TagKey<Biome> tagKey37 = CobblemonBiomeTags.HAS_THUNDER_STONE_ORE_RARE;
        Intrinsics.checkNotNullExpressionValue(tagKey37, (String)"HAS_THUNDER_STONE_ORE_RARE");
        THUNDER_STONE_UPPER_RARE = INSTANCE.of("thunder_stone_upper_rare", tagKey37);
        TagKey<Biome> tagKey38 = CobblemonBiomeTags.HAS_THUNDER_STONE_ORE_RARE;
        Intrinsics.checkNotNullExpressionValue(tagKey38, (String)"HAS_THUNDER_STONE_ORE_RARE");
        THUNDER_STONE_LOWER_RARE = INSTANCE.of("thunder_stone_lower_rare", tagKey38);
        TagKey<Biome> tagKey39 = CobblemonBiomeTags.HAS_WATER_STONE_ORE;
        Intrinsics.checkNotNullExpressionValue(tagKey39, (String)"HAS_WATER_STONE_ORE");
        WATER_STONE_UPPER = INSTANCE.of("water_stone_upper", tagKey39);
        TagKey<Biome> tagKey40 = CobblemonBiomeTags.HAS_WATER_STONE_ORE;
        Intrinsics.checkNotNullExpressionValue(tagKey40, (String)"HAS_WATER_STONE_ORE");
        WATER_STONE_LOWER = INSTANCE.of("water_stone_lower", tagKey40);
        TagKey<Biome> tagKey41 = CobblemonBiomeTags.HAS_WATER_STONE_ORE_RARE;
        Intrinsics.checkNotNullExpressionValue(tagKey41, (String)"HAS_WATER_STONE_ORE_RARE");
        WATER_STONE_UPPER_RARE = INSTANCE.of("water_stone_upper_rare", tagKey41);
        TagKey<Biome> tagKey42 = CobblemonBiomeTags.HAS_WATER_STONE_ORE_RARE;
        Intrinsics.checkNotNullExpressionValue(tagKey42, (String)"HAS_WATER_STONE_ORE_RARE");
        WATER_STONE_LOWER_RARE = INSTANCE.of("water_stone_lower_rare", tagKey42);
    }

    @Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u00008\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\t\b\u0082\b\u0018\u00002\u00020\u0001B#\u0012\f\u0010\n\u001a\b\u0012\u0004\u0012\u00020\u00030\u0002\u0012\f\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006\u00a2\u0006\u0004\b\u001c\u0010\u001dJ\u0016\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00030\u0002H\u00c6\u0003\u00a2\u0006\u0004\b\u0004\u0010\u0005J\u0016\u0010\b\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006H\u00c6\u0003\u00a2\u0006\u0004\b\b\u0010\tJ0\u0010\f\u001a\u00020\u00002\u000e\b\u0002\u0010\n\u001a\b\u0012\u0004\u0012\u00020\u00030\u00022\u000e\b\u0002\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006H\u00c6\u0001\u00a2\u0006\u0004\b\f\u0010\rJ\u001a\u0010\u0010\u001a\u00020\u000f2\b\u0010\u000e\u001a\u0004\u0018\u00010\u0001H\u00d6\u0003\u00a2\u0006\u0004\b\u0010\u0010\u0011J\u0010\u0010\u0013\u001a\u00020\u0012H\u00d6\u0001\u00a2\u0006\u0004\b\u0013\u0010\u0014J\u0010\u0010\u0016\u001a\u00020\u0015H\u00d6\u0001\u00a2\u0006\u0004\b\u0016\u0010\u0017R\u001d\u0010\n\u001a\b\u0012\u0004\u0012\u00020\u00030\u00028\u0006\u00a2\u0006\f\n\u0004\b\n\u0010\u0018\u001a\u0004\b\u0019\u0010\u0005R\u001d\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\u00070\u00068\u0006\u00a2\u0006\f\n\u0004\b\u000b\u0010\u001a\u001a\u0004\b\u001b\u0010\t\u00a8\u0006\u001e"}, d2={"Lcom/cobblemon/mod/common/world/feature/ore/CobblemonOrePlacedFeatures$FeatureHolder;", "", "Lnet/minecraft/resources/ResourceKey;", "Lnet/minecraft/world/level/levelgen/placement/PlacedFeature;", "component1", "()Lnet/minecraft/resources/ResourceKey;", "Lnet/minecraft/tags/TagKey;", "Lnet/minecraft/world/level/biome/Biome;", "component2", "()Lnet/minecraft/tags/TagKey;", "feature", "validBiomes", "copy", "(Lnet/minecraft/resources/ResourceKey;Lnet/minecraft/tags/TagKey;)Lcom/cobblemon/mod/common/world/feature/ore/CobblemonOrePlacedFeatures$FeatureHolder;", "other", "", "equals", "(Ljava/lang/Object;)Z", "", "hashCode", "()I", "", "toString", "()Ljava/lang/String;", "Lnet/minecraft/resources/ResourceKey;", "getFeature", "Lnet/minecraft/tags/TagKey;", "getValidBiomes", "<init>", "(Lnet/minecraft/resources/ResourceKey;Lnet/minecraft/tags/TagKey;)V", "common"})
    private static final class FeatureHolder {
        @NotNull
        private final ResourceKey<PlacedFeature> feature;
        @NotNull
        private final TagKey<Biome> validBiomes;

        public FeatureHolder(@NotNull ResourceKey<PlacedFeature> feature, @NotNull TagKey<Biome> validBiomes) {
            Intrinsics.checkNotNullParameter(feature, (String)"feature");
            Intrinsics.checkNotNullParameter(validBiomes, (String)"validBiomes");
            this.feature = feature;
            this.validBiomes = validBiomes;
        }

        @NotNull
        public final ResourceKey<PlacedFeature> getFeature() {
            return this.feature;
        }

        @NotNull
        public final TagKey<Biome> getValidBiomes() {
            return this.validBiomes;
        }

        @NotNull
        public final ResourceKey<PlacedFeature> component1() {
            return this.feature;
        }

        @NotNull
        public final TagKey<Biome> component2() {
            return this.validBiomes;
        }

        @NotNull
        public final FeatureHolder copy(@NotNull ResourceKey<PlacedFeature> feature, @NotNull TagKey<Biome> validBiomes) {
            Intrinsics.checkNotNullParameter(feature, (String)"feature");
            Intrinsics.checkNotNullParameter(validBiomes, (String)"validBiomes");
            return new FeatureHolder(feature, validBiomes);
        }

        public static /* synthetic */ FeatureHolder copy$default(FeatureHolder featureHolder, ResourceKey resourceKey, TagKey tagKey, int n, Object object) {
            if ((n & 1) != 0) {
                resourceKey = featureHolder.feature;
            }
            if ((n & 2) != 0) {
                tagKey = featureHolder.validBiomes;
            }
            return featureHolder.copy(resourceKey, tagKey);
        }

        @NotNull
        public String toString() {
            return "FeatureHolder(feature=" + this.feature + ", validBiomes=" + this.validBiomes + ")";
        }

        public int hashCode() {
            int result = this.feature.hashCode();
            result = result * 31 + this.validBiomes.hashCode();
            return result;
        }

        public boolean equals(@Nullable Object other) {
            if (this == other) {
                return true;
            }
            if (!(other instanceof FeatureHolder)) {
                return false;
            }
            FeatureHolder featureHolder = (FeatureHolder)other;
            if (!Intrinsics.areEqual(this.feature, featureHolder.feature)) {
                return false;
            }
            return Intrinsics.areEqual(this.validBiomes, featureHolder.validBiomes);
        }
    }
}

