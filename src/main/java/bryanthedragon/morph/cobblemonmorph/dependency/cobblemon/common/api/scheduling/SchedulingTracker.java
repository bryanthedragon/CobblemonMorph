/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  kotlin.Metadata
 *  kotlin.collections.CollectionsKt
 *  kotlin.jvm.internal.Intrinsics
 *  org.jetbrains.annotations.NotNull
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.scheduling;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.scheduling.ScheduledTask;
import java.util.ArrayList;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000(\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0010\u0007\n\u0002\b\u0003\n\u0002\u0010!\n\u0002\b\u0004\b\u0016\u0018\u00002\u00020\u0001B\u0007\u00a2\u0006\u0004\b\u0010\u0010\bJ\u0015\u0010\u0004\u001a\u00020\u00022\u0006\u0010\u0003\u001a\u00020\u0002\u00a2\u0006\u0004\b\u0004\u0010\u0005J\r\u0010\u0007\u001a\u00020\u0006\u00a2\u0006\u0004\b\u0007\u0010\bJ\u0015\u0010\u000b\u001a\u00020\u00062\u0006\u0010\n\u001a\u00020\t\u00a2\u0006\u0004\b\u000b\u0010\fR\u001a\u0010\u000e\u001a\b\u0012\u0004\u0012\u00020\u00020\r8\u0002X\u0082\u0004\u00a2\u0006\u0006\n\u0004\b\u000e\u0010\u000f\u00a8\u0006\u0011"}, d2={"Lcom/cobblemon/mod/common/api/scheduling/SchedulingTracker;", "", "Lcom/cobblemon/mod/common/api/scheduling/ScheduledTask;", "task", "addTask", "(Lcom/cobblemon/mod/common/api/scheduling/ScheduledTask;)Lcom/cobblemon/mod/common/api/scheduling/ScheduledTask;", "", "clear", "()V", "", "deltaSeconds", "update", "(F)V", "", "tasks", "Ljava/util/List;", "<init>", "common"})
public class SchedulingTracker {
    @NotNull
    private final List<ScheduledTask> tasks = new ArrayList();

    public final void clear() {
        this.tasks.clear();
    }

    public final void update(float deltaSeconds) {
        for (ScheduledTask task : CollectionsKt.toList((Iterable)this.tasks)) {
            task.update(deltaSeconds);
            if (!task.getExpired()) continue;
            this.tasks.remove(task);
        }
    }

    @NotNull
    public final ScheduledTask addTask(@NotNull ScheduledTask task) {
        Intrinsics.checkNotNullParameter((Object)task, (String)"task");
        this.tasks.add(task);
        return task;
    }
}

