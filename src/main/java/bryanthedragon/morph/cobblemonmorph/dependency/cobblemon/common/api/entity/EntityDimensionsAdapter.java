/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.gson.JsonDeserializationContext
 *  com.google.gson.JsonDeserializer
 *  com.google.gson.JsonElement
 *  com.google.gson.JsonObject
 *  com.google.gson.JsonSerializationContext
 *  com.google.gson.JsonSerializer
 *  kotlin.Metadata
 *  kotlin.jvm.internal.Intrinsics
 *  net.minecraft.world.entity.EntityDimensions
 *  org.jetbrains.annotations.NotNull
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.entity;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import java.lang.reflect.Type;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.minecraft.world.entity.EntityDimensions;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u00004\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0006\b\u00c6\u0002\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u00012\b\u0012\u0004\u0012\u00020\u00020\u0003B\t\b\u0002\u00a2\u0006\u0004\b\u0014\u0010\u0015J'\u0010\n\u001a\u00020\u00022\u0006\u0010\u0005\u001a\u00020\u00042\u0006\u0010\u0007\u001a\u00020\u00062\u0006\u0010\t\u001a\u00020\bH\u0016\u00a2\u0006\u0004\b\n\u0010\u000bJ'\u0010\u000e\u001a\u00020\u00042\u0006\u0010\f\u001a\u00020\u00022\u0006\u0010\u0007\u001a\u00020\u00062\u0006\u0010\t\u001a\u00020\rH\u0016\u00a2\u0006\u0004\b\u000e\u0010\u000fR\u0014\u0010\u0011\u001a\u00020\u00108\u0006X\u0086T\u00a2\u0006\u0006\n\u0004\b\u0011\u0010\u0012R\u0014\u0010\u0013\u001a\u00020\u00108\u0006X\u0086T\u00a2\u0006\u0006\n\u0004\b\u0013\u0010\u0012\u00a8\u0006\u0016"}, d2={"Lcom/cobblemon/mod/common/api/entity/EntityDimensionsAdapter;", "Lcom/google/gson/JsonSerializer;", "Lnet/minecraft/world/entity/EntityDimensions;", "Lcom/google/gson/JsonDeserializer;", "Lcom/google/gson/JsonElement;", "json", "Ljava/lang/reflect/Type;", "type", "Lcom/google/gson/JsonDeserializationContext;", "ctx", "deserialize", "(Lcom/google/gson/JsonElement;Ljava/lang/reflect/Type;Lcom/google/gson/JsonDeserializationContext;)Lnet/minecraft/world/entity/EntityDimensions;", "dimensions", "Lcom/google/gson/JsonSerializationContext;", "serialize", "(Lnet/minecraft/world/entity/EntityDimensions;Ljava/lang/reflect/Type;Lcom/google/gson/JsonSerializationContext;)Lcom/google/gson/JsonElement;", "", "HEIGHT", "Ljava/lang/String;", "WIDTH", "<init>", "()V", "common"})
public final class EntityDimensionsAdapter
implements JsonSerializer<EntityDimensions>,
JsonDeserializer<EntityDimensions> {
    @NotNull
    public static final EntityDimensionsAdapter INSTANCE = new EntityDimensionsAdapter();
    @NotNull
    public static final String WIDTH = "width";
    @NotNull
    public static final String HEIGHT = "height";

    private EntityDimensionsAdapter() {
    }

    @NotNull
    public JsonElement serialize(@NotNull EntityDimensions dimensions, @NotNull Type type, @NotNull JsonSerializationContext ctx) {
        Intrinsics.checkNotNullParameter((Object)dimensions, (String)"dimensions");
        Intrinsics.checkNotNullParameter((Object)type, (String)"type");
        Intrinsics.checkNotNullParameter((Object)ctx, (String)"ctx");
        JsonObject json = new JsonObject();
        json.addProperty(WIDTH, (Number)Float.valueOf(dimensions.f_20377_));
        json.addProperty(HEIGHT, (Number)Float.valueOf(dimensions.f_20378_));
        return (JsonElement)json;
    }

    @NotNull
    public EntityDimensions deserialize(@NotNull JsonElement json, @NotNull Type type, @NotNull JsonDeserializationContext ctx) {
        Intrinsics.checkNotNullParameter((Object)json, (String)"json");
        Intrinsics.checkNotNullParameter((Object)type, (String)"type");
        Intrinsics.checkNotNullParameter((Object)ctx, (String)"ctx");
        JsonObject cfr_ignored_0 = (JsonObject)json;
        return new EntityDimensions(((JsonObject)json).get(WIDTH).getAsFloat(), ((JsonObject)json).get(HEIGHT).getAsFloat(), false);
    }
}

