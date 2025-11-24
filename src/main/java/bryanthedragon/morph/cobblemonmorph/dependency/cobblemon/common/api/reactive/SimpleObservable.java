/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  kotlin.Metadata
 *  kotlin.Unit
 *  kotlin.collections.CollectionsKt
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
import java.util.List;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u00004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u0011\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0007\b\u0016\u0018\u0000*\u0004\b\u0000\u0010\u00012\b\u0012\u0004\u0012\u00028\u00000\u0002B\u0007\u00a2\u0006\u0004\b\u0017\u0010\u0018J#\u0010\u0006\u001a\u00020\u00052\u0012\u0010\u0004\u001a\n\u0012\u0006\b\u0001\u0012\u00028\u00000\u0003\"\u00028\u0000H\u0016\u00a2\u0006\u0004\b\u0006\u0010\u0007J1\u0010\r\u001a\b\u0012\u0004\u0012\u00028\u00000\f2\u0006\u0010\t\u001a\u00020\b2\u0012\u0010\u000b\u001a\u000e\u0012\u0004\u0012\u00028\u0000\u0012\u0004\u0012\u00020\u00050\nH\u0016\u00a2\u0006\u0004\b\r\u0010\u000eJ\u001d\u0010\u0010\u001a\u00020\u00052\f\u0010\u000f\u001a\b\u0012\u0004\u0012\u00028\u00000\fH\u0016\u00a2\u0006\u0004\b\u0010\u0010\u0011R&\u0010\u0013\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00028\u00000\f0\u00128\u0004X\u0084\u0004\u00a2\u0006\f\n\u0004\b\u0013\u0010\u0014\u001a\u0004\b\u0015\u0010\u0016\u00a8\u0006\u0019"}, d2={"Lcom/cobblemon/mod/common/api/reactive/SimpleObservable;", "T", "Lcom/cobblemon/mod/common/api/reactive/Observable;", "", "values", "", "emit", "([Ljava/lang/Object;)V", "Lcom/cobblemon/mod/common/api/Priority;", "priority", "Lkotlin/Function1;", "handler", "Lcom/cobblemon/mod/common/api/reactive/ObservableSubscription;", "subscribe", "(Lcom/cobblemon/mod/common/api/Priority;Lkotlin/jvm/functions/Function1;)Lcom/cobblemon/mod/common/api/reactive/ObservableSubscription;", "subscription", "unsubscribe", "(Lcom/cobblemon/mod/common/api/reactive/ObservableSubscription;)V", "Lcom/cobblemon/mod/common/api/PrioritizedList;", "subscriptions", "Lcom/cobblemon/mod/common/api/PrioritizedList;", "getSubscriptions", "()Lcom/cobblemon/mod/common/api/PrioritizedList;", "<init>", "()V", "common"})
@SourceDebugExtension(value={"SMAP\nSimpleObservable.kt\nKotlin\n*S Kotlin\n*F\n+ 1 SimpleObservable.kt\ncom/cobblemon/mod/common/api/reactive/SimpleObservable\n+ 2 _Arrays.kt\nkotlin/collections/ArraysKt___ArraysKt\n+ 3 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n*L\n1#1,42:1\n13579#2:43\n13580#2:46\n1855#3,2:44\n*S KotlinDebug\n*F\n+ 1 SimpleObservable.kt\ncom/cobblemon/mod/common/api/reactive/SimpleObservable\n*L\n36#1:43\n36#1:46\n39#1:44,2\n*E\n"})
public class SimpleObservable<T>
implements Observable<T> {
    @NotNull
    private final PrioritizedList<ObservableSubscription<T>> subscriptions = new PrioritizedList();

    @NotNull
    protected final PrioritizedList<ObservableSubscription<T>> getSubscriptions() {
        return this.subscriptions;
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

    public void emit(T ... values) {
        Intrinsics.checkNotNullParameter(values, (String)"values");
        if (this.subscriptions.isEmpty()) {
            return;
        }
        T[] $this$forEach$iv = values;
        boolean $i$f$forEach = false;
        int n = $this$forEach$iv.length;
        for (int i = 0; i < n; ++i) {
            T element$iv;
            T value2 = element$iv = $this$forEach$iv[i];
            boolean bl = false;
            List subscriptionsSnapshot = CollectionsKt.toList((Iterable)this.subscriptions);
            Iterable $this$forEach$iv2 = subscriptionsSnapshot;
            boolean $i$f$forEach2 = false;
            for (Object element$iv2 : $this$forEach$iv2) {
                ObservableSubscription subscription2 = (ObservableSubscription)element$iv2;
                boolean bl2 = false;
                subscription2.handle(value2);
            }
        }
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

