/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  kotlin.Metadata
 *  kotlin.jvm.internal.Intrinsics
 *  net.minecraft.world.phys.Vec3
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 *  org.joml.Vector3d
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.bedrock.animation;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.bedrock.molang.runtime.MoLangRuntime;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.bedrock.animation.BedrockAnimationKeyFrame;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.bedrock.animation.MolangBoneValue;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.bedrock.animation.Vector2d;
import java.util.ArrayList;
import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.joml.Vector3d;

@Metadata(mv={1, 8, 0}, k=2, xi=48, d1={"\u0000:\n\u0002\u0010\u0006\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\n\n\u0002\u0018\u0002\n\u0002\b\u0002\u001a7\u0010\u0006\u001a\u00020\u00002\u0006\u0010\u0001\u001a\u00020\u00002\u0006\u0010\u0002\u001a\u00020\u00002\u0006\u0010\u0003\u001a\u00020\u00002\u0006\u0010\u0004\u001a\u00020\u00002\u0006\u0010\u0005\u001a\u00020\u0000H\u0002\u00a2\u0006\u0004\b\u0006\u0010\u0007\u001aA\u0010\u0011\u001a\u00020\u00102\b\u0010\t\u001a\u0004\u0018\u00010\b2\u0006\u0010\n\u001a\u00020\b2\u0006\u0010\u000b\u001a\u00020\b2\b\u0010\f\u001a\u0004\u0018\u00010\b2\u0006\u0010\r\u001a\u00020\u00002\u0006\u0010\u000f\u001a\u00020\u000e\u00a2\u0006\u0004\b\u0011\u0010\u0012\u001aI\u0010\u0011\u001a\u00020\u00002\b\u0010\t\u001a\u0004\u0018\u00010\b2\u0006\u0010\n\u001a\u00020\b2\u0006\u0010\u000b\u001a\u00020\b2\b\u0010\f\u001a\u0004\u0018\u00010\b2\u0006\u0010\u0014\u001a\u00020\u00132\u0006\u0010\r\u001a\u00020\u00002\u0006\u0010\u000f\u001a\u00020\u000e\u00a2\u0006\u0004\b\u0011\u0010\u0015\u001a%\u0010\u0019\u001a\u00020\u00172\f\u0010\u0018\u001a\b\u0012\u0004\u0012\u00020\u00170\u00162\u0006\u0010\r\u001a\u00020\u0000H\u0002\u00a2\u0006\u0004\b\u0019\u0010\u001a\u001a%\u0010\u001e\u001a\u00020\u00002\u0006\u0010\u001b\u001a\u00020\u00002\u0006\u0010\u001c\u001a\u00020\u00002\u0006\u0010\u001d\u001a\u00020\u0000\u00a2\u0006\u0004\b\u001e\u0010\u001f\u001a\u0019\u0010 \u001a\u00020\u0000*\u00020\u00102\u0006\u0010\u0014\u001a\u00020\u0013\u00a2\u0006\u0004\b \u0010!\u001a\u001b\u0010 \u001a\u00020\u0000*\u00020\"2\u0006\u0010\u0014\u001a\u00020\u0013H\u0002\u00a2\u0006\u0004\b \u0010#\u00a8\u0006$"}, d2={"", "t", "p0", "p1", "p2", "p3", "catmullrom", "(DDDDD)D", "Lcom/cobblemon/mod/common/client/render/models/blockbench/bedrock/animation/BedrockAnimationKeyFrame;", "frameA", "frameB", "frameC", "frameD", "time", "Lcom/bedrockk/molang/runtime/MoLangRuntime;", "runtime", "Lnet/minecraft/world/phys/Vec3;", "catmullromLerp", "(Lcom/cobblemon/mod/common/client/render/models/blockbench/bedrock/animation/BedrockAnimationKeyFrame;Lcom/cobblemon/mod/common/client/render/models/blockbench/bedrock/animation/BedrockAnimationKeyFrame;Lcom/cobblemon/mod/common/client/render/models/blockbench/bedrock/animation/BedrockAnimationKeyFrame;Lcom/cobblemon/mod/common/client/render/models/blockbench/bedrock/animation/BedrockAnimationKeyFrame;DLcom/bedrockk/molang/runtime/MoLangRuntime;)Lnet/minecraft/world/phys/Vec3;", "", "axis", "(Lcom/cobblemon/mod/common/client/render/models/blockbench/bedrock/animation/BedrockAnimationKeyFrame;Lcom/cobblemon/mod/common/client/render/models/blockbench/bedrock/animation/BedrockAnimationKeyFrame;Lcom/cobblemon/mod/common/client/render/models/blockbench/bedrock/animation/BedrockAnimationKeyFrame;Lcom/cobblemon/mod/common/client/render/models/blockbench/bedrock/animation/BedrockAnimationKeyFrame;IDLcom/bedrockk/molang/runtime/MoLangRuntime;)D", "", "Lcom/cobblemon/mod/common/client/render/models/blockbench/bedrock/animation/Vector2d;", "points", "getPointOnSpline", "(Ljava/util/List;D)Lcom/cobblemon/mod/common/client/render/models/blockbench/bedrock/animation/Vector2d;", "before", "after", "value", "linearLerpAlpha", "(DDD)D", "get", "(Lnet/minecraft/world/phys/Vec3;I)D", "Lorg/joml/Vector3d;", "(Lorg/joml/Vector3d;I)D", "common"})
public final class InterpolationMathKt {
    @NotNull
    public static final Vec3 catmullromLerp(@Nullable BedrockAnimationKeyFrame frameA, @NotNull BedrockAnimationKeyFrame frameB, @NotNull BedrockAnimationKeyFrame frameC, @Nullable BedrockAnimationKeyFrame frameD, double time, @NotNull MoLangRuntime runtime2) {
        Intrinsics.checkNotNullParameter((Object)frameB, (String)"frameB");
        Intrinsics.checkNotNullParameter((Object)frameC, (String)"frameC");
        Intrinsics.checkNotNullParameter((Object)runtime2, (String)"runtime");
        return new Vec3(InterpolationMathKt.catmullromLerp(frameA, frameB, frameC, frameD, 0, time, runtime2), InterpolationMathKt.catmullromLerp(frameA, frameB, frameC, frameD, 1, time, runtime2), InterpolationMathKt.catmullromLerp(frameA, frameB, frameC, frameD, 2, time, runtime2));
    }

