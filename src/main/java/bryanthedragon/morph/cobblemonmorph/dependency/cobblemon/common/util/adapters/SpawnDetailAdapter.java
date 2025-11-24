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
 *  kotlin.text.StringsKt
 *  net.minecraft.resources.ResourceLocation
 *  org.jetbrains.annotations.NotNull
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.adapters;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.Cobblemon;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.SpawnDetailPresets;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.SpawnLoader;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.condition.SpawningCondition;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.context.RegisteredSpawningContext;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.context.SpawningContext;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.detail.RegisteredSpawnDetail;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.detail.SpawnDetail;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.preset.SpawnDetailPreset;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.GsonExtensionsKt;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.ResourceLocationExtensionsKt;
import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import kotlin.text.StringsKt;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000 \n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\b\u00c6\u0002\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001B\t\b\u0002\u00a2\u0006\u0004\b\u000b\u0010\fJ'\u0010\t\u001a\u00020\u00022\u0006\u0010\u0004\u001a\u00020\u00032\u0006\u0010\u0006\u001a\u00020\u00052\u0006\u0010\b\u001a\u00020\u0007H\u0016\u00a2\u0006\u0004\b\t\u0010\n\u00a8\u0006\r"}, d2={"Lcom/cobblemon/mod/common/util/adapters/SpawnDetailAdapter;", "Lcom/google/gson/JsonDeserializer;", "Lcom/cobblemon/mod/common/api/spawning/detail/SpawnDetail;", "Lcom/google/gson/JsonElement;", "element", "Ljava/lang/reflect/Type;", "type", "Lcom/google/gson/JsonDeserializationContext;", "ctx", "deserialize", "(Lcom/google/gson/JsonElement;Ljava/lang/reflect/Type;Lcom/google/gson/JsonDeserializationContext;)Lcom/cobblemon/mod/common/api/spawning/detail/SpawnDetail;", "<init>", "()V", "common"})
@SourceDebugExtension(value={"SMAP\nSpawnDetailAdapter.kt\nKotlin\n*S Kotlin\n*F\n+ 1 SpawnDetailAdapter.kt\ncom/cobblemon/mod/common/util/adapters/SpawnDetailAdapter\n+ 2 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n+ 3 fake.kt\nkotlin/jvm/internal/FakeKt\n*L\n1#1,85:1\n1549#2:86\n1620#2,3:87\n1603#2,9:90\n1855#2:99\n1856#2:101\n1612#2:102\n1855#2,2:104\n1855#2,2:106\n1#3:100\n1#3:103\n*S KotlinDebug\n*F\n+ 1 SpawnDetailAdapter.kt\ncom/cobblemon/mod/common/util/adapters/SpawnDetailAdapter\n*L\n38#1:86\n38#1:87,3\n39#1:90,9\n39#1:99\n39#1:101\n39#1:102\n59#1:104,2\n78#1:106,2\n39#1:100\n*E\n"})
public final class SpawnDetailAdapter
implements JsonDeserializer<SpawnDetail> {
    @NotNull
    public static final SpawnDetailAdapter INSTANCE = new SpawnDetailAdapter();

    private SpawnDetailAdapter() {
    }

    /*
     * WARNING - void declaration
     */
    @NotNull
    public SpawnDetail deserialize(@NotNull JsonElement element, @NotNull Type type, @NotNull JsonDeserializationContext ctx) {
        String string;
        Object object;
        RegisteredSpawnDetail<?> registeredSpawnDetail;
        List presets;
        block19: {
            Object object2;
            Iterator iterator;
            block18: {
                Iterator $this$mapNotNullTo$iv$iv;
                Iterable $this$map$iv;
                Object object3;
                block21: {
                    block20: {
                        void $this$mapTo$iv$iv;
                        Intrinsics.checkNotNullParameter((Object)element, (String)"element");
                        Intrinsics.checkNotNullParameter((Object)type, (String)"type");
                        Intrinsics.checkNotNullParameter((Object)ctx, (String)"ctx");
                        JsonObject cfr_ignored_0 = (JsonObject)element;
                        GsonExtensionsKt.singularToPluralList$default((JsonObject)element, "preset", null, 2, null);
                        object3 = ((JsonObject)element).get("presets");
                        if (object3 == null || (object3 = object3.getAsJsonArray()) == null) break block20;
                        $this$map$iv = (Iterable)object3;
                        boolean $i$f$map = false;
                        Iterable iterable = $this$map$iv;
                        Collection destination$iv$iv = new ArrayList(CollectionsKt.collectionSizeOrDefault((Iterable)$this$map$iv, (int)10));
                        boolean $i$f$mapTo = false;
                        for (Object item$iv$iv : $this$mapTo$iv$iv) {
                            void it;
                            JsonElement jsonElement = (JsonElement)item$iv$iv;
                            Collection collection = destination$iv$iv;
                            boolean bl = false;
                            collection.add(it.getAsString());
                        }
                        object3 = CollectionsKt.toMutableSet((Iterable)((List)destination$iv$iv));
                        if (object3 != null) break block21;
                    }
                    object3 = new LinkedHashSet();
                }
                Object presetNames = object3;
                Iterable $this$mapNotNull$iv = (Iterable)presetNames;
                boolean $i$f$mapNotNull = false;
                $this$map$iv = $this$mapNotNull$iv;
                Collection destination$iv$iv = new ArrayList();
                boolean $i$f$mapNotNullTo = false;
                void $this$forEach$iv$iv$iv = $this$mapNotNullTo$iv$iv;
                boolean $i$f$forEach = false;
                Iterator iterator2 = $this$forEach$iv$iv$iv.iterator();
                while (iterator2.hasNext()) {
                    SpawnDetailPreset it$iv$iv;
                    Object element$iv$iv$iv;
                    Object element$iv$iv = element$iv$iv$iv = iterator2.next();
                    boolean bl = false;
                    String it = (String)element$iv$iv;
                    boolean bl2 = false;
                    Map<ResourceLocation, SpawnDetailPreset> map = SpawnDetailPresets.INSTANCE.getPresets();
                    Intrinsics.checkNotNullExpressionValue((Object)it, (String)"it");
                    SpawnDetailPreset preset = map.get(ResourceLocationExtensionsKt.asIdentifierDefaultingNamespace$default(it, null, 1, null));
                    if (preset == null) {
                        Cobblemon.INSTANCE.getLOGGER().error("Unknown preset name: " + it + ".");
                    }
                    if (preset == null) continue;
                    boolean bl3 = false;
                    destination$iv$iv.add(it$iv$iv);
                }
                presets = (List)destination$iv$iv;
                for (Iterator it : (Iterable)presets) {
                    boolean bl = false;
                    if ((it = ((SpawnDetailPreset)((Object)it)).getSpawnDetailType()) == null) continue;
                    iterator = it;
                    break block18;
                }
                iterator = null;
            }
            Iterator firstType = iterator;
            GsonExtensionsKt.singularToPluralList$default((JsonObject)element, "condition", null, 2, null);
            GsonExtensionsKt.singularToPluralList$default((JsonObject)element, "anticondition", null, 2, null);
            GsonExtensionsKt.singularToPluralList$default((JsonObject)element, "weightMultiplier", null, 2, null);
            if (((JsonObject)element).has("weightMultipliers")) {
                JsonArray jsonArray = ((JsonObject)element).get("weightMultipliers").getAsJsonArray();
                Intrinsics.checkNotNullExpressionValue((Object)jsonArray, (String)"element.get(\"weightMultipliers\").asJsonArray");
                Iterable $this$forEach$iv = (Iterable)jsonArray;
                boolean $i$f$forEach = false;
                for (Object element$iv : $this$forEach$iv) {
                    JsonElement json = (JsonElement)element$iv;
                    boolean bl = false;
                    Intrinsics.checkNotNull((Object)json, (String)"null cannot be cast to non-null type com.google.gson.JsonObject");
                    JsonObject cfr_ignored_1 = (JsonObject)json;
                    GsonExtensionsKt.singularToPluralList$default((JsonObject)json, "condition", null, 2, null);
                    GsonExtensionsKt.singularToPluralList$default((JsonObject)json, "anticondition", null, 2, null);
                }
            }
            if ((object2 = firstType) == null) {
                JsonElement jsonElement = ((JsonObject)element).get("type");
                object2 = jsonElement != null ? jsonElement.getAsString() : null;
                if (object2 == null) {
                    throw new IllegalStateException("Spawn detail type name not mentioned in either presets or in spawn detail.");
                }
            }
            Iterator spawnDetailTypeName = object2;
            RegisteredSpawnDetail<?> registeredSpawnDetail2 = SpawnDetail.Companion.getSpawnDetailTypes().get(spawnDetailTypeName);
            if (registeredSpawnDetail2 == null) {
                throw new IllegalStateException("Unrecognized spawn detail type name: " + spawnDetailTypeName + ".");
            }
            registeredSpawnDetail = registeredSpawnDetail2;
            for (Object it : (Iterable)presets) {
                boolean bl = false;
                RegisteredSpawningContext<?> registeredSpawningContext = ((SpawnDetailPreset)it).getContext();
                it = registeredSpawningContext != null ? registeredSpawningContext.getName() : null;
                if (it == null) continue;
                object = it;
                break block19;
            }
            object = string = null;
        }
        if (object == null) {
            string = ((JsonObject)element).get("context").getAsString();
        }
        String ctxName = string;
        Intrinsics.checkNotNullExpressionValue(ctxName, (String)"ctxName");
        RegisteredSpawningContext<?> registeredSpawningContext = SpawningContext.Companion.getByName(ctxName);
        if (registeredSpawningContext == null) {
            throw new IllegalStateException("Unrecognized context name: " + ctxName);
        }
        RegisteredSpawningContext<?> ctxType = registeredSpawningContext;
        Class<? extends SpawningCondition<?>> clazz = SpawningCondition.Companion.getByName(ctxType.getDefaultCondition());
        if (clazz == null) {
            throw new IllegalStateException("There is no spawning condition registered with the name '" + ctxType.getDefaultCondition() + "'");
        }
        SpawnLoader.INSTANCE.setDeserializingConditionClass(clazz);
        Object detail = registeredSpawnDetail.deserializeDetail(element, ctx);
        Iterable $this$forEach$iv = presets;
        boolean $i$f$forEach = false;
        for (Object element$iv : $this$forEach$iv) {
            SpawnDetailPreset it = (SpawnDetailPreset)element$iv;
            boolean bl = false;
            it.apply((SpawnDetail)detail);
        }
        if (StringsKt.isBlank((CharSequence)((SpawnDetail)detail).getBucket().getName())) {
            throw new IllegalStateException("No bucket was specified for spawn: " + ((SpawnDetail)detail).getId());
        }
        ((SpawnDetail)detail).autoLabel();
        return detail;
    }
}

