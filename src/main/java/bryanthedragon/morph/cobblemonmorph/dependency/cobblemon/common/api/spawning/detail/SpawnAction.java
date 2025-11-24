/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  kotlin.Metadata
 *  kotlin.Unit
 *  kotlin.jvm.functions.Function1
 *  kotlin.jvm.internal.Intrinsics
 *  kotlin.jvm.internal.SourceDebugExtension
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.detail;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.context.SpawningContext;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.detail.SpawnDetail;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.influence.SpawningInfluence;
import java.util.concurrent.CompletableFuture;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000&\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0000\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0007\b&\u0018\u0000*\u0004\b\u0000\u0010\u00012\u00020\u0002B\u0017\u0012\u0006\u0010\u0007\u001a\u00020\u0006\u0012\u0006\u0010\f\u001a\u00020\u000b\u00a2\u0006\u0004\b\u0015\u0010\u0016J\u0011\u0010\u0003\u001a\u0004\u0018\u00018\u0000H\u0016\u00a2\u0006\u0004\b\u0003\u0010\u0004J\u0011\u0010\u0005\u001a\u0004\u0018\u00018\u0000H$\u00a2\u0006\u0004\b\u0005\u0010\u0004R\u0017\u0010\u0007\u001a\u00020\u00068\u0006\u00a2\u0006\f\n\u0004\b\u0007\u0010\b\u001a\u0004\b\t\u0010\nR\u001a\u0010\f\u001a\u00020\u000b8\u0016X\u0096\u0004\u00a2\u0006\f\n\u0004\b\f\u0010\r\u001a\u0004\b\u000e\u0010\u000fR\u001d\u0010\u0011\u001a\b\u0012\u0004\u0012\u00028\u00000\u00108\u0006\u00a2\u0006\f\n\u0004\b\u0011\u0010\u0012\u001a\u0004\b\u0013\u0010\u0014\u00a8\u0006\u0017"}, d2={"Lcom/cobblemon/mod/common/api/spawning/detail/SpawnAction;", "R", "", "complete", "()Ljava/lang/Object;", "run", "Lcom/cobblemon/mod/common/api/spawning/context/SpawningContext;", "ctx", "Lcom/cobblemon/mod/common/api/spawning/context/SpawningContext;", "getCtx", "()Lcom/cobblemon/mod/common/api/spawning/context/SpawningContext;", "Lcom/cobblemon/mod/common/api/spawning/detail/SpawnDetail;", "detail", "Lcom/cobblemon/mod/common/api/spawning/detail/SpawnDetail;", "getDetail", "()Lcom/cobblemon/mod/common/api/spawning/detail/SpawnDetail;", "Ljava/util/concurrent/CompletableFuture;", "future", "Ljava/util/concurrent/CompletableFuture;", "getFuture", "()Ljava/util/concurrent/CompletableFuture;", "<init>", "(Lcom/cobblemon/mod/common/api/spawning/context/SpawningContext;Lcom/cobblemon/mod/common/api/spawning/detail/SpawnDetail;)V", "common"})
@SourceDebugExtension(value={"SMAP\nSpawnAction.kt\nKotlin\n*S Kotlin\n*F\n+ 1 SpawnAction.kt\ncom/cobblemon/mod/common/api/spawning/detail/SpawnAction\n+ 2 fake.kt\nkotlin/jvm/internal/FakeKt\n+ 3 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n*L\n1#1,53:1\n1#2:54\n1855#3,2:55\n*S KotlinDebug\n*F\n+ 1 SpawnAction.kt\ncom/cobblemon/mod/common/api/spawning/detail/SpawnAction\n*L\n42#1:55,2\n*E\n"})
public abstract class SpawnAction<R> {
    @NotNull
    private final SpawningContext ctx;
    @NotNull
    private final SpawnDetail detail;
    @NotNull
    private final CompletableFuture<R> future;

    /*
     * WARNING - void declaration
     */
    public SpawnAction(@NotNull SpawningContext ctx, @NotNull SpawnDetail detail) {
        void it;
        CompletableFuture completableFuture;
        Intrinsics.checkNotNullParameter((Object)ctx, (String)"ctx");
        Intrinsics.checkNotNullParameter((Object)detail, (String)"detail");
        this.ctx = ctx;
        this.detail = detail;
        CompletableFuture completableFuture2 = completableFuture = new CompletableFuture();
        SpawnAction spawnAction = this;
        boolean bl = false;
        it.thenApply(arg_0 -> SpawnAction.future$lambda$1$lambda$0((Function1)new Function1<R, Unit>(this){
            final /* synthetic */ SpawnAction<R> this$0;
            {
                this.this$0 = $receiver;
                super(1);
            }

            public final void invoke(R result) {
                this.this$0.getCtx().getSpawner().afterSpawn(this.this$0, result);
            }
        }, arg_0));
        spawnAction.future = completableFuture;
    }

    @NotNull
    public final SpawningContext getCtx() {
        return this.ctx;
    }

    @NotNull
    public SpawnDetail getDetail() {
        return this.detail;
    }

    @NotNull
    public final CompletableFuture<R> getFuture() {
        return this.future;
    }

    @Nullable
    protected abstract R run();

    @Nullable
    public R complete() {
        if (this.future.isDone()) {
            return null;
        }
        Iterable $this$forEach$iv = this.ctx.getInfluences();
        boolean $i$f$forEach = false;
        for (Object element$iv : $this$forEach$iv) {
            SpawningInfluence it = (SpawningInfluence)element$iv;
            boolean bl = false;
            it.affectAction(this);
        }
        R result = this.run();
        if (result != null) {
            this.future.complete(result);
        } else {
            this.future.completeExceptionally(new Exception("Nothing was spawned."));
        }
        return result;
    }

    private static final Unit future$lambda$1$lambda$0(Function1 $tmp0, Object p0) {
        Intrinsics.checkNotNullParameter((Object)$tmp0, (String)"$tmp0");
        return (Unit)$tmp0.invoke(p0);
    }
}

