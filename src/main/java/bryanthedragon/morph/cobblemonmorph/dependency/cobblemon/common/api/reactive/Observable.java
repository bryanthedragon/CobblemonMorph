/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  kotlin.Metadata
 *  kotlin.Unit
 *  kotlin.jvm.functions.Function1
 *  kotlin.jvm.internal.Intrinsics
 *  kotlin.jvm.internal.Ref$ObjectRef
 *  kotlin.jvm.internal.SourceDebugExtension
 *  org.jetbrains.annotations.NotNull
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.reactive;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.Priority;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.reactive.ObservableSubscription;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.reactive.SingularObservable;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.reactive.Transform;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.reactive.TransformObservable;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.reactive.pipes.EmitWhileTransform;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.reactive.pipes.FilterTransform;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.reactive.pipes.IgnoreFirstTransform;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.reactive.pipes.MapTransform;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.reactive.pipes.StopAfterTransform;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.reactive.pipes.TakeFirstTransform;
import java.util.Arrays;
import java.util.concurrent.CompletableFuture;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Ref;
import kotlin.jvm.internal.SourceDebugExtension;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u00006\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0000\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0011\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0007\bf\u0018\u0000 '*\u0004\b\u0000\u0010\u00012\u00020\u0002:\u0001'J\u000f\u0010\u0003\u001a\u00028\u0000H\u0016\u00a2\u0006\u0004\b\u0003\u0010\u0004JO\u0010\u000b\u001a\u000e\u0012\u0004\u0012\u00028\u0000\u0012\u0004\u0012\u00028\u00020\n\"\u0004\b\u0001\u0010\u0005\"\u0004\b\u0002\u0010\u00062\u0012\u0010\b\u001a\u000e\u0012\u0004\u0012\u00028\u0000\u0012\u0004\u0012\u00028\u00010\u00072\u0012\u0010\t\u001a\u000e\u0012\u0004\u0012\u00028\u0001\u0012\u0004\u0012\u00028\u00020\u0007H\u0016\u00a2\u0006\u0004\b\u000b\u0010\fJi\u0010\u000b\u001a\u000e\u0012\u0004\u0012\u00028\u0000\u0012\u0004\u0012\u00028\u00030\n\"\u0004\b\u0001\u0010\u0005\"\u0004\b\u0002\u0010\u0006\"\u0004\b\u0003\u0010\r2\u0012\u0010\b\u001a\u000e\u0012\u0004\u0012\u00028\u0000\u0012\u0004\u0012\u00028\u00010\u00072\u0012\u0010\t\u001a\u000e\u0012\u0004\u0012\u00028\u0001\u0012\u0004\u0012\u00028\u00020\u00072\u0012\u0010\u000e\u001a\u000e\u0012\u0004\u0012\u00028\u0002\u0012\u0004\u0012\u00028\u00030\u0007H\u0016\u00a2\u0006\u0004\b\u000b\u0010\u000fJ\u0083\u0001\u0010\u000b\u001a\u000e\u0012\u0004\u0012\u00028\u0000\u0012\u0004\u0012\u00028\u00040\n\"\u0004\b\u0001\u0010\u0005\"\u0004\b\u0002\u0010\u0006\"\u0004\b\u0003\u0010\r\"\u0004\b\u0004\u0010\u00102\u0012\u0010\b\u001a\u000e\u0012\u0004\u0012\u00028\u0000\u0012\u0004\u0012\u00028\u00010\u00072\u0012\u0010\t\u001a\u000e\u0012\u0004\u0012\u00028\u0001\u0012\u0004\u0012\u00028\u00020\u00072\u0012\u0010\u000e\u001a\u000e\u0012\u0004\u0012\u00028\u0002\u0012\u0004\u0012\u00028\u00030\u00072\u0012\u0010\u0011\u001a\u000e\u0012\u0004\u0012\u00028\u0003\u0012\u0004\u0012\u00028\u00040\u0007H\u0016\u00a2\u0006\u0004\b\u000b\u0010\u0012J\u009d\u0001\u0010\u000b\u001a\u000e\u0012\u0004\u0012\u00028\u0000\u0012\u0004\u0012\u00028\u00050\n\"\u0004\b\u0001\u0010\u0005\"\u0004\b\u0002\u0010\u0006\"\u0004\b\u0003\u0010\r\"\u0004\b\u0004\u0010\u0010\"\u0004\b\u0005\u0010\u00132\u0012\u0010\b\u001a\u000e\u0012\u0004\u0012\u00028\u0000\u0012\u0004\u0012\u00028\u00010\u00072\u0012\u0010\t\u001a\u000e\u0012\u0004\u0012\u00028\u0001\u0012\u0004\u0012\u00028\u00020\u00072\u0012\u0010\u000e\u001a\u000e\u0012\u0004\u0012\u00028\u0002\u0012\u0004\u0012\u00028\u00030\u00072\u0012\u0010\u0011\u001a\u000e\u0012\u0004\u0012\u00028\u0003\u0012\u0004\u0012\u00028\u00040\u00072\u0012\u0010\u0014\u001a\u000e\u0012\u0004\u0012\u00028\u0004\u0012\u0004\u0012\u00028\u00050\u0007H\u0016\u00a2\u0006\u0004\b\u000b\u0010\u0015J\u00b7\u0001\u0010\u000b\u001a\u000e\u0012\u0004\u0012\u00028\u0000\u0012\u0004\u0012\u00028\u00060\n\"\u0004\b\u0001\u0010\u0005\"\u0004\b\u0002\u0010\u0006\"\u0004\b\u0003\u0010\r\"\u0004\b\u0004\u0010\u0010\"\u0004\b\u0005\u0010\u0013\"\u0004\b\u0006\u0010\u00162\u0012\u0010\b\u001a\u000e\u0012\u0004\u0012\u00028\u0000\u0012\u0004\u0012\u00028\u00010\u00072\u0012\u0010\t\u001a\u000e\u0012\u0004\u0012\u00028\u0001\u0012\u0004\u0012\u00028\u00020\u00072\u0012\u0010\u000e\u001a\u000e\u0012\u0004\u0012\u00028\u0002\u0012\u0004\u0012\u00028\u00030\u00072\u0012\u0010\u0011\u001a\u000e\u0012\u0004\u0012\u00028\u0003\u0012\u0004\u0012\u00028\u00040\u00072\u0012\u0010\u0014\u001a\u000e\u0012\u0004\u0012\u00028\u0004\u0012\u0004\u0012\u00028\u00050\u00072\u0012\u0010\u0017\u001a\u000e\u0012\u0004\u0012\u00028\u0005\u0012\u0004\u0012\u00028\u00060\u0007H\u0016\u00a2\u0006\u0004\b\u000b\u0010\u0018J/\u0010\u000b\u001a\b\u0012\u0004\u0012\u00028\u00010\u0000\"\u0004\b\u0001\u0010\u00192\u0012\u0010\u001a\u001a\u000e\u0012\u0004\u0012\u00028\u0000\u0012\u0004\u0012\u00028\u00010\u0007H\u0016\u00a2\u0006\u0004\b\u000b\u0010\u001bJ3\u0010\"\u001a\b\u0012\u0004\u0012\u00028\u00000!2\b\b\u0002\u0010\u001d\u001a\u00020\u001c2\u0012\u0010 \u001a\u000e\u0012\u0004\u0012\u00028\u0000\u0012\u0004\u0012\u00020\u001f0\u001eH&\u00a2\u0006\u0004\b\"\u0010#J\u001d\u0010%\u001a\u00020\u001f2\f\u0010$\u001a\b\u0012\u0004\u0012\u00028\u00000!H&\u00a2\u0006\u0004\b%\u0010&\u00a8\u0006("}, d2={"Lcom/cobblemon/mod/common/api/reactive/Observable;", "T", "", "await", "()Ljava/lang/Object;", "O1", "O2", "Lcom/cobblemon/mod/common/api/reactive/Transform;", "t1", "t2", "Lcom/cobblemon/mod/common/api/reactive/TransformObservable;", "pipe", "(Lcom/cobblemon/mod/common/api/reactive/Transform;Lcom/cobblemon/mod/common/api/reactive/Transform;)Lcom/cobblemon/mod/common/api/reactive/TransformObservable;", "O3", "t3", "(Lcom/cobblemon/mod/common/api/reactive/Transform;Lcom/cobblemon/mod/common/api/reactive/Transform;Lcom/cobblemon/mod/common/api/reactive/Transform;)Lcom/cobblemon/mod/common/api/reactive/TransformObservable;", "O4", "t4", "(Lcom/cobblemon/mod/common/api/reactive/Transform;Lcom/cobblemon/mod/common/api/reactive/Transform;Lcom/cobblemon/mod/common/api/reactive/Transform;Lcom/cobblemon/mod/common/api/reactive/Transform;)Lcom/cobblemon/mod/common/api/reactive/TransformObservable;", "O5", "t5", "(Lcom/cobblemon/mod/common/api/reactive/Transform;Lcom/cobblemon/mod/common/api/reactive/Transform;Lcom/cobblemon/mod/common/api/reactive/Transform;Lcom/cobblemon/mod/common/api/reactive/Transform;Lcom/cobblemon/mod/common/api/reactive/Transform;)Lcom/cobblemon/mod/common/api/reactive/TransformObservable;", "O6", "t6", "(Lcom/cobblemon/mod/common/api/reactive/Transform;Lcom/cobblemon/mod/common/api/reactive/Transform;Lcom/cobblemon/mod/common/api/reactive/Transform;Lcom/cobblemon/mod/common/api/reactive/Transform;Lcom/cobblemon/mod/common/api/reactive/Transform;Lcom/cobblemon/mod/common/api/reactive/Transform;)Lcom/cobblemon/mod/common/api/reactive/TransformObservable;", "O", "transform", "(Lcom/cobblemon/mod/common/api/reactive/Transform;)Lcom/cobblemon/mod/common/api/reactive/Observable;", "Lcom/cobblemon/mod/common/api/Priority;", "priority", "Lkotlin/Function1;", "", "handler", "Lcom/cobblemon/mod/common/api/reactive/ObservableSubscription;", "subscribe", "(Lcom/cobblemon/mod/common/api/Priority;Lkotlin/jvm/functions/Function1;)Lcom/cobblemon/mod/common/api/reactive/ObservableSubscription;", "subscription", "unsubscribe", "(Lcom/cobblemon/mod/common/api/reactive/ObservableSubscription;)V", "Companion", "common"})
public interface Observable<T> {
    @NotNull
    public static final Companion Companion = bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.reactive.Observable$Companion.$$INSTANCE;

