/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  kotlin.Metadata
 *  kotlin.Unit
 *  kotlin.jvm.functions.Function0
 *  kotlin.jvm.functions.Function1
 *  kotlin.jvm.internal.Intrinsics
 *  net.minecraft.server.MinecraftServer
 *  net.minecraft.world.level.Level
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.Cobblemon;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.Environment;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.Priority;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.reactive.Observable;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.reactive.ObservableSubscription;
import java.util.concurrent.CompletableFuture;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(mv={1, 8, 0}, k=2, xi=48, d1={"\u0000B\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\u001a\u0015\u0010\u0003\u001a\u00020\u00022\u0006\u0010\u0001\u001a\u00020\u0000\u00a2\u0006\u0004\b\u0003\u0010\u0004\u001a\u0015\u0010\u0006\u001a\u00020\u00022\u0006\u0010\u0005\u001a\u00020\u0000\u00a2\u0006\u0004\b\u0006\u0010\u0004\u001a\u0015\u0010\u0007\u001a\u00020\u00022\u0006\u0010\u0001\u001a\u00020\u0000\u00a2\u0006\u0004\b\u0007\u0010\u0004\u001a'\u0010\f\u001a\b\u0012\u0004\u0012\u00028\u00000\u000b\"\u0004\b\u0000\u0010\b2\f\u0010\n\u001a\b\u0012\u0004\u0012\u00028\u00000\t\u00a2\u0006\u0004\b\f\u0010\r\u001a\u000f\u0010\u000f\u001a\u0004\u0018\u00010\u000e\u00a2\u0006\u0004\b\u000f\u0010\u0010\u001a\u0011\u0010\u0013\u001a\u00020\u0012*\u00020\u0011\u00a2\u0006\u0004\b\u0013\u0010\u0014\u001a;\u0010\u0019\u001a\b\u0012\u0004\u0012\u00028\u00000\u0018\"\u0004\b\u0000\u0010\b*\b\u0012\u0004\u0012\u00028\u00000\u00152\b\b\u0002\u0010\u0017\u001a\u00020\u00162\f\u0010\n\u001a\b\u0012\u0004\u0012\u00020\u00020\t\u00a2\u0006\u0004\b\u0019\u0010\u001a\u00a8\u0006\u001b"}, d2={"Ljava/lang/Runnable;", "runnable", "", "ifClient", "(Ljava/lang/Runnable;)V", "action", "ifDedicatedServer", "ifServer", "T", "Lkotlin/Function0;", "block", "Ljava/util/concurrent/CompletableFuture;", "runOnServer", "(Lkotlin/jvm/functions/Function0;)Ljava/util/concurrent/CompletableFuture;", "Lnet/minecraft/server/MinecraftServer;", "server", "()Lnet/minecraft/server/MinecraftServer;", "Lnet/minecraft/world/level/Level;", "", "isServerSide", "(Lnet/minecraft/world/level/Level;)Z", "Lcom/cobblemon/mod/common/api/reactive/Observable;", "Lcom/cobblemon/mod/common/api/Priority;", "priority", "Lcom/cobblemon/mod/common/api/reactive/ObservableSubscription;", "subscribeOnServer", "(Lcom/cobblemon/mod/common/api/reactive/Observable;Lcom/cobblemon/mod/common/api/Priority;Lkotlin/jvm/functions/Function0;)Lcom/cobblemon/mod/common/api/reactive/ObservableSubscription;", "common"})
public final class DistributionUtilsKt {
    public static final void ifClient(@NotNull Runnable runnable) {
        Intrinsics.checkNotNullParameter((Object)runnable, (String)"runnable");
        if (Cobblemon.INSTANCE.getImplementation().environment() == Environment.CLIENT) {
            runnable.run();
        }
    }

    public static final void ifServer(@NotNull Runnable runnable) {
        Intrinsics.checkNotNullParameter((Object)runnable, (String)"runnable");
        if (Cobblemon.INSTANCE.getImplementation().environment() == Environment.SERVER) {
            runnable.run();
        }
    }

    public static final void ifDedicatedServer(@NotNull Runnable action2) {
        Intrinsics.checkNotNullParameter((Object)action2, (String)"action");
        if (Cobblemon.INSTANCE.getImplementation().environment() == Environment.SERVER) {
            action2.run();
        }
    }

    @NotNull
    public static final <T> CompletableFuture<T> runOnServer(@NotNull Function0<? extends T> block) {
        Intrinsics.checkNotNullParameter(block, (String)"block");
        CompletableFuture future2 = new CompletableFuture();
        MinecraftServer server = DistributionUtilsKt.server();
        if (server == null) {
            future2.completeExceptionally(new IllegalStateException("There is no server to schedule it on."));
        } else {
            server.execute(() -> DistributionUtilsKt.runOnServer$lambda$0(future2, block));
        }
        return future2;
    }

    @NotNull
    public static final <T> ObservableSubscription<T> subscribeOnServer(@NotNull Observable<T> $this$subscribeOnServer, @NotNull Priority priority, @NotNull Function0<Unit> block) {
        Intrinsics.checkNotNullParameter($this$subscribeOnServer, (String)"<this>");
        Intrinsics.checkNotNullParameter((Object)((Object)priority), (String)"priority");
        Intrinsics.checkNotNullParameter(block, (String)"block");
        return $this$subscribeOnServer.subscribe(priority, (Function1)new Function1<T, Unit>(block){
            final /* synthetic */ Function0<Unit> $block;
            {
                this.$block = $block;
                super(1);
            }

            public final void invoke(T it) {
                DistributionUtilsKt.runOnServer(this.$block);
            }
        });
    }

    public static /* synthetic */ ObservableSubscription subscribeOnServer$default(Observable observable2, Priority priority, Function0 function0, int n, Object object) {
        if ((n & 1) != 0) {
            priority = Priority.NORMAL;
        }
        return DistributionUtilsKt.subscribeOnServer(observable2, priority, (Function0<Unit>)function0);
    }

    @Nullable
    public static final MinecraftServer server() {
        return Cobblemon.INSTANCE.getImplementation().server();
    }

    public static final boolean isServerSide(@NotNull Level $this$isServerSide) {
        Intrinsics.checkNotNullParameter((Object)$this$isServerSide, (String)"<this>");
        return !$this$isServerSide.f_46443_;
    }

    private static final void runOnServer$lambda$0(CompletableFuture $future, Function0 $block) {
        Intrinsics.checkNotNullParameter((Object)$future, (String)"$future");
        Intrinsics.checkNotNullParameter((Object)$block, (String)"$block");
        $future.complete($block.invoke());
    }
}

