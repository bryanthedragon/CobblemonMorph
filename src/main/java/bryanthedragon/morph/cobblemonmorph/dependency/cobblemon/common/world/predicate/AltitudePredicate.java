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
 *  kotlin.jvm.functions.Function1
 *  kotlin.jvm.internal.DefaultConstructorMarker
 *  kotlin.jvm.internal.Intrinsics
 *  kotlin.ranges.IntRange
 *  kotlin.reflect.KProperty1
 *  net.minecraft.core.BlockPos
 *  net.minecraft.world.level.WorldGenLevel
 *  net.minecraft.world.level.levelgen.blockpredicates.BlockPredicate
 *  net.minecraft.world.level.levelgen.blockpredicates.BlockPredicateType
 *  org.jetbrains.annotations.NotNull
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.world.predicate;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.world.predicate.AltitudePredicate;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.world.predicate.CobblemonBlockPredicates;
import com.mojang.datafixers.kinds.App;
import com.mojang.datafixers.kinds.Applicative;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.PrimitiveCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.Optional;
import kotlin.Metadata;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.ranges.IntRange;
import kotlin.reflect.KProperty1;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.levelgen.blockpredicates.BlockPredicate;
import net.minecraft.world.level.levelgen.blockpredicates.BlockPredicateType;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u00008\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010\b\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\b\u0018\u0000 \u001b2\u00020\u0001:\u0001\u001bB#\u0012\f\u0010\u0012\u001a\b\u0012\u0004\u0012\u00020\r0\f\u0012\f\u0010\u000e\u001a\b\u0012\u0004\u0012\u00020\r0\f\u00a2\u0006\u0004\b\u0019\u0010\u001aJ\u0015\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00000\u0002H\u0016\u00a2\u0006\u0004\b\u0003\u0010\u0004J\u001f\u0010\n\u001a\u00020\t2\u0006\u0010\u0006\u001a\u00020\u00052\u0006\u0010\b\u001a\u00020\u0007H\u0016\u00a2\u0006\u0004\b\n\u0010\u000bR\u001d\u0010\u000e\u001a\b\u0012\u0004\u0012\u00020\r0\f8\u0006\u00a2\u0006\f\n\u0004\b\u000e\u0010\u000f\u001a\u0004\b\u0010\u0010\u0011R\u001d\u0010\u0012\u001a\b\u0012\u0004\u0012\u00020\r0\f8\u0006\u00a2\u0006\f\n\u0004\b\u0012\u0010\u000f\u001a\u0004\b\u0013\u0010\u0011R\u0017\u0010\u0015\u001a\u00020\u00148\u0006\u00a2\u0006\f\n\u0004\b\u0015\u0010\u0016\u001a\u0004\b\u0017\u0010\u0018\u00a8\u0006\u001c"}, d2={"Lcom/cobblemon/mod/common/world/predicate/AltitudePredicate;", "Lnet/minecraft/world/level/levelgen/blockpredicates/BlockPredicate;", "Lnet/minecraft/world/level/levelgen/blockpredicates/BlockPredicateType;", "getType", "()Lnet/minecraft/world/level/levelgen/blockpredicates/BlockPredicateType;", "Lnet/minecraft/world/level/WorldGenLevel;", "world", "Lnet/minecraft/core/BlockPos;", "block", "", "test", "(Lnet/minecraft/world/level/WorldGenLevel;Lnet/minecraft/core/BlockPos;)Z", "Ljava/util/Optional;", "", "max", "Ljava/util/Optional;", "getMax", "()Ljava/util/Optional;", "min", "getMin", "Lkotlin/ranges/IntRange;", "range", "Lkotlin/ranges/IntRange;", "getRange", "()Lkotlin/ranges/IntRange;", "<init>", "(Ljava/util/Optional;Ljava/util/Optional;)V", "Companion", "common"})
public final class AltitudePredicate
implements BlockPredicate {
    @NotNull
    public static final Companion Companion = new Companion(null);
    @NotNull
    private final Optional<Integer> min;
    @NotNull
    private final Optional<Integer> max;
    @NotNull
    private final IntRange range;
    @NotNull
    private static final Codec<AltitudePredicate> CODEC;

    public AltitudePredicate(@NotNull Optional<Integer> min2, @NotNull Optional<Integer> max2) {
        Intrinsics.checkNotNullParameter(min2, (String)"min");
        Intrinsics.checkNotNullParameter(max2, (String)"max");
        this.min = min2;
        this.max = max2;
        int n = ((Number)this.min.orElse(Integer.MIN_VALUE)).intValue();
        Integer n2 = this.max.orElse(Integer.MAX_VALUE);
        Intrinsics.checkNotNullExpressionValue((Object)n2, (String)"max.orElse(Int.MAX_VALUE)");
        this.range = new IntRange(n, ((Number)n2).intValue());
    }

    @NotNull
    public final Optional<Integer> getMin() {
        return this.min;
    }

    @NotNull
    public final Optional<Integer> getMax() {
        return this.max;
    }

    @NotNull
    public final IntRange getRange() {
        return this.range;
    }

    public boolean test(@NotNull WorldGenLevel world, @NotNull BlockPos block) {
        Intrinsics.checkNotNullParameter((Object)world, (String)"world");
        Intrinsics.checkNotNullParameter((Object)block, (String)"block");
        IntRange intRange = this.range;
        int n = intRange.getFirst();
        int n2 = intRange.getLast();
        int n3 = block.m_123342_();
        return n <= n3 ? n3 <= n2 : false;
    }

    @NotNull
    public BlockPredicateType<AltitudePredicate> m_183575_() {
        return CobblemonBlockPredicates.ALTITUDE;
    }

    private static final Optional CODEC$lambda$2$lambda$0(KProperty1 $tmp0, AltitudePredicate p0) {
        Intrinsics.checkNotNullParameter((Object)$tmp0, (String)"$tmp0");
        return (Optional)((Function1)$tmp0).invoke((Object)p0);
    }

    private static final Optional CODEC$lambda$2$lambda$1(KProperty1 $tmp0, AltitudePredicate p0) {
        Intrinsics.checkNotNullParameter((Object)$tmp0, (String)"$tmp0");
        return (Optional)((Function1)$tmp0).invoke((Object)p0);
    }

    private static final App CODEC$lambda$2(RecordCodecBuilder.Instance it) {
        return it.group((App)PrimitiveCodec.INT.optionalFieldOf("min").forGetter(arg_0 -> AltitudePredicate.CODEC$lambda$2$lambda$0((KProperty1)Companion.CODEC.1.1.INSTANCE, arg_0)), (App)PrimitiveCodec.INT.optionalFieldOf("max").forGetter(arg_0 -> AltitudePredicate.CODEC$lambda$2$lambda$1((KProperty1)Companion.CODEC.1.2.INSTANCE, arg_0))).apply((Applicative)it, AltitudePredicate::new);
    }

    static {
        Codec codec2 = RecordCodecBuilder.create(AltitudePredicate::CODEC$lambda$2);
        Intrinsics.checkNotNullExpressionValue((Object)codec2, (String)"create {\n            it.\u2026itudePredicate)\n        }");
        CODEC = codec2;
    }

    @Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0007\b\u0086\u0003\u0018\u00002\u00020\u0001B\t\b\u0002\u00a2\u0006\u0004\b\b\u0010\tR\u001d\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00030\u00028\u0006\u00a2\u0006\f\n\u0004\b\u0004\u0010\u0005\u001a\u0004\b\u0006\u0010\u0007\u00a8\u0006\n"}, d2={"Lcom/cobblemon/mod/common/world/predicate/AltitudePredicate$Companion;", "", "Lcom/mojang/serialization/Codec;", "Lcom/cobblemon/mod/common/world/predicate/AltitudePredicate;", "CODEC", "Lcom/mojang/serialization/Codec;", "getCODEC", "()Lcom/mojang/serialization/Codec;", "<init>", "()V", "common"})
    public static final class Companion {
        private Companion() {
        }

        @NotNull
        public final Codec<AltitudePredicate> getCODEC() {
            return CODEC;
        }

        public /* synthetic */ Companion(DefaultConstructorMarker $constructor_marker) {
            this();
        }
    }
}

