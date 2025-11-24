/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  kotlin.Deprecated
 *  kotlin.Metadata
 *  kotlin.Unit
 *  kotlin.jvm.JvmOverloads
 *  kotlin.jvm.functions.Function0
 *  kotlin.jvm.functions.Function1
 *  kotlin.jvm.internal.Intrinsics
 *  org.jetbrains.annotations.NotNull
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.scheduling;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.scheduling.ClientTaskTracker;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.scheduling.Schedulable;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.scheduling.ScheduledTask;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.scheduling.SchedulingTracker;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.scheduling.ServerTaskTracker;
import java.util.concurrent.CompletableFuture;
import kotlin.Deprecated;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.JvmOverloads;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 8, 0}, k=2, xi=48, d1={"\u0000>\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u0007\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0003\u001a;\u0010\t\u001a\u00020\u00072\b\b\u0002\u0010\u0001\u001a\u00020\u00002\b\b\u0002\u0010\u0003\u001a\u00020\u00022\b\b\u0002\u0010\u0005\u001a\u00020\u00042\f\u0010\b\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006H\u0007\u00a2\u0006\u0004\b\t\u0010\n\u001a/\u0010\f\u001a\u00020\u000b2\b\b\u0002\u0010\u0001\u001a\u00020\u00002\u0006\u0010\u0003\u001a\u00020\u00022\f\u0010\b\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006H\u0007\u00a2\u0006\u0004\b\f\u0010\r\u001a1\u0010\u000e\u001a\u00020\u000b2\b\b\u0002\u0010\u0001\u001a\u00020\u00002\b\b\u0002\u0010\u0003\u001a\u00020\u00022\f\u0010\b\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006H\u0007\u00a2\u0006\u0004\b\u000e\u0010\r\u001a1\u0010\u0010\u001a\b\u0012\u0004\u0012\u00020\u00070\u000f2\b\b\u0002\u0010\u0001\u001a\u00020\u00002\b\b\u0002\u0010\u0003\u001a\u00020\u00022\b\b\u0002\u0010\u0005\u001a\u00020\u0004\u00a2\u0006\u0004\b\u0010\u0010\u0011\u001a7\u0010\u0013\u001a\u00020\u000b2\b\b\u0002\u0010\u0003\u001a\u00020\u00022\b\b\u0002\u0010\u0005\u001a\u00020\u00042\u0012\u0010\b\u001a\u000e\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020\u00070\u0012H\u0007\u00a2\u0006\u0004\b\u0013\u0010\u0014\u001a-\u0010\u0015\u001a\u00020\u000b2\b\b\u0002\u0010\u0003\u001a\u00020\u00022\u0012\u0010\b\u001a\u000e\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020\u00070\u0012H\u0007\u00a2\u0006\u0004\b\u0015\u0010\u0016\u001a-\u0010\u0017\u001a\u00020\u000b2\b\b\u0002\u0010\u0003\u001a\u00020\u00022\u0012\u0010\b\u001a\u000e\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020\u00070\u0012H\u0007\u00a2\u0006\u0004\b\u0017\u0010\u0016\u001a\r\u0010\u0019\u001a\u00020\u0018\u00a2\u0006\u0004\b\u0019\u0010\u001a\u00a8\u0006\u001b"}, d2={"", "ticks", "", "seconds", "", "serverThread", "Lkotlin/Function0;", "", "action", "after", "(IFZLkotlin/jvm/functions/Function0;)V", "Lcom/cobblemon/mod/common/api/scheduling/ScheduledTask;", "afterOnClient", "(IFLkotlin/jvm/functions/Function0;)Lcom/cobblemon/mod/common/api/scheduling/ScheduledTask;", "afterOnServer", "Ljava/util/concurrent/CompletableFuture;", "delayedFuture", "(IFZ)Ljava/util/concurrent/CompletableFuture;", "Lkotlin/Function1;", "lerp", "(FZLkotlin/jvm/functions/Function1;)Lcom/cobblemon/mod/common/api/scheduling/ScheduledTask;", "lerpOnClient", "(FLkotlin/jvm/functions/Function1;)Lcom/cobblemon/mod/common/api/scheduling/ScheduledTask;", "lerpOnServer", "Lcom/cobblemon/mod/common/api/scheduling/ScheduledTask$Builder;", "taskBuilder", "()Lcom/cobblemon/mod/common/api/scheduling/ScheduledTask$Builder;", "common"})
public final class SchedulingFunctionsKt {
    @Deprecated(message="Use afterOnServer or afterOnClient; ambiguous side is not good for your health")
    @JvmOverloads
    public static final void after(int ticks, float seconds, boolean serverThread, @NotNull Function0<Unit> action2) {
        Intrinsics.checkNotNullParameter(action2, (String)"action");
        SchedulingTracker scheduler = serverThread ? ServerTaskTracker.INSTANCE : ClientTaskTracker.INSTANCE;
        ((Schedulable)((Object)scheduler)).after(seconds + (float)ticks / 20.0f, action2);
    }

