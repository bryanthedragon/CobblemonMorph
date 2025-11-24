/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  kotlin.Metadata
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.snowstorm;

import kotlin.Metadata;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0010\u0010\n\u0002\b\u0007\b\u0086\u0001\u0018\u00002\b\u0012\u0004\u0012\u00020\u00000\u0001B\t\b\u0002\u00a2\u0006\u0004\b\u0002\u0010\u0003j\u0002\b\u0004j\u0002\b\u0005j\u0002\b\u0006j\u0002\b\u0007\u00a8\u0006\b"}, d2={"Lcom/cobblemon/mod/common/api/snowstorm/ParticleEmitterAction;", "", "<init>", "(Ljava/lang/String;I)V", "NOTHING", "GO", "STOP", "RESET", "common"})
public final class ParticleEmitterAction
extends Enum<ParticleEmitterAction> {
    public static final /* enum */ ParticleEmitterAction NOTHING = new ParticleEmitterAction();
    public static final /* enum */ ParticleEmitterAction GO = new ParticleEmitterAction();
    public static final /* enum */ ParticleEmitterAction STOP = new ParticleEmitterAction();
    public static final /* enum */ ParticleEmitterAction RESET = new ParticleEmitterAction();
    private static final /* synthetic */ ParticleEmitterAction[] $VALUES;

    public static ParticleEmitterAction[] values() {
        return (ParticleEmitterAction[])$VALUES.clone();
    }

    public static ParticleEmitterAction valueOf(String value2) {
        return Enum.valueOf(ParticleEmitterAction.class, value2);
    }

    static {
        $VALUES = particleEmitterActionArray = new ParticleEmitterAction[]{ParticleEmitterAction.NOTHING, ParticleEmitterAction.GO, ParticleEmitterAction.STOP, ParticleEmitterAction.RESET};
    }
}

