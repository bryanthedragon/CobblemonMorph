/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  kotlin.Metadata
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.snowstorm;

import kotlin.Metadata;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0010\u0010\n\u0002\b\u0007\b\u0086\u0001\u0018\u00002\b\u0012\u0004\u0012\u00020\u00000\u0001B\t\b\u0002\u00a2\u0006\u0004\b\u0002\u0010\u0003j\u0002\b\u0004j\u0002\b\u0005j\u0002\b\u0006j\u0002\b\u0007\u00a8\u0006\b"}, d2={"Lcom/cobblemon/mod/common/api/snowstorm/ParticleMaterial;", "", "<init>", "(Ljava/lang/String;I)V", "ALPHA", "OPAQUE", "BLEND", "ADD", "common"})
public final class ParticleMaterial
extends Enum<ParticleMaterial> {
    public static final /* enum */ ParticleMaterial ALPHA = new ParticleMaterial();
    public static final /* enum */ ParticleMaterial OPAQUE = new ParticleMaterial();
    public static final /* enum */ ParticleMaterial BLEND = new ParticleMaterial();
    public static final /* enum */ ParticleMaterial ADD = new ParticleMaterial();
    private static final /* synthetic */ ParticleMaterial[] $VALUES;

    public static ParticleMaterial[] values() {
        return (ParticleMaterial[])$VALUES.clone();
    }

    public static ParticleMaterial valueOf(String value2) {
        return Enum.valueOf(ParticleMaterial.class, value2);
    }

    static {
        $VALUES = particleMaterialArray = new ParticleMaterial[]{ParticleMaterial.ALPHA, ParticleMaterial.OPAQUE, ParticleMaterial.BLEND, ParticleMaterial.ADD};
    }
}

