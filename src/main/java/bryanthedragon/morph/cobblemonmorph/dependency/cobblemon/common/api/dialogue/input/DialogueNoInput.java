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
 *  org.jetbrains.annotations.Nullable
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.dialogue.input;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.bedrock.molang.runtime.struct.VariableStruct;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.dialogue.ActiveDialogue;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.dialogue.DialogueAction;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.dialogue.FunctionDialogueAction;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.dialogue.input.ActiveInput;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.dialogue.input.DialogueInput;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.dialogue.input.DialogueTimeout;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u00004\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\b\u0018\u00002\u00020\u0001B\u0011\u0012\b\b\u0002\u0010\r\u001a\u00020\f\u00a2\u0006\u0004\b\u001a\u0010\u0012J\u001f\u0010\u0007\u001a\u00020\u00062\u0006\u0010\u0003\u001a\u00020\u00022\u0006\u0010\u0005\u001a\u00020\u0004H\u0016\u00a2\u0006\u0004\b\u0007\u0010\bJ\u0017\u0010\n\u001a\u00020\t2\u0006\u0010\u0003\u001a\u00020\u0002H\u0016\u00a2\u0006\u0004\b\n\u0010\u000bR\"\u0010\r\u001a\u00020\f8\u0006@\u0006X\u0086\u000e\u00a2\u0006\u0012\n\u0004\b\r\u0010\u000e\u001a\u0004\b\u000f\u0010\u0010\"\u0004\b\u0011\u0010\u0012R(\u0010\u0019\u001a\u0004\u0018\u00010\u00132\b\u0010\u0014\u001a\u0004\u0018\u00010\u00138V@VX\u0096\u000e\u00a2\u0006\f\u001a\u0004\b\u0015\u0010\u0016\"\u0004\b\u0017\u0010\u0018\u00a8\u0006\u001b"}, d2={"Lcom/cobblemon/mod/common/api/dialogue/input/DialogueNoInput;", "Lcom/cobblemon/mod/common/api/dialogue/input/DialogueInput;", "Lcom/cobblemon/mod/common/api/dialogue/input/ActiveInput;", "activeInput", "", "value", "", "handle", "(Lcom/cobblemon/mod/common/api/dialogue/input/ActiveInput;Ljava/lang/String;)V", "Lcom/bedrockk/molang/runtime/struct/VariableStruct;", "toMoLangStruct", "(Lcom/cobblemon/mod/common/api/dialogue/input/ActiveInput;)Lcom/bedrockk/molang/runtime/struct/VariableStruct;", "Lcom/cobblemon/mod/common/api/dialogue/DialogueAction;", "action", "Lcom/cobblemon/mod/common/api/dialogue/DialogueAction;", "getAction", "()Lcom/cobblemon/mod/common/api/dialogue/DialogueAction;", "setAction", "(Lcom/cobblemon/mod/common/api/dialogue/DialogueAction;)V", "Lcom/cobblemon/mod/common/api/dialogue/input/DialogueTimeout;", "<anonymous parameter 0>", "getTimeout", "()Lcom/cobblemon/mod/common/api/dialogue/input/DialogueTimeout;", "setTimeout", "(Lcom/cobblemon/mod/common/api/dialogue/input/DialogueTimeout;)V", "timeout", "<init>", "common"})
public final class DialogueNoInput
implements DialogueInput {
    @NotNull
    private DialogueAction action;

    public DialogueNoInput(@NotNull DialogueAction action2) {
        Intrinsics.checkNotNullParameter((Object)action2, (String)"action");
        this.action = action2;
    }

    public /* synthetic */ DialogueNoInput(DialogueAction dialogueAction, int n, DefaultConstructorMarker defaultConstructorMarker) {
        if ((n & 1) != 0) {
            dialogueAction = new FunctionDialogueAction((Function2<? super ActiveDialogue, ? super String, Unit>)((Function2)1.INSTANCE));
        }
        this(dialogueAction);
    }

    @NotNull
    public final DialogueAction getAction() {
        return this.action;
    }

    public final void setAction(@NotNull DialogueAction dialogueAction) {
        Intrinsics.checkNotNullParameter((Object)dialogueAction, (String)"<set-?>");
        this.action = dialogueAction;
    }

    @Override
    @Nullable
    public DialogueTimeout getTimeout() {
        return null;
    }

    @Override
    public void setTimeout(@Nullable DialogueTimeout dialogueTimeout) {
    }

    @Override
    @NotNull
    public VariableStruct toMoLangStruct(@NotNull ActiveInput activeInput) {
        Intrinsics.checkNotNullParameter((Object)activeInput, (String)"activeInput");
        return new VariableStruct();
    }

    @Override
    public void handle(@NotNull ActiveInput activeInput, @NotNull String value2) {
        Intrinsics.checkNotNullParameter((Object)activeInput, (String)"activeInput");
        Intrinsics.checkNotNullParameter((Object)value2, (String)"value");
        DialogueAction.DefaultImpls.invoke$default(this.action, activeInput.getActiveDialogue(), null, 2, null);
    }

    public DialogueNoInput() {
        this(null, 1, null);
    }
}

