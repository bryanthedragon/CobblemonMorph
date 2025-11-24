/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  kotlin.Metadata
 *  kotlin.Pair
 *  kotlin.TuplesKt
 *  kotlin.collections.MapsKt
 *  kotlin.jvm.functions.Function1
 *  kotlin.jvm.internal.DefaultConstructorMarker
 *  kotlin.jvm.internal.Intrinsics
 *  kotlin.jvm.internal.SourceDebugExtension
 *  net.minecraft.CrashReport
 *  net.minecraft.CrashReportCategory
 *  net.minecraft.ReportedException
 *  net.minecraft.client.model.geom.ModelPart
 *  net.minecraft.world.entity.Entity
 *  net.minecraft.world.phys.Vec3
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.bedrock.animation;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.bedrock.molang.runtime.MoLangEnvironment;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.bedrock.molang.runtime.MoLangRuntime;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.bedrock.molang.runtime.MoParams;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.bedrock.molang.runtime.value.DoubleValue;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.molang.MoLangFunctions;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.PoseableEntityModel;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.PoseableEntityState;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.bedrock.animation.BedrockBoneTimeline;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.bedrock.animation.BedrockBoneValue;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.bedrock.animation.BedrockEffectKeyframe;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.pose.Bone;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.repository.RenderContext;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.entity.pokemon.PokemonEntity;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.math.geometry.AngleExtensionsKt;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.TuplesKt;
import kotlin.collections.MapsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import net.minecraft.CrashReport;
import net.minecraft.CrashReportCategory;
import net.minecraft.ReportedException;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000b\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0007\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\u0006\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010$\n\u0002\u0010\u000e\n\u0002\u0018\u0002\n\u0002\b\u000b\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0013\b\u0086\b\u0018\u0000 :2\u00020\u0001:\u0001:B9\u0012\u0006\u0010\u001c\u001a\u00020\r\u0012\u0006\u0010\u001d\u001a\u00020\u0010\u0012\f\u0010\u001e\u001a\b\u0012\u0004\u0012\u00020\u00140\u0013\u0012\u0012\u0010\u001f\u001a\u000e\u0012\u0004\u0012\u00020\u0018\u0012\u0004\u0012\u00020\u00190\u0017\u00a2\u0006\u0004\b8\u00109J=\u0010\u000b\u001a\u00020\n\"\b\b\u0000\u0010\u0003*\u00020\u00022\u0006\u0010\u0004\u001a\u00028\u00002\f\u0010\u0006\u001a\b\u0012\u0004\u0012\u00028\u00000\u00052\u0006\u0010\b\u001a\u00020\u00072\u0006\u0010\t\u001a\u00020\u0007\u00a2\u0006\u0004\b\u000b\u0010\fJ\u0010\u0010\u000e\u001a\u00020\rH\u00c6\u0003\u00a2\u0006\u0004\b\u000e\u0010\u000fJ\u0010\u0010\u0011\u001a\u00020\u0010H\u00c6\u0003\u00a2\u0006\u0004\b\u0011\u0010\u0012J\u0016\u0010\u0015\u001a\b\u0012\u0004\u0012\u00020\u00140\u0013H\u00c6\u0003\u00a2\u0006\u0004\b\u0015\u0010\u0016J\u001c\u0010\u001a\u001a\u000e\u0012\u0004\u0012\u00020\u0018\u0012\u0004\u0012\u00020\u00190\u0017H\u00c6\u0003\u00a2\u0006\u0004\b\u001a\u0010\u001bJJ\u0010 \u001a\u00020\u00002\b\b\u0002\u0010\u001c\u001a\u00020\r2\b\b\u0002\u0010\u001d\u001a\u00020\u00102\u000e\b\u0002\u0010\u001e\u001a\b\u0012\u0004\u0012\u00020\u00140\u00132\u0014\b\u0002\u0010\u001f\u001a\u000e\u0012\u0004\u0012\u00020\u0018\u0012\u0004\u0012\u00020\u00190\u0017H\u00c6\u0001\u00a2\u0006\u0004\b \u0010!J\u001a\u0010#\u001a\u00020\r2\b\u0010\"\u001a\u0004\u0018\u00010\u0001H\u00d6\u0003\u00a2\u0006\u0004\b#\u0010$J\u0010\u0010&\u001a\u00020%H\u00d6\u0001\u00a2\u0006\u0004\b&\u0010'J7\u0010,\u001a\u00020\r2\n\u0010)\u001a\u0006\u0012\u0002\b\u00030(2\f\u0010\u0006\u001a\b\u0012\u0002\b\u0003\u0018\u00010\u00052\u0006\u0010*\u001a\u00020\u00072\u0006\u0010+\u001a\u00020\u0007\u00a2\u0006\u0004\b,\u0010-J\u0010\u0010.\u001a\u00020\u0018H\u00d6\u0001\u00a2\u0006\u0004\b.\u0010/R\u0017\u0010\u001d\u001a\u00020\u00108\u0006\u00a2\u0006\f\n\u0004\b\u001d\u00100\u001a\u0004\b1\u0010\u0012R#\u0010\u001f\u001a\u000e\u0012\u0004\u0012\u00020\u0018\u0012\u0004\u0012\u00020\u00190\u00178\u0006\u00a2\u0006\f\n\u0004\b\u001f\u00102\u001a\u0004\b3\u0010\u001bR\u001d\u0010\u001e\u001a\b\u0012\u0004\u0012\u00020\u00140\u00138\u0006\u00a2\u0006\f\n\u0004\b\u001e\u00104\u001a\u0004\b5\u0010\u0016R\u0017\u0010\u001c\u001a\u00020\r8\u0006\u00a2\u0006\f\n\u0004\b\u001c\u00106\u001a\u0004\b7\u0010\u000f\u00a8\u0006;"}, d2={"Lcom/cobblemon/mod/common/client/render/models/blockbench/bedrock/animation/BedrockAnimation;", "", "Lnet/minecraft/world/entity/Entity;", "T", "entity", "Lcom/cobblemon/mod/common/client/render/models/blockbench/PoseableEntityState;", "state", "", "previousSeconds", "newSeconds", "", "applyEffects", "(Lnet/minecraft/world/entity/Entity;Lcom/cobblemon/mod/common/client/render/models/blockbench/PoseableEntityState;FF)V", "", "component1", "()Z", "", "component2", "()D", "", "Lcom/cobblemon/mod/common/client/render/models/blockbench/bedrock/animation/BedrockEffectKeyframe;", "component3", "()Ljava/util/List;", "", "", "Lcom/cobblemon/mod/common/client/render/models/blockbench/bedrock/animation/BedrockBoneTimeline;", "component4", "()Ljava/util/Map;", "shouldLoop", "animationLength", "effects", "boneTimelines", "copy", "(ZDLjava/util/List;Ljava/util/Map;)Lcom/cobblemon/mod/common/client/render/models/blockbench/bedrock/animation/BedrockAnimation;", "other", "equals", "(Ljava/lang/Object;)Z", "", "hashCode", "()I", "Lcom/cobblemon/mod/common/client/render/models/blockbench/PoseableEntityModel;", "model", "animationSeconds", "intensity", "run", "(Lcom/cobblemon/mod/common/client/render/models/blockbench/PoseableEntityModel;Lcom/cobblemon/mod/common/client/render/models/blockbench/PoseableEntityState;FF)Z", "toString", "()Ljava/lang/String;", "D", "getAnimationLength", "Ljava/util/Map;", "getBoneTimelines", "Ljava/util/List;", "getEffects", "Z", "getShouldLoop", "<init>", "(ZDLjava/util/List;Ljava/util/Map;)V", "Companion", "common"})
@SourceDebugExtension(value={"SMAP\nBedrockAnimation.kt\nKotlin\n*S Kotlin\n*F\n+ 1 BedrockAnimation.kt\ncom/cobblemon/mod/common/client/render/models/blockbench/bedrock/animation/BedrockAnimation\n+ 2 _Maps.kt\nkotlin/collections/MapsKt___MapsKt\n+ 3 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n*L\n1#1,346:1\n215#2,2:347\n766#3:349\n857#3,2:350\n1855#3,2:352\n*S KotlinDebug\n*F\n+ 1 BedrockAnimation.kt\ncom/cobblemon/mod/common/client/render/models/blockbench/bedrock/animation/BedrockAnimation\n*L\n161#1:347,2\n215#1:349\n215#1:350,2\n215#1:352,2\n*E\n"})
public final class BedrockAnimation {
    @NotNull
    public static final Companion Companion;
    private final boolean shouldLoop;
    private final double animationLength;
    @NotNull
    private final List<BedrockEffectKeyframe> effects;
    @NotNull
    private final Map<String, BedrockBoneTimeline> boneTimelines;
    @NotNull
    private static final MoLangRuntime sharedRuntime;

