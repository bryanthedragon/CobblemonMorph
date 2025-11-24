/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.gson.Gson
 *  com.google.gson.GsonBuilder
 *  kotlin.Metadata
 *  kotlin.Pair
 *  kotlin.TuplesKt
 *  kotlin.collections.CollectionsKt
 *  kotlin.jvm.functions.Function1
 *  kotlin.jvm.internal.DefaultConstructorMarker
 *  kotlin.jvm.internal.Intrinsics
 *  kotlin.jvm.internal.SourceDebugExtension
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.world.entity.Entity
 *  org.jetbrains.annotations.NotNull
 *  org.joml.Vector3f
 *  org.joml.Vector4f
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.ModelAssetVariation;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.ModelLayer;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.ModelTextureSupplier;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.VaryingRenderableResolver;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.PoseableEntityModel;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.pose.Bone;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.repository.VaryingModelRepository;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.adapters.IdentifierAdapter;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.adapters.ModelTextureSupplierAdapter;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.adapters.Vector3fAdapter;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.adapters.Vector4fAdapter;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.Set;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.TuplesKt;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import org.jetbrains.annotations.NotNull;
import org.joml.Vector3f;
import org.joml.Vector4f;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000t\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0000\n\u0002\u0010\"\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u001c\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0010\u0007\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0010%\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010!\n\u0002\b\b\u0018\u0000 ?*\b\b\u0000\u0010\u0002*\u00020\u0001*\u000e\b\u0001\u0010\u0004*\b\u0012\u0004\u0012\u00028\u00000\u00032\u00020\u0005:\u0001?B\u001d\u0012\u0006\u0010-\u001a\u00020\u0007\u0012\f\u00109\u001a\b\u0012\u0004\u0012\u00020\u001d08\u00a2\u0006\u0004\b=\u0010>J\u0013\u0010\b\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006\u00a2\u0006\u0004\b\b\u0010\tJ!\u0010\u000e\u001a\b\u0012\u0004\u0012\u00020\r0\f2\f\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\n0\u0006\u00a2\u0006\u0004\b\u000e\u0010\u000fJ\u001b\u0010\u0010\u001a\u00028\u00012\f\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\n0\u0006\u00a2\u0006\u0004\b\u0010\u0010\u0011J!\u0010\u0012\u001a\b\u0012\u0004\u0012\u00020\r0\f2\f\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\n0\u0006\u00a2\u0006\u0004\b\u0012\u0010\u000fJ\u001b\u0010\u0013\u001a\u00020\u00072\f\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\n0\u0006\u00a2\u0006\u0004\b\u0013\u0010\u0014J\u001b\u0010\u0015\u001a\u00020\u00072\f\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\n0\u0006\u00a2\u0006\u0004\b\u0015\u0010\u0014J#\u0010\u0018\u001a\u00020\u00072\f\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\n0\u00062\u0006\u0010\u0017\u001a\u00020\u0016\u00a2\u0006\u0004\b\u0018\u0010\u0019J#\u0010\u001a\u001a\u00020\u00072\f\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\n0\u00062\u0006\u0010\u0017\u001a\u00020\u0016\u00a2\u0006\u0004\b\u001a\u0010\u0019J@\u0010 \u001a\u0004\u0018\u00018\u0002\"\u0004\b\u0002\u0010\u001b2\f\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\n0\u00062\u0019\u0010\u001f\u001a\u0015\u0012\u0004\u0012\u00020\u001d\u0012\u0006\u0012\u0004\u0018\u00018\u00020\u001c\u00a2\u0006\u0002\b\u001eH\u0002\u00a2\u0006\u0004\b \u0010!J!\u0010%\u001a\u00020$2\u0012\u0010#\u001a\u000e\u0012\u0004\u0012\u00028\u0000\u0012\u0004\u0012\u00028\u00010\"\u00a2\u0006\u0004\b%\u0010&R#\u0010)\u001a\u000e\u0012\u0004\u0012\u00020\u0007\u0012\u0004\u0012\u00020(0'8\u0006\u00a2\u0006\f\n\u0004\b)\u0010*\u001a\u0004\b+\u0010,R\u0017\u0010-\u001a\u00020\u00078\u0006\u00a2\u0006\f\n\u0004\b-\u0010.\u001a\u0004\b/\u00100R/\u00102\u001a\u001a\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u00020\u0007\u0012\u0004\u0012\u00020\u000701\u0012\u0004\u0012\u00028\u00010'8\u0006\u00a2\u0006\f\n\u0004\b2\u0010*\u001a\u0004\b3\u0010,R.\u0010#\u001a\u000e\u0012\u0004\u0012\u00028\u0000\u0012\u0004\u0012\u00028\u00010\"8\u0006@\u0006X\u0086.\u00a2\u0006\u0012\n\u0004\b#\u00104\u001a\u0004\b5\u00106\"\u0004\b7\u0010&R\u001d\u00109\u001a\b\u0012\u0004\u0012\u00020\u001d088\u0006\u00a2\u0006\f\n\u0004\b9\u0010:\u001a\u0004\b;\u0010<\u00a8\u0006@"}, d2={"Lcom/cobblemon/mod/common/client/render/VaryingRenderableResolver;", "Lnet/minecraft/world/entity/Entity;", "E", "Lcom/cobblemon/mod/common/client/render/models/blockbench/PoseableEntityModel;", "M", "", "", "Lnet/minecraft/resources/ResourceLocation;", "getAllModels", "()Ljava/util/Set;", "", "aspects", "", "Lcom/cobblemon/mod/common/client/render/ModelLayer;", "getLayers", "(Ljava/util/Set;)Ljava/lang/Iterable;", "getPoser", "(Ljava/util/Set;)Lcom/cobblemon/mod/common/client/render/models/blockbench/PoseableEntityModel;", "getResolvedLayers", "getResolvedModel", "(Ljava/util/Set;)Lnet/minecraft/resources/ResourceLocation;", "getResolvedPoser", "", "animationSeconds", "getResolvedTexture", "(Ljava/util/Set;F)Lnet/minecraft/resources/ResourceLocation;", "getTexture", "T", "Lkotlin/Function1;", "Lcom/cobblemon/mod/common/client/render/ModelAssetVariation;", "Lkotlin/ExtensionFunctionType;", "selector", "getVariationValue", "(Ljava/util/Set;Lkotlin/jvm/functions/Function1;)Ljava/lang/Object;", "Lcom/cobblemon/mod/common/client/render/models/blockbench/repository/VaryingModelRepository;", "repository", "", "initialize", "(Lcom/cobblemon/mod/common/client/render/models/blockbench/repository/VaryingModelRepository;)V", "", "Lcom/cobblemon/mod/common/client/render/models/blockbench/pose/Bone;", "models", "Ljava/util/Map;", "getModels", "()Ljava/util/Map;", "name", "Lnet/minecraft/resources/ResourceLocation;", "getName", "()Lnet/minecraft/resources/ResourceLocation;", "Lkotlin/Pair;", "posers", "getPosers", "Lcom/cobblemon/mod/common/client/render/models/blockbench/repository/VaryingModelRepository;", "getRepository", "()Lcom/cobblemon/mod/common/client/render/models/blockbench/repository/VaryingModelRepository;", "setRepository", "", "variations", "Ljava/util/List;", "getVariations", "()Ljava/util/List;", "<init>", "(Lnet/minecraft/resources/ResourceLocation;Ljava/util/List;)V", "Companion", "common"})
@SourceDebugExtension(value={"SMAP\nVaryingRenderableResolver.kt\nKotlin\n*S Kotlin\n*F\n+ 1 VaryingRenderableResolver.kt\ncom/cobblemon/mod/common/client/render/VaryingRenderableResolver\n+ 2 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n*L\n1#1,205:1\n533#2,4:206\n1726#2,3:210\n538#2:213\n1726#2,3:214\n766#2:217\n857#2,2:218\n1855#2,2:220\n*S KotlinDebug\n*F\n+ 1 VaryingRenderableResolver.kt\ncom/cobblemon/mod/common/client/render/VaryingRenderableResolver\n*L\n57#1:206,4\n57#1:210,3\n57#1:213\n64#1:214,3\n70#1:217\n70#1:218,2\n98#1:220,2\n*E\n"})
public final class VaryingRenderableResolver<E extends Entity, M extends PoseableEntityModel<E>> {
    @NotNull
    public static final Companion Companion = new Companion(null);
    @NotNull
    private final ResourceLocation name;
    @NotNull
    private final List<ModelAssetVariation> variations;
    public VaryingModelRepository<E, M> repository;
    @NotNull
    private final Map<Pair<ResourceLocation, ResourceLocation>, M> posers;
    @NotNull
    private final Map<ResourceLocation, Bone> models;
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().registerTypeAdapter((Type)((Object)ResourceLocation.class), (Object)IdentifierAdapter.INSTANCE).registerTypeAdapter((Type)((Object)Vector3f.class), (Object)Vector3fAdapter.INSTANCE).registerTypeAdapter((Type)((Object)Vector4f.class), (Object)Vector4fAdapter.INSTANCE).registerTypeAdapter((Type)((Object)ModelTextureSupplier.class), (Object)ModelTextureSupplierAdapter.INSTANCE).disableHtmlEscaping().setLenient().create();

