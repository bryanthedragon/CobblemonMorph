/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.gson.JsonArray
 *  com.google.gson.JsonDeserializationContext
 *  com.google.gson.JsonDeserializer
 *  com.google.gson.JsonElement
 *  com.google.gson.JsonSerializationContext
 *  com.google.gson.JsonSerializer
 *  kotlin.Metadata
 *  kotlin.jvm.JvmClassMappingKt
 *  kotlin.jvm.internal.Intrinsics
 *  kotlin.jvm.internal.SourceDebugExtension
 *  kotlin.reflect.KClass
 *  org.jetbrains.annotations.NotNull
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.adapters;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.collections.LazySet;
import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import java.lang.reflect.Type;
import java.util.Iterator;
import kotlin.Metadata;
import kotlin.jvm.JvmClassMappingKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import kotlin.reflect.KClass;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000>\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\u0018\u0000*\b\b\u0000\u0010\u0002*\u00020\u00012\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00028\u00000\u00040\u00032\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00028\u00000\u00040\u0005B\u0015\u0012\f\u0010\u0015\u001a\b\u0012\u0004\u0012\u00028\u00000\u0014\u00a2\u0006\u0004\b\u0017\u0010\u0018J-\u0010\f\u001a\b\u0012\u0004\u0012\u00028\u00000\u00042\u0006\u0010\u0007\u001a\u00020\u00062\u0006\u0010\t\u001a\u00020\b2\u0006\u0010\u000b\u001a\u00020\nH\u0016\u00a2\u0006\u0004\b\f\u0010\rJ-\u0010\u0012\u001a\u00020\u00112\f\u0010\u000e\u001a\b\u0012\u0004\u0012\u00028\u00000\u00042\u0006\u0010\u000f\u001a\u00020\b2\u0006\u0010\u000b\u001a\u00020\u0010H\u0016\u00a2\u0006\u0004\b\u0012\u0010\u0013R\u001a\u0010\u0015\u001a\b\u0012\u0004\u0012\u00028\u00000\u00148\u0002X\u0082\u0004\u00a2\u0006\u0006\n\u0004\b\u0015\u0010\u0016\u00a8\u0006\u0019"}, d2={"Lcom/cobblemon/mod/common/util/adapters/LazySetAdapter;", "", "T", "Lcom/google/gson/JsonDeserializer;", "Lcom/cobblemon/mod/common/util/collections/LazySet;", "Lcom/google/gson/JsonSerializer;", "Lcom/google/gson/JsonElement;", "json", "Ljava/lang/reflect/Type;", "typeOfT", "Lcom/google/gson/JsonDeserializationContext;", "context", "deserialize", "(Lcom/google/gson/JsonElement;Ljava/lang/reflect/Type;Lcom/google/gson/JsonDeserializationContext;)Lcom/cobblemon/mod/common/util/collections/LazySet;", "src", "typeOfSrc", "Lcom/google/gson/JsonSerializationContext;", "Lcom/google/gson/JsonArray;", "serialize", "(Lcom/cobblemon/mod/common/util/collections/LazySet;Ljava/lang/reflect/Type;Lcom/google/gson/JsonSerializationContext;)Lcom/google/gson/JsonArray;", "Lkotlin/reflect/KClass;", "type", "Lkotlin/reflect/KClass;", "<init>", "(Lkotlin/reflect/KClass;)V", "common"})
@SourceDebugExtension(value={"SMAP\nLazySetAdapter.kt\nKotlin\n*S Kotlin\n*F\n+ 1 LazySetAdapter.kt\ncom/cobblemon/mod/common/util/adapters/LazySetAdapter\n+ 2 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n*L\n1#1,40:1\n1855#2,2:41\n*S KotlinDebug\n*F\n+ 1 LazySetAdapter.kt\ncom/cobblemon/mod/common/util/adapters/LazySetAdapter\n*L\n37#1:41,2\n*E\n"})
public final class LazySetAdapter<T>
implements JsonDeserializer<LazySet<T>>,
JsonSerializer<LazySet<T>> {
    @NotNull
    private final KClass<T> type;

    public LazySetAdapter(@NotNull KClass<T> type) {
        Intrinsics.checkNotNullParameter(type, (String)"type");
        this.type = type;
    }

    @NotNull
    public LazySet<T> deserialize(@NotNull JsonElement json, @NotNull Type typeOfT, @NotNull JsonDeserializationContext context) {
        Intrinsics.checkNotNullParameter((Object)json, (String)"json");
        Intrinsics.checkNotNullParameter((Object)typeOfT, (String)"typeOfT");
        Intrinsics.checkNotNullParameter((Object)context, (String)"context");
        JsonArray jsonArray = json.getAsJsonArray();
        Intrinsics.checkNotNullExpressionValue((Object)jsonArray, (String)"json.asJsonArray");
        return new LazySet<T>(this.type, jsonArray);
    }

    @NotNull
    public JsonArray serialize(@NotNull LazySet<T> src, @NotNull Type typeOfSrc, @NotNull JsonSerializationContext context) {
        JsonArray jsonArray;
        Intrinsics.checkNotNullParameter(src, (String)"src");
        Intrinsics.checkNotNullParameter((Object)typeOfSrc, (String)"typeOfSrc");
        Intrinsics.checkNotNullParameter((Object)context, (String)"context");
        JsonArray $this$serialize_u24lambda_u241 = jsonArray = new JsonArray();
        boolean bl = false;
        Iterable $this$forEach$iv = src;
        boolean $i$f$forEach = false;
        Iterator iterator = $this$forEach$iv.iterator();
        while (iterator.hasNext()) {
            Object element$iv;
            Object element = element$iv = iterator.next();
            boolean bl2 = false;
            $this$serialize_u24lambda_u241.add(context.serialize(element, (Type)JvmClassMappingKt.getJavaClass(this.type)));
        }
        return jsonArray;
    }
}

