/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  kotlin.KotlinNothingValueException
 *  kotlin.Metadata
 *  kotlin.jvm.internal.DefaultConstructorMarker
 *  org.jetbrains.annotations.NotNull
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.reactive.pipes;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.reactive.Transform;
import kotlin.KotlinNothingValueException;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000\u0016\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\b\n\u0002\b\b\u0018\u0000*\u0004\b\u0000\u0010\u00012\u000e\u0012\u0004\u0012\u00028\u0000\u0012\u0004\u0012\u00028\u00000\u0002B\u0011\u0012\b\b\u0002\u0010\u0007\u001a\u00020\u0006\u00a2\u0006\u0004\b\r\u0010\fJ\u0018\u0010\u0004\u001a\u00028\u00002\u0006\u0010\u0003\u001a\u00028\u0000H\u0096\u0002\u00a2\u0006\u0004\b\u0004\u0010\u0005R\"\u0010\u0007\u001a\u00020\u00068\u0006@\u0006X\u0086\u000e\u00a2\u0006\u0012\n\u0004\b\u0007\u0010\b\u001a\u0004\b\t\u0010\n\"\u0004\b\u000b\u0010\f\u00a8\u0006\u000e"}, d2={"Lcom/cobblemon/mod/common/api/reactive/pipes/IgnoreFirstTransform;", "T", "Lcom/cobblemon/mod/common/api/reactive/Transform;", "input", "invoke", "(Ljava/lang/Object;)Ljava/lang/Object;", "", "amount", "I", "getAmount", "()I", "setAmount", "(I)V", "<init>", "common"})
public final class IgnoreFirstTransform<T>
implements Transform<T, T> {
    private int amount;

    public IgnoreFirstTransform(int amount) {
        this.amount = amount;
    }

    public /* synthetic */ IgnoreFirstTransform(int n, int n2, DefaultConstructorMarker defaultConstructorMarker) {
        if ((n2 & 1) != 0) {
            n = 1;
        }
        this(n);
    }

    public final int getAmount() {
        return this.amount;
    }

    public final void setAmount(int n) {
        this.amount = n;
    }

    @Override
    public T invoke(T input) {
        if (this.amount > 0) {
            int n = this.amount;
            this.amount = n + -1;
            this.noTransform(false);
            throw new KotlinNothingValueException();
        }
        return input;
    }

    @Override
    @NotNull
    public Void noTransform(boolean terminate) {
        return Transform.DefaultImpls.noTransform(this, terminate);
    }

    public IgnoreFirstTransform() {
        this(0, 1, null);
    }
}

