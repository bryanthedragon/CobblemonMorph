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

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.SpawnDetailPresets;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.preset.SpawnDetailPreset;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import java.lang.reflect.Type;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000 \n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\b\u00c6\u0002\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001B\t\b\u0002\u00a2\u0006\u0004\b\u000b\u0010\fJ'\u0010\t\u001a\u00020\u00022\u0006\u0010\u0004\u001a\u00020\u00032\u0006\u0010\u0006\u001a\u00020\u00052\u0006\u0010\b\u001a\u00020\u0007H\u0016\u00a2\u0006\u0004\b\t\u0010\n\u00a8\u0006\r"}, d2={"Lcom/cobblemon/mod/common/util/adapters/SpawnDetailPresetAdapter;", "Lcom/google/gson/JsonDeserializer;", "Lcom/cobblemon/mod/common/api/spawning/preset/SpawnDetailPreset;", "Lcom/google/gson/JsonElement;", "json", "Ljava/lang/reflect/Type;", "type", "Lcom/google/gson/JsonDeserializationContext;", "ctx", "deserialize", "(Lcom/google/gson/JsonElement;Ljava/lang/reflect/Type;Lcom/google/gson/JsonDeserializationContext;)Lcom/cobblemon/mod/common/api/spawning/preset/SpawnDetailPreset;", "<init>", "()V", "common"})
public final class SpawnDetailPresetAdapter
implements JsonDeserializer<SpawnDetailPreset> {
    @NotNull
    public static final SpawnDetailPresetAdapter INSTANCE = new SpawnDetailPresetAdapter();

    private SpawnDetailPresetAdapter() {
    }

    @NotNull
    public SpawnDetailPreset deserialize(@NotNull JsonElement json, @NotNull Type type, @NotNull JsonDeserializationContext ctx) {
        Intrinsics.checkNotNullParameter((Object)json, (String)"json");
        Intrinsics.checkNotNullParameter((Object)type, (String)"type");
        Intrinsics.checkNotNullParameter((Object)ctx, (String)"ctx");
        JsonObject cfr_ignored_0 = (JsonObject)json;
        JsonElement jsonElement = ((JsonObject)json).get("type");
        String string = jsonElement != null ? jsonElement.getAsString() : null;
        if (string == null) {
            string = "basic";
        }
        String type2 = string;
        Class<? extends SpawnDetailPreset> clazz = SpawnDetailPresets.INSTANCE.getPresetTypes().get(type2);
        if (clazz == null) {
            throw new IllegalStateException("Unrecognized preset type: " + type2);
        }
        Class<? extends SpawnDetailPreset> clazz2 = clazz;
        Object object = ctx.deserialize(json, (Type)clazz2);
        Intrinsics.checkNotNullExpressionValue((Object)object, (String)"ctx.deserialize(json, clazz)");
        return (SpawnDetailPreset)object;
    }
}

