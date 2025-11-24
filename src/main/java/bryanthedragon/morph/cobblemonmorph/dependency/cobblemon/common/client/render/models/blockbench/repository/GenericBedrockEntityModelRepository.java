/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  kotlin.Metadata
 *  kotlin.collections.CollectionsKt
 *  kotlin.jvm.functions.Function1
 *  kotlin.jvm.internal.Intrinsics
 *  net.minecraft.resources.ResourceLocation
 *  org.jetbrains.annotations.NotNull
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.repository;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.PoseableEntityModel;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.generic.JsonGenericPoseableModel;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.pose.Bone;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.repository.VaryingModelRepository;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.entity.generic.GenericBedrockEntity;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.MiscUtilsKt;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000B\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u000b\n\u0002\b\u0011\b\u00c6\u0002\u0018\u00002\u0014\u0012\u0004\u0012\u00020\u0002\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00020\u00030\u0001B\t\b\u0002\u00a2\u0006\u0004\b'\u0010\fJ)\u0010\b\u001a\u0014\u0012\u0004\u0012\u00020\u0007\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00020\u00030\u00062\u0006\u0010\u0005\u001a\u00020\u0004H\u0016\u00a2\u0006\u0004\b\b\u0010\tJ\u000f\u0010\u000b\u001a\u00020\nH\u0016\u00a2\u0006\u0004\b\u000b\u0010\fR \u0010\u000e\u001a\b\u0012\u0004\u0012\u00020\u00040\r8\u0016X\u0096\u0004\u00a2\u0006\f\n\u0004\b\u000e\u0010\u000f\u001a\u0004\b\u0010\u0010\u0011R\u001a\u0010\u0013\u001a\u00020\u00128\u0016X\u0096\u0004\u00a2\u0006\f\n\u0004\b\u0013\u0010\u0014\u001a\u0004\b\u0015\u0010\u0016R\u001a\u0010\u0018\u001a\u00020\u00178\u0016X\u0096D\u00a2\u0006\f\n\u0004\b\u0018\u0010\u0019\u001a\u0004\b\u0018\u0010\u001aR \u0010\u001b\u001a\b\u0012\u0004\u0012\u00020\u00040\r8\u0016X\u0096\u0004\u00a2\u0006\f\n\u0004\b\u001b\u0010\u000f\u001a\u0004\b\u001c\u0010\u0011R \u0010\u001d\u001a\b\u0012\u0004\u0012\u00020\u00040\r8\u0016X\u0096\u0004\u00a2\u0006\f\n\u0004\b\u001d\u0010\u000f\u001a\u0004\b\u001e\u0010\u0011R\u001a\u0010\u001f\u001a\u00020\u00048\u0016X\u0096D\u00a2\u0006\f\n\u0004\b\u001f\u0010 \u001a\u0004\b!\u0010\"R\u001a\u0010#\u001a\u00020\u00048\u0016X\u0096D\u00a2\u0006\f\n\u0004\b#\u0010 \u001a\u0004\b$\u0010\"R \u0010%\u001a\b\u0012\u0004\u0012\u00020\u00040\r8\u0016X\u0096\u0004\u00a2\u0006\f\n\u0004\b%\u0010\u000f\u001a\u0004\b&\u0010\u0011\u00a8\u0006("}, d2={"Lcom/cobblemon/mod/common/client/render/models/blockbench/repository/GenericBedrockEntityModelRepository;", "Lcom/cobblemon/mod/common/client/render/models/blockbench/repository/VaryingModelRepository;", "Lcom/cobblemon/mod/common/entity/generic/GenericBedrockEntity;", "Lcom/cobblemon/mod/common/client/render/models/blockbench/PoseableEntityModel;", "", "json", "Lkotlin/Function1;", "Lcom/cobblemon/mod/common/client/render/models/blockbench/pose/Bone;", "loadJsonPoser", "(Ljava/lang/String;)Lkotlin/jvm/functions/Function1;", "", "registerInBuiltPosers", "()V", "", "animationDirectories", "Ljava/util/List;", "getAnimationDirectories", "()Ljava/util/List;", "Lnet/minecraft/resources/ResourceLocation;", "fallback", "Lnet/minecraft/resources/ResourceLocation;", "getFallback", "()Lnet/minecraft/resources/ResourceLocation;", "", "isForLivingEntityRenderer", "Z", "()Z", "modelDirectories", "getModelDirectories", "poserDirectories", "getPoserDirectories", "title", "Ljava/lang/String;", "getTitle", "()Ljava/lang/String;", "type", "getType", "variationDirectories", "getVariationDirectories", "<init>", "common"})
public final class GenericBedrockEntityModelRepository
extends VaryingModelRepository<GenericBedrockEntity, PoseableEntityModel<GenericBedrockEntity>> {
    @NotNull
    public static final GenericBedrockEntityModelRepository INSTANCE = new GenericBedrockEntityModelRepository();
    @NotNull
    private static final String title = "Generic";
    @NotNull
    private static final String type = "generic";
    @NotNull
    private static final List<String> variationDirectories = CollectionsKt.listOf((Object)("bedrock/" + INSTANCE.getType() + "/variations"));
    @NotNull
    private static final List<String> poserDirectories = CollectionsKt.listOf((Object)("bedrock/" + INSTANCE.getType() + "/posers"));
    @NotNull
    private static final List<String> modelDirectories = CollectionsKt.listOf((Object)("bedrock/" + INSTANCE.getType() + "/models"));
    @NotNull
    private static final List<String> animationDirectories = CollectionsKt.listOf((Object)("bedrock/" + INSTANCE.getType() + "/animations"));
    private static final boolean isForLivingEntityRenderer;
    @NotNull
    private static final ResourceLocation fallback;

    private GenericBedrockEntityModelRepository() {
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
    public boolean isForLivingEntityRenderer() {
        return isForLivingEntityRenderer;
    }

    @Override
    @NotNull
    public ResourceLocation getFallback() {
        return fallback;
    }

    @Override
    public void registerInBuiltPosers() {
    }

    @Override
    @NotNull
    public Function1<Bone, PoseableEntityModel<GenericBedrockEntity>> loadJsonPoser(@NotNull String json) {
        Intrinsics.checkNotNullParameter((Object)json, (String)"json");
        return (Function1)new Function1<Bone, JsonGenericPoseableModel>(json){
            final /* synthetic */ String $json;
            {
                this.$json = $json;
                super(1);
            }

            public final JsonGenericPoseableModel invoke(@NotNull Bone it) {
                Intrinsics.checkNotNullParameter((Object)it, (String)"it");
                JsonGenericPoseableModel.JsonGenericPoseableModelAdapter.INSTANCE.setModelPart(it);
                Object object = JsonGenericPoseableModel.Companion.getGson().fromJson(this.$json, JsonGenericPoseableModel.class);
                Intrinsics.checkNotNullExpressionValue((Object)object, (String)"JsonGenericPoseableModel\u2026oseableModel::class.java)");
                return (JsonGenericPoseableModel)object;
            }
        };
    }

    static {
        fallback = MiscUtilsKt.cobblemonResource("substitute");
    }
}

