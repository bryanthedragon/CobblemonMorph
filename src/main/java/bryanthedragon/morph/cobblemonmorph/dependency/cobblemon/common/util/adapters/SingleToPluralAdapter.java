/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.gson.JsonArray
 *  com.google.gson.JsonDeserializationContext
 *  com.google.gson.JsonDeserializer
 *  com.google.gson.JsonElement
 *  kotlin.Metadata
 *  kotlin.collections.CollectionsKt
 *  kotlin.jvm.functions.Function1
 *  kotlin.jvm.internal.Intrinsics
 *  kotlin.jvm.internal.SourceDebugExtension
 *  org.jetbrains.annotations.NotNull
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.adapters;

import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u00008\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u001c\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\u0010 \n\u0002\b\u0007\u0018\u0000*\u0004\b\u0000\u0010\u0001*\u000e\b\u0001\u0010\u0003*\b\u0012\u0004\u0012\u00028\u00000\u00022\b\u0012\u0004\u0012\u00028\u00010\u0004B/\u0012\f\u0010\u000e\u001a\b\u0012\u0004\u0012\u00028\u00000\r\u0012\u0018\u0010\u0014\u001a\u0014\u0012\n\u0012\b\u0012\u0004\u0012\u00028\u00000\u0013\u0012\u0004\u0012\u00028\u00010\u0012\u00a2\u0006\u0004\b\u0018\u0010\u0019J'\u0010\u000b\u001a\u00028\u00012\u0006\u0010\u0006\u001a\u00020\u00052\u0006\u0010\b\u001a\u00020\u00072\u0006\u0010\n\u001a\u00020\tH\u0016\u00a2\u0006\u0004\b\u000b\u0010\fR\u001d\u0010\u000e\u001a\b\u0012\u0004\u0012\u00028\u00000\r8\u0006\u00a2\u0006\f\n\u0004\b\u000e\u0010\u000f\u001a\u0004\b\u0010\u0010\u0011R)\u0010\u0014\u001a\u0014\u0012\n\u0012\b\u0012\u0004\u0012\u00028\u00000\u0013\u0012\u0004\u0012\u00028\u00010\u00128\u0006\u00a2\u0006\f\n\u0004\b\u0014\u0010\u0015\u001a\u0004\b\u0016\u0010\u0017\u00a8\u0006\u001a"}, d2={"Lcom/cobblemon/mod/common/util/adapters/SingleToPluralAdapter;", "T", "", "C", "Lcom/google/gson/JsonDeserializer;", "Lcom/google/gson/JsonElement;", "json", "Ljava/lang/reflect/Type;", "type", "Lcom/google/gson/JsonDeserializationContext;", "ctx", "deserialize", "(Lcom/google/gson/JsonElement;Ljava/lang/reflect/Type;Lcom/google/gson/JsonDeserializationContext;)Ljava/lang/Iterable;", "Ljava/lang/Class;", "clazz", "Ljava/lang/Class;", "getClazz", "()Ljava/lang/Class;", "Lkotlin/Function1;", "", "converter", "Lkotlin/jvm/functions/Function1;", "getConverter", "()Lkotlin/jvm/functions/Function1;", "<init>", "(Ljava/lang/Class;Lkotlin/jvm/functions/Function1;)V", "common"})
@SourceDebugExtension(value={"SMAP\nSingleToPluralAdapter.kt\nKotlin\n*S Kotlin\n*F\n+ 1 SingleToPluralAdapter.kt\ncom/cobblemon/mod/common/util/adapters/SingleToPluralAdapter\n+ 2 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n*L\n1#1,32:1\n1549#2:33\n1620#2,3:34\n*S KotlinDebug\n*F\n+ 1 SingleToPluralAdapter.kt\ncom/cobblemon/mod/common/util/adapters/SingleToPluralAdapter\n*L\n27#1:33\n27#1:34,3\n*E\n"})
public final class SingleToPluralAdapter<T, C extends Iterable<? extends T>>
implements JsonDeserializer<C> {
    @NotNull
    private final Class<T> clazz;
    @NotNull
    private final Function1<List<? extends T>, C> converter;

    public SingleToPluralAdapter(@NotNull Class<T> clazz, @NotNull Function1<? super List<? extends T>, ? extends C> converter) {
        Intrinsics.checkNotNullParameter(clazz, (String)"clazz");
        Intrinsics.checkNotNullParameter(converter, (String)"converter");
        this.clazz = clazz;
        this.converter = converter;
    }

    @NotNull
    public final Class<T> getClazz() {
        return this.clazz;
    }

    @NotNull
    public final Function1<List<? extends T>, C> getConverter() {
        return this.converter;
    }

    /*
     * WARNING - void declaration
     */
    @NotNull
    public C deserialize(@NotNull JsonElement json, @NotNull Type type, @NotNull JsonDeserializationContext ctx) {
        List list;
        Intrinsics.checkNotNullParameter((Object)json, (String)"json");
        Intrinsics.checkNotNullParameter((Object)type, (String)"type");
        Intrinsics.checkNotNullParameter((Object)ctx, (String)"ctx");
        if (json.isJsonArray()) {
            void $this$mapTo$iv$iv;
            JsonArray jsonArray = json.getAsJsonArray();
            Intrinsics.checkNotNullExpressionValue((Object)jsonArray, (String)"json.asJsonArray");
            Iterable $this$map$iv = (Iterable)jsonArray;
            boolean $i$f$map = false;
            Iterable iterable = $this$map$iv;
            Collection destination$iv$iv = new ArrayList(CollectionsKt.collectionSizeOrDefault((Iterable)$this$map$iv, (int)10));
            boolean $i$f$mapTo = false;
            for (Object item$iv$iv : $this$mapTo$iv$iv) {
                void it;
                JsonElement jsonElement = (JsonElement)item$iv$iv;
                Collection collection = destination$iv$iv;
                boolean bl = false;
                collection.add(ctx.deserialize((JsonElement)it, (Type)this.clazz));
            }
            list = (List)destination$iv$iv;
        } else {
            list = CollectionsKt.listOf((Object)ctx.deserialize(json, (Type)this.clazz));
        }
        List list2 = list;
        return (C)((Iterable)this.converter.invoke((Object)list2));
    }
}

