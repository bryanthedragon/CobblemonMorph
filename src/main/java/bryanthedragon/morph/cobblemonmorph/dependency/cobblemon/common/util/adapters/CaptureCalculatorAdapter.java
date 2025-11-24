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
 *  kotlin.jvm.internal.Reflection
 *  org.jetbrains.annotations.NotNull
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.adapters;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.Cobblemon;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokeball.catching.calculators.CaptureCalculator;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokeball.catching.calculators.CaptureCalculators;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokeball.catching.calculators.CobblemonCaptureCalculator;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import java.lang.reflect.Type;
import java.util.Locale;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Reflection;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000,\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0005\b\u00c6\u0002\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u00012\b\u0012\u0004\u0012\u00020\u00020\u0003B\t\b\u0002\u00a2\u0006\u0004\b\u0010\u0010\u0011J'\u0010\n\u001a\u00020\u00022\u0006\u0010\u0005\u001a\u00020\u00042\u0006\u0010\u0007\u001a\u00020\u00062\u0006\u0010\t\u001a\u00020\bH\u0016\u00a2\u0006\u0004\b\n\u0010\u000bJ'\u0010\u000e\u001a\u00020\u00042\u0006\u0010\f\u001a\u00020\u00022\u0006\u0010\u0007\u001a\u00020\u00062\u0006\u0010\t\u001a\u00020\rH\u0016\u00a2\u0006\u0004\b\u000e\u0010\u000f\u00a8\u0006\u0012"}, d2={"Lcom/cobblemon/mod/common/util/adapters/CaptureCalculatorAdapter;", "Lcom/google/gson/JsonDeserializer;", "Lcom/cobblemon/mod/common/api/pokeball/catching/calculators/CaptureCalculator;", "Lcom/google/gson/JsonSerializer;", "Lcom/google/gson/JsonElement;", "element", "Ljava/lang/reflect/Type;", "type", "Lcom/google/gson/JsonDeserializationContext;", "context", "deserialize", "(Lcom/google/gson/JsonElement;Ljava/lang/reflect/Type;Lcom/google/gson/JsonDeserializationContext;)Lcom/cobblemon/mod/common/api/pokeball/catching/calculators/CaptureCalculator;", "calculator", "Lcom/google/gson/JsonSerializationContext;", "serialize", "(Lcom/cobblemon/mod/common/api/pokeball/catching/calculators/CaptureCalculator;Ljava/lang/reflect/Type;Lcom/google/gson/JsonSerializationContext;)Lcom/google/gson/JsonElement;", "<init>", "()V", "common"})
public final class CaptureCalculatorAdapter
implements JsonDeserializer<CaptureCalculator>,
JsonSerializer<CaptureCalculator> {
    @NotNull
    public static final CaptureCalculatorAdapter INSTANCE = new CaptureCalculatorAdapter();

    private CaptureCalculatorAdapter() {
    }

    @NotNull
    public CaptureCalculator deserialize(@NotNull JsonElement element, @NotNull Type type, @NotNull JsonDeserializationContext context) {
        Intrinsics.checkNotNullParameter((Object)element, (String)"element");
        Intrinsics.checkNotNullParameter((Object)type, (String)"type");
        Intrinsics.checkNotNullParameter((Object)context, (String)"context");
        String string = element.getAsString();
        Intrinsics.checkNotNullExpressionValue((Object)string, (String)"element.asString");
        String string2 = string.toLowerCase(Locale.ROOT);
        Intrinsics.checkNotNullExpressionValue((Object)string2, (String)"this as java.lang.String).toLowerCase(Locale.ROOT)");
        String id = string2;
        CaptureCalculator captureCalculator = CaptureCalculators.INSTANCE.fromId(id);
        if (captureCalculator == null) {
            Cobblemon.INSTANCE.getLOGGER().error("Failed to load CaptureCalculator from the ID {} defaulting to the {}", (Object)id, (Object)Reflection.getOrCreateKotlinClass(CobblemonCaptureCalculator.class).getSimpleName());
            return CobblemonCaptureCalculator.INSTANCE;
        }
        return captureCalculator;
    }

    @NotNull
    public JsonElement serialize(@NotNull CaptureCalculator calculator, @NotNull Type type, @NotNull JsonSerializationContext context) {
        Intrinsics.checkNotNullParameter((Object)calculator, (String)"calculator");
        Intrinsics.checkNotNullParameter((Object)type, (String)"type");
        Intrinsics.checkNotNullParameter((Object)context, (String)"context");
        String string = calculator.id().toLowerCase(Locale.ROOT);
        Intrinsics.checkNotNullExpressionValue((Object)string, (String)"this as java.lang.String).toLowerCase(Locale.ROOT)");
        return (JsonElement)new JsonPrimitive(string);
    }
}

