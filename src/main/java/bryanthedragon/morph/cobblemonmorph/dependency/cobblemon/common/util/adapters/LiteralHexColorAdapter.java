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
 *  kotlin.text.CharsKt
 *  kotlin.text.StringsKt
 *  org.jetbrains.annotations.NotNull
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.adapters;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import java.awt.Color;
import java.lang.reflect.Type;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.CharsKt;
import kotlin.text.StringsKt;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u00000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\b\u00c6\u0002\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u00012\b\u0012\u0004\u0012\u00020\u00020\u0003B\t\b\u0002\u00a2\u0006\u0004\b\u0011\u0010\u0012J'\u0010\n\u001a\u00020\u00022\u0006\u0010\u0005\u001a\u00020\u00042\u0006\u0010\u0007\u001a\u00020\u00062\u0006\u0010\t\u001a\u00020\bH\u0016\u00a2\u0006\u0004\b\n\u0010\u000bJ'\u0010\u000f\u001a\u00020\u000e2\u0006\u0010\f\u001a\u00020\u00022\u0006\u0010\u0007\u001a\u00020\u00062\u0006\u0010\t\u001a\u00020\rH\u0016\u00a2\u0006\u0004\b\u000f\u0010\u0010\u00a8\u0006\u0013"}, d2={"Lcom/cobblemon/mod/common/util/adapters/LiteralHexColorAdapter;", "Lcom/google/gson/JsonDeserializer;", "Ljava/awt/Color;", "Lcom/google/gson/JsonSerializer;", "Lcom/google/gson/JsonElement;", "element", "Ljava/lang/reflect/Type;", "type", "Lcom/google/gson/JsonDeserializationContext;", "context", "deserialize", "(Lcom/google/gson/JsonElement;Ljava/lang/reflect/Type;Lcom/google/gson/JsonDeserializationContext;)Ljava/awt/Color;", "color", "Lcom/google/gson/JsonSerializationContext;", "Lcom/google/gson/JsonPrimitive;", "serialize", "(Ljava/awt/Color;Ljava/lang/reflect/Type;Lcom/google/gson/JsonSerializationContext;)Lcom/google/gson/JsonPrimitive;", "<init>", "()V", "common"})
public final class LiteralHexColorAdapter
implements JsonDeserializer<Color>,
JsonSerializer<Color> {
    @NotNull
    public static final LiteralHexColorAdapter INSTANCE = new LiteralHexColorAdapter();

    private LiteralHexColorAdapter() {
    }

    @NotNull
    public Color deserialize(@NotNull JsonElement element, @NotNull Type type, @NotNull JsonDeserializationContext context) {
        Intrinsics.checkNotNullParameter((Object)element, (String)"element");
        Intrinsics.checkNotNullParameter((Object)type, (String)"type");
        Intrinsics.checkNotNullParameter((Object)context, (String)"context");
        String string = element.getAsString();
        Intrinsics.checkNotNullExpressionValue((Object)string, (String)"element.asString");
        String string2 = StringsKt.removePrefix((String)string, (CharSequence)"#");
        int n = 16;
        return new Color(Integer.parseInt(string2, CharsKt.checkRadix((int)n)));
    }

    @NotNull
    public JsonPrimitive serialize(@NotNull Color color, @NotNull Type type, @NotNull JsonSerializationContext context) {
        Intrinsics.checkNotNullParameter((Object)color, (String)"color");
        Intrinsics.checkNotNullParameter((Object)type, (String)"type");
        Intrinsics.checkNotNullParameter((Object)context, (String)"context");
        String string = Integer.toString(color.getRGB(), CharsKt.checkRadix((int)16));
        Intrinsics.checkNotNullExpressionValue((Object)string, (String)"toString(this, checkRadix(radix))");
        return new JsonPrimitive("#" + string);
    }
}

