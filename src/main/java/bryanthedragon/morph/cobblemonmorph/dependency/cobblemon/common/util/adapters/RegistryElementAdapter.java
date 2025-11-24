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
 *  kotlin.jvm.functions.Function0
 *  kotlin.jvm.internal.Intrinsics
 *  net.minecraft.core.Registry
 *  net.minecraft.resources.ResourceLocation
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
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u00006\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0007\u0018\u0000*\u0004\b\u0000\u0010\u00012\b\u0012\u0004\u0012\u00028\u00000\u00022\b\u0012\u0004\u0012\u00028\u00000\u0003B\u001b\u0012\u0012\u0010\u0012\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00028\u00000\u00110\u0010\u00a2\u0006\u0004\b\u0016\u0010\u0017J'\u0010\n\u001a\u00028\u00002\u0006\u0010\u0005\u001a\u00020\u00042\u0006\u0010\u0007\u001a\u00020\u00062\u0006\u0010\t\u001a\u00020\bH\u0016\u00a2\u0006\u0004\b\n\u0010\u000bJ'\u0010\u000e\u001a\u00020\u00042\u0006\u0010\f\u001a\u00028\u00002\u0006\u0010\u0007\u001a\u00020\u00062\u0006\u0010\t\u001a\u00020\rH\u0016\u00a2\u0006\u0004\b\u000e\u0010\u000fR#\u0010\u0012\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00028\u00000\u00110\u00108\u0006\u00a2\u0006\f\n\u0004\b\u0012\u0010\u0013\u001a\u0004\b\u0014\u0010\u0015\u00a8\u0006\u0018"}, d2={"Lcom/cobblemon/mod/common/util/adapters/RegistryElementAdapter;", "T", "Lcom/google/gson/JsonDeserializer;", "Lcom/google/gson/JsonSerializer;", "Lcom/google/gson/JsonElement;", "jElement", "Ljava/lang/reflect/Type;", "type", "Lcom/google/gson/JsonDeserializationContext;", "context", "deserialize", "(Lcom/google/gson/JsonElement;Ljava/lang/reflect/Type;Lcom/google/gson/JsonDeserializationContext;)Ljava/lang/Object;", "element", "Lcom/google/gson/JsonSerializationContext;", "serialize", "(Ljava/lang/Object;Ljava/lang/reflect/Type;Lcom/google/gson/JsonSerializationContext;)Lcom/google/gson/JsonElement;", "Lkotlin/Function0;", "Lnet/minecraft/core/Registry;", "registryProvider", "Lkotlin/jvm/functions/Function0;", "getRegistryProvider", "()Lkotlin/jvm/functions/Function0;", "<init>", "(Lkotlin/jvm/functions/Function0;)V", "common"})
public final class RegistryElementAdapter<T>
implements JsonDeserializer<T>,
JsonSerializer<T> {
    @NotNull
    private final Function0<Registry<T>> registryProvider;

    public RegistryElementAdapter(@NotNull Function0<? extends Registry<T>> registryProvider) {
        Intrinsics.checkNotNullParameter(registryProvider, (String)"registryProvider");
        this.registryProvider = registryProvider;
    }

    @NotNull
    public final Function0<Registry<T>> getRegistryProvider() {
        return this.registryProvider;
    }

    public T deserialize(@NotNull JsonElement jElement, @NotNull Type type, @NotNull JsonDeserializationContext context) {
        Intrinsics.checkNotNullParameter((Object)jElement, (String)"jElement");
        Intrinsics.checkNotNullParameter((Object)type, (String)"type");
        Intrinsics.checkNotNullParameter((Object)context, (String)"context");
        ResourceLocation identifier = (ResourceLocation)context.deserialize(jElement, (Type)((Object)ResourceLocation.class));
        Registry registry = (Registry)this.registryProvider.invoke();
        Object object = registry.m_7745_(identifier);
        if (object == null) {
            throw new IllegalArgumentException("Cannot resolve element '" + identifier + "' from " + registry.m_123023_().m_135782_());
        }
        return (T)object;
    }

    @NotNull
    public JsonElement serialize(T element, @NotNull Type type, @NotNull JsonSerializationContext context) {
        Intrinsics.checkNotNullParameter((Object)type, (String)"type");
        Intrinsics.checkNotNullParameter((Object)context, (String)"context");
        Registry registry = (Registry)this.registryProvider.invoke();
        ResourceLocation resourceLocation = registry.m_7981_(element);
        if (resourceLocation == null) {
            throw new IllegalArgumentException("Cannot resolve the identifier from the registry " + registry.m_123023_().m_135782_() + " for " + element);
        }
        ResourceLocation identifier = resourceLocation;
        return (JsonElement)new JsonPrimitive(identifier.toString());
    }
}

