/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  kotlin.Metadata
 *  kotlin.Unit
 *  kotlin.jvm.functions.Function2
 *  kotlin.jvm.internal.DefaultConstructorMarker
 *  kotlin.jvm.internal.Intrinsics
 *  org.jetbrains.annotations.NotNull
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.dialogue.input;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.dialogue.ActiveDialogue;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.dialogue.DialogueAction;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.dialogue.FunctionDialogueAction;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000 \n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010\u0007\n\u0002\b\u0006\n\u0002\u0010\u000b\n\u0002\b\t\u0018\u00002\u00020\u0001B%\u0012\b\b\u0002\u0010\n\u001a\u00020\t\u0012\b\b\u0002\u0010\u0011\u001a\u00020\u0010\u0012\b\b\u0002\u0010\u0003\u001a\u00020\u0002\u00a2\u0006\u0004\b\u0017\u0010\u0018R\"\u0010\u0003\u001a\u00020\u00028\u0006@\u0006X\u0086\u000e\u00a2\u0006\u0012\n\u0004\b\u0003\u0010\u0004\u001a\u0004\b\u0005\u0010\u0006\"\u0004\b\u0007\u0010\bR\"\u0010\n\u001a\u00020\t8\u0006@\u0006X\u0086\u000e\u00a2\u0006\u0012\n\u0004\b\n\u0010\u000b\u001a\u0004\b\f\u0010\r\"\u0004\b\u000e\u0010\u000fR\"\u0010\u0011\u001a\u00020\u00108\u0006@\u0006X\u0086\u000e\u00a2\u0006\u0012\n\u0004\b\u0011\u0010\u0012\u001a\u0004\b\u0013\u0010\u0014\"\u0004\b\u0015\u0010\u0016\u00a8\u0006\u0019"}, d2={"Lcom/cobblemon/mod/common/api/dialogue/input/DialogueTimeout;", "", "Lcom/cobblemon/mod/common/api/dialogue/DialogueAction;", "action", "Lcom/cobblemon/mod/common/api/dialogue/DialogueAction;", "getAction", "()Lcom/cobblemon/mod/common/api/dialogue/DialogueAction;", "setAction", "(Lcom/cobblemon/mod/common/api/dialogue/DialogueAction;)V", "", "duration", "F", "getDuration", "()F", "setDuration", "(F)V", "", "showTimer", "Z", "getShowTimer", "()Z", "setShowTimer", "(Z)V", "<init>", "(FZLcom/cobblemon/mod/common/api/dialogue/DialogueAction;)V", "common"})
public final class DialogueTimeout {
    private float duration;
    private boolean showTimer;
    @NotNull
    private DialogueAction action;

    public DialogueTimeout(float duration, boolean showTimer, @NotNull DialogueAction action2) {
        Intrinsics.checkNotNullParameter((Object)action2, (String)"action");
        this.duration = duration;
        this.showTimer = showTimer;
        this.action = action2;
    }

    public /* synthetic */ DialogueTimeout(float f, boolean bl, DialogueAction dialogueAction, int n, DefaultConstructorMarker defaultConstructorMarker) {
        if ((n & 1) != 0) {
            f = 10.0f;
        }
        if ((n & 2) != 0) {
            bl = true;
        }
        if ((n & 4) != 0) {
            dialogueAction = new FunctionDialogueAction((Function2<? super ActiveDialogue, ? super String, Unit>)((Function2)1.INSTANCE));
        }
        this(f, bl, dialogueAction);
    }

    public final float getDuration() {
        return this.duration;
    }

    public final void setDuration(float f) {
        this.duration = f;
    }

    public final boolean getShowTimer() {
        return this.showTimer;
    }

    public final void setShowTimer(boolean bl) {
        this.showTimer = bl;
    }

    @NotNull
    public final DialogueAction getAction() {
        return this.action;
    }

    public final void setAction(@NotNull DialogueAction dialogueAction) {
        Intrinsics.checkNotNullParameter((Object)dialogueAction, (String)"<set-?>");
        this.action = dialogueAction;
    }

    public DialogueTimeout() {
        this(0.0f, false, null, 7, null);
    }
}

