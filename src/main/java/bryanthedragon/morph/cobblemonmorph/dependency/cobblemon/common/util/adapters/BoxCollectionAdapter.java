/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.gson.JsonArray
 *  com.google.gson.JsonDeserializationContext
 *  com.google.gson.JsonDeserializer
 *  com.google.gson.JsonElement
 *  kotlin.Metadata
 *  kotlin.collections.CollectionsKt
 *  kotlin.jvm.internal.Intrinsics
 *  kotlin.jvm.internal.SourceDebugExtension
 *  net.minecraft.world.phys.AABB
 *  org.jetbrains.annotations.NotNull
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.adapters;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.block.BerryBlock;
import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import net.minecraft.world.phys.AABB;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u00000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u001e\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010%\n\u0002\u0010\u000e\n\u0002\b\u0007\b\u00c6\u0002\u0018\u00002\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00030\u00020\u0001B\t\b\u0002\u00a2\u0006\u0004\b\u0012\u0010\u0013J-\u0010\n\u001a\b\u0012\u0004\u0012\u00020\u00030\u00022\u0006\u0010\u0005\u001a\u00020\u00042\u0006\u0010\u0007\u001a\u00020\u00062\u0006\u0010\t\u001a\u00020\bH\u0016\u00a2\u0006\u0004\b\n\u0010\u000bR)\u0010\u000e\u001a\u0014\u0012\u0004\u0012\u00020\r\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00030\u00020\f8\u0006\u00a2\u0006\f\n\u0004\b\u000e\u0010\u000f\u001a\u0004\b\u0010\u0010\u0011\u00a8\u0006\u0014"}, d2={"Lcom/cobblemon/mod/common/util/adapters/BoxCollectionAdapter;", "Lcom/google/gson/JsonDeserializer;", "", "Lnet/minecraft/world/phys/AABB;", "Lcom/google/gson/JsonElement;", "json", "Ljava/lang/reflect/Type;", "type", "Lcom/google/gson/JsonDeserializationContext;", "ctx", "deserialize", "(Lcom/google/gson/JsonElement;Ljava/lang/reflect/Type;Lcom/google/gson/JsonDeserializationContext;)Ljava/util/Collection;", "", "", "boxesByName", "Ljava/util/Map;", "getBoxesByName", "()Ljava/util/Map;", "<init>", "()V", "common"})
@SourceDebugExtension(value={"SMAP\nBoxCollectionAdapter.kt\nKotlin\n*S Kotlin\n*F\n+ 1 BoxCollectionAdapter.kt\ncom/cobblemon/mod/common/util/adapters/BoxCollectionAdapter\n+ 2 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n*L\n1#1,69:1\n1549#2:70\n1620#2,3:71\n*S KotlinDebug\n*F\n+ 1 BoxCollectionAdapter.kt\ncom/cobblemon/mod/common/util/adapters/BoxCollectionAdapter\n*L\n66#1:70\n66#1:71,3\n*E\n"})
public final class BoxCollectionAdapter
implements JsonDeserializer<Collection<? extends AABB>> {
    @NotNull
    public static final BoxCollectionAdapter INSTANCE = new BoxCollectionAdapter();
    @NotNull
    private static final Map<String, Collection<AABB>> boxesByName = new LinkedHashMap();

    private BoxCollectionAdapter() {
    }

    @NotNull
    public final Map<String, Collection<AABB>> getBoxesByName() {
        return boxesByName;
    }

    /*
     * WARNING - void declaration
     */
    @NotNull
    public Collection<AABB> deserialize(@NotNull JsonElement json, @NotNull Type type, @NotNull JsonDeserializationContext ctx) {
        void $this$mapTo$iv$iv;
        Intrinsics.checkNotNullParameter((Object)json, (String)"json");
        Intrinsics.checkNotNullParameter((Object)type, (String)"type");
        Intrinsics.checkNotNullParameter((Object)ctx, (String)"ctx");
        if (json.isJsonPrimitive()) {
            Collection<AABB> collection = boxesByName.get(json.getAsString());
            if (collection == null) {
                throw new IllegalArgumentException("Unrecognized box collection name: " + json.getAsString());
            }
            return collection;
        }
        JsonArray jsonArray = json.getAsJsonArray();
        Intrinsics.checkNotNullExpressionValue((Object)jsonArray, (String)"json.asJsonArray");
        Iterable $this$map$iv = (Iterable)jsonArray;
        boolean $i$f$map = false;
        Iterable iterable = $this$map$iv;
        Collection destination$iv$iv = new ArrayList(CollectionsKt.collectionSizeOrDefault((Iterable)$this$map$iv, (int)10));
        boolean $i$f$mapTo = false;
        for (Object item$iv$iv : $this$mapTo$iv$iv) {
            void it;
            JsonElement jsonElement = (JsonElement)item$iv$iv;
            Collection collection = destination$iv$iv;
            boolean bl = false;
            collection.add((AABB)ctx.deserialize((JsonElement)it, (Type)((Object)AABB.class)));
        }
        return CollectionsKt.toList((Iterable)((List)destination$iv$iv));
    }

    static {
        boxesByName.put("standard-sprout", BerryBlock.Companion.getSTANDARD_SPROUT());
        boxesByName.put("standard-mature", BerryBlock.Companion.getSTANDARD_MATURE());
        boxesByName.put("short-sprout", BerryBlock.Companion.getSHORT_SPROUT());
        boxesByName.put("short-mature", BerryBlock.Companion.getSHORT_MATURE());
        boxesByName.put("volcano-sprout", BerryBlock.Companion.getVOLCANO_SPROUT());
        boxesByName.put("volcano-mature", BerryBlock.Companion.getVOLCANO_MATURE());
        boxesByName.put("nest-sprout", BerryBlock.Companion.getNEST_SPROUT());
        boxesByName.put("nest-mature", BerryBlock.Companion.getNEST_MATURE());
        boxesByName.put("frill-sprout", BerryBlock.Companion.getFRILL_SPROUT());
        boxesByName.put("frill-mature", BerryBlock.Companion.getFRILL_MATURE());
        boxesByName.put("block-sprout", BerryBlock.Companion.getBLOCK_SPROUT());
        boxesByName.put("block-mature", BerryBlock.Companion.getBLOCK_MATURE());
        boxesByName.put("pyramid-sprout", BerryBlock.Companion.getPYRAMID_SPROUT());
        boxesByName.put("pyramid-mature", BerryBlock.Companion.getPYRAMID_MATURE());
        boxesByName.put("tail-sprout", BerryBlock.Companion.getTAIL_SPROUT());
        boxesByName.put("tail-mature", BerryBlock.Companion.getTAIL_MATURE());
        boxesByName.put("sword-sprout", BerryBlock.Companion.getSWORD_SPROUT());
        boxesByName.put("sword-mature", BerryBlock.Companion.getSWORD_MATURE());
        boxesByName.put("platform-sprout", BerryBlock.Companion.getPLATFORM_SPROUT());
        boxesByName.put("platform-mature", BerryBlock.Companion.getPLATFORM_MATURE());
        boxesByName.put("stand-sprout", BerryBlock.Companion.getSTAND_SPROUT());
        boxesByName.put("stand-mature", BerryBlock.Companion.getSTAND_MATURE());
        boxesByName.put("cone-sprout", BerryBlock.Companion.getCONE_SPROUT());
        boxesByName.put("cone-mature", BerryBlock.Companion.getCONE_MATURE());
        boxesByName.put("squat-sprout", BerryBlock.Companion.getSQUAT_SPROUT());
        boxesByName.put("squat-mature", BerryBlock.Companion.getSQUAT_MATURE());
        boxesByName.put("lantern-sprout", BerryBlock.Companion.getLANTERN_SPROUT());
        boxesByName.put("lantern-mature", BerryBlock.Companion.getLANTERN_MATURE());
        boxesByName.put("box-sprout", BerryBlock.Companion.getBOX_SPROUT());
        boxesByName.put("box-mature", BerryBlock.Companion.getBOX_MATURE());
        boxesByName.put("blossom-sprout", BerryBlock.Companion.getBLOSSOM_SPROUT());
        boxesByName.put("blossom-mature", BerryBlock.Companion.getBLOSSOM_MATURE());
        boxesByName.put("lilypad-sprout", BerryBlock.Companion.getLILYPAD_SPROUT());
        boxesByName.put("lilypad-mature", BerryBlock.Companion.getLILYPAD_MATURE());
        boxesByName.put("tall-sprout", BerryBlock.Companion.getTALL_SPROUT());
        boxesByName.put("tall-mature", BerryBlock.Companion.getTALL_MATURE());
    }
}

