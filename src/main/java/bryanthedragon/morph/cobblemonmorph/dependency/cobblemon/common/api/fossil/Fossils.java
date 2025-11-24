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

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.Cobblemon;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.conditional.RegistryLikeCondition;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.data.JsonDataRegistry;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.fossil.Fossil;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.PokemonProperties;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.reactive.SimpleObservable;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.fossil.FossilRegistrySyncPacket;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.evolution.adapters.NbtItemPredicateAdapter;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.evolution.predicate.NbtItemPredicate;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.MiscUtilsKt;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.adapters.IdentifierAdapter;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.adapters.ItemLikeConditionAdapter;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.adapters.PokemonPropertiesAdapterKt;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.packs.PackType;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000v\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010 \n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010$\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\t\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u000e\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0007\b\u00c6\u0002\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001B\t\b\u0002\u00a2\u0006\u0004\b>\u0010?J\u0013\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00020\u0003\u00a2\u0006\u0004\b\u0004\u0010\u0005J\u0017\u0010\b\u001a\u0004\u0018\u00010\u00022\u0006\u0010\u0007\u001a\u00020\u0006\u00a2\u0006\u0004\b\b\u0010\tJ\u001d\u0010\f\u001a\u0004\u0018\u00010\u00022\f\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\n0\u0003\u00a2\u0006\u0004\b\f\u0010\rJ\u001d\u0010\u000e\u001a\u0004\u0018\u00010\u00022\f\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\n0\u0003\u00a2\u0006\u0004\b\u000e\u0010\rJ\u0015\u0010\u0011\u001a\u00020\u00102\u0006\u0010\u000f\u001a\u00020\n\u00a2\u0006\u0004\b\u0011\u0010\u0012J#\u0010\u0016\u001a\u00020\u00152\u0012\u0010\u0014\u001a\u000e\u0012\u0004\u0012\u00020\u0006\u0012\u0004\u0012\u00020\u00020\u0013H\u0016\u00a2\u0006\u0004\b\u0016\u0010\u0017J\u0017\u0010\u001a\u001a\u00020\u00152\u0006\u0010\u0019\u001a\u00020\u0018H\u0016\u00a2\u0006\u0004\b\u001a\u0010\u001bR0\u0010\u001e\u001a\u001e\u0012\u0004\u0012\u00020\u0006\u0012\u0004\u0012\u00020\u00020\u001cj\u000e\u0012\u0004\u0012\u00020\u0006\u0012\u0004\u0012\u00020\u0002`\u001d8\u0002X\u0082\u0004\u00a2\u0006\u0006\n\u0004\b\u001e\u0010\u001fR\"\u0010\"\u001a\n !*\u0004\u0018\u00010 0 8\u0016X\u0096\u0004\u00a2\u0006\f\n\u0004\b\"\u0010#\u001a\u0004\b$\u0010%R\u001a\u0010&\u001a\u00020\u00068\u0016X\u0096\u0004\u00a2\u0006\f\n\u0004\b&\u0010'\u001a\u0004\b(\u0010)R \u0010+\u001a\b\u0012\u0004\u0012\u00020\u00000*8\u0016X\u0096\u0004\u00a2\u0006\f\n\u0004\b+\u0010,\u001a\u0004\b-\u0010.R\u001a\u00100\u001a\u00020/8\u0016X\u0096D\u00a2\u0006\f\n\u0004\b0\u00101\u001a\u0004\b2\u00103R\u001a\u00105\u001a\u0002048\u0016X\u0096\u0004\u00a2\u0006\f\n\u0004\b5\u00106\u001a\u0004\b7\u00108R \u0010:\u001a\b\u0012\u0004\u0012\u00020\u0002098\u0016X\u0096\u0004\u00a2\u0006\f\n\u0004\b:\u0010;\u001a\u0004\b<\u0010=\u00a8\u0006@"}, d2={"Lcom/cobblemon/mod/common/api/fossil/Fossils;", "Lcom/cobblemon/mod/common/api/data/JsonDataRegistry;", "Lcom/cobblemon/mod/common/api/fossil/Fossil;", "", "all", "()Ljava/util/List;", "Lnet/minecraft/resources/ResourceLocation;", "identifier", "getByIdentifier", "(Lnet/minecraft/resources/ResourceLocation;)Lcom/cobblemon/mod/common/api/fossil/Fossil;", "Lnet/minecraft/world/item/ItemStack;", "fossilStacks", "getFossilByItemStacks", "(Ljava/util/List;)Lcom/cobblemon/mod/common/api/fossil/Fossil;", "getSubFossilByItemStacks", "itemStack", "", "isFossilIngredient", "(Lnet/minecraft/world/item/ItemStack;)Z", "", "data", "", "reload", "(Ljava/util/Map;)V", "Lnet/minecraft/server/level/ServerPlayer;", "player", "sync", "(Lnet/minecraft/server/level/ServerPlayer;)V", "Ljava/util/HashMap;", "Lkotlin/collections/HashMap;", "fossils", "Ljava/util/HashMap;", "Lcom/google/gson/Gson;", "kotlin.jvm.PlatformType", "gson", "Lcom/google/gson/Gson;", "getGson", "()Lcom/google/gson/Gson;", "id", "Lnet/minecraft/resources/ResourceLocation;", "getId", "()Lnet/minecraft/resources/ResourceLocation;", "Lcom/cobblemon/mod/common/api/reactive/SimpleObservable;", "observable", "Lcom/cobblemon/mod/common/api/reactive/SimpleObservable;", "getObservable", "()Lcom/cobblemon/mod/common/api/reactive/SimpleObservable;", "", "resourcePath", "Ljava/lang/String;", "getResourcePath", "()Ljava/lang/String;", "Lnet/minecraft/server/packs/PackType;", "type", "Lnet/minecraft/server/packs/PackType;", "getType", "()Lnet/minecraft/server/packs/PackType;", "Lcom/google/gson/reflect/TypeToken;", "typeToken", "Lcom/google/gson/reflect/TypeToken;", "getTypeToken", "()Lcom/google/gson/reflect/TypeToken;", "<init>", "()V", "common"})
@SourceDebugExtension(value={"SMAP\nFossils.kt\nKotlin\n*S Kotlin\n*F\n+ 1 Fossils.kt\ncom/cobblemon/mod/common/api/fossil/Fossils\n+ 2 _Maps.kt\nkotlin/collections/MapsKt___MapsKt\n+ 3 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n*L\n1#1,107:1\n215#2,2:108\n288#3,2:110\n288#3,2:112\n1747#3,3:114\n*S KotlinDebug\n*F\n+ 1 Fossils.kt\ncom/cobblemon/mod/common/api/fossil/Fossils\n*L\n53#1:108,2\n87#1:110,2\n96#1:112,2\n104#1:114,3\n*E\n"})
public final class Fossils
implements JsonDataRegistry<Fossil> {
    @NotNull
    public static final Fossils INSTANCE = new Fossils();
    @NotNull
    private static final ResourceLocation id = MiscUtilsKt.cobblemonResource("fossils");
    @NotNull
    private static final PackType type = PackType.SERVER_DATA;
    @NotNull
    private static final SimpleObservable<Fossils> observable = new SimpleObservable();
    private static final Gson gson;
    @NotNull
    private static final TypeToken<Fossil> typeToken;
    @NotNull
    private static final String resourcePath;
    @NotNull
    private static final HashMap<ResourceLocation, Fossil> fossils;

    private Fossils() {
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
    public SimpleObservable<Fossils> getObservable() {
        return observable;
    }

    @Override
    public Gson getGson() {
        return gson;
    }

    @Override
    @NotNull
    public TypeToken<Fossil> getTypeToken() {
        return typeToken;
    }

    @Override
    @NotNull
    public String getResourcePath() {
        return resourcePath;
    }

    @Override
    public void reload(@NotNull Map<ResourceLocation, Fossil> data) {
        Intrinsics.checkNotNullParameter(data, (String)"data");
        fossils.clear();
        Map<ResourceLocation, Fossil> $this$forEach$iv = data;
        boolean $i$f$forEach = false;
        Iterator<Map.Entry<ResourceLocation, Fossil>> iterator = $this$forEach$iv.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<ResourceLocation, Fossil> element$iv;
            Map.Entry<ResourceLocation, Fossil> entry = element$iv = iterator.next();
            boolean bl = false;
            ResourceLocation identifier = entry.getKey();
            Fossil fossil = entry.getValue();
            try {
                fossil.setIdentifier$common(identifier);
                ((Map)fossils).put(identifier, fossil);
            }
            catch (Exception e) {
                Cobblemon.INSTANCE.getLOGGER().error("Skipped loading the {} fossil", (Object)identifier, (Object)e);
            }
        }
        Cobblemon.INSTANCE.getLOGGER().info("Loaded {} fossils", (Object)fossils.size());
        Fossils[] fossilsArray = new Fossils[]{this};
        this.getObservable().emit((Fossils[])fossilsArray);
    }

    @Override
    public void sync(@NotNull ServerPlayer player) {
        Intrinsics.checkNotNullParameter((Object)player, (String)"player");
        new FossilRegistrySyncPacket(this.all()).sendToPlayer(player);
    }

    @NotNull
    public final List<Fossil> all() {
        Collection<Fossil> collection = fossils.values();
        Intrinsics.checkNotNullExpressionValue(collection, (String)"fossils.values");
        return CollectionsKt.toList((Iterable)collection);
    }

    @Nullable
    public final Fossil getByIdentifier(@NotNull ResourceLocation identifier) {
        Intrinsics.checkNotNullParameter((Object)identifier, (String)"identifier");
        return fossils.get(identifier);
    }

    @Nullable
    public final Fossil getFossilByItemStacks(@NotNull List<ItemStack> fossilStacks) {
        Object v0;
        block1: {
            Intrinsics.checkNotNullParameter(fossilStacks, (String)"fossilStacks");
            Iterable $this$firstOrNull$iv = this.all();
            boolean $i$f$firstOrNull = false;
            for (Object element$iv : $this$firstOrNull$iv) {
                Fossil it = (Fossil)element$iv;
                boolean bl = false;
                if (!it.matchesIngredients(fossilStacks)) continue;
                v0 = element$iv;
                break block1;
            }
            v0 = null;
        }
        return v0;
    }

    @Nullable
    public final Fossil getSubFossilByItemStacks(@NotNull List<ItemStack> fossilStacks) {
        Object v0;
        block1: {
            Intrinsics.checkNotNullParameter(fossilStacks, (String)"fossilStacks");
            Iterable $this$firstOrNull$iv = this.all();
            boolean $i$f$firstOrNull = false;
            for (Object element$iv : $this$firstOrNull$iv) {
                Fossil it = (Fossil)element$iv;
                boolean bl = false;
                if (!it.matchesIngredientsSubSet(fossilStacks)) continue;
                v0 = element$iv;
                break block1;
            }
            v0 = null;
        }
        return v0;
    }

    public final boolean isFossilIngredient(@NotNull ItemStack itemStack) {
        boolean bl;
        block3: {
            Intrinsics.checkNotNullParameter((Object)itemStack, (String)"itemStack");
            Iterable $this$any$iv = this.all();
            boolean $i$f$any = false;
            if ($this$any$iv instanceof Collection && ((Collection)$this$any$iv).isEmpty()) {
                bl = false;
            } else {
                for (Object element$iv : $this$any$iv) {
                    Fossil it = (Fossil)element$iv;
                    boolean bl2 = false;
                    if (!it.isIngredient(itemStack)) continue;
                    bl = true;
                    break block3;
                }
                bl = false;
            }
        }
        return bl;
    }

    @Override
    public void reload(@NotNull ResourceManager manager) {
        JsonDataRegistry.DefaultImpls.reload(this, manager);
    }

    static {
        Type[] typeArray = new Type[]{Item.class};
        gson = new GsonBuilder().disableHtmlEscaping().setPrettyPrinting().registerTypeAdapter((Type)((Object)ResourceLocation.class), (Object)IdentifierAdapter.INSTANCE).registerTypeAdapter((Type)((Object)PokemonProperties.class), (Object)PokemonPropertiesAdapterKt.getPokemonPropertiesShortAdapter()).registerTypeAdapter(TypeToken.getParameterized((Type)((Type)((Object)RegistryLikeCondition.class)), (Type[])typeArray).getType(), (Object)ItemLikeConditionAdapter.INSTANCE).registerTypeAdapter((Type)((Object)NbtItemPredicate.class), (Object)NbtItemPredicateAdapter.INSTANCE).create();
        TypeToken typeToken = TypeToken.get(Fossil.class);
        Intrinsics.checkNotNullExpressionValue((Object)typeToken, (String)"get(Fossil::class.java)");
        Fossils.typeToken = typeToken;
        resourcePath = "fossils";
        fossils = new HashMap();
    }
}

