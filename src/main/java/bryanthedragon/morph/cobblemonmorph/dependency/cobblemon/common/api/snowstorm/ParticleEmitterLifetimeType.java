/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  kotlin.Metadata
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.snowstorm;

import kotlin.Metadata;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0010\u0010\n\u0002\b\u0006\b\u0086\u0001\u0018\u00002\b\u0012\u0004\u0012\u00020\u00000\u0001B\t\b\u0002\u00a2\u0006\u0004\b\u0002\u0010\u0003j\u0002\b\u0004j\u0002\b\u0005j\u0002\b\u0006\u00a8\u0006\u0007"}, d2={"Lcom/cobblemon/mod/common/api/snowstorm/ParticleEmitterLifetimeType;", "", "<init>", "(Ljava/lang/String;I)V", "LOOPING", "ONCE", "EXPRESSION", "common"})
public final class ParticleEmitterLifetimeType
extends Enum<ParticleEmitterLifetimeType> {
    public static final /* enum */ ParticleEmitterLifetimeType LOOPING = new ParticleEmitterLifetimeType();
    public static final /* enum */ ParticleEmitterLifetimeType ONCE = new ParticleEmitterLifetimeType();
    public static final /* enum */ ParticleEmitterLifetimeType EXPRESSION = new ParticleEmitterLifetimeType();
    private static final /* synthetic */ ParticleEmitterLifetimeType[] $VALUES;

    public static ParticleEmitterLifetimeType[] values() {
        return (ParticleEmitterLifetimeType[])$VALUES.clone();
    }

    public static ParticleEmitterLifetimeType valueOf(String value2) {
        return Enum.valueOf(ParticleEmitterLifetimeType.class, value2);
    }

    static {
        $VALUES = particleEmitterLifetimeTypeArray = new ParticleEmitterLifetimeType[]{ParticleEmitterLifetimeType.LOOPING, ParticleEmitterLifetimeType.ONCE, ParticleEmitterLifetimeType.EXPRESSION};
    }
}

