/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  kotlin.Metadata
 *  kotlin.Unit
 *  kotlin.jvm.functions.Function1
 *  kotlin.jvm.functions.Function2
 *  kotlin.jvm.internal.DefaultConstructorMarker
 *  kotlin.jvm.internal.Intrinsics
 *  kotlin.jvm.internal.SourceDebugExtension
 *  net.minecraft.world.entity.Entity
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.pose;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.molang.ExpressionLike;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.PoseableEntityModel;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.PoseableEntityState;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.animation.StatefulAnimation;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.animation.StatelessAnimation;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.frame.ModelFrame;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.pose.ModelPartTransformation;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.quirk.ModelQuirk;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.entity.PoseType;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import net.minecraft.world.entity.Entity;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000\u008e\u0001\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0007\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0002\b\u0005\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010%\n\u0002\u0010\u000e\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u0011\n\u0002\u0018\u0002\n\u0002\b\f\n\u0002\u0010\"\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\b\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\u0018\u0000*\b\b\u0000\u0010\u0002*\u00020\u0001*\b\b\u0001\u0010\u0004*\u00020\u00032\u00020\u0005B\u00b1\u0001\u0012\u0006\u0010.\u001a\u00020\u001b\u0012\f\u00106\u001a\b\u0012\u0004\u0012\u00020504\u0012\u0014\u0010\"\u001a\u0010\u0012\u0004\u0012\u00028\u0000\u0012\u0004\u0012\u00020\u0017\u0018\u00010!\u0012\u001c\b\u0002\u0010,\u001a\u0016\u0012\f\u0012\n\u0012\u0004\u0012\u00028\u0000\u0018\u00010\t\u0012\u0004\u0012\u00020\u00110!\u0012\u0006\u0010@\u001a\u00020?\u0012\u0014\b\u0002\u0010\u001d\u001a\u000e\u0012\u0004\u0012\u00020\u001b\u0012\u0004\u0012\u00020\u001c0\u001a\u0012\u001a\u0010(\u001a\u0016\u0012\u0012\u0012\u0010\u0012\u0004\u0012\u00028\u0000\u0012\u0006\b\u0001\u0012\u00028\u00010'0&\u0012\f\u0010E\u001a\b\u0012\u0004\u0012\u00020D0&\u0012\u0016\u0010;\u001a\u0012\u0012\u000e\u0012\f\u0012\u0004\u0012\u00028\u0000\u0012\u0002\b\u00030:0&\u00a2\u0006\u0004\bM\u0010NJ[\u0010\u0012\u001a\u00020\u00112\b\u0010\u0006\u001a\u0004\u0018\u00018\u00002\f\u0010\b\u001a\b\u0012\u0004\u0012\u00028\u00000\u00072\f\u0010\n\u001a\b\u0012\u0004\u0012\u00028\u00000\t2\u0006\u0010\f\u001a\u00020\u000b2\u0006\u0010\r\u001a\u00020\u000b2\u0006\u0010\u000e\u001a\u00020\u000b2\u0006\u0010\u000f\u001a\u00020\u000b2\u0006\u0010\u0010\u001a\u00020\u000b\u00a2\u0006\u0004\b\u0012\u0010\u0013Je\u0010\u0015\u001a\u00020\u00112\f\u0010\b\u001a\b\u0012\u0004\u0012\u00028\u00000\u00072\u000e\u0010\n\u001a\n\u0012\u0004\u0012\u00028\u0000\u0018\u00010\t2\b\b\u0002\u0010\f\u001a\u00020\u000b2\b\b\u0002\u0010\r\u001a\u00020\u000b2\b\b\u0002\u0010\u000e\u001a\u00020\u000b2\b\b\u0002\u0010\u000f\u001a\u00020\u000b2\b\b\u0002\u0010\u0010\u001a\u00020\u000b2\u0006\u0010\u0014\u001a\u00020\u000b\u00a2\u0006\u0004\b\u0015\u0010\u0016J\u0015\u0010\u0018\u001a\u00020\u00172\u0006\u0010\u0006\u001a\u00028\u0000\u00a2\u0006\u0004\b\u0018\u0010\u0019R#\u0010\u001d\u001a\u000e\u0012\u0004\u0012\u00020\u001b\u0012\u0004\u0012\u00020\u001c0\u001a8\u0006\u00a2\u0006\f\n\u0004\b\u001d\u0010\u001e\u001a\u0004\b\u001f\u0010 R%\u0010\"\u001a\u0010\u0012\u0004\u0012\u00028\u0000\u0012\u0004\u0012\u00020\u0017\u0018\u00010!8\u0006\u00a2\u0006\f\n\u0004\b\"\u0010#\u001a\u0004\b$\u0010%R+\u0010(\u001a\u0016\u0012\u0012\u0012\u0010\u0012\u0004\u0012\u00028\u0000\u0012\u0006\b\u0001\u0012\u00028\u00010'0&8\u0006\u00a2\u0006\f\n\u0004\b(\u0010)\u001a\u0004\b*\u0010+R+\u0010,\u001a\u0016\u0012\f\u0012\n\u0012\u0004\u0012\u00028\u0000\u0018\u00010\t\u0012\u0004\u0012\u00020\u00110!8\u0006\u00a2\u0006\f\n\u0004\b,\u0010#\u001a\u0004\b-\u0010%R\"\u0010.\u001a\u00020\u001b8\u0006@\u0006X\u0086\u000e\u00a2\u0006\u0012\n\u0004\b.\u0010/\u001a\u0004\b0\u00101\"\u0004\b2\u00103R\u001d\u00106\u001a\b\u0012\u0004\u0012\u000205048\u0006\u00a2\u0006\f\n\u0004\b6\u00107\u001a\u0004\b8\u00109R'\u0010;\u001a\u0012\u0012\u000e\u0012\f\u0012\u0004\u0012\u00028\u0000\u0012\u0002\b\u00030:0&8\u0006\u00a2\u0006\f\n\u0004\b;\u0010<\u001a\u0004\b=\u0010>R\u0017\u0010@\u001a\u00020?8\u0006\u00a2\u0006\f\n\u0004\b@\u0010A\u001a\u0004\bB\u0010CR\u001d\u0010E\u001a\b\u0012\u0004\u0012\u00020D0&8\u0006\u00a2\u0006\f\n\u0004\bE\u0010F\u001a\u0004\bG\u0010HR]\u0010K\u001aH\u0012\u0004\u0012\u00020\u001b\u0012>\u0012<\u0012\u0012\u0012\u0010\u0012\u0004\u0012\u00028\u0000\u0012\u0006\b\u0001\u0012\u00020\u00030\u0000\u0012\u0012\u0012\u0010\u0012\u0004\u0012\u00028\u0000\u0012\u0006\b\u0001\u0012\u00020\u00030\u0000\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u00028\u0000\u0012\u0004\u0012\u00020\u00030J0I0\u001a8\u0006\u00a2\u0006\f\n\u0004\bK\u0010\u001e\u001a\u0004\bL\u0010 \u00a8\u0006O"}, d2={"Lcom/cobblemon/mod/common/client/render/models/blockbench/pose/Pose;", "Lnet/minecraft/world/entity/Entity;", "T", "Lcom/cobblemon/mod/common/client/render/models/blockbench/frame/ModelFrame;", "F", "", "entity", "Lcom/cobblemon/mod/common/client/render/models/blockbench/PoseableEntityModel;", "model", "Lcom/cobblemon/mod/common/client/render/models/blockbench/PoseableEntityState;", "state", "", "limbSwing", "limbSwingAmount", "ageInTicks", "headYaw", "headPitch", "", "idleStateful", "(Lnet/minecraft/world/entity/Entity;Lcom/cobblemon/mod/common/client/render/models/blockbench/PoseableEntityModel;Lcom/cobblemon/mod/common/client/render/models/blockbench/PoseableEntityState;FFFFF)V", "intensity", "idleStateless", "(Lcom/cobblemon/mod/common/client/render/models/blockbench/PoseableEntityModel;Lcom/cobblemon/mod/common/client/render/models/blockbench/PoseableEntityState;FFFFFF)V", "", "isSuitable", "(Lnet/minecraft/world/entity/Entity;)Z", "", "", "Lcom/cobblemon/mod/common/api/molang/ExpressionLike;", "animations", "Ljava/util/Map;", "getAnimations", "()Ljava/util/Map;", "Lkotlin/Function1;", "condition", "Lkotlin/jvm/functions/Function1;", "getCondition", "()Lkotlin/jvm/functions/Function1;", "", "Lcom/cobblemon/mod/common/client/render/models/blockbench/animation/StatelessAnimation;", "idleAnimations", "[Lcom/cobblemon/mod/common/client/render/models/blockbench/animation/StatelessAnimation;", "getIdleAnimations", "()[Lcom/cobblemon/mod/common/client/render/models/blockbench/animation/StatelessAnimation;", "onTransitionedInto", "getOnTransitionedInto", "poseName", "Ljava/lang/String;", "getPoseName", "()Ljava/lang/String;", "setPoseName", "(Ljava/lang/String;)V", "", "Lcom/cobblemon/mod/common/entity/PoseType;", "poseTypes", "Ljava/util/Set;", "getPoseTypes", "()Ljava/util/Set;", "Lcom/cobblemon/mod/common/client/render/models/blockbench/quirk/ModelQuirk;", "quirks", "[Lcom/cobblemon/mod/common/client/render/models/blockbench/quirk/ModelQuirk;", "getQuirks", "()[Lcom/cobblemon/mod/common/client/render/models/blockbench/quirk/ModelQuirk;", "", "transformTicks", "I", "getTransformTicks", "()I", "Lcom/cobblemon/mod/common/client/render/models/blockbench/pose/ModelPartTransformation;", "transformedParts", "[Lcom/cobblemon/mod/common/client/render/models/blockbench/pose/ModelPartTransformation;", "getTransformedParts", "()[Lcom/cobblemon/mod/common/client/render/models/blockbench/pose/ModelPartTransformation;", "Lkotlin/Function2;", "Lcom/cobblemon/mod/common/client/render/models/blockbench/animation/StatefulAnimation;", "transitions", "getTransitions", "<init>", "(Ljava/lang/String;Ljava/util/Set;Lkotlin/jvm/functions/Function1;Lkotlin/jvm/functions/Function1;ILjava/util/Map;[Lcom/cobblemon/mod/common/client/render/models/blockbench/animation/StatelessAnimation;[Lcom/cobblemon/mod/common/client/render/models/blockbench/pose/ModelPartTransformation;[Lcom/cobblemon/mod/common/client/render/models/blockbench/quirk/ModelQuirk;)V", "common"})
@SourceDebugExtension(value={"SMAP\nPose.kt\nKotlin\n*S Kotlin\n*F\n+ 1 Pose.kt\ncom/cobblemon/mod/common/client/render/models/blockbench/pose/Pose\n+ 2 _Arrays.kt\nkotlin/collections/ArraysKt___ArraysKt\n+ 3 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n*L\n1#1,48:1\n13579#2,2:49\n3792#2:51\n4307#2,2:52\n1855#3,2:54\n*S KotlinDebug\n*F\n+ 1 Pose.kt\ncom/cobblemon/mod/common/client/render/models/blockbench/pose/Pose\n*L\n40#1:49,2\n44#1:51\n44#1:52,2\n44#1:54,2\n*E\n"})
public final class Pose<T extends Entity, F extends ModelFrame> {
    @NotNull
    private String poseName;
    @NotNull
    private final Set<PoseType> poseTypes;
    @Nullable
    private final Function1<T, Boolean> condition;
    @NotNull
    private final Function1<PoseableEntityState<T>, Unit> onTransitionedInto;
    private final int transformTicks;
    @NotNull
    private final Map<String, ExpressionLike> animations;
    @NotNull
    private final StatelessAnimation<T, ? extends F>[] idleAnimations;
    @NotNull
    private final ModelPartTransformation[] transformedParts;
    @NotNull
    private final ModelQuirk<T, ?>[] quirks;
    @NotNull
    private final Map<String, Function2<Pose<T, ? extends ModelFrame>, Pose<T, ? extends ModelFrame>, StatefulAnimation<T, ModelFrame>>> transitions;