    public VaryingRenderableResolver(@NotNull ResourceLocation name, @NotNull List<ModelAssetVariation> variations) {
        Intrinsics.checkNotNullParameter((Object)name, (String)"name");
        Intrinsics.checkNotNullParameter(variations, (String)"variations");
        this.name = name;
        this.variations = variations;
        this.posers = new LinkedHashMap();
        this.models = new LinkedHashMap();
    }

    @NotNull
    public final ResourceLocation getName() {
        return this.name;
    }

    @NotNull
    public final List<ModelAssetVariation> getVariations() {
        return this.variations;
    }

    @NotNull
    public final VaryingModelRepository<E, M> getRepository() {
        VaryingModelRepository<E, M> varyingModelRepository = this.repository;
        if (varyingModelRepository != null) {
            return varyingModelRepository;
        }
        Intrinsics.throwUninitializedPropertyAccessException((String)"repository");
        return null;
    }

    public final void setRepository(@NotNull VaryingModelRepository<E, M> varyingModelRepository) {
        Intrinsics.checkNotNullParameter(varyingModelRepository, (String)"<set-?>");
        this.repository = varyingModelRepository;
    }

    @NotNull
    public final Map<Pair<ResourceLocation, ResourceLocation>, M> getPosers() {
        return this.posers;
    }

