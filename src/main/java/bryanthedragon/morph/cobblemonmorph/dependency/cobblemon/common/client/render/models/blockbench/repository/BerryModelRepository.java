/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.gson.Gson
 *  com.google.gson.reflect.TypeToken
 *  kotlin.Metadata
 *  kotlin.jvm.internal.Intrinsics
 *  kotlin.jvm.internal.SourceDebugExtension
 *  net.minecraft.client.model.geom.ModelPart
 *  net.minecraft.client.model.geom.builders.LayerDefinition
 *  net.minecraft.client.renderer.texture.TextureAtlasSprite
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.server.level.ServerPlayer
 *  net.minecraft.server.packs.PackType
 *  net.minecraft.server.packs.resources.ResourceManager
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.repository;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.Cobblemon;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.berry.Berries;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.berry.Berry;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.data.JsonDataRegistry;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.reactive.SimpleObservable;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.atlas.CobblemonAtlases;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.TexturedModel;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.MiscUtilsKt;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.packs.PackType;
import net.minecraft.server.packs.resources.ResourceManager;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0010$\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0006\b\u00c6\u0002\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001B\t\b\u0002\u00a2\u0006\u0004\b5\u0010\nJ\u0017\u0010\u0006\u001a\u0004\u0018\u00010\u00052\u0006\u0010\u0004\u001a\u00020\u0003\u00a2\u0006\u0004\b\u0006\u0010\u0007J\r\u0010\t\u001a\u00020\b\u00a2\u0006\u0004\b\t\u0010\nJ#\u0010\r\u001a\u00020\b2\u0012\u0010\f\u001a\u000e\u0012\u0004\u0012\u00020\u0003\u0012\u0004\u0012\u00020\u00020\u000bH\u0016\u00a2\u0006\u0004\b\r\u0010\u000eJ\u0017\u0010\u0011\u001a\u00020\b2\u0006\u0010\u0010\u001a\u00020\u000fH\u0016\u00a2\u0006\u0004\b\u0011\u0010\u0012R\u001a\u0010\u0014\u001a\u00020\u00138\u0016X\u0096\u0004\u00a2\u0006\f\n\u0004\b\u0014\u0010\u0015\u001a\u0004\b\u0016\u0010\u0017R\u001a\u0010\u0018\u001a\u00020\u00038\u0016X\u0096\u0004\u00a2\u0006\f\n\u0004\b\u0018\u0010\u0019\u001a\u0004\b\u001a\u0010\u001bR \u0010\u001d\u001a\b\u0012\u0004\u0012\u00020\u00000\u001c8\u0016X\u0096\u0004\u00a2\u0006\f\n\u0004\b\u001d\u0010\u001e\u001a\u0004\b\u001f\u0010 R0\u0010#\u001a\u001e\u0012\u0004\u0012\u00020\u0003\u0012\u0004\u0012\u00020\u00050!j\u000e\u0012\u0004\u0012\u00020\u0003\u0012\u0004\u0012\u00020\u0005`\"8\u0002X\u0082\u0004\u00a2\u0006\u0006\n\u0004\b#\u0010$R0\u0010%\u001a\u001e\u0012\u0004\u0012\u00020\u0003\u0012\u0004\u0012\u00020\u00020!j\u000e\u0012\u0004\u0012\u00020\u0003\u0012\u0004\u0012\u00020\u0002`\"8\u0002X\u0082\u0004\u00a2\u0006\u0006\n\u0004\b%\u0010$R\u001a\u0010'\u001a\u00020&8\u0016X\u0096D\u00a2\u0006\f\n\u0004\b'\u0010(\u001a\u0004\b)\u0010*R\u001a\u0010,\u001a\u00020+8\u0016X\u0096\u0004\u00a2\u0006\f\n\u0004\b,\u0010-\u001a\u0004\b.\u0010/R \u00101\u001a\b\u0012\u0004\u0012\u00020\u0002008\u0016X\u0096\u0004\u00a2\u0006\f\n\u0004\b1\u00102\u001a\u0004\b3\u00104\u00a8\u00066"}, d2={"Lcom/cobblemon/mod/common/client/render/models/blockbench/repository/BerryModelRepository;", "Lcom/cobblemon/mod/common/api/data/JsonDataRegistry;", "Lcom/cobblemon/mod/common/client/render/models/blockbench/TexturedModel;", "Lnet/minecraft/resources/ResourceLocation;", "identifier", "Lnet/minecraft/client/model/geom/ModelPart;", "modelOf", "(Lnet/minecraft/resources/ResourceLocation;)Lnet/minecraft/client/model/geom/ModelPart;", "", "patchModels", "()V", "", "data", "reload", "(Ljava/util/Map;)V", "Lnet/minecraft/server/level/ServerPlayer;", "player", "sync", "(Lnet/minecraft/server/level/ServerPlayer;)V", "Lcom/google/gson/Gson;", "gson", "Lcom/google/gson/Gson;", "getGson", "()Lcom/google/gson/Gson;", "id", "Lnet/minecraft/resources/ResourceLocation;", "getId", "()Lnet/minecraft/resources/ResourceLocation;", "Lcom/cobblemon/mod/common/api/reactive/SimpleObservable;", "observable", "Lcom/cobblemon/mod/common/api/reactive/SimpleObservable;", "getObservable", "()Lcom/cobblemon/mod/common/api/reactive/SimpleObservable;", "Ljava/util/HashMap;", "Lkotlin/collections/HashMap;", "processedModels", "Ljava/util/HashMap;", "rawModels", "", "resourcePath", "Ljava/lang/String;", "getResourcePath", "()Ljava/lang/String;", "Lnet/minecraft/server/packs/PackType;", "type", "Lnet/minecraft/server/packs/PackType;", "getType", "()Lnet/minecraft/server/packs/PackType;", "Lcom/google/gson/reflect/TypeToken;", "typeToken", "Lcom/google/gson/reflect/TypeToken;", "getTypeToken", "()Lcom/google/gson/reflect/TypeToken;", "<init>", "common"})
@SourceDebugExtension(value={"SMAP\nBerryModelRepository.kt\nKotlin\n*S Kotlin\n*F\n+ 1 BerryModelRepository.kt\ncom/cobblemon/mod/common/client/render/models/blockbench/repository/BerryModelRepository\n+ 2 _Maps.kt\nkotlin/collections/MapsKt___MapsKt\n+ 3 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n*L\n1#1,78:1\n215#2,2:79\n1855#3,2:81\n*S KotlinDebug\n*F\n+ 1 BerryModelRepository.kt\ncom/cobblemon/mod/common/client/render/models/blockbench/repository/BerryModelRepository\n*L\n44#1:79,2\n53#1:81,2\n*E\n"})
public final class BerryModelRepository
implements JsonDataRegistry<TexturedModel> {
    @NotNull
    public static final BerryModelRepository INSTANCE = new BerryModelRepository();
    @NotNull
    private static final ResourceLocation id = MiscUtilsKt.cobblemonResource("berry_models");
    @NotNull
    private static final PackType type = PackType.CLIENT_RESOURCES;
    @NotNull
    private static final SimpleObservable<BerryModelRepository> observable = new SimpleObservable();
    @NotNull
    private static final Gson gson;
    @NotNull
    private static final TypeToken<TexturedModel> typeToken;
    @NotNull
    private static final String resourcePath;
    @NotNull
    private static final HashMap<ResourceLocation, TexturedModel> rawModels;
    @NotNull
    private static final HashMap<ResourceLocation, ModelPart> processedModels;

    private BerryModelRepository() {
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
    public SimpleObservable<BerryModelRepository> getObservable() {
        return observable;
    }

    @Override
    @NotNull
    public Gson getGson() {
        return gson;
    }

    @Override
    @NotNull
    public TypeToken<TexturedModel> getTypeToken() {
        return typeToken;
    }

    @Override
    @NotNull
    public String getResourcePath() {
        return resourcePath;
    }

    @Override
    public void sync(@NotNull ServerPlayer player) {
        Intrinsics.checkNotNullParameter((Object)player, (String)"player");
    }

    @Override
    public void reload(@NotNull Map<ResourceLocation, TexturedModel> data) {
        Intrinsics.checkNotNullParameter(data, (String)"data");
        Map<ResourceLocation, TexturedModel> $this$forEach$iv = data;
        boolean $i$f$forEach = false;
        Iterator<Map.Entry<ResourceLocation, TexturedModel>> iterator = $this$forEach$iv.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<ResourceLocation, TexturedModel> element$iv;
            Map.Entry<ResourceLocation, TexturedModel> entry = element$iv = iterator.next();
            boolean bl = false;
            ResourceLocation identifier = entry.getKey();
            TexturedModel model = entry.getValue();
            ((Map)rawModels).put(identifier, model);
        }
        BerryModelRepository[] berryModelRepositoryArray = new BerryModelRepository[]{this};
        this.getObservable().emit((BerryModelRepository[])berryModelRepositoryArray);
        Cobblemon.INSTANCE.getLOGGER().info("Loaded {} berry models", (Object)rawModels.size());
    }

    public final void patchModels() {
        Iterable $this$forEach$iv = Berries.INSTANCE.all();
        boolean $i$f$forEach = false;
        for (Object element$iv : $this$forEach$iv) {
            LayerDefinition layerDefinition;
            Berry it = (Berry)element$iv;
            boolean bl = false;
            TexturedModel fruitModel = rawModels.get(it.getFruitModelIdentifier());
            TexturedModel flowerModel = rawModels.get(it.getFlowerModelIdentifier());
            ResourceLocation fruitTexId = it.getFruitTexture();
            ResourceLocation flowerTexId = it.getFlowerTexture();
            TextureAtlasSprite fruitTex = CobblemonAtlases.INSTANCE.getBERRY_SPRITE_ATLAS().m_118901_(fruitTexId);
            TextureAtlasSprite flowerTex = CobblemonAtlases.INSTANCE.getBERRY_SPRITE_ATLAS().m_118901_(flowerTexId);
            Map map = processedModels;
            ResourceLocation resourceLocation = it.getFruitModelIdentifier();
            Object object = fruitModel != null && (layerDefinition = fruitModel.createWithUvOverride(false, fruitTex.m_174743_(), fruitTex.m_174744_(), CobblemonAtlases.INSTANCE.getBERRY_SPRITE_ATLAS().f_118884_.f_276067_, CobblemonAtlases.INSTANCE.getBERRY_SPRITE_ATLAS().f_118884_.f_276070_)) != null ? layerDefinition.m_171564_() : null;
            Intrinsics.checkNotNull(object);
            layerDefinition = object;
            map.put(resourceLocation, layerDefinition);
            map = processedModels;
            resourceLocation = it.getFlowerModelIdentifier();
            ModelPart modelPart = flowerModel != null && (layerDefinition = flowerModel.createWithUvOverride(false, flowerTex.m_174743_(), flowerTex.m_174744_(), CobblemonAtlases.INSTANCE.getBERRY_SPRITE_ATLAS().f_118884_.f_276067_, CobblemonAtlases.INSTANCE.getBERRY_SPRITE_ATLAS().f_118884_.f_276070_)) != null ? layerDefinition.m_171564_() : null;
            Intrinsics.checkNotNull(modelPart);
            layerDefinition = modelPart;
            map.put(resourceLocation, layerDefinition);
        }
    }

    @Nullable
    public final ModelPart modelOf(@NotNull ResourceLocation identifier) {
        Intrinsics.checkNotNullParameter((Object)identifier, (String)"identifier");
        return processedModels.get(identifier);
    }

    @Override
    public void reload(@NotNull ResourceManager manager) {
        JsonDataRegistry.DefaultImpls.reload(this, manager);
    }

    static {
        Gson gson2 = TexturedModel.Companion.getGSON();
        Intrinsics.checkNotNullExpressionValue((Object)gson2, (String)"TexturedModel.GSON");
        gson = gson2;
        TypeToken typeToken = TypeToken.get(TexturedModel.class);
        Intrinsics.checkNotNullExpressionValue((Object)typeToken, (String)"get(TexturedModel::class.java)");
        BerryModelRepository.typeToken = typeToken;
        resourcePath = "bedrock/berries";
        rawModels = new HashMap();
        processedModels = new HashMap();
    }
}