    public Pose(@NotNull String poseName, @NotNull Set<? extends PoseType> poseTypes, @Nullable Function1<? super T, Boolean> condition2, @NotNull Function1<? super PoseableEntityState<T>, Unit> onTransitionedInto, int transformTicks, @NotNull Map<String, ExpressionLike> animations2, @NotNull StatelessAnimation<T, ? extends F>[] idleAnimations2, @NotNull ModelPartTransformation[] transformedParts, @NotNull ModelQuirk<T, ?>[] quirks2) {
        Intrinsics.checkNotNullParameter((Object)poseName, (String)"poseName");
        Intrinsics.checkNotNullParameter(poseTypes, (String)"poseTypes");
        Intrinsics.checkNotNullParameter(onTransitionedInto, (String)"onTransitionedInto");
        Intrinsics.checkNotNullParameter(animations2, (String)"animations");
        Intrinsics.checkNotNullParameter(idleAnimations2, (String)"idleAnimations");
        Intrinsics.checkNotNullParameter((Object)transformedParts, (String)"transformedParts");
        Intrinsics.checkNotNullParameter(quirks2, (String)"quirks");
        this.poseName = poseName;
        this.poseTypes = poseTypes;
        this.condition = condition2;
        this.onTransitionedInto = onTransitionedInto;
        this.transformTicks = transformTicks;
        this.animations = animations2;
        this.idleAnimations = idleAnimations2;
        this.transformedParts = transformedParts;
        this.quirks = quirks2;
        this.transitions = new LinkedHashMap();
    }

