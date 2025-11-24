/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  kotlin.KotlinNothingValueException
 *  kotlin.Metadata
 *  kotlin.jvm.functions.Function1
 *  kotlin.jvm.internal.Intrinsics
 *  org.jetbrains.annotations.NotNull
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.reactive.pipes;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.reactive.Transform;
import kotlin.KotlinNothingValueException;
import kotlin.Metadata;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000\u001a\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0010\u000b\n\u0002\b\u0005\u0018\u0000*\u0004\b\u0000\u0010\u00012\u000e\u0012\u0004\u0012\u00028\u0000\u0012\u0004\u0012\u00028\u00000\u0002B\u001b\u0012\u0012\u0010\b\u001a\u000e\u0012\u0004\u0012\u00028\u0000\u0012\u0004\u0012\u00020\u00070\u0006\u00a2\u0006\u0004\b\n\u0010\u000bJ\u0018\u0010\u0004\u001a\u00028\u00002\u0006\u0010\u0003\u001a\u00028\u0000H\u0096\u0002\u00a2\u0006\u0004\b\u0004\u0010\u0005R \u0010\b\u001a\u000e\u0012\u0004\u0012\u00028\u0000\u0012\u0004\u0012\u00020\u00070\u00068\u0002X\u0082\u0004\u00a2\u0006\u0006\n\u0004\b\b\u0010\t\u00a8\u0006\f"}, d2={"Lcom/cobblemon/mod/common/api/reactive/pipes/EmitWhileTransform;", "I", "Lcom/cobblemon/mod/common/api/reactive/Transform;", "input", "invoke", "(Ljava/lang/Object;)Ljava/lang/Object;", "Lkotlin/Function1;", "", "predicate", "Lkotlin/jvm/functions/Function1;", "<init>", "(Lkotlin/jvm/functions/Function1;)V", "common"})
public final class EmitWhileTransform<I>
implements Transform<I, I> {
    @NotNull
    private final Function1<I, Boolean> predicate;

    public EmitWhileTransform(@NotNull Function1<? super I, Boolean> predicate) {
        Intrinsics.checkNotNullParameter(predicate, (String)"predicate");
        this.predicate = predicate;
    }

    @Override
    public I invoke(I input) {
        if (((Boolean)this.predicate.invoke(input)).booleanValue()) {
            return input;
        }
        this.noTransform(true);
        throw new KotlinNothingValueException();
    }

    @Override
    @NotNull
    public Void noTransform(boolean terminate) {
        return Transform.DefaultImpls.noTransform(this, terminate);
    }
}

