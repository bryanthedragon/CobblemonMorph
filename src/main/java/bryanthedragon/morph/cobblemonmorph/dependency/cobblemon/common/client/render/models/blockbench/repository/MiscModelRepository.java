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
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.server.level.ServerPlayer
 *  net.minecraft.server.packs.PackType
 *  net.minecraft.server.packs.resources.ResourceManager
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.repository;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.Cobblemon;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.data.JsonDataRegistry;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.reactive.SimpleObservable;
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
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.packs.PackType;
import net.minecraft.server.packs.resources.ResourceManager;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000d\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010$\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u000e\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0007\b\u00c6\u0002\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001B\t\b\u0002\u00a2\u0006\u0004\b2\u00103J\u0017\u0010\u0006\u001a\u0004\u0018\u00010\u00052\u0006\u0010\u0004\u001a\u00020\u0003\u00a2\u0006\u0004\b\u0006\u0010\u0007J#\u0010\u000b\u001a\u00020\n2\u0012\u0010\t\u001a\u000e\u0012\u0004\u0012\u00020\u0003\u0012\u0004\u0012\u00020\u00020\bH\u0016\u00a2\u0006\u0004\b\u000b\u0010\fJ\u0017\u0010\u000f\u001a\u00020\n2\u0006\u0010\u000e\u001a\u00020\rH\u0016\u00a2\u0006\u0004\b\u000f\u0010\u0010R\u001a\u0010\u0012\u001a\u00020\u00118\u0016X\u0096\u0004\u00a2\u0006\f\n\u0004\b\u0012\u0010\u0013\u001a\u0004\b\u0014\u0010\u0015R\u001a\u0010\u0016\u001a\u00020\u00038\u0016X\u0096\u0004\u00a2\u0006\f\n\u0004\b\u0016\u0010\u0017\u001a\u0004\b\u0018\u0010\u0019R0\u0010\u001c\u001a\u001e\u0012\u0004\u0012\u00020\u0003\u0012\u0004\u0012\u00020\u00050\u001aj\u000e\u0012\u0004\u0012\u00020\u0003\u0012\u0004\u0012\u00020\u0005`\u001b8\u0002X\u0082\u0004\u00a2\u0006\u0006\n\u0004\b\u001c\u0010\u001dR \u0010\u001f\u001a\b\u0012\u0004\u0012\u00020\u00000\u001e8\u0016X\u0096\u0004\u00a2\u0006\f\n\u0004\b\u001f\u0010 \u001a\u0004\b!\u0010\"R\u001a\u0010$\u001a\u00020#8\u0016X\u0096D\u00a2\u0006\f\n\u0004\b$\u0010%\u001a\u0004\b&\u0010'R\u001a\u0010)\u001a\u00020(8\u0016X\u0096\u0004\u00a2\u0006\f\n\u0004\b)\u0010*\u001a\u0004\b+\u0010,R \u0010.\u001a\b\u0012\u0004\u0012\u00020\u00020-8\u0016X\u0096\u0004\u00a2\u0006\f\n\u0004\b.\u0010/\u001a\u0004\b0\u00101\u00a8\u00064"}, d2={"Lcom/cobblemon/mod/common/client/render/models/blockbench/repository/MiscModelRepository;", "Lcom/cobblemon/mod/common/api/data/JsonDataRegistry;", "Lcom/cobblemon/mod/common/client/render/models/blockbench/TexturedModel;", "Lnet/minecraft/resources/ResourceLocation;", "identifier", "Lnet/minecraft/client/model/geom/ModelPart;", "modelOf", "(Lnet/minecraft/resources/ResourceLocation;)Lnet/minecraft/client/model/geom/ModelPart;", "", "data", "", "reload", "(Ljava/util/Map;)V", "Lnet/minecraft/server/level/ServerPlayer;", "player", "sync", "(Lnet/minecraft/server/level/ServerPlayer;)V", "Lcom/google/gson/Gson;", "gson", "Lcom/google/gson/Gson;", "getGson", "()Lcom/google/gson/Gson;", "id", "Lnet/minecraft/resources/ResourceLocation;", "getId", "()Lnet/minecraft/resources/ResourceLocation;", "Ljava/util/HashMap;", "Lkotlin/collections/HashMap;", "models", "Ljava/util/HashMap;", "Lcom/cobblemon/mod/common/api/reactive/SimpleObservable;", "observable", "Lcom/cobblemon/mod/common/api/reactive/SimpleObservable;", "getObservable", "()Lcom/cobblemon/mod/common/api/reactive/SimpleObservable;", "", "resourcePath", "Ljava/lang/String;", "getResourcePath", "()Ljava/lang/String;", "Lnet/minecraft/server/packs/PackType;", "type", "Lnet/minecraft/server/packs/PackType;", "getType", "()Lnet/minecraft/server/packs/PackType;", "Lcom/google/gson/reflect/TypeToken;", "typeToken", "Lcom/google/gson/reflect/TypeToken;", "getTypeToken", "()Lcom/google/gson/reflect/TypeToken;", "<init>", "()V", "common"})
@SourceDebugExtension(value={"SMAP\nMiscModelRepository.kt\nKotlin\n*S Kotlin\n*F\n+ 1 MiscModelRepository.kt\ncom/cobblemon/mod/common/client/render/models/blockbench/repository/MiscModelRepository\n+ 2 _Maps.kt\nkotlin/collections/MapsKt___MapsKt\n*L\n1#1,50:1\n215#2,2:51\n*S KotlinDebug\n*F\n+ 1 MiscModelRepository.kt\ncom/cobblemon/mod/common/client/render/models/blockbench/repository/MiscModelRepository\n*L\n41#1:51,2\n*E\n"})
public final class MiscModelRepository
implements JsonDataRegistry<TexturedModel> {
    @NotNull
    public static final MiscModelRepository INSTANCE = new MiscModelRepository();
    @NotNull
    private static final ResourceLocation id = MiscUtilsKt.cobblemonResource("misc_models");
    @NotNull
    private static final PackType type = PackType.CLIENT_RESOURCES;
    @NotNull
    private static final SimpleObservable<MiscModelRepository> observable = new SimpleObservable();
    @NotNull
    private static final Gson gson;
    @NotNull
    private static final TypeToken<TexturedModel> typeToken;
    @NotNull
    private static final String resourcePath;
    @NotNull
    private static final HashMap<ResourceLocation, ModelPart> models;

    private MiscModelRepository() {
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
    public SimpleObservable<MiscModelRepository> getObservable() {
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
            ModelPart modelPart;
            Map.Entry<ResourceLocation, TexturedModel> element$iv;
            Map.Entry<ResourceLocation, TexturedModel> entry = element$iv = iterator.next();
            boolean bl = false;
            ResourceLocation identifier = entry.getKey();
            TexturedModel model = entry.getValue();
            Map map = models;
            Intrinsics.checkNotNullExpressionValue((Object)model.create(false).m_171564_(), (String)"model.create(false).createModel()");
            map.put(identifier, modelPart);
        }
        MiscModelRepository[] miscModelRepositoryArray = new MiscModelRepository[]{this};
        this.getObservable().emit((MiscModelRepository[])miscModelRepositoryArray);
        Cobblemon.INSTANCE.getLOGGER().info("Loaded {} misc models", (Object)models.size());
    }

    @Nullable
    public final ModelPart modelOf(@NotNull ResourceLocation identifier) {
        Intrinsics.checkNotNullParameter((Object)identifier, (String)"identifier");
        return models.get(identifier);
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
        MiscModelRepository.typeToken = typeToken;
        resourcePath = "bedrock/misc";
        models = new HashMap();
    }
}

