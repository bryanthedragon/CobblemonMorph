/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  kotlin.Metadata
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.bedrock.animation;

import kotlin.Metadata;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0010\u0010\n\u0002\b\u0005\b\u0086\u0001\u0018\u00002\b\u0012\u0004\u0012\u00020\u00000\u0001B\t\b\u0002\u00a2\u0006\u0004\b\u0002\u0010\u0003j\u0002\b\u0004j\u0002\b\u0005\u00a8\u0006\u0006"}, d2={"Lcom/cobblemon/mod/common/client/render/models/blockbench/bedrock/animation/InterpolationType;", "", "<init>", "(Ljava/lang/String;I)V", "SMOOTH", "LINEAR", "common"})
public final class InterpolationType
extends Enum<InterpolationType> {
    public static final /* enum */ InterpolationType SMOOTH = new InterpolationType();
    public static final /* enum */ InterpolationType LINEAR = new InterpolationType();
    private static final /* synthetic */ InterpolationType[] $VALUES;

    public static InterpolationType[] values() {
        return (InterpolationType[])$VALUES.clone();
    }

    public static InterpolationType valueOf(String value2) {
        return Enum.valueOf(InterpolationType.class, value2);
    }

    static {
        $VALUES = interpolationTypeArray = new InterpolationType[]{InterpolationType.SMOOTH, InterpolationType.LINEAR};
    }
}

