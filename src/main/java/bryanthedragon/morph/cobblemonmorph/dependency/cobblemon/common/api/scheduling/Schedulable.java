/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  kotlin.Metadata
 *  kotlin.Unit
 *  kotlin.jvm.functions.Function0
 *  kotlin.jvm.functions.Function1
 *  kotlin.jvm.internal.Intrinsics
 *  kotlin.jvm.internal.Ref$FloatRef
 *  org.jetbrains.annotations.NotNull
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.scheduling;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.scheduling.ScheduledTask;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.scheduling.SchedulingTracker;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Ref;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u00008\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0010\u0007\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\bf\u0018\u00002\u00020\u0001J'\u0010\b\u001a\u00020\u00072\b\b\u0002\u0010\u0003\u001a\u00020\u00022\f\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004H\u0016\u00a2\u0006\u0004\b\b\u0010\tJ-\u0010\u000b\u001a\u00020\u00072\b\b\u0002\u0010\u0003\u001a\u00020\u00022\u0012\u0010\u0006\u001a\u000e\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020\u00050\nH\u0016\u00a2\u0006\u0004\b\u000b\u0010\fJ\u001d\u0010\r\u001a\u00020\u00072\f\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004H\u0016\u00a2\u0006\u0004\b\r\u0010\u000eJ\u000f\u0010\u0010\u001a\u00020\u000fH\u0016\u00a2\u0006\u0004\b\u0010\u0010\u0011R\u0014\u0010\u0015\u001a\u00020\u00128&X\u00a6\u0004\u00a2\u0006\u0006\u001a\u0004\b\u0013\u0010\u0014\u00a8\u0006\u0016"}, d2={"Lcom/cobblemon/mod/common/api/scheduling/Schedulable;", "", "", "seconds", "Lkotlin/Function0;", "", "action", "Lcom/cobblemon/mod/common/api/scheduling/ScheduledTask;", "after", "(FLkotlin/jvm/functions/Function0;)Lcom/cobblemon/mod/common/api/scheduling/ScheduledTask;", "Lkotlin/Function1;", "lerp", "(FLkotlin/jvm/functions/Function1;)Lcom/cobblemon/mod/common/api/scheduling/ScheduledTask;", "momentarily", "(Lkotlin/jvm/functions/Function0;)Lcom/cobblemon/mod/common/api/scheduling/ScheduledTask;", "Lcom/cobblemon/mod/common/api/scheduling/ScheduledTask$Builder;", "taskBuilder", "()Lcom/cobblemon/mod/common/api/scheduling/ScheduledTask$Builder;", "Lcom/cobblemon/mod/common/api/scheduling/SchedulingTracker;", "getSchedulingTracker", "()Lcom/cobblemon/mod/common/api/scheduling/SchedulingTracker;", "schedulingTracker", "common"})
public interface Schedulable {
    @NotNull
    public SchedulingTracker getSchedulingTracker();

    @NotNull
    public ScheduledTask momentarily(@NotNull Function0<Unit> var1);

    @NotNull
    public ScheduledTask after(float var1, @NotNull Function0<Unit> var2);

    @NotNull
    public ScheduledTask lerp(float var1, @NotNull Function1<? super Float, Unit> var2);

    @NotNull
    public ScheduledTask.Builder taskBuilder();

    @Metadata(mv={1, 8, 0}, k=3, xi=48)
    public static final class DefaultImpls {
        @NotNull
        public static ScheduledTask momentarily(@NotNull Schedulable $this, @NotNull Function0<Unit> action2) {
            Intrinsics.checkNotNullParameter(action2, (String)"action");
            return DefaultImpls.after$default($this, 0.0f, action2, 1, null);
        }

        @NotNull
        public static ScheduledTask after(@NotNull Schedulable $this, float seconds, @NotNull Function0<Unit> action2) {
            Intrinsics.checkNotNullParameter(action2, (String)"action");
            return $this.getSchedulingTracker().addTask(new ScheduledTask((Function1)new Function1<ScheduledTask, Unit>(action2){
                final /* synthetic */ Function0<Unit> $action;
                {
                    this.$action = $action;
                    super(1);
                }

                public final void invoke(@NotNull ScheduledTask it) {
                    Intrinsics.checkNotNullParameter((Object)it, (String)"it");
                    this.$action.invoke();
                }
            }, null, seconds, 0.0f, 0, 26, null));
        }

        public static /* synthetic */ ScheduledTask after$default(Schedulable schedulable, float f, Function0 function0, int n, Object object) {
            if (object != null) {
                throw new UnsupportedOperationException("Super calls with default arguments not supported in this target, function: after");
            }
            if ((n & 1) != 0) {
                f = 0.0f;
            }
            return schedulable.after(f, (Function0<Unit>)function0);
        }

        @NotNull
        public static ScheduledTask lerp(@NotNull Schedulable $this, float seconds, @NotNull Function1<? super Float, Unit> action2) {
            Intrinsics.checkNotNullParameter(action2, (String)"action");
            Ref.FloatRef passed = new Ref.FloatRef();
            if (seconds == 0.0f) {
                action2.invoke((Object)Float.valueOf(1.0f));
                return ScheduledTask.Companion.getBLANK();
            }
            action2.invoke((Object)Float.valueOf(passed.element / seconds));
            return !(passed.element / seconds == 1.0f) ? $this.taskBuilder().tracker($this.getSchedulingTracker()).interval(0.0f).iterations(-1).execute((Function1<? super ScheduledTask, Unit>)((Function1)new Function1<ScheduledTask, Unit>(passed, seconds, action2){
                final /* synthetic */ Ref.FloatRef $passed;
                final /* synthetic */ float $seconds;
                final /* synthetic */ Function1<Float, Unit> $action;
                {
                    this.$passed = $passed;
                    this.$seconds = $seconds;
                    this.$action = $action;
                    super(1);
                }

                public final void invoke(@NotNull ScheduledTask task) {
                    Intrinsics.checkNotNullParameter((Object)task, (String)"task");
                    this.$passed.element = task.getSecondsPassed();
                    if (this.$passed.element > this.$seconds) {
                        this.$passed.element = this.$seconds;
                    }
                    float ratio = this.$passed.element / this.$seconds;
                    this.$action.invoke((Object)Float.valueOf(ratio));
                    if (this.$passed.element >= this.$seconds) {
                        task.expire();
                    }
                }
            })).build() : ScheduledTask.Companion.getBLANK();
        }

        public static /* synthetic */ ScheduledTask lerp$default(Schedulable schedulable, float f, Function1 function1, int n, Object object) {
            if (object != null) {
                throw new UnsupportedOperationException("Super calls with default arguments not supported in this target, function: lerp");
            }
            if ((n & 1) != 0) {
                f = 0.0f;
            }
            return schedulable.lerp(f, (Function1<? super Float, Unit>)function1);
        }

        @NotNull
        public static ScheduledTask.Builder taskBuilder(@NotNull Schedulable $this) {
            return new ScheduledTask.Builder().tracker($this.getSchedulingTracker());
        }
    }
}

