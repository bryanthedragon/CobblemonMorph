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
 *  kotlin.jvm.internal.SourceDebugExtension
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
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.levelgen.blockpredicates.BlockPredicate;
import net.minecraft.world.level.levelgen.placement.PlacementContext;
import net.minecraft.world.level.levelgen.placement.PlacementModifier;
import net.minecraft.world.level.levelgen.placement.PlacementModifierType;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000:\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\b\u0018\u0000 \u001a2\u00020\u0001:\u0001\u001aB\u0017\u0012\u0006\u0010\u0014\u001a\u00020\u0013\u0012\u0006\u0010\u000f\u001a\u00020\u000e\u00a2\u0006\u0004\b\u0018\u0010\u0019J-\u0010\t\u001a\b\u0012\u0004\u0012\u00020\u00060\b2\u0006\u0010\u0003\u001a\u00020\u00022\u0006\u0010\u0005\u001a\u00020\u00042\u0006\u0010\u0007\u001a\u00020\u0006H\u0016\u00a2\u0006\u0004\b\t\u0010\nJ\u0015\u0010\f\u001a\b\u0012\u0004\u0012\u00020\u00000\u000bH\u0016\u00a2\u0006\u0004\b\f\u0010\rR\u0017\u0010\u000f\u001a\u00020\u000e8\u0006\u00a2\u0006\f\n\u0004\b\u000f\u0010\u0010\u001a\u0004\b\u0011\u0010\u0012R\u0017\u0010\u0014\u001a\u00020\u00138\u0006\u00a2\u0006\f\n\u0004\b\u0014\u0010\u0015\u001a\u0004\b\u0016\u0010\u0017\u00a8\u0006\u001b"}, d2={"Lcom/cobblemon/mod/common/world/placementmodifier/ConditionalCountPlacementModifier;", "Lnet/minecraft/world/level/levelgen/placement/PlacementModifier;", "Lnet/minecraft/world/level/levelgen/placement/PlacementContext;", "context", "Lnet/minecraft/util/RandomSource;", "random", "Lnet/minecraft/core/BlockPos;", "pos", "Ljava/util/stream/Stream;", "getPositions", "(Lnet/minecraft/world/level/levelgen/placement/PlacementContext;Lnet/minecraft/util/RandomSource;Lnet/minecraft/core/BlockPos;)Ljava/util/stream/Stream;", "Lnet/minecraft/world/level/levelgen/placement/PlacementModifierType;", "getType", "()Lnet/minecraft/world/level/levelgen/placement/PlacementModifierType;", "", "count", "I", "getCount", "()I", "Lnet/minecraft/world/level/levelgen/blockpredicates/BlockPredicate;", "predicate", "Lnet/minecraft/world/level/levelgen/blockpredicates/BlockPredicate;", "getPredicate", "()Lnet/minecraft/world/level/levelgen/blockpredicates/BlockPredicate;", "<init>", "(Lnet/minecraft/world/level/levelgen/blockpredicates/BlockPredicate;I)V", "Companion", "common"})
@SourceDebugExtension(value={"SMAP\nConditionalCountPlacementModifier.kt\nKotlin\n*S Kotlin\n*F\n+ 1 ConditionalCountPlacementModifier.kt\ncom/cobblemon/mod/common/world/placementmodifier/ConditionalCountPlacementModifier\n+ 2 fake.kt\nkotlin/jvm/internal/FakeKt\n*L\n1#1,52:1\n1#2:53\n*E\n"})
public final class ConditionalCountPlacementModifier
extends PlacementModifier {
    @NotNull
    public static final Companion Companion = new Companion(null);
    @NotNull
    private final BlockPredicate predicate;
    private final int count;
    @NotNull
    private static final Codec<ConditionalCountPlacementModifier> MODIFIER_CODEC;

    public ConditionalCountPlacementModifier(@NotNull BlockPredicate predicate, int count) {
        Intrinsics.checkNotNullParameter((Object)predicate, (String)"predicate");
        this.predicate = predicate;
        this.count = count;
    }

    @NotNull
    public final BlockPredicate getPredicate() {
        return this.predicate;
    }

    public final int getCount() {
        return this.count;
    }

    @NotNull
    public PlacementModifierType<ConditionalCountPlacementModifier> m_183327_() {
        return CobblemonPlacementModifierTypes.CONDITIONAL_COUNT;
    }

    @NotNull
    public Stream<BlockPos> m_213676_(@NotNull PlacementContext context, @NotNull RandomSource random, @NotNull BlockPos pos) {
        Stream<Object> stream;
        Intrinsics.checkNotNullParameter((Object)context, (String)"context");
        Intrinsics.checkNotNullParameter((Object)random, (String)"random");
        Intrinsics.checkNotNullParameter((Object)pos, (String)"pos");
        if (this.predicate.test((Object)context.m_191831_(), (Object)pos)) {
            List list = new ArrayList();
            int n = this.count;
            int n2 = 0;
            while (n2 < n) {
                int it = n2++;
                boolean bl = false;
                list.add(pos);
            }
            Stream stream2 = list.stream();
            stream = stream2;
            Intrinsics.checkNotNullExpressionValue(stream2, (String)"{\n            val new = \u2026   new.stream()\n        }");
        } else {
            Stream<BlockPos> stream3 = Stream.of(pos);
            stream = stream3;
            Intrinsics.checkNotNullExpressionValue(stream3, (String)"{\n            Stream.of(pos)\n        }");
        }
        return stream;
    }

    private static final BlockPredicate MODIFIER_CODEC$lambda$3$lambda$1(ConditionalCountPlacementModifier it) {
        return it.predicate;
    }

    private static final Integer MODIFIER_CODEC$lambda$3$lambda$2(ConditionalCountPlacementModifier it) {
        return it.count;
    }

    private static final App MODIFIER_CODEC$lambda$3(RecordCodecBuilder.Instance instance) {
        return instance.group((App)BlockPredicate.f_190392_.fieldOf("predicate").forGetter(ConditionalCountPlacementModifier::MODIFIER_CODEC$lambda$3$lambda$1), (App)PrimitiveCodec.INT.fieldOf("count").forGetter(ConditionalCountPlacementModifier::MODIFIER_CODEC$lambda$3$lambda$2)).apply((Applicative)instance, ConditionalCountPlacementModifier::new);
    }

    static {
        Codec codec2 = RecordCodecBuilder.create(ConditionalCountPlacementModifier::MODIFIER_CODEC$lambda$3);
        Intrinsics.checkNotNullExpressionValue((Object)codec2, (String)"create { instance ->\n   \u2026cementModifier)\n        }");
        MODIFIER_CODEC = codec2;
    }

    @Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0007\b\u0086\u0003\u0018\u00002\u00020\u0001B\t\b\u0002\u00a2\u0006\u0004\b\b\u0010\tR\u001d\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00030\u00028\u0006\u00a2\u0006\f\n\u0004\b\u0004\u0010\u0005\u001a\u0004\b\u0006\u0010\u0007\u00a8\u0006\n"}, d2={"Lcom/cobblemon/mod/common/world/placementmodifier/ConditionalCountPlacementModifier$Companion;", "", "Lcom/mojang/serialization/Codec;", "Lcom/cobblemon/mod/common/world/placementmodifier/ConditionalCountPlacementModifier;", "MODIFIER_CODEC", "Lcom/mojang/serialization/Codec;", "getMODIFIER_CODEC", "()Lcom/mojang/serialization/Codec;", "<init>", "()V", "common"})
    public static final class Companion {
        private Companion() {
        }

        @NotNull
        public final Codec<ConditionalCountPlacementModifier> getMODIFIER_CODEC() {
            return MODIFIER_CODEC;
        }

        public /* synthetic */ Companion(DefaultConstructorMarker $constructor_marker) {
            this();
        }
    }
}

