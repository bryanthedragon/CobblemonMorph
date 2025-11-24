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
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.reactive;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.Priority;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.reactive.NoTransformThrowable;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.reactive.Observable;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.reactive.ObservableSubscription;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.reactive.SimpleObservable;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.reactive.Transform;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000:\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0018\u0002\n\u0002\b\u0005\u0018\u0000*\u0004\b\u0000\u0010\u0001*\u0004\b\u0001\u0010\u00022\b\u0012\u0004\u0012\u00028\u00010\u0003B)\u0012\f\u0010\u0012\u001a\b\u0012\u0004\u0012\u00028\u00000\u0011\u0012\u0012\u0010\u001b\u001a\u000e\u0012\u0004\u0012\u00028\u0000\u0012\u0004\u0012\u00028\u00010\u001a\u00a2\u0006\u0004\b\u001d\u0010\u001eJ\u0015\u0010\u0006\u001a\u00020\u00052\u0006\u0010\u0004\u001a\u00028\u0000\u00a2\u0006\u0004\b\u0006\u0010\u0007J1\u0010\r\u001a\b\u0012\u0004\u0012\u00028\u00010\f2\u0006\u0010\t\u001a\u00020\b2\u0012\u0010\u000b\u001a\u000e\u0012\u0004\u0012\u00028\u0001\u0012\u0004\u0012\u00020\u00050\nH\u0016\u00a2\u0006\u0004\b\r\u0010\u000eJ\r\u0010\u000f\u001a\u00020\u0005\u00a2\u0006\u0004\b\u000f\u0010\u0010R\u001a\u0010\u0012\u001a\b\u0012\u0004\u0012\u00028\u00000\u00118\u0002X\u0082\u0004\u00a2\u0006\u0006\n\u0004\b\u0012\u0010\u0013R*\u0010\u0014\u001a\n\u0012\u0004\u0012\u00028\u0000\u0018\u00010\f8\u0006@\u0006X\u0086\u000e\u00a2\u0006\u0012\n\u0004\b\u0014\u0010\u0015\u001a\u0004\b\u0016\u0010\u0017\"\u0004\b\u0018\u0010\u0019R \u0010\u001b\u001a\u000e\u0012\u0004\u0012\u00028\u0000\u0012\u0004\u0012\u00028\u00010\u001a8\u0002X\u0082\u0004\u00a2\u0006\u0006\n\u0004\b\u001b\u0010\u001c\u00a8\u0006\u001f"}, d2={"Lcom/cobblemon/mod/common/api/reactive/TransformObservable;", "I", "O", "Lcom/cobblemon/mod/common/api/reactive/SimpleObservable;", "input", "", "parentHandler", "(Ljava/lang/Object;)V", "Lcom/cobblemon/mod/common/api/Priority;", "priority", "Lkotlin/Function1;", "handler", "Lcom/cobblemon/mod/common/api/reactive/ObservableSubscription;", "subscribe", "(Lcom/cobblemon/mod/common/api/Priority;Lkotlin/jvm/functions/Function1;)Lcom/cobblemon/mod/common/api/reactive/ObservableSubscription;", "terminate", "()V", "Lcom/cobblemon/mod/common/api/reactive/Observable;", "observable", "Lcom/cobblemon/mod/common/api/reactive/Observable;", "rootSubscription", "Lcom/cobblemon/mod/common/api/reactive/ObservableSubscription;", "getRootSubscription", "()Lcom/cobblemon/mod/common/api/reactive/ObservableSubscription;", "setRootSubscription", "(Lcom/cobblemon/mod/common/api/reactive/ObservableSubscription;)V", "Lcom/cobblemon/mod/common/api/reactive/Transform;", "transform", "Lcom/cobblemon/mod/common/api/reactive/Transform;", "<init>", "(Lcom/cobblemon/mod/common/api/reactive/Observable;Lcom/cobblemon/mod/common/api/reactive/Transform;)V", "common"})
@SourceDebugExtension(value={"SMAP\nTransformObservable.kt\nKotlin\n*S Kotlin\n*F\n+ 1 TransformObservable.kt\ncom/cobblemon/mod/common/api/reactive/TransformObservable\n+ 2 fake.kt\nkotlin/jvm/internal/FakeKt\n*L\n1#1,52:1\n1#2:53\n*E\n"})
public final class TransformObservable<I, O>
extends SimpleObservable<O> {
    @NotNull
    private final Observable<I> observable;
    @NotNull
    private final Transform<I, O> transform;
    @Nullable
    private ObservableSubscription<I> rootSubscription;

    public TransformObservable(@NotNull Observable<I> observable2, @NotNull Transform<I, O> transform) {
        Intrinsics.checkNotNullParameter(observable2, (String)"observable");
        Intrinsics.checkNotNullParameter(transform, (String)"transform");
        this.observable = observable2;
        this.transform = transform;
    }

    @Nullable
    public final ObservableSubscription<I> getRootSubscription() {
        return this.rootSubscription;
    }

    public final void setRootSubscription(@Nullable ObservableSubscription<I> observableSubscription) {
        this.rootSubscription = observableSubscription;
    }

    @Override
    @NotNull
    public ObservableSubscription<O> subscribe(@NotNull Priority priority, @NotNull Function1<? super O, Unit> handler) {
        Intrinsics.checkNotNullParameter((Object)((Object)priority), (String)"priority");
        Intrinsics.checkNotNullParameter(handler, (String)"handler");
        if (this.rootSubscription == null) {
            this.rootSubscription = this.observable.subscribe(priority, (Function1)new Function1<I, Unit>(this){
                final /* synthetic */ TransformObservable<I, O> this$0;
                {
                    this.this$0 = $receiver;
                    super(1);
                }

                public final void invoke(I it) {
                    this.this$0.parentHandler(it);
                }
            });
        }
        return super.subscribe(priority, handler);
    }

    public final void terminate() {
        block0: {
            ObservableSubscription<I> observableSubscription = this.rootSubscription;
            if (observableSubscription == null) break block0;
            ObservableSubscription<I> it = observableSubscription;
            boolean bl = false;
            this.observable.unsubscribe(it);
        }
    }

    public final void parentHandler(I input) {
        block2: {
            try {
                Object[] objectArray = new Object[]{this.transform.invoke(input)};
                this.emit(objectArray);
            }
            catch (NoTransformThrowable throwable) {
                if (!throwable.getTerminate()) break block2;
                this.terminate();
            }
        }
    }
}