    public static /* synthetic */ void after$default(int n, float f, boolean bl, Function0 function0, int n2, Object object) {
        if ((n2 & 1) != 0) {
            n = 0;
        }
        if ((n2 & 2) != 0) {
            f = 0.0f;
        }
        if ((n2 & 4) != 0) {
            bl = false;
        }
        SchedulingFunctionsKt.after(n, f, bl, (Function0<Unit>)function0);
    }

    @NotNull
    public static final CompletableFuture<Unit> delayedFuture(int ticks, float seconds, boolean serverThread) {
        CompletableFuture<Unit> future2 = new CompletableFuture<Unit>();
        if (ticks == 0 && seconds == 0.0f) {
            future2.complete(Unit.INSTANCE);
        } else {
            SchedulingFunctionsKt.after(ticks, seconds, serverThread, (Function0<Unit>)((Function0)new Function0<Unit>(future2){
                final /* synthetic */ CompletableFuture<Unit> $future;
                {
                    this.$future = $future;
                    super(0);
                }

                public final void invoke() {
                    this.$future.complete(Unit.INSTANCE);
                }
            }));
        }
        return future2;
    }

    public static /* synthetic */ CompletableFuture delayedFuture$default(int n, float f, boolean bl, int n2, Object object) {
        if ((n2 & 1) != 0) {
            n = 0;
        }
        if ((n2 & 2) != 0) {
            f = 0.0f;
        }
        if ((n2 & 4) != 0) {
            bl = false;
        }
        return SchedulingFunctionsKt.delayedFuture(n, f, bl);
    }

    @JvmOverloads
    @NotNull
    public static final ScheduledTask afterOnServer(int ticks, float seconds, @NotNull Function0<Unit> action2) {
        Intrinsics.checkNotNullParameter(action2, (String)"action");
        return ServerTaskTracker.INSTANCE.after(seconds + (float)ticks / 20.0f, action2);
    }

    public static /* synthetic */ ScheduledTask afterOnServer$default(int n, float f, Function0 function0, int n2, Object object) {
        if ((n2 & 1) != 0) {
            n = 0;
        }
        if ((n2 & 2) != 0) {
            f = 0.0f;
        }
        return SchedulingFunctionsKt.afterOnServer(n, f, (Function0<Unit>)function0);
    }

    @JvmOverloads
    @NotNull
    public static final ScheduledTask afterOnClient(int ticks, float seconds, @NotNull Function0<Unit> action2) {
        Intrinsics.checkNotNullParameter(action2, (String)"action");
        return ClientTaskTracker.INSTANCE.after(seconds + (float)ticks / 20.0f, action2);
    }

    public static /* synthetic */ ScheduledTask afterOnClient$default(int n, float f, Function0 function0, int n2, Object object) {
        if ((n2 & 1) != 0) {
            n = 0;
        }
        return SchedulingFunctionsKt.afterOnClient(n, f, (Function0<Unit>)function0);
    }

    @Deprecated(message="Use lerpOnServer or lerpOnClient, side-ambiguity causes problems now")
    @NotNull
    public static final ScheduledTask lerp(float seconds, boolean serverThread, @NotNull Function1<? super Float, Unit> action2) {
        Intrinsics.checkNotNullParameter(action2, (String)"action");
        return ((Schedulable)((Object)(serverThread ? ServerTaskTracker.INSTANCE : ClientTaskTracker.INSTANCE))).lerp(seconds, action2);
    }

