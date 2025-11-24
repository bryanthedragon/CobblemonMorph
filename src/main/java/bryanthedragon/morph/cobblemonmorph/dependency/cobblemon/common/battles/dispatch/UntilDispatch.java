/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  kotlin.Metadata
 *  kotlin.jvm.functions.Function0
 *  kotlin.jvm.internal.Intrinsics
 *  org.jetbrains.annotations.NotNull
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.dispatch;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.dispatch.DispatchResult;
import kotlin.Metadata;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0007\u0018\u00002\u00020\u0001B\u0015\u0012\f\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\u00020\u0005\u00a2\u0006\u0004\b\n\u0010\u000bJ\u000f\u0010\u0003\u001a\u00020\u0002H\u0016\u00a2\u0006\u0004\b\u0003\u0010\u0004R\u001d\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\u00020\u00058\u0006\u00a2\u0006\f\n\u0004\b\u0006\u0010\u0007\u001a\u0004\b\b\u0010\t\u00a8\u0006\f"}, d2={"Lcom/cobblemon/mod/common/battles/dispatch/UntilDispatch;", "Lcom/cobblemon/mod/common/battles/dispatch/DispatchResult;", "", "canProceed", "()Z", "Lkotlin/Function0;", "condition", "Lkotlin/jvm/functions/Function0;", "getCondition", "()Lkotlin/jvm/functions/Function0;", "<init>", "(Lkotlin/jvm/functions/Function0;)V", "common"})
public final class UntilDispatch
implements DispatchResult {
    @NotNull
    private final Function0<Boolean> condition;

    public UntilDispatch(@NotNull Function0<Boolean> condition2) {
        Intrinsics.checkNotNullParameter(condition2, (String)"condition");
        this.condition = condition2;
    }

    @NotNull
    public final Function0<Boolean> getCondition() {
        return this.condition;
    }

    @Override
    public boolean canProceed() {
        return (Boolean)this.condition.invoke();
    }
}

