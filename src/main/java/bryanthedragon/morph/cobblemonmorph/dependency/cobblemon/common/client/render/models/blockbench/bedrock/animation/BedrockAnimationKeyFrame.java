/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  kotlin.Metadata
 *  kotlin.jvm.internal.Intrinsics
 *  org.jetbrains.annotations.NotNull
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.bedrock.animation;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.bedrock.animation.InterpolationType;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.bedrock.animation.MolangBoneValue;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.bedrock.animation.Transformation;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000(\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0006\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0007\b&\u0018\u00002\u00020\u0001B\u001f\u0012\u0006\u0010\u000e\u001a\u00020\r\u0012\u0006\u0010\u0013\u001a\u00020\u0012\u0012\u0006\u0010\u0003\u001a\u00020\u0002\u00a2\u0006\u0004\b\u0017\u0010\u0018R\u0017\u0010\u0003\u001a\u00020\u00028\u0006\u00a2\u0006\f\n\u0004\b\u0003\u0010\u0004\u001a\u0004\b\u0005\u0010\u0006R\u0014\u0010\n\u001a\u00020\u00078&X\u00a6\u0004\u00a2\u0006\u0006\u001a\u0004\b\b\u0010\tR\u0014\u0010\f\u001a\u00020\u00078&X\u00a6\u0004\u00a2\u0006\u0006\u001a\u0004\b\u000b\u0010\tR\u0017\u0010\u000e\u001a\u00020\r8\u0006\u00a2\u0006\f\n\u0004\b\u000e\u0010\u000f\u001a\u0004\b\u0010\u0010\u0011R\u0017\u0010\u0013\u001a\u00020\u00128\u0006\u00a2\u0006\f\n\u0004\b\u0013\u0010\u0014\u001a\u0004\b\u0015\u0010\u0016\u00a8\u0006\u0019"}, d2={"Lcom/cobblemon/mod/common/client/render/models/blockbench/bedrock/animation/BedrockAnimationKeyFrame;", "", "Lcom/cobblemon/mod/common/client/render/models/blockbench/bedrock/animation/InterpolationType;", "interpolationType", "Lcom/cobblemon/mod/common/client/render/models/blockbench/bedrock/animation/InterpolationType;", "getInterpolationType", "()Lcom/cobblemon/mod/common/client/render/models/blockbench/bedrock/animation/InterpolationType;", "Lcom/cobblemon/mod/common/client/render/models/blockbench/bedrock/animation/MolangBoneValue;", "getPost", "()Lcom/cobblemon/mod/common/client/render/models/blockbench/bedrock/animation/MolangBoneValue;", "post", "getPre", "pre", "", "time", "D", "getTime", "()D", "Lcom/cobblemon/mod/common/client/render/models/blockbench/bedrock/animation/Transformation;", "transformation", "Lcom/cobblemon/mod/common/client/render/models/blockbench/bedrock/animation/Transformation;", "getTransformation", "()Lcom/cobblemon/mod/common/client/render/models/blockbench/bedrock/animation/Transformation;", "<init>", "(DLcom/cobblemon/mod/common/client/render/models/blockbench/bedrock/animation/Transformation;Lcom/cobblemon/mod/common/client/render/models/blockbench/bedrock/animation/InterpolationType;)V", "common"})
public abstract class BedrockAnimationKeyFrame {
    private final double time;
    @NotNull
    private final Transformation transformation;
    @NotNull
    private final InterpolationType interpolationType;

    public BedrockAnimationKeyFrame(double time, @NotNull Transformation transformation, @NotNull InterpolationType interpolationType) {
        Intrinsics.checkNotNullParameter((Object)((Object)transformation), (String)"transformation");
        Intrinsics.checkNotNullParameter((Object)((Object)interpolationType), (String)"interpolationType");
        this.time = time;
        this.transformation = transformation;
        this.interpolationType = interpolationType;
    }

    public final double getTime() {
        return this.time;
    }

    @NotNull
    public final Transformation getTransformation() {
        return this.transformation;
    }

    @NotNull
    public final InterpolationType getInterpolationType() {
        return this.interpolationType;
    }

    @NotNull
    public abstract MolangBoneValue getPre();

    @NotNull
    public abstract MolangBoneValue getPost();
}

