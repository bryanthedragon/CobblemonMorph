/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  kotlin.Metadata
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.snowstorm;

import kotlin.Metadata;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0010\u0010\n\u0002\b\u000e\b\u0086\u0001\u0018\u00002\b\u0012\u0004\u0012\u00020\u00000\u0001B\t\b\u0002\u00a2\u0006\u0004\b\u0002\u0010\u0003j\u0002\b\u0004j\u0002\b\u0005j\u0002\b\u0006j\u0002\b\u0007j\u0002\b\bj\u0002\b\tj\u0002\b\nj\u0002\b\u000bj\u0002\b\fj\u0002\b\rj\u0002\b\u000e\u00a8\u0006\u000f"}, d2={"Lcom/cobblemon/mod/common/api/snowstorm/ParticleCameraModeType;", "", "<init>", "(Ljava/lang/String;I)V", "ROTATE_XYZ", "ROTATE_Y", "LOOK_AT_XYZ", "LOOK_AT_Y", "LOOK_AT_DIRECTION", "DIRECTION_X", "DIRECTION_Y", "DIRECTION_Z", "EMITTER_XY_PLANE", "EMITTER_XZ_PLANE", "EMITTER_YZ_PLANE", "common"})
public final class ParticleCameraModeType
extends Enum<ParticleCameraModeType> {
    public static final /* enum */ ParticleCameraModeType ROTATE_XYZ = new ParticleCameraModeType();
    public static final /* enum */ ParticleCameraModeType ROTATE_Y = new ParticleCameraModeType();
    public static final /* enum */ ParticleCameraModeType LOOK_AT_XYZ = new ParticleCameraModeType();
    public static final /* enum */ ParticleCameraModeType LOOK_AT_Y = new ParticleCameraModeType();
    public static final /* enum */ ParticleCameraModeType LOOK_AT_DIRECTION = new ParticleCameraModeType();
    public static final /* enum */ ParticleCameraModeType DIRECTION_X = new ParticleCameraModeType();
    public static final /* enum */ ParticleCameraModeType DIRECTION_Y = new ParticleCameraModeType();
    public static final /* enum */ ParticleCameraModeType DIRECTION_Z = new ParticleCameraModeType();
    public static final /* enum */ ParticleCameraModeType EMITTER_XY_PLANE = new ParticleCameraModeType();
    public static final /* enum */ ParticleCameraModeType EMITTER_XZ_PLANE = new ParticleCameraModeType();
    public static final /* enum */ ParticleCameraModeType EMITTER_YZ_PLANE = new ParticleCameraModeType();
    private static final /* synthetic */ ParticleCameraModeType[] $VALUES;

    public static ParticleCameraModeType[] values() {
        return (ParticleCameraModeType[])$VALUES.clone();
    }

    public static ParticleCameraModeType valueOf(String value2) {
        return Enum.valueOf(ParticleCameraModeType.class, value2);
    }

    static {
        $VALUES = particleCameraModeTypeArray = new ParticleCameraModeType[]{ParticleCameraModeType.ROTATE_XYZ, ParticleCameraModeType.ROTATE_Y, ParticleCameraModeType.LOOK_AT_XYZ, ParticleCameraModeType.LOOK_AT_Y, ParticleCameraModeType.LOOK_AT_DIRECTION, ParticleCameraModeType.DIRECTION_X, ParticleCameraModeType.DIRECTION_Y, ParticleCameraModeType.DIRECTION_Z, ParticleCameraModeType.EMITTER_XY_PLANE, ParticleCameraModeType.EMITTER_XZ_PLANE, ParticleCameraModeType.EMITTER_YZ_PLANE};
    }
}

