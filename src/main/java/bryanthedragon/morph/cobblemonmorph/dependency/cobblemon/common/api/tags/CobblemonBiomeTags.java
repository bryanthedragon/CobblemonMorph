/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  kotlin.Metadata
 *  kotlin.jvm.JvmField
 *  net.minecraft.core.registries.Registries
 *  net.minecraft.resources.ResourceKey
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.tags.TagKey
 *  net.minecraft.world.level.biome.Biome
 *  org.jetbrains.annotations.NotNull
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.tags;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.MiscUtils;
import kotlin.Metadata;
import kotlin.jvm.JvmField;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.biome.Biome;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000\u001a\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b&\b\u00c6\u0002\u0018\u00002\u00020\u0001B\t\b\u0002\u00a2\u0006\u0004\b)\u0010*J;\u0010\u0007\u001a&\u0012\f\u0012\n \u0006*\u0004\u0018\u00010\u00050\u0005 \u0006*\u0012\u0012\f\u0012\n \u0006*\u0004\u0018\u00010\u00050\u0005\u0018\u00010\u00040\u00042\u0006\u0010\u0003\u001a\u00020\u0002H\u0002\u00a2\u0006\u0004\b\u0007\u0010\bR8\u0010\t\u001a&\u0012\f\u0012\n \u0006*\u0004\u0018\u00010\u00050\u0005 \u0006*\u0012\u0012\f\u0012\n \u0006*\u0004\u0018\u00010\u00050\u0005\u0018\u00010\u00040\u00048\u0006X\u0087\u0004\u00a2\u0006\u0006\n\u0004\b\t\u0010\nR8\u0010\u000b\u001a&\u0012\f\u0012\n \u0006*\u0004\u0018\u00010\u00050\u0005 \u0006*\u0012\u0012\f\u0012\n \u0006*\u0004\u0018\u00010\u00050\u0005\u0018\u00010\u00040\u00048\u0006X\u0087\u0004\u00a2\u0006\u0006\n\u0004\b\u000b\u0010\nR8\u0010\f\u001a&\u0012\f\u0012\n \u0006*\u0004\u0018\u00010\u00050\u0005 \u0006*\u0012\u0012\f\u0012\n \u0006*\u0004\u0018\u00010\u00050\u0005\u0018\u00010\u00040\u00048\u0006X\u0087\u0004\u00a2\u0006\u0006\n\u0004\b\f\u0010\nR8\u0010\r\u001a&\u0012\f\u0012\n \u0006*\u0004\u0018\u00010\u00050\u0005 \u0006*\u0012\u0012\f\u0012\n \u0006*\u0004\u0018\u00010\u00050\u0005\u0018\u00010\u00040\u00048\u0006X\u0087\u0004\u00a2\u0006\u0006\n\u0004\b\r\u0010\nR8\u0010\u000e\u001a&\u0012\f\u0012\n \u0006*\u0004\u0018\u00010\u00050\u0005 \u0006*\u0012\u0012\f\u0012\n \u0006*\u0004\u0018\u00010\u00050\u0005\u0018\u00010\u00040\u00048\u0006X\u0087\u0004\u00a2\u0006\u0006\n\u0004\b\u000e\u0010\nR8\u0010\u000f\u001a&\u0012\f\u0012\n \u0006*\u0004\u0018\u00010\u00050\u0005 \u0006*\u0012\u0012\f\u0012\n \u0006*\u0004\u0018\u00010\u00050\u0005\u0018\u00010\u00040\u00048\u0006X\u0087\u0004\u00a2\u0006\u0006\n\u0004\b\u000f\u0010\nR8\u0010\u0010\u001a&\u0012\f\u0012\n \u0006*\u0004\u0018\u00010\u00050\u0005 \u0006*\u0012\u0012\f\u0012\n \u0006*\u0004\u0018\u00010\u00050\u0005\u0018\u00010\u00040\u00048\u0006X\u0087\u0004\u00a2\u0006\u0006\n\u0004\b\u0010\u0010\nR8\u0010\u0011\u001a&\u0012\f\u0012\n \u0006*\u0004\u0018\u00010\u00050\u0005 \u0006*\u0012\u0012\f\u0012\n \u0006*\u0004\u0018\u00010\u00050\u0005\u0018\u00010\u00040\u00048\u0006X\u0087\u0004\u00a2\u0006\u0006\n\u0004\b\u0011\u0010\nR8\u0010\u0012\u001a&\u0012\f\u0012\n \u0006*\u0004\u0018\u00010\u00050\u0005 \u0006*\u0012\u0012\f\u0012\n \u0006*\u0004\u0018\u00010\u00050\u0005\u0018\u00010\u00040\u00048\u0006X\u0087\u0004\u00a2\u0006\u0006\n\u0004\b\u0012\u0010\nR8\u0010\u0013\u001a&\u0012\f\u0012\n \u0006*\u0004\u0018\u00010\u00050\u0005 \u0006*\u0012\u0012\f\u0012\n \u0006*\u0004\u0018\u00010\u00050\u0005\u0018\u00010\u00040\u00048\u0006X\u0087\u0004\u00a2\u0006\u0006\n\u0004\b\u0013\u0010\nR8\u0010\u0014\u001a&\u0012\f\u0012\n \u0006*\u0004\u0018\u00010\u00050\u0005 \u0006*\u0012\u0012\f\u0012\n \u0006*\u0004\u0018\u00010\u00050\u0005\u0018\u00010\u00040\u00048\u0006X\u0087\u0004\u00a2\u0006\u0006\n\u0004\b\u0014\u0010\nR8\u0010\u0015\u001a&\u0012\f\u0012\n \u0006*\u0004\u0018\u00010\u00050\u0005 \u0006*\u0012\u0012\f\u0012\n \u0006*\u0004\u0018\u00010\u00050\u0005\u0018\u00010\u00040\u00048\u0006X\u0087\u0004\u00a2\u0006\u0006\n\u0004\b\u0015\u0010\nR8\u0010\u0016\u001a&\u0012\f\u0012\n \u0006*\u0004\u0018\u00010\u00050\u0005 \u0006*\u0012\u0012\f\u0012\n \u0006*\u0004\u0018\u00010\u00050\u0005\u0018\u00010\u00040\u00048\u0006X\u0087\u0004\u00a2\u0006\u0006\n\u0004\b\u0016\u0010\nR8\u0010\u0017\u001a&\u0012\f\u0012\n \u0006*\u0004\u0018\u00010\u00050\u0005 \u0006*\u0012\u0012\f\u0012\n \u0006*\u0004\u0018\u00010\u00050\u0005\u0018\u00010\u00040\u00048\u0006X\u0087\u0004\u00a2\u0006\u0006\n\u0004\b\u0017\u0010\nR8\u0010\u0018\u001a&\u0012\f\u0012\n \u0006*\u0004\u0018\u00010\u00050\u0005 \u0006*\u0012\u0012\f\u0012\n \u0006*\u0004\u0018\u00010\u00050\u0005\u0018\u00010\u00040\u00048\u0006X\u0087\u0004\u00a2\u0006\u0006\n\u0004\b\u0018\u0010\nR8\u0010\u0019\u001a&\u0012\f\u0012\n \u0006*\u0004\u0018\u00010\u00050\u0005 \u0006*\u0012\u0012\f\u0012\n \u0006*\u0004\u0018\u00010\u00050\u0005\u0018\u00010\u00040\u00048\u0006X\u0087\u0004\u00a2\u0006\u0006\n\u0004\b\u0019\u0010\nR8\u0010\u001a\u001a&\u0012\f\u0012\n \u0006*\u0004\u0018\u00010\u00050\u0005 \u0006*\u0012\u0012\f\u0012\n \u0006*\u0004\u0018\u00010\u00050\u0005\u0018\u00010\u00040\u00048\u0006X\u0087\u0004\u00a2\u0006\u0006\n\u0004\b\u001a\u0010\nR8\u0010\u001b\u001a&\u0012\f\u0012\n \u0006*\u0004\u0018\u00010\u00050\u0005 \u0006*\u0012\u0012\f\u0012\n \u0006*\u0004\u0018\u00010\u00050\u0005\u0018\u00010\u00040\u00048\u0006X\u0087\u0004\u00a2\u0006\u0006\n\u0004\b\u001b\u0010\nR8\u0010\u001c\u001a&\u0012\f\u0012\n \u0006*\u0004\u0018\u00010\u00050\u0005 \u0006*\u0012\u0012\f\u0012\n \u0006*\u0004\u0018\u00010\u00050\u0005\u0018\u00010\u00040\u00048\u0006X\u0087\u0004\u00a2\u0006\u0006\n\u0004\b\u001c\u0010\nR8\u0010\u001d\u001a&\u0012\f\u0012\n \u0006*\u0004\u0018\u00010\u00050\u0005 \u0006*\u0012\u0012\f\u0012\n \u0006*\u0004\u0018\u00010\u00050\u0005\u0018\u00010\u00040\u00048\u0006X\u0087\u0004\u00a2\u0006\u0006\n\u0004\b\u001d\u0010\nR8\u0010\u001e\u001a&\u0012\f\u0012\n \u0006*\u0004\u0018\u00010\u00050\u0005 \u0006*\u0012\u0012\f\u0012\n \u0006*\u0004\u0018\u00010\u00050\u0005\u0018\u00010\u00040\u00048\u0006X\u0087\u0004\u00a2\u0006\u0006\n\u0004\b\u001e\u0010\nR8\u0010\u001f\u001a&\u0012\f\u0012\n \u0006*\u0004\u0018\u00010\u00050\u0005 \u0006*\u0012\u0012\f\u0012\n \u0006*\u0004\u0018\u00010\u00050\u0005\u0018\u00010\u00040\u00048\u0006X\u0087\u0004\u00a2\u0006\u0006\n\u0004\b\u001f\u0010\nR8\u0010 \u001a&\u0012\f\u0012\n \u0006*\u0004\u0018\u00010\u00050\u0005 \u0006*\u0012\u0012\f\u0012\n \u0006*\u0004\u0018\u00010\u00050\u0005\u0018\u00010\u00040\u00048\u0006X\u0087\u0004\u00a2\u0006\u0006\n\u0004\b \u0010\nR8\u0010!\u001a&\u0012\f\u0012\n \u0006*\u0004\u0018\u00010\u00050\u0005 \u0006*\u0012\u0012\f\u0012\n \u0006*\u0004\u0018\u00010\u00050\u0005\u0018\u00010\u00040\u00048\u0006X\u0087\u0004\u00a2\u0006\u0006\n\u0004\b!\u0010\nR8\u0010\"\u001a&\u0012\f\u0012\n \u0006*\u0004\u0018\u00010\u00050\u0005 \u0006*\u0012\u0012\f\u0012\n \u0006*\u0004\u0018\u00010\u00050\u0005\u0018\u00010\u00040\u00048\u0006X\u0087\u0004\u00a2\u0006\u0006\n\u0004\b\"\u0010\nR8\u0010#\u001a&\u0012\f\u0012\n \u0006*\u0004\u0018\u00010\u00050\u0005 \u0006*\u0012\u0012\f\u0012\n \u0006*\u0004\u0018\u00010\u00050\u0005\u0018\u00010\u00040\u00048\u0006X\u0087\u0004\u00a2\u0006\u0006\n\u0004\b#\u0010\nR8\u0010$\u001a&\u0012\f\u0012\n \u0006*\u0004\u0018\u00010\u00050\u0005 \u0006*\u0012\u0012\f\u0012\n \u0006*\u0004\u0018\u00010\u00050\u0005\u0018\u00010\u00040\u00048\u0006X\u0087\u0004\u00a2\u0006\u0006\n\u0004\b$\u0010\nR8\u0010%\u001a&\u0012\f\u0012\n \u0006*\u0004\u0018\u00010\u00050\u0005 \u0006*\u0012\u0012\f\u0012\n \u0006*\u0004\u0018\u00010\u00050\u0005\u0018\u00010\u00040\u00048\u0006X\u0087\u0004\u00a2\u0006\u0006\n\u0004\b%\u0010\nR8\u0010&\u001a&\u0012\f\u0012\n \u0006*\u0004\u0018\u00010\u00050\u0005 \u0006*\u0012\u0012\f\u0012\n \u0006*\u0004\u0018\u00010\u00050\u0005\u0018\u00010\u00040\u00048\u0006X\u0087\u0004\u00a2\u0006\u0006\n\u0004\b&\u0010\nR8\u0010'\u001a&\u0012\f\u0012\n \u0006*\u0004\u0018\u00010\u00050\u0005 \u0006*\u0012\u0012\f\u0012\n \u0006*\u0004\u0018\u00010\u00050\u0005\u0018\u00010\u00040\u00048\u0006X\u0087\u0004\u00a2\u0006\u0006\n\u0004\b'\u0010\nR8\u0010(\u001a&\u0012\f\u0012\n \u0006*\u0004\u0018\u00010\u00050\u0005 \u0006*\u0012\u0012\f\u0012\n \u0006*\u0004\u0018\u00010\u00050\u0005\u0018\u00010\u00040\u00048\u0006X\u0087\u0004\u00a2\u0006\u0006\n\u0004\b(\u0010\n\u00a8\u0006+"}, d2={"Lcom/cobblemon/mod/common/api/tags/CobblemonBiomeTags;", "", "", "path", "Lnet/minecraft/tags/TagKey;", "Lnet/minecraft/world/level/biome/Biome;", "kotlin.jvm.PlatformType", "create", "(Ljava/lang/String;)Lnet/minecraft/tags/TagKey;", "HAS_APRICORNS_DENSE", "Lnet/minecraft/tags/TagKey;", "HAS_APRICORNS_NORMAL", "HAS_APRICORNS_SPARSE", "HAS_DAWN_STONE_ORE", "HAS_DAWN_STONE_ORE_RARE", "HAS_DUSK_STONE_ORE", "HAS_DUSK_STONE_ORE_RARE", "HAS_FIRE_STONE_ORE", "HAS_FIRE_STONE_ORE_NETHER", "HAS_FIRE_STONE_ORE_RARE", "HAS_ICE_STONE_ORE", "HAS_ICE_STONE_ORE_RARE", "HAS_LEAF_STONE_ORE", "HAS_LEAF_STONE_ORE_RARE", "HAS_MOON_STONE_ORE", "HAS_MOON_STONE_ORE_DRIPSTONE", "HAS_MOON_STONE_ORE_RARE", "HAS_REVIVAL_HERBS", "HAS_SHINY_STONE_ORE", "HAS_SHINY_STONE_ORE_RARE", "HAS_SUN_STONE_ORE", "HAS_SUN_STONE_ORE_RARE", "HAS_THUNDER_STONE_ORE", "HAS_THUNDER_STONE_ORE_RARE", "HAS_WATER_STONE_ORE", "HAS_WATER_STONE_ORE_RARE", "IS_AUTUMN", "IS_SPRING", "IS_SUMMER", "IS_TEMPERATE", "IS_WINTER", "<init>", "()V", "common"})
public final class CobblemonBiomeTags {
    @NotNull
    public static final CobblemonBiomeTags INSTANCE = new CobblemonBiomeTags();
    @JvmField
    public static final TagKey<Biome> IS_AUTUMN = INSTANCE.create("has_season/autumn");
    @JvmField
    public static final TagKey<Biome> IS_SPRING = INSTANCE.create("has_season/spring");
    @JvmField
    public static final TagKey<Biome> IS_SUMMER = INSTANCE.create("has_season/summer");
    @JvmField
    public static final TagKey<Biome> IS_TEMPERATE = INSTANCE.create("is_temperate");
    @JvmField
    public static final TagKey<Biome> IS_WINTER = INSTANCE.create("has_season/winter");
    @JvmField
    public static final TagKey<Biome> HAS_APRICORNS_DENSE = INSTANCE.create("has_feature/apricorns_dense");
    @JvmField
    public static final TagKey<Biome> HAS_APRICORNS_NORMAL = INSTANCE.create("has_feature/apricorns_normal");
    @JvmField
    public static final TagKey<Biome> HAS_APRICORNS_SPARSE = INSTANCE.create("has_feature/apricorns_sparse");
    @JvmField
    public static final TagKey<Biome> HAS_REVIVAL_HERBS = INSTANCE.create("has_feature/revival_herbs");
    @JvmField
    public static final TagKey<Biome> HAS_DAWN_STONE_ORE = INSTANCE.create("has_ore/ore_dawn_stone_normal");
    @JvmField
    public static final TagKey<Biome> HAS_DAWN_STONE_ORE_RARE = INSTANCE.create("has_ore/ore_dawn_stone_rare");
    @JvmField
    public static final TagKey<Biome> HAS_DUSK_STONE_ORE = INSTANCE.create("has_ore/ore_dusk_stone_normal");
    @JvmField
    public static final TagKey<Biome> HAS_DUSK_STONE_ORE_RARE = INSTANCE.create("has_ore/ore_dusk_stone_rare");
    @JvmField
    public static final TagKey<Biome> HAS_FIRE_STONE_ORE = INSTANCE.create("has_ore/ore_fire_stone_normal");
    @JvmField
    public static final TagKey<Biome> HAS_FIRE_STONE_ORE_RARE = INSTANCE.create("has_ore/ore_fire_stone_rare");
    @JvmField
    public static final TagKey<Biome> HAS_FIRE_STONE_ORE_NETHER = INSTANCE.create("has_ore/ore_fire_stone_nether");
    @JvmField
    public static final TagKey<Biome> HAS_ICE_STONE_ORE = INSTANCE.create("has_ore/ore_ice_stone_normal");
    @JvmField
    public static final TagKey<Biome> HAS_ICE_STONE_ORE_RARE = INSTANCE.create("has_ore/ore_ice_stone_rare");
    @JvmField
    public static final TagKey<Biome> HAS_LEAF_STONE_ORE = INSTANCE.create("has_ore/ore_leaf_stone_normal");
    @JvmField
    public static final TagKey<Biome> HAS_LEAF_STONE_ORE_RARE = INSTANCE.create("has_ore/ore_leaf_stone_rare");
    @JvmField
    public static final TagKey<Biome> HAS_MOON_STONE_ORE = INSTANCE.create("has_ore/ore_moon_stone_normal");
    @JvmField
    public static final TagKey<Biome> HAS_MOON_STONE_ORE_RARE = INSTANCE.create("has_ore/ore_moon_stone_rare");
    @JvmField
    public static final TagKey<Biome> HAS_MOON_STONE_ORE_DRIPSTONE = INSTANCE.create("has_ore/ore_moon_stone_dripstone");
    @JvmField
    public static final TagKey<Biome> HAS_SHINY_STONE_ORE = INSTANCE.create("has_ore/ore_shiny_stone_normal");
    @JvmField
    public static final TagKey<Biome> HAS_SHINY_STONE_ORE_RARE = INSTANCE.create("has_ore/ore_shiny_stone_rare");
    @JvmField
    public static final TagKey<Biome> HAS_SUN_STONE_ORE = INSTANCE.create("has_ore/ore_sun_stone_normal");
    @JvmField
    public static final TagKey<Biome> HAS_SUN_STONE_ORE_RARE = INSTANCE.create("has_ore/ore_sun_stone_rare");
    @JvmField
    public static final TagKey<Biome> HAS_THUNDER_STONE_ORE = INSTANCE.create("has_ore/ore_thunder_stone_normal");
    @JvmField
    public static final TagKey<Biome> HAS_THUNDER_STONE_ORE_RARE = INSTANCE.create("has_ore/ore_thunder_stone_rare");
    @JvmField
    public static final TagKey<Biome> HAS_WATER_STONE_ORE = INSTANCE.create("has_ore/ore_water_stone_normal");
    @JvmField
    public static final TagKey<Biome> HAS_WATER_STONE_ORE_RARE = INSTANCE.create("has_ore/ore_water_stone_rare");

    private CobblemonBiomeTags() {
    }

    private final TagKey<Biome> create(String path) {
        return TagKey.m_203882_((ResourceKey)Registries.f_256952_, (ResourceLocation)MiscUtils.cobblemonResource(path));
    }
}

