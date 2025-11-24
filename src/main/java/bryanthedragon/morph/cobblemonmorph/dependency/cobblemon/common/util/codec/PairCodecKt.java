/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.datafixers.kinds.App
 *  com.mojang.datafixers.kinds.Applicative
 *  com.mojang.serialization.Codec
 *  com.mojang.serialization.codecs.RecordCodecBuilder
 *  com.mojang.serialization.codecs.RecordCodecBuilder$Instance
 *  kotlin.Metadata
 *  kotlin.Pair
 *  kotlin.TuplesKt
 *  kotlin.jvm.internal.Intrinsics
 *  org.jetbrains.annotations.NotNull
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.codec;

import com.mojang.datafixers.kinds.App;
import com.mojang.datafixers.kinds.Applicative;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.TuplesKt;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 8, 0}, k=2, xi=48, d1={"\u0000\u0014\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\u001aG\u0010\u0006\u001a\u0014\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u00028\u0000\u0012\u0004\u0012\u00028\u00010\u00050\u0002\"\u0004\b\u0000\u0010\u0000\"\u0004\b\u0001\u0010\u00012\f\u0010\u0003\u001a\b\u0012\u0004\u0012\u00028\u00000\u00022\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u00028\u00010\u0002\u00a2\u0006\u0004\b\u0006\u0010\u0007\u00a8\u0006\b"}, d2={"A", "B", "Lcom/mojang/serialization/Codec;", "codecA", "codecB", "Lkotlin/Pair;", "pairCodec", "(Lcom/mojang/serialization/Codec;Lcom/mojang/serialization/Codec;)Lcom/mojang/serialization/Codec;", "common"})
public final class PairCodecKt {
    @NotNull
    public static final <A, B> Codec<Pair<A, B>> pairCodec(@NotNull Codec<A> codecA, @NotNull Codec<B> codecB) {
        Intrinsics.checkNotNullParameter(codecA, (String)"codecA");
        Intrinsics.checkNotNullParameter(codecB, (String)"codecB");
        Codec codec2 = RecordCodecBuilder.create(arg_0 -> PairCodecKt.pairCodec$lambda$3(codecA, codecB, arg_0));
        Intrinsics.checkNotNullExpressionValue((Object)codec2, (String)"create { instance ->\n   \u2026 { a, b -> a to b }\n    }");
        return codec2;
    }

    private static final Object pairCodec$lambda$3$lambda$0(Pair it) {
        return it.getFirst();
    }

    private static final Object pairCodec$lambda$3$lambda$1(Pair it) {
        return it.getSecond();
    }

    private static final Pair pairCodec$lambda$3$lambda$2(Object a, Object b) {
        return TuplesKt.to((Object)a, (Object)b);
    }

    private static final App pairCodec$lambda$3(Codec $codecA, Codec $codecB, RecordCodecBuilder.Instance instance) {
        Intrinsics.checkNotNullParameter((Object)$codecA, (String)"$codecA");
        Intrinsics.checkNotNullParameter((Object)$codecB, (String)"$codecB");
        return instance.group((App)$codecA.fieldOf("first").forGetter(PairCodecKt::pairCodec$lambda$3$lambda$0), (App)$codecB.fieldOf("second").forGetter(PairCodecKt::pairCodec$lambda$3$lambda$1)).apply((Applicative)instance, PairCodecKt::pairCodec$lambda$3$lambda$2);
    }
}

