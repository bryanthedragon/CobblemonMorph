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

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.Priority;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.reactive.ObservableSubscription;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.reactive.SimpleObservable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000<\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u0011\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010!\n\u0002\b\u0005\b\u0016\u0018\u0000*\u0004\b\u0000\u0010\u00012\b\u0012\u0004\u0012\u00028\u00000\u0002B\u0007\u00a2\u0006\u0004\b\u0015\u0010\u0016J#\u0010\u0006\u001a\u00020\u00052\u0012\u0010\u0004\u001a\n\u0012\u0006\b\u0001\u0012\u00028\u00000\u0003\"\u00028\u0000H\u0016\u00a2\u0006\u0004\b\u0006\u0010\u0007J1\u0010\r\u001a\b\u0012\u0004\u0012\u00028\u00000\f2\u0006\u0010\t\u001a\u00020\b2\u0012\u0010\u000b\u001a\u000e\u0012\u0004\u0012\u00028\u0000\u0012\u0004\u0012\u00020\u00050\nH\u0016\u00a2\u0006\u0004\b\r\u0010\u000eR\u0016\u0010\u0010\u001a\u00020\u000f8\u0002@\u0002X\u0082\u000e\u00a2\u0006\u0006\n\u0004\b\u0010\u0010\u0011R\u001c\u0010\u0013\u001a\b\u0012\u0004\u0012\u00028\u00000\u00128\u0002@\u0002X\u0082\u000e\u00a2\u0006\u0006\n\u0004\b\u0013\u0010\u0014\u00a8\u0006\u0017"}, d2={"Lcom/cobblemon/mod/common/api/reactive/SingularObservable;", "T", "Lcom/cobblemon/mod/common/api/reactive/SimpleObservable;", "", "values", "", "emit", "([Ljava/lang/Object;)V", "Lcom/cobblemon/mod/common/api/Priority;", "priority", "Lkotlin/Function1;", "handler", "Lcom/cobblemon/mod/common/api/reactive/ObservableSubscription;", "subscribe", "(Lcom/cobblemon/mod/common/api/Priority;Lkotlin/jvm/functions/Function1;)Lcom/cobblemon/mod/common/api/reactive/ObservableSubscription;", "", "completed", "Z", "", "completedValue", "Ljava/util/List;", "<init>", "()V", "common"})
@SourceDebugExtension(value={"SMAP\nSingularObservable.kt\nKotlin\n*S Kotlin\n*F\n+ 1 SingularObservable.kt\ncom/cobblemon/mod/common/api/reactive/SingularObservable\n+ 2 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n*L\n1#1,46:1\n1855#2,2:47\n*S KotlinDebug\n*F\n+ 1 SingularObservable.kt\ncom/cobblemon/mod/common/api/reactive/SingularObservable\n*L\n30#1:47,2\n*E\n"})
public class SingularObservable<T>
extends SimpleObservable<T> {
    private boolean completed;
    @NotNull
    private List<T> completedValue = new ArrayList();

    @Override
    @NotNull
    public ObservableSubscription<T> subscribe(@NotNull Priority priority, @NotNull Function1<? super T, Unit> handler) {
        Intrinsics.checkNotNullParameter((Object)((Object)priority), (String)"priority");
        Intrinsics.checkNotNullParameter(handler, (String)"handler");
        ObservableSubscription<? super T> subscription2 = new ObservableSubscription<T>(this, handler);
        if (this.completed) {
            Iterable $this$forEach$iv = this.completedValue;
            boolean $i$f$forEach = false;
            Iterator iterator = $this$forEach$iv.iterator();
            while (iterator.hasNext()) {
                Object element$iv;
                Object it = element$iv = iterator.next();
                boolean bl = false;
                handler.invoke(it);
            }
        } else {
            this.getSubscriptions().add(priority, subscription2);
        }
        return subscription2;
    }

    @Override
    public void emit(T ... values) {
        Intrinsics.checkNotNullParameter(values, (String)"values");
        if (this.completed) {
            throw new IllegalStateException("This observable is already completed!");
        }
        this.completed = true;
        CollectionsKt.addAll((Collection)this.completedValue, (Object[])values);
        super.emit(Arrays.copyOf(values, values.length));
        this.getSubscriptions().clear();
    }
}

