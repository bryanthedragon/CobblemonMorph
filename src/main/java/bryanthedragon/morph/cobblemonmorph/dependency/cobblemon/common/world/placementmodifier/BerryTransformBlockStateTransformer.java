/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.datafixers.kinds.App
 *  com.mojang.datafixers.kinds.Applicative
 *  com.mojang.serialization.Codec
 *  com.mojang.serialization.DataResult
 *  com.mojang.serialization.DynamicOps
 *  com.mojang.serialization.codecs.PrimitiveCodec
 *  com.mojang.serialization.codecs.RecordCodecBuilder
 *  com.mojang.serialization.codecs.RecordCodecBuilder$Instance
 *  kotlin.Metadata
 *  kotlin.NotImplementedError
 *  kotlin.jvm.internal.DefaultConstructorMarker
 *  kotlin.jvm.internal.Intrinsics
 *  kotlin.random.Random
 *  net.minecraft.network.FriendlyByteBuf
 *  net.minecraft.world.level.block.state.BlockState
 *  net.minecraft.world.level.block.state.properties.Property
 *  org.jetbrains.annotations.NotNull
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.world.placementmodifier;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.block.BerryBlock;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.world.placementmodifier.BlockStateTransformer;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.world.placementmodifier.BlockStateTransformerType;
import com.mojang.datafixers.kinds.App;
import com.mojang.datafixers.kinds.Applicative;
import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.DynamicOps;
import com.mojang.serialization.codecs.PrimitiveCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import kotlin.Metadata;
import kotlin.NotImplementedError;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.random.Random;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.Property;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000F\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0001\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\b\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u000b\n\u0002\b\b\u0018\u0000 &2\u00020\u0001:\u0001&B\u001f\u0012\u0006\u0010\u0018\u001a\u00020\u0013\u0012\u0006\u0010\u0014\u001a\u00020\u0013\u0012\u0006\u0010 \u001a\u00020\u001f\u00a2\u0006\u0004\b$\u0010%J)\u0010\u0006\u001a\b\u0012\u0004\u0012\u00028\u00000\u0005\"\u0004\b\u0000\u0010\u00022\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u00028\u00000\u0003H\u0016\u00a2\u0006\u0004\b\u0006\u0010\u0007J\u0017\u0010\u000b\u001a\u00020\n2\u0006\u0010\t\u001a\u00020\bH\u0016\u00a2\u0006\u0004\b\u000b\u0010\fJ\u001f\u0010\u0010\u001a\n \u000f*\u0004\u0018\u00010\r0\r2\u0006\u0010\u000e\u001a\u00020\rH\u0016\u00a2\u0006\u0004\b\u0010\u0010\u0011J\u0017\u0010\u0012\u001a\u00020\n2\u0006\u0010\t\u001a\u00020\bH\u0016\u00a2\u0006\u0004\b\u0012\u0010\fR\u0017\u0010\u0014\u001a\u00020\u00138\u0006\u00a2\u0006\f\n\u0004\b\u0014\u0010\u0015\u001a\u0004\b\u0016\u0010\u0017R\u0017\u0010\u0018\u001a\u00020\u00138\u0006\u00a2\u0006\f\n\u0004\b\u0018\u0010\u0015\u001a\u0004\b\u0019\u0010\u0017R\u001a\u0010\u001b\u001a\u00020\u001a8\u0016X\u0096\u0004\u00a2\u0006\f\n\u0004\b\u001b\u0010\u001c\u001a\u0004\b\u001d\u0010\u001eR\u0017\u0010 \u001a\u00020\u001f8\u0006\u00a2\u0006\f\n\u0004\b \u0010!\u001a\u0004\b\"\u0010#\u00a8\u0006'"}, d2={"Lcom/cobblemon/mod/common/world/placementmodifier/BerryTransformBlockStateTransformer;", "Lcom/cobblemon/mod/common/world/placementmodifier/BlockStateTransformer;", "T", "Lcom/mojang/serialization/DynamicOps;", "ops", "Lcom/mojang/serialization/DataResult;", "encode", "(Lcom/mojang/serialization/DynamicOps;)Lcom/mojang/serialization/DataResult;", "Lnet/minecraft/network/FriendlyByteBuf;", "buffer", "", "readFromBuffer", "(Lnet/minecraft/network/FriendlyByteBuf;)Ljava/lang/Void;", "Lnet/minecraft/world/level/block/state/BlockState;", "blockState", "kotlin.jvm.PlatformType", "transform", "(Lnet/minecraft/world/level/block/state/BlockState;)Lnet/minecraft/world/level/block/state/BlockState;", "writeToBuffer", "", "maxAge", "I", "getMaxAge", "()I", "minAge", "getMinAge", "Lcom/cobblemon/mod/common/world/placementmodifier/BlockStateTransformerType;", "type", "Lcom/cobblemon/mod/common/world/placementmodifier/BlockStateTransformerType;", "getType", "()Lcom/cobblemon/mod/common/world/placementmodifier/BlockStateTransformerType;", "", "wild", "Z", "getWild", "()Z", "<init>", "(IIZ)V", "Companion", "common"})
public final class BerryTransformBlockStateTransformer
implements BlockStateTransformer {
    @NotNull
    public static final Companion Companion = new Companion(null);
    private final int minAge;
    private final int maxAge;
    private final boolean wild;
    @NotNull
    private final BlockStateTransformerType type;
    @NotNull
    private static final Codec<BerryTransformBlockStateTransformer> CODEC;

    public BerryTransformBlockStateTransformer(int minAge, int maxAge, boolean wild) {
        this.minAge = minAge;
        this.maxAge = maxAge;
        this.wild = wild;
        this.type = BlockStateTransformerType.NONE;
    }

    public final int getMinAge() {
        return this.minAge;
    }

    public final int getMaxAge() {
        return this.maxAge;
    }

    public final boolean getWild() {
        return this.wild;
    }

    @Override
    @NotNull
    public BlockStateTransformerType getType() {
        return this.type;
    }

    @Override
    public BlockState transform(@NotNull BlockState blockState) {
        Intrinsics.checkNotNullParameter((Object)blockState, (String)"blockState");
        return (BlockState)((BlockState)blockState.m_61124_((Property)BerryBlock.Companion.getAGE(), (Comparable)Integer.valueOf(Random.Default.nextInt(this.minAge, this.maxAge + 1)))).m_61124_((Property)BerryBlock.Companion.getWAS_GENERATED(), (Comparable)Boolean.valueOf(this.wild));
    }

    @Override
    @NotNull
    public <T> DataResult<T> encode(@NotNull DynamicOps<T> ops) {
        Intrinsics.checkNotNullParameter(ops, (String)"ops");
        DataResult dataResult = CODEC.encodeStart(ops, (Object)this);
        Intrinsics.checkNotNullExpressionValue((Object)dataResult, (String)"CODEC.encodeStart(ops, this)");
        return dataResult;
    }

    @NotNull
    public Void readFromBuffer(@NotNull FriendlyByteBuf buffer) {
        Intrinsics.checkNotNullParameter((Object)buffer, (String)"buffer");
        throw new NotImplementedError("Not supposed to use this for block state transformers");
    }

    @NotNull
    public Void writeToBuffer(@NotNull FriendlyByteBuf buffer) {
        Intrinsics.checkNotNullParameter((Object)buffer, (String)"buffer");
        throw new NotImplementedError("Not supposed to use this for block state transformers");
    }

    private static final String CODEC$lambda$5$lambda$0(BerryTransformBlockStateTransformer it) {
        return it.getType().name();
    }

    private static final Integer CODEC$lambda$5$lambda$1(BerryTransformBlockStateTransformer it) {
        return it.minAge;
    }

    private static final Integer CODEC$lambda$5$lambda$2(BerryTransformBlockStateTransformer it) {
        return it.maxAge;
    }

    private static final Boolean CODEC$lambda$5$lambda$3(BerryTransformBlockStateTransformer it) {
        return it.wild;
    }

    private static final BerryTransformBlockStateTransformer CODEC$lambda$5$lambda$4(String string, Integer minAge, Integer maxAge, Boolean isWild) {
        Intrinsics.checkNotNullExpressionValue((Object)minAge, (String)"minAge");
        int n = minAge;
        Intrinsics.checkNotNullExpressionValue((Object)maxAge, (String)"maxAge");
        int n2 = maxAge;
        Intrinsics.checkNotNullExpressionValue((Object)isWild, (String)"isWild");
        return new BerryTransformBlockStateTransformer(n, n2, isWild);
    }

    private static final App CODEC$lambda$5(RecordCodecBuilder.Instance instance) {
        return instance.group((App)PrimitiveCodec.STRING.fieldOf("type").forGetter(BerryTransformBlockStateTransformer::CODEC$lambda$5$lambda$0), (App)PrimitiveCodec.INT.fieldOf("minAge").forGetter(BerryTransformBlockStateTransformer::CODEC$lambda$5$lambda$1), (App)PrimitiveCodec.INT.fieldOf("maxAge").forGetter(BerryTransformBlockStateTransformer::CODEC$lambda$5$lambda$2), (App)PrimitiveCodec.BOOL.fieldOf("isWild").forGetter(BerryTransformBlockStateTransformer::CODEC$lambda$5$lambda$3)).apply((Applicative)instance, BerryTransformBlockStateTransformer::CODEC$lambda$5$lambda$4);
    }

    static {
        Codec codec2 = RecordCodecBuilder.create(BerryTransformBlockStateTransformer::CODEC$lambda$5);
        Intrinsics.checkNotNullExpressionValue((Object)codec2, (String)"create { instance ->\n   \u2026xAge, isWild) }\n        }");
        CODEC = codec2;
    }

    @Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0007\b\u0086\u0003\u0018\u00002\u00020\u0001B\t\b\u0002\u00a2\u0006\u0004\b\b\u0010\tR\u001d\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00030\u00028\u0006\u00a2\u0006\f\n\u0004\b\u0004\u0010\u0005\u001a\u0004\b\u0006\u0010\u0007\u00a8\u0006\n"}, d2={"Lcom/cobblemon/mod/common/world/placementmodifier/BerryTransformBlockStateTransformer$Companion;", "", "Lcom/mojang/serialization/Codec;", "Lcom/cobblemon/mod/common/world/placementmodifier/BerryTransformBlockStateTransformer;", "CODEC", "Lcom/mojang/serialization/Codec;", "getCODEC", "()Lcom/mojang/serialization/Codec;", "<init>", "()V", "common"})
    public static final class Companion {
        private Companion() {
        }

        @NotNull
        public final Codec<BerryTransformBlockStateTransformer> getCODEC() {
            return CODEC;
        }

        public /* synthetic */ Companion(DefaultConstructorMarker $constructor_marker) {
            this();
        }
    }
}

