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
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.rules.selector.ExpressionSpawnDetailSelector;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.rules.selector.SpawnDetailSelector;
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

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000 \n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\b\u00c6\u0002\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001B\t\b\u0002\u00a2\u0006\u0004\b\u000b\u0010\fJ'\u0010\t\u001a\u00020\u00022\u0006\u0010\u0004\u001a\u00020\u00032\u0006\u0010\u0006\u001a\u00020\u00052\u0006\u0010\b\u001a\u00020\u0007H\u0016\u00a2\u0006\u0004\b\t\u0010\n\u00a8\u0006\r"}, d2={"Lcom/cobblemon/mod/common/util/adapters/SpawnDetailSelectorAdapter;", "Lcom/google/gson/JsonDeserializer;", "Lcom/cobblemon/mod/common/api/spawning/rules/selector/SpawnDetailSelector;", "Lcom/google/gson/JsonElement;", "json", "Ljava/lang/reflect/Type;", "type", "Lcom/google/gson/JsonDeserializationContext;", "ctx", "deserialize", "(Lcom/google/gson/JsonElement;Ljava/lang/reflect/Type;Lcom/google/gson/JsonDeserializationContext;)Lcom/cobblemon/mod/common/api/spawning/rules/selector/SpawnDetailSelector;", "<init>", "()V", "common"})
@SourceDebugExtension(value={"SMAP\nSpawnDetailSelectorAdapter.kt\nKotlin\n*S Kotlin\n*F\n+ 1 SpawnDetailSelectorAdapter.kt\ncom/cobblemon/mod/common/util/adapters/SpawnDetailSelectorAdapter\n+ 2 fake.kt\nkotlin/jvm/internal/FakeKt\n*L\n1#1,32:1\n1#2:33\n*E\n"})
public final class SpawnDetailSelectorAdapter
implements JsonDeserializer<SpawnDetailSelector> {
    @NotNull
    public static final SpawnDetailSelectorAdapter INSTANCE = new SpawnDetailSelectorAdapter();

    private SpawnDetailSelectorAdapter() {
    }

    @NotNull
    public SpawnDetailSelector deserialize(@NotNull JsonElement json, @NotNull Type type, @NotNull JsonDeserializationContext ctx) {
        SpawnDetailSelector spawnDetailSelector;
        Intrinsics.checkNotNullParameter((Object)json, (String)"json");
        Intrinsics.checkNotNullParameter((Object)type, (String)"type");
        Intrinsics.checkNotNullParameter((Object)ctx, (String)"ctx");
        if (json.isJsonPrimitive()) {
            ExpressionSpawnDetailSelector expressionSpawnDetailSelector;
            String string = json.getAsString();
            Intrinsics.checkNotNullExpressionValue((Object)string, (String)"json.asString");
            Expression expression = MoLangExtensionsKt.asExpression(string);
            ExpressionSpawnDetailSelector it = expressionSpawnDetailSelector = new ExpressionSpawnDetailSelector();
            boolean bl = false;
            it.setExpression(expression);
            spawnDetailSelector = expressionSpawnDetailSelector;
        } else {
            JsonObject cfr_ignored_0 = (JsonObject)json;
            String type2 = ((JsonObject)json).get("type").getAsString();
            Class<? extends SpawnDetailSelector> clazz = SpawnDetailSelector.Companion.getTypes().get(type2);
            if (clazz == null) {
                throw new IllegalArgumentException("Unknown spawn detail selector type: " + type2);
            }
            Class<? extends SpawnDetailSelector> clazz2 = clazz;
            Object object = ctx.deserialize(json, (Type)clazz2);
            Intrinsics.checkNotNullExpressionValue((Object)object, (String)"{\n            json as Js\u2026ze(json, clazz)\n        }");
            spawnDetailSelector = (SpawnDetailSelector)object;
        }
        return spawnDetailSelector;
    }
}