    public BedrockAnimation(boolean shouldLoop, double animationLength, @NotNull List<? extends BedrockEffectKeyframe> effects, @NotNull Map<String, BedrockBoneTimeline> boneTimelines) {
        Intrinsics.checkNotNullParameter(effects, (String)"effects");
        Intrinsics.checkNotNullParameter(boneTimelines, (String)"boneTimelines");
        this.shouldLoop = shouldLoop;
        this.animationLength = animationLength;
        this.effects = effects;
        this.boneTimelines = boneTimelines;
    }

    public final boolean getShouldLoop() {
        return this.shouldLoop;
    }

    public final double getAnimationLength() {
        return this.animationLength;
    }

    @NotNull
    public final List<BedrockEffectKeyframe> getEffects() {
        return this.effects;
    }

    @NotNull
    public final Map<String, BedrockBoneTimeline> getBoneTimelines() {
        return this.boneTimelines;
    }

    public final boolean run(@NotNull PoseableEntityModel<?> model, @Nullable PoseableEntityState<?> state, float animationSeconds, float intensity) {
        Intrinsics.checkNotNullParameter(model, (String)"model");
        float animationSeconds2 = 0.0f;
        animationSeconds2 = animationSeconds;
        if (this.shouldLoop) {
            animationSeconds2 %= (float)this.animationLength;
        } else if ((double)animationSeconds2 > this.animationLength && this.animationLength > 0.0) {
            return false;
        }
        Map<String, BedrockBoneTimeline> $this$forEach$iv = this.boneTimelines;
        boolean $i$f$forEach = false;
        Iterator<Map.Entry<String, BedrockBoneTimeline>> iterator = $this$forEach$iv.entrySet().iterator();
        while (iterator.hasNext()) {
            ModelPart modelPart;
            ModelPart part;
            Map.Entry<String, BedrockBoneTimeline> element$iv;
            Map.Entry<String, BedrockBoneTimeline> entry = element$iv = iterator.next();
            boolean bl = false;
            String boneName = entry.getKey();
            BedrockBoneTimeline timeline = entry.getValue();
            ModelPart modelPart2 = model.getRelevantPartsByName().get(boneName);
            if (modelPart2 == null) {
                if (Intrinsics.areEqual((Object)boneName, (Object)"root_part")) {
                    Bone bone = model.getRootPart();
                    Intrinsics.checkNotNull((Object)bone, (String)"null cannot be cast to non-null type net.minecraft.client.model.ModelPart");
                    modelPart2 = (ModelPart)bone;
                } else {
                    modelPart2 = null;
                }
            }
            if ((part = modelPart2) == null) continue;
            if (!timeline.getPosition().isEmpty()) {
                BedrockBoneValue bedrockBoneValue = timeline.getPosition();
                double d = animationSeconds2;
                PoseableEntityState<?> poseableEntityState = state;
                if (poseableEntityState == null || (poseableEntityState = poseableEntityState.getRuntime()) == null) {
                    poseableEntityState = sharedRuntime;
                }
                Vec3 position = bedrockBoneValue.resolve(d, (MoLangRuntime)((Object)poseableEntityState)).m_82490_((double)intensity);
                ModelPart $this$run_u24lambda_u243_u24lambda_u240 = modelPart = part;
                boolean bl2 = false;
                $this$run_u24lambda_u243_u24lambda_u240.f_104200_ += (float)position.f_82479_;
                $this$run_u24lambda_u243_u24lambda_u240.f_104201_ += (float)position.f_82480_;
                $this$run_u24lambda_u243_u24lambda_u240.f_104202_ += (float)position.f_82481_;
            }
            if (!timeline.getRotation().isEmpty()) {
                try {
                    BedrockBoneValue bedrockBoneValue = timeline.getRotation();
                    double d = animationSeconds2;
                    PoseableEntityState<?> poseableEntityState = state;
                    if (poseableEntityState == null || (poseableEntityState = poseableEntityState.getRuntime()) == null) {
                        poseableEntityState = sharedRuntime;
                    }
                    Vec3 rotation = bedrockBoneValue.resolve(d, (MoLangRuntime)((Object)poseableEntityState)).m_82490_((double)intensity);
                    ModelPart $this$run_u24lambda_u243_u24lambda_u241 = modelPart = part;
                    boolean bl3 = false;
                    $this$run_u24lambda_u243_u24lambda_u241.f_104203_ += AngleExtensionsKt.toRadians(Float.valueOf((float)rotation.f_82479_));
                    $this$run_u24lambda_u243_u24lambda_u241.f_104204_ += AngleExtensionsKt.toRadians(Float.valueOf((float)rotation.f_82480_));
                    $this$run_u24lambda_u243_u24lambda_u241.f_104205_ += AngleExtensionsKt.toRadians(Float.valueOf((float)rotation.f_82481_));
                }
                catch (Exception e) {
                    Entity entity2 = model.getContext().request(RenderContext.Companion.getENTITY());
                    Intrinsics.checkNotNull((Object)entity2);
                    IllegalStateException exception = new IllegalStateException("Bad animation for species: " + ((PokemonEntity)entity2).getPokemon().getSpecies().getName(), e);
                    CrashReport crash = new CrashReport("Cobblemon encountered an unexpected crash", (Throwable)exception);
                    CrashReportCategory section = crash.m_127514_("Animation Details");
                    PoseableEntityState<?> poseableEntityState = state;
                    if (poseableEntityState != null) {
                        PoseableEntityState<?> it = poseableEntityState;
                        boolean bl4 = false;
                        String string = state.getCurrentPose();
                        Intrinsics.checkNotNull((Object)string);
                        section.m_128159_("Pose", (Object)string);
                    }
                    section.m_128159_("Bone", (Object)boneName);
                    throw new ReportedException(crash);
                }
            }
            if (timeline.getScale().isEmpty()) continue;
            BedrockBoneValue bedrockBoneValue = timeline.getScale();
            double d = animationSeconds2;
            PoseableEntityState<?> poseableEntityState = state;
            if (poseableEntityState == null || (poseableEntityState = poseableEntityState.getRuntime()) == null) {
                poseableEntityState = sharedRuntime;
            }
            Vec3 scale = bedrockBoneValue.resolve(d, (MoLangRuntime)((Object)poseableEntityState));
            Vec3 deviation = scale.m_82490_(-1.0).m_82520_(1.0, 1.0, 1.0).m_82490_((double)intensity);
            Vec3 vec3 = deviation.m_82492_(1.0, 1.0, 1.0).m_82490_(-1.0);
            Intrinsics.checkNotNullExpressionValue((Object)vec3, (String)"deviation.subtract(1.0, 1.0, 1.0).multiply(-1.0)");
            scale = vec3;
            part.f_233553_ *= (float)scale.f_82479_;
            part.f_233554_ *= (float)scale.f_82480_;
            part.f_233555_ *= (float)scale.f_82481_;
        }
        return true;
    }

