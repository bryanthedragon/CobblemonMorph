/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.datafixers.kinds.App
 *  com.mojang.datafixers.kinds.Applicative
 *  com.mojang.serialization.Codec
 *  com.mojang.serialization.codecs.PrimitiveCodec
 *  com.mojang.serialization.codecs.RecordCodecBuilder
 *  com.mojang.serialization.codecs.RecordCodecBuilder$Instance
 *  kotlin.Metadata
 *  kotlin.Unit
 *  kotlin.jvm.internal.DefaultConstructorMarker
 *  kotlin.jvm.internal.Intrinsics
 *  kotlin.jvm.internal.SourceDebugExtension
 *  net.minecraft.core.BlockPos
 *  net.minecraft.util.RandomSource
 *  net.minecraft.world.level.levelgen.Heightmap$Types
 *  net.minecraft.world.level.levelgen.placement.PlacementContext
 *  net.minecraft.world.level.levelgen.placement.PlacementModifier
 *  net.minecraft.world.level.levelgen.placement.PlacementModifierType
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.world.placementmodifier;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.world.placementmodifier.CobblemonPlacementModifierTypes;
import com.mojang.datafixers.kinds.App;
import com.mojang.datafixers.kinds.Applicative;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.PrimitiveCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.placement.PlacementContext;
import net.minecraft.world.level.levelgen.placement.PlacementModifier;
import net.minecraft.world.level.levelgen.placement.PlacementModifierType;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000:\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\b\n\u0002\b\f\u0018\u0000 \u001e2\u00020\u0001:\u0001\u001eB!\u0012\u0006\u0010\u000f\u001a\u00020\u000e\u0012\u0006\u0010\u0014\u001a\u00020\u0013\u0012\b\u0010\u0018\u001a\u0004\u0018\u00010\u0013\u00a2\u0006\u0004\b\u001c\u0010\u001dJ-\u0010\t\u001a\b\u0012\u0004\u0012\u00020\u00060\b2\u0006\u0010\u0003\u001a\u00020\u00022\u0006\u0010\u0005\u001a\u00020\u00042\u0006\u0010\u0007\u001a\u00020\u0006H\u0016\u00a2\u0006\u0004\b\t\u0010\nJ\u0015\u0010\f\u001a\b\u0012\u0004\u0012\u00020\u00000\u000bH\u0016\u00a2\u0006\u0004\b\f\u0010\rR\u0017\u0010\u000f\u001a\u00020\u000e8\u0006\u00a2\u0006\f\n\u0004\b\u000f\u0010\u0010\u001a\u0004\b\u0011\u0010\u0012R\u0017\u0010\u0014\u001a\u00020\u00138\u0006\u00a2\u0006\f\n\u0004\b\u0014\u0010\u0015\u001a\u0004\b\u0016\u0010\u0017R\u0019\u0010\u0018\u001a\u0004\u0018\u00010\u00138\u0006\u00a2\u0006\f\n\u0004\b\u0018\u0010\u0019\u001a\u0004\b\u001a\u0010\u001b\u00a8\u0006\u001f"}, d2={"Lcom/cobblemon/mod/common/world/placementmodifier/BeneathHeightmapPlacementModifier;", "Lnet/minecraft/world/level/levelgen/placement/PlacementModifier;", "Lnet/minecraft/world/level/levelgen/placement/PlacementContext;", "context", "Lnet/minecraft/util/RandomSource;", "random", "Lnet/minecraft/core/BlockPos;", "pos", "Ljava/util/stream/Stream;", "getPositions", "(Lnet/minecraft/world/level/levelgen/placement/PlacementContext;Lnet/minecraft/util/RandomSource;Lnet/minecraft/core/BlockPos;)Ljava/util/stream/Stream;", "Lnet/minecraft/world/level/levelgen/placement/PlacementModifierType;", "getType", "()Lnet/minecraft/world/level/levelgen/placement/PlacementModifierType;", "Lnet/minecraft/world/Heightmap$Type;", "heightmap", "Lnet/minecraft/world/level/levelgen/Heightmap$Types;", "getHeightmap", "()Lnet/minecraft/world/level/levelgen/Heightmap$Types;", "", "offset", "I", "getOffset", "()I", "reach", "Ljava/lang/Integer;", "getReach", "()Ljava/lang/Integer;", "<init>", "(Lnet/minecraft/world/level/levelgen/Heightmap$Types;ILjava/lang/Integer;)V", "Companion", "common"})
@SourceDebugExtension(value={"SMAP\nBeneathHeightmapPlacementModifier.kt\nKotlin\n*S Kotlin\n*F\n+ 1 BeneathHeightmapPlacementModifier.kt\ncom/cobblemon/mod/common/world/placementmodifier/BeneathHeightmapPlacementModifier\n+ 2 fake.kt\nkotlin/jvm/internal/FakeKt\n*L\n1#1,63:1\n1#2:64\n*E\n"})
public final class BeneathHeightmapPlacementModifier
extends PlacementModifier {
    @NotNull
    public static final Companion Companion = new Companion(null);
    @NotNull
    private final Heightmap.Types heightmap;
    private final int offset;
    @Nullable
    private final Integer reach;
    @NotNull
    private static final Codec<BeneathHeightmapPlacementModifier> MODIFIER_CODEC;

    public BeneathHeightmapPlacementModifier(@NotNull Heightmap.Types heightmap, int offset, @Nullable Integer reach) {
        Intrinsics.checkNotNullParameter((Object)heightmap, (String)"heightmap");
        this.heightmap = heightmap;
        this.offset = offset;
        this.reach = reach;
    }

    @NotNull
    public final Heightmap.Types getHeightmap() {
        return this.heightmap;
    }

    public final int getOffset() {
        return this.offset;
    }

    @Nullable
    public final Integer getReach() {
        return this.reach;
    }

    @NotNull
    public PlacementModifierType<BeneathHeightmapPlacementModifier> m_183327_() {
        return CobblemonPlacementModifierTypes.BENEATH_HEIGHTMAP;
    }

    /*
     * WARNING - void declaration
     */
    @NotNull
    public Stream<BlockPos> m_213676_(@NotNull PlacementContext context, @NotNull RandomSource random, @NotNull BlockPos pos) {
        void it;
        int n;
        Intrinsics.checkNotNullParameter((Object)context, (String)"context");
        Intrinsics.checkNotNullParameter((Object)random, (String)"random");
        Intrinsics.checkNotNullParameter((Object)pos, (String)"pos");
        int z = 0;
        int x = pos.m_123341_();
        int n2 = n = pos.m_123343_();
        int n3 = x;
        Heightmap.Types types = this.heightmap;
        PlacementContext placementContext = context;
        boolean bl = false;
        z = it;
        Unit unit = Unit.INSTANCE;
        int topY = placementContext.m_191824_(types, n3, n) + this.offset;
        List positions = new ArrayList();
        for (int y = topY; y > context.m_191830_() && (this.reach == null || topY - y < this.reach); --y) {
            positions.add(new BlockPos(x, y, z));
        }
        Stream<BlockPos> stream = positions.stream();
        Intrinsics.checkNotNullExpressionValue(stream, (String)"positions.stream()");
        return stream;
    }

    private static final Heightmap.Types MODIFIER_CODEC$lambda$5$lambda$1(BeneathHeightmapPlacementModifier it) {
        return it.heightmap;
    }

    private static final Integer MODIFIER_CODEC$lambda$5$lambda$2(BeneathHeightmapPlacementModifier it) {
        return it.offset;
    }

    private static final Optional MODIFIER_CODEC$lambda$5$lambda$3(BeneathHeightmapPlacementModifier it) {
        return Optional.ofNullable(it.reach);
    }

    private static final BeneathHeightmapPlacementModifier MODIFIER_CODEC$lambda$5$lambda$4(Heightmap.Types heightmap, Integer offset, Optional reach) {
        Intrinsics.checkNotNullExpressionValue((Object)heightmap, (String)"heightmap");
        Intrinsics.checkNotNullExpressionValue((Object)offset, (String)"offset");
        return new BeneathHeightmapPlacementModifier(heightmap, offset, reach.orElse(null));
    }

    private static final App MODIFIER_CODEC$lambda$5(RecordCodecBuilder.Instance instance) {
        return instance.group((App)Heightmap.Types.f_64274_.fieldOf("heightmap").forGetter(BeneathHeightmapPlacementModifier::MODIFIER_CODEC$lambda$5$lambda$1), (App)PrimitiveCodec.INT.fieldOf("offset").forGetter(BeneathHeightmapPlacementModifier::MODIFIER_CODEC$lambda$5$lambda$2), (App)PrimitiveCodec.INT.optionalFieldOf("reach").forGetter(BeneathHeightmapPlacementModifier::MODIFIER_CODEC$lambda$5$lambda$3)).apply((Applicative)instance, BeneathHeightmapPlacementModifier::MODIFIER_CODEC$lambda$5$lambda$4);
    }

    static {
        Codec codec2 = RecordCodecBuilder.create(BeneathHeightmapPlacementModifier::MODIFIER_CODEC$lambda$5);
        Intrinsics.checkNotNullExpressionValue((Object)codec2, (String)"create { instance ->\n   \u2026              }\n        }");
        MODIFIER_CODEC = codec2;
    }

    @Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0007\b\u0086\u0003\u0018\u00002\u00020\u0001B\t\b\u0002\u00a2\u0006\u0004\b\b\u0010\tR\u001d\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00030\u00028\u0006\u00a2\u0006\f\n\u0004\b\u0004\u0010\u0005\u001a\u0004\b\u0006\u0010\u0007\u00a8\u0006\n"}, d2={"Lcom/cobblemon/mod/common/world/placementmodifier/BeneathHeightmapPlacementModifier$Companion;", "", "Lcom/mojang/serialization/Codec;", "Lcom/cobblemon/mod/common/world/placementmodifier/BeneathHeightmapPlacementModifier;", "MODIFIER_CODEC", "Lcom/mojang/serialization/Codec;", "getMODIFIER_CODEC", "()Lcom/mojang/serialization/Codec;", "<init>", "()V", "common"})
    public static final class Companion {
        private Companion() {
        }

        @NotNull
        public final Codec<BeneathHeightmapPlacementModifier> getMODIFIER_CODEC() {
            return MODIFIER_CODEC;
        }

        public /* synthetic */ Companion(DefaultConstructorMarker $constructor_marker) {
            this();
        }
    }
}

