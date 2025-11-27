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
 *  net.minecraft.world.level.block.Block
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
import net.minecraft.world.level.block.Block;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000\u001a\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b!\b\u00c6\u0002\u0018\u00002\u00020\u0001B\t\b\u0002\u00a2\u0006\u0004\b$\u0010%J;\u0010\u0007\u001a&\u0012\f\u0012\n \u0006*\u0004\u0018\u00010\u00050\u0005 \u0006*\u0012\u0012\f\u0012\n \u0006*\u0004\u0018\u00010\u00050\u0005\u0018\u00010\u00040\u00042\u0006\u0010\u0003\u001a\u00020\u0002H\u0002\u00a2\u0006\u0004\b\u0007\u0010\bR8\u0010\t\u001a&\u0012\f\u0012\n \u0006*\u0004\u0018\u00010\u00050\u0005 \u0006*\u0012\u0012\f\u0012\n \u0006*\u0004\u0018\u00010\u00050\u0005\u0018\u00010\u00040\u00048\u0006X\u0087\u0004\u00a2\u0006\u0006\n\u0004\b\t\u0010\nR8\u0010\u000b\u001a&\u0012\f\u0012\n \u0006*\u0004\u0018\u00010\u00050\u0005 \u0006*\u0012\u0012\f\u0012\n \u0006*\u0004\u0018\u00010\u00050\u0005\u0018\u00010\u00040\u00048\u0006X\u0087\u0004\u00a2\u0006\u0006\n\u0004\b\u000b\u0010\nR8\u0010\f\u001a&\u0012\f\u0012\n \u0006*\u0004\u0018\u00010\u00050\u0005 \u0006*\u0012\u0012\f\u0012\n \u0006*\u0004\u0018\u00010\u00050\u0005\u0018\u00010\u00040\u00048\u0006X\u0087\u0004\u00a2\u0006\u0006\n\u0004\b\f\u0010\nR8\u0010\r\u001a&\u0012\f\u0012\n \u0006*\u0004\u0018\u00010\u00050\u0005 \u0006*\u0012\u0012\f\u0012\n \u0006*\u0004\u0018\u00010\u00050\u0005\u0018\u00010\u00040\u00048\u0006X\u0087\u0004\u00a2\u0006\u0006\n\u0004\b\r\u0010\nR8\u0010\u000e\u001a&\u0012\f\u0012\n \u0006*\u0004\u0018\u00010\u00050\u0005 \u0006*\u0012\u0012\f\u0012\n \u0006*\u0004\u0018\u00010\u00050\u0005\u0018\u00010\u00040\u00048\u0006X\u0087\u0004\u00a2\u0006\u0006\n\u0004\b\u000e\u0010\nR8\u0010\u000f\u001a&\u0012\f\u0012\n \u0006*\u0004\u0018\u00010\u00050\u0005 \u0006*\u0012\u0012\f\u0012\n \u0006*\u0004\u0018\u00010\u00050\u0005\u0018\u00010\u00040\u00048\u0006X\u0087\u0004\u00a2\u0006\u0006\n\u0004\b\u000f\u0010\nR8\u0010\u0010\u001a&\u0012\f\u0012\n \u0006*\u0004\u0018\u00010\u00050\u0005 \u0006*\u0012\u0012\f\u0012\n \u0006*\u0004\u0018\u00010\u00050\u0005\u0018\u00010\u00040\u00048\u0006X\u0087\u0004\u00a2\u0006\u0006\n\u0004\b\u0010\u0010\nR8\u0010\u0011\u001a&\u0012\f\u0012\n \u0006*\u0004\u0018\u00010\u00050\u0005 \u0006*\u0012\u0012\f\u0012\n \u0006*\u0004\u0018\u00010\u00050\u0005\u0018\u00010\u00040\u00048\u0006X\u0087\u0004\u00a2\u0006\u0006\n\u0004\b\u0011\u0010\nR8\u0010\u0012\u001a&\u0012\f\u0012\n \u0006*\u0004\u0018\u00010\u00050\u0005 \u0006*\u0012\u0012\f\u0012\n \u0006*\u0004\u0018\u00010\u00050\u0005\u0018\u00010\u00040\u00048\u0006X\u0087\u0004\u00a2\u0006\u0006\n\u0004\b\u0012\u0010\nR8\u0010\u0013\u001a&\u0012\f\u0012\n \u0006*\u0004\u0018\u00010\u00050\u0005 \u0006*\u0012\u0012\f\u0012\n \u0006*\u0004\u0018\u00010\u00050\u0005\u0018\u00010\u00040\u00048\u0006X\u0087\u0004\u00a2\u0006\u0006\n\u0004\b\u0013\u0010\nR8\u0010\u0014\u001a&\u0012\f\u0012\n \u0006*\u0004\u0018\u00010\u00050\u0005 \u0006*\u0012\u0012\f\u0012\n \u0006*\u0004\u0018\u00010\u00050\u0005\u0018\u00010\u00040\u00048\u0006X\u0087\u0004\u00a2\u0006\u0006\n\u0004\b\u0014\u0010\nR8\u0010\u0015\u001a&\u0012\f\u0012\n \u0006*\u0004\u0018\u00010\u00050\u0005 \u0006*\u0012\u0012\f\u0012\n \u0006*\u0004\u0018\u00010\u00050\u0005\u0018\u00010\u00040\u00048\u0006X\u0087\u0004\u00a2\u0006\u0006\n\u0004\b\u0015\u0010\nR8\u0010\u0016\u001a&\u0012\f\u0012\n \u0006*\u0004\u0018\u00010\u00050\u0005 \u0006*\u0012\u0012\f\u0012\n \u0006*\u0004\u0018\u00010\u00050\u0005\u0018\u00010\u00040\u00048\u0006X\u0087\u0004\u00a2\u0006\u0006\n\u0004\b\u0016\u0010\nR8\u0010\u0017\u001a&\u0012\f\u0012\n \u0006*\u0004\u0018\u00010\u00050\u0005 \u0006*\u0012\u0012\f\u0012\n \u0006*\u0004\u0018\u00010\u00050\u0005\u0018\u00010\u00040\u00048\u0006X\u0087\u0004\u00a2\u0006\u0006\n\u0004\b\u0017\u0010\nR8\u0010\u0018\u001a&\u0012\f\u0012\n \u0006*\u0004\u0018\u00010\u00050\u0005 \u0006*\u0012\u0012\f\u0012\n \u0006*\u0004\u0018\u00010\u00050\u0005\u0018\u00010\u00040\u00048\u0006X\u0087\u0004\u00a2\u0006\u0006\n\u0004\b\u0018\u0010\nR8\u0010\u0019\u001a&\u0012\f\u0012\n \u0006*\u0004\u0018\u00010\u00050\u0005 \u0006*\u0012\u0012\f\u0012\n \u0006*\u0004\u0018\u00010\u00050\u0005\u0018\u00010\u00040\u00048\u0006X\u0087\u0004\u00a2\u0006\u0006\n\u0004\b\u0019\u0010\nR8\u0010\u001a\u001a&\u0012\f\u0012\n \u0006*\u0004\u0018\u00010\u00050\u0005 \u0006*\u0012\u0012\f\u0012\n \u0006*\u0004\u0018\u00010\u00050\u0005\u0018\u00010\u00040\u00048\u0006X\u0087\u0004\u00a2\u0006\u0006\n\u0004\b\u001a\u0010\nR8\u0010\u001b\u001a&\u0012\f\u0012\n \u0006*\u0004\u0018\u00010\u00050\u0005 \u0006*\u0012\u0012\f\u0012\n \u0006*\u0004\u0018\u00010\u00050\u0005\u0018\u00010\u00040\u00048\u0006X\u0087\u0004\u00a2\u0006\u0006\n\u0004\b\u001b\u0010\nR8\u0010\u001c\u001a&\u0012\f\u0012\n \u0006*\u0004\u0018\u00010\u00050\u0005 \u0006*\u0012\u0012\f\u0012\n \u0006*\u0004\u0018\u00010\u00050\u0005\u0018\u00010\u00040\u00048\u0006X\u0087\u0004\u00a2\u0006\u0006\n\u0004\b\u001c\u0010\nR8\u0010\u001d\u001a&\u0012\f\u0012\n \u0006*\u0004\u0018\u00010\u00050\u0005 \u0006*\u0012\u0012\f\u0012\n \u0006*\u0004\u0018\u00010\u00050\u0005\u0018\u00010\u00040\u00048\u0006X\u0087\u0004\u00a2\u0006\u0006\n\u0004\b\u001d\u0010\nR8\u0010\u001e\u001a&\u0012\f\u0012\n \u0006*\u0004\u0018\u00010\u00050\u0005 \u0006*\u0012\u0012\f\u0012\n \u0006*\u0004\u0018\u00010\u00050\u0005\u0018\u00010\u00040\u00048\u0006X\u0087\u0004\u00a2\u0006\u0006\n\u0004\b\u001e\u0010\nR8\u0010\u001f\u001a&\u0012\f\u0012\n \u0006*\u0004\u0018\u00010\u00050\u0005 \u0006*\u0012\u0012\f\u0012\n \u0006*\u0004\u0018\u00010\u00050\u0005\u0018\u00010\u00040\u00048\u0006X\u0087\u0004\u00a2\u0006\u0006\n\u0004\b\u001f\u0010\nR8\u0010 \u001a&\u0012\f\u0012\n \u0006*\u0004\u0018\u00010\u00050\u0005 \u0006*\u0012\u0012\f\u0012\n \u0006*\u0004\u0018\u00010\u00050\u0005\u0018\u00010\u00040\u00048\u0006X\u0087\u0004\u00a2\u0006\u0006\n\u0004\b \u0010\nR8\u0010!\u001a&\u0012\f\u0012\n \u0006*\u0004\u0018\u00010\u00050\u0005 \u0006*\u0012\u0012\f\u0012\n \u0006*\u0004\u0018\u00010\u00050\u0005\u0018\u00010\u00040\u00048\u0006X\u0087\u0004\u00a2\u0006\u0006\n\u0004\b!\u0010\nR8\u0010\"\u001a&\u0012\f\u0012\n \u0006*\u0004\u0018\u00010\u00050\u0005 \u0006*\u0012\u0012\f\u0012\n \u0006*\u0004\u0018\u00010\u00050\u0005\u0018\u00010\u00040\u00048\u0006X\u0087\u0004\u00a2\u0006\u0006\n\u0004\b\"\u0010\nR8\u0010#\u001a&\u0012\f\u0012\n \u0006*\u0004\u0018\u00010\u00050\u0005 \u0006*\u0012\u0012\f\u0012\n \u0006*\u0004\u0018\u00010\u00050\u0005\u0018\u00010\u00040\u00048\u0006X\u0087\u0004\u00a2\u0006\u0006\n\u0004\b#\u0010\n\u00a8\u0006&"}, d2={"Lcom/cobblemon/mod/common/api/tags/CobblemonBlockTags;", "", "", "name", "Lnet/minecraft/tags/TagKey;", "Lnet/minecraft/world/level/block/Block;", "kotlin.jvm.PlatformType", "createTag", "(Ljava/lang/String;)Lnet/minecraft/tags/TagKey;", "ALL_HANGING_SIGNS", "Lnet/minecraft/tags/TagKey;", "ALL_SIGNS", "APRICORNS", "APRICORN_LEAVES", "APRICORN_LOGS", "APRICORN_SAPLINGS", "BERRY_REPLACEABLE", "BERRY_SOIL", "BERRY_WILD_SOIL", "CEILING_HANGING_SIGNS", "CROPS", "DRIPSTONE_GROWABLE", "DRIPSTONE_REPLACEABLES", "FLOWERS", "MEDICINAL_LEEK_PLANTABLE", "MINTS", "ROOTS", "ROOTS_SPREADABLE", "SEES_SKY", "SIGNS", "SMALL_FLOWERS", "SNOW_BLOCK", "STANDING_SIGNS", "TUMBLESTONE_HEAT_SOURCE", "WALL_HANGING_SIGNS", "WALL_SIGNS", "<init>", "()V", "common"})
public final class CobblemonBlockTags {
    @NotNull
    public static final CobblemonBlockTags INSTANCE = new CobblemonBlockTags();
    @JvmField
    public static final TagKey<Block> ALL_HANGING_SIGNS = INSTANCE.createTag("all_hanging_signs");
    @JvmField
    public static final TagKey<Block> ALL_SIGNS = INSTANCE.createTag("all_signs");
    @JvmField
    public static final TagKey<Block> APRICORN_LEAVES = INSTANCE.createTag("apricorn_leaves");
    @JvmField
    public static final TagKey<Block> APRICORN_LOGS = INSTANCE.createTag("apricorn_logs");
    @JvmField
    public static final TagKey<Block> APRICORN_SAPLINGS = INSTANCE.createTag("apricorn_saplings");
    @JvmField
    public static final TagKey<Block> APRICORNS = INSTANCE.createTag("apricorns");
    @JvmField
    public static final TagKey<Block> BERRY_WILD_SOIL = INSTANCE.createTag("berry_wild_soil");
    @JvmField
    public static final TagKey<Block> BERRY_SOIL = INSTANCE.createTag("berry_soil");
    @JvmField
    public static final TagKey<Block> BERRY_REPLACEABLE = INSTANCE.createTag("berry_replaceable");
    @JvmField
    public static final TagKey<Block> CEILING_HANGING_SIGNS = INSTANCE.createTag("ceiling_hanging_signs");
    @JvmField
    public static final TagKey<Block> CROPS = INSTANCE.createTag("crops");
    @JvmField
    public static final TagKey<Block> DRIPSTONE_GROWABLE = INSTANCE.createTag("dripstone_growable");
    @JvmField
    public static final TagKey<Block> DRIPSTONE_REPLACEABLES = INSTANCE.createTag("dripstone_replaceables");
    @JvmField
    public static final TagKey<Block> FLOWERS = INSTANCE.createTag("flowers");
    @JvmField
    public static final TagKey<Block> MEDICINAL_LEEK_PLANTABLE = INSTANCE.createTag("medicinal_leek_plantable");
    @JvmField
    public static final TagKey<Block> MINTS = INSTANCE.createTag("mints");
    @JvmField
    public static final TagKey<Block> ROOTS_SPREADABLE = INSTANCE.createTag("roots_spreadable");
    @JvmField
    public static final TagKey<Block> SIGNS = INSTANCE.createTag("signs");
    @JvmField
    public static final TagKey<Block> SMALL_FLOWERS = INSTANCE.createTag("small_flowers");
    @JvmField
    public static final TagKey<Block> SEES_SKY = INSTANCE.createTag("sees_sky");
    @JvmField
    public static final TagKey<Block> SNOW_BLOCK = INSTANCE.createTag("snow_block");
    @JvmField
    public static final TagKey<Block> ROOTS = INSTANCE.createTag("roots");
    @JvmField
    public static final TagKey<Block> STANDING_SIGNS = INSTANCE.createTag("standing_signs");
    @JvmField
    public static final TagKey<Block> TUMBLESTONE_HEAT_SOURCE = INSTANCE.createTag("tumblestone_heat_source");
    @JvmField
    public static final TagKey<Block> WALL_HANGING_SIGNS = INSTANCE.createTag("wall_hanging_signs");
    @JvmField
    public static final TagKey<Block> WALL_SIGNS = INSTANCE.createTag("wall_signs");

    private CobblemonBlockTags() {
    }

    private final TagKey<Block> createTag(String name) {
        return TagKey.m_203882_((ResourceKey)Registries.f_256747_, (ResourceLocation)MiscUtils.cobblemonResource(name));
    }
}