    @NotNull
    public final Map<ResourceLocation, Bone> getModels() {
        return this.models;
    }

    @NotNull
    public final ResourceLocation getResolvedPoser(@NotNull Set<String> aspects) {
        Intrinsics.checkNotNullParameter(aspects, (String)"aspects");
        ResourceLocation resourceLocation = (ResourceLocation)this.getVariationValue(aspects, getResolvedPoser.1.INSTANCE);
        if (resourceLocation == null) {
            throw new IllegalStateException("Unable to find a poser for " + this.name + " with aspects " + CollectionsKt.joinToString$default((Iterable)aspects, null, null, null, (int)0, null, null, (int)63, null) + ". This shouldn't be possible if you've defined the fallback variation.");
        }
        return resourceLocation;
    }

    @NotNull
    public final ResourceLocation getResolvedModel(@NotNull Set<String> aspects) {
        Intrinsics.checkNotNullParameter(aspects, (String)"aspects");
        ResourceLocation resourceLocation = (ResourceLocation)this.getVariationValue(aspects, getResolvedModel.1.INSTANCE);
        if (resourceLocation == null) {
            throw new IllegalStateException("Unable to find a model for " + this.name + " with aspects " + CollectionsKt.joinToString$default((Iterable)aspects, null, null, null, (int)0, null, null, (int)63, null) + ". This shouldn't be possible if you've defined the fallback variation.");
        }
        return resourceLocation;
    }