    @NotNull
    public ObservableSubscription<T> subscribe(@NotNull Priority var1, @NotNull Function1<? super T, Unit> var2);

    public void unsubscribe(@NotNull ObservableSubscription<T> var1);

    @NotNull
    public <O> Observable<O> pipe(@NotNull Transform<T, O> var1);

    @NotNull
    public <O1, O2> TransformObservable<T, O2> pipe(@NotNull Transform<T, O1> var1, @NotNull Transform<O1, O2> var2);

    @NotNull
    public <O1, O2, O3> TransformObservable<T, O3> pipe(@NotNull Transform<T, O1> var1, @NotNull Transform<O1, O2> var2, @NotNull Transform<O2, O3> var3);

    @NotNull
    public <O1, O2, O3, O4> TransformObservable<T, O4> pipe(@NotNull Transform<T, O1> var1, @NotNull Transform<O1, O2> var2, @NotNull Transform<O2, O3> var3, @NotNull Transform<O3, O4> var4);

    @NotNull
    public <O1, O2, O3, O4, O5> TransformObservable<T, O5> pipe(@NotNull Transform<T, O1> var1, @NotNull Transform<O1, O2> var2, @NotNull Transform<O2, O3> var3, @NotNull Transform<O3, O4> var4, @NotNull Transform<O4, O5> var5);

