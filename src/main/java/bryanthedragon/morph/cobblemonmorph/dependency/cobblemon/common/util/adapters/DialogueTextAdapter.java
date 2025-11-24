/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.gson.JsonArray
 *  com.google.gson.JsonDeserializationContext
 *  com.google.gson.JsonDeserializer
 *  com.google.gson.JsonElement
 *  com.google.gson.JsonObject
 *  com.google.gson.JsonParseException
 *  kotlin.Metadata
 *  kotlin.collections.CollectionsKt
 *  kotlin.jvm.internal.Intrinsics
 *  kotlin.jvm.internal.SourceDebugExtension
 *  org.jetbrains.annotations.NotNull
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.adapters;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.dialogue.DialogueText;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.dialogue.ExpressionLikeDialogueText;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.dialogue.WrappedDialogueText;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.molang.ExpressionLike;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.text.TextKt;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.MoLangExtensionsKt;
import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000 \n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\b\u00c6\u0002\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001B\t\b\u0002\u00a2\u0006\u0004\b\u000b\u0010\fJ'\u0010\t\u001a\u00020\u00022\u0006\u0010\u0004\u001a\u00020\u00032\u0006\u0010\u0006\u001a\u00020\u00052\u0006\u0010\b\u001a\u00020\u0007H\u0016\u00a2\u0006\u0004\b\t\u0010\n\u00a8\u0006\r"}, d2={"Lcom/cobblemon/mod/common/util/adapters/DialogueTextAdapter;", "Lcom/google/gson/JsonDeserializer;", "Lcom/cobblemon/mod/common/api/dialogue/DialogueText;", "Lcom/google/gson/JsonElement;", "json", "Ljava/lang/reflect/Type;", "typeOfT", "Lcom/google/gson/JsonDeserializationContext;", "context", "deserialize", "(Lcom/google/gson/JsonElement;Ljava/lang/reflect/Type;Lcom/google/gson/JsonDeserializationContext;)Lcom/cobblemon/mod/common/api/dialogue/DialogueText;", "<init>", "()V", "common"})
@SourceDebugExtension(value={"SMAP\nDialogueTextAdapter.kt\nKotlin\n*S Kotlin\n*F\n+ 1 DialogueTextAdapter.kt\ncom/cobblemon/mod/common/util/adapters/DialogueTextAdapter\n+ 2 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n*L\n1#1,35:1\n1549#2:36\n1620#2,3:37\n*S KotlinDebug\n*F\n+ 1 DialogueTextAdapter.kt\ncom/cobblemon/mod/common/util/adapters/DialogueTextAdapter\n*L\n27#1:36\n27#1:37,3\n*E\n"})
public final class DialogueTextAdapter
implements JsonDeserializer<DialogueText> {
    @NotNull
    public static final DialogueTextAdapter INSTANCE = new DialogueTextAdapter();

    private DialogueTextAdapter() {
    }

    /*
     * WARNING - void declaration
     */
    @NotNull
    public DialogueText deserialize(@NotNull JsonElement json, @NotNull Type typeOfT, @NotNull JsonDeserializationContext context) {
        DialogueText dialogueText;
        Intrinsics.checkNotNullParameter((Object)json, (String)"json");
        Intrinsics.checkNotNullParameter((Object)typeOfT, (String)"typeOfT");
        Intrinsics.checkNotNullParameter((Object)context, (String)"context");
        if (json.isJsonPrimitive()) {
            String string = json.getAsString();
            Intrinsics.checkNotNullExpressionValue((Object)string, (String)"json.asString");
            dialogueText = new WrappedDialogueText(TextKt.text(string));
        } else if (json.isJsonArray()) {
            void $this$mapTo$iv$iv;
            JsonArray jsonArray = json.getAsJsonArray();
            Intrinsics.checkNotNullExpressionValue((Object)jsonArray, (String)"json.asJsonArray");
            Iterable $this$map$iv = (Iterable)jsonArray;
            boolean $i$f$map = false;
            Iterable iterable = $this$map$iv;
            Collection destination$iv$iv = new ArrayList(CollectionsKt.collectionSizeOrDefault((Iterable)$this$map$iv, (int)10));
            boolean $i$f$mapTo = false;
            for (Object item$iv$iv : $this$mapTo$iv$iv) {
                void it;
                JsonElement jsonElement = (JsonElement)item$iv$iv;
                Collection collection = destination$iv$iv;
                boolean bl = false;
                collection.add(it.getAsString());
            }
            ExpressionLike expressionLike = MoLangExtensionsKt.asExpressionLike((List)destination$iv$iv);
            dialogueText = new ExpressionLikeDialogueText(expressionLike);
        } else {
            JsonObject obj = json.getAsJsonObject();
            String typeId = obj.get("type").getAsString();
            Class<? extends DialogueText> clazz = DialogueText.Companion.getTypes().get(typeId);
            if (clazz == null) {
                throw new JsonParseException("Unknown dialogue text type " + typeId);
            }
            Class<? extends DialogueText> clazz2 = clazz;
            Object object = context.deserialize(json, (Type)clazz2);
            Intrinsics.checkNotNullExpressionValue((Object)object, (String)"{\n            val obj = \u2026ze(json, clazz)\n        }");
            dialogueText = (DialogueText)object;
        }
        return dialogueText;
    }
}

