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
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.reactive;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.PrioritizedList;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.Priority;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.reactive.Observable;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.reactive.ObservableSubscription;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.reactive.Transform;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.reactive.TransformObservable;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u00000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\b\b\u0016\u0018\u0000*\u0004\b\u0000\u0010\u00012\b\u0012\u0004\u0012\u00028\u00000\u0002B\u000f\u0012\u0006\u0010\u001a\u001a\u00028\u0000\u00a2\u0006\u0004\b\u001c\u0010\u0006J\u0017\u0010\u0005\u001a\u00020\u00042\u0006\u0010\u0003\u001a\u00028\u0000H\u0016\u00a2\u0006\u0004\b\u0005\u0010\u0006J\u000f\u0010\u0007\u001a\u00028\u0000H\u0016\u00a2\u0006\u0004\b\u0007\u0010\bJ\u0017\u0010\t\u001a\u00020\u00042\u0006\u0010\u0003\u001a\u00028\u0000H\u0016\u00a2\u0006\u0004\b\t\u0010\u0006J1\u0010\u000f\u001a\b\u0012\u0004\u0012\u00028\u00000\u000e2\u0006\u0010\u000b\u001a\u00020\n2\u0012\u0010\r\u001a\u000e\u0012\u0004\u0012\u00028\u0000\u0012\u0004\u0012\u00020\u00040\fH\u0016\u00a2\u0006\u0004\b\u000f\u0010\u0010J1\u0010\u0011\u001a\b\u0012\u0004\u0012\u00028\u00000\u000e2\b\b\u0002\u0010\u000b\u001a\u00020\n2\u0012\u0010\r\u001a\u000e\u0012\u0004\u0012\u00028\u0000\u0012\u0004\u0012\u00020\u00040\f\u00a2\u0006\u0004\b\u0011\u0010\u0010J\u001d\u0010\u0013\u001a\u00020\u00042\f\u0010\u0012\u001a\b\u0012\u0004\u0012\u00028\u00000\u000eH\u0016\u00a2\u0006\u0004\b\u0013\u0010\u0014R#\u0010\u0016\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00028\u00000\u000e0\u00158\u0006\u00a2\u0006\f\n\u0004\b\u0016\u0010\u0017\u001a\u0004\b\u0018\u0010\u0019R\u0016\u0010\u001a\u001a\u00028\u00008\u0002@\u0002X\u0082\u000e\u00a2\u0006\u0006\n\u0004\b\u001a\u0010\u001b\u00a8\u0006\u001d"}, d2={"Lcom/cobblemon/mod/common/api/reactive/SettableObservable;", "T", "Lcom/cobblemon/mod/common/api/reactive/Observable;", "newValue", "", "emit", "(Ljava/lang/Object;)V", "get", "()Ljava/lang/Object;", "set", "Lcom/cobblemon/mod/common/api/Priority;", "priority", "Lkotlin/Function1;", "handler", "Lcom/cobblemon/mod/common/api/reactive/ObservableSubscription;", "subscribe", "(Lcom/cobblemon/mod/common/api/Priority;Lkotlin/jvm/functions/Function1;)Lcom/cobblemon/mod/common/api/reactive/ObservableSubscription;", "subscribeIncludingCurrent", "subscription", "unsubscribe", "(Lcom/cobblemon/mod/common/api/reactive/ObservableSubscription;)V", "Lcom/cobblemon/mod/common/api/PrioritizedList;", "subscriptions", "Lcom/cobblemon/mod/common/api/PrioritizedList;", "getSubscriptions", "()Lcom/cobblemon/mod/common/api/PrioritizedList;", "value", "Ljava/lang/Object;", "<init>", "common"})
@SourceDebugExtension(value={"SMAP\nSettableObservable.kt\nKotlin\n*S Kotlin\n*F\n+ 1 SettableObservable.kt\ncom/cobblemon/mod/common/api/reactive/SettableObservable\n+ 2 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n*L\n1#1,63:1\n1855#2,2:64\n*S KotlinDebug\n*F\n+ 1 SettableObservable.kt\ncom/cobblemon/mod/common/api/reactive/SettableObservable\n*L\n55#1:64,2\n*E\n"})
public class SettableObservable<T>
implements Observable<T> {
    private T value;
    @NotNull
    private final PrioritizedList<ObservableSubscription<T>> subscriptions;

    public SettableObservable(T value2) {
        this.value = value2;
        this.subscriptions = new PrioritizedList();
    }

    @NotNull
    public final PrioritizedList<ObservableSubscription<T>> getSubscriptions() {
        return this.subscriptions;
    }

    @NotNull
    public final ObservableSubscription<T> subscribeIncludingCurrent(@NotNull Priority priority, @NotNull Function1<? super T, Unit> handler) {
        Intrinsics.checkNotNullParameter((Object)((Object)priority), (String)"priority");
        Intrinsics.checkNotNullParameter(handler, (String)"handler");
        ObservableSubscription<T> subscription2 = this.subscribe(priority, handler);
        subscription2.handle(this.value);
        return subscription2;
    }

    public static /* synthetic */ ObservableSubscription subscribeIncludingCurrent$default(SettableObservable settableObservable, Priority priority, Function1 function1, int n, Object object) {
        if (object != null) {
            throw new UnsupportedOperationException("Super calls with default arguments not supported in this target, function: subscribeIncludingCurrent");
        }
        if ((n & 1) != 0) {
            priority = Priority.NORMAL;
        }
        return settableObservable.subscribeIncludingCurrent(priority, function1);
    }

    @Override
    @NotNull
    public ObservableSubscription<T> subscribe(@NotNull Priority priority, @NotNull Function1<? super T, Unit> handler) {
        Intrinsics.checkNotNullParameter((Object)((Object)priority), (String)"priority");
        Intrinsics.checkNotNullParameter(handler, (String)"handler");
        ObservableSubscription<? super T> subscription2 = new ObservableSubscription<T>(this, handler);
        this.subscriptions.add(priority, subscription2);
        return subscription2;
    }

    @Override
    public void unsubscribe(@NotNull ObservableSubscription<T> subscription2) {
        Intrinsics.checkNotNullParameter(subscription2, (String)"subscription");
        this.subscriptions.remove(subscription2);
    }

    public void set(T newValue) {
        T t = this.value;
        boolean bl = t != null ? t.equals(newValue) : false;
        if (bl || this.value == null && newValue == null) {
            return;
        }
        this.emit(newValue);
    }

    public void emit(T newValue) {
        this.value = newValue;
        Iterable $this$forEach$iv = this.subscriptions;
        boolean $i$f$forEach = false;
        for (Object element$iv : $this$forEach$iv) {
            ObservableSubscription it = (ObservableSubscription)element$iv;
            boolean bl = false;
            it.handle(newValue);
        }
    }

    public T get() {
        return this.value;
    }

    @Override
    @NotNull
    public <O> Observable<O> pipe(@NotNull Transform<T, O> transform) {
        return Observable.DefaultImpls.pipe(this, transform);
    }

    @Override
    @NotNull
    public <O1, O2> TransformObservable<T, O2> pipe(@NotNull Transform<T, O1> t1, @NotNull Transform<O1, O2> t2) {
        return Observable.DefaultImpls.pipe(this, t1, t2);
    }

    @Override
    @NotNull
    public <O1, O2, O3> TransformObservable<T, O3> pipe(@NotNull Transform<T, O1> t1, @NotNull Transform<O1, O2> t2, @NotNull Transform<O2, O3> t3) {
        return Observable.DefaultImpls.pipe(this, t1, t2, t3);
    }

    @Override
    @NotNull
    public <O1, O2, O3, O4> TransformObservable<T, O4> pipe(@NotNull Transform<T, O1> t1, @NotNull Transform<O1, O2> t2, @NotNull Transform<O2, O3> t3, @NotNull Transform<O3, O4> t4) {
        return Observable.DefaultImpls.pipe(this, t1, t2, t3, t4);
    }

    @Override
    @NotNull
    public <O1, O2, O3, O4, O5> TransformObservable<T, O5> pipe(@NotNull Transform<T, O1> t1, @NotNull Transform<O1, O2> t2, @NotNull Transform<O2, O3> t3, @NotNull Transform<O3, O4> t4, @NotNull Transform<O4, O5> t5) {
        return Observable.DefaultImpls.pipe(this, t1, t2, t3, t4, t5);
    }

    @Override
    @NotNull
    public <O1, O2, O3, O4, O5, O6> TransformObservable<T, O6> pipe(@NotNull Transform<T, O1> t1, @NotNull Transform<O1, O2> t2, @NotNull Transform<O2, O3> t3, @NotNull Transform<O3, O4> t4, @NotNull Transform<O4, O5> t5, @NotNull Transform<O5, O6> t6) {
        return Observable.DefaultImpls.pipe(this, t1, t2, t3, t4, t5, t6);
    }

    @Override
    public T await() {
        return Observable.DefaultImpls.await(this);
    }
}

