/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.gson.Gson
 *  com.google.gson.GsonBuilder
 *  com.google.gson.JsonArray
 *  com.google.gson.JsonElement
 *  com.google.gson.JsonObject
 *  kotlin.Metadata
 *  kotlin.Pair
 *  kotlin.TuplesKt
 *  kotlin.collections.CollectionsKt
 *  kotlin.jvm.functions.Function1
 *  kotlin.jvm.internal.Intrinsics
 *  kotlin.jvm.internal.SourceDebugExtension
 *  kotlin.ranges.IntRange
 *  kotlin.text.StringsKt
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.world.entity.Entity
 *  org.jetbrains.annotations.NotNull
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.repository;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.PoseableEntityState;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.animation.StatefulAnimation;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.animation.StatelessAnimation;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.fossil.FossilModel;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.frame.ModelFrame;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.pokemon.AnimationReferenceFactory;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.pokemon.JsonPokemonPoseableModel;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.pose.Bone;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.quirk.ModelQuirk;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.repository.FossilModelRepository;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.repository.VaryingModelRepository;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.MiscUtils;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.TuplesKt;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import kotlin.ranges.IntRange;
import kotlin.text.StringsKt;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000J\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\u0011\b\u00c6\u0002\u0018\u00002\u000e\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020\u00030\u0001B\t\b\u0002\u00a2\u0006\u0004\b+\u0010\fJ#\u0010\b\u001a\u000e\u0012\u0004\u0012\u00020\u0007\u0012\u0004\u0012\u00020\u00030\u00062\u0006\u0010\u0005\u001a\u00020\u0004H\u0016\u00a2\u0006\u0004\b\b\u0010\tJ\u000f\u0010\u000b\u001a\u00020\nH\u0016\u00a2\u0006\u0004\b\u000b\u0010\fR \u0010\u000e\u001a\b\u0012\u0004\u0012\u00020\u00040\r8\u0016X\u0096\u0004\u00a2\u0006\f\n\u0004\b\u000e\u0010\u000f\u001a\u0004\b\u0010\u0010\u0011R\u001a\u0010\u0013\u001a\u00020\u00128\u0016X\u0096\u0004\u00a2\u0006\f\n\u0004\b\u0013\u0010\u0014\u001a\u0004\b\u0015\u0010\u0016R\u001c\u0010\u0019\u001a\n \u0018*\u0004\u0018\u00010\u00170\u00178\u0002X\u0082\u0004\u00a2\u0006\u0006\n\u0004\b\u0019\u0010\u001aR\u001a\u0010\u001c\u001a\u00020\u001b8\u0016X\u0096D\u00a2\u0006\f\n\u0004\b\u001c\u0010\u001d\u001a\u0004\b\u001c\u0010\u001eR \u0010\u001f\u001a\b\u0012\u0004\u0012\u00020\u00040\r8\u0016X\u0096\u0004\u00a2\u0006\f\n\u0004\b\u001f\u0010\u000f\u001a\u0004\b \u0010\u0011R \u0010!\u001a\b\u0012\u0004\u0012\u00020\u00040\r8\u0016X\u0096\u0004\u00a2\u0006\f\n\u0004\b!\u0010\u000f\u001a\u0004\b\"\u0010\u0011R\u001a\u0010#\u001a\u00020\u00048\u0016X\u0096D\u00a2\u0006\f\n\u0004\b#\u0010$\u001a\u0004\b%\u0010&R\u001a\u0010'\u001a\u00020\u00048\u0016X\u0096D\u00a2\u0006\f\n\u0004\b'\u0010$\u001a\u0004\b(\u0010&R \u0010)\u001a\b\u0012\u0004\u0012\u00020\u00040\r8\u0016X\u0096\u0004\u00a2\u0006\f\n\u0004\b)\u0010\u000f\u001a\u0004\b*\u0010\u0011\u00a8\u0006,"}, d2={"Lcom/cobblemon/mod/common/client/render/models/blockbench/repository/FossilModelRepository;", "Lcom/cobblemon/mod/common/client/render/models/blockbench/repository/VaryingModelRepository;", "Lnet/minecraft/world/entity/Entity;", "Lcom/cobblemon/mod/common/client/render/models/blockbench/fossil/FossilModel;", "", "json", "Lkotlin/Function1;", "Lcom/cobblemon/mod/common/client/render/models/blockbench/pose/Bone;", "loadJsonPoser", "(Ljava/lang/String;)Lkotlin/jvm/functions/Function1;", "", "registerInBuiltPosers", "()V", "", "animationDirectories", "Ljava/util/List;", "getAnimationDirectories", "()Ljava/util/List;", "Lnet/minecraft/resources/ResourceLocation;", "fallback", "Lnet/minecraft/resources/ResourceLocation;", "getFallback", "()Lnet/minecraft/resources/ResourceLocation;", "Lcom/google/gson/Gson;", "kotlin.jvm.PlatformType", "gson", "Lcom/google/gson/Gson;", "", "isForLivingEntityRenderer", "Z", "()Z", "modelDirectories", "getModelDirectories", "poserDirectories", "getPoserDirectories", "title", "Ljava/lang/String;", "getTitle", "()Ljava/lang/String;", "type", "getType", "variationDirectories", "getVariationDirectories", "<init>", "common"})
@SourceDebugExtension(value={"SMAP\nFossilModelRepository.kt\nKotlin\n*S Kotlin\n*F\n+ 1 FossilModelRepository.kt\ncom/cobblemon/mod/common/client/render/models/blockbench/repository/FossilModelRepository\n+ 2 GsonExtensions.kt\ncom/cobblemon/mod/common/util/GsonExtensionsKt\n*L\n1#1,96:1\n19#2:97\n*S KotlinDebug\n*F\n+ 1 FossilModelRepository.kt\ncom/cobblemon/mod/common/client/render/models/blockbench/repository/FossilModelRepository\n*L\n38#1:97\n*E\n"})
public final class FossilModelRepository
extends VaryingModelRepository<Entity, FossilModel> {
    @NotNull
    public static final FossilModelRepository INSTANCE = new FossilModelRepository();
    @NotNull
    private static final String title = "Fossil Pok\u00e9mon";
    @NotNull
    private static final String type = "fossils";
    @NotNull
    private static final List<String> variationDirectories = CollectionsKt.listOf((Object)("bedrock/" + INSTANCE.getType() + "/variations"));
    @NotNull
    private static final List<String> poserDirectories = CollectionsKt.listOf((Object)("bedrock/" + INSTANCE.getType() + "/posers"));
    @NotNull
    private static final List<String> modelDirectories = CollectionsKt.listOf((Object)("bedrock/" + INSTANCE.getType() + "/models"));
    @NotNull
    private static final List<String> animationDirectories = CollectionsKt.listOf((Object)("bedrock/" + INSTANCE.getType() + "/animations"));
    @NotNull
    private static final ResourceLocation fallback = MiscUtils.cobblemonResource("substitute");
    private static final boolean isForLivingEntityRenderer;
    private static final Gson gson;

    private FossilModelRepository() {
    }

    @Override
    @NotNull
    public String getTitle() {
        return title;
    }

    @Override
    @NotNull
    public String getType() {
        return type;
    }

    @Override
    @NotNull
    public List<String> getVariationDirectories() {
        return variationDirectories;
    }

    @Override
    @NotNull
    public List<String> getPoserDirectories() {
        return poserDirectories;
    }

    @Override
    @NotNull
    public List<String> getModelDirectories() {
        return modelDirectories;
    }

    @Override
    @NotNull
    public List<String> getAnimationDirectories() {
        return animationDirectories;
    }

    @Override
    @NotNull
    public ResourceLocation getFallback() {
        return fallback;
    }

    @Override
    public boolean isForLivingEntityRenderer() {
        return isForLivingEntityRenderer;
    }

    @Override
    @NotNull
    public Function1<Bone, FossilModel> loadJsonPoser(@NotNull String json) {
        Intrinsics.checkNotNullParameter((Object)json, (String)"json");
        Gson gson2 = gson;
        Intrinsics.checkNotNullExpressionValue((Object)gson2, (String)"gson");
        Gson $this$fromJson$iv = gson2;
        boolean $i$f$fromJson = false;
        JsonObject jsonObject = (JsonObject)$this$fromJson$iv.fromJson(json, JsonObject.class);
        JsonArray animations2 = jsonObject.getAsJsonArray("animations");
        JsonElement jsonElement = jsonObject.get("maxScale");
        float maxScale = jsonElement != null ? jsonElement.getAsFloat() : 1.0f;
        JsonElement jsonElement2 = jsonObject.get("yTranslation");
        float yTranslation = jsonElement2 != null ? jsonElement2.getAsFloat() : 0.0f;
        JsonElement jsonElement3 = jsonObject.get("yGrowthPoint");
        float yGrowthPoint = jsonElement3 != null ? jsonElement3.getAsFloat() : 0.0f;
        return (Function1)new Function1<Bone, FossilModel>(maxScale, yTranslation, yGrowthPoint, animations2, jsonObject){
            final /* synthetic */ float $maxScale;
            final /* synthetic */ float $yTranslation;
            final /* synthetic */ float $yGrowthPoint;
            final /* synthetic */ JsonArray $animations;
            final /* synthetic */ JsonObject $jsonObject;
            {
                this.$maxScale = $maxScale;
                this.$yTranslation = $yTranslation;
                this.$yGrowthPoint = $yGrowthPoint;
                this.$animations = $animations;
                this.$jsonObject = $jsonObject;
                super(1);
            }

            /*
             * WARNING - void declaration
             */
            @NotNull
            public final FossilModel invoke(@NotNull Bone bone) {
                void $this$mapTo$iv$iv;
                Object element$iv$iv$iv;
                void $this$mapNotNullTo$iv$iv;
                void $this$mapNotNull$iv;
                Intrinsics.checkNotNullParameter((Object)bone, (String)"bone");
                FossilModel model = new FossilModel(bone);
                model.setMaxScale(this.$maxScale);
                model.setYTranslation(this.$yTranslation);
                model.setYGrowthPoint(this.$yGrowthPoint);
                JsonArray jsonArray = this.$animations;
                Intrinsics.checkNotNullExpressionValue((Object)jsonArray, (String)"animations");
                Iterable iterable = (Iterable)jsonArray;
                Object object = model;
                boolean $i$f$mapNotNull = false;
                void var5_7 = $this$mapNotNull$iv;
                Iterable<StatelessAnimation<T, ModelFrame>> destination$iv$iv = new ArrayList<E>();
                boolean $i$f$mapNotNullTo = false;
                void $this$forEach$iv$iv$iv = $this$mapNotNullTo$iv$iv;
                boolean $i$f$forEach = false;
                Iterator<T> iterator = $this$forEach$iv$iv$iv.iterator();
                while (iterator.hasNext()) {
                    StatelessAnimation<T, ModelFrame> statelessAnimation;
                    T element$iv$iv = element$iv$iv$iv = iterator.next();
                    boolean bl = false;
                    JsonElement it = (JsonElement)element$iv$iv;
                    boolean bl2 = false;
                    String animString = it.getAsString();
                    Intrinsics.checkNotNullExpressionValue((Object)animString, (String)"animString");
                    String anim = StringsKt.substringBefore$default((String)animString, (String)"(", null, (int)2, null);
                    if (JsonPokemonPoseableModel.Companion.getANIMATION_FACTORIES().containsKey(anim)) {
                        AnimationReferenceFactory animationReferenceFactory = JsonPokemonPoseableModel.Companion.getANIMATION_FACTORIES().get(anim);
                        Intrinsics.checkNotNull((Object)animationReferenceFactory);
                        statelessAnimation = animationReferenceFactory.stateless(model, animString);
                    } else {
                        statelessAnimation = null;
                    }
                    if (statelessAnimation == null) continue;
                    StatelessAnimation<T, ModelFrame> it$iv$iv = statelessAnimation;
                    boolean bl3 = false;
                    destination$iv$iv.add(it$iv$iv);
                }
                Collection $this$toTypedArray$iv = (List)destination$iv$iv;
                boolean $i$f$toTypedArray = false;
                Collection thisCollection$iv = $this$toTypedArray$iv;
                ((FossilModel)object).setTankAnimations(thisCollection$iv.toArray(new StatelessAnimation[0]));
                JsonElement jsonElement = this.$jsonObject.get("quirks");
                Object object2 = jsonElement != null ? jsonElement.getAsJsonArray() : null;
                if (object2 == null) {
                    object2 = new JsonArray();
                }
                Iterable $this$map$iv = (Iterable)object2;
                boolean $i$f$map = false;
                destination$iv$iv = $this$map$iv;
                Collection destination$iv$iv2 = new ArrayList<E>(CollectionsKt.collectionSizeOrDefault((Iterable)$this$map$iv, (int)10));
                boolean $i$f$mapTo = false;
                for (T item$iv$iv : $this$mapTo$iv$iv) {
                    void json;
                    element$iv$iv$iv = (JsonElement)item$iv$iv;
                    object = destination$iv$iv2;
                    boolean bl = false;
                    Intrinsics.checkNotNull((Object)json, (String)"null cannot be cast to non-null type com.google.gson.JsonObject");
                    JsonObject cfr_ignored_0 = (JsonObject)json;
                    Function1 quirkAnimations2 = (Function1)new Function1<PoseableEntityState<Entity>, List<? extends StatefulAnimation<Entity, ModelFrame>>>((JsonElement)json, model){
                        final /* synthetic */ JsonElement $json;
                        final /* synthetic */ FossilModel $model;
                        {
                            this.$json = $json;
                            this.$model = $model;
                            super(1);
                        }

                        /*
                         * WARNING - void declaration
                         */
                        @NotNull
                        public final List<StatefulAnimation<Entity, ModelFrame>> invoke(@NotNull PoseableEntityState<Entity> poseableEntityState) {
                            void $this$mapNotNullTo$iv$iv;
                            void $this$mapNotNull$iv;
                            Intrinsics.checkNotNullParameter(poseableEntityState, (String)"<anonymous parameter 0>");
                            JsonElement jsonElement = ((JsonObject)this.$json).get("animations");
                            Object object = jsonElement != null ? jsonElement.getAsJsonArray() : null;
                            if (object == null) {
                                object = new JsonArray();
                            }
                            Iterable iterable = (Iterable)object;
                            FossilModel fossilModel = this.$model;
                            boolean $i$f$mapNotNull = false;
                            void var5_5 = $this$mapNotNull$iv;
                            Collection destination$iv$iv = new ArrayList<E>();
                            boolean $i$f$mapNotNullTo = false;
                            void $this$forEach$iv$iv$iv = $this$mapNotNullTo$iv$iv;
                            boolean $i$f$forEach = false;
                            Iterator<T> iterator = $this$forEach$iv$iv$iv.iterator();
                            while (iterator.hasNext()) {
                                StatefulAnimation<T, ModelFrame> it$iv$iv;
                                StatefulAnimation<T, ModelFrame> animation;
                                T element$iv$iv$iv;
                                T element$iv$iv = element$iv$iv$iv = iterator.next();
                                boolean bl = false;
                                JsonElement animJson = (JsonElement)element$iv$iv;
                                boolean bl2 = false;
                                String animString = animJson.getAsString();
                                Intrinsics.checkNotNullExpressionValue((Object)animString, (String)"animString");
                                String anim = StringsKt.substringBefore$default((String)animString, (String)"(", null, (int)2, null);
                                if (JsonPokemonPoseableModel.Companion.getANIMATION_FACTORIES().containsKey(anim)) {
                                    AnimationReferenceFactory animationReferenceFactory = JsonPokemonPoseableModel.Companion.getANIMATION_FACTORIES().get(anim);
                                    v3 = animationReferenceFactory != null ? animationReferenceFactory.stateful(fossilModel, animString) : null;
                                } else {
                                    v3 = animation = null;
                                }
                                if (animation == null) continue;
                                boolean bl3 = false;
                                destination$iv$iv.add(it$iv$iv);
                            }
                            return (List)destination$iv$iv;
                        }
                    };
                    JsonElement jsonElement2 = ((JsonObject)json).get("loopTimes");
                    int loopTimes = jsonElement2 != null ? jsonElement2.getAsInt() : 1;
                    JsonElement jsonElement3 = ((JsonObject)json).get("minSeconds");
                    float minSeconds = jsonElement3 != null ? jsonElement3.getAsFloat() : 8.0f;
                    JsonElement jsonElement4 = ((JsonObject)json).get("maxSeconds");
                    float maxSeconds = jsonElement4 != null ? jsonElement4.getAsFloat() : 30.0f;
                    Pair pair = TuplesKt.to((Object)Float.valueOf(minSeconds), (Object)Float.valueOf(maxSeconds));
                    IntRange intRange = new IntRange(1, loopTimes);
                    object.add(model.quirkMultiple((Pair<Float, Float>)pair, intRange, loadJsonPoser.tankQuirks.1.1.INSTANCE, quirkAnimations2));
                }
                List tankQuirks2 = (List)destination$iv$iv2;
                Collection $this$toTypedArray$iv2 = tankQuirks2;
                boolean $i$f$toTypedArray2 = false;
                Collection thisCollection$iv2 = $this$toTypedArray$iv2;
                model.setTankQuirks(thisCollection$iv2.toArray(new ModelQuirk[0]));
                return model;
            }
        };
    }

    @Override
    public void registerInBuiltPosers() {
    }

    static {
        gson = new GsonBuilder().create();
    }
}

