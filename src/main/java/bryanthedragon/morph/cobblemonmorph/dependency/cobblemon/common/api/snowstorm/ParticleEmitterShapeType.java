/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  kotlin.Metadata
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.snowstorm;

import kotlin.Metadata;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0010\u0010\n\u0002\b\b\b\u0086\u0001\u0018\u00002\b\u0012\u0004\u0012\u00020\u00000\u0001B\t\b\u0002\u00a2\u0006\u0004\b\u0002\u0010\u0003j\u0002\b\u0004j\u0002\b\u0005j\u0002\b\u0006j\u0002\b\u0007j\u0002\b\b\u00a8\u0006\t"}, d2={"Lcom/cobblemon/mod/common/api/snowstorm/ParticleEmitterShapeType;", "", "<init>", "(Ljava/lang/String;I)V", "SPHERE", "POINT", "BOX", "DISC", "ENTITY_BOUNDING_BOX", "common"})
public final class ParticleEmitterShapeType
extends Enum<ParticleEmitterShapeType> {
    public static final /* enum */ ParticleEmitterShapeType SPHERE = new ParticleEmitterShapeType();
    public static final /* enum */ ParticleEmitterShapeType POINT = new ParticleEmitterShapeType();
    public static final /* enum */ ParticleEmitterShapeType BOX = new ParticleEmitterShapeType();
    public static final /* enum */ ParticleEmitterShapeType DISC = new ParticleEmitterShapeType();
    public static final /* enum */ ParticleEmitterShapeType ENTITY_BOUNDING_BOX = new ParticleEmitterShapeType();
    private static final /* synthetic */ ParticleEmitterShapeType[] $VALUES;

    public static ParticleEmitterShapeType[] values() {
        return (ParticleEmitterShapeType[])$VALUES.clone();
    }

    public static ParticleEmitterShapeType valueOf(String value2) {
        return Enum.valueOf(ParticleEmitterShapeType.class, value2);
    }

    static {
        $VALUES = particleEmitterShapeTypeArray = new ParticleEmitterShapeType[]{ParticleEmitterShapeType.SPHERE, ParticleEmitterShapeType.POINT, ParticleEmitterShapeType.BOX, ParticleEmitterShapeType.DISC, ParticleEmitterShapeType.ENTITY_BOUNDING_BOX};
    }
}