    public static final double linearLerpAlpha(double before, double after2, double value2) {
        return (value2 - before) / (after2 - before);
    }

    public static final double catmullromLerp(@Nullable BedrockAnimationKeyFrame frameA, @NotNull BedrockAnimationKeyFrame frameB, @NotNull BedrockAnimationKeyFrame frameC, @Nullable BedrockAnimationKeyFrame frameD, int axis, double time, @NotNull MoLangRuntime runtime2) {
        Vec3 frameDData;
        Intrinsics.checkNotNullParameter((Object)frameB, (String)"frameB");
        Intrinsics.checkNotNullParameter((Object)frameC, (String)"frameC");
        Intrinsics.checkNotNullParameter((Object)runtime2, (String)"runtime");
        List vectors = new ArrayList();
        Object object = frameA;
        Vec3 frameAData = object != null && (object = ((BedrockAnimationKeyFrame)object).getPost()) != null ? ((MolangBoneValue)object).resolve(time, runtime2) : null;
        Vec3 frameBData = frameB.getPost().resolve(time, runtime2);
        Vec3 frameCData = frameC.getPre().resolve(time, runtime2);
        Object object2 = frameD;
        Vec3 vec3 = object2 != null && (object2 = ((BedrockAnimationKeyFrame)object2).getPre()) != null ? ((MolangBoneValue)object2).resolve(time, runtime2) : (frameDData = null);
        if (frameAData != null) {
            vectors.add(new Vector2d(frameA.getTime(), InterpolationMathKt.get(frameAData, axis)));
        }
        vectors.add(new Vector2d(frameB.getTime(), InterpolationMathKt.get(frameBData, axis)));
        vectors.add(new Vector2d(frameC.getTime(), InterpolationMathKt.get(frameCData, axis)));
        if (frameDData != null) {
            vectors.add(new Vector2d(frameD.getTime(), InterpolationMathKt.get(frameDData, axis)));
        }
        double alpha = (InterpolationMathKt.linearLerpAlpha(frameB.getTime(), frameC.getTime(), time) + (double)(frameA != null ? 1 : 0)) / (double)(vectors.size() - 1);
        return InterpolationMathKt.getPointOnSpline(vectors, alpha).getB();
    }

    public static final double get(@NotNull Vec3 $this$get, int axis) {
        Intrinsics.checkNotNullParameter((Object)$this$get, (String)"<this>");
        return switch (axis) {
            case 0 -> $this$get.f_82479_;
            case 1 -> $this$get.f_82480_;
            default -> $this$get.f_82481_;
        };
    }

    private static final Vector2d getPointOnSpline(List<Vector2d> points, double time) {
        double p = (double)(points.size() - 1) * time;
        int intPoint = (int)Math.floor(p);
        double weight = p - (double)intPoint;
        int p0Index = intPoint == 0 ? intPoint : intPoint - 1;
        int p2Index = intPoint > points.size() - 2 ? points.size() - 1 : intPoint + 1;
        int p3Index = intPoint > points.size() - 3 ? points.size() - 1 : intPoint + 2;
        Vector2d p0 = points.get(p0Index);
        Vector2d p1 = points.get(intPoint);
        Vector2d p2 = points.get(p2Index);
        Vector2d p3 = points.get(p3Index);
        return new Vector2d(InterpolationMathKt.catmullrom(weight, p0.getA(), p1.getA(), p2.getA(), p3.getA()), InterpolationMathKt.catmullrom(weight, p0.getB(), p1.getB(), p2.getB(), p3.getB()));
    }

    private static final double catmullrom(double t, double p0, double p1, double p2, double p3) {
        double v0 = (p2 - p0) * 0.5;
        double v1 = (p3 - p1) * 0.5;
        double t2 = t * t;
        double t3 = t * t2;
        return ((double)2 * p1 - (double)2 * p2 + v0 + v1) * t3 + ((double)-3 * p1 + (double)3 * p2 - (double)2 * v0 - v1) * t2 + v0 * t + p1;
    }

    private static final double get(Vector3d $this$get, int axis) {
        return switch (axis) {
            case 0 -> $this$get.x;
            case 1 -> $this$get.y;
            case 2 -> $this$get.z;
            default -> throw new IllegalStateException();
        };
    }
}

