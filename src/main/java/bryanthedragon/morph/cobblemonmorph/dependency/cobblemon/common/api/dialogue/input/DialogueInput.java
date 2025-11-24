/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  kotlin.Metadata
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.dialogue.input;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.bedrock.molang.runtime.struct.MoStruct;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.dialogue.input.ActiveInput;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.dialogue.input.DialogueTimeout;
import kotlin.Metadata;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/*
 * Uses 'sealed' constructs - enablewith --sealed true
 */
@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000>\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\bv\u0018\u00002\u00020\u0001J\u001f\u0010\u0007\u001a\u00020\u00062\u0006\u0010\u0003\u001a\u00020\u00022\u0006\u0010\u0005\u001a\u00020\u0004H&\u00a2\u0006\u0004\b\u0007\u0010\bJ\u0017\u0010\n\u001a\u00020\t2\u0006\u0010\u0003\u001a\u00020\u0002H&\u00a2\u0006\u0004\b\n\u0010\u000bR\u001e\u0010\u0011\u001a\u0004\u0018\u00010\f8&@&X\u00a6\u000e\u00a2\u0006\f\u001a\u0004\b\r\u0010\u000e\"\u0004\b\u000f\u0010\u0010\u0082\u0001\u0004\u0012\u0013\u0014\u0015\u00a8\u0006\u0016"}, d2={"Lcom/cobblemon/mod/common/api/dialogue/input/DialogueInput;", "", "Lcom/cobblemon/mod/common/api/dialogue/input/ActiveInput;", "activeInput", "", "value", "", "handle", "(Lcom/cobblemon/mod/common/api/dialogue/input/ActiveInput;Ljava/lang/String;)V", "Lcom/bedrockk/molang/runtime/struct/MoStruct;", "toMoLangStruct", "(Lcom/cobblemon/mod/common/api/dialogue/input/ActiveInput;)Lcom/bedrockk/molang/runtime/struct/MoStruct;", "Lcom/cobblemon/mod/common/api/dialogue/input/DialogueTimeout;", "getTimeout", "()Lcom/cobblemon/mod/common/api/dialogue/input/DialogueTimeout;", "setTimeout", "(Lcom/cobblemon/mod/common/api/dialogue/input/DialogueTimeout;)V", "timeout", "Lcom/cobblemon/mod/common/api/dialogue/input/DialogueAutoContinueInput;", "Lcom/cobblemon/mod/common/api/dialogue/input/DialogueNoInput;", "Lcom/cobblemon/mod/common/api/dialogue/input/DialogueOptionSetInput;", "Lcom/cobblemon/mod/common/api/dialogue/input/DialogueTextInput;", "common"})
public interface DialogueInput {
    @Nullable
    public DialogueTimeout getTimeout();

    public void setTimeout(@Nullable DialogueTimeout var1);

    @NotNull
    public MoStruct toMoLangStruct(@NotNull ActiveInput var1);

    public void handle(@NotNull ActiveInput var1, @NotNull String var2);
}

