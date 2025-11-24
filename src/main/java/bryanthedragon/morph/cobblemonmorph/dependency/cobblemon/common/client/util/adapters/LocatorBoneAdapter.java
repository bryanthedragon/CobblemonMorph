/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.gson.JsonArray
 *  com.google.gson.JsonDeserializationContext
 *  com.google.gson.JsonDeserializer
 *  com.google.gson.JsonElement
 *  com.google.gson.JsonObject
 *  kotlin.Metadata
 *  kotlin.collections.CollectionsKt
 *  kotlin.jvm.internal.Intrinsics
 *  kotlin.jvm.internal.SourceDebugExtension
 *  org.jetbrains.annotations.NotNull
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.util.adapters;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.LocatorBone;
import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000 \n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\b\u00c6\u0002\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001B\t\b\u0002\u00a2\u0006\u0004\b\u000b\u0010\fJ'\u0010\t\u001a\u00020\u00022\u0006\u0010\u0004\u001a\u00020\u00032\u0006\u0010\u0006\u001a\u00020\u00052\u0006\u0010\b\u001a\u00020\u0007H\u0016\u00a2\u0006\u0004\b\t\u0010\n\u00a8\u0006\r"}, d2={"Lcom/cobblemon/mod/common/client/util/adapters/LocatorBoneAdapter;", "Lcom/google/gson/JsonDeserializer;", "Lcom/cobblemon/mod/common/client/render/models/blockbench/LocatorBone;", "Lcom/google/gson/JsonElement;", "json", "Ljava/lang/reflect/Type;", "typeOfT", "Lcom/google/gson/JsonDeserializationContext;", "ctx", "deserialize", "(Lcom/google/gson/JsonElement;Ljava/lang/reflect/Type;Lcom/google/gson/JsonDeserializationContext;)Lcom/cobblemon/mod/common/client/render/models/blockbench/LocatorBone;", "<init>", "()V", "common"})
@SourceDebugExtension(value={"SMAP\nLocatorBoneAdapter.kt\nKotlin\n*S Kotlin\n*F\n+ 1 LocatorBoneAdapter.kt\ncom/cobblemon/mod/common/client/util/adapters/LocatorBoneAdapter\n+ 2 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n*L\n1#1,43:1\n1549#2:44\n1620#2,3:45\n1549#2:48\n1620#2,3:49\n*S KotlinDebug\n*F\n+ 1 LocatorBoneAdapter.kt\ncom/cobblemon/mod/common/client/util/adapters/LocatorBoneAdapter\n*L\n35#1:44\n35#1:45,3\n36#1:48\n36#1:49,3\n*E\n"})
public final class LocatorBoneAdapter
implements JsonDeserializer<LocatorBone> {
    @NotNull
    public static final LocatorBoneAdapter INSTANCE = new LocatorBoneAdapter();

    private LocatorBoneAdapter() {
    }

    @NotNull
    public LocatorBone deserialize(@NotNull JsonElement json, @NotNull Type typeOfT, @NotNull JsonDeserializationContext ctx) {
        Intrinsics.checkNotNullParameter((Object)json, (String)"json");
        Intrinsics.checkNotNullParameter((Object)typeOfT, (String)"typeOfT");
        Intrinsics.checkNotNullParameter((Object)ctx, (String)"ctx");
        List offset = null;
        List rotation = null;
        if (json instanceof JsonArray) {
            Object[] objectArray = new Float[]{Float.valueOf(((JsonArray)json).get(0).getAsFloat()), Float.valueOf(((JsonArray)json).get(1).getAsFloat()), Float.valueOf(((JsonArray)json).get(2).getAsFloat())};
            offset = CollectionsKt.listOf((Object[])objectArray);
            objectArray = new Float[]{Float.valueOf(0.0f), Float.valueOf(0.0f), Float.valueOf(0.0f)};
            rotation = CollectionsKt.listOf((Object[])objectArray);
        } else {
            List list;
            JsonElement jsonElement;
            JsonElement it;
            Collection collection;
            Object item$iv$iv;
            Iterator iterator;
            Object $this$mapTo$iv$iv;
            boolean $i$f$mapTo;
            Collection destination$iv$iv;
            boolean $i$f$map;
            Object $this$map$iv;
            JsonObject cfr_ignored_0 = (JsonObject)json;
            JsonElement jsonElement2 = ((JsonObject)json).get("offset");
            if (jsonElement2 != null && (jsonElement2 = jsonElement2.getAsJsonArray()) != null) {
                $this$map$iv = (Iterable)jsonElement2;
                $i$f$map = false;
                Object[] objectArray = $this$map$iv;
                destination$iv$iv = new ArrayList(CollectionsKt.collectionSizeOrDefault((Iterable)$this$map$iv, (int)10));
                $i$f$mapTo = false;
                iterator = $this$mapTo$iv$iv.iterator();
                while (iterator.hasNext()) {
                    item$iv$iv = iterator.next();
                    JsonElement jsonElement3 = (JsonElement)item$iv$iv;
                    collection = destination$iv$iv;
                    boolean bl = false;
                    collection.add(Float.valueOf(it.getAsFloat()));
                }
                v1 = (List)destination$iv$iv;
            } else {
                $this$map$iv = new Float[]{Float.valueOf(0.0f), Float.valueOf(0.0f), Float.valueOf(0.0f)};
                v1 = offset = CollectionsKt.listOf((Object[])$this$map$iv);
            }
            if ((jsonElement = ((JsonObject)json).get("rotation")) != null && (jsonElement = jsonElement.getAsJsonArray()) != null) {
                $this$map$iv = (Iterable)jsonElement;
                $i$f$map = false;
                $this$mapTo$iv$iv = $this$map$iv;
                destination$iv$iv = new ArrayList(CollectionsKt.collectionSizeOrDefault((Iterable)$this$map$iv, (int)10));
                $i$f$mapTo = false;
                iterator = $this$mapTo$iv$iv.iterator();
                while (iterator.hasNext()) {
                    item$iv$iv = iterator.next();
                    it = (JsonElement)item$iv$iv;
                    collection = destination$iv$iv;
                    boolean bl = false;
                    collection.add(Float.valueOf(it.getAsFloat()));
                }
                list = (List)destination$iv$iv;
            } else {
                Object[] objectArray = new Float[]{Float.valueOf(0.0f), Float.valueOf(0.0f), Float.valueOf(0.0f)};
                list = CollectionsKt.listOf((Object[])objectArray);
            }
            rotation = list;
        }
        return new LocatorBone(offset, rotation);
    }
}

