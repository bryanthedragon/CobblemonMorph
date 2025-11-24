/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  kotlin.Metadata
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.snowstorm;

import kotlin.Metadata;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0010\u0010\n\u0002\b\u0005\b\u0086\u0001\u0018\u00002\b\u0012\u0004\u0012\u00020\u00000\u0001B\t\b\u0002\u00a2\u0006\u0004\b\u0002\u0010\u0003j\u0002\b\u0004j\u0002\b\u0005\u00a8\u0006\u0006"}, d2={"Lcom/cobblemon/mod/common/api/snowstorm/ParticleTintingType;", "", "<init>", "(Ljava/lang/String;I)V", "EXPRESSION", "GRADIENT", "common"})
public final class ParticleTintingType
extends Enum<ParticleTintingType> {
    public static final /* enum */ ParticleTintingType EXPRESSION = new ParticleTintingType();
    public static final /* enum */ ParticleTintingType GRADIENT = new ParticleTintingType();
    private static final /* synthetic */ ParticleTintingType[] $VALUES;

    public static ParticleTintingType[] values() {
        return (ParticleTintingType[])$VALUES.clone();
    }

    public static ParticleTintingType valueOf(String value2) {
        return Enum.valueOf(ParticleTintingType.class, value2);
    }

    static {
        $VALUES = particleTintingTypeArray = new ParticleTintingType[]{ParticleTintingType.EXPRESSION, ParticleTintingType.GRADIENT};
    }
}

