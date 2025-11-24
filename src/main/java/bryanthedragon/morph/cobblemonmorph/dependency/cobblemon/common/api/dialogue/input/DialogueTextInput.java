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
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.dialogue.input;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.bedrock.molang.runtime.MoParams;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.bedrock.molang.runtime.struct.QueryStruct;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.dialogue.ActiveDialogue;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.dialogue.DialogueAction;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.dialogue.FunctionDialogueAction;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.dialogue.input.ActiveInput;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.dialogue.input.DialogueInput;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.dialogue.input.DialogueTextInput;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.dialogue.input.DialogueTimeout;
import java.util.HashMap;
import java.util.function.Function;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u00004\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\t\u0018\u00002\u00020\u0001B\u0007\u00a2\u0006\u0004\b\u0018\u0010\u0019J\u001f\u0010\u0007\u001a\u00020\u00062\u0006\u0010\u0003\u001a\u00020\u00022\u0006\u0010\u0005\u001a\u00020\u0004H\u0016\u00a2\u0006\u0004\b\u0007\u0010\bJ\u0017\u0010\n\u001a\u00020\t2\u0006\u0010\u0003\u001a\u00020\u0002H\u0016\u00a2\u0006\u0004\b\n\u0010\u000bR\u0017\u0010\r\u001a\u00020\f8\u0006\u00a2\u0006\f\n\u0004\b\r\u0010\u000e\u001a\u0004\b\u000f\u0010\u0010R$\u0010\u0012\u001a\u0004\u0018\u00010\u00118\u0016@\u0016X\u0096\u000e\u00a2\u0006\u0012\n\u0004\b\u0012\u0010\u0013\u001a\u0004\b\u0014\u0010\u0015\"\u0004\b\u0016\u0010\u0017\u00a8\u0006\u001a"}, d2={"Lcom/cobblemon/mod/common/api/dialogue/input/DialogueTextInput;", "Lcom/cobblemon/mod/common/api/dialogue/input/DialogueInput;", "Lcom/cobblemon/mod/common/api/dialogue/input/ActiveInput;", "activeInput", "", "value", "", "handle", "(Lcom/cobblemon/mod/common/api/dialogue/input/ActiveInput;Ljava/lang/String;)V", "Lcom/bedrockk/molang/runtime/struct/QueryStruct;", "toMoLangStruct", "(Lcom/cobblemon/mod/common/api/dialogue/input/ActiveInput;)Lcom/bedrockk/molang/runtime/struct/QueryStruct;", "Lcom/cobblemon/mod/common/api/dialogue/DialogueAction;", "action", "Lcom/cobblemon/mod/common/api/dialogue/DialogueAction;", "getAction", "()Lcom/cobblemon/mod/common/api/dialogue/DialogueAction;", "Lcom/cobblemon/mod/common/api/dialogue/input/DialogueTimeout;", "timeout", "Lcom/cobblemon/mod/common/api/dialogue/input/DialogueTimeout;", "getTimeout", "()Lcom/cobblemon/mod/common/api/dialogue/input/DialogueTimeout;", "setTimeout", "(Lcom/cobblemon/mod/common/api/dialogue/input/DialogueTimeout;)V", "<init>", "()V", "common"})
public final class DialogueTextInput
implements DialogueInput {
    @Nullable
    private DialogueTimeout timeout;
    @NotNull
    private final DialogueAction action = new FunctionDialogueAction((Function2<? super ActiveDialogue, ? super String, Unit>)((Function2)action.1.INSTANCE));

    @Override
    @Nullable
    public DialogueTimeout getTimeout() {
        return this.timeout;
    }

    @Override
    public void setTimeout(@Nullable DialogueTimeout dialogueTimeout) {
        this.timeout = dialogueTimeout;
    }

    @NotNull
    public final DialogueAction getAction() {
        return this.action;
    }

    @Override
    @NotNull
    public QueryStruct toMoLangStruct(@NotNull ActiveInput activeInput) {
        Intrinsics.checkNotNullParameter((Object)activeInput, (String)"activeInput");
        return new QueryStruct(new HashMap<String, Function<MoParams, Object>>());
    }

    @Override
    public void handle(@NotNull ActiveInput activeInput, @NotNull String value2) {
        Intrinsics.checkNotNullParameter((Object)activeInput, (String)"activeInput");
        Intrinsics.checkNotNullParameter((Object)value2, (String)"value");
        this.action.invoke(activeInput.getActiveDialogue(), value2);
    }
}

