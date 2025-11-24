/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  kotlin.Metadata
 *  org.jetbrains.annotations.NotNull
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.reactive;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.reactive.NoTransformThrowable;
import kotlin.Metadata;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0000\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0001\n\u0002\b\u0004\bf\u0018\u0000 \f*\u0004\b\u0000\u0010\u0001*\u0004\b\u0001\u0010\u00022\u00020\u0003:\u0001\fJ\u0018\u0010\u0005\u001a\u00028\u00012\u0006\u0010\u0004\u001a\u00028\u0000H\u00a6\u0002\u00a2\u0006\u0004\b\u0005\u0010\u0006J\u0017\u0010\n\u001a\u00020\t2\u0006\u0010\b\u001a\u00020\u0007H\u0016\u00a2\u0006\u0004\b\n\u0010\u000b\u00a8\u0006\r"}, d2={"Lcom/cobblemon/mod/common/api/reactive/Transform;", "I", "O", "", "input", "invoke", "(Ljava/lang/Object;)Ljava/lang/Object;", "", "terminate", "", "noTransform", "(Z)Ljava/lang/Void;", "Companion", "common"})
public interface Transform<I, O> {
    @NotNull
    public static final Companion Companion = bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.reactive.Transform$Companion.$$INSTANCE;

    public O invoke(I var1);

    @NotNull
    public Void noTransform(boolean var1);

    @Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000\u0010\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\b\u0086\u0003\u0018\u00002\u00020\u0001B\t\b\u0002\u00a2\u0006\u0004\b\u0006\u0010\u0007R\u0014\u0010\u0003\u001a\u00020\u00028\u0002X\u0082\u0004\u00a2\u0006\u0006\n\u0004\b\u0003\u0010\u0004R\u0014\u0010\u0005\u001a\u00020\u00028\u0002X\u0082\u0004\u00a2\u0006\u0006\n\u0004\b\u0005\u0010\u0004\u00a8\u0006\b"}, d2={"Lcom/cobblemon/mod/common/api/reactive/Transform$Companion;", "", "Lcom/cobblemon/mod/common/api/reactive/NoTransformThrowable;", "noTransformNoTerminateThrowable", "Lcom/cobblemon/mod/common/api/reactive/NoTransformThrowable;", "noTransformTerminateThrowable", "<init>", "()V", "common"})
    public static final class Companion {
        static final /* synthetic */ Companion $$INSTANCE;
        @NotNull
        private static final NoTransformThrowable noTransformNoTerminateThrowable;
        @NotNull
        private static final NoTransformThrowable noTransformTerminateThrowable;

        private Companion() {
        }

        static {
            $$INSTANCE = new Companion();
            noTransformNoTerminateThrowable = new NoTransformThrowable(false);
            noTransformTerminateThrowable = new NoTransformThrowable(true);
        }
    }

    @Metadata(mv={1, 8, 0}, k=3, xi=48)
    public static final class DefaultImpls {
        @NotNull
        public static <I, O> Void noTransform(@NotNull Transform<I, O> $this, boolean terminate) {
            throw terminate ? (Throwable)bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.reactive.Transform$Companion.noTransformTerminateThrowable : (Throwable)bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.reactive.Transform$Companion.noTransformNoTerminateThrowable;
        }
    }
}

