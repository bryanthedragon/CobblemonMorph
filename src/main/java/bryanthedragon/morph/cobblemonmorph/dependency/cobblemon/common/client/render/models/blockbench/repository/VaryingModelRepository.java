/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.gson.Gson
 *  kotlin.Metadata
 *  kotlin.Unit
 *  kotlin.collections.CollectionsKt
 *  kotlin.comparisons.ComparisonsKt
 *  kotlin.io.CloseableKt
 *  kotlin.io.FilesKt
 *  kotlin.jvm.functions.Function1
 *  kotlin.jvm.internal.DefaultConstructorMarker
 *  kotlin.jvm.internal.Intrinsics
 *  kotlin.jvm.internal.SourceDebugExtension
 *  net.minecraft.client.model.geom.ModelPart
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.server.packs.resources.Resource
 *  net.minecraft.server.packs.resources.ResourceManager
 *  net.minecraft.util.Tuple
 *  net.minecraft.world.entity.Entity
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.repository;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.Cobblemon;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.ModelLayer;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.ModelVariationSet;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.VaryingRenderableResolver;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.PoseableEntityModel;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.TexturedModel;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.pose.Bone;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.util.ClientDistributionUtilsKt;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.IdentifierExtensionsKt;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.MiscUtilsKt;
import com.google.gson.Gson;
import java.io.Closeable;
import java.io.File;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.BiFunction;
import java.util.function.Function;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.comparisons.ComparisonsKt;
import kotlin.io.CloseableKt;
import kotlin.io.FilesKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.Resource;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.util.Tuple;
import net.minecraft.world.entity.Entity;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000\u0082\u0001\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\"\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u001c\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u0007\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0010 \n\u0002\b\u0006\n\u0002\u0010\u000b\n\u0002\b\u0006\n\u0002\u0010%\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\t\n\u0002\u0018\u0002\n\u0002\b\u0005\b&\u0018\u0000 K*\b\b\u0000\u0010\u0002*\u00020\u0001*\u000e\b\u0001\u0010\u0004*\b\u0012\u0004\u0012\u00028\u00000\u00032\u00020\u0005:\u0001KB\u0007\u00a2\u0006\u0004\bJ\u0010!J)\u0010\r\u001a\b\u0012\u0004\u0012\u00020\f0\u000b2\u0006\u0010\u0007\u001a\u00020\u00062\f\u0010\n\u001a\b\u0012\u0004\u0012\u00020\t0\b\u00a2\u0006\u0004\b\r\u0010\u000eJ#\u0010\u000f\u001a\u00028\u00012\u0006\u0010\u0007\u001a\u00020\u00062\f\u0010\n\u001a\b\u0012\u0004\u0012\u00020\t0\b\u00a2\u0006\u0004\b\u000f\u0010\u0010J-\u0010\u0013\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\u00062\f\u0010\n\u001a\b\u0012\u0004\u0012\u00020\t0\b2\b\b\u0002\u0010\u0012\u001a\u00020\u0011\u00a2\u0006\u0004\b\u0013\u0010\u0014J/\u0010\u0015\u001a\u0004\u0018\u00010\u00062\u0006\u0010\u0007\u001a\u00020\u00062\f\u0010\n\u001a\b\u0012\u0004\u0012\u00020\t0\b2\b\b\u0002\u0010\u0012\u001a\u00020\u0011\u00a2\u0006\u0004\b\u0015\u0010\u0014J)\u0010\u001a\u001a\u00020\u00192\u0006\u0010\u0007\u001a\u00020\t2\u0012\u0010\u0018\u001a\u000e\u0012\u0004\u0012\u00020\u0017\u0012\u0004\u0012\u00028\u00010\u0016\u00a2\u0006\u0004\b\u001a\u0010\u001bJ#\u0010\u001e\u001a\u000e\u0012\u0004\u0012\u00020\u001d\u0012\u0004\u0012\u00028\u00010\u00162\u0006\u0010\u001c\u001a\u00020\tH&\u00a2\u0006\u0004\b\u001e\u0010\u001fJ\u000f\u0010 \u001a\u00020\u0019H&\u00a2\u0006\u0004\b \u0010!J\u0017\u0010$\u001a\u00020\u00192\u0006\u0010#\u001a\u00020\"H\u0016\u00a2\u0006\u0004\b$\u0010%J\u0015\u0010&\u001a\u00020\u00192\u0006\u0010#\u001a\u00020\"\u00a2\u0006\u0004\b&\u0010%J\u0015\u0010'\u001a\u00020\u00192\u0006\u0010#\u001a\u00020\"\u00a2\u0006\u0004\b'\u0010%J\u0015\u0010(\u001a\u00020\u00192\u0006\u0010#\u001a\u00020\"\u00a2\u0006\u0004\b(\u0010%J\u0015\u0010)\u001a\u00020\u00192\u0006\u0010#\u001a\u00020\"\u00a2\u0006\u0004\b)\u0010%R\u001a\u0010-\u001a\b\u0012\u0004\u0012\u00020\t0*8&X\u00a6\u0004\u00a2\u0006\u0006\u001a\u0004\b+\u0010,R\u0014\u00100\u001a\u00020\u00068&X\u00a6\u0004\u00a2\u0006\u0006\u001a\u0004\b.\u0010/R\u0014\u00102\u001a\u0002018&X\u00a6\u0004\u00a2\u0006\u0006\u001a\u0004\b2\u00103R\u001a\u00105\u001a\b\u0012\u0004\u0012\u00020\t0*8&X\u00a6\u0004\u00a2\u0006\u0006\u001a\u0004\b4\u0010,R\u001a\u00107\u001a\b\u0012\u0004\u0012\u00020\t0*8&X\u00a6\u0004\u00a2\u0006\u0006\u001a\u0004\b6\u0010,R/\u00109\u001a\u001a\u0012\u0004\u0012\u00020\u0006\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u00020\u001d\u0012\u0004\u0012\u00028\u00010\u0016088\u0006\u00a2\u0006\f\n\u0004\b9\u0010:\u001a\u0004\b;\u0010<R>\u0010>\u001a)\u0012\u0004\u0012\u00020\u0006\u0012\u001f\u0012\u001d\u0012\u0013\u0012\u001101\u00a2\u0006\f\b=\u0012\b\b\u0007\u0012\u0004\b\b(2\u0012\u0004\u0012\u00020\u001d0\u0016088\u0006\u00a2\u0006\f\n\u0004\b>\u0010:\u001a\u0004\b?\u0010<R\u0014\u0010B\u001a\u00020\t8&X\u00a6\u0004\u00a2\u0006\u0006\u001a\u0004\b@\u0010AR\u0014\u0010D\u001a\u00020\t8&X\u00a6\u0004\u00a2\u0006\u0006\u001a\u0004\bC\u0010AR\u001a\u0010F\u001a\b\u0012\u0004\u0012\u00020\t0*8&X\u00a6\u0004\u00a2\u0006\u0006\u001a\u0004\bE\u0010,R/\u0010H\u001a\u001a\u0012\u0004\u0012\u00020\u0006\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u00028\u0000\u0012\u0004\u0012\u00028\u00010G088\u0006\u00a2\u0006\f\n\u0004\bH\u0010:\u001a\u0004\bI\u0010<\u00a8\u0006L"}, d2={"Lcom/cobblemon/mod/common/client/render/models/blockbench/repository/VaryingModelRepository;", "Lnet/minecraft/world/entity/Entity;", "E", "Lcom/cobblemon/mod/common/client/render/models/blockbench/PoseableEntityModel;", "M", "", "Lnet/minecraft/resources/ResourceLocation;", "name", "", "", "aspects", "", "Lcom/cobblemon/mod/common/client/render/ModelLayer;", "getLayers", "(Lnet/minecraft/resources/ResourceLocation;Ljava/util/Set;)Ljava/lang/Iterable;", "getPoser", "(Lnet/minecraft/resources/ResourceLocation;Ljava/util/Set;)Lcom/cobblemon/mod/common/client/render/models/blockbench/PoseableEntityModel;", "", "animationSeconds", "getTexture", "(Lnet/minecraft/resources/ResourceLocation;Ljava/util/Set;F)Lnet/minecraft/resources/ResourceLocation;", "getTextureNoSubstitute", "Lkotlin/Function1;", "Lnet/minecraft/client/model/geom/ModelPart;", "model", "", "inbuilt", "(Ljava/lang/String;Lkotlin/jvm/functions/Function1;)V", "json", "Lcom/cobblemon/mod/common/client/render/models/blockbench/pose/Bone;", "loadJsonPoser", "(Ljava/lang/String;)Lkotlin/jvm/functions/Function1;", "registerInBuiltPosers", "()V", "Lnet/minecraft/server/packs/resources/ResourceManager;", "resourceManager", "registerJsonPosers", "(Lnet/minecraft/server/packs/resources/ResourceManager;)V", "registerModels", "registerPosers", "registerVariations", "reload", "", "getAnimationDirectories", "()Ljava/util/List;", "animationDirectories", "getFallback", "()Lnet/minecraft/resources/ResourceLocation;", "fallback", "", "isForLivingEntityRenderer", "()Z", "getModelDirectories", "modelDirectories", "getPoserDirectories", "poserDirectories", "", "posers", "Ljava/util/Map;", "getPosers", "()Ljava/util/Map;", "Lkotlin/ParameterName;", "texturedModels", "getTexturedModels", "getTitle", "()Ljava/lang/String;", "title", "getType", "type", "getVariationDirectories", "variationDirectories", "Lcom/cobblemon/mod/common/client/render/VaryingRenderableResolver;", "variations", "getVariations", "<init>", "Companion", "common"})
@SourceDebugExtension(value={"SMAP\nVaryingModelRepository.kt\nKotlin\n*S Kotlin\n*F\n+ 1 VaryingModelRepository.kt\ncom/cobblemon/mod/common/client/render/models/blockbench/repository/VaryingModelRepository\n+ 2 _Maps.kt\nkotlin/collections/MapsKt___MapsKt\n+ 3 GsonExtensions.kt\ncom/cobblemon/mod/common/util/GsonExtensionsKt\n+ 4 Maps.kt\nkotlin/collections/MapsKt__MapsKt\n+ 5 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n*L\n1#1,188:1\n215#2,2:189\n215#2:191\n216#2:200\n215#2:210\n125#2:211\n152#2,3:212\n216#2:217\n19#3:192\n361#4,7:193\n1045#5:201\n1360#5:202\n1446#5,5:203\n1855#5,2:208\n1855#5,2:215\n*S KotlinDebug\n*F\n+ 1 VaryingModelRepository.kt\ncom/cobblemon/mod/common/client/render/models/blockbench/repository/VaryingModelRepository\n*L\n62#1:189,2\n81#1:191\n81#1:200\n101#1:210\n103#1:211\n103#1:212,3\n101#1:217\n84#1:192\n85#1:193,7\n91#1:201\n91#1:202\n91#1:203,5\n95#1:208,2\n104#1:215,2\n*E\n"})
public abstract class VaryingModelRepository<E extends Entity, M extends PoseableEntityModel<E>> {
    @NotNull
    public static final Companion Companion;
    @NotNull
    private final Map<ResourceLocation, Function1<Bone, M>> posers = new LinkedHashMap();
    @NotNull
    private final Map<ResourceLocation, VaryingRenderableResolver<E, M>> variations = new LinkedHashMap();
    @NotNull
    private final Map<ResourceLocation, Function1<Boolean, Bone>> texturedModels = new LinkedHashMap();
    @NotNull
    private static Map<String, BiFunction<ResourceLocation, Resource, Tuple<ResourceLocation, Function<Boolean, Bone>>>> MODEL_FACTORIES;

