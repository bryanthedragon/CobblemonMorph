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
 *  net.minecraft.world.phys.AABB
 *  org.jetbrains.annotations.NotNull
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.adapters;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import java.lang.reflect.Type;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.minecraft.world.phys.AABB;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000 \n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\b\u00c6\u0002\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001B\t\b\u0002\u00a2\u0006\u0004\b\u000b\u0010\fJ'\u0010\t\u001a\u00020\u00022\u0006\u0010\u0004\u001a\u00020\u00032\u0006\u0010\u0006\u001a\u00020\u00052\u0006\u0010\b\u001a\u00020\u0007H\u0016\u00a2\u0006\u0004\b\t\u0010\n\u00a8\u0006\r"}, d2={"Lcom/cobblemon/mod/common/util/adapters/BoxAdapter;", "Lcom/google/gson/JsonDeserializer;", "Lnet/minecraft/world/phys/AABB;", "Lcom/google/gson/JsonElement;", "json", "Ljava/lang/reflect/Type;", "type", "Lcom/google/gson/JsonDeserializationContext;", "ctx", "deserialize", "(Lcom/google/gson/JsonElement;Ljava/lang/reflect/Type;Lcom/google/gson/JsonDeserializationContext;)Lnet/minecraft/world/phys/AABB;", "<init>", "()V", "common"})
public final class BoxAdapter
implements JsonDeserializer<AABB> {
    @NotNull
    public static final BoxAdapter INSTANCE = new BoxAdapter();

    private BoxAdapter() {
    }

    @NotNull
    public AABB deserialize(@NotNull JsonElement json, @NotNull Type type, @NotNull JsonDeserializationContext ctx) {
        Intrinsics.checkNotNullParameter((Object)json, (String)"json");
        Intrinsics.checkNotNullParameter((Object)type, (String)"type");
        Intrinsics.checkNotNullParameter((Object)ctx, (String)"ctx");
        JsonObject cfr_ignored_0 = (JsonObject)json;
        JsonElement jsonElement = ((JsonObject)json).get("minX");
        JsonElement jsonElement2 = ((JsonObject)json).get("minY");
        JsonElement jsonElement3 = ((JsonObject)json).get("minZ");
        JsonElement jsonElement4 = ((JsonObject)json).get("maxX");
        JsonElement jsonElement5 = ((JsonObject)json).get("maxY");
        JsonElement jsonElement6 = ((JsonObject)json).get("maxZ");
        return new AABB(jsonElement != null ? jsonElement.getAsDouble() : -9999999.0, jsonElement2 != null ? jsonElement2.getAsDouble() : 0.0, jsonElement3 != null ? jsonElement3.getAsDouble() : -9999999.0, jsonElement4 != null ? jsonElement4.getAsDouble() : 9999999.0, jsonElement5 != null ? jsonElement5.getAsDouble() : 9999999.0, jsonElement6 != null ? jsonElement6.getAsDouble() : 9999999.0);
    }
}

