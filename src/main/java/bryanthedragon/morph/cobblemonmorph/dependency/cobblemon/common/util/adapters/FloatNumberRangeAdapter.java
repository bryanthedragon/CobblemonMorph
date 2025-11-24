/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.gson.JsonDeserializationContext
 *  com.google.gson.JsonDeserializer
 *  com.google.gson.JsonElement
 *  com.google.gson.JsonSerializationContext
 *  com.google.gson.JsonSerializer
 *  kotlin.Metadata
 *  kotlin.jvm.internal.Intrinsics
 *  net.minecraft.advancements.critereon.MinMaxBounds$Doubles
 *  org.jetbrains.annotations.NotNull
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.adapters;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import java.lang.reflect.Type;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.minecraft.advancements.critereon.MinMaxBounds;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000,\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0005\b\u00c6\u0002\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u00012\b\u0012\u0004\u0012\u00020\u00020\u0003B\t\b\u0002\u00a2\u0006\u0004\b\u0010\u0010\u0011J'\u0010\n\u001a\u00020\u00022\u0006\u0010\u0005\u001a\u00020\u00042\u0006\u0010\u0007\u001a\u00020\u00062\u0006\u0010\t\u001a\u00020\bH\u0016\u00a2\u0006\u0004\b\n\u0010\u000bJ'\u0010\u000e\u001a\u00020\u00042\u0006\u0010\f\u001a\u00020\u00022\u0006\u0010\u0007\u001a\u00020\u00062\u0006\u0010\t\u001a\u00020\rH\u0016\u00a2\u0006\u0004\b\u000e\u0010\u000f\u00a8\u0006\u0012"}, d2={"Lcom/cobblemon/mod/common/util/adapters/FloatNumberRangeAdapter;", "Lcom/google/gson/JsonDeserializer;", "Lnet/minecraft/predicate/NumberRange$FloatRange;", "Lcom/google/gson/JsonSerializer;", "Lcom/google/gson/JsonElement;", "element", "Ljava/lang/reflect/Type;", "type", "Lcom/google/gson/JsonDeserializationContext;", "context", "deserialize", "(Lcom/google/gson/JsonElement;Ljava/lang/reflect/Type;Lcom/google/gson/JsonDeserializationContext;)Lnet/minecraft/advancements/critereon/MinMaxBounds$Doubles;", "range", "Lcom/google/gson/JsonSerializationContext;", "serialize", "(Lnet/minecraft/advancements/critereon/MinMaxBounds$Doubles;Ljava/lang/reflect/Type;Lcom/google/gson/JsonSerializationContext;)Lcom/google/gson/JsonElement;", "<init>", "()V", "common"})
public final class FloatNumberRangeAdapter
implements JsonDeserializer<MinMaxBounds.Doubles>,
JsonSerializer<MinMaxBounds.Doubles> {
    @NotNull
    public static final FloatNumberRangeAdapter INSTANCE = new FloatNumberRangeAdapter();

    private FloatNumberRangeAdapter() {
    }

    @NotNull
    public MinMaxBounds.Doubles deserialize(@NotNull JsonElement element, @NotNull Type type, @NotNull JsonDeserializationContext context) {
        Intrinsics.checkNotNullParameter((Object)element, (String)"element");
        Intrinsics.checkNotNullParameter((Object)type, (String)"type");
        Intrinsics.checkNotNullParameter((Object)context, (String)"context");
        MinMaxBounds.Doubles doubles = MinMaxBounds.Doubles.m_154791_((JsonElement)element);
        Intrinsics.checkNotNullExpressionValue((Object)doubles, (String)"fromJson(element)");
        return doubles;
    }

    @NotNull
    public JsonElement serialize(@NotNull MinMaxBounds.Doubles range, @NotNull Type type, @NotNull JsonSerializationContext context) {
        Intrinsics.checkNotNullParameter((Object)range, (String)"range");
        Intrinsics.checkNotNullParameter((Object)type, (String)"type");
        Intrinsics.checkNotNullParameter((Object)context, (String)"context");
        JsonElement jsonElement = range.m_55328_();
        Intrinsics.checkNotNullExpressionValue((Object)jsonElement, (String)"range.toJson()");
        return jsonElement;
    }
}

