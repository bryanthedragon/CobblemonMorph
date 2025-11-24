/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  kotlin.Metadata
 *  kotlin.Unit
 *  kotlin.jvm.functions.Function1
 *  kotlin.jvm.internal.Intrinsics
 *  org.jetbrains.annotations.NotNull
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.Cobblemon;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.runner.ShowdownService;
import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.CountDownLatch;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000,\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\u0018\u00002\u00020\u0001B\u0007\u00a2\u0006\u0004\b\u0011\u0010\u0004J\r\u0010\u0003\u001a\u00020\u0002\u00a2\u0006\u0004\b\u0003\u0010\u0004J!\u0010\b\u001a\u00020\u00022\u0012\u0010\u0007\u001a\u000e\u0012\u0004\u0012\u00020\u0006\u0012\u0004\u0012\u00020\u00020\u0005\u00a2\u0006\u0004\b\b\u0010\tJ\u000f\u0010\n\u001a\u00020\u0002H\u0016\u00a2\u0006\u0004\b\n\u0010\u0004R\u0014\u0010\f\u001a\u00020\u000b8\u0002X\u0082\u0004\u00a2\u0006\u0006\n\u0004\b\f\u0010\rR&\u0010\u000f\u001a\u0014\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u00020\u0006\u0012\u0004\u0012\u00020\u00020\u00050\u000e8\u0002X\u0082\u0004\u00a2\u0006\u0006\n\u0004\b\u000f\u0010\u0010\u00a8\u0006\u0012"}, d2={"Lcom/cobblemon/mod/common/battles/ShowdownThread;", "Ljava/lang/Thread;", "", "launch", "()V", "Lkotlin/Function1;", "Lcom/cobblemon/mod/common/battles/runner/ShowdownService;", "action", "queue", "(Lkotlin/jvm/functions/Function1;)V", "run", "Ljava/util/concurrent/CountDownLatch;", "latch", "Ljava/util/concurrent/CountDownLatch;", "Ljava/util/Queue;", "whenReady", "Ljava/util/Queue;", "<init>", "common"})
public final class ShowdownThread
extends Thread {
    @NotNull
    private final CountDownLatch latch = new CountDownLatch(1);
    @NotNull
    private final Queue<Function1<ShowdownService, Unit>> whenReady = new LinkedList();

    public ShowdownThread() {
        super("Cobblemon Showdown");
    }

    public final void launch() {
        this.start();
        this.latch.await();
        for (Function1 function1 : this.whenReady) {
            function1.invoke((Object)ShowdownService.Companion.getService());
        }
    }

    public final void queue(@NotNull Function1<? super ShowdownService, Unit> action2) {
        Intrinsics.checkNotNullParameter(action2, (String)"action");
        if (this.latch.getCount() == 0L) {
            action2.invoke((Object)ShowdownService.Companion.getService());
        } else {
            this.whenReady.add(action2);
        }
    }

    @Override
    public void run() {
        Cobblemon.INSTANCE.getLOGGER().info("Starting showdown service...");
        ShowdownService.Companion.getService().openConnection();
        Cobblemon.INSTANCE.getLOGGER().info("Showdown has been started!");
        this.latch.countDown();
    }
}

