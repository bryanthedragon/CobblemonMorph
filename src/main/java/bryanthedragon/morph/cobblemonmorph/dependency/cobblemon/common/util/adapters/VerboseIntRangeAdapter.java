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
 *  kotlin.ranges.IntRange
 *  org.jetbrains.annotations.NotNull
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.adapters;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import java.lang.reflect.Type;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlin.ranges.IntRange;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u00008\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0006\b\u00c6\u0002\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u00012\b\u0012\u0004\u0012\u00020\u00020\u0003B\t\b\u0002\u00a2\u0006\u0004\b\u0015\u0010\u0016J'\u0010\n\u001a\u00020\u00022\u0006\u0010\u0005\u001a\u00020\u00042\u0006\u0010\u0007\u001a\u00020\u00062\u0006\u0010\t\u001a\u00020\bH\u0016\u00a2\u0006\u0004\b\n\u0010\u000bJ'\u0010\u000f\u001a\u00020\u000e2\u0006\u0010\f\u001a\u00020\u00022\u0006\u0010\u0007\u001a\u00020\u00062\u0006\u0010\t\u001a\u00020\rH\u0016\u00a2\u0006\u0004\b\u000f\u0010\u0010R\u0014\u0010\u0012\u001a\u00020\u00118\u0002X\u0082T\u00a2\u0006\u0006\n\u0004\b\u0012\u0010\u0013R\u0014\u0010\u0014\u001a\u00020\u00118\u0002X\u0082T\u00a2\u0006\u0006\n\u0004\b\u0014\u0010\u0013\u00a8\u0006\u0017"}, d2={"Lcom/cobblemon/mod/common/util/adapters/VerboseIntRangeAdapter;", "Lcom/google/gson/JsonDeserializer;", "Lkotlin/ranges/IntRange;", "Lcom/google/gson/JsonSerializer;", "Lcom/google/gson/JsonElement;", "jElement", "Ljava/lang/reflect/Type;", "type", "Lcom/google/gson/JsonDeserializationContext;", "context", "deserialize", "(Lcom/google/gson/JsonElement;Ljava/lang/reflect/Type;Lcom/google/gson/JsonDeserializationContext;)Lkotlin/ranges/IntRange;", "range", "Lcom/google/gson/JsonSerializationContext;", "Lcom/google/gson/JsonObject;", "serialize", "(Lkotlin/ranges/IntRange;Ljava/lang/reflect/Type;Lcom/google/gson/JsonSerializationContext;)Lcom/google/gson/JsonObject;", "", "MAX", "Ljava/lang/String;", "MIN", "<init>", "()V", "common"})
public final class VerboseIntRangeAdapter
implements JsonDeserializer<IntRange>,
JsonSerializer<IntRange> {
    @NotNull
    public static final VerboseIntRangeAdapter INSTANCE = new VerboseIntRangeAdapter();
    @NotNull
    private static final String MIN = "min";
    @NotNull
    private static final String MAX = "max";

    private VerboseIntRangeAdapter() {
    }

    @NotNull
    public IntRange deserialize(@NotNull JsonElement jElement, @NotNull Type type, @NotNull JsonDeserializationContext context) {
        Intrinsics.checkNotNullParameter((Object)jElement, (String)"jElement");
        Intrinsics.checkNotNullParameter((Object)type, (String)"type");
        Intrinsics.checkNotNullParameter((Object)context, (String)"context");
        JsonObject json = jElement.getAsJsonObject();
        int min2 = json.get(MIN).getAsInt();
        int max2 = json.get(MAX).getAsInt();
        return new IntRange(min2, max2);
    }

    @NotNull
    public JsonObject serialize(@NotNull IntRange range, @NotNull Type type, @NotNull JsonSerializationContext context) {
        JsonObject jsonObject;
        Intrinsics.checkNotNullParameter((Object)range, (String)"range");
        Intrinsics.checkNotNullParameter((Object)type, (String)"type");
        Intrinsics.checkNotNullParameter((Object)context, (String)"context");
        JsonObject $this$serialize_u24lambda_u240 = jsonObject = new JsonObject();
        boolean bl = false;
        $this$serialize_u24lambda_u240.addProperty(MIN, (Number)range.getFirst());
        $this$serialize_u24lambda_u240.addProperty(MAX, (Number)range.getLast());
        return jsonObject;
    }
}

