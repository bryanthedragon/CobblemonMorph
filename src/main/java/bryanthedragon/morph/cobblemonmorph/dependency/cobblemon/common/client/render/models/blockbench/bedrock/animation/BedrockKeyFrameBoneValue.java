/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  kotlin.Metadata
 *  kotlin.collections.CollectionsKt
 *  kotlin.collections.MapsKt
 *  kotlin.jvm.internal.Intrinsics
 *  kotlin.jvm.internal.SourceDebugExtension
 *  net.minecraft.world.phys.Vec3
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.bedrock.animation;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.bedrock.molang.runtime.MoLangRuntime;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.bedrock.animation.BedrockAnimationKeyFrame;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.bedrock.animation.BedrockBoneValue;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.bedrock.animation.InterpolationMathKt;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.bedrock.animation.InterpolationType;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.SortedMap;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.collections.MapsKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u00004\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0006\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010\b\n\u0002\b\u0006\u0018\u00002\u001e\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020\u00030\u0001j\u000e\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020\u0003`\u00042\u00020\u0005B\u0007\u00a2\u0006\u0004\b\u0011\u0010\u0012J\u001f\u0010\n\u001a\u00020\t2\u0006\u0010\u0006\u001a\u00020\u00022\u0006\u0010\b\u001a\u00020\u0007H\u0016\u00a2\u0006\u0004\b\n\u0010\u000bJ)\u0010\u000f\u001a\u0004\u0018\u00010\u0003*\u000e\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020\u00030\f2\b\u0010\u000e\u001a\u0004\u0018\u00010\r\u00a2\u0006\u0004\b\u000f\u0010\u0010\u00a8\u0006\u0013"}, d2={"Lcom/cobblemon/mod/common/client/render/models/blockbench/bedrock/animation/BedrockKeyFrameBoneValue;", "Ljava/util/HashMap;", "", "Lcom/cobblemon/mod/common/client/render/models/blockbench/bedrock/animation/BedrockAnimationKeyFrame;", "Lkotlin/collections/HashMap;", "Lcom/cobblemon/mod/common/client/render/models/blockbench/bedrock/animation/BedrockBoneValue;", "time", "Lcom/bedrockk/molang/runtime/MoLangRuntime;", "runtime", "Lnet/minecraft/world/phys/Vec3;", "resolve", "(DLcom/bedrockk/molang/runtime/MoLangRuntime;)Lnet/minecraft/world/phys/Vec3;", "Ljava/util/SortedMap;", "", "index", "getAtIndex", "(Ljava/util/SortedMap;Ljava/lang/Integer;)Lcom/cobblemon/mod/common/client/render/models/blockbench/bedrock/animation/BedrockAnimationKeyFrame;", "<init>", "()V", "common"})
@SourceDebugExtension(value={"SMAP\nBedrockAnimation.kt\nKotlin\n*S Kotlin\n*F\n+ 1 BedrockAnimation.kt\ncom/cobblemon/mod/common/client/render/models/blockbench/bedrock/animation/BedrockKeyFrameBoneValue\n+ 2 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n*L\n1#1,346:1\n336#2,8:347\n*S KotlinDebug\n*F\n+ 1 BedrockAnimation.kt\ncom/cobblemon/mod/common/client/render/models/blockbench/bedrock/animation/BedrockKeyFrameBoneValue\n*L\n265#1:347,8\n*E\n"})
public final class BedrockKeyFrameBoneValue
extends HashMap<Double, BedrockAnimationKeyFrame>
implements BedrockBoneValue {
    @Nullable
    public final BedrockAnimationKeyFrame getAtIndex(@NotNull SortedMap<Double, BedrockAnimationKeyFrame> $this$getAtIndex, @Nullable Integer index) {
        Double key;
        Intrinsics.checkNotNullParameter($this$getAtIndex, (String)"<this>");
        if (index == null) {
            return null;
        }
        Set<Double> set2 = $this$getAtIndex.keySet();
        Intrinsics.checkNotNullExpressionValue(set2, (String)"this.keys");
        Double d = key = (Double)CollectionsKt.elementAtOrNull((Iterable)set2, (int)index);
        return d != null ? (BedrockAnimationKeyFrame)$this$getAtIndex.get(d) : null;
    }

    /*
     * Unable to fully structure code
     */
    @Override
    @NotNull
    public Vec3 resolve(double time, @NotNull MoLangRuntime runtime) {
        block12: {
            block14: {
                block15: {
                    block13: {
                        block11: {
                            Intrinsics.checkNotNullParameter((Object)runtime, (String)"runtime");
                            sortedTimeline = MapsKt.toSortedMap((Map)this);
                            v0 = sortedTimeline.keySet();
                            Intrinsics.checkNotNullExpressionValue(v0, (String)"sortedTimeline.keys");
                            $this$indexOfFirst$iv = v0;
                            $i$f$indexOfFirst = false;
                            index$iv = 0;
                            for (T item$iv : $this$indexOfFirst$iv) {
                                if (index$iv < 0) {
                                    CollectionsKt.throwIndexOverflow();
                                }
                                it = (Double)item$iv;
                                $i$a$-indexOfFirst-BedrockKeyFrameBoneValue$resolve$afterIndex$1 = false;
                                Intrinsics.checkNotNullExpressionValue((Object)it, (String)"it");
                                if (it > time) {
                                    v1 = index$iv;
                                    break block11;
                                }
                                ++index$iv;
                            }
                            v1 = -1;
                        }
                        afterIndex = v1;
                        $this$indexOfFirst$iv = -1;
                        if (afterIndex == $this$indexOfFirst$iv) {
                            afterIndex = null;
                        }
                        beforeIndex = ($i$f$indexOfFirst = afterIndex) == null ? Integer.valueOf(sortedTimeline.size() - 1) : ($i$f$indexOfFirst == 0 ? null : Integer.valueOf(afterIndex - 1));
                        after = this.getAtIndex(sortedTimeline, afterIndex);
                        before = this.getAtIndex(sortedTimeline, beforeIndex);
                        v2 = after;
                        if (v2 == null || (v2 = v2.getPre()) == null || (v2 = v2.resolve(time, runtime)) == null) {
                            v2 = afterData = Vec3.f_82478_;
                        }
                        if ((v3 = before) == null || (v3 = v3.getPost()) == null || (v3 = v3.resolve(time, runtime)) == null) {
                            v3 = beforeData = Vec3.f_82478_;
                        }
                        if (before == null && after == null) break block12;
                        if (before != null && before.getInterpolationType() == InterpolationType.SMOOTH) break block13;
                        if (after == null || after.getInterpolationType() != InterpolationType.SMOOTH) break block14;
                    }
                    if (before == null || after == null) break block15;
                    beforePlusIndex = beforeIndex == null || beforeIndex == 0 ? null : Integer.valueOf(beforeIndex - 1);
                    beforePlus = this.getAtIndex(sortedTimeline, beforePlusIndex);
                    if (afterIndex == null) ** GOTO lbl-1000
                    var14_17 = this.size() - 1;
                    if (afterIndex == var14_17) lbl-1000:
                    // 2 sources

                    {
                        v4 = null;
                    } else {
                        v4 = afterIndex + 1;
                    }
                    afterPlusIndex = v4;
                    afterPlus = this.getAtIndex(sortedTimeline, afterPlusIndex);
                    return InterpolationMathKt.catmullromLerp(beforePlus, before, after, afterPlus, time, runtime);
                }
                if (before != null) {
                    Intrinsics.checkNotNullExpressionValue((Object)beforeData, (String)"beforeData");
                    return beforeData;
                }
                Intrinsics.checkNotNullExpressionValue((Object)afterData, (String)"afterData");
                return afterData;
            }
            if (before != null && after != null) {
                return new Vec3(beforeData.f_82479_ + (afterData.f_82479_ - beforeData.f_82479_) * InterpolationMathKt.linearLerpAlpha(before.getTime(), after.getTime(), time), beforeData.f_82480_ + (afterData.f_82480_ - beforeData.f_82480_) * InterpolationMathKt.linearLerpAlpha(before.getTime(), after.getTime(), time), beforeData.f_82481_ + (afterData.f_82481_ - beforeData.f_82481_) * InterpolationMathKt.linearLerpAlpha(before.getTime(), after.getTime(), time));
            }
            if (before != null) {
                Intrinsics.checkNotNullExpressionValue((Object)beforeData, (String)"beforeData");
                return beforeData;
            }
            Intrinsics.checkNotNullExpressionValue((Object)afterData, (String)"afterData");
            return afterData;
        }
        return new Vec3(0.0, 0.0, 0.0);
    }
}