    @NotNull
    public final Map<ResourceLocation, Function1<Bone, M>> getPosers() {
        return this.posers;
    }

    @NotNull
    public final Map<ResourceLocation, VaryingRenderableResolver<E, M>> getVariations() {
        return this.variations;
    }

    @NotNull
    public final Map<ResourceLocation, Function1<Boolean, Bone>> getTexturedModels() {
        return this.texturedModels;
    }

    @NotNull
    public abstract String getTitle();

    @NotNull
    public abstract String getType();

    @NotNull
    public abstract List<String> getVariationDirectories();

    @NotNull
    public abstract List<String> getPoserDirectories();

    @NotNull
    public abstract List<String> getModelDirectories();

    @NotNull
    public abstract List<String> getAnimationDirectories();

    @NotNull
    public abstract ResourceLocation getFallback();

    public abstract boolean isForLivingEntityRenderer();

    @NotNull
    public abstract Function1<Bone, M> loadJsonPoser(@NotNull String var1);

    public final void registerPosers(@NotNull ResourceManager resourceManager) {
        Intrinsics.checkNotNullParameter((Object)resourceManager, (String)"resourceManager");
        this.posers.clear();
        this.registerInBuiltPosers();
        this.registerJsonPosers(resourceManager);
    }

    public abstract void registerInBuiltPosers();

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * WARNING - void declaration
     */
    public void registerJsonPosers(@NotNull ResourceManager resourceManager) {
        Intrinsics.checkNotNullParameter((Object)resourceManager, (String)"resourceManager");
        for (String directory : this.getPoserDirectories()) {
            void $this$forEach$iv;
            Intrinsics.checkNotNullExpressionValue((Object)resourceManager.m_214159_(directory, VaryingModelRepository::registerJsonPosers$lambda$0), (String)"resourceManager\n        \u2026 path.endsWith(\".json\") }");
            boolean $i$f$forEach = false;
            Iterator iterator = $this$forEach$iv.entrySet().iterator();
            while (iterator.hasNext()) {
                Map.Entry element$iv;
                Map.Entry entry = element$iv = iterator.next();
                boolean bl = false;
                ResourceLocation identifier = (ResourceLocation)entry.getKey();
                Resource resource = (Resource)entry.getValue();
                Closeable closeable = resource.m_215507_();
                Throwable throwable = null;
                try {
                    Charset charset;
                    byte[] byArray;
                    InputStream stream = (InputStream)closeable;
                    boolean bl2 = false;
                    Intrinsics.checkNotNullExpressionValue((Object)stream.readAllBytes(), (String)"stream.readAllBytes()");
                    Intrinsics.checkNotNullExpressionValue((Object)StandardCharsets.UTF_8, (String)"UTF_8");
                    String json = new String(byArray, charset);
                    ResourceLocation resolvedIdentifier = new ResourceLocation(identifier.m_135827_(), FilesKt.getNameWithoutExtension((File)new File(identifier.m_135815_())));
                    this.posers.put(resolvedIdentifier, this.loadJsonPoser(json));
                    Unit unit = Unit.INSTANCE;
                }
                catch (Throwable throwable2) {
                    throwable = throwable2;
                    throw throwable2;
                }
                finally {
                    CloseableKt.closeFinally((Closeable)closeable, (Throwable)throwable);
                }
            }
        }
    }

