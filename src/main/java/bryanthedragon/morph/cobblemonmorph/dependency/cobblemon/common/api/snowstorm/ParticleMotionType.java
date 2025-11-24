/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  kotlin.Metadata
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.snowstorm;

import kotlin.Metadata;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0010\u0010\n\u0002\b\u0006\b\u0086\u0001\u0018\u00002\b\u0012\u0004\u0012\u00020\u00000\u0001B\t\b\u0002\u00a2\u0006\u0004\b\u0002\u0010\u0003j\u0002\b\u0004j\u0002\b\u0005j\u0002\b\u0006\u00a8\u0006\u0007"}, d2={"Lcom/cobblemon/mod/common/api/snowstorm/ParticleMotionType;", "", "<init>", "(Ljava/lang/String;I)V", "DYNAMIC", "PARAMETRIC", "STATIC", "common"})
public final class ParticleMotionType
extends Enum<ParticleMotionType> {
    public static final /* enum */ ParticleMotionType DYNAMIC = new ParticleMotionType();
    public static final /* enum */ ParticleMotionType PARAMETRIC = new ParticleMotionType();
    public static final /* enum */ ParticleMotionType STATIC = new ParticleMotionType();
    private static final /* synthetic */ ParticleMotionType[] $VALUES;

    public static ParticleMotionType[] values() {
        return (ParticleMotionType[])$VALUES.clone();
    }

    public static ParticleMotionType valueOf(String value2) {
        return Enum.valueOf(ParticleMotionType.class, value2);
    }

    static {
        $VALUES = particleMotionTypeArray = new ParticleMotionType[]{ParticleMotionType.DYNAMIC, ParticleMotionType.PARAMETRIC, ParticleMotionType.STATIC};
    }
}