    public /* synthetic */ Pose(String string, Set set2, Function1 function1, Function1 function12, int n, Map map, StatelessAnimation[] statelessAnimationArray, ModelPartTransformation[] modelPartTransformationArray, ModelQuirk[] modelQuirkArray, int n2, DefaultConstructorMarker defaultConstructorMarker) {
        if ((n2 & 8) != 0) {
            function12 = 1.INSTANCE;
        }
        if ((n2 & 0x20) != 0) {
            map = new LinkedHashMap();
        }
        this(string, set2, function1, function12, n, map, statelessAnimationArray, modelPartTransformationArray, modelQuirkArray);
    }

    @NotNull
    public final String getPoseName() {
        return this.poseName;
    }

    public final void setPoseName(@NotNull String string) {
        Intrinsics.checkNotNullParameter((Object)string, (String)"<set-?>");
        this.poseName = string;
    }

    @NotNull
    public final Set<PoseType> getPoseTypes() {
        return this.poseTypes;
    }

    @Nullable
    public final Function1<T, Boolean> getCondition() {
        return this.condition;
    }

    @NotNull
    public final Function1<PoseableEntityState<T>, Unit> getOnTransitionedInto() {
        return this.onTransitionedInto;
    }

    public final int getTransformTicks() {
        return this.transformTicks;
    }

