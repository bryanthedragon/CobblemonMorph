/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.gson.JsonDeserializationContext
 *  com.google.gson.JsonDeserializer
 *  com.google.gson.JsonElement
 *  kotlin.Metadata
 *  kotlin.jvm.internal.Intrinsics
 *  org.jetbrains.annotations.NotNull
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.adapters;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.feature.SpeciesFeatureProvider;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.feature.SpeciesFeatures;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import java.lang.reflect.Type;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000 \n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\b\u00c6\u0002\u0018\u00002\f\u0012\b\u0012\u0006\u0012\u0002\b\u00030\u00020\u0001B\t\b\u0002\u00a2\u0006\u0004\b\u000b\u0010\fJ+\u0010\t\u001a\u0006\u0012\u0002\b\u00030\u00022\u0006\u0010\u0004\u001a\u00020\u00032\u0006\u0010\u0006\u001a\u00020\u00052\u0006\u0010\b\u001a\u00020\u0007H\u0016\u00a2\u0006\u0004\b\t\u0010\n\u00a8\u0006\r"}, d2={"Lcom/cobblemon/mod/common/util/adapters/SpeciesFeatureProviderAdapter;", "Lcom/google/gson/JsonDeserializer;", "Lcom/cobblemon/mod/common/api/pokemon/feature/SpeciesFeatureProvider;", "Lcom/google/gson/JsonElement;", "json", "Ljava/lang/reflect/Type;", "type", "Lcom/google/gson/JsonDeserializationContext;", "ctx", "deserialize", "(Lcom/google/gson/JsonElement;Ljava/lang/reflect/Type;Lcom/google/gson/JsonDeserializationContext;)Lcom/cobblemon/mod/common/api/pokemon/feature/SpeciesFeatureProvider;", "<init>", "()V", "common"})
public final class SpeciesFeatureProviderAdapter
implements JsonDeserializer<SpeciesFeatureProvider<?>> {
    @NotNull
    public static final SpeciesFeatureProviderAdapter INSTANCE = new SpeciesFeatureProviderAdapter();

    private SpeciesFeatureProviderAdapter() {
    }

    @NotNull
    public SpeciesFeatureProvider<?> deserialize(@NotNull JsonElement json, @NotNull Type type, @NotNull JsonDeserializationContext ctx) {
        Intrinsics.checkNotNullParameter((Object)json, (String)"json");
        Intrinsics.checkNotNullParameter((Object)type, (String)"type");
        Intrinsics.checkNotNullParameter((Object)ctx, (String)"ctx");
        String typeName = json.getAsJsonObject().get("type").getAsString();
        Class<? extends SpeciesFeatureProvider<?>> clazz = SpeciesFeatures.INSTANCE.getTypes().get(typeName);
        if (clazz == null) {
            throw new IllegalArgumentException("No type registered in SpeciesFeatures for name: " + typeName);
        }
        Class<? extends SpeciesFeatureProvider<?>> clazz2 = clazz;
        Object object = ctx.deserialize(json, (Type)clazz2);
        Intrinsics.checkNotNullExpressionValue((Object)object, (String)"ctx.deserialize(json, clazz)");
        return (SpeciesFeatureProvider)object;
    }
}

