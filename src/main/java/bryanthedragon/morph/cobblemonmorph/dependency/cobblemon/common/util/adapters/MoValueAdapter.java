/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.gson.JsonDeserializationContext
 *  com.google.gson.JsonDeserializer
 *  com.google.gson.JsonElement
 *  com.google.gson.JsonObject
 *  com.google.gson.JsonSerializationContext
 *  com.google.gson.JsonSerializer
 *  kotlin.Metadata
 *  kotlin.jvm.internal.Intrinsics
 *  org.jetbrains.annotations.NotNull
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.adapters;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.bedrock.molang.runtime.value.MoValue;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import java.lang.reflect.Type;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000,\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\b\u00c6\u0002\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u00012\b\u0012\u0004\u0012\u00020\u00020\u0003B\t\b\u0002\u00a2\u0006\u0004\b\u0011\u0010\u0012J'\u0010\n\u001a\u00020\u00022\u0006\u0010\u0005\u001a\u00020\u00042\u0006\u0010\u0007\u001a\u00020\u00062\u0006\u0010\t\u001a\u00020\bH\u0016\u00a2\u0006\u0004\b\n\u0010\u000bJ'\u0010\u000f\u001a\u00020\u00042\u0006\u0010\f\u001a\u00020\u00022\u0006\u0010\r\u001a\u00020\u00062\u0006\u0010\t\u001a\u00020\u000eH\u0016\u00a2\u0006\u0004\b\u000f\u0010\u0010\u00a8\u0006\u0013"}, d2={"Lcom/cobblemon/mod/common/util/adapters/MoValueAdapter;", "Lcom/google/gson/JsonSerializer;", "Lcom/bedrockk/molang/runtime/value/MoValue;", "Lcom/google/gson/JsonDeserializer;", "Lcom/google/gson/JsonElement;", "json", "Ljava/lang/reflect/Type;", "typeOfT", "Lcom/google/gson/JsonDeserializationContext;", "context", "deserialize", "(Lcom/google/gson/JsonElement;Ljava/lang/reflect/Type;Lcom/google/gson/JsonDeserializationContext;)Lcom/bedrockk/molang/runtime/value/MoValue;", "src", "typeOfSrc", "Lcom/google/gson/JsonSerializationContext;", "serialize", "(Lcom/bedrockk/molang/runtime/value/MoValue;Ljava/lang/reflect/Type;Lcom/google/gson/JsonSerializationContext;)Lcom/google/gson/JsonElement;", "<init>", "()V", "common"})
public final class MoValueAdapter
implements JsonSerializer<MoValue>,
JsonDeserializer<MoValue> {
    @NotNull
    public static final MoValueAdapter INSTANCE = new MoValueAdapter();

    private MoValueAdapter() {
    }

    @NotNull
    public JsonElement serialize(@NotNull MoValue src, @NotNull Type typeOfSrc, @NotNull JsonSerializationContext context) {
        Intrinsics.checkNotNullParameter((Object)src, (String)"src");
        Intrinsics.checkNotNullParameter((Object)typeOfSrc, (String)"typeOfSrc");
        Intrinsics.checkNotNullParameter((Object)context, (String)"context");
        JsonElement jsonElement = MoValue.writeToJson(src);
        if (jsonElement == null) {
            jsonElement = (JsonElement)new JsonObject();
        }
        return jsonElement;
    }

    @NotNull
    public MoValue deserialize(@NotNull JsonElement json, @NotNull Type typeOfT, @NotNull JsonDeserializationContext context) {
        Intrinsics.checkNotNullParameter((Object)json, (String)"json");
        Intrinsics.checkNotNullParameter((Object)typeOfT, (String)"typeOfT");
        Intrinsics.checkNotNullParameter((Object)context, (String)"context");
        MoValue moValue = MoValue.of(json);
        Intrinsics.checkNotNullExpressionValue((Object)moValue, (String)"of(json)");
        return moValue;
    }
}

