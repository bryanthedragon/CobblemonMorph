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
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.reactive;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.reactive.Observable;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000,\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0004\n\u0002\u0010\u000b\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\u0018\u0000*\u0004\b\u0000\u0010\u00012\u00020\u0002B)\u0012\f\u0010\u0014\u001a\b\u0012\u0004\u0012\u00028\u00000\u0013\u0012\u0012\u0010\u0011\u001a\u000e\u0012\u0004\u0012\u00028\u0000\u0012\u0004\u0012\u00020\u00040\u0010\u00a2\u0006\u0004\b\u0016\u0010\u0017J\u0015\u0010\u0005\u001a\u00020\u00042\u0006\u0010\u0003\u001a\u00028\u0000\u00a2\u0006\u0004\b\u0005\u0010\u0006J\r\u0010\u0007\u001a\u00020\u0004\u00a2\u0006\u0004\b\u0007\u0010\bR\"\u0010\n\u001a\u00020\t8\u0006@\u0006X\u0086\u000e\u00a2\u0006\u0012\n\u0004\b\n\u0010\u000b\u001a\u0004\b\f\u0010\r\"\u0004\b\u000e\u0010\u000fR \u0010\u0011\u001a\u000e\u0012\u0004\u0012\u00028\u0000\u0012\u0004\u0012\u00020\u00040\u00108\u0002X\u0082\u0004\u00a2\u0006\u0006\n\u0004\b\u0011\u0010\u0012R\u001a\u0010\u0014\u001a\b\u0012\u0004\u0012\u00028\u00000\u00138\u0002X\u0082\u0004\u00a2\u0006\u0006\n\u0004\b\u0014\u0010\u0015\u00a8\u0006\u0018"}, d2={"Lcom/cobblemon/mod/common/api/reactive/ObservableSubscription;", "T", "", "value", "", "handle", "(Ljava/lang/Object;)V", "unsubscribe", "()V", "", "alive", "Z", "getAlive", "()Z", "setAlive", "(Z)V", "Lkotlin/Function1;", "handler", "Lkotlin/jvm/functions/Function1;", "Lcom/cobblemon/mod/common/api/reactive/Observable;", "observable", "Lcom/cobblemon/mod/common/api/reactive/Observable;", "<init>", "(Lcom/cobblemon/mod/common/api/reactive/Observable;Lkotlin/jvm/functions/Function1;)V", "common"})
public final class ObservableSubscription<T> {
    @NotNull
    private final Observable<T> observable;
    @NotNull
    private final Function1<T, Unit> handler;
    private boolean alive;

    public ObservableSubscription(@NotNull Observable<T> observable2, @NotNull Function1<? super T, Unit> handler) {
        Intrinsics.checkNotNullParameter(observable2, (String)"observable");
        Intrinsics.checkNotNullParameter(handler, (String)"handler");
        this.observable = observable2;
        this.handler = handler;
        this.alive = true;
    }

    public final boolean getAlive() {
        return this.alive;
    }

    public final void setAlive(boolean bl) {
        this.alive = bl;
    }

    public final void handle(T value2) {
        this.handler.invoke(value2);
    }

    public final void unsubscribe() {
        this.observable.unsubscribe(this);
        this.alive = false;
    }
}

