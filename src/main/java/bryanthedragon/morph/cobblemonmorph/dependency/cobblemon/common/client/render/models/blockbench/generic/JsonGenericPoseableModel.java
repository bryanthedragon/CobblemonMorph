/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.gson.ExclusionStrategy
 *  com.google.gson.Gson
 *  com.google.gson.GsonBuilder
 *  com.google.gson.InstanceCreator
 *  com.google.gson.reflect.TypeToken
 *  kotlin.Metadata
 *  kotlin.jvm.internal.DefaultConstructorMarker
 *  kotlin.jvm.internal.Intrinsics
 *  net.minecraft.world.phys.Vec3
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.generic;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.entity.EntitySideDelegate;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.entity.GenericBedrockClientDelegate;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.JsonPoseableEntityModel;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.animation.StatefulAnimation;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.frame.ModelFrame;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.generic.JsonGenericPoseableModel;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.pose.Bone;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.pose.Pose;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.entity.generic.GenericBedrockEntity;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.adapters.Vec3dAdapter;
import com.google.gson.ExclusionStrategy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.InstanceCreator;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.util.function.Supplier;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000&\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\t\u0018\u0000 \u00122\b\u0012\u0004\u0012\u00020\u00020\u0001:\u0002\u0012\u0013B\u0019\u0012\u0006\u0010\f\u001a\u00020\u000b\u0012\b\b\u0002\u0010\b\u001a\u00020\u0007\u00a2\u0006\u0004\b\u0010\u0010\u0011J\u0017\u0010\u0005\u001a\u00020\u00042\u0006\u0010\u0003\u001a\u00020\u0002H\u0016\u00a2\u0006\u0004\b\u0005\u0010\u0006R\u001a\u0010\b\u001a\u00020\u00078\u0016X\u0096\u0004\u00a2\u0006\f\n\u0004\b\b\u0010\t\u001a\u0004\b\b\u0010\nR\u001a\u0010\f\u001a\u00020\u000b8\u0016X\u0096\u0004\u00a2\u0006\f\n\u0004\b\f\u0010\r\u001a\u0004\b\u000e\u0010\u000f\u00a8\u0006\u0014"}, d2={"Lcom/cobblemon/mod/common/client/render/models/blockbench/generic/JsonGenericPoseableModel;", "Lcom/cobblemon/mod/common/client/render/models/blockbench/JsonPoseableEntityModel;", "Lcom/cobblemon/mod/common/entity/generic/GenericBedrockEntity;", "entity", "Lcom/cobblemon/mod/common/client/entity/GenericBedrockClientDelegate;", "getState", "(Lcom/cobblemon/mod/common/entity/generic/GenericBedrockEntity;)Lcom/cobblemon/mod/common/client/entity/GenericBedrockClientDelegate;", "", "isForLivingEntityRenderer", "Z", "()Z", "Lcom/cobblemon/mod/common/client/render/models/blockbench/pose/Bone;", "rootPart", "Lcom/cobblemon/mod/common/client/render/models/blockbench/pose/Bone;", "getRootPart", "()Lcom/cobblemon/mod/common/client/render/models/blockbench/pose/Bone;", "<init>", "(Lcom/cobblemon/mod/common/client/render/models/blockbench/pose/Bone;Z)V", "Companion", "JsonGenericPoseableModelAdapter", "common"})
public final class JsonGenericPoseableModel
extends JsonPoseableEntityModel<GenericBedrockEntity> {
    @NotNull
    public static final Companion Companion = new Companion(null);
    @NotNull
    private final Bone rootPart;
    private final boolean isForLivingEntityRenderer;
    @Nullable
    private static JsonGenericPoseableModel model;
    private static final Gson gson;

    public JsonGenericPoseableModel(@NotNull Bone rootPart, boolean isForLivingEntityRenderer) {
        Intrinsics.checkNotNullParameter((Object)rootPart, (String)"rootPart");
        super(rootPart);
        this.rootPart = rootPart;
        this.isForLivingEntityRenderer = isForLivingEntityRenderer;
    }

    public /* synthetic */ JsonGenericPoseableModel(Bone bone, boolean bl, int n, DefaultConstructorMarker defaultConstructorMarker) {
        if ((n & 2) != 0) {
            bl = false;
        }
        this(bone, bl);
    }

    @Override
    @NotNull
    public Bone getRootPart() {
        return this.rootPart;
    }

    @Override
    public boolean isForLivingEntityRenderer() {
        return this.isForLivingEntityRenderer;
    }

    @NotNull
    public GenericBedrockClientDelegate getState(@NotNull GenericBedrockEntity entity2) {
        Intrinsics.checkNotNullParameter((Object)entity2, (String)"entity");
        EntitySideDelegate<GenericBedrockEntity> entitySideDelegate = entity2.getDelegate();
        Intrinsics.checkNotNull(entitySideDelegate, (String)"null cannot be cast to non-null type bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.entity.GenericBedrockClientDelegate");
        return (GenericBedrockClientDelegate)entitySideDelegate;
    }

    static {
        Object[] objectArray = new ExclusionStrategy[]{JsonPoseableEntityModel.JsonModelExclusion.INSTANCE};
        GsonBuilder gsonBuilder = new GsonBuilder().setPrettyPrinting().disableHtmlEscaping().registerTypeAdapter((Type)((Object)Vec3.class), (Object)Vec3dAdapter.INSTANCE).setExclusionStrategies(objectArray);
        objectArray = new Type[1];
        Type[] typeArray = new Type[]{GenericBedrockEntity.class, ModelFrame.class};
        objectArray[0] = TypeToken.getParameterized((Type)((Type)((Object)StatefulAnimation.class)), (Type[])typeArray).getType();
        gson = gsonBuilder.registerTypeAdapter(TypeToken.getParameterized((Type)((Type)((Object)Supplier.class)), (Type[])objectArray).getType(), new JsonPoseableEntityModel.StatefulAnimationAdapter(Companion.gson.1.INSTANCE)).registerTypeAdapter((Type)((Object)Pose.class), new JsonPoseableEntityModel.PoseAdapter(Companion.gson.2.INSTANCE)).registerTypeAdapter((Type)((Object)JsonGenericPoseableModel.class), (Object)JsonGenericPoseableModelAdapter.INSTANCE).create();
    }

    @Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\t\b\u0086\u0003\u0018\u00002\u00020\u0001B\t\b\u0002\u00a2\u0006\u0004\b\u000f\u0010\u0010R\u001f\u0010\u0004\u001a\n \u0003*\u0004\u0018\u00010\u00020\u00028\u0006\u00a2\u0006\f\n\u0004\b\u0004\u0010\u0005\u001a\u0004\b\u0006\u0010\u0007R$\u0010\t\u001a\u0004\u0018\u00010\b8\u0006@\u0006X\u0086\u000e\u00a2\u0006\u0012\n\u0004\b\t\u0010\n\u001a\u0004\b\u000b\u0010\f\"\u0004\b\r\u0010\u000e\u00a8\u0006\u0011"}, d2={"Lcom/cobblemon/mod/common/client/render/models/blockbench/generic/JsonGenericPoseableModel$Companion;", "", "Lcom/google/gson/Gson;", "kotlin.jvm.PlatformType", "gson", "Lcom/google/gson/Gson;", "getGson", "()Lcom/google/gson/Gson;", "Lcom/cobblemon/mod/common/client/render/models/blockbench/generic/JsonGenericPoseableModel;", "model", "Lcom/cobblemon/mod/common/client/render/models/blockbench/generic/JsonGenericPoseableModel;", "getModel", "()Lcom/cobblemon/mod/common/client/render/models/blockbench/generic/JsonGenericPoseableModel;", "setModel", "(Lcom/cobblemon/mod/common/client/render/models/blockbench/generic/JsonGenericPoseableModel;)V", "<init>", "()V", "common"})
    public static final class Companion {
        private Companion() {
        }

        @Nullable
        public final JsonGenericPoseableModel getModel() {
            return model;
        }

        public final void setModel(@Nullable JsonGenericPoseableModel jsonGenericPoseableModel) {
            model = jsonGenericPoseableModel;
        }

        public final Gson getGson() {
            return gson;
        }

        public /* synthetic */ Companion(DefaultConstructorMarker $constructor_marker) {
            this();
        }
    }

    @Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000\u001c\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\t\n\u0002\u0018\u0002\n\u0002\b\t\b\u00c6\u0002\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001B\t\b\u0002\u00a2\u0006\u0004\b\u0014\u0010\u0015J\u0017\u0010\u0005\u001a\u00020\u00022\u0006\u0010\u0004\u001a\u00020\u0003H\u0016\u00a2\u0006\u0004\b\u0005\u0010\u0006R$\u0010\u0007\u001a\u0004\u0018\u00010\u00028\u0006@\u0006X\u0086\u000e\u00a2\u0006\u0012\n\u0004\b\u0007\u0010\b\u001a\u0004\b\t\u0010\n\"\u0004\b\u000b\u0010\fR$\u0010\u000e\u001a\u0004\u0018\u00010\r8\u0006@\u0006X\u0086\u000e\u00a2\u0006\u0012\n\u0004\b\u000e\u0010\u000f\u001a\u0004\b\u0010\u0010\u0011\"\u0004\b\u0012\u0010\u0013\u00a8\u0006\u0016"}, d2={"Lcom/cobblemon/mod/common/client/render/models/blockbench/generic/JsonGenericPoseableModel$JsonGenericPoseableModelAdapter;", "Lcom/google/gson/InstanceCreator;", "Lcom/cobblemon/mod/common/client/render/models/blockbench/generic/JsonGenericPoseableModel;", "Ljava/lang/reflect/Type;", "type", "createInstance", "(Ljava/lang/reflect/Type;)Lcom/cobblemon/mod/common/client/render/models/blockbench/generic/JsonGenericPoseableModel;", "model", "Lcom/cobblemon/mod/common/client/render/models/blockbench/generic/JsonGenericPoseableModel;", "getModel", "()Lcom/cobblemon/mod/common/client/render/models/blockbench/generic/JsonGenericPoseableModel;", "setModel", "(Lcom/cobblemon/mod/common/client/render/models/blockbench/generic/JsonGenericPoseableModel;)V", "Lcom/cobblemon/mod/common/client/render/models/blockbench/pose/Bone;", "modelPart", "Lcom/cobblemon/mod/common/client/render/models/blockbench/pose/Bone;", "getModelPart", "()Lcom/cobblemon/mod/common/client/render/models/blockbench/pose/Bone;", "setModelPart", "(Lcom/cobblemon/mod/common/client/render/models/blockbench/pose/Bone;)V", "<init>", "()V", "common"})
    public static final class JsonGenericPoseableModelAdapter
    implements InstanceCreator<JsonGenericPoseableModel> {
        @NotNull
        public static final JsonGenericPoseableModelAdapter INSTANCE = new JsonGenericPoseableModelAdapter();
        @Nullable
        private static Bone modelPart;
        @Nullable
        private static JsonGenericPoseableModel model;

        private JsonGenericPoseableModelAdapter() {
        }

        @Nullable
        public final Bone getModelPart() {
            return modelPart;
        }

        public final void setModelPart(@Nullable Bone bone) {
            modelPart = bone;
        }

        @Nullable
        public final JsonGenericPoseableModel getModel() {
            return model;
        }

        public final void setModel(@Nullable JsonGenericPoseableModel jsonGenericPoseableModel) {
            model = jsonGenericPoseableModel;
        }

        @NotNull
        public JsonGenericPoseableModel createInstance(@NotNull Type type) {
            JsonGenericPoseableModel jsonGenericPoseableModel;
            Intrinsics.checkNotNullParameter((Object)type, (String)"type");
            Bone bone = modelPart;
            Intrinsics.checkNotNull((Object)bone);
            JsonGenericPoseableModel it = jsonGenericPoseableModel = new JsonGenericPoseableModel(bone, false, 2, null);
            boolean bl = false;
            model = it;
            Bone bone2 = modelPart;
            Intrinsics.checkNotNull((Object)bone2);
            it.loadAllNamedChildren(bone2);
            return jsonGenericPoseableModel;
        }
    }
}

