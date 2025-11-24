/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.gson.JsonDeserializationContext
 *  com.google.gson.JsonDeserializer
 *  com.google.gson.JsonElement
 *  com.google.gson.JsonObject
 *  com.google.gson.JsonPrimitive
 *  com.google.gson.JsonSerializationContext
 *  com.google.gson.JsonSerializer
 *  kotlin.Metadata
 *  kotlin.jvm.internal.Intrinsics
 *  org.jetbrains.annotations.NotNull
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.adapters;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.PokemonProperties;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import java.lang.reflect.Type;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u00004\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0007\b\u0016\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u00012\b\u0012\u0004\u0012\u00020\u00020\u0003B\u000f\u0012\u0006\u0010\u0011\u001a\u00020\u0010\u00a2\u0006\u0004\b\u0015\u0010\u0016J'\u0010\n\u001a\u00020\u00022\u0006\u0010\u0005\u001a\u00020\u00042\u0006\u0010\u0007\u001a\u00020\u00062\u0006\u0010\t\u001a\u00020\bH\u0016\u00a2\u0006\u0004\b\n\u0010\u000bJ'\u0010\u000e\u001a\u00020\u00042\u0006\u0010\f\u001a\u00020\u00022\u0006\u0010\u0007\u001a\u00020\u00062\u0006\u0010\t\u001a\u00020\rH\u0016\u00a2\u0006\u0004\b\u000e\u0010\u000fR\u0017\u0010\u0011\u001a\u00020\u00108\u0006\u00a2\u0006\f\n\u0004\b\u0011\u0010\u0012\u001a\u0004\b\u0013\u0010\u0014\u00a8\u0006\u0017"}, d2={"Lcom/cobblemon/mod/common/util/adapters/PokemonPropertiesAdapter;", "Lcom/google/gson/JsonSerializer;", "Lcom/cobblemon/mod/common/api/pokemon/PokemonProperties;", "Lcom/google/gson/JsonDeserializer;", "Lcom/google/gson/JsonElement;", "json", "Ljava/lang/reflect/Type;", "type", "Lcom/google/gson/JsonDeserializationContext;", "ctx", "deserialize", "(Lcom/google/gson/JsonElement;Ljava/lang/reflect/Type;Lcom/google/gson/JsonDeserializationContext;)Lcom/cobblemon/mod/common/api/pokemon/PokemonProperties;", "props", "Lcom/google/gson/JsonSerializationContext;", "serialize", "(Lcom/cobblemon/mod/common/api/pokemon/PokemonProperties;Ljava/lang/reflect/Type;Lcom/google/gson/JsonSerializationContext;)Lcom/google/gson/JsonElement;", "", "saveLong", "Z", "getSaveLong", "()Z", "<init>", "(Z)V", "common"})
public class PokemonPropertiesAdapter
implements JsonSerializer<PokemonProperties>,
JsonDeserializer<PokemonProperties> {
    private final boolean saveLong;

    public PokemonPropertiesAdapter(boolean saveLong) {
        this.saveLong = saveLong;
    }

    public final boolean getSaveLong() {
        return this.saveLong;
    }

    @NotNull
    public JsonElement serialize(@NotNull PokemonProperties props, @NotNull Type type, @NotNull JsonSerializationContext ctx) {
        Intrinsics.checkNotNullParameter((Object)props, (String)"props");
        Intrinsics.checkNotNullParameter((Object)type, (String)"type");
        Intrinsics.checkNotNullParameter((Object)ctx, (String)"ctx");
        return this.saveLong ? (JsonElement)props.saveToJSON() : (JsonElement)new JsonPrimitive(props.getOriginalString());
    }

    @NotNull
    public PokemonProperties deserialize(@NotNull JsonElement json, @NotNull Type type, @NotNull JsonDeserializationContext ctx) {
        PokemonProperties pokemonProperties;
        Intrinsics.checkNotNullParameter((Object)json, (String)"json");
        Intrinsics.checkNotNullParameter((Object)type, (String)"type");
        Intrinsics.checkNotNullParameter((Object)ctx, (String)"ctx");
        if (json.isJsonPrimitive()) {
            String string = json.getAsString();
            Intrinsics.checkNotNullExpressionValue((Object)string, (String)"json.asString");
            pokemonProperties = PokemonProperties.Companion.parse$default(PokemonProperties.Companion, string, null, null, 6, null);
        } else {
            PokemonProperties pokemonProperties2 = new PokemonProperties();
            JsonObject jsonObject = json.getAsJsonObject();
            Intrinsics.checkNotNullExpressionValue((Object)jsonObject, (String)"json.asJsonObject");
            pokemonProperties = pokemonProperties2.loadFromJSON(jsonObject);
        }
        return pokemonProperties;
    }
}

