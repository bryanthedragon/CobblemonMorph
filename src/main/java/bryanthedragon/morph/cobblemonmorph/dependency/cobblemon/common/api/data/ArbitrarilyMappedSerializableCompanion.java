/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.serialization.Codec
 *  kotlin.Metadata
 *  kotlin.jvm.functions.Function1
 *  kotlin.jvm.internal.Intrinsics
 *  net.minecraft.network.FriendlyByteBuf
 *  org.jetbrains.annotations.NotNull
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.data;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.codec.CodecMapped;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.codec.MappedCodec;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.data.RegisteredSubtype;
import com.mojang.serialization.Codec;
import java.util.LinkedHashMap;
import java.util.Map;
import kotlin.Metadata;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import net.minecraft.network.FriendlyByteBuf;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000L\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0010\u000e\n\u0002\b\b\n\u0002\u0010%\n\u0002\u0018\u0002\n\u0002\b\u0005\b&\u0018\u0000*\b\b\u0000\u0010\u0002*\u00020\u0001*\u0004\b\u0001\u0010\u00032\u00020\u0004BC\u0012\u0012\u0010\u001b\u001a\u000e\u0012\u0004\u0012\u00020\u001a\u0012\u0004\u0012\u00028\u00010\u0019\u0012\u0012\u0010!\u001a\u000e\u0012\u0004\u0012\u00028\u0001\u0012\u0004\u0012\u00020\u001a0\u0019\u0012\u0012\u0010\u001f\u001a\u000e\u0012\u0004\u0012\u00028\u0000\u0012\u0004\u0012\u00028\u00010\u0019\u00a2\u0006\u0004\b'\u0010(J\u0015\u0010\u0007\u001a\u00028\u00002\u0006\u0010\u0006\u001a\u00020\u0005\u00a2\u0006\u0004\b\u0007\u0010\bJ;\u0010\u0010\u001a\u00020\u000f\"\b\b\u0002\u0010\t*\u00028\u00002\u0006\u0010\n\u001a\u00028\u00012\f\u0010\f\u001a\b\u0012\u0004\u0012\u00028\u00020\u000b2\f\u0010\u000e\u001a\b\u0012\u0004\u0012\u00028\u00020\r\u00a2\u0006\u0004\b\u0010\u0010\u0011J\u001d\u0010\u0013\u001a\u00020\u000f2\u0006\u0010\u0006\u001a\u00020\u00052\u0006\u0010\u0012\u001a\u00028\u0000\u00a2\u0006\u0004\b\u0013\u0010\u0014R#\u0010\u000e\u001a\u000e\u0012\u0004\u0012\u00028\u0000\u0012\u0004\u0012\u00028\u00010\u00158\u0006\u00a2\u0006\f\n\u0004\b\u000e\u0010\u0016\u001a\u0004\b\u0017\u0010\u0018R#\u0010\u001b\u001a\u000e\u0012\u0004\u0012\u00020\u001a\u0012\u0004\u0012\u00028\u00010\u00198\u0006\u00a2\u0006\f\n\u0004\b\u001b\u0010\u001c\u001a\u0004\b\u001d\u0010\u001eR#\u0010\u001f\u001a\u000e\u0012\u0004\u0012\u00028\u0000\u0012\u0004\u0012\u00028\u00010\u00198\u0006\u00a2\u0006\f\n\u0004\b\u001f\u0010\u001c\u001a\u0004\b \u0010\u001eR#\u0010!\u001a\u000e\u0012\u0004\u0012\u00028\u0001\u0012\u0004\u0012\u00020\u001a0\u00198\u0006\u00a2\u0006\f\n\u0004\b!\u0010\u001c\u001a\u0004\b\"\u0010\u001eR(\u0010%\u001a\u0016\u0012\u0004\u0012\u00028\u0001\u0012\f\u0012\n\u0012\u0006\b\u0001\u0012\u00028\u00000$0#8\u0002X\u0082\u0004\u00a2\u0006\u0006\n\u0004\b%\u0010&\u00a8\u0006)"}, d2={"Lcom/cobblemon/mod/common/api/data/ArbitrarilyMappedSerializableCompanion;", "Lcom/cobblemon/mod/common/api/codec/CodecMapped;", "T", "K", "", "Lnet/minecraft/network/FriendlyByteBuf;", "buffer", "readFromBuffer", "(Lnet/minecraft/network/FriendlyByteBuf;)Lcom/cobblemon/mod/common/api/codec/CodecMapped;", "E", "key", "Ljava/lang/Class;", "clazz", "Lcom/mojang/serialization/Codec;", "codec", "", "registerSubtype", "(Ljava/lang/Object;Ljava/lang/Class;Lcom/mojang/serialization/Codec;)V", "value", "writeToBuffer", "(Lnet/minecraft/network/FriendlyByteBuf;Lcom/cobblemon/mod/common/api/codec/CodecMapped;)V", "Lcom/cobblemon/mod/common/api/codec/MappedCodec;", "Lcom/cobblemon/mod/common/api/codec/MappedCodec;", "getCodec", "()Lcom/cobblemon/mod/common/api/codec/MappedCodec;", "Lkotlin/Function1;", "", "keyFromString", "Lkotlin/jvm/functions/Function1;", "getKeyFromString", "()Lkotlin/jvm/functions/Function1;", "keyFromValue", "getKeyFromValue", "stringFromKey", "getStringFromKey", "", "Lcom/cobblemon/mod/common/api/data/RegisteredSubtype;", "subtypes", "Ljava/util/Map;", "<init>", "(Lkotlin/jvm/functions/Function1;Lkotlin/jvm/functions/Function1;Lkotlin/jvm/functions/Function1;)V", "common"})
public abstract class ArbitrarilyMappedSerializableCompanion<T extends CodecMapped, K> {
    @NotNull
    private final Function1<String, K> keyFromString;
    @NotNull
    private final Function1<K, String> stringFromKey;
    @NotNull
    private final Function1<T, K> keyFromValue;
    @NotNull
    private final MappedCodec<T, K> codec;
    @NotNull
    private final Map<K, RegisteredSubtype<? extends T>> subtypes;

