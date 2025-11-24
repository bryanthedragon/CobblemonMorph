/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  kotlin.Metadata
 *  kotlin.Unit
 *  kotlin.jvm.functions.Function2
 *  kotlin.jvm.internal.Intrinsics
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.dialogue;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.dialogue.ActiveDialogue;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.dialogue.DialogueAction;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000$\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0007\u0018\u00002\u00020\u0001B#\u0012\u001a\u0010\n\u001a\u0016\u0012\u0004\u0012\u00020\u0002\u0012\u0006\u0012\u0004\u0018\u00010\u0004\u0012\u0004\u0012\u00020\u00060\t\u00a2\u0006\u0004\b\u000e\u0010\u000fJ\"\u0010\u0007\u001a\u00020\u00062\u0006\u0010\u0003\u001a\u00020\u00022\b\u0010\u0005\u001a\u0004\u0018\u00010\u0004H\u0096\u0002\u00a2\u0006\u0004\b\u0007\u0010\bR+\u0010\n\u001a\u0016\u0012\u0004\u0012\u00020\u0002\u0012\u0006\u0012\u0004\u0018\u00010\u0004\u0012\u0004\u0012\u00020\u00060\t8\u0006\u00a2\u0006\f\n\u0004\b\n\u0010\u000b\u001a\u0004\b\f\u0010\r\u00a8\u0006\u0010"}, d2={"Lcom/cobblemon/mod/common/api/dialogue/FunctionDialogueAction;", "Lcom/cobblemon/mod/common/api/dialogue/DialogueAction;", "Lcom/cobblemon/mod/common/api/dialogue/ActiveDialogue;", "dialogue", "", "input", "", "invoke", "(Lcom/cobblemon/mod/common/api/dialogue/ActiveDialogue;Ljava/lang/String;)V", "Lkotlin/Function2;", "consumer", "Lkotlin/jvm/functions/Function2;", "getConsumer", "()Lkotlin/jvm/functions/Function2;", "<init>", "(Lkotlin/jvm/functions/Function2;)V", "common"})
public final class FunctionDialogueAction
implements DialogueAction {
    @NotNull
    private final Function2<ActiveDialogue, String, Unit> consumer;

    public FunctionDialogueAction(@NotNull Function2<? super ActiveDialogue, ? super String, Unit> consumer) {
        Intrinsics.checkNotNullParameter(consumer, (String)"consumer");
        this.consumer = consumer;
    }

    @NotNull
    public final Function2<ActiveDialogue, String, Unit> getConsumer() {
        return this.consumer;
    }

    @Override
    public void invoke(@NotNull ActiveDialogue dialogue2, @Nullable String input) {
        Intrinsics.checkNotNullParameter((Object)dialogue2, (String)"dialogue");
        this.consumer.invoke((Object)dialogue2, (Object)input);
    }
}