    @NotNull
    public final Map<String, ExpressionLike> getAnimations() {
        return this.animations;
    }

    @NotNull
    public final StatelessAnimation<T, ? extends F>[] getIdleAnimations() {
        return this.idleAnimations;
    }

    @NotNull
    public final ModelPartTransformation[] getTransformedParts() {
        return this.transformedParts;
    }

    @NotNull
    public final ModelQuirk<T, ?>[] getQuirks() {
        return this.quirks;
    }

    public final boolean isSuitable(@NotNull T entity2) {
        Intrinsics.checkNotNullParameter(entity2, (String)"entity");
        Function1<T, Boolean> function1 = this.condition;
        return function1 != null ? (Boolean)function1.invoke(entity2) : true;
    }

    @NotNull
    public final Map<String, Function2<Pose<T, ? extends ModelFrame>, Pose<T, ? extends ModelFrame>, StatefulAnimation<T, ModelFrame>>> getTransitions() {
        return this.transitions;
    }

    public final void idleStateless(@NotNull PoseableEntityModel<T> model, @Nullable PoseableEntityState<T> state, float limbSwing, float limbSwingAmount, float ageInTicks, float headYaw, float headPitch, float intensity) {
        Intrinsics.checkNotNullParameter(model, (String)"model");
        StatelessAnimation<T, ? extends F>[] $this$forEach$iv = this.idleAnimations;
        boolean $i$f$forEach = false;
        int n = $this$forEach$iv.length;
        for (int i = 0; i < n; ++i) {
            StatelessAnimation<Object, F> element$iv;
            StatelessAnimation<Object, F> it = element$iv = $this$forEach$iv[i];
            boolean bl = false;
            it.apply(null, model, state, limbSwing, limbSwingAmount, ageInTicks, headYaw, headPitch, intensity);
        }
    }

