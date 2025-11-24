/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.gson.JsonDeserializationContext
 *  com.google.gson.JsonDeserializer
 *  com.google.gson.JsonElement
 *  com.google.gson.JsonObject
 *  com.google.gson.JsonParseException
 *  kotlin.Metadata
 *  kotlin.jvm.internal.Intrinsics
 *  org.jetbrains.annotations.NotNull
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.adapters;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.dialogue.DialogueAction;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.dialogue.input.DialogueAutoContinueInput;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.dialogue.input.DialogueInput;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.dialogue.input.DialogueNoInput;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.dialogue.input.DialogueOptionSetInput;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.dialogue.input.DialogueTextInput;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import java.lang.reflect.Type;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000 \n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\b\u00c6\u0002\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001B\t\b\u0002\u00a2\u0006\u0004\b\u000b\u0010\fJ'\u0010\t\u001a\u00020\u00022\u0006\u0010\u0004\u001a\u00020\u00032\u0006\u0010\u0006\u001a\u00020\u00052\u0006\u0010\b\u001a\u00020\u0007H\u0016\u00a2\u0006\u0004\b\t\u0010\n\u00a8\u0006\r"}, d2={"Lcom/cobblemon/mod/common/util/adapters/DialogueInputAdapter;", "Lcom/google/gson/JsonDeserializer;", "Lcom/cobblemon/mod/common/api/dialogue/input/DialogueInput;", "Lcom/google/gson/JsonElement;", "json", "Ljava/lang/reflect/Type;", "typeOfT", "Lcom/google/gson/JsonDeserializationContext;", "context", "deserialize", "(Lcom/google/gson/JsonElement;Ljava/lang/reflect/Type;Lcom/google/gson/JsonDeserializationContext;)Lcom/cobblemon/mod/common/api/dialogue/input/DialogueInput;", "<init>", "()V", "common"})
public final class DialogueInputAdapter
implements JsonDeserializer<DialogueInput> {
    @NotNull
    public static final DialogueInputAdapter INSTANCE = new DialogueInputAdapter();

    private DialogueInputAdapter() {
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    @NotNull
    public DialogueInput deserialize(@NotNull JsonElement json, @NotNull Type typeOfT, @NotNull JsonDeserializationContext context) {
        Intrinsics.checkNotNullParameter((Object)json, (String)"json");
        Intrinsics.checkNotNullParameter((Object)typeOfT, (String)"typeOfT");
        Intrinsics.checkNotNullParameter((Object)context, (String)"context");
        if (json.isJsonPrimitive() || json.isJsonArray()) {
            Object object = context.deserialize(json, (Type)((Object)DialogueAction.class));
            Intrinsics.checkNotNullExpressionValue((Object)object, (String)"context.deserialize(json\u2026alogueAction::class.java)");
            return new DialogueNoInput((DialogueAction)object);
        }
        JsonObject obj = json.getAsJsonObject();
        String typeId = obj.get("type").getAsString();
        String string = typeId;
        if (string == null) throw new JsonParseException("Unknown dialogue input type " + typeId);
        int n = -1;
        switch (string.hashCode()) {
            case 898090757: {
                if (string.equals("auto-continue")) {
                    n = 1;
                }
                break;
            }
            case 3556653: {
                if (string.equals("text")) {
                    n = 2;
                }
                break;
            }
            case -1010136971: {
                if (string.equals("option")) {
                    n = 3;
                }
                break;
            }
        }
        switch (n) {
            case 2: {
                Object object = context.deserialize((JsonElement)obj, (Type)((Object)DialogueTextInput.class));
                Intrinsics.checkNotNullExpressionValue((Object)object, (String)"context.deserialize(obj,\u2026gueTextInput::class.java)");
                DialogueInput dialogueInput = (DialogueInput)object;
                return dialogueInput;
            }
            case 1: {
                Object object = context.deserialize((JsonElement)obj, (Type)((Object)DialogueAutoContinueInput.class));
                Intrinsics.checkNotNullExpressionValue((Object)object, (String)"context.deserialize(obj,\u2026ontinueInput::class.java)");
                DialogueInput dialogueInput = (DialogueInput)object;
                return dialogueInput;
            }
            case 3: {
                Object object = context.deserialize((JsonElement)obj, (Type)((Object)DialogueOptionSetInput.class));
                Intrinsics.checkNotNullExpressionValue((Object)object, (String)"context.deserialize(obj,\u2026tionSetInput::class.java)");
                DialogueInput dialogueInput = (DialogueInput)object;
                return dialogueInput;
            }
            default: {
                throw new JsonParseException("Unknown dialogue input type " + typeId);
            }
        }
    }
}

