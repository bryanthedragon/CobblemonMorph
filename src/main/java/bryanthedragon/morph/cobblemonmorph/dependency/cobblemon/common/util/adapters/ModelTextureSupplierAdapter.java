/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.gson.JsonDeserializationContext
 *  com.google.gson.JsonDeserializer
 *  com.google.gson.JsonElement
 *  com.google.gson.JsonObject
 *  kotlin.Metadata
 *  kotlin.collections.CollectionsKt
 *  kotlin.jvm.internal.Intrinsics
 *  kotlin.jvm.internal.Reflection
 *  kotlin.jvm.internal.SourceDebugExtension
 *  net.minecraft.resources.ResourceLocation
 *  org.jetbrains.annotations.NotNull
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.adapters;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.AnimatedModelTextureSupplier;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.ModelTextureSupplier;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.StaticModelTextureSupplier;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Reflection;
import kotlin.jvm.internal.SourceDebugExtension;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000 \n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\b\u00c6\u0002\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001B\t\b\u0002\u00a2\u0006\u0004\b\u000b\u0010\fJ'\u0010\t\u001a\u00020\u00022\u0006\u0010\u0004\u001a\u00020\u00032\u0006\u0010\u0006\u001a\u00020\u00052\u0006\u0010\b\u001a\u00020\u0007H\u0016\u00a2\u0006\u0004\b\t\u0010\n\u00a8\u0006\r"}, d2={"Lcom/cobblemon/mod/common/util/adapters/ModelTextureSupplierAdapter;", "Lcom/google/gson/JsonDeserializer;", "Lcom/cobblemon/mod/common/client/render/ModelTextureSupplier;", "Lcom/google/gson/JsonElement;", "json", "Ljava/lang/reflect/Type;", "type", "Lcom/google/gson/JsonDeserializationContext;", "ctx", "deserialize", "(Lcom/google/gson/JsonElement;Ljava/lang/reflect/Type;Lcom/google/gson/JsonDeserializationContext;)Lcom/cobblemon/mod/common/client/render/ModelTextureSupplier;", "<init>", "()V", "common"})
@SourceDebugExtension(value={"SMAP\nModelTextureSupplierAdapter.kt\nKotlin\n*S Kotlin\n*F\n+ 1 ModelTextureSupplierAdapter.kt\ncom/cobblemon/mod/common/util/adapters/ModelTextureSupplierAdapter\n+ 2 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n*L\n1#1,47:1\n1549#2:48\n1620#2,3:49\n*S KotlinDebug\n*F\n+ 1 ModelTextureSupplierAdapter.kt\ncom/cobblemon/mod/common/util/adapters/ModelTextureSupplierAdapter\n*L\n36#1:48\n36#1:49,3\n*E\n"})
public final class ModelTextureSupplierAdapter
implements JsonDeserializer<ModelTextureSupplier> {
    @NotNull
    public static final ModelTextureSupplierAdapter INSTANCE = new ModelTextureSupplierAdapter();

    private ModelTextureSupplierAdapter() {
    }

    /*
     * WARNING - void declaration
     */
    @NotNull
    public ModelTextureSupplier deserialize(@NotNull JsonElement json, @NotNull Type type, @NotNull JsonDeserializationContext ctx) {
        Intrinsics.checkNotNullParameter((Object)json, (String)"json");
        Intrinsics.checkNotNullParameter((Object)type, (String)"type");
        Intrinsics.checkNotNullParameter((Object)ctx, (String)"ctx");
        if (json.isJsonPrimitive()) {
            return new StaticModelTextureSupplier(new ResourceLocation(json.getAsString()));
        }
        if (json.isJsonObject()) {
            Collection destination$iv$iv;
            JsonObject jsonObject = (JsonObject)json;
            JsonElement jsonElement = jsonObject.get("loop");
            boolean loop = jsonElement != null ? jsonElement.getAsBoolean() : true;
            JsonElement jsonElement2 = jsonObject.get("fps");
            float fps = jsonElement2 != null ? jsonElement2.getAsFloat() : 1.0f;
            JsonElement jsonElement3 = jsonObject.get("frames");
            if (jsonElement3 != null && (jsonElement3 = jsonElement3.getAsJsonArray()) != null) {
                void $this$mapTo$iv$iv;
                Iterable $this$map$iv = (Iterable)jsonElement3;
                boolean $i$f$map = false;
                Iterable iterable = $this$map$iv;
                destination$iv$iv = new ArrayList(CollectionsKt.collectionSizeOrDefault((Iterable)$this$map$iv, (int)10));
                boolean $i$f$mapTo = false;
                for (Object item$iv$iv : $this$mapTo$iv$iv) {
                    void it;
                    JsonElement jsonElement4 = (JsonElement)item$iv$iv;
                    Collection collection = destination$iv$iv;
                    boolean bl = false;
                    collection.add(new ResourceLocation(it.getAsString()));
                }
            } else {
                throw new IllegalArgumentException("Animated textures require a 'frames' value.");
            }
            List frames = (List)destination$iv$iv;
            return new AnimatedModelTextureSupplier(loop, fps, frames);
        }
        throw new IllegalArgumentException("Invalid JSON provided for model texture, it was of type " + Reflection.getOrCreateKotlinClass(json.getClass()).getSimpleName() + " instead of a String or Object.");
    }
}

