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
 *  kotlin.jvm.internal.Intrinsics
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 *  org.joml.Vector4f
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.adapters;

import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import java.lang.reflect.Type;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.joml.Vector4f;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000,\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0006\b\u00c6\u0002\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u00012\b\u0012\u0004\u0012\u00020\u00020\u0003B\t\b\u0002\u00a2\u0006\u0004\b\u0012\u0010\u0013J'\u0010\n\u001a\u00020\u00022\u0006\u0010\u0005\u001a\u00020\u00042\u0006\u0010\u0007\u001a\u00020\u00062\u0006\u0010\t\u001a\u00020\bH\u0016\u00a2\u0006\u0004\b\n\u0010\u000bJ+\u0010\u0010\u001a\u00020\u00042\u0006\u0010\f\u001a\u00020\u00022\b\u0010\r\u001a\u0004\u0018\u00010\u00062\b\u0010\u000f\u001a\u0004\u0018\u00010\u000eH\u0016\u00a2\u0006\u0004\b\u0010\u0010\u0011\u00a8\u0006\u0014"}, d2={"Lcom/cobblemon/mod/common/util/adapters/Vector4fAdapter;", "Lcom/google/gson/JsonDeserializer;", "Lorg/joml/Vector4f;", "Lcom/google/gson/JsonSerializer;", "Lcom/google/gson/JsonElement;", "json", "Ljava/lang/reflect/Type;", "type", "Lcom/google/gson/JsonDeserializationContext;", "ctx", "deserialize", "(Lcom/google/gson/JsonElement;Ljava/lang/reflect/Type;Lcom/google/gson/JsonDeserializationContext;)Lorg/joml/Vector4f;", "src", "typeOfSrc", "Lcom/google/gson/JsonSerializationContext;", "context", "serialize", "(Lorg/joml/Vector4f;Ljava/lang/reflect/Type;Lcom/google/gson/JsonSerializationContext;)Lcom/google/gson/JsonElement;", "<init>", "()V", "common"})
public final class Vector4fAdapter
implements JsonDeserializer<Vector4f>,
JsonSerializer<Vector4f> {
    @NotNull
    public static final Vector4fAdapter INSTANCE = new Vector4fAdapter();

    private Vector4fAdapter() {
    }

    @NotNull
    public Vector4f deserialize(@NotNull JsonElement json, @NotNull Type type, @NotNull JsonDeserializationContext ctx) {
        Intrinsics.checkNotNullParameter((Object)json, (String)"json");
        Intrinsics.checkNotNullParameter((Object)type, (String)"type");
        Intrinsics.checkNotNullParameter((Object)ctx, (String)"ctx");
        JsonArray cfr_ignored_0 = (JsonArray)json;
        return new Vector4f(((JsonArray)json).get(0).getAsFloat(), ((JsonArray)json).get(1).getAsFloat(), ((JsonArray)json).get(2).getAsFloat(), ((JsonArray)json).get(3).getAsFloat());
    }

    @NotNull
    public JsonElement serialize(@NotNull Vector4f src, @Nullable Type typeOfSrc, @Nullable JsonSerializationContext context) {
        JsonArray jsonArray;
        Intrinsics.checkNotNullParameter((Object)src, (String)"src");
        JsonArray it = jsonArray = new JsonArray();
        boolean bl = false;
        it.add((Number)Float.valueOf(src.x));
        it.add((Number)Float.valueOf(src.y));
        it.add((Number)Float.valueOf(src.z));
        it.add((Number)Float.valueOf(src.w));
        return (JsonElement)jsonArray;
    }
}

