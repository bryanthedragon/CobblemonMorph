/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.gson.JsonDeserializationContext
 *  com.google.gson.JsonDeserializer
 *  com.google.gson.JsonElement
 *  com.google.gson.JsonPrimitive
 *  com.google.gson.JsonSerializationContext
 *  com.google.gson.JsonSerializer
 *  kotlin.Metadata
 *  kotlin.jvm.internal.Intrinsics
 *  kotlin.ranges.IntRange
 *  kotlin.text.MatchResult
 *  kotlin.text.MatchResult$Destructured
 *  kotlin.text.Regex
 *  org.jetbrains.annotations.NotNull
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.adapters;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import java.lang.reflect.Type;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlin.ranges.IntRange;
import kotlin.text.MatchResult;
import kotlin.text.Regex;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u00004\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0005\b\u00c6\u0002\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u00012\b\u0012\u0004\u0012\u00020\u00020\u0003B\t\b\u0002\u00a2\u0006\u0004\b\u0014\u0010\u0015J'\u0010\n\u001a\u00020\u00022\u0006\u0010\u0005\u001a\u00020\u00042\u0006\u0010\u0007\u001a\u00020\u00062\u0006\u0010\t\u001a\u00020\bH\u0016\u00a2\u0006\u0004\b\n\u0010\u000bJ'\u0010\u000f\u001a\u00020\u00042\u0006\u0010\f\u001a\u00020\u00022\u0006\u0010\u0007\u001a\u00020\u00062\u0006\u0010\u000e\u001a\u00020\rH\u0016\u00a2\u0006\u0004\b\u000f\u0010\u0010R\u0014\u0010\u0012\u001a\u00020\u00118\u0002X\u0082\u0004\u00a2\u0006\u0006\n\u0004\b\u0012\u0010\u0013\u00a8\u0006\u0016"}, d2={"Lcom/cobblemon/mod/common/util/adapters/IntRangeAdapter;", "Lcom/google/gson/JsonSerializer;", "Lkotlin/ranges/IntRange;", "Lcom/google/gson/JsonDeserializer;", "Lcom/google/gson/JsonElement;", "json", "Ljava/lang/reflect/Type;", "type", "Lcom/google/gson/JsonDeserializationContext;", "context", "deserialize", "(Lcom/google/gson/JsonElement;Ljava/lang/reflect/Type;Lcom/google/gson/JsonDeserializationContext;)Lkotlin/ranges/IntRange;", "range", "Lcom/google/gson/JsonSerializationContext;", "ctx", "serialize", "(Lkotlin/ranges/IntRange;Ljava/lang/reflect/Type;Lcom/google/gson/JsonSerializationContext;)Lcom/google/gson/JsonElement;", "Lkotlin/text/Regex;", "PATTERN", "Lkotlin/text/Regex;", "<init>", "()V", "common"})
public final class IntRangeAdapter
implements JsonSerializer<IntRange>,
JsonDeserializer<IntRange> {
    @NotNull
    public static final IntRangeAdapter INSTANCE = new IntRangeAdapter();
    @NotNull
    private static final Regex PATTERN = new Regex("(-?\\d+)-?(-?\\d+)?");

    private IntRangeAdapter() {
    }

    @NotNull
    public JsonElement serialize(@NotNull IntRange range, @NotNull Type type, @NotNull JsonSerializationContext ctx) {
        Intrinsics.checkNotNullParameter((Object)range, (String)"range");
        Intrinsics.checkNotNullParameter((Object)type, (String)"type");
        Intrinsics.checkNotNullParameter((Object)ctx, (String)"ctx");
        return range.getFirst() == range.getLast() ? (JsonElement)new JsonPrimitive((Number)range.getFirst()) : (JsonElement)new JsonPrimitive(range.getFirst() + "-" + range.getLast());
    }

    @NotNull
    public IntRange deserialize(@NotNull JsonElement json, @NotNull Type type, @NotNull JsonDeserializationContext context) {
        Intrinsics.checkNotNullParameter((Object)json, (String)"json");
        Intrinsics.checkNotNullParameter((Object)type, (String)"type");
        Intrinsics.checkNotNullParameter((Object)context, (String)"context");
        String string = json.getAsString();
        Intrinsics.checkNotNullExpressionValue((Object)string, (String)"json.asString");
        MatchResult matchResult = Regex.find$default((Regex)PATTERN, (CharSequence)string, (int)0, (int)2, null);
        Intrinsics.checkNotNull((Object)matchResult);
        MatchResult.Destructured destructured = matchResult.getDestructured();
        String start2 = (String)destructured.getMatch().getGroupValues().get(1);
        String end2 = (String)destructured.getMatch().getGroupValues().get(2);
        return ((CharSequence)end2).length() == 0 ? new IntRange(Integer.parseInt(start2), Integer.parseInt(start2)) : new IntRange(Integer.parseInt(start2), Integer.parseInt(end2));
    }
}

