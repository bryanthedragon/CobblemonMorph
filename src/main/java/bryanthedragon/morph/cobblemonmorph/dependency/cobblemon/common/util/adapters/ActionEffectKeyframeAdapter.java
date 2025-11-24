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
 *  org.jetbrains.annotations.NotNull
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.adapters;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.moves.animations.keyframes.ActionEffectKeyframe;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import java.lang.reflect.Type;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000 \n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\b\u00c6\u0002\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001B\t\b\u0002\u00a2\u0006\u0004\b\u000b\u0010\fJ'\u0010\t\u001a\u00020\u00022\u0006\u0010\u0004\u001a\u00020\u00032\u0006\u0010\u0006\u001a\u00020\u00052\u0006\u0010\b\u001a\u00020\u0007H\u0016\u00a2\u0006\u0004\b\t\u0010\n\u00a8\u0006\r"}, d2={"Lcom/cobblemon/mod/common/util/adapters/ActionEffectKeyframeAdapter;", "Lcom/google/gson/JsonDeserializer;", "Lcom/cobblemon/mod/common/api/moves/animations/keyframes/ActionEffectKeyframe;", "Lcom/google/gson/JsonElement;", "json", "Ljava/lang/reflect/Type;", "type", "Lcom/google/gson/JsonDeserializationContext;", "ctx", "deserialize", "(Lcom/google/gson/JsonElement;Ljava/lang/reflect/Type;Lcom/google/gson/JsonDeserializationContext;)Lcom/cobblemon/mod/common/api/moves/animations/keyframes/ActionEffectKeyframe;", "<init>", "()V", "common"})
public final class ActionEffectKeyframeAdapter
implements JsonDeserializer<ActionEffectKeyframe> {
    @NotNull
    public static final ActionEffectKeyframeAdapter INSTANCE = new ActionEffectKeyframeAdapter();

    private ActionEffectKeyframeAdapter() {
    }

    @NotNull
    public ActionEffectKeyframe deserialize(@NotNull JsonElement json, @NotNull Type type, @NotNull JsonDeserializationContext ctx) {
        ActionEffectKeyframe actionEffectKeyframe;
        Intrinsics.checkNotNullParameter((Object)json, (String)"json");
        Intrinsics.checkNotNullParameter((Object)type, (String)"type");
        Intrinsics.checkNotNullParameter((Object)ctx, (String)"ctx");
        if (json.isJsonPrimitive()) {
            Class<? extends ActionEffectKeyframe> clazz = ActionEffectKeyframe.Companion.getTypes().get(json.getAsString());
            if (clazz == null) {
                throw new IllegalArgumentException("Unrecognized action effect keyframe type: " + json.getAsJsonPrimitive());
            }
            Class<? extends ActionEffectKeyframe> clazz2 = clazz;
            ActionEffectKeyframe actionEffectKeyframe2 = clazz2.getConstructor(new Class[0]).newInstance(new Object[0]);
            Intrinsics.checkNotNullExpressionValue((Object)actionEffectKeyframe2, (String)"{\n            val clazz \u2026).newInstance()\n        }");
            actionEffectKeyframe = actionEffectKeyframe2;
        } else {
            JsonObject cfr_ignored_0 = (JsonObject)json;
            String typeString = ((JsonObject)json).get("type").getAsString();
            Class<? extends ActionEffectKeyframe> clazz = ActionEffectKeyframe.Companion.getTypes().get(typeString);
            if (clazz == null) {
                throw new IllegalArgumentException("Unrecognized action effect keyframe type: " + typeString);
            }
            Class<? extends ActionEffectKeyframe> clazz3 = clazz;
            Object object = ctx.deserialize(json, (Type)clazz3);
            Intrinsics.checkNotNullExpressionValue((Object)object, (String)"{\n            json as Js\u2026ze(json, clazz)\n        }");
            actionEffectKeyframe = (ActionEffectKeyframe)object;
        }
        return actionEffectKeyframe;
    }
}

