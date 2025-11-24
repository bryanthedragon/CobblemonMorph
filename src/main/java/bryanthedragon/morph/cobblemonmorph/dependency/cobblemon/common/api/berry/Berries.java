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
 *  kotlin.ranges.IntRange
 *  net.minecraft.advancements.critereon.MinMaxBounds$Doubles
 *  net.minecraft.core.registries.Registries
 *  net.minecraft.resources.ResourceKey
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.server.level.ServerPlayer
 *  net.minecraft.server.packs.PackType
 *  net.minecraft.server.packs.resources.ResourceManager
 *  net.minecraft.tags.TagKey
 *  net.minecraft.world.level.biome.Biome
 *  net.minecraft.world.phys.AABB
 *  net.minecraft.world.phys.Vec3
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.berry;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.Cobblemon;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.berry.Berry;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.berry.GrowthFactor;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.berry.spawncondition.BerrySpawnCondition;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.data.JsonDataRegistry;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.mulch.MulchVariant;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.stats.Stat;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.status.Status;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.reactive.SimpleObservable;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.data.BerryRegistrySyncPacket;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.adapters.CobblemonStatTypeAdapter;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.MiscUtilsKt;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.adapters.BoxAdapter;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.adapters.BoxCollectionAdapter;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.adapters.CobblemonBerrySpawnConditionAdapter;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.adapters.CobblemonGrowthFactorAdapter;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.adapters.FloatNumberRangeAdapter;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.adapters.IdentifierAdapter;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.adapters.LiteralHexColorAdapter;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.adapters.MulchVariantAdapter;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.adapters.StatusAdapter;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.adapters.TagKeyAdapter;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.adapters.VerboseIntRangeAdapter;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.adapters.VerboseVec3dAdapter;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import java.awt.Color;
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
import kotlin.ranges.IntRange;
import net.minecraft.advancements.critereon.MinMaxBounds;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.packs.PackType;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010 \n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0010$\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\t\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0007\b\u00c6\u0002\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001B\t\b\u0002\u00a2\u0006\u0004\b8\u00109J\u0013\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00020\u0003\u00a2\u0006\u0004\b\u0004\u0010\u0005J\u0017\u0010\b\u001a\u0004\u0018\u00010\u00022\u0006\u0010\u0007\u001a\u00020\u0006\u00a2\u0006\u0004\b\b\u0010\tJ\u0017\u0010\f\u001a\u0004\u0018\u00010\u00022\u0006\u0010\u000b\u001a\u00020\n\u00a2\u0006\u0004\b\f\u0010\rJ#\u0010\u0011\u001a\u00020\u00102\u0012\u0010\u000f\u001a\u000e\u0012\u0004\u0012\u00020\u0006\u0012\u0004\u0012\u00020\u00020\u000eH\u0016\u00a2\u0006\u0004\b\u0011\u0010\u0012J\u0017\u0010\u0015\u001a\u00020\u00102\u0006\u0010\u0014\u001a\u00020\u0013H\u0016\u00a2\u0006\u0004\b\u0015\u0010\u0016R0\u0010\u0019\u001a\u001e\u0012\u0004\u0012\u00020\u0006\u0012\u0004\u0012\u00020\u00020\u0017j\u000e\u0012\u0004\u0012\u00020\u0006\u0012\u0004\u0012\u00020\u0002`\u00188\u0002X\u0082\u0004\u00a2\u0006\u0006\n\u0004\b\u0019\u0010\u001aR\"\u0010\u001d\u001a\n \u001c*\u0004\u0018\u00010\u001b0\u001b8\u0016X\u0096\u0004\u00a2\u0006\f\n\u0004\b\u001d\u0010\u001e\u001a\u0004\b\u001f\u0010 R\u001a\u0010!\u001a\u00020\u00068\u0016X\u0096\u0004\u00a2\u0006\f\n\u0004\b!\u0010\"\u001a\u0004\b#\u0010$R \u0010&\u001a\b\u0012\u0004\u0012\u00020\u00000%8\u0016X\u0096\u0004\u00a2\u0006\f\n\u0004\b&\u0010'\u001a\u0004\b(\u0010)R\u001a\u0010*\u001a\u00020\n8\u0016X\u0096D\u00a2\u0006\f\n\u0004\b*\u0010+\u001a\u0004\b,\u0010-R\u001a\u0010/\u001a\u00020.8\u0016X\u0096\u0004\u00a2\u0006\f\n\u0004\b/\u00100\u001a\u0004\b1\u00102R \u00104\u001a\b\u0012\u0004\u0012\u00020\u0002038\u0016X\u0096\u0004\u00a2\u0006\f\n\u0004\b4\u00105\u001a\u0004\b6\u00107\u00a8\u0006:"}, d2={"Lcom/cobblemon/mod/common/api/berry/Berries;", "Lcom/cobblemon/mod/common/api/data/JsonDataRegistry;", "Lcom/cobblemon/mod/common/api/berry/Berry;", "", "all", "()Ljava/util/List;", "Lnet/minecraft/resources/ResourceLocation;", "identifier", "getByIdentifier", "(Lnet/minecraft/resources/ResourceLocation;)Lcom/cobblemon/mod/common/api/berry/Berry;", "", "name", "getByName", "(Ljava/lang/String;)Lcom/cobblemon/mod/common/api/berry/Berry;", "", "data", "", "reload", "(Ljava/util/Map;)V", "Lnet/minecraft/server/level/ServerPlayer;", "player", "sync", "(Lnet/minecraft/server/level/ServerPlayer;)V", "Ljava/util/HashMap;", "Lkotlin/collections/HashMap;", "berries", "Ljava/util/HashMap;", "Lcom/google/gson/Gson;", "kotlin.jvm.PlatformType", "gson", "Lcom/google/gson/Gson;", "getGson", "()Lcom/google/gson/Gson;", "id", "Lnet/minecraft/resources/ResourceLocation;", "getId", "()Lnet/minecraft/resources/ResourceLocation;", "Lcom/cobblemon/mod/common/api/reactive/SimpleObservable;", "observable", "Lcom/cobblemon/mod/common/api/reactive/SimpleObservable;", "getObservable", "()Lcom/cobblemon/mod/common/api/reactive/SimpleObservable;", "resourcePath", "Ljava/lang/String;", "getResourcePath", "()Ljava/lang/String;", "Lnet/minecraft/server/packs/PackType;", "type", "Lnet/minecraft/server/packs/PackType;", "getType", "()Lnet/minecraft/server/packs/PackType;", "Lcom/google/gson/reflect/TypeToken;", "typeToken", "Lcom/google/gson/reflect/TypeToken;", "getTypeToken", "()Lcom/google/gson/reflect/TypeToken;", "<init>", "()V", "common"})
@SourceDebugExtension(value={"SMAP\nBerries.kt\nKotlin\n*S Kotlin\n*F\n+ 1 Berries.kt\ncom/cobblemon/mod/common/api/berry/Berries\n+ 2 _Maps.kt\nkotlin/collections/MapsKt___MapsKt\n*L\n1#1,107:1\n215#2,2:108\n*S KotlinDebug\n*F\n+ 1 Berries.kt\ncom/cobblemon/mod/common/api/berry/Berries\n*L\n71#1:108,2\n*E\n"})
public final class Berries
implements JsonDataRegistry<Berry> {
    @NotNull
    public static final Berries INSTANCE = new Berries();
    @NotNull
    private static final ResourceLocation id = MiscUtilsKt.cobblemonResource("berries");
    @NotNull
    private static final PackType type = PackType.SERVER_DATA;
    @NotNull
    private static final SimpleObservable<Berries> observable = new SimpleObservable();
    private static final Gson gson;
    @NotNull
    private static final TypeToken<Berry> typeToken;
    @NotNull
    private static final String resourcePath;
    @NotNull
    private static final HashMap<ResourceLocation, Berry> berries;

    private Berries() {
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
    public SimpleObservable<Berries> getObservable() {
        return observable;
    }

    @Override
    public Gson getGson() {
        return gson;
    }

    @Override
    @NotNull
    public TypeToken<Berry> getTypeToken() {
        return typeToken;
    }

    @Override
    @NotNull
    public String getResourcePath() {
        return resourcePath;
    }

    @Override
    public void reload(@NotNull Map<ResourceLocation, Berry> data) {
        Intrinsics.checkNotNullParameter(data, (String)"data");
        berries.clear();
        Map<ResourceLocation, Berry> $this$forEach$iv = data;
        boolean $i$f$forEach = false;
        Iterator<Map.Entry<ResourceLocation, Berry>> iterator = $this$forEach$iv.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<ResourceLocation, Berry> element$iv;
            Map.Entry<ResourceLocation, Berry> entry = element$iv = iterator.next();
            boolean bl = false;
            ResourceLocation identifier = entry.getKey();
            Berry berry = entry.getValue();
            try {
                berry.setIdentifier$common(identifier);
                berry.validate$common();
                ((Map)berries).put(identifier, berry);
            }
            catch (Exception e) {
                Cobblemon.INSTANCE.getLOGGER().error("Skipped loading the {} berry", (Object)identifier, (Object)e);
            }
        }
        Cobblemon.INSTANCE.getLOGGER().info("Loaded {} berries", (Object)berries.size());
        Berries[] berriesArray = new Berries[]{this};
        this.getObservable().emit((Berries[])berriesArray);
    }

    @Override
    public void sync(@NotNull ServerPlayer player) {
        Intrinsics.checkNotNullParameter((Object)player, (String)"player");
        new BerryRegistrySyncPacket((Collection<Berry>)this.all()).sendToPlayer(player);
    }

    @NotNull
    public final List<Berry> all() {
        Collection<Berry> collection = berries.values();
        Intrinsics.checkNotNullExpressionValue(collection, (String)"berries.values");
        return CollectionsKt.toList((Iterable)collection);
    }

    @Nullable
    public final Berry getByIdentifier(@NotNull ResourceLocation identifier) {
        Intrinsics.checkNotNullParameter((Object)identifier, (String)"identifier");
        return berries.get(identifier);
    }

    @Nullable
    public final Berry getByName(@NotNull String name) {
        Intrinsics.checkNotNullParameter((Object)name, (String)"name");
        return this.getByIdentifier(MiscUtilsKt.cobblemonResource(name));
    }

    @Override
    public void reload(@NotNull ResourceManager manager) {
        JsonDataRegistry.DefaultImpls.reload(this, manager);
    }

    static {
        Type[] typeArray = new Type[]{AABB.class};
        GsonBuilder gsonBuilder = new GsonBuilder().disableHtmlEscaping().setPrettyPrinting().registerTypeAdapter((Type)((Object)MulchVariant.class), (Object)MulchVariantAdapter.INSTANCE).registerTypeAdapter((Type)((Object)MinMaxBounds.Doubles.class), (Object)FloatNumberRangeAdapter.INSTANCE).registerTypeAdapter((Type)((Object)Status.class), (Object)StatusAdapter.INSTANCE).registerTypeAdapter(TypeToken.getParameterized((Type)((Type)((Object)Collection.class)), (Type[])typeArray).getType(), (Object)BoxCollectionAdapter.INSTANCE).registerTypeAdapter((Type)((Object)AABB.class), (Object)BoxAdapter.INSTANCE).registerTypeAdapter((Type)((Object)Vec3.class), (Object)VerboseVec3dAdapter.INSTANCE).registerTypeAdapter((Type)((Object)ResourceLocation.class), (Object)IdentifierAdapter.INSTANCE).registerTypeAdapter((Type)((Object)GrowthFactor.class), (Object)CobblemonGrowthFactorAdapter.INSTANCE).registerTypeAdapter((Type)((Object)IntRange.class), (Object)VerboseIntRangeAdapter.INSTANCE).registerTypeAdapter((Type)((Object)Color.class), (Object)LiteralHexColorAdapter.INSTANCE).registerTypeAdapter((Type)((Object)Stat.class), (Object)CobblemonStatTypeAdapter.INSTANCE);
        typeArray = new Type[]{Biome.class};
        Type type = TypeToken.getParameterized((Type)((Type)((Object)TagKey.class)), (Type[])typeArray).getType();
        ResourceKey resourceKey = Registries.f_256952_;
        Intrinsics.checkNotNullExpressionValue((Object)resourceKey, (String)"BIOME");
        gson = gsonBuilder.registerTypeAdapter(type, new TagKeyAdapter(resourceKey)).registerTypeAdapter((Type)((Object)BerrySpawnCondition.class), (Object)CobblemonBerrySpawnConditionAdapter.INSTANCE).create();
        TypeToken typeToken = TypeToken.get(Berry.class);
        Intrinsics.checkNotNullExpressionValue((Object)typeToken, (String)"get(Berry::class.java)");
        Berries.typeToken = typeToken;
        resourcePath = "berries";
        berries = new HashMap();
    }
}

