/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  kotlin.Metadata
 *  kotlin.jvm.functions.Function1
 *  kotlin.jvm.internal.DefaultConstructorMarker
 *  kotlin.jvm.internal.Intrinsics
 *  org.jetbrains.annotations.NotNull
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.dialogue;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.dialogue.ActiveDialogue;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.dialogue.DialoguePredicate;
import kotlin.Metadata;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0007\u0018\u00002\u00020\u0001B\u001d\u0012\u0014\b\u0002\u0010\b\u001a\u000e\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020\u00040\u0007\u00a2\u0006\u0004\b\f\u0010\rJ\u0018\u0010\u0005\u001a\u00020\u00042\u0006\u0010\u0003\u001a\u00020\u0002H\u0096\u0002\u00a2\u0006\u0004\b\u0005\u0010\u0006R#\u0010\b\u001a\u000e\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020\u00040\u00078\u0006\u00a2\u0006\f\n\u0004\b\b\u0010\t\u001a\u0004\b\n\u0010\u000b\u00a8\u0006\u000e"}, d2={"Lcom/cobblemon/mod/common/api/dialogue/FunctionDialoguePredicate;", "Lcom/cobblemon/mod/common/api/dialogue/DialoguePredicate;", "Lcom/cobblemon/mod/common/api/dialogue/ActiveDialogue;", "dialogue", "", "invoke", "(Lcom/cobblemon/mod/common/api/dialogue/ActiveDialogue;)Z", "Lkotlin/Function1;", "predicate", "Lkotlin/jvm/functions/Function1;", "getPredicate", "()Lkotlin/jvm/functions/Function1;", "<init>", "(Lkotlin/jvm/functions/Function1;)V", "common"})
public final class FunctionDialoguePredicate
implements DialoguePredicate {
    @NotNull
    private final Function1<ActiveDialogue, Boolean> predicate;

    public FunctionDialoguePredicate(@NotNull Function1<? super ActiveDialogue, Boolean> predicate) {
        Intrinsics.checkNotNullParameter(predicate, (String)"predicate");
        this.predicate = predicate;
    }

    public /* synthetic */ FunctionDialoguePredicate(Function1 function1, int n, DefaultConstructorMarker defaultConstructorMarker) {
        if ((n & 1) != 0) {
            function1 = 1.INSTANCE;
        }
        this((Function1<? super ActiveDialogue, Boolean>)function1);
    }

    @NotNull
    public final Function1<ActiveDialogue, Boolean> getPredicate() {
        return this.predicate;
    }

    @Override
    public boolean invoke(@NotNull ActiveDialogue dialogue2) {
        Intrinsics.checkNotNullParameter((Object)dialogue2, (String)"dialogue");
        return (Boolean)this.predicate.invoke((Object)dialogue2);
    }

    public FunctionDialoguePredicate() {
        this(null, 1, null);
    }
}

