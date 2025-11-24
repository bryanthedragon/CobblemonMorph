/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  kotlin.Metadata
 *  kotlin.jvm.internal.Intrinsics
 *  org.jetbrains.annotations.NotNull
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.bedrock.animation;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.bedrock.animation.BedrockAnimationKeyFrame;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.bedrock.animation.InterpolationType;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.bedrock.animation.MolangBoneValue;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.bedrock.animation.Transformation;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000$\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0010\u0006\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\u0018\u00002\u00020\u0001B'\u0012\u0006\u0010\f\u001a\u00020\u000b\u0012\u0006\u0010\u000e\u001a\u00020\r\u0012\u0006\u0010\u0010\u001a\u00020\u000f\u0012\u0006\u0010\u0003\u001a\u00020\u0002\u00a2\u0006\u0004\b\u0011\u0010\u0012R\u0017\u0010\u0003\u001a\u00020\u00028\u0006\u00a2\u0006\f\n\u0004\b\u0003\u0010\u0004\u001a\u0004\b\u0005\u0010\u0006R\u001a\u0010\u0007\u001a\u00020\u00028\u0016X\u0096\u0004\u00a2\u0006\f\n\u0004\b\u0007\u0010\u0004\u001a\u0004\b\b\u0010\u0006R\u001a\u0010\t\u001a\u00020\u00028\u0016X\u0096\u0004\u00a2\u0006\f\n\u0004\b\t\u0010\u0004\u001a\u0004\b\n\u0010\u0006\u00a8\u0006\u0013"}, d2={"Lcom/cobblemon/mod/common/client/render/models/blockbench/bedrock/animation/SimpleBedrockAnimationKeyFrame;", "Lcom/cobblemon/mod/common/client/render/models/blockbench/bedrock/animation/BedrockAnimationKeyFrame;", "Lcom/cobblemon/mod/common/client/render/models/blockbench/bedrock/animation/MolangBoneValue;", "data", "Lcom/cobblemon/mod/common/client/render/models/blockbench/bedrock/animation/MolangBoneValue;", "getData", "()Lcom/cobblemon/mod/common/client/render/models/blockbench/bedrock/animation/MolangBoneValue;", "post", "getPost", "pre", "getPre", "", "time", "Lcom/cobblemon/mod/common/client/render/models/blockbench/bedrock/animation/Transformation;", "transformation", "Lcom/cobblemon/mod/common/client/render/models/blockbench/bedrock/animation/InterpolationType;", "interpolationType", "<init>", "(DLcom/cobblemon/mod/common/client/render/models/blockbench/bedrock/animation/Transformation;Lcom/cobblemon/mod/common/client/render/models/blockbench/bedrock/animation/InterpolationType;Lcom/cobblemon/mod/common/client/render/models/blockbench/bedrock/animation/MolangBoneValue;)V", "common"})
public final class SimpleBedrockAnimationKeyFrame
extends BedrockAnimationKeyFrame {
    @NotNull
    private final MolangBoneValue data;
    @NotNull
    private final MolangBoneValue pre;
    @NotNull
    private final MolangBoneValue post;

    public SimpleBedrockAnimationKeyFrame(double time, @NotNull Transformation transformation, @NotNull InterpolationType interpolationType, @NotNull MolangBoneValue data) {
        Intrinsics.checkNotNullParameter((Object)((Object)transformation), (String)"transformation");
        Intrinsics.checkNotNullParameter((Object)((Object)interpolationType), (String)"interpolationType");
        Intrinsics.checkNotNullParameter((Object)data, (String)"data");
        super(time, transformation, interpolationType);
        this.pre = this.data = data;
        this.post = this.data;
    }

    @NotNull
    public final MolangBoneValue getData() {
        return this.data;
    }

    @Override
    @NotNull
    public MolangBoneValue getPre() {
        return this.pre;
    }

    @Override
    @NotNull
    public MolangBoneValue getPost() {
        return this.post;
    }
}

