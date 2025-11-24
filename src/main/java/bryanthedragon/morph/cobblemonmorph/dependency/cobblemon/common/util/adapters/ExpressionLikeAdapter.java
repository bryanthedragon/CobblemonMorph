/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.gson.JsonArray
 *  com.google.gson.JsonDeserializationContext
 *  com.google.gson.JsonDeserializer
 *  com.google.gson.JsonElement
 *  kotlin.Metadata
 *  kotlin.collections.CollectionsKt
 *  kotlin.jvm.internal.Intrinsics
 *  kotlin.jvm.internal.SourceDebugExtension
 *  org.jetbrains.annotations.NotNull
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.adapters;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.bedrock.molang.Expression;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.molang.ExpressionLike;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.molang.ListExpression;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.molang.SingleExpression;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.MoLangExtensionsKt;
import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000 \n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\b\u00c6\u0002\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001B\t\b\u0002\u00a2\u0006\u0004\b\u000b\u0010\fJ'\u0010\t\u001a\u00020\u00022\u0006\u0010\u0004\u001a\u00020\u00032\u0006\u0010\u0006\u001a\u00020\u00052\u0006\u0010\b\u001a\u00020\u0007H\u0016\u00a2\u0006\u0004\b\t\u0010\n\u00a8\u0006\r"}, d2={"Lcom/cobblemon/mod/common/util/adapters/ExpressionLikeAdapter;", "Lcom/google/gson/JsonDeserializer;", "Lcom/cobblemon/mod/common/api/molang/ExpressionLike;", "Lcom/google/gson/JsonElement;", "json", "Ljava/lang/reflect/Type;", "type", "Lcom/google/gson/JsonDeserializationContext;", "ctx", "deserialize", "(Lcom/google/gson/JsonElement;Ljava/lang/reflect/Type;Lcom/google/gson/JsonDeserializationContext;)Lcom/cobblemon/mod/common/api/molang/ExpressionLike;", "<init>", "()V", "common"})
@SourceDebugExtension(value={"SMAP\nExpressionLikeAdapter.kt\nKotlin\n*S Kotlin\n*F\n+ 1 ExpressionLikeAdapter.kt\ncom/cobblemon/mod/common/util/adapters/ExpressionLikeAdapter\n+ 2 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n*L\n1#1,39:1\n1360#2:40\n1446#2,5:41\n*S KotlinDebug\n*F\n+ 1 ExpressionLikeAdapter.kt\ncom/cobblemon/mod/common/util/adapters/ExpressionLikeAdapter\n*L\n33#1:40\n33#1:41,5\n*E\n"})
public final class ExpressionLikeAdapter
implements JsonDeserializer<ExpressionLike> {
    @NotNull
    public static final ExpressionLikeAdapter INSTANCE = new ExpressionLikeAdapter();

    private ExpressionLikeAdapter() {
    }

    /*
     * WARNING - void declaration
     */
    @NotNull
    public ExpressionLike deserialize(@NotNull JsonElement json, @NotNull Type type, @NotNull JsonDeserializationContext ctx) {
        ExpressionLike expressionLike;
        Intrinsics.checkNotNullParameter((Object)json, (String)"json");
        Intrinsics.checkNotNullParameter((Object)type, (String)"type");
        Intrinsics.checkNotNullParameter((Object)ctx, (String)"ctx");
        if (json.isJsonPrimitive()) {
            String string = json.getAsString();
            Intrinsics.checkNotNullExpressionValue((Object)string, (String)"json.asString");
            Expression expression = MoLangExtensionsKt.asExpression(string);
            Intrinsics.checkNotNullExpressionValue((Object)expression, (String)"json.asString.asExpression()");
            expressionLike = new SingleExpression(expression);
        } else if (json.isJsonArray()) {
            void $this$flatMapTo$iv$iv;
            JsonArray jsonArray = json.getAsJsonArray();
            Intrinsics.checkNotNullExpressionValue((Object)jsonArray, (String)"json.asJsonArray");
            Iterable $this$flatMap$iv = (Iterable)jsonArray;
            boolean $i$f$flatMap = false;
            Iterable iterable = $this$flatMap$iv;
            Collection destination$iv$iv = new ArrayList();
            boolean $i$f$flatMapTo = false;
            for (Object element$iv$iv : $this$flatMapTo$iv$iv) {
                JsonElement it = (JsonElement)element$iv$iv;
                boolean bl = false;
                String string = it.getAsString();
                Intrinsics.checkNotNullExpressionValue((Object)string, (String)"it.asString");
                List<Expression> list = MoLangExtensionsKt.asExpressions(string);
                Intrinsics.checkNotNullExpressionValue(list, (String)"it.asString.asExpressions()");
                Iterable list$iv$iv = list;
                CollectionsKt.addAll((Collection)destination$iv$iv, (Iterable)list$iv$iv);
            }
            List list = (List)destination$iv$iv;
            expressionLike = new ListExpression(list);
        } else {
            throw new IllegalArgumentException("Invalid expression JSON: " + json);
        }
        return expressionLike;
    }
}