    public static /* synthetic */ void idleStateless$default(Pose pose, PoseableEntityModel poseableEntityModel, PoseableEntityState poseableEntityState, float f, float f2, float f3, float f4, float f5, float f6, int n, Object object) {
        if ((n & 4) != 0) {
            f = 0.0f;
        }
        if ((n & 8) != 0) {
            f2 = 0.0f;
        }
        if ((n & 0x10) != 0) {
            f3 = 0.0f;
        }
        if ((n & 0x20) != 0) {
            f4 = 0.0f;
        }
        if ((n & 0x40) != 0) {
            f5 = 0.0f;
        }
        pose.idleStateless(poseableEntityModel, poseableEntityState, f, f2, f3, f4, f5, f6);
    }

    /*
     * WARNING - void declaration
     */
    public final void idleStateful(@Nullable T entity2, @NotNull PoseableEntityModel<T> model, @NotNull PoseableEntityState<T> state, float limbSwing, float limbSwingAmount, float ageInTicks, float headYaw, float headPitch) {
        void $this$forEach$iv;
        void $this$filterTo$iv$iv;
        Intrinsics.checkNotNullParameter(model, (String)"model");
        Intrinsics.checkNotNullParameter(state, (String)"state");
        Object $this$filter$iv = this.idleAnimations;
        boolean $i$f$filter = false;
        StatelessAnimation<T, ? extends F>[] statelessAnimationArray = $this$filter$iv;
        Collection destination$iv$iv = new ArrayList();
        boolean $i$f$filterTo = false;
        int n = ((void)$this$filterTo$iv$iv).length;
        for (int i = 0; i < n; ++i) {
            void element$iv$iv;
            void it = element$iv$iv = $this$filterTo$iv$iv[i];
            boolean bl = false;
            if (!state.shouldIdleRun((StatelessAnimation<T, ?>)it, 0.0f)) continue;
            destination$iv$iv.add(element$iv$iv);
        }
        $this$filter$iv = (List)destination$iv$iv;
        boolean $i$f$forEach = false;
        for (Object element$iv : $this$forEach$iv) {
            StatelessAnimation idleAnimation = (StatelessAnimation)element$iv;
            boolean bl = false;
            idleAnimation.apply(entity2, model, state, limbSwing, limbSwingAmount, ageInTicks, headYaw, headPitch, state.getIdleIntensity(idleAnimation));
        }
    }
}

