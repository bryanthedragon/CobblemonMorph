/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.gson.Gson
 *  com.google.gson.GsonBuilder
 *  com.google.gson.reflect.TypeToken
 *  kotlin.Metadata
 *  kotlin.collections.CollectionsKt
 *  kotlin.jvm.internal.Intrinsics
 *  kotlin.jvm.internal.SourceDebugExtension
 *  kotlin.jvm.internal.TypeIntrinsics
 *  net.minecraft.core.DefaultedRegistry
 *  net.minecraft.core.Registry
 *  net.minecraft.core.registries.BuiltInRegistries
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.server.level.ServerPlayer
 *  net.minecraft.server.packs.PackType
 *  net.minecraft.server.packs.resources.ResourceManager
 *  net.minecraft.world.item.Item
 *  net.minecraft.world.item.ItemStack
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.fossil;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.data.JsonDataRegistry;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.fossil.NaturalMaterial;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.reactive.SimpleObservable;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.fossil.NaturalMaterialRegistrySyncPacket;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.registry.ItemTagCondition;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.MiscUtilsKt;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.adapters.IdentifierAdapter;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.adapters.ItemLikeConditionAdapter;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import kotlin.jvm.internal.TypeIntrinsics;
import net.minecraft.core.DefaultedRegistry;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.packs.PackType;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000z\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010$\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0010%\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u000e\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0007\b\u00c6\u0002\u0018\u00002\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00030\u00020\u0001B\t\b\u0002\u00a2\u0006\u0004\b:\u0010;J\u0017\u0010\u0007\u001a\u0004\u0018\u00010\u00062\u0006\u0010\u0005\u001a\u00020\u0004\u00a2\u0006\u0004\b\u0007\u0010\bJ\u0017\u0010\n\u001a\u0004\u0018\u00010\t2\u0006\u0010\u0005\u001a\u00020\u0004\u00a2\u0006\u0004\b\n\u0010\u000bJ\u0015\u0010\r\u001a\u00020\f2\u0006\u0010\u0005\u001a\u00020\u0004\u00a2\u0006\u0004\b\r\u0010\u000eJ)\u0010\u0012\u001a\u00020\u00112\u0018\u0010\u0010\u001a\u0014\u0012\u0004\u0012\u00020\t\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00030\u00020\u000fH\u0016\u00a2\u0006\u0004\b\u0012\u0010\u0013J\u0017\u0010\u0016\u001a\u00020\u00112\u0006\u0010\u0015\u001a\u00020\u0014H\u0016\u00a2\u0006\u0004\b\u0016\u0010\u0017R\u001a\u0010\u0019\u001a\u00020\u00188\u0016X\u0096\u0004\u00a2\u0006\f\n\u0004\b\u0019\u0010\u001a\u001a\u0004\b\u001b\u0010\u001cR\u001a\u0010\u001d\u001a\u00020\t8\u0016X\u0096\u0004\u00a2\u0006\f\n\u0004\b\u001d\u0010\u001e\u001a\u0004\b\u001f\u0010 R \u0010\"\u001a\u000e\u0012\u0004\u0012\u00020\t\u0012\u0004\u0012\u00020\u00030!8\u0002X\u0082\u0004\u00a2\u0006\u0006\n\u0004\b\"\u0010#R \u0010%\u001a\b\u0012\u0004\u0012\u00020\u00000$8\u0016X\u0096\u0004\u00a2\u0006\f\n\u0004\b%\u0010&\u001a\u0004\b'\u0010(R\u001a\u0010*\u001a\u00020)8\u0016X\u0096D\u00a2\u0006\f\n\u0004\b*\u0010+\u001a\u0004\b,\u0010-R \u0010/\u001a\u000e\u0012\u0004\u0012\u00020.\u0012\u0004\u0012\u00020\u00030!8\u0002X\u0082\u0004\u00a2\u0006\u0006\n\u0004\b/\u0010#R\u001a\u00101\u001a\u0002008\u0016X\u0096\u0004\u00a2\u0006\f\n\u0004\b1\u00102\u001a\u0004\b3\u00104R&\u00106\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00030\u0002058\u0016X\u0096\u0004\u00a2\u0006\f\n\u0004\b6\u00107\u001a\u0004\b8\u00109\u00a8\u0006<"}, d2={"Lcom/cobblemon/mod/common/api/fossil/NaturalMaterials;", "Lcom/cobblemon/mod/common/api/data/JsonDataRegistry;", "", "Lcom/cobblemon/mod/common/api/fossil/NaturalMaterial;", "Lnet/minecraft/world/item/ItemStack;", "item", "", "getContent", "(Lnet/minecraft/world/item/ItemStack;)Ljava/lang/Integer;", "Lnet/minecraft/resources/ResourceLocation;", "getReturnItem", "(Lnet/minecraft/world/item/ItemStack;)Lnet/minecraft/resources/ResourceLocation;", "", "isNaturalMaterial", "(Lnet/minecraft/world/item/ItemStack;)Z", "", "data", "", "reload", "(Ljava/util/Map;)V", "Lnet/minecraft/server/level/ServerPlayer;", "player", "sync", "(Lnet/minecraft/server/level/ServerPlayer;)V", "Lcom/google/gson/Gson;", "gson", "Lcom/google/gson/Gson;", "getGson", "()Lcom/google/gson/Gson;", "id", "Lnet/minecraft/resources/ResourceLocation;", "getId", "()Lnet/minecraft/resources/ResourceLocation;", "", "itemMap", "Ljava/util/Map;", "Lcom/cobblemon/mod/common/api/reactive/SimpleObservable;", "observable", "Lcom/cobblemon/mod/common/api/reactive/SimpleObservable;", "getObservable", "()Lcom/cobblemon/mod/common/api/reactive/SimpleObservable;", "", "resourcePath", "Ljava/lang/String;", "getResourcePath", "()Ljava/lang/String;", "Lcom/cobblemon/mod/common/registry/ItemTagCondition;", "tagMap", "Lnet/minecraft/server/packs/PackType;", "type", "Lnet/minecraft/server/packs/PackType;", "getType", "()Lnet/minecraft/server/packs/PackType;", "Lcom/google/gson/reflect/TypeToken;", "typeToken", "Lcom/google/gson/reflect/TypeToken;", "getTypeToken", "()Lcom/google/gson/reflect/TypeToken;", "<init>", "()V", "common"})
@SourceDebugExtension(value={"SMAP\nNaturalMaterials.kt\nKotlin\n*S Kotlin\n*F\n+ 1 NaturalMaterials.kt\ncom/cobblemon/mod/common/api/fossil/NaturalMaterials\n+ 2 _Maps.kt\nkotlin/collections/MapsKt___MapsKt\n+ 3 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n*L\n1#1,91:1\n215#2:92\n216#2:95\n1855#3,2:93\n1747#3,3:96\n288#3,2:99\n288#3,2:101\n*S KotlinDebug\n*F\n+ 1 NaturalMaterials.kt\ncom/cobblemon/mod/common/api/fossil/NaturalMaterials\n*L\n47#1:92\n47#1:95\n48#1:93,2\n62#1:96,3\n72#1:99,2\n84#1:101,2\n*E\n"})
public final class NaturalMaterials
implements JsonDataRegistry<List<? extends NaturalMaterial>> {
    @NotNull
    public static final NaturalMaterials INSTANCE = new NaturalMaterials();
    @NotNull
    private static final ResourceLocation id = MiscUtilsKt.cobblemonResource("natural_materials");
    @NotNull
    private static final PackType type = PackType.SERVER_DATA;
    @NotNull
    private static final SimpleObservable<NaturalMaterials> observable = new SimpleObservable();
    @NotNull
    private static final TypeToken<List<NaturalMaterial>> typeToken;
    @NotNull
    private static final String resourcePath;
    @NotNull
    private static final Gson gson;
    @NotNull
    private static final Map<ResourceLocation, NaturalMaterial> itemMap;
    @NotNull
    private static final Map<ItemTagCondition, NaturalMaterial> tagMap;

    private NaturalMaterials() {
    }

    @Override
    @NotNull
    public ResourceLocation getId() {
        return id;
    }

    @Override
    @NotNull
    public PackType getType() {
        return type;
    }

    @NotNull
    public SimpleObservable<NaturalMaterials> getObservable() {
        return observable;
    }

    @Override
    @NotNull
    public TypeToken<List<NaturalMaterial>> getTypeToken() {
        return typeToken;
    }

    @Override
    @NotNull
    public String getResourcePath() {
        return resourcePath;
    }

    @Override
    @NotNull
    public Gson getGson() {
        return gson;
    }

    @Override
    public void sync(@NotNull ServerPlayer player) {
        Intrinsics.checkNotNullParameter((Object)player, (String)"player");
        new NaturalMaterialRegistrySyncPacket(CollectionsKt.plus((Collection)CollectionsKt.toList((Iterable)itemMap.values()), (Iterable)CollectionsKt.toList((Iterable)tagMap.values()))).sendToPlayer(player);
    }

    @Override
    public void reload(@NotNull Map<ResourceLocation, ? extends List<NaturalMaterial>> data) {
        Intrinsics.checkNotNullParameter(data, (String)"data");
        Map<ResourceLocation, ? extends List<NaturalMaterial>> $this$forEach$iv = data;
        boolean $i$f$forEach = false;
        Iterator<Map.Entry<ResourceLocation, ? extends List<NaturalMaterial>>> iterator = $this$forEach$iv.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<ResourceLocation, ? extends List<NaturalMaterial>> element$iv;
            Map.Entry<ResourceLocation, ? extends List<NaturalMaterial>> entry = element$iv = iterator.next();
            boolean bl = false;
            Iterable $this$forEach$iv2 = entry.getValue();
            boolean $i$f$forEach2 = false;
            for (Object element$iv2 : $this$forEach$iv2) {
                NaturalMaterial it = (NaturalMaterial)element$iv2;
                boolean bl2 = false;
                Map<ResourceLocation, NaturalMaterial> map = itemMap;
                ResourceLocation resourceLocation = it.getItem();
                TypeIntrinsics.asMutableMap(map).remove(resourceLocation);
                if (it.getItem() != null) {
                    itemMap.put(it.getItem(), it);
                }
                if (it.getTag() == null) continue;
                tagMap.put(it.getTag(), it);
            }
        }
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public final boolean isNaturalMaterial(@NotNull ItemStack item) {
        DefaultedRegistry defaultedRegistry;
        Item item2;
        ItemTagCondition it;
        Intrinsics.checkNotNullParameter((Object)item, (String)"item");
        ResourceLocation resourceLocation = BuiltInRegistries.f_257033_.m_7981_((Object)item.m_41720_());
        Intrinsics.checkNotNullExpressionValue((Object)resourceLocation, (String)"ITEM.getId(item.item)");
        ResourceLocation itemId = resourceLocation;
        if (itemMap.keySet().contains(itemId)) return true;
        Iterable $this$any$iv = tagMap.keySet();
        boolean $i$f$any = false;
        if ($this$any$iv instanceof Collection && ((Collection)$this$any$iv).isEmpty()) {
            return false;
        }
        Iterator iterator = $this$any$iv.iterator();
        do {
            if (!iterator.hasNext()) return false;
            Object element$iv = iterator.next();
            it = (ItemTagCondition)element$iv;
            boolean bl = false;
            item2 = item.m_41720_();
            Intrinsics.checkNotNullExpressionValue((Object)item2, (String)"item.item");
            defaultedRegistry = BuiltInRegistries.f_257033_;
            Intrinsics.checkNotNullExpressionValue((Object)defaultedRegistry, (String)"ITEM");
        } while (!it.fits(item2, (Registry)defaultedRegistry));
        return true;
    }

    @Nullable
    public final Integer getContent(@NotNull ItemStack item) {
        Object v4;
        block3: {
            Intrinsics.checkNotNullParameter((Object)item, (String)"item");
            ResourceLocation resourceLocation = BuiltInRegistries.f_257033_.m_7981_((Object)item.m_41720_());
            Intrinsics.checkNotNullExpressionValue((Object)resourceLocation, (String)"ITEM.getId(item.item)");
            ResourceLocation itemId = resourceLocation;
            if (itemMap.keySet().contains(itemId)) {
                NaturalMaterial naturalMaterial = itemMap.get(itemId);
                return naturalMaterial != null ? Integer.valueOf(naturalMaterial.getContent()) : null;
            }
            Iterable $this$firstOrNull$iv = tagMap.keySet();
            boolean $i$f$firstOrNull = false;
            for (Object element$iv : $this$firstOrNull$iv) {
                ItemTagCondition it = (ItemTagCondition)element$iv;
                boolean bl = false;
                Item item2 = item.m_41720_();
                Intrinsics.checkNotNullExpressionValue((Object)item2, (String)"item.item");
                DefaultedRegistry defaultedRegistry = BuiltInRegistries.f_257033_;
                Intrinsics.checkNotNullExpressionValue((Object)defaultedRegistry, (String)"ITEM");
                if (!it.fits(item2, (Registry)defaultedRegistry)) continue;
                v4 = element$iv;
                break block3;
            }
            v4 = null;
        }
        ItemTagCondition tag = v4;
        if (tag != null) {
            NaturalMaterial naturalMaterial = tagMap.get(tag);
            return naturalMaterial != null ? Integer.valueOf(naturalMaterial.getContent()) : null;
        }
        return null;
    }

    @Nullable
    public final ResourceLocation getReturnItem(@NotNull ItemStack item) {
        Object v4;
        block3: {
            Intrinsics.checkNotNullParameter((Object)item, (String)"item");
            ResourceLocation resourceLocation = BuiltInRegistries.f_257033_.m_7981_((Object)item.m_41720_());
            Intrinsics.checkNotNullExpressionValue((Object)resourceLocation, (String)"ITEM.getId(item.item)");
            ResourceLocation itemId = resourceLocation;
            if (itemMap.keySet().contains(itemId)) {
                NaturalMaterial naturalMaterial = itemMap.get(itemId);
                return naturalMaterial != null ? naturalMaterial.getReturnItem() : null;
            }
            Iterable $this$firstOrNull$iv = tagMap.keySet();
            boolean $i$f$firstOrNull = false;
            for (Object element$iv : $this$firstOrNull$iv) {
                ItemTagCondition it = (ItemTagCondition)element$iv;
                boolean bl = false;
                Item item2 = item.m_41720_();
                Intrinsics.checkNotNullExpressionValue((Object)item2, (String)"item.item");
                DefaultedRegistry defaultedRegistry = BuiltInRegistries.f_257033_;
                Intrinsics.checkNotNullExpressionValue((Object)defaultedRegistry, (String)"ITEM");
                if (!it.fits(item2, (Registry)defaultedRegistry)) continue;
                v4 = element$iv;
                break block3;
            }
            v4 = null;
        }
        ItemTagCondition tag = v4;
        if (tag != null) {
            NaturalMaterial naturalMaterial = tagMap.get(tag);
            return naturalMaterial != null ? naturalMaterial.getReturnItem() : null;
        }
        return null;
    }

    @Override
    public void reload(@NotNull ResourceManager manager) {
        JsonDataRegistry.DefaultImpls.reload(this, manager);
    }

    static {
        Type[] typeArray = new Type[]{NaturalMaterial.class};
        TypeToken typeToken = TypeToken.getParameterized((Type)((Type)((Object)List.class)), (Type[])typeArray);
        Intrinsics.checkNotNull((Object)typeToken, (String)"null cannot be cast to non-null type com.google.gson.reflect.TypeToken<kotlin.collections.List<bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.fossil.NaturalMaterial>>");
        NaturalMaterials.typeToken = typeToken;
        resourcePath = "natural_materials";
        Gson gson2 = new GsonBuilder().setPrettyPrinting().registerTypeAdapter((Type)((Object)ResourceLocation.class), (Object)IdentifierAdapter.INSTANCE).registerTypeAdapter((Type)((Object)ItemTagCondition.class), (Object)ItemLikeConditionAdapter.INSTANCE).create();
        Intrinsics.checkNotNullExpressionValue((Object)gson2, (String)"GsonBuilder()\n        .s\u2026dapter)\n        .create()");
        gson = gson2;
        itemMap = new LinkedHashMap();
        tagMap = new LinkedHashMap();
    }
}

