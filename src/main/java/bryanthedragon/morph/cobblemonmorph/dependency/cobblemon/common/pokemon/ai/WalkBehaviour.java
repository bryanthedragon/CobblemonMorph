/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  kotlin.Metadata
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.ai;

import kotlin.Metadata;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0010\u000b\n\u0002\b\u0006\n\u0002\u0010\u0007\n\u0002\b\t\u0018\u00002\u00020\u0001B\u0007\u00a2\u0006\u0004\b\u0010\u0010\u0011R\u001a\u0010\u0003\u001a\u00020\u00028\u0006X\u0086D\u00a2\u0006\f\n\u0004\b\u0003\u0010\u0004\u001a\u0004\b\u0005\u0010\u0006R\u001a\u0010\u0007\u001a\u00020\u00028\u0006X\u0086D\u00a2\u0006\f\n\u0004\b\u0007\u0010\u0004\u001a\u0004\b\b\u0010\u0006R\"\u0010\n\u001a\u00020\t8\u0006@\u0006X\u0086\u000e\u00a2\u0006\u0012\n\u0004\b\n\u0010\u000b\u001a\u0004\b\f\u0010\r\"\u0004\b\u000e\u0010\u000f\u00a8\u0006\u0012"}, d2={"Lcom/cobblemon/mod/common/pokemon/ai/WalkBehaviour;", "", "", "avoidsLand", "Z", "getAvoidsLand", "()Z", "canWalk", "getCanWalk", "", "walkSpeed", "F", "getWalkSpeed", "()F", "setWalkSpeed", "(F)V", "<init>", "()V", "common"})
public final class WalkBehaviour {
    private final boolean canWalk;
    private final boolean avoidsLand;
    private float walkSpeed = 0.35f;

    public WalkBehaviour() {
        this.canWalk = true;
    }

    public final boolean getCanWalk() {
        return this.canWalk;
    }

    public final boolean getAvoidsLand() {
        return this.avoidsLand;
    }

    public final float getWalkSpeed() {
        return this.walkSpeed;
    }

    public final void setWalkSpeed(float f) {
        this.walkSpeed = f;
    }
}