    public ArbitrarilyMappedSerializableCompanion(@NotNull Function1<? super String, ? extends K> keyFromString, @NotNull Function1<? super K, String> stringFromKey, @NotNull Function1<? super T, ? extends K> keyFromValue) {
        Intrinsics.checkNotNullParameter(keyFromString, (String)"keyFromString");
        Intrinsics.checkNotNullParameter(stringFromKey, (String)"stringFromKey");
        Intrinsics.checkNotNullParameter(keyFromValue, (String)"keyFromValue");
        this.keyFromString = keyFromString;
        this.stringFromKey = stringFromKey;
        this.keyFromValue = keyFromValue;
        this.codec = new MappedCodec(new Function1<K, Codec<? extends T>>(this){
            final /* synthetic */ ArbitrarilyMappedSerializableCompanion<T, K> this$0;
            {
                this.this$0 = $receiver;
                super(1);
            }

            @NotNull
            public final Codec<? extends T> invoke(K it) {
                V v = ArbitrarilyMappedSerializableCompanion.access$getSubtypes$p(this.this$0).get(it);
                Intrinsics.checkNotNull(v);
                return ((RegisteredSubtype)v).getCodec();
            }
        }, null, this.keyFromString, 2, null);
        this.subtypes = new LinkedHashMap();
    }

    @NotNull
    public final Function1<String, K> getKeyFromString() {
        return this.keyFromString;
    }

    @NotNull
    public final Function1<K, String> getStringFromKey() {
        return this.stringFromKey;
    }

    @NotNull
    public final Function1<T, K> getKeyFromValue() {
        return this.keyFromValue;
    }

    @NotNull
    public final MappedCodec<T, K> getCodec() {
        return this.codec;
    }

    public final <E extends T> void registerSubtype(K key, @NotNull Class<E> clazz, @NotNull Codec<E> codec2) {
        Intrinsics.checkNotNullParameter(clazz, (String)"clazz");
        Intrinsics.checkNotNullParameter(codec2, (String)"codec");
        this.subtypes.put(key, new RegisteredSubtype<E>(clazz, codec2));
    }

    public final void writeToBuffer(@NotNull FriendlyByteBuf buffer, @NotNull T value2) {
        Intrinsics.checkNotNullParameter((Object)buffer, (String)"buffer");
        Intrinsics.checkNotNullParameter(value2, (String)"value");
        String typeString = (String)this.stringFromKey.invoke(this.keyFromValue.invoke(value2));
        buffer.m_130070_(typeString);
        value2.writeToBuffer(buffer);
    }

    @NotNull
    public final T readFromBuffer(@NotNull FriendlyByteBuf buffer) {
        Intrinsics.checkNotNullParameter((Object)buffer, (String)"buffer");
        String typeString = buffer.m_130277_();
        Intrinsics.checkNotNullExpressionValue((Object)typeString, (String)"typeString");
        RegisteredSubtype<? extends T> registeredSubtype = this.subtypes.get(this.keyFromString.invoke((Object)typeString));
        if (registeredSubtype == null || (registeredSubtype = registeredSubtype.getClazz()) == null) {
            throw new IllegalArgumentException("Unrecognized subtype: " + typeString);
        }
        RegisteredSubtype<? extends T> clazz = registeredSubtype;
        CodecMapped value2 = (CodecMapped)((Class)((Object)clazz)).getDeclaredConstructor(new Class[0]).newInstance(new Object[0]);
        value2.readFromBuffer(buffer);
        Intrinsics.checkNotNullExpressionValue((Object)value2, (String)"value");
        return (T)value2;
    }

    public static final /* synthetic */ Map access$getSubtypes$p(ArbitrarilyMappedSerializableCompanion $this) {
        return $this.subtypes;
    }
}

