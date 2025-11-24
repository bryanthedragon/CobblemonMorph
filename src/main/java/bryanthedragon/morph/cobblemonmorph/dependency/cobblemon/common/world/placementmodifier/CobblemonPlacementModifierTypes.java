/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.serialization.Codec
 *  kotlin.Metadata
 *  kotlin.jvm.JvmField
 *  kotlin.jvm.internal.Intrinsics
 *  net.minecraft.core.Registry
 *  net.minecraft.core.registries.BuiltInRegistries
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.world.level.levelgen.placement.PlacementModifier
 *  net.minecraft.world.level.levelgen.placement.PlacementModifierType
 *  org.jetbrains.annotations.NotNull
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.world.placementmodifier;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.MiscUtilsKt;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.world.placementmodifier.BeneathHeightmapPlacementModifier;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.world.placementmodifier.ConditionalCountPlacementModifier;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.world.placementmodifier.ConditionalRarityFilterPlacementModifier;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.world.placementmodifier.LocatePredicatePlacementModifier;
import com.mojang.serialization.Codec;
import kotlin.Metadata;
import kotlin.jvm.JvmField;
import kotlin.jvm.internal.Intrinsics;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.levelgen.placement.PlacementModifier;
import net.minecraft.world.level.levelgen.placement.PlacementModifierType;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000F\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\b\u00c6\u0002\u0018\u00002\u00020\u0001B\t\b\u0002\u00a2\u0006\u0004\b\u0017\u0010\rJ3\u0010\t\u001a\b\u0012\u0004\u0012\u00028\u00000\b\"\b\b\u0000\u0010\u0003*\u00020\u00022\u0006\u0010\u0005\u001a\u00020\u00042\f\u0010\u0007\u001a\b\u0012\u0004\u0012\u00028\u00000\u0006\u00a2\u0006\u0004\b\t\u0010\nJ\r\u0010\f\u001a\u00020\u000b\u00a2\u0006\u0004\b\f\u0010\rR\u001a\u0010\u000f\u001a\b\u0012\u0004\u0012\u00020\u000e0\b8\u0006X\u0087\u0004\u00a2\u0006\u0006\n\u0004\b\u000f\u0010\u0010R\u001a\u0010\u0012\u001a\b\u0012\u0004\u0012\u00020\u00110\b8\u0006X\u0087\u0004\u00a2\u0006\u0006\n\u0004\b\u0012\u0010\u0010R\u001a\u0010\u0014\u001a\b\u0012\u0004\u0012\u00020\u00130\b8\u0006X\u0087\u0004\u00a2\u0006\u0006\n\u0004\b\u0014\u0010\u0010R\u001a\u0010\u0016\u001a\b\u0012\u0004\u0012\u00020\u00150\b8\u0006X\u0087\u0004\u00a2\u0006\u0006\n\u0004\b\u0016\u0010\u0010\u00a8\u0006\u0018"}, d2={"Lcom/cobblemon/mod/common/world/placementmodifier/CobblemonPlacementModifierTypes;", "", "Lnet/minecraft/world/level/levelgen/placement/PlacementModifier;", "T", "", "id", "Lcom/mojang/serialization/Codec;", "codec", "Lnet/minecraft/world/level/levelgen/placement/PlacementModifierType;", "register", "(Ljava/lang/String;Lcom/mojang/serialization/Codec;)Lnet/minecraft/world/level/levelgen/placement/PlacementModifierType;", "", "touch", "()V", "Lcom/cobblemon/mod/common/world/placementmodifier/BeneathHeightmapPlacementModifier;", "BENEATH_HEIGHTMAP", "Lnet/minecraft/world/level/levelgen/placement/PlacementModifierType;", "Lcom/cobblemon/mod/common/world/placementmodifier/ConditionalCountPlacementModifier;", "CONDITIONAL_COUNT", "Lcom/cobblemon/mod/common/world/placementmodifier/ConditionalRarityFilterPlacementModifier;", "CONDITIONAL_RARITY_FILTER", "Lcom/cobblemon/mod/common/world/placementmodifier/LocatePredicatePlacementModifier;", "LOCATE_PREDICATE", "<init>", "common"})
public final class CobblemonPlacementModifierTypes {
    @NotNull
    public static final CobblemonPlacementModifierTypes INSTANCE = new CobblemonPlacementModifierTypes();
    @JvmField
    @NotNull
    public static final PlacementModifierType<BeneathHeightmapPlacementModifier> BENEATH_HEIGHTMAP = INSTANCE.register("beneath_heightmap", BeneathHeightmapPlacementModifier.Companion.getMODIFIER_CODEC());
    @JvmField
    @NotNull
    public static final PlacementModifierType<LocatePredicatePlacementModifier> LOCATE_PREDICATE = INSTANCE.register("locate_predicate", LocatePredicatePlacementModifier.Companion.getMODIFIER_CODEC());
    @JvmField
    @NotNull
    public static final PlacementModifierType<ConditionalCountPlacementModifier> CONDITIONAL_COUNT = INSTANCE.register("conditional_count", ConditionalCountPlacementModifier.Companion.getMODIFIER_CODEC());
    @JvmField
    @NotNull
    public static final PlacementModifierType<ConditionalRarityFilterPlacementModifier> CONDITIONAL_RARITY_FILTER = INSTANCE.register("conditional_rarity_filter", ConditionalRarityFilterPlacementModifier.Companion.getMODIFIER_CODEC());

    private CobblemonPlacementModifierTypes() {
    }

    @NotNull
    public final <T extends PlacementModifier> PlacementModifierType<T> register(@NotNull String id, @NotNull Codec<T> codec2) {
        Intrinsics.checkNotNullParameter((Object)id, (String)"id");
        Intrinsics.checkNotNullParameter(codec2, (String)"codec");
        Object object = Registry.m_122965_((Registry)BuiltInRegistries.f_256986_, (ResourceLocation)MiscUtilsKt.cobblemonResource(id), () -> CobblemonPlacementModifierTypes.register$lambda$0(codec2));
        Intrinsics.checkNotNullExpressionValue((Object)object, (String)"register(Registries.PLAC\u2026ntModifierType { codec })");
        return (PlacementModifierType)object;
    }

    public final void touch() {
    }

    private static final Codec register$lambda$0(Codec $codec) {
        Intrinsics.checkNotNullParameter((Object)$codec, (String)"$codec");
        return $codec;
    }
}

