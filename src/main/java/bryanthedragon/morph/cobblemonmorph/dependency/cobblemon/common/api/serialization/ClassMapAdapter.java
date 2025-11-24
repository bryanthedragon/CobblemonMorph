/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.gson.JsonDeserializationContext
 *  com.google.gson.JsonDeserializer
 *  com.google.gson.JsonElement
 *  kotlin.Metadata
 *  kotlin.jvm.functions.Function1
 *  kotlin.jvm.internal.Intrinsics
 *  org.jetbrains.annotations.NotNull
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.serialization;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import java.lang.reflect.Type;
import java.util.Map;
import kotlin.Metadata;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u00004\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010%\n\u0002\u0018\u0002\n\u0002\b\u0007\u0018\u0000*\u0004\b\u0000\u0010\u0001*\u0004\b\u0001\u0010\u00022\b\u0012\u0004\u0012\u00028\u00000\u0003B7\u0012\u001a\u0010\u0013\u001a\u0016\u0012\u0004\u0012\u00028\u0001\u0012\f\u0012\n\u0012\u0006\b\u0001\u0012\u00028\u00000\u00120\u0011\u0012\u0012\u0010\r\u001a\u000e\u0012\u0004\u0012\u00020\u0004\u0012\u0004\u0012\u00028\u00010\f\u00a2\u0006\u0004\b\u0017\u0010\u0018J'\u0010\n\u001a\u00028\u00002\u0006\u0010\u0005\u001a\u00020\u00042\u0006\u0010\u0007\u001a\u00020\u00062\u0006\u0010\t\u001a\u00020\bH\u0016\u00a2\u0006\u0004\b\n\u0010\u000bR#\u0010\r\u001a\u000e\u0012\u0004\u0012\u00020\u0004\u0012\u0004\u0012\u00028\u00010\f8\u0006\u00a2\u0006\f\n\u0004\b\r\u0010\u000e\u001a\u0004\b\u000f\u0010\u0010R+\u0010\u0013\u001a\u0016\u0012\u0004\u0012\u00028\u0001\u0012\f\u0012\n\u0012\u0006\b\u0001\u0012\u00028\u00000\u00120\u00118\u0006\u00a2\u0006\f\n\u0004\b\u0013\u0010\u0014\u001a\u0004\b\u0015\u0010\u0016\u00a8\u0006\u0019"}, d2={"Lcom/cobblemon/mod/common/api/serialization/ClassMapAdapter;", "T", "K", "Lcom/google/gson/JsonDeserializer;", "Lcom/google/gson/JsonElement;", "json", "Ljava/lang/reflect/Type;", "typeOfT", "Lcom/google/gson/JsonDeserializationContext;", "ctx", "deserialize", "(Lcom/google/gson/JsonElement;Ljava/lang/reflect/Type;Lcom/google/gson/JsonDeserializationContext;)Ljava/lang/Object;", "Lkotlin/Function1;", "keyFromElement", "Lkotlin/jvm/functions/Function1;", "getKeyFromElement", "()Lkotlin/jvm/functions/Function1;", "", "Ljava/lang/Class;", "mapping", "Ljava/util/Map;", "getMapping", "()Ljava/util/Map;", "<init>", "(Ljava/util/Map;Lkotlin/jvm/functions/Function1;)V", "common"})
public final class ClassMapAdapter<T, K>
implements JsonDeserializer<T> {
    @NotNull
    private final Map<K, Class<? extends T>> mapping;
    @NotNull
    private final Function1<JsonElement, K> keyFromElement;

    public ClassMapAdapter(@NotNull Map<K, Class<? extends T>> mapping, @NotNull Function1<? super JsonElement, ? extends K> keyFromElement) {
        Intrinsics.checkNotNullParameter(mapping, (String)"mapping");
        Intrinsics.checkNotNullParameter(keyFromElement, (String)"keyFromElement");
        this.mapping = mapping;
        this.keyFromElement = keyFromElement;
    }

    @NotNull
    public final Map<K, Class<? extends T>> getMapping() {
        return this.mapping;
    }

    @NotNull
    public final Function1<JsonElement, K> getKeyFromElement() {
        return this.keyFromElement;
    }

    public T deserialize(@NotNull JsonElement json, @NotNull Type typeOfT, @NotNull JsonDeserializationContext ctx) {
        Intrinsics.checkNotNullParameter((Object)json, (String)"json");
        Intrinsics.checkNotNullParameter((Object)typeOfT, (String)"typeOfT");
        Intrinsics.checkNotNullParameter((Object)ctx, (String)"ctx");
        Object key = this.keyFromElement.invoke((Object)json);
        Class<? extends T> clazz = this.mapping.get(key);
        if (clazz == null) {
            throw new IllegalStateException("Could not find class registered for key: " + key);
        }
        Class<? extends T> clazz2 = clazz;
        return (T)ctx.deserialize(json, (Type)clazz2);
    }
}

