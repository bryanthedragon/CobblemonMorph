/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.gson.ExclusionStrategy
 *  com.google.gson.FieldAttributes
 *  com.google.gson.InstanceCreator
 *  com.google.gson.JsonDeserializationContext
 *  com.google.gson.JsonDeserializer
 *  com.google.gson.JsonElement
 *  com.google.gson.JsonObject
 *  com.google.gson.JsonPrimitive
 *  kotlin.Metadata
 *  kotlin.Pair
 *  kotlin.TuplesKt
 *  kotlin.collections.CollectionsKt
 *  kotlin.collections.MapsKt
 *  kotlin.jvm.functions.Function0
 *  kotlin.jvm.functions.Function1
 *  kotlin.jvm.functions.Function2
 *  kotlin.jvm.internal.Intrinsics
 *  kotlin.jvm.internal.SourceDebugExtension
 *  kotlin.text.StringsKt
 *  net.minecraft.client.model.geom.ModelPart
 *  net.minecraft.world.entity.Entity
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.JsonPose;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.JsonPoseableEntityModel;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.PoseableEntityModel;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.animation.StatefulAnimation;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.frame.ModelFrame;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.pose.Bone;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.pose.Pose;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.quirk.ModelQuirk;
import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;
import com.google.gson.InstanceCreator;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.function.Supplier;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.TuplesKt;
import kotlin.collections.CollectionsKt;
import kotlin.collections.MapsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import kotlin.text.StringsKt;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.world.entity.Entity;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u000b\b&\u0018\u0000*\b\b\u0000\u0010\u0002*\u00020\u00012\b\u0012\u0004\u0012\u00028\u00000\u0003:\u0004\u000e\u000f\u0010\u0011B\u000f\u0012\u0006\u0010\b\u001a\u00020\u0007\u00a2\u0006\u0004\b\f\u0010\rJ\u000f\u0010\u0005\u001a\u00020\u0004H\u0016\u00a2\u0006\u0004\b\u0005\u0010\u0006R\u001a\u0010\b\u001a\u00020\u00078\u0016X\u0096\u0004\u00a2\u0006\f\n\u0004\b\b\u0010\t\u001a\u0004\b\n\u0010\u000b\u00a8\u0006\u0012"}, d2={"Lcom/cobblemon/mod/common/client/render/models/blockbench/JsonPoseableEntityModel;", "Lnet/minecraft/world/entity/Entity;", "T", "Lcom/cobblemon/mod/common/client/render/models/blockbench/PoseableEntityModel;", "", "registerPoses", "()V", "Lcom/cobblemon/mod/common/client/render/models/blockbench/pose/Bone;", "rootPart", "Lcom/cobblemon/mod/common/client/render/models/blockbench/pose/Bone;", "getRootPart", "()Lcom/cobblemon/mod/common/client/render/models/blockbench/pose/Bone;", "<init>", "(Lcom/cobblemon/mod/common/client/render/models/blockbench/pose/Bone;)V", "JsonModelExclusion", "JsonPoseableModelAdapter", "PoseAdapter", "StatefulAnimationAdapter", "common"})
public abstract class JsonPoseableEntityModel<T extends Entity>
extends PoseableEntityModel<T> {
    @NotNull
    private final Bone rootPart;

    public JsonPoseableEntityModel(@NotNull Bone rootPart) {
        Intrinsics.checkNotNullParameter((Object)rootPart, (String)"rootPart");
        super(null, 1, null);
        this.rootPart = rootPart;
    }

    @Override
    @NotNull
    public Bone getRootPart() {
        return this.rootPart;
    }

    @Override
    public void registerPoses() {
    }

    @Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0006\b\u00c6\u0002\u0018\u00002\u00020\u0001B\t\b\u0002\u00a2\u0006\u0004\b\u000b\u0010\fJ\u001b\u0010\u0005\u001a\u00020\u00042\n\u0010\u0003\u001a\u0006\u0012\u0002\b\u00030\u0002H\u0016\u00a2\u0006\u0004\b\u0005\u0010\u0006J\u0017\u0010\t\u001a\u00020\u00042\u0006\u0010\b\u001a\u00020\u0007H\u0016\u00a2\u0006\u0004\b\t\u0010\n\u00a8\u0006\r"}, d2={"Lcom/cobblemon/mod/common/client/render/models/blockbench/JsonPoseableEntityModel$JsonModelExclusion;", "Lcom/google/gson/ExclusionStrategy;", "Ljava/lang/Class;", "clazz", "", "shouldSkipClass", "(Ljava/lang/Class;)Z", "Lcom/google/gson/FieldAttributes;", "f", "shouldSkipField", "(Lcom/google/gson/FieldAttributes;)Z", "<init>", "()V", "common"})
    public static final class JsonModelExclusion
    implements ExclusionStrategy {
        @NotNull
        public static final JsonModelExclusion INSTANCE = new JsonModelExclusion();

        private JsonModelExclusion() {
        }

        public boolean shouldSkipField(@NotNull FieldAttributes f) {
            Intrinsics.checkNotNullParameter((Object)f, (String)"f");
            Object[] objectArray = new String[]{"JsonPokemonPoseableModel", "JsonGenericPoseableModel", "PoseableEntityModel", "Pose"};
            return !CollectionsKt.listOf((Object[])objectArray).contains(f.getDeclaringClass().getSimpleName());
        }

        public boolean shouldSkipClass(@NotNull Class<?> clazz) {
            Intrinsics.checkNotNullParameter(clazz, (String)"clazz");
            return false;
        }
    }

    @Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u00000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0014\u0018\u0000*\b\b\u0001\u0010\u0002*\u00020\u00012\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00028\u00010\u00040\u0003B0\u0012'\u0010\u000f\u001a#\u0012\u0013\u0012\u00110\u000b\u00a2\u0006\f\b\f\u0012\b\b\r\u0012\u0004\b\b(\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00028\u00010\u00070\n\u00a2\u0006\u0004\b\u001e\u0010\u001fJ\u001d\u0010\b\u001a\b\u0012\u0004\u0012\u00028\u00010\u00072\u0006\u0010\u0006\u001a\u00020\u0005H\u0016\u00a2\u0006\u0004\b\b\u0010\tR8\u0010\u000f\u001a#\u0012\u0013\u0012\u00110\u000b\u00a2\u0006\f\b\f\u0012\b\b\r\u0012\u0004\b\b(\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00028\u00010\u00070\n8\u0006\u00a2\u0006\f\n\u0004\b\u000f\u0010\u0010\u001a\u0004\b\u0011\u0010\u0012R*\u0010\u0013\u001a\n\u0012\u0004\u0012\u00028\u0001\u0018\u00010\u00078\u0006@\u0006X\u0086\u000e\u00a2\u0006\u0012\n\u0004\b\u0013\u0010\u0014\u001a\u0004\b\u0015\u0010\u0016\"\u0004\b\u0017\u0010\u0018R$\u0010\u000e\u001a\u0004\u0018\u00010\u000b8\u0006@\u0006X\u0086\u000e\u00a2\u0006\u0012\n\u0004\b\u000e\u0010\u0019\u001a\u0004\b\u001a\u0010\u001b\"\u0004\b\u001c\u0010\u001d\u00a8\u0006 "}, d2={"Lcom/cobblemon/mod/common/client/render/models/blockbench/JsonPoseableEntityModel$JsonPoseableModelAdapter;", "Lnet/minecraft/world/entity/Entity;", "T", "Lcom/google/gson/InstanceCreator;", "Lcom/cobblemon/mod/common/client/render/models/blockbench/PoseableEntityModel;", "Ljava/lang/reflect/Type;", "type", "Lcom/cobblemon/mod/common/client/render/models/blockbench/JsonPoseableEntityModel;", "createInstance", "(Ljava/lang/reflect/Type;)Lcom/cobblemon/mod/common/client/render/models/blockbench/JsonPoseableEntityModel;", "Lkotlin/Function1;", "Lnet/minecraft/client/model/geom/ModelPart;", "Lkotlin/ParameterName;", "name", "modelPart", "constructor", "Lkotlin/jvm/functions/Function1;", "getConstructor", "()Lkotlin/jvm/functions/Function1;", "model", "Lcom/cobblemon/mod/common/client/render/models/blockbench/JsonPoseableEntityModel;", "getModel", "()Lcom/cobblemon/mod/common/client/render/models/blockbench/JsonPoseableEntityModel;", "setModel", "(Lcom/cobblemon/mod/common/client/render/models/blockbench/JsonPoseableEntityModel;)V", "Lnet/minecraft/client/model/geom/ModelPart;", "getModelPart", "()Lnet/minecraft/client/model/geom/ModelPart;", "setModelPart", "(Lnet/minecraft/client/model/geom/ModelPart;)V", "<init>", "(Lkotlin/jvm/functions/Function1;)V", "common"})
    public static final class JsonPoseableModelAdapter<T extends Entity>
    implements InstanceCreator<PoseableEntityModel<T>> {
        @NotNull
        private final Function1<ModelPart, JsonPoseableEntityModel<T>> constructor;
        @Nullable
        private ModelPart modelPart;
        @Nullable
        private JsonPoseableEntityModel<T> model;

        public JsonPoseableModelAdapter(@NotNull Function1<? super ModelPart, ? extends JsonPoseableEntityModel<T>> constructor) {
            Intrinsics.checkNotNullParameter(constructor, (String)"constructor");
            this.constructor = constructor;
        }

        @NotNull
        public final Function1<ModelPart, JsonPoseableEntityModel<T>> getConstructor() {
            return this.constructor;
        }

        @Nullable
        public final ModelPart getModelPart() {
            return this.modelPart;
        }

        public final void setModelPart(@Nullable ModelPart modelPart) {
            this.modelPart = modelPart;
        }

        @Nullable
        public final JsonPoseableEntityModel<T> getModel() {
            return this.model;
        }

        public final void setModel(@Nullable JsonPoseableEntityModel<T> jsonPoseableEntityModel) {
            this.model = jsonPoseableEntityModel;
        }

        @NotNull
        public JsonPoseableEntityModel<T> createInstance(@NotNull Type type) {
            Intrinsics.checkNotNullParameter((Object)type, (String)"type");
            ModelPart modelPart = this.modelPart;
            Intrinsics.checkNotNull((Object)modelPart);
            Object object = this.constructor.invoke((Object)modelPart);
            JsonPoseableEntityModel it = (JsonPoseableEntityModel)object;
            boolean bl = false;
            this.model = it;
            ModelPart modelPart2 = this.modelPart;
            Intrinsics.checkNotNull((Object)modelPart2);
            it.loadAllNamedChildren(modelPart2);
            return (JsonPoseableEntityModel)object;
        }
    }

    @Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u00006\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0007\u0018\u0000*\b\b\u0001\u0010\u0002*\u00020\u00012\u0014\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u00028\u0001\u0012\u0004\u0012\u00020\u00050\u00040\u0003B\u001b\u0012\u0012\u0010\u0010\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00028\u00010\u000f0\u000e\u00a2\u0006\u0004\b\u0014\u0010\u0015J3\u0010\f\u001a\u000e\u0012\u0004\u0012\u00028\u0001\u0012\u0004\u0012\u00020\u00050\u00042\u0006\u0010\u0007\u001a\u00020\u00062\u0006\u0010\t\u001a\u00020\b2\u0006\u0010\u000b\u001a\u00020\nH\u0016\u00a2\u0006\u0004\b\f\u0010\rR#\u0010\u0010\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00028\u00010\u000f0\u000e8\u0006\u00a2\u0006\f\n\u0004\b\u0010\u0010\u0011\u001a\u0004\b\u0012\u0010\u0013\u00a8\u0006\u0016"}, d2={"Lcom/cobblemon/mod/common/client/render/models/blockbench/JsonPoseableEntityModel$PoseAdapter;", "Lnet/minecraft/world/entity/Entity;", "T", "Lcom/google/gson/JsonDeserializer;", "Lcom/cobblemon/mod/common/client/render/models/blockbench/pose/Pose;", "Lcom/cobblemon/mod/common/client/render/models/blockbench/frame/ModelFrame;", "Lcom/google/gson/JsonElement;", "json", "Ljava/lang/reflect/Type;", "type", "Lcom/google/gson/JsonDeserializationContext;", "ctx", "deserialize", "(Lcom/google/gson/JsonElement;Ljava/lang/reflect/Type;Lcom/google/gson/JsonDeserializationContext;)Lcom/cobblemon/mod/common/client/render/models/blockbench/pose/Pose;", "Lkotlin/Function0;", "Lcom/cobblemon/mod/common/client/render/models/blockbench/PoseableEntityModel;", "modelFinder", "Lkotlin/jvm/functions/Function0;", "getModelFinder", "()Lkotlin/jvm/functions/Function0;", "<init>", "(Lkotlin/jvm/functions/Function0;)V", "common"})
    @SourceDebugExtension(value={"SMAP\nJsonPoseableEntityModel.kt\nKotlin\n*S Kotlin\n*F\n+ 1 JsonPoseableEntityModel.kt\ncom/cobblemon/mod/common/client/render/models/blockbench/JsonPoseableEntityModel$PoseAdapter\n+ 2 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n+ 3 ArraysJVM.kt\nkotlin/collections/ArraysKt__ArraysJVMKt\n+ 4 fake.kt\nkotlin/jvm/internal/FakeKt\n*L\n1#1,100:1\n2661#2,7:101\n1603#2,9:110\n1855#2:119\n1856#2:121\n1612#2:122\n37#3,2:108\n1#4:120\n*S KotlinDebug\n*F\n+ 1 JsonPoseableEntityModel.kt\ncom/cobblemon/mod/common/client/render/models/blockbench/JsonPoseableEntityModel$PoseAdapter\n*L\n80#1:101,7\n93#1:110,9\n93#1:119\n93#1:121\n93#1:122\n89#1:108,2\n93#1:120\n*E\n"})
    public static final class PoseAdapter<T extends Entity>
    implements JsonDeserializer<Pose<T, ModelFrame>> {
        @NotNull
        private final Function0<PoseableEntityModel<T>> modelFinder;

        public PoseAdapter(@NotNull Function0<? extends PoseableEntityModel<T>> modelFinder) {
            Intrinsics.checkNotNullParameter(modelFinder, (String)"modelFinder");
            this.modelFinder = modelFinder;
        }

        @NotNull
        public final Function0<PoseableEntityModel<T>> getModelFinder() {
            return this.modelFinder;
        }

        /*
         * WARNING - void declaration
         */
        @NotNull
        public Pose<T, ModelFrame> deserialize(@NotNull JsonElement json, @NotNull Type type, @NotNull JsonDeserializationContext ctx) {
            void $this$mapNotNullTo$iv$iv;
            void $this$mapNotNull$iv;
            Pose pose;
            Object object;
            Object accumulator$iv;
            Function1 function1;
            Boolean mustBeTouchingWater;
            Intrinsics.checkNotNullParameter((Object)json, (String)"json");
            Intrinsics.checkNotNullParameter((Object)type, (String)"type");
            Intrinsics.checkNotNullParameter((Object)ctx, (String)"ctx");
            PoseableEntityModel model = (PoseableEntityModel)this.modelFinder.invoke();
            JsonObject obj = (JsonObject)json;
            JsonPose pose2 = new JsonPose(model, obj);
            List conditionsList = new ArrayList();
            JsonElement jsonElement = ((JsonObject)json).get("isTouchingWater");
            Boolean bl = mustBeTouchingWater = jsonElement != null ? Boolean.valueOf(jsonElement.getAsBoolean()) : null;
            if (mustBeTouchingWater != null) {
                conditionsList.add(new Function1<T, Boolean>(mustBeTouchingWater){
                    final /* synthetic */ Boolean $mustBeTouchingWater;
                    {
                        this.$mustBeTouchingWater = $mustBeTouchingWater;
                        super(1);
                    }

                    @NotNull
                    public final Boolean invoke(@NotNull T it) {
                        Intrinsics.checkNotNullParameter(it, (String)"it");
                        return Intrinsics.areEqual((Object)this.$mustBeTouchingWater, (Object)it.m_20069_());
                    }
                });
            }
            if (conditionsList.isEmpty()) {
                function1 = deserialize.poseCondition.1.INSTANCE;
            } else {
                Iterable $this$reduce$iv = conditionsList;
                boolean $i$f$reduce = false;
                Iterator iterator$iv = $this$reduce$iv.iterator();
                if (!iterator$iv.hasNext()) {
                    throw new UnsupportedOperationException("Empty collection can't be reduced.");
                }
                accumulator$iv = iterator$iv.next();
                while (iterator$iv.hasNext()) {
                    void function;
                    object = (Function1)iterator$iv.next();
                    Function1 acc = (Function1)accumulator$iv;
                    boolean bl2 = false;
                    accumulator$iv = new Function1<T, Boolean>(acc, function){
                        final /* synthetic */ Function1<T, Boolean> $acc;
                        final /* synthetic */ Function1<T, Boolean> $function;
                        {
                            this.$acc = $acc;
                            this.$function = $function;
                            super(1);
                        }

                        @NotNull
                        public final Boolean invoke(@NotNull T it) {
                            Intrinsics.checkNotNullParameter(it, (String)"it");
                            return (Boolean)this.$acc.invoke(it) != false && (Boolean)this.$function.invoke(it) != false;
                        }
                    };
                }
                function1 = (Function1)accumulator$iv;
            }
            Function1 poseCondition2 = function1;
            Collection $this$toTypedArray$iv = pose2.getQuirks();
            boolean $i$f$toTypedArray = false;
            Collection thisCollection$iv = $this$toTypedArray$iv;
            Pose it = pose = new Pose(pose2.getPoseName(), CollectionsKt.toSet((Iterable)pose2.getPoseTypes()), poseCondition2, null, pose2.getTransformTicks(), null, pose2.getIdleAnimations(), pose2.getTransformedParts(), thisCollection$iv.toArray(new ModelQuirk[0]), 40, null);
            boolean bl3 = false;
            accumulator$iv = pose2.getTransitions();
            object = it.getTransitions();
            boolean $i$f$mapNotNull = false;
            void bl2 = $this$mapNotNull$iv;
            Collection destination$iv$iv = new ArrayList();
            boolean $i$f$mapNotNullTo = false;
            void $this$forEach$iv$iv$iv = $this$mapNotNullTo$iv$iv;
            boolean $i$f$forEach = false;
            Iterator iterator = $this$forEach$iv$iv$iv.iterator();
            while (iterator.hasNext()) {
                Pair it$iv$iv;
                Object element$iv$iv$iv;
                Object element$iv$iv = element$iv$iv$iv = iterator.next();
                boolean bl4 = false;
                JsonPose.JsonPoseTransition it2 = (JsonPose.JsonPoseTransition)element$iv$iv;
                boolean bl5 = false;
                if (TuplesKt.to((Object)it2.getTo(), (Object)new Function2<Pose<T, ? extends ModelFrame>, Pose<T, ? extends ModelFrame>, StatefulAnimation<T, ModelFrame>>(it2, model){
                    final /* synthetic */ JsonPose.JsonPoseTransition $it;
                    final /* synthetic */ PoseableEntityModel<T> $model;
                    {
                        this.$it = $it;
                        this.$model = $model;
                        super(2);
                    }

                    @NotNull
                    public final StatefulAnimation<T, ModelFrame> invoke(@NotNull Pose<T, ? extends ModelFrame> pose, @NotNull Pose<T, ? extends ModelFrame> pose2) {
                        Intrinsics.checkNotNullParameter(pose, (String)"<anonymous parameter 0>");
                        Intrinsics.checkNotNullParameter(pose2, (String)"<anonymous parameter 1>");
                        ? obj = this.$it.getAnimation().resolveObject(this.$model.getRuntime()).getObj();
                        Intrinsics.checkNotNull(obj, (String)"null cannot be cast to non-null type bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.animation.StatefulAnimation<T of bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.JsonPoseableEntityModel.PoseAdapter.deserialize$lambda$2$lambda$1, bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.frame.ModelFrame>");
                        return (StatefulAnimation)obj;
                    }
                }) == null) continue;
                boolean bl6 = false;
                destination$iv$iv.add(it$iv$iv);
            }
            object.putAll(MapsKt.toMap((Iterable)((List)destination$iv$iv)));
            return pose;
        }
    }

    @Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000:\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0007\u0018\u0000*\b\b\u0001\u0010\u0002*\u00020\u00012\u001a\u0012\u0016\u0012\u0014\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u00028\u0001\u0012\u0004\u0012\u00020\u00060\u00050\u00040\u0003B\u001b\u0012\u0012\u0010\u0011\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00028\u00010\u00100\u000f\u00a2\u0006\u0004\b\u0015\u0010\u0016J9\u0010\r\u001a\u0014\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u00028\u0001\u0012\u0004\u0012\u00020\u00060\u00050\u00042\u0006\u0010\b\u001a\u00020\u00072\u0006\u0010\n\u001a\u00020\t2\u0006\u0010\f\u001a\u00020\u000bH\u0016\u00a2\u0006\u0004\b\r\u0010\u000eR#\u0010\u0011\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00028\u00010\u00100\u000f8\u0006\u00a2\u0006\f\n\u0004\b\u0011\u0010\u0012\u001a\u0004\b\u0013\u0010\u0014\u00a8\u0006\u0017"}, d2={"Lcom/cobblemon/mod/common/client/render/models/blockbench/JsonPoseableEntityModel$StatefulAnimationAdapter;", "Lnet/minecraft/world/entity/Entity;", "T", "Lcom/google/gson/JsonDeserializer;", "Ljava/util/function/Supplier;", "Lcom/cobblemon/mod/common/client/render/models/blockbench/animation/StatefulAnimation;", "Lcom/cobblemon/mod/common/client/render/models/blockbench/frame/ModelFrame;", "Lcom/google/gson/JsonElement;", "json", "Ljava/lang/reflect/Type;", "type", "Lcom/google/gson/JsonDeserializationContext;", "ctx", "deserialize", "(Lcom/google/gson/JsonElement;Ljava/lang/reflect/Type;Lcom/google/gson/JsonDeserializationContext;)Ljava/util/function/Supplier;", "Lkotlin/Function0;", "Lcom/cobblemon/mod/common/client/render/models/blockbench/PoseableEntityModel;", "modelFinder", "Lkotlin/jvm/functions/Function0;", "getModelFinder", "()Lkotlin/jvm/functions/Function0;", "<init>", "(Lkotlin/jvm/functions/Function0;)V", "common"})
    @SourceDebugExtension(value={"SMAP\nJsonPoseableEntityModel.kt\nKotlin\n*S Kotlin\n*F\n+ 1 JsonPoseableEntityModel.kt\ncom/cobblemon/mod/common/client/render/models/blockbench/JsonPoseableEntityModel$StatefulAnimationAdapter\n+ 2 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n*L\n1#1,100:1\n1549#2:101\n1620#2,3:102\n*S KotlinDebug\n*F\n+ 1 JsonPoseableEntityModel.kt\ncom/cobblemon/mod/common/client/render/models/blockbench/JsonPoseableEntityModel$StatefulAnimationAdapter\n*L\n61#1:101\n61#1:102,3\n*E\n"})
    public static final class StatefulAnimationAdapter<T extends Entity>
    implements JsonDeserializer<Supplier<StatefulAnimation<T, ModelFrame>>> {
        @NotNull
        private final Function0<PoseableEntityModel<T>> modelFinder;

        public StatefulAnimationAdapter(@NotNull Function0<? extends PoseableEntityModel<T>> modelFinder) {
            Intrinsics.checkNotNullParameter(modelFinder, (String)"modelFinder");
            this.modelFinder = modelFinder;
        }

        @NotNull
        public final Function0<PoseableEntityModel<T>> getModelFinder() {
            return this.modelFinder;
        }

        /*
         * WARNING - void declaration
         */
        @NotNull
        public Supplier<StatefulAnimation<T, ModelFrame>> deserialize(@NotNull JsonElement json, @NotNull Type type, @NotNull JsonDeserializationContext ctx) {
            void $this$mapTo$iv$iv;
            Intrinsics.checkNotNullParameter((Object)json, (String)"json");
            Intrinsics.checkNotNullParameter((Object)type, (String)"type");
            Intrinsics.checkNotNullParameter((Object)ctx, (String)"ctx");
            JsonPrimitive cfr_ignored_0 = (JsonPrimitive)json;
            String animString = ((JsonPrimitive)json).getAsString();
            Intrinsics.checkNotNullExpressionValue((Object)animString, (String)"animString");
            String[] stringArray = new String[]{","};
            Iterable $this$map$iv = StringsKt.split$default((CharSequence)StringsKt.replace$default((String)StringsKt.replace$default((String)animString, (String)"bedrock(", (String)"", (boolean)false, (int)4, null), (String)")", (String)"", (boolean)false, (int)4, null), (String[])stringArray, (boolean)false, (int)0, (int)6, null);
            boolean $i$f$map = false;
            Iterable iterable = $this$map$iv;
            Collection destination$iv$iv = new ArrayList(CollectionsKt.collectionSizeOrDefault((Iterable)$this$map$iv, (int)10));
            boolean $i$f$mapTo = false;
            for (Object item$iv$iv : $this$mapTo$iv$iv) {
                void p0;
                String string = (String)item$iv$iv;
                Collection collection = destination$iv$iv;
                boolean bl = false;
                collection.add(((Object)StringsKt.trim((CharSequence)((CharSequence)p0))).toString());
            }
            List splits = (List)destination$iv$iv;
            String file = (String)splits.get(0);
            String animation = (String)splits.get(1);
            return () -> StatefulAnimationAdapter.deserialize$lambda$0(this, file, animation);
        }

        private static final StatefulAnimation deserialize$lambda$0(StatefulAnimationAdapter this$0, String $file, String $animation) {
            Intrinsics.checkNotNullParameter((Object)this$0, (String)"this$0");
            Intrinsics.checkNotNullParameter((Object)$file, (String)"$file");
            Intrinsics.checkNotNullParameter((Object)$animation, (String)"$animation");
            return PoseableEntityModel.bedrockStateful$default((PoseableEntityModel)this$0.modelFinder.invoke(), $file, $animation, null, 4, null);
        }
    }
}

