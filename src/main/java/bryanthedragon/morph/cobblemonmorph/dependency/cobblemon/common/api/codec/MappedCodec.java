/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.datafixers.kinds.App
 *  com.mojang.datafixers.kinds.Applicative
 *  com.mojang.datafixers.util.Pair
 *  com.mojang.serialization.Codec
 *  com.mojang.serialization.DataResult
 *  com.mojang.serialization.DynamicOps
 *  com.mojang.serialization.codecs.PrimitiveCodec
 *  com.mojang.serialization.codecs.RecordCodecBuilder
 *  com.mojang.serialization.codecs.RecordCodecBuilder$Instance
 *  kotlin.Metadata
 *  kotlin.jvm.functions.Function1
 *  kotlin.jvm.internal.DefaultConstructorMarker
 *  kotlin.jvm.internal.Intrinsics
 *  org.jetbrains.annotations.NotNull
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.codec;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.codec.CodecMapped;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.codec.MappedCodec;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.codec.ThingWithType;
import com.mojang.datafixers.kinds.App;
import com.mojang.datafixers.kinds.Applicative;
import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.DynamicOps;
import com.mojang.serialization.codecs.PrimitiveCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import kotlin.Metadata;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u00006\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u000e\n\u0002\b\t\u0018\u0000*\b\b\u0000\u0010\u0002*\u00020\u0001*\u0004\b\u0001\u0010\u00032\b\u0012\u0004\u0012\u00028\u00000\u0004BA\u0012\u001a\u0010\u0011\u001a\u0016\u0012\u0004\u0012\u00028\u0001\u0012\f\u0012\n\u0012\u0006\b\u0001\u0012\u00028\u00000\u00040\u0010\u0012\b\b\u0002\u0010\u0018\u001a\u00020\u0015\u0012\u0012\u0010\u0016\u001a\u000e\u0012\u0004\u0012\u00020\u0015\u0012\u0004\u0012\u00028\u00010\u0010\u00a2\u0006\u0004\b\u001c\u0010\u001dJ=\u0010\u000b\u001a\u0014\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u00028\u0000\u0012\u0004\u0012\u00028\u00020\n0\t\"\u0004\b\u0002\u0010\u00052\f\u0010\u0007\u001a\b\u0012\u0004\u0012\u00028\u00020\u00062\u0006\u0010\b\u001a\u00028\u0002H\u0016\u00a2\u0006\u0004\b\u000b\u0010\fJ9\u0010\u000e\u001a\b\u0012\u0004\u0012\u00028\u00020\t\"\u0004\b\u0002\u0010\u00052\u0006\u0010\b\u001a\u00028\u00002\f\u0010\u0007\u001a\b\u0012\u0004\u0012\u00028\u00020\u00062\u0006\u0010\r\u001a\u00028\u0002H\u0016\u00a2\u0006\u0004\b\u000e\u0010\u000fR+\u0010\u0011\u001a\u0016\u0012\u0004\u0012\u00028\u0001\u0012\f\u0012\n\u0012\u0006\b\u0001\u0012\u00028\u00000\u00040\u00108\u0006\u00a2\u0006\f\n\u0004\b\u0011\u0010\u0012\u001a\u0004\b\u0013\u0010\u0014R#\u0010\u0016\u001a\u000e\u0012\u0004\u0012\u00020\u0015\u0012\u0004\u0012\u00028\u00010\u00108\u0006\u00a2\u0006\f\n\u0004\b\u0016\u0010\u0012\u001a\u0004\b\u0017\u0010\u0014R\u0017\u0010\u0018\u001a\u00020\u00158\u0006\u00a2\u0006\f\n\u0004\b\u0018\u0010\u0019\u001a\u0004\b\u001a\u0010\u001b\u00a8\u0006\u001e"}, d2={"Lcom/cobblemon/mod/common/api/codec/MappedCodec;", "Lcom/cobblemon/mod/common/api/codec/CodecMapped;", "A", "K", "Lcom/mojang/serialization/Codec;", "T", "Lcom/mojang/serialization/DynamicOps;", "ops", "input", "Lcom/mojang/serialization/DataResult;", "Lcom/mojang/datafixers/util/Pair;", "decode", "(Lcom/mojang/serialization/DynamicOps;Ljava/lang/Object;)Lcom/mojang/serialization/DataResult;", "prefix", "encode", "(Lcom/cobblemon/mod/common/api/codec/CodecMapped;Lcom/mojang/serialization/DynamicOps;Ljava/lang/Object;)Lcom/mojang/serialization/DataResult;", "Lkotlin/Function1;", "codecRetriever", "Lkotlin/jvm/functions/Function1;", "getCodecRetriever", "()Lkotlin/jvm/functions/Function1;", "", "keyFromString", "getKeyFromString", "keyName", "Ljava/lang/String;", "getKeyName", "()Ljava/lang/String;", "<init>", "(Lkotlin/jvm/functions/Function1;Ljava/lang/String;Lkotlin/jvm/functions/Function1;)V", "common"})
public final class MappedCodec<A extends CodecMapped, K>
implements Codec<A> {
    @NotNull
    private final Function1<K, Codec<? extends A>> codecRetriever;
    @NotNull
    private final String keyName;
    @NotNull
    private final Function1<String, K> keyFromString;

    public MappedCodec(@NotNull Function1<? super K, ? extends Codec<? extends A>> codecRetriever, @NotNull String keyName, @NotNull Function1<? super String, ? extends K> keyFromString) {
        Intrinsics.checkNotNullParameter(codecRetriever, (String)"codecRetriever");
        Intrinsics.checkNotNullParameter((Object)keyName, (String)"keyName");
        Intrinsics.checkNotNullParameter(keyFromString, (String)"keyFromString");
        this.codecRetriever = codecRetriever;
        this.keyName = keyName;
        this.keyFromString = keyFromString;
    }

    public /* synthetic */ MappedCodec(Function1 function1, String string, Function1 function12, int n, DefaultConstructorMarker defaultConstructorMarker) {
        if ((n & 2) != 0) {
            string = "type";
        }
        this(function1, string, function12);
    }

    @NotNull
    public final Function1<K, Codec<? extends A>> getCodecRetriever() {
        return this.codecRetriever;
    }

    @NotNull
    public final String getKeyName() {
        return this.keyName;
    }

    @NotNull
    public final Function1<String, K> getKeyFromString() {
        return this.keyFromString;
    }

    @NotNull
    public <T> DataResult<T> encode(@NotNull A input, @NotNull DynamicOps<T> ops, T prefix) {
        Intrinsics.checkNotNullParameter(input, (String)"input");
        Intrinsics.checkNotNullParameter(ops, (String)"ops");
        return input.encode(ops);
    }

    @NotNull
    public <T> DataResult<Pair<A, T>> decode(@NotNull DynamicOps<T> ops, T input) {
        Intrinsics.checkNotNullParameter(ops, (String)"ops");
        Codec codec2 = RecordCodecBuilder.create(arg_0 -> MappedCodec.decode$lambda$1(this, arg_0));
        Intrinsics.checkNotNullExpressionValue((Object)codec2, (String)"create { instance ->\n   \u2026:ThingWithType)\n        }");
        Codec thingCodec = codec2;
        DataResult thingWithType2 = thingCodec.decode(ops, input).map(arg_0 -> MappedCodec.decode$lambda$2(decode.thingWithType.1.INSTANCE, arg_0));
        String key = ((ThingWithType)thingWithType2.get().left().get()).getString();
        Codec codec3 = (Codec)this.codecRetriever.invoke(this.keyFromString.invoke((Object)key));
        DataResult dataResult = codec3.decode(ops, input).map(arg_0 -> MappedCodec.decode$lambda$3(decode.1.INSTANCE, arg_0));
        Intrinsics.checkNotNullExpressionValue((Object)dataResult, (String)"codec.decode(ops, input)\u2026ir(it.first, it.second) }");
        return dataResult;
    }

    private static final String decode$lambda$1$lambda$0(ThingWithType it) {
        return it.getString();
    }

    private static final App decode$lambda$1(MappedCodec this$0, RecordCodecBuilder.Instance instance) {
        Intrinsics.checkNotNullParameter((Object)this$0, (String)"this$0");
        return instance.group((App)PrimitiveCodec.STRING.fieldOf(this$0.keyName).forGetter(MappedCodec::decode$lambda$1$lambda$0)).apply((Applicative)instance, ThingWithType::new);
    }

    private static final ThingWithType decode$lambda$2(Function1 $tmp0, Object p0) {
        Intrinsics.checkNotNullParameter((Object)$tmp0, (String)"$tmp0");
        return (ThingWithType)$tmp0.invoke(p0);
    }

    private static final Pair decode$lambda$3(Function1 $tmp0, Object p0) {
        Intrinsics.checkNotNullParameter((Object)$tmp0, (String)"$tmp0");
        return (Pair)$tmp0.invoke(p0);
    }
}

