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

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.events.Cancelable;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.reactive.CancelableObservable;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.reactive.EventObservable;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.reactive.ObservableSubscription;
import java.util.Arrays;
import java.util.List;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000$\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u0011\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0007\b\u0016\u0018\u0000*\b\b\u0000\u0010\u0002*\u00020\u00012\b\u0012\u0004\u0012\u00028\u00000\u0003B\u0007\u00a2\u0006\u0004\b\u000f\u0010\u0010J#\u0010\u0007\u001a\u00020\u00062\u0012\u0010\u0005\u001a\n\u0012\u0006\b\u0001\u0012\u00028\u00000\u0004\"\u00028\u0000H\u0016\u00a2\u0006\u0004\b\u0007\u0010\bJE\u0010\r\u001a\u00020\u00062\u0006\u0010\t\u001a\u00028\u00002\u0014\b\u0002\u0010\u000b\u001a\u000e\u0012\u0004\u0012\u00028\u0000\u0012\u0004\u0012\u00020\u00060\n2\u0012\u0010\f\u001a\u000e\u0012\u0004\u0012\u00028\u0000\u0012\u0004\u0012\u00020\u00060\nH\u0086\b\u00f8\u0001\u0000\u00a2\u0006\u0004\b\r\u0010\u000e\u0082\u0002\u0007\n\u0005\b\u009920\u0001\u00a8\u0006\u0011"}, d2={"Lcom/cobblemon/mod/common/api/reactive/CancelableObservable;", "Lcom/cobblemon/mod/common/api/events/Cancelable;", "T", "Lcom/cobblemon/mod/common/api/reactive/EventObservable;", "", "values", "", "emit", "([Lcom/cobblemon/mod/common/api/events/Cancelable;)V", "event", "Lkotlin/Function1;", "ifCanceled", "ifSucceeded", "postThen", "(Lcom/cobblemon/mod/common/api/events/Cancelable;Lkotlin/jvm/functions/Function1;Lkotlin/jvm/functions/Function1;)V", "<init>", "()V", "common"})
@SourceDebugExtension(value={"SMAP\nEventObservables.kt\nKotlin\n*S Kotlin\n*F\n+ 1 EventObservables.kt\ncom/cobblemon/mod/common/api/reactive/CancelableObservable\n+ 2 _Arrays.kt\nkotlin/collections/ArraysKt___ArraysKt\n+ 3 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n+ 4 EventObservables.kt\ncom/cobblemon/mod/common/api/reactive/EventObservable\n*L\n1#1,48:1\n13579#2:49\n13580#2:52\n13579#2,2:55\n288#3,2:50\n17#4,2:53\n19#4:57\n*S KotlinDebug\n*F\n+ 1 EventObservables.kt\ncom/cobblemon/mod/common/api/reactive/CancelableObservable\n*L\n28#1:49\n28#1:52\n40#1:55,2\n32#1:50,2\n40#1:53,2\n40#1:57\n*E\n"})
public class CancelableObservable<T extends Cancelable>
extends EventObservable<T> {
    @Override
    public void emit(T ... values) {
        Intrinsics.checkNotNullParameter(values, (String)"values");
        if (this.getSubscriptions().isEmpty()) {
            return;
        }
        T[] $this$forEach$iv = values;
        boolean $i$f$forEach = false;
        int n = $this$forEach$iv.length;
        block0: for (int i = 0; i < n; ++i) {
            T element$iv;
            T value2 = element$iv = $this$forEach$iv[i];
            boolean bl = false;
            List subscriptionsSnapshot = CollectionsKt.toList((Iterable)this.getSubscriptions());
            Iterable $this$firstOrNull$iv = subscriptionsSnapshot;
            boolean $i$f$firstOrNull = false;
            for (Object element$iv2 : $this$firstOrNull$iv) {
                ObservableSubscription subscription2 = (ObservableSubscription)element$iv2;
                boolean bl2 = false;
                subscription2.handle(value2);
                if (!((Cancelable)value2).isCanceled()) continue;
                continue block0;
            }
        }
    }

    /*
     * WARNING - void declaration
     */
    public final void postThen(@NotNull T event, @NotNull Function1<? super T, Unit> ifCanceled, @NotNull Function1<? super T, Unit> ifSucceeded) {
        void this_$iv;
        Intrinsics.checkNotNullParameter(event, (String)"event");
        Intrinsics.checkNotNullParameter(ifCanceled, (String)"ifCanceled");
        Intrinsics.checkNotNullParameter(ifSucceeded, (String)"ifSucceeded");
        boolean $i$f$postThen = false;
        EventObservable eventObservable = this;
        Cancelable[] cancelableArray = new Cancelable[]{event};
        Cancelable[] events$iv = cancelableArray;
        boolean $i$f$post = false;
        this_$iv.emit(Arrays.copyOf(events$iv, events$iv.length));
        Cancelable[] $this$forEach$iv$iv = events$iv;
        boolean $i$f$forEach = false;
        int n = $this$forEach$iv$iv.length;
        for (int i = 0; i < n; ++i) {
            Cancelable element$iv$iv;
            Cancelable it = element$iv$iv = $this$forEach$iv$iv[i];
            boolean bl = false;
            if (it.isCanceled()) {
                ifCanceled.invoke((Object)it);
                continue;
            }
            ifSucceeded.invoke((Object)it);
        }
    }

    /*
     * WARNING - void declaration
     */
    public static /* synthetic */ void postThen$default(CancelableObservable $this, Cancelable event, Function1 ifCanceled, Function1 ifSucceeded, int n, Object object) {
        void this_$iv;
        if (object != null) {
            throw new UnsupportedOperationException("Super calls with default arguments not supported in this target, function: postThen");
        }
        if ((n & 2) != 0) {
            ifCanceled = postThen.1.INSTANCE;
        }
        Intrinsics.checkNotNullParameter((Object)event, (String)"event");
        Intrinsics.checkNotNullParameter((Object)ifCanceled, (String)"ifCanceled");
        Intrinsics.checkNotNullParameter((Object)ifSucceeded, (String)"ifSucceeded");
        boolean $i$f$postThen = false;
        object = $this;
        Cancelable[] cancelableArray = new Cancelable[]{event};
        Cancelable[] events$iv = cancelableArray;
        boolean $i$f$post = false;
        this_$iv.emit(Arrays.copyOf(events$iv, events$iv.length));
        Cancelable[] $this$forEach$iv$iv = events$iv;
        boolean $i$f$forEach = false;
        int n2 = $this$forEach$iv$iv.length;
        for (int i = 0; i < n2; ++i) {
            Cancelable element$iv$iv;
            Cancelable it = element$iv$iv = $this$forEach$iv$iv[i];
            boolean bl = false;
            if (it.isCanceled()) {
                ifCanceled.invoke((Object)it);
                continue;
            }
            ifSucceeded.invoke((Object)it);
        }
    }
}

