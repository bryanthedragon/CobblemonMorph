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
 *  kotlin.collections.CollectionsKt
 *  kotlin.jvm.functions.Function1
 *  kotlin.jvm.internal.Intrinsics
 *  kotlin.jvm.internal.SourceDebugExtension
 *  kotlin.text.StringsKt
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.world.entity.Entity
 *  org.jetbrains.annotations.NotNull
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.repository;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.animation.StatelessAnimation;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.blockentity.BlockEntityModel;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.frame.ModelFrame;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.pokemon.AnimationReferenceFactory;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.pokemon.JsonPokemonPoseableModel;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.pose.Bone;
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
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import kotlin.text.StringsKt;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000J\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\u0011\b\u00c6\u0002\u0018\u00002\u000e\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020\u00030\u0001B\t\b\u0002\u00a2\u0006\u0004\b+\u0010\fJ#\u0010\b\u001a\u000e\u0012\u0004\u0012\u00020\u0007\u0012\u0004\u0012\u00020\u00030\u00062\u0006\u0010\u0005\u001a\u00020\u0004H\u0016\u00a2\u0006\u0004\b\b\u0010\tJ\u000f\u0010\u000b\u001a\u00020\nH\u0016\u00a2\u0006\u0004\b\u000b\u0010\fR \u0010\u000e\u001a\b\u0012\u0004\u0012\u00020\u00040\r8\u0016X\u0096\u0004\u00a2\u0006\f\n\u0004\b\u000e\u0010\u000f\u001a\u0004\b\u0010\u0010\u0011R\u001a\u0010\u0013\u001a\u00020\u00128\u0016X\u0096\u0004\u00a2\u0006\f\n\u0004\b\u0013\u0010\u0014\u001a\u0004\b\u0015\u0010\u0016R\u001c\u0010\u0019\u001a\n \u0018*\u0004\u0018\u00010\u00170\u00178\u0002X\u0082\u0004\u00a2\u0006\u0006\n\u0004\b\u0019\u0010\u001aR\u001a\u0010\u001c\u001a\u00020\u001b8\u0016X\u0096D\u00a2\u0006\f\n\u0004\b\u001c\u0010\u001d\u001a\u0004\b\u001c\u0010\u001eR \u0010\u001f\u001a\b\u0012\u0004\u0012\u00020\u00040\r8\u0016X\u0096\u0004\u00a2\u0006\f\n\u0004\b\u001f\u0010\u000f\u001a\u0004\b \u0010\u0011R \u0010!\u001a\b\u0012\u0004\u0012\u00020\u00040\r8\u0016X\u0096\u0004\u00a2\u0006\f\n\u0004\b!\u0010\u000f\u001a\u0004\b\"\u0010\u0011R\u001a\u0010#\u001a\u00020\u00048\u0016X\u0096D\u00a2\u0006\f\n\u0004\b#\u0010$\u001a\u0004\b%\u0010&R\u001a\u0010'\u001a\u00020\u00048\u0016X\u0096D\u00a2\u0006\f\n\u0004\b'\u0010$\u001a\u0004\b(\u0010&R \u0010)\u001a\b\u0012\u0004\u0012\u00020\u00040\r8\u0016X\u0096\u0004\u00a2\u0006\f\n\u0004\b)\u0010\u000f\u001a\u0004\b*\u0010\u0011\u00a8\u0006,"}, d2={"Lcom/cobblemon/mod/common/client/render/models/blockbench/repository/BlockEntityModelRepository;", "Lcom/cobblemon/mod/common/client/render/models/blockbench/repository/VaryingModelRepository;", "Lnet/minecraft/world/entity/Entity;", "Lcom/cobblemon/mod/common/client/render/models/blockbench/blockentity/BlockEntityModel;", "", "json", "Lkotlin/Function1;", "Lcom/cobblemon/mod/common/client/render/models/blockbench/pose/Bone;", "loadJsonPoser", "(Ljava/lang/String;)Lkotlin/jvm/functions/Function1;", "", "registerInBuiltPosers", "()V", "", "animationDirectories", "Ljava/util/List;", "getAnimationDirectories", "()Ljava/util/List;", "Lnet/minecraft/resources/ResourceLocation;", "fallback", "Lnet/minecraft/resources/ResourceLocation;", "getFallback", "()Lnet/minecraft/resources/ResourceLocation;", "Lcom/google/gson/Gson;", "kotlin.jvm.PlatformType", "gson", "Lcom/google/gson/Gson;", "", "isForLivingEntityRenderer", "Z", "()Z", "modelDirectories", "getModelDirectories", "poserDirectories", "getPoserDirectories", "title", "Ljava/lang/String;", "getTitle", "()Ljava/lang/String;", "type", "getType", "variationDirectories", "getVariationDirectories", "<init>", "common"})
@SourceDebugExtension(value={"SMAP\nBlockEntityModelRepository.kt\nKotlin\n*S Kotlin\n*F\n+ 1 BlockEntityModelRepository.kt\ncom/cobblemon/mod/common/client/render/models/blockbench/repository/BlockEntityModelRepository\n+ 2 GsonExtensions.kt\ncom/cobblemon/mod/common/util/GsonExtensionsKt\n*L\n1#1,63:1\n19#2:64\n*S KotlinDebug\n*F\n+ 1 BlockEntityModelRepository.kt\ncom/cobblemon/mod/common/client/render/models/blockbench/repository/BlockEntityModelRepository\n*L\n37#1:64\n*E\n"})
public final class BlockEntityModelRepository
extends VaryingModelRepository<Entity, BlockEntityModel> {
    @NotNull
    public static final BlockEntityModelRepository INSTANCE = new BlockEntityModelRepository();
    @NotNull
    private static final String title = "Block Entity";
    @NotNull
    private static final String type = "block_entities";
    @NotNull
    private static final List<String> variationDirectories;
    @NotNull
    private static final List<String> poserDirectories;
    @NotNull
    private static final List<String> modelDirectories;
    @NotNull
    private static final List<String> animationDirectories;
    @NotNull
    private static final ResourceLocation fallback;
    private static final boolean isForLivingEntityRenderer;
    private static final Gson gson;

    private BlockEntityModelRepository() {
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
    public Function1<Bone, BlockEntityModel> loadJsonPoser(@NotNull String json) {
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
        return (Function1)new Function1<Bone, BlockEntityModel>(maxScale, yTranslation, animations2){
            final /* synthetic */ float $maxScale;
            final /* synthetic */ float $yTranslation;
            final /* synthetic */ JsonArray $animations;
            {
                this.$maxScale = $maxScale;
                this.$yTranslation = $yTranslation;
                this.$animations = $animations;
                super(1);
            }

            /*
             * WARNING - void declaration
             */
            @NotNull
            public final BlockEntityModel invoke(@NotNull Bone bone) {
                void $this$mapNotNullTo$iv$iv;
                void $this$mapNotNull$iv;
                Intrinsics.checkNotNullParameter((Object)bone, (String)"bone");
                BlockEntityModel model = new BlockEntityModel(bone);
                model.setMaxScale(this.$maxScale);
                model.setYTranslation(this.$yTranslation);
                JsonArray jsonArray = this.$animations;
                Intrinsics.checkNotNullExpressionValue((Object)jsonArray, (String)"animations");
                Iterable iterable = (Iterable)jsonArray;
                BlockEntityModel blockEntityModel = model;
                boolean $i$f$mapNotNull = false;
                void var5_6 = $this$mapNotNull$iv;
                Collection destination$iv$iv = new ArrayList<E>();
                boolean $i$f$mapNotNullTo = false;
                void $this$forEach$iv$iv$iv = $this$mapNotNullTo$iv$iv;
                boolean $i$f$forEach = false;
                Iterator<T> iterator = $this$forEach$iv$iv$iv.iterator();
                while (iterator.hasNext()) {
                    StatelessAnimation<T, ModelFrame> statelessAnimation;
                    T element$iv$iv$iv;
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
                blockEntityModel.setIdleAnimations(thisCollection$iv.toArray(new StatelessAnimation[0]));
                return model;
            }
        };
    }

    @Override
    public void registerInBuiltPosers() {
    }

    static {
        Object[] objectArray = new String[]{"bedrock/" + INSTANCE.getType() + "/variations", "bedrock/" + INSTANCE.getType()};
        variationDirectories = CollectionsKt.listOf((Object[])objectArray);
        poserDirectories = CollectionsKt.listOf((Object)("bedrock/" + INSTANCE.getType() + "/posers"));
        modelDirectories = CollectionsKt.listOf((Object)("bedrock/" + INSTANCE.getType() + "/models"));
        animationDirectories = CollectionsKt.listOf((Object)("bedrock/" + INSTANCE.getType() + "/animations"));
        fallback = MiscUtils.cobblemonResource("substitute");
        gson = new GsonBuilder().create();
    }
}