    @NotNull
    public final ResourceLocation getResolvedTexture(@NotNull Set<String> aspects, float animationSeconds) {
        Intrinsics.checkNotNullParameter(aspects, (String)"aspects");
        ModelTextureSupplier modelTextureSupplier = (ModelTextureSupplier)this.getVariationValue(aspects, getResolvedTexture.1.INSTANCE);
        if (modelTextureSupplier == null || (modelTextureSupplier = modelTextureSupplier.invoke(animationSeconds)) == null) {
            throw new IllegalStateException("Unable to find a texture for " + this.name + " with aspects " + CollectionsKt.joinToString$default((Iterable)aspects, null, null, null, (int)0, null, null, (int)63, null) + ". This shouldn't be possible if you've defined the fallback variation.");
        }
        return modelTextureSupplier;
    }

    private final <T> T getVariationValue(Set<String> aspects, Function1<? super ModelAssetVariation, ? extends T> selector2) {
        ModelAssetVariation modelAssetVariation;
        block5: {
            List<ModelAssetVariation> $this$lastOrNull$iv = this.variations;
            boolean $i$f$lastOrNull = false;
            ListIterator<ModelAssetVariation> iterator$iv = $this$lastOrNull$iv.listIterator($this$lastOrNull$iv.size());
            while (iterator$iv.hasPrevious()) {
                boolean bl;
                ModelAssetVariation it;
                ModelAssetVariation element$iv;
                block4: {
                    it = element$iv = iterator$iv.previous();
                    boolean bl2 = false;
                    Iterable $this$all$iv = it.getAspects();
                    boolean $i$f$all = false;
                    if ($this$all$iv instanceof Collection && ((Collection)$this$all$iv).isEmpty()) {
                        bl = true;
                    } else {
                        for (Object element$iv2 : $this$all$iv) {
                            String it2 = (String)element$iv2;
                            boolean bl3 = false;
                            if (aspects.contains(it2)) continue;
                            bl = false;
                            break block4;
                        }
                        bl = true;
                    }
                }
                if (!(bl && selector2.invoke((Object)it) != null)) continue;
                modelAssetVariation = element$iv;
                break block5;
            }
            modelAssetVariation = null;
        }
        ModelAssetVariation modelAssetVariation2 = modelAssetVariation;
        return (T)(modelAssetVariation2 != null ? selector2.invoke((Object)modelAssetVariation2) : null);
    }

    /*
     * WARNING - void declaration
     */
    @NotNull
    public final Iterable<ModelLayer> getResolvedLayers(@NotNull Set<String> aspects) {
        void $this$filterTo$iv$iv;
        Iterable<ModelLayer> layers;
        Intrinsics.checkNotNullParameter(aspects, (String)"aspects");
        Map layerMaps = new LinkedHashMap();
        for (ModelAssetVariation variation : this.variations) {
            boolean bl;
            block6: {
                layers = variation.getLayers();
                if (layers == null) continue;
                Iterable $this$all$iv = variation.getAspects();
                boolean $i$f$all = false;
                if ($this$all$iv instanceof Collection && ((Collection)$this$all$iv).isEmpty()) {
                    bl = true;
                } else {
                    Iterator iterator = $this$all$iv.iterator();
                    while (iterator.hasNext()) {
                        Object element$iv = iterator.next();
                        String it = (String)element$iv;
                        boolean bl2 = false;
                        if (aspects.contains(it)) continue;
                        bl = false;
                        break block6;
                    }
                    bl = true;
                }
            }
            if (!bl) continue;
            for (ModelLayer layer : layers) {
                layerMaps.put(layer.getName(), layer);
            }
        }
        Iterable $this$filter$iv = layerMaps.values();
        boolean $i$f$filter = false;
        layers = $this$filter$iv;
        Collection destination$iv$iv = new ArrayList();
        boolean $i$f$filterTo = false;
        for (Object element$iv$iv : $this$filterTo$iv$iv) {
            ModelLayer p0 = (ModelLayer)element$iv$iv;
            boolean bl = false;
            if (!p0.getEnabled()) continue;
            destination$iv$iv.add(element$iv$iv);
        }
        return (List)destination$iv$iv;
    }

    @NotNull
    public final Set<ResourceLocation> getAllModels() {
        Set models = new LinkedHashSet();
        for (ModelAssetVariation variation : this.variations) {
            if (variation.getModel() == null) continue;
            models.add(variation.getModel());
        }
        return models;
    }

