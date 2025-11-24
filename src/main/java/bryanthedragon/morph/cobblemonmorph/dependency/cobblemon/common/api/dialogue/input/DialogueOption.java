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
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.dialogue.DialoguePredicate;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.dialogue.DialogueText;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.dialogue.FunctionDialogueAction;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.dialogue.FunctionDialoguePredicate;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.dialogue.WrappedDialogueText;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000(\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010\u000e\n\u0002\b\t\u0018\u00002\u00020\u0001B9\u0012\b\b\u0002\u0010\u000f\u001a\u00020\u000e\u0012\b\b\u0002\u0010\u0016\u001a\u00020\u0015\u0012\b\b\u0002\u0010\u0003\u001a\u00020\u0002\u0012\b\b\u0002\u0010\r\u001a\u00020\t\u0012\b\b\u0002\u0010\n\u001a\u00020\t\u00a2\u0006\u0004\b\u001c\u0010\u001dR\"\u0010\u0003\u001a\u00020\u00028\u0006@\u0006X\u0086\u000e\u00a2\u0006\u0012\n\u0004\b\u0003\u0010\u0004\u001a\u0004\b\u0005\u0010\u0006\"\u0004\b\u0007\u0010\bR\u0017\u0010\n\u001a\u00020\t8\u0006\u00a2\u0006\f\n\u0004\b\n\u0010\u000b\u001a\u0004\b\n\u0010\fR\u0017\u0010\r\u001a\u00020\t8\u0006\u00a2\u0006\f\n\u0004\b\r\u0010\u000b\u001a\u0004\b\r\u0010\fR\"\u0010\u000f\u001a\u00020\u000e8\u0006@\u0006X\u0086\u000e\u00a2\u0006\u0012\n\u0004\b\u000f\u0010\u0010\u001a\u0004\b\u0011\u0010\u0012\"\u0004\b\u0013\u0010\u0014R\"\u0010\u0016\u001a\u00020\u00158\u0006@\u0006X\u0086\u000e\u00a2\u0006\u0012\n\u0004\b\u0016\u0010\u0017\u001a\u0004\b\u0018\u0010\u0019\"\u0004\b\u001a\u0010\u001b\u00a8\u0006\u001e"}, d2={"Lcom/cobblemon/mod/common/api/dialogue/input/DialogueOption;", "", "Lcom/cobblemon/mod/common/api/dialogue/DialogueAction;", "action", "Lcom/cobblemon/mod/common/api/dialogue/DialogueAction;", "getAction", "()Lcom/cobblemon/mod/common/api/dialogue/DialogueAction;", "setAction", "(Lcom/cobblemon/mod/common/api/dialogue/DialogueAction;)V", "Lcom/cobblemon/mod/common/api/dialogue/DialoguePredicate;", "isSelectable", "Lcom/cobblemon/mod/common/api/dialogue/DialoguePredicate;", "()Lcom/cobblemon/mod/common/api/dialogue/DialoguePredicate;", "isVisible", "Lcom/cobblemon/mod/common/api/dialogue/DialogueText;", "text", "Lcom/cobblemon/mod/common/api/dialogue/DialogueText;", "getText", "()Lcom/cobblemon/mod/common/api/dialogue/DialogueText;", "setText", "(Lcom/cobblemon/mod/common/api/dialogue/DialogueText;)V", "", "value", "Ljava/lang/String;", "getValue", "()Ljava/lang/String;", "setValue", "(Ljava/lang/String;)V", "<init>", "(Lcom/cobblemon/mod/common/api/dialogue/DialogueText;Ljava/lang/String;Lcom/cobblemon/mod/common/api/dialogue/DialogueAction;Lcom/cobblemon/mod/common/api/dialogue/DialoguePredicate;Lcom/cobblemon/mod/common/api/dialogue/DialoguePredicate;)V", "common"})
public final class DialogueOption {
    @NotNull
    private DialogueText text;
    @NotNull
    private String value;
    @NotNull
    private DialogueAction action;
    @NotNull
    private final DialoguePredicate isVisible;
    @NotNull
    private final DialoguePredicate isSelectable;

    public DialogueOption(@NotNull DialogueText text, @NotNull String value2, @NotNull DialogueAction action2, @NotNull DialoguePredicate isVisible, @NotNull DialoguePredicate isSelectable) {
        Intrinsics.checkNotNullParameter((Object)text, (String)"text");
        Intrinsics.checkNotNullParameter((Object)value2, (String)"value");
        Intrinsics.checkNotNullParameter((Object)action2, (String)"action");
        Intrinsics.checkNotNullParameter((Object)isVisible, (String)"isVisible");
        Intrinsics.checkNotNullParameter((Object)isSelectable, (String)"isSelectable");
        this.text = text;
        this.value = value2;
        this.action = action2;
        this.isVisible = isVisible;
        this.isSelectable = isSelectable;
    }

    public /* synthetic */ DialogueOption(DialogueText dialogueText, String string, DialogueAction dialogueAction, DialoguePredicate dialoguePredicate, DialoguePredicate dialoguePredicate2, int n, DefaultConstructorMarker defaultConstructorMarker) {
        if ((n & 1) != 0) {
            dialogueText = new WrappedDialogueText(null, 1, null);
        }
        if ((n & 2) != 0) {
            string = "";
        }
        if ((n & 4) != 0) {
            dialogueAction = new FunctionDialogueAction((Function2<? super ActiveDialogue, ? super String, Unit>)((Function2)1.INSTANCE));
        }
        if ((n & 8) != 0) {
            dialoguePredicate = new FunctionDialoguePredicate(null, 1, null);
        }
        if ((n & 0x10) != 0) {
            dialoguePredicate2 = new FunctionDialoguePredicate(null, 1, null);
        }
        this(dialogueText, string, dialogueAction, dialoguePredicate, dialoguePredicate2);
    }

    @NotNull
    public final DialogueText getText() {
        return this.text;
    }

    public final void setText(@NotNull DialogueText dialogueText) {
        Intrinsics.checkNotNullParameter((Object)dialogueText, (String)"<set-?>");
        this.text = dialogueText;
    }

    @NotNull
    public final String getValue() {
        return this.value;
    }

    public final void setValue(@NotNull String string) {
        Intrinsics.checkNotNullParameter((Object)string, (String)"<set-?>");
        this.value = string;
    }

    @NotNull
    public final DialogueAction getAction() {
        return this.action;
    }

    public final void setAction(@NotNull DialogueAction dialogueAction) {
        Intrinsics.checkNotNullParameter((Object)dialogueAction, (String)"<set-?>");
        this.action = dialogueAction;
    }

    @NotNull
    public final DialoguePredicate isVisible() {
        return this.isVisible;
    }

    @NotNull
    public final DialoguePredicate isSelectable() {
        return this.isSelectable;
    }

    public DialogueOption() {
        this(null, null, null, null, null, 31, null);
    }
}

