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
 *  kotlin.jvm.internal.DefaultConstructorMarker
 *  kotlin.jvm.internal.Intrinsics
 *  net.minecraft.core.BlockPos
 *  net.minecraft.util.RandomSource
 *  net.minecraft.world.level.levelgen.blockpredicates.BlockPredicate
 *  net.minecraft.world.level.levelgen.placement.PlacementContext
 *  net.minecraft.world.level.levelgen.placement.PlacementModifier
 *  net.minecraft.world.level.levelgen.placement.PlacementModifierType
 *  org.jetbrains.annotations.NotNull
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.world.placementmodifier;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.world.placementmodifier.CobblemonPlacementModifierTypes;
import com.mojang.datafixers.kinds.App;
import com.mojang.datafixers.kinds.Applicative;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.PrimitiveCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.stream.Stream;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.levelgen.blockpredicates.BlockPredicate;
import net.minecraft.world.level.levelgen.placement.PlacementContext;
import net.minecraft.world.level.levelgen.placement.PlacementModifier;
import net.minecraft.world.level.levelgen.placement.PlacementModifierType;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000:\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\f\u0018\u0000 \u001e2\u00020\u0001:\u0001\u001eB'\u0012\u0006\u0010\u0014\u001a\u00020\u0013\u0012\u0006\u0010\u000f\u001a\u00020\u000e\u0012\u0006\u0010\u0018\u001a\u00020\u000e\u0012\u0006\u0010\u001a\u001a\u00020\u000e\u00a2\u0006\u0004\b\u001c\u0010\u001dJ-\u0010\t\u001a\b\u0012\u0004\u0012\u00020\u00060\b2\u0006\u0010\u0003\u001a\u00020\u00022\u0006\u0010\u0005\u001a\u00020\u00042\u0006\u0010\u0007\u001a\u00020\u0006H\u0016\u00a2\u0006\u0004\b\t\u0010\nJ\u0013\u0010\f\u001a\u0006\u0012\u0002\b\u00030\u000bH\u0016\u00a2\u0006\u0004\b\f\u0010\rR\u0017\u0010\u000f\u001a\u00020\u000e8\u0006\u00a2\u0006\f\n\u0004\b\u000f\u0010\u0010\u001a\u0004\b\u0011\u0010\u0012R\u0017\u0010\u0014\u001a\u00020\u00138\u0006\u00a2\u0006\f\n\u0004\b\u0014\u0010\u0015\u001a\u0004\b\u0016\u0010\u0017R\u0017\u0010\u0018\u001a\u00020\u000e8\u0006\u00a2\u0006\f\n\u0004\b\u0018\u0010\u0010\u001a\u0004\b\u0019\u0010\u0012R\u0017\u0010\u001a\u001a\u00020\u000e8\u0006\u00a2\u0006\f\n\u0004\b\u001a\u0010\u0010\u001a\u0004\b\u001b\u0010\u0012\u00a8\u0006\u001f"}, d2={"Lcom/cobblemon/mod/common/world/placementmodifier/LocatePredicatePlacementModifier;", "Lnet/minecraft/world/level/levelgen/placement/PlacementModifier;", "Lnet/minecraft/world/level/levelgen/placement/PlacementContext;", "context", "Lnet/minecraft/util/RandomSource;", "random", "Lnet/minecraft/core/BlockPos;", "pos", "Ljava/util/stream/Stream;", "getPositions", "(Lnet/minecraft/world/level/levelgen/placement/PlacementContext;Lnet/minecraft/util/RandomSource;Lnet/minecraft/core/BlockPos;)Ljava/util/stream/Stream;", "Lnet/minecraft/world/level/levelgen/placement/PlacementModifierType;", "getType", "()Lnet/minecraft/world/level/levelgen/placement/PlacementModifierType;", "", "maxTries", "I", "getMaxTries", "()I", "Lnet/minecraft/world/level/levelgen/blockpredicates/BlockPredicate;", "predicate", "Lnet/minecraft/world/level/levelgen/blockpredicates/BlockPredicate;", "getPredicate", "()Lnet/minecraft/world/level/levelgen/blockpredicates/BlockPredicate;", "xzRange", "getXzRange", "yRange", "getYRange", "<init>", "(Lnet/minecraft/world/level/levelgen/blockpredicates/BlockPredicate;III)V", "Companion", "common"})
public final class LocatePredicatePlacementModifier
extends PlacementModifier {
    @NotNull
    public static final Companion Companion = new Companion(null);
    @NotNull
    private final BlockPredicate predicate;
    private final int maxTries;
    private final int xzRange;
    private final int yRange;
    @NotNull
    private static final Codec<LocatePredicatePlacementModifier> MODIFIER_CODEC;

    public LocatePredicatePlacementModifier(@NotNull BlockPredicate predicate, int maxTries, int xzRange, int yRange) {
        Intrinsics.checkNotNullParameter((Object)predicate, (String)"predicate");
        this.predicate = predicate;
        this.maxTries = maxTries;
        this.xzRange = xzRange;
        this.yRange = yRange;
    }

    @NotNull
    public final BlockPredicate getPredicate() {
        return this.predicate;
    }

    public final int getMaxTries() {
        return this.maxTries;
    }

    public final int getXzRange() {
        return this.xzRange;
    }

    public final int getYRange() {
        return this.yRange;
    }

    @NotNull
    public Stream<BlockPos> m_213676_(@NotNull PlacementContext context, @NotNull RandomSource random, @NotNull BlockPos pos) {
        Intrinsics.checkNotNullParameter((Object)context, (String)"context");
        Intrinsics.checkNotNullParameter((Object)random, (String)"random");
        Intrinsics.checkNotNullParameter((Object)pos, (String)"pos");
        int i = 0;
        int n = this.maxTries;
        if (i <= n) {
            while (true) {
                BlockPos newPos = pos.m_7918_(random.m_216332_(0, this.xzRange), random.m_216332_(-this.yRange, this.yRange), random.m_216332_(0, this.xzRange));
                if (this.predicate.test((Object)context.m_191831_(), (Object)newPos)) {
                    Stream<BlockPos> stream = Stream.of(newPos);
                    Intrinsics.checkNotNullExpressionValue(stream, (String)"of(newPos)");
                    return stream;
                }
                if (i == n) break;
                ++i;
            }
        }
        Stream<BlockPos> stream = Stream.empty();
        Intrinsics.checkNotNullExpressionValue(stream, (String)"empty()");
        return stream;
    }

    @NotNull
    public PlacementModifierType<?> m_183327_() {
        return CobblemonPlacementModifierTypes.LOCATE_PREDICATE;
    }

    private static final BlockPredicate MODIFIER_CODEC$lambda$5$lambda$0(LocatePredicatePlacementModifier it) {
        return it.predicate;
    }

    private static final Integer MODIFIER_CODEC$lambda$5$lambda$1(LocatePredicatePlacementModifier it) {
        return it.maxTries;
    }

    private static final Integer MODIFIER_CODEC$lambda$5$lambda$2(LocatePredicatePlacementModifier it) {
        return it.xzRange;
    }

    private static final Integer MODIFIER_CODEC$lambda$5$lambda$3(LocatePredicatePlacementModifier it) {
        return it.yRange;
    }

    private static final LocatePredicatePlacementModifier MODIFIER_CODEC$lambda$5$lambda$4(BlockPredicate predicate, Integer maxTries, Integer xzRange, Integer yRange) {
        Intrinsics.checkNotNullExpressionValue((Object)predicate, (String)"predicate");
        Intrinsics.checkNotNullExpressionValue((Object)maxTries, (String)"maxTries");
        int n = maxTries;
        Intrinsics.checkNotNullExpressionValue((Object)xzRange, (String)"xzRange");
        int n2 = xzRange;
        Intrinsics.checkNotNullExpressionValue((Object)yRange, (String)"yRange");
        return new LocatePredicatePlacementModifier(predicate, n, n2, yRange);
    }

    private static final App MODIFIER_CODEC$lambda$5(RecordCodecBuilder.Instance instance) {
        return instance.group((App)BlockPredicate.f_190392_.fieldOf("predicate").forGetter(LocatePredicatePlacementModifier::MODIFIER_CODEC$lambda$5$lambda$0), (App)PrimitiveCodec.INT.fieldOf("maxTries").forGetter(LocatePredicatePlacementModifier::MODIFIER_CODEC$lambda$5$lambda$1), (App)PrimitiveCodec.INT.fieldOf("xzRange").forGetter(LocatePredicatePlacementModifier::MODIFIER_CODEC$lambda$5$lambda$2), (App)PrimitiveCodec.INT.fieldOf("yRange").forGetter(LocatePredicatePlacementModifier::MODIFIER_CODEC$lambda$5$lambda$3)).apply((Applicative)instance, LocatePredicatePlacementModifier::MODIFIER_CODEC$lambda$5$lambda$4);
    }

    static {
        Codec codec2 = RecordCodecBuilder.create(LocatePredicatePlacementModifier::MODIFIER_CODEC$lambda$5);
        Intrinsics.checkNotNullExpressionValue((Object)codec2, (String)"create { instance ->\n   \u2026)\n            }\n        }");
        MODIFIER_CODEC = codec2;
    }

    @Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0007\b\u0086\u0003\u0018\u00002\u00020\u0001B\t\b\u0002\u00a2\u0006\u0004\b\b\u0010\tR\u001d\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00030\u00028\u0006\u00a2\u0006\f\n\u0004\b\u0004\u0010\u0005\u001a\u0004\b\u0006\u0010\u0007\u00a8\u0006\n"}, d2={"Lcom/cobblemon/mod/common/world/placementmodifier/LocatePredicatePlacementModifier$Companion;", "", "Lcom/mojang/serialization/Codec;", "Lcom/cobblemon/mod/common/world/placementmodifier/LocatePredicatePlacementModifier;", "MODIFIER_CODEC", "Lcom/mojang/serialization/Codec;", "getMODIFIER_CODEC", "()Lcom/mojang/serialization/Codec;", "<init>", "()V", "common"})
    public static final class Companion {
        private Companion() {
        }

        @NotNull
        public final Codec<LocatePredicatePlacementModifier> getMODIFIER_CODEC() {
            return MODIFIER_CODEC;
        }

        public /* synthetic */ Companion(DefaultConstructorMarker $constructor_marker) {
            this();
        }
    }
}

