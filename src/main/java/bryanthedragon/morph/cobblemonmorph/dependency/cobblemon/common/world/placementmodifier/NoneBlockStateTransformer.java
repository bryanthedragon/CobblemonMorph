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
 *  net.minecraft.network.FriendlyByteBuf
 *  net.minecraft.world.level.block.state.BlockState
 *  org.jetbrains.annotations.NotNull
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.world.placementmodifier;

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
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u00006\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0001\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\b\u0018\u0000 \u00192\u00020\u0001:\u0001\u0019B\u0007\u00a2\u0006\u0004\b\u0017\u0010\u0018J)\u0010\u0006\u001a\b\u0012\u0004\u0012\u00028\u00000\u0005\"\u0004\b\u0000\u0010\u00022\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u00028\u00000\u0003H\u0016\u00a2\u0006\u0004\b\u0006\u0010\u0007J\u0017\u0010\u000b\u001a\u00020\n2\u0006\u0010\t\u001a\u00020\bH\u0016\u00a2\u0006\u0004\b\u000b\u0010\fJ\u0017\u0010\u000f\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\rH\u0016\u00a2\u0006\u0004\b\u000f\u0010\u0010J\u0017\u0010\u0011\u001a\u00020\n2\u0006\u0010\t\u001a\u00020\bH\u0016\u00a2\u0006\u0004\b\u0011\u0010\fR\u001a\u0010\u0013\u001a\u00020\u00128\u0016X\u0096\u0004\u00a2\u0006\f\n\u0004\b\u0013\u0010\u0014\u001a\u0004\b\u0015\u0010\u0016\u00a8\u0006\u001a"}, d2={"Lcom/cobblemon/mod/common/world/placementmodifier/NoneBlockStateTransformer;", "Lcom/cobblemon/mod/common/world/placementmodifier/BlockStateTransformer;", "T", "Lcom/mojang/serialization/DynamicOps;", "ops", "Lcom/mojang/serialization/DataResult;", "encode", "(Lcom/mojang/serialization/DynamicOps;)Lcom/mojang/serialization/DataResult;", "Lnet/minecraft/network/FriendlyByteBuf;", "buffer", "", "readFromBuffer", "(Lnet/minecraft/network/FriendlyByteBuf;)Ljava/lang/Void;", "Lnet/minecraft/world/level/block/state/BlockState;", "blockState", "transform", "(Lnet/minecraft/world/level/block/state/BlockState;)Lnet/minecraft/world/level/block/state/BlockState;", "writeToBuffer", "Lcom/cobblemon/mod/common/world/placementmodifier/BlockStateTransformerType;", "type", "Lcom/cobblemon/mod/common/world/placementmodifier/BlockStateTransformerType;", "getType", "()Lcom/cobblemon/mod/common/world/placementmodifier/BlockStateTransformerType;", "<init>", "()V", "Companion", "common"})
public final class NoneBlockStateTransformer
implements BlockStateTransformer {
    @NotNull
    public static final Companion Companion = new Companion(null);
    @NotNull
    private final BlockStateTransformerType type = BlockStateTransformerType.NONE;
    @NotNull
    private static final Codec<NoneBlockStateTransformer> CODEC;

    @Override
    @NotNull
    public BlockStateTransformerType getType() {
        return this.type;
    }

    @Override
    @NotNull
    public BlockState transform(@NotNull BlockState blockState) {
        Intrinsics.checkNotNullParameter((Object)blockState, (String)"blockState");
        return blockState;
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

    private static final String CODEC$lambda$2$lambda$0(NoneBlockStateTransformer it) {
        return it.getType().name();
    }

    private static final NoneBlockStateTransformer CODEC$lambda$2$lambda$1(String string) {
        return new NoneBlockStateTransformer();
    }

    private static final App CODEC$lambda$2(RecordCodecBuilder.Instance instance) {
        return instance.group((App)PrimitiveCodec.STRING.fieldOf("type").forGetter(NoneBlockStateTransformer::CODEC$lambda$2$lambda$0)).apply((Applicative)instance, NoneBlockStateTransformer::CODEC$lambda$2$lambda$1);
    }

    static {
        Codec codec2 = RecordCodecBuilder.create(NoneBlockStateTransformer::CODEC$lambda$2);
        Intrinsics.checkNotNullExpressionValue((Object)codec2, (String)"create { instance ->\n   \u2026Transformer() }\n        }");
        CODEC = codec2;
    }

    @Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0007\b\u0086\u0003\u0018\u00002\u00020\u0001B\t\b\u0002\u00a2\u0006\u0004\b\b\u0010\tR\u001d\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00030\u00028\u0006\u00a2\u0006\f\n\u0004\b\u0004\u0010\u0005\u001a\u0004\b\u0006\u0010\u0007\u00a8\u0006\n"}, d2={"Lcom/cobblemon/mod/common/world/placementmodifier/NoneBlockStateTransformer$Companion;", "", "Lcom/mojang/serialization/Codec;", "Lcom/cobblemon/mod/common/world/placementmodifier/NoneBlockStateTransformer;", "CODEC", "Lcom/mojang/serialization/Codec;", "getCODEC", "()Lcom/mojang/serialization/Codec;", "<init>", "()V", "common"})
    public static final class Companion {
        private Companion() {
        }

        @NotNull
        public final Codec<NoneBlockStateTransformer> getCODEC() {
            return CODEC;
        }

        public /* synthetic */ Companion(DefaultConstructorMarker $constructor_marker) {
            this();
        }
    }
}

