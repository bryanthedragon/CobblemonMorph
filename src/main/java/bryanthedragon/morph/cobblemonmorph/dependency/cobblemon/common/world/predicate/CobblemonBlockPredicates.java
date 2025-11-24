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
 *  net.minecraft.world.level.levelgen.blockpredicates.BlockPredicate
 *  net.minecraft.world.level.levelgen.blockpredicates.BlockPredicateType
 *  org.jetbrains.annotations.NotNull
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.world.predicate;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.MiscUtilsKt;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.world.predicate.AltitudePredicate;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.world.predicate.BiomePredicate;
import com.mojang.serialization.Codec;
import kotlin.Metadata;
import kotlin.jvm.JvmField;
import kotlin.jvm.internal.Intrinsics;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.levelgen.blockpredicates.BlockPredicate;
import net.minecraft.world.level.levelgen.blockpredicates.BlockPredicateType;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000:\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\b\u00c6\u0002\u0018\u00002\u00020\u0001B\t\b\u0002\u00a2\u0006\u0004\b\u0015\u0010\rJ5\u0010\t\u001a\b\u0012\u0004\u0012\u00028\u00000\b\"\n\b\u0000\u0010\u0003*\u0004\u0018\u00010\u00022\u0006\u0010\u0005\u001a\u00020\u00042\f\u0010\u0007\u001a\b\u0012\u0004\u0012\u00028\u00000\u0006\u00a2\u0006\u0004\b\t\u0010\nJ\r\u0010\f\u001a\u00020\u000b\u00a2\u0006\u0004\b\f\u0010\rR\u001a\u0010\u000f\u001a\b\u0012\u0004\u0012\u00020\u000e0\b8\u0006X\u0087\u0004\u00a2\u0006\u0006\n\u0004\b\u000f\u0010\u0010R\u001d\u0010\u0012\u001a\b\u0012\u0004\u0012\u00020\u00110\b8\u0006\u00a2\u0006\f\n\u0004\b\u0012\u0010\u0010\u001a\u0004\b\u0013\u0010\u0014\u00a8\u0006\u0016"}, d2={"Lcom/cobblemon/mod/common/world/predicate/CobblemonBlockPredicates;", "", "Lnet/minecraft/world/level/levelgen/blockpredicates/BlockPredicate;", "P", "", "id", "Lcom/mojang/serialization/Codec;", "codec", "Lnet/minecraft/world/level/levelgen/blockpredicates/BlockPredicateType;", "register", "(Ljava/lang/String;Lcom/mojang/serialization/Codec;)Lnet/minecraft/world/level/levelgen/blockpredicates/BlockPredicateType;", "", "touch", "()V", "Lcom/cobblemon/mod/common/world/predicate/AltitudePredicate;", "ALTITUDE", "Lnet/minecraft/world/level/levelgen/blockpredicates/BlockPredicateType;", "Lcom/cobblemon/mod/common/world/predicate/BiomePredicate;", "BIOME", "getBIOME", "()Lnet/minecraft/world/level/levelgen/blockpredicates/BlockPredicateType;", "<init>", "common"})
public final class CobblemonBlockPredicates {
    @NotNull
    public static final CobblemonBlockPredicates INSTANCE = new CobblemonBlockPredicates();
    @JvmField
    @NotNull
    public static final BlockPredicateType<AltitudePredicate> ALTITUDE = INSTANCE.register("altitude", AltitudePredicate.Companion.getCODEC());
    @NotNull
    private static final BlockPredicateType<BiomePredicate> BIOME = INSTANCE.register("biome", BiomePredicate.Companion.getCODEC());

    private CobblemonBlockPredicates() {
    }

    @NotNull
    public final BlockPredicateType<BiomePredicate> getBIOME() {
        return BIOME;
    }

    @NotNull
    public final <P extends BlockPredicate> BlockPredicateType<P> register(@NotNull String id, @NotNull Codec<P> codec2) {
        Intrinsics.checkNotNullParameter((Object)id, (String)"id");
        Intrinsics.checkNotNullParameter(codec2, (String)"codec");
        Object object = Registry.m_122965_((Registry)BuiltInRegistries.f_256906_, (ResourceLocation)MiscUtilsKt.cobblemonResource(id), () -> CobblemonBlockPredicates.register$lambda$0(codec2));
        Intrinsics.checkNotNullExpressionValue((Object)object, (String)"register(Registries.BLOC\u2026kPredicateType { codec })");
        return (BlockPredicateType)object;
    }

    public final void touch() {
    }

    private static final Codec register$lambda$0(Codec $codec) {
        Intrinsics.checkNotNullParameter((Object)$codec, (String)"$codec");
        return $codec;
    }
}

