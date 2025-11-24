/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.gson.JsonDeserializationContext
 *  com.google.gson.JsonDeserializer
 *  com.google.gson.JsonElement
 *  com.google.gson.JsonPrimitive
 *  com.google.gson.JsonSerializationContext
 *  com.google.gson.JsonSerializer
 *  kotlin.Metadata
 *  kotlin.jvm.internal.Intrinsics
 *  org.jetbrains.annotations.NotNull
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.adapters;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.context.RegisteredSpawningContext;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.context.SpawningContext;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import java.lang.reflect.Type;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u00000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\b\u00c6\u0002\u0018\u00002\f\u0012\b\u0012\u0006\u0012\u0002\b\u00030\u00020\u00012\f\u0012\b\u0012\u0006\u0012\u0002\b\u00030\u00020\u0003B\t\b\u0002\u00a2\u0006\u0004\b\u0011\u0010\u0012J+\u0010\n\u001a\u0006\u0012\u0002\b\u00030\u00022\u0006\u0010\u0005\u001a\u00020\u00042\u0006\u0010\u0007\u001a\u00020\u00062\u0006\u0010\t\u001a\u00020\bH\u0016\u00a2\u0006\u0004\b\n\u0010\u000bJ+\u0010\u000f\u001a\u00020\u000e2\n\u0010\f\u001a\u0006\u0012\u0002\b\u00030\u00022\u0006\u0010\u0007\u001a\u00020\u00062\u0006\u0010\t\u001a\u00020\rH\u0016\u00a2\u0006\u0004\b\u000f\u0010\u0010\u00a8\u0006\u0013"}, d2={"Lcom/cobblemon/mod/common/util/adapters/RegisteredSpawningContextAdapter;", "Lcom/google/gson/JsonSerializer;", "Lcom/cobblemon/mod/common/api/spawning/context/RegisteredSpawningContext;", "Lcom/google/gson/JsonDeserializer;", "Lcom/google/gson/JsonElement;", "json", "Ljava/lang/reflect/Type;", "type", "Lcom/google/gson/JsonDeserializationContext;", "ctx", "deserialize", "(Lcom/google/gson/JsonElement;Ljava/lang/reflect/Type;Lcom/google/gson/JsonDeserializationContext;)Lcom/cobblemon/mod/common/api/spawning/context/RegisteredSpawningContext;", "rctx", "Lcom/google/gson/JsonSerializationContext;", "Lcom/google/gson/JsonPrimitive;", "serialize", "(Lcom/cobblemon/mod/common/api/spawning/context/RegisteredSpawningContext;Ljava/lang/reflect/Type;Lcom/google/gson/JsonSerializationContext;)Lcom/google/gson/JsonPrimitive;", "<init>", "()V", "common"})
public final class RegisteredSpawningContextAdapter
implements JsonSerializer<RegisteredSpawningContext<?>>,
JsonDeserializer<RegisteredSpawningContext<?>> {
    @NotNull
    public static final RegisteredSpawningContextAdapter INSTANCE = new RegisteredSpawningContextAdapter();

    private RegisteredSpawningContextAdapter() {
    }

    @NotNull
    public JsonPrimitive serialize(@NotNull RegisteredSpawningContext<?> rctx, @NotNull Type type, @NotNull JsonSerializationContext ctx) {
        Intrinsics.checkNotNullParameter(rctx, (String)"rctx");
        Intrinsics.checkNotNullParameter((Object)type, (String)"type");
        Intrinsics.checkNotNullParameter((Object)ctx, (String)"ctx");
        return new JsonPrimitive(rctx.getName());
    }

    @NotNull
    public RegisteredSpawningContext<?> deserialize(@NotNull JsonElement json, @NotNull Type type, @NotNull JsonDeserializationContext ctx) {
        Intrinsics.checkNotNullParameter((Object)json, (String)"json");
        Intrinsics.checkNotNullParameter((Object)type, (String)"type");
        Intrinsics.checkNotNullParameter((Object)ctx, (String)"ctx");
        String string = json.getAsString();
        Intrinsics.checkNotNullExpressionValue((Object)string, (String)"json.asString");
        RegisteredSpawningContext<?> registeredSpawningContext = SpawningContext.Companion.getByName(string);
        if (registeredSpawningContext == null) {
            throw new IllegalArgumentException("No such spawning context: " + json.getAsString());
        }
        return registeredSpawningContext;
    }
}

