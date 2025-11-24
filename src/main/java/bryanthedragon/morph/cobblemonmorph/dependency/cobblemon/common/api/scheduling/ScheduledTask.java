/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  kotlin.Metadata
 *  kotlin.Unit
 *  kotlin.jvm.functions.Function1
 *  kotlin.jvm.internal.DefaultConstructorMarker
 *  kotlin.jvm.internal.Intrinsics
 *  kotlin.jvm.internal.SourceDebugExtension
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.scheduling;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.scheduling.ScheduledTask;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.scheduling.SchedulingTracker;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.scheduling.ServerTaskTracker;
import java.util.concurrent.CompletableFuture;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000@\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010\u0007\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\b\n\u0002\b\u0005\n\u0002\u0010\u000b\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u001d\u0018\u0000 72\u00020\u0001:\u000287BC\u0012\u0012\u0010\r\u001a\u000e\u0012\u0004\u0012\u00020\u0000\u0012\u0004\u0012\u00020\u00020\f\u0012\n\b\u0002\u0010!\u001a\u0004\u0018\u00010\u0005\u0012\u0006\u00104\u001a\u00020\b\u0012\b\b\u0002\u0010$\u001a\u00020\b\u0012\b\b\u0002\u0010(\u001a\u00020\u0011\u00a2\u0006\u0004\b5\u00106J\r\u0010\u0003\u001a\u00020\u0002\u00a2\u0006\u0004\b\u0003\u0010\u0004J\u000f\u0010\u0006\u001a\u00020\u0005H\u0016\u00a2\u0006\u0004\b\u0006\u0010\u0007J\u0015\u0010\n\u001a\u00020\u00022\u0006\u0010\t\u001a\u00020\b\u00a2\u0006\u0004\b\n\u0010\u000bR#\u0010\r\u001a\u000e\u0012\u0004\u0012\u00020\u0000\u0012\u0004\u0012\u00020\u00020\f8\u0006\u00a2\u0006\f\n\u0004\b\r\u0010\u000e\u001a\u0004\b\u000f\u0010\u0010R$\u0010\u0013\u001a\u00020\u00112\u0006\u0010\u0012\u001a\u00020\u00118\u0006@BX\u0086\u000e\u00a2\u0006\f\n\u0004\b\u0013\u0010\u0014\u001a\u0004\b\u0015\u0010\u0016R$\u0010\u0018\u001a\u00020\u00172\u0006\u0010\u0012\u001a\u00020\u00178\u0006@BX\u0086\u000e\u00a2\u0006\f\n\u0004\b\u0018\u0010\u0019\u001a\u0004\b\u001a\u0010\u001bR\u001d\u0010\u001d\u001a\b\u0012\u0004\u0012\u00020\u00020\u001c8\u0006\u00a2\u0006\f\n\u0004\b\u001d\u0010\u001e\u001a\u0004\b\u001f\u0010 R\u0019\u0010!\u001a\u0004\u0018\u00010\u00058\u0006\u00a2\u0006\f\n\u0004\b!\u0010\"\u001a\u0004\b#\u0010\u0007R\u0017\u0010$\u001a\u00020\b8\u0006\u00a2\u0006\f\n\u0004\b$\u0010%\u001a\u0004\b&\u0010'R\u0017\u0010(\u001a\u00020\u00118\u0006\u00a2\u0006\f\n\u0004\b(\u0010\u0014\u001a\u0004\b)\u0010\u0016R*\u0010+\u001a\u00020\u00172\u0006\u0010*\u001a\u00020\u00178\u0006@FX\u0086\u000e\u00a2\u0006\u0012\n\u0004\b+\u0010\u0019\u001a\u0004\b,\u0010\u001b\"\u0004\b-\u0010.R\"\u0010/\u001a\u00020\b8\u0006@\u0006X\u0086\u000e\u00a2\u0006\u0012\n\u0004\b/\u0010%\u001a\u0004\b0\u0010'\"\u0004\b1\u0010\u000bR$\u00102\u001a\u00020\b2\u0006\u0010\u0012\u001a\u00020\b8\u0006@BX\u0086\u000e\u00a2\u0006\f\n\u0004\b2\u0010%\u001a\u0004\b3\u0010'\u00a8\u00069"}, d2={"Lcom/cobblemon/mod/common/api/scheduling/ScheduledTask;", "", "", "expire", "()V", "", "toString", "()Ljava/lang/String;", "", "deltaSeconds", "update", "(F)V", "Lkotlin/Function1;", "action", "Lkotlin/jvm/functions/Function1;", "getAction", "()Lkotlin/jvm/functions/Function1;", "", "<set-?>", "currentIteration", "I", "getCurrentIteration", "()I", "", "expired", "Z", "getExpired", "()Z", "Ljava/util/concurrent/CompletableFuture;", "future", "Ljava/util/concurrent/CompletableFuture;", "getFuture", "()Ljava/util/concurrent/CompletableFuture;", "identifier", "Ljava/lang/String;", "getIdentifier", "intervalSeconds", "F", "getIntervalSeconds", "()F", "iterations", "getIterations", "value", "paused", "getPaused", "setPaused", "(Z)V", "secondsPassed", "getSecondsPassed", "setSecondsPassed", "secondsRemaining", "getSecondsRemaining", "delaySeconds", "<init>", "(Lkotlin/jvm/functions/Function1;Ljava/lang/String;FFI)V", "Companion", "Builder", "common"})
@SourceDebugExtension(value={"SMAP\nScheduledTask.kt\nKotlin\n*S Kotlin\n*F\n+ 1 ScheduledTask.kt\ncom/cobblemon/mod/common/api/scheduling/ScheduledTask\n+ 2 fake.kt\nkotlin/jvm/internal/FakeKt\n*L\n1#1,185:1\n1#2:186\n*E\n"})
public final class ScheduledTask {
    @NotNull
    public static final Companion Companion = new Companion(null);
    @NotNull
    private final Function1<ScheduledTask, Unit> action;
    @Nullable
    private final String identifier;
    private final float intervalSeconds;
    private final int iterations;
    @NotNull
    private final CompletableFuture<Unit> future;
    private float secondsPassed;
    private int currentIteration;
    private float secondsRemaining;
    private boolean expired;
    private boolean paused;
    @NotNull
    private static final ScheduledTask BLANK = new ScheduledTask(Companion.BLANK.1.INSTANCE, null, 0.0f, 0.0f, 0, 26, null);

    public ScheduledTask(@NotNull Function1<? super ScheduledTask, Unit> action2, @Nullable String identifier, float delaySeconds, float intervalSeconds, int iterations) {
        Intrinsics.checkNotNullParameter(action2, (String)"action");
        this.action = action2;
        this.identifier = identifier;
        this.intervalSeconds = intervalSeconds;
        this.iterations = iterations;
        this.future = new CompletableFuture();
        if (delaySeconds > 0.0f) {
            this.secondsRemaining = delaySeconds;
        }
    }

    public /* synthetic */ ScheduledTask(Function1 function1, String string, float f, float f2, int n, int n2, DefaultConstructorMarker defaultConstructorMarker) {
        if ((n2 & 2) != 0) {
            string = null;
        }
        if ((n2 & 8) != 0) {
            f2 = -1.0f;
        }
        if ((n2 & 0x10) != 0) {
            n = 1;
        }
        this((Function1<? super ScheduledTask, Unit>)function1, string, f, f2, n);
    }

    @NotNull
    public final Function1<ScheduledTask, Unit> getAction() {
        return this.action;
    }

    @Nullable
    public final String getIdentifier() {
        return this.identifier;
    }

    public final float getIntervalSeconds() {
        return this.intervalSeconds;
    }

    public final int getIterations() {
        return this.iterations;
    }

    @NotNull
    public final CompletableFuture<Unit> getFuture() {
        return this.future;
    }

    public final float getSecondsPassed() {
        return this.secondsPassed;
    }

    public final void setSecondsPassed(float f) {
        this.secondsPassed = f;
    }

    public final int getCurrentIteration() {
        return this.currentIteration;
    }

    public final float getSecondsRemaining() {
        return this.secondsRemaining;
    }

    public final boolean getExpired() {
        return this.expired;
    }

    public final boolean getPaused() {
        return this.paused;
    }

    public final void setPaused(boolean value2) {
        this.paused = value2;
    }

    @NotNull
    public String toString() {
        Object object;
        block3: {
            block2: {
                object = this.identifier;
                if (object == null) break block2;
                String it = object;
                boolean bl = false;
                String string = "Task-" + it;
                object = string;
                if (string != null) break block3;
            }
            object = super.toString();
        }
        return object;
    }

    public final void update(float deltaSeconds) {
        if (!this.expired && !this.paused) {
            this.secondsPassed += deltaSeconds;
            this.secondsRemaining = Math.max(0.0f, this.secondsRemaining - deltaSeconds);
            if (this.secondsRemaining == 0.0f) {
                this.action.invoke((Object)this);
                int n = this.currentIteration;
                this.currentIteration = n + 1;
                if (!(this.intervalSeconds == -1.0f || this.currentIteration >= this.iterations && this.iterations != -1)) {
                    this.secondsRemaining = this.intervalSeconds;
                } else {
                    this.expired = true;
                }
            }
        }
    }

    public final void expire() {
        this.expired = true;
        this.future.complete(Unit.INSTANCE);
    }

    @Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000<\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0007\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0002\b\u0005\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u000b\u0018\u00002\u00020\u0001B\u0007\u00a2\u0006\u0004\b \u0010!J\r\u0010\u0003\u001a\u00020\u0002\u00a2\u0006\u0004\b\u0003\u0010\u0004J\u0015\u0010\u0007\u001a\u00020\u00002\u0006\u0010\u0006\u001a\u00020\u0005\u00a2\u0006\u0004\b\u0007\u0010\bJ!\u0010\f\u001a\u00020\u00002\u0012\u0010\u000b\u001a\u000e\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020\n0\t\u00a2\u0006\u0004\b\f\u0010\rJ\u0015\u0010\u000f\u001a\u00020\u00002\u0006\u0010\u000f\u001a\u00020\u000e\u00a2\u0006\u0004\b\u000f\u0010\u0010J\r\u0010\u0011\u001a\u00020\u0000\u00a2\u0006\u0004\b\u0011\u0010\u0012J\u0015\u0010\u0013\u001a\u00020\u00002\u0006\u0010\u0013\u001a\u00020\u0005\u00a2\u0006\u0004\b\u0013\u0010\bJ\u0015\u0010\u0015\u001a\u00020\u00002\u0006\u0010\u0015\u001a\u00020\u0014\u00a2\u0006\u0004\b\u0015\u0010\u0016J\u0015\u0010\u0019\u001a\u00020\u00002\u0006\u0010\u0018\u001a\u00020\u0017\u00a2\u0006\u0004\b\u0019\u0010\u001aR$\u0010\u000b\u001a\u0010\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020\n\u0018\u00010\t8\u0002@\u0002X\u0082\u000e\u00a2\u0006\u0006\n\u0004\b\u000b\u0010\u001bR\u0016\u0010\u0006\u001a\u00020\u00058\u0002@\u0002X\u0082\u000e\u00a2\u0006\u0006\n\u0004\b\u0006\u0010\u001cR\u0018\u0010\u000f\u001a\u0004\u0018\u00010\u000e8\u0002@\u0002X\u0082\u000e\u00a2\u0006\u0006\n\u0004\b\u000f\u0010\u001dR\u0016\u0010\u0013\u001a\u00020\u00058\u0002@\u0002X\u0082\u000e\u00a2\u0006\u0006\n\u0004\b\u0013\u0010\u001cR\u0016\u0010\u0015\u001a\u00020\u00148\u0002@\u0002X\u0082\u000e\u00a2\u0006\u0006\n\u0004\b\u0015\u0010\u001eR\u0018\u0010\u0019\u001a\u0004\u0018\u00010\u00178\u0002@\u0002X\u0082\u000e\u00a2\u0006\u0006\n\u0004\b\u0019\u0010\u001f\u00a8\u0006\""}, d2={"Lcom/cobblemon/mod/common/api/scheduling/ScheduledTask$Builder;", "", "Lcom/cobblemon/mod/common/api/scheduling/ScheduledTask;", "build", "()Lcom/cobblemon/mod/common/api/scheduling/ScheduledTask;", "", "delaySeconds", "delay", "(F)Lcom/cobblemon/mod/common/api/scheduling/ScheduledTask$Builder;", "Lkotlin/Function1;", "", "action", "execute", "(Lkotlin/jvm/functions/Function1;)Lcom/cobblemon/mod/common/api/scheduling/ScheduledTask$Builder;", "", "identifier", "(Ljava/lang/String;)Lcom/cobblemon/mod/common/api/scheduling/ScheduledTask$Builder;", "infiniteIterations", "()Lcom/cobblemon/mod/common/api/scheduling/ScheduledTask$Builder;", "interval", "", "iterations", "(I)Lcom/cobblemon/mod/common/api/scheduling/ScheduledTask$Builder;", "Lcom/cobblemon/mod/common/api/scheduling/SchedulingTracker;", "schedulingTracker", "tracker", "(Lcom/cobblemon/mod/common/api/scheduling/SchedulingTracker;)Lcom/cobblemon/mod/common/api/scheduling/ScheduledTask$Builder;", "Lkotlin/jvm/functions/Function1;", "F", "Ljava/lang/String;", "I", "Lcom/cobblemon/mod/common/api/scheduling/SchedulingTracker;", "<init>", "()V", "common"})
    @SourceDebugExtension(value={"SMAP\nScheduledTask.kt\nKotlin\n*S Kotlin\n*F\n+ 1 ScheduledTask.kt\ncom/cobblemon/mod/common/api/scheduling/ScheduledTask$Builder\n+ 2 fake.kt\nkotlin/jvm/internal/FakeKt\n*L\n1#1,185:1\n1#2:186\n*E\n"})
    public static final class Builder {
        @Nullable
        private Function1<? super ScheduledTask, Unit> action;
        private float delaySeconds;
        private float interval = -1.0f;
        private int iterations = 1;
        @Nullable
        private String identifier;
        @Nullable
        private SchedulingTracker tracker;

        @NotNull
        public final Builder identifier(@NotNull String identifier) {
            Intrinsics.checkNotNullParameter((Object)identifier, (String)"identifier");
            this.identifier = identifier;
            return this;
        }

        @NotNull
        public final Builder execute(@NotNull Function1<? super ScheduledTask, Unit> action2) {
            Intrinsics.checkNotNullParameter(action2, (String)"action");
            this.action = action2;
            return this;
        }

        @NotNull
        public final Builder delay(float delaySeconds) {
            if (!(delaySeconds >= 0.0f)) {
                boolean bl = false;
                String string = "Delay must not be below 0";
                throw new IllegalArgumentException(string.toString());
            }
            this.delaySeconds = delaySeconds;
            return this;
        }

        @NotNull
        public final Builder interval(float interval) {
            if (!(interval >= 0.0f)) {
                boolean bl = false;
                String string = "Interval must not be below 0";
                throw new IllegalArgumentException(string.toString());
            }
            this.interval = interval;
            return this;
        }

        @NotNull
        public final Builder iterations(int iterations) {
            if (!(iterations >= -1)) {
                boolean bl = false;
                String string = "Iterations must not be below -1";
                throw new IllegalArgumentException(string.toString());
            }
            this.iterations = iterations;
            return this;
        }

        @NotNull
        public final Builder infiniteIterations() {
            return this.iterations(-1);
        }

        @NotNull
        public final Builder tracker(@NotNull SchedulingTracker schedulingTracker) {
            Intrinsics.checkNotNullParameter((Object)schedulingTracker, (String)"schedulingTracker");
            this.tracker = schedulingTracker;
            return this;
        }

        @NotNull
        public final ScheduledTask build() {
            if (this.action == null) {
                boolean bl = false;
                String string = "action must be set";
                throw new IllegalStateException(string.toString());
            }
            Function1<? super ScheduledTask, Unit> function1 = this.action;
            Intrinsics.checkNotNull(function1);
            ScheduledTask task = new ScheduledTask(function1, this.identifier, this.delaySeconds, this.interval, this.iterations);
            SchedulingTracker schedulingTracker = this.tracker;
            if (schedulingTracker == null) {
                schedulingTracker = ServerTaskTracker.INSTANCE;
            }
            schedulingTracker.addTask(task);
            return task;
        }
    }

    @Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000\u0010\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0018\u0002\n\u0002\b\u0007\b\u0086\u0003\u0018\u00002\u00020\u0001B\t\b\u0002\u00a2\u0006\u0004\b\u0007\u0010\bR\u0017\u0010\u0003\u001a\u00020\u00028\u0006\u00a2\u0006\f\n\u0004\b\u0003\u0010\u0004\u001a\u0004\b\u0005\u0010\u0006\u00a8\u0006\t"}, d2={"Lcom/cobblemon/mod/common/api/scheduling/ScheduledTask$Companion;", "", "Lcom/cobblemon/mod/common/api/scheduling/ScheduledTask;", "BLANK", "Lcom/cobblemon/mod/common/api/scheduling/ScheduledTask;", "getBLANK", "()Lcom/cobblemon/mod/common/api/scheduling/ScheduledTask;", "<init>", "()V", "common"})
    public static final class Companion {
        private Companion() {
        }

        @NotNull
        public final ScheduledTask getBLANK() {
            return BLANK;
        }

        public /* synthetic */ Companion(DefaultConstructorMarker $constructor_marker) {
            this();
        }
    }
}

