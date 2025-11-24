/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.gson.JsonDeserializationContext
 *  com.google.gson.JsonDeserializer
 *  com.google.gson.JsonElement
 *  com.google.gson.JsonObject
 *  kotlin.Metadata
 *  kotlin.jvm.internal.Intrinsics
 *  kotlin.jvm.internal.SourceDebugExtension
 *  org.jetbrains.annotations.NotNull
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.adapters;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.bedrock.molang.Expression;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.rules.selector.ConditionalSpawningContextSelector;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.rules.selector.ExpressionSpawningContextSelector;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.rules.selector.SpawningContextSelector;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.MoLangExtensionsKt;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import java.lang.reflect.Type;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000 \n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\b\u00c6\u0002\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001B\t\b\u0002\u00a2\u0006\u0004\b\u000b\u0010\fJ'\u0010\t\u001a\u00020\u00022\u0006\u0010\u0004\u001a\u00020\u00032\u0006\u0010\u0006\u001a\u00020\u00052\u0006\u0010\b\u001a\u00020\u0007H\u0016\u00a2\u0006\u0004\b\t\u0010\n\u00a8\u0006\r"}, d2={"Lcom/cobblemon/mod/common/util/adapters/SpawningContextSelectorAdapter;", "Lcom/google/gson/JsonDeserializer;", "Lcom/cobblemon/mod/common/api/spawning/rules/selector/SpawningContextSelector;", "Lcom/google/gson/JsonElement;", "json", "Ljava/lang/reflect/Type;", "type", "Lcom/google/gson/JsonDeserializationContext;", "ctx", "deserialize", "(Lcom/google/gson/JsonElement;Ljava/lang/reflect/Type;Lcom/google/gson/JsonDeserializationContext;)Lcom/cobblemon/mod/common/api/spawning/rules/selector/SpawningContextSelector;", "<init>", "()V", "common"})
@SourceDebugExtension(value={"SMAP\nSpawningContextSelectorAdapter.kt\nKotlin\n*S Kotlin\n*F\n+ 1 SpawningContextSelectorAdapter.kt\ncom/cobblemon/mod/common/util/adapters/SpawningContextSelectorAdapter\n+ 2 fake.kt\nkotlin/jvm/internal/FakeKt\n*L\n1#1,33:1\n1#2:34\n*E\n"})
public final class SpawningContextSelectorAdapter
implements JsonDeserializer<SpawningContextSelector> {
    @NotNull
    public static final SpawningContextSelectorAdapter INSTANCE = new SpawningContextSelectorAdapter();

    private SpawningContextSelectorAdapter() {
    }

    @NotNull
    public SpawningContextSelector deserialize(@NotNull JsonElement json, @NotNull Type type, @NotNull JsonDeserializationContext ctx) {
        SpawningContextSelector spawningContextSelector;
        Intrinsics.checkNotNullParameter((Object)json, (String)"json");
        Intrinsics.checkNotNullParameter((Object)type, (String)"type");
        Intrinsics.checkNotNullParameter((Object)ctx, (String)"ctx");
        if (json.isJsonPrimitive()) {
            ExpressionSpawningContextSelector expressionSpawningContextSelector;
            String string = json.getAsString();
            Intrinsics.checkNotNullExpressionValue((Object)string, (String)"json.asString");
            Expression expression = MoLangExtensionsKt.asExpression(string);
            ExpressionSpawningContextSelector it = expressionSpawningContextSelector = new ExpressionSpawningContextSelector();
            boolean bl = false;
            it.setExpression(expression);
            spawningContextSelector = expressionSpawningContextSelector;
        } else {
            JsonObject cfr_ignored_0 = (JsonObject)json;
            JsonElement jsonElement = ((JsonObject)json).get("type");
            String string = jsonElement != null ? jsonElement.getAsString() : null;
            if (string == null) {
                Object object = ctx.deserialize(json, (Type)((Object)ConditionalSpawningContextSelector.class));
                Intrinsics.checkNotNullExpressionValue((Object)object, (String)"ctx.deserialize(json, Co\u2026textSelector::class.java)");
                return (SpawningContextSelector)object;
            }
            String type2 = string;
            Class<? extends SpawningContextSelector> clazz = SpawningContextSelector.Companion.getTypes().get(type2);
            if (clazz == null) {
                throw new IllegalArgumentException("Unknown spawn detail selector type: " + type2);
            }
            Class<? extends SpawningContextSelector> clazz2 = clazz;
            Object object = ctx.deserialize(json, (Type)clazz2);
            Intrinsics.checkNotNullExpressionValue((Object)object, (String)"{\n            json as Js\u2026ze(json, clazz)\n        }");
            spawningContextSelector = (SpawningContextSelector)object;
        }
        return spawningContextSelector;
    }
}

