/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.gson.JsonArray
 *  com.google.gson.JsonDeserializationContext
 *  com.google.gson.JsonDeserializer
 *  com.google.gson.JsonElement
 *  com.google.gson.JsonObject
 *  kotlin.Metadata
 *  kotlin.collections.CollectionsKt
 *  kotlin.jvm.internal.Intrinsics
 *  kotlin.jvm.internal.SourceDebugExtension
 *  org.jetbrains.annotations.NotNull
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.adapters;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.dialogue.DialogueAction;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.dialogue.ExpressionLikeDialogueAction;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.molang.ExpressionLike;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.MoLangExtensionsKt;
import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000 \n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\b\u00c6\u0002\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001B\t\b\u0002\u00a2\u0006\u0004\b\u000b\u0010\fJ'\u0010\t\u001a\u00020\u00022\u0006\u0010\u0004\u001a\u00020\u00032\u0006\u0010\u0006\u001a\u00020\u00052\u0006\u0010\b\u001a\u00020\u0007H\u0016\u00a2\u0006\u0004\b\t\u0010\n\u00a8\u0006\r"}, d2={"Lcom/cobblemon/mod/common/util/adapters/DialogueActionAdapter;", "Lcom/google/gson/JsonDeserializer;", "Lcom/cobblemon/mod/common/api/dialogue/DialogueAction;", "Lcom/google/gson/JsonElement;", "json", "Ljava/lang/reflect/Type;", "typeOfT", "Lcom/google/gson/JsonDeserializationContext;", "context", "deserialize", "(Lcom/google/gson/JsonElement;Ljava/lang/reflect/Type;Lcom/google/gson/JsonDeserializationContext;)Lcom/cobblemon/mod/common/api/dialogue/DialogueAction;", "<init>", "()V", "common"})
@SourceDebugExtension(value={"SMAP\nDialogueActionAdapter.kt\nKotlin\n*S Kotlin\n*F\n+ 1 DialogueActionAdapter.kt\ncom/cobblemon/mod/common/util/adapters/DialogueActionAdapter\n+ 2 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n*L\n1#1,33:1\n1549#2:34\n1620#2,3:35\n*S KotlinDebug\n*F\n+ 1 DialogueActionAdapter.kt\ncom/cobblemon/mod/common/util/adapters/DialogueActionAdapter\n*L\n29#1:34\n29#1:35,3\n*E\n"})
public final class DialogueActionAdapter
implements JsonDeserializer<DialogueAction> {
    @NotNull
    public static final DialogueActionAdapter INSTANCE = new DialogueActionAdapter();

    private DialogueActionAdapter() {
    }

    /*
     * WARNING - void declaration
     */
    @NotNull
    public DialogueAction deserialize(@NotNull JsonElement json, @NotNull Type typeOfT, @NotNull JsonDeserializationContext context) {
        DialogueAction dialogueAction;
        Intrinsics.checkNotNullParameter((Object)json, (String)"json");
        Intrinsics.checkNotNullParameter((Object)typeOfT, (String)"typeOfT");
        Intrinsics.checkNotNullParameter((Object)context, (String)"context");
        JsonElement jsonElement = json;
        if (jsonElement instanceof JsonObject) {
            String typeId = ((JsonObject)json).get("type").getAsString();
            Class<? extends DialogueAction> clazz = DialogueAction.Companion.getTypes().get(typeId);
            if (clazz == null) {
                throw new IllegalArgumentException("Unknown dialogue action type " + typeId);
            }
            Class<? extends DialogueAction> clazz2 = clazz;
            Object object = context.deserialize(json, (Type)clazz2);
            Intrinsics.checkNotNullExpressionValue((Object)object, (String)"{\n                val ty\u2026son, clazz)\n            }");
            dialogueAction = (DialogueAction)object;
        } else if (jsonElement instanceof JsonArray) {
            void $this$mapTo$iv$iv;
            List list = ((JsonArray)json).asList();
            Intrinsics.checkNotNullExpressionValue((Object)list, (String)"json.asList()");
            Iterable $this$map$iv = list;
            boolean $i$f$map = false;
            Iterable clazz2 = $this$map$iv;
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
            dialogueAction = new ExpressionLikeDialogueAction(expressionLike);
        } else {
            String string = json.getAsString();
            Intrinsics.checkNotNullExpressionValue((Object)string, (String)"json.asString");
            dialogueAction = new ExpressionLikeDialogueAction(MoLangExtensionsKt.asExpressionLike(string));
        }
        return dialogueAction;
    }
}