    public final void inbuilt(@NotNull String name, @NotNull Function1<? super ModelPart, ? extends M> model) {
        Intrinsics.checkNotNullParameter((Object)name, (String)"name");
        Intrinsics.checkNotNullParameter(model, (String)"model");
        this.posers.put(MiscUtilsKt.cobblemonResource(name), new Function1<Bone, M>(model){
            final /* synthetic */ Function1<ModelPart, M> $model;
            {
                this.$model = $model;
                super(1);
            }

            @NotNull
            public final M invoke(@NotNull Bone bone) {
                Intrinsics.checkNotNullParameter((Object)bone, (String)"bone");
                return (M)((PoseableEntityModel)this.$model.invoke((Object)((ModelPart)bone)));
            }
        });
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * WARNING - void declaration
     */
    public final void registerVariations(@NotNull ResourceManager resourceManager) {
        Intrinsics.checkNotNullParameter((Object)resourceManager, (String)"resourceManager");
        Map nameToModelVariationSets = new LinkedHashMap();
        for (String string : this.getVariationDirectories()) {
            void $this$forEach$iv;
            Intrinsics.checkNotNullExpressionValue((Object)resourceManager.m_214159_(string, VaryingModelRepository::registerVariations$lambda$3), (String)"resourceManager\n        \u2026 path.endsWith(\".json\") }");
            boolean $i$f$forEach = false;
            Iterator iterator = $this$forEach$iv.entrySet().iterator();
            while (iterator.hasNext()) {
                Map.Entry element$iv;
                Map.Entry entry = element$iv = iterator.next();
                boolean bl = false;
                Resource resource = (Resource)entry.getValue();
                Closeable closeable = resource.m_215507_();
                Object object = null;
                try {
                    Object object2;
                    void $this$getOrPut$iv;
                    Charset charset;
                    byte[] byArray;
                    InputStream stream = (InputStream)closeable;
                    boolean bl2 = false;
                    Intrinsics.checkNotNullExpressionValue((Object)stream.readAllBytes(), (String)"stream.readAllBytes()");
                    Intrinsics.checkNotNullExpressionValue((Object)StandardCharsets.UTF_8, (String)"UTF_8");
                    String json = new String(byArray, charset);
                    Gson gson2 = VaryingRenderableResolver.Companion.getGSON();
                    Intrinsics.checkNotNullExpressionValue((Object)gson2, (String)"VaryingRenderableResolver.GSON");
                    Object $this$fromJson$iv = gson2;
                    boolean $i$f$fromJson = false;
                    ModelVariationSet modelVariationSet = (ModelVariationSet)$this$fromJson$iv.fromJson(json, ModelVariationSet.class);
                    $this$fromJson$iv = nameToModelVariationSets;
                    ResourceLocation key$iv = modelVariationSet.getName();
                    boolean $i$f$getOrPut = false;
                    Object value$iv = $this$getOrPut$iv.get(key$iv);
                    if (value$iv == null) {
                        boolean bl3 = false;
                        List answer$iv = new ArrayList();
                        $this$getOrPut$iv.put(key$iv, answer$iv);
                        object2 = answer$iv;
                    } else {
                        object2 = value$iv;
                    }
                    List list = (List)object2;
                    Intrinsics.checkNotNullExpressionValue((Object)modelVariationSet, (String)"modelVariationSet");
                    list.add(modelVariationSet);
                    stream = Unit.INSTANCE;
                }
                catch (Throwable stream) {
                    object = stream;
                    throw stream;
                }
                finally {
                    CloseableKt.closeFinally((Closeable)closeable, (Throwable)object);
                }
            }
        }
        for (Map.Entry entry : nameToModelVariationSets.entrySet()) {
            void $this$flatMapTo$iv$iv;
            void $this$flatMap$iv;
            ResourceLocation species = (ResourceLocation)entry.getKey();
            List speciesVariationSets = (List)entry.getValue();
            Iterable $this$sortedBy$iv = speciesVariationSets;
            boolean $i$f$sortedBy = false;
            $this$sortedBy$iv = CollectionsKt.sortedWith((Iterable)$this$sortedBy$iv, (Comparator)new Comparator(){

                public final int compare(T a, T b) {
                    ModelVariationSet it = (ModelVariationSet)a;
                    boolean bl = false;
                    Comparable comparable = Integer.valueOf(it.getOrder());
                    it = (ModelVariationSet)b;
                    Comparable comparable2 = comparable;
                    bl = false;
                    return ComparisonsKt.compareValues((Comparable)comparable2, (Comparable)Integer.valueOf(it.getOrder()));
                }
            });
            boolean $i$f$flatMap = false;
            void bl = $this$flatMap$iv;
            Collection destination$iv$iv = new ArrayList();
            boolean $i$f$flatMapTo = false;
            for (Object element$iv$iv : $this$flatMapTo$iv$iv) {
                ModelVariationSet it = (ModelVariationSet)element$iv$iv;
                boolean bl4 = false;
                Iterable list$iv$iv = it.getVariations();
                CollectionsKt.addAll((Collection)destination$iv$iv, (Iterable)list$iv$iv);
            }
            List variations = CollectionsKt.toMutableList((Collection)((List)destination$iv$iv));
            this.variations.put(species, new VaryingRenderableResolver(species, variations));
        }
        Iterable $this$forEach$iv = this.variations.values();
        boolean bl = false;
        for (Object element$iv : $this$forEach$iv) {
            VaryingRenderableResolver it = (VaryingRenderableResolver)element$iv;
            boolean bl2 = false;
            it.initialize(this);
        }
    }

    /*
     * WARNING - void declaration
     */
    public final void registerModels(@NotNull ResourceManager resourceManager) {
        Intrinsics.checkNotNullParameter((Object)resourceManager, (String)"resourceManager");
        int models = 0;
        for (String directory : this.getModelDirectories()) {
            Map<String, BiFunction<ResourceLocation, Resource, Tuple<ResourceLocation, Function<Boolean, Bone>>>> $this$forEach$iv = MODEL_FACTORIES;
            boolean $i$f$forEach = false;
            Iterator<Map.Entry<String, BiFunction<ResourceLocation, Resource, Tuple<ResourceLocation, Function<Boolean, Bone>>>>> iterator = $this$forEach$iv.entrySet().iterator();
            while (iterator.hasNext()) {
                void $this$forEach$iv2;
                Map.Entry entry;
                void $this$mapTo$iv$iv;
                Iterable $this$map$iv;
                Map.Entry<String, BiFunction<ResourceLocation, Resource, Tuple<ResourceLocation, Function<Boolean, Bone>>>> element$iv;
                Map.Entry<String, BiFunction<ResourceLocation, Resource, Tuple<ResourceLocation, Function<Boolean, Bone>>>> entry2 = element$iv = iterator.next();
                boolean bl = false;
                String key = entry2.getKey();
                BiFunction<ResourceLocation, Resource, Tuple<ResourceLocation, Function<Boolean, Bone>>> func = entry2.getValue();
                Intrinsics.checkNotNullExpressionValue((Object)resourceManager.m_214159_(directory, arg_0 -> VaryingModelRepository.registerModels$lambda$13$lambda$10(key, arg_0)), (String)"resourceManager.findReso\u2026h -> path.endsWith(key) }");
                boolean $i$f$map = false;
                Iterator iterator2 = $this$map$iv;
                Collection destination$iv$iv = new ArrayList($this$map$iv.size());
                boolean $i$f$mapTo = false;
                for (Map.Entry item$iv$iv : $this$mapTo$iv$iv.entrySet()) {
                    void it;
                    entry = item$iv$iv;
                    Collection collection = destination$iv$iv;
                    boolean bl2 = false;
                    collection.add(func.apply((ResourceLocation)it.getKey(), (Resource)it.getValue()));
                }
                $this$map$iv = (List)destination$iv$iv;
                boolean $i$f$forEach2 = false;
                for (Object element$iv2 : $this$forEach$iv2) {
                    Tuple it = (Tuple)element$iv2;
                    boolean bl3 = false;
                    Map<ResourceLocation, Function1<Boolean, Bone>> map = this.texturedModels;
                    Intrinsics.checkNotNullExpressionValue((Object)it.m_14418_(), (String)"it.left");
                    Function1<Boolean, Bone> function1 = new Function1<Boolean, Bone>((Tuple<ResourceLocation, Function<Boolean, Bone>>)it){
                        final /* synthetic */ Tuple<ResourceLocation, Function<Boolean, Bone>> $it;
                        {
                            this.$it = $it;
                            super(1);
                        }

                        @NotNull
                        public final Bone invoke(boolean isForLivingEntityRenderer) {
                            R r = ((Function)this.$it.m_14419_()).apply(isForLivingEntityRenderer);
                            Intrinsics.checkNotNullExpressionValue(r, (String)"it.right.apply(isForLivingEntityRenderer)");
                            return (Bone)r;
                        }
                    };
                    map.put((ResourceLocation)entry, function1);
                    ++models;
                }
            }
        }
        Cobblemon.INSTANCE.getLOGGER().info("Loaded " + models + " " + this.getTitle() + " models.");
    }

    public final void reload(@NotNull ResourceManager resourceManager) {
        Intrinsics.checkNotNullParameter((Object)resourceManager, (String)"resourceManager");
        this.variations.clear();
        this.posers.clear();
        Cobblemon.INSTANCE.getLOGGER().info("Loading " + this.getTitle() + " models...");
        this.registerModels(resourceManager);
        this.registerPosers(resourceManager);
        this.registerVariations(resourceManager);
    }

    @NotNull
    public final M getPoser(@NotNull ResourceLocation name, @NotNull Set<String> aspects) {
        Intrinsics.checkNotNullParameter((Object)name, (String)"name");
        Intrinsics.checkNotNullParameter(aspects, (String)"aspects");
        try {
            M poser;
            VaryingRenderableResolver<E, M> varyingRenderableResolver = this.variations.get(name);
            Object object = poser = varyingRenderableResolver != null ? varyingRenderableResolver.getPoser(aspects) : null;
            if (poser != null) {
                return poser;
            }
        }
        catch (IllegalStateException illegalStateException) {
            // empty catch block
        }
        VaryingRenderableResolver<E, M> varyingRenderableResolver = this.variations.get(this.getFallback());
        Intrinsics.checkNotNull(varyingRenderableResolver);
        return varyingRenderableResolver.getPoser(aspects);
    }

    @NotNull
    public final ResourceLocation getTexture(@NotNull ResourceLocation name, @NotNull Set<String> aspects, float animationSeconds) {
        Intrinsics.checkNotNullParameter((Object)name, (String)"name");
        Intrinsics.checkNotNullParameter(aspects, (String)"aspects");
        try {
            ResourceLocation texture;
            VaryingRenderableResolver<E, M> varyingRenderableResolver = this.variations.get(name);
            ResourceLocation resourceLocation = texture = varyingRenderableResolver != null ? varyingRenderableResolver.getTexture(aspects, animationSeconds) : null;
            if (texture != null && ClientDistributionUtilsKt.exists(texture)) {
                return texture;
            }
        }
        catch (IllegalStateException illegalStateException) {
            // empty catch block
        }
        VaryingRenderableResolver<E, M> varyingRenderableResolver = this.variations.get(this.getFallback());
        Intrinsics.checkNotNull(varyingRenderableResolver);
        return varyingRenderableResolver.getTexture(aspects, animationSeconds);
    }

    public static /* synthetic */ ResourceLocation getTexture$default(VaryingModelRepository varyingModelRepository, ResourceLocation resourceLocation, Set set2, float f, int n, Object object) {
        if (object != null) {
            throw new UnsupportedOperationException("Super calls with default arguments not supported in this target, function: getTexture");
        }
        if ((n & 4) != 0) {
            f = 0.0f;
        }
        return varyingModelRepository.getTexture(resourceLocation, set2, f);
    }

    @Nullable
    public final ResourceLocation getTextureNoSubstitute(@NotNull ResourceLocation name, @NotNull Set<String> aspects, float animationSeconds) {
        Intrinsics.checkNotNullParameter((Object)name, (String)"name");
        Intrinsics.checkNotNullParameter(aspects, (String)"aspects");
        try {
            ResourceLocation texture;
            VaryingRenderableResolver<E, M> varyingRenderableResolver = this.variations.get(name);
            ResourceLocation resourceLocation = texture = varyingRenderableResolver != null ? varyingRenderableResolver.getTexture(aspects, animationSeconds) : null;
            if (texture != null && ClientDistributionUtilsKt.exists(texture)) {
                return texture;
            }
        }
        catch (IllegalStateException illegalStateException) {
            // empty catch block
        }
        return null;
    }

    public static /* synthetic */ ResourceLocation getTextureNoSubstitute$default(VaryingModelRepository varyingModelRepository, ResourceLocation resourceLocation, Set set2, float f, int n, Object object) {
        if (object != null) {
            throw new UnsupportedOperationException("Super calls with default arguments not supported in this target, function: getTextureNoSubstitute");
        }
        if ((n & 4) != 0) {
            f = 0.0f;
        }
        return varyingModelRepository.getTextureNoSubstitute(resourceLocation, set2, f);
    }

    @NotNull
    public final Iterable<ModelLayer> getLayers(@NotNull ResourceLocation name, @NotNull Set<String> aspects) {
        Intrinsics.checkNotNullParameter((Object)name, (String)"name");
        Intrinsics.checkNotNullParameter(aspects, (String)"aspects");
        try {
            Iterable<ModelLayer> layers;
            VaryingRenderableResolver<E, M> varyingRenderableResolver = this.variations.get(name);
            Iterable<ModelLayer> iterable = layers = varyingRenderableResolver != null ? varyingRenderableResolver.getLayers(aspects) : null;
            if (layers != null) {
                return layers;
            }
        }
        catch (IllegalStateException illegalStateException) {
            // empty catch block
        }
        VaryingRenderableResolver<E, M> varyingRenderableResolver = this.variations.get(this.getFallback());
        Intrinsics.checkNotNull(varyingRenderableResolver);
        return varyingRenderableResolver.getLayers(aspects);
    }

    private static final boolean registerJsonPosers$lambda$0(ResourceLocation path) {
        Intrinsics.checkNotNullExpressionValue((Object)path, (String)"path");
        return IdentifierExtensionsKt.endsWith(path, ".json");
    }

    private static final boolean registerVariations$lambda$3(ResourceLocation path) {
        Intrinsics.checkNotNullExpressionValue((Object)path, (String)"path");
        return IdentifierExtensionsKt.endsWith(path, ".json");
    }

    private static final boolean registerModels$lambda$13$lambda$10(String $key, ResourceLocation path) {
        Intrinsics.checkNotNullParameter((Object)$key, (String)"$key");
        Intrinsics.checkNotNullExpressionValue((Object)path, (String)"path");
        return IdentifierExtensionsKt.endsWith(path, $key);
    }

    private static final Bone MODEL_FACTORIES$lambda$17$lambda$16$lambda$15$lambda$14(TexturedModel $texturedModel, Boolean it) {
        Intrinsics.checkNotNullParameter((Object)$texturedModel, (String)"$texturedModel");
        Intrinsics.checkNotNullParameter((Object)it, (String)"it");
        return (Bone)$texturedModel.create(it).m_171564_();
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private static final Tuple MODEL_FACTORIES$lambda$17$lambda$16(ResourceLocation identifier, Resource resource) {
        Tuple tuple;
        Intrinsics.checkNotNullParameter((Object)identifier, (String)"identifier");
        Intrinsics.checkNotNullParameter((Object)resource, (String)"resource");
        Closeable closeable = resource.m_215507_();
        Throwable throwable = null;
        try {
            InputStream stream = (InputStream)closeable;
            boolean bl = false;
            byte[] byArray = stream.readAllBytes();
            Intrinsics.checkNotNullExpressionValue((Object)byArray, (String)"stream.readAllBytes()");
            byte[] byArray2 = byArray;
            Charset charset = StandardCharsets.UTF_8;
            Intrinsics.checkNotNullExpressionValue((Object)charset, (String)"UTF_8");
            Charset charset2 = charset;
            String json = new String(byArray2, charset2);
            ResourceLocation resolvedIdentifier = new ResourceLocation(identifier.m_135827_(), FilesKt.getNameWithoutExtension((File)new File(identifier.m_135815_())));
            TexturedModel texturedModel = TexturedModel.Companion.from(json);
            Function<Boolean, Bone> boneCreator = arg_0 -> VaryingModelRepository.MODEL_FACTORIES$lambda$17$lambda$16$lambda$15$lambda$14(texturedModel, arg_0);
            tuple = new Tuple((Object)resolvedIdentifier, boneCreator);
        }
        catch (Throwable throwable2) {
            throwable = throwable2;
            throw throwable2;
        }
        finally {
            CloseableKt.closeFinally((Closeable)closeable, (Throwable)throwable);
        }
        return tuple;
    }

    static {
        Map map;
        Companion = new Companion(null);
        Map it = map = (Map)new LinkedHashMap();
        boolean bl = false;
        it.put(".geo.json", VaryingModelRepository::MODEL_FACTORIES$lambda$17$lambda$16);
        MODEL_FACTORIES = map;
    }

    @Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000<\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u000b\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0010%\n\u0002\b\u0005\b\u0086\u0003\u0018\u00002\u00020\u0001B\t\b\u0002\u00a2\u0006\u0004\b\u0012\u0010\u0013JG\u0010\r\u001a\u00020\f2\u0006\u0010\u0003\u001a\u00020\u000220\u0010\u000b\u001a,\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u0006\u0012\u001c\u0012\u001a\u0012\u0004\u0012\u00020\u0005\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u00020\t\u0012\u0004\u0012\u00020\n0\b0\u00070\u0004\u00a2\u0006\u0004\b\r\u0010\u000eRL\u0010\u0010\u001a8\u0012\u0004\u0012\u00020\u0002\u0012.\u0012,\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u0006\u0012\u001c\u0012\u001a\u0012\u0004\u0012\u00020\u0005\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u00020\t\u0012\u0004\u0012\u00020\n0\b0\u00070\u00040\u000f8\u0002@\u0002X\u0082\u000e\u00a2\u0006\u0006\n\u0004\b\u0010\u0010\u0011\u00a8\u0006\u0014"}, d2={"Lcom/cobblemon/mod/common/client/render/models/blockbench/repository/VaryingModelRepository$Companion;", "", "", "id", "Ljava/util/function/BiFunction;", "Lnet/minecraft/resources/ResourceLocation;", "Lnet/minecraft/server/packs/resources/Resource;", "Lnet/minecraft/util/Tuple;", "Ljava/util/function/Function;", "", "Lcom/cobblemon/mod/common/client/render/models/blockbench/pose/Bone;", "factory", "", "registerFactory", "(Ljava/lang/String;Ljava/util/function/BiFunction;)V", "", "MODEL_FACTORIES", "Ljava/util/Map;", "<init>", "()V", "common"})
    public static final class Companion {
        private Companion() {
        }

        public final void registerFactory(@NotNull String id, @NotNull BiFunction<ResourceLocation, Resource, Tuple<ResourceLocation, Function<Boolean, Bone>>> factory) {
            Intrinsics.checkNotNullParameter((Object)id, (String)"id");
            Intrinsics.checkNotNullParameter(factory, (String)"factory");
            MODEL_FACTORIES.put(id, factory);
        }

        public /* synthetic */ Companion(DefaultConstructorMarker $constructor_marker) {
            this();
        }
    }
}