    public final void initialize(@NotNull VaryingModelRepository<E, M> repository) {
        Intrinsics.checkNotNullParameter(repository, (String)"repository");
        this.setRepository(repository);
        this.posers.clear();
        Iterable $this$forEach$iv = this.getAllModels();
        boolean $i$f$forEach = false;
        for (Object element$iv : $this$forEach$iv) {
            ResourceLocation identifier = (ResourceLocation)element$iv;
            boolean bl = false;
            try {
                Function1<Boolean, Bone> function1 = repository.getTexturedModels().get(identifier);
                Intrinsics.checkNotNull(function1);
                this.models.put(identifier, (Bone)function1.invoke((Object)repository.isForLivingEntityRenderer()));
            }
            catch (Exception e) {
                throw new IllegalStateException("Unable to load model " + identifier + " for " + this.name, e);
            }
        }
    }

    @NotNull
    public final M getPoser(@NotNull Set<String> aspects) {
        PoseableEntityModel poseableEntityModel;
        Intrinsics.checkNotNullParameter(aspects, (String)"aspects");
        ResourceLocation poserName = this.getResolvedPoser(aspects);
        Function1<Bone, M> function1 = this.getRepository().getPosers().get(poserName);
        if (function1 == null) {
            throw new IllegalStateException("No poser found for name: " + poserName + " for " + this.name);
        }
        Function1<Bone, M> poserSupplier = function1;
        ResourceLocation modelName = this.getResolvedModel(aspects);
        PoseableEntityModel existingEntityModel = (PoseableEntityModel)this.posers.get(TuplesKt.to((Object)poserName, (Object)modelName));
        if (existingEntityModel != null) {
            poseableEntityModel = existingEntityModel;
        } else {
            Bone bone = this.models.get(modelName);
            Intrinsics.checkNotNull((Object)bone);
            Bone model = bone;
            PoseableEntityModel entityModel = (PoseableEntityModel)poserSupplier.invoke((Object)model);
            entityModel.initializeLocatorAccess();
            entityModel.registerPoses();
            this.posers.put((Pair<ResourceLocation, ResourceLocation>)TuplesKt.to((Object)poserName, (Object)modelName), entityModel);
            poseableEntityModel = entityModel;
        }
        return (M)poseableEntityModel;
    }

    @NotNull
    public final ResourceLocation getTexture(@NotNull Set<String> aspects, float animationSeconds) {
        Intrinsics.checkNotNullParameter(aspects, (String)"aspects");
        if (this.getRepository().getPosers().get(this.getResolvedPoser(aspects)) == null) {
            throw new IllegalStateException("No poser for " + this.name);
        }
        return this.getResolvedTexture(aspects, animationSeconds);
    }

    @NotNull
    public final Iterable<ModelLayer> getLayers(@NotNull Set<String> aspects) {
        Intrinsics.checkNotNullParameter(aspects, (String)"aspects");
        if (this.getRepository().getPosers().get(this.getResolvedPoser(aspects)) == null) {
            throw new IllegalStateException("No poser for " + this.name);
        }
        return this.getResolvedLayers(aspects);
    }

    @Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000\u0010\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0018\u0002\n\u0002\b\b\b\u0086\u0003\u0018\u00002\u00020\u0001B\t\b\u0002\u00a2\u0006\u0004\b\b\u0010\tR\u001f\u0010\u0004\u001a\n \u0003*\u0004\u0018\u00010\u00020\u00028\u0006\u00a2\u0006\f\n\u0004\b\u0004\u0010\u0005\u001a\u0004\b\u0006\u0010\u0007\u00a8\u0006\n"}, d2={"Lcom/cobblemon/mod/common/client/render/VaryingRenderableResolver$Companion;", "", "Lcom/google/gson/Gson;", "kotlin.jvm.PlatformType", "GSON", "Lcom/google/gson/Gson;", "getGSON", "()Lcom/google/gson/Gson;", "<init>", "()V", "common"})
    public static final class Companion {
        private Companion() {
        }

        public final Gson getGSON() {
            return GSON;
        }

        public /* synthetic */ Companion(DefaultConstructorMarker $constructor_marker) {
            this();
        }
    }
}

