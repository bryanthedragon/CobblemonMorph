/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.gson.JsonArray
 *  com.google.gson.JsonDeserializationContext
 *  com.google.gson.JsonDeserializer
 *  com.google.gson.JsonElement
 *  com.google.gson.JsonPrimitive
 *  kotlin.Metadata
 *  kotlin.collections.CollectionsKt
 *  kotlin.jvm.internal.Intrinsics
 *  kotlin.jvm.internal.SourceDebugExtension
 *  org.jetbrains.annotations.NotNull
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.adapters;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.dialogue.ArtificialDialogueFaceProvider;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.dialogue.DialogueFaceProvider;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.dialogue.ExpressionLikeDialogueFaceProvider;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.molang.ExpressionLike;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.MoLangExtensionsKt;
import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000 \n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\b\u00c6\u0002\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001B\t\b\u0002\u00a2\u0006\u0004\b\u000b\u0010\fJ'\u0010\t\u001a\u00020\u00022\u0006\u0010\u0004\u001a\u00020\u00032\u0006\u0010\u0006\u001a\u00020\u00052\u0006\u0010\b\u001a\u00020\u0007H\u0016\u00a2\u0006\u0004\b\t\u0010\n\u00a8\u0006\r"}, d2={"Lcom/cobblemon/mod/common/util/adapters/DialogueFaceProviderAdapter;", "Lcom/google/gson/JsonDeserializer;", "Lcom/cobblemon/mod/common/api/dialogue/DialogueFaceProvider;", "Lcom/google/gson/JsonElement;", "json", "Ljava/lang/reflect/Type;", "typeOfT", "Lcom/google/gson/JsonDeserializationContext;", "context", "deserialize", "(Lcom/google/gson/JsonElement;Ljava/lang/reflect/Type;Lcom/google/gson/JsonDeserializationContext;)Lcom/cobblemon/mod/common/api/dialogue/DialogueFaceProvider;", "<init>", "()V", "common"})
@SourceDebugExtension(value={"SMAP\nDialogueFaceProviderAdapter.kt\nKotlin\n*S Kotlin\n*F\n+ 1 DialogueFaceProviderAdapter.kt\ncom/cobblemon/mod/common/util/adapters/DialogueFaceProviderAdapter\n+ 2 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n*L\n1#1,32:1\n1549#2:33\n1620#2,3:34\n*S KotlinDebug\n*F\n+ 1 DialogueFaceProviderAdapter.kt\ncom/cobblemon/mod/common/util/adapters/DialogueFaceProviderAdapter\n*L\n28#1:33\n28#1:34,3\n*E\n"})
public final class DialogueFaceProviderAdapter
implements JsonDeserializer<DialogueFaceProvider> {
    @NotNull
    public static final DialogueFaceProviderAdapter INSTANCE = new DialogueFaceProviderAdapter();

    private DialogueFaceProviderAdapter() {
    }

    /*
     * WARNING - void declaration
     */
    @NotNull
    public DialogueFaceProvider deserialize(@NotNull JsonElement json, @NotNull Type typeOfT, @NotNull JsonDeserializationContext context) {
        DialogueFaceProvider dialogueFaceProvider;
        Intrinsics.checkNotNullParameter((Object)json, (String)"json");
        Intrinsics.checkNotNullParameter((Object)typeOfT, (String)"typeOfT");
        Intrinsics.checkNotNullParameter((Object)context, (String)"context");
        JsonElement jsonElement = json;
        if (jsonElement instanceof JsonPrimitive) {
            String string = ((JsonPrimitive)json).getAsString();
            Intrinsics.checkNotNullExpressionValue((Object)string, (String)"json.asString");
            dialogueFaceProvider = new ExpressionLikeDialogueFaceProvider(MoLangExtensionsKt.asExpressionLike(string));
        } else if (jsonElement instanceof JsonArray) {
            void $this$mapTo$iv$iv;
            JsonArray jsonArray = ((JsonArray)json).getAsJsonArray();
            Intrinsics.checkNotNullExpressionValue((Object)jsonArray, (String)"json.asJsonArray");
            Iterable $this$map$iv = (Iterable)jsonArray;
            boolean $i$f$map = false;
            Iterable iterable = $this$map$iv;
            Collection destination$iv$iv = new ArrayList(CollectionsKt.collectionSizeOrDefault((Iterable)$this$map$iv, (int)10));
            boolean $i$f$mapTo = false;
            for (Object item$iv$iv : $this$mapTo$iv$iv) {
                void it;
                JsonElement jsonElement2 = (JsonElement)item$iv$iv;
                Collection collection = destination$iv$iv;
                boolean bl = false;
                collection.add(it.getAsString());
            }
            ExpressionLike expressionLike = MoLangExtensionsKt.asExpressionLike((List)destination$iv$iv);
            dialogueFaceProvider = new ExpressionLikeDialogueFaceProvider(expressionLike);
        } else {
            Object object = context.deserialize(json, (Type)((Object)ArtificialDialogueFaceProvider.class));
            Intrinsics.checkNotNullExpressionValue((Object)object, (String)"context.deserialize(json\u2026FaceProvider::class.java)");
            dialogueFaceProvider = (DialogueFaceProvider)object;
        }
        return dialogueFaceProvider;
    }
}

