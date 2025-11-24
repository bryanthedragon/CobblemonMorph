/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.serialization.DataResult
 *  com.mojang.serialization.DynamicOps
 *  com.mojang.serialization.codecs.PrimitiveCodec
 *  kotlin.Metadata
 *  kotlin.jvm.functions.Function1
 *  kotlin.jvm.internal.Intrinsics
 *  org.jetbrains.annotations.NotNull
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.codec;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.bedrock.molang.Expression;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.MoLangExtensionsKt;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.codec.ExpressionCodecKt;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.DynamicOps;
import com.mojang.serialization.codecs.PrimitiveCodec;
import kotlin.Metadata;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 8, 0}, k=2, xi=48, d1={"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\"\u001d\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00010\u00008\u0006\u00a2\u0006\f\n\u0004\b\u0002\u0010\u0003\u001a\u0004\b\u0004\u0010\u0005\u00a8\u0006\u0006"}, d2={"Lcom/mojang/serialization/codecs/PrimitiveCodec;", "Lcom/bedrockk/molang/Expression;", "EXPRESSION_CODEC", "Lcom/mojang/serialization/codecs/PrimitiveCodec;", "getEXPRESSION_CODEC", "()Lcom/mojang/serialization/codecs/PrimitiveCodec;", "common"})
public final class ExpressionCodecKt {
    @NotNull
    private static final PrimitiveCodec<Expression> EXPRESSION_CODEC = (PrimitiveCodec)new PrimitiveCodec<Expression>(){

        @NotNull
        public <T> DataResult<Expression> read(@NotNull DynamicOps<T> ops, T input) {
            Intrinsics.checkNotNullParameter(ops, (String)"ops");
            DataResult dataResult = ops.getStringValue(input).map(arg_0 -> EXPRESSION_CODEC.1.read$lambda$0(EXPRESSION_CODEC.read.1.INSTANCE, arg_0));
            Intrinsics.checkNotNullExpressionValue((Object)dataResult, (String)"ops.getStringValue(input\u2026r(it).parseExpression() }");
            return dataResult;
        }

        public <T> T write(@NotNull DynamicOps<T> ops, @NotNull Expression value2) {
            Intrinsics.checkNotNullParameter(ops, (String)"ops");
            Intrinsics.checkNotNullParameter((Object)value2, (String)"value");
            return (T)ops.createString(MoLangExtensionsKt.getString(value2));
        }

        private static final Expression read$lambda$0(Function1 $tmp0, Object p0) {
            Intrinsics.checkNotNullParameter((Object)$tmp0, (String)"$tmp0");
            return (Expression)$tmp0.invoke(p0);
        }
    };

    @NotNull
    public static final PrimitiveCodec<Expression> getEXPRESSION_CODEC() {
        return EXPRESSION_CODEC;
    }
}