    @NotNull
    public <O1, O2, O3, O4, O5, O6> TransformObservable<T, O6> pipe(@NotNull Transform<T, O1> var1, @NotNull Transform<O1, O2> var2, @NotNull Transform<O2, O3> var3, @NotNull Transform<O3, O4> var4, @NotNull Transform<O4, O5> var5, @NotNull Transform<O5, O6> var6);

    public T await();

    @Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000h\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0011\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0005\b\u0086\u0003\u0018\u00002\u00020\u0001B\t\b\u0002\u00a2\u0006\u0004\b)\u0010*J-\u0010\u0007\u001a\b\u0012\u0004\u0012\u00028\u00010\u0006\"\u0004\b\u0001\u0010\u00022\u0012\u0010\u0005\u001a\u000e\u0012\u0004\u0012\u00028\u0001\u0012\u0004\u0012\u00020\u00040\u0003\u00a2\u0006\u0004\b\u0007\u0010\bJ-\u0010\t\u001a\b\u0012\u0004\u0012\u00028\u00010\u0006\"\u0004\b\u0001\u0010\u00022\u0012\u0010\u0005\u001a\u000e\u0012\u0004\u0012\u00028\u0001\u0012\u0004\u0012\u00020\u00040\u0003\u00a2\u0006\u0004\b\t\u0010\bJ-\u0010\u000b\u001a\b\u0012\u0004\u0012\u00028\u00010\n\"\u0004\b\u0001\u0010\u00022\u0012\u0010\u0005\u001a\u000e\u0012\u0004\u0012\u00028\u0001\u0012\u0004\u0012\u00020\u00040\u0003\u00a2\u0006\u0004\b\u000b\u0010\fJ#\u0010\u0010\u001a\b\u0012\u0004\u0012\u00028\u00010\u000f\"\u0004\b\u0001\u0010\u00022\b\b\u0002\u0010\u000e\u001a\u00020\r\u00a2\u0006\u0004\b\u0010\u0010\u0011J-\u0010\u0015\u001a\b\u0012\u0004\u0012\u00028\u00010\u0014\"\u0004\b\u0001\u0010\u00022\u0012\u0010\u0013\u001a\n\u0012\u0006\b\u0001\u0012\u00028\u00010\u0012\"\u00028\u0001\u00a2\u0006\u0004\b\u0015\u0010\u0016J9\u0010\u001a\u001a\u000e\u0012\u0004\u0012\u00028\u0001\u0012\u0004\u0012\u00028\u00020\u0019\"\u0004\b\u0001\u0010\u0002\"\u0004\b\u0002\u0010\u00172\u0012\u0010\u0018\u001a\u000e\u0012\u0004\u0012\u00028\u0001\u0012\u0004\u0012\u00028\u00020\u0003\u00a2\u0006\u0004\b\u001a\u0010\u001bJ'\u0010\u001e\u001a\b\u0012\u0004\u0012\u00028\u00010\u0014\"\u0004\b\u0001\u0010\u00022\f\u0010\u001d\u001a\b\u0012\u0004\u0012\u00028\u00010\u001c\u00a2\u0006\u0004\b\u001e\u0010\u001fJ-\u0010!\u001a\b\u0012\u0004\u0012\u00028\u00010 \"\u0004\b\u0001\u0010\u00022\u0012\u0010\u0005\u001a\u000e\u0012\u0004\u0012\u00028\u0001\u0012\u0004\u0012\u00020\u00040\u0003\u00a2\u0006\u0004\b!\u0010\"J#\u0010$\u001a\b\u0012\u0004\u0012\u00028\u00010#\"\u0004\b\u0001\u0010\u00022\b\b\u0002\u0010\u000e\u001a\u00020\r\u00a2\u0006\u0004\b$\u0010%J3\u0010(\u001a\u000e\u0012\u0004\u0012\u00028\u0001\u0012\u0004\u0012\u00028\u00010\u0019\"\u0004\b\u0001\u0010\u00022\u0012\u0010'\u001a\u000e\u0012\u0004\u0012\u00028\u0001\u0012\u0004\u0012\u00020&0\u0003\u00a2\u0006\u0004\b(\u0010\u001b\u00a8\u0006+"}, d2={"Lcom/cobblemon/mod/common/api/reactive/Observable$Companion;", "", "T", "Lkotlin/Function1;", "", "predicate", "Lcom/cobblemon/mod/common/api/reactive/pipes/EmitWhileTransform;", "emitUntil", "(Lkotlin/jvm/functions/Function1;)Lcom/cobblemon/mod/common/api/reactive/pipes/EmitWhileTransform;", "emitWhile", "Lcom/cobblemon/mod/common/api/reactive/pipes/FilterTransform;", "filter", "(Lkotlin/jvm/functions/Function1;)Lcom/cobblemon/mod/common/api/reactive/pipes/FilterTransform;", "", "amount", "Lcom/cobblemon/mod/common/api/reactive/pipes/IgnoreFirstTransform;", "ignoreFirst", "(I)Lcom/cobblemon/mod/common/api/reactive/pipes/IgnoreFirstTransform;", "", "values", "Lcom/cobblemon/mod/common/api/reactive/Observable;", "just", "([Ljava/lang/Object;)Lcom/cobblemon/mod/common/api/reactive/Observable;", "O", "mapping", "Lcom/cobblemon/mod/common/api/reactive/pipes/MapTransform;", "map", "(Lkotlin/jvm/functions/Function1;)Lcom/cobblemon/mod/common/api/reactive/pipes/MapTransform;", "Ljava/util/concurrent/CompletableFuture;", "future", "of", "(Ljava/util/concurrent/CompletableFuture;)Lcom/cobblemon/mod/common/api/reactive/Observable;", "Lcom/cobblemon/mod/common/api/reactive/pipes/StopAfterTransform;", "stopAfter", "(Lkotlin/jvm/functions/Function1;)Lcom/cobblemon/mod/common/api/reactive/pipes/StopAfterTransform;", "Lcom/cobblemon/mod/common/api/reactive/pipes/TakeFirstTransform;", "takeFirst", "(I)Lcom/cobblemon/mod/common/api/reactive/pipes/TakeFirstTransform;", "", "handler", "tap", "<init>", "()V", "common"})
    @SourceDebugExtension(value={"SMAP\nObservable.kt\nKotlin\n*S Kotlin\n*F\n+ 1 Observable.kt\ncom/cobblemon/mod/common/api/reactive/Observable$Companion\n+ 2 fake.kt\nkotlin/jvm/internal/FakeKt\n*L\n1#1,86:1\n1#2:87\n*E\n"})
    public static final class Companion {
        static final /* synthetic */ Companion $$INSTANCE;

        private Companion() {
        }

        @NotNull
        public final <T> Observable<T> just(T ... values) {
            SingularObservable<T> singularObservable;
            Intrinsics.checkNotNullParameter(values, (String)"values");
            SingularObservable<T> it = singularObservable = new SingularObservable<T>();
            boolean bl = false;
            it.emit(Arrays.copyOf(values, values.length));
            return singularObservable;
        }

        @NotNull
        public final <T> Observable<T> of(@NotNull CompletableFuture<T> future2) {
            Intrinsics.checkNotNullParameter(future2, (String)"future");
            SingularObservable observable2 = new SingularObservable();
            future2.thenAccept(arg_0 -> Companion.of$lambda$1((Function1)new Function1<T, Unit>(observable2){
                final /* synthetic */ SingularObservable<T> $observable;
                {
                    this.$observable = $observable;
                    super(1);
                }

                public final void invoke(T it) {
                    Object[] objectArray = new Object[]{it};
                    this.$observable.emit(objectArray);
                }
            }, arg_0));
            return observable2;
        }

        @NotNull
        public final <T> TakeFirstTransform<T> takeFirst(int amount) {
            return new TakeFirstTransform(amount);
        }

        public static /* synthetic */ TakeFirstTransform takeFirst$default(Companion companion, int n, int n2, Object object) {
            if ((n2 & 1) != 0) {
                n = 1;
            }
            return companion.takeFirst(n);
        }

        @NotNull
        public final <T> IgnoreFirstTransform<T> ignoreFirst(int amount) {
            return new IgnoreFirstTransform(amount);
        }

        public static /* synthetic */ IgnoreFirstTransform ignoreFirst$default(Companion companion, int n, int n2, Object object) {
            if ((n2 & 1) != 0) {
                n = 1;
            }
            return companion.ignoreFirst(n);
        }

        @NotNull
        public final <T> FilterTransform<T> filter(@NotNull Function1<? super T, Boolean> predicate) {
            Intrinsics.checkNotNullParameter(predicate, (String)"predicate");
            return new FilterTransform<T>(predicate);
        }

        @NotNull
        public final <T, O> MapTransform<T, O> map(@NotNull Function1<? super T, ? extends O> mapping) {
            Intrinsics.checkNotNullParameter(mapping, (String)"mapping");
            return new MapTransform<T, O>(mapping);
        }

        @NotNull
        public final <T> EmitWhileTransform<T> emitWhile(@NotNull Function1<? super T, Boolean> predicate) {
            Intrinsics.checkNotNullParameter(predicate, (String)"predicate");
            return new EmitWhileTransform<T>(predicate);
        }

        @NotNull
        public final <T> StopAfterTransform<T> stopAfter(@NotNull Function1<? super T, Boolean> predicate) {
            Intrinsics.checkNotNullParameter(predicate, (String)"predicate");
            return new StopAfterTransform<T>(predicate);
        }

        @NotNull
        public final <T> EmitWhileTransform<T> emitUntil(@NotNull Function1<? super T, Boolean> predicate) {
            Intrinsics.checkNotNullParameter(predicate, (String)"predicate");
            return new EmitWhileTransform((Function1)new Function1<T, Boolean>(predicate){
                final /* synthetic */ Function1<T, Boolean> $predicate;
                {
                    this.$predicate = $predicate;
                    super(1);
                }

                @NotNull
                public final Boolean invoke(T it) {
                    return (Boolean)this.$predicate.invoke(it) == false;
                }
            });
        }

        @NotNull
        public final <T> MapTransform<T, T> tap(@NotNull Function1<? super T, Unit> handler) {
            Intrinsics.checkNotNullParameter(handler, (String)"handler");
            return this.map((Function1)new Function1<T, T>(handler){
                final /* synthetic */ Function1<T, Unit> $handler;
                {
                    this.$handler = $handler;
                    super(1);
                }

                public final T invoke(T it) {
                    this.$handler.invoke(it);
                    return it;
                }
            });
        }

        private static final void of$lambda$1(Function1 $tmp0, Object p0) {
            Intrinsics.checkNotNullParameter((Object)$tmp0, (String)"$tmp0");
            $tmp0.invoke(p0);
        }

        static {
            $$INSTANCE = new Companion();
        }
    }

    @Metadata(mv={1, 8, 0}, k=3, xi=48)
    public static final class DefaultImpls {
        public static /* synthetic */ ObservableSubscription subscribe$default(Observable observable2, Priority priority, Function1 function1, int n, Object object) {
            if (object != null) {
                throw new UnsupportedOperationException("Super calls with default arguments not supported in this target, function: subscribe");
            }
            if ((n & 1) != 0) {
                priority = Priority.NORMAL;
            }
            return observable2.subscribe(priority, function1);
        }

        @NotNull
        public static <T, O> Observable<O> pipe(@NotNull Observable<T> $this, @NotNull Transform<T, O> transform) {
            Intrinsics.checkNotNullParameter(transform, (String)"transform");
            return new TransformObservable<T, O>($this, transform);
        }

        @NotNull
        public static <T, O1, O2> TransformObservable<T, O2> pipe(@NotNull Observable<T> $this, @NotNull Transform<T, O1> t1, @NotNull Transform<O1, O2> t2) {
            Intrinsics.checkNotNullParameter(t1, (String)"t1");
            Intrinsics.checkNotNullParameter(t2, (String)"t2");
            return new TransformObservable($this, Companion.map((Function1)new Function1<T, O2>(t2, t1){
                final /* synthetic */ Transform<O1, O2> $t2;
                final /* synthetic */ Transform<T, O1> $t1;
                {
                    this.$t2 = $t2;
                    this.$t1 = $t1;
                    super(1);
                }

                public final O2 invoke(T it) {
                    return this.$t2.invoke(this.$t1.invoke(it));
                }
            }));
        }

        @NotNull
        public static <T, O1, O2, O3> TransformObservable<T, O3> pipe(@NotNull Observable<T> $this, @NotNull Transform<T, O1> t1, @NotNull Transform<O1, O2> t2, @NotNull Transform<O2, O3> t3) {
            Intrinsics.checkNotNullParameter(t1, (String)"t1");
            Intrinsics.checkNotNullParameter(t2, (String)"t2");
            Intrinsics.checkNotNullParameter(t3, (String)"t3");
            return new TransformObservable($this, Companion.map((Function1)new Function1<T, O3>(t3, t2, t1){
                final /* synthetic */ Transform<O2, O3> $t3;
                final /* synthetic */ Transform<O1, O2> $t2;
                final /* synthetic */ Transform<T, O1> $t1;
                {
                    this.$t3 = $t3;
                    this.$t2 = $t2;
                    this.$t1 = $t1;
                    super(1);
                }

                public final O3 invoke(T it) {
                    return this.$t3.invoke(this.$t2.invoke(this.$t1.invoke(it)));
                }
            }));
        }

        @NotNull
        public static <T, O1, O2, O3, O4> TransformObservable<T, O4> pipe(@NotNull Observable<T> $this, @NotNull Transform<T, O1> t1, @NotNull Transform<O1, O2> t2, @NotNull Transform<O2, O3> t3, @NotNull Transform<O3, O4> t4) {
            Intrinsics.checkNotNullParameter(t1, (String)"t1");
            Intrinsics.checkNotNullParameter(t2, (String)"t2");
            Intrinsics.checkNotNullParameter(t3, (String)"t3");
            Intrinsics.checkNotNullParameter(t4, (String)"t4");
            return new TransformObservable($this, Companion.map((Function1)new Function1<T, O4>(t4, t3, t2, t1){
                final /* synthetic */ Transform<O3, O4> $t4;
                final /* synthetic */ Transform<O2, O3> $t3;
                final /* synthetic */ Transform<O1, O2> $t2;
                final /* synthetic */ Transform<T, O1> $t1;
                {
                    this.$t4 = $t4;
                    this.$t3 = $t3;
                    this.$t2 = $t2;
                    this.$t1 = $t1;
                    super(1);
                }

                public final O4 invoke(T it) {
                    return this.$t4.invoke(this.$t3.invoke(this.$t2.invoke(this.$t1.invoke(it))));
                }
            }));
        }

        @NotNull
        public static <T, O1, O2, O3, O4, O5> TransformObservable<T, O5> pipe(@NotNull Observable<T> $this, @NotNull Transform<T, O1> t1, @NotNull Transform<O1, O2> t2, @NotNull Transform<O2, O3> t3, @NotNull Transform<O3, O4> t4, @NotNull Transform<O4, O5> t5) {
            Intrinsics.checkNotNullParameter(t1, (String)"t1");
            Intrinsics.checkNotNullParameter(t2, (String)"t2");
            Intrinsics.checkNotNullParameter(t3, (String)"t3");
            Intrinsics.checkNotNullParameter(t4, (String)"t4");
            Intrinsics.checkNotNullParameter(t5, (String)"t5");
            return new TransformObservable($this, Companion.map((Function1)new Function1<T, O5>(t5, t4, t3, t2, t1){
                final /* synthetic */ Transform<O4, O5> $t5;
                final /* synthetic */ Transform<O3, O4> $t4;
                final /* synthetic */ Transform<O2, O3> $t3;
                final /* synthetic */ Transform<O1, O2> $t2;
                final /* synthetic */ Transform<T, O1> $t1;
                {
                    this.$t5 = $t5;
                    this.$t4 = $t4;
                    this.$t3 = $t3;
                    this.$t2 = $t2;
                    this.$t1 = $t1;
                    super(1);
                }

                public final O5 invoke(T it) {
                    return this.$t5.invoke(this.$t4.invoke(this.$t3.invoke(this.$t2.invoke(this.$t1.invoke(it)))));
                }
            }));
        }

        @NotNull
        public static <T, O1, O2, O3, O4, O5, O6> TransformObservable<T, O6> pipe(@NotNull Observable<T> $this, @NotNull Transform<T, O1> t1, @NotNull Transform<O1, O2> t2, @NotNull Transform<O2, O3> t3, @NotNull Transform<O3, O4> t4, @NotNull Transform<O4, O5> t5, @NotNull Transform<O5, O6> t6) {
            Intrinsics.checkNotNullParameter(t1, (String)"t1");
            Intrinsics.checkNotNullParameter(t2, (String)"t2");
            Intrinsics.checkNotNullParameter(t3, (String)"t3");
            Intrinsics.checkNotNullParameter(t4, (String)"t4");
            Intrinsics.checkNotNullParameter(t5, (String)"t5");
            Intrinsics.checkNotNullParameter(t6, (String)"t6");
            return new TransformObservable($this, Companion.map((Function1)new Function1<T, O6>(t6, t5, t4, t3, t2, t1){
                final /* synthetic */ Transform<O5, O6> $t6;
                final /* synthetic */ Transform<O4, O5> $t5;
                final /* synthetic */ Transform<O3, O4> $t4;
                final /* synthetic */ Transform<O2, O3> $t3;
                final /* synthetic */ Transform<O1, O2> $t2;
                final /* synthetic */ Transform<T, O1> $t1;
                {
                    this.$t6 = $t6;
                    this.$t5 = $t5;
                    this.$t4 = $t4;
                    this.$t3 = $t3;
                    this.$t2 = $t2;
                    this.$t1 = $t1;
                    super(1);
                }

                public final O6 invoke(T it) {
                    return this.$t6.invoke(this.$t5.invoke(this.$t4.invoke(this.$t3.invoke(this.$t2.invoke(this.$t1.invoke(it))))));
                }
            }));
        }

        public static <T> T await(@NotNull Observable<T> $this) {
            Ref.ObjectRef result = new Ref.ObjectRef();
            DefaultImpls.subscribe$default($this, null, new Function1<T, Unit>(result){
                final /* synthetic */ Ref.ObjectRef<T> $result;
                {
                    this.$result = $result;
                    super(1);
                }

                public final void invoke(T it) {
                    this.$result.element = it;
                }
            }, 1, null);
            while (result.element == null) {
                Thread.sleep(1L);
            }
            Object object = result.element;
            Intrinsics.checkNotNull((Object)object);
            return (T)object;
        }
    }
}