    /*
     * WARNING - void declaration
     */
    public final <T extends Entity> void applyEffects(@NotNull T entity2, @NotNull PoseableEntityState<T> state, float previousSeconds, float newSeconds) {
        void $this$forEach$iv;
        void $this$filterTo$iv$iv;
        Intrinsics.checkNotNullParameter(entity2, (String)"entity");
        Intrinsics.checkNotNullParameter(state, (String)"state");
        Function1 effectCondition2 = previousSeconds > newSeconds ? (Function1)new Function1<BedrockEffectKeyframe, Boolean>(previousSeconds, newSeconds){
            final /* synthetic */ float $previousSeconds;
            final /* synthetic */ float $newSeconds;
            {
                this.$previousSeconds = $previousSeconds;
                this.$newSeconds = $newSeconds;
                super(1);
            }

            @NotNull
            public final Boolean invoke(@NotNull BedrockEffectKeyframe it) {
                Intrinsics.checkNotNullParameter((Object)it, (String)"it");
                return it.getSeconds() >= this.$previousSeconds || it.getSeconds() <= this.$newSeconds;
            }
        } : (Function1)new Function1<BedrockEffectKeyframe, Boolean>(previousSeconds, newSeconds){
            final /* synthetic */ float $previousSeconds;
            final /* synthetic */ float $newSeconds;
            {
                this.$previousSeconds = $previousSeconds;
                this.$newSeconds = $newSeconds;
                super(1);
            }

            @NotNull
            public final Boolean invoke(@NotNull BedrockEffectKeyframe it) {
                Intrinsics.checkNotNullParameter((Object)it, (String)"it");
                float f = it.getSeconds();
                return this.$previousSeconds <= f ? f <= this.$newSeconds : false;
            }
        };
        Iterable $this$filter$iv = this.effects;
        boolean $i$f$filter = false;
        Iterable iterable = $this$filter$iv;
        Collection destination$iv$iv = new ArrayList();
        boolean $i$f$filterTo = false;
        for (Object element$iv$iv : $this$filterTo$iv$iv) {
            if (!((Boolean)effectCondition2.invoke(element$iv$iv)).booleanValue()) continue;
            destination$iv$iv.add(element$iv$iv);
        }
        $this$filter$iv = (List)destination$iv$iv;
        boolean $i$f$forEach = false;
        for (Object element$iv : $this$forEach$iv) {
            BedrockEffectKeyframe it = (BedrockEffectKeyframe)element$iv;
            boolean bl = false;
            it.run(entity2, state);
        }
    }