    public static /* synthetic */ ScheduledTask lerp$default(float f, boolean bl, Function1 function1, int n, Object object) {
        if ((n & 1) != 0) {
            f = 0.0f;
        }
        if ((n & 2) != 0) {
            bl = false;
        }
        return SchedulingFunctionsKt.lerp(f, bl, (Function1<? super Float, Unit>)function1);
    }

    @JvmOverloads
    @NotNull
    public static final ScheduledTask lerpOnServer(float seconds, @NotNull Function1<? super Float, Unit> action2) {
        Intrinsics.checkNotNullParameter(action2, (String)"action");
        return ServerTaskTracker.INSTANCE.lerp(seconds, action2);
    }

    public static /* synthetic */ ScheduledTask lerpOnServer$default(float f, Function1 function1, int n, Object object) {
        if ((n & 1) != 0) {
            f = 0.0f;
        }
        return SchedulingFunctionsKt.lerpOnServer(f, (Function1<? super Float, Unit>)function1);
    }

    @JvmOverloads
    @NotNull
    public static final ScheduledTask lerpOnClient(float seconds, @NotNull Function1<? super Float, Unit> action2) {
        Intrinsics.checkNotNullParameter(action2, (String)"action");
        return ClientTaskTracker.INSTANCE.lerp(seconds, action2);
    }

    public static /* synthetic */ ScheduledTask lerpOnClient$default(float f, Function1 function1, int n, Object object) {
        if ((n & 1) != 0) {
            f = 0.0f;
        }
        return SchedulingFunctionsKt.lerpOnClient(f, (Function1<? super Float, Unit>)function1);
    }

    @NotNull
    public static final ScheduledTask.Builder taskBuilder() {
        return new ScheduledTask.Builder();
    }

    @Deprecated(message="Use afterOnServer or afterOnClient; ambiguous side is not good for your health")
    @JvmOverloads
    public static final void after(int ticks, float seconds, @NotNull Function0<Unit> action2) {
        Intrinsics.checkNotNullParameter(action2, (String)"action");
        SchedulingFunctionsKt.after$default(ticks, seconds, false, action2, 4, null);
    }

    @Deprecated(message="Use afterOnServer or afterOnClient; ambiguous side is not good for your health")
    @JvmOverloads
    public static final void after(int ticks, @NotNull Function0<Unit> action2) {
        Intrinsics.checkNotNullParameter(action2, (String)"action");
        SchedulingFunctionsKt.after$default(ticks, 0.0f, false, action2, 6, null);
    }

    @Deprecated(message="Use afterOnServer or afterOnClient; ambiguous side is not good for your health")
    @JvmOverloads
    public static final void after(@NotNull Function0<Unit> action2) {
        Intrinsics.checkNotNullParameter(action2, (String)"action");
        SchedulingFunctionsKt.after$default(0, 0.0f, false, action2, 7, null);
    }

    @JvmOverloads
    @NotNull
    public static final ScheduledTask afterOnServer(int ticks, @NotNull Function0<Unit> action2) {
        Intrinsics.checkNotNullParameter(action2, (String)"action");
        return SchedulingFunctionsKt.afterOnServer$default(ticks, 0.0f, action2, 2, null);
    }

    @JvmOverloads
    @NotNull
    public static final ScheduledTask afterOnServer(@NotNull Function0<Unit> action2) {
        Intrinsics.checkNotNullParameter(action2, (String)"action");
        return SchedulingFunctionsKt.afterOnServer$default(0, 0.0f, action2, 3, null);
    }

    @JvmOverloads
    @NotNull
    public static final ScheduledTask afterOnClient(float seconds, @NotNull Function0<Unit> action2) {
        Intrinsics.checkNotNullParameter(action2, (String)"action");
        return SchedulingFunctionsKt.afterOnClient$default(0, seconds, action2, 1, null);
    }

    @JvmOverloads
    @NotNull
    public static final ScheduledTask lerpOnServer(@NotNull Function1<? super Float, Unit> action2) {
        Intrinsics.checkNotNullParameter(action2, (String)"action");
        return SchedulingFunctionsKt.lerpOnServer$default(0.0f, action2, 1, null);
    }

    @JvmOverloads
    @NotNull
    public static final ScheduledTask lerpOnClient(@NotNull Function1<? super Float, Unit> action2) {
        Intrinsics.checkNotNullParameter(action2, (String)"action");
        return SchedulingFunctionsKt.lerpOnClient$default(0.0f, action2, 1, null);
    }
}

