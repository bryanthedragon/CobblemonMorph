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
 *  net.minecraft.core.Registry
 *  net.minecraft.resources.ResourceKey
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.tags.TagKey
 *  org.jetbrains.annotations.NotNull
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.adapters;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import java.lang.reflect.Type;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000:\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\u0018\u0000*\u0004\b\u0000\u0010\u00012\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00028\u00000\u00030\u00022\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00028\u00000\u00030\u0004B\u001b\u0012\u0012\u0010\u0013\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00028\u00000\u00120\u0011\u00a2\u0006\u0004\b\u0015\u0010\u0016J-\u0010\u000b\u001a\b\u0012\u0004\u0012\u00028\u00000\u00032\u0006\u0010\u0006\u001a\u00020\u00052\u0006\u0010\b\u001a\u00020\u00072\u0006\u0010\n\u001a\u00020\tH\u0016\u00a2\u0006\u0004\b\u000b\u0010\fJ-\u0010\u000f\u001a\u00020\u00052\f\u0010\r\u001a\b\u0012\u0004\u0012\u00028\u00000\u00032\u0006\u0010\b\u001a\u00020\u00072\u0006\u0010\n\u001a\u00020\u000eH\u0016\u00a2\u0006\u0004\b\u000f\u0010\u0010R \u0010\u0013\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00028\u00000\u00120\u00118\u0002X\u0082\u0004\u00a2\u0006\u0006\n\u0004\b\u0013\u0010\u0014\u00a8\u0006\u0017"}, d2={"Lcom/cobblemon/mod/common/util/adapters/TagKeyAdapter;", "T", "Lcom/google/gson/JsonDeserializer;", "Lnet/minecraft/tags/TagKey;", "Lcom/google/gson/JsonSerializer;", "Lcom/google/gson/JsonElement;", "element", "Ljava/lang/reflect/Type;", "type", "Lcom/google/gson/JsonDeserializationContext;", "ctx", "deserialize", "(Lcom/google/gson/JsonElement;Ljava/lang/reflect/Type;Lcom/google/gson/JsonDeserializationContext;)Lnet/minecraft/tags/TagKey;", "tagKey", "Lcom/google/gson/JsonSerializationContext;", "serialize", "(Lnet/minecraft/tags/TagKey;Ljava/lang/reflect/Type;Lcom/google/gson/JsonSerializationContext;)Lcom/google/gson/JsonElement;", "Lnet/minecraft/resources/ResourceKey;", "Lnet/minecraft/core/Registry;", "key", "Lnet/minecraft/resources/ResourceKey;", "<init>", "(Lnet/minecraft/resources/ResourceKey;)V", "common"})
public final class TagKeyAdapter<T>
implements JsonDeserializer<TagKey<T>>,
JsonSerializer<TagKey<T>> {
    @NotNull
    private final ResourceKey<Registry<T>> key;

    public TagKeyAdapter(@NotNull ResourceKey<Registry<T>> key) {
        Intrinsics.checkNotNullParameter(key, (String)"key");
        this.key = key;
    }

    @NotNull
    public TagKey<T> deserialize(@NotNull JsonElement element, @NotNull Type type, @NotNull JsonDeserializationContext ctx) {
        Intrinsics.checkNotNullParameter((Object)element, (String)"element");
        Intrinsics.checkNotNullParameter((Object)type, (String)"type");
        Intrinsics.checkNotNullParameter((Object)ctx, (String)"ctx");
        ResourceLocation identifier = new ResourceLocation(element.getAsString());
        TagKey tagKey = TagKey.m_203882_(this.key, (ResourceLocation)identifier);
        Intrinsics.checkNotNullExpressionValue((Object)tagKey, (String)"of(this.key, identifier)");
        return tagKey;
    }

    @NotNull
    public JsonElement serialize(@NotNull TagKey<T> tagKey, @NotNull Type type, @NotNull JsonSerializationContext ctx) {
        Intrinsics.checkNotNullParameter(tagKey, (String)"tagKey");
        Intrinsics.checkNotNullParameter((Object)type, (String)"type");
        Intrinsics.checkNotNullParameter((Object)ctx, (String)"ctx");
        return (JsonElement)new JsonPrimitive(tagKey.f_203868_().toString());
    }
}

