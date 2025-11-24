/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.gson.ExclusionStrategy
 *  com.google.gson.FieldAttributes
 *  com.google.gson.Gson
 *  com.google.gson.GsonBuilder
 *  com.google.gson.InstanceCreator
 *  com.google.gson.JsonDeserializationContext
 *  com.google.gson.JsonDeserializer
 *  com.google.gson.JsonElement
 *  com.google.gson.JsonObject
 *  com.google.gson.JsonPrimitive
 *  com.google.gson.annotations.SerializedName
 *  com.google.gson.reflect.TypeToken
 *  kotlin.Lazy
 *  kotlin.LazyKt
 *  kotlin.Metadata
 *  kotlin.Pair
 *  kotlin.TuplesKt
 *  kotlin.collections.CollectionsKt
 *  kotlin.collections.MapsKt
 *  kotlin.jvm.functions.Function0
 *  kotlin.jvm.functions.Function1
 *  kotlin.jvm.functions.Function2
 *  kotlin.jvm.internal.DefaultConstructorMarker
 *  kotlin.jvm.internal.Intrinsics
 *  kotlin.jvm.internal.SourceDebugExtension
 *  kotlin.text.StringsKt
 *  net.minecraft.client.model.geom.ModelPart
 *  net.minecraft.world.entity.Entity
 *  net.minecraft.world.phys.Vec3
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.pokemon;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.entity.PokemonSideDelegate;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.molang.ExpressionLike;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.entity.PokemonClientDelegate;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.JsonPose;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.JsonPoseableEntityModel;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.PoseableEntityModel;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.PoseableEntityState;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.animation.SingleBoneLookAnimation;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.animation.StatefulAnimation;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.animation.StatelessAnimation;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.frame.HeadedFrame;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.frame.ModelFrame;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.pokemon.AnimationReferenceFactory;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.pokemon.CryProvider;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.pokemon.JsonPokemonPoseableModel;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.pokemon.PokemonPoseableModel;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.pose.Bone;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.pose.ModelPartTransformation;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.pose.Pose;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.quirk.ModelQuirk;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.entity.pokemon.PokemonEntity;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.EntityExtensionsKt;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.MoLangExtensionsKt;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.adapters.ExpressionLikeAdapter;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.adapters.Vec3dAdapter;
import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.InstanceCreator;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import com.google.gson.annotations.SerializedName;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Supplier;
import kotlin.Lazy;
import kotlin.LazyKt;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.TuplesKt;
import kotlin.collections.CollectionsKt;
import kotlin.collections.MapsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import kotlin.text.StringsKt;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000d\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u000e\n\u0002\b\u0004\n\u0002\u0010\u0007\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\r\n\u0002\u0018\u0002\n\u0002\b\f\u0018\u0000 A2\u00020\u00012\u00020\u0002:\u0005ABCDEB\u000f\u0012\u0006\u0010;\u001a\u00020\u001a\u00a2\u0006\u0004\b?\u0010@J3\u0010\t\u001a\u0010\u0012\u0004\u0012\u00020\u0003\u0012\u0004\u0012\u00020\b\u0018\u00010\u00072\u0006\u0010\u0004\u001a\u00020\u00032\f\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\u00030\u0005H\u0016\u00a2\u0006\u0004\b\t\u0010\nJ\u000f\u0010\f\u001a\u00020\u000bH\u0016\u00a2\u0006\u0004\b\f\u0010\rR+\u0010\u000f\u001a\u0016\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u00020\u0003\u0012\u0004\u0012\u00020\b0\u0007\u0018\u00010\u000e8\u0006\u00a2\u0006\f\n\u0004\b\u000f\u0010\u0010\u001a\u0004\b\u0011\u0010\u0012R\u001a\u0010\u0014\u001a\u00020\u00138\u0016X\u0096\u0004\u00a2\u0006\f\n\u0004\b\u0014\u0010\u0015\u001a\u0004\b\u0016\u0010\u0017R+\u0010\u0018\u001a\u0016\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u00020\u0003\u0012\u0004\u0012\u00020\b0\u0007\u0018\u00010\u000e8\u0006\u00a2\u0006\f\n\u0004\b\u0018\u0010\u0010\u001a\u0004\b\u0019\u0010\u0012R\u001b\u0010\u001f\u001a\u00020\u001a8VX\u0096\u0084\u0002\u00a2\u0006\f\n\u0004\b\u001b\u0010\u001c\u001a\u0004\b\u001d\u0010\u001eR\u001c\u0010!\u001a\u0004\u0018\u00010 8\u0006X\u0087\u0004\u00a2\u0006\f\n\u0004\b!\u0010\"\u001a\u0004\b#\u0010$R\"\u0010&\u001a\u00020%8\u0016@\u0016X\u0096\u000e\u00a2\u0006\u0012\n\u0004\b&\u0010'\u001a\u0004\b(\u0010)\"\u0004\b*\u0010+R*\u0010.\u001a\n -*\u0004\u0018\u00010,0,8\u0016@\u0016X\u0096\u000e\u00a2\u0006\u0012\n\u0004\b.\u0010/\u001a\u0004\b0\u00101\"\u0004\b2\u00103R\"\u00104\u001a\u00020%8\u0016@\u0016X\u0096\u000e\u00a2\u0006\u0012\n\u0004\b4\u0010'\u001a\u0004\b5\u0010)\"\u0004\b6\u0010+R*\u00107\u001a\n -*\u0004\u0018\u00010,0,8\u0016@\u0016X\u0096\u000e\u00a2\u0006\u0012\n\u0004\b7\u0010/\u001a\u0004\b8\u00101\"\u0004\b9\u00103R\u001a\u0010;\u001a\u00020:8\u0016X\u0096\u0004\u00a2\u0006\f\n\u0004\b;\u0010<\u001a\u0004\b=\u0010>\u00a8\u0006F"}, d2={"Lcom/cobblemon/mod/common/client/render/models/blockbench/pokemon/JsonPokemonPoseableModel;", "Lcom/cobblemon/mod/common/client/render/models/blockbench/pokemon/PokemonPoseableModel;", "Lcom/cobblemon/mod/common/client/render/models/blockbench/frame/HeadedFrame;", "Lcom/cobblemon/mod/common/entity/pokemon/PokemonEntity;", "pokemonEntity", "Lcom/cobblemon/mod/common/client/render/models/blockbench/PoseableEntityState;", "state", "Lcom/cobblemon/mod/common/client/render/models/blockbench/animation/StatefulAnimation;", "Lcom/cobblemon/mod/common/client/render/models/blockbench/frame/ModelFrame;", "getFaintAnimation", "(Lcom/cobblemon/mod/common/entity/pokemon/PokemonEntity;Lcom/cobblemon/mod/common/client/render/models/blockbench/PoseableEntityState;)Lcom/cobblemon/mod/common/client/render/models/blockbench/animation/StatefulAnimation;", "", "registerPoses", "()V", "Ljava/util/function/Supplier;", "cry", "Ljava/util/function/Supplier;", "getCry", "()Ljava/util/function/Supplier;", "Lcom/cobblemon/mod/common/client/render/models/blockbench/pokemon/CryProvider;", "cryAnimation", "Lcom/cobblemon/mod/common/client/render/models/blockbench/pokemon/CryProvider;", "getCryAnimation", "()Lcom/cobblemon/mod/common/client/render/models/blockbench/pokemon/CryProvider;", "faint", "getFaint", "Lcom/cobblemon/mod/common/client/render/models/blockbench/pose/Bone;", "head$delegate", "Lkotlin/Lazy;", "getHead", "()Lcom/cobblemon/mod/common/client/render/models/blockbench/pose/Bone;", "head", "", "headJoint", "Ljava/lang/String;", "getHeadJoint", "()Ljava/lang/String;", "", "portraitScale", "F", "getPortraitScale", "()F", "setPortraitScale", "(F)V", "Lnet/minecraft/world/phys/Vec3;", "kotlin.jvm.PlatformType", "portraitTranslation", "Lnet/minecraft/world/phys/Vec3;", "getPortraitTranslation", "()Lnet/minecraft/world/phys/Vec3;", "setPortraitTranslation", "(Lnet/minecraft/world/phys/Vec3;)V", "profileScale", "getProfileScale", "setProfileScale", "profileTranslation", "getProfileTranslation", "setProfileTranslation", "Lnet/minecraft/client/model/geom/ModelPart;", "rootPart", "Lnet/minecraft/client/model/geom/ModelPart;", "getRootPart", "()Lnet/minecraft/client/model/geom/ModelPart;", "<init>", "(Lcom/cobblemon/mod/common/client/render/models/blockbench/pose/Bone;)V", "Companion", "JsonModelExclusion", "JsonPokemonPoseableModelAdapter", "PoseAdapter", "StatefulAnimationAdapter", "common"})
@SourceDebugExtension(value={"SMAP\nJsonPokemonPoseableModel.kt\nKotlin\n*S Kotlin\n*F\n+ 1 JsonPokemonPoseableModel.kt\ncom/cobblemon/mod/common/client/render/models/blockbench/pokemon/JsonPokemonPoseableModel\n+ 2 fake.kt\nkotlin/jvm/internal/FakeKt\n*L\n1#1,205:1\n1#2:206\n*E\n"})
public final class JsonPokemonPoseableModel
extends PokemonPoseableModel
implements HeadedFrame {
    @NotNull
    public static final Companion Companion = new Companion(null);
    @NotNull
    private final ModelPart rootPart;
    @SerializedName(value="head")
    @Nullable
    private final String headJoint;
    @NotNull
    private final Lazy head$delegate;
    private float portraitScale;
    private Vec3 portraitTranslation;
    private float profileScale;
    private Vec3 profileTranslation;
    @Nullable
    private final Supplier<StatefulAnimation<PokemonEntity, ModelFrame>> faint;
    @Nullable
    private final Supplier<StatefulAnimation<PokemonEntity, ModelFrame>> cry;
    @NotNull
    private final CryProvider cryAnimation;
    private static final Gson gson;
    @NotNull
    private static final Map<String, AnimationReferenceFactory> ANIMATION_FACTORIES;

    /*
     * WARNING - void declaration
     */
    public JsonPokemonPoseableModel(@NotNull Bone rootPart) {
        void it;
        Intrinsics.checkNotNullParameter((Object)rootPart, (String)"rootPart");
        Map.Entry entry = (Map.Entry)CollectionsKt.first((Iterable)((ModelPart)rootPart).f_104213_.entrySet());
        JsonPokemonPoseableModel jsonPokemonPoseableModel = this;
        boolean bl = false;
        ModelPart modelPart = (ModelPart)rootPart;
        Object k = it.getKey();
        Intrinsics.checkNotNullExpressionValue(k, (String)"it.key");
        jsonPokemonPoseableModel.rootPart = this.registerChildWithAllChildren(modelPart, (String)k);
        this.head$delegate = LazyKt.lazy((Function0)((Function0)new Function0<Bone>(this, rootPart){
            final /* synthetic */ JsonPokemonPoseableModel this$0;
            final /* synthetic */ Bone $rootPart;
            {
                this.this$0 = $receiver;
                this.$rootPart = $rootPart;
                super(0);
            }

            /*
             * Enabled aggressive block sorting
             */
            @NotNull
            public final Bone invoke() {
                Bone bone;
                String string = this.this$0.getHeadJoint();
                if (string != null) {
                    String string2 = string;
                    JsonPokemonPoseableModel jsonPokemonPoseableModel = this.this$0;
                    String it = string2;
                    boolean bl = false;
                    ModelPart modelPart = jsonPokemonPoseableModel.getPart(it);
                    string = modelPart;
                    if (modelPart != null) {
                        bone = (Bone)((Object)string);
                        return bone;
                    }
                }
                bone = this.$rootPart;
                return bone;
            }
        }));
        this.portraitScale = 1.0f;
        this.portraitTranslation = Vec3.f_82478_;
        this.profileScale = 1.0f;
        this.profileTranslation = Vec3.f_82478_;
        this.cryAnimation = (arg_0, arg_1) -> JsonPokemonPoseableModel.cryAnimation$lambda$1(this, arg_0, arg_1);
    }

    @NotNull
    public ModelPart getRootPart() {
        return this.rootPart;
    }

    @Override
    public void registerPoses() {
    }

    @Nullable
    public final String getHeadJoint() {
        return this.headJoint;
    }

    @Override
    @NotNull
    public Bone getHead() {
        Lazy lazy = this.head$delegate;
        return (Bone)lazy.getValue();
    }

    @Override
    public float getPortraitScale() {
        return this.portraitScale;
    }

    @Override
    public void setPortraitScale(float f) {
        this.portraitScale = f;
    }

    @Override
    public Vec3 getPortraitTranslation() {
        return this.portraitTranslation;
    }

    @Override
    public void setPortraitTranslation(Vec3 vec3) {
        this.portraitTranslation = vec3;
    }

    @Override
    public float getProfileScale() {
        return this.profileScale;
    }

    @Override
    public void setProfileScale(float f) {
        this.profileScale = f;
    }

    @Override
    public Vec3 getProfileTranslation() {
        return this.profileTranslation;
    }

    @Override
    public void setProfileTranslation(Vec3 vec3) {
        this.profileTranslation = vec3;
    }

    @Nullable
    public final Supplier<StatefulAnimation<PokemonEntity, ModelFrame>> getFaint() {
        return this.faint;
    }

    @Nullable
    public final Supplier<StatefulAnimation<PokemonEntity, ModelFrame>> getCry() {
        return this.cry;
    }

    @Override
    @Nullable
    public StatefulAnimation<PokemonEntity, ModelFrame> getFaintAnimation(@NotNull PokemonEntity pokemonEntity, @NotNull PoseableEntityState<PokemonEntity> state) {
        Intrinsics.checkNotNullParameter((Object)pokemonEntity, (String)"pokemonEntity");
        Intrinsics.checkNotNullParameter(state, (String)"state");
        Supplier<StatefulAnimation<PokemonEntity, ModelFrame>> supplier = this.faint;
        return supplier != null ? supplier.get() : null;
    }

    @Override
    @NotNull
    public CryProvider getCryAnimation() {
        return this.cryAnimation;
    }

    @Override
    @NotNull
    public <T extends Entity> SingleBoneLookAnimation<T> singleBoneLook(boolean invertX, boolean invertY, boolean disableX, boolean disableY, @Nullable Float pitchMultiplier, @Nullable Float yawMultiplier, @Nullable Float maxPitch, @Nullable Float minPitch, @Nullable Float maxYaw, @Nullable Float minYaw) {
        return HeadedFrame.DefaultImpls.singleBoneLook(this, invertX, invertY, disableX, disableY, pitchMultiplier, yawMultiplier, maxPitch, minPitch, maxYaw, minYaw);
    }

    private static final StatefulAnimation cryAnimation$lambda$1(JsonPokemonPoseableModel this$0, PokemonEntity pokemonEntity, PoseableEntityState poseableEntityState) {
        Intrinsics.checkNotNullParameter((Object)this$0, (String)"this$0");
        Intrinsics.checkNotNullParameter((Object)pokemonEntity, (String)"<anonymous parameter 0>");
        Intrinsics.checkNotNullParameter((Object)poseableEntityState, (String)"<anonymous parameter 1>");
        Supplier<StatefulAnimation<PokemonEntity, ModelFrame>> supplier = this$0.cry;
        return supplier != null ? supplier.get() : null;
    }

    static {
        Object[] objectArray = new ExclusionStrategy[]{JsonPoseableEntityModel.JsonModelExclusion.INSTANCE};
        GsonBuilder gsonBuilder = new GsonBuilder().setPrettyPrinting().disableHtmlEscaping().registerTypeAdapter((Type)((Object)Vec3.class), (Object)Vec3dAdapter.INSTANCE).setExclusionStrategies(objectArray);
        objectArray = new Type[1];
        Type[] typeArray = new Type[]{PokemonEntity.class, ModelFrame.class};
        objectArray[0] = TypeToken.getParameterized((Type)((Type)((Object)StatefulAnimation.class)), (Type[])typeArray).getType();
        gson = gsonBuilder.registerTypeAdapter(TypeToken.getParameterized((Type)((Type)((Object)Supplier.class)), (Type[])objectArray).getType(), new JsonPoseableEntityModel.StatefulAnimationAdapter(Companion.gson.1.INSTANCE)).registerTypeAdapter((Type)((Object)ExpressionLike.class), (Object)ExpressionLikeAdapter.INSTANCE).registerTypeAdapter((Type)((Object)Pose.class), (Object)PoseAdapter.INSTANCE).registerTypeAdapter((Type)((Object)JsonPokemonPoseableModel.class), (Object)JsonPokemonPoseableModelAdapter.INSTANCE).create();
        ANIMATION_FACTORIES = new LinkedHashMap();
    }

    @Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000,\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0010%\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\b\b\u0086\u0003\u0018\u00002\u00020\u0001B\t\b\u0002\u00a2\u0006\u0004\b\u0014\u0010\u0015J\u001d\u0010\u0007\u001a\u00020\u00062\u0006\u0010\u0003\u001a\u00020\u00022\u0006\u0010\u0005\u001a\u00020\u0004\u00a2\u0006\u0004\b\u0007\u0010\bR#\u0010\n\u001a\u000e\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020\u00040\t8\u0006\u00a2\u0006\f\n\u0004\b\n\u0010\u000b\u001a\u0004\b\f\u0010\rR\u001f\u0010\u0010\u001a\n \u000f*\u0004\u0018\u00010\u000e0\u000e8\u0006\u00a2\u0006\f\n\u0004\b\u0010\u0010\u0011\u001a\u0004\b\u0012\u0010\u0013\u00a8\u0006\u0016"}, d2={"Lcom/cobblemon/mod/common/client/render/models/blockbench/pokemon/JsonPokemonPoseableModel$Companion;", "", "", "id", "Lcom/cobblemon/mod/common/client/render/models/blockbench/pokemon/AnimationReferenceFactory;", "factory", "", "registerFactory", "(Ljava/lang/String;Lcom/cobblemon/mod/common/client/render/models/blockbench/pokemon/AnimationReferenceFactory;)V", "", "ANIMATION_FACTORIES", "Ljava/util/Map;", "getANIMATION_FACTORIES", "()Ljava/util/Map;", "Lcom/google/gson/Gson;", "kotlin.jvm.PlatformType", "gson", "Lcom/google/gson/Gson;", "getGson", "()Lcom/google/gson/Gson;", "<init>", "()V", "common"})
    public static final class Companion {
        private Companion() {
        }

        public final Gson getGson() {
            return gson;
        }

        public final void registerFactory(@NotNull String id, @NotNull AnimationReferenceFactory factory) {
            Intrinsics.checkNotNullParameter((Object)id, (String)"id");
            Intrinsics.checkNotNullParameter((Object)factory, (String)"factory");
            this.getANIMATION_FACTORIES().put(id, factory);
        }

        @NotNull
        public final Map<String, AnimationReferenceFactory> getANIMATION_FACTORIES() {
            return ANIMATION_FACTORIES;
        }

        public /* synthetic */ Companion(DefaultConstructorMarker $constructor_marker) {
            this();
        }
    }

    @Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0006\b\u00c6\u0002\u0018\u00002\u00020\u0001B\t\b\u0002\u00a2\u0006\u0004\b\u000b\u0010\fJ\u001b\u0010\u0005\u001a\u00020\u00042\n\u0010\u0003\u001a\u0006\u0012\u0002\b\u00030\u0002H\u0016\u00a2\u0006\u0004\b\u0005\u0010\u0006J\u0017\u0010\t\u001a\u00020\u00042\u0006\u0010\b\u001a\u00020\u0007H\u0016\u00a2\u0006\u0004\b\t\u0010\n\u00a8\u0006\r"}, d2={"Lcom/cobblemon/mod/common/client/render/models/blockbench/pokemon/JsonPokemonPoseableModel$JsonModelExclusion;", "Lcom/google/gson/ExclusionStrategy;", "Ljava/lang/Class;", "clazz", "", "shouldSkipClass", "(Ljava/lang/Class;)Z", "Lcom/google/gson/FieldAttributes;", "f", "shouldSkipField", "(Lcom/google/gson/FieldAttributes;)Z", "<init>", "()V", "common"})
    public static final class JsonModelExclusion
    implements ExclusionStrategy {
        @NotNull
        public static final JsonModelExclusion INSTANCE = new JsonModelExclusion();

        private JsonModelExclusion() {
        }

        public boolean shouldSkipField(@NotNull FieldAttributes f) {
            Intrinsics.checkNotNullParameter((Object)f, (String)"f");
            Object[] objectArray = new String[]{"JsonPokemonPoseableModel", "PoseableEntityModel", "Pose"};
            return !CollectionsKt.listOf((Object[])objectArray).contains(f.getDeclaringClass().getSimpleName());
        }

        public boolean shouldSkipClass(@NotNull Class<?> clazz) {
            Intrinsics.checkNotNullParameter(clazz, (String)"clazz");
            return false;
        }
    }

    @Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000\u001c\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\t\n\u0002\u0018\u0002\n\u0002\b\t\b\u00c6\u0002\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001B\t\b\u0002\u00a2\u0006\u0004\b\u0014\u0010\u0015J\u0017\u0010\u0005\u001a\u00020\u00022\u0006\u0010\u0004\u001a\u00020\u0003H\u0016\u00a2\u0006\u0004\b\u0005\u0010\u0006R$\u0010\u0007\u001a\u0004\u0018\u00010\u00028\u0006@\u0006X\u0086\u000e\u00a2\u0006\u0012\n\u0004\b\u0007\u0010\b\u001a\u0004\b\t\u0010\n\"\u0004\b\u000b\u0010\fR$\u0010\u000e\u001a\u0004\u0018\u00010\r8\u0006@\u0006X\u0086\u000e\u00a2\u0006\u0012\n\u0004\b\u000e\u0010\u000f\u001a\u0004\b\u0010\u0010\u0011\"\u0004\b\u0012\u0010\u0013\u00a8\u0006\u0016"}, d2={"Lcom/cobblemon/mod/common/client/render/models/blockbench/pokemon/JsonPokemonPoseableModel$JsonPokemonPoseableModelAdapter;", "Lcom/google/gson/InstanceCreator;", "Lcom/cobblemon/mod/common/client/render/models/blockbench/pokemon/JsonPokemonPoseableModel;", "Ljava/lang/reflect/Type;", "type", "createInstance", "(Ljava/lang/reflect/Type;)Lcom/cobblemon/mod/common/client/render/models/blockbench/pokemon/JsonPokemonPoseableModel;", "model", "Lcom/cobblemon/mod/common/client/render/models/blockbench/pokemon/JsonPokemonPoseableModel;", "getModel", "()Lcom/cobblemon/mod/common/client/render/models/blockbench/pokemon/JsonPokemonPoseableModel;", "setModel", "(Lcom/cobblemon/mod/common/client/render/models/blockbench/pokemon/JsonPokemonPoseableModel;)V", "Lcom/cobblemon/mod/common/client/render/models/blockbench/pose/Bone;", "modelPart", "Lcom/cobblemon/mod/common/client/render/models/blockbench/pose/Bone;", "getModelPart", "()Lcom/cobblemon/mod/common/client/render/models/blockbench/pose/Bone;", "setModelPart", "(Lcom/cobblemon/mod/common/client/render/models/blockbench/pose/Bone;)V", "<init>", "()V", "common"})
    public static final class JsonPokemonPoseableModelAdapter
    implements InstanceCreator<JsonPokemonPoseableModel> {
        @NotNull
        public static final JsonPokemonPoseableModelAdapter INSTANCE = new JsonPokemonPoseableModelAdapter();
        @Nullable
        private static Bone modelPart;
        @Nullable
        private static JsonPokemonPoseableModel model;

        private JsonPokemonPoseableModelAdapter() {
        }

        @Nullable
        public final Bone getModelPart() {
            return modelPart;
        }

        public final void setModelPart(@Nullable Bone bone) {
            modelPart = bone;
        }

        @Nullable
        public final JsonPokemonPoseableModel getModel() {
            return model;
        }

        public final void setModel(@Nullable JsonPokemonPoseableModel jsonPokemonPoseableModel) {
            model = jsonPokemonPoseableModel;
        }

        @NotNull
        public JsonPokemonPoseableModel createInstance(@NotNull Type type) {
            JsonPokemonPoseableModel jsonPokemonPoseableModel;
            Intrinsics.checkNotNullParameter((Object)type, (String)"type");
            Bone bone = modelPart;
            Intrinsics.checkNotNull((Object)bone);
            JsonPokemonPoseableModel it = jsonPokemonPoseableModel = new JsonPokemonPoseableModel(bone);
            boolean bl = false;
            model = it;
            Bone bone2 = modelPart;
            Intrinsics.checkNotNull((Object)bone2, (String)"null cannot be cast to non-null type net.minecraft.client.model.ModelPart");
            ModelPart cfr_ignored_0 = (ModelPart)bone2;
            Bone bone3 = modelPart;
            Intrinsics.checkNotNull((Object)bone3);
            it.loadAllNamedChildren(bone3);
            return jsonPokemonPoseableModel;
        }
    }

    @Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000(\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\b\u00c6\u0002\u0018\u00002\u0014\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u00020\u0003\u0012\u0004\u0012\u00020\u00040\u00020\u0001B\t\b\u0002\u00a2\u0006\u0004\b\r\u0010\u000eJ3\u0010\u000b\u001a\u000e\u0012\u0004\u0012\u00020\u0003\u0012\u0004\u0012\u00020\u00040\u00022\u0006\u0010\u0006\u001a\u00020\u00052\u0006\u0010\b\u001a\u00020\u00072\u0006\u0010\n\u001a\u00020\tH\u0016\u00a2\u0006\u0004\b\u000b\u0010\f\u00a8\u0006\u000f"}, d2={"Lcom/cobblemon/mod/common/client/render/models/blockbench/pokemon/JsonPokemonPoseableModel$PoseAdapter;", "Lcom/google/gson/JsonDeserializer;", "Lcom/cobblemon/mod/common/client/render/models/blockbench/pose/Pose;", "Lcom/cobblemon/mod/common/entity/pokemon/PokemonEntity;", "Lcom/cobblemon/mod/common/client/render/models/blockbench/frame/ModelFrame;", "Lcom/google/gson/JsonElement;", "json", "Ljava/lang/reflect/Type;", "type", "Lcom/google/gson/JsonDeserializationContext;", "ctx", "deserialize", "(Lcom/google/gson/JsonElement;Ljava/lang/reflect/Type;Lcom/google/gson/JsonDeserializationContext;)Lcom/cobblemon/mod/common/client/render/models/blockbench/pose/Pose;", "<init>", "()V", "common"})
    @SourceDebugExtension(value={"SMAP\nJsonPokemonPoseableModel.kt\nKotlin\n*S Kotlin\n*F\n+ 1 JsonPokemonPoseableModel.kt\ncom/cobblemon/mod/common/client/render/models/blockbench/pokemon/JsonPokemonPoseableModel$PoseAdapter\n+ 2 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n+ 3 ArraysJVM.kt\nkotlin/collections/ArraysKt__ArraysJVMKt\n+ 4 fake.kt\nkotlin/jvm/internal/FakeKt\n*L\n1#1,205:1\n2661#2,7:206\n1603#2,9:215\n1855#2:224\n1856#2:226\n1612#2:227\n37#3,2:213\n1#4:225\n*S KotlinDebug\n*F\n+ 1 JsonPokemonPoseableModel.kt\ncom/cobblemon/mod/common/client/render/models/blockbench/pokemon/JsonPokemonPoseableModel$PoseAdapter\n*L\n184#1:206,7\n198#1:215,9\n198#1:224\n198#1:226\n198#1:227\n194#1:213,2\n198#1:225\n*E\n"})
    public static final class PoseAdapter
    implements JsonDeserializer<Pose<PokemonEntity, ModelFrame>> {
        @NotNull
        public static final PoseAdapter INSTANCE = new PoseAdapter();

        private PoseAdapter() {
        }

        /*
         * WARNING - void declaration
         */
        @NotNull
        public Pose<PokemonEntity, ModelFrame> deserialize(@NotNull JsonElement json, @NotNull Type type, @NotNull JsonDeserializationContext ctx) {
            void $this$mapNotNullTo$iv$iv;
            void $this$mapNotNull$iv;
            Function1 acc;
            Object object;
            Iterator iterator$iv;
            Function1 function1;
            Boolean mustBeDusk;
            Boolean mustBeStandingOnSandOrRedSand;
            Boolean mustBeStandingOnSand;
            Boolean mustBeStandingOnRedSand;
            Boolean mustBeSubmergedInWater;
            Boolean mustBeTouchingWaterOrRain;
            Boolean mustBeTouchingWater;
            Boolean mustBeInBattle;
            Intrinsics.checkNotNullParameter((Object)json, (String)"json");
            Intrinsics.checkNotNullParameter((Object)type, (String)"type");
            Intrinsics.checkNotNullParameter((Object)ctx, (String)"ctx");
            JsonPokemonPoseableModel jsonPokemonPoseableModel = JsonPokemonPoseableModelAdapter.INSTANCE.getModel();
            Intrinsics.checkNotNull((Object)jsonPokemonPoseableModel);
            JsonPokemonPoseableModel model = jsonPokemonPoseableModel;
            JsonObject obj = (JsonObject)json;
            JsonPose<PokemonEntity> pose = new JsonPose<PokemonEntity>(model, obj);
            List conditionsList = new ArrayList();
            JsonElement jsonElement = ((JsonObject)json).get("isBattle");
            Boolean bl = mustBeInBattle = jsonElement != null ? Boolean.valueOf(jsonElement.getAsBoolean()) : null;
            if (mustBeInBattle != null) {
                conditionsList.add(new Function1<PokemonEntity, Boolean>(mustBeInBattle){
                    final /* synthetic */ Boolean $mustBeInBattle;
                    {
                        this.$mustBeInBattle = $mustBeInBattle;
                        super(1);
                    }

                    @NotNull
                    public final Boolean invoke(@NotNull PokemonEntity it) {
                        Intrinsics.checkNotNullParameter((Object)it, (String)"it");
                        return Intrinsics.areEqual((Object)this.$mustBeInBattle, (Object)it.isBattling());
                    }
                });
            }
            JsonElement jsonElement2 = ((JsonObject)json).get("isTouchingWater");
            Boolean bl2 = mustBeTouchingWater = jsonElement2 != null ? Boolean.valueOf(jsonElement2.getAsBoolean()) : null;
            if (mustBeTouchingWater != null) {
                conditionsList.add(new Function1<PokemonEntity, Boolean>(mustBeTouchingWater){
                    final /* synthetic */ Boolean $mustBeTouchingWater;
                    {
                        this.$mustBeTouchingWater = $mustBeTouchingWater;
                        super(1);
                    }

                    @NotNull
                    public final Boolean invoke(@NotNull PokemonEntity it) {
                        Intrinsics.checkNotNullParameter((Object)it, (String)"it");
                        return Intrinsics.areEqual((Object)this.$mustBeTouchingWater, (Object)it.m_20069_());
                    }
                });
            }
            JsonElement jsonElement3 = ((JsonObject)json).get("isTouchingWaterOrRain");
            Boolean bl3 = mustBeTouchingWaterOrRain = jsonElement3 != null ? Boolean.valueOf(jsonElement3.getAsBoolean()) : null;
            if (mustBeTouchingWaterOrRain != null) {
                conditionsList.add(new Function1<PokemonEntity, Boolean>(mustBeTouchingWaterOrRain){
                    final /* synthetic */ Boolean $mustBeTouchingWaterOrRain;
                    {
                        this.$mustBeTouchingWaterOrRain = $mustBeTouchingWaterOrRain;
                        super(1);
                    }

                    @NotNull
                    public final Boolean invoke(@NotNull PokemonEntity it) {
                        Intrinsics.checkNotNullParameter((Object)it, (String)"it");
                        return Intrinsics.areEqual((Object)this.$mustBeTouchingWaterOrRain, (Object)it.m_20070_());
                    }
                });
            }
            JsonElement jsonElement4 = ((JsonObject)json).get("isSubmergedInWater");
            Boolean bl4 = mustBeSubmergedInWater = jsonElement4 != null ? Boolean.valueOf(jsonElement4.getAsBoolean()) : null;
            if (mustBeSubmergedInWater != null) {
                conditionsList.add(new Function1<PokemonEntity, Boolean>(mustBeSubmergedInWater){
                    final /* synthetic */ Boolean $mustBeSubmergedInWater;
                    {
                        this.$mustBeSubmergedInWater = $mustBeSubmergedInWater;
                        super(1);
                    }

                    @NotNull
                    public final Boolean invoke(@NotNull PokemonEntity it) {
                        Intrinsics.checkNotNullParameter((Object)it, (String)"it");
                        return Intrinsics.areEqual((Object)this.$mustBeSubmergedInWater, (Object)it.m_5842_());
                    }
                });
            }
            JsonElement jsonElement5 = ((JsonObject)json).get("isStandingOnRedSand");
            Boolean bl5 = mustBeStandingOnRedSand = jsonElement5 != null ? Boolean.valueOf(jsonElement5.getAsBoolean()) : null;
            if (mustBeStandingOnRedSand != null) {
                conditionsList.add(new Function1<PokemonEntity, Boolean>(mustBeStandingOnRedSand){
                    final /* synthetic */ Boolean $mustBeStandingOnRedSand;
                    {
                        this.$mustBeStandingOnRedSand = $mustBeStandingOnRedSand;
                        super(1);
                    }

                    @NotNull
                    public final Boolean invoke(@NotNull PokemonEntity it) {
                        Intrinsics.checkNotNullParameter((Object)it, (String)"it");
                        return Intrinsics.areEqual((Object)this.$mustBeStandingOnRedSand, (Object)EntityExtensionsKt.isStandingOnRedSand((Entity)it));
                    }
                });
            }
            JsonElement jsonElement6 = ((JsonObject)json).get("isStandingOnSand");
            Boolean bl6 = mustBeStandingOnSand = jsonElement6 != null ? Boolean.valueOf(jsonElement6.getAsBoolean()) : null;
            if (mustBeStandingOnSand != null) {
                conditionsList.add(new Function1<PokemonEntity, Boolean>(mustBeStandingOnSand){
                    final /* synthetic */ Boolean $mustBeStandingOnSand;
                    {
                        this.$mustBeStandingOnSand = $mustBeStandingOnSand;
                        super(1);
                    }

                    @NotNull
                    public final Boolean invoke(@NotNull PokemonEntity it) {
                        Intrinsics.checkNotNullParameter((Object)it, (String)"it");
                        return Intrinsics.areEqual((Object)this.$mustBeStandingOnSand, (Object)EntityExtensionsKt.isStandingOnSand((Entity)it));
                    }
                });
            }
            JsonElement jsonElement7 = ((JsonObject)json).get("isStandingOnSandOrRedSand");
            Boolean bl7 = mustBeStandingOnSandOrRedSand = jsonElement7 != null ? Boolean.valueOf(jsonElement7.getAsBoolean()) : null;
            if (mustBeStandingOnSandOrRedSand != null) {
                conditionsList.add(new Function1<PokemonEntity, Boolean>(mustBeStandingOnSandOrRedSand){
                    final /* synthetic */ Boolean $mustBeStandingOnSandOrRedSand;
                    {
                        this.$mustBeStandingOnSandOrRedSand = $mustBeStandingOnSandOrRedSand;
                        super(1);
                    }

                    @NotNull
                    public final Boolean invoke(@NotNull PokemonEntity it) {
                        Intrinsics.checkNotNullParameter((Object)it, (String)"it");
                        return Intrinsics.areEqual((Object)this.$mustBeStandingOnSandOrRedSand, (Object)EntityExtensionsKt.isStandingOnSandOrRedSand((Entity)it));
                    }
                });
            }
            JsonElement jsonElement8 = ((JsonObject)json).get("isDusk");
            Boolean bl8 = mustBeDusk = jsonElement8 != null ? Boolean.valueOf(jsonElement8.getAsBoolean()) : null;
            if (mustBeDusk != null) {
                conditionsList.add(new Function1<PokemonEntity, Boolean>(mustBeDusk){
                    final /* synthetic */ Boolean $mustBeDusk;
                    {
                        this.$mustBeDusk = $mustBeDusk;
                        super(1);
                    }

                    @NotNull
                    public final Boolean invoke(@NotNull PokemonEntity it) {
                        Intrinsics.checkNotNullParameter((Object)it, (String)"it");
                        return Intrinsics.areEqual((Object)this.$mustBeDusk, (Object)EntityExtensionsKt.isDusk((Entity)it));
                    }
                });
            }
            conditionsList.add(new Function1<PokemonEntity, Boolean>(pose){
                final /* synthetic */ JsonPose<PokemonEntity> $pose;
                {
                    this.$pose = $pose;
                    super(1);
                }

                @NotNull
                public final Boolean invoke(@NotNull PokemonEntity it) {
                    Intrinsics.checkNotNullParameter((Object)it, (String)"it");
                    PokemonSideDelegate pokemonSideDelegate = it.getDelegate();
                    Intrinsics.checkNotNull((Object)pokemonSideDelegate, (String)"null cannot be cast to non-null type bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.entity.PokemonClientDelegate");
                    return MoLangExtensionsKt.resolveBoolean(((PokemonClientDelegate)pokemonSideDelegate).getRuntime(), this.$pose.getCondition());
                }
            });
            if (conditionsList.isEmpty()) {
                function1 = null;
            } else {
                Iterable $this$reduce$iv = conditionsList;
                boolean $i$f$reduce = false;
                iterator$iv = $this$reduce$iv.iterator();
                if (!iterator$iv.hasNext()) {
                    throw new UnsupportedOperationException("Empty collection can't be reduced.");
                }
                Object accumulator$iv22 = iterator$iv.next();
                while (iterator$iv.hasNext()) {
                    void function;
                    object = (Function1)iterator$iv.next();
                    acc = (Function1)accumulator$iv22;
                    boolean bl9 = false;
                    accumulator$iv22 = (Function1)new Function1<PokemonEntity, Boolean>((Function1<? super PokemonEntity, Boolean>)acc, (Function1<? super PokemonEntity, Boolean>)function){
                        final /* synthetic */ Function1<PokemonEntity, Boolean> $acc;
                        final /* synthetic */ Function1<PokemonEntity, Boolean> $function;
                        {
                            this.$acc = $acc;
                            this.$function = $function;
                            super(1);
                        }

                        @NotNull
                        public final Boolean invoke(@NotNull PokemonEntity it) {
                            Intrinsics.checkNotNullParameter((Object)it, (String)"it");
                            return (Boolean)this.$acc.invoke((Object)it) != false && (Boolean)this.$function.invoke((Object)it) != false;
                        }
                    };
                }
                function1 = (Function1)accumulator$iv22;
            }
            Function1 poseCondition2 = function1;
            Object object2 = pose.getPoseName();
            Set $i$f$reduce = CollectionsKt.toSet((Iterable)pose.getPoseTypes());
            iterator$iv = pose.getAnimations();
            int accumulator$iv22 = pose.getTransformTicks();
            object = pose.getIdleAnimations();
            acc = pose.getTransformedParts();
            Collection $this$toTypedArray$iv = pose.getQuirks();
            boolean $i$f$toTypedArray = false;
            Collection thisCollection$iv = $this$toTypedArray$iv;
            ModelQuirk[] bl9 = thisCollection$iv.toArray(new ModelQuirk[0]);
            Object it = object2 = new Pose((String)object2, $i$f$reduce, poseCondition2, null, accumulator$iv22, (Map)((Object)iterator$iv), (StatelessAnimation[])object, (ModelPartTransformation[])acc, bl9, 8, null);
            boolean bl10 = false;
            Iterable accumulator$iv22 = pose.getTransitions();
            object = ((Pose)it).getTransitions();
            boolean $i$f$mapNotNull = false;
            bl9 = $this$mapNotNull$iv;
            Collection destination$iv$iv = new ArrayList();
            boolean $i$f$mapNotNullTo = false;
            void $this$forEach$iv$iv$iv = $this$mapNotNullTo$iv$iv;
            boolean $i$f$forEach = false;
            Iterator iterator = $this$forEach$iv$iv$iv.iterator();
            while (iterator.hasNext()) {
                Pair it$iv$iv;
                Object element$iv$iv$iv;
                Object element$iv$iv = element$iv$iv$iv = iterator.next();
                boolean bl11 = false;
                JsonPose.JsonPoseTransition it2 = (JsonPose.JsonPoseTransition)element$iv$iv;
                boolean bl12 = false;
                if (TuplesKt.to((Object)it2.getTo(), (Object)new Function2<Pose<PokemonEntity, ? extends ModelFrame>, Pose<PokemonEntity, ? extends ModelFrame>, StatefulAnimation<PokemonEntity, ModelFrame>>(it2, model){
                    final /* synthetic */ JsonPose.JsonPoseTransition $it;
                    final /* synthetic */ JsonPokemonPoseableModel $model;
                    {
                        this.$it = $it;
                        this.$model = $model;
                        super(2);
                    }

                    @NotNull
                    public final StatefulAnimation<PokemonEntity, ModelFrame> invoke(@NotNull Pose<PokemonEntity, ? extends ModelFrame> pose, @NotNull Pose<PokemonEntity, ? extends ModelFrame> pose2) {
                        Intrinsics.checkNotNullParameter(pose, (String)"<anonymous parameter 0>");
                        Intrinsics.checkNotNullParameter(pose2, (String)"<anonymous parameter 1>");
                        ? obj = this.$it.getAnimation().resolveObject(this.$model.getRuntime()).getObj();
                        Intrinsics.checkNotNull(obj, (String)"null cannot be cast to non-null type bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.animation.StatefulAnimation<bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.entity.pokemon.PokemonEntity, bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.frame.ModelFrame>");
                        return (StatefulAnimation)obj;
                    }
                }) == null) continue;
                boolean bl13 = false;
                destination$iv$iv.add(it$iv$iv);
            }
            object.putAll(MapsKt.toMap((Iterable)((List)destination$iv$iv)));
            return object2;
        }
    }

    @Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000,\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\b\u00c6\u0002\u0018\u00002\u001a\u0012\u0016\u0012\u0014\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u00020\u0004\u0012\u0004\u0012\u00020\u00050\u00030\u00020\u0001B\t\b\u0002\u00a2\u0006\u0004\b\u000e\u0010\u000fJ9\u0010\f\u001a\u0014\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u00020\u0004\u0012\u0004\u0012\u00020\u00050\u00030\u00022\u0006\u0010\u0007\u001a\u00020\u00062\u0006\u0010\t\u001a\u00020\b2\u0006\u0010\u000b\u001a\u00020\nH\u0016\u00a2\u0006\u0004\b\f\u0010\r\u00a8\u0006\u0010"}, d2={"Lcom/cobblemon/mod/common/client/render/models/blockbench/pokemon/JsonPokemonPoseableModel$StatefulAnimationAdapter;", "Lcom/google/gson/JsonDeserializer;", "Ljava/util/function/Supplier;", "Lcom/cobblemon/mod/common/client/render/models/blockbench/animation/StatefulAnimation;", "Lcom/cobblemon/mod/common/entity/pokemon/PokemonEntity;", "Lcom/cobblemon/mod/common/client/render/models/blockbench/frame/ModelFrame;", "Lcom/google/gson/JsonElement;", "json", "Ljava/lang/reflect/Type;", "type", "Lcom/google/gson/JsonDeserializationContext;", "ctx", "deserialize", "(Lcom/google/gson/JsonElement;Ljava/lang/reflect/Type;Lcom/google/gson/JsonDeserializationContext;)Ljava/util/function/Supplier;", "<init>", "()V", "common"})
    public static final class StatefulAnimationAdapter
    implements JsonDeserializer<Supplier<StatefulAnimation<PokemonEntity, ModelFrame>>> {
        @NotNull
        public static final StatefulAnimationAdapter INSTANCE = new StatefulAnimationAdapter();

        private StatefulAnimationAdapter() {
        }

        @NotNull
        public Supplier<StatefulAnimation<PokemonEntity, ModelFrame>> deserialize(@NotNull JsonElement json, @NotNull Type type, @NotNull JsonDeserializationContext ctx) {
            Intrinsics.checkNotNullParameter((Object)json, (String)"json");
            Intrinsics.checkNotNullParameter((Object)type, (String)"type");
            Intrinsics.checkNotNullParameter((Object)ctx, (String)"ctx");
            JsonPrimitive cfr_ignored_0 = (JsonPrimitive)json;
            String animString = ((JsonPrimitive)json).getAsString();
            Intrinsics.checkNotNullExpressionValue((Object)animString, (String)"animString");
            String format = StringsKt.substringBefore$default((String)animString, (String)"(", null, (int)2, null);
            return () -> StatefulAnimationAdapter.deserialize$lambda$0(format, animString);
        }

        private static final StatefulAnimation deserialize$lambda$0(String $format, String $animString) {
            Intrinsics.checkNotNullParameter((Object)$format, (String)"$format");
            AnimationReferenceFactory animationReferenceFactory = Companion.getANIMATION_FACTORIES().get($format);
            Intrinsics.checkNotNull((Object)animationReferenceFactory);
            JsonPokemonPoseableModel jsonPokemonPoseableModel = JsonPokemonPoseableModelAdapter.INSTANCE.getModel();
            Intrinsics.checkNotNull((Object)jsonPokemonPoseableModel);
            PoseableEntityModel poseableEntityModel = jsonPokemonPoseableModel;
            Intrinsics.checkNotNullExpressionValue((Object)$animString, (String)"animString");
            return animationReferenceFactory.stateful(poseableEntityModel, $animString);
        }
    }
}

