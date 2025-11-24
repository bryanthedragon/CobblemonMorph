/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  kotlin.Metadata
 *  kotlin.Unit
 *  kotlin.jvm.functions.Function0
 *  kotlin.jvm.functions.Function1
 *  org.jetbrains.annotations.NotNull
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.scheduling;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.scheduling.Schedulable;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.scheduling.ScheduledTask;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.scheduling.SchedulingTracker;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000\u001c\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0010\t\n\u0002\b\f\b\u00c6\u0002\u0018\u00002\u00020\u00012\u00020\u0002B\t\b\u0002\u00a2\u0006\u0004\b\u0011\u0010\u0005J\r\u0010\u0004\u001a\u00020\u0003\u00a2\u0006\u0004\b\u0004\u0010\u0005R\"\u0010\u0007\u001a\u00020\u00068\u0006@\u0006X\u0086\u000e\u00a2\u0006\u0012\n\u0004\b\u0007\u0010\b\u001a\u0004\b\t\u0010\n\"\u0004\b\u000b\u0010\fR\u001a\u0010\r\u001a\u00020\u00008\u0016X\u0096\u0004\u00a2\u0006\f\n\u0004\b\r\u0010\u000e\u001a\u0004\b\u000f\u0010\u0010\u00a8\u0006\u0012"}, d2={"Lcom/cobblemon/mod/common/api/scheduling/ServerRealTimeTaskTracker;", "Lcom/cobblemon/mod/common/api/scheduling/Schedulable;", "Lcom/cobblemon/mod/common/api/scheduling/SchedulingTracker;", "", "update", "()V", "", "lastTicked", "J", "getLastTicked", "()J", "setLastTicked", "(J)V", "schedulingTracker", "Lcom/cobblemon/mod/common/api/scheduling/ServerRealTimeTaskTracker;", "getSchedulingTracker", "()Lcom/cobblemon/mod/common/api/scheduling/ServerRealTimeTaskTracker;", "<init>", "common"})
public final class ServerRealTimeTaskTracker
extends SchedulingTracker
implements Schedulable {
    @NotNull
    public static final ServerRealTimeTaskTracker INSTANCE;
    @NotNull
    private static final ServerRealTimeTaskTracker schedulingTracker;
    private static long lastTicked;

    private ServerRealTimeTaskTracker() {
    }

    @Override
    @NotNull
    public ServerRealTimeTaskTracker getSchedulingTracker() {
        return schedulingTracker;
    }

    public final long getLastTicked() {
        return lastTicked;
    }

    public final void setLastTicked(long l) {
        lastTicked = l;
    }

    public final void update() {
        long now = System.currentTimeMillis();
        long delta = now - lastTicked;
        lastTicked = now;
        this.update((float)delta / 1000.0f);
    }

    @Override
    @NotNull
    public ScheduledTask momentarily(@NotNull Function0<Unit> action2) {
        return Schedulable.DefaultImpls.momentarily(this, action2);
    }

    @Override
    @NotNull
    public ScheduledTask after(float seconds, @NotNull Function0<Unit> action2) {
        return Schedulable.DefaultImpls.after(this, seconds, action2);
    }

    @Override
    @NotNull
    public ScheduledTask lerp(float seconds, @NotNull Function1<? super Float, Unit> action2) {
        return Schedulable.DefaultImpls.lerp(this, seconds, action2);
    }

    @Override
    @NotNull
    public ScheduledTask.Builder taskBuilder() {
        return Schedulable.DefaultImpls.taskBuilder(this);
    }

    static {
        schedulingTracker = INSTANCE = new ServerRealTimeTaskTracker();
        lastTicked = System.currentTimeMillis();
    }
}

