/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  kotlin.Metadata
 *  kotlin.jvm.internal.Intrinsics
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.bedrock.animation;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.bedrock.animation.BedrockBoneValue;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000(\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0018\u0002\n\u0002\b\n\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\t\b\u0086\b\u0018\u00002\u00020\u0001B\u001f\u0012\u0006\u0010\u0007\u001a\u00020\u0002\u0012\u0006\u0010\b\u001a\u00020\u0002\u0012\u0006\u0010\t\u001a\u00020\u0002\u00a2\u0006\u0004\b\u001a\u0010\u001bJ\u0010\u0010\u0003\u001a\u00020\u0002H\u00c6\u0003\u00a2\u0006\u0004\b\u0003\u0010\u0004J\u0010\u0010\u0005\u001a\u00020\u0002H\u00c6\u0003\u00a2\u0006\u0004\b\u0005\u0010\u0004J\u0010\u0010\u0006\u001a\u00020\u0002H\u00c6\u0003\u00a2\u0006\u0004\b\u0006\u0010\u0004J.\u0010\n\u001a\u00020\u00002\b\b\u0002\u0010\u0007\u001a\u00020\u00022\b\b\u0002\u0010\b\u001a\u00020\u00022\b\b\u0002\u0010\t\u001a\u00020\u0002H\u00c6\u0001\u00a2\u0006\u0004\b\n\u0010\u000bJ\u001a\u0010\u000e\u001a\u00020\r2\b\u0010\f\u001a\u0004\u0018\u00010\u0001H\u00d6\u0003\u00a2\u0006\u0004\b\u000e\u0010\u000fJ\u0010\u0010\u0011\u001a\u00020\u0010H\u00d6\u0001\u00a2\u0006\u0004\b\u0011\u0010\u0012J\u0010\u0010\u0014\u001a\u00020\u0013H\u00d6\u0001\u00a2\u0006\u0004\b\u0014\u0010\u0015R\u0017\u0010\u0007\u001a\u00020\u00028\u0006\u00a2\u0006\f\n\u0004\b\u0007\u0010\u0016\u001a\u0004\b\u0017\u0010\u0004R\u0017\u0010\b\u001a\u00020\u00028\u0006\u00a2\u0006\f\n\u0004\b\b\u0010\u0016\u001a\u0004\b\u0018\u0010\u0004R\u0017\u0010\t\u001a\u00020\u00028\u0006\u00a2\u0006\f\n\u0004\b\t\u0010\u0016\u001a\u0004\b\u0019\u0010\u0004\u00a8\u0006\u001c"}, d2={"Lcom/cobblemon/mod/common/client/render/models/blockbench/bedrock/animation/BedrockBoneTimeline;", "", "Lcom/cobblemon/mod/common/client/render/models/blockbench/bedrock/animation/BedrockBoneValue;", "component1", "()Lcom/cobblemon/mod/common/client/render/models/blockbench/bedrock/animation/BedrockBoneValue;", "component2", "component3", "position", "rotation", "scale", "copy", "(Lcom/cobblemon/mod/common/client/render/models/blockbench/bedrock/animation/BedrockBoneValue;Lcom/cobblemon/mod/common/client/render/models/blockbench/bedrock/animation/BedrockBoneValue;Lcom/cobblemon/mod/common/client/render/models/blockbench/bedrock/animation/BedrockBoneValue;)Lcom/cobblemon/mod/common/client/render/models/blockbench/bedrock/animation/BedrockBoneTimeline;", "other", "", "equals", "(Ljava/lang/Object;)Z", "", "hashCode", "()I", "", "toString", "()Ljava/lang/String;", "Lcom/cobblemon/mod/common/client/render/models/blockbench/bedrock/animation/BedrockBoneValue;", "getPosition", "getRotation", "getScale", "<init>", "(Lcom/cobblemon/mod/common/client/render/models/blockbench/bedrock/animation/BedrockBoneValue;Lcom/cobblemon/mod/common/client/render/models/blockbench/bedrock/animation/BedrockBoneValue;Lcom/cobblemon/mod/common/client/render/models/blockbench/bedrock/animation/BedrockBoneValue;)V", "common"})
public final class BedrockBoneTimeline {
    @NotNull
    private final BedrockBoneValue position;
    @NotNull
    private final BedrockBoneValue rotation;
    @NotNull
    private final BedrockBoneValue scale;

    public BedrockBoneTimeline(@NotNull BedrockBoneValue position, @NotNull BedrockBoneValue rotation, @NotNull BedrockBoneValue scale) {
        Intrinsics.checkNotNullParameter((Object)position, (String)"position");
        Intrinsics.checkNotNullParameter((Object)rotation, (String)"rotation");
        Intrinsics.checkNotNullParameter((Object)scale, (String)"scale");
        this.position = position;
        this.rotation = rotation;
        this.scale = scale;
    }

    @NotNull
    public final BedrockBoneValue getPosition() {
        return this.position;
    }

    @NotNull
    public final BedrockBoneValue getRotation() {
        return this.rotation;
    }

    @NotNull
    public final BedrockBoneValue getScale() {
        return this.scale;
    }

    @NotNull
    public final BedrockBoneValue component1() {
        return this.position;
    }

    @NotNull
    public final BedrockBoneValue component2() {
        return this.rotation;
    }

    @NotNull
    public final BedrockBoneValue component3() {
        return this.scale;
    }

    @NotNull
    public final BedrockBoneTimeline copy(@NotNull BedrockBoneValue position, @NotNull BedrockBoneValue rotation, @NotNull BedrockBoneValue scale) {
        Intrinsics.checkNotNullParameter((Object)position, (String)"position");
        Intrinsics.checkNotNullParameter((Object)rotation, (String)"rotation");
        Intrinsics.checkNotNullParameter((Object)scale, (String)"scale");
        return new BedrockBoneTimeline(position, rotation, scale);
    }

    public static /* synthetic */ BedrockBoneTimeline copy$default(BedrockBoneTimeline bedrockBoneTimeline, BedrockBoneValue bedrockBoneValue, BedrockBoneValue bedrockBoneValue2, BedrockBoneValue bedrockBoneValue3, int n, Object object) {
        if ((n & 1) != 0) {
            bedrockBoneValue = bedrockBoneTimeline.position;
        }
        if ((n & 2) != 0) {
            bedrockBoneValue2 = bedrockBoneTimeline.rotation;
        }
        if ((n & 4) != 0) {
            bedrockBoneValue3 = bedrockBoneTimeline.scale;
        }
        return bedrockBoneTimeline.copy(bedrockBoneValue, bedrockBoneValue2, bedrockBoneValue3);
    }

    @NotNull
    public String toString() {
        return "BedrockBoneTimeline(position=" + this.position + ", rotation=" + this.rotation + ", scale=" + this.scale + ")";
    }

    public int hashCode() {
        int result = this.position.hashCode();
        result = result * 31 + this.rotation.hashCode();
        result = result * 31 + this.scale.hashCode();
        return result;
    }

    public boolean equals(@Nullable Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof BedrockBoneTimeline)) {
            return false;
        }
        BedrockBoneTimeline bedrockBoneTimeline = (BedrockBoneTimeline)other;
        if (!Intrinsics.areEqual((Object)this.position, (Object)bedrockBoneTimeline.position)) {
            return false;
        }
        if (!Intrinsics.areEqual((Object)this.rotation, (Object)bedrockBoneTimeline.rotation)) {
            return false;
        }
        return Intrinsics.areEqual((Object)this.scale, (Object)bedrockBoneTimeline.scale);
    }
}