    public final boolean component1() {
        return this.shouldLoop;
    }

    public final double component2() {
        return this.animationLength;
    }

    @NotNull
    public final List<BedrockEffectKeyframe> component3() {
        return this.effects;
    }

    @NotNull
    public final Map<String, BedrockBoneTimeline> component4() {
        return this.boneTimelines;
    }

    @NotNull
    public final BedrockAnimation copy(boolean shouldLoop, double animationLength, @NotNull List<? extends BedrockEffectKeyframe> effects, @NotNull Map<String, BedrockBoneTimeline> boneTimelines) {
        Intrinsics.checkNotNullParameter(effects, (String)"effects");
        Intrinsics.checkNotNullParameter(boneTimelines, (String)"boneTimelines");
        return new BedrockAnimation(shouldLoop, animationLength, effects, boneTimelines);
    }

    public static /* synthetic */ BedrockAnimation copy$default(BedrockAnimation bedrockAnimation, boolean bl, double d, List list, Map map, int n, Object object) {
        if ((n & 1) != 0) {
            bl = bedrockAnimation.shouldLoop;
        }
        if ((n & 2) != 0) {
            d = bedrockAnimation.animationLength;
        }
        if ((n & 4) != 0) {
            list = bedrockAnimation.effects;
        }
        if ((n & 8) != 0) {
            map = bedrockAnimation.boneTimelines;
        }
        return bedrockAnimation.copy(bl, d, list, map);
    }

