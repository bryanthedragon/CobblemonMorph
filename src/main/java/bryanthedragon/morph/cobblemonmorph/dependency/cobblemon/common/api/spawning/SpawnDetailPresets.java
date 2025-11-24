/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.gson.Gson
 *  com.google.gson.GsonBuilder
 *  com.google.gson.reflect.TypeToken
 *  com.mojang.datafixers.util.Either
 *  kotlin.Metadata
 *  kotlin.collections.MapsKt
 *  kotlin.jvm.internal.Intrinsics
 *  net.minecraft.core.registries.Registries
 *  net.minecraft.resources.ResourceKey
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.server.level.ServerPlayer
 *  net.minecraft.server.packs.PackType
 *  net.minecraft.server.packs.resources.ResourceManager
 *  net.minecraft.tags.TagKey
 *  net.minecraft.world.level.biome.Biome
 *  net.minecraft.world.level.block.Block
 *  net.minecraft.world.level.levelgen.structure.Structure
 *  net.minecraft.world.level.material.Fluid
 *  org.jetbrains.annotations.NotNull
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.Cobblemon;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.conditional.RegistryLikeCondition;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.data.JsonDataRegistry;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.PokemonProperties;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.reactive.SimpleObservable;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.MoonPhaseRange;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.SpawnBucket;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.SpawnDetailPresets;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.TimeRange;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.condition.SpawningCondition;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.context.RegisteredSpawningContext;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.detail.PossibleHeldItem;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.preset.SpawnDetailPreset;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.MiscUtilsKt;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.adapters.BiomeLikeConditionAdapter;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.adapters.BlockLikeConditionAdapter;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.adapters.EitherIdentifierOrTagAdapter;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.adapters.FluidLikeConditionAdapter;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.adapters.IdentifierAdapter;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.adapters.IntRangesAdapter;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.adapters.PokemonPropertiesAdapterKt;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.adapters.PossibleHeldItemAdapter;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.adapters.RegisteredSpawningContextAdapter;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.adapters.SpawnBucketAdapter;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.adapters.SpawnDetailPresetAdapter;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.adapters.SpawningConditionAdapter;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.mojang.datafixers.util.Either;
import java.lang.reflect.Type;
import java.util.LinkedHashMap;
import java.util.Map;
import kotlin.Metadata;
import kotlin.collections.MapsKt;
import kotlin.jvm.internal.Intrinsics;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.packs.PackType;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.minecraft.world.level.material.Fluid;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000^\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0010$\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u000b\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010%\n\u0002\b\u000b\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0007\b\u00c6\u0002\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001B\t\b\u0002\u00a2\u0006\u0004\b;\u0010<J-\u0010\t\u001a\u00020\b\"\b\b\u0000\u0010\u0003*\u00020\u00022\u0006\u0010\u0005\u001a\u00020\u00042\f\u0010\u0007\u001a\b\u0012\u0004\u0012\u00028\u00000\u0006\u00a2\u0006\u0004\b\t\u0010\nJ#\u0010\u000e\u001a\u00020\b2\u0012\u0010\r\u001a\u000e\u0012\u0004\u0012\u00020\f\u0012\u0004\u0012\u00020\u00020\u000bH\u0016\u00a2\u0006\u0004\b\u000e\u0010\u000fJ\u0017\u0010\u0012\u001a\u00020\b2\u0006\u0010\u0011\u001a\u00020\u0010H\u0016\u00a2\u0006\u0004\b\u0012\u0010\u0013R\u001f\u0010\u0016\u001a\n \u0015*\u0004\u0018\u00010\u00140\u00148\u0006\u00a2\u0006\f\n\u0004\b\u0016\u0010\u0017\u001a\u0004\b\u0018\u0010\u0019R\u001a\u0010\u001a\u001a\u00020\u00148\u0016X\u0096\u0004\u00a2\u0006\f\n\u0004\b\u001a\u0010\u0017\u001a\u0004\b\u001b\u0010\u0019R\u001a\u0010\u001c\u001a\u00020\f8\u0016X\u0096\u0004\u00a2\u0006\f\n\u0004\b\u001c\u0010\u001d\u001a\u0004\b\u001e\u0010\u001fR \u0010!\u001a\b\u0012\u0004\u0012\u00020\u00000 8\u0016X\u0096\u0004\u00a2\u0006\f\n\u0004\b!\u0010\"\u001a\u0004\b#\u0010$R+\u0010&\u001a\u0016\u0012\u0004\u0012\u00020\u0004\u0012\f\u0012\n\u0012\u0006\b\u0001\u0012\u00020\u00020\u00060%8\u0006\u00a2\u0006\f\n\u0004\b&\u0010'\u001a\u0004\b(\u0010)R.\u0010*\u001a\u000e\u0012\u0004\u0012\u00020\f\u0012\u0004\u0012\u00020\u00020%8\u0006@\u0006X\u0086\u000e\u00a2\u0006\u0012\n\u0004\b*\u0010'\u001a\u0004\b+\u0010)\"\u0004\b,\u0010\u000fR\u001a\u0010-\u001a\u00020\u00048\u0016X\u0096D\u00a2\u0006\f\n\u0004\b-\u0010.\u001a\u0004\b/\u00100R\u001a\u00102\u001a\u0002018\u0016X\u0096\u0004\u00a2\u0006\f\n\u0004\b2\u00103\u001a\u0004\b4\u00105R>\u00107\u001a&\u0012\f\u0012\n \u0015*\u0004\u0018\u00010\u00020\u0002 \u0015*\u0012\u0012\f\u0012\n \u0015*\u0004\u0018\u00010\u00020\u0002\u0018\u000106068\u0016X\u0096\u0004\u00a2\u0006\f\n\u0004\b7\u00108\u001a\u0004\b9\u0010:\u00a8\u0006="}, d2={"Lcom/cobblemon/mod/common/api/spawning/SpawnDetailPresets;", "Lcom/cobblemon/mod/common/api/data/JsonDataRegistry;", "Lcom/cobblemon/mod/common/api/spawning/preset/SpawnDetailPreset;", "T", "", "name", "Ljava/lang/Class;", "detailClass", "", "registerPresetType", "(Ljava/lang/String;Ljava/lang/Class;)V", "", "Lnet/minecraft/resources/ResourceLocation;", "data", "reload", "(Ljava/util/Map;)V", "Lnet/minecraft/server/level/ServerPlayer;", "player", "sync", "(Lnet/minecraft/server/level/ServerPlayer;)V", "Lcom/google/gson/Gson;", "kotlin.jvm.PlatformType", "GSON", "Lcom/google/gson/Gson;", "getGSON", "()Lcom/google/gson/Gson;", "gson", "getGson", "id", "Lnet/minecraft/resources/ResourceLocation;", "getId", "()Lnet/minecraft/resources/ResourceLocation;", "Lcom/cobblemon/mod/common/api/reactive/SimpleObservable;", "observable", "Lcom/cobblemon/mod/common/api/reactive/SimpleObservable;", "getObservable", "()Lcom/cobblemon/mod/common/api/reactive/SimpleObservable;", "", "presetTypes", "Ljava/util/Map;", "getPresetTypes", "()Ljava/util/Map;", "presets", "getPresets", "setPresets", "resourcePath", "Ljava/lang/String;", "getResourcePath", "()Ljava/lang/String;", "Lnet/minecraft/server/packs/PackType;", "type", "Lnet/minecraft/server/packs/PackType;", "getType", "()Lnet/minecraft/server/packs/PackType;", "Lcom/google/gson/reflect/TypeToken;", "typeToken", "Lcom/google/gson/reflect/TypeToken;", "getTypeToken", "()Lcom/google/gson/reflect/TypeToken;", "<init>", "()V", "common"})
public final class SpawnDetailPresets
implements JsonDataRegistry<SpawnDetailPreset> {
    @NotNull
    public static final SpawnDetailPresets INSTANCE = new SpawnDetailPresets();
    private static final Gson GSON;
    @NotNull
    private static final Map<String, Class<? extends SpawnDetailPreset>> presetTypes;
    @NotNull
    private static final Gson gson;
    private static final TypeToken<SpawnDetailPreset> typeToken;
    @NotNull
    private static final String resourcePath;
    @NotNull
    private static final ResourceLocation id;
    @NotNull
    private static final PackType type;
    @NotNull
    private static final SimpleObservable<SpawnDetailPresets> observable;
    @NotNull
    private static Map<ResourceLocation, SpawnDetailPreset> presets;

    private SpawnDetailPresets() {
    }

    public final Gson getGSON() {
        return GSON;
    }

    @NotNull
    public final Map<String, Class<? extends SpawnDetailPreset>> getPresetTypes() {
        return presetTypes;
    }

    public final <T extends SpawnDetailPreset> void registerPresetType(@NotNull String name, @NotNull Class<T> detailClass) {
        Intrinsics.checkNotNullParameter((Object)name, (String)"name");
        Intrinsics.checkNotNullParameter(detailClass, (String)"detailClass");
        presetTypes.put(name, detailClass);
    }

    @Override
    @NotNull
    public Gson getGson() {
        return gson;
    }

    @Override
    public TypeToken<SpawnDetailPreset> getTypeToken() {
        return typeToken;
    }

    @Override
    @NotNull
    public String getResourcePath() {
        return resourcePath;
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
    public SimpleObservable<SpawnDetailPresets> getObservable() {
        return observable;
    }

    @NotNull
    public final Map<ResourceLocation, SpawnDetailPreset> getPresets() {
        return presets;
    }

    public final void setPresets(@NotNull Map<ResourceLocation, SpawnDetailPreset> map) {
        Intrinsics.checkNotNullParameter(map, (String)"<set-?>");
        presets = map;
    }

    @Override
    public void sync(@NotNull ServerPlayer player) {
        Intrinsics.checkNotNullParameter((Object)player, (String)"player");
    }

    @Override
    public void reload(@NotNull Map<ResourceLocation, ? extends SpawnDetailPreset> data) {
        Intrinsics.checkNotNullParameter(data, (String)"data");
        presets = MapsKt.toMutableMap(data);
        Cobblemon.INSTANCE.getLOGGER().info("Loaded " + presets.size() + " spawn detail presets.");
    }

    @Override
    public void reload(@NotNull ResourceManager manager) {
        JsonDataRegistry.DefaultImpls.reload(this, manager);
    }

    static {
        Type[] typeArray = new Type[]{Biome.class};
        GsonBuilder gsonBuilder = new GsonBuilder().setPrettyPrinting().setLenient().disableHtmlEscaping().registerTypeAdapter((Type)((Object)SpawnBucket.class), (Object)SpawnBucketAdapter.INSTANCE).registerTypeAdapter((Type)((Object)RegisteredSpawningContext.class), (Object)RegisteredSpawningContextAdapter.INSTANCE).registerTypeAdapter(TypeToken.getParameterized((Type)((Type)((Object)RegistryLikeCondition.class)), (Type[])typeArray).getType(), (Object)BiomeLikeConditionAdapter.INSTANCE);
        typeArray = new Type[]{Block.class};
        GsonBuilder gsonBuilder2 = gsonBuilder.registerTypeAdapter(TypeToken.getParameterized((Type)((Type)((Object)RegistryLikeCondition.class)), (Type[])typeArray).getType(), (Object)BlockLikeConditionAdapter.INSTANCE);
        typeArray = new Type[]{Fluid.class};
        GsonBuilder gsonBuilder3 = gsonBuilder2.registerTypeAdapter(TypeToken.getParameterized((Type)((Type)((Object)RegistryLikeCondition.class)), (Type[])typeArray).getType(), (Object)FluidLikeConditionAdapter.INSTANCE);
        typeArray = new Type[2];
        typeArray[0] = ResourceLocation.class;
        Type[] typeArray2 = new Type[]{Structure.class};
        typeArray[1] = TypeToken.getParameterized((Type)((Type)((Object)TagKey.class)), (Type[])typeArray2).getType();
        Type type = TypeToken.getParameterized((Type)((Type)((Object)Either.class)), (Type[])typeArray).getType();
        ResourceKey resourceKey = Registries.f_256944_;
        Intrinsics.checkNotNullExpressionValue((Object)resourceKey, (String)"STRUCTURE");
        GSON = gsonBuilder3.registerTypeAdapter(type, new EitherIdentifierOrTagAdapter(resourceKey)).registerTypeAdapter((Type)((Object)SpawnDetailPreset.class), (Object)SpawnDetailPresetAdapter.INSTANCE).registerTypeAdapter((Type)((Object)ResourceLocation.class), (Object)IdentifierAdapter.INSTANCE).registerTypeAdapter((Type)((Object)SpawningCondition.class), (Object)SpawningConditionAdapter.INSTANCE).registerTypeAdapter((Type)((Object)TimeRange.class), new IntRangesAdapter<TimeRange>(TimeRange.Companion.getTimeRanges(), GSON.1.INSTANCE)).registerTypeAdapter((Type)((Object)MoonPhaseRange.class), new IntRangesAdapter<MoonPhaseRange>(MoonPhaseRange.Companion.getMoonPhaseRanges(), GSON.2.INSTANCE)).registerTypeAdapter((Type)((Object)PokemonProperties.class), (Object)PokemonPropertiesAdapterKt.getPokemonPropertiesShortAdapter()).registerTypeAdapter((Type)((Object)PossibleHeldItem.class), (Object)PossibleHeldItemAdapter.INSTANCE).create();
        presetTypes = new LinkedHashMap();
        Gson gson2 = GSON;
        Intrinsics.checkNotNullExpressionValue((Object)gson2, (String)"GSON");
        gson = gson2;
        typeToken = TypeToken.get(SpawnDetailPreset.class);
        resourcePath = "spawn_detail_presets";
        id = MiscUtilsKt.cobblemonResource(INSTANCE.getResourcePath());
        SpawnDetailPresets.type = PackType.SERVER_DATA;
        observable = new SimpleObservable();
        presets = new LinkedHashMap();
    }
}

