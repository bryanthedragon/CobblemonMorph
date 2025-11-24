/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  kotlin.Metadata
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.dispatch;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.dispatch.DispatchResult;
import kotlin.Metadata;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000 \n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\t\n\u0002\b\u0004\n\u0002\u0010\u0007\n\u0002\b\u0004\u0018\u00002\u00020\u0001B\u000f\u0012\u0006\u0010\u000b\u001a\u00020\n\u00a2\u0006\u0004\b\f\u0010\rJ\u000f\u0010\u0003\u001a\u00020\u0002H\u0016\u00a2\u0006\u0004\b\u0003\u0010\u0004R\u0017\u0010\u0006\u001a\u00020\u00058\u0006\u00a2\u0006\f\n\u0004\b\u0006\u0010\u0007\u001a\u0004\b\b\u0010\t\u00a8\u0006\u000e"}, d2={"Lcom/cobblemon/mod/common/battles/dispatch/WaitDispatch;", "Lcom/cobblemon/mod/common/battles/dispatch/DispatchResult;", "", "canProceed", "()Z", "", "readyTime", "J", "getReadyTime", "()J", "", "delaySeconds", "<init>", "(F)V", "common"})
public final class WaitDispatch
implements DispatchResult {
    private final long readyTime;

    public WaitDispatch(float delaySeconds) {
        this.readyTime = System.currentTimeMillis() + (long)((int)(delaySeconds * (float)1000));
    }

    public final long getReadyTime() {
        return this.readyTime;
    }

    @Override
    public boolean canProceed() {
        return System.currentTimeMillis() >= this.readyTime;
    }
}