    @NotNull
    public String toString() {
        return "BedrockAnimation(shouldLoop=" + this.shouldLoop + ", animationLength=" + this.animationLength + ", effects=" + this.effects + ", boneTimelines=" + this.boneTimelines + ")";
    }

    public int hashCode() {
        int n = this.shouldLoop ? 1 : 0;
        if (n != 0) {
            n = 1;
        }
        int result = n;
        result = result * 31 + Double.hashCode(this.animationLength);
        result = result * 31 + ((Object)this.effects).hashCode();
        result = result * 31 + ((Object)this.boneTimelines).hashCode();
        return result;
    }

    public boolean equals(@Nullable Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof BedrockAnimation)) {
            return false;
        }
        BedrockAnimation bedrockAnimation = (BedrockAnimation)other;
        if (this.shouldLoop != bedrockAnimation.shouldLoop) {
            return false;
        }
        if (Double.compare(this.animationLength, bedrockAnimation.animationLength) != 0) {
            return false;
        }
        if (!Intrinsics.areEqual(this.effects, bedrockAnimation.effects)) {
            return false;
        }
        return Intrinsics.areEqual(this.boneTimelines, bedrockAnimation.boneTimelines);
    }

    private static final Object sharedRuntime$lambda$6$lambda$5(DoubleValue $zero, MoParams it) {
        Intrinsics.checkNotNullParameter((Object)$zero, (String)"$zero");
        Intrinsics.checkNotNullParameter((Object)it, (String)"it");
        return $zero;
    }

    static {
        MoLangRuntime moLangRuntime;
        Companion = new Companion(null);
        MoLangRuntime it = moLangRuntime = new MoLangRuntime();
        boolean bl = false;
        DoubleValue zero = new DoubleValue(0.0);
        MoLangEnvironment moLangEnvironment = it.getEnvironment();
        Intrinsics.checkNotNullExpressionValue((Object)moLangEnvironment, (String)"it.environment");
        MoLangFunctions.INSTANCE.addFunctions(MoLangFunctions.getQueryStruct$default(MoLangFunctions.INSTANCE, moLangEnvironment, null, 1, null), MapsKt.mapOf((Pair)TuplesKt.to((Object)"anim_time", arg_0 -> BedrockAnimation.sharedRuntime$lambda$6$lambda$5(zero, arg_0))));
        sharedRuntime = moLangRuntime;
    }

    @Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000\u0010\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0018\u0002\n\u0002\b\u0007\b\u0086\u0003\u0018\u00002\u00020\u0001B\t\b\u0002\u00a2\u0006\u0004\b\u0007\u0010\bR\u0017\u0010\u0003\u001a\u00020\u00028\u0006\u00a2\u0006\f\n\u0004\b\u0003\u0010\u0004\u001a\u0004\b\u0005\u0010\u0006\u00a8\u0006\t"}, d2={"Lcom/cobblemon/mod/common/client/render/models/blockbench/bedrock/animation/BedrockAnimation$Companion;", "", "Lcom/bedrockk/molang/runtime/MoLangRuntime;", "sharedRuntime", "Lcom/bedrockk/molang/runtime/MoLangRuntime;", "getSharedRuntime", "()Lcom/bedrockk/molang/runtime/MoLangRuntime;", "<init>", "()V", "common"})
    public static final class Companion {
        private Companion() {
        }

        @NotNull
        public final MoLangRuntime getSharedRuntime() {
            return sharedRuntime;
        }

        public /* synthetic */ Companion(DefaultConstructorMarker $constructor_marker) {
            this();
        }
    }
}

